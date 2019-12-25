package practice.stock.feature.pingpong;

import java.io.IOException;

import practice.stock.biz.BizFromClient;
import practice.stock.client.Client;
import practice.stock.msg.AbstractMsg;

public class PingBizFromClient extends BizFromClient {

	@Override
	public void run(AbstractMsg msg) {
		System.out.println(new String(msg.getBody()));
		Client client = msg.getClient();
		try {
			client.send(new PingResMsgToClient(client, msg.getCmd(), "pong".getBytes()).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
