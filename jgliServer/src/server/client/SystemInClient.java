package server.client;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SystemInClient extends AbsClient {

	private PrintStream printStream = System.out;
	
	@Override
	public void send(ByteBuffer message, Runnable run) {
		//message 처리 후 run 을 수행해 준다
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
