/**
 * Title:        LogUtils
 * Description:  NEDSS Logging Utility
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       12/28/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     01/08/2001 Sohrab Jahani
 * @version      1.0.0
 */

package gov.cdc.nedss.util;

import java.io.File;
import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import org.apache.logging.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

public class LogUtils extends Logger{

  static final String LogConfigurationPath = System.getProperty("nbs.dir") +
	System.getProperty("file.separator") +
	"Properties" + System.getProperty("file.separator") +
	"LogConfig";

  static final String LevelNames[] = {
    "OFF",    // 0 LowestLevel
    "FATAL",  // 1
    "ERROR",  // 2
    "WARN",   // 3
    "INFO",   // 4
    "DEBUG",  // 5
    "ALL"     // 6 HighestLevel
  };

	static final Level Levels[] = { Level.OFF, // 0 LowestLevel
			Level.FATAL, // 1
			Level.ERROR, // 2
			Level.WARN, // 3
			Level.INFO, // 4
			Level.DEBUG, // 5
			Level.ALL // 6 HighestLevel
	};

  static int currentLevel;

  static {
//    currentLevel = 5;
	setLogLevel(1);
    //##!! System.out.println("Logger Trying to be intialized ... ");
//    DOMConfigurator.configure(getLogLevelConfigurationFilename());
    //##!! System.out.println("Logger Initialized to Level: " +  getLogLevelName() + "(" +  currentLevel + ") Using File :" + getLogLevelConfigurationFilename());
  }

//public static Logger logger;
Logger logger;

/*
  // iyer - singleton implementation
 private static LogUtils _instance;

 // For lazy initialization
 public static synchronized LogUtils getLoggerInstance(String name) {
  if (_instance==null) {
    //##!! System.out.println("Trying to be intialize new Logger ");
    //##!! System.out.println("Name is " + name);
    _instance = new LogUtils(name);
    //##!! System.out.println("Instance  is " + _instance);

  }
  else
  {
    //##!! System.out.println("REUSED EXISTING Logger ");
    logger = Logger.getLogger(name);
    //##!! System.out.println("Name is " + name);
   }
  return _instance;
 }
  // end of singleton implementation
*/

 /**
  * LogUtils Constructor
  */
  public LogUtils(String name) {
    super(name);
    logger = getLogger(name);
  }

 /**
  * Gets the current Logger
  *
  * @return      current Logger
  */
  public Logger getLogger() {
    return logger;
  }

 /**
  * Gets the level of logging.
  *
  * @return      Level of Logging
  */
  public  static int getLogLevel() {
    return currentLevel;
  }


  /**
   * Sets the level of logging.
   *
   * @param currentLevel Level of Logging
   */
   public  static void setLogLevel(int newLevel) {
 	currentLevel = newLevel;
 	String configFileName = getLogLevelConfigurationFilename();
 	File file = new File(configFileName);
 	if (file.exists()) {
 		// If we have config files
 		URI uri = file.toURI();
 		Configurator.reconfigure(uri);
 	} else {
 		Configurator.initialize(new DefaultConfiguration());
 		Configurator.setRootLevel(Levels[currentLevel]);
 	}
//     currentLevel = newLevel;
//     DOMConfigurator.configure(getLogLevelConfigurationFilename());
     //##!! System.out.println("Logger Reinitialzied to Level: " +  getLogLevelName() + "(" +  currentLevel + ") Using File :" + getLogLevelConfigurationFilename());
   }

 /**
  * Gets the name of level of logging.
  *
  * @return      Level of Logging as a Name
  */
  public  static String getLogLevelName() {
    return LevelNames[currentLevel];
  }

 /**
  * Gets the filename corresponding to the level of logging.
  *
  * @return      Filename corresponding to the Level of logging
  */
  public static String getLogLevelConfigurationFilename() {
    return LogConfigurationPath +
		System.getProperty("file.separator") +
		"LOG_CONFIG_" + LevelNames[currentLevel] + ".xml";
  }

 /**
  * Converts the level name to the level number.
  *
  * @return      Level of Logging
  */
  public static int levelName2LevelNumber(String strLevel) {

     for (int i=0; i<LevelNames.length; i++)
      if (strLevel.compareTo(LevelNames[i]) == 0) {
        return i;
      }

     return -1;
  }

