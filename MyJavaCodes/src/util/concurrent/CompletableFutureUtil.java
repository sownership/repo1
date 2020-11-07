package util.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureUtil {

	public static void main(String[] args) throws InterruptedException {

		scenario3();

	}

	/**
	 * 1의 결과로 2를 하고 2의 결과를 출력한다
	 */
	private static void scenario1() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		CompletableFuture.supplyAsync(() -> 1, es).thenApplyAsync(i -> i.toString(), es).thenAcceptAsync(s -> {
			System.out.println(s);
			es.shutdown();
		}, es);

	}

	/**
	 * 1과 2의 결과로 3을 하고 3의 결과를 출력한다
	 */
	private static void scenario2() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		Map<Integer, String> results = Collections.synchronizedMap(new HashMap<>());

		// 방법1
		CompletableFuture.runAsync(() -> {
			results.put(1, "1");
		}, es).runAfterBothAsync(CompletableFuture.runAsync(() -> {
			results.put(2, "2");
		}, es), () -> {
			results.put(3, results.get(1) + results.get(2));
			System.out.println(results.get(3));
//			es.shutdown();
		}, es);

		// 방법2
		CompletableFuture.allOf(CompletableFuture.runAsync(() -> results.put(1, "1"), es),
				CompletableFuture.runAsync(() -> results.put(2, "2"), es)).thenRunAsync(() -> {
					results.put(3, results.get(1) + results.get(2));
					System.out.println(results.get(3));
					es.shutdown();
				}, es);
	}

	private static void scenario3() {
		CompletableFuture<String> cf1=CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread()+",cf1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "haha1";
		});
//		cf1.thenAccept((s)->{
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println(Thread.currentThread()+",1,"+s);
//		});
//		cf1.thenAccept((s)->{
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println(Thread.currentThread()+",2,"+s);
//		});
		cf1.thenAcceptAsync((s)->{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread()+",1,"+s);
		});
		cf1.thenAcceptAsync((s)->{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread()+",2,"+s);
		});
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
