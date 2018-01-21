package aop.v3;

import java.lang.reflect.InvocationHandler;

/**
 *
 * .java ---编译---> .class --runtime-->JVM
 * 知识铺垫：类装载过程主角:ClassLoader  -->把 .class文件装到JVM。
 * ClassLoader寻找.class文件并构造出类的JVM内部表示对象的内存中的对象组件。
 * ClassLoader工作方式：[rumtime的重要系统原件]
 * 1.装载：查找导入class.
 * 2.连接：{执行校验看class文件数据对不对；准备 给静态变量分配内存；解析步骤符号引用变直接引用[可选]。}
 * 3.初始化工作。方法区部分赋值。
 *
 * ClassLoader运行时将产生3个ClassLoader:
 * 1）.  ClassLoader [C++语言编写 ，查不到源代码。负责装载JRE核心库rt.jar和charset.jar等]
 * 2).  ExtClassLoader 负责装载JRE扩展目录ext目录下的*.jar包，
 * 3).  AppClassLoader 就是我们的src目录下的jar .class文件
 * 以上三个类装载器之间 存在 子父关系
 */


//在一个使用场景下，
// 在编译时仍然不能确定需要实现哪个接口。
//利用JDK的动态代理方案，在runtime时 创建一个实现一组接口的新的代理类。
// proxy是程序设计者用不到，系统设计者经常使用的技能。

import java.lang.reflect.Method;

/**
 * 进阶阅读:构造一个实现了一组接口（平时我都用的是一个接口），
 * 那么这个接口我们可以用一个Class类类型对象表示，如：Myinterface.class
 * 因它是一个接口在编译时候，无法确定类型[接口的实际类型]。
 * 这时候，你想到了用newIntance()方法去构造，或者用反射技术找它的构造器。
 * 但是，你别忘了，一个接口没有对象，不能实例化。编译时的一些技术，你就放弃了。
 * 反射技术就是在编译阶段才有效。绕过编译就绕过来反射。对吧？
 * 那么你就只能从runtime时刻去下手了，就是在运行状态时定义一个新的类。
 *
 * 代理机制是一种很好的方案，它可以在运行时创建一个全新的类，
 * 还能指定它实现什么接口，指定接口所需要的全部方法。[自然也包括Object类的全部方法。]
 *
 * 注意，不能直接在runtime 定义方法的具体代码。而是用一个invocation handler(调用处理器)
 * 这个调用处理器如何得来呢？简单：弄一个 实现InvocationHandler的接口的类对象即可。
 * 这个接口就一个方法：invoke方法(Object proxy_target,Method method,Object..args);
 *  只要代理对象上的方法被调用，
 *  那么invationHandler的invoke(被代理对象，method,args...)方法就会被调用。
 *  [调用处理器对象然后必须找出如何处理这个调用。]
 */

public class MyIncationHandler implements InvocationHandler {

    //被代理对象
    private Object target;

    //构造器方式：初始化一下属性。
    public MyIncationHandler(Object target){
        this.target=target;
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
