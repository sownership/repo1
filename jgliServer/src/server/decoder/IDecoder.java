package server.decoder;

import java.util.Queue;

public interface IDecoder {

	public boolean isDecoderable(Queue<Byte> qeueue);
	
	public byte[] decode(Queue<Byte> queue);
}
