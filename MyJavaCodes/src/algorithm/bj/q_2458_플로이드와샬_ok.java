package algorithm.bj;
import java.util.*;
public class q_2458_플로이드와샬_ok {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		int n=scanner.nextInt();
		int[][] map = new int[n+1][n+1];
		int edgeCnt = scanner.nextInt();
		for(int i=0;i<edgeCnt;i++) {
			int from = scanner.nextInt();
			int to = scanner.nextInt();
			map[from][to]=1;
		}
		for(int m=1;m<=n;m++) {
			for(int s=1;s<=n;s++) {
				for(int e=1;e<=n;e++) {
					if(map[s][m]==1 && map[m][e]==1) map[s][e]=1;
				}
			}
		}
		int answer = 0;
		for(int i=1;i<=n;i++) {
			int sum=0;
			for(int j=1;j<=n;j++) {
				sum += map[i][j] + map[j][i];
			}
			if(sum==n-1) answer++;
		}
		System.out.println(answer);
	}
}
