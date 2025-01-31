/**
 * Title: PropertyUtil.java
 * Description: Stand alone java class for reading a nedss.properties file.When creating new object,
 * it takes path to configuration file. Every line in configuration file matches to a method
 * for reading it.
 * Updated in release 6.0 for SAS Server IP address
 * Copyright:    Copyright (c) 2001
 * Company: CSC
 * @author: npeng
 *  @updated Pradeep Sharma
 * @version 4.4.1
 *  @updated Pradeep Sharma
 * @version 6.0
 */
package gov.cdc.nedss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuth;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuthHome;

public class PropertyUtil
{
    static final LogUtils logger = new LogUtils(PropertyUtil.class.getName());
    private static String propertiesFile = "jndi.properties";
    private static Properties properties =  null;
	private static boolean readFromFile = false;
    private static boolean valid = true;
	private static ArrayList<Object> cachedStdList= new ArrayList<Object>();
	private static ArrayList<Object> cachedStdAndHivList= new ArrayList<Object>();
	private static ArrayList<Object> cachedHivList= new ArrayList<Object>();
	private static ArrayList<Object> cachedTBList= new ArrayList<Object>();
	

    private static PropertyUtil instance = null;

    public static final String nedssDir = new StringBuffer(System.getProperty("nbs.dir"))
													.append(File.separator)
													.toString()
													.intern();

    public static final String propertiesDir = new StringBuffer(nedssDir)
															.append("Properties")
															.append(File.separator)
															.toString()
															.intern();
    private static String dbInstanceName = null;
    

  	/**
	 * Get the unique instance of this class.
  	 * 
	 */
	public static PropertyUtil getInstance() {

		if (instance == null) {
			instance = new PropertyUtil();
			if (properties == null)
				properties = new Properties();
			FileInputStream myFile = null;
			try {
				myFile = new FileInputStream(propertiesDir + propertiesFile);
				properties = new Properties();
				properties.load(myFile);
				NedssUtils nedssUtils = new NedssUtils();
				Object objref = nedssUtils
						.lookupBean(JNDINames.NBS_DB_SECURITY_EJB);
				DbAuth dbAuth = null;
				DbAuthHome home = (DbAuthHome) PortableRemoteObject.narrow(
						objref, DbAuthHome.class);
				dbAuth = home.create();
				Map<String, String> configMap = dbAuth.getConfigList();
				if (!configMap.isEmpty()) {
					instance.properties.putAll(configMap);
					logger.info("Note: Read "
							+ configMap.size()
							+ " properties from NBS_ODSE.nbs_configuration table");
				}
				
				if(properties==null || properties.size()==0) {
					valid = false;
					logger.error("Error: Unable to read Properties from Database or jndi properties");
				}

			} catch (CreateException e) {
				logger.error("PropertyUtil: CreateException thrown from DbAuthEJB "
						+ e);
			} catch (RemoteException e) {
				logger.error("PropertyUtil: RemoteException thrown from DbAuthEJB "
						+ e);
			} catch (IOException e) {
				logger.fatal("", e);
				valid = false;
			} catch (Exception e) {
				logger.error("PropertyUtil: Exception thrown from DbAuthEJB "
						+ e);
			} finally {
				try {
					if (myFile != null) {
						myFile.close();
					}
				} catch (IOException e) {
					logger.fatal("Failed to Close Property File", e);
					valid = false;
				}
			}
		}
		synchronized (instance) {
			return instance;
		}
	}
	
    public PropertyUtil(){

    }

    /**
     *  Gets whether this is an ABC state or not.
     *  Used by BMIRD.
     *  Default value is "ABCQUESTION".
     *  @return "ABCQUESTION".
     */
    public String getABCSTATE()
    {
        String strDefault = "ABCQUESTION";
        if(!valid)
        {
            return strDefault;
        }
        return properties.getProperty("ABCSTATE", strDefault);
    }

    public int getCacheCount()
    {
        if(isValid())
        {
            return (Integer.parseInt(properties.getProperty("CACHECOUNT")));
        }
        else
        {
            return 0;
        }
    }

    public int getLabCount() {
      if (isValid()) {
        return (Integer.parseInt(properties.getProperty("LAB_COUNT")));
      }
      else {
        return 0;
      }
    }
    public int getMorbCount() {
      if (isValid()) {
        return (Integer.parseInt(properties.getProperty("MORB_COUNT")));
      }
      else {
        return 0;
      }
    }
    
    public Boolean isTest(){
    	return (Boolean.parseBoolean(properties.getProperty("TEST_ENV_FLAG")));
    }
    
    public int getDocCount() {
        if (isValid()) {
          return (Integer.parseInt(properties.getProperty("DOC_COUNT")));
        }
        else {
          return 0;
        }
      }
    public String getLab112Cd() {
      if (isValid()) {
        return properties.getProperty("Lab112Cd");
      }
      else {
        return null;
      }
    }
   
    public String getInitialContextFactory()
    {
        if(isValid())
        {
            return properties.getProperty("INITIAL_CONTEXT_FACTORY");
        }
        else
        {
            return null;
        }
    }

    public String getLookup(String JNDIname)
    {
    	System.out.println(" in PropertyUtil.getLookup(" + JNDIname + ") ");
        if(isValid())
        {
            return (properties.getProperty("PREFIX") + "://" +
            		properties.getProperty("IP") + ":"
					+ properties.getProperty("PORT") + "/"
					+ properties.getProperty("DIR_NAME") + "/" + JNDIname).intern();
        }
        else
        {
            return null;
        }
    }

    public String getLookup()
    {
    	System.out.println(" in PropertyUtil.getLookup() ");
        if(isValid())
        {
            return (properties.getProperty("PREFIX") + "://" +
						properties.getProperty("IP") + ":" +
						properties.getProperty("PORT") + "/" +
						properties.getProperty("DIR_NAME") + "/").intern();
        }
        else
        {
            return null;
        }
    }

    /**
     *  Gets the minimum valid year for MMWR.
     *  Used by Summary Data.
     *  Default value is "1950".
     *  @return the minimum MMWR year.
     */
    public String getMIN_MMWR_YEAR()
    {
        String strDefault = "1950";
        if(!valid)
        {
            return strDefault;
        }
        return properties.getProperty("MIN_MMWR_YEAR", strDefault);
    }

    public String getNBS_CLASS_CODE()
    {
        String strDefault = null;
        if(!valid)
        {
            return strDefault;
        }
        strDefault = "";
        return properties.getProperty("NBS_CLASS_CODE", strDefault);
    }

