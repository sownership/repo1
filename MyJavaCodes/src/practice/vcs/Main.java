package practice.vcs;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {

		Svc svc = new Svc();
		Console console = new Console(svc);
		Server server = new Server(svc);
		
		console.start();
		server.start();
	}
}
