package com.lgcns.jgli.clientmanage;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

	private static Map<String, Client> addressClient = new ConcurrentHashMap<>();

	public static void putClient(Socket socket) {
		SocketAddress socketAddress = socket.getRemoteSocketAddress();
		addressClient.put(socketAddress.toString(), new Client(socket));
	}
}
