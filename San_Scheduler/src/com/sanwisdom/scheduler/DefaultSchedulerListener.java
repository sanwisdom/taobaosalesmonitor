package com.sanwisdom.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Trigger;
import org.quartz.listeners.SchedulerListenerSupport;

public class DefaultSchedulerListener extends SchedulerListenerSupport {

	static Logger log = Logger.getLogger(DefaultSchedulerListener.class);
	
	@Override
	public void schedulerStarted() {
		super.schedulerStarted();
		log.info("Scheduer Started...");
	}
	
	@Override
	public void schedulerShutdown() {
		super.schedulerShutdown();
		log.info("Scheduer Shutdown...");
	}
	
	@Override
	public void jobScheduled(Trigger trigger) {
		super.jobScheduled(trigger);
		log.info(String.format("Job Scheduled...Trigger Job Group(%s) Key(%s), Trigger Group(%s) Key(%s).", 
				trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), 
				trigger.getKey().getGroup(), trigger.getKey().getName()));
	}
}
