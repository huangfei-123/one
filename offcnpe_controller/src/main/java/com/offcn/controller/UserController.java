package com.offcn.controller;


import com.offcn.pojo.User;
import com.offcn.service.UserService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    /**
     * 登录检验
     * @param user
     * @return
     */
   /* @RequestMapping("login")
    public Result login(User user,HttpSession session){
        Result result = userService.login(user);
        if(result.isFlag()){
            //将当前登录成功的用户存储到session中
            session.setAttribute("currentUser",result.getData());
        }
        return result;
    }*/

}

