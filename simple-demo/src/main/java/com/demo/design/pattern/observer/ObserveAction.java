package com.demo.design.pattern.observer;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/1 23:54
 */
@Data
public class ObserveAction {

	/**
	 * 观察者类
	 */
	Object target;
	/**
	 * 观察者事件方法
	 */
	Method method;

	public ObserveAction(Object target, Method method) {
		this.target = target;
		this.method = method;
		this.method.setAccessible(true);
	}

	/**
	 * 事件方法执行器
	 * @param event
	 */
	public void execute(Object event){
		try {
			method.invoke(target, event);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
