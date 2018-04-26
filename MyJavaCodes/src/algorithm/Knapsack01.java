package algorithm;

public class Knapsack01 {

	private static int t = 50;
	private static int v[] = { 60, 100, 120 };
	private static int w[] = { 10, 20, 30 };

	public static void main(String[] args) {

		System.out.println(r(v.length - 1, t));
	}

	/**
	 * @param i
	 *            item index
	 * @param t
	 *            remained weight
	 * @return value
	 */
	private static int r(int i, int t) {

		if (i < 0 || t <= 0) {
			return 0;
		}

		int notuse = r(i - 1, t);

		int use = 0;
		if (t >= w[i]) {
			use = r(i - 1, t - w[i]) + v[i];
		}

		return Math.max(notuse, use);
	}
}
