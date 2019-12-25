package practice.stock.msg;

import practice.stock.client.Client;

public class ReqMsgFromClient extends AbstractMsg {

	public ReqMsgFromClient(Client client, byte cmd, byte[] body) {
		super(client, cmd, body);
	}

}
