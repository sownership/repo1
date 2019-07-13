package practice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (ServerSocket ss = new ServerSocket(9002)) {
			try (Socket s = ss.accept();
					BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
					BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream(), 1024*16)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 100; i++) {
					sb.append(
							"1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
				}
				bos.write(sb.toString().getBytes());
//				bos.flush();
				Thread.sleep(1000*60);
			}
		}
	}
}
