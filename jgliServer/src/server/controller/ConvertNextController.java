package server.controller;

import java.util.Map;

import server.client.AbsClient;
import server.util.WhiteBoard;

public class ConvertNextController implements IController {

	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		WhiteBoard.notify("convert/" + client + "/ACK", null);
	}

}
