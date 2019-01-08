package server.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import server.client.AbsClient;
import server.util.ExecutorUtil;
import server.util.WhiteBoard;
import server.util.WhiteBoard.Listener;

public class CommunicationConvertController extends AbsConvertController {

	private Listener listener = new Listener() {

		@Override
		public void onEvent(String topic, Map<String, Object> params) {

			if (("convert/" + client + "/ACK").equals(topic)) {
			} else if (topic.startsWith(("convert/" + client + "/NUM"))) {
				// todo set in.position
			}

			next();
		}
	};

	private AbsClient client;
	private Map<String, Object> msg;
	private SeekableByteChannel in;
	private ByteBuffer bf = ByteBuffer.allocate(1024 * 8);

	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		this.client = client;
		this.msg = msg;

		WhiteBoard.registerListener("convert/" + client + "/ACK", listener);
		WhiteBoard.registerListener("convert/" + client + "/NUM", listener);

		String file = (String) msg.get("message");
		Path pathIn = Paths.get("", file);
		try {
			in = Files.newByteChannel(pathIn, StandardOpenOption.READ);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		next();
	}

	private void next() {

		ExecutorUtil.bizExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					if (in.read(bf) > 0) {
						ByteBuffer result = convert((String) msg.get("command"), bf);
						bf.clear();
						result.flip();
						client.send(result, null);
					} else {
						ByteBuffer result = convertRemain((String) msg.get("command"), bf);
						result.flip();
						client.send(result, null);
						// System.out.println(System.currentTimeMillis() - startTime);
						terminate();
					}
				} catch (IOException e) {
					e.printStackTrace();
					terminate();
				}
			}
		});
	}

	private void terminate() {
//		try {
//			in.close();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

		WhiteBoard.unregisterListener("convert/" + client + "/ACK", listener);
		WhiteBoard.unregisterListener("convert/" + client + "/NUM", listener);
	}
}
