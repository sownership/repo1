package algorithm;

import java.util.LinkedList;
import java.util.List;

public class StringMerge {

	public static void main(String[] args) {

		String in = "ABC,BC,CD,DE,EB,CF,FG";
		System.out.println(solve(in));
	}

	private static String solve(String in) {

		String[] ina = in.split(",");
		List<Character> dup = getDup(ina);
		
		List<String> tmp = new LinkedList<>();
		for (String ins : ina) {
			merge(tmp, ins);
		}
		return null;
	}

	private static List<Character> getDup(String[] ina) {
		List<Character> dup = new LinkedList<>();
		String tmp = "";
		for (String ins : ina) {
			for (int i = 0; i < ins.length(); i++) {
				int inx = tmp.indexOf(ins.charAt(i));
				if (inx >= 0) {
					if (inx + 1 < tmp.length() && i + 1 < ins.length() && tmp.charAt(inx + 1) != ins.charAt(i + 1)) {
						dup.add(ins.charAt(i));
					}
				}
			}
		}
		System.out.println(dup);
		return dup;
	}

	private static void merge(List<String> tmp, String ins) {

		if (tmp.size() == 0) {
			tmp.add(ins);
		} else {
			for (String tmps : tmp) {

			}
		}
	}
}
