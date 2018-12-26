package server.client;

import java.nio.ByteBuffer;

public abstract class AbsClient {

	private ByteBuffer recevedData = ByteBuffer.allocate(1024 * 10);

	public synchronized void addPacket(byte[] packet) {
		recevedData.compact();
		recevedData.put(packet);
		recevedData.flip();
	}

	public ByteBuffer getReceivedData() {
		return recevedData;
	}

	public abstract void send(ByteBuffer message, Runnable run);

	public abstract void close();
}
