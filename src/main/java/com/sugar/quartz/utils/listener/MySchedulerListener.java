package com.sugar.quartz.utils.listener;

import org.quartz.*;

/**
 * 功能描述: SchedulerListener
 *
 * @author XiaoNianXin
 * @date 2021/12/15 18:38
 */
public class MySchedulerListener implements SchedulerListener {

    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println(trigger.getKey().getName() + "完成部署");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println(triggerKey.getName() + "完成卸载");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println(trigger.getKey().getName() + "触发器移出");
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println(triggerKey.getName() + "触发器暂停");
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        System.out.println(triggerGroup + "触发器组暂停");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println(triggerKey.getName() + "触发器恢复");
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        System.out.println(triggerGroup + "触发器组恢复");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey().getName() + "任务被添加");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey.getName() + "任务被删除");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println(jobKey.getName() + "任务被暂停");
    }

    @Override
    public void jobsPaused(String jobGroup) {
        System.out.println(jobGroup + "任务组被暂停");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println(jobKey.getName() + "任务被恢复");
    }

    @Override
    public void jobsResumed(String jobGroup) {
        System.out.println(jobGroup + "任务组被恢复");
    }

    @Override
    public void schedulerError(String msg, SchedulerException e) {
        System.out.println("scheduler发生严重错误" + msg + e.getUnderlyingException());
    }

    @Override
    public void schedulerInStandbyMode() {
        System.out.println("scheduler处于挂起状态");
    }

    @Override
    public void schedulerStarted() {
        System.out.println("scheduler处于开启状态");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("scheduler处于正在开启状态");
    }

    @Override
    public void schedulerShutdown() {
        System.out.println("scheduler处于关闭状态");
    }

    @Override
    public void schedulerShuttingdown() {
        System.out.println("scheduler处于正在关闭状态");
    }

    @Override
    public void schedulingDataCleared() {
        System.out.println("scheduler处于数据清除状态");
    }
}
