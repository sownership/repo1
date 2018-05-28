package algorithm.sort;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		int arr[] = { 12, 11, 13, 5, 6, 7 };
		sort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}

	private static void sort(int[] arr, int low, int high) {
		if (high - low < 1) {
			return;
		}
		int pi = partition(arr, low, high);
		sort(arr, low, pi - 1);
		sort(arr, pi + 1, high);
	}

	private static int partition(int[] arr, int low, int high) {
		int j = low;
		for (int i = low; i < high; i++) {
			if (arr[i] < arr[high]) {
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				j++;
			}
		}
		int tmp = arr[high];
		arr[high] = arr[j];
		arr[j] = tmp;
		return j;
	}
}
