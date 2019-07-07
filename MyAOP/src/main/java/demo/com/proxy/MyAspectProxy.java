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
	 * 然后子类可以有选择的实现before 、after ... 等方法 这样就实现在目标方法前后执行我们的代码了
	 *
	 * @param proxyChain 代理链
	 * @return
	 * @throws Throwable
	 */
	@Override
	public final Object doProxy(MyProxyChain proxyChain) throws Throwable {

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
			error(targetClass, targetMethod, methodArgs, e);
			/**
			 * 需要注意的是这里将 Exception 继续抛出，AOP并不将异常吞下
			 */
			throw e;
		} finally {
			// end
			end();
		}
		return result;
	}

	/**
	 * 拦截之前执行
	 */
	public void begin() {
	}

	/**
	 * 目标方法之前执行
	 *
	 * @param cls    目标类
	 * @param method 目标方法
	 * @param args   目标方法参数
	 * @throws Throwable
	 */
	public void before(Class<?> cls, Method method, Object[] args) throws Throwable {
	}

	/**
	 * 目标方法之后执行
	 *
	 * @param cls    目标类
	 * @param method 目标方法
	 * @param args   目标方法参数
	 * @throws Throwable
	 */
	public void after(Class<?> cls, Method method, Object[] args) throws Throwable {
	}

	/**
	 * 目标方法返回后执行
	 */
	public void end() {
	}

	/**
	 * 目标方法异常时 执行
	 *
	 * @param cls    目标类
	 * @param method 目标方法
	 * @param args   目标方法参数
	 * @param e
	 */
	public void error(Class<?> cls, Method method, Object[] args, Throwable e) {
	}

	/**
	 * 拦截规则
	 *
	 * @param cls    目标类
	 * @param method 目标方法
	 * @param args   目标方法参数
	 * @return
	 * @throws Throwable
	 */
	public boolean intercept(Class<?> cls, Method method, Object[] args) throws Throwable {
		return true;
	}

}