    /**
     * getTEST_ENV_FLAG(): returns the value of TEST_ENV_FLAG from the nbs_configuration table
     * @return
     */
    public String getTEST_ENV_FLAG(){
    	 String strDefault = null;
         if(!valid)
         {
             return strDefault;
         }
         return properties.getProperty("TEST_ENV_FLAG", strDefault);
    	
    }
    /**
     *  Gets the state code for this state.
     *  Used by Summary Data.
     *  Default value is null.
     *  @return the NBS state code.
     */
    public String getNBS_STATE_CODE()
    {
        String strDefault = null;
        if(!valid)
        {
            return strDefault;
        }
        return properties.getProperty("NBS_STATE_CODE", strDefault);
    }

    /* NND_ARCHIVE_MESSAGE - used to archive XML files to \nedss\NND\MessageArchive directory */
    public String getNNDArchiveMessage()
    {
        String s = null;
        if(!valid)
        {
            return null;
        }
        s = properties.getProperty("NND_ARCHIVE_MESSAGE");
        if(s == null)
        {
            s = "Y";  //default is archive
        }
        return s;
    }

    public Integer getNNDMaxRow()
    {
        if(isValid())
        {
            return new Integer(properties.getProperty("NND_MAX_ROW"));
        }
        else
        {
            return null;
        }
    }

    public String getNNDRootExtensiontTxt()
    {
        if(isValid())
        {
            return properties.getProperty("NND_ROOT_EXTENSION_TXT");
        }
        else
        {
            return null;
        }
    }

    public String getNNDSendingFacilityNm()
    {
        if(isValid())
        {
            return properties.getProperty("NND_SENDING_FACILITY_NM");
        }
        else
        {
            return null;
        }
    }

    public int getLabNumberOfRows()
    {
    	int numbeOfRows = 100;
        try
        {
            if(isValid())
            {
                if(properties.getProperty("LABNUMBEROFROWS") != null)
                {
                	numbeOfRows = Integer.parseInt(properties.getProperty("LABNUMBEROFROWS"));
                    if(numbeOfRows>2500)
                    	return 2500;
                }
                else
                {
                    return numbeOfRows;
                }
            }
            else
            {
                return numbeOfRows;
            }
        }
        catch(Exception e)
        {
            logger.fatal("", e);
            return 100;
        }
		return numbeOfRows;
    }


    public int getNumberOfRows()
    {
    	int numbeOfRows = 100;
        try
        {
            if(isValid())
            {
                if(properties.getProperty("NUMBEROFROWS") != null)
                {
                	numbeOfRows = Integer.parseInt(properties.getProperty("NUMBEROFROWS"));
                    if(numbeOfRows>2500)
                    	return 2500;
                }
                else
                {
                    return numbeOfRows;
                }
            }
            else
            {
                return numbeOfRows;
            }
        }
        catch(Exception e)
        {
            logger.fatal("", e);
            return 100;
        }
		return numbeOfRows;
    }

    public int getStartIndex()
    {
        if(isValid())
        {
            return (Integer.parseInt(properties.getProperty("STARTINDEX")));
        }
        else
        {
            return 0;
        }
    }

    public String getLoadOnStartupLabCache()
 {
       if(isValid())
     {
       return properties.getProperty("LOAD_ON_STARTUP");
     }
     else
     {
         return null;
     }
 }
 /**
  * Returns the maximum number for drop downs allowed for SRT Filtering mechanism
  * @return String
  */
 public String getMaxDropDownCount()
{
    if(isValid())
  {
    return properties.getProperty("MAX_DROPDOWN_COUNT");
  }
  else
  {
      return null;
  }
}



    public boolean isValid()
    {
        return valid;
    }

    public Properties getProperties()
    {
        if(isValid())
        {
            return properties;
        }
        else
        {
            return null;
        }
    }

    public String getProviderURL()
    {
        if(isValid())
        {
            return (properties.getProperty("PREFIX") + "://" +
				   properties.getProperty("IP") + ":" +
				   properties.getProperty("PORT").intern());
        }
        else
        {
            return null;
        }
    }

