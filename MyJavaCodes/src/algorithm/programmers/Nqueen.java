package algorithm.programmers;

public class Nqueen {

	private static int[] ltrb;
	private static int[] rtlb;
	private static int[] col;
	private static int len;
	private static int answer;

	public static void main(String[] args) {

		getCase(13);
		System.out.println(answer);
	}

	private static void getCase(int c) {

		col = new int[c];
		ltrb = new int[2 * c];
		rtlb = new int[2 * c - 1];
		len = c;

		dfs(0);
	}

	private static void dfs(int r) {

		if (r >= len) {
			answer++;
		}
		for (int i = 0; i < len; i++) {

			if (col[i] == 0 && ltrb[i - r + len] == 0 && rtlb[i + r] == 0) {
				col[i] = 1;
				ltrb[i - r + len] = 1;
				rtlb[i + r] = 1;
				dfs(r + 1);
				col[i] = 0;
				ltrb[i - r + len] = 0;
				rtlb[i + r] = 0;
			}
		}
	}
}
