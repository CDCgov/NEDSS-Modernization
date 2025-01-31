package gov.cdc.nedss.systemservice.scheduler.msgout.msgoutmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.msgout.msgoutscheduler.MsgOutScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class MsgOutManager {


    static final LogUtils logger = new LogUtils(MsgOutManager.class.getName());

	
	
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.MSGOUT_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isMsgOutSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.MSGOUT_SCHEDULE_TO_RUN);
	    if(isMsgOutSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during MsgOutManager schedule" + ex);
	    		}
	    		timer.schedule(new MsgOutScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}