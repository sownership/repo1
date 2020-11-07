package algorithm.leetcode.medium;

public class P2_AddTwoNum {

	// 3:17~3:27~3:51 11:03~11:26
	public static void main(String[] args) {

		//807
		ListNode l1=new ListNode(2, new ListNode(4, new ListNode(3)));
		ListNode l2=new ListNode(5, new ListNode(6, new ListNode(4)));
		
//		ListNode l1=new ListNode(9);
//		ListNode l2=new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))))))));
		
		ListNode r=new P2_AddTwoNum().addTwoNumbers(l1, l2);
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
		
		String s1=get(l1);
		String s2=get(l2);
		String sum=sum(s1, s2);
		return make(sum, 1);
	}
	
	private ListNode make(String sum, int i) {
		if(sum.length()-i<0) return null;
		return new ListNode(Integer.valueOf(sum.substring(sum.length()-i, sum.length()-i+1)), make(sum, i+1));
	}
	
	private String sum(String s1, String s2) {
		String r="";
		boolean over=false;
		for(int i=0;i<Math.max(s1.length(), s2.length());i++) {
			int n1=0;
			if(s1.length()-1-i>=0) {
				n1=Integer.valueOf(s1.substring(s1.length()-1-i, s1.length()-i));
			}
			int n2=0;
			if(s2.length()-1-i>=0) {
				n2=Integer.valueOf(s2.substring(s2.length()-1-i, s2.length()-i));
			}
			if(s1.length()-1-i>=0 || s2.length()-1-i>=0) {
				r=((n1+n2+(over?1:0))%10)+r;
				if(n1+n2+(over?1:0)>=10) over=true;
				else over=false;
			} else
				break;
		}
		return over?1+r:r;
	}
	
	private String get(ListNode l) {
	    return l.next==null?("" + l.val):("" + get(l.next) + l.val);
	}
}
