package com.demo.mylock.AQS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestAQSLock {

	private int val;
	private MyAQSLockImpl lock = new MyAQSLockImpl();

	public int getNext() {
		lock.lock();
		try {
			return val++;
		} finally {
			lock.unlock();
		}
	}

	public int getNextNext() {
		lock.lock();
		try {
			getNext();
			return val++;
		} finally {
			lock.unlock();
		}
	}

	public Runnable getTask() {
		return () -> {
			while (true) {
				System.out.println(this.getNextNext());
			}
		};
	}

	public void testnotReentrant() {

		Runnable task = getTask();

		ExecutorService taskpool = Executors.newFixedThreadPool(4);

		for (int i = 0; i < 4; i++) {
			taskpool.submit(task);
		}

		taskpool.shutdown();
	}

	public void testReentrant() {
		Runnable task = getTask();
		this.getNextNext();
		new Thread(task).start();
	}

	public static void main(String[] args) {

		TestAQSLock testAQSLock = new TestAQSLock();

		testAQSLock.testnotReentrant();
		//testAQSLock.testReentrant();
	}

}
