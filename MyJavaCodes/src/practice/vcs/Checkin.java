package practice.vcs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Checkin {

	void checkIn(String cmdLine, List<String> lines, Meta meta, Repo repo) throws IOException {
		String fileStr = cmdLine.split(" ")[1];
		String comment = cmdLine.split(" ")[2];

		System.out.println("------------------------------checkinTargetsInRemote");
		System.out.println(fileStr);

		Path relative = Paths.get(fileStr);
		List<Path> addings = meta.adding(relative);
		List<Path> modifyings = meta.modifying(
				ExHelper.bfWrapper((repoRelative, localRelative) -> Files.readAllLines(repoRelative).equals(lines)),
				relative);
		List<Path> deletings = meta.deleting(false, relative);

		// dir modification state apply
//		Stream.concat(Stream.concat(addings.stream(), modifyings.stream()), deletings.stream()).map(p -> {
//			AtomicReference<Path> ar = new AtomicReference<>(p);
//			return IntStream.range(0, p.getNameCount() - 1).mapToObj(i -> {
//				ar.set(ar.get().getParent());
//				return ar.get();
//			}).filter(p2 -> !addings.contains(p2) && !deletings.contains(p2)).collect(Collectors.toSet());
//		}).flatMap(s -> s.stream()).forEach(modifyings::add);

		print(addings, modifyings, deletings);

		if (addings.size() + modifyings.size() + deletings.size() == 0) {
			return;
		}

		String newVer = meta.newVer();
		System.out.println("------------------------------newVer");
		System.out.println(newVer);

		System.out.println("------------------------------copy targets");
		Stream.concat(addings.stream(), modifyings.stream()).forEach(ExHelper.cWrapper(p -> {
			System.out.println(p);
			repo.copy(newVer, p, lines);
		}));

		// add meta
		meta.add(addings, modifyings, deletings, comment);
	}

	/**
	 * @param cmdLine
	 * @param inputTargets relative
	 * @throws IOException
	 */
	void checkIn(String cmdLine, Path[] inputTargets, Path localRoot, Meta meta, Repo repo) throws IOException {
		String filesStr = cmdLine.split(" ")[1];
		String comment = cmdLine.split(" ")[2];

		System.out.println("------------------------------checkinTargetsInLocal");
		Arrays.stream(inputTargets).sorted().forEach(System.out::println);

		List<Path> addings = meta.adding(inputTargets);
		List<Path> modifyings = meta.modifying(
				ExHelper.bfWrapper(
						(repoRelative, localRelative) -> isSameContent(repoRelative, localRelative, localRoot)),
				inputTargets);
		List<Path> deletings = meta.deleting("*".equals(filesStr), inputTargets);

		print(addings, modifyings, deletings);

		if (addings.size() + modifyings.size() + deletings.size() == 0) {
			return;
		}

		String newVer = meta.newVer();
		System.out.println("------------------------------newVer");
		System.out.println(newVer);

		System.out.println("------------------------------copy targets");
		Stream.concat(addings.stream(), modifyings.stream()).forEach(ExHelper.cWrapper(p -> {
			System.out.println(p);
			repo.copy(newVer, p, localRoot.resolve(p));
		}));

		// add meta
		meta.add(addings, modifyings, deletings, comment);
	}

	private boolean isSameContent(Path repoRelative, Path localRelative, Path localRoot) throws IOException {
		Path repoPath = Repo.path.resolve(repoRelative);
		Path localPath = localRoot.resolve(localRelative);
		if (repoPath.toFile().length() != localPath.toFile().length()) {
			return false;
		}
		try (BufferedInputStream bis1 = new BufferedInputStream(Files.newInputStream(repoPath));
				BufferedInputStream bis2 = new BufferedInputStream(Files.newInputStream(localPath))) {
			while (bis1.available() > 0 || bis2.available() > 0) {
				if (bis1.read() != bis2.read()) {
					return false;
				}
			}
		}
		return true;
	}

	private void print(List<Path> addings, List<Path> modifyings, List<Path> deletings) {
		System.out.println("------------------------------addings");
		addings.forEach(System.out::println);
		System.out.println("------------------------------modifyings");
		modifyings.forEach(System.out::println);
		System.out.println("------------------------------deletings");
		deletings.forEach(System.out::println);
	}
}
