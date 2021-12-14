package com.sugar.quartz.utils.trigger;

import com.sugar.quartz.utils.job.HelloJobTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 功能描述: Trigger
 *
 * @author XiaoNianXin
 * @date 2021/12/13 21:08
 */
public class HelloTriggerDemo {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(HelloJobTrigger.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .usingJobData("msg","JDM使用 - Detail")    // JDM 传递参数
                .usingJobData("count",0)
                .build();

        // 3、触发器 - 控制执行次数和执行时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 同上
                .startNow() // 立刻启动
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(3).
                        withRepeatCount(10))   // 循环10次,每次间隔3s
                .usingJobData("msg","JDM使用 - Trigger")
                .build();

        // 调度器关联触发器,并启动
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }
}
