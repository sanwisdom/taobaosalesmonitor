package com.sanwisdom.scheduler;



import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.listeners.TriggerListenerSupport;

public class DefaultTriggerListener extends TriggerListenerSupport {

	static Logger log = Logger.getLogger(DefaultSchedulerListener.class);
	
	@Override
	public String getName() {
		return DefaultTriggerListener.class.getName();
	}
	
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.info("Trigger Fired...");
		super.triggerFired(trigger, context);
	}
	
	@Override
	public void triggerMisfired(Trigger trigger) {
		log.info("Trigger Misfired...");
		super.triggerMisfired(trigger);
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		log.info("Trigger Complete...");
		super.triggerComplete(trigger, context, triggerInstructionCode);
	}
	
	
}
