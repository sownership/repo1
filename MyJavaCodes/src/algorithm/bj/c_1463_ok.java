package algorithm.bj;

import java.util.Arrays;
import java.util.Scanner;

public class c_1463_ok {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {

		int n = scanner.nextInt();
		System.out.println(new c_1463_ok().solution(n));
	}

	private int solution(int n) {
		int[] dp = new int[n + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[1] = 0;

		int[] parent = new int[dp.length];
		int[] what = new int[dp.length];

		for (int i = 2; i <= n; i++) {

			if (i % 3 == 0 && dp[i] > 1 + dp[i / 3]) {
				dp[i] = 1 + dp[i / 3];
				parent[i] = i / 3;
				what[i] = 1;
			}
			if (i % 2 == 0 && dp[i] > 1 + dp[i / 2]) {
				dp[i] = 1 + dp[i / 2];
				parent[i] = i / 2;
				what[i] = 2;
			}
			if (dp[i] > 1 + dp[i - 1]) {
				dp[i] = 1 + dp[i - 1];
				parent[i] = i - 1;
				what[i] = 3;
			}
		}

//		int tmp = n;
//		while(tmp > 1) {
//			System.out.println(tmp + "," + parent[tmp] + "," + what[tmp]);
//			tmp = parent[tmp];
//		}
//		System.out.println();
		
		return dp[n];
	}
}
