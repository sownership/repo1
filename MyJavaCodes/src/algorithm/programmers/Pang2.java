package algorithm.programmers;

import java.util.LinkedList;
import java.util.List;

public class Pang2 {

	public static void main(String[] args) {
		Pang2 pang = new Pang2();

		// int[] in = new int[] { 1, 2, 2, 1, 2, 3, 1, 3, 3, 1 };
		// int[] in = new int[] { 1, 2, 2, 1, 1, 3, 1, 3, 3, 1 , 1, 2, 2, 2, 2, 3, 1, 3,
		// 3, 1 , 1, 2, 2, 1, 2, 3, 1, 3, 3, 1 };
		int[] in = new int[] {1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2,1,1,1,1,1,2,2,2,1,2,2};
		System.out.println(pang.solution(in));
	}

	public int solution(int[] in) {

		// 각 연속된 숫자별 갯수를 구한다
		LinkedList<Integer> vl = new LinkedList<>();
		LinkedList<Integer> il = new LinkedList<>();
		vl.add(in[0]);
		il.add(1);
		for (int i = 1; i < in.length; i++) {
			if (in[i] == vl.getLast()) {
				il.set(il.size() - 1, il.getLast() + 1);
			} else {
				vl.add(in[i]);
				il.add(1);
			}
		}

		return recursive(vl, il, new LinkedList<>());
	}

	private int recursive(List<Integer> vl, List<Integer> il, LinkedList<Integer> dp) {

		// System.out.println("vl: " + vl);
		// System.out.println("il: " + il);
		// System.out.println("reusedp: " + reusedp);

		if (dp.size() == 0) {
			dp.add(il.get(0) * il.get(0));
		}
		// dp 를 구한다
		for (int i = dp.size(); i < vl.size(); i++) {
			int before = -1;
			// 이전 같은 숫자 위치를 찾는다
			for (int j = i - 2; j >= 0; j--) {
				if (vl.get(j) == il.get(i)) {
					before = j;
					break;
				}
			}
			int chain = 0;
			// 이전 같은 숫자가 있으면 연결할 경우 최대 가치를 구한다
			if (before >= 0) {
				// 이전 같은 숫자와 연결했을 경우 재활용할 수 있는 dp 가 있다면 넘겨준다
				LinkedList<Integer> toreusedp = new LinkedList<>();
				for (int j = 0; j < before; j++) {
					toreusedp.add(dp.get(j));
				}
				// 이전 같은 숫자와 연결했을 경우의 리스트를 만든다
				LinkedList<Integer> il2 = new LinkedList<>();
				il2.addAll(il.subList(0, before + 1));
				il2.set(il2.size() - 1, il2.get(before) + il.get(i));
				chain = recursive(vl.subList(before + 1, i), il.subList(before + 1, i), new LinkedList<>())
						+ recursive(vl.subList(0, before + 1), il2, toreusedp);
			}
			int notchain = il.get(i) * il.get(i) + dp.get(i - 1);
			// 최대 가치는 현재 숫자를 이전 같은 숫자와 합치는 경우와 안합치는 경우의 최대
			dp.add(Math.max(notchain, chain));
		}
		// System.out.println(Arrays.toString(dp));

		return dp.getLast();
	}
}
