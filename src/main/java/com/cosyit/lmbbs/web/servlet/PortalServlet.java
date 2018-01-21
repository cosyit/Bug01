package com.cosyit.lmbbs.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 制作自己的一个控制器类。
 */
public class PortalServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request,response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取请求
        String uri=request.getRequestURI();

        String action=uri.substring(uri.lastIndexOf("/")+1);

        System.out.println(action);

        if("index".equals(action)){ //如果action是index,就做index的方法。
            index(request,response);
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //index方法什么都不做，就是做一个页面的跳转而已。
        getServletContext().getRequestDispatcher("/WEB-INF/pages/portal/index.jsp").forward(request,response);
    }

}
