package gov.cdc.nedss.systemservice.scheduler.blowfishkey.blowfishkeymanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;

import gov.cdc.nedss.systemservice.scheduler.elr.elrscheduler.ELRScheduler;
import gov.cdc.nedss.util.LogUtils;



public class BlowfishKeyManager {


	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
    private static Properties properties = null;
    static final LogUtils logger = new LogUtils(BlowfishKeyManager.class.getName());

	public static void resetProperties(){
    	FileInputStream myFile = null;
        try
        {
                myFile = new FileInputStream( propertiesDirectory + "NEDSSBatchProcess.properties") ;
                //myFile = new FileInputStream(NEDSSConstants.PROPERTY_FILE);
                if(myFile != null)
                {
                	properties = new Properties();
                	properties.load(myFile);
                }
                else
                {  
                }
        }
        catch(IOException e)
        {
            logger.fatal("", e);
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
                logger.fatal("Failed to Close Property File", e);
            }
        }
    }
    
	public static String getPropertyValue(String key){
		String value = properties.getProperty(key);
		return value;
	}
	
	public void schedule() {
	    Timer timer = new Timer();
	    resetProperties();
	    long longDelay = 0;
	    long longTimeGap = 0;
	    String timeGap = properties.getProperty("ELR_SCHEDULE_TIMEGAP_IN_MINUTES");
	    String isElrSchedulerToRun = properties.getProperty("ELR_SCHEDULE_TO_RUN");
	    if(isElrSchedulerToRun.equalsIgnoreCase("YES")){
	    	if( timeGap!=null && timeGap.trim()!="" ){
	    		try{
	    			longDelay = 0;
	    			longTimeGap = Long.parseLong(timeGap.trim())*60000;
	    		}catch(Exception ex){
	    			logger.error("Error caught during conversion" + ex);
	    		}
	    		timer.schedule(new ELRScheduler(), longDelay, longTimeGap);
	    	}
	    }
	}
}