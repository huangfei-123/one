package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.SetmealCheckgroupMapper;
import com.offcn.mapper.SetmealMapper;
import com.offcn.pojo.Setmeal;
import com.offcn.pojo.SetmealCheckgroup;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealCheckgroupMapper setmealCheckgroupMapper;
    @Override
    @Transactional
    public Result addSetmeal(Setmeal setmeal, Integer[] checkGroupIds) {
        Result result=new Result();
        try{
            //校验编码唯一
            //新增套餐基本信息
            setmealMapper.insert(setmeal);
            //新增套餐检查组信息
            for(Integer integer:checkGroupIds){
                SetmealCheckgroup setmealCheckgroup=new SetmealCheckgroup();
                setmealCheckgroup.setSetmealId(setmeal.getId());
                setmealCheckgroup.setCheckgroupId(integer);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
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
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageResult pageResult=new PageResult();
        Page<Setmeal> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Setmeal> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null&&queryPageBean.getQueryString().length()>0){
            queryWrapper.like("code",queryPageBean.getQueryString()).or()
                    .like("name",queryPageBean.getQueryString()).or()
                    .like("helpCode",queryPageBean.getQueryString());
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        pageResult.setTotal(setmealPage.getTotal());
        pageResult.setRows(setmealPage.getRecords());
        return pageResult;
    }

    @Override
    public Result findAllSetmeals() {
        Result result=new Result();
        try{
            List<Setmeal> setmeals = setmealMapper.selectList(null);
            result.setFlag(true);
            result.setData(setmeals);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取失败");
        }
        return result;
    }

    @Override
    public Result findInfoById(int id) {
        Result result=new Result();
        try{
            Setmeal setmeal = setmealMapper.findSetmealById(id);
            result.setFlag(true);
            result.setData(setmeal);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取信息失败");
        }
        return result;
    }

    @Override
    public Result getSetmealReport() {
        Result result=new Result();
        try{
            Map<String,Object> map=new HashMap<>();
            List<Map<String, Object>> maps = setmealMapper.findSetmealCount();
            map.put("setmealCount",maps);
            List<String> setmealNames=new ArrayList<>();
            for(Map<String,Object> map1:maps){
                setmealNames.add(map1.get("name").toString());
            }
            map.put("setmealNames",setmealNames);
            result.setFlag(true);
            result.setData(map);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }

        return result;
    }
}
