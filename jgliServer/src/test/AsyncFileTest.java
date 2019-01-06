package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncFileTest {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10000; i++)
			dosync2();
//		for (int i = 0; i < 1; i++)
//			es.execute(new Runnable() {
//
//				@Override
//				public void run() {
////					dosync2();
//				}
//			});

		Thread.currentThread().join();
	}

	private static void dosync2Sub(SeekableByteChannel sbc, ByteBuffer bf, long startTime) {
		int i = 0;
		try {
			if ((i = sbc.read(bf)) > 0) {
				bf.flip();
				bf.clear();
				es.execute(new Runnable() {
					
					@Override
					public void run() {
						dosync2Sub(sbc, bf, startTime);
					}
				});
			} else {
				System.out.println(System.currentTimeMillis()-startTime);
				sbc.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void dosync2() {
		long startTime = System.currentTimeMillis();
		Path path = Paths.get("", file);
		try {
			SeekableByteChannel sbc = Files.newByteChannel(path, StandardOpenOption.READ);
			ByteBuffer bf = ByteBuffer.allocate(1024 * 8);
			dosync2Sub(sbc, bf, startTime);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static final String file = "C:\\torrent\\Venom (2018) [BluRay] [1080p] [YTS.AM]\\Venom.2018.1080p.BluRay.x264-[YTS.AM].mp4";

	private static void dosync() {
		long startTime = System.currentTimeMillis();
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] b = new byte[1024 * 8];
			while (bis.read(b) != -1) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}

	private static class Attachment {
		private ByteBuffer bb;
		private int point;

		Attachment(ByteBuffer bb, int point) {
			this.bb = bb;
			this.point = point;
		}
	}

	private static ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2500));
//
//	private static void doasynch() {
//		long startTime = System.currentTimeMillis();
////		Path path = Paths.get("", file);
//		try {
//			pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//			AsynchronousFileChannel fileChannel = 
//			      AsynchronousFileChannel.open(Paths.get("", file), 
//			                                   new HashSet<StandardOpenOption>(Arrays.asList(StandardOpenOption.READ)), pool);
//			
////			Set<OpenOption> openOptionSet = new HashSet<>();
////			openOptionSet.add(StandardOpenOption.READ);
//			// AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,
//			// StandardOpenOption.READ);
////			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, openOptionSet, es);
//			ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
//
//			Attachment attachment = new Attachment(buffer, 0);
//			fileChannel.read(buffer, attachment.point, attachment, new CompletionHandler<Integer, Attachment>() {
//
//				@Override
//				public void completed(Integer result, Attachment attachment) {
//					// result is number of bytes read
//					// attachment is the buffer containing content
//					if (result == -1) {
//						System.out.println(System.currentTimeMillis() - startTime);
//						return;
//					}
//					attachment.bb.flip();
//					attachment.point += result;
//					fileChannel.read(attachment.bb, attachment.point, attachment, this);
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//				@Override
//				public void failed(Throwable exc, Attachment attachment) {
//
//				}
//			});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
