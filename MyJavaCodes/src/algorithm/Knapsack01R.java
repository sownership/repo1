package algorithm;

public class Knapsack01R {

	private static int k = 50;
	private static int v[] = { 60, 100, 120 };
	private static int w[] = { 10, 20, 30 };
	
	public static void main(String[] args) {

		System.out.println(r(v.length - 1, k));
	}

	private static int r(int n, int rk) {

		if (n < 0 || rk <= 0) {
			return 0;
		}

		int notuse = r(n - 1, rk);

		int use = 0;
		if (rk - w[n] >= 0) {
			use = r(n - 1, rk - w[n]) + v[n];
		}

		return Math.max(notuse, use);
	}
}
