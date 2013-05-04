package com.sanwisdom.scheduler;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	
	@Test
	public void testQuartz() throws InterruptedException {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.getListenerManager().addSchedulerListener(new DefaultSchedulerListener());
			
			 // define the job and tie it to our HelloJob class
	        JobDetail job = JobBuilder.newJob(TestJob.class)
	            .withIdentity("job1", "group1")
	            .build();
	        
	        // Trigger the job to run now, and then repeat every 40 seconds
	        GregorianCalendar gregorianCalendar = new GregorianCalendar();
	        gregorianCalendar.add(GregorianCalendar.MINUTE, 10);
			Trigger trigger = TriggerBuilder.newTrigger()
	            .withIdentity("trigger1", "group1")
	            .startNow()
	            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                    .withIntervalInSeconds(4)
	                    .repeatForever())
	            .endAt(gregorianCalendar.getTime())
	            .build();
//	        CronScheduleBuilder
	        // Tell quartz to schedule the job using our trigger
	        scheduler.scheduleJob(job, trigger);
			
			// and start it off
			scheduler.start();
			
//			scheduler.shutdown();
			
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
		Thread.sleep(1000*60*10);
	}
}
