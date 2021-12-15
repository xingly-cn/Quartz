package com.sugar.quartz.utils.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 功能描述: JobListener
 *
 * @author XiaoNianXin
 * @date 2021/12/15 18:38
 */
public class MyJobListener implements JobListener {
    /**
     * 获取实例名称
     * @return
     */
    @Override
    public String getName() {
        String name = this.getClass().getSimpleName();
        return name;
    }


    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("=============Detail 运行前=============");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("=============Trigger 否决=============");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
        System.out.println("=============Job 运行后=============");
    }
}
