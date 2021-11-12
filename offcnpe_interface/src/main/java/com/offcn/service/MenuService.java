package com.offcn.service;

import com.offcn.pojo.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 根据用户id获取该用户的菜单权限列表
     * @param uid
     * @return
     */
    public List<Menu> findMenusByUid(int uid);
}
