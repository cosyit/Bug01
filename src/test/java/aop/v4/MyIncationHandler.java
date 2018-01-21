package aop.v4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 重构MyIncationHandler ，避免到处都调用Proxy类的工厂方法。
 * 给v3版本此类设计一个
 */
public class MyIncationHandler implements InvocationHandler {

    //被代理对象
    private Object target;

    //构造器方式：初始化一下属性。
    public MyIncationHandler(Object target){
        this.target=target;
    }

    //@SuppressWarnings("unchecked")//忽略编译时候的向下转型的警告，工厂方法返回的是Object类型。
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前面做的事情");
        //Method 反射
        Object result= method.invoke(target,args);
        System.out.println("后面做的事情");
        return result;
    }



}
