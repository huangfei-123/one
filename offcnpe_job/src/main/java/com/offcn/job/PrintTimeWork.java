package com.offcn.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintTimeWork implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //定义需要根据计划定时执行的任务
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        System.out.println("执行定时任务，时间:"+time);
    }
}
