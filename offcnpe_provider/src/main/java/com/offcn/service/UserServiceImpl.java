package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.UserMapper;
import com.offcn.pojo.User;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(User user) {
        Result result=new Result();
        try{
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("username",user.getUsername());
            queryWrapper.eq("password",user.getPassword());
            User one = userMapper.selectOne(queryWrapper);
            if(one!=null){
                result.setFlag(true);
                result.setMessage("登录成功");
                result.setData(one);
            }else{
                result.setFlag(false);
                result.setMessage("用户名或者密码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }
        return result;
    }

    @Override
    public User findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }
}
