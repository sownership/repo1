package socket.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		try (ServerSocket ss = new ServerSocket(10000);) {

			while (true) {
				Socket s = ss.accept();
				start(s);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void start(Socket clientSocket) {

		try (Socket s = clientSocket;
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				DataInputStream dis = new DataInputStream(bis)) {

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
				try (FileOutputStream fos = new FileOutputStream(fileName);
						BufferedOutputStream bos = new BufferedOutputStream(fos)) {

					byte[] fileBuf = new byte[1024 * 8];
					int remain = versionInfoFileLen;
					while (true) {
						dis.readFully(fileBuf, 0, Math.min(fileBuf.length, remain));
						bos.write(fileBuf);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
