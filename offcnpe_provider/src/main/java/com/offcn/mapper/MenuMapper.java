package com.offcn.mapper;

import com.offcn.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id获取功能权限列表
     * @param uid
     * @return
     */
    public List<Menu> findMenusByUid(int uid);
}
