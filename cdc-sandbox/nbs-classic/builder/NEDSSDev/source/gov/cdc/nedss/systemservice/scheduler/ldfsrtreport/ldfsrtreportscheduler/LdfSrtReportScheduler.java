package gov.cdc.nedss.systemservice.scheduler.ldfsrtreport.ldfsrtreportscheduler;


import gov.cdc.nedss.ldf.helper.RunLDFSRTReport;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;



public class LdfSrtReportScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(LdfSrtReportScheduler.class.getName());

boolean isProcessRuning = false;

  public void run() {
    logger.debug("Generating report LdfSrtReportScheduler");
    SchedulerUtil.resetProperties();
    String isLDFSrtReportSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.LDF_SRT_REPORT_SCHEDULE_TO_RUN);
    if(isLDFSrtReportSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning=false;
    }
  }  
  
  private void runProcessor(){
	    
	  	String username = SchedulerUtil.getPropertyValue(SchedulerConstants.LDFREPORT_USERNAME);
	  	String fileName = SchedulerUtil.getPropertyValue(SchedulerConstants.LDFREPORT_FILENAME);
	  	String reportType= SchedulerUtil.getPropertyValue(SchedulerConstants.LDFREPORT_REPORT_TYPE);
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.LDF_SRT_REPORT_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PROCESS_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.LDF_SRT_REPORT_SCHEDULE_TIME_IN_HOUR);
	  	String PROCESS_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.LDF_SRT_REPORT_SCHEDULE_TIME_IN_MINUTE);
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
				logger.error("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for LdfSrtReportScheduler!");
			}
			if (isProcessReadyToRun)
				runProcess( username,  fileName,  reportType);

		} catch (Exception ex) {
			logger.fatal("error raised in DeduplicationProcessScheduler:" + ex);
		}
}

private void runProcess(String username, String fileName, String reportType){
	  try {
		  RunLDFSRTReport.ldfSRTReportProcessor( username,  fileName,  reportType);
	} catch (Exception e) {
		logger.fatal("Exception thrown in DeduplicationProcessScheduler"+ SchedulerUtil.getStackTrace(e));
	}
}
  }
  
  


