package server.controller;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import server.client.AbsClient;
import server.util.WhiteBoard;

public class EncryptNumAgainController implements IController {

	@Override
	public void start(AbsClient client, ByteBuffer req) {
		Map<String, Object> v = new HashMap<>();
		v.put("num", 10);
		WhiteBoard.notify("encrypt"+client+"/num", v);
	}

}
