package practice.stock.msg.fromclient.res;

import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class ResMsgFromClient extends AbstractMsg {

	public ResMsgFromClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
