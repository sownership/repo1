package algorithm.codingbat;

public class WordEnds {

	public static void main(String[] args) {
		WordEnds wordEnds = new WordEnds();
		// "c13i"
		System.out.println(wordEnds.wordEnds("abcXY123XYijk", "XY"));
		// "13"
		System.out.println(wordEnds.wordEnds("XY123XY", "XY"));
		// "11"
		System.out.println(wordEnds.wordEnds("XY1XY", "XY"));
	}

	public String wordEnds(String str, String word) {
		String result = "";
		int fromIndex = 0;
		while (fromIndex <= str.length() - 1) {
			int index = str.indexOf(word, fromIndex);
			if (index < 0) {
				break;
			}
			if (index - 1 >= 0) {
				result += str.charAt(index - 1);
			}
			if (index + word.length() <= str.length() - 1) {
				result += str.charAt(index + word.length());
			}
			fromIndex = index + word.length();
		}
		return result;
	}

	public String wordEnds2(String str, String word) {
		int slen = str.length();
		int wlen = word.length();
		String fin = "";

		for (int i = 0; i < slen - wlen + 1; i++) {
			String tmp = str.substring(i, i + wlen);
			if (i > 0 && tmp.equals(word))
				fin += str.substring(i - 1, i);
			if (i < slen - wlen && tmp.equals(word))
				fin += str.substring(i + wlen, i + wlen + 1);
		}
		return fin;
	}
}
