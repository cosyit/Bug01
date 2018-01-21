package simple.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import java.io.IOException;

public class MyFirstTag implements SimpleTag {

    JspContext jspContext;
    @Override
    public void doTag() throws JspException, IOException {
        System.out.println("this is my first tag");
    }

    @Override
    public void setParent(JspTag jspTag) {
        System.out.println("设置父标签");
    }

    @Override
    public JspTag getParent() {
        return null;
    }

    @Override
    public void setJspContext(JspContext jspContext) {
        System.out.println("设置我们的属性setJspContext");
        this.jspContext=jspContext;
    }

    @Override
    public void setJspBody(JspFragment jspFragment) {
        System.out.println("setJspBody");
    }
}
