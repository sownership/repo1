package practice.twod;

import java.util.Arrays;

public class SpiralPartial {

	public static void main(String[] args) {
		
//		method1();
		
		method2();
	}

	private static void method2() {
		int n=10;
		int x1=2;
		int x2=8;
		int y1=2;
		int y2=8;
		int[][] pan = new int[n][n];
		
		int ys=y2-y1, xs=x2-x1+1, yc=x2-x1+1, xc=y2-y1+1, y=y1, x=x1-1, sub=1, idx=0;
		
		while(xc>0 && yc>0) {
			if(xc-->0) {
				for(int k=0;k<xs;k++) {
					x+=sub;
					pan[y][x]=idx++;
				}
				xs--;
			}
			if(yc-->0) {
				for(int k=0;k<ys;k++) {
					y+=sub;
					pan[y][x]=idx++;
				}
				ys--;
			}
			sub=-sub;
		}
		
		Arrays.stream(pan).forEach(p->System.out.println(Arrays.toString(p)));
	}

	private static void method1() {
		int n=10;
		int x1=2;
		int x2=8;
		int y1=2;
		int y2=8;
		int[][] pan = new int[n][n];
		
		int idx=0;
		for(int k=0;k<n/2+n%2;k++) {
			for(int x=x1+k;x<=x2-k;x++) {
				if(pan[y1+k][x]==0) pan[y1+k][x] = idx++;
			}
			for(int y=y1+k+1;y<y2-k;y++) {
				if(pan[y][x2-k]==0) pan[y][x2-k] = idx++;
			}
			for(int x=x2-k;x>=x1+k;x--) {
				if(pan[y2-k][x]==0) pan[y2-k][x] = idx++;
			}
			for(int y=y2-k-1;y>y1+k;y--) {
				if(pan[y][x1+k]==0) pan[y][x1+k] = idx++;
			}
		}
		
		Arrays.stream(pan).forEach(p->System.out.println(Arrays.toString(p)));
	}
}
