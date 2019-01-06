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

public class NonStopConvertWithFileSaveController extends NonStopConvertController {

	private AbsClient client;
	private Map<String, Object> msg;
	private SeekableByteChannel in;
	private SeekableByteChannel out;
	private ByteBuffer bf = ByteBuffer.allocate(1024 * 8);
	private long startTime = System.currentTimeMillis();

	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		this.client = client;
		this.msg = msg;

		String file = (String) msg.get("message");
		Path pathIn = Paths.get("", file);
		Path pathOut = Paths.get("", client + ".enc");
		try {
			in = Files.newByteChannel(pathIn, StandardOpenOption.READ);
			out = Files.newByteChannel(pathOut, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void next() {
		int i = 0;
		try {
			if ((i = in.read(bf)) > 0) {
				ByteBuffer result = super.convert((String) msg.get("command"), bf);
				result.flip();
				out.write(result);
				bf.clear();
				ExecutorUtil.bizExecutor.execute(new Runnable() {

					@Override
					public void run() {
						next();
					}
				});
			} else {
				out.write(super.convertRemain((String) msg.get("command"), bf));
				System.out.println(System.currentTimeMillis() - startTime);
				terminate();
			}
		} catch (IOException e) {
			e.printStackTrace();
			terminate();
		}
	}

	protected void terminate() {
		try {
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
