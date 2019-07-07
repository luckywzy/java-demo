package demo.com.utils;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 *
 */
public class ReflectionUtilTest {

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

	@Test
	public void testReflect() {
		ServiceA a = new ServiceA();
		Class<?> aClass = ClassUtil.loadClass(ServiceA.class.getName());
		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method method : declaredMethods) {
			System.out.print(method.getName());
			System.out.print("(");
			Parameter[] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				System.out.print(parameter.getType().getName() + " " + parameter.getName() + ",");
			}
			System.out.println(")");
		}
	}

	@Test
	public void testToTypes() throws InvocationTargetException, IllegalAccessException {
		Object args[] = new Object[2];
		args[0] = "2123214";
		args[1] = "312312312";
		helper("toInteger", args);

	}

	@Test
	public void testToBool() throws InvocationTargetException, IllegalAccessException {
		ServiceA a = new ServiceA();
		Class<?> aClass = ClassUtil.loadClass(ServiceA.class.getName());
		Method[] method = aClass.getDeclaredMethods();
		for (Method mt : method) {
			if (mt.getName().equals("tobool")) {
				Parameter[] parameters = mt.getParameters();
				final int len = parameters.length;
				Object b = "false";
				Object params[] = new Object[len];
				for (int i = 0; i < parameters.length; i++) {
					params[i] = convert(b, parameters[i].getType());
				}
				Object invoke = mt.invoke(a, params);
				System.out.println(invoke);
			}
		}
	}

	private void helper(String methodName, Object[] args) throws InvocationTargetException, IllegalAccessException {
		ServiceA a = new ServiceA();
		Class<?> aClass = ClassUtil.loadClass(ServiceA.class.getName());
		Method[] method = aClass.getDeclaredMethods();
		for (Method mt : method) {
			if (mt.getName().equals(methodName)) {
				Parameter[] parameters = mt.getParameters();
				final int len = parameters.length;

				Object params[] = new Object[len];
				for (int i = 0; i < parameters.length; i++) {
					params[i] = convert(args[i], parameters[i].getType());
				}
				Object invoke = mt.invoke(a, params);
				System.out.println(invoke);
			}
		}
	}

	@Test
	public void testToLong() throws InvocationTargetException, IllegalAccessException {
		ServiceA a = new ServiceA();
		Class<?> aClass = ClassUtil.loadClass(ServiceA.class.getName());
		Method[] method = aClass.getDeclaredMethods();
		for (Method mt : method) {
			if (mt.getName().equals("toLong")) {
				Parameter[] parameters = mt.getParameters();
				final int len = parameters.length;
				Object b = "1234546213456";
				Object params[] = new Object[len];
				for (int i = 0; i < parameters.length; i++) {
					params[i] = convert(b, parameters[i].getType());
				}
				Object invoke = mt.invoke(a, params);
				System.out.println(invoke);
			}
		}
	}

	static class ServiceA {

		public void say(String name) {
			System.out.println("hello :" + name);
		}

		public Integer toInteger(Integer a, Integer b) {
			System.out.println(a + b);
			return a + b;
		}

		public boolean tobool(Boolean b) {
			System.out.println(b);
			return b;
		}

		public Long toLong(Long b) {
			System.out.println(b);
			return b;
		}
	}

}