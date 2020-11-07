package algorithm;

import java.util.Arrays;

public class SegmentTree {

	public static void main(String[] args) {

		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		int[] st = new int[getStSize(arr)];

		init(st, arr, 0, 0, arr.length - 1);

		System.out.println(Arrays.toString(st));
		System.out.println(getSum(st, 3, 7, 0, 0, arr.length - 1));
		update(st, 7, -3, 0, 0, arr.length - 1);
		System.out.println(getSum(st, 3, 7, 0, 0, arr.length - 1));
	}

	private static void update(int[] st, int target, int v, int current, int s, int e) {
		if (target < s || e < target)
			return;
		st[current] += v;
		if(s==e) return;
		int m = (s + e) / 2;
		update(st, target, v, current * 2 + 1, s, m);
		update(st, target, v, current * 2 + 2, m + 1, e);
	}

	private static int getSum(int[] st, int qs, int qe, int current, int s, int e) {
		if (e < qs || qe < s)
			return 0;
		if (qs <= s && e <= qe)
			return st[current];
		int m = (s + e) / 2;
		return getSum(st, qs, qe, 2 * current + 1, s, m) + getSum(st, qs, qe, 2 * current + 2, m + 1, e);
	}

	private static int init(int[] st, int[] arr, int current, int s, int e) {
		if (s == e) {
			st[current] = arr[s];
		} else {
			int m = (s + e) / 2;
			st[current] = init(st, arr, 2 * current + 1, s, m) + init(st, arr, 2 * current + 2, m + 1, e);
		}
		return st[current];
	}

	private static int getStSize(int[] arr) {
		int h = (int) (Math.ceil(Math.log(arr.length) / Math.log(2)));
		return 2 * (int) Math.pow(2, h) - 1;
	}
	
//	private static int getTreeSize(int[] arr) {
//		int size=0;
//		int n=0;
//		while(true) {
//			int temp=(int)Math.pow(2, n++);
//			size+=temp;
//			if(temp>=arr.length) {
//				break;
//			}
//		}
//		return size;
//	}
}
