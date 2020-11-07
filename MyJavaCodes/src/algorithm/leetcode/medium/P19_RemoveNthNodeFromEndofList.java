package algorithm.leetcode.medium;

public class P19_RemoveNthNodeFromEndofList {

	public static void main(String[] args) {
//		ListNode n1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
		ListNode n1 = new ListNode(1, new ListNode(1, null));
		System.out.println(new P19_RemoveNthNodeFromEndofList().removeNthFromEnd(n1, 2));
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}

		@Override
		public String toString() {
			if (next == null) {
				return "" + val;
			} else {
				return val + "->" + next.toString();
			}
		}
	}

	public ListNode removeNthFromEnd(ListNode head, int n) {
		int r = r(head, n);
		if (r == n)
			head = head.next;
		return head;
	}

	private int r(ListNode head, int n) {
		int r = 0;
		if (head.next == null)
			r = 1;
		else
			r = 1 + r(head.next, n);
		if (r == n + 1)
			head.next = head.next.next;
		return r;
	}
}
