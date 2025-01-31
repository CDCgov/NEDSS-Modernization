package gov.cdc.nedss.systemservice.scheduler.alertemail.alertemailmanager;



import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.alertemail.alertemailscheduler.AlertEmailScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class AlertEmailManager  {

    static final LogUtils logger = new LogUtils(AlertEmailManager.class.getName());

	
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isAlertSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.ALERT_EMAIL_SCHEDULE_TO_RUN);
	    if(isAlertSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during AlertEmailManager:schedule " + ex);
	    		}
	    		AlertEmailScheduler.resetQueue();
	    		timer.schedule(new AlertEmailScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}