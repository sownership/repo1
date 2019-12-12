package algorithm.programmers;

public class cookie {

	public static void main(String[] args) {

		int[] cookie = new int[] { 1, 2, 4, 5 };
		System.out.println(new cookie().solution(cookie));
	}

	public int solution(int[] cookie) {
		int[] answers = new int[cookie.length];
		for (int i = cookie.length - 2; i >= 0; i--) {
			answers[i] = Math.max(answers[i + 1], getMaxWithI(cookie, i));
		}

		return answers[0];
	}

	private int getMaxWithI(int[] cookie, int i) {
		int maxWithi = 0;
		int leftIndex = i;
		int leftSum = i;
		int rightIndex = i + 1;
		int rightSum = i + 1;
		while (true) {
			if (leftSum == rightSum) {
				maxWithi = leftSum;

				leftIndex++;
				leftSum += cookie[leftIndex];
				rightSum -= cookie[leftIndex];

				rightIndex++;
				if (rightIndex >= cookie.length) {
					break;
				}
				rightSum += cookie[rightIndex];
			} else if (leftSum > rightSum) {
				rightIndex++;
				if (rightIndex >= cookie.length) {
					break;
				}
				rightSum += cookie[rightIndex];
			} else {
				leftIndex++;
				leftSum += cookie[leftIndex];
				rightSum -= cookie[leftIndex];
			}
		}
		return maxWithi;
	}
}
