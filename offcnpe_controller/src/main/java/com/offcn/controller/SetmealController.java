package com.offcn.controller;


import com.offcn.pojo.Setmeal;
import com.offcn.service.SetmealService;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mwx
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 上传图片操作
     * @return
     */
    @RequestMapping("uploadpic")
    public Result uploadpic(@RequestParam("imgFile") MultipartFile multipartFile){
       Result result=new Result();
        //获取上传图片原名称
        String originalFilename = multipartFile.getOriginalFilename();
        //获取图片后缀
        int indexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(indexOf - 1);
        //为图片生成新的名称
        String fileName= UUID.randomUUID().toString()+suffix;
        File newPah=new File("C:\\Users\\mwx\\uploadFile\\"+fileName);
        //上传图片
        try {
            multipartFile.transferTo(newPah);
            result.setFlag(true);
            result.setMessage("上传成功");
            result.setData(fileName);
            //图片上传成功后存到redis集合中
            SetOperations<String, String> set = redisTemplate.opsForSet();
            set.add(MessageConstant.UPLOAD_PIC_SERVER,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("上传失败");
        }
        return result;
    }

    /**
     * 新增套餐
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping("addSetmeal")
    public Result addSetmeal(Integer[] checkgroupIds,@RequestBody Setmeal setmeal){
        Result result = setmealService.addSetmeal(setmeal, checkgroupIds);
        if(result.isFlag()){
            //新增套餐成功，往redis集合中存储新增成功的图片
            SetOperations<String, String> set = redisTemplate.opsForSet();
            set.add(MessageConstant.UPLOAD_PIC_DB,setmeal.getImg());
        }
        return result;
    }
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult result = setmealService.findPage(queryPageBean);
        return result;
    }

}

