package com.cosyit.lmbbs.web.servlet.manage;

import com.cosyit.lmbbs.entity.Category;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.CategoryService;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理设计1：版面管理
 * 1.首先列出所有的版面。
 */
@WebServlet(name = "CategoryManageServlet", urlPatterns = "/CategoryManageServlet/*")
public class CategoryManageServlet extends HttpServlet {


    private CategoryService categoryService = ServiceFactory.getCategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//不管get，post它都可以拿到请求。
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(action);
        /**
         * 当然你也可能是传统的 ?k1=v1&k2=v2的形式
         * 那么就应该使用
         * String method =request.getParameter("actionName_X");
         *
         */

        if ("list".equals(action)) {
            //如果做分发的话，可以参考ProtalServlet的判断分发地址的处理方式。只是局部更新，肯定发送Ajax，不用刷新全页面。
            list(request, response);
        } else if ("add_category".equals(action)) {
            addCategory(request, response);
        }

    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryname");

        if (categoryName.length() < 1 || categoryName.length() > 30) {
            response.getWriter().print("fail");
            return;
        }
/*        if(){
           TODO 我觉得有必有加上 版块名字是否重复的判断。
        }*/
        else {
            Category category = new Category();
            category.setName(categoryName);
            categoryService.addCategory(category);//数据在内存。

            response.getWriter().print("success");
        }


    }


    //TODO 关于这里我有2个思考：1.findAll，如果数据量太大的话，Ajax发送的数据太多，肯定要发送分页信息。未来肯定要改为分页信息。
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println(categoryService.findAll());
        try {
            out.print(JSONUtil.serialize(categoryService.findAll()));
        } catch (JSONException e) {
            e.printStackTrace();
            out.print("fail");
        }
    }

}
