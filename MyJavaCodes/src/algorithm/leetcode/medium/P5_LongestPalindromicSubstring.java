package algorithm.leetcode.medium;

public class P5_LongestPalindromicSubstring {

	public static void main(String[] args) {
		// bab
		System.out.println(new P5_LongestPalindromicSubstring().longestPalindrome("babad"));
		// bb
		System.out.println(new P5_LongestPalindromicSubstring().longestPalindrome("cbbd"));
	}

	public String longestPalindrome(String s) {
		if(s==null || s.length()==0) return "";
		char[] cs=s.toCharArray();
		int n=s.length();
		boolean[][] dp=new boolean[n][n];
		int ri=0, rj=0, max=0;
		for(int i=n-1;i>=0;i--) {
			for(int j=i;j<n;j++) {
				dp[i][j]=cs[i]==cs[j] && (j-i<3 || dp[i+1][j-1]);
				if(dp[i][j] && j-i+1>max) {
					max=j-i+1;
					ri=i;
					rj=j;
				}
			}
		}
		return s.substring(ri,rj+1);
	}
}
