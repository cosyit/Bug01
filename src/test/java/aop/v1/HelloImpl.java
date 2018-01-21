package aop.v1;

public class HelloImpl implements  Hello{
    @Override
    public void say(String name) {
        System.out.println("前面做的事情");
        System.out.println("Hello"+name);
        System.out.println("后面做的事情");
    }
}
