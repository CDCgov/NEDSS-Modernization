package gov.cdc.nedss.page.ejb.pageproxyejb.bean;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao.NBSAttachmentNoteDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.InterventionUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.InterviewCaseUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.PageActProxyHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.PageCaseUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.CompareUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;


/**
 * PageProxyEJB stores the Public_health_case, Participations, act_relationship, nbs_case_answer,
 * NBS_CASE_answer_hist, act_entity, Pam_case_entity_hist. It also handles Change Condition.
 * Release 5.2/2017: added support for merge investigation cases
 *@since NBS4.0
 *@update: NBS 4.5
 * @author Pradeep Sharma
 * @Company: CSRA
 * @since: NBS5.2
 * @updatedByAuthor: Pradeep Sharma
 */
public class PageProxyEJB extends BMPBase implements javax.ejb.SessionBean {
	// For logging
	static final LogUtils logger = new LogUtils(PageProxyEJB.class.getName());
	private static final long serialVersionUID = 1L;


	PageProxyVO pageProxyVO = null;


	public PageProxyVO getPageProxyVO(String typeCd, long uid, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException{
		PageProxyVO pageProxyVO = null;

		try {
			
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE) ||typeCd.equalsIgnoreCase(NEDSSConstants.PRINT_CDC_CASE)  || typeCd.equalsIgnoreCase(NEDSSConstants.CASE_LITE)){
				PageCaseUtil pageCaseUtil= new PageCaseUtil();

				pageProxyVO=pageCaseUtil.getPageProxyVO(typeCd, uid,nbsSecurityObj );
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.INTERVIEW_CLASS_CODE) ){
				InterviewCaseUtil interviewCaseUtil = new InterviewCaseUtil();
				pageProxyVO=interviewCaseUtil.getPageProxyVO(uid,nbsSecurityObj );
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.VACCINATION_CLASS_CODE) ){
				InterventionUtil interventionUtil = new InterventionUtil();
				pageProxyVO=interventionUtil.getPageProxyVO(uid,nbsSecurityObj );
			}else {
				String fatalErrorString = "PageProxyEJB.getPageProxyVO:The typeCd is a required attribute. Please check!! typeCd defined is : "+typeCd;
				logger.fatal(fatalErrorString);
				throw new NEDSSSystemException(fatalErrorString);
			}
		} catch (RemoteException re) {
			logger.fatal("PageProxyEJB.getPageProxyVO: RemoteException:" + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage(),re);
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.getPageProxyVO: Exception: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return pageProxyVO;
	}
	public void transferOwnership(String typeCd, Long publicHealthCaseUID,
			PageActProxyVO pageProxyVO, String newJurisdictionCode,
			Boolean isExportCase, NBSSecurityObj nbsSecurityObj)
	throws RemoteException, javax.ejb.EJBException, NEDSSSystemException,
	NEDSSConcurrentDataException{
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		try {
			if (typeCd.equalsIgnoreCase(NEDSSConstants.CASE)) {
				pageCaseUtil.transferOwnership(publicHealthCaseUID,
						pageProxyVO, newJurisdictionCode, isExportCase,
						nbsSecurityObj);
			}
		}  catch (NEDSSConcurrentDataException ne) {
			logger.fatal("PageProxyEJB.transferOwnership: NEDSSConcurrentDataException: "+ ne.getMessage(),ne);
			throw new NEDSSSystemException(ne.getMessage(),ne);
		} 	catch (Exception e) {
			logger.fatal("PageProxyEJB.transferOwnership: Exception: "+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public void changeCondition(String typeCd, Long publicHealthCaseUID,
			PageActProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj)
	throws RemoteException, javax.ejb.EJBException, NEDSSSystemException{
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		try {
			if (typeCd.equalsIgnoreCase(NEDSSConstants.CASE)) {
				pageCaseUtil.changeCondition(publicHealthCaseUID,
						pageProxyVO,nbsSecurityObj);
			}
		}	catch (Exception e) {
			logger.fatal("PageProxyEJB.changeCondition: "+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	

	
		

	public long setPageProxyVO(String typeCd,PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException{
		
		Long pageProxyUid =null;
		try {
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE)){
				PageCaseUtil pageCaseUtil= new PageCaseUtil();
				pageProxyUid = pageCaseUtil.setPageActProxyVO(pageProxyVO,nbsSecurityObj);
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.INTERVIEW_CLASS_CODE) ){
				InterviewCaseUtil interviewCaseUtil = new InterviewCaseUtil();
				pageProxyUid=interviewCaseUtil.setPageActProxyVO(pageProxyVO,nbsSecurityObj );
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.VACCINATION_CLASS_CODE) ){
				InterventionUtil interventionUtil = new InterventionUtil();
				pageProxyUid=interventionUtil.setPageActProxyVO(pageProxyVO,nbsSecurityObj );
			}else {
				String fatalErrorString = "PageProxyEJB.setPageProxyVO:The typeCd is a required attribute. Please check!! typeCd defined is : "+typeCd;
				logger.fatal(fatalErrorString);
				throw new NEDSSSystemException(fatalErrorString);
			}
		} catch (RemoteException re) {
			logger.fatal("PageProxyEJB.setPageProxyVO: RemoteException: " + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage(),re);
		} catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("PageProxyEJB.setPageProxyVO: NEDSSConcurrentDataException: " + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (CreateException e) {
			logger.fatal("PageProxyEJB.setPageProxyVO: CreateException: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return pageProxyUid;
	}

/**
 * setPageProxyVOMerge: this method contains the same than setPageProxyVO but also it starts doing calling all the methods necessary for the merge, like:
 * deleting the losing investigation, moving the associations and creating the notes in nbs_note.
 * @param typeCd
 * @param pageProxyVO
 * @param supersededProxyVO
 * @param localIdSurvivor
 * @param localIdSuperseded
 * @param nbsSecurityObj
 * @return
 * @throws RemoteException
 * @throws EJBException
 * @throws NEDSSSystemException
 * @throws FinderException
 * @throws CreateException
 */
		

		public long setPageProxyVOMerge(String typeCd,PageProxyVO pageProxyVO, PageProxyVO supersededProxyVO, PageProxyVO survivorProxyOld,String localIdSurvivor,String localIdSuperseded, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, FinderException, CreateException, NEDSSAppException{
		Long pageProxyUid =null;
		try{
			//MERGE CODE: START
			PageCaseUtil caseUtil = new PageCaseUtil();
			Map<Object,Object> coinfectionMapToIgnore=null;
			if(pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null && supersededProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null){
				coinfectionMapToIgnore=caseUtil.setCoInfectionsAndEpiLinkIdForMerge((PageActProxyVO)pageProxyVO, (PageActProxyVO)supersededProxyVO, nbsSecurityObj);
			}else{
				caseUtil.mergeContactRecordForNonSTDCases((PageActProxyVO)pageProxyVO, (PageActProxyVO)supersededProxyVO, nbsSecurityObj);
			}
		    Long superSededPublicHealthCaseUid = supersededProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		    Long survivingPublicHealthCaseUid = pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		    //Associations
		    caseUtil.setAssociationsAfterMergeInvestigation(superSededPublicHealthCaseUid, survivingPublicHealthCaseUid, (PageActProxyVO)pageProxyVO, nbsSecurityObj);
		    
		    
		    logger.debug("setPageProxyVOMerge: superSededPublicHealthCaseUID is "+superSededPublicHealthCaseUid);
		    logger.debug("setPageProxyVOMerge: survivingPublicHealthCaseUid is "+survivingPublicHealthCaseUid);
			
			//Add new note for surviving and losing investigations:
			PublicHealthCaseVO survivorVO = ((PageActProxyVO) pageProxyVO).getPublicHealthCaseVO();
			PublicHealthCaseVO supersededVO = ((PageActProxyVO) supersededProxyVO).getPublicHealthCaseVO();
			
			//Update  lot number (epi-link ID) of the winning investigation will propagate down to the losing investigation’s related contacts/contact investigations
			if(supersededVO.getTheCaseManagementDT()!=null && supersededVO.getTheCaseManagementDT().getEpiLinkId()!=null &&
					survivorVO.getTheCaseManagementDT()!=null && survivorVO.getTheCaseManagementDT().getEpiLinkId()!=null){
				caseUtil.updateEpilink(supersededVO.getTheCaseManagementDT().getEpiLinkId(), survivorVO.getTheCaseManagementDT().getEpiLinkId(), supersededVO, survivorVO, coinfectionMapToIgnore, nbsSecurityObj);
			}
			 logger.debug("setPageProxyVOMerge: updateEpilink is processed correctly!!");
			   
			Long publicHealthCaseUidSurvivor = survivorVO.getThePublicHealthCaseDT().getUid();
			Long publicHealthCaseUidSuperseded = supersededVO.getThePublicHealthCaseDT().getUid();
	

			
			caseUtil.addNewNoteForSurvivingInvestigation(nbsSecurityObj, publicHealthCaseUidSurvivor,publicHealthCaseUidSuperseded, localIdSurvivor, localIdSuperseded);
			 logger.debug("setPageProxyVOMerge: addNewNoteForSurvivingInvestigation is processed correctly!!");
			//Change initial interview to re-interview
			
			CompareUtil.changeInitialInterviewToReInterview(survivorProxyOld, supersededProxyVO);
			 logger.debug("setPageProxyVOMerge: changeInitialInterviewToReInterview is processed correctly!!");

			//MERGE CODE: END
			/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
			 * ((PageActProxyVO)pageProxyVO).setRenterant(true);
			 */
		
			PageCaseUtil pageCaseUtil= new PageCaseUtil();
			pageProxyUid = pageCaseUtil.setPageActProxyVO(pageProxyVO,nbsSecurityObj);
			 logger.debug("setPageProxyVOMerge: setPageActProxyVO is processed correctly for surviving investigation!!");
		
			caseUtil.deletePageProxy(superSededPublicHealthCaseUid,true, nbsSecurityObj );
			 logger.debug("setPageProxyVOMerge: deletePageProxy is processed correctly for superseded investigation!!");
			 
			 /*PKS : NOT REQUIRED
			  * pageCaseUtil.updateContactsForMergeInvestigation(superSededPublicHealthCaseUID, survivingPublicHealthCaseUid,((PageActProxyVO)pageProxyVO).getTheParticipationDTCollection());
			  */
			
		} catch (RemoteException re) {
			logger.fatal("PageProxyEJB.setPageProxyVO: RemoteException: " + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage(),re);
		} catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("PageProxyEJB.setPageProxyVO: NEDSSConcurrentDataException: " + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (CreateException e) {
			logger.fatal("PageProxyEJB.setPageProxyVO: CreateException: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.setPageProxyVO: Exception: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return pageProxyUid;
	}

	

	public Long setPageProxyWithAutoAssoc(String typeCd,PageProxyVO pageProxyVO, Long observationUid, String observationTypeCd, String processingDecision, NBSSecurityObj nBSSecurityObj) 
	throws javax.ejb.EJBException, javax.ejb.CreateException, java.rmi.RemoteException, NEDSSSystemException, 
	NEDSSConcurrentDataException{
		Long publicHealthCaseUID=null;	
		try {
			PageCaseUtil pageCaseUtil= new PageCaseUtil();

			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE)){
				publicHealthCaseUID=pageCaseUtil.setPageProxyWithAutoAssoc(pageProxyVO,observationUid,observationTypeCd, processingDecision, nBSSecurityObj );
			}
		} catch (RemoteException re) {
			logger.fatal("PageProxyEJB.setPageProxyWithAutoAssoc:RemoteException: " + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage());
		} catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("PageProxyEJB.setPageProxyWithAutoAssoc: NEDSSConcurrentDataException: " + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (CreateException e) {
			logger.fatal("PageProxyEJB.setPageProxyWithAutoAssoc: CreateException: " + e.getMessage());
			throw new NEDSSSystemException(e.getMessage());
		}
		catch (Exception re) {
			logger.fatal("PageProxyEJB.setPageProxyWithAutoAssoc:Exception: " + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage());
		}
		return publicHealthCaseUID;
	} 

	public Map<Object,Object> deletePageProxy(String typeCd,Long uid,   NBSSecurityObj nbsSecurityObj) 
	throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
	javax.ejb.FinderException, NEDSSConcurrentDataException{
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		try {
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE)){
				returnMap=pageCaseUtil.deletePageProxy(uid,false, nbsSecurityObj );
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.INTERVIEW_CLASS_CODE) ){
				InterviewCaseUtil interviewCaseUtil = new InterviewCaseUtil();
				returnMap=interviewCaseUtil.deleteInterview(uid,nbsSecurityObj);
			}else if(typeCd.equalsIgnoreCase(NEDSSConstants.VACCINATION_CLASS_CODE) ){
				InterventionUtil interventionUtil = new InterventionUtil();
				returnMap=interventionUtil.deleteIntervention(uid,nbsSecurityObj);
			}else {
				String fatalErrorString = "PageProxyEJB.deletePageProxy:The typeCd is a required attribute. Please check!! typeCd defined is : "+typeCd;
				logger.fatal(fatalErrorString);
				throw new NEDSSSystemException(fatalErrorString);
			}
		} catch (RemoteException re) {
			logger.fatal("Remote exception while deleting event: "+typeCd +" with event_uid= "+uid+" :" + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage(),re);
		} catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("NEDSSConcurrentDataException while deleting event: "+typeCd +" with event_uid= "+uid+" :" + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (CreateException ce) {
			logger.fatal("PageProxyEJB.deletePageProxy:NEDSSSystemException : " + ce.getMessage(),ce);
			throw new NEDSSSystemException(ce.getMessage(),ce);
		}catch (Exception e) {
			logger.fatal("PageProxyEJB.deletePageProxy: Exception: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return returnMap;
	}


	public Map<Object,Object> checkAssociationBeforeDelete(String typeCd, Long uid,   NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			javax.ejb.FinderException, NEDSSConcurrentDataException{
				logger.debug("typeCd: "+typeCd+", uid: "+uid);
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				try {
					PageActProxyHelper pageActProxyHelper = new PageActProxyHelper();
					returnMap = pageActProxyHelper.countInvestigationAssociationWithEvent(uid,typeCd,nbsSecurityObj);
				} catch (Exception e) {
					logger.fatal("PageProxyEJB.checkAssociationBeforeDelete: typeCd: "+typeCd+", uid: "+uid+ ", Exception Message: " + e.getMessage(),e);
					throw new NEDSSSystemException(e.getMessage(),e);
				}
				return returnMap;
			}
	
	public Long exportOwnership(String typeCd, NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) 
	throws javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException, java.rmi.RemoteException{
		Long publicHealthCaseUid= null;
		PageCaseUtil pageCaseUtil= new PageCaseUtil();

		try {
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE)){
				publicHealthCaseUid=pageCaseUtil.exportOwnership(notProxyVO,newJurisdictionCode, nbsSecurityObj );
			}
		}catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("PageProxyEJB.exportOwnership:NEDSSConcurrentDataException: " + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.exportOwnership:Exception: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return publicHealthCaseUid;
	}

    /**
     * setChangeConditionInNewCase - logically delete old case and create a new case with changed condition
     * @param type code = CASE
     * @param publicHealthCase to logically delete
     * @param pageProxyVO
     * @param newConditionCd
     * @param newConditionDesc
     * @param securityObj
     * @return new publicHealthCaseUID
     */
	public Long setChangeConditionInNewCase(String typeCd, Long oldPublicHealthCaseUID,	PageActProxyVO newPageProxyVO, NBSSecurityObj nbsSecurityObj)
	throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException {
		//User needs Add Investigation Permission for the Program Area
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.ADD,
				newPageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
						logger.fatal("no add permissions for changing condition");
							throw new NEDSSSystemException(
							"NO ADD PERMISSIONS for changing condition");
		}
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		Long pageProxyUid =null;
		try {
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CASE)){
				//create a new case from the old with the new condition
				pageProxyUid = pageCaseUtil.setPageActProxyVO(newPageProxyVO,nbsSecurityObj);
				//add the associations (act relationships)
				pageCaseUtil.setAssociations(pageProxyUid, newPageProxyVO, nbsSecurityObj);
				//re-link the contact records with new investigation, pass PA and Juris in case ProgramArea changed 
				pageCaseUtil.updateContactsForChangeCondition(oldPublicHealthCaseUID,pageProxyUid,
						newPageProxyVO.getTheParticipationDTCollection(), newPageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
						newPageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd());
				//update the parent case for any notes and/or attachments
				pageCaseUtil.updateNotesForChangeCondition(oldPublicHealthCaseUID,pageProxyUid);
				pageCaseUtil.updateAttachmentsForChangeCondition(oldPublicHealthCaseUID,pageProxyUid);
				//physically remove the associations from the old case
				pageCaseUtil.deleteExistingAssociations(oldPublicHealthCaseUID, nbsSecurityObj);
				//logically delete the old case(moved to action class 9/11/2012)
				//pageCaseUtil.deletePageProxy(oldPublicHealthCaseUID, nbsSecurityObj);
			}else{
				//in future releases, the type_Cd will be extended to cover other Objects like Entities and other act Objects.
				logger.error("PageProxyEJB.setChangeConditionInNewCase: "+oldPublicHealthCaseUID);
			}
		} catch (RemoteException re) {
			logger.fatal("PageProxyEJB.setChangeConditionInNewCase: RemoteException: " + re.getMessage(),re);
			throw new NEDSSSystemException(re.getMessage(),re);
		} catch (NEDSSConcurrentDataException ncde) {
			logger.fatal("PageProxyEJB.setChangeConditionInNewCase: NEDSSConcurrentDataException: " + ncde.getMessage(),ncde);
			throw new NEDSSSystemException(ncde.getMessage(),ncde);
		} catch (CreateException ce) {
			logger.fatal("PageProxyEJB.setChangeConditionInNewCase: CreateException: " + ce.getMessage(),ce);
			throw new NEDSSSystemException(ce.getMessage(),ce);
		}
		return pageProxyUid;
	}
	
	public Long setNBSAttachment(NBSAttachmentDT nbsAttachmentDT, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException{
		// Used the View permission of Investigation.   Should we change it ?
		Long nbsCaseAttachmentUid = null;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.VIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)){
			logger.debug("no Edit  permissions for for the investigation");
			throw new NEDSSSystemException(
				"NO EDIT PERMISSIONS for the Investigation");
		}else{
			try{
				NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
				
				if(nbsAttachmentDT.isItNew()){
					nbsCaseAttachmentUid =  nbsAttachmentNoteDao.insertNbsAttachment(nbsAttachmentDT);
				}
			}catch(Exception e){
				logger.fatal("PageProxyEJB.setNBSAttachment:" + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			return nbsCaseAttachmentUid;
		}
		
	}
	public byte[] getNBSAttachment(Long nbsAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
	// Need to  see if we need the security 	
	  if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)) {
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is false");
			throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is true");
		try{
			NBSAttachmentNoteDAOImpl nBSAttachmentNoteDAO = new NBSAttachmentNoteDAOImpl();
			return nBSAttachmentNoteDAO.getNBSAttachment(nbsAttachmentUid);
		}catch(Exception e){
			logger.fatal("PageProxyEJB.getNBSAttachment:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
	}
	public void deleteNbsAttachment(Long nbsAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)) {
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is false");
			throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is true");

		NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao = new NBSAttachmentNoteDAOImpl();
		try{
			nbsAttachmentNoteDao.removeNbsAttachment(nbsAttachmentUid);
		}catch(Exception e){
			logger.fatal("PageProxyEJB.deleteNbsAttachment:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	


	public void updateEpilink (String currEpilink, String newEpilink, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
    {
		try {
			PageCaseUtil caseUtil = new PageCaseUtil();
			caseUtil.updateEpilink(currEpilink, newEpilink, null,null, null,nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.updateEpilink:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
	
	public long setAutoContactPageProxyVO(String typeCd,CTContactProxyVO cTContactProxyVO,PageProxyVO pageProxyVO,  NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException{
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		Long contactPageProxyUid =null;
		try {
			if(typeCd.equalsIgnoreCase(NEDSSConstants.CONTACT_AND_CASE)){
				contactPageProxyUid = pageCaseUtil.setContactAndPageActProxyVO(cTContactProxyVO,pageProxyVO, nbsSecurityObj);
			}else {
				
				String fatalErrorString = "setAutoContactPageProxyVO CONTACT_AND_CASE is a required attribute and should be set to CONTACT_AND_CASE. Please check!! typeCd defined is : "+typeCd;
				logger.fatal(fatalErrorString);
				throw new NEDSSSystemException(fatalErrorString);
			}
		} catch (EJBException e) {
			logger.fatal("PageProxyEJB.setAutoContactPageProxyVO: EJBException: " + e.getMessage(),e);
			throw new EJBException(e.getMessage(),e);
		} 
		return contactPageProxyUid;
	}
	/**
	 * Currently used to associate/disassociate interviews from the View Interview Associate Investigations button.
	 * @param nbsEventUid
	 * @param actType
	 * @param associatedInvestigationList
	 * @param disassociatedInvestigationList
	 * @param nbsSecurityObj
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public void setEventCaseAssociations(Long nbsEventUid,
										String actType,
										ArrayList<Long> associatedInvestigationList,
										ArrayList<Long> disassociatedInvestigationList
										,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		//check Edit PHC permission
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT)) {
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is false");
			throw new NEDSSSystemException("NO Investigation Edit Permission");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is true");
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		try{
			pageCaseUtil.setInterviewCaseAssociations(nbsEventUid,
                    actType,
                    associatedInvestigationList,
                    disassociatedInvestigationList,
                    nbsSecurityObj);
		}catch(Exception e){
			logger.fatal("PageProxyEJB.setEventCaseAssociations:"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @param coninfectionId
	 * @param mprUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public Collection<Object> getCoinfectionSummaryVOByCoInfectionId(String coninfectionId,Long mprUid, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException{
		try{
			RetrieveSummaryVO retrieveSummaryVO =  new RetrieveSummaryVO();
			Collection<Object> coinfectionSummaryVOCollection = retrieveSummaryVO.getInvListForCoInfectionId(mprUid,coninfectionId);
			return coinfectionSummaryVOCollection;
		}catch(Exception ex){
			logger.fatal("PageProxyEJB.getCoinfectionSummaryVOByCoInfectionId:"+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * @param classCd
	 * @param typeCd
	 * @param uid
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws javax.ejb.FinderException
	 * @throws NEDSSConcurrentDataException
	 */
	public boolean checkForExistingNotificationsByCdsAndUid(String classCd, String typeCd, Long uid,   NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			javax.ejb.FinderException, NEDSSConcurrentDataException{
		logger.debug("ClassCd:"+classCd+", TypeCd: "+typeCd+", uid: "+uid);
		try {
			NNDMessageSenderHelper nndMessageSenderHelper= NNDMessageSenderHelper.getInstance();
		    	if(nndMessageSenderHelper.checkForExistingNotificationsByCdsAndUid(classCd,typeCd,uid))
		    		return true;
		    	else
		    		return false;
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.checkForExistingNotificationsByCdsAndUid: ClassCd:"+classCd+", TypeCd: "+typeCd+", uid: "+uid+ ", Exception Message: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	public Long setNBSNote(NBSNoteDT nbsNoteDT, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
    {
		try {
			PageCaseUtil caseUtil = new PageCaseUtil();
			return caseUtil.setNBSNote(nbsNoteDT, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.setNBSNote:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
	
	public PageProxyEJB(){}
	/**
	 * Container generated method
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.CreateException
	 */
	public void ejbActivate() {}
	public void ejbPassivate() {}
	public void ejbCreate() throws java.rmi.RemoteException,javax.ejb.CreateException {}
	public void ejbRemove() {}
	public void setSessionContext(SessionContext sc)throws java.rmi.RemoteException, javax.ejb.EJBException {}

	
	/**
	 * checkForExistingNotificationsByPublicHealthUid: returns true if there's any notification associated to the investigation
	 * @param uid
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws javax.ejb.FinderException
	 * @throws NEDSSConcurrentDataException
	 */
	public boolean checkForExistingNotificationsByPublicHealthUid(Long uid,   NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			javax.ejb.FinderException, NEDSSConcurrentDataException{
		logger.debug("uid: "+uid);
		try {
			NNDMessageSenderHelper nndMessageSenderHelper= NNDMessageSenderHelper.getInstance();
		    if(nndMessageSenderHelper.checkForExistingNotificationsByUid(uid))
		    		return true;
		    	else
		    		return false;
		} catch (Exception e) {
			logger.fatal("PageProxyEJB.checkForExistingNotificationsByCdsAndUid: "+uid+ ", Exception Message: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

}