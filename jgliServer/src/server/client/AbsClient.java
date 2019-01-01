package server.client;

import java.nio.ByteBuffer;

public abstract class AbsClient {

	/**
	 * �׻� ���������� ������� �� ��
	 */
	private ByteBuffer receivedData = ByteBuffer.allocate(1024 * 8);

	public void addPacket(byte[] packet) {
		receivedData.put(packet);
	}
	
	public void addPacket(ByteBuffer packet) {
		receivedData.put(packet);
	}

	public ByteBuffer getReceivedData() {
		return receivedData;
	}

	public abstract void send(ByteBuffer message, Runnable run);

	public abstract void close();
	
	public abstract String getId();
}
