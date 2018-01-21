<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mm" uri="/WEB-INF/mytags.tld" %>
<html>
<head>
    <title>测试页</title>
</head>
<body>
    <c:out value="${aritcle}">没有显示啊！</c:out>
    <mm:firstTag/>
</body>
</html>
