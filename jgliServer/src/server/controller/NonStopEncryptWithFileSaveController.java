package server.controller;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

import server.client.AbsClient;

public class NonStopEncryptWithFileSaveController extends NonStopEncryptController {

	private SeekableByteChannel seekableByteChannel;
	
	@Override
	public void start(AbsClient client, ByteBuffer req) {
		seekableByteChannel = getWriteChannel(req);
		super.start(client, req);
	}

	@Override
	protected void endProcess(AbsClient client, ByteBuffer msg, Runnable run) {
		seekableByteChannel.write(msg) {
			run.run();
		}
	}

	private SeekableByteChannel getWriteChannel(ByteBuffer req) {
		// TODO Auto-generated method stub
		return null;
	}
}
