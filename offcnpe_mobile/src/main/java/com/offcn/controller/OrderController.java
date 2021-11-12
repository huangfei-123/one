package com.offcn.controller;

import com.offcn.pojo.Order;
import com.offcn.service.OrderService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference
    private OrderService orderService;
    /**
     * 提交订单
     * @param map
     * @return
     */
    @RequestMapping("submitOrder")
    public Result submitOrder(@RequestBody Map<String,String> map){
        Result result = orderService.submitOrder(map);
        return result;
    }

    /**
     * 根据订单id获取预约成功的信息
     * @param id
     * @return
     */
    @RequestMapping("findOrderById")
    public Result findOrderById(int id){
        Result result = orderService.findOrderById(id);
        return result;
    }
}
