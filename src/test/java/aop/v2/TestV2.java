package aop.v2;

public class TestV2 {

    public static void main(String[] args) {
        Hello helloProxy=new HelloProxy();
        helloProxy.say("木木老师");
    }
}
