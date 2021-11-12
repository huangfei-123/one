package com.offcn.service;

import com.offcn.pojo.Checkitem;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkitem
     * @return
     */
    public Result addItem(Checkitem checkitem);

    /**
     * 分页条件查询检查项信息
     * @param queryPageBean
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项信息
     * @param id
     * @return
     */
    public Result deleteInfoById(int id);

    /**
     * 根据id获取检查项信息
     * @param id
     * @return
     */
    public Result findInfoById(int id);

    /**
     * 编辑检查项
     * @param checkitem
     * @return
     */
    public  Result updateInfoById(Checkitem checkitem);

    /**
     * 获取所有检查项信息
     * @return
     */
    public Result showAllItemInfo();
}
