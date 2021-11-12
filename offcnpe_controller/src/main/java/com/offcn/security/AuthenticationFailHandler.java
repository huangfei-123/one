package com.offcn.security;

import com.alibaba.fastjson.JSONObject;
import com.offcn.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //将登录失败的异常打印到控制台
        exception.printStackTrace();
        Result result=new Result();
        result.setFlag(false);
        result.setMessage("登录失败");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(result));
        writer.flush();
        writer.close();

    }
}
