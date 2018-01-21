package com.cosyit.lmbbs.util.web;

//Cookie的存取包装成帮助类
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 包装cookie 的操作：把Cookie的添加和取的操作包装成类
 * Cookie登录无非是在登录成功的时候，添加cookie。以后再登录的时候找cookie的内容。
 * 把这2个操作都包装到类中。MemberAutoLoginCookieUtil《会员登录cookie类》。
 * 
 * @author Administrator
 *
 */
public class CookieUitl {
	/**
	 * 《存》 添加cookie就是创建新的cookie， 该cookie有以下的属性呢：
	 * 
	 * @param cookieName
	 *            名
	 * @param cookieValue
	 *            值
	 * @param	cookiePath
	 *            路径
	 * @param maxAge
	 *            cookie的时间
	 */
	public static void addCookie(String cookieName, String cookieValue, String cookiePath, Integer maxAge,
			HttpServletResponse response) {
		// 如果cookie的路径为空，给其默认路径
		if (cookiePath == null) {
			cookiePath = "/";
		}
		// 如果cookie的不存在时间，给他一个默认时间
		if (maxAge == null) {
			maxAge = 0;
		}
		// 创建cookie
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath(cookiePath);
		// 服务器给响应对象添加cookie
		response.addCookie(cookie);
	}

	/**
	 * 《取》 从请求中根据名字查找cookie
	 * 
	 * @param cookieName
	 * @param request
	 */
	public static String getCookieValue(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		// 如果没有返回空
		if (cookies == null) {
			return null;
		}
		// 如果有，遍历
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				// 如果服务器找到了匹配的返回cookie的值
				return cookies[i].getValue();
			}
		}
		// 没结果就返回空
		return null;
	}
}
