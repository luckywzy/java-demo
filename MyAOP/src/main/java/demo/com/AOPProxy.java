package demo.com;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPProxy {

    private static Object _target;
    private static MyAOPClass _aop;

    public static Object getProxyInstance(Object target, MyAOPClass aop) {
        _target = target;
        _aop = aop;
        return Proxy.newProxyInstance(_target.getClass().getClassLoader(), _target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        _aop.begin();
                        Object result = method.invoke(_target, args);
                        _aop.end();
                        return result;
                    }
                });
    }

}
