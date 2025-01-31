package gov.cdc.nedss.systemservice.scheduler.undocdfimport.undocdfimportscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.ldf.helper.LDFUndoImportEJBClient;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;

public class UndoCdfImportScheduler extends SchedulerUtil {
	static final LogUtils logger = new LogUtils(UndoCdfImportScheduler.class.getName());

	boolean isProcessRuning = false;
	public void run() {
		logger.debug("Generating report for UndoCdfImportScheduler");
		SchedulerUtil.resetProperties();
		String isUserProfileSchedulerToRun = SchedulerUtil.getPropertyValue("ELR_PROFILE_SCHEDULE_TO_RUN");
		if (isUserProfileSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning) {
			isProcessRuning=true;
			runProcessor();
			isProcessRuning= false;
		}
	}

	private void runProcessor() {
		String nbsRelease = SchedulerUtil.getPropertyValue(SchedulerConstants.UNDOCDF_SUBFORM_IMPORT_NBS_RELEASE);
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.UNDOCDF_SUBFORM_IMPORT_USER_NAME);

		String PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.UNDOCDF_SUBFORM_IMPORT_SCHEDULE_TO_RUN_HOURLY_OR_DAILY_OR_MINUTE);
		String PHCMART_SCHEDULE_TIME_IN_HOUR = SchedulerUtil
				.getPropertyValue(SchedulerConstants.UNDOCDF_SUBFORM_IMPORT_SCHEDULE_TO_RUN_TIME_IN_HOUR);
		String PHCMART_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.UNDOCDF_SUBFORM_IMPORT_SCHEDULE_TO_RUN_TIME_IN_MINUTE);
		int PHCMART_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PHCMART_SCHEDULE_TIME_IN_HOUR.trim());
		int PHCMART_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PHCMART_SCHEDULE_TIME_IN_MINUTE.trim());
		
		try {
			boolean isProcessReadyToRun = false;
			if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(PHCMART_SCHEDULE_TIME_IN_HOUR_INT,
						PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PHCMART_SCHEDULE_TIME_IN_HOUR_INT, PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger
						.error("PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				LDFUndoImportEJBClient.ldfUndoImportEJBClientProcessor(userName, nbsRelease);

		} catch (Exception ex) {
			logger.fatal("error raised in phcMartProcessor:" + SchedulerUtil.getStackTrace(ex));
		}
	}
}
