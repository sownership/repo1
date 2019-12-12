package algorithm.programmers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Test01 {
	public static void main(String[] args) {
		int[][] nodeinfo = new int[][] { { 5, 3 }, { 11, 5 }, { 13, 3 }, { 3, 5 }, { 6, 1 }, { 1, 3 }, { 8, 6 },
				{ 7, 2 }, { 2, 2 } };
		new Test01().solution(nodeinfo);
	}

	static class N {
		int x;
		int y;
		N l;
		N r;

		public N(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
//			return String.format("(x:%d, y:%d, l:%s, r:%s)", x, y, l, r);
			return String.format("(x:%d, y:%d)", x, y);
		}
	}

	public int[][] solution(int[][] nodeinfo) {

		int[] rootArr = Arrays.stream(nodeinfo).reduce((n1, n2) -> n1[1] > n2[1] ? n1 : n2).get();
		N root = new N(rootArr[0], rootArr[1]);
		Arrays.stream(nodeinfo).forEach(n -> add(root, n));
		System.out.println(root);

		int[][] answer = new int[2][];
		// 전위
		List<N> preResult = new LinkedList<>();
		Stack<N> preStack = new Stack<>();
		preStack.push(root);
		while (!preStack.isEmpty()) {
			N cur = preStack.pop();
			preResult.add(cur);
			if (cur.r != null) {
				preStack.push(cur.r);
			}
			if (cur.l != null) {
				preStack.push(cur.l);
			}
		}
		System.out.println(preResult);

		// 후위
		List<N> postResult = new LinkedList<>();
		Stack<N> postStack = new Stack<>();
		Set<N> visited = new HashSet<>();
		postStack.push(root);
		while (!postStack.isEmpty()) {
			N cur = postStack.pop();
			if (visited.contains(cur)) {
				postResult.add(cur);
			} else {
				visited.add(cur);
				postStack.push(cur);
				if (cur.r != null) {
					postStack.push(cur.r);
				}
				if (cur.l != null) {
					postStack.push(cur.l);
				}
			}
		}
		System.out.println(postResult);

		return answer;
	}

	private void add(N root, int[] node) {
		if (root.x == node[0] && root.y == node[1]) {
			return;
		}
		System.out.println(root);
		N curN = root;
		while (true) {
			if (curN.x < node[0]) {
				if (curN.r == null) {
					curN.r = new N(node[0], node[1]);
					return;
				} else {
					if (curN.r.y < node[1]) {
						N tmp = curN.r;
						curN.r = new N(node[0], node[1]);
						if (curN.r.x > tmp.x) {
							curN.r.l = tmp;
						} else {
							curN.r.r = tmp;
						}
						return;
					} else {
						curN = curN.r;
					}
				}
			} else {
				if (curN.l == null) {
					curN.l = new N(node[0], node[1]);
					return;
				} else {
					if (curN.l.y < node[1]) {
						N tmp = curN.l;
						curN.l = new N(node[0], node[1]);
						if (curN.l.x > tmp.x) {
							curN.l.l = tmp;
						} else {
							curN.l.r = tmp;
						}
						return;
					} else {
						curN = curN.l;
					}
				}
			}
		}
	}
}
