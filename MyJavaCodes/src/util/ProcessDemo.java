package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ProcessDemo {
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] command = new String[] { "cmd" };
		ProcessDemo demo = new ProcessDemo();
//		demo.runLine(command);

//		demo.runLine("cmd");

		System.out.println(demo.getMyPid());
	}

	public String getMyPid() {
		return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}

	public void runLine(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			System.out.println(br.readLine());
		}
	}

	public void runChar(String[] command) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process p = builder.start();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
			int iv;
			while ((iv = br.read()) != -1) {
				System.out.print((char) iv);
				if ((char) iv == '>') {
					bw.write("exit");
					bw.newLine();
					bw.flush();
				}
			}
		}
		p.waitFor();
	}

	public void runLine(String[] command) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process p = builder.start();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
			bw.newLine();
			bw.flush();
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.endsWith(">")) {
					bw.write("exit");
					bw.newLine();
					bw.flush();
				}
			}
		}
		p.waitFor();
	}

	public static long getPid() {
		return Long.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
	}

	public static boolean isRunning(String pid) throws IOException {
		String[] cmd = { "cmd", "/c", "tasklist /fi \"pid eq " + pid + "\"" };
		Process process = Runtime.getRuntime().exec(cmd);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(pid))
					return true;
			}
		}
		return false;
	}
}