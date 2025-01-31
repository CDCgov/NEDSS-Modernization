package gov.cdc.nedss.systemservice.scheduler.phcmartetl.phcmartetlscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;


public class PhcmartEtlScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(PhcmartEtlScheduler.class.getName());

boolean isProcessRuning = false;

  public void run() {
    logger.debug("Generating scheduler for PhcmartEtlScheduler");
    SchedulerUtil.resetProperties();
    String isSchedulerReadyToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TO_RUN);
    if(isSchedulerReadyToRun.equalsIgnoreCase("YES")&& !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning= false;
    }
  }

  private void runProcessor(){
	  	String PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TO_RUN_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PHCMART_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TO_RUN_SCHEDULE_TIME_IN_HOUR);
	  	String PHCMART_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TO_RUN_SCHEDULE_TIME_IN_MINUTE);
	  	int PHCMART_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PHCMART_SCHEDULE_TIME_IN_HOUR.trim());
	  	int PHCMART_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PHCMART_SCHEDULE_TIME_IN_MINUTE.trim());
	 	
	  	try {
			boolean isProcessReadyToRun = false;
			if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(PHCMART_SCHEDULE_TIME_IN_HOUR_INT, PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PHCMART_SCHEDULE_TIME_IN_HOUR_INT, PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PHCMART_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("PHCMART_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				this.runWindowsBatchProcess(SchedulerConstants.PHCMART_WINDOWS_FILE);

		} catch (Exception ex) {
			logger.fatal("error raised in phcMartProcessor:" + SchedulerUtil.getStackTrace(ex));
		}
  }
  
  

  }




