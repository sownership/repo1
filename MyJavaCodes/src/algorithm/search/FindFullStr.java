package algorithm.search;

public class FindFullStr {

	public static void main(String[] args) {
		// String in = "bcg,fbc,efbcg,def,cde,abcde,abc";
		String in = "afg,eaf,cea,dceaf,cdc,bcd,abc";

		String[] ins = in.split(",");
		tmp = new char[ins.length][9];

		System.out.println(recursive(ins, 0));
	}

	private static String recursive(String[] ins, int i) {

		if (i >= ins.length) {
			return null;
		}
		for (int j = 0; j < 9; j++) {
			if (j + ins[i].length() > 9) {
				break;
			}
			for (int k = 0; k < ins[i].length(); k++) {
				tmp[i][j + k] = ins[i].charAt(k);
			}
			if (i == ins.length - 1 && isSuccess()) {
				return getResult();
			}
			String result = recursive(ins, i + 1);
			if (result != null) {
				return result;
			}
			for (int k = 0; k < ins[i].length(); k++) {
				tmp[i][j + k] = 0;
			}
		}
		return null;
	}

	private static String getResult() {
		String r = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < tmp.length; j++) {
				if (tmp[j][i] != 0) {
					r += tmp[j][i];
					break;
				}
			}
		}
		return r;
	}

	private static boolean isSuccess() {
		for (int i = 0; i < 9; i++) {
			char cur = 0;
			for (int j = 0; j < tmp.length; j++) {
				if (tmp[j][i] != 0) {
					if (cur == 0) {
						cur = tmp[j][i];
					} else if (cur != tmp[j][i]) {
						return false;
					}
				}
			}
			if (cur == 0) {
				return false;
			}
		}
		return true;
	}

	private static char[][] tmp;
}
