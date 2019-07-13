package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class FileUtil {

	public static void main(String[] args) throws IOException {
//		dirCopy("C:\\ljg\\tmp\\1", "C:\\ljg\\tmp\\2");
//		dirDelete("C:\\ljg\\tmp\\2\\1");
//		System.out.println(searchFile("C:\\ljg\\eclipse\\ws\\git\\repo1\\MyJavaCodes\\resource\SOURCEDIR", ".txt"));
		dirCopy2(".\\resource\\SRCTOP", ".\\resource\\DESTOP");
	}
	
	private static void dirCopy2(String src, String destParent) throws IOException {
		Path srcPath = Paths.get(src);
		Path dstPath = Paths.get(destParent, srcPath.getFileName().toString());

		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(new File(src));
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			}
			Files.copy(f.toPath(), dstPath.resolve(srcPath.relativize(f.toPath())),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private static void dirCopy(String src, String destParent) throws IOException {
		File srcD = new File(src);
		File destD = new File(destParent, srcD.getName());
		destD.mkdir();

		File[] fs = srcD.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				dirCopy(f.getPath(), destD.getPath());
			} else {
				Files.copy(f.toPath(), new File(destD, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private static void dirDelete(String dir) throws IOException {
		File target = new File(dir);
		File[] fs = target.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				dirDelete(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
		target.delete();
	}

	private static void dirMove(String src, String dest) throws IOException {
		dirCopy("C:\\ljg\\tmp\\1", "C:\\ljg\\tmp\\2");
		dirDelete("C:\\ljg\\tmp\\2\\1");
	}

	private static Set<File> searchFile(String dir, String endsWith) {
		String endsWithUppercase = endsWith.toUpperCase();
		Set<File> result = new HashSet<>();
		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(new File(dir));
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			} else if (f.getName().toUpperCase().endsWith(endsWithUppercase)) {
				result.add(f);
			}
		}
		return result;
	}
}
