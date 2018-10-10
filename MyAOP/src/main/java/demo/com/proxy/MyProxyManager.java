package demo.com.proxy;

import demo.com.intface.MyProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理类管理，所有代理类都从此处创建
 */
public class MyProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<MyProxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObj, Method targetmethod, Object[] methodArgs, MethodProxy methodProxy)
                    throws Throwable {
                return new MyProxyChain(targetClass, targetObj, targetmethod, methodArgs, methodProxy);
            }
        });
    }
}
