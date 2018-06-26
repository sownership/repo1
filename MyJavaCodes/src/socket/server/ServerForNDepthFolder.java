package socket.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerForNDepthFolder {

	public static void main(String[] args) {

		try (ServerSocket ss = new ServerSocket(5555)) {

			while (true) {
				try (Socket s = ss.accept()) {
					acceptTopFolder(s);
				} catch (Exception e) {
					e.printStackTrace();
					Thread.sleep(1000);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void acceptTopFolder(Socket s) throws Exception {

		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()))) {

			String type;
			while (!"END".equals(type = dis.readUTF())) {
				if ("DIRECTORY".equals(type)) {
					acceptDirectory(dis);
				} else if ("FILE".equals(type)) {
					acceptFile(dis);
				}
			}
			dos.writeUTF("bye-bye");
			dos.flush();
		}
	}

	private static void acceptFile(DataInputStream dis) throws IOException {

		String path = dis.readUTF();

		String name = dis.readUTF();

		long size = dis.readLong();

		byte[] buf = new byte[1024 * 8];
		long remain = size;
		int len;

		try (FileOutputStream fos = new FileOutputStream("DESTOP" + File.separator + path + File.separator + name);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			while ((len = dis.read(buf, 0, (int) Math.min(remain, buf.length))) > 0) {
				// write file
				bos.write(buf, 0, len);
				remain -= len;
			}
		}
	}

	private static void acceptDirectory(DataInputStream dis) throws Exception {

		String path = dis.readUTF();

		String name = dis.readUTF();

		// make directory
		boolean isMkdirSuccess = new File("DESTOP" + File.separator + path + File.separator + name).mkdir();
		if (!isMkdirSuccess) {
			throw new Exception("DESTOP" + File.separator + path + File.separator + name + " create failed.");
		}
	}
}
