package algorithm.bj;

import java.util.*;

public class c_11657_��������_ok {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		Map<Integer, Map<Integer, Integer>> es = new HashMap<>();

		// �Է��� �޴´�
		int vl = scanner.nextInt();
		int el = scanner.nextInt();
		for (int i = 0; i < el; i++) {
			int from = scanner.nextInt();
			int to = scanner.nextInt();
			int time = scanner.nextInt();
			es.computeIfAbsent(from, k->new HashMap<>()).compute(to, (k,v)->v==null?time:Math.min(v, time));
		}

		// solution
		int[] times = new int[vl + 1];
		Arrays.fill(times, 2, vl + 1, Integer.MAX_VALUE);

		// v-1 �� ���� ���̳ʽ���ȯ? �� �ִ��� Ȯ���ϱ� ���� v �� ����
		for (int i = 1; i <= vl; i++) {
			// 0, 1, 2 ������� relaxation �� �Ѵ�
			for (int j = 1; j <= vl; j++) {
				if (!es.containsKey(j) || times[j] == Integer.MAX_VALUE) {
					continue;
				}
				for(Map.Entry<Integer, Integer> e : es.get(j).entrySet() ) {
					int to = e.getKey();
					int w = e.getValue();
					if (times[to] > times[j] + w) {
						if (vl == i) {
							System.out.println(-1);
							return;
						}
						times[to] = times[j] + w;
					}	
				}
			}
		}

		Arrays.stream(times).skip(2).forEach(i -> System.out.println(i == Integer.MAX_VALUE ? -1 : i));
	}
}
