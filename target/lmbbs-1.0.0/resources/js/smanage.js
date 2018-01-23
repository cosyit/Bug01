var mmManage = {
    layoutManage:function(){
        mmManage.manager_util.commons_changeViewFunction("CategoryManageServlet/list",mmManage.manager_util.layoutManageView);
    },

    manager_util: {
        commons_changeViewFunction: function (ajaxUrl, view) {
            $.ajax({
                type: "POST",
                url: ajaxUrl,//
                success: function (data) {
                    alert(data);
                    var forums =JSON.parse(data);
                    alert(forums[0].id + "=" );
                    var html= "";
                    html+="<div class='errorMessages'></div>";
                    html+="<table class='list'>" +
                        "                        <tr class='table_head'>" +
                        "                            <th>名称</th>" +
                        "                            <th width='180px'>主题数</th>" +
                        "                            <th width='180px'>文章数</th>" +
                        "                            <th width='280px'>相关操作</th>" +
                        "                        </tr>" +
                        "                        <!-- 这个就是取categorys 显示数据列表开始-->";
                    var len= forums.length;
                    alert(len);
                    for(var i=0 ;i<len;i++){
                        html+="<tr class='item'>\n" +
                            "                                <td colspan='3' style='color: #70c7d8; font-weight: bold;'>"+forums[i].name+"</td>\n" +
                            "                                <td><a href='CategoryManageServlet?method=updateUI&id="+forums[i].id +"'>修改</a>\n" +
                            "                                    <a href='#' onclick=''>删除</a>\n" +
                            "                                    <a href='#'>上移</a>\n" +
                            "                                    <a href='#'>下移</a>\n" +
                            "                                    <!-- 这边向服务器发送请求，服务器的servlet一定是要处理这个请求里的一些业务需要的逻辑的，配合页\n" +
                            "                                    显示出来的。Servlet中定义addUI方法，addUI的方法里，肯定是要跳转到添加子版面的页面去的。 -->\n" +
                            "                                    <a href='javascript:void(0)'>添加子版面</a>\n" +
                            "                                </td>\n" +
                            "                            </tr>";

                        for(var j=0;j<forums[i].length;j++){
                            html+="<tr>\n" +
                                "                                    <td style='padding-left: 30px;'>\n" +
                                "                                        <span style='color: #787878;'>"+forums[i][j].name+" </span><br>\n" +
                                "                                        描述: "+forums[i][j].description+"\n" +
                                "                                    </td>\n" +
                                "                                    <td>+"+forums[i][j].topicCount+"</td>\n" +
                                "                                    <td>"+forums[i][j].articleCount +"</td>\n" +
                                "                                    <td><a href='ForumManageServlet?method=updateUI&forumId="+forums[i][j].id+"'>修改</a>\n" +
                                "                                        <a href='#' onclick='delForum('"+forums[i][j].id +"')'>删除</a>\n" +
                                "                                        <a href='#'>上移</a>\n" +
                                "                                        <a href='#'>下移</a>\n" +
                                "                                    </td>\n" +
                                "                                </tr>";
                        }

                    }
                    html+="<tr class='func_list'><td align='right' colspan='4'><a href='CategoryManageServlet?method=addUI'>添加分类</a></td></tr></table>";
                      $("#module_big_screen").html(html);
                }

            });
        }
    }
}

