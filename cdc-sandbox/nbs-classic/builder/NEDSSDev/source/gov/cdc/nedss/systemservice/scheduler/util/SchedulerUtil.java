package gov.cdc.nedss.systemservice.scheduler.util;


import gov.cdc.nedss.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimerTask;

public class SchedulerUtil extends TimerTask{
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final String batchFileDirectory = new StringBuffer(nedssDirectory).append("BatchFiles").append(File.separator).toString().intern();
    private static Properties properties = null;
    protected boolean isScheduledToRun = true;
    protected int dayNumber= -1;
    protected int runTimeInMinutes = 0;
    protected int dailyHours= -1;
    protected int dailyMinutes= -1;
    protected int SCHEDULED_TIME = -1;
    protected int runTimeMinuteDiff = 0;
    protected int processTime = -1;
    static final LogUtils logger = new LogUtils(SchedulerUtil.class.getName());
    public static int initialTIme = 0;
    private int currentDay=0;
    boolean comparetime = false;
    public static  synchronized  void resetProperties(){
    	Calendar cal = new GregorianCalendar();
    	int timeGap = 0;
    	if((cal.get(Calendar.MINUTE)-initialTIme)>0)
    	 timeGap = cal.get(Calendar.MINUTE)-initialTIme;
    	else
    		timeGap = cal.get(Calendar.MINUTE)+60-initialTIme;
    	if(timeGap==0 ||timeGap>=2){
    		resetPropertyFile();
    		initialTIme = cal.get(Calendar.MINUTE);
    	}
    }
	public static void resetPropertyFile(){
    	FileInputStream myFile = null;
        try
        {
                myFile = new FileInputStream( propertiesDirectory + "NEDSSBatchProcess.properties") ;
                if(myFile != null)
                {
                	properties = new Properties();
                	properties.load(myFile);
                }
                else
                {  
                    logger.fatal("The property file cannot be located in SchedularUtil");
                }
        }
        catch(IOException e)
        {
            logger.fatal("SchedularUtil resetPropertyFile method", getStackTrace(e));
        }
        finally
        {
            try
            {
                if(myFile != null)
                {
                    //close the stream
                    myFile.close();
                }
            }
            catch(IOException e)
            {
                logger.fatal("Failed to Close Property File in resetPropertyFile", getStackTrace(e));
            }
        }
    }
	  public void run() {
		  
	  }
	public static String getPropertyValue(String key){
		String value = properties.getProperty(key);
		return value;
	}
	   protected boolean minuteBatchProcess(int runTimeInMinutes, int runTimeMinuteDiff, int SCHEDULE_TIME_IN_MINUTE){
		   Calendar cal = new GregorianCalendar();
			if((runTimeInMinutes==0)||
				((runTimeInMinutes+runTimeMinuteDiff) >= (runTimeInMinutes + SCHEDULE_TIME_IN_MINUTE)))
			{ 
				this.runTimeInMinutes = cal.get(Calendar.MINUTE);
				this.runTimeMinuteDiff = 0;
		  	return true;	
			}else{
		  			this.runTimeMinuteDiff = (cal.get(Calendar.MINUTE)-runTimeInMinutes);
		  			if(runTimeMinuteDiff<0)
		  				this.runTimeMinuteDiff = -runTimeMinuteDiff;
		  	return false;	
			}
	  } 
	   protected boolean dailyBatchProcess(int SCHEDULE_TIME_IN_HOUR, int SCHEDULE_TIME_IN_MINUTE){
		  Calendar cal = new GregorianCalendar();
		  int HOUR_TIME = SCHEDULE_TIME_IN_HOUR;
		  int MINUTE_TIME = SCHEDULE_TIME_IN_MINUTE;
		  if(MINUTE_TIME>59){
			  logger.fatal("....SCHEDULE_TIME_IN_MINUTE cannot be more than 59, Please check!!");
			  return false;
		  }
		  if(HOUR_TIME>23){
			  logger.fatal("....SCHEDULE_TIME_IN_HOUR cannot be more than 23, Please check!!");
			  return false;
		  }
		  if(dailyHours!= HOUR_TIME || dailyMinutes !=MINUTE_TIME){
			  dailyHours= HOUR_TIME;
			  dailyMinutes =MINUTE_TIME;
			  SCHEDULED_TIME = dailyHours*100 + dailyMinutes;
			  if(SCHEDULED_TIME<( cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE)))
			      dayNumber = cal.get(Calendar.DATE);
			  else
			      dayNumber = -1; 
		  }
		  if((( cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE)) >=SCHEDULED_TIME) &&  dayNumber !=cal.get(Calendar.DATE))
		  {
	  			 dayNumber = cal.get(Calendar.DATE);
	  			logger.debug("SchedulerUtil.dailyBatchProcess.SCHEDULED_TIME is in if :"+SCHEDULED_TIME);
	  			logger.debug("SchedulerUtil.dailyBatchProcess.calculated time is in if:"+(cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE)));
	  			logger.debug("SchedulerUtil.dailyBatchProcess.dayNumber is in if:"+dayNumber);
	  			return true;
	  	  }
		  else{
	  			logger.debug("SchedulerUtil.dailyBatchProcess.SCHEDULED_TIME is in else :"+SCHEDULED_TIME);
	  			logger.debug("SchedulerUtil.dailyBatchProcess.calculated time is in else:"+(cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE)));
	  			logger.debug("SchedulerUtil.dailyBatchProcess.dayNumber is in else:"+dayNumber);
	  			return false;
		  }
	  	}
	 ////checkout for deduplication changes - separation of same and similar process
	   
	   protected boolean dailyBatchProcess(int SCHEDULE_TIME_IN_HOUR, int SCHEDULE_TIME_IN_MINUTE, String strProcess){
			  Calendar cal = new GregorianCalendar();	  
			  int HOUR_TIME = SCHEDULE_TIME_IN_HOUR;
			  int MINUTE_TIME = SCHEDULE_TIME_IN_MINUTE;
			  logger.debug("currentDay :"+currentDay);
			  if(MINUTE_TIME>59){
				  logger.fatal("....SCHEDULE_TIME_IN_MINUTE cannot be more than 59, Please check!!");
				  return false;
			  }
			  if(HOUR_TIME>23){
				  logger.fatal("....SCHEDULE_TIME_IN_HOUR cannot be more than 23, Please check!!");
				  return false;
			  }
			  if(dailyHours!= HOUR_TIME || dailyMinutes !=MINUTE_TIME){
				  dailyHours= HOUR_TIME;
				  dailyMinutes =MINUTE_TIME;
				  SCHEDULED_TIME = dailyHours*100 + dailyMinutes;
				  if(SCHEDULED_TIME<( cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE))){
				      dayNumber = cal.get(Calendar.DATE);
					   if(currentDay != cal.get(Calendar.DATE)){
						  comparetime = compareschedule();
						  if(comparetime){
						  currentDay = cal.get(Calendar.DATE);
						 // dayNumber =  -1;
						  }
					    }
					  
				  } else
				      dayNumber = -1; 
			  }
			 
			  logger.debug("currentDay :"+currentDay);
			  if(((( cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE)) >=SCHEDULED_TIME) &&  dayNumber !=cal.get(Calendar.DATE)) || comparetime)
			  {
				   logger.debug("before SchedulerUtil.dailyBatchProcess.dayNumber is in if:"+dayNumber);
				     dayNumber = cal.get(Calendar.DATE);
				     currentDay = cal.get(Calendar.DATE);
				     comparetime = false;
		  			logger.debug("SchedulerUtil.dailyBatchProcess.SCHEDULED_TIME is in if :"+SCHEDULED_TIME);
		  			logger.debug("SchedulerUtil.dailyBatchProcess.calculated time is in if:"+(cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE)));
		  			logger.debug("SchedulerUtil.dailyBatchProcess.dayNumber is in if:"+dayNumber);
		  			return true;
		  	  }
			  else{
		  			logger.debug("SchedulerUtil.dailyBatchProcess.SCHEDULED_TIME is in else :"+SCHEDULED_TIME);
		  			logger.debug("SchedulerUtil.dailyBatchProcess.calculated time is in else:"+(cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE)));
		  			logger.debug("SchedulerUtil.dailyBatchProcess.dayNumber is in else:"+dayNumber);
		  			return false;
			  }
		  	}
		   
	   protected boolean hourlyBatchProcess(int processTime, boolean isScheduledToRun, int HOUR_TIME, int MINUTE_TIME){
		  Calendar cal = new GregorianCalendar();
		  int SCHEDULED_TIME = HOUR_TIME*100+MINUTE_TIME;
		  if(MINUTE_TIME>60){
			  logger.fatal("....SCHEDULE_TIME_IN_MINUTE cannot be more than 60, Please check and recorrect!!");
			  return false;
		  }
		  if(isScheduledToRun && (( cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE)) >=SCHEDULED_TIME))
		  {
	  			this.processTime = cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE);
	  			this.isScheduledToRun = false;
	  			return true;
		  			
	  		}else{
	  			if(cal.get(Calendar.HOUR)*100+cal.get(Calendar.MINUTE)-processTime>=SCHEDULED_TIME)
	  				this.isScheduledToRun=true;
	  			return false;
	  		}
	  	}
	   
	   protected void runWindowsBatchProcess(String batchFileName) 
	   { 
		  
		   Runtime rt=Runtime.getRuntime(); 
		   try 
		   { 
		 	  rt.exec("cmd /c "+ SchedulerUtil.batchFileDirectory+ batchFileName); 
		   }catch(Exception e){
		 	  logger.fatal("error raised"+ getStackTrace(e));
		   } 
		   
	   }
	   
		public static String getStackTrace(Throwable t) {
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        t.printStackTrace(pw);
	        pw.flush();
	        return sw.toString();
	    }
     
		private boolean  compareschedule(){
			 boolean scheduleflag= false;
			 
				String PROCESS_SIMILAR_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
			  	String PROCESS_SIMILAR_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TIME_IN_HOUR);
			  	String PROCESS_SIMILAR_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SIMILAR_SCHEDULE_TIME_IN_MINUTE);
			  	String PROCESS_SAME_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_HOURLY_OR_DAILY_OR_MINUTE);
			  	String PROCESS_SAME_SCHEDULE_TIME_IN_HOUR = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TIME_IN_HOUR);
			  	String PROCESS_SAME_SCHEDULE_TIME_IN_MINUTE = SchedulerUtil.getPropertyValue(SchedulerConstants.DEDUPLICATION_SAME_SCHEDULE_TIME_IN_MINUTE);
			  	
			  	if(PROCESS_SIMILAR_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE.equals(PROCESS_SAME_SCHEDULE_HOURlY_OR_DAILY_OR_MINUTE) && PROCESS_SIMILAR_SCHEDULE_TIME_IN_HOUR.equals(PROCESS_SAME_SCHEDULE_TIME_IN_HOUR)&& PROCESS_SIMILAR_SCHEDULE_TIME_IN_MINUTE.equals(PROCESS_SAME_SCHEDULE_TIME_IN_MINUTE)){
			  
			  		logger.debug("schedule for same and similar process are ther same ");
			  		scheduleflag = true;
			  		//count++;
			  	}
			return  scheduleflag; 
		  }

}
