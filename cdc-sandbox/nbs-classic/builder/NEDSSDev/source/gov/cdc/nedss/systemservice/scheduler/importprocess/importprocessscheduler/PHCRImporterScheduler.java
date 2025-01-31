package gov.cdc.nedss.systemservice.scheduler.importprocess.importprocessscheduler;

import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxAutoInvFromInterface;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.PHCRImporter;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;

public class PHCRImporterScheduler extends SchedulerUtil {
	static final LogUtils logger = new LogUtils(PHCRImporterScheduler.class.getName());

	boolean isProcessRuning= false;
	public void run() {
		logger.debug("Generating report for ImportProcessScheduler");
		SchedulerUtil.resetProperties();
		String isImportProcessSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCR_IMPORTER_SCHEDULE_TO_RUN);
		if (isImportProcessSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning) {
			isProcessRuning= true;
			importProcessProcessor();
			isProcessRuning= false;
		}
	}

	private void importProcessProcessor() {
		//String populateFlag = SchedulerUtil.getPropertyValue(SchedulerConstants.POPULATE_FLAG);
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCR_IMPORTER_USER_NAME);

		String PHCR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.PHCR_IMPORTER_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
		String PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR = SchedulerUtil
				.getPropertyValue(SchedulerConstants.PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR);
		String PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil
				.getPropertyValue(SchedulerConstants.PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE);
		int PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR.trim());
		int PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE.trim());
		
		try {
			boolean isProcessReadyToRun = false;
			if (PHCR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT, PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PHCR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT, PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PHCR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("PHCR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				EdxAutoInvFromInterface.nbsCaseImportScheduler(userName);

		} catch (Exception ex) {
			logger.fatal("error raised in PHCRImporter:" + SchedulerUtil.getStackTrace(ex));
		}
	}
}
