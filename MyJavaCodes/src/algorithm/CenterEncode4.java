package algorithm;

public class CenterEncode4 {

	public static void main(String[] args) {
		//String in = "bcg,fbc,efbcg,def,cde,abcde,abc";
		String in = "afg,eaf,cea,dceaf,cdc,bcd,abc";
		long t = System.currentTimeMillis();

		String[] ins = in.split(",");
		tmp = new char[ins.length][9];

		System.out.println(recursive(ins, 0));
		System.out.println(System.currentTimeMillis() - t);
	}

	static char[][] tmp;

	private static String recursive(String[] ins, int i) {
		if (i >= ins.length) {
			return null;
		}
		for (int j = 0; j < 9; j++) {
			if (j + ins[i].length() > 9) {
				continue;
			}
			for (int k = 0; k < ins[i].length(); k++) {
				tmp[i][j + k] = ins[i].charAt(k);
			}
			if (i == ins.length - 1) {
				//printTmp(ins.length);
			}
			if (i == ins.length - 1 && isSuccess(ins.length)) {
				return getResult(ins.length);
			}
			String r = recursive(ins, i + 1);
			if (r != null) {
				return r;
			}
			for (int k = 0; k < ins[i].length(); k++) {
				tmp[i][j + k] = 0;
			}
		}
		return null;
	}

	private static void printTmp(int inLen) {
		for (int i = 0; i < inLen; i++) {
			System.out.print("[");
			for (int j = 0; j < 9; j++) {
				System.out.print(tmp[i][j] + ",");
			}
			System.out.print("]");
		}
		System.out.println();
	}

	private static String getResult(int inLen) {
		String r = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < inLen; j++) {
				if (tmp[j][i] != 0) {
					r += tmp[j][i];
					break;
				}
			}
		}
		return r;
	}

	private static boolean isSuccess(int inLen) {
		for (int i = 0; i < 9; i++) {
			char tc = 0;
			for (int j = 0; j < inLen; j++) {
				if (tmp[j][i] != 0) {
					if (tc == 0) {
						tc = tmp[j][i];
					}
					if (tc != tmp[j][i]) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
