package com.offcn.security;

import com.offcn.pojo.User;
import com.offcn.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户登录后  走这里
 */
@Component
public class UserSecurityService implements UserDetailsService {
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        //返回当前登录用户的用户名，密码，角色等信息
        return new UserSecurity(user.getUsername(),user.getPassword(),user.getRoleList());
    }
}
