package server;

import server.decoder.LineSeperatorDecoder;

public class ServerMain {

	public static void main(String[] args) throws InterruptedException {
		new SystemIOAcceptor(new LineSeperatorDecoder()).start();
		
		Thread.currentThread().join();
	}
}
