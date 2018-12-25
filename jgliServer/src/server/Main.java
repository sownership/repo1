package server;

import server.decoder.LineSeperatorDecoder;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		new SystemIOAcceptor(new LineSeperatorDecoder()).start();
		
		Thread.currentThread().join();
	}
}
