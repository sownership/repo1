package algorithm.programmers;

import java.util.Arrays;
import java.util.LinkedList;

public class 게임맵최단거리 {

	public static void main(String[] args) {
		int[][] maps = new int[][] { { 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 }, { 1, 1, 1, 0, 1 },
				{ 0, 0, 0, 0, 1 } };
//		int[][] maps = new int[][] { { 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 }, { 1, 1, 1, 0, 0 },
//				{ 0, 0, 0, 0, 1 } };

//		Arrays.stream(maps).forEach(ia -> System.out.println(Arrays.toString(ia)));

		System.out.println(new 게임맵최단거리().solution(maps));
	}

	class XY {
		int x;
		int y;
		int depth;

		public XY(int x, int y, int depth) {
			super();
			this.x = x;
			this.y = y;
			this.depth = depth;
		}

		@Override
		public String toString() {
			return x + "," + y;
		}
	}

	int[][] visited;
	int[][] maps;

	public int solution(int[][] maps) {
		this.maps = maps;
		this.visited = new int[maps.length][maps[0].length];

		LinkedList<XY> q = new LinkedList<>();
		q.offer(new XY(0, 0, 1));

		while (!q.isEmpty()) {
//			System.out.println(q);
			XY xy = q.poll();
			visited[xy.x][xy.y] = 1;
			if (xy.x == maps.length - 1 && xy.y == maps[0].length - 1) {
				return xy.depth;
			}
			if (isPossible(xy.x + 1, xy.y)) {
				q.offer(new XY(xy.x + 1, xy.y, xy.depth + 1));
			}
			if (isPossible(xy.x - 1, xy.y)) {
				q.offer(new XY(xy.x - 1, xy.y, xy.depth + 1));
			}
			if (isPossible(xy.x, xy.y + 1)) {
				q.offer(new XY(xy.x, xy.y + 1, xy.depth + 1));
			}
			if (isPossible(xy.x, xy.y - 1)) {
				q.offer(new XY(xy.x, xy.y - 1, xy.depth + 1));
			}
		}

		return -1;
	}

	private boolean isPossible(int x, int y) {
		return x >= 0 && y >= 0 && x < maps.length && y < maps[0].length && maps[x][y] == 1 && visited[x][y] == 0;
	}
}
