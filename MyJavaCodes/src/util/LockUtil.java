package util;

public class LockUtil {

	public static void main(String[] args) throws InterruptedException {
		LockUtil lockUtil = new LockUtil();
		lockUtil.test();
	}

	private void test() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				test1();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				test2();
			}
		}).start();
	}

	private synchronized void test1() {
		System.out.println("test1 start");
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test1 end");
	}

	private synchronized void test2() {
		System.out.println("test2 start");
		try {
			notifyAll();
			Thread.sleep(1000);
			System.out.println("test21 start");
			Thread.sleep(1000);
			System.out.println("test22 start");
			Thread.sleep(1000);
			System.out.println("test23 start");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test2 end");
	}
}
