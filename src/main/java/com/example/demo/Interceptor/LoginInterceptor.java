package com.example.demo.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//      获取进过拦截器的路径
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginJudge");
        Object sloginUser = session.getAttribute("sloginJudge");
        if(sloginUser !=null ){
            System.out.println("StaffState:"+sloginUser);
//          放行
            return true;
        }else {
            return false;
        }
//      拦截   就是未登录,自动跳转到登录页面，然后写拦截住的逻辑

    }




}
