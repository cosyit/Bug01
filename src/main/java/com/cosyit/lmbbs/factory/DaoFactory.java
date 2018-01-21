package com.cosyit.lmbbs.factory;

import java.io.IOException;
import java.util.Properties;

import com.cosyit.lmbbs.dao.base.DaoSupport;
import com.cosyit.lmbbs.dao.interfaces.CategoryDao;
import com.cosyit.lmbbs.dao.interfaces.ForumDao;
import com.cosyit.lmbbs.dao.interfaces.RoleDao;
import com.cosyit.lmbbs.dao.interfaces.SystemprivilegeDao;
import com.cosyit.lmbbs.dao.interfaces.TopicDao;
import com.cosyit.lmbbs.dao.interfaces.UserDao;

public class DaoFactory {
private static  Properties prop=new Properties();//注意这里是个静态，应该是一个单例模式。
static{
	try {
   prop.load(DaoFactory.class.getResourceAsStream("/dao.properties"));//斜杠开头，表示相对项目的根目录即这里即相对SRC
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	public static UserDao getUserDao(){
		try {
		return	(UserDao) Class.forName(prop.getProperty("userDao")).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CategoryDao getCategoryDao(){
		try {
			return	(CategoryDao) Class.forName(prop.getProperty("categoryDao")).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static ForumDao getForumDao(){
		try {
			return	(ForumDao) Class.forName(prop.getProperty("forumDao")).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> DaoSupport<T> getBaseDao(){
		try {
		return	(DaoSupport<T>) Class.forName(prop.getProperty("baseDao")).newInstance();
			} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
public static RoleDao getRoleDao(){
		try {
		return	(RoleDao) Class.forName(prop.getProperty("roleDao")).newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

public static SystemprivilegeDao getSystemprivilegeDao(){
	try {
	return	(SystemprivilegeDao) Class.forName(prop.getProperty("systemprivilegeDao")).newInstance();
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public static TopicDao getTopicDao(){
	try {
	return	(TopicDao) Class.forName(prop.getProperty("TopicDao")).newInstance();
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}
}
