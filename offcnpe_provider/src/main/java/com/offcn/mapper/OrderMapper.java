package com.offcn.mapper;

import com.offcn.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 根据日期统计订单数量
     * @param date
     * @return
     */
   public int countOrderByDate(String date);

    /**
     * 指定时间端统计订单数量
     * @param startDate
     * @param endDate
     * @return
     */
   public int countOrdersByDateBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
