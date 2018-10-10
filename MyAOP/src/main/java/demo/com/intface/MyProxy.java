package demo.com.intface;

import demo.com.proxy.ProxyChain;

/**
 * 代理接口
 */
public interface MyProxy {

    /**
     * 执行链式代理
     * @param proxyChain  代理链
     * @return Object  代理方法的返回值
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
