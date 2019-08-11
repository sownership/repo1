package practice.diff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Diff2 {

	public static void main(String[] args) throws IOException {

		Path leftRoot = Paths.get(".\\resource\\practice\\diff\\SRCTOP");
		Path rightRoot = Paths.get(".\\resource\\practice\\diff\\DESTOP\\SRCTOP");

		List<Path> leftChilds = getReverseOrderChilds(leftRoot);
		List<Path> rightChilds = getReverseOrderChilds(rightRoot);

		List<String> diffResult = diff(leftRoot, leftChilds, rightRoot, rightChilds);

		Collections.reverse(diffResult);
		diffResult.forEach(System.out::println);
	}

	private static List<String> diff(Path leftRoot, List<Path> leftChilds, Path rightRoot, List<Path> rightChilds)
			throws IOException {
		DiffResult diffResult = new DiffResult();

		LineNumberList lefts = new LineNumberList(leftChilds);
		LineNumberList rights = new LineNumberList(rightChilds);

		Set<File> sameSet = new HashSet<>();

		Path left = lefts.next();
		Path right = rights.next();

		while (true) {
			if (left == null) {
				if (right == null) {
					break;
				} else {
					diffResult.onRightOnly(rightRoot.resolve(right));
					right = rights.next();
				}
			} else {
				if (right == null) {
					diffResult.onLeftOnly(leftRoot.resolve(left));
					left = lefts.next();
				} else {
					int compare = left.compareTo(right);
					if (compare == 0) {
						boolean isSame = false;
						File leftFile = leftRoot.resolve(left).toFile();
						File rightFile = rightRoot.resolve(right).toFile();
						if (leftFile.isDirectory()) {
							if (rightFile.isDirectory()) {
								isSame = !Stream.concat(Arrays.stream(leftFile.listFiles()),
										Arrays.stream(rightFile.listFiles())).anyMatch((f) -> {
											return !sameSet.contains(f);
										});
							} else {
							}
						} else {
							if (rightFile.isDirectory()) {
							} else {
								isSame = leftFile.length() == rightFile.length() && Arrays.equals(
										Files.readAllBytes(leftFile.toPath()), Files.readAllBytes(rightFile.toPath()));
							}
						}
						if (isSame) {
							diffResult.onSame(leftRoot.resolve(left), rightRoot.resolve(right));
							sameSet.add(leftFile);
							sameSet.add(rightFile);
						} else {
							diffResult.onDifferent(leftRoot.resolve(left), rightRoot.resolve(right));
						}
						left = lefts.next();
						right = rights.next();
					} else if (compare < 0) {
						diffResult.onRightOnly(rightRoot.resolve(right));
						right = rights.next();
					} else if (compare > 0) {
						diffResult.onLeftOnly(leftRoot.resolve(left));
						left = lefts.next();
					}
				}
			}
		}
		return diffResult.list;
	}

	static class DiffResult {
		List<String> list = new LinkedList<>();

		public void onRightOnly(Path right) {
			list.add("R: " + right);
		}

		public void onSame(Path left, Path right) {
			list.add("S: " + left + ", " + right);
		}

		public void onDifferent(Path left, Path right) {
			list.add("D: " + left + ", " + right);
		}

		public void onLeftOnly(Path left) {
			list.add("L: " + left);
		}
	}

	static class LineNumberList {
		List<Path> list;

		LineNumberList(List<Path> list) {
			this.list = list;
		}

		int lineNum = -1;

		Path next() {
			if (lineNum < list.size() - 1) {
				return list.get(++lineNum);
			}
			return null;
		}
	}

	private static List<Path> getReverseOrderChilds(Path root) {
		List<Path> childs = new LinkedList<>();

		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(root.toFile());
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				Arrays.stream(f.listFiles()).forEach(q::offer);
			}
			childs.add(root.relativize(f.toPath()));
		}
		Collections.sort(childs, Collections.reverseOrder());
		return childs;
	}
}
