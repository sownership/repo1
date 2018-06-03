package algorithm.search;

import java.util.Arrays;

public class Pang {

	public static void main(String[] args) {
		recursive("");
		System.out.println(maxScore + ":" + maxProgress);
	}

	private static int maxScore;
	private static String maxProgress;
	
	private static void recursive(String progress) {
		for (int i = 0; i < in.length; i++) {
			if (in.length <= i) {
				continue;
			}
			
			int[] inBak = in;
			int scoreBak = score;
			String progressTmp = progress;
			progressTmp += Integer.valueOf(in[i]);
			in = pang(i);
			if (in.length != 0) {
				recursive(progressTmp);
			} else {
				if(maxScore < score) {
					maxScore = score;
					maxProgress = progressTmp;
				}
				maxScore = Math.max(maxScore, score);
				//System.out.println(score + ":" + progressTmp);
			}

			in = inBak;
			score = scoreBak;
		}
	}

	private static int score;
	private static int[] in = new int[] { 1, 2, 2, 1, 2, 3, 1, 3, 3, 1 };

	private static int[] pang(int idx) {
		int cnt = 1;
		int l = 1;
		while (idx - l >= 0) {
			if (in[idx - l] != in[idx]) {
				break;
			}
			cnt++;
			l++;
		}
		int r = 1;
		while (idx + r < in.length) {
			if (in[idx + r] != in[idx]) {
				break;
			}
			cnt++;
			r++;
		}
		score += Math.pow(cnt, 2);

		int[] panged = new int[in.length - 1 - (l - 1) - (r - 1)];
		System.arraycopy(in, 0, panged, 0, idx - (l - 1));
		System.arraycopy(in, idx + 1 + (r - 1), panged, idx - (l - 1), in.length - (idx + 1 + r - 1));

		//System.out.println("in:" + Arrays.toString(in));
		//System.out.println("pa:" + Arrays.toString(panged));
		return panged;
	}
}
