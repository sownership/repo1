package util;

public class BitOperation {

	public static void main(String[] args) {
		bitOperation();
	}

	public static void bitOperation() {
		// 2����Ʈ���� 4��° ��Ʈ�� 1���� Ȯ��
		byte[] b = new byte[2];
		b[0] = 0x04;
		b[1] = 0x23;
		System.out.println((b[0] & 0x01) == 0x01);
	}
}
