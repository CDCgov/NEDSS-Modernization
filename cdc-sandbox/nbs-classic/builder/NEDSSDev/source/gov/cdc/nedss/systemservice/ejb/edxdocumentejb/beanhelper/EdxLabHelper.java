package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.EDXObservationDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT.STATUS_VAL;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.HL7CommonLabUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.HL7ELRValidateDecisionSupport;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.transaction.Status;
import javax.transaction.UserTransaction;


/**
 * 
 * @author Pradeep Kumar Sharma EdxLabHelper is a bean helper class where the
 *         processing logic specific to ELRs is coded.
 * @updatedBy Pradeep Kumar Sharma : Performance related changes to EDX helper so that it can run multiple threads of ELR imports
 */

public class EdxLabHelper {

	static final LogUtils logger = new LogUtils(EdxLabHelper.class.getName());

	
	public EdxRuleAlgorothmManagerDT processELRService(NbsInterfaceDT nbsInterfaceDT, NBSSecurityObj nbsSecurityObj, SessionContext sessionCtx) {
		EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT = new EdxRuleAlgorothmManagerDT();
		// HL7CommonLabUtil.processELR(nbsInterfaceDT, nbsSecurityObj);
		return edxRuleAlgorothmManagerDT;
	}

	
	public Map<Integer,ArrayList<Object>> getUnProcessedElrDTMap(int batches, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		Map<Integer,ArrayList<Object>> mappedList =null; 
		try {
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			mappedList = nbsInterfaceDAOImpl.getUnProcessedElrDTMap(EdxELRConstants.ELR_DOC_TYPE_CD, batches);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new NEDSSAppException( e.toString(), e);
		}
		return mappedList;
	}


	public ArrayList<Object> getUnProcessedELRList(NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		ArrayList<Object> list =null;
		try {
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			list = nbsInterfaceDAOImpl.getSQLUnprocessedInterfaceList(EdxELRConstants.ELR_DOC_TYPE_CD);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			throw new NEDSSAppException( e.toString(), e);
		}
		return list;
	}
 
	
	public EdxLabInformationDT getUnProcessedELR(Long nbsInterfaceUid, SessionContext sessionCtx, NBSSecurityObj nbsSecurityObj) throws RemoteException, NEDSSSystemException, NEDSSAppException {

		EdxLabInformationDT edxLabInformationDT = new EdxLabInformationDT();
		NbsInterfaceDT nbsInterfaceDT = null;
		NbsDocument nbsDocument = null;
		ObservationDT observationDT = null;
		UserTransaction userTrans = sessionCtx.getUserTransaction();
		String detailedMsg="";
		EdxCommonHelper edxCommonHelper = new EdxCommonHelper();
		
		try {
			NedssUtils nedssUtils = new NedssUtils();

			Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			NbsDocumentHome docHome = (NbsDocumentHome) PortableRemoteObject.narrow(theLookedUpObject, NbsDocumentHome.class);
			nbsDocument = docHome.create();

			LabResultProxyVO labResultProxyVO;
			edxLabInformationDT.setStatus(STATUS_VAL.Success);
			edxLabInformationDT.setUserName(nbsSecurityObj.getTheUserProfile().getTheUser().getUserID());
			//userTrans = sessionCtx.getUserTransaction();
			userTrans.begin();
			NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
			if(nbsInterfaceUid<0)
				nbsInterfaceDT = nbsInterfaceDAOImpl.getUnprocessedInterfaceDT(EdxELRConstants.ELR_DOC_TYPE_CD);
			if(nbsInterfaceUid>0)
				nbsInterfaceDT = nbsInterfaceDAOImpl.selectNbsInterfaceDT(nbsInterfaceUid);
			
			edxLabInformationDT.setNbsInterfaceUid(nbsInterfaceUid);
			HL7ELRValidateDecisionSupport hL7ELRValidateDecisionSupport = new HL7ELRValidateDecisionSupport();
			//edxLabInformationDT.setEdxActivityLogDT(updateActivityLogDT(nbsInterfaceDT, edxLabInformationDT));
			HL7CommonLabUtil hL7CommonLabUtil = new HL7CommonLabUtil();
			labResultProxyVO = hL7CommonLabUtil.processELR(nbsInterfaceDT, edxLabInformationDT, nbsSecurityObj);
			edxLabInformationDT.setLabResultProxyVO(labResultProxyVO);
			if(nbsInterfaceDT.getObservationUid()!=null && nbsInterfaceDT.getObservationUid()>0)
				edxLabInformationDT.setRootObserbationUid(nbsInterfaceDT.getObservationUid());
			Long aPersonUid = null;
			observationDT =EDXObservationDAO.matchingObservationInODS(edxLabInformationDT);
			if(observationDT!=null){
				LabResultProxyVO matchedlabResultProxyVO =hL7CommonLabUtil.getLabResultToProxy(observationDT.getObservationUid(), nbsSecurityObj);
				hL7CommonLabUtil.processMatchedProxyVO(labResultProxyVO,matchedlabResultProxyVO,edxLabInformationDT );
				aPersonUid = hL7CommonLabUtil.getMatchedPersonUID(matchedlabResultProxyVO);//matchedlabResultProxyVO.getPersonVO_s(1).getThePersonDT().getPersonUid();
				

				//hL7CommonLabUtil.updatePersonELRUpdate(labResultProxyVO,matchedlabResultProxyVO);
				//Instead of the specific ELR updatePersonELRUpdate method, we are calling the generic one for PHDC and ELR:

				 Collection<Object> personCollection = matchedlabResultProxyVO.getThePersonVOCollection();
				 PersonVO personPatientMatchedVO = null;
				 	
					if(personCollection!=null){
						Iterator<Object> iterator = personCollection.iterator();
					
						
						while(iterator.hasNext()){
							PersonVO personVO =(PersonVO)iterator.next();
							String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
							if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
							
							personPatientMatchedVO = personVO;
							
							}
							
				 
						}
				 
				 }
				 
				 
				 
				 PersonVO personPatientLabResultVO = null;
				 
				 	if(labResultProxyVO.getThePersonVOCollection()!=null){
						Iterator<Object> iter = labResultProxyVO.getThePersonVOCollection().iterator();
						while(iter.hasNext()){
							PersonVO personVO =(PersonVO)iter.next();
							String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
							if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
								personPatientLabResultVO = personVO;
								
							}
							
						}
							
							
					}
							
				 		
							
				
				edxCommonHelper.updatePersonELRPHDCUpdate(personPatientLabResultVO, personPatientMatchedVO);
				
				
				
				
				
				
				
				
				
				
				edxLabInformationDT.setRootObserbationUid(observationDT.getObservationUid());
				if(observationDT.getProgAreaCd()!=null && observationDT.getJurisdictionCd()!=null)
					edxLabInformationDT.setLabIsUpdateDRRQ(true);
				else
					edxLabInformationDT.setLabIsUpdateDRSA(true);
				edxLabInformationDT.setPatientMatch(true);
			}else{
				edxLabInformationDT.setLabIsCreate(true);
			}
			
