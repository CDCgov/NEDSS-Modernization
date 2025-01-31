package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.vo.NBSInterfaceVO;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.rmi.PortableRemoteObject;
import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;



public class EdxImporter {
	static final LogUtils logger = new LogUtils(EdxImporter.class.getName());

	public static void main(String[] args)
	{
		System.out.println("EdxImporter batch process  started");
		
		//TODO correct this 
		try
		{
			String isPreRun = null;
			String userName = null;
			//Check for the user input
			 
			if(args.length == 2){
				userName = args[0];
				isPreRun = args[1];
			} else {
				System.out.println("Usage :  EdxImporter [PAM_IMPORTER_USER_ID] [production/prerun]"); 
				System.exit(-1); 
			}
			
			if(isPreRun != null){
				if(isPreRun.equalsIgnoreCase("production") || 	isPreRun.equalsIgnoreCase("prerun")){
					//ok
					logger.debug("isPreRun value is:"+isPreRun);
				}
			}else {
				System.out.println("Usage :  EdxImporter.bat [PAM_IMPORTER_USER_ID] [production/prerun]"); 
				System.exit(-1); 
			}
			 String path="C:\\wildfly-10.0.0.Final\\nedssdomain\\Nedss\\tempPHCRMessages\\Sample PHDC messages.xml";
			 String XmlAsString=convertFileToString(path);
			 retrieveNBSDocumentMetadataDT(XmlAsString);
			Boolean isPrerunBoolean = new Boolean(false);
			if(isPreRun.equalsIgnoreCase("production")){
				isPrerunBoolean= new Boolean(false);
				pamPHCRConverter(isPrerunBoolean, userName, XmlAsString);
			}
			else if(isPreRun.equalsIgnoreCase("prerun")){
				isPrerunBoolean= new Boolean(true);
				checkErrors( isPrerunBoolean,userName, XmlAsString);
			}
			else{
				System.out.println("EdxImporter failed:Usage: EdxImporter.bat [PAM_IMPORTER_USER_ID] [production/prerun] ");
				throw new Exception("Batch file Usage: EdxImporter.bat [PAM_IMPORTER_USER_ID] [production/prerun]");
			}
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("EdxImporter Complete!!!");
	} 
	
	
	public static void pamPHCRConverter(Boolean isPrerun,String userName, String XmlAsString){
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		//Create a MainSessionCommand bean instance
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = JNDINames.EDX_PHCR_DOCUMENT_EJB;
			String sMethod = "importPHCRDocument";
			Object[] oParams = new Object[] {isPrerun , XmlAsString};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	        System.out.println("Success with creating patient");
			NBSInterfaceVO nBSInterfaceVO= (NBSInterfaceVO)arr.get(0);
			
			String sBeanJndiName2 = JNDINames.EDX_PHCR_DOCUMENT_EJB;
			String sMethod2 = "createInvestigation";
			Object[] oParams2 = new Object[] {nBSInterfaceVO};
			ArrayList<?> arr2 =msCommand.processRequest(sBeanJndiName2, sMethod2, oParams2);
			Long PHClongValue= (Long)arr2.get(0);
	        System.out.println("PHC case created! with PHC Uid:-"+PHClongValue);
	        nBSInterfaceVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(PHClongValue);
	        String sBeanJndiName3 = JNDINames.EDX_PHCR_DOCUMENT_EJB;
			String sMethod3 = "sendNotification";
			Object[] oParams3 = new Object[] {nBSInterfaceVO.getPublicHealthCaseVO()};
			ArrayList<?> arr3 =msCommand.processRequest(sBeanJndiName3, sMethod3, oParams3);
			Long notificationUid= (Long)arr3.get(0);
			System.out.println("Notification created for PHC! notificationUid:-"+notificationUid);
			
	        
		} catch (Exception e) {
			System.out.println("ERROR raised"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static HashMap<?,?> checkErrors(Boolean isPrerun,String userName, String XmlAsString){
		HashMap<?,?> returnMap = new HashMap<Object,Object>();
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = JNDINames.EDX_PHCR_DOCUMENT_EJB;
			String sMethod = "importPHCRDocument";
			Object[] oParams = new Object[] {isPrerun , XmlAsString};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnMap = (HashMap<?,?>)arr.get(0);
	        
	        if(returnMap.get("ERROR")!=null){
	        	String errrorCode= returnMap.get("ERROR").toString();
	        	System.out.println(errrorCode);
	        }
	 	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMap;
	}
	
	private static String convertFileToString(String path) throws java.io.IOException{
		 byte[] buffer = new byte[(int) new File(path).length()];
		    BufferedInputStream f = null;
		    try {
		        f = new BufferedInputStream(new FileInputStream(path));
		        f.read(buffer);
		    }catch(Exception e){
		    	System.out.println("Please check path and other settings! Defined path is :"+path);
		    }
		    finally {
		        if (f != null) try { f.close(); } catch (IOException ignored) { }
		    }
		    return new String(buffer);

    }


	public static void  retrieveNBSDocumentMetadataDT(String xml) {
		try{
			XmlObject xobj = XmlObject.Factory.parse(xml);
			XmlCursor cursor = xobj.newCursor();
			String schemaLocation="";
			
			if (cursor.toFirstChild()) {
				schemaLocation = cursor.getAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"));
			}
			
			cursor.dispose();
	} catch (XmlException e) {
			System.out.println("Error in retrieveXMLSchemaLocation() "+e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}
	
	
}
