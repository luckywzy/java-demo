package demo.DI;

import demo.anntation.BeanUtil;
import demo.anntation.MyAutoWired;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassPathXMLApplicationContext {
    Logger LOG = LoggerFactory.getLogger(ClassPathXMLApplicationContext.class);

    List<BeanDefine> beanList = new LinkedList<>();
    Map<String, Object> singletons = new HashMap<>();

    public ClassPathXMLApplicationContext(String fileName) {

        this.readXML(fileName); //读XML配置文件

        this.instanceBeans(); //初始化bean

        this.annotationInject(); //注解处理器
    }

    //注解处理器
    private void annotationInject() {
        for (String beanName : singletons.keySet()) {
            Object bean = singletons.get(beanName);
            if (bean != null) {
                this.propertyAnnotation(bean);
                this.fieldAnnotation(bean);
            }
        }
    }

    //方法注解处理
    private void propertyAnnotation(Object bean) {

        try {
            //获取属性描述
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();

            for (PropertyDescriptor p : propertyDescriptors) {
                Method setter = p.getWriteMethod(); //获取所有setter方法
                //判断是否是自定义的注解
                if (setter != null && setter.isAnnotationPresent(MyAutoWired.class)) {
                    MyAutoWired annotation = setter.getAnnotation(MyAutoWired.class);
                    String name = "";
                    Object needInjectObj = null;
                    if (annotation != null && !name.equals(annotation.name())) {
                        name = annotation.name();
                        needInjectObj = singletons.get(name);
                    } else { //如果当前没有name属性，则根据类型匹配
                        for (String key : singletons.keySet()) {
                            if (p.getPropertyType().isAssignableFrom(singletons.get(key).getClass())) {
                                needInjectObj = singletons.get(key);
                                break;
                            }
                        }
                    }
                    setter.setAccessible(true);
                    setter.invoke(bean, needInjectObj);
                }
            }
        } catch (Exception e) {
            LOG.error("set 方法注入错误：", e);
        }
    }

    //属性注解处理
    private void fieldAnnotation(Object bean) {
        //获取所有字段描述  注意区分bean.getClass().getFields()
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field f : fields) {
            //判断注解是不是我定义的注解
            if (f != null && f.isAnnotationPresent(MyAutoWired.class)) {
                MyAutoWired annotation = f.getAnnotation(MyAutoWired.class);
                String name = "";
                Object value = null;
                // 注解上 name 有值，直接在bean集合中找到这个对象，并将其注入
                if (annotation.name() != null && !"".equals(annotation.name())) {
                    name = annotation.name();
                    value = singletons.get(name);
                } else {
                    for (String key : singletons.keySet()) {
                        //获取当前属性的类型，根据类型匹配
                        if (f.getType().isAssignableFrom(singletons.get(key).getClass())) {
                            value = singletons.get(key);
                            break;
                        }
                    }
                }
                //允许 访问private 字段
                f.setAccessible(true);
                try {
                    //把引用对象注入属性
                    f.set(bean, value);
                } catch (IllegalAccessException e) {
                    LOG.error("自动注入引用对象出错:", e);
                }
            }
        }
    }

    //使用 class. forName 实例化bean
    private void instanceBeans() {
        for (BeanDefine bean : beanList) {
            try {
                singletons.put(bean.getId(), Class.forName(bean.getClassName()).newInstance());
            } catch (Exception e) {
                LOG.error("实例化bean出错:", e);
            }
        }
    }

    private void readXML(String fileName) {
        Document document = null;
        SAXReader saxReader = new SAXReader();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            document = saxReader.read(classLoader.getResourceAsStream(fileName));
            Element beans = document.getRootElement();

            //获取xml 文件中bean元素
            for (Iterator<Element> beanIterator = beans.elementIterator(); beanIterator.hasNext(); ) {
                Element element = beanIterator.next();
                if ("component-scan".equals(element.getName())) {
                    String basePackage = element.attributeValue("base-package");
                    BeanUtil beanUtil = new BeanUtil(beanList, basePackage);
                    beanUtil.initAnnotation();
                }
                //bean  的属性 id  和 class
                BeanDefine bean = new BeanDefine(
                        element.attributeValue("id"),
                        element.attributeValue("class"));

                beanList.add(bean);
            }
        } catch (DocumentException e) {
            LOG.error("读取XML配置文件出错:{}", fileName);
        }
    }

    public Object getBean(String beanId) {
        return singletons.get(beanId);
    }

    public <T extends Object> T getBean(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            LOG.error("实例化对象错误：", e);
        } catch (IllegalAccessException e) {
            LOG.error("传入对象不合法：", e);
        }
        return null;
    }
}
