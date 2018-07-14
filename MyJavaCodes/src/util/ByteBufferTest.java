package util;

import java.nio.ByteBuffer;

public class ByteBufferTest {

	public static void main(String[] args) {
		ByteBufferTest ins = new ByteBufferTest();
		
		ins.byteBufferTest();
	}

	/**
	 * http://tutorials.jenkov.com/java-nio/buffers.html<br>
	 * 
	 * flip: ÷�� ���� ����ε� ���� �б���� �ٲٷ� �� ��, limit=pos, pos=0<br>
	 * rewind: pos �� 0���� �ؼ� ÷���� �ٽ� ������ �� ��<br>
	 * compact: �дٰ� ������ ���ĺ��� ������ �� ��<br>
	 * clear: �дٰ� ������ ������ ÷���� ������ �� ��<br>
	 * mark: ���� pos �� �����ߴٰ� ���߿� reset ���� ���ƿ� �� �ִ�<br>
	 */
	private void byteBufferTest() {
		ByteBuffer bb = ByteBuffer.allocate(10);
		System.out.println("0:" + bb.toString());
		bb.put((byte)0x01);
		bb.put((byte)0x02);
		bb.put((byte)0x03);
		System.out.println("1:" + bb.toString());
		//read mode
		bb.flip();
		System.out.println("2:" + bb.toString());
		bb.get();
		System.out.println("3:" + bb.toString());
		//write mode
		bb.compact();
		System.out.println("4:" + bb.toString());
	}
}
