<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/commons/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title> [ 系 统 管 理 ]</title>
    <%@ include file="/WEB-INF/pages/commons/link.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/pages/commons/header.jsp"%>

<style>

    .main_body_container{
        width: 1200px;
        height: 100vh;
        background: aliceblue;
        margin:60px auto;
    }

</style>
    <div class="main_body_container">
        <div class="catalogs"></div>
        <div class="play_view"></div>
    </div>


</body>
</html>
