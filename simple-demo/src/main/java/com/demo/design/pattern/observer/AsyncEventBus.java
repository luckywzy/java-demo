package com.demo.design.pattern.observer;

import java.util.concurrent.Executor;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 00:39
 */
public class AsyncEventBus extends EventBus {
	public AsyncEventBus(Executor executor) {
		super(executor);
	}

}
