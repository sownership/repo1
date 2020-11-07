package algorithm.leetcode.medium;

public class P2_AddTwoNum2 {

	public static void main(String[] args) {

		//10
//		ListNode l1=new ListNode(5);
//		ListNode l2=new ListNode(5);
		
		//807
		ListNode l1=new ListNode(2, new ListNode(4, new ListNode(3)));
		ListNode l2=new ListNode(5, new ListNode(6, new ListNode(4)));
		
//		ListNode l1=new ListNode(9);
//		ListNode l2=new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))))))));
		
		ListNode r=new P2_AddTwoNum2().addTwoNumbers(l1, l2);
		System.out.println(r);
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
			if(next==null) return "" + val;
			return "" + next.toString() + val;
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		
		ListNode r = new ListNode();
		ListNode h=r;
		ListNode p=l1;
		ListNode q=l2;
		int carry=0;
		while(p!=null || q!=null) {
			int v1=p!=null?p.val:0;
			int v2=q!=null?q.val:0;
			h.next=new ListNode((v1+v2+carry)%10);
			carry=(v1+v2+carry)/10;
			
			p=p!=null?p.next:null;
			q=q!=null?q.next:null;
			h=h.next;
		}
		if(carry==1) h.next=new ListNode(1);
		
		return r.next;
	}
}
