package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientMain {

	private static AtomicInteger messageRead = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {

		ClientMain ins = new ClientMain();
		int clientCnt = 1000;
		for (int i = 0; i < clientCnt; i++) {
			ins.connect("localhost", 4999);
		}
		while (true) {
			System.out.println(notFirstWrite.size() + ", " + messageRead.get());
			Thread.sleep(1000 * 5);
		}
	}

	public void connect(String host, int port) throws IOException {

		AsynchronousSocketChannel sockChannel = AsynchronousSocketChannel.open();

		sockChannel.connect(new InetSocketAddress(host, port), sockChannel,
				new CompletionHandler<Void, AsynchronousSocketChannel>() {
					@Override
					public void completed(Void result, AsynchronousSocketChannel channel) {
						startWrite(channel);
					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel channel) {
						System.out.println("fail to connect to server");
					}

				});
	}

	private void startRead(final AsynchronousSocketChannel sockChannel) {
		final ByteBuffer buf = ByteBuffer.allocate(1024 * 8);

		sockChannel.read(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {

			@Override
			public void completed(Integer result, AsynchronousSocketChannel channel) {
				messageRead.getAndIncrement();
				buf.flip();
//				System.out.println(new String(buf.array(), 0, buf.remaining()));
				buf.clear();
				startWrite(channel);
			}

			@Override
			public void failed(Throwable exc, AsynchronousSocketChannel channel) {
				System.out.println("fail to read message from server");
			}
		});

	}

	private static Set<AsynchronousSocketChannel> notFirstWrite = Collections.synchronizedSet(new HashSet<>());

	private void startWrite(final AsynchronousSocketChannel sockChannel) {
		ByteBuffer buf = ByteBuffer.allocate(2048);
		if (notFirstWrite.contains(sockChannel)) {
			buf.put("ACK".getBytes());
		} else {
			buf.put("C:\\ljg\\temp\\test1.txt".getBytes());
			notFirstWrite.add(sockChannel);
		}
		buf.flip();
		sockChannel.write(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
			@Override
			public void completed(Integer result, AsynchronousSocketChannel channel) {
				startRead(channel);
			}

			@Override
			public void failed(Throwable exc, AsynchronousSocketChannel channel) {
				System.out.println("Fail to write the message to server");
			}
		});
	}
}
