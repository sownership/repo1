package algorithm.bj;

import java.util.Scanner;

public class q_2869_´ÞÆØÀÌ_ÀÌºÐÅ½»ö_ok {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int v = scanner.nextInt();
		
		int s=1;
		int e=v/(a-b) + 1;
		while(e>s) {
			int m=(s+e)/2;
			int d=(m-1)*(a-b)+a;
			if(d>v) {
				e=m;
			} else if(d==v) {
				System.out.println(m);
				return;
			} else {
				s=m+1;
			}
		}
		System.out.println(e);
	}
}
