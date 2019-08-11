package practice.svn;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class SvnServerForSpeed {

	public static void main(String[] args) throws IOException {

		// asis, tobe, input 의 경로를 구한다.
		Path asisPath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1\\1_a.txt");

		String[] asisNameElements = asisPath.getFileName().toString().split("_");
		String tobeName = (Integer.parseInt(asisNameElements[0]) + 1) + "_" + asisNameElements[1];
		Path tobePath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1", tobeName);

		Path inputPath = Paths.get(".\\resource\\practice\\svn\\client\\a.txt");

		// asis, input 을 list 로 변환한다
		NumberList asis = new NumberList();
		Files.readAllLines(asisPath).forEach((line) -> {
			asis.list.add(line);
		});

		NumberList input = new NumberList();
		Files.readAllLines(inputPath).forEach((line) -> {
			input.list.add(line);
		});

		// asis, input 으로 tobe 를 구한다
		TobeList tobe = commit(asis, input);

		// tobe 결과를 출력한다
		Files.write(tobePath, tobe.list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		tobe.list.forEach((l) -> {
			System.out.println(l);
		});
	}

	private static TobeList commit(NumberList asis, NumberList input) throws IOException {

		try (TobeList tobe = new TobeList()) {
			asis.next();
			input.next();

			while (true) {
				if (asis.get() == null) {
					if (input.get() == null) {
						break;
					} else {
						tobe.onAdd(asis.lineNum, input.get());
						input.next();
					}
				} else {
					if (input.get() == null) {
						tobe.onDelete(asis.lineNum);
						asis.next();
					} else {
						int offsetOfSame = input.offsetOfSame(asis.get());
						if (offsetOfSame >= 0) {
							for (int i = 0; i < offsetOfSame; i++) {
								tobe.onAdd(asis.lineNum, input.get());
								input.next();
							}
							tobe.onSame(asis.lineNum);
							asis.next();
							input.next();
						} else {
							int offsetOfModify = input.offsetOfModify(asis.get());
							if (offsetOfModify >= 0) {
								for (int i = 0; i < offsetOfModify; i++) {
									tobe.onAdd(asis.lineNum, input.get());
									input.next();
								}
								tobe.onModify(asis.lineNum, input.get());
								asis.next();
								input.next();
							} else {
								tobe.onDelete(asis.lineNum);
								asis.next();
							}
						}
					}
				}
			}
			return tobe;
		}
	}

	public static class TobeList implements Closeable {

		private List<String> list = new LinkedList<>();

		String type;
		List<String> inputs = new LinkedList<>();
		int startOfType;
		int currentOfType;

		public void save() {
			if ("ADD".equals(type)) {
				list.add(startOfType + "#ADD");
				list.addAll(inputs);
				inputs.clear();

			} else if ("MODIFY".equals(type)) {
				list.add(startOfType + "~" + currentOfType + "#MODIFY");
				list.addAll(inputs);
				inputs.clear();

			} else if ("SAME".equals(type)) {

			} else if ("DELETE".equals(type)) {
				list.add(startOfType + "~" + currentOfType + "#DELETE");
			}
		}

		private void setType(int lineNum, String type) {
			if (!type.equals(this.type)) {
				save();
				this.type = type;
				startOfType = lineNum;
			}
			currentOfType = lineNum;
		}

		public void onAdd(int lineNum, String input) {
			setType(lineNum, "ADD");
			inputs.add(input);
		}

		public void onModify(int lineNum, String input) {
			setType(lineNum, "MODIFY");
			inputs.add(input);
		}

		public void onSame(int lineNum) {
			setType(lineNum, "SAME");
		}

		public void onDelete(int lineNum) {
			setType(lineNum, "DELETE");
		}

		@Override
		public void close() throws IOException {
			save();
		}
	}

	public static class NumberList {

		private List<String> list = new LinkedList<>();

		@Override
		public String toString() {
			return "" + lineNum;
		}

		int lineNum = -1;

		public boolean next() {
			if (lineNum >= list.size()) {
				return false;
			}
			lineNum++;
			return true;
		}

		public int offsetOfModify(String find) {
			int offset = 0;
			int mark = lineNum;
			try {
				do {
					if (get() != null && isModified(find, get())) {
						return offset;
					}
					offset++;
				} while (next());
			} finally {
				lineNum = mark;
			}
			return -1;
		}

		private boolean isModified(String find, String line) {
			int leftIdx = 0;
			int rightIdx = 0;
			int sameCnt = 0;
			while (leftIdx < find.length() && rightIdx < line.length()) {
				char left = find.charAt(leftIdx++);
				int tmp = line.indexOf(left, rightIdx);
				if (tmp >= 0) {
					sameCnt++;
					rightIdx = tmp + 1;
				}
			}
			BigDecimal bd1 = new BigDecimal(find.length() + line.length());
			BigDecimal bd2 = bd1.divide(new BigDecimal(2));
			BigDecimal bd3 = new BigDecimal(sameCnt).divide(bd2, 2, RoundingMode.HALF_UP);
			return bd3.doubleValue() >= 0.92D;
//			return false;
		}

		public int offsetOfSame(String find) {
			int offset = 0;
			int mark = lineNum;
			try {
				do {
					if (find.equals(get())) {
						return offset;
					}
					offset++;
				} while (next());
			} finally {
				lineNum = mark;
			}
			return -1;
		}

		public String get() {
			return lineNum >= list.size() ? null : list.get(lineNum);
		}
	}
}
