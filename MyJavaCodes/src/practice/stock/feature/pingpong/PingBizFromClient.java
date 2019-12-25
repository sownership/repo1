package practice.stock.feature.pingpong;

import practice.stock.biz.BizFromClient;
import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class PingBizFromClient extends BizFromClient {

	@Override
	public void run(AbstractMsg msg) {
		System.out.println(msg);
		Client client = msg.getClient();
		client.send(new PingResMsgToClient(client, (byte)0xff, "pong".getBytes()));
	}

}
