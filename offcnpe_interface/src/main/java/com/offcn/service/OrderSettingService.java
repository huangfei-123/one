package com.offcn.service;

import com.offcn.pojo.Ordersetting;
import com.offcn.util.Result;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 批量导入预约设置条件
     * @param lists
     * @return
     */
    public Result importOrderSettings(List<String[]> lists);

    /**
     * 获取指定时间的预约设置信息
     * @param date
     * @return
     */
    public Result getDayInfoByDate(String date);

    /**
     * 修改可预约人数
     * @param map
     * @return
     */
    public Result updatePersonCount(Map<String,String> map);
}
