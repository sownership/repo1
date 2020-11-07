package algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P15_3Sum {

	public static void main(String[] args) {
//		System.out.println(new P15_3Sum().threeSum(new int[] { -1, 0, 1, 2, -1, -4 }));
		System.out
				.println(new P15_3Sum().threeSum(new int[] { -4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0 }));
	}

	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> r = new ArrayList<>();
		int n = nums.length;
		if (n < 3)
			return r;
		Arrays.sort(nums);
		for (int i = 0; i < n && nums[i] <= 0; i++) {
			if (nums[i] + nums[n - 2] + nums[n - 1] < 0)
				continue;
			for (int j = i + 1, k = n - 1; j < k;) {
				int c = nums[i] + nums[j] + nums[k];
				if (c == 0) {
					while (j + 1 < k && nums[j] == nums[j + 1])
						j++;
					while (j < k - 1 && nums[k] == nums[k - 1])
						k--;
					r.add(Arrays.asList(nums[i], nums[j++], nums[k--]));
				} else if (c > 0)
					k--;
				else
					j++;
			}
			while (i < n - 2 && nums[i] == nums[i + 1])
				i++;
		}

		return r;
	}
}
