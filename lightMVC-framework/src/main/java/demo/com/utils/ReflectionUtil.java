package demo.com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 反射机制 工具类
 */
public final class ReflectionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 根据类 类型 创建对象
	 *
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object obj;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new object {} failure ", cls, e);
			throw new RuntimeException(e);
		}
		return obj;
	}

	/**
	 * 调用 目标类的方法
	 *
	 * @param obj    目标类
	 * @param method 目标类方法
	 * @param args   方法参数
	 * @return Object   方法返回值
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, convertParams(args, method));
		} catch (Exception e) {
			LOGGER.error("invoke method failure : {}, {}", method, args, e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 设置成员变量的值
	 *
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			LOGGER.error("set field failure: {}, ", field, value, e);
			throw new RuntimeException(e);
		}
	}


	private static Object[] convertParams(Object[] params, Method method) {
		final int len = params.length;
		Object[] resParams = new Object[len];
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < len; i++) {
			resParams[i] = convert(params[i], parameters[i].getType());
		}
		return resParams;
	}

	/**
	 * 对基本类型的参数进行转型操作（略微显得笨拙）
	 *
	 * @param o   输入参数
	 * @param clz 转型后的类型
	 * @param <T> 返回值类型
	 * @return
	 */
	private static <T> T convert(Object o, Class<T> clz) {
		T cast;

		if (clz.equals(Long.class)) {
			cast = (T) Long.valueOf(String.valueOf(o));
		} else if (clz.equals(Boolean.class)) {
			cast = (T) Boolean.valueOf(String.valueOf(o));
		} else if (clz.equals(Double.class)) {
			cast = (T) Double.valueOf(String.valueOf(o));
		} else if (clz.equals(Integer.class)) {
			cast = (T) Integer.valueOf(String.valueOf(o));
		} else if (clz.equals(Short.class)) {
			cast = (T) Short.valueOf(String.valueOf(o));
		} else {
			cast = clz.cast(o);
		}
		return cast;
	}
}
