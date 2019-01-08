package server.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsyncSocketClient extends AbsClient {

	private AsynchronousSocketChannel socketChannel;

	public AsyncSocketClient(AsynchronousSocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	@Override
	public void send(ByteBuffer message, Runnable run) {
		// 비동기 처리후 run callback 을 실행해 준다
		socketChannel.write(message, run, new CompletionHandler<Integer, Runnable>() {

			@Override
			public void completed(Integer result, Runnable attachment) {
				if(attachment!=null) {
					attachment.run();					
				}
			}

			@Override
			public void failed(Throwable exc, Runnable attachment) {
				exc.printStackTrace();
			}
		});
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		return socketChannel.toString();
	}
}
