package gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.deduplication.dedupbatchprocess.DeDuplicationSimilarBatchProcessor;
import gov.cdc.nedss.deduplication.dedupbatchprocess.DeDuplicationSameBatchProcessor;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;




public class DeduplicationSameProcessScheduler extends SchedulerUtil {
static final LogUtils logger = new LogUtils(DeduplicationSameProcessScheduler.class.getName());

boolean issameProcessRuning=false;
boolean issimilarProcessRuning=false;
boolean checksamestatus =  false;
String excep = "";

  public void run() {
    logger.debug("Generating report for DeduplicationSameProcessScheduler");
    
    SchedulerUtil.resetProperties();
    String dedupProcess = "InSameProcess";
    SchedulerConstants.setDEDUPPROCESS(dedupProcess);
   // logger.debug("After Setting DEDUPPROCESS value is : "+SchedulerConstants.getDEDUPPROCESS());
    
    String issameDedupSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TO_RUN);
    if(issameDedupSchedulerToRun.equalsIgnoreCase("YES") && !issameProcessRuning){
    	issameProcessRuning= true;
    	//logger.debug("before runSameProcessor");
    	runSameProcessor();
    	if(checksamestatus){
    		try {
    			
    			DeDuplicationSameBatchProcessor.deDuplicationActivityProcess(excep);
    		} catch (Exception e) {
    			logger.fatal("Exception thrown in DeduplicationProcessScheduler"+ SchedulerUtil.getStackTrace(e));
    		}	
    	}
    		checksamestatus = false;
    	//logger.debug("after runSameProcessor");
    	issameProcessRuning = false;
    }
   logger.debug("Same DEDUPPROCESS value is : "+SchedulerConstants.getDEDUPPROCESS());
   dedupProcess = null;
   SchedulerConstants.setDEDUPPROCESS(dedupProcess);
    /* String issimilarDedupSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TO_RUN);
    if(issimilarDedupSchedulerToRun.equalsIgnoreCase("YES") && !issimilarProcessRuning && !issameProcessRuning){
    	issimilarProcessRuning= true;
    	logger.debug("before runSimilarProcessor");
    	runSimilarProcessor();
    	logger.debug("after runSimilarProcessor");
    	issimilarProcessRuning = false;
    }*/
  } 
  
  private void runSameProcessor(){
	    
	  	//String processType = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_PROCESS_TYPE);
	//  logger.debug("into runSameProcessor");
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PROCESS_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TIME_IN_HOUR);
	  	String PROCESS_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TIME_IN_MINUTE);
	  	int PROCESS_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_HOUR.trim());
	  	int PROCESS_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_MINUTE.trim());
	  	logger.debug("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE : "+PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE);
		  
	  	try {
			boolean issameProcessReadyToRun = false;
			
			if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				issameProcessReadyToRun = this.dailyBatchProcess(PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				issameProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				issameProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for DeduplicationProcessScheduler!");
			}
			if (issameProcessReadyToRun)
				runSameProcess();

		} catch (Exception ex) {
			logger.fatal("error raised in DeduplicationProcessScheduler:" + ex);
		}
  }
  
  private void runSameProcess(){
	  try {
		      //logger.debug("into runSameProcess");
	         DeDuplicationSameBatchProcessor.deDuplicationProcess();
	} catch (Exception e) {
		       checksamestatus = true;
		        excep = e.toString();
		       if(excep.length() > 1999)
		    	   excep = excep.substring(0, 1999);
		logger.fatal("Exception thrown in DeduplicationProcessScheduler"+ SchedulerUtil.getStackTrace(e));
	}
  }
  
 /* private void runSimilarProcessor(){
	    
	  	//String processType = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_PROCESS_TYPE);
		
	  	String PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
	  	String PROCESS_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TIME_IN_HOUR);
	  	String PROCESS_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TIME_IN_MINUTE);
	  	int PROCESS_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_HOUR.trim());
	  	int PROCESS_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(PROCESS_SCHEDULE_TIME_IN_MINUTE.trim());
		  
	  	try {
			boolean issimilarProcessReadyToRun = false;
			if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.DAILY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				issimilarProcessReadyToRun = this.dailyBatchProcess(PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.HOURLY)) {
				runTimeInMinutes = 0;
				runTimeMinuteDiff = 0;
				issimilarProcessReadyToRun = this.hourlyBatchProcess(processTime, isScheduledToRun,
						PROCESS_SCHEDULE_TIME_IN_HOUR_INT, PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else if (PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase(SchedulerConstants.MINUTE)) {
				issimilarProcessReadyToRun = this.minuteBatchProcess(runTimeInMinutes, runTimeMinuteDiff,
						PROCESS_SCHEDULE_TIME_IN_MINUTE_INT);
			} else {
				logger.error("PROCESS_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE for DeduplicationProcessScheduler!");
			}
			if (issimilarProcessReadyToRun)
				runSimilarProcess();

		} catch (Exception ex) {
			logger.fatal("error raised in DeduplicationProcessScheduler:" + ex);
		}
    }*/
   
 /* private void runSimilarProcess(){
	  try {
		DeDuplicationSimilarBatchProcessor.deDuplicationProcess();
	} catch (Exception e) {
		checksamestatus = true;
		logger.fatal("Exception thrown in DeduplicationProcessScheduler"+ SchedulerUtil.getStackTrace(e));
	}
  }*/
  }
  
  


