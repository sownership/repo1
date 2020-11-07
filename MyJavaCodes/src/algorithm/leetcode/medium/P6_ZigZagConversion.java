package algorithm.leetcode.medium;

import java.util.Arrays;
import java.util.stream.IntStream;

public class P6_ZigZagConversion {

	public static void main(String[] args) {
		// PAHNAPLSIIGYIR
		System.out.println(new P6_ZigZagConversion().convert("PAYPALISHIRING", 3));
		// PINALSIGYAHRPI
		System.out.println(new P6_ZigZagConversion().convert("PAYPALISHIRING", 4));
	}

	public String convert(String s, int numRows) {
		StringBuilder[] sbs = new StringBuilder[numRows];
		IntStream.range(0, numRows).forEach(i -> sbs[i] = new StringBuilder());
		int rn = 0;
		int d = 1;
		for (char c : s.toCharArray()) {
			sbs[rn].append(c);
			if (numRows != 1) {
				if (rn == numRows - 1)
					d = -1;
				else if (rn == 0)
					d = 1;
				rn += d;
			}
		}
		return Arrays.stream(sbs).reduce((sb1, sb2) -> sb1.append(sb2)).get().toString();
	}
}
