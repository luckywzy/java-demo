package demo.com.proxy;

import demo.com.intface.MyProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理框架类 用来拿到代理类，做成切面代理类
 */
public abstract class MyAspectProxy implements MyProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAspectProxy.class);

    /**
     * 执行方法链，框架方法（方法模板）
     * 然后子类可以有选择的实现before 或者 after 方法 这样就实现在目标方法前后执行我们的代码了
     * 
     * @param proxyChain 代理链
     * @return
     * @throws Throwable
     */
    @Override
    public Object doProxy(MyProxyChain proxyChain) throws Throwable {

        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodArgs = proxyChain.getMethodArgs();

        begin();
        try {
            // 需要拦截
            if (intercept(targetClass, targetMethod, methodArgs)) {
                // before
                before(targetClass, targetMethod, methodArgs);

                result = proxyChain.doProxyChain();

                // after
                after(targetClass, targetMethod, methodArgs);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("aspect proxy failure ", e);

            // throw
            error(targetClass, targetMethod, methodArgs);

            throw e;
        } finally {
            // end
            end();
        }
        return result;
    }

    public void begin() {
    }

    public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] args) throws Throwable {
    }

    public void end() {
    }

    public void error(Class<?> cls, Method method, Object[] args) throws Throwable {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] args) throws Throwable {
        return true;
    }

}
