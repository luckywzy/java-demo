package com.demo.simlpeRPC;

public class HelloServiceImpl implements HelloService {
	@Override
	public String say(String name) {
		return "hello," + name;
	}
}
