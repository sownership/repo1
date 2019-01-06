package server.controller;

import java.util.Map;

import server.client.AbsClient;

public interface IController {

	public void start(AbsClient client, Map<String, Object> msg);
}
