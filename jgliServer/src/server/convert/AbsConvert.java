package server.convert;

import java.nio.ByteBuffer;

public abstract class AbsConvert {

	protected ByteBuffer remain = ByteBuffer.allocate(1024 * 16);
	protected ByteBuffer convertResult = ByteBuffer.allocate(1024 * 16);
	
	public abstract ByteBuffer convert(ByteBuffer data);
	
	public abstract ByteBuffer convertRemain(ByteBuffer data);
}
