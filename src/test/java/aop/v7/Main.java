package aop.v7;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context=  new AnnotationConfigApplicationContext(AopConfig.class);

        context.getBean(DemoAnnotionService.class).add();
        context.getBean(DemoMethodSerive.class).add();


        context.close();
    }
}
