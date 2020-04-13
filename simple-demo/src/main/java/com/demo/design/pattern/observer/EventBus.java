package com.demo.design.pattern.observer;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.Collection;
import java.util.concurrent.Executor;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/1 23:48
 */
public class EventBus {
	private Executor executor;
	private ObserveRegister register = new ObserveRegister();


	public EventBus(Executor executor) {
		this.executor = executor;
	}

	public EventBus() {
		executor = MoreExecutors.directExecutor();
	}

	/**
	 * 注册
	 *
	 * @param object
	 * @return
	 */
	public boolean register(Object object) {
		register.register(object);
		return true;
	}

	/**
	 * 删除
	 *
	 * @param object
	 * @return
	 */
	public boolean unRegister(Object object) {
		return true;
	}

	/**
	 * 执行观察者的事件
	 *
	 * @param event
	 * @return
	 */
	public boolean post(Object event) {
		Collection<ObserveAction> observeActions = register.getMatchObserverActions(event);
		for (ObserveAction observeAction : observeActions) {
			//同步阻塞的方式
			executor.execute(new Runnable() {
				@Override
				public void run() {
					observeAction.execute(event);
				}
			});

		}
		return true;
	}
}
