package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

public class PHCRImporter {
	static final LogUtils logger = new LogUtils(PHCRImporter.class.getName());
	public static void main (String args[]){
		try {
			if(args.length !=1){
				System.out.println("The user is a required field");
				System.out.println("Usage :  PHCRImporter.bat [USER_ID]"); 
				System.exit(-1); 
			}
			String userName = args[0];
			caseImporter(userName);
		} catch (Exception e) {
			System.out.println("PHCRImporter.main Method Exception thrown "+ e.getMessage());
			e.printStackTrace();
		}
	}	
	public static void nbsCaseImportSchedular (String userName){
		try {
			caseImporter(userName);
		} catch (Exception e) {
			System.out.println("PHCRImporter.main Method Exception thrown "+ e.getMessage());
			e.printStackTrace();
		}
	}	

	public static void getImportMessageColl(NBSSecurityObj nbsSecurityObj){
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			NbsDocumentHome docHome = (NbsDocumentHome) PortableRemoteObject.narrow(theLookedUpObject, NbsDocumentHome.class);
			NbsDocument nbsDocument= docHome.create();
			Collection<Object> newMessageColl=nbsDocument.extractNBSInterfaceUids(nbsSecurityObj);
			System.out.println("Import process started.");
			if(newMessageColl!=null )
				System.out.println("NBS Application found "+ newMessageColl.size()+ " document(s) to be imported into NBS.");
			int successCounter=0;
			int failureCounter=0;
			
				
			if(newMessageColl!=null){
				Iterator<Object> it = newMessageColl.iterator();
				while(it.hasNext()){
					java.util.Date dateTime = new java.util.Date();
					Timestamp time = new Timestamp(dateTime.getTime());
					 NbsInterfaceDT nbsInterfaceDT  = (NbsInterfaceDT)it.next();
					try {
						nbsDocument.createNBSDocument(nbsInterfaceDT, nbsSecurityObj);
						System.out.println("NBS_Interface_uid  "+nbsInterfaceDT.getNbsInterfaceUid()+" imported successfully.");
						nbsInterfaceDT=nbsDocument.extractXMLDocument(nbsInterfaceDT.getNbsInterfaceUid(), nbsSecurityObj);
						successCounter++;
					} catch (Exception e) {
						System.out.println("NBS_Interface_uid "+nbsInterfaceDT.getNbsInterfaceUid()+" could not be imported successfully.");
						System.out.println("PHCRImporter.getImportMessageCol:Exception thrown "+ e.getMessage());
						nbsInterfaceDT.setRecordStatusCd(NEDSSConstants.EDX_ACTIVITY_FAIL);
						nbsInterfaceDT.setRecordStatusTime(time);

						EDXActivityLogDT  eDXActivityLogDT = new EDXActivityLogDT();
						eDXActivityLogDT.setSourceUid(nbsInterfaceDT.getNbsInterfaceUid());
						eDXActivityLogDT.setImpExpIndCd("I");
						eDXActivityLogDT.setRecordStatusTime(time);
						eDXActivityLogDT.setSourceTypeCd("INT");
						eDXActivityLogDT.setTargetTypeCd("DOC");
						eDXActivityLogDT.setTargetUid(nbsInterfaceDT.getNbsDocumentUid());
						eDXActivityLogDT.setException(e.toString());
						eDXActivityLogDT.setRecordStatusCd(NEDSSConstants.EDX_ACTIVITY_FAIL);
						eDXActivityLogDT.setDocType(NEDSSConstants.PHC_236);
						nbsDocument.createEDXActivityLog(nbsInterfaceDT,  eDXActivityLogDT,nbsSecurityObj);
						failureCounter++;
						//e.printStackTrace();
					}
				}
			}
			if(successCounter>0)
				System.out.println(successCounter + " case(s) imported successfully.");
			if(failureCounter>0)
				System.out.println(failureCounter + " case(s) could not be imported. Please check EDX_activity_log table for details.");
			
		} catch (NEDSSSystemException e) {
			System.out.println("PHCRImporter.getImportMessageColl NEDSSSystemException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (ClassCastException e) {
			System.out.println("PHCRImporter.getImportMessageColl ClassCastException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("PHCRImporter.getImportMessageColl RemoteException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (CreateException e) {
			System.out.println("PHCRImporter.getImportMessageColl CreateException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("PHCRImporter.getImportMessageColl NEDSSSystemException thrown "+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private static void caseImporter(String userName){
		try{
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			//System.out.println("msCommand = " + msCommand.getClass());

			//System.out.println("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			NBSSecurityObj nbsSecurityObj = msCommand.nbsSecurityLogin( userName, userName);
			getImportMessageColl(nbsSecurityObj);
		} catch (NEDSSSystemException e) {
		System.out.println("PHCRImporter.caseImporter Method NEDSSSystemException thrown "+ e.getMessage());
		logger.error("PHCRImporter.caseImporter Method NEDSSSystemException thrown "+ e.getMessage());
		e.printStackTrace();
	} catch (ClassCastException e) {
		System.out.println("PHCRImporter.caseImporter Method ClassCastException thrown "+ e.getMessage());
		logger.error("PHCRImporter.caseImporter Method ClassCastException thrown "+ e.getMessage());
		e.printStackTrace();
	} catch (RemoteException e) {
		System.out.println("PHCRImporter.caseImporter Method RemoteException thrown "+ e.getMessage());
		logger.error("PHCRImporter.caseImporter Method RemoteException thrown "+ e.getMessage());
		e.printStackTrace();
	} catch (CreateException e) {
		System.out.println("PHCRImporter.caseImporter Method CreateException thrown "+ e.getMessage());
		logger.error("PHCRImporter.caseImporter Method CreateException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (NEDSSAppException e) {
		System.out.println("PHCRImporter.caseImporter Method NEDSSAppException thrown "+ e.getMessage());
		logger.error("PHCRImporter.caseImporter Method NEDSSAppException thrown "+ e.getMessage());
		e.printStackTrace();
	}
	}
	
}
