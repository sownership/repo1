package algorithm;

public class UnboundedKnapsack {

	public static void main(String[] args) {
		
		int k = 8;
		int[] wa = {1, 3, 4, 5};
		int[] va = {10, 40, 50, 70};
		System.out.println(unboundedKnapsack(wa, va, k));
	}
	
	private static int unboundedKnapsack(int[] wa, int[] va, int k) {
		int[] dpa = new int[k+1];
		
		for(int j=0; j<wa.length; j++) {
			for(int i=0; i<=k; i++) {
				if(i-wa[j]>=0) {
					dpa[i] = Math.max(dpa[i-wa[j]] + va[j], dpa[i]);
				}
			}
			//System.out.println(Arrays.toString(dpa));
		}
		
		return dpa[k];
	}
}
