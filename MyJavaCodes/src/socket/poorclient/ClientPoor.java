package socket.poorclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientPoor {

	public static void main(String[] args) {
		ClientPoor instance = new ClientPoor();
		instance.startClient();
	}

	private void startClient() {
		try (Socket s = new Socket("127.0.0.1", 10000);
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {

			// message 전송 2회 반복
			for (int i = 0; i < 2; i++) {
				ByteBuffer bOut = getSendMessage();
				bOut.flip();
				int idx = 1;
				//천천히 보낸다
				//패킷수를 2배수씩 늘리면서(1,2,4,8...)
				while (bOut.hasRemaining()) {
					int len = Math.min(bOut.remaining(), idx);
					// System.out.println(len);
					idx *= 2;
					byte[] b = new byte[len];
					bOut.get(b, 0, b.length);
					bos.write(b);
					bos.flush();
					sleep(100);
				}
			}

			ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);
			byte[] bIn = new byte[1024 * 8];
			int len;
			while ((len = bis.read(bIn)) != -1) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sleep(int d) {
		try {
			Thread.sleep(d);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * garbage(5) + header(20) + body(1024*100) 생성
	 * 
	 * @return
	 */
	private ByteBuffer getSendMessage() {
		int sizeOfGarbage = 5;
		int sizeOfHeader = 20;
		int sizeOfBody = 1024 * 100;
		ByteBuffer bb = ByteBuffer.allocate(sizeOfGarbage + sizeOfHeader + sizeOfBody);
		// garbage
		bb.put(new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 });
		// start
		bb.put((byte) 0x0a);
		// size of body
		bb.putInt(sizeOfBody);
		// param
		bb.put(new byte[13]);
		// check of header
		bb.put((byte) 0x10);
		// check of body
		bb.put((byte) 0x11);
		// body
		for (int i = 1; i <= sizeOfBody; i++) {
			bb.put((byte) (10 % i));
		}

		return bb;
	}

	private byte[] getIntBigEndian(int n) {
		byte[] intBigEndian = new byte[] { (byte) (n >>> 24), (byte) (n >>> 16), (byte) (n >>> 8), (byte) n };
		return intBigEndian;
	}
}
