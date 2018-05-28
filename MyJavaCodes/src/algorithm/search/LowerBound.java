package algorithm.search;

public class LowerBound {

	public static void main(String[] args) {
		
		int[] ia = {1,5,10,15,20};
		System.out.println(get(ia, 20));
	}

	private static int get(int[] ia, int t) {
		
		int s = 0;
		int e = ia.length; //t>ia[last] 일 경우 ia[length] return 하는 것이므로
		
		while(e > s) {
			int m = (s+e)/2;
			if(t <= ia[m]) {
				e = m;
			} else {
				s = m + 1;
			}
		}		
		
		return s;
	}
}