  String prepareMessage(Object userInfo, Object message) {
    long lCurrentTime = System.currentTimeMillis();
    Date dateCurrent = new Date(lCurrentTime);
    Time timeCurrent = new Time(lCurrentTime);
    String strTemp = userInfo + " @ " +
                     dateCurrent + " " +
                     timeCurrent + " > " +
                     message;
    return strTemp;
  }

 /**
  * Logs message object with the DEBUG level.
  *
  * @param message Message Object
  *
  */
  public void debug(Object message) {
//    //##!! System.out.println(logger);
//    //##!! System.out.println(message);
    logger.debug(message);
  }

 /**
  * Logs message object along with exception object with the DEBUG level.
  *
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void debug(Object message, Throwable t) {
    logger.debug(message, t);
  }

 /**
  * Logs userInfo object along with date, time , and message object with the DEBUG level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  *
  */
  public void debug(Object userInfo, Object message) {
    logger.debug(prepareMessage(userInfo, message));
  }

 /**
  * Logs userInfo object along with date, time, message, and exception object with the DEBUG level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void debug(Object userInfo, Object message, Throwable t) {
    logger.debug(prepareMessage(userInfo, message), t);
  }

 /**
  * Logs message object with the ERROR level.
  *
  * @param message Message Object
  *
  */
  public void error(Object message) {
    logger.error(message);
  }

 /**
  * Logs message object along with exception object with the ERROR level.
  *
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void error(Object message, Throwable t) {
    logger.error(message, t);
  }

 /**
  * Logs userInfo object along with date, time , and message object with the ERROR level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  *
  */
  public void error(Object userInfo, Object message) {
    logger.error(prepareMessage(userInfo, message));
  }

 /**
  * Logs userInfo object along with date, time, message, and exception object with the ERROR level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void error(Object userInfo, Object message, Throwable t) {
    logger.error(prepareMessage(userInfo, message), t);
  }

 /**
  * Logs message object with the FATAL level.
  *
  * @param message Message Object
  *
  */


  public void fatal(Object message) {
    logger.fatal(message);
  }

 /**
  * Logs message object along with exception object with the FATAL level.
  *
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void fatal(Object message, Throwable t) {
    logger.fatal(message, t);
  }

 /**
  * Logs userInfo object along with date, time , and message object with the FATAL level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  *
  */
  public void fatal(Object userInfo, Object message) {
    logger.fatal(prepareMessage(userInfo, message));
  }

 /**
  * Logs userInfo object along with date, time , and message object with the FATAL level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void fatal(Object userInfo, Object message, Throwable t) {
    logger.fatal(prepareMessage(userInfo, message), t);
  }

 /**
  * Logs message object with the INFO level.
  *
  * @param message Message Object
  *
  */
  public void info(Object message) {
    logger.info(message);
  }

 /**
  * Logs message object along with exception object with the INFO level.
  *
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void info(Object message, Throwable t) {
    logger.info(message, t);
  }


 /**
  * Logs userInfo object along with date, time , and message object with the INFO level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  *
  */
  public void info(Object userInfo, Object message) {
    logger.info(prepareMessage(userInfo, message));
  }

 /**
  * Logs userInfo object along with date, time, message, and exception object with the INFO level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void info(Object userInfo, Object message, Throwable t) {
    logger.info(prepareMessage(userInfo, message), t);
  }

 /**
  * Logs message object with the WARN level.
  *
  * @param message Message Object
  *
  */
  public void warn(Object message) {
    logger.warn(message);
  }

 /**
  * Logs message object along with exception object with the WARN level.
  *
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void warn(Object message, Throwable t) {
    logger.warn(message, t);
  }

 /**
  * Logs userInfo object along with date, time , and message object with the WARN level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  *
  */
  public void warn(Object userInfo, Object message) {
    logger.warn(prepareMessage(userInfo, message));
  }

 /**
  * Logs userInfo object along with date, time, message, and exception object with the WARN level.
  *
  * @param userInfo User Information Object
  * @param message Message Object
  * @param t the exception to log, including its stack trace
  *
  */
  public void warn(Object userInfo, Object message, Throwable t) {
    logger.warn(prepareMessage(userInfo, message), t);
  }
}