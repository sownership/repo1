package algorithm.programmers;

public class Tiling {
	public int tiling(int n) {
		
		if(n==1) {
			return 1;
		}
		if(n==2) {
			return 2;
		}
		
		int n1 = 1;
		int n2 = 2;
		int tmp = 0;
		for(int i=3; i<=n; i++) {
			tmp = n2 + n1;
			n1 = n2;
			n2 = tmp;
			if(n1 > 100000 || n2 > 100000) {
				n1%=100000;
				n2%=100000;
			}
		}
		
		return n2;
	}

	public static void main(String args[]) {
		Tiling tryHelloWorld = new Tiling();
		// 아래는 테스트로 출력해 보기 위한 코드입니다.
		System.out.print(tryHelloWorld.tiling(569));
	}
}
