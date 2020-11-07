package algorithm.bj;

import java.util.Arrays;
import java.util.Scanner;

public class q_1920_ÀÌºÐÅ½»ö_ok {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] arr1 = new int[n];
		for (int i = 0; i < n; i++) {
			arr1[i] = scanner.nextInt();
		}
		int m = scanner.nextInt();
		int[] arr2 = new int[m];
		for (int i = 0; i < m; i++) {
			arr2[i] = scanner.nextInt();
		}

		Arrays.sort(arr1);
		for (int i = 0; i < m; i++) {
			int target = arr2[i];
			int s = 0;
			int e = arr1.length-1;
			boolean ok = false;
			while (e >= s) {
				int mid = (s + e) / 2;
				if (target > arr1[mid])
					s = mid + 1;
				else if (target == arr1[mid]) {
					ok = true;
					break;
				} else
					e = mid - 1;
			}
			if (ok)
				System.out.println("1");
			else
				System.out.println("0");
		}
	}
}
