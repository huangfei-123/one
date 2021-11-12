package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.MemberMapper;
import com.offcn.pojo.Member;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Result getMemberReport() {
        Result result=new Result();
        try{
            Map<String,Object> map=new HashMap<>();
            //获取当前时间
            Calendar calendar=Calendar.getInstance();
            //获取当前时间倒退12个月的时间
            calendar.add(Calendar.MONTH,-12);
            List<String> months=new ArrayList<>();
            for(int i=0;i<12;i++){
                //倒退12月的时间加1的时间
                calendar.add(Calendar.MONTH,1);
                Date date = calendar.getTime();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM");
                String format = simpleDateFormat.format(date);
                months.add(format);
            }
            //获取每个月新增的会员数量
            List<Integer> memberCount = this.findMembersByMonths(months);
            map.put("months",months);
            map.put("memberCount",memberCount);
            result.setFlag(true);
            result.setData(map);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取失败");
        }

        return result;
    }

    @Override
    public List<Integer> findMembersByMonths(List<String> months) {
        List<Integer> memberCount=new ArrayList<>();
        for(String moth:months){
            //获取指定月的起始时间
            String start=moth+".01";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
            try {
                Date startTime = simpleDateFormat.parse(start);
                //获取指定月的结束时间
                String end=moth+".31";
                Date endTime = simpleDateFormat.parse(end);
                QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
                queryWrapper.ge("regTime",startTime);
                queryWrapper.le("regTime",endTime);
                Integer count = memberMapper.selectCount(queryWrapper);
                memberCount.add(count);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return memberCount;
    }
}
