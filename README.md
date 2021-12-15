# 1、Quartz

## 1.1 引入依赖

```xml
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.3.2</version>
</dependency>
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz-jobs</artifactId>
    <version>2.3.2</version>
</dependency>
```

## 1.2 入门案例

任务：将任务类执行 10 次，每次间隔 3 秒。

1. 任务类,需要实现 `Job` 接口

```java
package com.sugar.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 任务
 *
 * @author XiaoNianXin
 * @date 2021/12/13 20:52
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);
        // 业务功能模拟
        System.out.println("开始备份数据库,时间：" + dateSrting);
    }
}
```

2. 定时器类

```java
package com.sugar.quartz.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 功能描述: 定时器配置
 *
 * @author XiaoNianXin
 * @date 2021/12/13 21:08
 */
public class HelloSchedulerDemo {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(HelloJob.class)
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

        // 调度器关联触发器,并启动
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    }
}
```

## 1.3 Job 与 JobDetail

Job：基于反射的任务调度接口，所有任务类都要实现该接口，在接口的 `execute` 里编写自己的业务逻辑。



Job 生命周期：每次执行 Job，在 execute 方法前会`创建新的 Job实例`，调用后实例被释放，再被GC回收。



JobDetail：封装 Job，给 Job 实例提供许多属性。



JobDetail 属性：name、group、jobClass、jobDataMap。



## 1.4 JobExecutionContext

下文将 JobExecutionContext 简称为 JEC



JEC ：当调度器调用 Job 时，会将 JEC 传递给 Job 的 execute 方法。



JEC 作用：Job 通过 JEC 获取运行环境信息，以及 Job 信息。



## 1.5 JobDataMap

下文将 JobDataMap 简称为 JDM



JDM：任务调度时，JDM 存储在 JEC 中，方便获取。



JDM 优点：实现 Map 接口，可以存取任何可序列化对象，Job 执行时会将参数传给 JDM。



1、手动获取 JDM 参数案例

HelloSchedulerDemo：

```java
package com.sugar.quartz.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 功能描述: 定时器配置
 *
 * @author XiaoNianXin
 * @date 2021/12/13 21:08
 */
public class HelloSchedulerDemo {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .usingJobData("msg","JDM使用 - Detail")    // JDM 传递参数
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
```

HelloJob：

```java
package com.sugar.quartz.utils;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 定时业务功能
 *
 * @author XiaoNianXin
 * @date 2021/12/13 20:52
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);

        // 获取 JDM
        JobDataMap Detail_JDM = context.getJobDetail().getJobDataMap();
        JobDataMap Trigger_JDM = context.getTrigger().getJobDataMap();
        String detail_jdmString = Detail_JDM.getString("msg");
        String trigger_jdmString = Trigger_JDM.getString("msg");
        System.out.println("---------------------------------------------------");
        System.out.println("detail_jdmString = " + detail_jdmString);
        System.out.println("trigger_jdmString = " + trigger_jdmString);

        // 业务功能模拟
        System.out.println("开始备份数据库,时间：" + dateSrting);

        // 其他内容
        System.out.println("Job 运行时间：" + context.getJobRunTime());
        System.out.println("Job 当前运行时间：" + context.getFireTime());
        System.out.println("Job 下次运行时间：" + context.getNextFireTime());
        System.out.println("---------------------------------------------------");
    }
}
```

2、Job 类实现 JDM 参数的 Setter 方法，实例化时自动绑定参数

HelloJob：

```
// 实例化时自动绑定 JDM key对应的值
private String msg;

public void setMsg(String msg) {
    this.msg = msg;
}

// 获取 JDM
System.out.println(Trigger JDM ： " + msg);
```

问题：上文中 JobDetail 和 Trigger 中的 JDM 的 key 均为 `"msg"`，那此 msg 是哪一个？

E.g：遇到同名key，Trigger 会覆盖 JobDetail 的值，所以 msg 为 Trigger JDM 的值。



## 1.6 Job 状态

有状态 Job：多次调用 Job 期间，公用同一个 JDM。



有状态 Job：多次调用 Job 期间，每次新建一个新的 JDM。



1、有无状态 Job 区别案例

预期：无状态 count 输出永远为 1，有状态 count 输出累加。

HelloSchedulerDemo：

```
// JobDeatil 添加一个 JDM，用做计数器
.usingJobData("count",0)
```

无状态 HelloJob：

```
package com.sugar.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 任务类
 *
 * @author XiaoNianXin
 * @date 2021/12/13 20:52
 */
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
        System.out.println("Trigger JDM ： " + msg); 
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
```

有状态 HelloJob：

```
// 任务类加上下面注解，多次调用 Job，会持久化 Job，JDM 的数据会被保存，供下次使用
@PersistJobDataAfterExecution
```



## 1.7 Trigger

Trigger 常用：SimpleTrigger、CronTrigger。



JobKey：Job 实例标识，触发器触发时，执行 JobKey 对应任务。



StartTime：第一次触发时间。



EndTime：终止触发时间。



1、Trigger 获取参数案例

HelloSchedulerTriggerDemo：

```
package com.sugar.quartz.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 功能描述: 定时器配置2
 *
 * @author XiaoNianXin
 * @date 2021/12/13 21:08
 */
public class HelloSchedulerTriggerDemo {
    public static void main(String[] args) throws SchedulerException {

        // 任务开始时间推迟 3 s,结束时间推迟 10 s
        Date startData = new Date();
        startData.setTime(startData.getTime() + 3000);
        Date endData = new Date();
        endData.setTime(endData.getTime() + 10000);

        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(helloJobTrigger.class)
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
```

helloJobTrigger：

```
package com.sugar.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: 任务类2
 *
 * @author XiaoNianXin
 * @date 2021/12/13 20:52
 */
@PersistJobDataAfterExecution
public class helloJobTrigger implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);

        // 业务功能模拟
        System.out.println("---------------------------------------------------");
        System.out.println("开始备份数据库,时间：" + dateSrting);

        // 获取 JobKey,StartTime,EndTime
        System.out.println("JobKey ： " + context.getTrigger().getJobKey());
        System.out.println("StartTime ： " + format.format(context.getTrigger().getStartTime()));
        System.out.println("EndTime ： " + format.format(context.getTrigger().getEndTime()));
        System.out.println("---------------------------------------------------");
    }
}

// 运行结果
---------------------------------------------------
开始备份数据库,时间：2021-12-13 23:25:06
JobKey ： group1.job1
StartTime ： 2021-12-13 23:25:06
EndTime ： 2021-12-13 23:25:13
---------------------------------------------------
```



## 1.8 SimpleTripper

下文将 SimpleTripper 简称为 ST



ST：特定时间范围启动/结束，且以一个时间间隔重复 n 次 Job 所设计。



ST 属性：开始时间、结束时间、重复次数和时间间隔。



ST 提示：指定了结束时间，那么结束时间优先级 > 重复次数。
