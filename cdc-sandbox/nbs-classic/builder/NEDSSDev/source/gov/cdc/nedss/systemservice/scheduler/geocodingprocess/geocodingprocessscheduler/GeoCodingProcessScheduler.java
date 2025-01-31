package gov.cdc.nedss.systemservice.scheduler.geocodingprocess.geocodingprocessscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.geocoding.batchprocess.GeoCodingBatchProcessor;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;

/**
 * Geocoding batch process scheduler.
 * 
 * @author rdodge
 *
 */
public class GeoCodingProcessScheduler extends SchedulerUtil {

	static final LogUtils logger = new LogUtils(GeoCodingProcessScheduler.class.getName());

	boolean isProcessRuning = false;

	/** Top-level run method. */
	public void run() {

		logger.debug("Generating report for GeoCodingProcessScheduler");
		SchedulerUtil.resetProperties();
		String isGeoCodingSchedulerToRun = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_TO_RUN);

		if (isGeoCodingSchedulerToRun != null &&
				isGeoCodingSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning) {

			isProcessRuning = true;
			runProcessor();
			isProcessRuning = false;
		}
	}

	/** Invokes the batch processor conditionally (contingent upon schedule constraints). */
	private void runProcessor() {

		// String processType = SchedulerUtil.getPropertyValue(
		//		SchedulerConstants.GEOCODING_PROCESS_TYPE);

		String scheduleHDM = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
		String scheduleTimeInHourString = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_TIME_IN_HOUR);
		String scheduleTimeInMinuteString = SchedulerUtil.getPropertyValue(
				SchedulerConstants.GEOCODING_SCHEDULE_TIME_IN_MINUTE);

		int scheduleTimeInHour = Integer.parseInt(scheduleTimeInHourString.trim());
		int scheduleTimeInMinute = Integer.parseInt(scheduleTimeInMinuteString.trim());

		try {
			boolean isProcessReadyToRun = false;

			if (scheduleHDM.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(
						scheduleTimeInHour, scheduleTimeInMinute);
			}
			else if (scheduleHDM.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						scheduleTimeInHour, scheduleTimeInMinute);
			}
			else if (scheduleHDM.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes,
						runTimeMinuteDiff, scheduleTimeInMinute);
			}
			else {
				logger.error("PROCESS_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for GeoCodingProcessScheduler!");
			}

			// Invoke the batch process (if applicable) //
			if (isProcessReadyToRun)
				runProcess();

		}
		catch (Exception e) {
			logger.fatal("error raised in GeoCodingProcessScheduler:" + e);
		}
	}

	/** Batch process invoker for Geocoding.  Handles/logs exceptions. */ 
	private void runProcess() {
		try {
			GeoCodingBatchProcessor.geoCodingProcess();
		} catch (Exception e) {
			logger.fatal("Exception thrown in GeoCodingProcessScheduler" + SchedulerUtil.getStackTrace(e));
		}
	}
}
