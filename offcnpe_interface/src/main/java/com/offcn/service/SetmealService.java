package com.offcn.service;

import com.offcn.pojo.Setmeal;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

public interface SetmealService {
    /**
     * 新增套餐
     * @return
     */
    public Result addSetmeal(Setmeal setmeal,Integer[] checkGroupIds);

    /**
     * 分页条件查询套餐列表
     * @param queryPageBean
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 获取所有的套餐信息
     * @return
     */
    public Result findAllSetmeals();

    /**
     * 根据套餐id获取套餐详情信息
     * @param id
     * @return
     */
    public Result findInfoById(int id);

    /**
     * 获取套餐报表数据
     * @return
     */
    public Result getSetmealReport();
}
