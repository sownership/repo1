package algorithm.bj;
import java.util.*;
public class q_17412_networkflow_ok {
	static Scanner scanner = new Scanner(System.in); 
	public static void main(String[] args) {
		int answer = 0;
		int cityCnt = scanner.nextInt();
		Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
		Map<Integer, Map<Integer, Integer>> flow = new HashMap<>();
		graph.computeIfAbsent(1,k->new HashMap<>()).put(1,0);
		flow.computeIfAbsent(1,k->new HashMap<>()).put(1,0);
		int infoCnt = scanner.nextInt();
		for(int i=0;i<infoCnt;i++) {
			int s = scanner.nextInt();
			int e = scanner.nextInt();
			graph.computeIfAbsent(s, k->new HashMap<>()).compute(e, (k,v)->v==null?1:v+1);
			graph.computeIfAbsent(e, k->new HashMap<>()).compute(s, (k,v)->v==null?0:v);
			flow.computeIfAbsent(s, k->new HashMap<>()).put(e, 0);
			flow.computeIfAbsent(e, k->new HashMap<>()).put(s, 0);
		}
		Queue<Integer> q = new LinkedList<>();
		Map<Integer,Integer> prev = new HashMap<>();
		while(true) {
			q.clear();
			prev.clear();
			q.offer(1);
			prev.put(1, 1);
			boolean needToContinue = true;
			while(needToContinue && !q.isEmpty()) {
				int cur = q.poll();
				if(!graph.containsKey(cur)) continue;
				for(Map.Entry<Integer, Integer> toCapa : graph.get(cur).entrySet()) {
					int to = toCapa.getKey();
					int capa = toCapa.getValue();
					if(prev.get(to)!=null) continue;
					if(capa-flow.get(cur).get(to)>0) {
						q.offer(to);
						prev.put(to, cur);
						if(to==2) {
							needToContinue = false;
							break;
						}
					}
				}
			}
			if(prev.get(2)==null) break;
			int minFlow = Integer.MAX_VALUE;
			for(int v=2;prev.get(v)!=v;v=prev.get(v)) 
				minFlow = Math.min(minFlow, graph.get(prev.get(v)).get(v)-flow.get(prev.get(v)).get(v));
			int decisionFlow = minFlow;
			for(int v=2;prev.get(v)!=v;v=prev.get(v)) {
				flow.get(prev.get(v)).compute(v,(k,f)->f+=decisionFlow);
				flow.get(v).compute(prev.get(v),(k,f)->f-=decisionFlow);
			}
			answer+=decisionFlow;
		}
		System.out.println(answer);
	}
}
