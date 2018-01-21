package aop.v3;

import aop.v2.Hello;

import java.lang.reflect.Proxy;

public class TestV3 {
    public static void main(String[] args) {
        Hello hello=(name)->
            System.out.println("动态代理的名字："+name);

        MyIncationHandler invocationHandler=new MyIncationHandler(hello);//调用处理器

        //Proxy的工厂方法，动态创建接口代理类。然后调用代理类的say业务方法。
        Hello helloProxy= (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),hello.getClass().getInterfaces(),invocationHandler);

        helloProxy.say("zsb");
    }
}
