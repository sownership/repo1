package practice.stock.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import practice.stock.biz.AbstractBiz;
import practice.stock.codec.Decoder;
import practice.stock.feature.BizFromClientFactory;
import practice.stock.msg.AbstractMsg;
import practice.stock.msg.fromclient.req.ReqMsgFromClient;
import practice.stock.msg.fromclient.res.ResMsgFromClient;

public class Client {

	private Socket socket;
	private ByteBuffer recvBuffer = ByteBuffer.allocate(1024 * 1024);

	public Client(Socket socket) {
		this.socket = socket;
	}

	public synchronized boolean putRecvBuffer(byte[] b, int len) {
		recvBuffer.put(b, 0, len);
		return true;
	}

	public ByteBuffer getRecvBuffer() {
		return this.recvBuffer;
	}

	public void start() {
		try (BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
			byte[] b = new byte[1024 * 8];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				if (!putRecvBuffer(b, len))
					continue;
				AbstractMsg msg = null;
				while ((msg = Decoder.decode(this)) != null) {
					AbstractBiz biz = null;
					if (msg instanceof ResMsgFromClient) {
						biz = BizFromClientFactory.get(msg);
					} else if (msg instanceof ReqMsgFromClient) {
						biz = BizFromClientFactory.get(msg);
					}
					if (biz != null)
						biz.run();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
