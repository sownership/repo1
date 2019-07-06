package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

	public static void main(String[] args) throws ParseException {

		System.out.println(subtractToHms("091100", "141010"));
		System.out.println(subtractToSeconds("091100", "141010"));
		System.out.println(subtractTodHms("20180530090500", "20180531080601"));
	}

	private static int addToSeconds(String HHmmss1, String HHmmss2) throws ParseException {

		int h1 = Integer.valueOf(HHmmss1.substring(0, 2));
		int m1 = Integer.valueOf(HHmmss1.substring(2, 4));
		int s1 = Integer.valueOf(HHmmss1.substring(4, 6));
		int h2 = Integer.valueOf(HHmmss2.substring(0, 2));
		int m2 = Integer.valueOf(HHmmss2.substring(2, 4));
		int s2 = Integer.valueOf(HHmmss2.substring(4, 6));

		int seconds = (h2 + h1) * 60 * 60 + (m2 + m1) * 60 + (s2 + s1);

		return seconds;
	}

	private static String addToHms(String HHmmss1, String HHmmss2) throws ParseException {

		int seconds = addToSeconds(HHmmss1, HHmmss2);
		int h = seconds / (60 * 60);
		int m = seconds / 60 % 60;
		int s = seconds % 60;

		return String.format("%02d%02d%02d", h, m, s);
	}

	private static String addTodHms(String HHmmss1, String HHmmss2) throws ParseException {

		int seconds = addToSeconds(HHmmss1, HHmmss2);
		int d = seconds / (60 * 60 * 24);
		int h = seconds / (60 * 60) % 24;
		int m = seconds / 60 % 60;
		int s = seconds % 60;

		return String.format("%02d%02d%02d", h, m, s);
	}

	private static int subtractToSeconds(String HHmmss1, String HHmmss2) throws ParseException {

		int h1 = Integer.valueOf(HHmmss1.substring(0, 2));
		int m1 = Integer.valueOf(HHmmss1.substring(2, 4));
		int s1 = Integer.valueOf(HHmmss1.substring(4, 6));
		int h2 = Integer.valueOf(HHmmss2.substring(0, 2));
		int m2 = Integer.valueOf(HHmmss2.substring(2, 4));
		int s2 = Integer.valueOf(HHmmss2.substring(4, 6));

		int diff = (h2 - h1) * 60 * 60 + (m2 - m1) * 60 + (s2 - s1);

		return diff;
	}

	private static String subtractToHms(String HHmmss1, String HHmmss2) throws ParseException {

		int seconds = subtractToSeconds(HHmmss1, HHmmss2);
		int h = seconds / (60 * 60);
		int m = seconds / 60 % 60;
		int s = seconds % 60;

		return String.format("%02d%02d%02d", h, m, s);
	}

	private static String subtractTodHms(String yyyyMMddHHmmss1, String yyyyMMddHHmmss2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long d1 = sdf.parse(yyyyMMddHHmmss1).getTime();
		long d2 = sdf.parse(yyyyMMddHHmmss2).getTime();

		long seconds = d2 - d1;
		long diffD = seconds / (1000 * 60 * 60 * 24);
		long diffH = seconds / (1000 * 60 * 60) % 24;
		long diffM = seconds / (1000 * 60) % 60;
		long diffS = seconds / 1000 % 60;

		return String.format("%02d%02d%02d%02d", diffD, diffH, diffM, diffS);
	}

}
