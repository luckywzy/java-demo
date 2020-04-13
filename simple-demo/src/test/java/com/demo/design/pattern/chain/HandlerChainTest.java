package com.demo.design.pattern.chain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 23:49
 */
public class HandlerChainTest {


	@Test
	public void application() {
		HandlerChain handlerChain = new HandlerChain();
		handlerChain.addHandler(new AHandler());
		handlerChain.addHandler(new BHandler());

		handlerChain.execute(new Object(), new Object());
	}
}