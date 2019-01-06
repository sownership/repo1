package server;

import server.decoder.KeywordBaseDecoder;

public class ServerMain {

	public static void main(String[] args) throws InterruptedException {
		new AsyncSocketAcceptor(new KeywordBaseDecoder()).start();

		Thread.currentThread().join();
	}
}
