package practice.vcs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Merge {

	public static void main(String[] args) {
		List<String> base = new LinkedList<>();
		base.add("a");
		base.add("b");
		base.add("c");
		base.add("d");
		base.add("e");
		List<String> first = new LinkedList<>();
		first.add("r");
		first.add("s");
		first.add("a");
		first.add("b");
		first.add("c");
		first.add("d");
		first.add("e");
		List<String> second = new LinkedList<>();
		second.add("p");
		second.add("q");
		second.add("a");
		second.add("b");
		second.add("c");
		second.add("d");
		second.add("f");
		second.add("e21");
		second.add("e22");

		System.out.println(new Merge().merge(base, first, second));
	}

	static class E {
		String state;
		String line;

		public E(String state, String line) {
			this.state = state;
			this.line = line;
		}

		@Override
		public String toString() {
			return state + "#" + line;
		}
	}

	List<String> merge(List<String> base, List<String> first, List<String> second) {
		//first �� base �� ������ ������ ���Ѵ�. base �� ������ first �� S,M,D �� �ϳ� �� A �� ���� �� �ִ�
		Map<Integer, List<E>> merged1 = merge(base, first);
		print(merged1);

		//second �� base �� ������ ������ ���Ѵ�. base �� ������ second �� S,M,D �� �ϳ� �� A �� ���� �� �ִ�
		Map<Integer, List<E>> merged2 = merge(base, second);
		print(merged2);

		//first, second �� ������ ��� base �� ���� ���泻���� �����Ѵ�
		Map<Integer, List<E>> merged = new HashMap<>();
		//-1 �� base �� ù ���� �տ� �߰��� ���ε��̸� first �� �߰��Ѱź��� �ִ´�
		merged.put(-1, Stream.concat(merged1.get(-1).stream(), merged2.get(-1).stream()).collect(Collectors.toList()));

		//base �� 0~������ �� ���ο� ���� first, second �� ������ ������ �����Ѵ�
		IntStream.range(0, base.size()).forEach(i -> {
			//�� ������ ���泻���� �����Ͽ� ������ list
			List<E> esOfi = new LinkedList<>();
			merged.put(i, esOfi);

			//merged1, merged2 �� i ��° ����� merged �� i ��°  ���� �����
			List<E> el1 = merged1.get(i);
			List<E> el2 = merged2.get(i);
			Stream<E> stream1 = el1.stream();
			Stream<E> stream2 = el2.stream();

			//base �� i ��° ������ first �� �������� ���� ���
			if (el1.get(0).state.equals("S")) {
				// base �� i ��° ������ second �� �����̳� ������ ���
				if (el2.get(0).state.equals("M") || el2.get(0).state.equals("D")) {
					// second �� ����, ������ ������ �����Ѵ�
					esOfi.add(el2.get(0));
					// second �� M, D �� �����ϹǷ� first �� S �� skip
					stream1 = stream1.skip(1);
				}
				//S,S �� first S �� ���ϰ� S,(M,D) �� ������ (M,D) �� ���� ��������Ƿ� second �� ù��°�� ������ skip
				stream2 = stream2.skip(1);

			} else if (el1.get(0).state.equals("M") || el1.get(0).state.equals("D")) {
				//base �� i��° ������ first �� ������ ��� second �� �ش� ������ �������� �ʾ����Ƿ� ù��°�� skip
				stream2 = stream2.skip(1);

			} else if (el1.get(0).state.equals("A")) {
				// a���� ������ ���� map key==-1 �� �����
			}
			//first ���泻���� second ���泻���� ��ģ��
			Stream.concat(stream1, stream2).forEach(e -> esOfi.add(e));
		});
		print(merged);

		//���� ���泻���� map �� line ��ȣ�� ���� �� line ��ȣ�� ���� ���� List<E> �� List �� List<E> �� �ٲٰ�
		//������ ������ �����ϰ� E �� line ���鸸 list �� �����Ѵ�(���� merge �� lines)
		return merged.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).map(e -> e.getValue())
				.flatMap(el -> el.stream()).filter(e -> !e.state.equals("D")).map(e -> e.line)
				.collect(Collectors.toList());
	}

	private void print(Map<Integer, List<E>> merged) {
		merged.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.forEach(e -> System.out.println(e.getKey() + "#" + e.getValue()));
		System.out.println();
	}

	private Map<Integer, List<E>> merge(List<String> base, List<String> other) {
		// other �� base �� ��� �����ߴ��� base line ��ȣ �������� �����Ѵ�
		Map<Integer, List<E>> merged = new HashMap<>();
		
		// -1 ������ base 0���κ��� �տ� �߰��� �����̴�
		merged.put(-1, new LinkedList<>());
		
		// �켱 base �� ���ο� ���� ��� S �� �־ �ʱ�ȭ�Ѵ�
		IntStream.range(0, base.size()).forEach(i -> {
			merged.put(i, new LinkedList<>(Arrays.asList(new E("S", base.get(i)))));
		});

		//base i ���ΰ� other j ������ ���Ҵٸ� i+1 ���ο� ���ؼ��� j ���� ���ĺ��� �˻��ϱ� ���� j+1 ������ ��Ƶδ� ����
		AtomicInteger otherIdx = new AtomicInteger(0);
		//base �� ���ο� ����
		IntStream.range(0, base.size()).forEach(bi -> {
			// other ���� base bi ���ΰ� ���� ������ �ִ��� ã�´�
			OptionalInt matched = IntStream.range(otherIdx.get(), other.size())
					.filter(oi -> base.get(bi).equals(other.get(oi))).findFirst();
			// �̹� S �� �ʱ�ȭ�� �� �־�����Ƿ� S�� �ٽ� ���� �ʿ�� ����
			if (matched.isPresent()) {
				// base bi ���� �ն���(bi-1)�� �߰��� ������� ������ �ݿ��Ѵ�
				IntStream.range(otherIdx.get(), matched.getAsInt())
						.forEach(i -> merged.get(bi - 1).add(new E("A", other.get(i))));

				//S ���� ���κ��� �����ϱ� ���� ��ġ�� �����Ѵ�
				otherIdx.set(matched.getAsInt() + 1);
				return;
			}

			// other ���� base bi ���ΰ� ���� ������ ������ ������ �����̶� �ִ��� ã�´�
			// ������ ������ �յڶ����� ������ �߰������� �ٸ� ����̹Ƿ� base �յڶ����� �ִ� ��츸 �����Ѵ�
			if (bi - 1 >= 0 && bi + 1 < base.size()) {
				OptionalInt modified = IntStream.range(otherIdx.get(), other.size()).filter(fi -> {
					//other ���� ���� �ն����� base ���� ���� �ն��ΰ� ����
					if (fi - 1 >= 0 && base.get(bi - 1).equals(other.get(fi - 1))) {
						//other ���� ���� �޶����� base ���� ���� �޶��ΰ� ������ ���� ����
						if (fi + 1 < other.size() && base.get(bi + 1).equals(other.get(fi + 1))) {
							return true;
						}
					}
					return false;
				}).findFirst();
				if (modified.isPresent()) {
					// base bi ���� �ն���(bi-1)�� �߰��� ������� ������ �ݿ��Ѵ�
					IntStream.range(otherIdx.get(), modified.getAsInt())
							.forEach(i -> merged.get(bi - 1).add(new E("A", other.get(i))));

					// ������������ ��������� �ݿ��Ѵ�
					merged.get(bi).set(0, new E("M", other.get(modified.getAsInt())));
					// M ���� ���κ��� �����ϱ� ���� ��ġ�� �����Ѵ�
					otherIdx.set(modified.getAsInt() + 1);
					return;
				}
			}

			// S,M �� ������ D �̴�
			merged.get(bi).set(0, new E("D", base.get(bi)));
		});

		// base ���������� �� �Ŀ� other �� ���� �͵��� ������ base ������ ���ο� A �� �͵��̴�
		other.stream().skip(otherIdx.get()).forEach(l -> merged.get(base.size() - 1).add(new E("A", l)));

		return merged;
	}
}
