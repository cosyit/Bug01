<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%--
        先写 HTML在body中看效果，然后，用转字符串工具，放入JS。
    <div id="massage_tip">
        <span>  <i class="iconfont icon-sousuo"></i> 请输入用户名 </span>
    </div>
--%>
<header>
    <div class="header-panel">

        <!--LOGO-->
        <div class="hp-logo fl"><!--1. LOGO -->
            <a href="PortalServlet/index" class="logo">
                <img src="resources/images/blogease.png" alt="博客" width="66" height="60"/>
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
                <%-- javascript:void(0) --%>
                <li></li>
                <li>版块</li>
                <li>音乐</li>
                <li>图书</li>
                <li>视频</li>
                <li>博客</li>
                <li>论坛</li>
                <li>艺术</li>
                <li>服务</li>
                <li><a href="PortalServlet/smanage">系统</a></li>
            </ul>
        </div>

        <!--4. 用户登录注册 -->
        <div class="hp-user fl">
            <ul id="login_view_container">
                <%-- 为了静态化。这种动态内容舍弃
 <c:if test="${ SESSION_LOGGED_ON_USER eq null}" var="ISHASSESSION">
      <li></li>
      <li><a href="javascript:void(0)" onclick="mmlogin.iwantlogin()">登录</a></li>
      <li><a href="javascript:void(0)" onclick="mmlogin.logout()">注册</a></li>
      <li></li>
  </c:if>

  <c:if test="${!ISHASSESSION}">
      <li>消息图标 </li>
      <li><a href="去个人中心"></a>用户像${user.avanda}</li>
      <li>写博客的图标</li>
      <li><a href="javascript:void(0)" onclick="mmlogin.logout()">退出</a></li>
  </c:if>--%>
            </ul>
        </div>
    </div>
</header>
