package com.sugar.quartz.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 功能描述: CronTrigger
 *
 * @author XiaoNianXin
 * @date 2021/12/14 20:37
 */
public class HelloCronTriggerDemo {
    public static void main(String[] args) throws SchedulerException {

        // 任务开始时间推迟 3 s,结束时间推迟 10 s
        Date startData = new Date();
        startData.setTime(startData.getTime() + 3000);
        Date endData = new Date();
        endData.setTime(endData.getTime() + 10000);

        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(helloJobSimpleTrigger.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .usingJobData("msg","JDM使用 - Detail")    // JDM 传递参数
                .build();

        // 3、触发器 - 控制执行次数和执行时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 同上
                .startNow() // 立刻启动
                .startAt(startData)
                .endAt(endData)
                .build();

        // 调度器关联触发器,并启动
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }
}
