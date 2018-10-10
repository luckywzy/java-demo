package demo.com.proxy;

import demo.com.intface.MyProxy;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class MyProxyChain {

    /**
     * 目标类对象
     */
    private final Class<?> targetClass;
    /**
     * 目标实例对象
     */
    private final Object targetObj;
    /**
     * 目标方法
     */
    private final Method targetMethod;
    /**
     * 目标参数
     */
    private final Object[] methodArgs;
    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;
    /**
     * 代理链列表
     */
    private List<MyProxy> proxyList = new ArrayList<>();
    /**
     * 代理索引
     */
    private int proxyIndex = 0;

    public MyProxyChain(Class<?> targetClass, Object targetObj, Method targetMethod, Object[] methodArgs,
            MethodProxy methodProxy) {
        this.targetClass = targetClass;
        this.targetObj = targetObj;
        this.targetMethod = targetMethod;
        this.methodArgs = methodArgs;
        this.methodProxy = methodProxy;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObj() {
        return targetObj;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

    /**
     * 执行代理链方法
     * 
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObj, methodArgs);
        }
        return methodResult;
    }
}
