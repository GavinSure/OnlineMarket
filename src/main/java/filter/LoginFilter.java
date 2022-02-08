package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //核心功能：校验用户是否成功登录
        //在登录功能中 登录成功  会把用户的信息放到 session

        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //用户正在登陆 正在注册  不需要拦截
        final String uri=request.getRequestURI();
        if (uri.equals("/")
                || uri.contains("login")
                || uri.contains(".js")
                || uri.contains(".css")
                || uri.contains(".jpg")
                || uri.contains(".png")
                || uri.contains("reg")){
            filterChain.doFilter(request,response);
        }else {

            Object userInfo = request.getSession().getAttribute("userInfo");
            if (userInfo != null) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/login.html");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
