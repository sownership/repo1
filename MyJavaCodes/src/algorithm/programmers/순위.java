package algorithm.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class 순위 {

	public static void main(String[] args) {
		int n = 5;
		int[][] results = new int[][] { { 4, 3 }, { 4, 2 }, { 3, 2 }, { 1, 2 }, { 2, 5 } };
		System.out.println(new 순위().solution(n, results));
	}

	public int solution(int n, int[][] results) {
		int answer = 0;

		Map<Integer, List<Integer>> relation = Arrays.stream(results)
				.collect(Collectors.groupingBy(a -> a[0], Collectors.mapping(a -> a[1], Collectors.toList())));

		List<Set<Integer>> topological = new ArrayList<>();

		Map<Integer, Long> indegree = Arrays.stream(results)
				.collect(Collectors.groupingBy(a -> a[1], Collectors.counting()));

		List<Integer> values = relation.values().stream().flatMap(li -> li.stream()).collect(Collectors.toList());
		Set<Integer> notTargets = relation.keySet().stream().filter(i -> !values.contains(i))
				.collect(Collectors.toSet());

		while (notTargets.size() > 0) {
			topological.add(notTargets);
			Set<Integer> newNotTarget = new HashSet<>();
			for (Integer notTarget : notTargets) {
				List<Integer> tos = relation.get(notTarget);
				if (tos != null) {
					for (Integer to : tos) {
						indegree.put(to, indegree.get(to) - 1);
						if (indegree.get(to) == 0) {
							newNotTarget.add(to);
						}
					}
				}
			}
			notTargets = newNotTarget;
		}

		System.out.println(topological);
		
		return answer;
	}
}
