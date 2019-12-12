package algorithm.programmers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class N으로표현 {

	public static void main(String[] args) {

		System.out.println(new N으로표현().solution(5, 12));
	}

	public int solution(int N, int number) {
		for (String target : getTargets(N)) {
			System.out.println(target);
			if (check(target)) {
				return target.replace(",", "").length();
			}
		}
		return -1;
	}

	private boolean check(String target) {
		// TODO Auto-generated method stub
		return false;
	}

	private List<String> getTargets(int N) {
		// 5
		// 5,5
		// 55
		// 5,5,5
		// 5,55
		// 55,5
		// 555
		return IntStream.range(1, 9).mapToObj(i -> getTargets(N, i)).flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private List<String> getTargets(int N, int cntOfN) {
		// 5,5,5
		// 5,55
		// 55,5
		// 555
		return r(N, cntOfN, 0, new LinkedList<>());
	}

	private List<String> r(int N, int cntOfN, int consume, List<String> acc) {
		return IntStream.range(1, cntOfN + 1).filter(i -> consume + i <= cntOfN).mapToObj(i -> {
			// 5,5,5
			// 5,55
			List<String> newAcc = new LinkedList<>(acc);
			newAcc.add(IntStream.range(0, i).mapToObj(j -> String.valueOf(N)).collect(Collectors.joining()));
			if (consume + i == cntOfN) {
				System.out.println(newAcc);
				return newAcc.stream().collect(Collectors.joining(","));
			} else {
				return r(N, cntOfN, consume + i, newAcc).stream().collect(Collectors.joining(","));
			}
		}).collect(Collectors.toList());
	}
}
