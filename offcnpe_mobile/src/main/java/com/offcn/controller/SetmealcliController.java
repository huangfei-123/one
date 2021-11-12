package com.offcn.controller;

import com.offcn.service.SetmealService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("setmealcli")
public class SetmealcliController {
    @Reference
    private SetmealService setmealService;
    /**
     * 移动端获取套餐列表信息
     * @return
     */
    @RequestMapping("getAllSetmeal")
    public Result getAllSetmeal(){
        Result allSetmeals = setmealService.findAllSetmeals();
        return allSetmeals;
    }

    /**
     * 获取套餐详情信息
     * @return
     */
    @RequestMapping("findInfoById")
    public Result findInfoById(int id){
        Result result = setmealService.findInfoById(id);
        return  result;
    }
}
