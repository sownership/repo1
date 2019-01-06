package server.controller;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import server.client.AbsClient;
import server.util.WhiteBoard;

public class ConvertNumAgainController implements IController {

	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		Map<String, Object> v = new HashMap<>();
		v.put("num", 10);
		WhiteBoard.notify("convert"+client+"/num", v);
	}

}
