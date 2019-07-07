package demo.com.helper;

import demo.com.annotation.MyInject;
import demo.com.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现依赖注入
 */
public class IoCHelper {

	static {
		Map<Class<?>, Object> beanMap = BeanManagerHelper.getBeanMap();
		if (beanMap.size() > 0) {
			//遍历 beanMap
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				setField(beanEntry.getKey(), beanEntry.getValue(), beanMap);
			}
		}
	}


	public static void setField(Class<?> beanClass, Object bean, Map<Class<?>, Object> beanMap) {
		//获取所有声明的成员变量
		Field[] fields = beanClass.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				//是否添加自动注入的注解
				if (field.isAnnotationPresent(MyInject.class)) {
					Class<?> fieldType = field.getType();
					Object fieldInstance = beanMap.get(fieldType);
					if (fieldInstance != null) {
						ReflectionUtil.setField(bean, field, fieldInstance);
					}

				}
			}
		}
	}
}
