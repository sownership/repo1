package algorithm.leetcode.medium;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class P1115_printfoobaralternately {
	private int n;

	public static void main(String[] args) {
		P1115_printfoobaralternately ins=new P1115_printfoobaralternately();
		ins.n=2;
		new Thread(()->{
			try {
				ins.foo(()->System.out.print("foo"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println("end1");
		}).start();
		new Thread(()->{
			try {
				ins.bar(()->System.out.print("bar"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println("end2");
		}).start();
	}

	private static AtomicInteger ai = new AtomicInteger(0);
	private static Object lock = new Object();

	public void foo(Runnable printFoo) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			synchronized (lock) {
				if(ai.get() != 0)
					lock.wait();
				// printFoo.run() outputs "foo". Do not change or remove this line.
				printFoo.run();
				ai.set(1);
				lock.notify();
			}
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			synchronized (lock) {
				if(ai.get() != 1)
					lock.wait();
				// printBar.run() outputs "bar". Do not change or remove this line.
				printBar.run();
				ai.set(0);
				lock.notify();
			}
		}
	}
}
