package com.offcn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.CheckitemMapper;
import com.offcn.pojo.Checkitem;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckitemMapper checkitemMapper;
    @Override
    public Result addItem(Checkitem checkitem) {
        Result result=new Result();
        try{
            //编码唯一校验
            QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("code",checkitem.getCode());
            List<Checkitem> checkitems = checkitemMapper.selectList(queryWrapper);
            if(checkitems!=null&&checkitems.size()>0){
                result.setMessage("编码重复");
                result.setFlag(false);
                return result;
            }
            checkitemMapper.insert(checkitem);
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return result;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Checkitem> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
        if(queryPageBean.getQueryString()!=null&&queryPageBean.getQueryString().length()>0){
            queryWrapper.like("code",queryPageBean.getQueryString())
                    .or().like("name",queryPageBean.getQueryString());

        }
        Page<Checkitem> page1 = checkitemMapper.selectPage(page, queryWrapper);
        PageResult pageResult=new PageResult();
        pageResult.setTotal(page1.getTotal());
        pageResult.setRows(page1.getRecords());
        return pageResult;
    }

    @Override
    public Result deleteInfoById(int id) {
        Result result=new Result();
        try{
            checkitemMapper.deleteById(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return result;
    }

    @Override
    public Result findInfoById(int id) {
        Result result=new Result();
        try{
            Checkitem checkitem = checkitemMapper.selectById(id);
            result.setFlag(true);
            result.setData(checkitem);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
        }
        return result;
    }

    @Override
    public Result updateInfoById(Checkitem checkitem) {
        Result result=new Result();
        //编码唯一校验
        try{
            QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
            queryWrapper.ne("id",checkitem.getId()).eq("code",checkitem.getCode());
            List<Checkitem> checkitems = checkitemMapper.selectList(queryWrapper);
            if(checkitems!=null&&checkitems.size()>0){
                result.setFlag(false);
                result.setMessage("编码重复");
                return  result;
            }
            //修改
            checkitemMapper.updateById(checkitem);
            result.setFlag(true);
            result.setMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("操作失败");
        }
        return result;
    }

    @Override
    public Result showAllItemInfo() {
        Result result=new Result();
        try{
            List<Checkitem> checkitems = checkitemMapper.selectList(null);
            result.setFlag(true);
            result.setMessage("获取成功");
            result.setData(checkitems);
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取失败");
        }

        return result;
    }
}
