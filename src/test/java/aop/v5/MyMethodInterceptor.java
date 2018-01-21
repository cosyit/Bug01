package aop.v5;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
//我的方法拦截
public class MyMethodInterceptor implements MethodInterceptor {

    public <T> T getProxy(Class<T> cls){
        //增强器：spring 前置通知，后置增强，环绕增强，返回通知都是一个道理。
        return (T) Enhancer.create(cls,this);
    }

    /**
     *
     * @param obj
     * @param method
     * @param args
     * @param methodProxy 方法级别的代理。
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        long before = System.currentTimeMillis();
        Object result = null;

        try {
            //    @before

            result = methodProxy.invokeSuper(obj,args);

            //    @AfterReturning

        } catch (Throwable throwable) {

            throwable.printStackTrace();

            //    @afterThrowing

        }

        //    @After

        // ps：@Around：围绕方法执行。环绕通知需要携带ProceedingJoinPoint类型的参数。
        long after =System.currentTimeMillis();
        System.out.println("程序方法性能测试 : "+(after-before));
        return result;
    }
}
