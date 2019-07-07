package com.demo.mylock.notReentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 最简单的不可重入锁，只实现lock和unlock
 * 实现原理：使用synchronized关键字
 */
public class MyLockNotReentrant implements Lock {

	private boolean isLock = false;

	@Override
	public synchronized void lock() {

		//没有拿到锁的线程会阻塞在这里
		while (isLock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isLock = true;
	}

	@Override
	public synchronized void unlock() {
		//释放锁的线程肯定是拿到锁的线程，直接将isLock设置为false，然后notify()即可
		isLock = false;
		notify();
	}


	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}


	@Override
	public Condition newCondition() {
		return null;
	}
}
