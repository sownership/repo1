package algorithm.bj;

import java.util.*;

public class q_1197_mst_ok {

	private static class Node {
		int from;
		int to;
		int cost;
		Node(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		List<Node> nodes = new ArrayList<>();
		for(int i=0;i<m;i++) 
			nodes.add(new Node(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
		nodes.sort((a,b)->a.cost-b.cost);
		
		int[] set = new int[n+1];
		for(int i=0;i<n+1;i++) set[i]=i;
		
		int answer=0;
		for(int i=0;i<m;i++) {
			Node node = nodes.remove(0);
			int x = find(set, node.from);
			int y = find(set, node.to);
			if(x!=y) {
				answer+=node.cost;
				union(set, x,y);
			}
		}
		System.out.println(answer);
	}
	
	static int find(int[] set, int s) {
		if(set[s]==s) return s;
		return set[s]=find(set,set[s]);
	}
	
	static void union(int[] set,int s1,int s2) {
		int x=find(set,s1);
		int y=find(set,s2);
		set[y]=x;
	}
}
