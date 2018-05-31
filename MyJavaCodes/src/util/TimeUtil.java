package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtil {

	public static void main(String[] args) throws ParseException {

		System.out.println(getDiffHHmmss("091100", "141010"));
		System.out.println(getDiffSecHHmmss("091100", "141010"));
		System.out.println(getDiffyyyyMMddHHmmss("20180530090500", "20180531080601"));
	}

	private static int getDiffSecHHmmss(String HHmmss1, String HHmmss2) throws ParseException {

		int h1 = Integer.valueOf(HHmmss1.substring(0, 2));
		int m1 = Integer.valueOf(HHmmss1.substring(2, 4));
		int s1 = Integer.valueOf(HHmmss1.substring(4, 6));
		int h2 = Integer.valueOf(HHmmss2.substring(0, 2));
		int m2 = Integer.valueOf(HHmmss2.substring(2, 4));
		int s2 = Integer.valueOf(HHmmss2.substring(4, 6));

		int diff = (h2 - h1) * 60 * 60 + (m2 - m1) * 60 + (s2 - s1);

		return diff;
	}

	private static String getDiffHHmmss(String HHmmss1, String HHmmss2) throws ParseException {

		int diff = getDiffSecHHmmss(HHmmss1, HHmmss2);
		int diffH = diff / (60 * 60);
		int diffM = diff / 60 % 60;
		int diffS = diff % 60;

		return String.format("%02d%02d%02d", diffH, diffM, diffS);
	}

	private static String getDiffyyyyMMddHHmmss(String yyyyMMddHHmmss1, String yyyyMMddHHmmss2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long d1 = sdf.parse(yyyyMMddHHmmss1).getTime();
		long d2 = sdf.parse(yyyyMMddHHmmss2).getTime();
		
		long diff = d2 - d1;
		long diffD = diff / 1000 / 60 / 60 / 24;
		long diffH = diff / 1000 / 60 / 60 % 24;
		long diffM = diff / 1000 / 60 % 60;
		long diffS = diff / 1000 % 60;

		return String.format("%02d%02d%02d%02d", diffD, diffH, diffM, diffS);
	}
}
