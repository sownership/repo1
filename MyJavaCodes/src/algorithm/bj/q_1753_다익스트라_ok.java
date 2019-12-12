package algorithm.bj;

import java.util.*;

public class q_1753_다익스트라_ok {
    static class Node {
        int idx;
        int min;
        Node(int idx, int min) {
            this.idx = idx;
            this.min = min;
        }
    }
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int start = scanner.nextInt();
        Map<Integer,Map<Integer, Integer>> graph = new HashMap<>();
        for(int i=0;i<m;i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.computeIfAbsent(from, k->new HashMap<>()).compute(to, (k,v)->v==null?weight:Math.min(v, weight));
        }
        int[] dist = new int[n+1];
        for(int i=1;i<dist.length;i++) dist[i] = Integer.MAX_VALUE;
        dist[start] = 0;
        Queue<Node> pq = new PriorityQueue<>((n1,n2)->n1.min - n2.min);
        pq.offer(new Node(start, 0));
        Set<Integer> settled = new HashSet<>();
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            if(settled.contains(cur.idx)) continue; //for garbage
            settled.add(cur.idx);
            if(!graph.containsKey(cur.idx)) continue; 
            for(Map.Entry<Integer, Integer> e : graph.get(cur.idx).entrySet()) {
                int to = e.getKey();
                int weight = e.getValue();
                if(settled.contains(to)) continue;
                if(dist[to] > dist[cur.idx] + weight) {
                    dist[to] = dist[cur.idx] + weight;
                    pq.offer(new Node(to, dist[to]));
                }
            }
        }
        for(int i=1;i<n+1;i++) {
            if(settled.contains(i)) System.out.println(dist[i]);
            else System.out.println("INF");
        }
    }
}