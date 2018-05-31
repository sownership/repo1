package util;

import java.util.Arrays;

public class ArrayUtil {

	public static void main(String[] args) {
		int[] arr1 = new int[] { 1, 2, 3, 4, 5 };
		int[] arr2 = new int[] { 6, 7, 8 };
		System.out.println(Arrays.toString(insert(arr1, arr2, 3)));
		System.out.println(Arrays.toString(delete(arr1, 2, 3)));
	}

	private static int[] insert(int[] arr1, int[] arr2, int idx) {
		int[] newArr = new int[arr1.length + arr2.length];
		System.arraycopy(arr1, 0, newArr, 0, idx);
		System.arraycopy(arr2, 0, newArr, idx, arr2.length);
		System.arraycopy(arr1, idx, newArr, idx + arr2.length, arr1.length - idx);

		return newArr;
	}

	private static int[] delete(int[] arr, int s, int e) {
		int[] newArr = new int[arr.length - (e - s + 1)];
		System.arraycopy(arr, 0, newArr, 0, s);
		System.arraycopy(arr, e + 1, newArr, s, newArr.length - s);

		return newArr;
	}
}
