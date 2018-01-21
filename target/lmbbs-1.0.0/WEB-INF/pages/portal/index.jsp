<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="mm" uri="/WEB-INF/mytags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title> [ Mu Mu 博 客 ]</title>
    <link rel="shortcut icon" href="images/me.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/loginbox.css">
    <link rel="stylesheet" type="text/css" href="https://at.alicdn.com/t/font_516817_yciqkobhzqqto6r.css">
    <script type="text/javascript" src="js/jquery-3.2.1.js" charset="utf-8" async defer></script>
    <script type="text/javascript" src="js/mmjs_userlogin.js" charset="utf-8" async defer></script>
    <script type="text/javascript" src="js/mmjs_util.js" charset="utf-8" async defer></script>
</head>
<body>
<!-- 一.头部 -->
<header>
    <div class="header-panel">

        <!--LOGO-->
        <div class="hp-logo fl"><!--1. LOGO -->
            <a href="首页" class="logo">
                <img src="images/blogease.png" alt="木木博客" width="60" height="60"/>
                <span>博客</span>
            </a>
        </div>

        <div class="hp-search fl"><!--2. 搜索框 -->
            <input type="text" class="search-box"/>
            <i class="iconfont icon-sousuo"></i>
        </div>

        <!-- 3.导航模块 10占8，两端留白-->
        <div class="hp-navi fl">
            <ul>
                <li></li>
                <li>视频</li>
                <li>音乐</li>
                <li>阅读</li>
                <li>博客</li>
                <li>论坛</li>
                <li>咨询</li>
                <li>模块</li>
                <li>模块</li>
                <li></li>
            </ul>
        </div>

        <!--4. 用户登录注册 -->
        <div class="hp-user fl">
            <ul id="mmloginBox">
              <%-- 为了静态化。动态内容舍弃
               <c:if test="${ SESSION_LOGGED_ON_USER eq null}" var="ISHASSESSION">
                    <li></li>
                    <li><a href="javascript:void(0)" onclick="mmlogin.login()">登录</a></li>
                    <li><a href="javascript:void(0)" onclick="mmlogin.logout()">注册</a></li>
                    <li></li>
                </c:if>

                <c:if test="${!ISHASSESSION}">
                    <li>消息图标 </li>
                    <li><a href="可去个人中心"></a>用户像${user.avanda}</li>
                    <li>写博客的图标</li>
                    <li><a href="javascript:void(0)" onclick="mmlogin.logout()">退出</a></li>
                </c:if>--%>
            </ul>
        </div>
    </div>
</header>
        ${user.avatar}
</body>
</html>
