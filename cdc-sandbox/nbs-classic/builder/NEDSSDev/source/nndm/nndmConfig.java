/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *  Modification : 09/11/2003 BP added two (Action & Service) variables to read from config file
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
* This class loads system configuration values from nndmConfig.properties,
* and exposes them to worker classes.
* <dl>
*  <dt><code> NEDSS_PROPERTIES_FILE</code></dt>
*  <dt><code> POLLING_INTERVAL </code></dt>
*  <dd>Example of polling interval of 8 seconds; <code> POLLING_INTERVAL=8000 </code></dd>
*  <dt><code> XSLT_STYLESHEETS </code></dt>
*  <dd>Example (using ending right slash!); <code> XSLT_STYLESHEETS=C:/platform/nndm/styles/ </code></dd>
*  <dt><code> XML_VALIDATION </code></dt>
*  <dd>Example; <code> XML_VALIDATION=true </code></dd>
*  <dt><code> ERROR_LOG </code></dt>
*  <dd>Example; <code> ERROR_LOG=C:/platform/logs/error.log </code></dd>
* </dl>
* <p>
* NOTE: Regardless of Windows or Unix environment, use a right slash (<b> / </b>)
* as the path seperator for the file location entries in the nndmConfig.properties file.
* </p>
*/
public class nndmConfig
{


   // ***********************  DEFAULT VALUES  *********************************
   /**
    * Default polling time to use when the Polling interval is not set in the
    * nndmConfig.properties file - default value is <code> 5000 </code> milliseconds
    * @see #DAEMON_POLLING_TIME_PROPERTY_NAME
    */
   public static final long DEFAULT_DAEMON_POLLING_TIME = 5000;

    /**
     *  Default location of the error log file when the Polling interval is not set in the
     *  nndmConfig.properties file - default value is <code> [userDirectory]/nndm_error.log </code>
     *  where the userDirectory is the location in the file system from where the
     *  <code> java </code> command was invoked.
     *  @see #ERROR_LOG_PROPERTY_NAME
     */
   public static final String DEFAULT_ERROR_LOG = System.getProperty("user.dir")
                           + System.getProperty("file.separator")
                           + "nndm_error.log";


    /**
     *  Default flag to determine if XML validation against its defining Schema
     *  will be performed - default value is <code> false </code> .
     *  @see #XML_VALIDATION_PROPERTY_NAME
     */
   public static final boolean DEFAULT_XML_VALIDATION = false;



   // ***************  ALLOWABLE CONFIG PROPERTY VALUES  ***********************

   /**
    *  The property name in the configuration / properties file for the
    *  polling time (the time for the daemon thread to sleep) -
    *  <code> POLLING_INTERVAL </code> .
    */
   public static final String DAEMON_POLLING_TIME_PROPERTY_NAME = "POLLING_INTERVAL";

   /**
    *  The property name in the configuration / properties file for the
    *  database type(the time for the daemon thread to sleep) -
    *  <code> POLLING_INTERVAL </code> .
    */
   public static final String DATABASE_TYPE = "DATABASE_TYPE";
   
   /**
    *  The property name in the configuration / properties file for the
    *  location (the location being a a full path with ending path separator)
    *  of the XSLT Stylesheets - <code> XSLT_STYLESHEETS </code> .
    */
   public static final String STYLES_PROPERTY_NAME = "XSLT_STYLESHEETS";

   /**
    *  The property name in the configuration / properties file for the
    *  flag to determine if XML validation against its defining Schema
    *  will be performed - <code> XML_VALIDATION </code> .
    */
   public static final String XML_VALIDATION_PROPERTY_NAME = "XML_VALIDATION";

   /**
    *  The property name in the configuration / properties file for the
    *  location (the location being a a full path with file name)
    *  of the error log for this nndm process - <code> ERROR_LOG </code> .
    */
   public static final String ERROR_LOG_PROPERTY_NAME = "ERROR_LOG";

    /**
     *  The property name in the configuration / properties file for the
     *  Service (String value of NNDM + Version)
     */
    public static final String SERVICE_PROPERTY_NAME = "SERVICE";

    /**
     *  The property name in the configuration / properties file for the
     *  Action (previously PHMTS value TRANSPORT_ACTION_SEND = send)
     */
    public static final String ACTION_PROPERTY_NAME = "ACTION";

   /**
    * The properties / configuration of this Notification Manager subsystem.
    */
   private Properties properties;

   /**
    * Name of config file for this nndm process; use the JVM command
    * swith of <code> -DnndmConfig.propertiesFile=[dir-name]/nndmConfig.properties </code>.
    */
   private String configProperty = "nndmConfig.propertiesFile";

	public static final String propertiesDir = new StringBuffer(System.getProperty("nbs.dir"))
												.append(File.separator)
												.append("Properties")
												.append(File.separator)
												.toString()
												.intern();


   /**
    * The single instance of this object housing the cofiguration settings for the NNDM process.
    */
   private static nndmConfig nndmConfigurations = new nndmConfig();


   /**
    * Get a particular configuration setting for the NNDM process.
    * @param propertyName one of nndmConfig constants representing a configuration
    * property, i.e. <a href="#JDBC_DRIVER_NAME>nndmConfig.JDBC_DRIVER_NAME</a>, etc.
    * @return String value of the requested configuration property found in the
    * nndmConfig.properties, otherwise empty ("").
    */
   public static String getNndmConfigurations(String propertyName){
      return (nndmConfigurations.properties.containsKey(propertyName))? (String)nndmConfigurations.properties.get(propertyName) : "" ;
   }

   /**
    * Construct this nndmConfig object and reads in in the nndmConfig.properties file that
    * is set via the JVM command swith of <code> -DnndmConfig.propertiesFile=[dir-name]/nndmConfig.properties </code>.
    */
	private nndmConfig()
	{
		super();
      readConfig();
	}

    /**  This opens the nndmConfig.properties file and
     *  populates the values of properties exposed by this class.
     */
    private void readConfig(){
      // does file exist....
      String configFile = propertiesDir + "nndmConfig.properties";
      
      if (!((configFile == null) || (configFile.length() == 0))){
         // open nndmConfig.ini which is located in the same folder as this class
         FileInputStream in = null;
         try{
        	 
            this.properties = new Properties();
            in = new FileInputStream(configFile);
            properties.load(in);
         } catch (Exception e){
            // log the problem
            Exception ne = new nndmException("Error loading the " + configFile + " file.",e);
         } finally {
            if (in != null){
               try{in.close();} catch(IOException ioe){
                  Exception ne = new nndmException("Could not properly close the " + configFile + " file.",ioe);
               }
            }
         }
      }
      else {
         Exception e = new nndmException("Unable to properly instantiate the nndm.nndmConfig object and start the nndm process because of "
                        + "missing config Property [" + configProperty  + "]. Check in domain\\Nedss\\Properties directory" );
      }
    }
}
/* END CLASS DEFINITION nndmConfig */
