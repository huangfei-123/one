package com.offcn;

import com.offcn.job.PrintTimeWork;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyJobTest {
    public static void main(String[] args) {
        //构建一个工具对象 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(PrintTimeWork.class).withIdentity("job1","group1").build();
        //构架 Trigger
        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .startNow().withSchedule(simpleSchedule().withIntervalInSeconds(3).withRepeatCount(5))
                .build();
        //创建当前的调度器工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //工厂生产调度器
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
