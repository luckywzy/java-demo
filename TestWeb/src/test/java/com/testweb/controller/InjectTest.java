package com.testweb.controller;

import demo.com.MyApplicationContext;
import demo.com.helper.BeanManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InjectTest {

	MyApplicationContext context;

	@Before
	public void init() {
		MyApplicationContext.init();
	}

	@Test
	public void testInject() {

		HelloController helloController = BeanManagerHelper.getBean(HelloController.class);
		String name = "liming";
		Assert.assertEquals("hello," + name, helloController.sayHello(name));
	}
}
