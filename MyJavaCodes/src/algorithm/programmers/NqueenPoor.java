package algorithm.programmers;

public class NqueenPoor {

	public static void main(String[] args) {
		System.out.println(new NqueenPoor().solution(11));
	}

	int[] horizontal;
	int[] vertical;
	int[] ru;
	int[] rd;
	int cnt;

	public int solution(int n) {
		horizontal = new int[n];
		vertical = new int[n];
		ru = new int[n * 2 - 1];
		rd = new int[n * 2 - 1];

		recursive(n, 0, 0, 0);

		return cnt;
	}

	private void recursive(int n, int ya, int xa, int i) {
		for (int y = ya; y < n; y++) {
			for (int x = 0; x < n; x++) {
				if (y == ya && x < xa)
					continue;
				if (set(n, y, x)) {
					if (i == n - 1) {
						cnt++;
						unset(n, y, x);
						continue;
					}
					recursive(n, y, x, i + 1);
					unset(n, y, x);
				}
			}
		}
	}

	private boolean set(int n, int y, int x) {
		if (horizontal[x] == 1)
			return false;
		if (vertical[y] == 1)
			return false;
		if (ru[y + x] == 1)
			return false;
		if (rd[x - y + n - 1] == 1)
			return false;
		horizontal[x] = 1;
		vertical[y] = 1;
		ru[y + x] = 1;
		rd[x - y + n - 1] = 1;
		return true;
	}

	private void unset(int n, int y, int x) {
		horizontal[x] = 0;
		vertical[y] = 0;
		ru[y + x] = 0;
		rd[x - y + n - 1] = 0;
	}
}
