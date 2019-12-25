package practice.stock.msg;

import java.nio.ByteBuffer;

import practice.stock.client.Client;

public class ResMsgToClient extends AbstractMsg {

	private byte reqCmd;
	
	public ResMsgToClient(Client client, byte reqCmd, byte[] body) {
		super(client, (byte)0xff, body);
		this.reqCmd=reqCmd;
	}

	public byte[] getBytes() {
		ByteBuffer bb = ByteBuffer.allocate(1024*8);
		bb.put((byte) 0xff); //start
		bb.put(getCmd()); //ack
		bb.put(reqCmd); //reqCmd
		bb.putInt(getBody().length); //body length
		bb.put(getBody()); //body
		bb.flip();
		byte[] b = new byte[bb.remaining()];
		bb.get(b);
		return b;
	}
}
