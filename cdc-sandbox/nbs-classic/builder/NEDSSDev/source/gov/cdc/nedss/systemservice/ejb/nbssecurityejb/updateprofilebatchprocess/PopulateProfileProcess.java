package gov.cdc.nedss.systemservice.ejb.nbssecurityejb.updateprofilebatchprocess;


/**
 * Title:        PopulateProfileProcess
 * Description:  PopulateProfileProcess is a batch process that populates the
 * 				user profile information to ODS or HTML or both depending on the
 * 				user input. 
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Sree Chadalavada
 */
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NbsDbPropertyUtil;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import javax.rmi.PortableRemoteObject;


public class PopulateProfileProcess
{

	// Create a logger
	static final LogUtils logger = new LogUtils(PopulateProfileProcess.class.getName());
	static final PropertyUtil properties = PropertyUtil.getInstance();

	public PopulateProfileProcess()
	{
	}

	public static void main(String[] args)
	{
		try
		{
			String populateFlag = null;
			String userName = null;
			
			//Check for the user input
			 
			if(args.length == 2){
				userName = args[0];
				populateFlag = args[1];
			} else {
				System.out.println("Usage: UserProfileUpdateProcess.bat [HTML]/[ODS]/[ODS_AND_HTML]");
				System.exit(-1); 
			}
			
			if(populateFlag != null){
				if(!populateFlag.equalsIgnoreCase("HTML")&&
					!populateFlag.equalsIgnoreCase("ODS")&&
					!populateFlag.equalsIgnoreCase("ODS_AND_HTML")){
						System.out.println("Usage: UserProfileUpdateProcess.bat [HTML]/[ODS]/[ODS_AND_HTML]"); 
						System.exit(-1); 
					}
			}else {
				System.out.println("Usage: UserProfileUpdateProcess.bat [HTML]/[ODS]/[ODS_AND_HTML]"); 
				System.exit(-1); 
			}
			
			NBSSecurityObj nbsSecurityObj = null;
			NedssUtils nedssUtils = new NedssUtils();
			
			//Create a MainSessionCommand bean instance
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);

			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(
												  objref,
												  MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info(
					"About to call nbsSecurityLogin with msCommand; VAL IS: " +
					msCommand);
			
			//Use msa as the user to login.
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin(
									 userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +
					nbsSecurityObj);

			String	sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
			String sMethod = "populateUserProfiles";
			Object[] oParams = { populateFlag};

			msCommand.processRequest(sBeanJndiName, sMethod, oParams);

		  }
		catch (Exception e3)
		{
			e3.printStackTrace();
			logger.fatal("Error: " + e3.getMessage());
		}

		System.out.println("User Profile Update Process Complete!!!");
	}
	public static void userProfileProcess(String populateFlag, String userName)
	{
		try
		{
			
			NBSSecurityObj nbsSecurityObj = null;
			NedssUtils nedssUtils = new NedssUtils();
			
			//Create a MainSessionCommand bean instance
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);

			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(
												  objref,
												  MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info(
					"About to call nbsSecurityLogin with msCommand; VAL IS: " +
					msCommand);
			
			//Use msa as the user to login.
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin(
									 userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +
					nbsSecurityObj);

			//Forward the request to NBSAuth bean

			String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
			String sMethod = "populateUserProfiles";
			Object[] oParams = { populateFlag};

			msCommand.processRequest(sBeanJndiName, sMethod, oParams);

		  }
		catch (Exception e3)
		{
			e3.printStackTrace();
			logger.fatal("Error: " + e3.getMessage());
		}

		System.out.println("User Profile Update Process Complete!!!");
	}
}