package algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class P22_GenerateParentheses {

	// 8:55~
	public static void main(String[] args) {
//		System.out.println(new P22_GenerateParentheses().generateParenthesis(3));
		System.out.println(new P22_GenerateParentheses().generateParenthesis(15).size());
		System.out.println(new P22_GenerateParentheses().generateParenthesis2(15).size());
	}

	public List<String> generateParenthesis2(int n) {
        List<String> ans = new ArrayList<>();
        if (n == 0) {
            ans.add("");
        } else {
            for (int c = 0; c < n; ++c)
                for (String left: generateParenthesis(c))
                    for (String right: generateParenthesis(n-1-c))
                        ans.add("(" + left + ")" + right);
        }
        return ans;
    }
	
	public List<String> generateParenthesis(int n) {
		List<String> r = new ArrayList<>();
		r(r, new char[n * 2], n, 0, 0);
		return r;
	}

	private void r(List<String> r, char[] tmp, int n, int open, int close) {
		if (open + close == n * 2) {
			r.add(new String(tmp));
			return;
		}
		if (open < n) {
			tmp[open + close] = '(';
			r(r, tmp, n, open + 1, close);
		}
		if (close < open) {
			tmp[open + close] = ')';
			r(r, tmp, n, open, close + 1);
		}
	}
}
