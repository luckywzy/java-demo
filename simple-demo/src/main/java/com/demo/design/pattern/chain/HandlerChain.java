package com.demo.design.pattern.chain;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/2 23:45
 */
public class HandlerChain {

	private Handler head = null;
	private Handler tail = null;


	public void addHandler(Handler handler){
		handler.setNextHandler(null);

		if(head ==null){
			head = handler;
			tail = handler;
			return;
		}

		tail.setNextHandler(handler);
		tail = handler;
	}

	public void execute(Object request, Object response){
		if(head != null){
			head.handler(request, response);
		}
	}
}
