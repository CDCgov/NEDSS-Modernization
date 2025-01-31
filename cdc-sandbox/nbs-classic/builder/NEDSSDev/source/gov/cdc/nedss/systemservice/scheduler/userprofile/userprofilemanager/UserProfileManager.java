package gov.cdc.nedss.systemservice.scheduler.userprofile.userprofilemanager;
import gov.cdc.nedss.systemservice.scheduler.userprofile.userprofilescheduler.UserProfileScheduler;
import gov.cdc.nedss.util.LogUtils;

import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;

public class UserProfileManager {


    static final LogUtils logger = new LogUtils(UserProfileManager.class.getName());

    
	public void schedule() {
	    Timer timer = new Timer();
	    SchedulerUtil.resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String isUserProfileSchedulerToRun = SchedulerUtil.getPropertyValue("USER_PROFILE_SCHEDULE_TO_RUN");
	    String timeGap = SchedulerUtil.getPropertyValue("USER_PROFILE_SCHEDULE_TIMEGAP_IN_MINUTES");
	    if(isUserProfileSchedulerToRun.equalsIgnoreCase("YES")){
	    	if(timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during conversion" + ex);
	    		}
	    	}
	    	timer.schedule(new UserProfileScheduler(), longDelay, longTimeGap);
	    }
	  }
	}