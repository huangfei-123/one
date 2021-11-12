package com.offcn.service;

import com.offcn.pojo.User;
import com.offcn.util.Result;

public interface UserService {
    /**
     * 登录校验
     * @param user
     * @return
     */
    public Result login(User user);

    /**
     * 根据用户名获取用户角色信息
     * @param username
     * @return
     */
    public User findUserByUserName(String username);


}
