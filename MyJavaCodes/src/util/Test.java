package util;

import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		LinkedList<Integer> ll = new LinkedList<>();
		ll.add(1);
		ll.add(2);
		ll.add(3);
		List<Integer> sl = ll.subList(0, 2);
		sl.set(1, 100);
		System.out.println(ll);
		System.out.println(sl);
	}
}
