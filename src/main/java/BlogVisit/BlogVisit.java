package BlogVisit;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by jet.chen on 2017/3/21.
 */
public class BlogVisit {
    public static void main(String[] args) {
        visitQuatzDemo();
    }

    private static void visitQuatzDemo() {
        try {
            // 获取调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            // 创建任务器：自定义任务细节
            JobDetail jobDetail = JobBuilder.newJob(VisitQuartz.class).withIdentity("job2", "group2").build();
            ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever();

            // 定义触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "simpleTriggerGroup").withSchedule(scheduleBuilder).startNow().build();

            // 将任务和触发器注册到调度器中
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
