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
	 * flip: 첨에 쓰기 모드인데 이제 읽기모드로 바꾸려 할 때, limit=pos, pos=0<br>
	 * rewind: pos 를 0으로 해서 첨부터 다시 읽으려 할 때<br>
	 * compact: 읽다가 남은거 이후부터 쓰려고 할 때<br>
	 * clear: 읽다가 남은거 버리고 첨부터 쓰려고 할 때<br>
	 * mark: 현재 pos 를 저장했다가 나중에 reset 으로 돌아올 수 있다<br>
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
