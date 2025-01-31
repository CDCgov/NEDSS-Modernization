package gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.deduplication.dedupbatchprocess.DeDuplicationBatchProcessor;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;



public class DeduplicationProcessScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(DeduplicationProcessScheduler.class.getName());

/*boolean isProcessRuning=false;

  public void run() {
    logger.debug("Generating report for DeduplicationProcessScheduler");
    SchedulerUtil.resetProperties();
    String isDedupSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_TO_RUN);
    if(isDedupSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning = false;
    }
  }  
  
  private void runProcessor(){
	    
	  	//String processType = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_PROCESS_TYPE);
		
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PROCESS_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_TIME_IN_HOUR);
	  	String PROCESS_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SCHEDULE_TIME_IN_MINUTE);
	  	int PROCESS_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_HOUR.trim());
	  	int PROCESS_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_MINUTE.trim());
		  
	  	try {
			boolean isProcessReadyToRun = false;
			if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for DeduplicationProcessScheduler!");
			}
			if (isProcessReadyToRun)
				runProcess();

		} catch (Exception ex) {
			logger.fatal("error raised in DeduplicationProcessScheduler:" + ex);
		}
  }
  
  private void runProcess(){
	  try {
		DeDuplicationBatchProcessor.deDuplicationProcess();
	} catch (Exception e) {
		logger.fatal("Exception thrown in DeduplicationProcessScheduler"+ SchedulerUtil.getStackTrace(e));
	}
  }*/
  }
  
  


