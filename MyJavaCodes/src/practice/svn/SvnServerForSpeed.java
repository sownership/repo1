package practice.svn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class SvnServerForSpeed {
	public static void main(String[] args) throws IOException {
		startServer();

//		startConsole();
	}

	private static void startConsole() throws IOException {
		Path asisPath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1\\1_a.txt");
		Path inputPath = Paths.get(".\\resource\\practice\\svn\\client\\a.txt");
		String[] asisNameEles = asisPath.toFile().getName().split("_");
		Path tobePath = asisPath.resolveSibling((Integer.parseInt(asisNameEles[0]) + 1) + "_" + asisNameEles[1]);

		LineNumberList asis = new LineNumberList(Files.readAllLines(asisPath));
		LineNumberList input = new LineNumberList(Files.readAllLines(inputPath));

		List<String> tobe = commit(asis, input);

		Files.write(tobePath, tobe, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		tobe.forEach(System.out::println);
	}

	private static void startServer() throws IOException {
		try (ServerSocket ss = new ServerSocket(10000)) {
			while (true) {
				Socket s = ss.accept();
				Thread t = new Thread(() -> {

					try (Socket cs = s; DataInputStream dis = new DataInputStream(cs.getInputStream())) {

						String fileName = dis.readUTF();
						long fileLen = dis.readLong();

						Path repoPath = Paths.get(".\\resource\\practice\\svn\\server\\root\\d1");
						Optional<Path> revision = Files.list(repoPath).filter((p) -> {
							return p.getFileName().toString().matches("\\d_" + fileName);
						}).sorted((a, b) -> {
							return new Integer(a.getFileName().toString().split("_")[0])
									.compareTo(Integer.parseInt(b.getFileName().toString().split("_")[0]));
						}).findFirst();
						Path asisPath = revision.isPresent() ? revision.get() : null;

						Path tempPath = repoPath.resolveSibling(asisPath.getFileName().toString() + "_temp");
						try (BufferedOutputStream bos = new BufferedOutputStream(
								new FileOutputStream(tempPath.toFile()))) {
							byte[] b = new byte[1024 * 8];
							int len;
							while ((len = dis.read(b)) != -1) {
								bos.write(b, 0, len);
							}
						}
						LineNumberList input = new LineNumberList(Files.readAllLines(tempPath));
						Files.delete(tempPath);

						String[] asisNameEles = asisPath.toFile().getName().split("_");
						Path tobePath = asisPath
								.resolveSibling((Integer.parseInt(asisNameEles[0]) + 1) + "_" + asisNameEles[1]);
						LineNumberList asis = new LineNumberList(Files.readAllLines(asisPath));
						List<String> tobe = commit(asis, input);

						Files.write(tobePath, tobe, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

						tobe.forEach(System.out::println);

					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				t.setDaemon(true);
				t.start();
			}
		}
	}

	static class LineNumberList {
		private List<String> lines;

		LineNumberList(List<String> lines) {
			this.lines = lines;
		}

		int lineNum = -1;

		String get() {
			return lineNum >= lines.size() ? null : lines.get(lineNum);
		}

		String next() {
			if (lineNum < lines.size()) {
				lineNum++;
			}
			return get();
		}

		private int offsetOfModify(String asisLine) {
			for (int i = lineNum; i < lines.size(); i++) {
				if (isModified(asisLine, lines.get(i))) {
					return i - lineNum;
				}
			}
			return -1;
		}

		private boolean isModified(String asisLine, String inputLine) {

			AtomicInteger sameCnt = new AtomicInteger(0);
			AtomicInteger inputIndex = new AtomicInteger(0);
			asisLine.chars().forEach((c) -> {
				int tmp = inputLine.indexOf(c, inputIndex.get());
				if (tmp >= 0) {
					sameCnt.incrementAndGet();
					inputIndex.set(tmp + 1);
				}
			});
			BigDecimal bd1 = new BigDecimal(asisLine.length() + inputLine.length());
			BigDecimal bd2 = bd1.divide(new BigDecimal(2), 1);
			BigDecimal bd3 = new BigDecimal(sameCnt.get());
			return bd3.divide(bd2, 2, RoundingMode.HALF_UP).doubleValue() > 0.91D;
		}

		private int offsetOfSame(String asisLine) {
			for (int i = lineNum; i < lines.size(); i++) {
				if (lines.get(i).equals(asisLine)) {
					return i - lineNum;
				}
			}
			return -1;
		}
	}

	static class TobeList implements Closeable {

		private List<String> lines = new LinkedList<>();

		private String type;
		private int startOfType;
		private int endOfType;
		private List<String> typeLines = new LinkedList<>();

		private void onModify(int asisIndex, String inputLine) {
			checkAndReset("MODIFY", asisIndex);
			typeLines.add(inputLine);
		}

		private void onSame(int asisIndex) {
			checkAndReset("SAME", asisIndex);
		}

		private void onDelete(int asisIndex) {
			checkAndReset("DELETE", asisIndex);
		}

		private void onAdd(int asisIndex, String inputLine) {
			checkAndReset("ADD", asisIndex);
			typeLines.add(inputLine);
		}

		private void checkAndReset(String mode, int asisIndex) {
			if (!mode.equals(type)) {
				save();
				type = mode;
				startOfType = asisIndex;
				typeLines.clear();
			}
			endOfType = asisIndex;
		}

		private void save() {
			if ("ADD".equals(type)) {
				lines.add(startOfType + "#ADD");
				typeLines.forEach(lines::add);
			} else if ("SAME".equals(type)) {
			} else if ("DELETE".equals(type)) {
				lines.add(startOfType + "~" + endOfType + "#DELETED");
			} else if ("MODIFY".equals(type)) {
				lines.add(startOfType + "~" + endOfType + "#MODIFY");
				typeLines.forEach(lines::add);
			}
		}

		@Override
		public String toString() {
			return "--------------------------" + System.lineSeparator() + super.toString() + System.lineSeparator()
					+ typeLines + System.lineSeparator() + "--------------------------";
		}

		@Override
		public void close() throws IOException {
			save();
		}
	}

	private static List<String> commit(LineNumberList asis, LineNumberList input) throws IOException {

		try (TobeList tobe = new TobeList()) {
			String asisLine = asis.next();
			String inputLine = input.next();

			while (true) {
				if (asisLine == null) {
					if (inputLine == null) {
						break;
					} else {
						tobe.onAdd(asis.lineNum, inputLine);
						inputLine = input.next();
					}
				} else {
					if (inputLine == null) {
						tobe.onDelete(asis.lineNum);
						asisLine = asis.next();
					} else {
						int offsetOfSame = input.offsetOfSame(asisLine);
						if (offsetOfSame >= 0) {
							for (int i = 0; i < offsetOfSame; i++) {
								tobe.onAdd(asis.lineNum, inputLine);
								inputLine = input.next();
							}
							tobe.onSame(asis.lineNum);
							asisLine = asis.next();
							inputLine = input.next();
						} else {
							int offsetOfModify = input.offsetOfModify(asisLine);
							if (offsetOfModify >= 0) {
								for (int i = 0; i < offsetOfModify; i++) {
									tobe.onAdd(asis.lineNum, inputLine);
									inputLine = input.next();
								}
								tobe.onModify(asis.lineNum, inputLine);
								asisLine = asis.next();
								inputLine = input.next();
							} else {
								tobe.onDelete(asis.lineNum);
								asisLine = asis.next();
							}
						}
					}
				}
			}
			return tobe.lines;
		}
	}
}