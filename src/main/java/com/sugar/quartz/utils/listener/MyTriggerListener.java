package com.sugar.quartz.utils.listener;

import org.quartz.*;

/**
 * 功能描述: TriggerListener
 *
 * @author XiaoNianXin
 * @date 2021/12/15 18:38
 */
public class MyTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("============ 触发器触发 ============");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        System.out.println("============ 否决 Job 执行============");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("============ 错过触发 ============");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        System.out.println("============ 触发完成 ============");
    }
}
