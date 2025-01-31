package gov.cdc.nedss.systemservice.scheduler.elr.elrmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.elr.elrscheduler.ELRScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class ELRManager  {

    static final LogUtils logger = new LogUtils(ELRManager.class.getName());

	
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isElrSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.ELR_IMPORTER_SCHEDULE_TO_RUN);
	    if(isElrSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during ELRManager:schedule" + ex);
	    		}
	    		timer.schedule(new ELRScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}