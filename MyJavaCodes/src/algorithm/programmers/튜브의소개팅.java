package algorithm.programmers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class 튜브의소개팅 {

	public static void main(String[] args) {
//		int m = 3;
//		int n = 3;
//		int s = 150;
//		int[][] time_map = new int[][] { { 0, 2, 99 }, { 100, 100, 4 }, { 1, 2, 0 } };

//		int m = 4;
//		int n = 6;
//		int s = 25;
//		int[][] time_map = { { 0, 1, 1, -1, 2, 4 }, { -1, 7, 2, 1, 5, 7 }, { -1, 1, -1, 1, 6, 3 },
//				{ -1, 1, -1, -1, 7, 0 } };

		int m = 5;
		int n = 5;
		int s = 12;
		int[][] time_map = { { 0, 1, 1, 1, 1 }, { 9, 9, 9, 1, 9 }, { 1, 1, 1, 1, 9 }, { 1, 1, 5, 9, 9 },
				{ 1, 1, 1, 1, 0 } };

		System.out.println(Arrays.toString(new 튜브의소개팅().solution(m, n, s, time_map)));
	}

	static class Node {
		int x;
		int y;
		int d;
		int s = Integer.MAX_VALUE;
		Node parent;

		public Node(int x, int y, int d, int s, Node parent) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
			this.s = s;
			this.parent = parent;
		}

		@Override
		public String toString() {
			return String.format("x=%d, y=%d, d=%d, s=%s", x, y, d, s);
		}
	}

	public int[] solution(int m, int n, int s, int[][] time_map) {

		int moveDistance = 0;
		int sumOfTalkTime = 0;

		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(0, 0, 0, 0, null));

		int[][] minT = new int[m][n];
		for (int[] e : minT) {
			Arrays.fill(e, Integer.MAX_VALUE);
		}
		minT[0][0] = 0;

		int[] h = { 0, 0, -1, 1 };
		int[] v = { -1, 1, 0, 0 };

		while (!q.isEmpty()) {
			Node node = q.poll();

			if (node.x == m - 1 && node.y == n - 1) {
				if (moveDistance == 0 || (moveDistance == node.d && sumOfTalkTime > node.s)) {
//					System.out.println(node);
					moveDistance = node.d;
					sumOfTalkTime = node.s;
				}
			}

			for (int i = 0; i < 4; i++) {
				int newX = node.x + v[i];
				int newY = node.y + h[i];
				int tempS = node.s + time_map[node.x][node.y];
				if (newX >= 0 && newY >= 0 && newX < m && newY < n && time_map[newX][newY] != -1 && tempS <= s
						&& tempS < minT[newX][newY]) {
					minT[newX][newY] = tempS;
					q.offer(new Node(newX, newY, node.d + 1, tempS, node));
//					System.out.printf("node=%s, newX=%d, newY=%d, tempS=%d, newMinT=%d\n", node, newX, newY, tempS,
//							minT[newX][newY]);
				}
			}
		}

		int[] answer = new int[2];
		answer[0] = moveDistance;
		answer[1] = sumOfTalkTime;

		return answer;
	}
}
