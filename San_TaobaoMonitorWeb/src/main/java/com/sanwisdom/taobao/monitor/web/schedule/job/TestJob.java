package com.sanwisdom.taobao.monitor.web.schedule.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

	static Logger log = Logger.getLogger(TestJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Hello Quartz! Test Job executed...");
	}

}
