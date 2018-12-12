package com.lgcns.jgli.tcpnioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

public class TcpNioServer {

	public TcpNioServer() throws IOException {
		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
				.withFixedThreadPool(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());

		AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel
				.open(channelGroup);

		asynchronousServerSocketChannel.bind(new InetSocketAddress(5001));

		asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void attachment) {
				// 연결 후 실행 코드
				asynchronousServerSocketChannel.accept(null, this);
			}

			public void failed(Throwable exc, Void attachment) {
				// 연결 수락 실패 시 실행할 코드
			}
		});
	}
}
