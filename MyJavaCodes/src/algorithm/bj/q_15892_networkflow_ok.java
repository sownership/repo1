package algorithm.bj;

import java.util.*;

public class q_15892_networkflow_ok {

	private static Scanner sc = new Scanner(System.in);
	public static void main(String args[]) {
		int answer = 0;
		int dest = sc.nextInt();
		int corridorCnt = sc.nextInt();

		Map<Integer, Map<Integer, Integer>> fromToCapacity = new HashMap<>();
		Map<Integer, Map<Integer, Integer>> fromToFlow = new HashMap<>();
		for (int i = 0; i < corridorCnt; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			int capacity = sc.nextInt();
			fromToCapacity.computeIfAbsent(from, k->new HashMap<>()).compute(to, (k,v)->v==null?capacity:v+capacity);
			fromToCapacity.computeIfAbsent(to, k->new HashMap<>()).compute(from, (k,v)->v==null?capacity:v+capacity);
//			fromTosCapacity.computeIfAbsent(to, k->new HashMap<>()).computeIfAbsent(from, k->0);
			fromToFlow.computeIfAbsent(from, k->new HashMap<>()).put(to, 0);
			fromToFlow.computeIfAbsent(to, k->new HashMap<>()).put(from, 0);
		}

		Queue<Integer> q = new LinkedList<>();
		Map<Integer, Integer> prev = new HashMap<>();
		while (true) {
			q.clear();
			prev.clear();
			q.add(1);
			prev.put(1, 1);
			boolean needToContinue = true;
			while (needToContinue && !q.isEmpty()) {
				int cur = q.poll();
				if (!fromToCapacity.containsKey(cur))
					continue;
				for (Map.Entry<Integer, Integer> toCapacity : fromToCapacity.get(cur).entrySet()) {
					int to = toCapacity.getKey();
					if (prev.get(to)!=null)
						continue;
					if (toCapacity.getValue() - fromToFlow.get(cur).get(to) > 0) {
						q.add(to);
						prev.put(to, cur);
						if (to == dest) {
							needToContinue = false;
							break;
						}
					}
				}
			}
			if (prev.get(dest) == null)
				break;
			int minFlow = Integer.MAX_VALUE;
			for (int v = dest; prev.get(v) != v; v = prev.get(v)) {
				minFlow = Math.min(minFlow, fromToCapacity.get(prev.get(v)).get(v) - fromToFlow.get(prev.get(v)).get(v));
			}
			for (int v = dest; prev.get(v) != v; v = prev.get(v)) {
				int newCost = minFlow;
				fromToFlow.get(prev.get(v)).compute(v, (key, val)->val+=newCost);
				fromToFlow.get(v).compute(prev.get(v), (key, val)->val-=newCost);
			}
			answer += minFlow;
		}
		System.out.println(answer);
	}
}
