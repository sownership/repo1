package socket.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFor1Folder {

	public static void main(String[] args) {

		try (ServerSocket ss = new ServerSocket(10000);) {

			while (true) {
				Socket s = ss.accept();
				receiveFiles(s);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void receiveFiles(Socket clientSocket) {

		try (Socket s = clientSocket;
				InputStream bis = new BufferedInputStream(s.getInputStream());
				DataInputStream dis = new DataInputStream(bis);
				OutputStream bos = new BufferedOutputStream(s.getOutputStream());
				DataOutputStream dos = new DataOutputStream(bos)) {

			// start character
			while (dis.readByte() != 0x0A)
				;

			// total file count
			int totalFileCnt = dis.readInt();

			for (int i = 0; i < totalFileCnt; i++) {
				// file length
				int versionInfoFileLen = dis.readInt();

				// file name
				String fileName = dis.readUTF();

				// file
				try (OutputStream fos = new FileOutputStream("DESTDIR\\" + fileName);
						BufferedOutputStream fbos = new BufferedOutputStream(fos)) {

					byte[] fileBuf = new byte[1024 * 8];
					int remain = versionInfoFileLen;
					while (remain > 0) {
						int len = Math.min(fileBuf.length, remain);
						dis.readFully(fileBuf, 0, len);
						fbos.write(fileBuf, 0, len);
						remain -= len;
					}
				}
			}

			// bye-bye
			dos.writeUTF("bye-bye");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
