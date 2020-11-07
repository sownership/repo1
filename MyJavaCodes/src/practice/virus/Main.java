package practice.virus;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		
		Map<String, List<String>> map = Resource.getPeople().stream().collect(groupingBy(l->l.substring(0,4), mapping(l->l, toList())));
		map.entrySet().forEach(System.out::println);
		
		System.out.println();
		
		Map<String, List<String>> map2 = Resource.getPeople().stream().collect(groupingBy(l->l.substring(0,4), TreeMap::new, mapping(l->l, toList())));
		map2.entrySet().forEach(System.out::println);
	}
}
