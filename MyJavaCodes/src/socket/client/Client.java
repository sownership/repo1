package socket.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {

		try (Socket s = new Socket("127.0.0.1", 10000);
				OutputStream bos = new BufferedOutputStream(s.getOutputStream());
				DataOutputStream dos = new DataOutputStream(bos);
				InputStream bis = new BufferedInputStream(s.getInputStream());
				DataInputStream dis = new DataInputStream(bis)) {

			File dir = new File("SOURCEDIR");
			File[] files = dir.listFiles();

			// start character
			dos.writeByte(0x0A);

			// total file count
			dos.writeInt(files.length);

			for (int i = 0; i < files.length; i++) {

				// file length
				dos.writeInt((int) files[i].length());

				// file name
				dos.writeUTF(files[i].getName());

				// file
				try (InputStream is = new FileInputStream(files[i]);
						BufferedInputStream fbis = new BufferedInputStream(is)) {
					byte[] buf = new byte[1024 * 8];
					int len;
					while ((len = fbis.read(buf)) != -1) {
						dos.write(buf, 0, len);
						dos.flush();
					}
				}
			}

			while (!"bye-bye".equals(dis.readUTF()))
				;

			System.out.println("bye-bye");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
