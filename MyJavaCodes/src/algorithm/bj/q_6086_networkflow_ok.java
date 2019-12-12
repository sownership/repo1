package algorithm.bj;

import java.util.*;

public class q_6086_networkflow_ok {

	private static Scanner sc = new Scanner(System.in);
	public static void main(String args[]) {
		int answer = 0;
		int corridorCnt = Integer.valueOf(sc.nextLine());

		Map<String, Map<String, Integer>> fromToCapacity = new HashMap<>();
		Map<String, Map<String, Integer>> fromToflow = new HashMap<>();
		for (int i = 0; i < corridorCnt; i++) {
			String[] le = sc.nextLine().split(" ");
			String from = le[0];
			String to = le[1];
			int capacity = Integer.valueOf(le[2]);
			fromToCapacity.computeIfAbsent(from, k->new HashMap<>()).compute(to, (k,v)->v==null?capacity:v+capacity);
			fromToCapacity.computeIfAbsent(to, k->new HashMap<>()).compute(from, (k,v)->v==null?capacity:v+capacity);
//			fromTosCapacity.computeIfAbsent(to, k->new HashMap<>()).computeIfAbsent(from, k->0);
			fromToflow.computeIfAbsent(from, k->new HashMap<>()).put(to, 0);
			fromToflow.computeIfAbsent(to, k->new HashMap<>()).put(from, 0);
		}

		Queue<String> q = new LinkedList<>();
		Map<String, String> prev = new HashMap<>();
		while (true) {
			q.clear();
			prev.clear();
			q.add("A");
			prev.put("A", "A");
			boolean needToContinue = true;
			while (needToContinue && !q.isEmpty()) {
				String cur = q.poll();
				if (!fromToCapacity.containsKey(cur))
					continue;
				for (Map.Entry<String, Integer> toCapacity : fromToCapacity.get(cur).entrySet()) {
					String to = toCapacity.getKey();
					if (prev.get(to)!=null)
						continue;
					if (toCapacity.getValue() - fromToflow.get(cur).get(to) > 0) {
						q.add(to);
						prev.put(to, cur);
						if (to.equals("Z")) {
							needToContinue = false;
							break;
						}
					}
				}
			}
			if (prev.get("Z") == null)
				break;
			int minFlow = Integer.MAX_VALUE;
			for (String v = "Z"; prev.get(v) != v; v = prev.get(v)) {
				minFlow = Math.min(minFlow, fromToCapacity.get(prev.get(v)).get(v) - fromToflow.get(prev.get(v)).get(v));
			}
			for (String v = "Z"; prev.get(v) != v; v = prev.get(v)) {
				int newCost = minFlow;
				fromToflow.get(prev.get(v)).compute(v, (key, val)->val+=newCost);
				fromToflow.get(v).compute(prev.get(v), (key, val)->val-=newCost);
			}
			answer += minFlow;
		}
		System.out.println(answer);
	}
}
