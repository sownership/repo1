package server.decoder;

import java.nio.ByteBuffer;

public interface IDecoder {

	public boolean isDecoderable(ByteBuffer packet);
	
	public byte[] decode(ByteBuffer packet);
}
