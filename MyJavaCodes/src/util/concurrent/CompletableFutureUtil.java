package util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureUtil {

	public static void main(String[] args) throws InterruptedException {

		scenario1();

	}

	/**
	 * 1�� ����� 2�� �ϰ� 2�� ����� ����Ѵ�
	 */
	private static void scenario1() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		CompletableFuture.supplyAsync(() -> 1, es).thenApplyAsync(i -> i.toString())
				.thenAccept(s -> System.out.println(s));
	}
}
