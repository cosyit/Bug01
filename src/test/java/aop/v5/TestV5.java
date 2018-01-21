package aop.v5;

public class TestV5 {
    public static void main(String[] args) {
        //1.首先创建方法拦截器
        MyMethodInterceptor myMethodInterceptor =new MyMethodInterceptor();
        TestTarget testTargetProxy=myMethodInterceptor.getProxy(TestTarget.class);
        testTargetProxy.testMethod();
    }
}
