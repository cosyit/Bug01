package aop.v7;

import org.springframework.stereotype.Service;

//编写使用注解的被拦截类。
@Service
public class DemoAnnotionService {

    @Action(name="注解式拦截的添加操作...")
    public void add(){};
}
