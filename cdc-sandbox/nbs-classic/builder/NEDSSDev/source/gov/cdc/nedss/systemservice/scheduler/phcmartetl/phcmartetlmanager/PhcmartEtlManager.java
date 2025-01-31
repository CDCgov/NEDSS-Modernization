package gov.cdc.nedss.systemservice.scheduler.phcmartetl.phcmartetlmanager;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.phcmartetl.phcmartetlscheduler.PhcmartEtlScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;



public class PhcmartEtlManager {


    static final LogUtils logger = new LogUtils(PhcmartEtlManager.class.getName());


	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TIMEGAP_IN_MINUTES);
	    String isPHCMartSchedulerToRun = SchedulerUtil.getPropertyValue(SchedulerConstants.PHCMART_SCHEDULE_TO_RUN);
	    if(isPHCMartSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.fatal("Error caught during conversion in PhcmartEtlManager" + ex);
	    		}
	    		timer.schedule(new PhcmartEtlScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}