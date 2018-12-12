package com.lgcns.jgli.client.card;

import com.lgcns.jgli.client.card.decoder.Decoder;
import com.lgcns.jgli.client.card.decoder.Encoder;

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
		return true;
	}
}
