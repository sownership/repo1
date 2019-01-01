package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import server.client.AbsClient;
import server.client.AsyncSocketClient;
import server.client.ClientManager;
import server.decoder.IDecoder;

public class AsyncSocketAcceptor extends AbsIOAccepter {

	private CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> acceptHandler = new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {

		@Override
		public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
			System.out.println("fail to accept a connection");
		}

		@Override
		public void completed(AsynchronousSocketChannel sockChannel, AsynchronousServerSocketChannel serverSock) {
			AbsClient client = new AsyncSocketClient(sockChannel);
			ClientManager.add(client.getId(), client);

			serverSock.accept(serverSock, this);
			startRead(sockChannel);
		}
	};

	private static class ReadAttachMent {
		private AsynchronousSocketChannel sockChannel;
		private ByteBuffer byteBuffer;

		ReadAttachMent(AsynchronousSocketChannel sockChannel) {
			this.sockChannel = sockChannel;
			this.byteBuffer = ByteBuffer.allocate(1024 * 8);
		}
	}

	private CompletionHandler<Integer, ReadAttachMent> readHandler = new CompletionHandler<Integer, ReadAttachMent>() {

		@Override
		public void failed(Throwable exc, ReadAttachMent attachment) {
			System.out.println("fail to read message from client");
			ClientManager.remove(attachment.sockChannel.toString());
		}

		@Override
		public void completed(Integer result, ReadAttachMent attachment) {
			AbsClient client = ClientManager.getClient(attachment.sockChannel.toString());
			client.addPacket(attachment.byteBuffer);
			IDecoder decoder = AsyncSocketAcceptor.this.getDecoder();
			while (decoder.isDecoderable(client.getReceivedData())) { // 한번에 여러 command 들어올 수 있음
				byte[] command = decoder.decode(client.getReceivedData());
				if (command == null) {
					break;
				}
				AsyncSocketAcceptor.this.onMessageReceived(client, command);
			}
			attachment.byteBuffer.clear();
			attachment.sockChannel.read(attachment.byteBuffer, attachment, this);
		}
	};

	public AsyncSocketAcceptor(IDecoder decoder) {
		super(decoder);
	}

	private void startRead(AsynchronousSocketChannel sockChannel) {
		ReadAttachMent readAttachMent = new ReadAttachMent(sockChannel);
		sockChannel.read(readAttachMent.byteBuffer, readAttachMent, readHandler);
	}

	@Override
	public void start() {
		try {
			AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4999);
			serverChannel.bind(hostAddress, 100);
			serverChannel.accept(serverChannel, acceptHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
