package algorithm.sort;

import java.util.Arrays;

public class SquaresSort {

	public static void main(String[] args) {
		int[] arr = new int[] { -6, -3, -1, 2, 4, 5 };
		sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void sort(int[] arr) {
		int ridx = 0;
		while (ridx < arr.length && arr[ridx] < 0) {
			ridx++;
		}
		int[] tmp = new int[arr.length];
		int tidx = 0;
		int lidx = ridx - 1;
		while (lidx >= 0 && ridx < arr.length) {
			if (arr[lidx] * arr[lidx] > arr[ridx] * arr[ridx]) {
				tmp[tidx] = arr[ridx] * arr[ridx];
				ridx++;
			} else {
				tmp[tidx] = arr[lidx] * arr[lidx];
				lidx--;
			}
			tidx++;
		}
		while (lidx >= 0) {
			tmp[tidx++] = arr[lidx] * arr[lidx];
			lidx--;
		}
		while (ridx < arr.length) {
			tmp[tidx++] = arr[ridx] * arr[ridx];
			ridx++;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = tmp[i];
		}
	}
}
