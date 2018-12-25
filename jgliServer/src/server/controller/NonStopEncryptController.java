package server.controller;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import server.client.AbsClient;
import server.util.ExecutorUtil;

public class NonStopEncryptController extends AbsEncryptController {

	private SeekableByteChannel seekableByteChannel;

	@Override
	public void start(AbsClient client, ByteBuffer req) {
		
		seekableByteChannel = getChannel(req);
		
		next(client, req);
	}

	/**
	 * read -> encrypt -> write 가 계속된다
	 * read 는 한번에 8k 이상하는 것은 더 이상의 성능향상이 없다. 오히려 메모리 부담을 줄 수 있다
	 * read 와 write 가 너무 느리므로 encrypt 8k 를 multithread 로 하는 것은 전체 속도에 의미를 못준다
	 * 오히려 read 와 write 를 위한 thread 를 잡아두지 않는 것이 최선이다
	 * 즉 read 와 write 를 비동기로 처리한다
	 * 
	 * @param client
	 * @param req
	 */
	private void next(AbsClient client, ByteBuffer req) {
		ExecutorUtil.executor.execute(new Runnable() {
			
			@Override
			public void run() {
				read8k(new callback() {
					ByteBuffer msg = null;
					if(data==null) {
						msg = encryptRemain(req, data);
					} else {
						msg = encrypt(req, data);
					}
					endProcess(client, msg, new callback() {
						next(client, req);
					})
				});
			}
		});
	}
	
	protected void endProcess(AbsClient client, ByteBuffer msg, Runnable run) {
		client.send(msg, run);
	}
	
	private SeekableByteChannel getChannel(ByteBuffer req) {
		// TODO Auto-generated method stub
		return null;
	}
}
