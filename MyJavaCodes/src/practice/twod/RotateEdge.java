package practice.twod;

import java.util.Arrays;

public class RotateEdge {

	public static void main(String[] args) {

		int[][] pan = new int[5][5];
		for (int y = 0; y < pan.length; y++) {
			for (int x = 0; x < pan[0].length; x++) {
				pan[y][x] = y * pan[0].length + x;
			}
		}

		int ys = 1;
		int xs = 1;
		int ye = 3;
		int xe = 3;

		for (int y = 0; y < pan.length; y++)
			System.out.println(Arrays.toString(pan[y]));
		System.out.println("-----------------");

		int temp1 = pan[ys][xe];
		for (int x = xe; x > xs; x--) {
			pan[ys][x] = pan[ys][x - 1];
		}

		int temp2 = pan[ye][xe];
		for (int y = ye; y > ys + 1; y--) {
			pan[y][xe] = pan[y - 1][xe];
		}
		pan[ys + 1][xe] = temp1;

		int temp3 = pan[ye][xs];
		for (int x = xs; x < xe - 1; x++) {
			pan[ye][x] = pan[ye][x + 1];
		}
		pan[ye][xe - 1] = temp2;

		for (int y = ys; y < ye - 1; y++) {
			pan[y][xs] = pan[y + 1][xs];
		}
		pan[ye - 1][xs] = temp3;

		for (int y = 0; y < pan.length; y++)
			System.out.println(Arrays.toString(pan[y]));
		System.out.println("-----------------");
	}
}