			hL7CommonLabUtil.processELREntities(labResultProxyVO, edxLabInformationDT, nbsSecurityObj);
		
			if(edxLabInformationDT.isLabIsUpdateDRRQ() || edxLabInformationDT.isLabIsUpdateDRSA()){
			
				//Instead of the following method, we have create a generic one to be called from both ELR and PHDC
				
				//	hL7CommonLabUtil.setPersonUIDOnUpdate(aPersonUid,labResultProxyVO );//thisis used to update it. Without it, it doesnt udpate neither create a new one.
				
				Collection<Object> personCollection = labResultProxyVO.getThePersonVOCollection();
				if(personCollection!=null){
					Iterator<Object> iterator = personCollection.iterator();

					while(iterator.hasNext()){
						PersonVO personVO =(PersonVO)iterator.next();
						String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
						if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
							edxCommonHelper.setPersonUIDOnUpdate(aPersonUid, personVO);//Generic method used from ELR and PHDC

						}
					}
				}
			}
					
			edxLabInformationDT.setLabResultProxyVO(labResultProxyVO);
			
			//Check for user security to add/update lab
			String nbsOperation = edxLabInformationDT.isLabIsCreate()? NBSOperationLookup.ADD :
		          NBSOperationLookup.EDIT;
			ObservationVO orderTest = getOrderedTest(labResultProxyVO);
			String programAreaCd = orderTest.getTheObservationDT().getProgAreaCd();
			String jurisdictionCd = orderTest.getTheObservationDT().getJurisdictionCd();
			checkSecurity(nbsSecurityObj, edxLabInformationDT, NBSBOLookup.OBSERVATIONLABREPORT, nbsOperation, programAreaCd, jurisdictionCd);
			
				observationDT = hL7CommonLabUtil.sendLabResultToProxy(labResultProxyVO, nbsSecurityObj);
				if(edxLabInformationDT.isLabIsCreate()){
					edxLabInformationDT.setLabIsCreateSuccess(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_2);
				}

				logger.debug("localId is " + observationDT.getLocalId());
				edxLabInformationDT.setLocalId(observationDT.getLocalId());
				edxLabInformationDT.getEdxActivityLogDT().setBusinessObjLocalId(observationDT.getLocalId());
				edxLabInformationDT.setRootObserbationUid(observationDT.getObservationUid());
				edxLabInformationDT.setProgramAreaName(CachedDropDowns.getProgAreadDesc(observationDT.getProgAreaCd()));
				
				String jurisdictionName =CachedDropDowns.getJurisdictionDesc(observationDT.getJurisdictionCd());
				edxLabInformationDT.setJurisdictionName(jurisdictionName);
				if(edxLabInformationDT.isLabIsCreateSuccess()&&(edxLabInformationDT.getProgramAreaName()==null || edxLabInformationDT.getJurisdictionName()==null)){
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_1);
				}
				
				userTrans.commit();
				
				if(edxLabInformationDT.isLabIsUpdateDRRQ()){
					edxLabInformationDT.setLabIsUpdateSuccess(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_15);
				}
				else if(edxLabInformationDT.isLabIsUpdateDRSA()){
					edxLabInformationDT.setLabIsUpdateSuccess(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_22);
				}
				
				if(edxLabInformationDT.isLabIsCreate()){
					if(observationDT.getJurisdictionCd()!=null && observationDT.getProgAreaCd()!=null){
					userTrans.begin();
					hL7ELRValidateDecisionSupport.validateProxyVO(labResultProxyVO, edxLabInformationDT, nbsSecurityObj);
					PageActProxyVO pageActProxyVO =null;
					PamProxyVO pamProxyVO = null;
					PublicHealthCaseVO publicHealthCaseVO =null;
					Long phcUid=null;
					if(edxLabInformationDT.getAction()!=null && edxLabInformationDT.getAction().equalsIgnoreCase(DecisionSupportConstants.MARK_AS_REVIEWED)){
						//Check for user security to mark as review lab
						checkSecurity(nbsSecurityObj, edxLabInformationDT, NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.MARKREVIEWED, programAreaCd, jurisdictionCd);
						hL7CommonLabUtil.markAsReviewedHandler(observationDT.getObservationUid(),edxLabInformationDT,nbsSecurityObj);
						if(edxLabInformationDT.getAssociatedPublicHealthCaseUid()!=null && edxLabInformationDT.getAssociatedPublicHealthCaseUid().longValue()>0){
							edxLabInformationDT.setPublicHealthCaseUid(edxLabInformationDT.getAssociatedPublicHealthCaseUid());
							edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_21);
							edxLabInformationDT.setLabAssociatedToInv(true);
						}
						else
							edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_11);
						
					}
					else if(edxLabInformationDT.getObject()!=null){
						//Check for user security to create investigation
						checkSecurity(nbsSecurityObj, edxLabInformationDT, NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD, programAreaCd, jurisdictionCd);
						if (edxLabInformationDT.getObject() instanceof PageProxyVO) {
							pageActProxyVO = (PageActProxyVO) edxLabInformationDT.getObject();
							publicHealthCaseVO=pageActProxyVO.getPublicHealthCaseVO();
						}else{
							pamProxyVO = (PamProxyVO)edxLabInformationDT.getObject();
							publicHealthCaseVO=pamProxyVO.getPublicHealthCaseVO();
						}
						if(publicHealthCaseVO.getErrorText()!=null){
							requiredFieldError(publicHealthCaseVO.getErrorText(),edxLabInformationDT);
						}
						if(pageActProxyVO!=null && observationDT.getJurisdictionCd()!=null && observationDT.getProgAreaCd()!=null ){
							Object object = nedssUtils.lookupBean(JNDINames.PAGE_PROXY_EJB);
						    PageProxyHome pageProxyHome = (PageProxyHome)javax.rmi.PortableRemoteObject.narrow(object, PageProxyHome.class);
						    PageProxy pageProxy = pageProxyHome.create();
						    phcUid=pageProxy.setPageProxyWithAutoAssoc(NEDSSConstants.CASE, pageActProxyVO,edxLabInformationDT.getRootObserbationUid(),NEDSSConstants.LABRESULT_CODE,null, nbsSecurityObj);
						    pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(phcUid);
						    edxLabInformationDT.setInvestigationSuccessfullyCreated(true);
						    edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_3);
						    edxLabInformationDT.setPublicHealthCaseUid(phcUid);
						    edxLabInformationDT.setLabAssociatedToInv(true);
						}else if(observationDT.getJurisdictionCd()!=null && observationDT.getProgAreaCd()!=null){
							Object object = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
						    PamProxyHome pamProxyHome = (PamProxyHome)javax.rmi.PortableRemoteObject.narrow(object, PamProxyHome.class);
						    PamProxy pamProxy = pamProxyHome.create();
						    phcUid=pamProxy.setPamProxyWithAutoAssoc(pamProxyVO,edxLabInformationDT.getRootObserbationUid(),NEDSSConstants.LABRESULT_CODE, nbsSecurityObj);
						    pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(phcUid);
						    edxLabInformationDT.setInvestigationSuccessfullyCreated(true);
						    edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_3);
						    edxLabInformationDT.setPublicHealthCaseUid(phcUid);
						    edxLabInformationDT.setLabAssociatedToInv(true);
						}
						
						userTrans.commit();
					
						userTrans.begin();
						if(edxLabInformationDT.getAction().equalsIgnoreCase(DecisionSupportConstants.CREATE_INVESTIGATION_WITH_NND_VALUE)){
							//Check for user security to create notification
							checkSecurity(nbsSecurityObj, edxLabInformationDT, NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE, programAreaCd, jurisdictionCd);
							EDXActivityDetailLogDT edxActivityDetailLogDT = EdxCommonHelper.sendNotification(publicHealthCaseVO, edxLabInformationDT.getNndComment(), nbsSecurityObj);
							edxActivityDetailLogDT.setRecordType(EdxELRConstants.ELR_RECORD_TP);
							edxActivityDetailLogDT.setRecordName(EdxELRConstants.ELR_RECORD_NM);
							ArrayList<Object> details = (ArrayList<Object>)edxLabInformationDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails();
							if(details==null){
								details = new ArrayList<Object>();
							}
							details.add(edxActivityDetailLogDT);
							edxLabInformationDT.getEdxActivityLogDT().setEDXActivityLogDTWithVocabDetails(details);
							if(edxActivityDetailLogDT.getLogType()!=null && edxActivityDetailLogDT.getLogType().equals(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.name())){
								if(edxActivityDetailLogDT.getComment()!=null && edxActivityDetailLogDT.getComment().indexOf(EdxELRConstants.MISSING_NOTF_REQ_FIELDS)!=-1){
								edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_8);
								edxLabInformationDT.setNotificationMissingFields(true);
								}
								else{
									edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_10);
								}
								throw new NEDSSSystemException("MISSING NOTI REQUIRED: "+edxActivityDetailLogDT.getComment());
							}else{
							//edxLabInformationDT.setNotificationSuccessfullyCreated(true);
							edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_6);
							}
						}
					}
					userTrans.commit();
				}
			}
		}
		catch (Exception e) {		
			logger.error("Exception EdxLabHelper.getUnProcessedELR processing exception: " + e, e);
			String accessionNumberToAppend = "Accession Number:"+edxLabInformationDT.getFillerNumber();
			edxLabInformationDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			edxLabInformationDT.setSystemException(true);
			if( e.toString().indexOf("Invalid XML")!= -1)
            {
				edxLabInformationDT.setInvalidXML(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
            }
			if(edxLabInformationDT.getObject()!=null && !edxLabInformationDT.isInvestigationSuccessfullyCreated()){
				if(edxLabInformationDT.isInvestigationMissingFields())
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_5);
				else
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_9);
			}
			
			else if(edxLabInformationDT.getObject()!=null && edxLabInformationDT.isInvestigationSuccessfullyCreated()){
				if(edxLabInformationDT.isNotificationMissingFields())
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_8);
				else
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_10);
			}
			if(edxLabInformationDT.getErrorText()==null){
				//if error text is null, that means lab was not created due to unexpected error.
				if(e!=null && (e.getMessage().contains(EdxELRConstants.SQL_FIELD_TRUNCATION_ERROR_MSG) || e.getMessage().contains(EdxELRConstants.ORACLE_FIELD_TRUNCATION_ERROR_MSG))){
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_18);
					edxLabInformationDT.setFieldTruncationError(true);
					edxLabInformationDT.setSystemException(false);
					try{
						// Extract table name from Exception message, first find table name and ignore text after it.
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						String exceptionMessage = errors.toString();
						//Patient is not created so setting patient_parent_id to 0
						edxLabInformationDT.setPersonParentUid(0);
						//No need to create success message "The Ethnicity code provided in the message is not found in the SRT database. The code is saved to the NBS." in case of exception scenario
						edxLabInformationDT.setEthnicityCodeTranslated(true);
						String textToLookFor = "Table Name : ";
						String tableName = exceptionMessage.substring(exceptionMessage.indexOf(textToLookFor)+textToLookFor.length());
						tableName = tableName.substring(0, tableName.indexOf(" "));
						detailedMsg = "SQLException while inserting into "+tableName+"\n "+accessionNumberToAppend+"\n "+exceptionMessage;
						detailedMsg = detailedMsg.substring(0,Math.min(detailedMsg.length(), 2000));
					}catch(Exception ex){
						logger.error("Exception while formatting exception message for Activity Log: "+ex.getMessage(), ex);
					}
				} else if (e!=null && e.getMessage().contains(EdxELRConstants.DATE_VALIDATION)) {
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_20);
					edxLabInformationDT.setInvalidDateError(true);
					edxLabInformationDT.setSystemException(false);
					
					//Patient is not created so setting patient_parent_id to 0
					edxLabInformationDT.setPersonParentUid(0);
					//No need to create success message for Ethnic code
					edxLabInformationDT.setEthnicityCodeTranslated(true);
					try {
					// Extract problem date from Exception message
					String problemDateInfoSubstring = e.getMessage().substring(e.getMessage().indexOf(EdxELRConstants.DATE_VALIDATION));
					problemDateInfoSubstring = problemDateInfoSubstring.substring(0,problemDateInfoSubstring.indexOf(EdxELRConstants.DATE_VALIDATION_END_DELIMITER1));
					detailedMsg = problemDateInfoSubstring+"\n "+accessionNumberToAppend+"\n"+e.getMessage();
					detailedMsg = detailedMsg.substring(0,Math.min(detailedMsg.length(), 2000));
					}catch(Exception ex){
						logger.error("Exception while formatting date exception message for Activity Log: "+ex.getMessage(), ex);
					}
				}else{
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_16);
					try{
						//Patient is not created so setting patient_parent_id to 0
						edxLabInformationDT.setPersonParentUid(0);
						//No need to create success message for Ethnicity code provided in the message is not found in the SRT database. The code is saved to the NBS." in case of exception scenario
						edxLabInformationDT.setEthnicityCodeTranslated(true);
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						String exceptionMessage = accessionNumberToAppend+"\n"+errors.toString();
						detailedMsg = exceptionMessage.substring(0,Math.min(exceptionMessage.length(), 2000));
					}catch(Exception ex){
						logger.error("Exception while formatting exception message for Activity Log: "+ex.getMessage(), ex);
					}
				}
			}
			if( edxLabInformationDT.isInvestigationMissingFields() || edxLabInformationDT.isNotificationMissingFields() || (edxLabInformationDT.getErrorText()!=null && edxLabInformationDT.getErrorText().equals(EdxELRConstants.ELR_MASTER_LOG_ID_10))){
				edxLabInformationDT.setSystemException(false);
            }
			
			if(edxLabInformationDT.isReflexResultedTestCdMissing() || edxLabInformationDT.isResultedTestNameMissing() || edxLabInformationDT.isOrderTestNameMissing() || edxLabInformationDT.isReasonforStudyCdMissing()){
				try{
					String exceptionMsg = e.getMessage();
					String textToLookFor = "XMLElementName: ";
					detailedMsg = "Blank identifiers in segments "+exceptionMsg.substring(exceptionMsg.indexOf(textToLookFor)+textToLookFor.length())+"\n\n"+accessionNumberToAppend;
					detailedMsg = detailedMsg.substring(0,Math.min(detailedMsg.length(), 2000));
				}catch(Exception ex){
					logger.error("Exception while formatting exception message for Activity Log: "+ex.getMessage(), ex);
				}
			}
			
		} finally {
			try {
				if (userTrans.getStatus() == Status.STATUS_ACTIVE || userTrans.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
					userTrans.rollback();
				}
				if (nbsDocument != null) {
					userTrans = sessionCtx.getUserTransaction();
					userTrans.begin();
					updateActivityLogDT(nbsInterfaceDT,edxLabInformationDT);
					addActivityDetailLogs(edxLabInformationDT, detailedMsg);
					edxLabInformationDT.getEdxActivityLogDT().setRecordStatusCd(edxLabInformationDT.getStatus().name());
					nbsInterfaceDT.setObservationUid(edxLabInformationDT.getRootObserbationUid());
					nbsInterfaceDT.setRecordStatusCd(edxLabInformationDT.getEdxActivityLogDT().getRecordStatusCd());
					nbsDocument.createEDXActivityLog(nbsInterfaceDT, edxLabInformationDT.getEdxActivityLogDT(), nbsSecurityObj);
					userTrans.commit();
				}
			} catch (Exception e) {
				logger.error("EdxLabHelper.getUnProcessedELR processing exception  in finally(2): " + e.getMessage(), e);
			}
		}
		return edxLabInformationDT;
	}

	private void updateActivityLogDT(NbsInterfaceDT nbsInterfaceDT, EdxLabInformationDT edxLabInformationDT) {
		EDXActivityLogDT edxActivityLogDT = edxLabInformationDT.getEdxActivityLogDT();
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		nbsInterfaceDT.setRecordStatusTime(time);

		edxActivityLogDT.setLogDetailAllStatus(true);
		edxActivityLogDT.setSourceUid(nbsInterfaceDT.getNbsInterfaceUid());
		edxActivityLogDT.setTargetUid(edxLabInformationDT.getRootObserbationUid());
		setActivityLogExceptionTxt(edxActivityLogDT,edxLabInformationDT.getErrorText());
		edxActivityLogDT.setImpExpIndCd("I");
		edxActivityLogDT.setRecordStatusTime(time);
		edxActivityLogDT.setSourceTypeCd("INT");
		edxActivityLogDT.setTargetTypeCd("LAB");
		edxActivityLogDT.setDocType(EdxELRConstants.ELR_DOC_TYPE_CD);
		edxActivityLogDT.setRecordStatusCd(edxLabInformationDT.getStatus().toString());
		//If accessionNumber is > 100 characters, trim it down to 100 chars to store in EDX_activity_log table
		if(edxLabInformationDT.getFillerNumber()!=null && edxLabInformationDT.getFillerNumber().length()>100)
			edxActivityLogDT.setAccessionNbr(edxLabInformationDT.getFillerNumber().substring(0, 100));
		else
			edxActivityLogDT.setAccessionNbr(edxLabInformationDT.getFillerNumber());
		edxActivityLogDT.setMessageId(edxLabInformationDT.getMessageControlID());
		edxActivityLogDT.setEntityNm(edxLabInformationDT.getEntityName());
		edxActivityLogDT.setSrcName(edxLabInformationDT.getSendingFacilityName());
		edxActivityLogDT.setBusinessObjLocalId(edxLabInformationDT.getLocalId());
		edxActivityLogDT.setMessageId(edxLabInformationDT.getMessageControlID());
		edxActivityLogDT.setAlgorithmName(edxLabInformationDT.getDsmAlgorithmName());
		edxActivityLogDT.setAlgorithmAction(edxLabInformationDT.getAction());
	}
	
	private void setActivityLogExceptionTxt(EDXActivityLogDT edxActivityLogDT, String txt){
		if(txt==null)
			return;
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_1))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_1);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_2))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_2);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_3))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_3);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_4))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_4);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_5))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_5);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_6))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_6);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_7))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_7);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_8))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_8);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_9))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_9);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_10))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_10);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_11))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_11);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_12))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_12);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_13))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_13);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_14))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_14);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_15))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_15);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_16))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_16);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_17))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_17);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_18))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_18);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_19))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_19);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_20))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_20);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_21))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_21);
		else if(txt.equals(EdxELRConstants.ELR_MASTER_LOG_ID_22))
			edxActivityLogDT.setException(EdxELRConstants.ELR_MASTER_MSG_ID_22);
	}


	
	public void addActivityDetailLogs(EdxLabInformationDT edxLabInformationDT, String detailedMsg) {
		try {
			ArrayList<Object> delailList = (ArrayList<Object>)edxLabInformationDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails();
			if (delailList == null) {
				delailList = new ArrayList<Object>();
			}
			String id = String.valueOf(edxLabInformationDT.getLocalId());
			boolean errorReturned = false;

			if (edxLabInformationDT.isInvalidXML()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.INVALID_XML);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isMultipleOBR()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.MULTIPLE_OBR);
				errorReturned = true;
			}
			else if (!edxLabInformationDT.isFillerNumberPresent()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.FILLER_FAIL);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isOrderTestNameMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_ORDTEST_NAME+" "+detailedMsg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isReflexOrderedTestCdMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_REFLEX_ORDERED_NM);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isReflexResultedTestCdMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_REFLEX_RESULT_NM+" "+detailedMsg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isResultedTestNameMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_RESULT_NAME+" "+detailedMsg);
				errorReturned = true;
			}else if (edxLabInformationDT.isReasonforStudyCdMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_REASON_FOR_STUDY+" "+detailedMsg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isDrugNameMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_DRUG_NAME);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isMultipleSubject()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.MULTIPLE_SUBJECT);
				errorReturned = true;
			}
			
			else if (edxLabInformationDT.isNoSubject()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_SUBJECT);
				errorReturned = true;
			}
			
			else if (edxLabInformationDT.isChildOBRWithoutParent()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.CHILD_OBR_WITHOUT_PARENT);
				errorReturned = true;
			}
			
			else if (edxLabInformationDT.isOrderOBRWithParent()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.ORDER_OBR_WITH_PARENT);
				errorReturned = true;
			}
			else if (!edxLabInformationDT.isObsStatusTranslated()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.TRANSLATE_OBS_STATUS);
				errorReturned = true;
			}
