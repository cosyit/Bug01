package com.cosyit.lmbbs.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;

@WebFilter(filterName="LoggingFilter",urlPatterns = {"/*"},initParams = {
        @WebInitParam(name="logFileName",value = "log.txt"),
        @WebInitParam(name = "prefix",value="URI:")
})
public class LoggingFilter implements Filter {
    //把用到的数据类型，定义为属性。
    private PrintWriter logger;
    private String prefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix=filterConfig.getInitParameter("prefix");
        //logFileName用于创建printWriter的输出流。
        String logFileName=filterConfig.getInitParameter("logFileName");
//在目前最新的Java Servlet API 2.1版本中，不赞成使用request.getRealPath()这个方法，使用ServletContext.getRealPath(java.lang.String)代替它。
        //通过这个方法可以获取到app的工作目录working directory. pwd显示。
        // pwd路径，结合logFileName就可以设计日志绝对的路径。
        String appPath=filterConfig.getServletContext().getRealPath("/");

        try {
            logger = new PrintWriter(new File(appPath,logFileName),"UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
        System.out.println("日志过滤器 init方法执行完毕:日志文件已创建。\n如应用中文件已存在将覆盖之。");


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转的目的让对象升级。拥有更多需要的功能方法。
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        logger.println(new Date() + " "+prefix+request.getRequestURI() );
        logger.flush();
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        //应用关闭后，PrintWriter大对象需要被关闭。
        if(logger==null) logger.close();
    }

/*    	<filter>
		<filter-name>ImageProtectorFilter</filter-name>
		<filter-class>com.cosyit.lmbbs.web.filter.ImageProtectorFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>ImageProtectorFilter</filter-name>
		<url-pattern>*.png</url-pattern>
	</filter-mapping>
	<filter-mapping>
		<filter-name>ImageProtectorFilter</filter-name>
		<url-pattern>*.jpg</url-pattern>
	</filter-mapping>
	<filter-mapping>
		<filter-name>ImageProtectorFilter</filter-name>
		<url-pattern>*.gif</url-pattern>
	</filter-mapping>*/

}
