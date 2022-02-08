package servlet;

import Bean.UserInfo;
import com.alibaba.fastjson.JSON;
import common.CodeEnum;
import common.ServerResponse;
import service.UserInfoService;
import service.impl.UserInfoServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private static UserInfoService userInfoService=new UserInfoServiceImpl();
    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email=req.getParameter("email");
        String password=req.getParameter("password");

        ServerResponse<Void> result;
        UserInfo userInfo=userInfoService.finduserInfo(email,password);
        //没有查到
        if(userInfo==null){
            result=ServerResponse.error(CodeEnum.USER_LOGIN_ERROR);
        }
        else{
            //登录成功
            //将用户信息存储session
            req.getSession().setAttribute("userInfo",userInfo);
            result=ServerResponse.success();

        }
        //将结果告诉前台页面  将对象转json
        resp.setContentType("application/json;charset=UTF-8");

        String jsonString= JSON.toJSONString(result);
        PrintWriter writer=resp.getWriter();
        writer.write(jsonString);
        writer.close();
    }
    private void register(HttpServletRequest req,HttpServletResponse resp){

    }
}
