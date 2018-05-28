package algorithm.programmers;

import java.util.LinkedList;
import java.util.List;

public class diguk {

	public static void main(String[] args) {
		diguk ins = new diguk();
		ins.in = new char[][] { { 'b', 'a', 'a', 'a', 'c', 'c' }, { 'b', 'b', 'b', 'a', 'c', 'c' },
				{ 'd', 'b', 'a', 'a', 'a', 'a' }, { 'd', 'b', 'd', 'd', 'a', 'c' }, { 'd', 'b', 'b', 'b', 'a', 'c' },
				{ 'c', 'c', 'c', 'c', 'a', 'a' } };
		System.out.println(ins.getMax(ins.in));
	}

	private char[][] in;
	private char[][] in2;

	private int getMax(char[][] in) {

		int max = 0;
		in2 = new char[in.length][in.length];

		for (int y = 0; y < in.length; y++) {
			for (int x = 0; x < in.length; x++) {
				if (isEdgeR(y, x)) {
					max = Math.max(max, getMaxR(y, x));
				}
				if (isEdgeL(y, x)) {
					max = Math.max(max, getMaxL(y, x));
				}
				if (isEdgeT(y, x)) {
					max = Math.max(max, getMaxT(y, x));
				}
				if (isEdgeB(y, x)) {
					max = Math.max(max, getMaxB(y, x));
				}
			}
		}
		return max;
	}

	private int getMaxR(int y, int x) {
		char c = in[y][x];
		List<Integer> left = new LinkedList<>();
		List<Integer> top = new LinkedList<>();
		List<Integer> left = new LinkedList<>();
		List<Integer> left = new LinkedList<>();
		for(int i=x;i>=0;i--) {
			if(c!=in[y][i]) {
				break;
			}
			
		}
		return 0;
	}

	private boolean isEdgeR(int y, int x) {
		if ((x - 1 < 0 || in[y][x] == in[y][x - 1]) && (x + 1 >= in.length || in[y][x] != in[y][x + 1])) {
			return true;
		}
		return false;
	}

	private boolean isEdgeL(int y, int x) {
		if ((x - 1 < 0 || in[y][x] != in[y][x - 1]) && (x + 1 >= in.length || in[y][x] == in[y][x + 1])) {
			return true;
		}
		return false;
	}

	private boolean isEdgeT(int y, int x) {
		if ((y - 1 < 0 || in[y - 1][x] != in[y - 1][x]) && (y + 1 >= in.length || in[y][x] == in[y + 1][x])) {
			return true;
		}
		return false;
	}

	private boolean isEdgeB(int y, int x) {
		if ((y - 1 < 0 || in[y - 1][x] == in[y - 1][x]) && (y + 1 >= in.length || in[y][x] != in[y + 1][x])) {
			return true;
		}
		return false;
	}
}
