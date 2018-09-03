package demo.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE}) //声明该注解用于类、接口等定义时期
public @interface MyBean {

    public String name() default "";
}
