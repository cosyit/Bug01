package aop.v6;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logging {
    @After(value = "execution(* aop.v6.AlogithimImpl.*(..))")//这个过程就是横切过程。非写死在代码中。
    public void beforeLoggin(JoinPoint joinPoint){
        System.out.println(Arrays.asList(joinPoint.getArgs()));
        System.out.println("Advice ->" +  joinPoint.getSignature().getName() +  "-"+Arrays.asList(joinPoint.getArgs()));
    }
}
