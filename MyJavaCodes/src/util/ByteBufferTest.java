package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferTest {

	public static void main(String[] args) {
		ByteBufferTest ins = new ByteBufferTest();
		
		ins.endianDemo();
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
	
	private void endianDemo() {
		byte[] x=new byte[100];  
		ByteBuffer buf=ByteBuffer.allocate(100);  
		buf.putInt(0x01020304);  
		buf.putChar((char)0x0506);  
		buf.putLong(0x0102030405060708L);  
		buf.putDouble(3.3e15);  
		buf.position(0);  
		buf.get(x);  
		System.out.println(x[0]+","+x[1]+","+x[2]+","+x[3]);   
		buf.position(0);  
		System.out.println(Integer.toHexString(buf.getInt()));  
		System.out.println(Integer.toHexString(buf.getChar()));  
		System.out.println(Long.toHexString(buf.getLong()));  
		System.out.println(buf.getDouble());  

		System.out.println("=============================");

		buf.order(ByteOrder.LITTLE_ENDIAN);  
		buf.position(0);  
		buf.get(x);  
		System.out.println(x[0]+","+x[1]+","+x[2]+","+x[3]);   
		buf.position(0);  
		System.out.println(Integer.toHexString(buf.getInt()));  
		System.out.println(Integer.toHexString(buf.getChar()));  
		System.out.println(Long.toHexString(buf.getLong()));  
		System.out.println(buf.getDouble());  
	}
}
