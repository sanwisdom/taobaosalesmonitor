package com.sanwisdom.taobao.monitor.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sanwisdom.taobao.monitor.web.schedule.job.TestJob;

public class InitializeScanScheduleTaskServlet extends HttpServlet {

	static Logger log = Logger
			.getLogger(InitializeScanScheduleTaskServlet.class);

	private boolean performShutdown = true;
	private boolean waitOnShutdown = false;

	private transient Scheduler scheduler = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		log.info("Initializing Scheduler PlugIn for Jobs!");
		super.init(config);
		StdSchedulerFactory factory = null;
//		ServletContext ctx = config.getServletContext();
//		StdSchedulerFactory factory = (StdSchedulerFactory) ctx
//				.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);

		try {
			String configFile = config.getInitParameter("quartz:config-file");  
            String shutdownPref = config.getInitParameter("shutdown-on-unload");  
  
            if (shutdownPref != null) {  
                performShutdown = Boolean.valueOf(shutdownPref).booleanValue();  
            }  
            String shutdownWaitPref = config.getInitParameter("wait-on-shutdown");  
            if (shutdownPref != null) {  
                waitOnShutdown = Boolean.valueOf(shutdownWaitPref).booleanValue();  
            }  
  
            factory = getSchedulerFactory(configFile); 
			scheduler = factory.getScheduler();
			JobDetail jd = JobBuilder.newJob(TestJob.class)
					.withIdentity("job1", "group1").build();

			String cronExpression = getInitParameter("cronExpr");
			log.info(cronExpression);
			CronTrigger cronTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity("trigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)) // every 5 sec "0/5 * * * * ?"
					.build();
			scheduler.start();
			scheduler.scheduleJob(jd, cronTrigger);
			log.info("Job scheduled now ..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected StdSchedulerFactory getSchedulerFactory(String configFile)
			throws SchedulerException {
		StdSchedulerFactory factory;
		// get Properties
		if (configFile != null) {
			factory = new StdSchedulerFactory(configFile);
		} else {
			factory = new StdSchedulerFactory();
		}
		return factory;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.print("<html> <body> <h1>");
		pw.print("Please wait, Test Operations is performing.............");
		pw.print("</h1></body></html>");
	}

	@Override
	public void destroy() {
		if (!performShutdown) {
			return;
		}
		try {
			if (scheduler != null) {
				scheduler.shutdown(waitOnShutdown);
			}
		} catch (Exception e) {
			log.info("Quartz Scheduler 停止失败: " + e.toString());
		}

		log.info("Quartz Scheduler 停止了.");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
