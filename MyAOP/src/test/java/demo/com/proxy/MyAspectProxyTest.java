package demo.com.proxy;

import demo.com.intface.MyProxy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * aop模块测试
 */
public class MyAspectProxyTest {

    private ArrayList<MyProxy> proxyList = new ArrayList<>();

    private Fuck targetObj = null;
    @Before
    public void addProxyClass(){

        proxyList.add(new AspectProxyImplA());
        proxyList.add(new AspectProxyImplB());

        targetObj = (Fuck)MyProxyManager.createProxy(Fuck.class, proxyList);

    }


    @Test
    public void testActionaop() {
        targetObj.func("hi!");
    }

    @Test(expected = ArithmeticException.class)
    public void testExceptionAOP(){
        targetObj.fund("abcd");
    }

    /**
     * 切面类实现
     */
    public static class AspectProxyImplA extends MyAspectProxy {
        @Override
        public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println(this.getClass().getName()+"   before");
        }

        @Override
        public void after(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println(this.getClass().getName()+"   after");
        }

        @Override
        public void end() {
            System.out.println(this.getClass().getName()+"   end");
        }

    }

    /**
     * 代理切面类
     */
    public static class AspectProxyImplB extends MyAspectProxy {
        @Override
        public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println(this.getClass().getName()+"   before");
        }

        @Override
        public void after(Class<?> cls, Method method, Object[] args) throws Throwable {
            System.out.println(this.getClass().getName()+"   after");
        }

        @Override
        public void end() {
            System.out.println(this.getClass().getName()+"   end");
        }

        @Override
        public void error(Class<?> cls, Method method, Object[] args, Throwable e) {
            System.out.println("catch exception :" +e);
        }
    }

    public static class Fuck{

        public String func(String a)
        {
            String res = this.getClass().getName()+"func() : running....";
            System.out.println(a);
            return a;
        }

        public String fund(String a)
        {
            String res = this.getClass().getName()+"fund() : running....";
            System.out.println(a);
            int i = 10 / 0;  //异常
            return res;
        }

    }
}