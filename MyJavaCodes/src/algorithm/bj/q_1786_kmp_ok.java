package algorithm.bj;
import java.util.*;
public class q_1786_kmp_ok {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
	
		String text = scanner.nextLine();
		String pat = scanner.nextLine();
		
		int[] lps = new int[pat.length()];
		setLps(lps, pat);
		
		List<Integer> idxs = new ArrayList<>();
		int i=0,j=0;
		while(i<text.length()) {
			if(text.charAt(i)==pat.charAt(j)) {
				i++;
				if(++j==pat.length()) {
					idxs.add(i-j);
					j=lps[j-1];
				}
			} else {
				if(j==0)
					i++;
				else
					j=lps[j-1];
			}
		}
		
		System.out.println(idxs.size());
		idxs.forEach(idx->System.out.println(idx+1));
	}
	private static void setLps(int[] lps, String pat) {
		
		int i=1, j=0;
		while(i<pat.length()) {
			if(pat.charAt(i)==pat.charAt(j))
				lps[i++]=(j++)+1;
			else {
				if(j==0)
					i++;
				else
					j=lps[j-1];
			}
		}
	}
}
