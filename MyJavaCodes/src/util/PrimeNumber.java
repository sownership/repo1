package util;


public class PrimeNumber {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int target = 5000000;
		int[] all = new int[target];
		for (int i = 0; i < target; i++) {
			all[i] = i;
		}
		int check = 0;
		for (int i = 2; i < Math.sqrt(target); i++) {
			if (all[i] == 0)
				continue;
			for (int j = i + i; j < target; j += i) {
				check++;
				all[j] = 0;
			}
		}
		int cnt = 0;
		for (int i = 2; i < target; i++) {
			if (all[i] != 0) {
				cnt++;
//				System.out.println(i);
			}
		}
		System.out.println(System.currentTimeMillis()-start + ":" + cnt + ":" + check);
	}
}
