package practice.svn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class SvnServerForBigFile {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Path svnFilePath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1\\1_a.txt");
		Path inputFilePath = Paths.get(".\\resource\\practice\\svn\\client\\a.txt");
		Files.readAllLines(commit(svnFilePath, inputFilePath)).forEach((l) -> {
			System.out.println(l);
		});
	}

	public static class AsisReader extends LineNumberReader {

		String lineVal;

		public AsisReader(Reader in) {
			super(in);
		}

		public String next() throws IOException {
			return lineVal = readLine();
		}

		public String getLine() {
			return lineVal;
		}
	}

	public static class InputRaf extends RandomAccessFile {

		String lineVal;
		int lineNum;

		public InputRaf(File file, String mode) throws FileNotFoundException {
			super(file, mode);
		}

		public String next() throws IOException {
			lineNum++;
			return lineVal = readLine();
		}

		public int offsetOfSame(String finding) throws IOException {
			long mark = getFilePointer();
			try {
				String line = lineVal;
				int offset = 0;
				do {
					if (finding.equals(line)) {
						return offset;
					}
					offset++;
				} while ((line = readLine()) != null);
			} finally {
				seek(mark);
			}
			return -1;
		}

		public int offsetOfModify(String finding) throws IOException {
			long mark = getFilePointer();
			try {
				String line = lineVal;
				int offset = 0;
				do {
					if (isModified(finding, line)) {
						return offset;
					}
					offset++;
				} while ((line = readLine()) != null);
			} finally {
				seek(mark);
			}
			return -1;
		}

		private boolean isModified(String finding, String line) {
			// TODO Auto-generated method stub
			return false;
		}

		public String getLine() {
			return lineVal;
		}

	}

	public static class TobeWriter extends BufferedWriter {

		public TobeWriter(Writer out) {
			super(out);
		}

		String type;
		int startLineNumber;
		List<String> lines = new LinkedList<>();

		public void save(int endLineNumber) throws IOException {
			if ("ADD".equals(type)) {
				write(startLineNumber + "#ADD");
				newLine();
				for (String l : lines) {
					write(l);
					newLine();
				}
				lines.clear();
			} else if ("DELETE".equals(type)) {
				write(startLineNumber + "~" + endLineNumber + "#DELETE");
				newLine();
			} else if ("MODIFY".equals(type)) {
				write(startLineNumber + "~" + endLineNumber + "#MODIFY");
				newLine();
				for (String l : lines) {
					write(l);
					newLine();
				}
				lines.clear();
			} else if ("SAME".equals(type)) {
			}
		}

		private void setType(String type, int lineNum) throws IOException {
			if (!type.equals(this.type)) {
				save(lineNum - 1);
				this.type = type;
				startLineNumber = lineNum;
			}
		}

		public void onAdd(int lineNum, String lineVal) throws IOException {
			setType("ADD", lineNum);
			lines.add(lineVal);
		}

		public void onDelete(int lineNum) throws IOException {
			setType("DELETE", lineNum);
		}

		public void onModify(int lineNum, String lineVal) throws IOException {
			setType("MODIFY", lineNum);
			lines.add(lineVal);
		}

		public void onSame(int lineNum) throws IOException {
			setType("SAME", lineNum);
		}
	}

	private static Path commit(Path svnFilePath, Path inputFilePath) throws FileNotFoundException, IOException {

		String[] svnFileElements = svnFilePath.getFileName().toString().split("_");
		Path tobePath = svnFilePath.getParent()
				.resolve((Integer.parseInt(svnFileElements[0]) + 1) + "_" + svnFileElements[1]);

		try (AsisReader asis = new AsisReader(new FileReader(svnFilePath.toFile()));
				InputRaf input = new InputRaf(inputFilePath.toFile(), "r");
				TobeWriter tobe = new TobeWriter(new FileWriter(tobePath.toFile()))) {

			asis.next();
			input.next();

			while (true) {
				if (asis.getLine() == null) {
					if (input.getLine() == null) {
						break;
					} else {
						tobe.onAdd(asis.getLineNumber(), input.getLine());
						input.next();
					}
				} else {
					if (input.getLine() == null) {
						tobe.onDelete(asis.getLineNumber());
						asis.next();
					} else {
						int offsetOfSame = input.offsetOfSame(asis.getLine());
						if (offsetOfSame >= 0) {
							for (int i = 0; i < offsetOfSame; i++) {
								tobe.onAdd(asis.getLineNumber(), input.getLine());
								input.next();
							}
							tobe.onSame(asis.getLineNumber());
							asis.next();
							input.next();
						} else {
							int offsetOfModify = input.offsetOfModify(asis.getLine());
							if (offsetOfModify >= 0) {
								for (int i = 0; i < offsetOfModify; i++) {
									tobe.onAdd(asis.getLineNumber(), input.getLine());
									input.next();
								}
								tobe.onModify(asis.getLineNumber(), input.getLine());
								asis.next();
								input.next();
							} else {
								tobe.onDelete(asis.getLineNumber());
								asis.next();
							}
						}
					}
				}
			}
			tobe.save(asis.getLineNumber());
		}
		return tobePath;
	}
}
