package gov.cdc.nedss.systemservice.scheduler.msgout.msgoutscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.nnd.nndbatchprocess.NNDBatchProcessor;

public class MsgOutScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(MsgOutScheduler.class.getName());

boolean isProcessRuning= false;

  public void run() {
	  logger.debug("Generating scheduler for MsgOutScheduler class");
    SchedulerUtil.resetProperties();
    String isSchedulerReadyToRun = SchedulerUtil.getPropertyValue("MSGOUT_SCHEDULE_TO_RUN");
    if(isSchedulerReadyToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	msgOutProcessor();
    	isProcessRuning=false;
    }
  }  
  
  private void msgOutProcessor(){
	    
		
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.MSGOUT_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String MSGOUT_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.MSGOUT_SCHEDULE_TIME_IN_HOUR);
	  	String MSGOUT_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.MSGOUT_SCHEDULE_TIME_IN_MINUTE);
	  	int PROCESS_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(MSGOUT_SCHEDULE_TIME_IN_HOUR.trim());
	  	int PROCESS_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(MSGOUT_SCHEDULE_TIME_IN_MINUTE.trim());
	  	
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
			logger.fatal("error raised in DeduplicationProcessScheduler:" + SchedulerUtil.getStackTrace(ex));
		}
  }
  
  private void runProcess(){
	  NNDBatchProcessor.runNNDBatchProcessor();
  }
  
  }
  
  


