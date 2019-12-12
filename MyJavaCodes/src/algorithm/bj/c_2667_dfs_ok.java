package algorithm.bj;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class c_2667_dfs_ok {

	private static Scanner scanner = new Scanner(System.in);

	private static class XY {
		int x;
		int y;
		public XY(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		int n = scanner.nextInt();
		scanner.nextLine();
		int[][] map = new int[n][n];
		for (int i = 0; i < n; i++) {
			String line = scanner.nextLine();
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}

//		for (int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}

		int[] xs = { -1, 1, 0, 0 };
		int[] ys = { 0, 0, -1, 1 };
		int[][] stacked = new int[n][n];
		int turn = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == 1 && stacked[i][j] == 0) {
					turn++;

					Stack<XY> stack = new Stack<>();
					stack.push(new XY(i, j));
					stacked[i][j] = turn;
					while (!stack.isEmpty()) {
						XY xy = stack.pop();
						for (int k = 0; k < 4; k++) {
							int newX = xy.x + xs[k];
							int newY = xy.y + ys[k];
							if (newX >= 0 && newX < n && newY >= 0 && newY < n && map[newX][newY] == 1
									&& stacked[newX][newY] == 0) {
								stack.push(new XY(newX, newY));
								stacked[newX][newY] = turn;
							}
						}
					}
				}
			}
		}

		int[] results = new int[turn];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (stacked[i][j] > 0) {
					results[stacked[i][j]-1]++;
				}
			}
		}

		System.out.println(turn);
		Arrays.sort(results);
		Arrays.stream(results).forEach(System.out::println);
	}
}
