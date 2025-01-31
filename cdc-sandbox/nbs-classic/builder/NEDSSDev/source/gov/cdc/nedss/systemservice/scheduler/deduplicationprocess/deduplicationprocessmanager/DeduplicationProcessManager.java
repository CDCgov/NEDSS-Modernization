package gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessscheduler.DeduplicationProcessScheduler;
import gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessscheduler.DeduplicationSameProcessScheduler;
import gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessscheduler.DeduplicationSimilarProcessScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;

public class DeduplicationProcessManager {

	static final LogUtils logger = new LogUtils(
			DeduplicationProcessManager.class.getName());

	/*public void schedule() {
		Timer timer = new Timer();
		SchedulerUtil.resetProperties();
		long longDelay = 0;
		long longTimeGap = 0;
		String timeGap = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_TIMEGAP_IN_MINUTES);
		String isElrSchedulerToRun = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_TO_RUN);
		if (isElrSchedulerToRun.equalsIgnoreCase("YES")) {
			if (timeGap != null && timeGap.trim() != "") {
				try {
					longDelay = 0;
					longTimeGap = Long.parseLong(timeGap.trim()) * 60000;
				} catch (Exception ex) {
					logger
							.error("Error caught during DeduplicationProcessManager:schedule"
									+ ex);
				}
				timer.schedule(new DeduplicationProcessScheduler(), longDelay,
						longTimeGap);
			}
		}
	}*/
	
	public void sameschedule() {
		Timer timer = new Timer();
		logger.debug("calling sameschedule method of DeduplicationProcessManager");
		SchedulerUtil.resetProperties();
		long longDelay = 0;
		long longTimeGap = 0;
		String timeGap = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TIMEGAP_IN_MINUTES);
		String isElrSchedulerToRun = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TO_RUN);
		if (isElrSchedulerToRun.equalsIgnoreCase("YES")) {
			if (timeGap != null && timeGap.trim() != "") {
				try {
					longDelay = 0;
					longTimeGap = Long.parseLong(timeGap.trim()) * 60000;
				} catch (Exception ex) {
					logger
							.error("Error caught during DeduplicationProcessManager:schedule"
									+ ex);
				}
				
				timer.schedule(new DeduplicationSameProcessScheduler(), longDelay,
						longTimeGap);
				
				
			}
		}
		
	}
	
	public void similarschedule(String str_status) {
		if(!str_status.equalsIgnoreCase("sameschedule")){
			try {
				 Thread.sleep(2000);
				} catch (InterruptedException e) {
				           e.printStackTrace();
				}


		}
		Timer timer = new Timer();
		SchedulerUtil.resetProperties();
		long longDelay = 0;
		long longTimeGap = 0;
		logger.debug("calling similarschedule method of DeduplicationProcessManager");
		String timeGap = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TIMEGAP_IN_MINUTES);
		String isElrSchedulerToRun = SchedulerUtil
				.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TO_RUN);
		if (isElrSchedulerToRun.equalsIgnoreCase("YES")) {
			if (timeGap != null && timeGap.trim() != "") {
				try {
					longDelay = 0;
					longTimeGap = Long.parseLong(timeGap.trim()) * 60000;
				} catch (Exception ex) {
					logger
							.error("Error caught during DeduplicationProcessManager:schedule"
									+ ex);
				}
				timer.schedule(new DeduplicationSimilarProcessScheduler(), longDelay,
						longTimeGap);
			}
		}
		
	}


}