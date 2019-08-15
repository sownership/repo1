package practice.svn;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SvnServerForBigFile {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		startServer();
		Thread.currentThread().join();

//		Path svnFilePath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1\\1_a.txt");
//		Path inputFilePath = Paths.get(".\\resource\\practice\\svn\\client\\a.txt");
//
//		Files.readAllLines(commit(svnFilePath, inputFilePath)).forEach((l) -> {
//			System.out.println(l);
//		});
	}

	private static void startServer() throws IOException {
		try (ServerSocket ss = new ServerSocket(10000)) {
			Socket s = ss.accept();
			Thread t = new Thread(() -> {
				try (BufferedInputStream bis = new BufferedInputStream(s.getInputStream())) {
					ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);
					byte[] b = new byte[1024 * 8];
					int len;
					while ((len = bis.read(b)) != -1) {
						bb.put(b, 0, len);
					}

					bb.flip();
					short fileNameLen = bb.getShort();
					byte[] fileNameBa = new byte[fileNameLen];
					bb.get(fileNameBa);
					AtomicReference<String> fileNameRef = new AtomicReference<>();
					fileNameRef.set(new String(fileNameBa));
					long fileLen = bb.getLong();

					Path svnDirPath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1");
					Path tempPath = svnDirPath.resolve(fileNameRef.get() + "_temp");

					byte[] fileBa = new byte[bb.limit() - bb.position()];
					bb.get(fileBa);
					Files.write(tempPath, fileBa, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

					Optional<Path> recent = Files.list(svnDirPath)
							.filter(f -> f.getFileName().toString().matches("\\d+_" + fileNameRef.get()))
							.sorted((p1, p2) -> new Integer(p2.getFileName().toString().split("_")[0])
									.compareTo(new Integer(p1.getFileName().toString().split("_")[0])))
							.findFirst();

					Path tobePath = commit(recent.get(), tempPath);

					Files.readAllLines(tobePath).stream().forEach(System.out::println);

					Files.delete(tempPath);

				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			t.setDaemon(true);
			t.start();
		}
	}

	public static class AsisReader implements Closeable {

		private BufferedReader reader;
		private int lineNum = -1;
		private String lineVal;

		public AsisReader(Reader in) {
			reader = new BufferedReader(in);
		}

		public int next() throws IOException {
			lineVal = reader.readLine();
			return ++lineNum;
		}

		@Override
		public void close() throws IOException {
			reader.close();
		}
	}

	public static class InputFile implements Closeable {

		private RandomAccessFile raf;
		private String lineVal;

		public InputFile(File file) throws FileNotFoundException {
			raf = new RandomAccessFile(file, "r");
		}

		public String next() throws IOException {
			lineVal = raf.readLine();
			return lineVal;
		}

		public int offsetOfSame(String finding) throws IOException {
			long mark = raf.getFilePointer();
			try {
				String line = lineVal;
				int offset = 0;
				do {
					if (finding.equals(line)) {
						return offset;
					}
					offset++;
				} while ((line = raf.readLine()) != null);
			} finally {
				raf.seek(mark);
			}
			return -1;
		}

		public int offsetOfModify(String finding) throws IOException {
			long mark = raf.getFilePointer();
			try {
				String line = lineVal;
				int offset = 0;
				do {
					if (isModified(finding, line)) {
						return offset;
					}
					offset++;
				} while ((line = raf.readLine()) != null);
			} finally {
				raf.seek(mark);
			}
			return -1;
		}

		private boolean isModified(String finding, String line) {
			int idxL = 0;
			int idxR = 0;
			int sameCnt = 0;
			char l;
			while (idxL < finding.length() && idxR < line.length()) {
				l = finding.charAt(idxL++);
				int temp = line.indexOf(l, idxR);
				if (temp >= 0) {
					sameCnt++;
					idxR = temp + 1;
				}
			}
			BigDecimal bd1 = new BigDecimal(finding.length() + line.length());
			BigDecimal bd2 = bd1.divide(new BigDecimal(2));
			BigDecimal bd3 = new BigDecimal(sameCnt).divide(bd2, 2, RoundingMode.HALF_UP);
			return bd3.doubleValue() >= 0.92D;
		}

		public String getLine() {
			return lineVal;
		}

		@Override
		public void close() throws IOException {
			raf.close();
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
				InputFile input = new InputFile(inputFilePath.toFile());
				TobeWriter tobe = new TobeWriter(new FileWriter(tobePath.toFile()))) {

			int asisLineNumber = asis.next();
			input.next();

			while (true) {
				if (asis.lineVal == null) {
					if (input.getLine() == null) {
						break;
					} else {
						tobe.onAdd(asisLineNumber, input.getLine());
						input.next();
					}
				} else {
					if (input.getLine() == null) {
						tobe.onDelete(asisLineNumber);
						asisLineNumber = asis.next();
					} else {
						int offsetOfSame = input.offsetOfSame(asis.lineVal);
						if (offsetOfSame >= 0) {
							for (int i = 0; i < offsetOfSame; i++) {
								tobe.onAdd(asisLineNumber, input.getLine());
								input.next();
							}
							tobe.onSame(asisLineNumber);
							asisLineNumber = asis.next();
							input.next();
						} else {
							int offsetOfModify = input.offsetOfModify(asis.lineVal);
							if (offsetOfModify >= 0) {
								for (int i = 0; i < offsetOfModify; i++) {
									tobe.onAdd(asisLineNumber, input.getLine());
									input.next();
								}
								tobe.onModify(asisLineNumber, input.getLine());
								asisLineNumber = asis.next();
								input.next();
							} else {
								tobe.onDelete(asisLineNumber);
								asisLineNumber = asis.next();
							}
						}
					}
				}
			}
			tobe.save(asisLineNumber);
		}
		return tobePath;
	}
}
