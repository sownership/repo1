package algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class P17_LetterCombinationsofaPhoneNumber {

	public static void main(String[] args) {
		System.out.println(new P17_LetterCombinationsofaPhoneNumber().letterCombinations("23"));
	}

	char[][] cs = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' }, { 'j', 'k', 'l' }, { 'm', 'n', 'o' },
			{ 'p', 'q', 'r', 's' }, { 't', 'u', 'v' }, { 'w', 'x', 'y', 'z' } };

	public List<String> letterCombinations(String digits) {

		List<String> r = new ArrayList<>();
		if (digits != null && digits.length() > 0)
			r(r, digits, 0, new char[digits.length()]);
		return r;
	}

	private void r(List<String> r, String digits, int i, char[] tmp) {
		if (i == digits.length()) {
			StringBuilder s = new StringBuilder();
			for (int si = 0; si < digits.length(); si++)
				s.append(tmp[si]);
			r.add(s.toString());
			return;
		}
		for (char c : cs[digits.charAt(i) - '0' - 2]) {
			tmp[i] = c;
			r(r, digits, i + 1, tmp);
		}
	}
}
