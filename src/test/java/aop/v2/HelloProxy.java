package aop.v2;

//静态代理：1.继承需求接口。2.组合被代理对象，赋值方式构造器，给组合属性赋值。3.编写业务逻辑。此处为：after,before
public class HelloProxy implements aop.v2.Hello {
    //被代理对象
    private Hello hello;

    public HelloProxy(){
        hello = (name)-> System.out.println("hello"+name);//即是hello=new HelloImpl();这样可以少写一个实现类。
    }

    public void before(){
        System.out.println("前面做的事情");
    }
    public void after(){
        System.out.println("后面做的事情");
    }

    @Override
    public void say(String name) {
        before();
        hello.say("mumu");
        after();
    }
}
