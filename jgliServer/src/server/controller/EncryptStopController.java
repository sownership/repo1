package server.controller;

import java.nio.ByteBuffer;

import server.client.AbsClient;
import server.util.WhiteBoard;

public class EncryptStopController implements IController {

	@Override
	public void start(AbsClient client, ByteBuffer req) {
		WhiteBoard.notify("encrypt"+client+"/stop", null);
	}

}
