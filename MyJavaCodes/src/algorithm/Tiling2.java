package algorithm;

public class Tiling2 {

	public int tiling(int n) {

		if (n % 2 == 1) {
			return 0;
		}
		if (n == 2) {
			return 3;
		}

		int t1 = 1;
		int t2 = 3;
		int x = 2;
		while (x < n) {
			x += 2;
			int tmp = 4 * t2 - t1;
			t1 = t2;
			t2 = tmp;
			if (t1 >= 100000 || t2 >= 100000) {
				t1 %= 100000;
				t2 %= 100000;
				if (t1 > t2) {
					t2 += 100000;
				}
			}
		}

		return t2 % 100000;
	}

	// 아래는 테스트로 출력해 보기 위한 코드입니다.
	public static void main(String[] args) {
		Tiling2 t = new Tiling2();
		for (int i = 1000; i < 2000; i++) {
			System.out.println(t.tiling(463));
		}
	}
}
