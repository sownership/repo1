package algorithm.bj;

import java.util.Scanner;

public class q_11053_lis_ok {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] seq = new int[n];
		for(int i=0;i<n;i++) {
			seq[i] = scanner.nextInt();
		}
		
		System.out.println(solve(n, seq));
	}
	
	private static int solve(int n, int[] seq) {
		if(n==0) return 0;
		
		int[] a = new int[n];
		int aInx = 0;
		a[0] = seq[0];
		
		for(int i=1;i<n;i++) {
			int s = 0;
			int e = aInx + 1;
			while(s<e) {
				int m = (s+e)/2;
				if(a[m]<seq[i])
					s = m+1;
				else
					e = m;
			}
			a[e]=seq[i];
			if(e==aInx+1) aInx++;
		}
		return aInx + 1;
	}
}