    public String getUrlPkgPrefixes()
    {
    	if(isValid())
        {
            return properties.getProperty("URL_PKG_PREFIXES");
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Gets the value of the SHOW_CONTEXT_INFO property.
     * Used by navbar.xsp.
     * Default value is "Y".
     * @return Y to show context info or N to not show it.
     */
    public String getSHOW_CONTEXT_INFO()
    {
        return getProperty("SHOW_CONTEXT_INFO", "Y");
    }

    /**
     * Gets the value of the SHOW_DEBUG_BUTTON property.
     * Used by various xsp files.
     * Default value is "Y".
     * @return Y to show the debug button on the toolbar or N to not show it.
     */
    public String getSHOW_DEBUG_BUTTON()
    {
        return getProperty("SHOW_DEBUG_BUTTON", "Y");
    }

    /**
     * Gets the value of the NND_NBS_PROCESSING_CODE property.
     * @return T for testing, P for production.
     */
     public String getNND_MESSAGE_VERSION()
    {
        if(isValid())
        {
            return properties.getProperty("NND_MESSAGE_VERSION");
        }
        else
        {
            return null;
        }
    }

  /* Gets whether LDF is supported or not. It looks at LDF
    variable value in property file.
  * @return LDF is supported or not.
  */
   /* public static boolean getLDF()
    {
        if(isValid())
        {
            String ldfStr= (String)properties.getProperty("LDF");
            if (ldfStr.equalsIgnoreCase("TRUE"))
              return true;
        }
        return false;
    }*/
    public String getUserAuthentication()
    {
        if(isValid())
        {
            return properties.getProperty("USER_AUTHENTICATION_CLASS");
        }
        else
        {
            return null;
        }
    }
    public String getUserAuthenticationMethod()
   {
       if(isValid())
       {
           return properties.getProperty("USER_AUTHENTICATION_METHOD");
       }
       else
       {
           return null;
       }
   }
   public String getUserAuthenticationErrorPage()
      {
          if(isValid())
          {
              return properties.getProperty("USER_AUTHENTICATION_ERROR_PAGE");
          }
          else
          {
              return null;
          }
      }


   public String getDmMappingFile()
   {
        if(isValid())
        {
             return properties.getProperty("DM_MAPPING_FILE");
        }
         else
         {
           return null;
         }
   }

   public boolean needToSendNotificationAfterDM()
   {
        if(isValid())
        {
          if(properties.getProperty("NND_FOR_DM") == null
             || (properties.getProperty("NND_FOR_DM").equalsIgnoreCase("YES") ||
                 properties.getProperty("NND_FOR_DM").trim().equals("")))
          {
            return true;
          }
          else
          {
            return false;
          }
        }
         else
         {
           return false;
         }
   }

   public String getStateSiteCode() {
     return properties.getProperty("STATE_SITE_CODE");

   }
   /** Default data migration type is event.
    *  User can set it to entity in property file
   */
   public boolean getEntityMigrationType()
   {
        if(isValid())
        {
          if(properties.getProperty("DM_ENTITY") != null
             && properties.getProperty("DM_ENTITY").equalsIgnoreCase("YES"))
          {
            return true;
          }
          else
          {
            return false;
          }
        }
         else
         {
           return false;
         }
   }
   /** Default is to delete generated XML files.
    *  User can set it to YES, to keep them for debug, in property file
   */
   public boolean getDMKeepItGenerated()
   {
        if(isValid())
        {
          if(properties.getProperty("DM_KEEPGENERATED") != null
             && properties.getProperty("DM_KEEPGENERATED").equalsIgnoreCase("YES"))
          {
            return true;
          }
          else
          {
            return false;
          }
        }
         else
         {
           return false;
         }
   }
    /**
     * Invocation results in the DeDupOverride property being returned to the caller.
     * @return boolean
     */
    public boolean getDeDupOverride()
    {
           if(isValid())
           {
               if(properties.getProperty("DEDUP_OVERRIDE") == null) return false;

               return (properties.getProperty("DEDUP_OVERRIDE").equals(NEDSSConstants.OVERRIDE)) ? true : false;
           }

           return false;
    }

    /**
     * Mutator to toggle "DEDUP_OVERRIDE".
     * @param inString
     */
    public void setDeDupOverride(String inString)
    {
        if(isValid())
        {
            properties.setProperty("DEDUP_OVERRIDE", inString);
        }
    }
    /**
     * Sets the value of the readFromFile property.
     *
     * @param aReadFromFile the new value of the readFromFile property
     */
    public void setReadFromFile(boolean aReadFromFile)
    {
       readFromFile = aReadFromFile;
    }

    /**
     * Access method for the businessObjUid property.
     *
     * @return   the current value of the businessObjUid property
     */
    public boolean getReadFromFile()
    {
       return readFromFile;
    }




    //testing code
    public void main(String[] args)
    {
        try
        {
            PropertyUtil PropertyUtil = new PropertyUtil();
            /* logger.debug( PropertyUtil.getLookup("PersonList") );
               logger.debug( PropertyUtil.getLookup() );
               logger.debug( PropertyUtil.getProviderURL() );*/

            //##!! System.out.println( "STATE CODE = " + PropertyUtil.getNBS_STATE_CODE());
        }
        catch(Exception e)
        {
            logger.debug(e);
        }
    }

    /**
     * Reads property values.
     * @param pName the name of the property to read.
     * @param pDefault the default value to return if the property doesn't exist.
     * @return the property value or the default value or null.
     */
    public  String getProperty(String pName, String pDefault)
    {
        //  Verify parameters.
        if(pName == null)
        {
            return null;
        }
        //  Make sure properties file has been loaded.
        if(!valid)
        {
            return pDefault;
        }
        //  Read property value.
        //  Return default value if property not found.
        String s = properties.getProperty(pName.intern(), pDefault.intern());
        return s;
    }

    public  int getMaxLDFCount() {
      return Integer.parseInt(properties.getProperty("MAX_LDF_COUNT", "300"));
    }

    public  String getSeedValue() {
          return properties.getProperty("SEED_VALUE");
    }
    public  String getUidSufixCode() {
          return properties.getProperty("UID_SUFFIX_CODE");
    }




    public  int getDataMigrationPersonCount() {
      if (isValid()) {
        Integer countInd = new Integer(properties.getProperty("DM_PERSON"));
        return  countInd.intValue();
      }
      else {
        return 0;
      }
    }
    public  int getDataMigrationOrgCount() {
      if (isValid()) {
        Integer countInd = new Integer(properties.getProperty("DM_ORGANIZATION"));
        return  countInd.intValue();
      }
      else {
        return 0;
      }
    }

    public String getDmMappingDirectory()
    {
      if(isValid())
      {
        return properties.getProperty("DM_MAPPING_DIR");
      }
      else
      {
           return null;
      }
    }

    public String getSRTFilterProperty(String mappingTableName)
    {
      if(isValid())
      {
        return properties.getProperty(mappingTableName);
      }
      else
      {
           return null;
      }
    }

    public String getLDAPMangerDN()
    {
      if(isValid())
     {
       return properties.getProperty("LDAP_MANAGER_DN");
     }
     else
     {
          return null;
     }
    }

    public String getSystemReferenceTable()
   {
     if(isValid())
    {
      return properties.getProperty("SYSTEM_REFERENCE_TABLE");
    }
    else
    {
         return null;
    }
   }


     public String getLDAPManagerPassword()
   {
     if (isValid()) {
       return properties.getProperty("LDAP_MANAGER_PASSWORD");
     }
     else {
       return null;
    }

    }


     public boolean isLDFXspCacheOn()
   {

     if (isValid()) {
       String ldfString =  properties.getProperty("LDF_XSP_CACHE_ON");
       if(ldfString != null && ldfString.equalsIgnoreCase("false")){
       	return false;
       }
     }

     // default to true
     return true;

    }

  	public Map<Object,Object> getEncryptionSettings() {



  		if (isValid()) {
  			Map<Object,Object> retval = new HashMap<Object,Object>();
  			for (Iterator<Object> iter = properties.keySet().iterator();
  				iter.hasNext();
  				) {
  				String element = (String) iter.next();
  				if (element != null
  					&& element.startsWith("encryption.setting.")) {
  					retval.put(element, properties.get(element));
  				}
  			}
  			return retval;
  		} else {
  			return null;
  		}

  	}

  	public String getUsernameEncryptionSetting() {
  		String s = properties.getProperty("user.name.encryption.setting");
  		if (isValid()) {
  			if(s==null)
  			  return null;
  		    	else
  			return s;
  		} else {
  			return null;
  		}

  	}


     public String getLogoutPage()
   {

     if (isValid()) {
       return properties.getProperty("LOGOUT_PAGE");
     }

     // default to null
     return null;

    }

	/**
	 * Returns the number of rows configuration for Approval Queue
	 *	for Initial Notifications Queue
	 * @return number of rows for Approval Queue for Initial Notifications Queue
	 */
	public int getApprQueueForNotificationsDisplaySize(){
		if(isValid()){
			String sizeStr= properties.getProperty("APPR_QUEUE_FOR_NOTIF_DISP_SIZE");

			if(sizeStr != null && sizeStr.trim().length() != 0){
				Integer size = new Integer(sizeStr);
				return size.intValue();
			}
		}

		return 0;
	}


	/**
	 * This method will return the size of the queue to be displayed per page.
	 * @param queue - The queue String for which the display size is needed
	 * @return int value
	 */
	public Integer getQueueSize(String queue){
		if(isValid() && queue!=null){
			String sizeStr= properties.getProperty(queue);

			if(sizeStr != null && sizeStr.trim().length() != 0){
				Integer size = new Integer(sizeStr.trim());
				return size;
			}
		}
		return new Integer(10);
	}


	/**
	 * Returns the number of rows configuration for Updated Queue
	 *	for Initial Notifications Queue
	 * @return number of rows for Updated Queue for Initial Notifications Queue
	 */
	public int getUpdQueueForNotificationsDisplaySize(){
		if(isValid()){
			String sizeStr= properties.getProperty("UPD_QUEUE_FOR_NOTIF_DISP_SIZE");

			if(sizeStr != null && sizeStr.trim().length() != 0){
				Integer size = new Integer(sizeStr);
				return size.intValue();
			}
		}

		return 0;
	}



	public int getDefaultLabTabOrder() {
		if (isValid()) {
			String tab = properties.getProperty("DEFAULT_LAB_TAB");
			if (tab != null	&& tab.trim().equalsIgnoreCase(NEDSSConstants.EVENT_TAB_PATIENT_NAME))
				return NEDSSConstants.EVENT_TAB_PATIENT_ORDER;
		}

		return NEDSSConstants.EVENT_TAB_EVENT_ORDER;
	}

	public int getDefaultIntTabOrder() {
		if (isValid()) {
			String tab = properties.getProperty("DEFAULT_INT_TAB");
			if (tab != null	&& tab.trim().equalsIgnoreCase(NEDSSConstants.EVENT_TAB_PATIENT_NAME))
				return NEDSSConstants.EVENT_TAB_PATIENT_ORDER;
		}

		return NEDSSConstants.EVENT_TAB_EVENT_ORDER;
	}

	public int getDefaultMorbTabOrder() {
		if (isValid()) {
			String tab = properties.getProperty("DEFAULT_MORB_TAB");
			if (tab != null	&& tab.trim().equalsIgnoreCase(NEDSSConstants.EVENT_TAB_PATIENT_NAME))
				return NEDSSConstants.EVENT_TAB_PATIENT_ORDER;
		}

		return NEDSSConstants.EVENT_TAB_EVENT_ORDER;
	}

	public int getDefaultInvTabOrder() {
		if (isValid()) {
			String tab = properties.getProperty("DEFAULT_INV_TAB");
			if (tab != null	&& tab.trim().equalsIgnoreCase(NEDSSConstants.EVENT_TAB_PATIENT_NAME))
				return NEDSSConstants.EVENT_TAB_PATIENT_ORDER;
		}

		return NEDSSConstants.EVENT_TAB_EVENT_ORDER;
	}
	
	public String getDefaultRvctTabOrder() {
		if(isValid())
		{
			return properties.getProperty("DEFAULT_RVCT_TAB");
		}else{
			return null;
		}
		
	}

	public String getDefaultVaricellaTabOrder() {
		if(isValid())
		{
			return properties.getProperty("DEFAULT_VARICELLA_TAB");
		}else{
			return null;
		}
		
	}
	/**
	 * @return
	 */
	public boolean isSSO() {
        if(isValid() && properties.getProperty("SSO_AUTHENTICATION") != null
             && properties.getProperty("SSO_AUTHENTICATION").equalsIgnoreCase("true")) {
            return true;
        }
        return false;
	}

	/**
	 * @return
	 */
	public boolean supportPDPIntegration() {
        if(isValid() && properties.getProperty("SUPPORT_PDP_INTEGRATION") != null
             && properties.getProperty("SUPPORT_PDP_INTEGRATION").equalsIgnoreCase("true")) {
            return true;
        }
        return false;
	}

	/**
	 * @return
	 */
	public boolean supportServiceWrapper() {
        if(isValid() && properties.getProperty("SUPPORT_SERVICE_WRAPPER") != null
             && properties.getProperty("SUPPORT_SERVICE_WRAPPER").equalsIgnoreCase("true")) {
            return true;
        }
        return false;
	}

	public boolean isEventBasedSearchDisabled(){
        if(isValid() && properties.getProperty("DISABLE_EVENT_SEARCH") != null
                && properties.getProperty("DISABLE_EVENT_SEARCH").equalsIgnoreCase("YES")) {
               return true;
           }
           return false;

	}

   //	-----LDF Extraction properties Begins

	public String getPublicKeyLDAPDN()
    {
      if(isValid())
      {
        return properties.getProperty("PUBLIC_KEY_LDAP_DN");
      }
      else
      {
           return null;
      }
    }
	public String getPublicKeyLDAPBaseDN()
    {
      if(isValid())
      {
        return properties.getProperty("PUBLIC_KEY_LDAP_BASE_DN");
      }
      else
      {
           return null;
      }
    }
	public String getPublicKeyLDAPAddress()
    {
      if(isValid())
      {
        return properties.getProperty("PUBLIC_KEY_LDAP_ADDRESS");
      }
      else
      {
           return null;
      }
    }
	public String getDestinationFileName()
    {
      if(isValid())
      {
        return properties.getProperty("DESTINATION_FILE_NAME");
      }
      else
      {
           return null;
      }
    }
	public String getRouteInfo()
    {
      if(isValid())
      {
        return properties.getProperty("ROUTE_INFO");
      }
      else
      {
           return null;
      }
    }
	public String getAction()
    {
      if(isValid())
      {
        return properties.getProperty("ACTION");
      }
      else
      {
           return null;
      }
    }
	public String getEncryption()
    {
      if(isValid())
      {
        return properties.getProperty("ENCRYPTION");
      }
      else
      {
           return null;
      }
    }
	public String getSignature()
    {
      if(isValid())
      {
        return properties.getProperty("SIGNATURE");
      }
      else
      {
           return null;
      }
    }
      public String getNBSNNDVersion()
      {
        if(isValid())
        {
          return properties.getProperty("NBS_NND_VERSION");
        }
        else
        {
             return null;
        }
      }
        public String getNBSLDFVersion()
        {
          if(isValid())
          {
            return properties.getProperty("NBS_LDF_VERSION");
          }
          else
          {
               return null;
          }
    }
        public String getSASServerIP()
        {
          if(isValid())
          {
            return properties.getProperty("SASServerIP");
          }
          else
          {
               return null;
          }
    }
        public String getSASServerArchitecture()
        {
          if(isValid())
          {
            return properties.getProperty("SASServerArchitecture");
          }
          else
          {
               return null;
          }
    }
        public String getUserNamePrompt()
        {
          if(isValid())
          {
            return properties.getProperty("USERNAMEPROMPT");
          }
          else
          {
               return null;
          }
    }
        public String getPassWordPrompt()
        {
          if(isValid())
          {
            return properties.getProperty("PASSWORDPROMPT");
          }
          else
          {
               return null;
          }
    }
        public String getUserName()
        {
          if(isValid())
          {
            return properties.getProperty("USERNAME");
          }
          else
          {
               return null;
          }
    }
        public String getPassWord()
        {
          if(isValid())
          {
            return properties.getProperty("PASSWORD");
          }
          else
          {
               return null;
          }
    }
        public String getCommandPrompt()
        {
          if(isValid())
          {
            return properties.getProperty("COMMANDPROMPT");
          }
          else
          {
               return null;
          }
        }
        public String getSasLocation()
        {
          if(isValid())
          {
            return properties.getProperty("SAS_LOCATION");
          }
          else
          {
               return null;
          }
        }
        public String getHIVAidsDisplayInd()
        {
          if(isValid())
          {
            return properties.getProperty("HIV_AIDS_DISPLAY");
          }
          else
          {
               return null;
          }
        }
        public String getRejectedNotificationDaysLimit()
        {
          if(isValid())
          {
            return properties.getProperty("REJECTED_NOTIF_DAYS_IN_QUEUE_LIMIT");
          }
          else
          {
               return null;
          }
        }
		public int getRejectedQueueForNotificationsDisplaySize()
		{
			if(isValid()){
				String sizeStr= properties.getProperty("REJECTED_NOTIF_QUEUE_DISP_SIZE");

				if(sizeStr != null && sizeStr.trim().length() != 0){
					Integer size = new Integer(sizeStr.trim());
					return size.intValue();
				}
				else
					return 20;
			}
				return 20;
    	}
        public String getMyProgramAreaSecurity()
        {
          if(isValid())
          {
            return properties.getProperty("MY_PROGRAM_AREAS_SECURITY");
          }
          else
          {
               return null;
          }
        }
        public String getObservationsNeedingReviewSecurity()
        {
          if(isValid())
          {
            return properties.getProperty("OBSERVATIONS_NEEDING_REVIEW_SECURITY");
          }
          else
          {
               return null;
          }
        }
        
        public String getPersonSearchNameOperatorDefault()
        {
          if(isValid())
          {
            return properties.getProperty("PERSON_SEARCH_NAME_OPERATOR_DEFAULT");
          }
          else
          {
               return null;
          }
        }
        public String getOrganizationSearchNameOperatorDefault()
        {
          if(isValid())
          {
            return properties.getProperty("ORGANIZATION_SEARCH_NAME_OPERATOR_DEFAULT");
          }
          else
          {
               return null;
          }
        }
        public String getIsLoginPageDisabled()
        {
          if(isValid())
          {
            return properties.getProperty("LOGIN_PAGE_DISABLED");
          }
          else
          {
               return null;
          }
        }
        public String getReportAdminSasPgmLocation()
        {
          if(isValid())
          {
            return properties.getProperty("REPORT_ADMIN_SAS_PGM_LOCATION");
          }
          else
          {
               return null;
          }
        }        
        public String getAlertMailHost()
        {
          if(isValid())
          {
            return properties.getProperty("ALERT_MAIL_HOST");
          }
          else
          {
               return null;
          }
        }        
        public String getAlertSmtp()
        {
          if(isValid())
          {
            return properties.getProperty("ALERT_SMTP");
          }
          else
          {
               return null;
          }
        }        
        public String getAlertAdminEmail()
        {
          if(isValid())
          {
            return properties.getProperty("ALERT_ADMIN_EMAIL");
          }
          else
          {
               return null;
          }
        }        
        public String getAlertAdminUser()
        {
          if(isValid())
          {
            return properties.getProperty("ALERT_ADMIN_USER");
          }
          else
          {
               return null;
          }
        }        
        //Property to determine if NND PAM Intermediary Message is written to FILE, TABLE or BOTH
        public String getNNDPamIntermediaryMessageOutput()
        {
          if(isValid())
          {
            return properties.getProperty("NND_PAM_INTERMEDIARY_MESSAGE_OUTPUT");
          }
          else
          {
               return null;
          }
        }
        //Property to determine if PHDC Message is written to FILE, TABLE or BOTH
        public String getPHDCMessageOutput()
        {
          if(isValid())
          {
            return properties.getProperty("PHDC_MESSAGE_OUTPUT");
          }
          else
          {
               return null;
          }
        }
        public String getMsgSendingApplication()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_SENDING_APPLICATION");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgSendingApplicationDesc()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_SENDING_APPLICATION_DESC");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgSendingApplicationOID()
        {
            if(isValid())
            {
               return properties.getProperty("MSG_SENDING_APPLICATION_OID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgSendingFacility()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_SENDING_FACILITY");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgSendingFacilityDesc()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_SENDING_FACILITY_DESC");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgSendingFacilityOID()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_SENDING_FACILITY_OID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgReceivingApplication()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_RECEIVING_APPLICATION");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgReceivingApplicationOID()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_RECEIVING_APPLICATION_OID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgReceivingFacility()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_RECEIVING_FACILITY");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgReceivingFacilityOID()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_RECEIVING_FACILITY_OID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgEntityIdentifier1()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_ENTITY_IDENTIFIER_1");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgNamespaceId1()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_NAMESPACE_ID_1");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgNamespaceId2()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_NAMESPACE_ID_2");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgMsgProfileId1()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_MSG_PROFILE_ID_1");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgMsgProfileId2()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_MSG_PROFILE_ID_2");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgMessageStructure()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_MESSAGE_STRUCTURE");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgProcessingId()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_PROCESSING_ID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgVersionId()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_VERSION_ID");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgNameTypeCode()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_NAME_TYPE_CODE");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId1Identifier()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID1_IDENTIFIER");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId1Text()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID1_TEXT");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId1CodingSystem()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID1_CODING_SYSTEM");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId2Identifier()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID2_IDENTIFIER");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId2Text()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID2_TEXT");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalServiceId2CodingSystem()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_SERVICE_ID2_CODING_SYSTEM");
            }
            else
            {
                 return null;
            }
          }
        public String getMsgUniversalIdType()
        {
            if(isValid())
            {
                return properties.getProperty("MSG_UNIVERSAL_ID_TYPE");        
            }
            else
            {
                 return null;
            }
          }
        
        public String getNBSUserGuideLocation()
        {
          if(isValid())
          {
            return properties.getProperty("NBS_USER_GUIDE_LOCATION");
          }
          else
          {
               return null;
          }
        }
        
        /**
         * Return a collection of RSS feed links defined in the properties file.
         * @return
         */
        public Collection<Object> getHomePageRSSFeedLinks() {
        	
        	ArrayList<Object> links = new ArrayList<Object>();
			if (isValid()) {
        		// State defined feeds
				String csv = properties.getProperty("STATE_RSS_FEEDS_LIST");
				if (csv.trim().length() > 0) {
					String[] tmp = csv.split(",");
					links.addAll(Arrays.asList(tmp));
				}
        		return links;
			} else {
				return null;
			}
        }
        
        

        /**
         * Retrieve the max size of a file in Megabytes (MB) that can be attached to
         * an artifact like contact record, investigation etc...
         * @return
         */
        public int getMaxFileAttachmentSizeInMB()
        {
        	// default is 2MB if it is not defined in the properties file
			int size = 2;
			if (isValid()) {
				String sizeInMB = properties.getProperty("MAX_FILE_ATTACHMENT_SIZE_MB");
				if (sizeInMB.trim().length() > 0) {
					size = Integer.valueOf(sizeInMB).intValue();	
				}
			}
			return size;
        }
        
        /**
         * Get the title for the LDF block on the home page.
		 * 
         * @return
         */
        public String getHomePageLDFDashletTitle()
        {
        	if(isValid())
        	{
        		return properties.getProperty("HOMEPAGE_LDF_DASHLET_TITLE");
        	}
        	else
        	{
        		return null;
        	}
        }
        
        /**
         * Get the title for the LDF block on the home page.
		 * 
         * @return
         */
        public String getStateName()
        {
        	if(isValid())
        	{
        		return properties.getProperty("STATE_NAME");
        	}
        	else
        	{
        		return null;
        	}
        }
        
        /**
         * Create a map of array lists to represent the order of colGroup 
         * (an informational block, for example., chart, reports, queues) on homepage. 
         * Each map entry represents a column on the screen. Items in a column
         * are arranged from top to bottom in that order.
         * 
         * For example, there are 3 columns in this layout. Each column
         * has 2 rows in it.
         *  
         * |Patient Search| My Queues |My Reports | 
         * |  News Feeds  | My Charts |  LDFs  |    
         * 
         * The value of property HOMEPAGE_DASHLET_ORDER to represent the above structure is
         * [PatientSearch,Feeds][MyQueues,MyCharts][MyReports,Notices]
         * 
         * @return
         */
        public Map<String, Collection<String>> getHomePageDashletOrder()
        {
        	Map<String, Collection<String>> cols = new TreeMap<String, Collection<String>>();
        	if(isValid())
        	{
        		String orderStr = properties.getProperty("HOMEPAGE_DASHLET_ORDER");
        		if (orderStr.trim().length() > 0) {
            		String[] colGroups = orderStr.split("]");
            		int colIndex = 0;
            		for (int i = 0; i < colGroups.length; i++) {
            			colGroups[i] = colGroups[i].replace("[","");
            			String[] colItems = colGroups[i].split(",");
            			Collection<String> rows = new ArrayList<String>();
            			for (int k = 0; k < colItems.length; k++) {
            				rows.add(colItems[k].trim());
            			}
            			cols.put(String.valueOf(colIndex++), rows);
            		}
        		}
        		return cols;
        	}
        	else {
        		return null;
        	}
    	}
        
        public boolean getMyProgramAreasQueueSortbyNewestInvStartdate()
        {
            if(isValid())
            {
              if(properties.getProperty("MY_PROGRAM_AREAS_QUEUE_SORTBY_NEWEST_INV_STARTDATE") == null
                 || (properties.getProperty("MY_PROGRAM_AREAS_QUEUE_SORTBY_NEWEST_INV_STARTDATE").equalsIgnoreCase("TRUE") ||
                     properties.getProperty("MY_PROGRAM_AREAS_QUEUE_SORTBY_NEWEST_INV_STARTDATE").trim().equals("")))
              {
                return true;
              }
              else
              {
                return false;
              }
            }
             else
             {
               return false;
             }        	
        	
        }  
        
        public int getPhinVadsMaxCount() {
            if (isValid()) {
              return (Integer.parseInt(properties.getProperty("PHIN_VADS_MAX_PAGE_COUNT")));
            }
            else {
              return 0;
            }
          }
        
        public String getServiceUrlPHINVADSWebService()
        {
          if(isValid())
          {
            return properties.getProperty("SERVICEURL");
          }
          else
          {
               return null;
          }
        } 
        
        public String getServerRestart()
        {
            if(isValid())
          {
            return properties.getProperty("SERVER_RESTART");
          }
          else
          {
              return null;
          }
        }

        public String getELRProviderOrganizationMatch()
        {
           if(isValid())
          {
            return properties.getProperty("ELR_PROVIDER_ORG_MATCH");
          }
          else
          {
              return "Y";
          }
        }
        public String getRelNum()
        {
            if(isValid())
            {
                return properties.getProperty("CODE_BASE");//This comes from: select * from nbs_odse..nbs_configuration where config_key = 'CODE_BASE'
            }
            else
            {
                 return null;
            }
          }
        
      
        public String getSecureNewEncryptionKey()
        {
            if(isValid())
            {
                return properties.getProperty("SECURE_NEW_ENCRYPTION_KEY");
            }
            else
            {
                 return null;
            }
          }

        public String getSecureEncryptionKey()
        {
            if(isValid())
            {
                return properties.getProperty("SECURE_ENCRYPTION_KEY");
            }
            else
            {
                 return null;
            }
          }

        public String getPHCRToCdaPort()
        {
            if(isValid())
            {
                return properties.getProperty("PHCR_TO_CDA_PORT");
            }
            else
            {
                 return null;
            }
          }
        public String getXSSFilterEnabled()
        {
            if(isValid())
            {
                return properties.getProperty("ENABLE_XSS_FILTER");
            }
            else
            {
                 return null;
            }
          }
        public String getSTDProgramAreas()
        {
            if(isValid())
            {
                return properties.getProperty("STD_PROGRAM_AREAS");
            }
            else
            {
                 return null;
            }
          }
        
        
        public String getTBConditionsCodes()
        {
            if(isValid())
            {
                return properties.getProperty("TB_CONDITION_CODES");
            }
            else
            {
                 return null;
            }
          }
        
        
        public String getHIVProgramAreas()
        {
            if(isValid())
            {
                return properties.getProperty("HIV_PROGRAM_AREAS");
            }
            else
            {
                 return null;
            }
          }


        private static void cachedStdProgramArea(){
        	try {
				String delim=",";
				String line=getInstance().getSTDProgramAreas();
				StringTokenizer tokens = new StringTokenizer(line, delim, true);
				String previous = delim;
				while (tokens.hasMoreTokens()) {
				    String token = tokens.nextToken();
				    if (!delim.equals(token)) {
				    	cachedStdList.add(token.toUpperCase().trim());
				    } else if (previous.equals(delim)) {
				    	
				    }
				    previous = token;
				}
				if(cachedStdList.size()==0){
					cachedStdList= null;
				}
			} catch (Exception e) {
				logger.error("Exception thrown by PropertyUtil.cachedStdProgramArea:-" + e );
			}
        }
        
        private static void cachedHivProgramArea(){
        	try {
				String delim=",";
				String line=getInstance().getHIVProgramAreas();
				StringTokenizer tokens = new StringTokenizer(line, delim, true);
				String previous = delim;
				while (tokens.hasMoreTokens()) {
				    String token = tokens.nextToken();
				    if (!delim.equals(token)) {
				    	cachedHivList.add(token.toUpperCase().trim());
				    } else if (previous.equals(delim)) {
				    	
				    }
				    previous = token;
				}
				if(cachedHivList.size()==0){
					cachedHivList= null;
				}
			} catch (Exception e) {
				logger.error("Exception thrown by PropertyUtil.cachedHivProgramArea:-" + e );
			}
        }


        private static void cachedTbConditionCodes(){
        	
        		
	        	try {
					String delim=",";
					String line=getInstance().getTBConditionsCodes();
					StringTokenizer tokens = new StringTokenizer(line, delim, true);
					String previous = delim;
					while (tokens.hasMoreTokens()) {
					    String token = tokens.nextToken();
					    if (!delim.equals(token)) {
					    	cachedTBList.add(token.toUpperCase().trim());
					    } else if (previous.equals(delim)) {
					    	
					    }
					    previous = token;
					}
					if(cachedTBList.size()==0){
						cachedTBList= null;
					}
				} catch (Exception e) {
					logger.error("Exception thrown by PropertyUtil.cachedTbConditionCodes:-" + e );
				}
	        	
        	
        }
        
        
        private static void cachedHivOrStdProgramArea(){
        	if(cachedStdList !=null && cachedStdList.size()==0){
        		cachedStdProgramArea();
        	}
        	if(cachedHivList !=null && cachedHivList.size()==0){
        		cachedHivProgramArea();
        	}
        	if(cachedStdList!=null)
        		cachedStdAndHivList.addAll(cachedStdList);	
        	if(cachedHivList!=null)
        		cachedStdAndHivList.addAll(cachedHivList);	
        }
        
        
        
        
        public static boolean isSTDProgramArea(String pa) {
        	if(getInstance().getSTDProgramAreas()!=null && cachedStdList!=null && cachedStdList.size()==0 ){
        		cachedStdProgramArea();
        	}
        	if(pa==null){
	    		return false;
        	}
        	else if(cachedStdList!=null && cachedStdList.contains(pa.toUpperCase())){
	    		return true;
        	}else{
	    		return false;
        	}
        }
        
 
        public static boolean isHIVProgramArea(String pa) {
        	if(getInstance().getSTDProgramAreas()!=null && cachedHivList!=null && cachedHivList.size()==0 ){
        		cachedHivProgramArea();
        	}
        	if(pa==null){
	    		return false;
        	}
        	else if(cachedHivList!=null && cachedHivList.contains(pa.toUpperCase())){
	    		return true;
        	}else{
	    		return false;
        	}
        }
        public static boolean isStdOrHivProgramArea(String pa) {
        	
        	if(cachedStdAndHivList!=null && cachedStdAndHivList.size()==0){
        		cachedHivOrStdProgramArea();
        	}
        	if(pa==null){
	    		return false;
        	}
        	else if(cachedStdAndHivList!=null && cachedStdAndHivList.contains(pa.toUpperCase())){
	    		return true;
        	}else{
	    		return false;
        	}
        }
        
        public static boolean isTBCode(String conditionCode) {
        	
        	if(cachedTBList!=null && cachedTBList.size()==0){
        		cachedTbConditionCodes();
        	}
        	if(conditionCode==null){
	    		return false;
        	}
        	else if(cachedTBList!=null && cachedTBList.contains(conditionCode)){
	    		return true;
        	}else{
	    		return false;
        	}
        }
 
        public String getPartnerServicesSendingAgency()
        {
            if(isValid())
            {
                return properties.getProperty("PSDATA_SENDING_AGENCY");
            }
            else
            {
                 return null;
            }
          }
        public String getPartnerServicesDefaultAgency()
        {
            if(isValid())
            {
                return properties.getProperty("PSDATA_DEFAULT_AGENCY");
            }
            else
            {
                 return null;
            }
          }
        
        public String getPHDCImpCheckForExistingInv()
        {
            if(isValid())
            {
                return properties.getProperty("PHDC_IMP_CHECK_FOR_EXISTING_INV");
            }
            else
            {
                 return "NO";
            }
          }

