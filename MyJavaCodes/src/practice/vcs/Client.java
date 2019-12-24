package practice.vcs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try (Socket s = new Socket("127.0.0.1", 10000);
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {

			merge(bis, bos);
		}
	}

	private static void merge(BufferedInputStream bis, BufferedOutputStream bos) throws IOException {
		bos.write("MERGE\n".getBytes());
		bos.flush();
		byte[] b = new byte[1024 * 8];
		int len = bis.read(b);
		if (new String(b, 0, len).equals("ACK\n")) {
			bos.write("src1.txt\n".getBytes());
			bos.flush();
			len = bis.read(b);
			if (new String(b, 0, len).equals("ACK\n")) {
				bos.write("01\n".getBytes());
				bos.flush();
				len = bis.read(b);
				if (new String(b, 0, len).equals("ACK\n")) {
					bos.write("9\n".getBytes());
					bos.flush();
					len = bis.read(b);
					if (new String(b, 0, len).equals("ACK\n")) {
						bos.write("p\n".getBytes());
						bos.write("q\n".getBytes());
						bos.write("a\n".getBytes());
						bos.write("b\n".getBytes());
						bos.write("c\n".getBytes());
						bos.write("d\n".getBytes());
						bos.write("f\n".getBytes());
						bos.write("e21\n".getBytes());
						bos.write("e22\n".getBytes());
						bos.flush();
						ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);
						int mergedLineCnt = -1;
						while ((len = bis.read(b)) != -1) {
							bb.put(b, 0, len);

							while (true) {
								String nextLine = getNextLine(bb);
								if (nextLine == null) {
									break;
								} else {
									if (mergedLineCnt == -1) {
										mergedLineCnt = Integer.parseInt(nextLine);
										bos.write("ACK\n".getBytes());
										bos.flush();
									} else {
										System.out.println(nextLine);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static String getNextLine(ByteBuffer bb) {
		bb.flip();
		bb.mark();
		String nextLine = null;
		int start = bb.position();
		while (bb.hasRemaining()) {
			if (bb.get() == '\n') {
				nextLine = new String(Arrays.copyOfRange(bb.array(), start, bb.position() - 1));
//				System.out.println("next(" + nextLine + ")");
				break;
			}
		}
		if (nextLine == null) {
			bb.reset();
		}
		bb.compact();
		return nextLine;
	}
}
