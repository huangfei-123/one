package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.CheckgroupCheckitemMapper;
import com.offcn.mapper.CheckgroupMapper;
import com.offcn.mapper.SetmealCheckgroupMapper;
import com.offcn.pojo.Checkgroup;
import com.offcn.pojo.CheckgroupCheckitem;
import com.offcn.pojo.SetmealCheckgroup;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.apache.xmlbeans.impl.store.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckgroupMapper checkgroupMapper;
    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Autowired
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Override
    @Transactional
    public Result addGroup(Checkgroup checkgroup, Integer[] checkItems) {
        Result result=new Result();
        try{
            //校验编码唯一
            QueryWrapper<Checkgroup> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("code",checkgroup.getCode());
            List<Checkgroup> checkgroups = checkgroupMapper.selectList(queryWrapper);
            if(checkgroups!=null&&checkgroups.size()>0){
                result.setFlag(false);
                result.setMessage("编码重复");
                return result;
            }
            //新增检查组信息
            checkgroupMapper.insert(checkgroup);
            //新增检查组检查项关系表数据
            for(Integer checkItemId:checkItems){
                CheckgroupCheckitem checkgroupCheckitem=new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitem.setCheckitemId(checkItemId);
                checkgroupCheckitemMapper.insert(checkgroupCheckitem);
            }
            result.setFlag(true);
            result.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageResult pageResult=new PageResult();
        Page<Checkgroup> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Checkgroup> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null&&queryPageBean.getQueryString().length()>0){
            queryWrapper.like("code",queryPageBean.getQueryString()).or()
                    .like("name",queryPageBean.getQueryString()).or()
                    .like("helpCode",queryPageBean.getQueryString());
        }
        Page<Checkgroup> selectPage = checkgroupMapper.selectPage(page, queryWrapper);
        pageResult.setTotal(selectPage.getTotal());
        pageResult.setRows(selectPage.getRecords());
        return pageResult;
    }

    @Override
    public Result findGroupById(int id) {
        Result result=new Result();
        try{
            Checkgroup checkgroup = checkgroupMapper.selectById(id);
            result.setFlag(true);
            result.setData(checkgroup);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }

        return result;
    }

    @Override
    public Result getCheckItemIdsByCheckGroupId(int id) {
        Result result=new Result();
        try{
            Map<String,Object> map=new HashMap<>();
            map.put("checkgroup_id",id);
            List<CheckgroupCheckitem> list = checkgroupCheckitemMapper.selectByMap(map);
            List<Integer> checkItems = new ArrayList<>();
            list.forEach(i->{
                checkItems.add(i.getCheckitemId());
            });
            result.setFlag(true);
            result.setData(checkItems);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }
        return result;
    }

    @Override
    @Transactional
    public Result updateCheckGroup(Integer[] checkitemIds, Checkgroup checkgroup) {
       Result result=new Result();
       try{
           QueryWrapper<Checkgroup> queryWrapper=new QueryWrapper<>();
           queryWrapper.eq("code",checkgroup.getCode()).ne("id",checkgroup.getId());
           List<Checkgroup> checkgroups = checkgroupMapper.selectList(queryWrapper);
           if(checkgroups!=null&&checkgroups.size()>0){
               result.setFlag(false);
               result.setMessage("编码重复");
               return  result;
           }
           //修改检查组基本信息
           checkgroupMapper.updateById(checkgroup);
           //删除检查组对应的以前的检查项信息
           QueryWrapper<CheckgroupCheckitem> queryWrapper1=new QueryWrapper<>();
           queryWrapper1.eq("checkgroup_id",checkgroup.getId());
           checkgroupCheckitemMapper.delete(queryWrapper1);
           //添加修改之后的对应的检查项信息
           for(Integer id:checkitemIds){
               CheckgroupCheckitem checkgroupCheckitem=new CheckgroupCheckitem();
               checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
               checkgroupCheckitem.setCheckitemId(id);
               checkgroupCheckitemMapper.insert(checkgroupCheckitem);
           }
           result.setFlag(true);
           result.setMessage("操作成功");
       }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
            //手动回滚事务
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
       }
        return result;
    }

    @Override
    @Transactional
    public Result deleteInfoById(int id) {
        Result result=new Result();
        try{
            //删除检查组检查项关系数据
            QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("checkgroup_id",id);
            checkgroupCheckitemMapper.delete(queryWrapper);
            //删除检查组检查套餐关系数据
            QueryWrapper<SetmealCheckgroup> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("checkgroup_id",id);
            setmealCheckgroupMapper.delete(queryWrapper1);
            //删除检查组信息
            checkgroupMapper.deleteById(id);
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
    public Result getAllGroup() {
        Result result=new Result();
        try {
            List<Checkgroup> checkgroups = checkgroupMapper.selectList(null);
            result.setFlag(true);
            result.setMessage("操作成功");
            result.setData(checkgroups);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }

        return result;
    }


}
