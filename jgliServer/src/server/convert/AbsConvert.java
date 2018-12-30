package server.convert;

import java.nio.ByteBuffer;

public abstract class AbsConvert {

	private ByteBuffer remain;
	
	public abstract ByteBuffer encrypt(ByteBuffer data);
	
	public abstract ByteBuffer encryptRemain(ByteBuffer data);
}
