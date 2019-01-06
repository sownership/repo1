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

public class NonStopConvertController extends AbsConvertController {

	private SeekableByteChannel seekableByteChannel;

	@Override
	public void start(AbsClient client, Map<String, Object> msg) {
		
//		seekableByteChannel = getChannel(req);
//		
//		next(client, req);
	}

	private void next(AbsClient client, ByteBuffer req) {
//		ExecutorUtil.executor.execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				read8k(new callback() {
//					ByteBuffer msg = null;
//					if(data==null) {
//						msg = convertRemain(req, data);
//					} else {
//						msg = convert(req, data);
//					}
//					endProcess(client, msg, new callback() {
//						next(client, req);
//					})
//				});
//			}
//		});
	}
	
	protected void endProcess(AbsClient client, ByteBuffer msg, Runnable run) {
		client.send(msg, run);
	}
	
	private SeekableByteChannel getChannel(ByteBuffer req) {
		// TODO Auto-generated method stub
		return null;
	}
}
