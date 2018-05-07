package algorithm.knapsack;

public class KnapsackUDp {

	private static int k = 8;
	private static int[] w = { 1, 3, 4, 5 };
	private static int[] v = { 10, 40, 50, 70 };

	public static void main(String[] args) {

		long pt = System.currentTimeMillis();
		System.out.println(knapsackU(w.length, k));
		System.out.println(System.currentTimeMillis() - pt);
	}

	private static int knapsackU(int n, int k) {

		int dp[] = new int[k + 1];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= k; j++) {

				int notuse = dp[j];
				int use = (j >= w[i]) ? dp[j - w[i]] + v[i] : 0;
				dp[j] = Math.max(notuse, use);
			}
		}

		return dp[k];
	}
}
