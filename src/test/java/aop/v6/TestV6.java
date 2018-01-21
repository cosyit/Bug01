package aop.v6;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestV6 {

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"application_test.xml"});
        Algorithim alogithim= context.getBean("xxx",Algorithim.class);
        System.out.println(alogithim.add(8,8));
    }
}
