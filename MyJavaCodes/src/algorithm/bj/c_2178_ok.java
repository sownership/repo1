package algorithm.bj;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class c_2178_ok {

	private static Scanner scanner = new Scanner(System.in);

	static class XY {
		int x;
		int y;
		int cnt;

		public XY(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}

	public static void main(String[] args) {

		// input
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int[][] map = new int[n][m];

		scanner.nextLine();
		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}

//		for (int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}

		int[] xs = { -1, 1, 0, 0 };
		int[] ys = { 0, 0, -1, 1 };
		int[][] qed = new int[n][m];

		// bfs
		Queue<XY> q = new LinkedList<>();
		q.offer(new XY(0, 0, 1));
		while (!q.isEmpty()) {
			XY xy = q.poll();
			if (xy.x == n - 1 && xy.y == m - 1) {
				System.out.println(xy.cnt);
				return;
			}
			for (int i = 0; i < 4; i++) {
				int newX = xy.x + xs[i];
				int newY = xy.y + ys[i];
				if (newX >= 0 && newX < n && newY >= 0 && newY < m && map[newX][newY] == 1
						&& qed[newX][newY] == 0) {
					q.offer(new XY(newX, newY, xy.cnt + 1));
					qed[newX][newY] = 1;
				}
			}
		}
	}
}
