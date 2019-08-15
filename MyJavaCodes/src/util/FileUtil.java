package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileUtil {

	@FunctionalInterface
	public static interface FunctionWithException<T, R, E extends Exception> {
		R apply(T t) throws E;
	}

	private static <T, R, E extends Exception> Function<T, R> fWrapper(FunctionWithException<T, R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	public interface ConsumerWithException<T, E extends Exception> {
		void accept(T t) throws E;
	}

	private static <T, E extends Exception> Consumer<T> cWrapper(ConsumerWithException<T, E> ce) {
		return arg -> {
			try {
				ce.accept(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	private static void dirCopy3(Path src, Path dstParent) throws IOException {
		Files.walk(src).forEach(cWrapper(
				p -> Files.copy(p, dstParent.resolve(src.relativize(p)), StandardCopyOption.REPLACE_EXISTING)));
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

		for (File f : srcD.listFiles()) {
			if (f.isDirectory()) {
				dirCopy(f.getPath(), destD.getPath());
			} else {
				Files.copy(f.toPath(), new File(destD, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private static void dirDelete(String dir) throws IOException {
		File target = new File(dir);
		for (File f : target.listFiles()) {
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

	private static boolean isSame(Path p1, Path p2) throws IOException {
		if (p1.toFile().length() != p2.toFile().length()) {
			return false;
		}
		return Arrays.equals(Files.readAllBytes(p1), Files.readAllBytes(p2));
//		try (BufferedInputStream bis1 = new BufferedInputStream(Files.newInputStream(p1, StandardOpenOption.READ));
//				BufferedInputStream bis2 = new BufferedInputStream(Files.newInputStream(p2, StandardOpenOption.READ))) {
//			while (bis1.available() > 0 || bis2.available() > 0) {
//				if (bis1.read() != bis2.read()) {
//					return false;
//				}
//			}
//		}
//		return true;
	}

	private static boolean isSame(File f1, File f2) throws IOException {
		return isSame(f1.toPath(), f2.toPath());
	}

	private static List<Path> allFiles(Path root) throws IOException {
		return Files.walk(root).collect(Collectors.toList());
	}

	private static void walkFileTree(Path root) throws IOException {
		Files.walkFileTree(root, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("preVisitDirectory: " + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("visitFile: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				System.out.println("visitFileFailed: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				System.out.println("postVisitDirectory: " + dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	public static void main(String[] args) throws IOException {
		dirCopy3(Paths.get("C:\\ljg\\tmp\\1"), Paths.get("C:\\ljg\\tmp\\2"));
//		dirDelete("C:\\ljg\\tmp\\2\\1");
//		System.out.println(searchFile("C:\\ljg\\eclipse\\ws\\git\\repo1\\MyJavaCodes\\resource\SOURCEDIR", ".txt"));
//		dirCopy2(".\\resource\\SRCTOP", ".\\resource\\DESTOP");
//		System.out.println(
//				isSame(new File(".\\resource\\practice\\diff\\DESTOP\\SRCTOP\\eclipse.ini"),
//						new File(".\\resource\\practice\\diff\\SRCTOP\\eclipse.ini")));
//		allFiles(Paths.get(".\\resource\\practice\\diff")).stream().forEach(System.out::println);
	}
}
