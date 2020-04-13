package com.demo.design.pattern.chain;



/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 23:45
 */
public class BHandler extends Handler {
	@Override
	protected boolean doHandler(Object request, Object response) {
		boolean handlered = false;
		System.out.println("B doHandler...");
		return handlered;
	}
}
