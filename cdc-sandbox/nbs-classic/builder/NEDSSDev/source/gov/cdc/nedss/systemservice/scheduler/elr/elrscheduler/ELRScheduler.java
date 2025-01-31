package gov.cdc.nedss.systemservice.scheduler.elr.elrscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxAutoLabInvFromInterface;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;


public class ELRScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(ELRScheduler.class.getName());


boolean isProcessRuning = false;

  public void run() {
    logger.debug("Generating report for ELRScheduler");
    SchedulerUtil.resetProperties();
    String isELRSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_TO_RUN);
    if(isELRSchedulerToRun.equalsIgnoreCase("YES") && !isProcessRuning){
    	isProcessRuning= true;
    	runProcessor();
    	isProcessRuning= false;
    }
  }  
  
  private void runProcessor(){
	    
		
	  	String ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE);
	  	String ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR);
	  	String ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE);
	  	int ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR.trim());
	  	int ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE.trim());
	  
	  	try {
			boolean isProcessReadyToRun = false;
			if (ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.dailyBatchProcess(ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT, ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				isProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						ELR_IMPORTER_SCHEDULE_TIME_IN_HOUR_INT, ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				isProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						ELR_IMPORTER_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("ELR_IMPORTER_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
			}
			if (isProcessReadyToRun)
				EdxAutoLabInvFromInterface.nbsCaseImportScheduler(EdxELRConstants.DEBUG, NEDSSConstants.ELR_LOAD_USER_ACCOUNT);

		} catch (Exception ex) {
			logger.fatal("error raised in elrProcessor:" + SchedulerUtil.getStackTrace(ex));
		}
  }
 
  }
  
  


