package practice.vcs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Server {

	private Svc svc;

	public Server(Svc svc) {
		this.svc = svc;
	}

	public void start() throws IOException {
		try (ServerSocket ss = new ServerSocket(10000)) {
			while (true) {
				Socket s = ss.accept();
				Thread t = new Thread(ExHelper.rWrapper(() -> {
					startClient(s);
				}));
				t.setDaemon(true);
				t.start();
			}
		}
	}

	private void startClient(Socket s) throws IOException {
		try(BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream())) {

			while (true) {
				byte[] b = new byte[1024];
				int len = bis.read(b);
				
				String command = new String(b, 0, len);
				sendAck(bos);

				if ("CHECKOUT".equals(command)) {
					checkOut(bis, bos);

				} else if ("CHECKIN".equals(command)) {
					checkIn(bis, bos);

				} else if ("MERGE".equals(command)) {
					merge(bis, bos);
				}

			}
		}
	}

	private void checkOut(BufferedInputStream bis, BufferedOutputStream bos) throws IOException {
		
		svc.checkoutTargets().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).forEach(ExHelper.cWrapper(e -> {
			Path relative = e.getKey();
			Path real = e.getValue();
			if (real.toFile().isDirectory()) {
				bos.write((relative.toString() + "\n").getBytes());
				bos.flush();
			} else {
				bos.write((relative.toString() + "" + real.toFile().length() + "\n").getBytes());
				bos.flush();
				bis.read(new byte[100]); //ack\n
				List<String> lines = Files.readAllLines(real);
				bos.write((lines.size() + "\n").getBytes());
				lines.stream().forEach(ExHelper.cWrapper(l -> {
					bos.write((l + "\n").getBytes());
				}));
				bos.flush();
			}
		}));
	}

	private void sendAck(BufferedOutputStream bos) throws IOException {
		bos.write("ACK\n".getBytes());
		bos.flush();
	}

	private void merge(BufferedReader br, BufferedOutputStream bos) throws IOException {
		String relativePath = br.readLine();
		sendAck(bos);
		String baseVer = br.readLine();
		sendAck(bos);
		String lineNum = br.readLine();
		sendAck(bos);
		List<String> lines = IntStream.range(0, Integer.parseInt(lineNum))
				.mapToObj(ExHelper.ifWrapper(i -> br.readLine())).collect(Collectors.toList());

		List<String> merged = svc.merge(String.format("MERGE %s %s", relativePath, baseVer), lines);
		bos.write((merged.size() + "\n").getBytes());
		bos.flush();
		if (br.readLine().equals("ACK")) {
			merged.stream().forEach(ExHelper.cWrapper(l -> {
				bos.write((l + "\n").getBytes());
			}));
			bos.flush();
		}
	}

	private void checkIn(BufferedReader br, BufferedOutputStream bos) throws IOException {
		String relativePath = br.readLine();
		sendAck(bos);
		String lineNum = br.readLine();
		sendAck(bos);
		List<String> lines = IntStream.range(0, Integer.parseInt(lineNum))
				.mapToObj(ExHelper.ifWrapper(i -> br.readLine())).collect(Collectors.toList());
		String comment = br.readLine();
		svc.checkIn(String.format("CHECKIN %s %s", relativePath, comment), lines);
	}
}
