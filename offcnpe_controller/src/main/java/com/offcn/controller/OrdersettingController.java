package com.offcn.controller;


import com.offcn.pojo.Ordersetting;
import com.offcn.service.OrderSettingService;
import com.offcn.util.POIUtils;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {
    @Reference
    private OrderSettingService orderSettingService;
    /**
     * 上传模板数据
     * @param excelFile
     * @return
     */
    @RequestMapping("uploadTempleate")
    public Result uploadTempleate(@RequestParam("excelFile") MultipartFile excelFile){
        Result result=new Result();
        try {
            List<String[]> lists = POIUtils.readExcel(excelFile);
            result = orderSettingService.importOrderSettings(lists);
        } catch (IOException e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }
        return result;
    }

    /**
     * 获取预约设置信息
     * @return
     */
    @RequestMapping("getDayInfoByDate")
    public Result getDayInfoByDate(String date){
        //2021-7
        //获取指定月的预约条件设置信息
        Result result = orderSettingService.getDayInfoByDate(date);
        return result;
    }

    /**
     * 修改可预约人数
     * @param map
     * @return
     */
    @RequestMapping("updatePersonCount")
    public Result updatePersonCount(@RequestBody Map<String,String> map){
        Result result = orderSettingService.updatePersonCount(map);
        return result;
    }

}

