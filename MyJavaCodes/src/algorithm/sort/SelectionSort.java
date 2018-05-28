package algorithm.sort;

import java.util.Arrays;

public class SelectionSort {

	public static void main(String[] args) {
		
		int[] in = new int[] {10, 3, 7, 9, 5, 6, 8, 8, 1};
		
		for(int i=0; i<in.length-1; i++) {
			int min = i;
			for(int j=i+1;j<in.length; j++) {
				if(in[min] > in[j]) {
					min = j;
				}
			}
			int tmp = in[min];
			in[min] = in[i];
			in[i] = tmp;
		}
		System.out.println(Arrays.toString(in));
	}
	
}
