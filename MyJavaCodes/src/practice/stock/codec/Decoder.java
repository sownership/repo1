package practice.stock.codec;

import java.nio.ByteBuffer;

import practice.stock.client.Client;
import practice.stock.feature.ReqMsgFromClientFactory;
import practice.stock.feature.ResMsgFromClientFactory;
import practice.stock.msg.AbstractMsg;

public class Decoder {
	
	/**
	 * start 1byte: 0xff
	 * cmd 1byte(0xff is ack)
	 * body len 4byte
	 * body
	 * @return
	 */
	public static AbstractMsg decode(Client client) {
		ByteBuffer bb = client.getRecvBuffer();
		bb.flip();
		boolean isStarted = false;
		while(bb.hasRemaining()) {
			if(bb.get()==(byte)0xff) {
				isStarted = true;
				break;
			}
		}
		if(!isStarted) return null;
		if(!bb.hasRemaining()) {
			bb.position(bb.position()-1);
			return null;
		}
		byte cmd = bb.get();
		if(bb.remaining()<4) {
			bb.position(bb.position()-2);
			return null;
		}
		int bodyLen = bb.getInt();
		if(bb.remaining()<bodyLen) {
			bb.position(bb.position()-6);
			return null;
		}
		byte[] b = new byte[bodyLen];
		bb.get(b);
		AbstractMsg msg = null;
		if(cmd==0xff) {
			msg = ResMsgFromClientFactory.get(client, cmd, b);
		} else {
			msg = ReqMsgFromClientFactory.get(client, cmd, b);
		}
		
		bb.compact();
		
		return msg;
	}
}
