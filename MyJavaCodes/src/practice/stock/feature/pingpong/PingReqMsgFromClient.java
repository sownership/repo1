package practice.stock.feature.pingpong;

import practice.stock.client.Client;
import practice.stock.msg.ReqMsgFromClient;

public class PingReqMsgFromClient extends ReqMsgFromClient {

	public PingReqMsgFromClient(Client client, byte[] body) {
		super(client, (byte)0x01, body);
	}

}
