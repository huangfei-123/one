package com.offcn.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * 注册用户时调用的加密方法
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        //加密输入的明文密码
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("用户输入的明文密码："+rawPassword);
        System.out.println("数据库查询出的加密之后的密码："+encodedPassword);
        System.out.println("用户输入的明文密码加密之后的密码："+DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }
}
