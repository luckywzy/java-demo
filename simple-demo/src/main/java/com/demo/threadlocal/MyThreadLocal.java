package com.demo.threadlocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 就是每个线程中独立的变量，可以看作是每个线程内的局部变量，
 *
 * @param <T>
 */
public class MyThreadLocal<T> {

    private Map<Thread, T> container = Collections.synchronizedMap(new HashMap<Thread, T>());

    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread currentThread = Thread.currentThread();
        T value = container.get(currentThread);

        if (value == null && !container.containsKey(currentThread)) {
            value = initialValue();
            container.put(currentThread, value);
        }

        return value;
    }

    public void remove() {
        container.remove(Thread.currentThread());
    }

    protected T initialValue() {
        return null;
    }
}
