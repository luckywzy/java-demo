package demo.com;

import demo.com.helper.BeanLoadHelper;
import demo.com.helper.BeanManagerHelper;
import demo.com.helper.IoCHelper;
import demo.com.helper.RequestManagerHelper;
import demo.com.utils.ClassUtil;

/**
 * 上下文环境
 */
public final class MyApplicationContext {

    public static void init() {
        //创建 bean集合
        ClassUtil.loadClass(BeanLoadHelper.class.getName());
        //创建bean容器
        ClassUtil.loadClass(BeanManagerHelper.class.getName());
        //完成依赖注入
        ClassUtil.loadClass(IoCHelper.class.getName());
        // 完成请求到方法的映射
        ClassUtil.loadClass(RequestManagerHelper.class.getName());
    }
}
