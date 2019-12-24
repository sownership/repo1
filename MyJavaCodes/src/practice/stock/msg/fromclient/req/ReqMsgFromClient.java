package practice.stock.msg.fromclient.req;

import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class ReqMsgFromClient extends AbstractMsg {

	public ReqMsgFromClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
