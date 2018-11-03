package com.testweb.aspect;

import demo.com.annotation.MyAspect;
import demo.com.annotation.MyController;
import demo.com.proxy.MyAspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 作为代理链的测试
 */
@MyAspect(MyController.class)
public class ProxyChainAspect extends MyAspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyChainAspect.class);

    private Long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
        LOGGER.debug("ProxyChainAspect------------------begin-------------------------");
        LOGGER.debug("ProxyChainAspect======before");
        LOGGER.debug(String.format("class：%s # %s", cls.getName(), method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] args) throws Throwable {

        LOGGER.debug(String.format("used time : %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("ProxyChainAspect======after");
    }

    @Override
    public void end() {
        LOGGER.debug("ProxyChainAspect======end");
    }
}
