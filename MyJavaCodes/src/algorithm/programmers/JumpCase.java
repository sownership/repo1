package algorithm;

public class JumpCase {
	public int jumpCase(int num) {
		int answer = 0;

		if(num<3) {
			answer = num;
		} else {
			int first = 1;
			int second = 2;
			for(int i=3; i<=num; i++) {
				int tmp = second;
				second = first + second;
				first = tmp;
			}
			answer = second;
		}
		
		return answer;
	}

	public static void main(String[] args) {
		JumpCase c = new JumpCase();
		int testCase = 4;
		// �Ʒ��� �׽�Ʈ�� ����� ���� ���� �ڵ��Դϴ�.
		System.out.println(c.jumpCase(testCase));
	}
}
