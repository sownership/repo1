package algorithm.programmers;

public class OneTwoFour {
	public String change124(int n) {

		StringBuffer sb = new StringBuffer();
		int v = n;
		while (true) {
			int remain = v % 3;
			if (remain == 1) {
				sb.append("1");
			} else if (remain == 2) {
				sb.append("2");
			} else if (remain == 0) {
				sb.append("4");
			}
			v = (v - 1) / 3;
			if (v == 0) {
				break;
			}
		}
		return sb.reverse().toString();
	}

	// 아래는 테스트로 출력해 보기 위한 코드입니다.
	public static void main(String[] args) {
		OneTwoFour oneTwoFour = new OneTwoFour();
		for (int i = 1; i < 100; i++)
			System.out.println(i + " " + oneTwoFour.change124(i));
	}
}
