package com.demo.mylock.failLock;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单实现的 公平锁
 * 公平锁：指线程能以先进先出的顺序获得锁和释放锁
 * <p>
 * 实现原理：在原来锁的基础上，增加一个队列，用来存放获得锁的线程
 * TODO：实现有问题！！！！！
 */
public class failLock {

	private boolean isLocked = false;
	private Thread lockingThread = null;
	private List<QueueObject> waitingTheads = new ArrayList<>();

	public void lock() throws InterruptedException {

		QueueObject queueObject = new QueueObject();
		boolean isLockedForCuruentThread = true;
		System.out.println("lock " + Thread.currentThread().getName());
		synchronized (this) {
			System.out.println("add " + Thread.currentThread().getName());
			waitingTheads.add(queueObject);
		}

		while (isLockedForCuruentThread) {
			synchronized (this) {
				isLockedForCuruentThread = isLocked || waitingTheads.get(0) != queueObject;
				if (!isLockedForCuruentThread) {
					isLocked = true;
					waitingTheads.remove(queueObject);
					lockingThread = Thread.currentThread();
					return;
				}
				try {
					queueObject.doWait();
				} catch (InterruptedException e) {
					synchronized (this) {
						waitingTheads.remove(queueObject);
					}
					throw e;
				}
			}
		}
	}

	public synchronized void unlock() {
		if (this.lockingThread != Thread.currentThread()) {
			throw new IllegalMonitorStateException();
		}

		isLocked = false;
		lockingThread = null;
		System.out.println("unlock " + lockingThread.getName());
		if (waitingTheads.size() > 0) {
			waitingTheads.get(0).doNotify();
		}

	}
}
