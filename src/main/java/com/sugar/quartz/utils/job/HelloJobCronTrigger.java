package com.sugar.quartz.utils.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 任务类3
 *
 * @author XiaoNianXin
 * @date 2021/12/14 20:43
 */
public class HelloJobCronTrigger implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);

        // 业务功能模拟
        System.out.println("---------------------------------------------------");
        System.out.println("开始备份数据库,时间：" + dateSrting);

        // 其他内容
        System.out.println("Job 运行时间：" + format.format(context.getJobRunTime()));
        System.out.println("Job 当前运行时间：" + format.format(context.getFireTime()));
        System.out.println("Job 下次运行时间：" + format.format(context.getNextFireTime()));
        System.out.println("---------------------------------------------------");
    }
}
