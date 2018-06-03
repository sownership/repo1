package algorithm.search;

import java.util.Arrays;
import java.util.Scanner;

public class MoleDfs {

	public static void main(String[] args) {

		int cellLength = input();

		solve(cellLength);

		print();
	}

	private static void print() {
		if(cnt<2) {
			System.out.println(0);
			return;
		}
		int[] r = new int[cnt - 1];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j] != 0) {
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

	private static int cnt = 1;

	private static void solve(int cellLength) {
		for (int i = 0; i < cellLength; i++) {
			for (int j = 0; j < cellLength; j++) {
				if (cells[i][j] == 1) {
					cnt++;
					dfs(i, j);
				}
			}
		}
	}

	private static void dfs(int i, int j) {
		if (i < 0 || i >= cells.length || j < 0 || j >= cells.length) {
			return;
		}
		if (cells[i][j] == 1) {
			cells[i][j] = cnt;
		} else {
			return;
		}
		// left
		dfs(i, j - 1);
		// down
		dfs(i + 1, j);
		// right
		dfs(i, j + 1);
		// up
		dfs(i - 1, j);
	}

	private static Scanner scanner = new Scanner(System.in);
	private static int[][] cells;

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
