package practice.stock.msg.toclient.req;

import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class ReqMsgToClient extends AbstractMsg {

	public ReqMsgToClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
