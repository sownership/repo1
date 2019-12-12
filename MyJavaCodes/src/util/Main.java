package util;

import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		
		Queue<Integer> pq = new PriorityQueue<>((a,b)->a-b);
		pq.offer(4);
		pq.offer(1);
		pq.offer(2);
		pq.offer(3);
		System.out.println(pq.poll());
	}
}
