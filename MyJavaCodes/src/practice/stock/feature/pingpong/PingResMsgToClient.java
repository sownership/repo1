package practice.stock.feature.pingpong;

import practice.stock.client.Client;
import practice.stock.msg.toclient.res.ResMsgToClient;

public class PingResMsgToClient extends ResMsgToClient {

	public PingResMsgToClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
