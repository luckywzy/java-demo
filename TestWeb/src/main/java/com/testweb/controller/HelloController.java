package com.testweb.controller;

import com.testweb.service.HelloService;
import com.testweb.service.TranscationService;
import demo.com.annotation.MyController;
import demo.com.annotation.MyInject;
import demo.com.annotation.MyRequestMapping;
import demo.com.annotation.MyRequestMethod;

@MyController
public class HelloController {

	@MyInject
	private HelloService helloService;

	@MyInject
	private TranscationService transcationService;

	@MyRequestMapping(path = "/sayHello", method = MyRequestMethod.GET)
	public String sayHello(String name) {
		return helloService.sayHello(name);
	}


	@MyRequestMapping(path = "/add", method = MyRequestMethod.GET)
	public String add(Integer a, Integer b) {
		return String.valueOf(helloService.add(a, b));
	}

	@MyRequestMapping(path = "/dopost", method = MyRequestMethod.POST)
	public String dopost(String name) {
		return helloService.recive(name);
	}

	@MyRequestMapping(path = "/tx", method = MyRequestMethod.GET)
	public String tx(String a) {
		return transcationService.tranA(a);
	}
}
