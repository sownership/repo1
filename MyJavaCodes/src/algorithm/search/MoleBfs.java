package algorithm.search;

import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class MoleBfs {

	public static void main(String[] args) {

		int cellLength = input();

		solve(cellLength);

		print();
	}

	private static void print() {
		if (cnt < 2) {
			System.out.println(0);
			return;
		}
		int[] r = new int[cnt - 1];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j] > 1) {
					r[cells[i][j] - 2]++;
				}
			}
		}
		Arrays.sort(r);
		System.out.println(r.length);
		for (int i = r.length - 1; i >= 0; i--) {
			System.out.println(r[i]);
		}
	}

	private static void solve(int cellLength) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j] != 1) {
					continue;
				}
				cnt++;
				q.offer(new Point(i, j));
				bfs();
			}
		}
	}

	private static void bfs() {
		Point p;
		while ((p = q.poll()) != null) {
			int i = p.r;
			int j = p.c;
			if (cells[i][j] != 1) {
				continue;
			}
			cells[i][j] = cnt;
			// left
			if (isIn(i, j - 1))
				q.offer(new Point(i, j - 1));
			// down
			if (isIn(i + 1, j))
				q.offer(new Point(i + 1, j));
			// right
			if (isIn(i, j + 1))
				q.offer(new Point(i, j + 1));
			// up
			if (isIn(i - 1, j))
				q.offer(new Point(i - 1, j));
		}
	}

	private static boolean isIn(int i, int j) {
		if (i < 0 || i >= cells.length || j < 0 || j >= cells.length) {
			return false;
		}
		return true;
	}

	private static class Point {
		private int r;
		private int c;

		Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	private static Scanner scanner = new Scanner(System.in);
	private static int[][] cells;
	private static int cnt = 1;
	private static Queue<Point> q = new LinkedBlockingQueue<>();

	private static int input() {
		int cellLength = Integer.valueOf(scanner.nextLine());
		cells = new int[cellLength][cellLength];
		for (int i = 0; i < cellLength; i++) {
			String[] row = scanner.nextLine().split(" ");
			for (int j = 0; j < cellLength; j++) {
				cells[i][j] = Integer.valueOf(row[j]);
			}
		}
		return cellLength;
	}
}
