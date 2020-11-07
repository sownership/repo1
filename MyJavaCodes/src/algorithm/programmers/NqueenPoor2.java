package algorithm.programmers;

public class NqueenPoor2 {

	private static int[][] pan;
	private static int answer;

	public static void main(String[] args) {
		int n = 9;
		pan = new int[n][n];
		dfs(n, 0, 0, 0);
		System.out.println(answer);
	}

	private static void dfs(int n, int ys, int xs, int current) {
		for (int y = ys; y < n; y++) {
			for (int x = 0; x < n; x++) {
				if (y == ys && x < xs)
					continue;
				if (isPossible(y, x)) {
					if (current == n - 1) {
						answer++;
						continue;
					}
					set(n, y, x);
					dfs(n, y, x, current + 1);
					unset(n, y, x);
				}
			}
		}
	}

	private static void unset(int n, int currentY, int currentX) {
		for (int y = 0; y < n; y++)
			pan[y][currentX]--;
		for (int x = 0; x < n; x++)
			if (x != currentX)
				pan[currentY][x]--;
		int newY = currentY;
		int newX = currentX;
		while (isBound(n, ++newY, ++newX))
			pan[newY][newX]--;
		newY = currentY;
		newX = currentX;
		while (isBound(n, ++newY, --newX))
			pan[newY][newX]--;
		newY = currentY;
		newX = currentX;
		while (isBound(n, --newY, ++newX))
			pan[newY][newX]--;
		newY = currentY;
		newX = currentX;
		while (isBound(n, --newY, --newX))
			pan[newY][newX]--;
	}

	private static void set(int n, int currentY, int currentX) {
		for (int y = 0; y < n; y++)
			pan[y][currentX]++;
		for (int x = 0; x < n; x++)
			if (x != currentX)
				pan[currentY][x]++;
		int newY = currentY;
		int newX = currentX;
		while (isBound(n, ++newY, ++newX))
			pan[newY][newX]++;
		newY = currentY;
		newX = currentX;
		while (isBound(n, ++newY, --newX))
			pan[newY][newX]++;
		newY = currentY;
		newX = currentX;
		while (isBound(n, --newY, ++newX))
			pan[newY][newX]++;
		newY = currentY;
		newX = currentX;
		while (isBound(n, --newY, --newX))
			pan[newY][newX]++;
	}

	private static boolean isBound(int n, int y, int x) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}

	private static boolean isPossible(int currentY, int currentX) {
		return pan[currentY][currentX] == 0;
	}
}
