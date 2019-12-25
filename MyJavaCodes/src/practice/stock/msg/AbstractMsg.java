package practice.stock.msg;

import practice.stock.client.Client;

public abstract class AbstractMsg {

	private Client client;
	
	private byte cmd;
	
	private byte[] body;
	
	public AbstractMsg(Client client, byte cmd, byte[] body) {
		this.client = client;
		this.cmd = cmd;
		this.body = body;
	}
	
	public byte getCmd() {
		return this.cmd;
	}

	public byte[] getBody() {
		return this.body;
	}
	
	public Client getClient() {
		return this.client;
	}
	
}
