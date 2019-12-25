package practice.stock.msg;

import practice.stock.client.Client;

public class ResMsgFromClient extends AbstractMsg {

	public ResMsgFromClient(Client client, byte cmd, byte[] body) {
		super(client, cmd, body);
	}

}
