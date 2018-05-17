package algorithm;

import java.util.LinkedList;
import java.util.List;

public class CenterEncode {

	public static void main(String[] args) {

		String input = "AEB,EBFG,BFGH,GHIJKL,ZAE,XZAEB";
		System.out.println(getOrder(input));
	}

	private static String getOrder(String input) {
		String[] es = input.split(",");
		List<String> l = new LinkedList<>();
		for (int i = 0; i < es.length; i++) {
			l.add(es[i]);
		}
		while (l.size() > 1) {
			String s0 = l.get(0);
			String s1 = l.get(1);
			if (s0.contains(s1.substring(0, 1))) {
				l.set(0, add(s0, s1));
				l.remove(1);
			} else if (s1.contains(s0.substring(0, 1))) {
				l.set(1, add(s1, s0));
				l.remove(0);
			}
		}

		return l.get(0);
	}

	private static String add(String s0, String s1) {
		String tmp = s0;
		for (int i = 0; i < s1.length(); i++) {
			String next = s1.substring(i, i + 1);
			if (!s0.contains(next)) {
				tmp += next;
			}
		}
		return tmp;
	}
}
