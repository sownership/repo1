package practice.twod;

import java.util.Arrays;

public class Marmmo {

	public static void main(String[] args) {
		int n=10;
		int yp=2;
		int xp=1;
		int r=4;
		int[][] pan = new int[n][n];
		for(int y=0;y<n;y++) {
			for(int x=0;x<n;x++) {
				pan[y][x]=y*n+x;
			}
		}
		Arrays.stream(pan).forEach(p->System.out.println(Arrays.toString(p)));
		
		for(int k=0;k<r/2;k++) {
			int y=yp-2-k;
			if(y<0 || y>=n) continue;
			int xs=Math.max(0, xp-1-r/2+k);
			int xe=Math.min(n-1, xp-1+r/2-k);
			for(int x=xs;x<xe;x++) {
				System.out.print(pan[y][x] + " ");
			}
			System.out.println();
		}
		for(int k=0;k<r/2;k++) {
			int y=yp-1+k;
			if(y<0 || y>=n) continue;
			int xs=Math.max(0, xp-1-r/2+k);
			int xe=Math.min(n-1, xp-1+r/2-k);
			for(int x=xs;x<xe;x++) {
				System.out.print(pan[y][x] + " ");
			}
			System.out.println();
		}
	}
}
