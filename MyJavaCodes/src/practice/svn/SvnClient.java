package practice.svn;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SvnClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

		try (Socket s = new Socket("127.0.0.1", 10000);
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()))) {
			String fileName = "a.txt";
			dos.writeUTF(fileName);

			String pathString = "C:\\ljg\\eclipse\\ws\\git\\repo1\\MyJavaCodes\\resource\\practice\\svn\\client\\a.txt";
			dos.writeLong(new File(pathString).length());

			Files.readAllLines(Paths.get(".\\resource\\practice\\svn\\client\\a.txt")).forEach((line) -> {
				try {
					dos.write((line + System.lineSeparator()).getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
//			Thread.sleep(1000*5);
		}
	}
}
