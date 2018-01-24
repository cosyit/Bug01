package com.cosyit.lmbbs.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding = null;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //Content-Type
        //用于定义网络文件的类型和网页的编码，响应给浏览器，
        // 以决定浏览器将以什么形式、什么编码读取这个文件
        response.setContentType("text/html;charset="+encoding);//设置浏览器读。

//        CharacterEncoding
//       作用是设置对客户端请求进行重新编码的编码
        request.setCharacterEncoding(encoding);//设置取。request对象不用强转也能调用这些方法。

        response.setCharacterEncoding(encoding);//设置printWriter对象的编码方式。
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
         encoding =config.getInitParameter("encoding");
    }

}
