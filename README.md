# java-demo
尝试实现一些关于Java 常用的特性的demo
- 加深自己对组件的理解
- 明白组件的原理实现

### MVC 框架的简单实现
- 包括实现 dispatchServlet 请求派发器、View 视图、 Data 数据；其中view和Data都可以携带数据 即model
- IOC框架，DI依赖注入实现（注解实现）
- 实现注解有：Controller， Service、Inject（自动注入）、RequestMapping（URL映射路径）
- 测试模块是 testWeb，将 Tomcat 作为 plugin 放在 pom 文件中，运行使用 mvn tomcat:run 命令即可

### AOP 框架实现
##### 实现原理
使用CGLIB生成的代理类，并且支持多个代理类，有机会可以实现在 CGLIB 和 JDK 动态代理之间切换
##### 使用方式
继承MyAspectProxy类，实现其中的切面方法（begin、interruptor、before、after、end、error），添加注解@MyAspect(MyController.class)
MyController也是一个注解，目前实现仅支持对指定了某注解（例如@MyController）的类的**所有方法**进行代理；
##### 需要进行优化的点
- 指定AOP的规则
- 仅对指定了的规则的方法进行切面增强

### IOC和AOP整合
- AOPHelper 初始化AOP
- 加入到 MyApplicationContext的初始化列表 中
- 添加pom 

### 添加事务特性
#####原理
事务也是一个切面，在需要添加事务的方法上添加MyTransaction注解，然后在AOP框架中添加对添加了@Service注解的类扫描，如果其中有方法添加了
事务注解，那么就为其添加事务切面类
#####事务切面
事务切面接做三件事
1. 开启事务
2. 提交事务
3. 出现异常回滚提交

**注意**：每个事务关联着一个数据库连接，所以这个连接必须是当前线程的（如果不是当前线程会发生什么？？），即使用ThreadLocal实现
