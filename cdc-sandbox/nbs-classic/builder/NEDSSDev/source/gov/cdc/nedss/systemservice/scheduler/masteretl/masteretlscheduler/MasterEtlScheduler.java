package gov.cdc.nedss.systemservice.scheduler.masteretl.masteretlscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;



public class MasterEtlScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(MasterEtlScheduler.class.getName());

boolean isProcessRuning = false;


  public void run() {
    logger.debug("Generating report for MasterEtlScheduler");
    SchedulerUtil.resetProperties();
    String isMasterEtlSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTER_ETL_SCHEDULE_TO_RUN);
    if(isMasterEtlSchedulerToRun.equalsIgnoreCase("YES")&& !isProcessRuning){
    	isProcessRuning=true;
    	runProcessor();
    	isProcessRuning=false;
  
    }
  }  
  
  private void runProcessor(){
	    String MASTERETL_ETL_PROCESS= "";
	    if(SchedulerUtil.getPropertyValue(SchedulerConstants.MASTERETL_ETL_PROCESS)!=null)
	    		MASTERETL_ETL_PROCESS=SchedulerUtil.getPropertyValue(SchedulerConstants.MASTERETL_ETL_PROCESS);
	    
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTERETL_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PROCESS_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTERETL_SCHEDULE_TIME_IN_HOUR);
	  	String PROCESS_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTERETL_SCHEDULE_TIME_IN_MINUTE);
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
				logger.error("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for MasterEtlScheduler!");
			}
			if (isProcessReadyToRun)
			{
				    if(MASTERETL_ETL_PROCESS == null){
				    	logger.error("The MasterETL process is set to null, Please set the process to either INCR or FULL");
				    }
				    else if(MASTERETL_ETL_PROCESS.equalsIgnoreCase(SchedulerConstants.MASTERETL_ETL_INCR)){
				    	logger.debug("The MasterETL process is set to "+ MASTERETL_ETL_PROCESS);
						this.runWindowsBatchProcess(SchedulerConstants.INCRE_MASTERETL_WINDOWS_FILE);
					    	
				    }else if(MASTERETL_ETL_PROCESS.equalsIgnoreCase(SchedulerConstants.MASTERETL_ETL_FULL)){
				    	logger.debug("The MasterETL process is set to "+ MASTERETL_ETL_PROCESS);
						this.runWindowsBatchProcess(SchedulerConstants.MASTERETL_WINDOWS_FILE);
					    	
				    }
			   
			   
			}
	  	}
		 catch (Exception ex) {
			logger.fatal("error raised in MasterEtlScheduler:" + SchedulerUtil.getStackTrace(ex));
		}
}

  }
  
  


