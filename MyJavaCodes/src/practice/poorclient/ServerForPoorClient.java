package practice.poorclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

public class ServerForPoorClient {

	public static void main(String[] args) throws IOException {

		ServerForPoorClient instance = new ServerForPoorClient();
		instance.startServer();
	}

	private void startServer() throws IOException {
		try (ServerSocket ss = new ServerSocket(10000)) {
			while (true) {
				Socket cs = ss.accept();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						communicate(cs);
					}
				});
				t.setDaemon(true);
				t.start();
			}
		}
	}

	private void communicate(Socket cs) {
		try (BufferedInputStream bis = new BufferedInputStream(cs.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(cs.getOutputStream())) {
			ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);
			byte[] b = new byte[1024 * 8];
			int len;
			while ((len = bis.read(b)) != -1) {
				//System.out.println(bb + " " + len);
				bb.put(b, 0, len);
				byte[] result = biz(bb);
				if (result != null) {
					bos.write(result);
					bos.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * start: 1<br>
	 * size of body: 4<br>
	 * param: 13<br>
	 * check of header: 1<br>
	 * check of body: 1<br>
	 * body: ?<br>
	 * 
	 * @author 서효진
	 *
	 */
	private static class Protocol {
		private static final int START = 0x0a;
	}

	/**
	 * ByteBufferTest class 참조
	 * 
	 * @param bb
	 * @return
	 */
	private byte[] biz(ByteBuffer bb) {
		bb.flip();
		DatatypeConverter.printHexBinary(bb.array());
		// find start
		boolean startDetected = false;
		while (bb.hasRemaining()) {
			if (bb.get() == Protocol.START) {
				startDetected = true;
				bb.position(bb.position() - 1);
				bb.mark();
				break;
			}
		}
		if (!startDetected) {
			bb.compact();
			return null;
		}
		// header size 만큼 안되면 return
		if (bb.remaining() < 20) {
			bb.reset();
			bb.compact();
			return null;
		}
		// start
		bb.get();
		// size of body
		int sizeOfBody = bb.getInt();
		if (bb.remaining() < 15 + sizeOfBody) {
			bb.reset();
			bb.compact();
			return null;
		}
		// param
		bb.position(bb.position() + 13);
		// check of header
		bb.get();
		// check of body
		bb.get();
		// body
		byte[] body = new byte[sizeOfBody];
		bb.get(body);
		System.out.println(DatatypeConverter.printHexBinary(body));

		bb.compact();
		return new byte[] { 0x00 };
	}
}
