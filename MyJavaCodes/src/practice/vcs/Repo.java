package practice.vcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Repo {
	static Path path = Paths.get(".\\resource\\repo");

	public static boolean isDirectory(Path p, String ver) {
		return path.resolve(ver).resolve(p).toFile().isDirectory();
	}

	public void copy(String newVer, Path relative, Path fromReal) throws IOException {
		Path toReal = path.resolve(newVer).resolve(relative);
		if (fromReal.toFile().isDirectory()) {
			Files.createDirectories(toReal);
		} else {
			Files.createDirectories(toReal.getParent());
			Files.copy(fromReal, toReal);
		}
	}

	public void copy(String newVer, Path relative, List<String> lines) throws IOException {
		Path toReal = path.resolve(newVer).resolve(relative);
		Files.createDirectories(toReal.getParent());
		Files.write(toReal, lines);
	}
}