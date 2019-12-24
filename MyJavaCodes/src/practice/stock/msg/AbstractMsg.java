package practice.stock.msg;

import practice.stock.client.Client;

public abstract class AbstractMsg {

	private Client client;
	
	private byte cmd;
	
	public AbstractMsg(Client client, byte cmd) {
		this.client = client;
		this.cmd = cmd;
	}
	
	public byte getCmd() {
		return this.cmd;
	}
}
