package gov.cdc.nedss.systemservice.scheduler.masteretl.masteretlmanager;


import java.util.Timer;



import gov.cdc.nedss.systemservice.scheduler.masteretl.masteretlscheduler.MasterEtlScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class MasterEtlManager {


	 static final LogUtils logger = new LogUtils(MasterEtlManager.class.getName());


	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTER_ETL_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isElrSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.MASTER_ETL_SCHEDULE_TO_RUN);
	    if(isElrSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during conversion" + ex);
	    		}
	    		timer.schedule(new MasterEtlScheduler(), longDelay, longTimeGap);
	    		//timer.schedule(new MasterETLSasScheduler(), longDelay+ SchedulerConstants.TIME_GAP_TO_IN_MIN_RUN_MASTERRTLSAS_PROGRAM_AFTER_NEW_RDB_TABLES, longTimeGap );
	    	}
	    }
	}
}