# java-demo
尝试实现一些关于Java 常用的特性的demo
- 加深自己对组件的理解
- 明白组件的原理实现

### 非常简单的IOC和DI的实现

### Java的不可重入锁和可重入锁的简单实现
-
### MVC 框架的简单实现
- 包括实现 dispatchServlet 请求派发器、View 视图、 Data 数据；其中view和Data都可以携带数据 即model
- IOC框架，DI依赖注入实现（注解实现）
- 实现注解有：Controller， Service、Inject（自动注入）、RequestMapping（URL映射路径）
- 测试在testWeb

### AOP 框架实现
##### 原理
使用CGLIB生成的代理类，并且支持多喝代理类
##### 使用方式
继承MyAspectProxy类，实现其中的切面方法（begin、interruptor、before、after、end、error），添加注解@MyAspect(MyController.class)
MyController也是一个注解，目前实现仅支持对指定了某注解（例如@MyController）的类的**所有方法**进行代理；
##### 需要进行优化的点
- 指定AOP的规则
- 仅对指定了规则的方法进行切面增强

##### ThreadLocal 

### IOC和AOP整合
- AOPHelper 初始化AOP
- 加入到 MyApplicationContext的初始化列表 中
- 添加pom 


######
