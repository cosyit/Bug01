
/*$(function () {});*/
//uncaught ReferenceError: $ is not defined 没找到问题哦！ 找到了！async defer 这个在放在<script标签末尾影响了。
$(document).ready(function () {
    mmlogin.query_login_status_to_server();
});

var mmMessage={
    tip:function (msg) {
        $("#msg_tip").remove();
        $("body").append("<div id='msg_tip'>" +
            "        <span>  <i class='iconfont icon-wodefankui' style='font-size: 28px;margin-right: 28px'></i> "+msg+" </span>" +
            "    </div>");

        $("#msg_tip").animate({top:0},800).delay(1000).animate({top:-64},600);
    }
};

/**
    1.JS语言中，一个类的对象和属性是不分上下顺序的。
    2.因为首页需要做静态化处理，首页是不能写<c:if >这样的动态标签的，所以要做静态化的问题。

 */

var mmlogin={

    //向服务器查询登陆状态。根据登录状态显示不同的登录视图。
    query_login_status_to_server:function(){
        $.ajax({
            type:"POST",
            url:"UserServlet/haslogged",
            success:function (data) {//默认是一个'n'+'u'+'l'+'l' 的null字符串
                if(data!="null"){
                    var userInfo = JSON.parse(data);
                    $("#login_view_container").html(mmlogin.loggedView);


                    $(".login_txtinfo_username").append("<img src='"+userInfo.avatar+"' width='36' height='36' id='user-avatar'>");
                    $("#user-avatar").css({"border-radius":"50%","margin":"12px 0 0 6px"})

                }
                else{
                    $("#login_view_container").html(mmlogin.unloggedView);
                }
            }
        });
    },

    iwantlogin:function(){
        $("login_dialog").remove();
        $('body').append(mmlogin.wangToLoginView);

        //对话框对象的下一个next()元素。 off()函数用于移除元素上绑定的一个或多个事件的事件处理函数。
        // off()函数主要用于解除由on()函数绑定的事件处理函数。本段代码让登录框具有
        // 对话框的下一个元素[即dialog_over阴影层]。
        // 在该阴影层上移除事件处理函数后，绑定一个点击事件。
        // 当被点击时候，执行函数体里的代码：就是登录对话框，add(this)
        //	添加到this上去。并具有且淡出特效。
        $("#login_dialog").next().off("click").on("click",function () {
            //alert(typeof this);
            $("#login_dialog").add(this).fadeOut("slow",function(){
                $(this).remove();
            });
        });

        $("#login_dialog").find(".submit_btn").off("click").on("click",loginMain);

        //为了去事件，把点击发送Ajax的函数封装。
        function loginMain(){
            var $this = $(this);
            //执行登录操作。
            var username=$(".username").val();
            var password=$(".password").val();
            /*      var verfi_typing=$(".verfi_typing").val();*/

            if(isEmpty($(".username").val())){
                mmMessage.tip("请输入账号")
                $(".username").focus();
                return;
            }

            //如果输错密码，还一直点击，会浪费请求。有一个简洁聪明的做法。服务器返回一个fail，在ajax内清空密码。
            if(isEmpty($(".password").val())){
                mmMessage.tip("请输入密码")
                $(".password").focus();
                return;
            }
            /*            if(isEmpty(verfi_typing)){
                            alert("请输入验证码")
                            $(".verfi_typing").focus();
                            return;
                        }*/
            var params = {username:username,password:password};
            // $(this).css("background","green");return;

            //因为前面已经点过一次了，所以已经点了一次了，然后去除当前的事件，再发Ajax，然后就不能点了。防止你一直点。
            // 直到Ajax请求被服务器响应之后，响应回来的时候。或发生错误的时候。
            $this.off("click").text("登录中...").css("background","#567");//防止登录ING时浪费请求_代码1
            // $(this).off("click");
            /*return; *///TODO delete
            //  alert(JSON.stringify(params)); //JSON.parse(str)则可用于从字符串-->解析为 jsonObj格式。

            $.ajax({
                type:"POST",
                url:"UserServlet/iwantlogin",
                data:params,
                error:function () {
                    //服务器出错，把登录按钮的事件重新绑定回来。
                    $this.on("click",loginMain).text("登录").css("background","darkcyan");  //防止登录ING时浪费请求_代码2
                    // $(this).on("click",loginMain());
                },
                cache:false,//如果请求相同的URL ，浏览器就会去取缓存，so
                success : function(data){
                    //服务器出错和这里[Ajax发回来的时候，再改回来。]
                    $(".submit_btn").on("click",loginMain).text("登录").css("background","darkcyan");
                    if(data=="success"){
                        mmMessage.tip("登录成功！欢迎你，木木！");
                        $("#login_dialog").next().trigger('click');//点击它的旁边区域，让登录框消失。

                            mmlogin.query_login_status_to_server();//需要查询，因为要把服务器数据下载下来，而不是简单的更换视图。
                        //trigger() 触发事件 --规定被选元素要触发的事件
                        //更改用户信息。
                    }
                    if(data == "username_null"){
                        mmMessage.tip("请输入用户名...")

                    }
                    if(data == "password_null"){
                        mmMessage.tip("请输入密码...")
                    }
                    if(data == "fail"){
                        mmMessage.tip("请输入正确的登录信息。");
                        $(".password").val("").focus();
                    }
                }

            });
        };
    },

    logout:function(){//退出的时候，触发标签上的函数，发送一个Ajax
        $.ajax({
            type:"POST",
            url:"UserServlet/logout",
            success:function(data){
                //退出请求， 响应成功。
                if(data=="success"){
                    $("#login_view_container").html(mmlogin.unloggedView);
                    mmMessage.tip("已安全退出");
                }
            }
        });
    },




    //想登录时弹出的登录框
    wangToLoginView:"<div id='login_dialog'>"+
    "    <div class='mid_position'>"+
    "        <div class='logo m8-w240-h40'></div>"+
    "        <div class='content_typing_u m8-w240-h40'>"+
    "            <span class='login_txtinfo'>用户</span><input type='text' class='username login_input_style' name='username' value='mm' placeholder='请输入用户名...'>"+
    "        </div>"+
    "        <div class='content_typing_pw m8-w240-h40'>"+
    "            <span class='login_txtinfo'>密码</span><input type='password' class='password login_input_style' name='password' value='mumu'>"+
    "        </div>"+
    "        <div class='verification m8-w240-h40'>"+
    "            <span class='login_txtinfo'>验证</span><input type='text' class='verfi_typing login_input_style' name='verfi_typing' placeholder='请输入验证码...'>"+
    "        </div>"+
    "        <div class='verification m8-w240-h40'>"+
    "            <div class='verify_img fl'>"+
    "                <img src='images/myblog.png' alt='验证码' width='120' height='32'/>"+
    "            </div>"+
    "            <div class='change_btn fl'>"+
    "                <a href='#'>换图</a>"+
    "            </div>"+
    "        </div>"+
    "        <div class='m8-w240-h40' >"+
    "            <div class='submit_btn'>登录</div>"+
    "            <div class='autologins_7days'>"+
    "                <input type='checkbox'>"+
    "                <span>自动登录</span>"+
    "            </div>"+
    "        </div>"+
    "        <div class='m8-w240-h40'>"+
    "            没有账号？<a href=''>注册</a>"+
    "        </div>"+
    "    </div>"+
    "</div>"+
    "<!--弹窗遮罩层-->"+
    "<div class='dialog_over'></div>"
    ,
    //未登录视图
    unloggedView:"<li></li>" +
    "<li><a href='javascript:void(0)' onclick='mmlogin.iwantlogin()'> <i class='iconfont icon-shenfenzheng'  style='font-size: 25px'></i></a></li>" +
    "<li><a href='javascript:void(0)' >  <i class='iconfont icon-yijianfankui' style='font-size: 25px'></i></a></li>" +
    "<li></li>"
    ,

    //登录视图
    loggedView:"<li><i class='iconfont icon-wodefankui'  style='font-size: 25px'></i></li>" +
    "<li><a href='可去个人中心' class='login_txtinfo_username'></a></li>" +
    "<li><i class='iconfont icon-shouhuodizhiyebianji'  style='font-size: 25px'></i></li>" +
    "<li><a href='javascript:void(0)' onclick='mmlogin.logout()' ><i class='iconfont icon-cuowu'  style='font-size: 25px'></i></a></li>"
}
