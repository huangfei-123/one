package com.offcn.job;

import com.offcn.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

@Component
public class CleanPicJob {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 每隔10秒执行一次定时任务
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void cleanPid(){
        System.out.println("执行定时任务");
        //获取存放服务器图片的set集合
        BoundSetOperations<String, String> set1 = redisTemplate.boundSetOps(MessageConstant.UPLOAD_PIC_SERVER);
        //求差集
        Set<String> diff = set1.diff(MessageConstant.UPLOAD_PIC_DB);
        //删除服务器上多余的图片
        for(String pic:diff){
            File file=new File("C:\\Users\\mwx\\uploadFile\\"+pic);
            file.delete();
        }
    }
}
