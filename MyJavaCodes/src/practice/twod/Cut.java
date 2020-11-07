package practice.twod;

import java.util.Arrays;

public class Cut {

	public static void main(String[] args) {

		int[][] pan = new int[5][5];
		for (int y = 0; y < pan.length; y++) {
			for (int x = 0; x < pan[0].length; x++) {
				pan[y][x] = y * pan[0].length + x;
			}
		}
		int[] ccut = new int[] { 1, 3 };
		int[] rcut = new int[] { 1, 3 };

		for (int y = 0; y < pan.length; y++)
			System.out.println(Arrays.toString(pan[y]));
		System.out.println("--------------------");

		int[] ccut2 = new int[ccut.length + 2];
		int[] rcut2 = new int[rcut.length + 2];
		ccut2[ccut2.length - 1] = pan[0].length;
		rcut2[rcut2.length - 1] = pan.length;
		System.arraycopy(ccut, 0, ccut2, 1, ccut.length);
		System.arraycopy(rcut, 0, rcut2, 1, rcut.length);

		for (int y = 0; y < rcut2.length - 1; y++) {
			int ys = rcut2[y];
			int ye = rcut2[y + 1];
			for (int x = 0; x < ccut2.length - 1; x++) {
				int xs = ccut2[x];
				int xe = ccut2[x + 1];
				int sum = 0;
				for (int yc = ys; yc < ye; yc++) {
					for (int xc = xs; xc < xe; xc++) {
						sum += pan[yc][xc];
					}
				}
				System.out.printf("%d,%d - %d,%d : %d\n", ys, xs, ye - 1, xe - 1, sum);
			}
		}
	}
}
