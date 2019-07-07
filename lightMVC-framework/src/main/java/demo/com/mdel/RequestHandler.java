package demo.com.mdel;

import java.lang.reflect.Method;

/**
 * 封装 请求对应的 controller 类 和 requestMapping方法
 */
public class RequestHandler {

	//controller 类
	private Class<?> controllerClass;

	// requestMapping 对应的方法
	private Method ActionMethod;

	public RequestHandler(Class<?> controllerClass, Method actionMethod) {
		this.controllerClass = controllerClass;
		ActionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public Method getActionMethod() {
		return ActionMethod;
	}
}
