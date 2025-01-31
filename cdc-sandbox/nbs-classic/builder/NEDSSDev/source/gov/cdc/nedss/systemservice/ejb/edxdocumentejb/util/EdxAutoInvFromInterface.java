package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithm;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmHome;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocument;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentHome;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.genericXMLParser.MsgXMLMappingDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

/**
 * @author Pradeep Sharma
 * @since 2010 Version NBS_AUTO_CREATE
 */
public class EdxAutoInvFromInterface {
	static final LogUtils logger = new LogUtils(EdxAutoInvFromInterface.class.getName());
	static final Map<String, ArrayList<MsgXMLMappingDT>> XMLMapping = new HashMap<String, ArrayList<MsgXMLMappingDT>>();
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				System.out.println("The user is a required field");
				System.out.println("Usage :  PHCRImporter.bat [USER_ID]   OR  PHCRImporter.bat [USER_ID] [DATAMIGRATION]");
				System.exit(-1);
			}
			String datamigration= null;
			String userName = args[0];
			
			if(args.length==2){
			datamigration= args[1];
				if(datamigration!=null && !datamigration.equalsIgnoreCase("DATAMIGRATION")){
					System.out.println("Usage :  PHCRImporter.bat [USER_ID] /n PHCRImporter.bat [USER_ID] [DATAMIGRATION]");
					System.out.println("/n Note: DATAMIGRATION parameter is optional.");
					System.out.println("Usage :  DATAMIGRATION may be spelled incorrectly. Please check.");
				}
			}
			//get and process the collection
			getSecurityObjAndStartRun(userName, datamigration);
		} catch (Exception e) {
			System.out.println("EdxAutoInvFromInterface.main Method Exception thrown " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void nbsCaseImportScheduler(String userName) {
		try {
			getSecurityObjAndStartRun(userName, null);
		} catch (Exception e) {
			System.out.println("EdxAutoInvFromInterface.nbsCaseImportScheduler Method Exception thrown " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void prcoessMessageColl(String datamigration, NBSSecurityObj nbsSecurityObj) {
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			NbsDocumentHome docHome = (NbsDocumentHome) PortableRemoteObject.narrow(theLookedUpObject, NbsDocumentHome.class);
			NbsDocument nbsDocument = docHome.create();
			Collection<Object> newMessageColl = nbsDocument.extractNBSInterfaceUids(nbsSecurityObj);

			Object phcrLookedUpObject = nedssUtils.lookupBean(JNDINames.EDX_PHCR_DOCUMENT_EJB);
			EdxPHCRDocumentHome edxPHCRDocumentHome = (EdxPHCRDocumentHome) PortableRemoteObject.narrow(phcrLookedUpObject, EdxPHCRDocumentHome.class);
			EdxPHCRDocument edxPHCRDocument = edxPHCRDocumentHome.create();

			Object dsmLookedUpObject = nedssUtils.lookupBean(JNDINames.DSMAlgorithmEJB);
			DSMAlgorithmHome dsmAlgorithmHome = (DSMAlgorithmHome) PortableRemoteObject.narrow(dsmLookedUpObject, DSMAlgorithmHome.class);
			DSMAlgorithm dsmAlgorithm = dsmAlgorithmHome.create();
			Collection< NbsInterfaceDT> originalMessagesQueued = new ArrayList< NbsInterfaceDT>();
			int successCounter = 0;
			int failureCounter = 0;
			int notProcessedCount = 0;
			
			if (newMessageColl != null) {
				//clear cache at start of each run   
				dsmAlgorithm.refreshDSMAlgorithmCache(nbsSecurityObj);
				System.out.println("NBS found " + newMessageColl.size() + " unprocessed documents(s) to import in the NBS_INTERFACE table.");
				System.out.println("");
				
				Iterator<Object> it = newMessageColl.iterator();
				while (it.hasNext()) {
					EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT	= null;
					java.util.Date dateTime = new java.util.Date();
					Timestamp time = new Timestamp(dateTime.getTime());
					NbsInterfaceDT nbsInterfaceDT = (NbsInterfaceDT) it.next();
//					System.out.println("*************************************************************************************************");
					System.out.println("Import Uid: "+nbsInterfaceDT.getNbsInterfaceUid());
					try {
						nbsInterfaceDT =nbsDocument.extractXMLDocument(nbsInterfaceDT.getNbsInterfaceUid(), nbsSecurityObj);
						if(nbsInterfaceDT.getXmlPayLoadContent()==null && nbsInterfaceDT.getOriginalPayload() !=null && nbsInterfaceDT.getRecordStatusCd().equals(NEDSSConstants.ORIG_XML_QUEUED)){
							 originalMessagesQueued.add(nbsInterfaceDT);
							 continue;
						 }
						if(nbsInterfaceDT.getXmlPayLoadContent()==null && nbsInterfaceDT.getOriginalPayload() !=null && nbsInterfaceDT.getRecordStatusCd().equals(NEDSSConstants.QUEUED)){
							notProcessedCount++;
							 continue;
						 }
						edxRuleAlgorithmManagerDT = edxPHCRDocument.createNbsEventsFromPHCR(datamigration, nbsInterfaceDT, nbsSecurityObj);
						//System.out.println("Here at one");
						if (edxRuleAlgorithmManagerDT!=null && edxRuleAlgorithmManagerDT.getObject() == null) {
							//System.out.println("Here at two");
							if(edxRuleAlgorithmManagerDT.getStatus()!=null && edxRuleAlgorithmManagerDT.getStatus().equals(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success)){
								System.out.println("\tSuccess - imported successfully");
								successLogging(edxRuleAlgorithmManagerDT);
								if(edxRuleAlgorithmManagerDT.isCaseUpdated())
									System.out.println("\tEvent record Updated: "+ 
								edxRuleAlgorithmManagerDT.getErrorText());
								else 
									System.out.println("\tEvent record not created: "+ 
											edxRuleAlgorithmManagerDT.getErrorText());
								successCounter++;
							}else{
								System.out.println("\tFailure - not imported successfully");
								System.out.println("\tEvent record could not be created: " + 
								edxRuleAlgorithmManagerDT.getErrorText());
								failureCounter++;
							}
						} else if (edxRuleAlgorithmManagerDT!=null && edxRuleAlgorithmManagerDT.getDsmAlgorithmUid() != null) {
							if (edxRuleAlgorithmManagerDT.getStatus()!=null && edxRuleAlgorithmManagerDT.getStatus().equals(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success)) {
								successCounter++;
								System.out.println("\tSuccess - imported successfully");
								
								successLogging(edxRuleAlgorithmManagerDT);
								
							} else {
								failureCounter++;
								//System.out.println("Here at three");
								failureLogging(edxRuleAlgorithmManagerDT);
								
							}
							//System.out.println("Here at four");
						} 

					} catch (Exception e) {
						//System.out.println("Here at five");
						String errorText = "NBS_Interface_uid " + nbsInterfaceDT.getNbsInterfaceUid() + 
								" could not be imported successfully. " +
								"EdxAutoInvFromInterface.prcoessMessageColl: Exception thrown " + e.getMessage() + "\n\t" +
								"Cause:=" + e.getCause() + "\n\t" +
								"StackTrace:=\n" + stackTraceToString(e);
						failureCounter++;
						System.out.println("\tFailure - not imported successfully");
						System.out.println("\tUid " + nbsInterfaceDT.getNbsInterfaceUid() + " could not be imported successfully.");
		
						try {
							nbsInterfaceDT.setRecordStatusCd(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.toString());
							nbsInterfaceDT.setRecordStatusTime(time);
	
							EDXActivityLogDT eDXActivityLogDT = new EDXActivityLogDT();
							eDXActivityLogDT.setSourceUid(nbsInterfaceDT.getNbsInterfaceUid());
							eDXActivityLogDT.setImpExpIndCd("I");
							eDXActivityLogDT.setRecordStatusTime(time);
							eDXActivityLogDT.setSourceTypeCd("INT");
							eDXActivityLogDT.setDocType(NEDSSConstants.PHC_236);
							eDXActivityLogDT.setTargetTypeCd("DOC");
							eDXActivityLogDT.setException(errorText);
							eDXActivityLogDT.setRecordStatusCd(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.toString());
							nbsInterfaceDT.setRecordStatusCd(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.toString());
							nbsInterfaceDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
							nbsDocument.createEDXActivityLog(nbsInterfaceDT, eDXActivityLogDT, nbsSecurityObj);
						} catch (Exception e2) {
							System.out.println("\tException writing to EDX_activity_log: "+e2);
							e2.printStackTrace();
						}
					}

					if (edxRuleAlgorithmManagerDT != null) {
						try {
							EDXActivityLogDT edxActivityLogDT = edxRuleAlgorithmManagerDT.getEdxActivityLogDT();
		
							edxActivityLogDT.setLogDetailAllStatus(true);
							edxActivityLogDT.setSourceUid(nbsInterfaceDT.getNbsInterfaceUid());
							edxActivityLogDT.setTargetUid(nbsInterfaceDT.getNbsInterfaceUid());
							edxActivityLogDT.setException(edxRuleAlgorithmManagerDT.getErrorText());
							edxActivityLogDT.setImpExpIndCd("I");
							edxActivityLogDT.setRecordStatusTime(time);
							edxActivityLogDT.setSourceTypeCd("INT");
							edxActivityLogDT.setTargetTypeCd("DOC");
							edxActivityLogDT.setDocType(NEDSSConstants.PHC_236);
							edxActivityLogDT.setRecordStatusCd(edxRuleAlgorithmManagerDT.getStatus().toString());
		
							// set status in interface record
							nbsInterfaceDT.setRecordStatusCd(edxActivityLogDT.getRecordStatusCd());
							nbsInterfaceDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
		
							nbsDocument.createEDXActivityLog(nbsInterfaceDT, edxActivityLogDT, nbsSecurityObj);
						} catch (Exception e) {
							System.out.println("\tException writing to EDX_activity_log: "+e);
							e.printStackTrace();							
						}
					}
//					System.out.println("*************************************************************************************************");
				}
			}
			if (successCounter > 0) {
				System.out.println(successCounter + " case(s) imported successfully.");
			}
			if (failureCounter > 0) {
				System.out.println(failureCounter + " case(s) could not be imported. Please check EDX_activity_log table for details.");
			}
			if (notProcessedCount > 0) {
				System.out.println(notProcessedCount + " case(s) could not be imported. Please make sure message is queued Properly with correct status and payload content.");
			}
			if (originalMessagesQueued != null && originalMessagesQueued.size() > 0)
				processOriginalPayloadMessages(originalMessagesQueued,nbsSecurityObj);
		} catch (Exception e) {
			System.out.println("EdxAutoInvFromInterface.getImportMessageColl Exception thrown " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public static void processOriginalPayloadMessages(Collection< NbsInterfaceDT> originalMessagesQueued, NBSSecurityObj nbsSecurityObj){
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			NbsDocumentHome docHome = (NbsDocumentHome) PortableRemoteObject.narrow(theLookedUpObject, NbsDocumentHome.class);
			NbsDocument nbsDocument= docHome.create();
			System.out.println("************************************************");
			System.out.println("*Import process started for Original Documents.*");
			System.out.println("************************************************");
			System.out.println("");
			if(originalMessagesQueued!=null )
				System.out.println("NBS Application found "+ originalMessagesQueued.size()+ " document(s) to be imported into NBS.");
			int successCounter=0;
			int failureCounter=0;
			if(originalMessagesQueued!=null){
				Iterator<NbsInterfaceDT> it = originalMessagesQueued.iterator();
				while(it.hasNext()){
					java.util.Date dateTime = new java.util.Date();
					Timestamp time = new Timestamp(dateTime.getTime());
					 NbsInterfaceDT nbsInterfaceDT  = it.next();
					try {
						nbsInterfaceDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_IN_BATCH_PROCESS);
						nbsInterfaceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
						nbsDocument.updateNBSInterface(nbsInterfaceDT, nbsSecurityObj);
						ArrayList<MsgXMLMappingDT> genericMappingList = XMLMapping.get(nbsInterfaceDT.getOriginalDocTypeCd());
						if(genericMappingList==null){
							genericMappingList = nbsDocument.getXMLMapping(nbsInterfaceDT.getOriginalDocTypeCd(), nbsSecurityObj);
							XMLMapping.put(nbsInterfaceDT.getOriginalDocTypeCd(), genericMappingList);
						}
						nbsDocument.parseOriginalXML(genericMappingList, nbsInterfaceDT, nbsSecurityObj);
						System.out.println("NBS_Interface_uid  "+nbsInterfaceDT.getNbsInterfaceUid()+" for "+nbsInterfaceDT.getOriginalDocTypeCd()+" imported successfully.");
						successCounter++;
					} catch (Exception e) {
						System.out.println("NBS_Interface_uid "+nbsInterfaceDT.getNbsInterfaceUid()+" could not be imported successfully.");
						System.out.println("PHCRImporter.processOriginalPayloadMessages:Exception thrown "+ e.getMessage());
						nbsInterfaceDT.setRecordStatusCd(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.toString());
						nbsInterfaceDT.setRecordStatusTime(time);

						EDXActivityLogDT  eDXActivityLogDT = new EDXActivityLogDT();
						eDXActivityLogDT.setSourceUid(nbsInterfaceDT.getNbsInterfaceUid());
						eDXActivityLogDT.setImpExpIndCd("I");
						eDXActivityLogDT.setRecordStatusTime(time);
						eDXActivityLogDT.setSourceTypeCd("INT");
						eDXActivityLogDT.setTargetTypeCd("INT");
						eDXActivityLogDT.setTargetUid(nbsInterfaceDT.getNbsInterfaceUid());
						eDXActivityLogDT.setException(e.toString());
						eDXActivityLogDT.setDocType(NEDSSConstants.PHC_236);
						eDXActivityLogDT.setRecordStatusCd(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.toString());
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
			System.out.println("");
			System.out.println("**************************************************");
			System.out.println("*Import process completed for Original Documents.*");
			System.out.println("**************************************************");
			
		} catch (NEDSSSystemException e) {
			System.out.println("PHCRImporter Original Message.processOriginalPayloadMessages NEDSSSystemException thrown for nbs_interface_uid "+ e.getMessage());
			e.printStackTrace();
		} catch (ClassCastException e) {
			System.out.println("PHCRImporter.processOriginalPayloadMessages ClassCastException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("PHCRImporter.processOriginalPayloadMessages RemoteException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (CreateException e) {
			System.out.println("PHCRImporter.processOriginalPayloadMessages CreateException thrown "+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("PHCRImporter.processOriginalPayloadMessages NEDSSSystemException thrown "+ e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}

	private static void getSecurityObjAndStartRun(String userName, String datamigration) throws Exception {
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);
			MainSessionCommandHome home = (MainSessionCommandHome) PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			NBSSecurityObj nbsSecurityObj = msCommand.nbsSecurityLogin(userName, userName);

			prcoessMessageColl(datamigration, nbsSecurityObj);
	} 

	private static String stackTraceToString(Exception e) {
		String ret = "";
		StackTraceElement[] stes = e.getStackTrace();
		for (StackTraceElement ste : stes) {
			ret += ste.toString() + "\n";
		}
		return ret;
	}

	private static String padStringTo13Chars(String in) {
		while (in.length() <13) {
			in += " ";
		}
		return in;
	}
	
	private static void failureLogging(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT)throws NEDSSAppException{
		System.out.println("\tFailure - not imported successfully");
		try{
		EDXActivityLogDT edxActivityLogDT = edxRuleAlgorithmManagerDT.getEdxActivityLogDT();
		if(edxActivityLogDT!=null){
		Collection<Object> details = edxActivityLogDT.getEDXActivityLogDTWithVocabDetails();
		if(details!=null && details.size()>0){
		Iterator<Object> detIter = details.iterator();
		while (detIter.hasNext()) {
			EDXActivityDetailLogDT detail = (EDXActivityDetailLogDT) detIter.next();
			String detStatus = detail.getLogType();
			String detComponent=null;
			if(detail.getRecordType()!=null)
			 detComponent = padStringTo13Chars(detail.getRecordType());
			String detComment = detail.getComment();
			System.out.println("\t\t"+detStatus+"\t"+detComponent+"\t"+detComment);
		}
		}
		}
		}catch(Exception ex){
			String errorTxt = "Exception raised while writing failure activity logging: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorTxt,ex);
		}
	}
	
	private static void successLogging(EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT)throws NEDSSAppException{
		try{
		EDXActivityLogDT edxActivityLogDT = edxRuleAlgorithmManagerDT.getEdxActivityLogDT();
		if(edxActivityLogDT!=null){
		Collection<Object> details = edxActivityLogDT.getEDXActivityLogDTWithVocabDetails();
		if(details!=null && details.size()>0){
		Iterator<Object> detIter = details.iterator();
		while (detIter.hasNext()) {
			EDXActivityDetailLogDT detail = (EDXActivityDetailLogDT) detIter.next();
			String detStatus = detail.getLogType();
			String detComponent=null;
			if(detail.getRecordType()!=null)
			detComponent = padStringTo13Chars(detail.getRecordType());
			String detComment = detail.getComment();
			System.out.println("\t\t"+detStatus+"\t"+detComponent+"\t"+detComment);
		}
		}
		}
		if(edxRuleAlgorithmManagerDT.getDsmAlgorithmName()!=null && edxRuleAlgorithmManagerDT.getDsmAlgorithmUid()!=null){
			System.out.println("\tAlgorithm - Uid: " + 
				edxRuleAlgorithmManagerDT.getDsmAlgorithmUid() + " \"" +
				edxRuleAlgorithmManagerDT.getDsmAlgorithmName() + "\"");
		}
		
	}catch(Exception ex){
		String errorTxt = "Exception raised while writing success activity logging: "+ex.getMessage();
		ex.printStackTrace();
		throw new NEDSSAppException(errorTxt,ex);
	}
	}
}
