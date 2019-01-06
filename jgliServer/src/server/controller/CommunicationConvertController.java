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
	 * read -> convert -> write �� ��ӵȴ�
	 * read �� �ѹ��� 8k �̻��ϴ� ���� �� �̻��� ��������� ����
	 * read �� write �� �ʹ� �����Ƿ� convert 8k �� multithread �� �ϴ� ���� ��ü �ӵ��� �ǹ̸� ���ش�
	 * ������ read �� write �� ���� thread �� ��Ƶ��� �ʴ� ���� �ּ��̴�
	 * �� read �� write �� �񵿱�� ó���Ѵ�
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
