package com.demo.mylock.notReentrant;

public class TestLock {

    MyLockNotReentrant lock = new MyLockNotReentrant();
    private int val = 0;

    public int getNext() {
        lock.lock();
        val++;
        lock.unlock();
        return val;
    }

    public static void main(String[] args) {
        TestLock testLock = new TestLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(testLock.getNext());
                }
            }
        });
        Thread t2 = new Thread(t1);
        Thread t3 = new Thread(t1);

        t1.start();
        t2.start();
        t3.start();

    }

}
