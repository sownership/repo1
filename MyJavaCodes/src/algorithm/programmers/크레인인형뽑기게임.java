package algorithm.programmers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class 크레인인형뽑기게임 {

	public static void main(String[] args) {
		int[][] board = new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 3 }, { 0, 2, 5, 0, 1 }, { 4, 2, 4, 4, 2 },
				{ 3, 5, 1, 3, 1 } };
		int[] moves = new int[] { 1, 5, 3, 5, 1, 2, 1, 4 };
		System.out.println(new 크레인인형뽑기게임().solution(board, moves));
	}

	public int solution(int[][] board, int[] moves) {
		int answer = 0;

		List<LinkedList<Integer>> lists = new ArrayList<>();
		for (int i = 0; i < board.length; i++)
			lists.add(new LinkedList<>());

		for (int[] bs : board)
			for (int i = 0; i < bs.length; i++)
				if (bs[i] != 0)
					lists.get(i).add(bs[i]);

		LinkedList<Integer> bucket = new LinkedList<>();
		for (int move : moves) {
			if (lists.get(move - 1).isEmpty())
				continue;
			int next = lists.get(move - 1).removeFirst();
			if (!bucket.isEmpty() && bucket.getLast() == next) {
				bucket.removeLast();
				answer += 2;
			} else
				bucket.add(next);
		}

		return answer;
	}
}
