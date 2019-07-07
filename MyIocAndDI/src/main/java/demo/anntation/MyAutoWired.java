package demo.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//生命周期，在运行时执行
@Retention(RetentionPolicy.RUNTIME)
// 适用于 字段，方法
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface MyAutoWired {

	public String name() default "";
}
