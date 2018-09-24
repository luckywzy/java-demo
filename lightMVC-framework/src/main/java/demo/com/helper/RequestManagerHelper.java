package demo.com.helper;

import demo.com.annotation.MyRequestMapping;
import demo.com.annotation.MyRequestMethod;
import demo.com.mdel.Request;
import demo.com.mdel.RequestHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求管理器， 封装请求和动作
 */
public class RequestManagerHelper {

    //请求和动作方法的映射
    private static final Map<Request, RequestHandler> REQ_ACTION_MAP = new HashMap<>(64);

    static {
        Set<Class<?>> controllerBeanSet = BeanLoadHelper.getControllerBeanSet();

        if (controllerBeanSet.size() > 0) {
            //遍历controller
            for (Class<?> ControllerClass : controllerBeanSet) {
                // 得到controller中的所有方法
                Method[] methods = ControllerClass.getDeclaredMethods();
                if (methods.length > 0) {
                    // 遍历所有方法
                    for (Method method : methods) {
                        //得到有MyRequestMapping 标记的方法
                        if (method.isAnnotationPresent(MyRequestMapping.class)) {
                            // 获取注解值
                            MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                            String path = annotation.path();
                            MyRequestMethod[] requestmethod = annotation.method();
                            // 设置请求路径和 请求 处理的方法
                            Request request = new Request((requestmethod.length == 0 ? MyRequestMethod.GET : requestmethod[0]), path);
                            RequestHandler handler = new RequestHandler(ControllerClass, method);
                            REQ_ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static RequestHandler getHandler(MyRequestMethod requestMethod, String requestPath) {
        return REQ_ACTION_MAP.get(new Request(requestMethod, requestPath));
    }


}
