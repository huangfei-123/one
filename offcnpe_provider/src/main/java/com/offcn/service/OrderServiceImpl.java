package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.MemberMapper;
import com.offcn.mapper.OrderMapper;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.mapper.SetmealMapper;
import com.offcn.pojo.Member;
import com.offcn.pojo.Order;
import com.offcn.pojo.Ordersetting;
import com.offcn.pojo.Setmeal;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    @Transactional
    public Result submitOrder(Map<String,String> map) {
        Result result=new Result();
        try{
            //判断预约日期是否可预约
            String orderDate=map.get("orderDate");
            Ordersetting ordersetting = ordersettingMapper.findOrderSettingByOrderDate(orderDate);
            if(ordersetting==null){
                //该日期不可预约
                result.setFlag(false);
                result.setMessage("该日期不可预约");
                return result;
            }
            //判断该日期是否已经预约满
            if(ordersetting.getReservations()>=ordersetting.getNumber()){
                //该日期已经预约满
                result.setFlag(false);
                result.setMessage("已经预约满");
                return result;
            }
            //判断该用户是否注册过
            String telephone=map.get("telephone");
            QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("phoneNumber",telephone);
            Member member = memberMapper.selectOne(queryWrapper);
            int memberId=0;
            if(member==null){
                //用户未注册，进行自动注册，并下单
                Member member1=new Member();
                member1.setName(map.get("name"));
                member1.setSex(map.get("sex"));
                member1.setIdcard(map.get("idCard"));
                member1.setPhonenumber(telephone);
                member1.setRegtime(LocalDate.now());
                memberMapper.insert(member1);
                memberId=member1.getId();
            }else{
                //该用户已经注册，判断该用户是否已经下过相同套餐的订单
                QueryWrapper<Order> queryWrapper1=new QueryWrapper<>();
                queryWrapper1.eq("member_id",member.getId());
                queryWrapper1.eq("setmeal_id",map.get("setmealId"));
                Order order = orderMapper.selectOne(queryWrapper1);
                memberId=member.getId();
                if(order!=null){
                    result.setFlag(false);
                    result.setMessage("您已经预约过该套餐");
                    return  result;
                }
            }
            //预约下单
            Order order=new Order();
            order.setMemberId(memberId);
            order.setSetmealId(Integer.parseInt(map.get("setmealId")));
            DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(orderDate, dateTimeFormatter);
            order.setOrderdate(localDate);
            order.setOrderstatus("未就诊");
            order.setOrdertype("小程序端");
            orderMapper.insert(order);
            //下单成功更新已下单的人数
            ordersetting.setReservations(ordersetting.getReservations()+1);
            ordersettingMapper.updateById(ordersetting);
            result.setFlag(true);
            result.setMessage("预约成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("预约失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result findOrderById(int id) {
        Result result=new Result();
        try{
            //获取订单信息
            Order order = orderMapper.selectById(id);
            //获取会员信息
            Member member = memberMapper.selectById(order.getMemberId());
            //获取套餐信息
            Setmeal setmeal = setmealMapper.selectById(order.getSetmealId());
            Map<String,Object> map=new HashMap<>();
            map.put("member",member.getName());
            map.put("setmeal",setmeal.getName());
            map.put("orderDate",order.getOrderdate());
            map.put("orderType",order.getOrdertype());
            result.setFlag(true);
            result.setMessage("操作成功");
            result.setData(map);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }

        return result;
    }
}
