package aop.v5;

public class TestTarget {
    public void testMethod(){
        for(int i=0;i<10000;i++){
            System.out.println(i);
        }
    }
}
