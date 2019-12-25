package practice.stock.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import practice.stock.biz.AbstractBiz;
import practice.stock.codec.Decoder;
import practice.stock.feature.BizFromClientFactory;
import practice.stock.feature.pingpong.PingResMsgToClient;
import practice.stock.msg.AbstractMsg;

public class Client {

	private Socket socket;
	private ByteBuffer recvBuffer = ByteBuffer.allocate(1024 * 1024);

	public Client(Socket socket) {
		this.socket = socket;
	}

	private synchronized boolean putRecvBuffer(byte[] b, int len) {
		recvBuffer.put(b, 0, len);
		return true;
	}

	public ByteBuffer getRecvBuffer() {
		return this.recvBuffer;
	}

	public void start() {
		try (BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
			System.out.println("client started:"+socket);
			byte[] b = new byte[1024 * 8];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				if (!putRecvBuffer(b, len))
					continue;
				AbstractMsg msg = null;
				while ((msg = Decoder.decode(this)) != null) {
					AbstractBiz biz = BizFromClientFactory.get(msg);
					if (biz != null)
						biz.run(msg);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(PingResMsgToClient pingResMsgToClient) {
		
	}
}
