package server.client;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SystemInClient extends AbsClient {

	private PrintStream printStream = System.out;
	
	@Override
	public void send(ByteBuffer message, Runnable run) {
		//message ó�� �� run �� ������ �ش�
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
