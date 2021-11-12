package com.offcn.mapper;

import com.offcn.pojo.Member;
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
public interface MemberMapper extends BaseMapper<Member> {
    /**
     * 根据指定时间统计会员数量
     * @param date
     * @return
     */
    public int countByDate(String date);

    /**
     * 统计指定时间段的会员数量
     * @param startTime
     * @param endTime
     * @return
     */
    public int countByDateBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
