package algorithm;

public class PrimeRight {

	public static void main(String[] args) {
		
		int n = 8;
		f(n-1, "2");
		f(n-1, "3");
		f(n-1, "5");
		f(n-1, "7");
	}
	
	private static int cnt;
	
	private static void f(int n, String s) {
		if(n==0) {
			if(isPrime(s)) {
				cnt++;
				System.out.println(cnt + " " + s);
			}
			return;
		}
		if(!isPrime(s)) {
			return;
		}
		f(n-1, s+"1");
		f(n-1, s+"3");
		f(n-1, s+"7");
		f(n-1, s+"9");
	}

	private static boolean isPrime(String s) {
		
		int is = Integer.valueOf(s);
		for(int i=2; i*i<=is; i++) {
			if(is%i==0) {
				return false;
			}
		}
		
		return true;
	}
}
