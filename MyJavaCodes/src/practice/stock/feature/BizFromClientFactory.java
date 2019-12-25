package practice.stock.feature;

import practice.stock.biz.AbstractBiz;
import practice.stock.feature.pingpong.PingBizFromClient;
import practice.stock.msg.AbstractMsg;
import practice.stock.msg.ReqMsgFromClient;
import practice.stock.msg.ResMsgFromClient;

public class BizFromClientFactory {

	public static AbstractBiz get(AbstractMsg msg) {
		if(msg instanceof ReqMsgFromClient) {
			if(msg.getCmd()==0x01) {
				return new PingBizFromClient();
			}
		} else if(msg instanceof ResMsgFromClient) {
			
		}
		return null;
	}

}
