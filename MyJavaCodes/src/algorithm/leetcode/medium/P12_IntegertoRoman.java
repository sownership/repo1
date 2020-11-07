package algorithm.leetcode.medium;

public class P12_IntegertoRoman {

	public static void main(String[] args) {
		// MCMXCIV
		System.out.println(new P12_IntegertoRoman().intToRoman(1994));
	}

	public String intToRoman(int num) {
		String r = "";

		String[] d1 = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
		String[] d2 = { "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
		String[] d3 = { "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
		String[] d4 = { "M", "MM", "MMM" };

		if (num % 10 > 0)
			r = d1[num % 10 - 1] + r;

		if (num >= 10 && num / 10 % 10 > 0)
			r = d2[num / 10 % 10 - 1] + r;

		if (num >= 100 && num / 100 % 10 > 0)
			r = d3[num / 100 % 10 - 1] + r;

		if (num >= 1000)
			r = d4[num / 1000 - 1] + r;

		return r;
	}
}
