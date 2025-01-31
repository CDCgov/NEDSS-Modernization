package gov.cdc.nedss.systemservice.scheduler.cdfsubformimport.cdfsubformimportscheduler;

import gov.cdc.nedss.ldf.subform.util.SubformImportUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;



public class CdfSubformImportScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(CdfSubformImportScheduler.class.getName());


boolean isProcessRuning = false;

  public void run() {
    logger.debug("Generating report for CdfSubformImportScheduler");
    SchedulerUtil.resetProperties();
    String isCdfSubformSchedulerToRun = SchedulerUtil.getPropertyValue("CDF_SUBFORM_IMPORT_SCHEDULE_TO_RUN");
    if(isCdfSubformSchedulerToRun.equalsIgnoreCase("YES")&& !isProcessRuning){
    	isProcessRuning = true;
    	cdfSubFormImportProcessor();
    	isProcessRuning = false;
    }
  }  
  
  private void cdfSubFormImportProcessor(){
	  String nbsRelease = SchedulerUtil.getPropertyValue(SchedulerConstants.CDF_SUBFORM_IMPORT_NBS_RELEASE);
		String userName = SchedulerUtil.getPropertyValue(SchedulerConstants.CDF_SUBFORM_IMPORT_USER_NAME);
		
	  	String CDF_SUBFORM_IMPORT_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.CDF_SUBFORM_IMPORT_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR);
	  	String CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE);
	  	int CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR.trim());
	  	int CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE.trim());
	    
	  	try {
			boolean isProcessReadyToRun = false;
			if (CDF_SUBFORM_IMPORT_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				
				isProcessReadyToRun = this.dailyBatchProcess(CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR_INT, CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (CDF_SUBFORM_IMPORT_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_HOUR_INT, CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (CDF_SUBFORM_IMPORT_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						CDF_SUBFORM_IMPORT_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("CDF_SUBFORM_IMPORT_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				SubformImportUtil.subFormImportScheduler(userName,nbsRelease);

		} catch (Exception ex) {
			logger.fatal("error raised in CdfSubformImportScheduler:" + SchedulerUtil.getStackTrace(ex));
		}
  }
 
  
  }
  
  


