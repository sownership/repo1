package server.decoder;

import java.nio.ByteBuffer;
import java.util.Map;

public interface IDecoder {

	public boolean isDecoderable(ByteBuffer packet);
	
	public Map<String, Object> decode(ByteBuffer packet);
}
