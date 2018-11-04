package com.demo.mylock.failLock;

public class QueueObject {

    private boolean isNotified = false; //释放被唤醒

    public synchronized void doWait() throws InterruptedException {
        while (!isNotified) { //没被唤醒就一直等待
            this.wait();
        }
    }

    public synchronized void doNotify() {
        isNotified = true;
        this.notify();  // 唤醒当前的线程
    }

    public boolean equals(Object o) {
        return this == o;
    }
}
