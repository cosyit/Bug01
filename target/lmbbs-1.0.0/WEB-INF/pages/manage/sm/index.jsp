<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../commons/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title> [ 系 统 管 理 ]</title>
    <%@ include file="../../commons/link.jsp" %>
</head>
<body>
<%@include file="../../commons/header.jsp" %>

<div class="main_body_container">
    <div class="system_manage">
        <ul class="catalogs fl">
            <li><a href="#">角色管理</a></li>
            <script>

            </script>
            <%-- mmManage.query_category_list() --%>
            <li><a href="javascript:void(0)" onclick="mmManage.query_category_list()">版面管理</a></li>
            <li><a href="#">权限管理</a></li>
            <li><a href="#">用户管理</a></li>
            <li><a href="#">文章管理</a></li>
            <li><a href="#">系统设置</a></li>
            <li><a href="#">返回首页</a></li>
            <%--
                        <li><a href="#">视频管理</a></li>
                        <li><a href="#">主题搜索</a></li>
                        <li><a href="#">博客管理</a></li>
                        --%>

        </ul>
        <div class="the_content fr">
            <div class="Channel_title">模块简介</div>
            <div class="play_view">
                <ul id="module_big_screen">

                </ul>
            </div>
        </div>
    </div>
</div>


<%@include file="../../commons/footer.jsp" %>
</body>
</html>
