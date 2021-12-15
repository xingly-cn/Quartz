package com.sugar.quartz.utils.trigger;

import com.sugar.quartz.utils.job.HelloSchedulerListener;
import com.sugar.quartz.utils.listener.MySchedulerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 功能描述: SchedulerListener
 *
 * @author XiaoNianXin
 * @date 2021/12/15 18:32
 */
public class HelloSchedulerListenerDemo {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(HelloSchedulerListener.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .build();

        // 3、触发器 - 控制执行次数和执行时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 同上
                .startNow() // 立刻启动
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(3).
                        withRepeatCount(2))   // 循环10次,每次间隔3s
                .build();

        // 调度器关联触发器,并启动
        scheduler.scheduleJob(job,trigger);
        // 注册全局监听器
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
        // 注册局部监听器
        //scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
        scheduler.start();
    }
}
