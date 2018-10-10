package demo.com.proxy;

import java.lang.reflect.Method;

/**
 * 代理链
 */
public class ProxyChain {

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

    public ProxyChain(
            Class<?> targetClass, Object targetObj, Method targetMethod, Object[] methodArgs, MethodProxy methodProxy) {
        this.targetClass = targetClass;
        this.targetObj = targetObj;
        this.targetMethod = targetMethod;
        this.methodArgs = methodArgs;
        this.methodProxy = methodProxy;
    }
}
