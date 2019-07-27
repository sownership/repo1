package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {

	public static void main(String[] args) throws ParseException {

//		System.out.println(subtractToHms("091100", "141010"));
//		System.out.println(subtractToSeconds("091100", "141010"));
//		System.out.println(subtractTodHms("20180530090500", "20180531080601"));

		System.out.println(addTime("10:10:10", "16:05:03"));
	}

	/**
	 * addTime("10:10:10", "10:05:03") = 20:15:13
	 */
	private static String addTime(String time1, String time2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		long after = sdf.parse(time2).getTime() - sdf.parse("00:00:00").getTime();
		return sdf.format(new Date(sdf.parse(time1).getTime() + after));
	}

	/**
	 * subTime("10:10:10", "10:05:03") = 00:05:07
	 */
	private static String subTime(String time1, String time2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		long before = sdf.parse(time2).getTime() - sdf.parse("00:00:00").getTime();
		return sdf.format(new Date(sdf.parse(time1).getTime() - before));
	}

	/**
	 * addDate("20190228 10:10:10", 5) = 20190305 10:10:10
	 */
	private static String addDate(String date1, int days) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return sdf.format(new Date(sdf.parse(date1).getTime() + days * 24 * 60 * 60 * 1000));
	}

	/**
	 * subDate("20190228 10:10:10", 5) = 20190223 10:10:10
	 */
	private static String subDate(String date1, int days) throws ParseException {
		return addDate(date1, -days);
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
		return toHms(addToSeconds(HHmmss1, HHmmss2));
	}

	private static String toHms(int seconds) {
		int h = seconds / (60 * 60);
		int m = seconds / 60 % 60;
		int s = seconds % 60;

		return String.format("%02d%02d%02d", h, m, s);
	}

	private static String addTodHms(String HHmmss1, String HHmmss2) throws ParseException {
		return todHms(addToSeconds(HHmmss1, HHmmss2));
	}

	private static String todHms(int seconds) {
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
		return toHms(subtractToSeconds(HHmmss1, HHmmss2));
	}

	private static String subtractTodHms(String yyyyMMddHHmmss1, String yyyyMMddHHmmss2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long d1 = sdf.parse(yyyyMMddHHmmss1).getTime();
		long d2 = sdf.parse(yyyyMMddHHmmss2).getTime();

		int seconds = (int) ((d2 - d1) / 1000);
		return todHms(seconds);
	}

}
