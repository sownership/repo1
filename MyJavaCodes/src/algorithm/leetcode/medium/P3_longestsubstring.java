package algorithm.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

public class P3_longestsubstring {

	// 1:30~1:35~
	public static void main(String[] args) {
		// 3
		System.out.println(new P3_longestsubstring().lengthOfLongestSubstring("pwwkew"));
		// 1
		System.out.println(new P3_longestsubstring().lengthOfLongestSubstring("bbbbbbb"));
		// 5
		System.out.println(new P3_longestsubstring().lengthOfLongestSubstring("tmmzuxt"));
	}
	
	public int lengthOfLongestSubstring(String s) {
		int r = 0;
		int[] used=new int[128];
		int i=0,j=0,c=0;
		while(j<s.length()) {
			if(used[s.charAt(j)]==1) {
				if(s.charAt(i)==s.charAt(j)) {
					j++;
				} else {
					used[s.charAt(i)]=0;
					c--;
				}
				i++;
			} else {
				used[s.charAt(j++)]=1;
				r=Math.max(r, ++c);
			}
		}
		return r;
	}

	public int lengthOfLongestSubstring2(String s) {
		int r = 0;
		Set<Character> used = new HashSet<>();
		int i = 0, j = 0;
		while (j < s.length()) {
			if (used.contains(s.charAt(j))) {
				if (s.charAt(i) == s.charAt(j)) {
					j++;
				} else {
					used.remove(s.charAt(i));
				}
				i++;
			} else {
				used.add(s.charAt(j++));
				r = Math.max(r, used.size());
			}
		}
		return r;
	}
}
