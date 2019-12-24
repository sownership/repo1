package practice.stock.feature.pingpong;

import practice.stock.client.Client;
import practice.stock.msg.fromclient.req.ReqMsgFromClient;

public class PingReqMsgFromClient extends ReqMsgFromClient {

	public PingReqMsgFromClient(Client client, byte cmd) {
		super(client, cmd);
	}

}
