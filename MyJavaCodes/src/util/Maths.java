package util;

public class Maths {

	public static void main(String[] args) {

		int src = 1153;

		// 반올림 1153->1200
		System.out.println(src / 100 * 100 + (src / 10 % 10 >= 5 ? 100 : 0));
		System.out.println(Math.round((double) src / 100) * 100);

		// 버럼 1153->1100
		System.out.println(src / 100 * 100);
		System.out.println((int) Math.floor(src / 100) * 100);
		
		src=1143;
		
		// 올림 1143->1200
		System.out.println(src / 100 * 100 + (src / 10 % 10 > 0 ? 100 : 0));
		
		// nooooooooooooooooooooooooooooo 1103->1200
		System.out.println((int) Math.ceil((double) src / 100) * 100);
	}
}
