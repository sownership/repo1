package practice.twod;

import java.util.Arrays;

public class RotatePartial {

	public static void main(String[] args) {

		int n = 5;
		int[][] pan = new int[n][n];
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				pan[y][x] = n * y + x;
			}
		}

		// copy pan
		int[][] pan2 = new int[n][n];
		for (int y = 0; y < n; y++) {
			System.arraycopy(pan[y], 0, pan2[y], 0, n);
		}
		for (int y = 0; y < n; y++)
			System.out.println(Arrays.toString(pan2[y]));
		System.out.println("---------------------");

		int x1 = 1;
		int y1 = 1;
		int x2 = 3;
		int y2 = 3;
		for (int y = y1, k1 = 0; y <= y2; y++, k1++) {
			for (int x = x1, k2 = 0; x <= x2; x++, k2++) {
				pan2[y1 + k2][x2 - k1] = pan[y][x];
			}
		}

		for (int y = 0; y < n; y++)
			System.out.println(Arrays.toString(pan2[y]));
		System.out.println("---------------------");
	}
}
