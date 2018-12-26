package server.controller;

import java.nio.ByteBuffer;

import server.client.AbsClient;
import server.util.DependancyInjectionUtil;

public class FrontController {

	public static void run(AbsClient client, ByteBuffer message) {
		String cmd = new String(message.array());
		IController controller = DependancyInjectionUtil.get("commandControllerMapper.properties", cmd, message,
				IController.class);
		controller.start(client, message);
	}

}
