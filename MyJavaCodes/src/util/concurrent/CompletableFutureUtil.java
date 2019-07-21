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
	 * 1의 결과로 2를 하고 2의 결과를 출력한다
	 */
	private static void scenario1() {
		ExecutorService es = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

		CompletableFuture.supplyAsync(() -> 1, es).thenApplyAsync(i -> i.toString())
				.thenAccept(s -> System.out.println(s));
	}
}
