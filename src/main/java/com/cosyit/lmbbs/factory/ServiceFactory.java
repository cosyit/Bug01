package com.cosyit.lmbbs.factory;

import com.cosyit.lmbbs.dao.interfaces.CategoryDao;
import com.cosyit.lmbbs.dao.interfaces.ForumDao;
import com.cosyit.lmbbs.dao.interfaces.RoleDao;
import com.cosyit.lmbbs.dao.interfaces.SystemprivilegeDao;
import com.cosyit.lmbbs.dao.interfaces.TopicDao;
import com.cosyit.lmbbs.dao.interfaces.UserDao;
import com.cosyit.lmbbs.service.impl.CategoryServiceImpl;
import com.cosyit.lmbbs.service.impl.ForumServiceImpl;
import com.cosyit.lmbbs.service.impl.RoleServiceImpl;
import com.cosyit.lmbbs.service.impl.SystemprivilegeServiceImpl;
import com.cosyit.lmbbs.service.impl.TopicServiceImpl;
import com.cosyit.lmbbs.service.impl.UserServiceImpl;
import com.cosyit.lmbbs.service.interfaces.CategoryService;
import com.cosyit.lmbbs.service.interfaces.ForumService;
import com.cosyit.lmbbs.service.interfaces.RoleService;
import com.cosyit.lmbbs.service.interfaces.SystemprivilegeService;
import com.cosyit.lmbbs.service.interfaces.TopicService;
import com.cosyit.lmbbs.service.interfaces.UserService;

//这里做了一些什么事情呢？
public class ServiceFactory {
    public static UserService getUserService() {
        UserServiceImpl us = new UserServiceImpl();
        UserDao ud = DaoFactory.getUserDao();//通过getUserDao方法的反射的类类型返回之后，获得UserDaoImpl实例对象
        us.setUd(ud);
        return us;
    }

    public static CategoryService getCategoryService() {
        CategoryServiceImpl categoryService = new CategoryServiceImpl();
        CategoryDao categoryDao = DaoFactory.getCategoryDao();//获得了CategoryDaoImpl的实例CDI。
        ForumDao forumDao = DaoFactory.getForumDao();
        categoryService.setcategoryDao(categoryDao);
        categoryService.setForumDao(forumDao);
        return categoryService;
    }

    //把DAO和service集成起来。以后就接口ForumService的一个虚例化对象fs，利用多态效应。去解决所有问题。
    public static ForumService getForumService() {
        ForumServiceImpl fs = new ForumServiceImpl();
        ForumDao fd = DaoFactory.getForumDao();
        fs.setFd(fd);
        return fs;
    }

    public static SystemprivilegeService getSystemPrivilegeServic() {
        //创建业务实现类的实例
        SystemprivilegeServiceImpl ss = new SystemprivilegeServiceImpl();
        //通过工厂创建Dao的实例
        SystemprivilegeDao sd = DaoFactory.getSystemprivilegeDao();
        //要把dao实例设置为业务对象的dao接口内型的属性的值。
        ss.setSd(sd);
        return ss;

    }

    public static RoleService getRoleService() {
        //首先搞（new）一个业务实现类的实例对象
        RoleServiceImpl rs = new RoleServiceImpl();
        //再搞一个dao实现类的实例
        RoleDao rd = DaoFactory.getRoleDao();
        //把	业务类的这个二个属性设值，为实例。即这2个属性private RoleDao rd;private SystemprivilegeService ss;
        rs.setRd(rd);
        rs.setSs(getSystemPrivilegeServic());
        //接口属性赋值完毕，返回！
        return rs;
    }

    public static TopicService getTopicService() {
        TopicServiceImpl ts = new TopicServiceImpl();
        TopicDao td = DaoFactory.getTopicDao();
        ts.setTd(td);
        return ts;
    }
}
