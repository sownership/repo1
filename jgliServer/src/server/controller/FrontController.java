package server.controller;

import java.nio.ByteBuffer;

import server.client.AbsClient;
import server.util.DependancyInjectionUtil;

public class FrontController {

	public static void run(AbsClient client, ByteBuffer message) {
		String msg = new String(message.array());
		IController controller = DependancyInjectionUtil.get("commandControllerMapper.properties", msg, msg,
				IController.class);
		controller.start(client, message);
	}

}
