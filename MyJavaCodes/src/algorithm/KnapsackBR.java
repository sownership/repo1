package algorithm;

public class KnapsackBR {

	private static int k = 1000;
	private static int[] w = { 39, 27, 52, 30, 32, 23, 15, 13, 48, 9, 22, 50, 43, 42, 4, 7, 11, 24, 68, 18, 73, 153 };
	private static int[] v = { 40, 60, 10, 10, 30, 30, 60, 35, 10, 150, 80, 160, 75, 70, 50, 20, 70, 15, 45, 12, 40,
			200 };
	private static int[] q = { 4, 4, 12, 2, 1, 4, 10, 1, 1, 1, 1, 4, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1 };

	private static int[] uc;

	public static void main(String[] args) {

		uc = new int[w.length];

		System.out.println(r(w.length - 1, k));

		for (int i : uc) {
			System.out.print(i + " ");
		}
	}

	private static int r(int n, int rk) {

		if (n < 0 || rk <= 0) {
			return 0;
		}

		int notuse = r(n - 1, rk);

		int tryuse = 0;
		if (rk - w[n] >= 0 && uc[n] < q[n]) {
			uc[n]++;
			tryuse = r(n, rk - w[n]) + v[n];
			uc[n]--;
		}

		return Math.max(notuse, tryuse);
	}
}
