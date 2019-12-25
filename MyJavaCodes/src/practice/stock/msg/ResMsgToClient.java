package practice.stock.msg;

import practice.stock.client.Client;

public class ResMsgToClient extends AbstractMsg {

	public ResMsgToClient(Client client, byte cmd, byte[] body) {
		super(client, cmd, body);
	}

}
