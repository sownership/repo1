package com.github.ffalcinelli.jdivert;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;import java.net.SocketAddress;

public class Client {

	public static void main(String[] args) throws Exception {

		try (Socket s = getSocket();
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {
			bos.write("hahaha".getBytes());
			bos.flush();
			Thread.sleep(3000);
		}

	}

	private static Socket getSocket() throws IOException {
		Socket s = new Socket();
		s.setReuseAddress(true);
		s.bind(new InetSocketAddress("127.0.0.1", 8012));
		s.connect(new InetSocketAddress("127.0.0.1", 8010));
		return s;
	}
}
