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
		//first 가 base 를 변경한 내역을 구한다. base 각 라인은 first 에 S,M,D 중 하나 후 A 가 있을 수 있다
		Map<Integer, List<E>> merged1 = merge(base, first);
		print(merged1);

		//second 가 base 를 변경한 내역을 구한다. base 각 라인은 second 에 S,M,D 중 하나 후 A 가 있을 수 있다
		Map<Integer, List<E>> merged2 = merge(base, second);
		print(merged2);

		//first, second 가 수정한 결과 base 의 최종 변경내역을 취합한다
		Map<Integer, List<E>> merged = new HashMap<>();
		//-1 은 base 의 첫 라인 앞에 추가된 라인들이며 first 가 추가한거부터 넣는다
		merged.put(-1, Stream.concat(merged1.get(-1).stream(), merged2.get(-1).stream()).collect(Collectors.toList()));

		//base 의 0~끝까지 각 라인에 대해 first, second 가 변경한 내역을 취합한다
		IntStream.range(0, base.size()).forEach(i -> {
			//각 라인의 변경내역을 취합하여 저장할 list
			List<E> esOfi = new LinkedList<>();
			merged.put(i, esOfi);

			//merged1, merged2 의 i 번째 값들로 merged 의 i 번째  값을 만든다
			List<E> el1 = merged1.get(i);
			List<E> el2 = merged2.get(i);
			Stream<E> stream1 = el1.stream();
			Stream<E> stream2 = el2.stream();

			//base 의 i 번째 라인을 first 가 변경하지 않은 경우
			if (el1.get(0).state.equals("S")) {
				// base 의 i 번째 라인을 second 가 수정이나 삭제한 경우
				if (el2.get(0).state.equals("M") || el2.get(0).state.equals("D")) {
					// second 가 수정, 삭제한 라인을 적용한다
					esOfi.add(el2.get(0));
					// second 의 M, D 를 적용하므로 first 의 S 는 skip
					stream1 = stream1.skip(1);
				}
				//S,S 면 first S 를 취하고 S,(M,D) 면 위에서 (M,D) 를 먼저 사용했으므로 second 의 첫번째는 무조건 skip
				stream2 = stream2.skip(1);

			} else if (el1.get(0).state.equals("M") || el1.get(0).state.equals("D")) {
				//base 의 i번째 라인을 first 가 변경한 경우 second 는 해당 라인을 변경하지 않았으므로 첫번째는 skip
				stream2 = stream2.skip(1);

			} else if (el1.get(0).state.equals("A")) {
				// a부터 나오는 경우는 map key==-1 인 경우임
			}
			//first 변경내역과 second 변경내역을 합친다
			Stream.concat(stream1, stream2).forEach(e -> esOfi.add(e));
		});
		print(merged);

		//최종 변경내역인 map 을 line 번호로 정렬 후 line 번호는 떼고 값인 List<E> 의 List 를 List<E> 로 바꾸고
		//삭제된 라인을 제거하고 E 의 line 값들만 list 로 변경한다(최종 merge 된 lines)
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
		// other 가 base 를 어떻게 변경했는지 base line 번호 기준으로 정리한다
		Map<Integer, List<E>> merged = new HashMap<>();
		
		// -1 라인은 base 0라인보다 앞에 추가된 값들이다
		merged.put(-1, new LinkedList<>());
		
		// 우선 base 각 라인에 대해 모두 S 로 넣어서 초기화한다
		IntStream.range(0, base.size()).forEach(i -> {
			merged.put(i, new LinkedList<>(Arrays.asList(new E("S", base.get(i)))));
		});

		//base i 라인과 other j 라인이 같았다면 i+1 라인에 대해서는 j 라인 이후부터 검색하기 위해 j+1 라인을 담아두는 변수
		AtomicInteger otherIdx = new AtomicInteger(0);
		//base 각 라인에 대해
		IntStream.range(0, base.size()).forEach(bi -> {
			// other 에서 base bi 라인과 같은 라인이 있는지 찾는다
			OptionalInt matched = IntStream.range(otherIdx.get(), other.size())
					.filter(oi -> base.get(bi).equals(other.get(oi))).findFirst();
			// 이미 S 는 초기화할 때 넣어놨으므로 S를 다시 넣을 필요는 없고
			if (matched.isPresent()) {
				// base bi 라인 앞라인(bi-1)에 추가된 내용들이 있으면 반영한다
				IntStream.range(otherIdx.get(), matched.getAsInt())
						.forEach(i -> merged.get(bi - 1).add(new E("A", other.get(i))));

				//S 다음 라인부터 진행하기 위해 위치를 저장한다
				otherIdx.set(matched.getAsInt() + 1);
				return;
			}

			// other 에서 base bi 라인과 같은 라인이 없으면 수정한 라인이라도 있는지 찾는다
			// 수정의 기준은 앞뒤라인이 같은데 중간라인이 다른 경우이므로 base 앞뒤라인이 있는 경우만 진행한다
			if (bi - 1 >= 0 && bi + 1 < base.size()) {
				OptionalInt modified = IntStream.range(otherIdx.get(), other.size()).filter(fi -> {
					//other 현재 라인 앞라인이 base 현재 라인 앞라인과 같고
					if (fi - 1 >= 0 && base.get(bi - 1).equals(other.get(fi - 1))) {
						//other 현재 라인 뒷라인이 base 현재 라인 뒷라인과 같으면 수정 라인
						if (fi + 1 < other.size() && base.get(bi + 1).equals(other.get(fi + 1))) {
							return true;
						}
					}
					return false;
				}).findFirst();
				if (modified.isPresent()) {
					// base bi 라인 앞라인(bi-1)에 추가된 내용들이 있으면 반영한다
					IntStream.range(otherIdx.get(), modified.getAsInt())
							.forEach(i -> merged.get(bi - 1).add(new E("A", other.get(i))));

					// 수정라인으로 변경사항을 반영한다
					merged.get(bi).set(0, new E("M", other.get(modified.getAsInt())));
					// M 다음 라인부터 진행하기 위해 위치를 저장한다
					otherIdx.set(modified.getAsInt() + 1);
					return;
				}
			}

			// S,M 이 없으면 D 이다
			merged.get(bi).set(0, new E("D", base.get(bi)));
		});

		// base 마지막까지 한 후에 other 에 남은 것들이 있으면 base 마지막 라인에 A 된 것들이다
		other.stream().skip(otherIdx.get()).forEach(l -> merged.get(base.size() - 1).add(new E("A", l)));

		return merged;
	}
}
