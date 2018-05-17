package algorithm;

import java.util.Arrays;

public class TryHelloWorld {
	public int nextBigNumber(int n) {
		int answer = 0;

		int size = 1;
		while(n>=Math.pow(2, size++));

		int[] n2 = new int[size];
		int mok = n;
		for(int i=size-1; i>0; i--) {
			n2[i] = mok%2;
			mok = mok/2;
		}
		
		for(int i=size-1; i>0; i--) {
			if(n2[i]==1 && n2[i-1]==0) {
				n2[i]=0;
				n2[i-1]=1;
				Arrays.sort(n2, i+1, size);
				break;
			}
		}
		
		for(int i=size-1; i>=0; i--) {
			answer += Math.pow(2, size-i-1)*n2[i];
		}
		
		return answer;
	}

	public static void main(String[] args) {
		TryHelloWorld test = new TryHelloWorld();
		int n = 78;
		System.out.println(test.nextBigNumber(n));
	}
}
