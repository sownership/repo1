package algorithm.search;

public class UpperBound {

	public static void main(String[] args) {
		
		int[] ia = {1,5,10,15,20};
		System.out.println(get(ia, 3));
	}

	private static int get(int[] ia, int t) {
		
		int s = 0;
		int e = ia.length;
		
		while(e > s) {
			int m = (s+e)/2;
			if(t < ia[m]) {
				e = m;
			} else {
				s = m + 1;
			}
		}
		
		return s;
	}
}
