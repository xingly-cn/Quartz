package com.sugar.quartz.utils.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: JobListener
 *
 * @author XiaoNianXin
 * @date 2021/12/15 18:30
 */
public class HelloJobListener implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 输出当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 模拟业务逻辑
        System.out.println("当前时间 ：" + dateFormat.format(date));
    }
}
