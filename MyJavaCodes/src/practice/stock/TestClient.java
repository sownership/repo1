package practice.stock;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		//49152
		try(Socket s = new Socket("127.0.0.1", 49152);
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {
			
			ByteBuffer bb = ByteBuffer.allocate(1024*8);
			byte[] content = "ping".getBytes();
			bb.put((byte) 0xff);
			bb.put((byte) 0x01);
			bb.putInt(content.length);
			bb.put(content);
			bb.flip();
			byte[] sendBuffer = new byte[bb.remaining()];
			bb.get(sendBuffer);
			
			byte[] recvBuffer = new byte[1024*8];
			
			while(true) {
				bos.write(sendBuffer, 0, 6+content.length);
				bos.flush();
				
				int len = bis.read(recvBuffer);
				System.out.println(Arrays.copyOfRange(recvBuffer, 6, len-2));
				
				Thread.sleep(1000);
			}
		}
	}
}
