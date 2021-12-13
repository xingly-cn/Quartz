package com.sugar.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 任务类
 *
 * @author XiaoNianXin
 * @date 2021/12/13 20:52
 */
@PersistJobDataAfterExecution
public class HelloJob implements Job {

    // 实例化时自动绑定 JDM key对应的值
    private String msg;
    private Integer count;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);

        // 获取 JDM
        System.out.println("---------------------------------------------------");
        System.out.println("Trigger JDM ： " + msg);     // Trigger JDM 覆盖 JobDetail JDM
        System.out.println("Count ： " + count);

        // 更新 JobDetail JDM 的 count
        count++;
        context.getJobDetail().getJobDataMap().put("count",count);

        // 业务功能模拟
        System.out.println("开始备份数据库,时间：" + dateSrting);

        // 其他内容
        System.out.println("Job 运行时间：" + context.getJobRunTime());
        System.out.println("Job 当前运行时间：" + context.getFireTime());
        System.out.println("Job 下次运行时间：" + context.getNextFireTime());
        System.out.println("---------------------------------------------------");
    }
}
