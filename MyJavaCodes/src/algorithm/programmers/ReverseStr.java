package algorithm.programmers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ReverseStr {
	public String reverseStr(String str) {

		List<String> list = new LinkedList<>();
		for(int i=0; i<str.length(); i++) {
			list.add(str.substring(i, i+1));
		}
		list.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if(o1.compareTo("a")>=0 && o1.compareTo("z")<=0) {
					if(o2.compareTo("a")>=0 && o2.compareTo("z")<=0) {
						return o2.compareTo(o1);
					} else {
						return -1;
					}
				} else {
					if(o2.compareTo("a")>=0 && o2.compareTo("z")<=0) {
						return 1;
					} else {
						return o2.compareTo(o1);
					}
				}
			}
		});
		StringBuffer sb = new StringBuffer();
		for(String s : list) {
			sb.append(s);
		}
		return sb.toString();
	}

	// 아래는 테스트로 출력해 보기 위한 코드입니다.
	public static void main(String[] args) {
		ReverseStr rs = new ReverseStr();
		System.out.println(rs.reverseStr("Zbcdefg"));
	}
}
