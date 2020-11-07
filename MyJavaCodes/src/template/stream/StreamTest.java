package template.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) throws InterruptedException {

		test01();
//		innerJoin();
//		leftJoin();
//		fullJoin();
//		sort();
	}

	private static void sort() {
		int[] ia={2,4,1,5};
		int[] reverse=Arrays.stream(ia).boxed().sorted(Collections.reverseOrder()).mapToInt(i->i).toArray();
		System.out.println(Arrays.toString(reverse));
	}

	static class C1 {
		String id;
		String at;
		String v;

		public C1(String id, String at, String v) {
			this.id = id;
			this.at = at;
			this.v = v;
		}
	}

	static class C2 {
		String at;
		String a;

		public C2(String at, String a) {
			this.at = at;
			this.a = a;
		}
	}

	static class C3 {
		String id;
		String at;
		String v;
		String a;

		public C3(String id, String at, String v, String a) {
			this.id = id;
			this.at = at;
			this.v = v;
			this.a = a;
		}

		@Override
		public String toString() {
			return String.format("%s %s %s %s", id, at, v, a);
		}
	}

	private static void innerJoin() {
		List<String> s1L = new ArrayList<>();
		s1L.add("id1 at1 1");
		s1L.add("id2 at1 0");
		s1L.add("id5 at3 0");
		s1L.add("id5 at3 4");
		s1L.add("id5 at1 4");
		s1L.add("id5 at5 9");
		s1L.add("id3 at2 5");
		s1L.add("id4 at3 7");
		List<C1> c1L = s1L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C1(se[0], se[1], se[2]);
		}).collect(Collectors.toList());

		List<String> s2L = new ArrayList<>();
		s2L.add("at1 a1");
		s2L.add("at1 a2");
		s2L.add("at3 a3");
		s2L.add("at2 a4");
		s2L.add("at2 a5");
		List<C2> c2L = s2L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C2(se[0], se[1]);
		}).collect(Collectors.toList());

		List<C3> c3L = c1L.stream()
				.map(c1 -> c2L.stream().filter(c2 -> c2.at.equals(c1.at)).map(c2 -> new C3(c1.id, c1.at, c2.a, c1.v)))
				.flatMap(Function.identity()).collect(Collectors.toList());
		c3L.forEach(System.out::println);
	}

	private static void fullJoin() {
		List<String> s1L = new ArrayList<>();
		s1L.add("id1 at1 1");
		s1L.add("id2 at1 0");
		s1L.add("id5 at3 0");
		s1L.add("id5 at3 4");
		s1L.add("id5 at1 4");
		s1L.add("id5 at5 9");
		s1L.add("id3 at2 5");
		s1L.add("id4 at3 7");
		List<C1> c1L = s1L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C1(se[0], se[1], se[2]);
		}).collect(Collectors.toList());

		List<String> s2L = new ArrayList<>();
		s2L.add("at1 a1");
		s2L.add("at1 a2");
		s2L.add("at3 a3");
		s2L.add("at2 a4");
		s2L.add("at2 a5");
		s2L.add("at7 a5");
		List<C2> c2L = s2L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C2(se[0], se[1]);
		}).collect(Collectors.toList());

		Stream<C3> rslt1 = c1L.stream().map(c1 -> {
			List<C3> c3L = new ArrayList<>();
			List<C2> c2L2 = c2L.stream().filter(c2 -> c2.at.equals(c1.at)).collect(Collectors.toList());
			if (c2L2.size() > 0) {
				for (C2 c2 : c2L2) {
					c3L.add(new C3(c1.id, c1.at, c2.a, c1.v));
				}
			}
			return c3L;
		}).flatMap(c3l -> c3l.stream());

		Stream<C3> rslt2 = c2L.stream().filter(c2 -> c1L.stream().noneMatch(c1 -> c1.at.equals(c2.at)))
				.map(c1 -> new C3("", c1.at, c1.a, ""));

		List<C3> rslt = Stream.concat(rslt1, rslt2).collect(Collectors.toList());

		rslt.forEach(System.out::println);
	}

	private static void leftJoin() {
		List<String> s1L = new ArrayList<>();
		s1L.add("id1 at1 1");
		s1L.add("id2 at1 0");
		s1L.add("id5 at3 0");
		s1L.add("id5 at3 4");
		s1L.add("id5 at1 4");
		s1L.add("id5 at5 9");
		s1L.add("id3 at2 5");
		s1L.add("id4 at3 7");
		List<C1> c1L = s1L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C1(se[0], se[1], se[2]);
		}).collect(Collectors.toList());

		List<String> s2L = new ArrayList<>();
		s2L.add("at1 a1");
		s2L.add("at1 a2");
		s2L.add("at3 a3");
		s2L.add("at2 a4");
		s2L.add("at2 a5");
		List<C2> c2L = s2L.stream().map(s -> {
			String[] se = s.split(" ");
			return new C2(se[0], se[1]);
		}).collect(Collectors.toList());

		List<C3> c3L = c1L.stream().map(c1 -> {
			List<C3> c3L2 = new ArrayList<>();
			List<C2> c2L2 = c2L.stream().filter(c2 -> c2.at.equals(c1.at)).collect(Collectors.toList());
			if (c2L2.size() > 0) {
				for (C2 c2 : c2L2) {
					c3L2.add(new C3(c1.id, c1.at, c2.a, c1.v));
				}
			} else {
				c3L2.add(new C3(c1.id, c1.at, "", c1.v));
			}
			return c3L2;
		}).flatMap(lc3 -> lc3.stream()).collect(Collectors.toList());

		c3L.forEach(System.out::println);
	}

	static class E {
		String at;
		long cnt;

		E(String at, long cnt) {
			this.at = at;
			this.cnt = cnt;
		}
		
		@Override
		public String toString() {
			return at + "=" + cnt;
		}
	}

	private static void test01() {
		List<String> ls = new ArrayList<>();
		ls.add("id1 at1 1");
		ls.add("id2 at1 0");
		ls.add("id5 at3 0");
		ls.add("id5 at3 4");
		ls.add("id5 at1 4");
		ls.add("id5 at5 9");
		ls.add("id3 at2 5");
		ls.add("id4 at3 7");

		List<E> es = ls.stream().collect(Collectors.groupingBy(s -> s.substring(4, 7), Collectors.counting()))
				.entrySet().stream().filter(e -> e.getValue() > 0).map(e -> new E(e.getKey(), e.getValue()))
				.sorted((e1, e2) -> (int) (e2.cnt - e1.cnt)).collect(Collectors.toList());
		System.out.println(es);

		System.out.println();

		Map<String, Set<String>> map3 = ls.stream().collect(
				Collectors.groupingBy(s -> s.substring(4, 7), TreeMap::new, Collectors.mapping(s -> s.substring(0, 3),
						Collectors.toCollection(() -> new TreeSet<>((id1, id2) -> id2.compareTo(id1))))));
		System.out.println(map3);
	}
}
