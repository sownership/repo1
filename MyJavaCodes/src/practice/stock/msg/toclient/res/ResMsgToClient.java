package practice.stock.msg.toclient.res;

import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class ResMsgToClient extends AbstractMsg {

	public ResMsgToClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
