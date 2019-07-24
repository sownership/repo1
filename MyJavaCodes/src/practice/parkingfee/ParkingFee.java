package practice.parkingfee;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ParkingFee {

	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

		startServer();

		startConsole();
	}

	private static void startConsole() throws IOException, ParseException {
		try (OutputStream out = new FileOutputStream("resource\\parkingfee\\output\\RESULT.TXT", true)) {
			while (true) {
				String cmd = sc.nextLine();
				if ("quit".equals(cmd)) {
					return;
				}
				cmdProcess(cmd, out);
			}
		}
	}

	private static void startServer() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try (ServerSocket ss = new ServerSocket(9000)) {
					Socket c = ss.accept();
					newClientProcess(c);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private static void newClientProcess(Socket c) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try (Socket s = c;
						BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
						OutputStream out = s.getOutputStream()) {
					byte[] b = new byte[1024];
					int len;
					while(true) {
						len = bis.read(b);
						String cmd = new String(b, 0, len);
						if("quit".equals(cmd)) {
							break;
						}
						cmdProcess(cmd, out);
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private static void cmdProcess(String cmd, OutputStream out)
			throws FileNotFoundException, IOException, ParseException {
		if (cmd.startsWith("CARNO=")) {
			carNoProcess(cmd, out);
		} else if (cmd.startsWith("DATE=")) {
			dateProcess(cmd, out);
		}
	}

	private static void carNoProcess(String cmd, OutputStream out)
			throws FileNotFoundException, IOException, ParseException {
		String[] sa = cmd.split("=");
		String carNo = sa[1];

		Map<String, Integer> dateParkingtime = new TreeMap<>();
		Map<String, Integer> dateParkingFee = new TreeMap<>();

		List<File> fs = getFiles();
		for (File f : fs) {
			String yyyyMMdd = f.getName().substring(0, f.getName().lastIndexOf(".LOG"));
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] lE = line.split("#");
					if (!carNo.equals(lE[2])) {
						continue;
					}
					// 건별 주차 시간
					int parkingTime = subtractToSeconds(lE[1], lE[0]);
					// 총 주차 시간
					if (dateParkingtime.containsKey(yyyyMMdd)) {
						dateParkingtime.put(yyyyMMdd, parkingTime + dateParkingtime.get(yyyyMMdd));
					} else {
						dateParkingtime.put(yyyyMMdd, parkingTime);
					}
					// 총 주차 요금
					int parkingFee = calParkingFee(parkingTime, lE[3]);
					if (dateParkingFee.containsKey(yyyyMMdd)) {
						dateParkingFee.put(yyyyMMdd, parkingFee + dateParkingFee.get(yyyyMMdd));
					} else {
						dateParkingFee.put(yyyyMMdd, parkingFee);
					}
				}
			}
		}

		// 결과를 파일에 저장
		reportForCarNo(cmd, dateParkingtime, dateParkingFee, out);
	}

	private static void dateProcess(String cmd, OutputStream out)
			throws FileNotFoundException, IOException, ParseException {
		String[] sa = cmd.split("=");
		String date = sa[1];

		Map<String, Integer> carParkingFee = new TreeMap<>();

		List<File> fs = getFiles(date);
		for (File f : fs) {
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] lE = line.split("#");
					String carNo = lE[2];
					// 건별 주차 시간
					int parkingTime = subtractToSeconds(lE[1], lE[0]);
					// 총 주차 요금
					int parkingFee = calParkingFee(parkingTime, lE[3]);
					if (carParkingFee.containsKey(carNo)) {
						carParkingFee.put(carNo, parkingFee + carParkingFee.get(carNo));
					} else {
						carParkingFee.put(carNo, parkingFee);
					}
				}
			}
		}

		// 결과를 파일에 저장
		reportForDate(cmd, carParkingFee, out);
	}

	private static List<File> getFiles(String date) {
		List<File> fl = new LinkedList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		File dir = new File(".\\resource\\parkingfee\\input");
		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(dir);
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			} else if (f.getName().endsWith(".LOG")) {
				try {
					String fDate = f.getName().substring(0, 8);
					sdf.parse(fDate);
					if (date.equals(fDate)) {
						fl.add(f);
					}
				} catch (ParseException e) {
					continue;
				}
			}
		}
		return fl;
	}

	private static void reportForCarNo(String cmd, Map<String, Integer> dateParkingtime,
			Map<String, Integer> dateParkingFee, OutputStream out) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(out);
		bos.write(cmd.getBytes());
		for (Map.Entry<String, Integer> e : dateParkingtime.entrySet()) {
			String yyyyMMdd = e.getKey();
			bos.write("#".getBytes());
			bos.write(yyyyMMdd.getBytes());
			bos.write("#".getBytes());
			bos.write(toHms(e.getValue()).getBytes());
			bos.write("#".getBytes());
			bos.write(String.valueOf(dateParkingFee.get(yyyyMMdd)).getBytes());
		}
		bos.write("\n".getBytes());
		bos.flush();
	}

	private static String toHms(int seconds) {
		int h = seconds / (60 * 60);
		int m = seconds / 60 % 60;
		int s = seconds % 60;

		return String.format("%02d%02d%02d", h, m, s);
	}
	
	private static void reportForDate(String cmd, Map<String, Integer> carParkingFee, OutputStream out)
			throws IOException {
		try (BufferedOutputStream bis = new BufferedOutputStream(out)) {
			bis.write(cmd.getBytes());
			for (Map.Entry<String, Integer> e : carParkingFee.entrySet()) {
				String carNo = e.getKey();
				bis.write("#".getBytes());
				bis.write(carNo.getBytes());
				bis.write("#".getBytes());
				bis.write(String.valueOf(e.getValue()).getBytes());
			}
			bis.write("\n".getBytes());
			bis.flush();
		}
	}

	private static int calParkingFee(int parkingTime, String type) {
		int fee = 0;
		if (parkingTime < 60 * 10) {
			return fee;
		}
		fee = 3000;
		int unit = 0;
		if ("1".equals(type)) {
			unit = 1000;
		} else if ("2".equals(type)) {
			unit = 1500;
		}
		fee += unit * ((parkingTime - (60 * 30)) / (60 * 10));
		if ((parkingTime - (60 * 30)) % (60 * 10) != 0) {
			fee += unit;
		}
		return fee;
	}

	private static List<File> getFiles() {
		List<File> fl = new LinkedList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);

		File dir = new File(".\\resource\\parkingfee\\input");
		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(dir);
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			} else if (f.getName().endsWith(".LOG")) {
				try {
					sdf.parse(f.getName().substring(0, 8));
					fl.add(f);
				} catch (ParseException e) {
					continue;
				}
			}
		}
		return fl;
	}

	private static int subtractToSeconds(String HHmmss2, String HHmmss1) throws ParseException {

		int h1 = Integer.valueOf(HHmmss1.substring(0, 2));
		int m1 = Integer.valueOf(HHmmss1.substring(2, 4));
		int s1 = Integer.valueOf(HHmmss1.substring(4, 6));
		int h2 = Integer.valueOf(HHmmss2.substring(0, 2));
		int m2 = Integer.valueOf(HHmmss2.substring(2, 4));
		int s2 = Integer.valueOf(HHmmss2.substring(4, 6));

		int diff = (h2 - h1) * 60 * 60 + (m2 - m1) * 60 + (s2 - s1);

		return diff;
	}
}
