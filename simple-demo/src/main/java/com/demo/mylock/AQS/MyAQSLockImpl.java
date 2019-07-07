package com.demo.mylock.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 这里简单的实现lock 和 unlock
 * 以此了解 AbstractQueuedSynchronizer 的用法， 但却不止于此！！！
 */
public class MyAQSLockImpl implements Lock {

	private HelperSync helperSync = new HelperSync();

	@Override
	public void lock() {
		helperSync.tryAcquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		helperSync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return helperSync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return helperSync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		helperSync.tryRelease(1);
	}

	@Override
	public Condition newCondition() {
		return helperSync.newCondition();
	}

	private class HelperSync extends AbstractQueuedSynchronizer {
		protected HelperSync() {
			super();
		}

		@Override
		protected boolean tryAcquire(int arg) {
			/**
			 * 第一个线程 进来可以拿到锁， 返回true
			 * 第二个线程进来，不能拿到锁，返回false
			 *
			 * 如何判断是第一个线程，使用AbstractQueuedSynchronizer 提供的单个原子变量表示的状态
			 */

			int state = getState();

			if (state == 0) {
				if (compareAndSetState(state, arg)) { // CAS操作
					setExclusiveOwnerThread(Thread.currentThread()); //设置线程id
					return true;
				}
			} else if (Thread.currentThread() == getExclusiveOwnerThread()) { //重入逻辑
				setState(state + arg);
				return true;
			}

			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			// 锁的获取和释放是一一对应的，调用此方法的一定是当前线程

			if (Thread.currentThread() != getExclusiveOwnerThread()) {
				throw new RuntimeException();
			}
			int state = getState() - arg;
			if (state > 0) {  //重入
				setState(state);
				return true;
			}
			if (state == 0) {
				setExclusiveOwnerThread(null);
				return true;
			}

			return false;
		}

		@Override
		protected int tryAcquireShared(int arg) {
			return super.tryAcquireShared(arg);
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			return super.tryReleaseShared(arg);
		}

		@Override
		protected boolean isHeldExclusively() {
			return super.isHeldExclusively();
		}

		@Override
		public String toString() {
			return super.toString();
		}

		Condition newCondition() {
			return new ConditionObject();
		}
	}
}
