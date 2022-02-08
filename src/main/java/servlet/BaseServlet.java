package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得页面请求路径
        String requestURI=req.getRequestURI();
        String methodname=requestURI.substring(requestURI.lastIndexOf("/")+1);

        try {
            Method method=this.getClass().getDeclaredMethod(methodname,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);         //开启访问权限
            method.invoke(this,req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
