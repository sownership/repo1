package algorithm.programmers;

public class Caesar {
	String caesar(String s, int n) {
		
		char[] ca = s.toCharArray();
		for(int i=0; i<ca.length; i++) {
			ca[i] = next(ca[i], n);
		}
		
		return new String(ca);
	}

	private char next(char c, int n) {
		if(c>='a' && c<='z') {
			return (char) ('a'+(c-'a'+n)%('z'-'a'+1));
		} else if(c>='A' && c<='Z') {
			return (char) ('A'+(c-'A'+n)%('Z'-'A'+1));
		}
		return c;
	}

	public static void main(String[] args) {
		Caesar c = new Caesar();
		System.out.println("s는 'a B z', n은 4인 경우: " + c.caesar("JbrPZi",43));
	}
}
