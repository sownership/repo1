package server;

import java.nio.ByteBuffer;
import java.util.Scanner;

import server.client.AbsClient;
import server.client.ClientManager;
import server.client.SystemInClient;
import server.decoder.IDecoder;

public class SystemIOAcceptor extends AbsIOAccepter {

	private static Scanner scanner = new Scanner(System.in);
	private static AbsClient client = new SystemInClient();

	public SystemIOAcceptor(IDecoder decoder) {
		super(decoder);
		ClientManager.add(client.getId(), client);
	}

	@Override
	public void start() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					client.addPacket(scanner.nextLine().getBytes());
					IDecoder decoder = getDecoder();
					ByteBuffer receivedData = client.getReceivedData(); //누적해서 받은 데이터
					while (decoder.isDecoderable(receivedData)) { //한번에 여러 command 들어올 수 있음
						onMessageReceived(client, decoder.decode(receivedData));
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
