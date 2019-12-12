package algorithm.bj;
import java.util.*;
public class q_2606_bfs_ok {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		int v=scanner.nextInt();
		int n=scanner.nextInt();
		Map<Integer,List<Integer>> computers = new HashMap<>();
		for(int i=0;i<n;i++) {
			int from = scanner.nextInt();
			int to = scanner.nextInt();
			computers.computeIfAbsent(from, k->new ArrayList<>()).add(to);
			computers.computeIfAbsent(to, k->new ArrayList<>()).add(from);
		}
		Queue<Integer> q = new LinkedList<>();
		q.offer(1);
		Set<Integer> visited = new HashSet<>();
		while(!q.isEmpty()) {
			int cur = q.poll();
		    visited.add(cur);
		    if(computers.containsKey(cur))
		    	for(int c : computers.get(cur))
		    		if(!visited.contains(c)) q.offer(c);
		}
		System.out.println(visited.size()-1);
	}
}
