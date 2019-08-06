package practice.svn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SvnServer {

	public static void main(String[] args) throws IOException {
		SvnServer ins = new SvnServer();
		Path asis = Paths.get(
				"C:\\ljg\\eclipse\\ws\\git\\repo1\\MyJavaCodes\\resource\\practice\\svn\\server\\root\\d1\\1_a.txt");
		Path request = Paths
				.get("C:\\ljg\\eclipse\\ws\\git\\repo1\\MyJavaCodes\\resource\\practice\\svn\\client\\a.txt");
		Files.readAllLines(ins.commit(asis, request)).forEach((line) -> {
			System.out.println(line);
		});
	}

	public static class Position {
		String line;
		int lineNumber = -1;

		public void next(String line) {
			this.line = line;
			lineNumber++;
		}

		@Override
		public String toString() {
			return lineNumber + ":" + line;
		}
	}

	public static class FindLineRaf extends RandomAccessFile {

		Position pos = new Position();

		@Override
		public String toString() {
			return pos.toString();
		}

		public FindLineRaf(File file, String mode) throws FileNotFoundException {
			super(file, mode);
		}

		public int offsetOfSame(String line) throws IOException {
			long mark = getFilePointer();
			int offset = 0;
			String tmpLine = pos.line;
			try {
				while (tmpLine != null) {
					if (line.equals(tmpLine)) {
						return offset;
					}
					offset++;
					tmpLine = readLine();
				}
			} finally {
				seek(mark);
			}
			return -1;
		}

		public int offsetOfModify(String line) throws IOException {
			long mark = getFilePointer();
			int offset = 0;
			String tmpLine = pos.line;
			try {
				while (tmpLine != null) {
					if (isModified(line, tmpLine)) {
						return offset;
					}
					offset++;
					tmpLine = readLine();
				}
			} finally {
				seek(mark);
			}
			return -1;
		}

		public boolean isModified(String line1, String line2) {
			return false;
		}

		public void moveNextLine() throws IOException {
			pos.next(readLine());
		}
	}

	public static class TobeBufferedWriter extends BufferedWriter {

		public TobeBufferedWriter(Writer out) {
			super(out);
		}

		public void writeAdd(int asisLineNumber, String addedLine) throws IOException {
			write(asisLineNumber + "#ADDED");
			newLine();
			write(addedLine);
			newLine();
		}

		public void writeDelete(int asisLineNumber) throws IOException {
			write(asisLineNumber + "#DELETED");
			newLine();
		}

		public void writeModify(int asisLineNumber, String modifiedLine) throws IOException {
			write(asisLineNumber + "#MODIFIED");
			newLine();
			write(modifiedLine);
			newLine();
		}
	}

	public static class AsisReader extends BufferedReader {

		Position pos = new Position();

		@Override
		public String toString() {
			return pos.toString();
		}

		public AsisReader(Reader in) {
			super(in);
		}

		public void moveNextLine() throws IOException {
			pos.next(super.readLine());
		}
	}

	/**
	 * @param asis    filename format: revision + "_" + name
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Path commit(Path asis, Path request) throws IOException {

		String[] fileNameElements = asis.getFileName().toString().split("_");
		Path tobe = asis.resolveSibling((Integer.parseInt(fileNameElements[0]) + 1) + "_" + fileNameElements[1]);

		try (AsisReader asisReader = new AsisReader(new FileReader(asis.toFile()));
				FindLineRaf inputRaf = new FindLineRaf(request.toFile(), "r");
				TobeBufferedWriter tobeWriter = new TobeBufferedWriter(new FileWriter(tobe.toFile()))) {

			asisReader.moveNextLine();
			inputRaf.moveNextLine();

			while (true) {
				if (asisReader.pos.line == null) {
					if (inputRaf.pos.line == null) {
						break;
					} else {
						tobeWriter.writeAdd(asisReader.pos.lineNumber, inputRaf.pos.line);
						inputRaf.moveNextLine();
					}
				} else {
					if (inputRaf.pos.line == null) {
						tobeWriter.writeDelete(asisReader.pos.lineNumber);
						asisReader.moveNextLine();
					} else {
						int offsetOfSame = inputRaf.offsetOfSame(asisReader.pos.line);
						if (offsetOfSame >= 0) {
							for (int i = 0; i < offsetOfSame; i++) {
								tobeWriter.writeAdd(asisReader.pos.lineNumber, inputRaf.pos.line);
								inputRaf.moveNextLine();
							}
							asisReader.moveNextLine();
							inputRaf.moveNextLine();
						} else {
							int offsetOfModify = inputRaf.offsetOfModify(asisReader.pos.line);
							if (offsetOfModify >= 0) {
								for (int i = 0; i < offsetOfSame; i++) {
									tobeWriter.writeAdd(asisReader.pos.lineNumber, inputRaf.pos.line);
									inputRaf.moveNextLine();
								}
								tobeWriter.writeModify(asisReader.pos.lineNumber, inputRaf.pos.line);
								asisReader.moveNextLine();
								inputRaf.moveNextLine();
							} else {
								tobeWriter.writeDelete(asisReader.pos.lineNumber);
								asisReader.moveNextLine();
							}
						}
					}
				}
			}
		}

		return tobe;
	}
}
