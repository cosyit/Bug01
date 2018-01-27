var mmManage = {
    query_category_list: function () {
            $.ajax({
                type: "POST",
                url: "CategoryManageServlet/list",
                success: function (data) {

                    if(data=="null" || data == "fail" ){
                        var html="";
                        html += "<div class='errorMessages'><p> SORRY，服务器丢失了数据，404 ！</p></div>";
                        $("#module_big_screen").html(html);
                        mmMessage.tip("服务器没有响应，数据有误！");
                    }
                        else {
                        var forums = JSON.parse(data);

                        var html = "";
                        html += "<table class='list'>" +
                            "                        <tr class='table_head'>" +
                            "                            <th>名称</th>" +
                            "                            <th width='180px'>主题数</th>" +
                            "                            <th width='180px'>文章数</th>" +
                            "                            <th width='280px'>相关操作</th>" +
                            "                        </tr>" +
                            "                        <!-- 这个就是取categorys 显示数据列表开始-->";
                        var len = forums.length;

                        for (var i = 0; i < len; i++) {

                            html += "<tr class='item'>\n" +
                                "                                <td colspan='3' style='color: #70c7d8; font-weight: bold;'>" + forums[i].name + "</td>\n" +
                                "                                <td><a href='javascript:void(0)' onclick='mmManage.update_category()' dataname='"+forums[i].name+"'  dataid='"+forums[i].id+"' id='category_update'>修改</a>\n" +
                                "                                    <a href='javascript:void(0)' onclick='mmManage.delete_category()' dataname='"+forums[i].name+"'  dataid='"+forums[i].id+"' id='category_delete'>删除</a>\n" +
                                "                                    <a href='javascript:void(0)'>上移</a>\n" +
                                "                                    <a href='javascript:void(0)'>下移</a>\n" +
                                "                                    <!-- 这边向服务器发送请求，服务器的servlet一定是要处理这个请求里的一些业务需要的逻辑的，配合页\n" +
                                "                                    显示出来的。Servlet中定义addUI方法，addUI的方法里，肯定是要跳转到添加子版面的页面去的。 -->\n" +
                                "                                    <a href='javascript:void(0)'>添加子版面</a>\n" +
                                "                                </td>\n" +
                                "                            </tr>";

                            for (var j = 0; j < forums[i].length; j++) {
                                html += "<tr>\n" +
                                    "                                    <td style='padding-left: 30px;'>\n" +
                                    "                                        <span style='color: #787878;'>" + forums[i][j].name + " </span><br>\n" +
                                    "                                        描述: " + forums[i][j].description + "\n" +
                                    "                                    </td>\n" +
                                    "                                    <td>+" + forums[i][j].topicCount + "</td>\n" +
                                    "                                    <td>" + forums[i][j].articleCount + "</td>\n" +
                                    "                                    <td><a href='ForumManageServlet?method=updateUI&forumId=" + forums[i][j].id + "'>修改</a>\n" +
                                    "                                        <a href='javascript:void(0)' onclick='delForum('" + forums[i][j].id + "')'>删除</a>\n" +
                                    "                                        <a href='javascript:void(0)'>上移</a>\n" +
                                    "                                        <a href='javascript:void(0)'>下移</a>\n" +
                                    "                                    </td>\n" +
                                    "                                </tr>";
                            }

                        }
                        html += "<tr class='func_list'><td align='right' colspan='4'><a href='script:void(0)' onclick='mmManage.add_category()'>添加分类</a></td></tr></table>";
                        $("#module_big_screen").html(html);
                        $(".Channel_title").html("[ 版 块 管 理 ]");
                       // mmMessage.tip("版块管理列表成功显示 ！")
                    }
                    }

            });
        }
        ,
    /**
     *
     添加版面做的事情仅仅弹窗即可。无须发送ajax，因为还没输入呢。 todo 我添加更改一起做可行的话，就一起做。先写着试试。
     *
     */
    add_category:function(){
        $("login_dialog").remove();
        $('body').append(mmManage.wangToAddCategoryView);

        $("#login_dialog").next().off("click").on("click",function () {
            $("#login_dialog").add(this).fadeOut("slow",function(){
                $(this).remove();  // $("#login_dialog").add(this) 组合值，add的功能。
            });
        });

        $("#login_dialog").find(".submit_btn").off("click").on("click",clickAddFucntion);

        //为了去事件，把点击发送Ajax的函数封装。
        function clickAddFucntion(){
            //绿色按钮
            var $this = $(this);

            if(isEmpty($(".username").val())){
                mmMessage.tip("请输入新版面的名称")
                $(".username").focus();
                return;
            }

            //已经被点击过后就要去事件。
            $this.off("click").text("添加中...").css("background","#567");

            $.ajax({
                type:"POST",
                url:"CategoryManageServlet/add_category",
                data:{"categoryname":$(".username").val()},
                error:function () {
                    //服务器出错，把登录按钮的事件重新绑定回来。
                    $this.on("click",clickAddFucntion).text("添加").css("background","darkcyan");  //防止登录ING时浪费请求_代码2
                    // $(this).on("click",logonOpration());
                },
                cache:false,//如果请求相同的URL ，浏览器就会去取缓存，so
                success : function(data){
                    //服务器出错和这里[Ajax发回来的时候，再改回来。]
                    $(".submit_btn").on("click",clickAddFucntion).text("添加").css("background","darkcyan");
                    if(data == "fail" || data=="null"){
                        mmMessage.tip("请输入合适的长度的版块名");
                        $(".username").val("").focus();
                    }
                   else if(data == "success"){
                        mmMessage.tip("添加成功!");
                        $("#login_dialog").next().trigger('click');//点击它的旁边区域，让登录框消失。

                        //mmManage.query_category_list();//操作完成后，再次遍历视图。事实发现这个操作很多余。TODO 没想通为什么会有显示的提示。
                        //trigger() 触发事件 --规定被选元素要触发的事件
                        //更改用户信息。
                    }
                }
            });
        };
    }
    ,
    update_category:function(){
        this;//mmManager

        $.ajax({
            type:"POST",
            url:"CategoryManageServlet/updateCategoryById",
            data:{"id":this.dataid},
            success:function(){
               alert(1);
            }
        });
        //当我点击updata方法按钮时，先发ajax根据id，把值查询到，再值注入到 即将调出的视图。
        $("body").html("<div id='login_dialog'>"+
            "    <div class='mid_position'>"+
            "        <div class='logo m8-w240-h40'></div>"+
            "        <div class='content_typing_u m8-w240-h40'>"+
            "            <span class='login_txtinfo'>版名</span><input type='text' class='username login_input_style' name='categoryname'  placeholder='请输入版面名...'>"+
            "        </div>"+
            "        <div class='m8-w240-h40' >"+
            "            <div class='autologins_7days'>"+
            "                <span>请仔细确认信息</span>"+
            "            </div>"+
            "            <div class='submit_btn' onclick='mmManage.query_category_list()'>修改</div>"+
            "        </div>"+
            "        <div class='m8-w240-h40'>"+
            "            <small>立刻充值，权限大升级!</small><a href='javascript:void(0)' style='color: #ff2e1d'>充值</a>"+
            "        </div>"+
            "    </div>"+
            "</div>"+
            "<!--弹窗遮罩层-->"+
            "<div class='popup_box'></div>");
    }
    ,
    delete_category:function(){

        alert(("#category_delete").data("dataid"));
        alert(("#category_delete").attr("dataid"));
        alert(this.getAttribute("dataid"));
        alert($(this).attr("dataid"));
        alert($(this).data("dataid"));

       /* $.ajax({
            type:"POST",
            url:"CategoryManageServlet/deleteCategoryById",
            data:{"id":$("#category_delete").attr("dataid")},
            success:function(data){
                //alert(data);
            }

        });*/

    }






























    ,
    wangToAddCategoryView:"<div id='login_dialog'>"+
    "    <div class='mid_position'>"+
    "        <div class='logo m8-w240-h40'></div>"+
    "        <div class='content_typing_u m8-w240-h40'>"+
    "            <span class='login_txtinfo'>版面名</span><input type='text' class='username login_input_style' name='categoryname'  placeholder='请输入版面名...'>"+
    "        </div>"+
    "        <div class='m8-w240-h40' >"+
    "            <div class='autologins_7days'>"+
    "                <span>请仔细确认信息</span>"+
    "            </div>"+
    "            <div class='submit_btn' onclick='mmManage.query_category_list()'>添加</div>"+
    "        </div>"+
    "        <div class='m8-w240-h40'>"+
    "            <small>立刻充值，权限大升级!</small><a href='javascript:void(0)' style='color: #ff2e1d'>充值</a>"+
    "        </div>"+
    "    </div>"+
    "</div>"+
    "<!--弹窗遮罩层-->"+
    "<div class='popup_box'></div>"

}

