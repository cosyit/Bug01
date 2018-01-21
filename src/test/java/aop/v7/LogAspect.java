package aop.v7;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect//1.声明为切面
@Component//2.声明为bean
public class LogAspect {



    @Pointcut("@annotation(com.cosyit.aop.Action)")
    public void annotationPointCut(){};


    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint){
       MethodSignature methodSignature= (MethodSignature)joinPoint.getSignature();

       Method method = methodSignature.getMethod();

        Action action = method.getAnnotation(Action.class);

        System.out.println("注解式拦截器：" +action.name());
    }


    @Before("execution(* com.cosyit.aop.DemoMethodSerive.*(..))")
    public void Before(JoinPoint joinPoint){
        MethodSignature methodSignature= (MethodSignature)joinPoint.getSignature();

        Method method=methodSignature.getMethod();

        System.out.println("方法规则式拦截"+method.getName());
    }

}
