package main.mylock.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 可重入是指：同一个线程可以再次获得锁
 * <p>
 * 所以可以扩展不可重入的实现，在其中加上 是否是当前线程的判断，和重入次数的计数
 */
public class MyLockReentrant implements Lock {

    private boolean isLock = false;
    private Thread currentThread = null;
    private int lockCount = 0;

    @Override
    public synchronized void lock() {
        /**
         *  如果是当前线程,锁住次数 加 1（不是第一次进入）
         *
         *  如果不是当前线程，走到while循环，阻塞在wait()方法上
         *
         *  如果是第一次进入，会走到最后三行
         */
        if (currentThread == Thread.currentThread()) {
            lockCount++;
            return;
        }

        while (isLock) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentThread = Thread.currentThread();
        isLock = true;
        lockCount++;
    }

    @Override
    public synchronized void unlock() {
        //释放锁的线程一定是得到锁的线程，如果不是 可能 需要抛出一个异常？！
        if (currentThread == Thread.currentThread()) {
            lockCount--;
        }
        if (lockCount == 0) {
            isLock = false;
            notify();
        }
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
