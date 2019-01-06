package server.controller;

import java.util.Map;

import server.client.AbsClient;
import server.util.DependancyInjectionUtil;

public class FrontController {

	public static void run(AbsClient client, Map<String, Object> msg) {
		String cmd = (String) msg.get("command");
		IController controller = DependancyInjectionUtil.get("commandControllerMapper.properties", cmd,
				IController.class);
		controller.start(client, msg);
	}

}
