package com.cosyit.lmbbs.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cosyit.lmbbs.entity.User;
//成员用户验证的帮助类
public class MemberAuthUtil {
	//从请求中的session里获取SESSION_LOGGED_ON_USER已登录用户的属性，判断是否登录
	public static boolean isLogin(HttpServletRequest request){
		//在session范围内做以下判断
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute(WebContaints.SESSION_LOGGED_ON_USER);
		    if(user==null){
		    	System.out.println("判断用户为空吗:"+(user==null));
			   return false;}//没有有用户存在返回false，说明没有用户登录
			   return true;//有用户存在，返回true,说明已经存在用户登录了。
	}
	
	//登录了，这样干----如果注册成功没有session，还要创建session比较麻烦。修改
//	public static void logon(HttpSession session,User user){
//		session.setAttribute("SESSION_LOGGED_ON_USER", user);
//	}
	//修改
	public static void logon(HttpServletRequest request,User user){
	HttpSession session=request.getSession();
	session.setAttribute(WebContaints.SESSION_LOGGED_ON_USER, user);
}
	//用户注销了，这样干
	public static void loginout(HttpSession session){
		session.removeAttribute(WebContaints.SESSION_LOGGED_ON_USER);
	}
	
/*	//因为将来在很多地方要判断这个事情，所以在帮助类里定义一个是否是管理员的方法
	public static boolean isSuperman(HttpServletRequest request){
		//在request范围里获得sessionHttpRequest对象有两种形式的getSession的方法调用：
	小贴士：
	 *	一个是getSession()
		一个是getSession(boolen isNew)
		这样，前者会检测当前时候是否有session存在，如果不存在则创建一个，如果存在就返回当前的。
		getSession()相当于getSession(true);
		参数为true时，若存在会话，则返回该会话，否则新建一个会话；
		参数为false时，如存在会话，则返回该会话，否则返回NULL；
			HttpSession session=request.getSession();
			User user=(User)request.getAttribute(WebContaints.SESSION_LOGGED_ON_USER);
			if(user!=null&&"superman".equals(user.getLoginName())){
				return true;
			}
			return false;
	}*/
}
