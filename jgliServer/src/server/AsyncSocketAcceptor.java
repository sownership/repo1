package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Map;

import server.client.AsyncSocketClient;
import server.client.ClientManager;
import server.decoder.IDecoder;

public class AsyncSocketAcceptor extends AbsIOAccepter {

	private AsynchronousServerSocketChannel serverChannel;
	private CompletionHandler<Integer, Map<String, Object>> readCallbak;

	public AsyncSocketAcceptor(IDecoder decoder) {
		super(decoder);
	}

	@Override
	public void start() {
		try {
			serverChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4999);
			serverChannel.bind(hostAddress);
			while (true) {

				serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

					@Override
					public void completed(AsynchronousSocketChannel clientChannel, Object attachment) {
						if (serverChannel.isOpen()) {
							serverChannel.accept(null, this);
						}
						ClientManager.add(new AsyncSocketClient(clientChannel));
						if ((clientChannel != null) && (clientChannel.isOpen())) {
							ByteBuffer buffer = ByteBuffer.allocate(32);
							Map<String, Object> readInfo = new HashMap<>();
							readInfo.put("action", "read");
							readInfo.put("buffer", buffer);
							clientChannel.read(buffer, readInfo, readCallbak);
						}
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						// process error
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
