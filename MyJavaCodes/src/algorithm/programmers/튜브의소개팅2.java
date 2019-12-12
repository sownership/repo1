package algorithm.programmers;

import java.util.Arrays;

public class 튜브의소개팅2 {

	public static void main(String[] args) {
//		int m = 3;
//		int n = 3;
//		int s = 150;
//		int[][] time_map = new int[][] { { 0, 2, 99 }, { 100, 100, 4 }, { 1, 2, 0 } };

		int m = 4;
		int n = 6;
		int s = 25;
		int[][] time_map = { { 0, 1, 1, -1, 2, 4 }, { -1, 7, 2, 1, 5, 7 }, { -1, 1, -1, 1, 6, 3 },
				{ -1, 1, -1, -1, 7, 0 } };

//		int m = 5;
//		int n = 5;
//		int s = 12;
//		int[][] time_map = { { 0, 1, 1, 1, 1 }, { 9, 9, 9, 1, 9 }, { 1, 1, 1, 1, 9 }, { 1, 1, 5, 9, 9 },
//				{ 1, 1, 1, 1, 0 } };

		System.out.println(Arrays.toString(new 튜브의소개팅2().solution(m, n, s, time_map)));
	}

	public int[] solution(int m, int n, int s, int[][] time_map) {

		int moveDistance = 0;
		int sumOfTalkTime = 0;

		int[][][] dp = new int[m][n][m * n];
		for (int[][] dp1 : dp) {
			for (int[] dp2 : dp1) {
				Arrays.fill(dp2, Integer.MAX_VALUE);
			}
		}
		dp[0][0][0] = 0;

		int[] h = { 0, 0, -1, 1 };
		int[] v = { -1, 1, 0, 0 };

		for (int i = 0; i < m * n - 1; i++) {
			for (int x = 0; x < m; x++) {
				for (int y = 0; y < n; y++) {
					if (dp[x][y][i] == Integer.MAX_VALUE) {
						continue;
					}
					int tempS = dp[x][y][i] + time_map[x][y];

					for (int k = 0; k < 4; k++) {
						int newX = x + v[k];
						int newY = y + h[k];
						if (newX >= 0 && newY >= 0 && newX < m && newY < n && time_map[newX][newY] != -1 && tempS <= s
								&& tempS < dp[newX][newY][i + 1]) {
							dp[newX][newY][i + 1] = tempS;
//							System.out.printf("i=%d, x=%d, y=%d, newX=%d, newY=%d, tempS=%d\n", i, x, y, newX, newY,
//									tempS);
						}
					}
				}
			}
		}

		for (int i = 0; i < m * n; i++) {
			if (dp[m - 1][n - 1][i] != Integer.MAX_VALUE) {
				moveDistance = i;
				sumOfTalkTime = dp[m - 1][n - 1][i];
				break;
			}
		}

		int[] answer = new int[2];
		answer[0] = moveDistance;
		answer[1] = sumOfTalkTime;

		return answer;
	}
}
