package algorithm.knapsack;

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

	private static Stack<MyE> s = new Stack<>();

	private static int s(int n, int k) {

		s.push(new MyE(n, k));

		while (!s.isEmpty()) {
			MyE e = s.pop();

			if (needPushLeft(e)) {
				pushLeft(e);
			} else if (needPushRight(e)) {
				pushRight(e);
			} else {
				cal(e);
			}
		}
		return dp.get(n).get(k);
	}

	private static void cal(MyE e) {
		if (e.n == 0) {
			if (e.k - w[0] >= 0) {
				putDp(0, e.k, v[0]);
			} else {
				putDp(0, e.k, 0);
			}
		} else {
			int rv = e.k >= w[e.n] ? getDp(e.n - 1, e.k - w[e.n]) + v[e.n] : 0;
			putDp(e.n, e.k, Math.max(getDp(e.n - 1, e.k), rv));
		}
	}

	private static void pushRight(MyE e) {
		s.push(e);
		s.push(new MyE(e.n - 1, e.k - w[e.n]));
	}

	private static void pushLeft(MyE e) {
		s.push(e);
		int curN = e.n;
		while (--curN >= 0) {
			s.push(new MyE(curN, e.k));
		}
	}

	private static boolean needPushRight(MyE e) {
		if (e.n <= 0) {
			return false;
		}
		if (e.k - w[e.n] < 0) {
			return false;
		}
		if (getDp(e.n - 1, e.k - w[e.n]) != -1) {
			return false;
		}
		return true;
	}

	private static boolean needPushLeft(MyE e) {
		if (e.n <= 0) {
			return false;
		}
		if (getDp(e.n - 1, e.k) != -1) {
			return false;
		}
		return true;
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
		Map<Integer, Integer> m = dp.get(n);
		if (m == null) {
			return -1;
		}
		Integer lv = m.get(k);
		return lv == null ? -1 : lv;
	}
}
