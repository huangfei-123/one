package com.offcn.service;

import com.offcn.pojo.Order;
import com.offcn.util.Result;

import java.util.Map;

public interface OrderService {
    /**
     * 提交订单
     * @param map
     * @return
     */
    public Result submitOrder(Map<String,String> map);

    /**
     * 根据订单id获取订单预约信息
     * @param id
     * @return
     */
    public Result findOrderById(int id);
}
