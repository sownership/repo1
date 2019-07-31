package practice.dir1depthsend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFor1DeptDir {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try (Socket s = new Socket("127.0.0.1", 10000);
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
				DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()))) {

			File dir = new File("resource\\practice\\dir1depthsend\\SOURCEDIR");
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
				try (BufferedInputStream fbis = new BufferedInputStream(new FileInputStream(files[i]))) {
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
		}
	}
}
