package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import java.util.ArrayList;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.util.CaseNotificationUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

public class EdxEntityMatchHashcodeBatchUtil {
	
	static final LogUtils logger = new LogUtils(CaseNotificationUtil.class
			.getName());	
	
	public static void main(String[] args)
	{
		System.out.println("EdxEntityMatchHashcodeBatchUtil batch process  started.  Please wait");
		try
		{
			String runProcess = null;
			String userName = null;
			if(args.length == 1){
				userName = args[0];
				System.out.println(userName);
			} else {
				System.out.println("Usage :  EdxEntityMatchHashcodeBatchUtil  [run]"); 
				System.exit(-1); 
			}
			
			generateHashCdforEntities(userName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("EdxEntityMatchHashcodeBatchUtil Complete!!!");
	} 
	
	private static void generateHashCdforEntities(String userName){
		
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);	
		
		
		try {
			
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = JNDINames.EDX_PHCR_DOCUMENT_EJB;
			String sMethod = "getEntityHashCd";
			Object[] oParams = new Object[] {};
		

		ArrayList<?>  arr  =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		  
		  
	} catch (Exception  e) {
		e.printStackTrace();
		System.out.println("Moved all the entities to EdxEntityMatch table:  "+e.getMessage() );
	}
		
	}

}
