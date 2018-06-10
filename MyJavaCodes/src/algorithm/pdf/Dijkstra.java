package algorithm.pdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * 
7 11
1 2 47
1 3 69
2 4 57
2 5 124
3 4 37
3 5 59
3 6 86
4 6 27
4 7 94
5 7 21
6 7 40

149
 * @author 서효진
 *
 */
public class Dijkstra {

	private static Scanner scanner = new Scanner(System.in);
	private static int nodeCount;
	private static int lineCount;
	private static Map<Integer, Map<Integer, Integer>> info = new HashMap<>();

	public static void main(String[] args) {

		input();

		solve();
	}

	private static void solve() {
		Set<Integer> completedNodeSet = new HashSet<>();

		int[] cost = new int[nodeCount+1];
		for (int i = 1; i <= nodeCount; i++) {
			cost[i] = Integer.MAX_VALUE;
		}

		// 시작 노드는 바로 완료 처리
		completedNodeSet.add(1);
		cost[1] = 0;
		
		
		int startNode = 1;
		while (startNode != nodeCount) {
			for (Entry<Integer, Integer> e : info.get(startNode).entrySet()) {
				int to = e.getKey();
				int newCost = e.getValue();
				cost[to] = Math.min(cost[to], cost[startNode] + newCost);
			}

			//아래는 treeset 으로 logn 으로 줄일 수 있음
			int currentMinCost = Integer.MAX_VALUE;
			for (int i = 1; i <= nodeCount; i++) {
				if (completedNodeSet.contains(i)) {
					continue;
				}
				if (currentMinCost > cost[i]) {
					currentMinCost = cost[i];
					startNode = i;
				}
			}
			completedNodeSet.add(startNode);
		}
		
		System.out.println(cost[nodeCount]);
	}

	/**
	 * map<startnode, Map<endnode, cost>> 형태로 입력을 정리한다
	 */
	private static void input() {

		String[] elements = scanner.nextLine().split(" ");
		nodeCount = Integer.valueOf(elements[0]);
		lineCount = Integer.valueOf(elements[1]);

		while (true) {
			String line = scanner.nextLine();
			if (line == null || "".equals(line)) {
				break;
			}
			elements = line.split(" ");

			int startNode = Integer.valueOf(elements[0]);
			int endNode = Integer.valueOf(elements[1]);
			int cost = Integer.valueOf(elements[2]);

			Map<Integer, Integer> toCost = info.get(startNode);
			if (toCost == null) {
				toCost = new HashMap<>();
				info.put(startNode, toCost);
			}
			toCost.put(endNode, cost);
		}
	}
}
