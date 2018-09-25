package com.testweb.controller;

import demo.com.MyApplicationContext;
import demo.com.helper.BeanManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class IocAndDITest {

    private Map<Class<?>, Object> map;

    @Before
    public void getBeanContainer() {
        map = BeanManagerHelper.getBeanMap();
    }

    @Test
    public void testBeanContainerNotEmpty(){

        Assert.assertNotEquals(map.size(), 0);
        for (Class<?> bean : map.keySet()) {
            System.out.println(bean+":"+BeanManagerHelper.getBean(bean));
        }
    }
}