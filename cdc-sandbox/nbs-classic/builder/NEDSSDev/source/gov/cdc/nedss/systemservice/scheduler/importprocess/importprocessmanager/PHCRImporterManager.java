package gov.cdc.nedss.systemservice.scheduler.importprocess.importprocessmanager;

import gov.cdc.nedss.systemservice.scheduler.importprocess.importprocessscheduler.PHCRImporterScheduler;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.LogUtils;

import java.util.Timer;

public class PHCRImporterManager {

static final LogUtils logger = new LogUtils(PHCRImporterManager.class.getName());

    
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String isUserProfileSchedulerToRun = SchedulerUtil.getPropertyValue("PHCR_IMPORTER_SCHEDULE_TO_RUN");
	    String timeGap = SchedulerUtil.getPropertyValue("PHCR_IMPORTER_SCHEDULE_TIMEGAP_IN_MINUTES");
	    if(isUserProfileSchedulerToRun.equalsIgnoreCase("YES")){
	    	if(timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during conversion" + ex);
	    		}
	    	}
	    	timer.schedule(new PHCRImporterScheduler(), longDelay, longTimeGap);
	    }
	  }
	}