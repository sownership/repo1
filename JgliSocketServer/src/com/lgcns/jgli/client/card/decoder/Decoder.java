package com.lgcns.jgli.client.card.decoder;

import java.nio.ByteBuffer;

/**
 * toclientreq: 0xaa + COMMAND(1) + SERICODE(4) + BODYLEN(4) + BODY(n)
 * toclientres: 0xaa + 0xab + SERICODE(4) + BODYLEN(4) + BODY(n) fromclientreq:
 * 0xaa + COMMAND(1) + SERICODE(4) + BODYLEN(4) + BODY(n) fromclientres: 0xaa +
 * 0xab + SERICODE(4) + BODYLEN(4) + BODY(n)
 */
public class Decoder {

	private static final int MAX_MESSAGE_LENGTH = 1024 * 10;
	private static final int HEADER_LEANTH = 10;

	private ByteBuffer data = ByteBuffer.allocate(MAX_MESSAGE_LENGTH);

	public synchronized void addPacket(byte[] packet) {
		data.compact();
		data.put(packet);
		data.flip();
	}

	public synchronized byte[] decode() {
		if (data.remaining() < HEADER_LEANTH) {
			return null;
		}
		do {
			data.mark();
		} while (data.get() != 0xaa);
		if (data.remaining() < HEADER_LEANTH - 1) {
			data.reset();
			return null;
		}
		byte command = data.get();
		int transactionNumber = data.getInt();
		int bodyLength = data.getInt();
		if (data.remaining() < bodyLength) {
			data.reset();
			return null;
		}
		data.reset();
		byte[] message = new byte[HEADER_LEANTH + bodyLength];
		data.get(message);

		return message;
	}
}
