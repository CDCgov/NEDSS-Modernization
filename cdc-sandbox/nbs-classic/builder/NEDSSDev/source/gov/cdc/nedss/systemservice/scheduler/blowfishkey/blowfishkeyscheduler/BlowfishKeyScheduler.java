package gov.cdc.nedss.systemservice.scheduler.blowfishkey.blowfishkeyscheduler;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimerTask;


public class BlowfishKeyScheduler extends TimerTask {
static final LogUtils logger = new LogUtils(BlowfishKeyScheduler.class.getName());


int elrProfileTimeInMinutes = 0;
int elrProfileMinuteDiff = 0;
boolean isExecutedInSameHour = false;

int elrProfileProcessTime = -1;
boolean isScheduledToRun = true;


  public void run() {
    System.out.println("Generating report");
    SchedulerUtil.resetProperties();
    String isUserProfileSchedulerToRun = SchedulerUtil.getPropertyValue("ELR_PROFILE_SCHEDULE_TO_RUN");
    if(isUserProfileSchedulerToRun.equalsIgnoreCase("YES")){
    	elrPrfileProcessor();
    }
  }  
  
  private void elrPrfileProcessor(){
	  Calendar cal = new GregorianCalendar();
	    
	  	String populateFlag = SchedulerUtil.getPropertyValue("POPULATE_FLAG");
		String elrName = SchedulerUtil.getPropertyValue("ELR_NAME");
		
	  	String ELR_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue("ELR_PROFILE_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE");
	  	String ELR_PROFILE_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue("ELR_PROFILE_SCHEDULE_TIME_IN_HOUR");
	  	String ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue("ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE");
	  	int ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT = Integer.parseInt(ELR_PROFILE_SCHEDULE_TIME_IN_HOUR.trim());
	  	int ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT = Integer.parseInt(ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE.trim());
	    
	    try{
	    	
	    	if(ELR_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase("DAILY"))
	    		dailyBatchProcess(cal, ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT, populateFlag, elrName);
	    	else if(ELR_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase("HOURLY"))	
	    		hourlyBatchProcess(cal, ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT, populateFlag, elrName);
	    	else if(ELR_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equalsIgnoreCase("MINUTE"))
	     		minuteBatchProcess(cal, ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT, populateFlag, elrName);
	 	   else{
	    		logger.error("ELR_PROFILE_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE is not defined correct as DAILY, HOURLY or MINUTE!");
	    	}
	    
	    }catch(Exception ex){
	    	logger.error("error raised :" +ex);
	    }
  }
   private void minuteBatchProcess(Calendar cal, int ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, int ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT,String populateFlag, String elrName){
		
		if((elrProfileTimeInMinutes==0)||
			((elrProfileTimeInMinutes+elrProfileMinuteDiff) >= (elrProfileTimeInMinutes + ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT)))
		{ 
			 //MsgInMigrator.elrJobScheduler();
			 elrProfileTimeInMinutes = cal.get(Calendar.MINUTE);
			elrProfileMinuteDiff = elrProfileTimeInMinutes-cal.get(Calendar.MINUTE);
	  		}else{
	  			elrProfileMinuteDiff = (cal.get(Calendar.MINUTE)-elrProfileTimeInMinutes);
	  			if(elrProfileMinuteDiff<0)
	  				elrProfileMinuteDiff = -elrProfileMinuteDiff;
	  		}
  }
  private void dailyBatchProcess(Calendar cal, int ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, int ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT,String populateFlag, String elrName){
	  elrProfileTimeInMinutes = 0;
	  elrProfileMinuteDiff = 0;
	  int HOUR_TIME = ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT;
	  int MINUTE_TIME = ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT;
	  int SCHEDULED_TIME = HOUR_TIME*100+MINUTE_TIME;
	  
	  if((( cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE)) >=SCHEDULED_TIME) &&
			  !isExecutedInSameHour)
	  {
  			 //MsgInMigrator.elrJobScheduler();
  			 isExecutedInSameHour = true;
  		}else{
  			if((SCHEDULED_TIME-cal.get(Calendar.HOUR_OF_DAY)*100)>=2400)	
  				isExecutedInSameHour = false;
	  		}
  	}
  private void hourlyBatchProcess(Calendar cal, int ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT, int ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT,String populateFlag, String elrName){
	  elrProfileTimeInMinutes = 0;
	  elrProfileMinuteDiff = 0;
	  int HOUR_TIME = ELR_PROFILE_SCHEDULE_TIME_IN_HOUR_INT;
	  int MINUTE_TIME = ELR_PROFILE_SCHEDULE_TIME_IN_MINUTE_INT;
	  int SCHEDULED_TIME = HOUR_TIME*100+MINUTE_TIME;
	  
	  if(isScheduledToRun && (( cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE)) >=SCHEDULED_TIME))
	  {
  			 //MsgInMigrator.elrJobScheduler();
  			elrProfileProcessTime = cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE);
  			isScheduledToRun = false;
  			
  		}else{
  			if(cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE)-elrProfileProcessTime>=SCHEDULED_TIME)
  				isScheduledToRun=true;
	  		}
  	}
  
  }
  
  


