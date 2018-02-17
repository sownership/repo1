package socket.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientForNFolders {

	public static void main(String[] args) {

		try (Socket s = new Socket("127.0.0.1", 5555);
				OutputStream bis = new BufferedOutputStream(s.getOutputStream());
				DataOutputStream dos = new DataOutputStream(bis)) {

			File dir = new File("SRCTOP");
			send(dos, dir);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void send(DataOutputStream dos, File file) throws IOException {

		if (file.isDirectory()) {
			transferDir(dos, file);

			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					send(dos, f);
				} else {
					transferFile(dos, file);
				}
			}
		}
	}

	private static void transferDir(DataOutputStream dos, File file) throws IOException {

		// directory
		dos.writeUTF("DIRECTORY");

		// path
		dos.writeUTF(file.getParent());

		// name
		dos.writeUTF(file.getName());
	}

	private static void transferFile(DataOutputStream dos, File file) throws IOException {

		// file
		dos.writeUTF("FILE");

		// path
		dos.writeUTF(file.getParent());

		// name
		dos.writeUTF(file.getName());

		// size
		dos.writeLong(file.length());

		// data
		try (InputStream is = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(is)) {
			byte[] buf = new byte[1024 * 8];
			int len;
			while ((len = bis.read(buf)) != -1) {
				dos.write(buf, 0, len);
			}
		}
	}
}