//	public String getMaxReportFilterUid() {
//		if (isValid()) {
//			if (properties.getProperty("MAX_REPORT_FILTER_UID") != null)
//				return properties.getProperty("MAX_REPORT_FILTER_UID");
//			else
//				return "15";
//		} else {
//			return "15";
//		}
//	}
	public String getNETSSOutputFileLocation() {
		if (isValid()) {
			if (properties.getProperty("NETSS_OUTPUT_FILE_LOCATION") != null)
				return properties.getProperty("NETSS_OUTPUT_FILE_LOCATION");
			else
				return null;
		} else {
			return null;
		}
	}
	
	public String getAppInfoTestEnv(){
	    if ( isValid() ) {
	    	return ( (String)properties.getProperty("TEST_ENV_SHOW_APP_INFO"));
	    } else {
	    	return "false";
	    }
	}

	/**
	 * Convenience method for getMergeCandidateDefaultSurvivor
	 * @return
	 */
	 public static Boolean isMergeCandidateDefaultSurvivorOldest() {
		 if (getMergeCandidateDefaultSurvivor().equalsIgnoreCase(NEDSSConstants.MERGE_CANDIDATE_DEFAULT_SURVIVOR_OLDEST))
			 return true;
		 else
			 return false;
		 
	 }
	
	/**
	 * If this property isn't there default to oldest survivor
	 * @return
	 */
    public static String getMergeCandidateDefaultSurvivor()
    {
        try
        { 
        	if(properties.getProperty("MERGE_CANDIDATE_DEFAULT_SURVIVOR") != null) {
        		return (properties.getProperty("MERGE_CANDIDATE_DEFAULT_SURVIVOR"));
        	} else {
        		return NEDSSConstants.MERGE_CANDIDATE_DEFAULT_SURVIVOR_OLDEST;
        	}

        } catch(Exception e) {
            logger.error("mergeCandidateDefaultSurvivor property? ", e);
            return NEDSSConstants.MERGE_CANDIDATE_DEFAULT_SURVIVOR_OLDEST;
        }
    }

    /**
     * Retrieve the attachment extensions allowed to be uploaded
     * an artifact like morbidity report etc...
     * @return
     */
    public String getFileAttachmentExtensions()
    {
    	// default is 2MB if it is not defined in the properties file
    	ArrayList<String> allowedExtensions = new ArrayList<String>();
    	
    	String allowedExtensionsStr = null;
		if (isValid()) {
			allowedExtensionsStr = properties.getProperty("ALLOWABLE_ATTACHMENT_EXT_TYPE");
    	}
		
		return allowedExtensionsStr;
    }
    
    public String getPHDCSkipDRRQ()
    {
        if(isValid())
        {
            return properties.getProperty("PHDC_SKIP_DRRQ");
        }
        else
        {
             return "Y";
        }
      }
    
    public String getIncludeSusceptibilitiesInLabNND()
    {
        if(isValid())
        {
            return properties.getProperty("INCLUDE_SUSCEPTIBILITIES_FOR_LAB_NND");
        }
        else
        {
             return "Y";
        }
      }
    public String getCaseAnswerHistoryCountVariationForRollback(){
    	if (isValid()) {
    		return properties.getProperty(NEDSSConstants.ANSWER_COUNT_DIFF_FOR_ROLLBACK);
        }else {
        	     return NEDSSConstants.NOT_APPLICABLE;
        }
    }
    public String getQueryImmunizationRegistry()
    {
        if(isValid())
        {
            return properties.getProperty("QUERY_IMMUNIZATION_REGISTRY");
        }
        else
        {
             return "F";
        }
      }
    
    public String getIISSourceType()
    {
        if(isValid())
        {
            return properties.getProperty("IIS_SOURCE_TYPE");
        }
        else
        {
             return "F";
        }
    }
    
    public String getIISLocalPath()
    {
        if(isValid())
        {
            return properties.getProperty("IIS_LOCAL_PATH");
        }
        else
        {
             return "F";
        }
    }
    
    
    /**
     * It used to populate RCP-2.1, maximum number of records returns from IIS for Patient search.
     * 
     * @return
     */
    public String getIISPatientSearchMaxQuantity(){
    	if (isValid()) {
    		return properties.getProperty("IIS_PATIENT_SEARCH_MAX_QUANTITY");
        }else {
        	return "20";
        }
    }
    
    
    /**
     * It used to populate RCP-2.1, maximum number of records returns from IIS for Vaccination search.
     * 
     * @return
     */
    public String getIISVaccinationSearchMaxQuantity(){
    	if (isValid()) {
    		return properties.getProperty("IIS_VACCINATION_SEARCH_MAX_QUANTITY");
        }else {
        	return "100";
        }
    }
    
    
    /**
     * It used to populate RCP-2.1, maximum number of records returns from IIS for Vaccination search.
     * 
     * @return
     */
    public String getSasVersion(){
    	if (isValid()) {
    		return properties.getProperty(NEDSSConstants.SAS_VERSION);
        }else {
        	     return NEDSSConstants.SAS_VERSION9_3;
        }
    }
    
    public String getDisableSubmitBeforePageLoadsFlag(){
    	if (isValid()) {
    		return properties.getProperty(NEDSSConstants.DISABLE_SUBMIT_BEFORE_PAGE_LOAD);
        }else {
        	     return NEDSSConstants.TRUE;
        }
    }
    
	/* COVID Related properties */
	public String getCOVIDCRUpdate() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE);
		} else {
			return NEDSSConstants.FALSE;
		}
	}

	public String getCOVIDCRUpdateSendingSystem() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_SENDING_SYSTEM);
		} else {
			return null;
		}
	}

	public String getCOVIDCRUpdateIgnore() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_IGNORE);
		} else {
			return null;
		}
	}

	public String getCOVIDCRUpdateTimeframe() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_TIMEFRAME);
		} else {
			return null;
		}
	}

	public String getCOVIDCRUpdateClosed() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_CLOSED);
		} else {
			return null;
		}
	}

	public String getCOVIDCRUpdateMultiClosed() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_MULTI_CLOSED);
		} else {
			return null;
		}
	}

	public String getCOVIDCRUpdateMultiOpen() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.COVID_CR_UPDATE_MULTI_OPEN);
		} else {
			return null;
		}
	}
    
	public String getBackYearsForELRMatching() {
		if (isValid()) {
			return properties.getProperty(NEDSSConstants.GOBACK_YEARS_ELR_MATCHING);
		} else {
			return null;
		}
	}
	
	public String getHTMLEncodingEnabled()
    {
        if(isValid())
        {
            return properties.getProperty("ENABLE_HTML_ENCODING");
        }
        else
        {
             return null;
        }
      }
	
	public String getRefererHeaderCheck()
    {
        if(isValid())
        {
            return properties.getProperty("ENABLE_REFERER_HEADER_CHECK");
        }
        else
        {
             return null;
        }
      }
	
	public String getValidRefererHeaderList()
    {
        if(isValid())
        {
            return properties.getProperty("REFERER_HEADER_LIST");
        }
        else
        {
             return null;
        }
      }
	
	public String getEnableELRAlert()
    {
        if(isValid())
        {
            return properties.getProperty("ENABLE_ELR_ALERT");
        }
        else
        {
             return null;
        }
      }
}
