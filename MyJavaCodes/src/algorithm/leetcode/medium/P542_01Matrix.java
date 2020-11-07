package algorithm.leetcode.medium;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class P542_01Matrix {

	// 8:45~
	public static void main(String[] args) throws IOException {
//		int[][] matrix = getMatrix();
//		int[][] matrix = {{1}};
		int[][] matrix = { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } };
		int[][] r = new P542_01Matrix().updateMatrix(matrix);
		Arrays.stream(r).forEach(re -> System.out.println(Arrays.toString(re)));
	}

	private static int[][] getMatrix() throws IOException {
		String[] sa = Files.readAllLines(Paths.get("d:\\temp\\input")).get(0).replace("{", "").replace("}", "")
				.split(",");
		int[][] r = new int[sa.length][1];
		for (int y = 0; y < sa.length; y++)
			r[y][0] = Integer.valueOf(sa[y]);
		return r;
	}

	public int[][] updateMatrix(int[][] matrix) {
		int[][] d = new int[matrix.length][matrix[0].length];

		int[] xd = { -1, 1, 0, 0 };
		int[] yd = { 0, 0, -1, 1 };

		Queue<Point> q = new LinkedList<>();

		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				if (matrix[y][x] == 0) {
					d[y][x] = 0;
					q.offer(new Point(x, y));
				}
			}
		}
		while (!q.isEmpty()) {
			Point cp = q.poll();
			for (int k = 0; k < 4; k++) {
				int ny = cp.y + yd[k];
				int nx = cp.x + xd[k];
				if (ny >= 0 && ny < matrix.length && nx >= 0 && nx < matrix[0].length && matrix[ny][nx] == 1
						&& d[ny][nx] == 0) {
					q.offer(new Point(nx, ny));
					d[ny][nx] = d[cp.y][cp.x] + 1;
				}
			}
		}
		return d;
	}
}
