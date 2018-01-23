<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%@include file="../../commons/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title> [ 系 统 管 理 ]</title>
    <%@ include file="../../commons/link.jsp"%>
</head>
<body>
<%@include file="../../commons/header.jsp"%>

<style>
    .system_manage{
        width: 1000px;
        height:1000px;/*todo delete 暂时支撑而已*/
        background: lightblue;
        margin:30px auto;
    }


    .system_manage .catalogs{
        width: 200px;
        height: 1000px;
        background: #f2f2f2;
    }

    .catalogs li{
        line-height: 30px;
        width: 120px;
        background: cornflowerblue;
        margin:20px auto;
        border-radius: 4px;
        text-align: center;

    }
    .system_manage .the_content{
        width: 800px;
        height: 1000px;
        background: darkgreen;
    }
    .system_manage .Channel_title{
        width: 800px;
        line-height: 60px;
        text-align: center;
        background: darkcyan;
    }

</style>


<div class="main_body_container">
    <div class="system_manage">
        <ul class="catalogs fl">
            <li><a href="#">权限管理</a></li>
            <li><a href="#">用户管理</a></li>
            <li><a href="#">文章管理</a></li>
            <li><a href="#">版块管理</a></li>
            <li><a href="#">会员管理</a></li>
            <li><a href="#">视频管理</a></li>
            <li><a href="#"></a></li>
            <li><a href="#"></a></li>
            <li><a href="#"></a></li>
            <li><a href="#"></a></li>
            <li><a href="#"></a></li>

        </ul>
        <div class="the_content fr">
            <div class="Channel_title">模块简介</div>
            <div class="play_view">大荧幕</div>
        </div>
    </div>
</div>


<%@include file="../../commons/footer.jsp"%>
</body>
</html>
