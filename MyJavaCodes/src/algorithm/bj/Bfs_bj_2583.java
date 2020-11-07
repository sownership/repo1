package algorithm.bj;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Bfs_bj_2583 {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		int m = scanner.nextInt();
		int n = scanner.nextInt();
		int k = scanner.nextInt();

		int[][] pan = new int[m][n];
		for (int i = 0; i < k; i++) {
			int x1 = scanner.nextInt();
			int y1 = scanner.nextInt();
			int x2 = scanner.nextInt();
			int y2 = scanner.nextInt();
			for (int y = Math.min(y1, y2); y < Math.max(y1, y2); y++) {
				for (int x = Math.min(x1, x2); x < Math.max(x1, x2); x++) {
					pan[y][x] = 1;
				}
			}
		}

//		for(int i=0;i<m;i++) System.out.println(Arrays.toString(pan[i]));
//		System.out.println("----------------------");

		List<Integer> r = new ArrayList<>();

		int[] xd = new int[] { 1, -1, 0, 0 };
		int[] yd = new int[] { 0, 0, 1, -1 };

		int idx = 2;
		for (int y = 0; y < m; y++) {
			for (int x = 0; x < n; x++) {
				if (pan[y][x] != 0)
					continue;
				int cntForIdx = 0;
				Queue<Point> q = new LinkedList<>();
				q.offer(new Point(x, y));
				while (!q.isEmpty()) {
					Point p = q.poll();
					if (pan[p.y][p.x] != 0)
						continue;
					pan[p.y][p.x] = idx;
					cntForIdx++;
					for (int d = 0; d < 4; d++) {
						int newY = p.y + yd[d];
						int newX = p.x + xd[d];
						if (newX >= 0 && newY >= 0 && newX < n && newY < m && pan[newY][newX] == 0) {
							q.offer(new Point(newX, newY));
						}
					}
				}
//				for(int i=0;i<m;i++) System.out.println(Arrays.toString(pan[i]));
//				System.out.println("----------------------");

				r.add(cntForIdx);
				idx++;
			}
		}

		System.out.println(idx - 2);
		if (r.size() > 0) {
			r.sort((r1, r2) -> r1 - r2);
			System.out.print(r.get(0));
			for (int i = 1; i < r.size(); i++) {
				System.out.print(" " + r.get(i));
			}
		}
	}
}
