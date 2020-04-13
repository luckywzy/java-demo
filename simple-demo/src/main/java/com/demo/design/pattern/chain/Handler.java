package com.demo.design.pattern.chain;

/**
 *
 * @Author: wangzongyu
 * @Date: 2020/4/2 23:40
 */
public abstract class Handler {

	protected Handler nextHandler;

	public void setNextHandler(Handler nextHandler){
		this.nextHandler = nextHandler;
	}

	public final void handler(Object request, Object response){
		boolean handlered = doHandler(request, response);
		if(!handlered && nextHandler !=null){
			nextHandler.handler(request,response);
		}
	}

	/**
	 *  @param request
	 * @param response
	 * @return
	 */
	protected abstract boolean doHandler(Object request, Object response);
}