//		if (edxLabInformationDT.isMultiplePerformingLab()) {
//			setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
//					EdxELRConstants.MULTIPLE_PERFORMLAB);
//			return;
//		}
			else if (edxLabInformationDT.isUniversalServiceIdMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.UNIVSRVCID);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isActivityToTimeMissing()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.ODSACTIVTOTIME_FAIL);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isActivityTimeOutOfSequence()) {
				String msg = EdxELRConstants.LABTEST_SEQUENCE.replace("%1", edxLabInformationDT.getFillerNumber());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure, msg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isFinalPostCorrected()) {
				String msg = EdxELRConstants.FINAL_POST_CORRECTED.replace("%1",
						edxLabInformationDT.getLocalId()).replace("%2",
						edxLabInformationDT.getFillerNumber());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isPreliminaryPostFinal()) {
				String msg = EdxELRConstants.PRELIMINARY_POST_FINAL.replace("%1",
						edxLabInformationDT.getLocalId()).replace("%2",
						edxLabInformationDT.getFillerNumber());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isPreliminaryPostCorrected()) {
				String msg = EdxELRConstants.PRELIMINARY_POST_CORRECTED.replace("%1",
						edxLabInformationDT.getLocalId()).replace("%2",
						edxLabInformationDT.getFillerNumber());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isMissingOrderingProviderandFacility()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_ORDERINGPROVIDER);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isUnexpectedResultType()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.UNEXPECTED_RESULT_TYPE);
				errorReturned = true;
			}
			else if (edxLabInformationDT.isChildSuscWithoutParentResult()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.CHILD_SUSC_WITH_NO_PARENT_RESULT);
				errorReturned = true;
			}
			else if(!edxLabInformationDT.isCreateLabPermission()){
				String msg = EdxELRConstants.NO_LAB_CREATE_PERMISSION.replace("%1", edxLabInformationDT.getUserName());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				errorReturned = true;
			}
			else if(!edxLabInformationDT.isUpdateLabPermission()){
				String msg = EdxELRConstants.NO_LAB_UPDATE_PERMISSION.replace("%1", edxLabInformationDT.getUserName());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				errorReturned = true;
			}
			else if(!edxLabInformationDT.isMarkAsReviewPermission()){
				String msg = EdxELRConstants.NO_LAB_MARK_REVIEW_PERMISSION.replace("%1", edxLabInformationDT.getUserName());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
			}
			else if(!edxLabInformationDT.isCreateInvestigationPermission()){
				String msg = EdxELRConstants.NO_INV_PERMISSION.replace("%1", edxLabInformationDT.getUserName());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.OFCI);
			}
			else if(!edxLabInformationDT.isCreateNotificationPermission()){
				String msg = EdxELRConstants.NO_NOT_PERMISSION.replace("%1", edxLabInformationDT.getUserName());
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,msg);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.OFCN);
			}
			else if(edxLabInformationDT.isFieldTruncationError()){
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure, detailedMsg);
			}
			else if(edxLabInformationDT.isInvalidDateError()){
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure, detailedMsg);
				errorReturned = true;
			}
			else if (!errorReturned && edxLabInformationDT.isSystemException()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure, detailedMsg);
				if(edxLabInformationDT.getLabResultProxyVO()==null)
					errorReturned = true;
				if(edxLabInformationDT.isLabIsCreate() && !edxLabInformationDT.isLabIsCreateSuccess())
					errorReturned = true;
				else if ((edxLabInformationDT.isLabIsUpdateDRRQ() || edxLabInformationDT.isLabIsUpdateDRSA()) && !edxLabInformationDT.isLabIsUpdateSuccess())
					errorReturned = true;
				if(edxLabInformationDT.isInvestigationSuccessfullyCreated())
					setActivityDetailLog(delailList, id, STATUS_VAL.Success,
							EdxELRConstants.OFCN);
				else if(edxLabInformationDT.isLabIsCreateSuccess())
					setActivityDetailLog(delailList, id, STATUS_VAL.Success,
							EdxELRConstants.OFCI);
			}
			if(errorReturned){
				edxLabInformationDT.getEdxActivityLogDT().setEDXActivityLogDTWithVocabDetails(delailList);
				return;
			}
			
			if (edxLabInformationDT.isMultipleSubjectMatch()) {
				String msg = EdxELRConstants.SUBJECTMATCH_MULT.replace("%1",
						edxLabInformationDT.getEntityName()).replace("%2",
						edxLabInformationDT.getPersonParentUid() + "");
				setActivityDetailLog(delailList,
						edxLabInformationDT.getPersonParentUid() + "",
						STATUS_VAL.Success, msg);
			}
			if (edxLabInformationDT.isPatientMatch()) {
				String msg = EdxELRConstants.SUBJECT_MATCH_FOUND.replace("%1",
						edxLabInformationDT.getEntityName()).replace("%2",
						edxLabInformationDT.getPersonParentUid() + "");
				setActivityDetailLog(delailList,
						edxLabInformationDT.getPersonParentUid() + "",
						STATUS_VAL.Success, msg);
			}
			if (!edxLabInformationDT.isMultipleSubjectMatch() && !edxLabInformationDT.isPatientMatch() && edxLabInformationDT.getPersonParentUid()!=0) {
				String msg = EdxELRConstants.SUJBECTMATCH_NO.replace("%1",
						edxLabInformationDT.getEntityName()).replace("%2",
						edxLabInformationDT.getPersonParentUid() + "");
				setActivityDetailLog(delailList,
						edxLabInformationDT.getPersonParentUid() + "",
						STATUS_VAL.Success, msg);
			}
			if (edxLabInformationDT.isLabIsCreateSuccess() && edxLabInformationDT.getJurisdictionName() != null) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.JURISDICTION_DERIVED);
			}
			if (edxLabInformationDT.isLabIsCreateSuccess() && edxLabInformationDT.getJurisdictionName() == null) {
				edxLabInformationDT.setSystemException(false);
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_JURISDICTION_DERIVED);
			}
			if (edxLabInformationDT.isLabIsCreateSuccess() && edxLabInformationDT.getProgramAreaName() != null) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.PROG_AREA_DERIVED);
			}
			if (edxLabInformationDT.isLabIsCreateSuccess()&& edxLabInformationDT.getProgramAreaName() == null) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_PROG_AREA_DERIVED);
			}
			if (!edxLabInformationDT.isMatchingAlgorithm()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Failure,
						EdxELRConstants.NO_MATCHING_ALGORITHM);
			}
			if(edxLabInformationDT.isLabIsCreateSuccess()){
				String msg = EdxELRConstants.LAB_CREATE_SUCCESS.replace("%1", id);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,msg);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,EdxELRConstants.DOC_CREATE_SUCCESS);
			}
			if(edxLabInformationDT.isLabIsUpdateSuccess()){
				String msg = null;
				if(edxLabInformationDT.isLabIsUpdateDRRQ())
					msg = EdxELRConstants.LAB_UPDATE_SUCCESS_DRRQ.replace("%1", id);
				else if(edxLabInformationDT.isLabIsUpdateDRSA())
					msg = EdxELRConstants.LAB_UPDATE_SUCCESS_DRSA.replace("%1", id);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,msg);
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,EdxELRConstants.DOC_CREATE_SUCCESS);
			}
			if (!edxLabInformationDT.isMissingOrderingProviderandFacility() && edxLabInformationDT.isMissingOrderingProvider()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MISSING_ORD_PROV);
			}
			if (!edxLabInformationDT.isMissingOrderingProviderandFacility() && edxLabInformationDT.isMissingOrderingFacility()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MISSING_ORD_FAC);
			}
			if (edxLabInformationDT.isMultipleOrderingProvider()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_PROVIDER);
			}
			if (edxLabInformationDT.isMultipleCollector()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_COLLECTOR);
			}
			if (edxLabInformationDT.isMultiplePrincipalInterpreter()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_INTERP);
			}
			if (edxLabInformationDT.isMultipleOrderingFacility()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_ORDERFAC);
			}
			if (edxLabInformationDT.isMultipleReceivingFacility()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_RECEIVEFAC);
			}
			if (edxLabInformationDT.isMultipleSpecimen()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.MULTIPLE_SPECIMEN);
			}
			if (!edxLabInformationDT.isEthnicityCodeTranslated()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.TRANSLATE_ETHN_GRP);
			}
			if (!edxLabInformationDT.isObsMethodTranslated()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.TRANSLATE_OBS_METH);
			}
			if (!edxLabInformationDT.isRaceTranslated()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.TRANSLATE_RACE);
			}
			if (!edxLabInformationDT.isSexTranslated()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.TRANSLATE_SEX);
			}
			if (edxLabInformationDT.isSsnInvalid()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.INFO_SSN_INVALID);
			}
			if (edxLabInformationDT.isNullClia()) {
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.NULL_CLIA);
			}
			if(edxLabInformationDT.isInvestigationSuccessfullyCreated()){
				String msg = EdxELRConstants.INV_SUCCESS_CREATED.replace("%1", edxLabInformationDT.getPublicHealthCaseUid()+"");
				setActivityDetailLog(delailList, edxLabInformationDT.getPublicHealthCaseUid()+"", STATUS_VAL.Success,msg);
			}
			if(edxLabInformationDT.isLabAssociatedToInv()){
				String msg = EdxELRConstants.LAB_ASSOCIATED_TO_INV.replace("%1", edxLabInformationDT.getPublicHealthCaseUid()+"");
				setActivityDetailLog(delailList, edxLabInformationDT.getPublicHealthCaseUid()+"", STATUS_VAL.Success,msg);
			}
			
			if(edxLabInformationDT.isNotificationSuccessfullyCreated()){
				String msg = EdxELRConstants.NOT_SUCCESS_CREATED.replace("%1", edxLabInformationDT.getNotificationUid()+"");
				setActivityDetailLog(delailList, edxLabInformationDT.getNotificationUid()+"", STATUS_VAL.Success,msg);
			}
			if(edxLabInformationDT.isInvestigationMissingFields()){
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.OFCI);
			}
			if(edxLabInformationDT.isNotificationMissingFields()){
				setActivityDetailLog(delailList, id, STATUS_VAL.Success,
						EdxELRConstants.OFCN);
			}
			edxLabInformationDT.getEdxActivityLogDT().setEDXActivityLogDTWithVocabDetails(delailList);
		} catch (Exception e) {
			String id = String.valueOf(edxLabInformationDT.getLocalId());
			ArrayList<Object> delailList = (ArrayList<Object>)edxLabInformationDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails();
			if (delailList == null) {
				delailList = new ArrayList<Object>();
			}
			return;
		}
	}
	
	private void setActivityDetailLog(ArrayList<Object> delailLogs,String id, STATUS_VAL status,String comment){
		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		edxActivityDetailLogDT.setRecordId(id);
		edxActivityDetailLogDT.setRecordType(EdxELRConstants.ELR_RECORD_TP);
		edxActivityDetailLogDT.setRecordName(EdxELRConstants.ELR_RECORD_NM);
		edxActivityDetailLogDT.setLogType(status.name());
		edxActivityDetailLogDT.setComment(comment);
		delailLogs.add(edxActivityDetailLogDT);
	}

	private ObservationVO getOrderedTest(LabResultProxyVO labResultProxyVO) {
		for (Iterator<ObservationVO> it = labResultProxyVO
				.getTheObservationVOCollection().iterator(); it.hasNext();) {
			ObservationVO obsVO = (ObservationVO) it.next();

			String obsDomainCdSt1 = obsVO.getTheObservationDT()
					.getObsDomainCdSt1();
			if (obsDomainCdSt1 != null
					&& obsDomainCdSt1
							.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD)) {
				return obsVO;

			}
		}
		return null;
	}
	private void checkSecurity(NBSSecurityObj nbsSecurityObj, EdxLabInformationDT edxLabInformationDT, String businessObject, String businessOperation, String programArea, String jurisdiction){
		boolean permission = false;
		if(businessObject.equals(NBSBOLookup.OBSERVATIONLABREPORT) && (programArea==null || jurisdiction==null)){
			permission = nbsSecurityObj.getPermission(businessObject, businessOperation, NBSOperationLookup.ANY, NBSOperationLookup.ANY);
			if(!permission){
				if(businessOperation!=null && businessOperation.equals(NBSOperationLookup.ADD)){
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					edxLabInformationDT.setCreateLabPermission(false);
				}
				else{
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					edxLabInformationDT.setUpdateLabPermission(false);
				}
			}
		}
		else if (businessObject.equals(NBSBOLookup.OBSERVATIONLABREPORT) && programArea!=null && jurisdiction!=null && !NBSOperationLookup.MARKREVIEWED.equals(businessOperation)){
			permission = nbsSecurityObj.getPermission(businessObject, businessOperation, programArea, jurisdiction);
			if(!permission){
				if(businessOperation!=null && businessOperation.equals(NBSOperationLookup.ADD)){
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					edxLabInformationDT.setCreateLabPermission(false);
				}
				else{
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					edxLabInformationDT.setUpdateLabPermission(false);
				}
			}
		}
		else if (NBSBOLookup.OBSERVATIONLABREPORT.equals(businessObject) && NBSOperationLookup.MARKREVIEWED.equals(businessOperation)){
			permission = nbsSecurityObj.getPermission(businessObject, businessOperation, programArea, jurisdiction);
			if(!permission){
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_12);
				edxLabInformationDT.setMarkAsReviewPermission(false);
			}
		}
		else if(NBSBOLookup.INVESTIGATION.equals(businessObject)){
			permission = nbsSecurityObj.getPermission(businessObject, businessOperation, programArea, jurisdiction);
			if(!permission){
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_9);
				edxLabInformationDT.setCreateInvestigationPermission(false);
			}
		}
		else if(NBSBOLookup.NOTIFICATION.equals(businessObject)){
			permission = nbsSecurityObj.getPermission(businessObject, businessOperation, programArea, jurisdiction);
			if(!permission){
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_10);
				edxLabInformationDT.setCreateNotificationPermission(false);
			}
		}
		if(!permission){
			throw new NEDSSSystemException("Error "+businessOperation+" "+businessObject+" while processing processing ELR due to insufficient permissions");
		}
	}

	private void requiredFieldError(String errorTxt,
			EdxLabInformationDT edxLabInformationDT) {
		if (errorTxt != null) {
			edxLabInformationDT	.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_5);
			if (edxLabInformationDT.getEdxActivityLogDT()
					.getEDXActivityLogDTWithVocabDetails() == null)
				edxLabInformationDT.getEdxActivityLogDT()
						.setEDXActivityLogDTWithVocabDetails(
								new ArrayList<Object>());
			setActivityDetailLog((ArrayList<Object>) edxLabInformationDT
					.getEdxActivityLogDT()
					.getEDXActivityLogDTWithVocabDetails(),
					String.valueOf(edxLabInformationDT.getLocalId()),
					STATUS_VAL.Failure, errorTxt);
			edxLabInformationDT.setInvestigationMissingFields(true);
			throw new NEDSSSystemException("MISSING REQUIRED FIELDS: "+errorTxt);
		}
	}
}
