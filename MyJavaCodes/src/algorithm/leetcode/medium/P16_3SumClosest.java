package algorithm.leetcode.medium;

import java.util.Arrays;

public class P16_3SumClosest {

	public static void main(String[] args) {
		// 2
		System.out.println(new P16_3SumClosest().threeSumClosest(new int[] { -1, 2, 1, -4 }, 1));
		// 0
		System.out.println(new P16_3SumClosest().threeSumClosest(new int[] { 0, 2, 1, -3 }, 1));
	}

	public int threeSumClosest(int[] nums, int target) {

		int a = -1;
		Arrays.sort(nums);
		int mind = Integer.MAX_VALUE;
		int n = nums.length;
		main: for (int i = 0; i < n; i++) {
			for (int j = i + 1, k = n - 1; j < k;) {
				int s = nums[i] + nums[j] + nums[k];
				int d = Math.abs(s - target);
				if (d < mind) {
					mind = d;
					a = s;
				}
				if (s == target) {
					a = s;
					break main;
				} else if (s > target) {
					k--;
				} else {
					j++;
				}
			}
		}
		return a;
	}
}
