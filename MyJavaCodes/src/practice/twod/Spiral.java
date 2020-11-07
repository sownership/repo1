package practice.twod;

import java.util.Arrays;

public class Spiral {

	public static void main(String[] args) {

		int[][] pan = new int[5][5];

		int cy = 0;
		int cx = 0;
		int idx = 1;
		int direction = 0;
		int directionChangeCnt = 0;
		pan[cy][cx] = idx++;
		while (directionChangeCnt < 3) {
			if (direction == 0) {
				if (cx + 1 < pan[0].length && pan[cy][cx + 1] == 0) {
					pan[cy][++cx] = idx++;
					directionChangeCnt = 0;
				} else {
					direction = 1;
					directionChangeCnt++;
				}
			} else if (direction == 1) {
				if (cy + 1 < pan.length && pan[cy + 1][cx] == 0) {
					pan[++cy][cx] = idx++;
					directionChangeCnt = 0;
				} else {
					direction = 2;
					directionChangeCnt++;
				}
			} else if (direction == 2) {
				if (cx - 1 >= 0 && pan[cy][cx - 1] == 0) {
					pan[cy][--cx] = idx++;
					directionChangeCnt = 0;
				} else {
					direction = 3;
					directionChangeCnt++;
				}
			} else if (direction == 3) {
				if (cy - 1 >= 0 && pan[cy - 1][cx] == 0) {
					pan[--cy][cx] = idx++;
					directionChangeCnt = 0;
				} else {
					direction = 0;
					directionChangeCnt++;
				}
			}
		}

		for (int y = 0; y < pan.length; y++)
			System.out.println(Arrays.toString(pan[y]));

	}
}
