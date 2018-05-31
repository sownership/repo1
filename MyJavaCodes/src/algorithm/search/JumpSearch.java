package algorithm.search;

public class JumpSearch {

	public static void main(String[] args) {
		int arr[] = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610 };
		int x = 55;

		int index = jumpSearch(arr, x);

		System.out.println("\nNumber " + x + " is at index " + index);
	}

	private static int jumpSearch(int[] arr, int x) {
		int n = arr.length;

		int step = (int) Math.floor(Math.sqrt(n));
		int stop = -1;
		// jump
		for (int i = 0; i < n; i += step) {
			if (arr[i] == x) {
				return i;
			} else if (arr[i] > x) {
				stop = i;
				break;
			}
		}
		if (stop == -1) {
			return -1;
		}
		// linear
		for (int i = stop - step + 1; i < stop; i++) {
			if (arr[i] == x) {
				return i;
			}
		}

		return -1;
	}
}
