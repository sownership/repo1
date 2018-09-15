package util;

public class BitOperation {

	public static void main(String[] args) {
		bitOperation();
	}

	public static void bitOperation() {
		// 2바이트에서 4번째 비트가 1인지 확인
		byte[] b = new byte[2];
		b[0] = 0x04;
		b[1] = 0x23;
		System.out.println((b[0] & 0x01) == 0x01);
	}
}
