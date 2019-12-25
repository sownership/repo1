package practice.stock.feature;

import practice.stock.client.Client;
import practice.stock.feature.pingpong.PingReqMsgFromClient;
import practice.stock.msg.AbstractMsg;

public class ReqMsgFromClientFactory {

	public static AbstractMsg get(Client client, byte cmd, byte[] body) {
		if(cmd==0x01) {
			return new PingReqMsgFromClient(client, body);
		}
		return null;
	}

}
