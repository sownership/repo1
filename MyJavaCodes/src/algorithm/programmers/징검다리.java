package algorithm.programmers;

import java.util.Arrays;

public class Â¡°Ë´Ù¸® {

	public static void main(String[] args) {

		int distance = 25;
		int[] rocks = new int[] { 2, 14, 11, 21, 17 };
		int n = 2;
		System.out.println(new Â¡°Ë´Ù¸®().solution(distance, rocks, n));
	}

	public int solution(int distance, int[] rocks, int n) {
		Arrays.sort(rocks);
		int[] arr = new int[rocks.length + 2];
		arr[0] = 0;
		arr[rocks.length + 1] = distance;
		System.arraycopy(rocks, 0, arr, 1, rocks.length);

		int s = 0;
		int e = distance;
		while (e > s) {
			int m = (s + e + 1) / 2;
			boolean isOk = true;
			int broken = 0;
			for (int first = 0, second = 1; second < arr.length;) {
				if (arr[second] - arr[first] < m) {
					broken++;
					if (broken > n) {
						isOk = false;
						break;
					}
				} else
					first = second;
				second++;
			}
			if (isOk)
				s = m;
			else
				e = m - 1;
		}

		return e;
	}
}
