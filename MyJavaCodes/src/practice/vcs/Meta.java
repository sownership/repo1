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
	// repo �� relative �� �ֽ� state �� �����Ѵ�
	static Map<Path, Status> relativeStatus = new HashMap<>();

	Meta() throws IOException {
		updateRelativeStatus();
	}

	// ver:01
	// files:A#1.txt,2.txt:M#3.txt
	// comment:c1
	private void updateRelativeStatus() throws IOException {
		// meta ������ �ִ� ��쿡 �ֽ� ������ �����Ѵ�
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
		// 01 �������� �����Ѵ�
		if (!path.toFile().exists()) {
			return "01";
		}
		List<String> metaLines = Files.readAllLines(path);
		return String.format("%02d", Integer.parseInt(metaLines.get(metaLines.size() - 3).split(":")[1]) + 1);
	}

	List<Path> adding(Path... inputTargets) {
		// �ؿ� parent �� �߰��� �� �ߺ����� �ʵ��� set ���� �Ѵ�
		Set<Path> adding = new HashSet<>();
		// inputTargets �� ������ add ����̸� �߰��ϰ�
		// �� p �� parent �鵵 �߰� ������� Ȯ���ؼ� �߰��� �ش�
		Arrays.stream(inputTargets).filter(this::needToAdd).forEach(p -> {
			adding.add(p);
			Path tmp = p;
			while ((tmp = tmp.getParent()) != null) {
				if (!adding.contains(tmp) && needToAdd(tmp)) {
					adding.add(tmp);
				}
			}
		});
		// �����ؼ� �ش�
		return adding.stream().sorted().collect(Collectors.toList());
	}

	private boolean needToAdd(Path p) {
		// checkin �� ���� ���ų� �ִµ� D �� �������̸� �߰� ����̴�
		return !relativeStatus.containsKey(p)
				|| (relativeStatus.containsKey(p) && relativeStatus.get(p).state.equals("D"));
	}

	/**
	 * @param inputTargets
	 * @param compare      <repo, local, compareresult>
	 * @return
	 */
	List<Path> modifying(BiFunction<Path, Path, Boolean> compare, Path... inputTargets) {
		// inputTargets ������ ������ ���̸� �����ؼ� list �� �����ش�
		return Arrays.stream(inputTargets).filter(ExHelper.pWrapper(p -> needToModify(p, compare))).sorted()
				.collect(Collectors.toList());
	}

	private boolean needToModify(Path p, BiFunction<Path, Path, Boolean> compare) throws IOException {
		// checkin �� ���� �ְ� �������� D �� �ƴϸ� ���丮�� �ƴϰ� ���� ������ �����Ǿ����� ������ ���̴�
		return (relativeStatus.containsKey(p) && !relativeStatus.get(p).state.equals("D"))
				&& !Repo.isDirectory(p, relativeStatus.get(p).ver) && isModified(p, compare);
	}

	private boolean isModified(Path p, BiFunction<Path, Path, Boolean> compare) throws IOException {
		// ���������� checkin �� ���ϰ� ��û�� ������ ��¥ �ٸ��� Ȯ���Ѵ�
		// relative �� ���� ��û�Ǵ� ���ϵ��� �ϰ��� ���� �ʰ� comare �� �޾Ƽ� ó���Ѵ�
		return !compare.apply(Paths.get(relativeStatus.get(p).ver).resolve(p), p);
	}

	List<Path> deleting(Path inputTarget) {
		return deleting(false, inputTarget);
	}

	List<Path> deleting(boolean isAll, Path... inputTargets) {
		// ���������� checkin �� ���ϵ� ������ ���� ������� �Ǵ��ؼ� �����ؼ� list �� �ش�
		return relativeStatus.entrySet().stream().filter(e -> {
			return needToDelete(isAll, e, inputTargets);
		}).map(e -> e.getKey()).sorted().collect(Collectors.toList());
	}

	private boolean needToDelete(boolean isAll, Entry<Path, Status> meta, Path... inputTargets) {
		Status status = meta.getValue();
		// ������ checkin �� D �� �ƴϾ�� ������ �� ��� ��������
		if (!status.state.equals("D")) {
			// ���� checkin �Ϸ��� ���ϵ鿡 ������ A�� M�̹Ƿ� D ����� �ƴϴ�
			if (Arrays.stream(inputTargets).anyMatch(p -> p.equals(meta.getKey()))) {
				return false;
			} else {
				// ���� checkin �Ϸ��� ���ϵ鿡 meta �� ���µ� meta �� ������ checkin ����̸� meta �� ���� ������� �Ǵ�
				return isAll || Arrays.stream(inputTargets).anyMatch(p -> meta.getKey().startsWith(p));
			}
		} else {
			// ������ checkin �� D �̸� �ٽ� D �� �ʿ䰡 ����
			return false;
		}
	}

	public void add(List<Path> addings, List<Path> modifyings, List<Path> deletings, String comment)
			throws IOException {
		// meta ���Ͽ� ���ο� ���� ������ �߰��Ѵ�
		List<String> newMetaLines = newMetaLines(newVer(), addings, modifyings, deletings, comment);
		System.out.println("------------------------------newMetaLines");
		newMetaLines.forEach(System.out::println);
		Files.write(path, newMetaLines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		// meta ������ ����Ǿ����Ƿ� repo �� relative �� �ֽ� state �� �����Ѵ�
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
		// checkout �� ������ ����ּҿ� ���� ������ ���� ����� �� ���·� �����ش�
		// repo relative �� ���� checkin �������� D �� ���� �ش�
		return relativeStatus.entrySet().stream().filter(e -> !e.getValue().state.equals("D")).collect(
				Collectors.toMap(e -> e.getKey(), e -> Repo.path.resolve(e.getValue().ver).resolve(e.getKey())));
	}

	/**
	 * @return relativePath, repoRealPath
	 */
	public Map<Path, Path> checkoutTargetsInfo(Path target) {
		// checkout �� ������ ����ּҿ� ���� ������ ���� ����� �� ���·� �����ش�
		// repo relative �� ���� checkin �������� D �� ���� �ش�
		return relativeStatus.entrySet().stream().filter(e -> !e.getValue().state.equals("D"))
				.filter(e -> e.getKey().startsWith(target)).collect(Collectors.toMap(e -> e.getKey(),
						e -> Repo.path.resolve(e.getValue().ver).resolve(e.getKey())));
	}
}
