package algorithm.leetcode.medium;

public class P11_ContainerWithMostWater {

	public static void main(String[] args) {
		// 49
		System.out.println(new P11_ContainerWithMostWater().maxArea(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
		// 17
		System.out.println(new P11_ContainerWithMostWater().maxArea(new int[] { 2, 3, 4, 5, 18, 17, 6 }));
	}

	public int maxArea(int[] height) {

		int a = 0, l = 0, r = height.length - 1;
		while (l < r) {
			a = Math.max(a, Math.min(height[l], height[r]) * (r - l));
			if (height[r] > height[l])
				l++;
			else
				r--;
		}
		return a;
	}
}
