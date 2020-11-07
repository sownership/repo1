package algorithm.bj;

import java.util.*;
public class q_1654_�����ڸ���_�̺�Ž��_ok {
	public static void main(String[] args) {
		Scanner z = new Scanner(System.in);
		int k = z.nextInt();
		int n = z.nextInt();
		int[] l = new int[k];
		for (int i = 0; i < k; i++)
			l[i] = z.nextInt();
		Arrays.sort(l);
		long s = 1;
		long e = l[k - 1];
		while (e > s) {
			long m = (s + e + 1) / 2;
			int c = 0;
			for (int i = 0; i < k; i++)
				c += l[i] / m;
			if (c >= n)
				s = m;
			else
				e = m - 1;
		}
		System.out.println(e);
	}
}
