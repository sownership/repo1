package algorithm.codingbat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class String3 {

	public static void main(String[] args) {
		String3 instance = new String3();
		instance.gHappyRunner(instance);
	}

	private void equalIsNotRunner(String3 instance) {
		System.out.println(instance.equalIsNot("This is not"));
		System.out.println(instance.equalIsNot("This is notnot"));
		System.out.println(instance.equalIsNot("noisxxnotyynotxisi"));
	}

	public boolean equalIsNot(String str) {

		int isCnt = 0;
		int notCnt = 0;
		for (int i = 0; i < str.length(); i++) {
			if (i + "is".length() <= str.length() && "is".equals(str.substring(i, i + "is".length()))) {
				isCnt++;
			}
			if (i + "not".length() <= str.length() && "not".equals(str.substring(i, i + "not".length()))) {
				notCnt++;
			}
		}
		return isCnt == notCnt;
	}

	private void gHappyRunner(String3 instance) {
		System.out.println(instance.gHappy("xxggxx"));
		System.out.println(instance.gHappy("xxgxx"));
		System.out.println(instance.gHappy("xxggyygxx"));
	}

	public boolean gHappy(String str) {
		// for (int i = 0; i < str.length(); i++) {
		// if (str.charAt(i) == 'g') {
		// if ((i + 1 < str.length() && str.charAt(i + 1) == 'g') || (i - 1 >= 0 &&
		// str.charAt(i - 1) == 'g')) {
		// continue;
		// } else {
		// return false;
		// }
		// }
		// }
		// return true;

		// int cnt = 0;
		// for (int i = 0; i < str.length(); i++) {
		// if (str.charAt(i) == 'g') {
		// cnt++;
		// } else if (cnt > 1) {
		// cnt = 0;
		// } else if (cnt == 1) {
		// return false;
		// }
		// }
		// if (cnt == 1) {
		// return false;
		// }
		// return true;

		Pattern p = Pattern.compile("(?<!g)g(?!g)");
		Matcher m = p.matcher(str);
		return !m.find();
	}
}
