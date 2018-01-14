package com.bucharest.ag.jobs;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Helper class for scheduling jobs.
 *
 * @author Sabin Antohe
 */
public class Scheduler {
    /* Everyday  at 12:00AM*/
	private final static String reminderjobCronschedule = " 0 0 00 * * ?";
	
	//every minute
	//private final static String reminderjobCronschedule = "0 0/1 * * * ?";
	
	//every hour
    // private final static String reminderjobCronschedule = "0 0 0/1 * * ?";
     
    //every 2 days
	//private final static String reminderjobCronschedule = "0 0 0/48 * * ?";
   
	/**
     * Schedules reminder job that runs everyday
     *
     * @throws SchedulerException
     */
    
	public static void scheduleReminderJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(ReminderJob.class).build();
        Trigger t1 = TriggerBuilder.newTrigger().withIdentity("ReminderTrigger").withSchedule(CronScheduleBuilder.cronSchedule(reminderjobCronschedule)).build();
        org.quartz.Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
        sc.start();
        sc.scheduleJob(job, t1);
    }
}
