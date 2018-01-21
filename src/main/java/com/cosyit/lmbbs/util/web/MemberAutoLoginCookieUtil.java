package com.cosyit.lmbbs.util.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cosyit.lmbbs.entity.User;
import com.cosyit.lmbbs.service.interfaces.UserService;

public class MemberAutoLoginCookieUtil {
	/**
	 * 这个方法将来要在servlet里面自动登录成功后，如果选择了自动登录。就需要完成自动登录的操作
	 * //需要添加cookie，还需要把cookie的数据存入数据库，准备自动登录的工作，以便自动登录时使用。
	 * 
	 * @param response
	 * @param autoLoginDays
	 * @param user
	 *            给哪个用户添加cookie，用户登录成功后发cookie， 所以业务逻辑和用户对象user关联 自动登录有效期多少天
	 */
	public static void addAutoLoginCookieAndSetAutoAntherKey(HttpServletResponse response, int autoLoginDays,
			User user) {
		// 标准的cookie的value是有讲究的，包含以下的信息。

		// 获得过期时间点
		long expiretime = System.currentTimeMillis() + (long) (autoLoginDays * 24 * 3600);
		// // 这是服务器写过去的数据，给客户端读取的
		String autoLoginAuthKey = user.getId() + "_" + expiretime;
		// 通过cookie的帮助类CookieUtil类创建
		String cookieName = "user";
		String cookieValue = autoLoginAuthKey;
		String cookiePath = "/";// 默认路径
		int maxAge = autoLoginDays * 24 * 3600;
		CookieUitl.addCookie(cookieName, cookieValue, cookiePath, maxAge, response);
		// 记录autoAnthkey--存入到数据库中
		user.setAutoLoginAuthKey(autoLoginAuthKey);
	}

	// 尝试去做自动登录的时候做以下事情
	public static void tryToAutoLogin(HttpServletRequest request, UserService userService) {

		//已经登录直接return
		if (MemberAuthUtil.isLogin(request)) {
			return;
		}
		// 遍历cookie串，获取满足条件的值
		String cookieValue = CookieUitl.getCookieValue("AUTO_LOGIN_COOKIE", request);
		if (cookieValue == null) {// 没有自动登录的cookie信息
			return;// 没找到值，不管你了，程序直接返回，函数结束。
		}
		// 如果以上条件都不满足
		String[] tokens = cookieValue.split("_");
		/* 分析：下划线前面的是用户ID，下划线后面的是过期时间，首先我们得看看用户ID能不能找到一个用户对象 */
		User user = userService.findById(tokens[0]);
		// 当前时间，将会于cookie里的值，做比较
		long now = System.currentTimeMillis();
		// user!=null，说明的确是我用的信息，并且当前的时间小于那个时间（当前的时间还没有到那个时间），否则就过期了。
		if (user != null && now < Long.parseLong(tokens[1])) {
			// ok可以自动登录--即把用户的信息保存到session里去。
			MemberAuthUtil.logon(request, user);
		}
	}

	// 写一个配合注销操作的清除cookie的操作--不是直接清除cookie而是把cookie的值变为空
	public static void delCookie(HttpServletResponse response) {
		String cookieName = WebContaints.AUTO_LOGIN_COOKIE;
		String cookieValue = "";
		String cookiePath = "/";// 默认路径
		int maxAge = 0;
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath(cookiePath);
		// 把同一个名字的cookie响应给客户端。只不过将它的有效时间变为0，再做自动登录的时候，有会给他新默认的时间。
		response.addCookie(cookie);

	}
}
