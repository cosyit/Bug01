package com.cosyit.lmbbs.web.servlet;

import javax.servlet.RequestDispatcher;
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
        System.out.println(uri);

        String action=uri.substring(uri.lastIndexOf("/")+1);

        System.out.println(action);
        String dispatchUrl = null;
        if("index".equals(action)){ //如果action是index,就做index的方法。
            //index(request,response);
            dispatchUrl="/WEB-INF/pages/portal/index.jsp";
        }
        else if("smanage".equals(action)){
            //manager(request,response);
            dispatchUrl="/WEB-INF/pages/manage/sm/index.jsp";
            System.out.println("分发器地址为："+dispatchUrl );
        }


        if(dispatchUrl !=null){
            System.out.println(1);
            RequestDispatcher rd=request.getRequestDispatcher(dispatchUrl);
            rd.forward(request,response);
        }
    }



/*
    这种编程风格太过时了。
    private void manager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //这里是指定浏览器跳转一个页面还是顺水推舟转发一个页面，还是发送Ajax响应呢？转发吧，比较是管理，肯定要服务器去领路的的。
        request.getRequestDispatcher("")

    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //index方法什么都不做，就是做一个页面的跳转而已。
        getServletContext().getRequestDispatcher("/WEB-INF/pages/portal/index.jsp").forward(request,response);
    }*/

}
