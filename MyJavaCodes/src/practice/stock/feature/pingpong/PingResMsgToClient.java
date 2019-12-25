package practice.stock.feature.pingpong;

import practice.stock.client.Client;
import practice.stock.msg.ResMsgToClient;

public class PingResMsgToClient extends ResMsgToClient {

	public PingResMsgToClient(Client client, byte cmd, byte[] body) {
		super(client, cmd, body);
	}

}
