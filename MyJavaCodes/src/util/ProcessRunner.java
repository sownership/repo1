package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcessRunner {
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] command = new String[] { "cmd" };
		ProcessRunner runner = new ProcessRunner();
		runner.runChar(command);
	}

	public void runChar(String[] command) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process p = builder.start();

		try (BufferedReader pbr = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedWriter pbw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
			int iv;
			while((iv=pbr.read())!=-1) {
				System.out.print((char)iv);
				if((char)iv=='>') {
					pbw.write("exit");
					pbw.newLine();
					pbw.flush();
				}
			}
		}
		p.waitFor();
	}
	
	public void runLine(String[] command) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process p = builder.start();

		try (BufferedReader pbr = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedWriter pbw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
			pbw.newLine();
			pbw.flush();
			String line;
			while((line=pbr.readLine())!=null) {
				System.out.println(line);
				if(line.endsWith(">")) {
					pbw.write("exit");
					pbw.newLine();
					pbw.flush();
				}
			}
		}
		p.waitFor();
	}
}