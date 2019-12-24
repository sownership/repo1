package practice.vcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Meta {
	static class Status {
		String ver;
		String state;

		Status(String ver, String status) {
			this.ver = ver;
			this.state = status;
		}

		@Override
		public String toString() {
			return ver + ":" + state;
		}
	}

	static Path path = Repo.path.resolve("meta.txt");
	// repo 각 relative 의 최신 state 를 유지한다
	static Map<Path, Status> relativeStatus = new HashMap<>();

	Meta() throws IOException {
		updateRelativeStatus();
	}

	// ver:01
	// files:A#1.txt,2.txt:M#3.txt
	// comment:c1
	private void updateRelativeStatus() throws IOException {
		// meta 파일이 있는 경우에 최신 정보를 유지한다
		if (!path.toFile().exists()) {
			relativeStatus.clear();
			return;
		}
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String verLine;
			while ((verLine = br.readLine()) != null) {
				String ver = verLine.split(":")[1];
				Arrays.stream(br.readLine().split(":")).skip(1).forEach(s -> {
					String state = s.split("#")[0];
					Arrays.stream(s.split("#")[1].split(",")).forEach(fn -> {
						relativeStatus.put(Paths.get(fn), new Status(ver, state));
					});
				});
				br.readLine();
			}
		}
		System.out.println("------------------------------updateRelativeStatus");
		relativeStatus.entrySet().stream().sorted((l, r) -> l.getKey().compareTo(r.getKey())).forEach(e -> {
			System.out.println(e.getKey() + ", " + e.getValue());
		});
	}

	String newVer() throws IOException {
		// 01 버전부터 시작한다
		if (!path.toFile().exists()) {
			return "01";
		}
		List<String> metaLines = Files.readAllLines(path);
		return String.format("%02d", Integer.parseInt(metaLines.get(metaLines.size() - 3).split(":")[1]) + 1);
	}

	List<Path> adding(Path... inputTargets) {
		// 밑에 parent 들 추가할 때 중복나지 않도록 set 으로 한다
		Set<Path> adding = new HashSet<>();
		// inputTargets 의 각각이 add 대상이면 추가하고
		// 각 p 의 parent 들도 추가 대상인지 확인해서 추가해 준다
		Arrays.stream(inputTargets).filter(this::needToAdd).forEach(p -> {
			adding.add(p);
			Path tmp = p;
			while ((tmp = tmp.getParent()) != null) {
				if (!adding.contains(tmp) && needToAdd(tmp)) {
					adding.add(tmp);
				}
			}
		});
		// 정렬해서 준다
		return adding.stream().sorted().collect(Collectors.toList());
	}

	private boolean needToAdd(Path p) {
		// checkin 한 적이 없거나 있는데 D 가 마지막이면 추가 대상이다
		return !relativeStatus.containsKey(p)
				|| (relativeStatus.containsKey(p) && relativeStatus.get(p).state.equals("D"));
	}

	/**
	 * @param inputTargets
	 * @param compare      <repo, local, compareresult>
	 * @return
	 */
	List<Path> modifying(BiFunction<Path, Path, Boolean> compare, Path... inputTargets) {
		// inputTargets 각각이 수정된 것이면 정렬해서 list 로 돌려준다
		return Arrays.stream(inputTargets).filter(ExHelper.pWrapper(p -> needToModify(p, compare))).sorted()
				.collect(Collectors.toList());
	}

	private boolean needToModify(Path p, BiFunction<Path, Path, Boolean> compare) throws IOException {
		// checkin 한 적이 있고 마지막이 D 가 아니며 디렉토리가 아니고 실제 내용이 수정되었으면 수정된 것이다
		return (relativeStatus.containsKey(p) && !relativeStatus.get(p).state.equals("D"))
				&& !Repo.isDirectory(p, relativeStatus.get(p).ver) && isModified(p, compare);
	}

	private boolean isModified(Path p, BiFunction<Path, Path, Boolean> compare) throws IOException {
		// 마지막으로 checkin 된 파일과 요청된 파일이 진짜 다른지 확인한다
		// relative 에 대한 요청되는 파일들을 일괄로 받지 않고 comare 를 받아서 처리한다
		return !compare.apply(Paths.get(relativeStatus.get(p).ver).resolve(p), p);
	}

	List<Path> deleting(Path inputTarget) {
		return deleting(false, inputTarget);
	}

	List<Path> deleting(boolean isAll, Path... inputTargets) {
		// 마지막으로 checkin 된 파일들 각각을 삭제 대상인지 판단해서 정렬해서 list 로 준다
		return relativeStatus.entrySet().stream().filter(e -> {
			return needToDelete(isAll, e, inputTargets);
		}).map(e -> e.getKey()).sorted().collect(Collectors.toList());
	}

	private boolean needToDelete(boolean isAll, Entry<Path, Status> meta, Path... inputTargets) {
		Status status = meta.getValue();
		// 마지막 checkin 이 D 가 아니어야 삭제할 지 계속 따져본다
		if (!status.state.equals("D")) {
			// 지금 checkin 하려는 파일들에 있으면 A나 M이므로 D 대상이 아니다
			if (Arrays.stream(inputTargets).anyMatch(p -> p.equals(meta.getKey()))) {
				return false;
			} else {
				// 지금 checkin 하려는 파일들에 meta 가 없는데 meta 의 상위가 checkin 대상이면 meta 는 삭제 대상으로 판단
				return isAll || Arrays.stream(inputTargets).anyMatch(p -> meta.getKey().startsWith(p));
			}
		} else {
			// 마지막 checkin 이 D 이면 다시 D 할 필요가 없다
			return false;
		}
	}

	public void add(List<Path> addings, List<Path> modifyings, List<Path> deletings, String comment)
			throws IOException {
		// meta 파일에 새로운 버전 정보를 추가한다
		List<String> newMetaLines = newMetaLines(newVer(), addings, modifyings, deletings, comment);
		System.out.println("------------------------------newMetaLines");
		newMetaLines.forEach(System.out::println);
		Files.write(path, newMetaLines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		// meta 파일이 변경되었으므로 repo 각 relative 의 최신 state 를 갱신한다
		updateRelativeStatus();
	}

	private List<String> newMetaLines(String newVer, List<Path> addings, List<Path> modifyings, List<Path> deletings,
			String comment) throws IOException {

		// ver:01
		// files:A#1.txt,2.txt:M#3.txt:D#4.txt
		// comment:c1
		List<String> newMetaLines = new ArrayList<>();
		newMetaLines.add("VER:" + newVer);

		String addingsStr = addings.size() <= 0 ? ""
				: ":A#" + addings.stream().map(Path::toString).collect(Collectors.joining(","));
		String modifyingsStr = modifyings.size() <= 0 ? ""
				: ":M#" + modifyings.stream().map(Path::toString).collect(Collectors.joining(","));
		String deletingsStr = deletings.size() <= 0 ? ""
				: ":D#" + deletings.stream().map(Path::toString).collect(Collectors.joining(","));
		newMetaLines.add("files" + addingsStr + modifyingsStr + deletingsStr);

		newMetaLines.add("comment:" + comment);
		return newMetaLines;
	}

	/**
	 * @return relativePath, repoRealPath
	 */
	public Map<Path, Path> checkoutTargetsInfo() {
		// checkout 할 대상들을 상대주소와 실제 복사할 파일 경로의 맵 형태로 돌려준다
		// repo relative 별 최종 checkin 정보에서 D 를 빼고 준다
		return relativeStatus.entrySet().stream().filter(e -> !e.getValue().state.equals("D")).collect(
				Collectors.toMap(e -> e.getKey(), e -> Repo.path.resolve(e.getValue().ver).resolve(e.getKey())));
	}

	/**
	 * @return relativePath, repoRealPath
	 */
	public Map<Path, Path> checkoutTargetsInfo(Path target) {
		// checkout 할 대상들을 상대주소와 실제 복사할 파일 경로의 맵 형태로 돌려준다
		// repo relative 별 최종 checkin 정보에서 D 를 빼고 준다
		return relativeStatus.entrySet().stream().filter(e -> !e.getValue().state.equals("D"))
				.filter(e -> e.getKey().startsWith(target)).collect(Collectors.toMap(e -> e.getKey(),
						e -> Repo.path.resolve(e.getValue().ver).resolve(e.getKey())));
	}
}
