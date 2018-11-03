package demo.com.helper;


import demo.com.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 管理bean类信息和 实例 的容器
 */
public class BeanManagerHelper {

    // 存放bean信息，与其实例对象的映射
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>(64);

    static {
        Set<Class<?>> allBeanSet = BeanLoadHelper.getAllBeanSet();
        //这里都是单实例bean
        for (Class<?> bean : allBeanSet) {
            Object newInstance = ReflectionUtil.newInstance(bean);
            BEAN_MAP.put(bean, newInstance);
        }
    }

    /**
     * 获取所有bean 映射
     *
     * @return Map<Class   <   ?>, Object>
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     * @param cls class
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        if(!BEAN_MAP.containsKey(cls))
        {
          throw new RuntimeException("class not found : "+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 添加bean实例
     *
     * @param cls
     * @param object
     */
    public static void setBean(Class<?> cls, Object object) {
        BEAN_MAP.put(cls, object);
    }

}
