package algorithm.pdf;

public class Nqueen {

	private static int[] ru;
	private static int[] rd;
	private static int[] c;

	public static void main(String[] args) {

		int n = 13;
		ru = new int[2 * n - 1];
		rd = new int[2 * n - 1];
		c = new int[n];

		recursive(n, 0);

		System.out.println(answer);
	}

	private static int answer;

	private static void recursive(int n, int r) {

		for (int j = 0; j < n; j++) {
			// c
			if (c[j] != 0) {
				continue;
			}
			// ru
			if (ru[r + j] != 0) {
				continue;
			}
			// rd
			if (rd[r - j + n - 1] != 0) {
				continue;
			}
			c[j] = 1;
			ru[r + j] = 1;
			rd[r - j + n - 1] = 1;

			if (r == n - 1) {
				// System.out.println(n + " " + r + " " + j);
				// System.out.println(Arrays.toString(c));
				// System.out.println(Arrays.toString(ru));
				// System.out.println(Arrays.toString(rd));
				answer++;
				c[j] = 0;
				ru[r + j] = 0;
				rd[r - j + n - 1] = 0;
				continue;
			}

			recursive(n, r + 1);
			c[j] = 0;
			ru[r + j] = 0;
			rd[r - j + n - 1] = 0;
		}
	}
}
