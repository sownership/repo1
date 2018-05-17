package algorithm.programmers;

import java.math.BigInteger;

public class CorrectParenthesis {
	public BigInteger parenthesisCase(int n) {
//		BigInteger answer = new BigInteger("0");
//
//		return answer;
		
		char[] arr = new char[2*n];
		dfs(arr, 0, 0, 0);
		return new BigInteger(String.valueOf(result));
	}

	private static int result;
	
	private static void dfs(char[] arr, int index, int left, int right) {
		
		if(left==arr.length/2 && right==arr.length/2) {
			result++;
			return;
		}
		
		if(left<arr.length/2) {
			arr[index] = '(';
			dfs(arr, index+1, left+1, right);
			arr[index] = 'x';	
		}
		
		if(right<arr.length/2 && right<left) {
			arr[index] = '(';
			dfs(arr, index+1, left, right+1);
			arr[index] = 'x';
		}
	}
	
	// 실행을 위한 main입니다.
	public static void main(String[] args) {
		CorrectParenthesis cp = new CorrectParenthesis();
		if (cp.parenthesisCase(16).equals(new BigInteger("100"))) {
			System.out.println("parenthesisCase(3)이 정상 동작합니다. 제출을 눌러서 다른 경우에도 정답인지 확인해 보세요.");
		} else {
			System.out.println("parenthesisCase(3)이 정상 동작하지 않습니다.");
		}
	}
}
