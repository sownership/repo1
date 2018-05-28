package algorithm.sort;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int arr[] = { 12, 11, 13, 5, 6, 7 };
		MergeSort ins = new MergeSort();
		ins.sort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}

	private void sort(int[] arr, int low, int high) {
		//System.out.println(Arrays.toString(arr) + ", " + low + ", " + high);
		if (high - low < 1) {
			return;
		}
		int m = (high + low) / 2;
		sort(arr, low, m);
		sort(arr, m + 1, high);
		merge(arr, low, m, high);
	}

	private void merge(int[] arr, int low, int m, int high) {
		//System.out.println(Arrays.toString(arr) + ", " + low + ", " + m + ", " + high);
		int l = low;
		int r = m + 1;
		int[] arr2 = new int[arr.length];
		int i = 0;
		while (l <= m && r <= high) {
			if (arr[l] > arr[r]) {
				arr2[i++] = arr[r++];
			} else {
				arr2[i++] = arr[l++];
			}
		}
		while (l <= m) {
			arr2[i++] = arr[l++];
		}
		while (r <= high) {
			arr2[i++] = arr[r++];
		}
		for (int j = low; j <= high; j++) {
			arr[j] = arr2[j - low];
		}
	}
}
