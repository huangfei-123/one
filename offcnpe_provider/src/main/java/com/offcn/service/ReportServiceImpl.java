package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.MemberMapper;
import com.offcn.mapper.OrderMapper;
import com.offcn.mapper.SetmealMapper;
import com.offcn.pojo.Member;
import com.offcn.util.DateUtils;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public Result getBusinessReportData() {
        Result result=new Result();
        try{
            Map<String,Object> map=new HashMap<>();
            //获取当天统计日期
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String dateFormat = simpleDateFormat.format(date);
            map.put("reportDate",dateFormat);
            //获取当天新增会员数量
            int todayNewMember = memberMapper.countByDate(dateFormat);
            map.put("todayNewMember",todayNewMember);
            //获取总的会员数量
            QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
            Integer totalMember = memberMapper.selectCount(null);
            map.put("totalMember",totalMember);
            //获取本周新增会员数量
            String firstDayOfWeekStr = DateUtils.getFirstDayOfWeekStr();
            String lastDayOfWeekStr = DateUtils.getLastDayOfWeekStr();
            int thisWeekNewMember = memberMapper.countByDateBetween(firstDayOfWeekStr, lastDayOfWeekStr);
            map.put("thisWeekNewMember",thisWeekNewMember);
            //获取本月新增会员数量
            String firstDayOfMonthStr = DateUtils.getFirstDayOfMonthStr();
            String lastDayOfMonthStr = DateUtils.getLastDayOfMonthStr();
            int thisMonthNewMember = memberMapper.countByDateBetween(firstDayOfMonthStr, lastDayOfMonthStr);
            map.put("thisMonthNewMember",thisMonthNewMember);
            //获取当天订单数量
            int todayOrderNumber = orderMapper.countOrderByDate(dateFormat);
            map.put("todayOrderNumber",todayOrderNumber);
            //统计本周订单数量
            int thisWeekOrderNumber = orderMapper.countOrdersByDateBetween(firstDayOfWeekStr, lastDayOfWeekStr);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            //统计本月订单数量
            int thisMonthOrderNumber = orderMapper.countOrdersByDateBetween(firstDayOfMonthStr, lastDayOfMonthStr);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            //获取热门套餐
            List<Map<String,Object>> hotSetmeal=setmealMapper.findHotSetmeal();
            map.put("hotSetmeal",hotSetmeal);
            result.setFlag(true);
            result.setData(map);
            result.setMessage("获取成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取信息失败");
        }

        return result;
    }
}
