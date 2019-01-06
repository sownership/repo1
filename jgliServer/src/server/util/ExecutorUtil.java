package server.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {

	public static final int CPUS = Runtime.getRuntime().availableProcessors();

	public static ExecutorService bizExecutor = new ThreadPoolExecutor(1 + CPUS / 4, 1 + CPUS / 4, 10, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(110000));

	public static ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(2);
}
