package gov.cdc.nedss.systemservice.scheduler.nndldfextraction.nndldfextractionmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.nndldfextraction.nndldfextractionscheduler.NndLdfExtractionScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class NndLdfExtractionManager {


	static final LogUtils logger = new LogUtils(NndLdfExtractionManager.class.getName());

	
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXTRACTION_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.NND_LDF_EXTRACTION_SCHEDULE_TO_RUN);
	    if(isSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during conversion" + ex);
	    		}
	    		timer.schedule(new NndLdfExtractionScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}