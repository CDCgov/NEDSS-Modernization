package gov.cdc.nedss.systemservice.scheduler.alertemail.alertemailscheduler;


import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.alert.admin.AlertMailler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;


public class AlertEmailScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(AlertEmailScheduler.class.getName());


static boolean  isProcessRuning = false;

  public void run() {
    logger.debug("Generating report for AlertEmailScheduler");
    SchedulerUtil.resetProperties();
    String isAlertEmailSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_TO_RUN);
    if(isAlertEmailSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning= false;
    }
  }  
  
  private void runProcessor(){
	    
		
	  	String ALERT_EMAIL_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE);
	  	String ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR);
	  	String ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE);
	  	int ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR.trim());
	  	int ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE.trim());
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_USER_NAME);
	  	try {
			boolean isProcessReadyToRun = false;
			if (ALERT_EMAIL_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR_INT, ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (ALERT_EMAIL_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						ALERT_EMAIL_SCHEDULE_TIME_IN_HOUR_INT, ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (ALERT_EMAIL_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						ALERT_EMAIL_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("ALERT_EMAIL_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun){
				AlertMailler.getAlertMessageCollection(userName);
			}
		} catch (Exception ex) {
			logger.fatal("error raised in AlertEmailScheduler:" + SchedulerUtil.getStackTrace(ex));
		}
  }
 
  public static void resetQueue(){
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_USER_NAME);
		AlertMailler.resetQueue(userName);
	  
  }
 }
  
  


