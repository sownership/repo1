package util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class HexUtil {

	public static void main(String[] args) {
		System.out.println(plus("12301234", 1));
		System.out.println(plus("F3210EFF", 1));
		System.out.println(plus("FFFFFFFF", 1));
	}

	public static String plusOne(String hex) {
		char[] ca = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '0' };
		Map<Character, Character> map = new HashMap<>();
		for (int i = 0; i < ca.length - 1; i++)
			map.put(ca[i], ca[i + 1]);
		StringBuilder sb = new StringBuilder();
		boolean inc = false;
		for (int i = hex.length() - 1; i >= 0; i--) {
			if (i == hex.length() - 1 || inc) {
				sb.append(map.get(hex.charAt(i)));
				inc = hex.charAt(i) == 'F';
			} else {
				sb.append(hex.charAt(i));
			}
		}
		if (inc)
			sb.append("1");
		return sb.reverse().toString();
	}

	public static String plus(String hex, int n) {
		return new BigInteger(hex, 16).add(new BigInteger("" + n)).toString(16).toUpperCase();
	}
}
