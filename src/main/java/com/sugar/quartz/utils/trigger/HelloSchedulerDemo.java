package com.sugar.quartz.utils.trigger;

import com.sugar.quartz.utils.job.HelloJobScheduler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: Scheduler 创建
 *
 * @author XiaoNianXin
 * @date 2021/12/15 17:40
 */
public class HelloSchedulerDemo {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(HelloJobScheduler.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .build();

        // 3、触发器 - 控制执行次数和执行时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 同上
                .startNow() // 立刻启动
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(3).
                        withRepeatCount(10))   // 循环10次,每次间隔3s
                .build();

        // 调度器关联触发器
        Date date = scheduler.scheduleJob(job, trigger);
        // 输出调度器开始时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("调度器开始时间 ： " + dateFormat.format(date));
        // 启动 2 s 后暂停
        scheduler.start();
        Thread.sleep(2000);
        // 暂停 5 s 后重启
        scheduler.standby();
        Thread.sleep(5000);
        // 关闭任务
        scheduler.shutdown();
        // 重启
        scheduler.start();
    }
}
