package practice.diff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Diff2 {

	public static void main(String[] args) throws IOException {

		Path lRootPath = Paths.get(".\\resource\\practice\\diff\\SRCTOP");
		Path rRootPath = Paths.get(".\\resource\\practice\\diff\\DESTOP\\SRCTOP");

		List<Path> lRelativePathOfChilds = getRelativePathOfChilds(lRootPath);
		List<Path> rRelativePathOfChilds = getRelativePathOfChilds(rRootPath);

		Collections.sort(lRelativePathOfChilds, Collections.reverseOrder());
		Collections.sort(rRelativePathOfChilds, Collections.reverseOrder());

		NumberList lNumberList = new NumberList(lRelativePathOfChilds);
		NumberList rNumberList = new NumberList(rRelativePathOfChilds);

		Path lPath = lNumberList.next();
		Path rPath = rNumberList.next();

		Set<Path> sames = new HashSet<>();

		while (true) {
			if (lPath == null) {
				if (rPath == null) {
					break;
				} else {
					System.out.println("R:" + rRootPath.resolve(rPath));
					rPath = rNumberList.next();
				}
			} else {
				if (rPath == null) {
					System.out.println("L:" + lRootPath.resolve(lPath));
					lPath = lNumberList.next();
				} else {
					Path lFullPath = lRootPath.resolve(lPath);
					Path rFullPath = rRootPath.resolve(rPath);

					int comp = lPath.compareTo(rPath);
					if (comp > 0) {
						System.out.println("L:" + lFullPath);
						lPath = lNumberList.next();
					} else if (comp < 0) {
						System.out.println("R:" + rFullPath);
						rPath = rNumberList.next();
					} else {
						if (lFullPath.toFile().isDirectory()) {
							if (rFullPath.toFile().isDirectory()) {
								File[] lChilds = lFullPath.toFile().listFiles();
								File[] rChilds = rFullPath.toFile().listFiles();
								if (lChilds.length != rChilds.length) {
									System.out.println("D:" + lFullPath + ", " + rFullPath);
								} else {
									if (Stream.concat(Arrays.stream(lChilds), Arrays.stream(rChilds)).anyMatch((f) -> {
										return !sames.contains(f.toPath());
									})) {
										System.out.println("D:" + lFullPath + ", " + rFullPath);
									} else {
										System.out.println("E:" + lFullPath + ", " + rFullPath);
									}
								}
							} else {
								System.out.println("L:" + lFullPath);
								System.out.println("R:" + rFullPath);
							}
						} else {
							if (rFullPath.toFile().isDirectory()) {
								System.out.println("L:" + lFullPath);
								System.out.println("R:" + rFullPath);
							} else {
								if (lFullPath.toFile().length() != rFullPath.toFile().length()) {
									System.out.println("D:" + lFullPath + ", " + rFullPath);
								} else {
									if (!Arrays.equals(Files.readAllBytes(lFullPath), Files.readAllBytes(rFullPath))) {
										System.out.println("D:" + lFullPath + ", " + rFullPath);
									} else {
										sames.add(lFullPath);
										sames.add(rFullPath);
										System.out.println("E:" + lFullPath + ", " + rFullPath);
									}
								}
							}
						}
						lPath = lNumberList.next();
						rPath = rNumberList.next();
					}
				}
			}
		}
	}

	static class NumberList {

		List<Path> paths;
		int lineNumber = -1;

		public NumberList(List<Path> files) {
			this.paths = files;
		}

		Path next() {
			if (lineNumber < paths.size() - 1) {
				return paths.get(++lineNumber);
			}
			return null;
		}
	}

	private static List<Path> getRelativePathOfChilds(Path rootPath) {
		List<Path> tree = new LinkedList<>();

		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(rootPath.toFile());
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			}
			tree.add(rootPath.relativize(f.toPath()));
		}

		return tree;
	}
}
