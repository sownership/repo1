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
		//비동기 처리후 run callback 을 실행해 준다
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
