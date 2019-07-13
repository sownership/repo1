package practice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ParkingFeeClient {

	public static void main(String[] args) throws IOException {

		try (Socket s = new Socket("127.0.0.1", 9002);
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {
			byte[] b = new byte[1024 * 1024];
			int len;
			while((len=bis.read(b))!=-1) {
				System.out.println(new String(b, 0, len).length());
			}
			
		}
	}
}
