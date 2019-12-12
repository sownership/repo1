package algorithm.programmers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 사칙연산 {

	public static void main(String[] args) {
		String[] arr = new String[] { "1", "-", "3", "+", "5", "-", "8" };
//		String[] arr = new String[] { "5", "-", "3", "+", "1", "+", "2", "-", "4" };
		System.out.println(new 사칙연산().solution(arr));
	}

	public int solution(String arr[]) {
		List<String> list = new LinkedList<>(Arrays.asList(arr));

		return r(list, 0);
	}

	private int r(List<String> list, int depth) {
		if (list.size() == 1) {
			return Integer.parseInt(list.get(0));
		}

		String spaces = IntStream.range(0, depth).mapToObj(j -> " ").collect(Collectors.joining());
		System.out.println(spaces + list);
		int answer = 0;

		for (int i = 0; i < list.size() - 2; i += 2) {

			// mark
			int l = Integer.parseInt(list.remove(i));
			String operand = list.remove(i);
			int r = Integer.parseInt(list.remove(i));

			System.out.println(spaces + l + " " + operand + " " + r);

			try {
				if ("+".equals(operand)) {
					list.add(i, String.valueOf(l + r));
				} else {
					list.add(i, String.valueOf(l - r));
				}

				answer = Math.max(answer, r(list, depth + 1));
			} finally {
				// unmark
				list.remove(i);
				list.add(i, String.valueOf(r));
				list.add(i, operand);
				list.add(i, String.valueOf(l));
			}
		}
		return answer;
	}
}
