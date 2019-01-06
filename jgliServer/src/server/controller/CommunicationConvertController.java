package server.controller;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import server.client.AbsClient;
import server.util.ExecutorUtil;
import server.util.WhiteBoard;
import server.util.WhiteBoard.Listener;

public class CommunicationConvertController extends AbsConvertController {

	private SeekableByteChannel seekableByteChannel;
	private Queue<String> eventQ = new LinkedBlockingQueue<>();
	private AbsClient client;
	private ByteBuffer req;
	
	private Listener listener = new Listener() {
		
		@Override
		public void onEvent(String topic, Map<String, Object> params) {
			eventQ.offer(topic);
			next(client, req);
		}
	};
	
	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		WhiteBoard.registerListener("convert/"+client+"/*", listener);
		seekableByteChannel = getChannel(req);
		
		next(client, req);
	}

	/**
	 * read -> convert -> write 가 계속된다
	 * read 는 한번에 8k 이상하는 것은 더 이상의 성능향상이 없다
	 * read 와 write 가 너무 느리므로 convert 8k 를 multithread 로 하는 것은 전체 속도에 의미를 못준다
	 * 오히려 read 와 write 를 위한 thread 를 잡아두지 않는 것이 최선이다
	 * 즉 read 와 write 를 비동기로 처리한다
	 * 
	 * @param client
	 * @param req
	 */
	private void next(AbsClient client, ByteBuffer req) {
		
		long fileCurrent = getcur();
		if(q=="stop") {
			terminate(client);
			return;
		} else if(q=="num") {
			fileCurrent = num;
		}
		
		ExecutorUtil.executor.execute(new Runnable() {
			
			@Override
			public void run() {
				read8k(new callback() {
					ByteBuffer msg = null;
					if(data==null) {
						msg = convertRemain(req, data);
					} else {
						msg = convert(req, data);
					}
					endProcess(client, msg, null);
				}, num);
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
	
	private void terminate(AbsClient client) {
		WhiteBoard.unregisterListener("convert/"+client+"/*", listener);
	}
}
