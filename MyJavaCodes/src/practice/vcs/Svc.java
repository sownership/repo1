package practice.vcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Svc {

	Repo repo = new Repo();
	Meta meta;
	Checkin checkin = new Checkin();
	Merge merge = new Merge();

	Svc() throws IOException {
		this.meta = new Meta();
	}

	/**
	 * @return relativePath, repoRealPath
	 * @throws IOException
	 */
	Stream<Entry<Path, Path>> checkoutTargets() throws IOException {
		return meta.checkoutTargetsInfo().entrySet().stream();
	}

	Stream<Entry<Path, Path>> checkoutTargets(Path target) throws IOException {
		return meta.checkoutTargetsInfo(target).entrySet().stream();
	}
	
	void checkIn(String cmdLine, List<String> lines) throws IOException {
		checkin.checkIn(cmdLine, lines, meta, repo);
	}

	/**
	 * @param cmdLine
	 * @param inputTargets relative
	 * @throws IOException
	 */
	void checkIn(String cmdLine, Path[] inputTargets, Path localRoot) throws IOException {
		checkin.checkIn(cmdLine, inputTargets, localRoot, meta, repo);
	}

	List<String> merge(String cmdLine, List<String> lines) throws IOException {
		// cmdLine: MERGE relativepath basever
		Path relative = Paths.get(cmdLine.split(" ")[1]);
		String baseVer = cmdLine.split(" ")[2];
		List<String> base = Files.readAllLines(Repo.path.resolve(baseVer).resolve(relative));
		List<String> recent = Files
				.readAllLines(Repo.path.resolve(Meta.relativeStatus.get(relative).ver).resolve(relative));
		return merge.merge(base, recent, lines);
	}
}
