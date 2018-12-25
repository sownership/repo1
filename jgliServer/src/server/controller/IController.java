package server.controller;

import java.nio.ByteBuffer;

import server.client.AbsClient;

public interface IController {

	public void start(AbsClient client, ByteBuffer req);
}
