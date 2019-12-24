package practice.vcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Local {
	static Path path = Paths.get(".\\resource\\local");

	/**
	 * @param relative
	 * @return childs(relative)
	 * @throws IOException
	 */
	public List<Path> getChildsRecursively(String relative) throws IOException {
		List<Path> childs = walk(relative);
		childs.remove(0);
		return childs;
	}

	/**
	 * @param relative
	 * @return this and childs(relative)
	 * @throws IOException
	 */
	public List<Path> walk(String relative) throws IOException {
		List<Path> childs = Files.walk(path.resolve(relative)).map(p -> path.relativize(p))
				.collect(Collectors.toList());
		return childs;
	}

	public boolean isDirectory(String relative) {
		return path.resolve(relative).toFile().isDirectory();
	}

	public void copy(Path relative, Path fromReal) throws IOException {
		Path toReal = path.resolve(relative);
		if (fromReal.toFile().isDirectory()) {
			Files.createDirectories(toReal);
		} else {
			Files.createDirectories(toReal.getParent());
			Files.copy(fromReal, toReal, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public void clear(String relative) throws IOException {
		Stream<Path> preStream = null;
		if ("".equals(relative)) {
			preStream = Files.walk(path).filter(p -> !p.equals(path));
		} else {
			preStream = Files.walk(path.resolve(Paths.get(relative)));
		}
		preStream.sorted(Collections.reverseOrder()).forEach(ExHelper.cWrapper(p -> Files.delete(p)));
	}

//	public static void main(String[] args) throws IOException {
//		Files.copy(Paths.get(".\\resource\\local\\sub2"), Paths.get(".\\resource\\local\\sub4"));
//	}
}