package com.cosyit.lmbbs.util.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cosyit.lmbbs.entity.Role;
import com.cosyit.lmbbs.entity.Systemprivilege;
import com.cosyit.lmbbs.entity.User;
//检查类
public class PrivilegeCheckUtil {
	/**
	 * 
	 * @param request
	 * @param action 哪些动作
	 * @param resource 动作的resource：属于哪一块的动作，如：topic动作，reply动作，manage动作等等
	 * @return
	 */
	//这里有一个判断有没有权限的方法                    参数这块，缺什么我就传什么
	public static boolean isPrivilege(HttpServletRequest request, String action, String resource) {
		//如果你没有登录返回false
		if (!MemberAuthUtil.isLogin(request)){
			return false;
	}
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute(WebContaints.SESSION_LOGGED_ON_USER);	
		//是否为超级管理员，是超级管理员才返回true
		if ("superman".equals(user.getLoginName()))
			return true;
		
		//否则其他情况呢，我们就获得user的角色。先从user对象中得到所有的角色。遍历角色，每个角色所拥有的权限集合再做遍历所有的权限
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Systemprivilege> systemprivileges = role.getSystemprivileges();
			for (Systemprivilege systemprivilege : systemprivileges) {
				if (systemprivilege.getAction().equals(action) && systemprivilege.getResource() == resource)
					return true;
			}
		}
		return false;
	}
}
