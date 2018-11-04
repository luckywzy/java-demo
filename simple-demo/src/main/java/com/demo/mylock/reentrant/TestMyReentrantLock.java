package com.demo.mylock.reentrant;

public class TestMyReentrantLock {

    private int val = 0;
    private MyLockReentrant lock = new MyLockReentrant();

    public int next() {
        lock.lock();
        val++;
        nextNext();
        lock.unlock();
        return val;
    }

    public int nextNext() {
        lock.lock();
        val++;
        nextNextNext();
        lock.unlock();
        return val;
    }

    public int nextNextNext() {
        lock.lock();
        val++;
        lock.unlock();
        return val;
    }

    public static void main(String[] args) {

        TestMyReentrantLock test = new TestMyReentrantLock();
        Runnable task = () -> {
            while (true)
                System.out.println(test.next());  //输出数字间隔为 3
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
