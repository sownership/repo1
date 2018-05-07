package algorithm.knapsack;

public class KnapsackUR {

	private static int k = 8;
	private static int[] w = { 1, 3, 4, 5 };
	private static int[] v = { 10, 40, 50, 70 };

	public static void main(String[] args) {

		System.out.println(r(w.length - 1, k));
	}

	private static int r(int n, int rk) {

		if (n < 0 || rk <= 0) {
			return 0;
		}

		int notuse = r(n - 1, rk);

		int use = 0;
		if (rk - w[n] >= 0) {
			use = r(n, rk - w[n]) + v[n];
		}

		return Math.max(notuse, use);
	}
}
