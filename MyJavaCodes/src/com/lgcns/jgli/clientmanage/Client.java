package com.lgcns.jgli.clientmanage;

import com.lgcns.jgli.codec.Decoder;
import com.lgcns.jgli.codec.Encoder;

public class Client {

	private String address;
	private Decoder decoder;
	private Encoder encoder;
	
	public Client(String address) {
		this.address = address;
		decoder = new Decoder();
		encoder = new Encoder();
	}
	
	public boolean send(String message) {
		decoder
		return true;
	}
}
