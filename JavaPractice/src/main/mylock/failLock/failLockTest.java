package main.mylock.failLock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;

import static org.junit.Assert.*;

public class failLockTest {

    private static failLock lock = new failLock();
    private int val = 0;

    private static void getNext() {
        try {
            lock.lock();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            threadPool.submit(() -> {
                getNext();
            });
        }
        threadPool.shutdown();

    }
}