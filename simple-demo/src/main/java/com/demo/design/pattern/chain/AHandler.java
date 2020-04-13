package com.demo.design.pattern.chain;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 23:42
 */
public class AHandler extends Handler {

	@Override
	protected boolean doHandler(Object request, Object response) {
		boolean handlered = false;

		System.out.println("A doHandler....");

		return handlered;
	}

}
