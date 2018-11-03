package com.testweb.aspect;

import demo.com.annotation.MyAspect;
import demo.com.annotation.MyController;
import demo.com.proxy.MyAspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 代理类测试
 */
@MyAspect(MyController.class)
public class ControllerAspect extends MyAspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private Long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
        LOGGER.debug("ControllerAspect------------------begin-------------------------");
        LOGGER.debug("ControllerAspect======before");
        LOGGER.debug(String.format("class：%s # %s", cls.getName(), method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] args) throws Throwable {

        LOGGER.debug(String.format("used time : %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("ControllerAspect======after");
    }

    @Override
    public void end() {
        LOGGER.debug("ControllerAspect======end");
    }
}
