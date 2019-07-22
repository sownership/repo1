package socket.dirndepthsend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientForNDepthDir {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try (Socket s = new Socket("127.0.0.1", 5555);
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
				DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()))) {

			File dir = new File("SRCTOP");
			send(dos, dir);

			dos.writeUTF("END");
			dos.flush();

			while (!"bye-bye".equals(dis.readUTF()))
				;

			System.out.println("bye-bye");
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
					transferFile(dos, f);
				}
			}
		}
	}

	private static void transferDir(DataOutputStream dos, File file) throws IOException {

		// directory
		dos.writeUTF("DIRECTORY");

		// path
		dos.writeUTF(file.getParent() == null ? "" : file.getParent());

		// name
		dos.writeUTF(file.getName());
	}

	private static void transferFile(DataOutputStream dos, File file) throws IOException {

		// file
		dos.writeUTF("FILE");

		// path
		dos.writeUTF(file.getParent() == null ? "" : file.getParent());

		// name
		dos.writeUTF(file.getName());

		// size
		dos.writeLong(file.length());

		// data
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] buf = new byte[1024 * 8];
			int len;
			while ((len = bis.read(buf)) != -1) {
				dos.write(buf, 0, len);
			}
		}
	}
}
