package com.offcn.mapper;

import com.offcn.pojo.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
public interface SetmealMapper extends BaseMapper<Setmeal> {
    /**
     * 根据id获取套餐检查组检查项详情信息
     * @param id
     * @return
     */
    public Setmeal findSetmealById(int id);

    /**
     * 获取套餐信息以及套餐对应的订单数量
     * @return
     */
    public List<Map<String,Object>> findSetmealCount();

    /**
     * 获取热门套餐
     * @return
     */
    public List<Map<String,Object>> findHotSetmeal();

}
