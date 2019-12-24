package practice.stock;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import practice.stock.client.Client;

public class Server {

	private Set<Client> clients = Collections.synchronizedSet(new HashSet<>());

	public static void main(String[] args) {
		Server server = new Server();
		while (true) {
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000 * 3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void start() throws IOException {

		try (ServerSocket ss = new ServerSocket(49152)) {
			Socket socket = ss.accept();
			new Thread(() -> {
				Client client = new Client(socket);
				clients.add(client);
				try {
					client.start();
				} finally {
					clients.remove(client);
				}
			}).start();
		}
	}
}
