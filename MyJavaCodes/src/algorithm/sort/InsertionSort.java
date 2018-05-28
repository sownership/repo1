package algorithm.sort;

import java.util.Arrays;

public class InsertionSort {

	public static void main(String[] args) {

		int arr[] = { 12, 11, 13, 5, 6 };
		InsertionSort ins = new InsertionSort();
		ins.sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	private void sort(int[] arr) {

		for (int i = 0; i < arr.length; i++) {
			int key = arr[i];
			int idx = i;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] > key) {
					arr[j + 1] = arr[j];
					idx = j;
				} else {
					break;
				}
			}
			arr[idx] = key;
		}
	}
}
