package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.pojo.Ordersetting;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Override
    @Transactional
    public Result importOrderSettings(List<String[]> lists) {
        Result result=new Result();
        try{
            for(String[] cells:lists){
                Ordersetting ordersetting=new Ordersetting();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(cells[0], dateTimeFormatter);
                ordersetting.setOrderdate(date);
                ordersetting.setNumber(Integer.parseInt(cells[1]));
               //判断当前日期是否是今天之后
                if(!date.isAfter(LocalDate.now())){
                    continue;
                }
                //判断该日期是否已经设置了预约人数
                String queryDate=cells[0];
                queryDate.replace("/","-");
                Ordersetting orderSettingByOrderDate = ordersettingMapper.findOrderSettingByOrderDate(queryDate);
                if(orderSettingByOrderDate!=null){
                    //修改预约条件设置
                    orderSettingByOrderDate.setNumber(ordersetting.getNumber());
                    ordersettingMapper.updateById(orderSettingByOrderDate);
                }else{
                    //批量新增
                    ordersettingMapper.insert(ordersetting);
                }

            }
            result.setFlag(true);
            result.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result getDayInfoByDate(String date) {
        Result result=new Result();
        try{
            //获取查询当月起始日期
            String startTime=date+"-01";
            String endTime=date+"-31";
            QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
            queryWrapper.ge("orderDate",startTime);
            queryWrapper.le("orderDate",endTime);
            List<Ordersetting> ordersettings = ordersettingMapper.selectList(queryWrapper);
            //新增date字段
            ordersettings.forEach(i->{
                int dayOfMonth = i.getOrderdate().getDayOfMonth();
                i.setDate(dayOfMonth);
            });
            result.setFlag(true);
            result.setData(ordersettings);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");

        }
        return result;
    }

    @Override
    public Result updatePersonCount(Map<String,String> map) {
        Result result=new Result();
        try{
            Ordersetting ordersetting = ordersettingMapper.findOrderSettingByOrderDate(map.get("orderdate"));
            if(ordersetting!=null){
                ordersetting.setNumber(Integer.parseInt(map.get("number")));
                ordersettingMapper.updateById(ordersetting);
            }else{
                Ordersetting ordersetting1=new Ordersetting();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(map.get("orderdate"), dateTimeFormatter);
                ordersetting1.setOrderdate(date);
                ordersetting1.setNumber(Integer.parseInt(map.get("number")));
                ordersettingMapper.insert(ordersetting1);
            }
            result.setFlag(true);
            result.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }
        return result;
    }
}
