package practice.twod;

import java.util.LinkedList;
import java.util.ListIterator;

public class 연속2개의수들삭제 {

	public static void main(String[] args) {
		method1();
		mehtod2(); //better
	}

	private static void mehtod2() {
		LinkedList<Integer> ll = new LinkedList<>();
		ll.add(1);
		ll.add(1);
		ll.add(2);
		ll.add(3);
		ll.add(3);
		ll.add(2);
		ll.add(4);

		LinkedList<Integer> ll2 = new LinkedList<>();
		for (int l : ll) {
			if (ll2.isEmpty()) {
				ll2.add(l);
			} else {
				if (ll2.getLast() == l) {
					ll2.removeLast();
				} else {
					ll2.add(l);
				}
			}
		}

		System.out.println(ll2);
	}

	private static void method1() {
		LinkedList<Integer> ll = new LinkedList<>();
		ll.add(1);
		ll.add(1);
		ll.add(2);
		ll.add(3);
		ll.add(3);
		ll.add(2);
		ll.add(4);

		ListIterator<Integer> li = ll.listIterator();
		while (true) {
			int first;
			if (li.hasPrevious()) {
				first = li.previous();
			} else if (li.hasNext()) {
				first = li.next();
			} else {
				break;
			}
			int second;
			if (li.hasNext()) {
				second = li.next();
			} else {
				break;
			}
			if (first == second) {
				li.remove();
				li.previous();
				li.remove();
			}
		}

		System.out.println(ll);
	}
}
