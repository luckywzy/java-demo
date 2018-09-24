package demo.com.helper;

import demo.com.annotation.MyController;
import demo.com.annotation.MyService;
import demo.com.utils.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 用来获取应用基础包下的，所有添加了MyController,MyService注解的类
 * 即 需要管理的bean
 */
public final class BeanLoadHelper {

    //存放 加载的bean 集合
    private static final Set<Class<?>> BEAN_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        BEAN_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getBeanSet() {
        return BEAN_SET;
    }

    /**
     * 获取所有的 service bean
     *
     * @return
     */
    public static Set<Class<?>> getServiceBeanSet() {
        Set<Class<?>> serviceSet = new HashSet<>();

        for (Class<?> cls : BEAN_SET) {
            if (cls.isAnnotationPresent(MyService.class)) {
                serviceSet.add(cls);
            }
        }
        return serviceSet;
    }

    /**
     * 获取所有的 Controller bean
     *
     * @return
     */
    public static Set<Class<?>> getControllerBeanSet() {
        Set<Class<?>> ControllerSet = new HashSet<>();

        for (Class<?> cls : BEAN_SET) {
            if (cls.isAnnotationPresent(MyController.class)) {
                ControllerSet.add(cls);
            }
        }
        return ControllerSet;
    }

    /**
     * 获取所有的需要管理的 bean
     * 就是 controller 和service
     * @return
     */
    public static Set<Class<?>> getAllBeanSet() {
        Set<Class<?>> beanSet = new HashSet<>();

        beanSet.addAll(getServiceBeanSet());
        beanSet.addAll(getControllerBeanSet());
        return beanSet;
    }

}
