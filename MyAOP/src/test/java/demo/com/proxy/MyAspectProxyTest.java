package demo.com.proxy;

import demo.com.intface.MyProxy;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * aop模块测试
 */
public class MyAspectProxyTest {

    @Test
    public void testaop() {
        ArrayList<MyProxy> proxyList = new ArrayList<>();

        proxyList.add(new AspectProxyImpl());

        Fuck proxy = (Fuck)MyProxyManager.createProxy(Fuck.class, proxyList);

        proxy.func("hi!");
    }

    /**
     * 切面类实现
     */
    public static class AspectProxyImpl extends MyAspectProxy {
        @Override
        public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println("before");
        }

        @Override
        public void after(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println("after");
        }
    }

    public static class Fuck{

        public String func(String a)
        {
            System.out.println("running....");
            return a;
        }
    }
}