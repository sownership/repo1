package algorithm.search;

import java.util.Arrays;

public class ExponentialSearch {

	public static void main(String[] args) {
		ExponentialSearch ins = new ExponentialSearch();

		int arr[] = { 2, 3, 4, 10, 40 };
		System.out.println(search(arr, arr.length, 10));
	}

	private static int search(int[] arr, int n, int x) {

		if (arr[0] == x) {
			return 0;
		}

		int idx = 1;
		while (idx < n && arr[idx] <= x) {
			idx *= 2;
		}

		return Arrays.binarySearch(arr, idx / 2, idx, x);
	}
}
