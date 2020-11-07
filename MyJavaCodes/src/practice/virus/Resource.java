package practice.virus;

import java.util.ArrayList;
import java.util.List;

public class Resource {

	public static List<String> getPeople() {
		
		List<String> ls = new ArrayList<>();
		ls.add("P001 00:00 00:30 L1");
		ls.add("P001 00:40 01:00 L1");
		ls.add("P001 01:00 01:30 L2");
		ls.add("P001 01:40 02:00 L3");
		ls.add("P001 02:10 02:30 L1");
		
		ls.add("P002 00:35 00:45 L1");
		ls.add("P002 00:50 01:20 L2");
		ls.add("P002 01:30 02:00 L4");
		
		ls.add("P003 00:00 00:50 L1");
		ls.add("P003 01:00 01:30 L5");
		
		ls.add("P004 01:30 03:00 L4");
		
		ls.add("P005 01:00 01:40 L5");
		ls.add("P005 01:30 02:30 L4");
		
		return ls;
	}
}
