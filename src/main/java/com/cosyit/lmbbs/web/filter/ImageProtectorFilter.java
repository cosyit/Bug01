package com.cosyit.lmbbs.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP Referer是header的一部分，当浏览器向web服务器发送请求的时候，一般会带上Referer，
 * 告诉服务器我是从哪个页面链接过来的，服务器基此可以获得一些信息用于处理。
 *
 * 如果你也曾复制过一些类似腾讯等网站的文章，你会发现黏贴下来发表之后经常出现图片无法显示，这是如何做到的呢？
 *
 */
public class ImageProtectorFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("图像保护过滤器");
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        //检查Header的refferer值。首先得取得其值。
        String referrer=httpServletRequest.getHeader("referer");
        System.out.println("Referer"+referrer);
        //检查referer值,如果不是我的网站。重定向到一个位置。如果你可以伪造referer技术。我觉得，这个技术有点过分。
        if(referrer!=null || !referrer.startsWith("localhost")){ //referer有值，返回request语法的原始页面作为referer值。
            HttpServletResponse response= (HttpServletResponse) resp;
            response.sendRedirect("/index.jsp");//http://domian.com
            chain.doFilter(req,resp);
        }else{
            throw new ServletException("Image not available,因为请求中没有referrer值，意味着直接通过URL来访问的。可能被其他站点所引用。");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
