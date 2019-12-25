package practice.stock.msg;

import practice.stock.client.Client;

public class ReqMsgToClient extends AbstractMsg {

	public ReqMsgToClient(Client client, byte cmd, byte[] body) {
		super(client, cmd, body);
	}

}
