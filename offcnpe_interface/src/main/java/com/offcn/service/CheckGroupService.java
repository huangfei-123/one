package com.offcn.service;

import com.offcn.pojo.Checkgroup;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

public interface CheckGroupService {
    /**
     * 新增检查组信息
     * @param checkgroup
     * @param checkItems
     * @return
     */
    public Result addGroup(Checkgroup checkgroup,Integer[] checkItems);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据分组id获取检查组信息
     * @param id
     * @return
     */
    public Result findGroupById(int id);

    /**
     * 根据检查组id获取检查项信息
     * @param id
     * @return
     */
    public Result getCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组信息
     * @param checkitemIds
     * @param checkgroup
     * @return
     */
    public  Result updateCheckGroup(Integer[] checkitemIds,Checkgroup checkgroup);

    /**
     * 根据id删除检查组信息
     * @param id
     * @return
     */
    public Result deleteInfoById(int id);

    /**
     * 获取所有的检查组信息
     * @return
     */
    public Result getAllGroup();


}
