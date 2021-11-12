package com.offcn.security;

import com.alibaba.fastjson.JSONObject;
import com.offcn.pojo.User;
import com.offcn.service.UserService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Reference
    private UserService userService;
    /**
     * springsecurity认证成功后调用的方法
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Result result=new Result();
        result.setFlag(true);
        result.setMessage("登录成功");
        //将登录成功的用户存储到session中
        HttpSession session=request.getSession();
        //获取当前登录的用户的用户名
        String username = authentication.getName();
        //获取当前登录用户
        User user = userService.findUserByUserName(username);
        session.setAttribute("currentUser",user);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(result));
        writer.flush();
        writer.close();

    }
}
