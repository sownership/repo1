package algorithm.leetcode.medium;

public class P8_StringtoInteger_atoi {

	public static void main(String[] args) {
		// 42
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("42"));
		// -42
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("   -42"));
		// 4193
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("4193 with words"));
		// 0
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("words and 987"));
		// -2147483648
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("-91283472332"));
		// 12345678
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("  0000000000012345678"));
		// 0
		System.out.println(new P8_StringtoInteger_atoi().myAtoi("0-1"));

	}

	public int myAtoi(String str) {

		int i = 0, j;

		while (i < str.length() && str.charAt(i) == ' ')
			i++;
		if (i == str.length())
			return 0;

		char c = str.charAt(i);
		if (c == '+' || c == '-')
			i++;
		boolean minus = c == '-';
		if (i == str.length())
			return 0;

		c = str.charAt(i);
		if (c - '0' < 0 || c - '0' > 9)
			return 0;

		boolean zero = false;
		if (c == '0') {
			zero = true;
			while (i < str.length() && str.charAt(i) == '0')
				i++;
		}
		if (zero && (i == str.length() || str.charAt(i) - '0' < 0 || str.charAt(i) - '0' > 9))
			return 0;

		j = i;
		while (j < str.length() && str.charAt(j) - '0' >= 0 && str.charAt(j) - '0' <= 9)
			j++;

		String n = str.substring(i, j);
		if (n.length() > 10) {
			if (minus)
				return Integer.MIN_VALUE;
			else
				return Integer.MAX_VALUE;
		} else if (n.length() == 10) {
			if (minus) {
				if (n.compareTo(String.valueOf(Integer.MIN_VALUE).substring(1)) > 0)
					return Integer.MIN_VALUE;
				else
					return Integer.valueOf('-' + n);
			} else {
				if (n.compareTo(String.valueOf(Integer.MAX_VALUE)) > 0)
					return Integer.MAX_VALUE;
				else
					return Integer.valueOf(n);
			}
		} else {
			if (minus) {
				return Integer.valueOf('-' + n);
			} else {
				return Integer.valueOf(n);
			}
		}
	}
}
