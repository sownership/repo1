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
	
	// ������ ���� main�Դϴ�.
	public static void main(String[] args) {
		CorrectParenthesis cp = new CorrectParenthesis();
		if (cp.parenthesisCase(16).equals(new BigInteger("100"))) {
			System.out.println("parenthesisCase(3)�� ���� �����մϴ�. ������ ������ �ٸ� ��쿡�� �������� Ȯ���� ������.");
		} else {
			System.out.println("parenthesisCase(3)�� ���� �������� �ʽ��ϴ�.");
		}
	}
}
