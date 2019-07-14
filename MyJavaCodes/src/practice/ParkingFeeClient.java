package practice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ParkingFeeClient {

	public static void main(String[] args) throws IOException {

		try (Socket s = new Socket("127.0.0.1", 9000);
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {
			bos.write("CARNO=16Çã4438".getBytes());
			bos.flush();

			byte[] b = new byte[1024 * 1024];
			int len = bis.read(b);
			System.out.println(new String(b, 0, len));

			bos.write("DATE=20190714".getBytes());
			bos.flush();

			len = bis.read(b);
			System.out.println(new String(b, 0, len));

			bos.write("quit".getBytes());
			bos.flush();
		}
	}
}
