package server.encrypt;

import java.nio.ByteBuffer;

public abstract class AbsEncrypt {

	private ByteBuffer remain;
	
	public abstract ByteBuffer encrypt(ByteBuffer data);
	
	public abstract ByteBuffer encryptRemain(ByteBuffer data);
}
