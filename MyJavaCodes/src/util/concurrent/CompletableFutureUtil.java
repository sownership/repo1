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

		scenario2();

	}

	/**
	 * 1�� ����� 2�� �ϰ� 2�� ����� ����Ѵ�
	 */
	private static void scenario1() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		CompletableFuture.supplyAsync(() -> 1, es).thenApplyAsync(i -> i.toString(), es).thenAcceptAsync(s -> {
			System.out.println(s);
			es.shutdown();
		}, es);

	}

	/**
	 * 1�� 2�� ����� 3�� �ϰ� 3�� ����� ����Ѵ�
	 */
	private static void scenario2() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		Map<Integer, String> results = Collections.synchronizedMap(new HashMap<>());

		// ���1
		CompletableFuture.runAsync(() -> {
			results.put(1, "1");
		}, es).runAfterBothAsync(CompletableFuture.runAsync(() -> {
			results.put(2, "2");
		}, es), () -> {
			results.put(3, results.get(1) + results.get(2));
			System.out.println(results.get(3));
//			es.shutdown();
		}, es);

		// ���2
		CompletableFuture.allOf(CompletableFuture.runAsync(() -> results.put(1, "1"), es),
				CompletableFuture.runAsync(() -> results.put(2, "2"), es)).thenRunAsync(() -> {
					results.put(3, results.get(1) + results.get(2));
					System.out.println(results.get(3));
					es.shutdown();
				}, es);
	}
}
