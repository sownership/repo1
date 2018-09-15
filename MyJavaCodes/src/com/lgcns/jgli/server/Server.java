package com.lgcns.jgli.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.lgcns.jgli.clientmanage.Client;
import com.lgcns.jgli.clientmanage.ClientManager;

public class Server {

	public static void start() {
		while(true) {
			try(ServerSocket ss = new ServerSocket(8001)) {
				Socket s = ss.accept();
				ClientManager.putClient(s.getRemoteSocketAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
				sleep(1000 * 3);
			}
		}
	}

	private static void sleep(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
