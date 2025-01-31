package gov.cdc.nedss.systemservice.ejb.dbauthejb.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.rmi.PortableRemoteObject;

public class NBSConfigurationUtil {
	static final LogUtils logger = new LogUtils(NBSConfigurationUtil.class.getName());
	/**
	 * This is a utility to interact with the NBS_ODSE.NBS_configuration table.
	 * @param args
	 */
	public static void main(String[] args) {
		String requestType = "updateFromProperty";
		String userName = null;
		String preProd = "prerun"; //default to prerun

		/*
		System.out.println("Args.length = " + args.length);
		for ( int i = 0; i < args.length; i++ )      
	   	    System.out.println( "argument " + i + "=" + args[i] );
		*/
	    String nedssDir = new StringBuffer(System.getProperty("nbs.dir"))
							.append(File.separator)
									.toString()
										.intern();

	    String propertiesDir = new StringBuffer(nedssDir)
				.append("Properties")
					.append(File.separator)
						.toString()
							.intern();
	    
	    String propertyFilePath= propertiesDir + "NEDSS.properties";
    	
		if(args.length>0){
			userName = args[0];
			
		} else {
			System.out.println("This utility updates the NBS_Configuration table from a property file."); 
			System.out.println("Usage :  NBSConfigurationUtil.bat <USER_ID> [production/prerun] [optional property file name]");
			System.out.println("If prerun/production not entered, prerun is assumed.");
			System.out.println("If optional file name not entered, " +propertyFilePath +" is assumed.");
			System.out.println("If file name present is assumed to be in "+propertiesDir+" directory.");
			System.exit(-1); 
		}
		
		
		if (args.length>1)
			preProd = args[1];
		if(preProd == null || (!preProd.equalsIgnoreCase("prerun") && !preProd.equalsIgnoreCase("production"))){
			System.out.println("This utility updates the NBS_Configuration table from a property file."); 
			System.out.println("Second parameter must be prerun or production)");
			System.out.println("Usage :  NBSConfigurationUtil.bat [USER_ID] [updateFromProperty] [production/prerun] [optional property file name]");  
			System.exit(-1); 
		}
		
		
		if (args.length>2) {
			String enteredFileName = args[2];
			if (enteredFileName.equals("recreate")) { //backdoor to get dumg of configuration
				Map<String,ArrayList<String>>  returnMsgs = recreateNBSPropertiesFileFromCurrentNBSConfiguration(userName, preProd);
				displayReturnMessages(returnMsgs);
				System.exit(0); 
			}
			propertyFilePath = propertiesDir + enteredFileName;
			File f = new File(propertyFilePath);
			if(!f.exists() || f.isDirectory()) {
				System.out.println("The fourth parameter must be an existing properties file to load values into the NBS_configuration table");
				if (f.isDirectory())
					System.out.println("The file " +propertyFilePath+" is a directory. Please specify the file.");
				else
					System.out.println("The file " +propertyFilePath+" was not found. Check the file name and try again");
			}
			
		}
		
		//get the Current Configuration 
		Map<String,ArrayList<String>>  returnMsgs = updateCurrentConfigurationFromProperty(userName, propertyFilePath, preProd);
		if (returnMsgs != null) {
			System.out.println("Update of configuration completed with the following result(s):");
			if (!preProd.equalsIgnoreCase("production")) 
				System.out.println("---->Note: Since 'production' not specified, no actual updates were attempted.<-----");
			displayReturnMessages(returnMsgs);
		}
	}

