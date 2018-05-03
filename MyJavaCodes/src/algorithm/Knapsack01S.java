package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Knapsack01S {

	private static int k = 50;
	private static int v[] = { 60, 100, 120 };
	private static int w[] = { 10, 20, 30 };

	public static void main(String[] args) {
		System.out.println(s(v.length - 1, k));
	}

	private static class MyE {
		public MyE(int n, int k) {
			this.n = n;
			this.k = k;
		}

		private int n;
		private int k;
	}

	private static Map<Integer, Map<Integer, Integer>> dp = new HashMap<>();

	private static int s(int n, int k) {

		Stack<MyE> s = new Stack<>();
		s.push(new MyE(n, k));

		while (!s.isEmpty()) {
			MyE e = s.pop();
			int lChildV = getDp(e.n, e.k);
			int rChildV = getDp(e.n, e.k - w[e.n]);

			if (lChildV == -1) {
				int thisN = e.n;
				while (thisN > 0) {
					s.push(new MyE(thisN--, e.k));
				}
				if (e.k - w[0] >= 0) {
					putDp(thisN, e.k, v[thisN]);
				} else {
					putDp(thisN, e.k, 0);
				}
			} else if (rChildV == -1) {
				s.push(e);
				if (e.n > 1) {
					if (e.k - w[e.n] >= 0) {
						s.push(new MyE(e.n - 1, e.k - w[e.n]));
					} else {
						putDp(e.n - 1, e.k - w[1], 0);
					}
				} else {
					if (e.k - w[1] - w[0] >= 0) {
						putDp(0, e.k - w[1], v[0]);
					} else {
						putDp(0, e.k - w[1], 0);
					}
				}
			} else {
				putDp(e.n, e.k, Math.max(lChildV, v[e.n] + rChildV));
			}
		}
		return dp.get(n).get(k);
	}

	private static void putDp(int n, int k, int v) {
		Map<Integer, Integer> m = dp.get(n);
		if (m == null) {
			m = new HashMap<>();
			dp.put(n, m);
		}
		m.put(k, v);
	}

	private static int getDp(int n, int k) {
		Map<Integer, Integer> m = dp.get(n - 1);
		if (m == null) {
			return -1;
		}
		Integer lv = m.get(k);
		return lv == null ? -1 : lv;
	}
}
