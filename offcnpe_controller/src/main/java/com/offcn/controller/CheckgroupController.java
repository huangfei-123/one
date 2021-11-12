package com.offcn.controller;


import com.offcn.pojo.Checkgroup;
import com.offcn.service.CheckGroupService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("addGroup")
    public Result addGroup(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        Result result = checkGroupService.addGroup(checkgroup, checkitemIds);
        return result;
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult result = checkGroupService.findPage(queryPageBean);
        return result;
    }

    /**
     * 根据id查询检查组信息
     * @param id
     * @return
     */
    @RequestMapping("findGroupById")
    public Result findGroupById(int id){
        Result groupById = checkGroupService.findGroupById(id);
        return groupById;
    }

    /**
     * 修改检查组信息
     * @param checkitemIds
     * @param checkgroup
     * @return
     */
    @RequestMapping("editCheckgoup")
    public Result editCheckgoup(Integer[] checkitemIds,@RequestBody Checkgroup checkgroup){
        Result result = checkGroupService.updateCheckGroup(checkitemIds, checkgroup);
        return result;
    }

    /**
     * 根据id删除检查组信息
     * @param id
     * @return
     */
    @RequestMapping("deleteInfoById")
    public Result deleteInfoById(int id){
        Result result = checkGroupService.deleteInfoById(id);
        return result;
    }

    /**
     * 获取所有的检查组信息
     * @return
     */
    @RequestMapping("getAllGroup")
    public Result getAllGroup(){
        Result allGroup = checkGroupService.getAllGroup();
        return allGroup;
    }



}

