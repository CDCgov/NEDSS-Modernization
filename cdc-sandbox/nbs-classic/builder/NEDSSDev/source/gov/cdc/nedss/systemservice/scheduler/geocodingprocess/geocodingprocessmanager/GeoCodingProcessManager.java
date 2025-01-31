package gov.cdc.nedss.systemservice.scheduler.geocodingprocess.geocodingprocessmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.geocodingprocess.geocodingprocessscheduler.GeoCodingProcessScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;

/**
 * Geocoding batch process manager.
 * 
 * @author rdodge
 *
 */
public class GeoCodingProcessManager {

	static final LogUtils logger = new LogUtils(GeoCodingProcessManager.class.getName());

	/** Schedules the Geocoding batch process to run at the indicated times/intervals. */
	public void schedule() {

		Timer timer = new Timer();
		SchedulerUtil.resetProperties();

		long longDelay = 0;
		long longTimeGap = 0;

		String timeGap = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_TIMEGAP_IN_MINUTES);
		String isSchedulerToRun = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_TO_RUN);

		if (isSchedulerToRun != null && isSchedulerToRun.equalsIgnoreCase("YES")) {
			if (timeGap != null && timeGap.trim() != "") {
				try {
					longDelay = 0;
					longTimeGap = Long.parseLong(timeGap.trim()) * 60000;
				}
				catch (Exception e) {
					logger.error("Error caught during GeoCodingProcessManager:schedule" + e);
				}
				timer.schedule(new GeoCodingProcessScheduler(), longDelay, longTimeGap);
			}
		}
	}
}