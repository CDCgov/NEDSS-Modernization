package gov.cdc.nedss.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NedssTimeLogger{
	static final LogUtils logger = new LogUtils(NedssTimeLogger.class.getName());
	public static long generateTimeDiffStartLog(String className, String MethodName){
		if(LogUtils.getLogLevel()>0){
			Calendar calendar = new GregorianCalendar();
			logger.debug(className+"."+MethodName+":-The start Time is:-", calendar.getTimeInMillis());
			return calendar.getTimeInMillis();
		}
		else 
			return 0;
	}
	public static void generateTimeDiffEndLog(String className, String MethodName, long initialTime){
		if(LogUtils.getLogLevel()>0){
			Calendar calendar = new GregorianCalendar();
			if(initialTime>0)
				logger.debug(className+"."+MethodName+":-Time diff is in milliseconds is :-"+(calendar.getTimeInMillis()-calendar.getTimeInMillis()));
			else
				logger.debug(className+"."+MethodName);
		}
	}
	
	public static void generateLog(String className, String MethodName, String value ){
		if(LogUtils.getLogLevel()>0){
			logger.debug(className+"."+MethodName+":-The value is:-"+ value);
		}
	}
	
	public static void generateErrorLog(String className, String MethodName, Exception error ){
		if(LogUtils.getLogLevel()>0){
			logger.fatal(className+"."+MethodName+":-The Error cause is "+ error.getCause());
			logger.fatal(className+"."+MethodName+":-The Error Message is "+ error.getMessage());
		}
	}
	
	
	
}