	private static void displayReturnMessages(Map<String,ArrayList<String>>  returnMsgs) {
		if (returnMsgs.containsKey("errorList")) {
			ArrayList<String> errorList = returnMsgs.get("errorList");

			if (!errorList.isEmpty()) {
				Iterator errIter = errorList.iterator();
				System.out.println("Configuration utility request returned with the following errors:");
				while (errIter.hasNext())
					System.out.println("  "+errIter.next());
			} else
				System.out.println("Configuration utility request completed without error.");

		}
		if (returnMsgs.containsKey("updatedItemList")) {
			ArrayList<String> updateList = returnMsgs.get("updatedItemList");
			Iterator updIter = updateList.iterator();
			System.out.println("The following configuration items were updated from the property file:");
			while (updIter.hasNext())
				System.out.println("  "+updIter.next());
		}
		if (returnMsgs.containsKey("insertedItemList")) {
			ArrayList<String> insertedList = returnMsgs.get("insertedItemList");
			Iterator insertedIter = insertedList.iterator();
			System.out.println("The following items were present in the property file but not in the current NBS_configuration and were inserted:");
			while (insertedIter.hasNext())
				System.out.println("  "+insertedIter.next());
		}
	}

/**
 * Call the EJB to get Key/Value map of the current NBS_configuration table.
 * Note: This is not actually used.
 * @param userName
 * @return
 */
private static HashMap<String,String> getCurrentConfiguration(String userName) {
	HashMap<String,String> returnMap = new HashMap<String,String>();
	NedssUtils nedssUtils = new NedssUtils();
	String sBeanName = JNDINames.MAIN_CONTROL_EJB;;
	Object objref = nedssUtils.lookupBean(sBeanName);

	try {
		MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
		MainSessionCommand msCommand = home.create();

		logger.info("msCommand = " + msCommand.getClass());
		logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

		logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
		System.out.println("Successful Login");
		System.out.println("Calling to retrieve current configuration..");
		String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
		String sMethod = "getConfigList";
		Object[] oParams = new Object[] {};
		ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		returnMap = (HashMap<String,String>)arr.get(0);
        
        if(returnMap.get("ERROR")!=null){
        	String errrorCode= returnMap.get("ERROR").toString();
        	System.out.println(errrorCode);
        }
 	} catch (Exception e) {
 		logger.error("Exception calling getConfigList is " +e.getMessage());
 		System.out.println("Exception calling getConfigList is " +e.getMessage());
		e.printStackTrace();
	}
	return returnMap;
}

/**
 * Update the current configuration from the specified property file
 * @param userName
 * @param thePropertyFilePath
 * @param preProd - prerun or production
 * @return
 */
private static Map<String,ArrayList<String>> updateCurrentConfigurationFromProperty(
		String userName, String thePropertyFilePath, String preProd) {
	
	Map<String,ArrayList<String>> returnMap = new HashMap<String,ArrayList<String>>();
	ArrayList<String> errArrayList = new ArrayList<String>();
	NedssUtils nedssUtils = new NedssUtils();
	String sBeanName = JNDINames.MAIN_CONTROL_EJB;;
	Object objref = nedssUtils.lookupBean(sBeanName);
	System.out.println("Properties being read from "+thePropertyFilePath);
	
	//read the property file into properties
	FileInputStream myFile = null;
    Properties properties = new Properties();
    try {
			myFile = new FileInputStream(thePropertyFilePath) ;
			if (myFile != null)   
				properties.load(myFile);
			else {
				System.out.println("Error: Unable to read Properties from file!!");
				System.out.println("  Property file path and name are: "+thePropertyFilePath);
				logger.error("Error: Unable to read Properties from file!!");
				errArrayList.add("Error: Unable to read Properties from file. Check file name "+thePropertyFilePath);
				returnMap.put("errorList", errArrayList);
				return(returnMap); //can't continue
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file "+ thePropertyFilePath + " was not found. Correct the file name and try again.");
			errArrayList.add("Error: Properties file not found. Check file name "+thePropertyFilePath);
			returnMap.put("errorList", errArrayList);
			return(returnMap); //can't continue
		} catch (IOException e) {
			System.out.println("Unexpected I/O error trying to read  " + thePropertyFilePath);
			e.printStackTrace();
			errArrayList.add("Error: Unexpected I/O error trying to read Properties from file "+thePropertyFilePath);
			returnMap.put("errorList", errArrayList);
			return(returnMap); //can't continue
		}
    	

	try {
		MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
		MainSessionCommand msCommand = home.create();

		logger.info("msCommand = " + msCommand.getClass());
		logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
		
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

		logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
		System.out.println("Successful Login");
		System.out.println("Calling to update current configuration from property file..");
		String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
		String sMethod = "updateConfigListFromProperties";
		Object[] oParams = new Object[] {properties, preProd};
		ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		returnMap = (Map<String,ArrayList<String>>)arr.get(0);
        
 	} catch (Exception e) {
 		logger.error("Exception calling updateConfigListFromProperties is " +e.getMessage());
 		System.out.println("Exception calling updateConfigListFromProperties is " +e.getMessage());
		e.printStackTrace();
	}
	return returnMap;
}

/**
 * Recreate the nedss.properties file from the configuration. Note: The old file is backed up to
 * NEDSS_properties.yyyyMMdd
 * @param userName
 * @param preProd
 * @return
 */
private static Map<String, ArrayList<String>> recreateNBSPropertiesFileFromCurrentNBSConfiguration(
		String userName, String preProd) {
	Map<String,ArrayList<String>> returnMap = new HashMap<String,ArrayList<String>>();
	NedssUtils nedssUtils = new NedssUtils();
	String sBeanName = JNDINames.MAIN_CONTROL_EJB;;
	Object objref = nedssUtils.lookupBean(sBeanName);

	try {
		MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
		MainSessionCommand msCommand = home.create();

		logger.info("msCommand = " + msCommand.getClass());
		logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

		logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
		System.out.println("Successful Login");
		System.out.println("Calling to recreate NEDSS.properties file.");
		String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
		String sMethod = "recreateNBSProperiesFileFromConfiguration";
		Object[] oParams = new Object[] {preProd};
		ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		returnMap = (Map<String,ArrayList<String>>)arr.get(0);
        
 	} catch (Exception e) {
 		logger.error("Exception calling updateConfigListFromProperties is " +e.getMessage());
 		System.out.println("Exception calling recreateNBSProperiesFileFromConfiguration is " +e.getMessage());
		e.printStackTrace();
	}
	return returnMap;
}

}//class
