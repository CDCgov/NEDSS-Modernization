package gov.cdc.nedss.systemservice.scheduler.nndldfextraction.nndldfextractionscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.nnd.helper.NNDLDFExtraction;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;


public class NndLdfExtractionScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(NndLdfExtractionScheduler.class.getName());

boolean isProcessRuning = false;

  public void run() {
    logger.debug("Generating report for NndLdfExtractionScheduler");
    SchedulerUtil.resetProperties();
    String isSchedulerReadyToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXTRACTION_SCHEDULE_TO_RUN);
    if(isSchedulerReadyToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning= false;
    }
  }

  private void runProcessor(){
	  	String SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXT_SCHEDULE_TO_RUN_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXT_SCHEDULE_TO_RUN_SCHEDULE_TIME_IN_HOUR);
	  	String SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXT_SCHEDULE_TO_RUN_SCHEDULE_TIME_IN_MINUTE);
	  	int SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(SCHEDULE_TIME_IN_HOUR.trim());
	  	int SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(SCHEDULE_TIME_IN_MINUTE.trim());

	  	try {
			boolean isProcessReadyToRun = false;
			if (SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(SCHEDULE_TIME_IN_HOUR_INT, SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						SCHEDULE_TIME_IN_HOUR_INT, SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("NndLdfExtractionScheduler SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE inNndLdfExtractionScheduler!");
			}
			if (isProcessReadyToRun)
				NNDLDFExtraction.runNNDLDFExtProcessor();

		} catch (Exception ex) {
			logger.fatal("error raised in NndLdfExtractionScheduler:" + SchedulerUtil.getStackTrace(ex));
		}
}

  }




