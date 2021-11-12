package com.offcn.service;

import com.offcn.mapper.MenuMapper;
import com.offcn.pojo.Menu;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> findMenusByUid(int uid) {
        return menuMapper.findMenusByUid(uid);
    }
}
