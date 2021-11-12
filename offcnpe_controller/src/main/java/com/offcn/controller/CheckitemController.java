package com.offcn.controller;


import com.offcn.pojo.Checkitem;
import com.offcn.service.CheckGroupService;
import com.offcn.service.CheckItemService;
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
@RequestMapping("/checkitem")
public class CheckitemController {
    @Reference
    private CheckItemService checkItemService;
    @Reference
    private CheckGroupService checkGroupService;
    /**
     * 新增检查项
     * @param checkitem
     * @return
     */
    @RequestMapping("addItem")
    public Result addItem(@RequestBody Checkitem checkitem){
        Result result = checkItemService.addItem(checkitem);
        return result;
    }

    /**
     * 分页条件获取检查项信息
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult result = checkItemService.findPage(queryPageBean);
        return result;
    }

    /**
     * 删除检查项
     * @return
     */
    @RequestMapping("deleteInfoById")
    public Result deleteInfoById(int id){
        Result result = checkItemService.deleteInfoById(id);
        return result;
    }

    /**
     * 根据id获取检查项信息
     * @param id
     * @return
     */
    @RequestMapping("findInfoById")
    public Result findInfoById(int id){
        Result result = checkItemService.findInfoById(id);
        return result;
    }

    /**
     * 修改检查项
     * @param checkitem
     * @return
     */
    @RequestMapping("updateInfoById")
    public Result updateInfoById(@RequestBody Checkitem checkitem){
        Result result = checkItemService.updateInfoById(checkitem);
        return result;
    }

    /**
     * 获取所有检查项信息
     * @return
     */
    @RequestMapping("showAllItemInfo")
    public Result showAllItemInfo(){
        Result result = checkItemService.showAllItemInfo();
        return result;
    }

    /**
     * 根据检查组获取检查项信息
     * @param id
     * @return
     */
    @RequestMapping("getCheckItemIdsByCheckGroupId")
    public Result getCheckItemIdsByCheckGroupId(int id){
        Result result = checkGroupService.getCheckItemIdsByCheckGroupId(id);
        return result;
    }

}

