package algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P18_4Sum {

	public static void main(String[] args) {
		// [
		// [-1, 0, 0, 1],
		// [-2, -1, 1, 2],
		// [-2, 0, 0, 2]
		// ]
		System.out.println(new P18_4Sum().fourSum(new int[] { 1, 0, -1, 0, -2, 2 }, 0));
	}

	public List<List<Integer>> fourSum(int[] nums, int target) {

		Arrays.sort(nums);
		int n = nums.length;
		return r(nums, n, target, 0, 4);
	}

	private List<List<Integer>> r(int[] nums, int n, int target, int s, int k) {
		List<List<Integer>> r = new ArrayList<>();
		if (k == 2) {
			return twosum(nums, n, target, s);
		} else {
			for (int i = s; i < n; i++) {
				if (i == s || nums[i] > nums[i - 1])
					for (List<Integer> l : r(nums, n, target - nums[i], i + 1, k - 1)) {
						r.add(new ArrayList<>(Arrays.asList(nums[i])));
						r.get(r.size() - 1).addAll(l);
					}
			}
		}
		return r;
	}

	private List<List<Integer>> twosum(int[] nums, int n, int target, int s) {
		List<List<Integer>> r = new ArrayList<>();
		int i = s, j = n - 1;
		while (i < j) {
			int c = nums[i] + nums[j];
			if (c > target)
				j--;
			else if (c < target)
				i++;
			else {
				r.add(Arrays.asList(nums[i], nums[j]));
				while (j > 0 && nums[j] == nums[j-- - 1])
					;
				while (i + 1 < n && nums[i] == nums[i++ + 1])
					;
			}
		}
		return r;
	}
}
