package practice.diff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

public class Diff {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String lRoot = ".\\resource\\practice\\diff\\SRCTOP";
		String rRoot = ".\\resource\\practice\\diff\\DESTOP\\SRCTOP";

		Map<File, Boolean> fileSameMap = new HashMap<>();

		Stack<File> lStack = new Stack<>();
		Stack<File> rStack = new Stack<>();

		lStack.push(new File(lRoot));
		rStack.push(new File(rRoot));

		File lf = next(lStack, fileSameMap);
		File rf = next(rStack, fileSameMap);

		while (lf != null || rf != null) {
			if (isLeftOnly(lf, rf, lRoot, rRoot)) {
				printLeftOnly(lf);
				fileSameMap.put(lf, false);
				lf = next(lStack, fileSameMap);

			} else if (isRightOnly(lf, rf, lRoot, rRoot)) {
				printRightOnly(rf);
				fileSameMap.put(rf, false);
				rf = next(rStack, fileSameMap);

			} else if (isSame(lf, rf, fileSameMap)) {
				printSame(lf, rf);
				fileSameMap.put(lf, true);
				fileSameMap.put(rf, true);
				lf = next(lStack, fileSameMap);
				rf = next(rStack, fileSameMap);

			} else {
				printDifferent(lf, rf);
				fileSameMap.put(lf, false);
				fileSameMap.put(rf, false);
				lf = next(lStack, fileSameMap);
				rf = next(rStack, fileSameMap);
			}
		}
	}

	private static void printSame(File lf, File rf) {
		System.out.println("S: " + lf + ",    " + rf);
	}

	private static void printDifferent(File lf, File rf) {
		System.out.println("D: " + lf + ",    " + rf);
	}

	private static void printRightOnly(File rf) {
		System.out.println("R: " + rf);
	}

	private static void printLeftOnly(File lf) {
		System.out.println("L: " + lf);
	}

	private static boolean isSame(File lf, File rf, Map<File, Boolean> fileSameMap)
			throws FileNotFoundException, IOException {
		if (lf.isDirectory()) {
			return rf.isDirectory()
					&& !Stream.concat(Arrays.stream(lf.listFiles()), Arrays.stream(rf.listFiles())).anyMatch((f) -> {
						Boolean isChildSame = fileSameMap.get(f);
						return isChildSame == null || !isChildSame;
					});
		} else {
			return !rf.isDirectory() && lf.length() == rf.length() && isSameContent(lf, rf);
		}
	}

	private static boolean isSameContent(File lf, File rf) throws FileNotFoundException, IOException {
		return Arrays.equals(Files.readAllBytes(lf.toPath()), Files.readAllBytes(rf.toPath()));
	}

	private static File next(Stack<File> stack, Map<File, Boolean> fileSameMap) {
		if (stack.isEmpty()) {
			return null;
		}
		while (true) {
			File f = stack.pop();
			if (f.isDirectory()) {
				File[] childs = f.listFiles();
				if (childs.length == 0) {
					return f;
				}
				if (fileSameMap.containsKey(childs[0])) {
					return f;
				} else {
					stack.push(f);
					Arrays.sort(childs);
					stack.addAll(Arrays.asList(childs));
				}
			} else {
				return f;
			}
		}
	}

	private static boolean isRightOnly(File lf, File rf, String lRoot, String rRoot) {
		return isLeftOnly(rf, lf, rRoot, lRoot);
	}

	private static boolean isLeftOnly(File lf, File rf, String lRoot, String rRoot) {
		if (lf == null) {
			return false;
		}
		if (rf == null) {
			return true;
		}
		int compared = lf.toPath().relativize(Paths.get(lRoot)).compareTo(rf.toPath().relativize(Paths.get(rRoot)));
		if (compared > 0) {
			return true;
		}
		return false;
	}
}
