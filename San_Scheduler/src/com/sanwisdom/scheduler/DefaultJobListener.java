package com.sanwisdom.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

public class DefaultJobListener extends JobListenerSupport {

	static Logger log = Logger.getLogger(DefaultJobListener.class);
	
	@Override
	public String getName() {
		return DefaultJobListener.class.getName();
	}
	
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.info("Job to be executed...");
		super.jobToBeExecuted(context);
	}

	
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		log.info("Job was executed...");
		super.jobWasExecuted(context, jobException);
	}
}
