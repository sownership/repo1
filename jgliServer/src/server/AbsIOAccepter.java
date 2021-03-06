package server;

import java.util.Map;

import server.client.AbsClient;
import server.controller.FrontController;
import server.decoder.IDecoder;

public abstract class AbsIOAccepter {

	private IDecoder decoder;

	public AbsIOAccepter(IDecoder decoder) {
		this.decoder = decoder;
	}

	public abstract void start();

	protected void onMessageReceived(AbsClient client, Map<String, Object> msg) {
		//System.out.println(msg);
		FrontController.run(client, msg);
	}

	public IDecoder getDecoder() {
		return decoder;
	}

	protected void terminate() {

	}
}
