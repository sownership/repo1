package server;

import java.nio.ByteBuffer;

import server.client.AbsClient;
import server.controller.FrontController;
import server.decoder.IDecoder;

public abstract class AbsIOAccepter {

	private IDecoder decoder;

	public AbsIOAccepter(IDecoder decoder) {
		this.decoder = decoder;
	}

	public abstract void start();

	protected void onMessageReceived(AbsClient client, byte[] message) {
		FrontController.run(client, ByteBuffer.wrap(message));
	}
	
	protected void terminate() {
		
	}
}
