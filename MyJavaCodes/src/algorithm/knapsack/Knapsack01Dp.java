package algorithm.knapsack;

public class Knapsack01Dp {

	private static int k = 50;
	private static int v[] = { 60, 100, 120 };
	private static int w[] = { 10, 20, 30 };

	public static void main(String[] args) {

		System.out.println(knapsack01(v.length, k));
	}

	private static int knapsack01(int n, int k) {

		int dp[][] = new int[n][k + 1];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= k; j++) {

				int notuse = i >= 1 ? dp[i - 1][j] : 0;
				int use = (i >= 1 && j >= w[i]) ? dp[i - 1][j - w[i]] + v[i] : 0;
				dp[i][j] = Math.max(notuse, use);
			}
		}

		return dp[n - 1][k];
	}
}
