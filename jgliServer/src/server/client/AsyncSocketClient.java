package server.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AsyncSocketClient extends AbsClient {

	private AsynchronousSocketChannel socketChannel;
	
	public AsyncSocketClient(AsynchronousSocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	@Override
	public void send(ByteBuffer message, Runnable run) {
		//�񵿱� ó���� run callback �� ������ �ش�
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
