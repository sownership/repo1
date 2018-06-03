package algorithm.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Pang2 {

	public static void main(String[] args) {
		//int[] in = new int[] { 1, 2, 2, 1, 2, 3, 1, 3, 3, 1 };
		int[] in = new int[] { 1, 2, 2, 1, 1, 3, 1, 3, 3, 1 , 1, 2, 2, 2, 2, 3, 1, 3, 3, 1 , 1, 2, 2, 1, 2, 3, 1, 3, 3, 1 };
		System.out.println(solve(in));
	}

	private static int solve(int[] in) {
		System.out.println(Arrays.toString(in));
		int result = 0;
		Map<Integer, Integer> m = new HashMap<>();
		for (int i = 0; i < in.length; i++) {
			Integer v = m.get(in[i]);
			if (v == null) {
				m.put(in[i], 1);
			} else {
				m.put(in[i], ++v);
			}
		}
		int maxV = -1;
		int maxK = -1;
		for (Entry<Integer, Integer> e : m.entrySet()) {
			if (e.getValue() > maxV) {
				maxV = e.getValue();
				maxK = e.getKey();
			}
		}
		int s = -1;
		int e = -1;
		while (true) {
			boolean bs = false;
			for (int i = e + 1; i < in.length; i++) {
				if (in[i] == maxK) {
					if (bs && i >= 1) {
						break;
					}
				} else {
					if (!bs) {
						bs = true;
						s = i;
					}
					e = i;
				}
			}
			if (bs) {
				result += solve(Arrays.copyOfRange(in, s, e + 1));
			} else {
				break;
			}
		}
		result += Math.pow(maxV, 2);
		return result;
	}
}
