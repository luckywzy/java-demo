package com.demo.design.pattern;

import com.google.common.base.Preconditions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/1 23:58
 */
public class ObserveRegister {

	private ConcurrentHashMap<Class<?>, CopyOnWriteArraySet<ObserveAction>> register = new ConcurrentHashMap<>();

	/**
	 * 注册一个观察者对象
	 *
	 * @param observer
	 * @return
	 */
	public boolean register(Object observer) {
		Map<Class<?>, Collection<ObserveAction>> observeActionMap = findAllObserveAction(observer);
		for (Map.Entry<Class<?>, Collection<ObserveAction>> entry : observeActionMap.entrySet()) {
			Collection<ObserveAction> observeActions = entry.getValue();
			Class<?> eventType = entry.getKey();
			CopyOnWriteArraySet<ObserveAction> registeredActions = register.get(eventType);
			if (registeredActions == null) {
				register.put(eventType, new CopyOnWriteArraySet<>(observeActions));
				registeredActions = register.get(eventType);
			}
			registeredActions.addAll(observeActions);
		}
		return true;
	}


	/**
	 * 从注册的观察者对象中获取所有的事件集合
	 *
	 * @param observer
	 * @return <事件类型，同类型的事件集合>
	 */
	private Map<Class<?>, Collection<ObserveAction>> findAllObserveAction(Object observer) {
		Map<Class<?>, Collection<ObserveAction>> observerActionsMap = new HashMap<>();
		Class<?> clazz = observer.getClass();
		List<Method> annotationMethods = getAnnotationMethods(clazz);

		for (Method method : annotationMethods) {
			if (!observerActionsMap.containsKey(method.getParameterTypes()[0])) {
				observerActionsMap.put(method.getParameterTypes()[0], new ArrayList<>());
			}
			observerActionsMap.get(method.getParameterTypes()[0]).add(new ObserveAction(observer, method));
		}

		return observerActionsMap;
	}

	/**
	 * 获取标注了指定注解的方法集合
	 *
	 * @param clazz
	 * @return
	 */
	private List<Method> getAnnotationMethods(Class<?> clazz) {
		List<Method> methods = new ArrayList<>();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Subscribe.class)) {
				Class<?>[] eventType = method.getParameterTypes();
				Preconditions.checkArgument(eventType.length == 1, "method %s has @Subscribe annotation but has %s parameters." + "Subscriber method must have exactly 1 parameter", method, eventType.length);
			}
			methods.add(method);
		}
		return methods;
	}

	/**
	 * 获取匹配的事件方法集合
	 *
	 * @param event
	 * @return
	 */
	public Collection<ObserveAction> getMatchObserverActions(Object event) {
		ArrayList<ObserveAction> matchedActions = new ArrayList<>();
		for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserveAction>> entry : register.entrySet()) {
			Class<?> eventType = entry.getKey();
			if (eventType.isAssignableFrom(event.getClass())) {
				matchedActions.addAll(entry.getValue());
			}
		}
		return matchedActions;
	}
}
