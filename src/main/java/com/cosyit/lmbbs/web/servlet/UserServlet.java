package com.cosyit.lmbbs.web.servlet;

import com.cosyit.lmbbs.entity.User;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.UserService;
import com.cosyit.lmbbs.util.commons.MmStringUtils;
import com.cosyit.lmbbs.util.web.MemberAutoLoginCookieUtil;
import com.cosyit.lmbbs.util.web.WebContaints;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet/*"})
public class UserServlet extends HttpServlet {
    private UserService userService = ServiceFactory.getUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }


    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//不管get，post它都可以拿到请求。
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(action);
        /**
         * 当然你也可能是传统的 ?k1=v1&k2=v2的形式
         * 那么就应该使用
         * String method =request.getParameter("actionName_X");
         *
         */

        if ("login".equals(action)) {
            //response.getWriter().print("登录的操作");
            login(request, response);
        }

        if("logout".equals(action)){
            logout(request,response);
        }

        if("haslogged".equals(action)){
            hasLoggedOn(request,response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session后去论坛首页
        HttpSession session=request.getSession();

        session.invalidate();//会话失效

        response.getWriter().print("success");

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户输入，然后和数据库中的进行比较。
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//            String verfi_typing = request.getParameter("verfi_typing");

        PrintWriter out = response.getWriter();


        //密码和邮箱为null,那么响应体推送 响应的null标识
        if (MmStringUtils.isEmpty(username)) {
            out.print("username_null");
            System.out.println("username_null");
            return;
        }
        if (MmStringUtils.isEmpty(password)) {
            out.print("passworld_null");
            System.out.println("passworld_null");
            return;
        }

        //通过我们的当前类的属性user业务对象。来做业务。
        User user=userService.findByLoginNameAndPassword(username, password);
        System.out.println(user);

        HttpSession session = request.getSession();

        //+"==="+verfi_typing 先不做验证，浪费测试时间。测试阶段先写固定值。。
        if (user == null) {
            session.setAttribute("message","fail");
            System.out.println("不存在此用户...");
            out.print(session.getAttribute("message"));
        }
       else if(user.isLocked()){
            session.setAttribute("massage","locked");
            out.print(session.getAttribute("message"));
    }
       if(user != null ) {
            session.setAttribute("message","success");
            System.out.println("验证成功！");
            session.setAttribute("user",user);

            out.print(session.getAttribute("message"));
        }
    }

    private User hasLoggedOn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        User user=(User)request.getSession().getAttribute("user");
       // response.getWriter().print(user==null?"unlogged":"logged"); 不需要这样，返回用户名。
        return user;
    }
}
