<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    int port = request.getServerPort();
    String basePath = null;
    if(port==80){
        basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
    }else{
        basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    }
    pageContext.setAttribute("basePath", basePath);
%>