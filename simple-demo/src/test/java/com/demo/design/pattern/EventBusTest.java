package com.demo.design.pattern;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 00:42
 */
public class EventBusTest {

	@Test
	public void post() {

		EventBus eventBus = new EventBus();

		eventBus.register(new A());
		eventBus.register(new B());


		eventBus.post(1L);
	}


	static class A{

		@Subscribe
		public void on(Long a){
			System.out.println("on long");
		}
	}

	static class B{

		@Subscribe
		public void on(Number a){
			System.out.println("on number");
		}
	}

}