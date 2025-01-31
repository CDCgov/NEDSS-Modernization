package gov.cdc.nedss.alert.admin;

import gov.cdc.nedss.alert.ejb.alertejb.AlertHome;
import gov.cdc.nedss.alert.vo.AlertEmailMessageVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.rmi.PortableRemoteObject;


public class AlertMailler {
	  //For logging
	  static final LogUtils logger = new LogUtils(AlertMailler.class.getName());
	  static boolean isProcessRuning = false;

	
	public static void main(String[] args)
	{
		System.out.println("Sending Email Alert batch process  started");
		try
		{
			String populateFlag = null;
			String userName = null;
			
			//Check for the user input
			 
			if(args.length == 2){
				userName = args[0];
				populateFlag = args[1];
			} else {
				System.out.println("Usage: SendAlertEMail.bat[ALERT_ADMIN_USER_ID]");
				System.exit(-1); 
			}
			
			if(populateFlag != null){
				if(	!populateFlag.equalsIgnoreCase("ALERT_ADMIN_USER_ID")){
						System.out.println("Usage: SendAlertEMail.bat [ALERT_ADMIN_USER_ID]"); 
						System.exit(-1); 
					}
			}else {
				System.out.println("Usage: SendAlertEMail.bat [ALERT_ADMIN_USER_ID]"); 
				System.exit(-1); 
			}
			resetQueue(userName);
			getAlertMessageCollection(userName);
		  }
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("Email Sent Process Complete!!!");
	}
	
	public static void getAlertMessageCollection(String userName) {
		if(isProcessRuning){
			return;
		}
		
		isProcessRuning= true;
		ArrayList<?> alertEmailMessageColl = new ArrayList<Object> ();
		try {
			
			System.out.println("EmailAlert process will  try and obtain security credentials of user :" +userName);
			
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
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin(
									 userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +
					nbsSecurityObj);

			//Forward the request to NBSAuth bean

			String sBeanJndiName = JNDINames.ALERT_EJB;
			String sMethod = "getAlertMessageCollection";
			Object[] oParams = {};

			  ArrayList<?>  arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				System.out.println("getAlertMessageCollection  process : AlertEJB data obtained");
				alertEmailMessageColl = (ArrayList<?>)arr.get(0);
			   if(alertEmailMessageColl!=null){
					Iterator<?> it = alertEmailMessageColl.iterator();
					while(it.hasNext()){
						AlertEmailMessageVO alertEmailMessageVO =(AlertEmailMessageVO)it.next();
						String sendMessageMethod = "sendAndLogMessage";
						Object[] oParam = {alertEmailMessageVO};
						msCommand.processRequest(sBeanJndiName, sendMessageMethod, oParam);
					}
				}
			
		}  catch (Exception e) {
			isProcessRuning= false;
			System.out.println("ERROR: Please check Alert Log table for detals"+ e.getMessage());
		}
		isProcessRuning= false;
		
	}
	
	public static void resetQueue(String userName){
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		
		//Create a MainSessionCommand bean instance
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(
												  objref,
												  MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info(
					"About to call nbsSecurityLogin with msCommand; VAL IS: " +
					msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin(
									 userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +
					nbsSecurityObj);
			String sBeanJndiName = JNDINames.ALERT_EJB;
			String sMethod = "resetQueue";
			Object[] oParams = {};
			ArrayList<?>  arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
