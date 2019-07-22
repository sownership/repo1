package socket.dir1depthsend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFor1DepthDir {

	public static void main(String[] args) throws IOException {

		try (ServerSocket ss = new ServerSocket(10000);) {

			while (true) {
				Socket s = ss.accept();
				receiveFiles(s);
			}
		}
	}

	private static void receiveFiles(Socket clientSocket) throws IOException {

		try (Socket s = clientSocket;
				DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()))) {

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
				try (BufferedOutputStream fbos = new BufferedOutputStream(
						new FileOutputStream("resource\\DESTDIR\\" + fileName))) {

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
		}
	}
}
