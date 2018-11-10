package demo.com.helper;

import demo.com.annotation.MyAspect;
import demo.com.annotation.MyService;
import demo.com.intface.MyProxy;
import demo.com.proxy.MyAspectProxy;
import demo.com.proxy.MyProxyManager;
import demo.com.transaction.TransactionProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP 帮助类
 */

@Slf4j
public class AOPHelper {

    /**
     * 静态初始化AOP框架
     */
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<MyProxy>> targetMap = createTargetMap(proxyMap);

            for (Map.Entry<Class<?>, List<MyProxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<MyProxy> proxyList = targetEntry.getValue();
                //生成代理调用链
                Object proxy = MyProxyManager.createProxy(targetClass, proxyList);
                //装配成bean ，并放到bean容器中
                BeanManagerHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            log.error("aop container init failure");
        }
    }


    /**
     * 获取代理类和目标类之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {

        HashMap<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 添加事务切面类
     *
     * @param proxyMap
     */
    private static void addTransactionProxy(HashMap<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClasses = BeanLoadHelper.getClassSetByAnnotation(MyService.class);
        proxyMap.put(TransactionProxy.class, serviceClasses);

    }

    /**
     * 添加普通的切面类
     *
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(HashMap<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> proxyClassSet = BeanLoadHelper.getClassSetBySuper(MyAspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(MyAspect.class)) {
                MyAspect aspect = proxyClass.getAnnotation(MyAspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    /**
     * 获取所有带有@MyAspect 注解的切面类
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(MyAspect aspect) throws Exception {

        HashSet<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        //符合切面类的条件 ，带有 MyAspect 注解  和 MyProxy的子类
        if (annotation != null && !annotation.equals(MyAspect.class)) {
            targetClassSet.addAll(BeanLoadHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 生成目标对象到代理对象之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<MyProxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        HashMap<Class<?>, List<MyProxy>> targetMap = new HashMap<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

            for (Class<?> targetClass : targetClassSet) {
                MyProxy proxy = (MyProxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<MyProxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }

        return targetMap;
    }
}
