package algorithm.leetcode.medium;

public class P24_SwapNodesinPairs {

	// 3:15~
	public static void main(String[] args) {
		ListNode ln = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
		System.out.println(new P24_SwapNodesinPairs().swapPairs(ln));
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
			return val + (next == null ? "" : " -> " + next);
		}
	}

	public ListNode swapPairs(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode n = head.next;
		head.next = n.next == null ? null : swapPairs(n.next);
		n.next = head;
		return n;
	}
}
