package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientMain {

	public static void main(String[] args) {
		
	}
	
	public EchoClient(String host, int port, final String message, final AtomicInteger messageWritten,
			final AtomicInteger messageRead) throws IOException {
		// create a socket channel
		AsynchronousSocketChannel sockChannel = AsynchronousSocketChannel.open();

		// try to connect to the server side
		sockChannel.connect(new InetSocketAddress(host, port), sockChannel,
				new CompletionHandler<Void, AsynchronousSocketChannel>() {
					@Override
					public void completed(Void result, AsynchronousSocketChannel channel) {
						// start to read message
						startRead(channel, messageRead);

						// write an message to server side
						startWrite(channel, message, messageWritten);
					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel channel) {
						System.out.println("fail to connect to server");
					}

				});
	}
}
