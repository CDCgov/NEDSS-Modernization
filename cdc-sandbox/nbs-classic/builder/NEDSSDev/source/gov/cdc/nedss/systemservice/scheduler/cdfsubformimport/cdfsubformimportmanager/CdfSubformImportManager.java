package gov.cdc.nedss.systemservice.scheduler.cdfsubformimport.cdfsubformimportmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.cdfsubformimport.cdfsubformimportscheduler.CdfSubformImportScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;

public class CdfSubformImportManager {

	static final LogUtils logger = new LogUtils(CdfSubformImportManager.class
			.getName());

	public void schedule() {
		Timer timer = new Timer();
		SchedulerUtil.resetProperties();
		long longDelay = 0;
		long longTimeGap = 0;
		String timeGap = SchedulerUtil
				.getPropertyValue(SchedulerConstants.CDF_SUBFORM_SCHEDULE_TIMEGAP_IN_MINUTES);
		String isCDF_SUBFORMSchedulerToRun = SchedulerUtil
				.getPropertyValue(SchedulerConstants.CDF_SUBFORM_SCHEDULE_TO_RUN);
		if (isCDF_SUBFORMSchedulerToRun.equalsIgnoreCase("YES")) {
			if (timeGap != null && timeGap.trim() != "") {
				try {
					longDelay = 0;
					longTimeGap = Long.parseLong(timeGap.trim()) * 60000;
				} catch (Exception ex) {
					logger
							.error("Error caught during CdfSubformImportManager:schedule"
									+ ex);
				}
				timer.schedule(new CdfSubformImportScheduler(), longDelay,
						longTimeGap);
			}
		}
	}
}