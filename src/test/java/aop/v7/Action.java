package aop.v7;

import java.lang.annotation.*;

//编写一个拦截规则的注解。
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    String name();
}

