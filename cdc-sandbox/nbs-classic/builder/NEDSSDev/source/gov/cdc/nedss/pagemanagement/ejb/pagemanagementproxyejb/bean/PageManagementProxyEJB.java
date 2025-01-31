package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean;


//Import Statements
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NedssAppLogException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSPageDAOImpl;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.helper.PageManagementHelper;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageMetadataVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dao.WaRuleMetadataDaoImpl;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.RuleElementDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.pagemanagement.wa.dao.ConditionDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dao.TemplatesDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.NbsBusObjMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.proxy.ejb.epilink.dao.EpilinkActivityLogDaoImpl;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DsmLogSearchDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.managerules.ManageRulesAction;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.RuleManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managerules.ManageRulesForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.File;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 *
 */
public class PageManagementProxyEJB extends BMPBase implements javax.ejb.SessionBean {
	// For logging
	static final LogUtils logger = new LogUtils(PageManagementProxyEJB.class.getName());
	
	private static final long serialVersionUID = 1L;
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	  private SessionContext cntx  ;
	 
	  
	/**
	 * updateAllRules(): this method updates all the rules from the pageUid received as a parameter, or all the rules from all the pages if pageUid is null.
	 * @param pageUid
	 * @param nbsSecurityObj
	 */
	  
	public void updateAllRules(Long pageUid, NBSSecurityObj nbsSecurityObj){

		WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
		ArrayList<Long> pagesUid = new ArrayList<Long>();
		
		if(pageUid==null)//update all the rules from all the pages
			pagesUid=waRuleMetadao.selectPageUidsFromWaRuleMetada();
		else
			pagesUid.add(pageUid);
		
		for(int i=0; i<pagesUid.size(); i++){
			
			pageUid=pagesUid.get(i);
			
			try{		   	
		    	
				//Retrieve RULE metadata
				Collection<Object> ruleMetadataColl = waRuleMetadao.selectWaRuleMetadataDTByTemplate(pageUid);
				
			    for (Iterator iterator = ruleMetadataColl.iterator(); iterator.hasNext();) {
			    	WaRuleMetadataDT waRuleMetadataDT = (WaRuleMetadataDT) iterator.next();
			    	

			    	


			    	updateRuleMetadataDT(waRuleMetadataDT, nbsSecurityObj);
			    }
		    
			} catch (Exception e) {
				logger.fatal("PageManagementProxyEJB.updateAllRules: ",e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
		}
	}
	 

	public PageManagementProxyEJB(){}

	public PageManagementProxyVO publishPage(WaTemplateDT inWaTemplateDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException {
		

		updateAllRules(inWaTemplateDT.getWaTemplateUid(), nbsSecurityObj);
		
		
		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.publishPage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.publishPage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
		
		PageManagementProxyVO pageManagementProxyVO = new PageManagementProxyVO();
		PageManagementHelper pageManagementHelper = new PageManagementHelper();

		try {
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			
			//Get the waTemplateDT for the page about to be published
			//Mark as published and increment publish_version_nbr
			WaTemplateDT waTemplateDT = pageManagementDAOImpl.findWaTemplate(inWaTemplateDT.getWaTemplateUid());

			if ((waTemplateDT.getPublishIndCd() != null && waTemplateDT.getPublishIndCd().equalsIgnoreCase(NEDSSConstants.TRUE)) && (waTemplateDT.getTemplateType() != null && waTemplateDT.getTemplateType().equals("Published"))){
				// Already published, shouldn't be re-published - a new draft should be created first
				throw new NEDSSSystemException("PageManagementProxyEJB.publishPage:  Page already published, waTemplateUid = " + waTemplateDT.getWaTemplateUid());
			}
			
			pageManagementDAOImpl.deleteDPatientQuestionsFromWaRdbMetadata();
			
			//Retrieve UI, NND and RDB metadata
			Collection<Object> pageElementVOCollection = pageManagementDAOImpl.getPageElementVOCollection(inWaTemplateDT.getWaTemplateUid(),false);
			
			//Retrieve RULE metadata
			WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
			Collection<Object> ruleMetadataColl = waRuleMetadao.selectWaRuleMetadataDTByTemplate(inWaTemplateDT.getWaTemplateUid());
			pageManagementProxyVO.setWaRuleMetadataDTCollection(ruleMetadataColl);
			Collection<Object> pageCondMappingDTColl = pageManagementDAOImpl.getPageCondMappingColl(inWaTemplateDT.getWaTemplateUid());
			pageManagementProxyVO.setPageCondMappingColl(pageCondMappingDTColl);			

			//Retrieve Wa questions that are available for the WaUiMetadataDT's for this page
			pageElementVOCollection = pageManagementDAOImpl.retrieveWaQuestionForPageElements(pageElementVOCollection);

			//Populate pageManagementProxyVO for xml/jsp page creation process
			pageManagementProxyVO.setThePageElementVOCollection(pageElementVOCollection);

			waTemplateDT.setPublishIndCd(NEDSSConstants.TRUE);
			waTemplateDT.setTemplateType("Published");
			waTemplateDT.setLastChgUserId(currentUser);
			waTemplateDT.setVersionNote(inWaTemplateDT.getVersionNote());
			//TO DO - need to revisit this part - we might have to get the published version from the previous published page
			if (waTemplateDT.getPublishVersionNbr() != null) {
				//Check to see if condition already exists in published state in Wa_template - if so, move to history
				Collection<Object> existingWaTemplateUids = pageManagementDAOImpl.findWaTemplateUidByPageNm(waTemplateDT.getTemplateNm());				

				if (existingWaTemplateUids != null) {
					for (Iterator<Object> i = existingWaTemplateUids.iterator(); i.hasNext(); ) {
						Long templateByCondUid = (Long)i.next(); 
						if ( templateByCondUid.compareTo(inWaTemplateDT.getWaTemplateUid()) != 0 ) {
							WaTemplateDT waDT = pageManagementDAOImpl.findWaTemplate(templateByCondUid);
							if(waDT.getPublishVersionNbr() != null){
							    //setting the publish version control number here
							    waTemplateDT.setPublishVersionNbr(waDT.getPublishVersionNbr()+1);
							    //Moving to History
								pageManagementDAOImpl.movePublishedPageToHistory(templateByCondUid, pageManagementProxyVO);
							}							 
						    
						}
					}
					
				}
			} else {
				waTemplateDT.setPublishVersionNbr(1);
			}
			pageManagementProxyVO.setWaTemplateDT(waTemplateDT);

			//TODO:  Get xmlPayload and jspPayload from generateXMLandJSP functionality
			
			String xmlPayload = pageManagementHelper.generateXMLPayLoad(pageManagementProxyVO,inWaTemplateDT.getWaTemplateUid());
			waTemplateDT.setXmlPayload(xmlPayload);
			File jspPayloadFile = pageManagementHelper.generateJSPPayLoad(pageManagementProxyVO,inWaTemplateDT.getWaTemplateUid());
   		    byte[] jspPayload = pageManagementHelper.getBytesFromFile(jspPayloadFile);
			
   		     ConditionDAOImpl conditionDAO = new ConditionDAOImpl();
   		     boolean publishedBefore = conditionDAO.pagePublishedBefore(waTemplateDT.getFormCd());
			pageManagementDAOImpl.updateWaTemplate(waTemplateDT);
			// If we choose not to restart the server to get the new pages for legacy update the condition table else do not update
			
			    /* This is for the defect 20105 -  As per the design discussion we decided to remove the condition code from the
			     * page condition mapping table if the same condition code is associated with other pages.
			     * This will help in case of clone and republishing the draft after creating a clone out of it.  
			     * changed on 10/20/2010.		      
			     */
			     
			    Collection<Object> pageCondColl = pageManagementProxyVO.getPageCondMappingColl();
			    String oldFormcd = null;
			    String newFormCd =  waTemplateDT.getFormCd();
			    if(pageCondColl != null){
			    	Iterator<Object> iter = pageCondColl.iterator();
			    	while(iter.hasNext()){
			    		PageCondMappingDT pdt  = (PageCondMappingDT)iter.next();
			    		String pConditionCd = pdt.getConditionCd();
			    		//gst added bus obj so contact and interview pages don't cause deletions
			    		ArrayList<Object> pdtNewColl  =  (ArrayList<Object>)pageManagementDAOImpl.getPageCondMappingDTCollByCond(pConditionCd, waTemplateDT.getBusObjType());
			    		if(pdtNewColl != null && pdtNewColl.size()>0){
			    			int index = 0;
			    			
			    			while(index<pdtNewColl.size()){
			    				
			    				PageCondMappingDT pdtNew = (PageCondMappingDT)pdtNewColl.get(index);
					    		if(pdtNew.getWaTemplateUid().compareTo(pdt.getWaTemplateUid())!=0){
					    			WaTemplateDT oldWaTemplateDT = pageManagementDAOImpl.findWaTemplate(pdtNew.getWaTemplateUid());
					    			oldFormcd = oldWaTemplateDT.getFormCd();
					    			if(PortPageUtil.TEMPLATE_TYPE_PUBLISHED_WITH_DRAFT.equals(oldWaTemplateDT.getTemplateType())){
					    				pageManagementDAOImpl.deletePageConditionMapping(pConditionCd, pdtNew.getWaTemplateUid());
					    			}
					    		}
					    	index++;	
			    			}
					    		
			    			
			    		}
			    		
			    	}
			    }
			
			    //Publish Work Area (Wa) data to the NBS metadata tables
			
				pageManagementHelper.publishPageMetadataToNBS(pageManagementProxyVO, currentUser, jspPayload, publishedBefore);
				
				//update the pre-pop mapping in the question_lookup table and reset the cache
			if (oldFormcd != null && newFormCd != null && !oldFormcd.equals(newFormCd)) {
				pageManagementDAOImpl.updatePrepopMapping(oldFormcd, newFormCd);
				QuestionsCache.toPrePopFormMapping.remove(oldFormcd);
				QuestionsCache.fromPrePopFormMapping.remove(NEDSSConstants.LAB_FORM_CD);
			}
			//TODO:  Implement call to XML creation - need to keep in same transaction
		
			//Create Dynamic Data Source
				
		//	if(!propertyUtil.getDatabaseServerType().equalsIgnoreCase(NEDSSConstants.ORACLE_ID))
				if(pageManagementProxyVO.getWaTemplateDT()!=null){
					String dataMartName = pageManagementProxyVO.getWaTemplateDT().getDataMartNm();
					String type = pageManagementProxyVO.getWaTemplateDT().getBusObjType();
					Long templateUid = inWaTemplateDT.getWaTemplateUid();
					if(dataMartName!=null){
						dataMartName = "DM_"+type+"_"+dataMartName;
						pageManagementDAOImpl.createDataSourceDynamically(templateUid, dataMartName);
					}
				}
				if(pageManagementProxyVO.getWaTemplateDT()!=null){
					pageManagementDAOImpl.resetMetadataData();
				}
		}

		catch(Exception e) {
			logger.fatal("PageManagementProxyEJB.publishPage:  " + e.getMessage(),e);
			pageManagementHelper.deleteJspFiles(pageManagementProxyVO.getWaTemplateDT().getFormCd());
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		//TODO:  Implement call to PageManagement Log to log success/failure of publishing this page
		
		return pageManagementProxyVO;
	}


	public Long addPage(WaTemplateVO waTemplateVO,String actionMode,  NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																						 javax.ejb.EJBException,																									 																					   javax.ejb.CreateException, 
																						 NEDSSSystemException, 
																						 NEDSSConcurrentDataException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.addPage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.addPage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");
		
		Long waTemplateUid = null;		
		WaTemplateDT waTemplateDT =  waTemplateVO.getWaTemplateDT();
		
		if (waTemplateDT.getBusObjType()!=null && waTemplateDT.getBusObjType().equalsIgnoreCase("VAC")){
			 waTemplateDT.setMessageId(null);
		}
		
		Collection<Object> pageCondMappingColl = waTemplateVO.getWaPageCondMappingDTColl();
		try {
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();		
			waTemplateUid =  pageManagementDAOImpl.createWaTemplate(waTemplateDT);
			
			Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
			if(pageCondMappingColl != null && pageCondMappingColl.size()>0){
				Iterator<Object> iter = pageCondMappingColl.iterator();
				while(iter.hasNext()){
					PageCondMappingDT pageCondMappDT = (PageCondMappingDT)iter.next();
					pageCondMappDT.setWaTemplateUid(waTemplateUid);
					try{
						pageManagementDAOImpl.setPageCondiMapping(pageCondMappDT);
						if(actionMode != null && actionMode.equalsIgnoreCase(NEDSSConstants.CLONE_SUBMIT_ACTION)){
							pageManagementDAOImpl.deletePageConditionMapping(pageCondMappDT.getConditionCd(), waTemplateDT.getWaTemplateRefUid());
						}
					}catch(Exception e){
						logger.fatal("PageManagementProxyEJB.addPage: " +  e.getMessage(),e);
						throw new NEDSSSystemException(e.getMessage(),e);
					}
				}
			}
			boolean addInd = true; 
			pageManagementDAOImpl.initializeNewPageWithTemplate(waTemplateUid, waTemplateDT.getWaTemplateRefUid(),currentUser,addInd);
		} catch(Exception e) {
			logger.fatal("PageManagementProxyEJB.addPage: " +  e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		// Set the Rules if the template have them
		try{
			WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
			waRuleMetadao.setwaRuleMetadata(waTemplateDT.getWaTemplateRefUid(),waTemplateUid);
			
		
		}catch(Exception ex){
			logger.fatal("PageManagementProxyEJB.addPage: "+"from_WaTemplate uid :"+ waTemplateDT.getWaTemplateRefUid()+"to_WaTemplate uid :"+ waTemplateUid +  ex.toString(),ex);
			throw new NEDSSSystemException(ex.toString());		
		}
		try{
			if(waTemplateDT.getBusObjType()!=null && waTemplateDT.getBusObjType().equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)){
				
					PageManagementDAOImpl waRuleMetadao = new PageManagementDAOImpl();
					waRuleMetadao.importLDFMetadataAndData(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
					waRuleMetadao.inActivateLDFPageSetId(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE, null);
				
			}	/*
				 * hack for treatment, as business object does not match what is in
				 * the LDF meta data and LDF page set
				 */
			
		}catch(Exception ex){
				logger.fatal("exception while migrating LDF data from legacy page :"+waTemplateUid+" for form cd :"+waTemplateDT.getFormCd());
				throw new NEDSSSystemException(ex.toString());		
			}
		
		return waTemplateUid;

	}

	public Long savePublishedPageAsDraft(Long waTemplateDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																										  javax.ejb.EJBException,																									 																					   javax.ejb.CreateException, 
																										  NEDSSSystemException, 
																										  NEDSSConcurrentDataException {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			logger.info("PageManagementProxyEJB.savePublishedPageAsDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false");
			throw new NEDSSSystemException("PageManagementProxyEJB.savePublishedPageAsDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false");
		}
		logger.info("PageManagementProxyEJB.savePublishedPageAsDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		Long waTemplateUid = null;		

		try {
			Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
			Timestamp currentTime = new Timestamp(new Date().getTime());

			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();

			//Get TemplateDT for Published Page
			WaTemplateDT waTemplateDT = pageManagementDAOImpl.findWaTemplate(waTemplateDTUid);
			
			//Get the collection of PageCondMapping table 

			//Update published page to indicate active draft is in place
			waTemplateDT.setTemplateType("Published With Draft");
			
			/** As per the defect 19299 and the further discussion with Jit , we have decided not to change the last change time.
			  Since only the TemplateType is changing (which is actually the status of the page) in this case, 
			  we decided to use the RecordStatusTime **/
			
			waTemplateDT.setRecordStatusTime(currentTime);
			waTemplateDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
			pageManagementDAOImpl.updateWaTemplate(waTemplateDT);
			
			//Create draft with same data from published page 
			waTemplateDT.setTemplateType("Draft");
			waTemplateDT.setPublishIndCd(NEDSSConstants.FALSE);
			waTemplateDT.setPublishVersionNbr(0);
			waTemplateDT.setAddTime(currentTime);
			waTemplateDT.setAddUserId(currentUser);
			waTemplateUid = pageManagementDAOImpl.createWaTemplate(waTemplateDT);
			
			pageManagementDAOImpl.initializePageCondMapping(waTemplateUid, waTemplateDTUid);			
			//Use metadata from published page to populate draft
			// Use the published version UID as the references UID in this method 
			pageManagementDAOImpl.initializeNewPageWithTemplate(waTemplateUid, waTemplateDTUid,currentUser,false);
			// getting the Rule Metadata for this draft.
			try{
				WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
				waRuleMetadao.setwaRuleMetadata(waTemplateDTUid,waTemplateUid);
			}catch(Exception ex){
				logger.fatal("PageManagementProxyEJB.savePublishedPageAsDraft: "+"from template_uid :" + waTemplateDTUid +"to template_uid :"+ waTemplateUid + ex.toString(),ex);
				throw new NEDSSSystemException(ex.toString(),ex);
			}			
			
		} catch(Exception e) {
			logger.fatal("PageManagementProxyEJB.addPage: "+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return waTemplateUid;

	}

	
	public Long savePageDraft(PageManagementProxyVO pageManagementProxyVO, String pageElementOrder, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																												 javax.ejb.EJBException, 
																												 javax.ejb.CreateException, 
																												 NEDSSSystemException, 
																												 NEDSSConcurrentDataException {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.savePageDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.savePageDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		WaTemplateDT waTemplateDT = pageManagementProxyVO.getWaTemplateDT();
		Collection<Object> pageElementVOCollection = pageManagementProxyVO.getThePageElementVOCollection();
		
		try {
			PageManagementHelper pageManagementHelper = new PageManagementHelper();
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			// For save as template  create a new Wa Template row for draft update the existing row  
			pageManagementDAOImpl.updateWaTemplate(waTemplateDT);	
			pageManagementHelper.copyDataMartRepeatNumberFromRepeatingSubsectionsToQuestion(pageElementVOCollection);
			
			
			//Sort/order UI metadata *** this is not required for SaveTemplate because 
			if(pageElementOrder != null){
				pageElementVOCollection = pageManagementHelper.orderAndSyncPageElements(pageElementVOCollection, pageElementOrder);
			}
				//Remove previous metadata for this template_uid   
				pageManagementDAOImpl.deleteMetadataByWaTemplateUid(waTemplateDT.getWaTemplateUid());
				
				// Need to keep the update method for pageCondMappingDT Collection ** 4.1 - Do we really need it on Savedraft? This is getting 
				// updated on update page details
				if(pageManagementProxyVO.getPageCondMappingColl() != null){
					Iterator<Object> iter = pageManagementProxyVO.getPageCondMappingColl().iterator();
					while(iter.hasNext()){
						PageCondMappingDT dt = (PageCondMappingDT)iter.next();
						pageManagementDAOImpl.setPageCondiMapping(dt);
					}
				}
				
			Iterator<Object> pageElementIter = pageElementVOCollection.iterator();
			while (pageElementIter.hasNext()) {
				PageElementVO pageElementVO = (PageElementVO)pageElementIter.next();
				WaUiMetadataDT waUiMetadataDT = pageElementVO.getWaUiMetadataDT();
				WaNndMetadataDT waNndMetadataDT = pageElementVO.getWaNndMetadataDT();
				WaRdbMetadataDT waRdbMetadataDT = pageElementVO.getWaRdbMetadataDT();
				
				Long waUimetadataUid = null;
				if (waUiMetadataDT != null){
					try{
						waUimetadataUid = pageManagementDAOImpl.createWaUiMetadata(waUiMetadataDT);
					   }catch(Exception e){
						   logger.error("Error while creating the waUImetadata  on Label "+waUiMetadataDT.getQuestionLabel()+ e.getMessage());
					   }
					
				}else { // Should always have a WaUiMetadataDT
					String error = "PageManagementProxyEJB.savePageDraft:  Error, missing WaUiMetadataDT while saving page draft for WaTemplate uid:  " + waTemplateDT.getWaTemplateUid();
					logger.error(error);
					throw new NEDSSSystemException(error);
				}
					
				if (waRdbMetadataDT != null){
					waRdbMetadataDT.setWaUiMetadataUid(waUimetadataUid);
					try{
						pageManagementDAOImpl.createWaRdbMetadata(waRdbMetadataDT);
					 }catch(Exception e){
						   logger.error("Error while creating the waRdbmetadata  on Label "+waUiMetadataDT.getQuestionLabel()+ e.getMessage());
					   }
				}

				if (waNndMetadataDT != null){
					waNndMetadataDT.setWaUiMetadataUid(waUimetadataUid);
					try{
						pageManagementDAOImpl.createWaNndMetadata(waNndMetadataDT);
					}catch(Exception e){
						   logger.error("Error while creating the waNndmetadata  on Label "+waUiMetadataDT.getQuestionLabel()+ e.getMessage());
					}
				}
			}
			
			//Retrieve RULE metadata
			WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
			waRuleMetadao.createDraftWaRuleMetadata(pageManagementProxyVO.getWaRuleMetadataDTCollection());
		
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.savePageDraft: WaTemplate uid: " + waTemplateDT.getWaTemplateUid() + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return  waTemplateDT.getWaTemplateUid();
	}

	public Long saveTemplate(Long waTemplateUid,String templNm, String desc, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																												 javax.ejb.EJBException, 
																												 javax.ejb.CreateException, 
																												 NEDSSSystemException, 
																												 NEDSSConcurrentDataException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.saveTemplate:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.saveTemplate:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		try{
			PageManagementProxyVO pmProxyVO = getPageDraft(waTemplateUid,false,nbsSecurityObj);
			//get XML if not there (draft saved as template)
			if (pmProxyVO.getWaTemplateDT().getXmlPayload().equals("XML Payload")) {
				//need the generated XML stored
				MarshallPageXML marshallPageXML = new MarshallPageXML();
				marshallPageXML.GeneratePageXMLFile(pmProxyVO, "");
			}
			
			pmProxyVO.getWaTemplateDT().setTemplateType(NEDSSConstants.TEMPLATE);
			pmProxyVO.getWaTemplateDT().setTemplateNm(templNm);
			pmProxyVO.getWaTemplateDT().setDescTxt(desc);
			WaTemplateDT waTemplateDT = pmProxyVO.getWaTemplateDT();
			
			//savePageDraft(pmProxyVO,null,nbsSecurityObj);
			java.util.Date startDate= new java.util.Date();
			Timestamp aAddTime = new Timestamp(startDate.getTime());
			waTemplateDT.setLastChgTime(aAddTime);
			waTemplateDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
			waTemplateDT.setAddTime(aAddTime);
			waTemplateDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
			waTemplateDT.setFormCd(null);
			waTemplateDT.setConditionCd(null);
			waTemplateDT.setPublishIndCd(NEDSSConstants.FALSE);
			waTemplateDT.setSourceNm(propertyUtil.getStateName());
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			// create a new row in the wa_template table 
			waTemplateUid = pageManagementDAOImpl.createWaTemplate(waTemplateDT);
			Collection<Object> pageElementVOCollection = pmProxyVO.getThePageElementVOCollection();
			PageManagementHelper pageManagementHelper = new PageManagementHelper();
			
			pageManagementHelper.copyDataMartRepeatNumberFromRepeatingSubsectionsToQuestion(pageElementVOCollection);
			
			Iterator<Object> pageElementIter = pageElementVOCollection.iterator();
			while (pageElementIter.hasNext()) {
				PageElementVO pageElementVO = (PageElementVO)pageElementIter.next();
				WaUiMetadataDT waUiMetadataDT = pageElementVO.getWaUiMetadataDT();
				WaNndMetadataDT waNndMetadataDT = pageElementVO.getWaNndMetadataDT();
				WaRdbMetadataDT waRdbMetadataDT = pageElementVO.getWaRdbMetadataDT();
				WaQuestionDT waQuestionDT =pageElementVO.getWaQuestionDT();
				////////////////////////////////////////////////////////////////////////////////
				// GST commented out 2016-06-02
				// Incorrectly added by swati in revision 2149
				//if(waQuestionDT != null){
				//	waUiMetadataDT.setCoinfectionIndCd(waQuestionDT.getCoInfQuestion());
				//}else if(waUiMetadataDT.getQuestionIdentifier()!= null){
				//	logger.debug("Retrieve waQuestion for : "  +waUiMetadataDT.getQuestionIdentifier());
				//	waQuestionDT = pageManagementDAOImpl.findWaQuestion(waUiMetadataDT.getQuestionIdentifier());
				//	waUiMetadataDT.setCoinfectionIndCd(waQuestionDT.getCoInfQuestion());
				//}
				///////////////////////////////////////////////////////////////////////////////
				Long waUimetadataUid = null;
				// For save as template this is the new template Uid 
					waUiMetadataDT.setWaTemplateUid(waTemplateUid);
					if (waNndMetadataDT !=null)
						waNndMetadataDT.setWaTemplateUid(waTemplateUid);
					if(waRdbMetadataDT != null)
						waRdbMetadataDT.setWaTemplateUid(waTemplateUid);
				
					
				if (waUiMetadataDT != null){
					try{
						waUimetadataUid = pageManagementDAOImpl.createWaUiMetadata(waUiMetadataDT);
					}catch(Exception e){
						logger.error("Error from WAUI Metadata Table while saving the Label : "+waUiMetadataDT.getQuestionLabel()+e.getMessage());
						throw new NEDSSSystemException(e.getMessage());
					}
					
				}else { // Should always have a WaUiMetadataDT
					logger.error("PageManagementProxyEJB.savePageDraft:  Error, missing WaUiMetadataDT while saving page draft for WaTemplate uid:  " + waTemplateDT.getWaTemplateUid());
				}
					
				if (waRdbMetadataDT != null){
					waRdbMetadataDT.setWaUiMetadataUid(waUimetadataUid);
					try{
						pageManagementDAOImpl.createWaRdbMetadata(waRdbMetadataDT);
					}catch(Exception e){
						logger.error("Error from WARDB Metadata Table while saving the Label : "+waUiMetadataDT.getQuestionLabel()+e.getMessage());
						throw new NEDSSSystemException(e.getMessage(),e);
					}	
				}

				if (waNndMetadataDT != null){
					waNndMetadataDT.setWaUiMetadataUid(waUimetadataUid);
					try{
						pageManagementDAOImpl.createWaNndMetadata(waNndMetadataDT);
					}catch(Exception e){
						logger.error("Error from WANND Metadata Table while saving the Label : "+waUiMetadataDT.getQuestionLabel()+e.getMessage());
						throw new NEDSSSystemException(e.getMessage(),e);
					}
				}
			}
			//Setting the rule 
			try{
				Collection<Object> ruleColl = new ArrayList<Object>();
				if(pmProxyVO.getWaRuleMetadataDTCollection() != null){
					Iterator<Object> iter = pmProxyVO.getWaRuleMetadataDTCollection().iterator();
					while(iter.hasNext()){
						WaRuleMetadataDT waRuleMetaDT = (WaRuleMetadataDT)iter.next();
						waRuleMetaDT.setWaTemplateUid(waTemplateUid);
						ruleColl.add(waRuleMetaDT);
					}
				}
				
				WaRuleMetadataDaoImpl waRuleMetadataDao = new WaRuleMetadataDaoImpl();
				waRuleMetadataDao.insertWaRuleMetadataDTColl(ruleColl,true);
			}catch (Exception ex){
				logger.error("Error in setting the rule Metadata");
				throw new NEDSSSystemException(ex.getMessage(),ex);
			}
		}catch(Exception e){
			logger.fatal("PageManagementProxyEJB.saveTemplate:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}

		return  waTemplateUid;
	}

	public void inactivatePage(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																		  			     javax.ejb.EJBException, 
																						 javax.ejb.CreateException, 
																						 NEDSSSystemException, 
																						 NEDSSConcurrentDataException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.inactivatePage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.inactivatePage:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		try {
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			WaTemplateDT waTemplateDT = pageManagementDAOImpl.findWaTemplate(waTemplateUid);
			waTemplateDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Inactive);
			waTemplateDT.setRecordStatusTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
			pageManagementDAOImpl.updateWaTemplate(waTemplateDT);
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.inactivatePage: WaTemplate uid: " + waTemplateUid + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}

	}

/**
 * This method is to delete the Page Draft.  It is a physical delete.
 * @param waTemplateUid
 * @param nbsSecurityObj
 * @throws java.rmi.RemoteException
 * @throws javax.ejb.EJBException
 * @throws javax.ejb.CreateException
 * @throws NEDSSSystemException
 * @throws NEDSSConcurrentDataException
 */
	public Long deleteDraft(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																					  javax.ejb.EJBException, 
																					  javax.ejb.CreateException, 
																					  NEDSSSystemException, 
																					  NEDSSConcurrentDataException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.deleteDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.deleteDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");
		
		Long prevTemplateUid = null;

		try{
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		
		String templateNm = pageManagementDAOImpl.findWaTemplate(waTemplateUid).getTemplateNm();
		Collection<Object> templateUidsByTemplateNm = pageManagementDAOImpl.findWaTemplateUidByPageNm(templateNm);

		if (templateUidsByTemplateNm!=null) {
			for (Iterator<Object> i = templateUidsByTemplateNm.iterator(); i.hasNext(); ) {
				Long templateByCondUid = (Long)i.next();
				  // The 
				if ( templateByCondUid.compareTo(waTemplateUid) != 0 ) {
					// Found another template for this condition - set its template_type back from 'Published with Draft' to 'Published'.
					prevTemplateUid = templateByCondUid;
					WaTemplateDT waTempDT  = pageManagementDAOImpl.findWaTemplate(templateByCondUid);
					// Setting this if condition because there could be other draft(cloned object) which has the version control number as 0
					//We do not want those to change it to published
					if(waTempDT!= null && waTempDT.getPublishVersionNbr() != null && waTempDT.getPublishVersionNbr()>0)
						pageManagementDAOImpl.setTemplateTypeToPublished(templateByCondUid);
				}
			}
		}

	    // Remove the ruleMetadata from for this template 
		WaRuleMetadataDaoImpl waDaoImpl = new WaRuleMetadataDaoImpl();
		waDaoImpl.deleteWaRuleMetadataForTemplate(waTemplateUid);
		//Remove the  metadata for this template_uid
		pageManagementDAOImpl.deleteMetadataByWaTemplateUid(waTemplateUid);
		
		//Then delete draft's wa_template row
		pageManagementDAOImpl.deleteTemplate(waTemplateUid);
		
		
		}catch(Exception e){
			logger.fatal("PageManagementProxyEJB.deleteDraft: "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return prevTemplateUid;


	}

	public PageManagementProxyVO getPageDraft(Long waTemplateUid,boolean pageBuilderInd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	                 																				    javax.ejb.EJBException, 
	                 																				    javax.ejb.CreateException, 
	                 																				    NEDSSSystemException, 
	                 																				    NEDSSConcurrentDataException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getPageDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.getPageDraft:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		PageManagementProxyVO pageManagementProxyVO = new PageManagementProxyVO();

		try {
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			
			pageManagementProxyVO.setWaTemplateDT(pageManagementDAOImpl.findWaTemplate(waTemplateUid));
			
			// To get port req Ind code from condition code table
			String conditionCode = pageManagementProxyVO.getWaTemplateDT().getConditionCd();
			WaTemplateDT waTemplateDT = pageManagementDAOImpl.getPortReqIndCd(conditionCode);
			pageManagementProxyVO.getWaTemplateDT().setPortReqIndCd(waTemplateDT.getPortReqIndCd());
			// get the PageCondMapping and set it here
			pageManagementProxyVO.setPageCondMappingColl(pageManagementDAOImpl.getPageCondMappingColl(waTemplateUid));
			
			
			//retrieve UI, NND and RDB metadata
			pageManagementProxyVO.setThePageElementVOCollection(pageManagementDAOImpl.getPageElementVOCollection(waTemplateUid,pageBuilderInd));			
			
			// getting the Rule Metadata for this draft.
			try{
				WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
				Collection<Object> ruleMetadataColl = waRuleMetadao.selectWaRuleMetadataDTByTemplate(waTemplateUid);
				pageManagementProxyVO.setWaRuleMetadataDTCollection(ruleMetadataColl);
			}catch(Exception ex){
				String errorString =  "PageManagementProxyEJB.getPageDraft:  Error while retrieving waRuleMetadataDTColl for WaTemplate uid:  " + waTemplateUid + " - " + ex.getMessage();
				logger.error("Error in getting the rule Metadata DT Collection "+ex.getMessage());
				throw new NEDSSSystemException(errorString + ex.getMessage());
			}
			
			
			
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getPageDraft: WaTemplate uid: " + waTemplateUid + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return pageManagementProxyVO;
	}

	public Map<Object,Object> getPageSummaries(NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	     																	        javax.ejb.EJBException, 
	     																	        javax.ejb.CreateException, 
	     																	        NEDSSSystemException, 
	     																	        NEDSSConcurrentDataException {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getPageSummaries:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.getPageSummaries:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		try {		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();

			return pageManagementDAOImpl.retrieveWaTemplateSummaries();
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getPageSummaries:"+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	
	public Collection<Object> getWaQuestion(Long waQuestionUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, 
	                                                                                            NEDSSSystemException, javax.ejb.FinderException,
	                                                                                            javax.ejb.CreateException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getWaQuestion:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.getWaQuestion:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		ArrayList<Object> waQuestionDTCollection  = new ArrayList<Object>();

		try {		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			WaQuestionDT waQuestionDT = pageManagementDAOImpl.findWaQuestion(waQuestionUid);
			waQuestionDTCollection.add(waQuestionDT);
			// If the question is part of a draft or Published page or Template , You can not edit it 
			
			WaUiMetadataDT uiDT = pageManagementDAOImpl.findUiMetadataByQuestionIdentifier(waQuestionDT.getQuestionIdentifier());
			if(uiDT != null && uiDT.getWaUiMetadataUid() != null)
				waQuestionDT.setReferencedInd(NEDSSConstants.TRUE);
			else 
				waQuestionDT.setReferencedInd(NEDSSConstants.FALSE);
			
			//If Question has an associated Unit question, retrieve it now too
			if (waQuestionDT.getQuestionUnitIdentifier() != null && !waQuestionDT.getQuestionUnitIdentifier().equals("")) {
				
				waQuestionDTCollection.add(pageManagementDAOImpl.findWaQuestion(waQuestionDT.getQuestionUnitIdentifier()));
			}
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getWaQuestion: waQuestionuid: " + waQuestionUid + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return waQuestionDTCollection;
	}
	
	
	public Long setWaQuestion(Collection<Object> waQuestionDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, 
																							    NEDSSSystemException, javax.ejb.FinderException,
																							    javax.ejb.CreateException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.setWaQuestion:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.setWaQuestion:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		Long waQuestionDTUid = null;

		try{		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			//if (waQuestionDTCollection != null && waQuestionDTCollection.size() == 1) {
				WaQuestionDT waQuestionDT = (WaQuestionDT)(waQuestionDTCollection.iterator().next());
				if (waQuestionDT.isItNew())
					waQuestionDTUid = new Long(pageManagementDAOImpl.insertWaQuestion(waQuestionDT));
				else if (waQuestionDT.isItDirty()) {
					WaQuestionDT oldWaQuestionDT = pageManagementDAOImpl.findWaQuestion(waQuestionDT.getWaQuestionUid());
					pageManagementDAOImpl.updateWaQuestion(waQuestionDT);
					// If this WaQuestion used to have a Unit assigned to it and no longer does then in-activate the WaQuestion representing the unit
					if ( (waQuestionDT.getQuestionIdentifier() == null || waQuestionDT.getQuestionIdentifier().trim().length() == 0) && 
						  oldWaQuestionDT.getQuestionUnitIdentifier() != null) {
						oldWaQuestionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Inactive);
						oldWaQuestionDT.setRecordStatusTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
						pageManagementDAOImpl.updateWaQuestion(oldWaQuestionDT);
					}
				}
		
		}catch(Exception e){
			logger.fatal("PageManagementProxyEJB.setWaQuestion: " + e.getMessage(),e );
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return waQuestionDTUid;
	}
	
	
	public Long setWaUIMetadata(Collection<Object> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, 
    	NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException 
	{

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.setWaUIMetadata:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.setWaUIMetadata:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		Long waQuestionDTUid = null;
		try {		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			if (waUIMetadataDTCollection != null && waUIMetadataDTCollection.size() == 1) {
				WaUiMetadataDT waUIMetadataDT = (WaUiMetadataDT)(waUIMetadataDTCollection.iterator().next());
				if (waUIMetadataDT.isItNew())
					waQuestionDTUid = new Long(pageManagementDAOImpl.createWaUiMetadata(waUIMetadataDT));
				else if (waUIMetadataDT.isItDirty()) {
					pageManagementDAOImpl.updateWaUiMetadata(waUIMetadataDT);
				}
			} else if (waUIMetadataDTCollection != null && waUIMetadataDTCollection.size() >1) {
				Iterator<Object> iter = waUIMetadataDTCollection.iterator();
				while (iter.hasNext()) {
					WaUiMetadataDT waUIMetadataDT= (WaUiMetadataDT)(iter.next());
					if (waUIMetadataDT.isItNew()) {
						Long uid = new Long(pageManagementDAOImpl.createWaUiMetadata(waUIMetadataDT));
						
					} else if (waUIMetadataDT.isItDirty()) {
						pageManagementDAOImpl.updateWaUiMetadata(waUIMetadataDT);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("PageManagementProxyEJB.setWaUIMetadata: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return waQuestionDTUid;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Object> searchWaQuestions(String whereclause, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																							             javax.ejb.EJBException, 
    																									 NEDSSSystemException, 
    																									 javax.ejb.FinderException,
    																									 javax.ejb.CreateException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.searchWaQuestions:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.searchWaQuestions:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		ArrayList<Object> qList = new ArrayList<Object>();

		try {		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();

			qList = pageManagementDAOImpl.searchWaQuestions(whereclause);
		} catch(Exception e) {
			logger.error("PageManagementProxyEJB.searchWaQuestions: "+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}

		/*for(int i=0;i<qList.size();i++){
			
			WaQuestionDT questionDT = (WaQuestionDT)qList.get(i);
			String rdbColumnName = questionDT.getRdbcolumnNm();
			String subgroup = questionDT.getSubGroupNm();
			
			if(rdbColumnName!=null && rdbColumnName.indexOf(subgroup+"_")==0){
				rdbColumnName=rdbColumnName.substring(subgroup.length()+1);
				questionDT.setRdbcolumnNm(rdbColumnName);
			}
		}*/
		
		return qList;
   }


	public Collection<Object> getWaQuestionDTCollection(NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																							  javax.ejb.EJBException, 
																							  NEDSSSystemException, 
																							  javax.ejb.FinderException,
																							  javax.ejb.CreateException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getWaQuestionDTCollection:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.getWaQuestionDTCollection:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

		try {		
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();

			return pageManagementDAOImpl.getWaQuestionDTCollection();
		} catch(Exception e) {
			logger.fatal("PageManagementProxyEJB.getWaQuestionDTCollection: " +e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object>  processManageConditionRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException {

    	Collection<Object>  returnedColl = null;    	
		Object aDAO = null;
		try {
			Class<?> daoClass = Class.forName(daoName); 
			aDAO = daoClass.newInstance();
			Map<Object, Object> methodMap = getMethods(daoClass);			
			Method method = (Method)methodMap.get(sMethod);
            ArrayList<Object> objAr = new ArrayList<Object> ();
            if(oParams != null) {
                for(int i = 0; i < oParams.length; i++) {
                    logger.debug("processSRTAdminRequest - oParams = " + oParams[i]);
                    objAr.add(oParams[i]);
                }
            }			
			Object obj = method.invoke(aDAO, objAr.toArray());			
			returnedColl = (Collection<Object>) obj;
			
		} catch (ClassNotFoundException err) {
			logger.fatal("ClassNotFoundException in processSRTAdminRequest: " + err.getMessage(),err);
			throw new java.rmi.RemoteException(err.getMessage(),err);
		} catch (NEDSSSystemException nse) {
			logger.fatal("NEDSSSystemException in processSRTAdminRequest: " + nse.getMessage(),nse);
			throw new java.rmi.RemoteException(nse.getMessage(),nse);		
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.processManageConditionRequest: " + e.getMessage(),e);
			throw new java.rmi.RemoteException(e.getMessage(),e );
		}	
		    	
    	return returnedColl;
    }
	
	protected Map<Object,Object> getMethods(Class<?> daoClass) {
		
		Method[] gettingMethods = daoClass.getMethods();
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try{
		for(int i = 0; i < gettingMethods.length; i++) {
			Method method = (Method)gettingMethods[i];
			String methodName = method.getName();
			resultMap.put(methodName, method);
		}
	} catch (Exception e) {
		logger.fatal("PageManagementProxyEJB.getMethods: ",e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
	}
		return resultMap;
	}

	public Collection<Object> getConditionLib(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		try{
		ConditionDAOImpl conditionDAO = new ConditionDAOImpl();
		Collection<Object> conditionColl = conditionDAO.getConditionDTCollection(null);
		return conditionColl;
		} catch (Exception e) {
		logger.fatal("PageManagementProxyEJB.getConditionLib: ",e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	public Map<Object,Object> createCondition( ConditionDT dt, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		try{
		ConditionDAOImpl conditionDAO = new ConditionDAOImpl();
		Map<Object, Object> errorMap = conditionDAO.createCondition(dt);
		return errorMap;
		} catch (Exception e) {
		logger.fatal("PageManagementProxyEJB.createCondition: ",e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
		}
		
	}

	public Map<Object,Object> updateCondition( ConditionDT dt, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception {
		try{
		ConditionDAOImpl conditionDAO = new ConditionDAOImpl();
		Map<Object, Object> errorMap = conditionDAO.updateCondition(dt);
		return errorMap;
		} catch (Exception e) {
		logger.fatal("PageManagementProxyEJB.updateCondition: ",e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
	
    public WaRuleMetadataDT insertRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDT = waRuleMetadataDaoImpl.insertWaRuleMetadataDT(waRuleMetadataDT,false);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.insertRuleMetadataDT: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    	return waRuleMetadataDT;
    }
    public void insertRuleMetadataDTColl(Collection<Object> waRuleMetadataDTColl, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDaoImpl.insertWaRuleMetadataDTColl(waRuleMetadataDTColl,false);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.insertRuleMetadataDTColl: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }

    public WaRuleMetadataDT updateRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDT = waRuleMetadataDaoImpl.updateWaRuleMetadataDT(waRuleMetadataDT);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.updateRuleMetadataDT: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    	return waRuleMetadataDT;
    }
    public void deleteRuleMetadata(Long  waRuleMetadataDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
       	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDaoImpl.deleteWaRuleMetadataDT(waRuleMetadataDTUid);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.deleteRuleMetadata: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
    public void publishRuleMetadata(Collection<WaRuleMetadataDT> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
       	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			 waRuleMetadataDaoImpl.publishWaRuleMetadata(waUIMetadataDTCollection);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.publishRuleMetadata: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
    public void createDraftWaRuleMetadata(Collection<Object> waUIMetadataDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDaoImpl.createDraftWaRuleMetadata(waUIMetadataDTCollection);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.createDraftWaRuleMetadata: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    	
    }

	public Collection<Object> getWaRuleSummaryDTCollection(Long templateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
    	Collection<Object> coll=null;
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			coll=waRuleMetadataDaoImpl.selectRuleMetatdataForTemplate(templateUid);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.getWaRuleSummaryDTCollection: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    	return coll;
    }
    public Collection<Object> getWaUiElementDTDropDown(Long templateUid, String type, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
    	{
	    	Collection<Object> coll=null;
	    	try {
				WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
				coll=waRuleMetadataDaoImpl.getUiMetatdataElementsDropDown(templateUid, type);
			} catch (Exception e) {
				logger.fatal("PageManagementProxyEJB.getWaUiElementDTDropDown: ",e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			return coll;
			
    	}
    
    /**
     * This method for to get only subsection information
     * @param templateUid
     * @param nbsSecurityObj
     * @return
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.EJBException
     * @throws NEDSSSystemException
     * @throws javax.ejb.FinderException
     * @throws javax.ejb.CreateException
     */
    public Collection<Object> getUiMetaQuestionDropDown(Long templateUid, String source, String mode, String ruleId, String ruleCode, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
	{
    	Collection<Object> coll=null;
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			coll=waRuleMetadataDaoImpl.getUiMetaQuestionDropDown(templateUid, source, mode, ruleId, ruleCode);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.getUiMetaQuestionDropDown: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return coll;
		
	}
    
    public Collection<Object> getUiMetaSubsectionDropDown(Long templateUid, String source,String mode, String ruleId, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
	{
    	Collection<Object> coll=null;
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			coll=waRuleMetadataDaoImpl.getUiMetaSubsectionDropDown(templateUid, source, mode, ruleId);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.getUiMetaSubsectionDropDown: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return coll;
		
	}
    
    public Collection<String> getPreviousSeletedTargets(Long templateUid,String mode, String ruleId, String ruleCode, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
	{
    	Collection<String> coll=null;
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			coll=waRuleMetadataDaoImpl.getPreviousSeletedTargets(templateUid, mode, ruleId, ruleCode);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.getPreviousSeletedTargets: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return coll;
		
	}
    
	public WaRuleMetadataDT selectWaRuleMetadataDT(Long waRuleMetadataDTUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException{
		WaRuleMetadataDT waRuleMetadataDT=null;
    	try {
			WaRuleMetadataDaoImpl waRuleMetadataDaoImpl=new WaRuleMetadataDaoImpl();
			waRuleMetadataDT=waRuleMetadataDaoImpl.selectWaRuleMetadataDT(waRuleMetadataDTUid);
		} catch (Exception e) {
			logger.fatal("PageManagementProxyEJB.selectWaRuleMetadataDT: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    	return waRuleMetadataDT;
    }
	
	public Collection<Object> getPageHistory(String pageNm,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
     javax.ejb.EJBException, 
     javax.ejb.CreateException, 
     NEDSSSystemException, 
     NEDSSConcurrentDataException {
		if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
		String errorString = "PageManagementProxyEJB.getPageHistory:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
		logger.info(errorString);
		throw new NEDSSSystemException(errorString);
		}
		logger.info("PageManagementProxyEJB.getPageHistory:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");
		
		try {		
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		return pageManagementDAOImpl.retrievePageHistory(pageNm);
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getPageHistory: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
     }
	
	public Collection<Object> getJspFiles(String invFormCd,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
    javax.ejb.EJBException, 
    javax.ejb.CreateException, 
    NEDSSSystemException, 
    NEDSSConcurrentDataException {		
		try {		
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		return pageManagementDAOImpl.retrieveJspFiles(invFormCd);
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getJspFiles: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
	
	public Collection<Object> getInvestigationFormCodeonServerStartup() throws java.rmi.RemoteException, 
    javax.ejb.EJBException, 
    javax.ejb.CreateException, 
    NEDSSSystemException, 
    NEDSSConcurrentDataException {		
		try {		
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();	
		return pageManagementDAOImpl.getInvestigationFormCodeonServerStartup();
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.getJspFiles: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
	public ArrayList<Object> getTemplateLib(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			ArrayList<Object> templateColl = templateDAO.getTemplateDTCollection();
			return templateColl;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getTemplateLib: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		
		}
	}
	public Map<Object,Object> fetchInsertSql(String strTableName,String colName, ArrayList<Object> IdList,String waTemplateUid,String templateNm,Map<Object,Object> codeSetNmColl,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
    javax.ejb.EJBException, 
    javax.ejb.CreateException, 
    NEDSSSystemException, 
    NEDSSConcurrentDataException {		
		try {		
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
		return templateDAO.fetchInsertSql(strTableName, colName, IdList,waTemplateUid, templateNm,codeSetNmColl);
		} catch(Exception e){
			logger.fatal("PageManagementProxyEJB.fetchInsertSql: ",e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }
	
	public String InsertImportSqlWithLog( ArrayList<Object> aList,ArrayList<Object> vList,String templateNm,Long activityLogUid ,Map<Object,Object> codeSetNmColl,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException {
		EDXActivityLogDAOImpl edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
		 PageManagementProxy pMap = null;
		String ret ="";
		try{
			InsertImportSql(aList,vList,templateNm,activityLogUid, codeSetNmColl,nbsSecurityObj);
			}
		catch(Exception e){
			ret="error occurred";
			logger.fatal("PageManagementProxyEJB.InsertImportSqlWithLog: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
			return ret;	
	}
	
	public String WriteImportSqlLog( EDXActivityLogDT dt) throws java.rmi.RemoteException,javax.ejb.EJBException {
		EDXActivityLogDAOImpl edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();		
		String ret ="";		
		try{
		edxActivityLogDAOImpl.insertEDXActivityLog(dt);
		}catch(Exception e){
			logger.fatal("PageManagementProxyEJB.WriteImportSqlLog: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return ret;
		
	}
	public String InsertImportSql( ArrayList<Object> aList,ArrayList<Object> vList,String templateNm,Long activityLogUid ,Map<Object,Object> codeSetNmColl, NBSSecurityObj nbsSecurityObj) throws NedssAppLogException, java.rmi.RemoteException,javax.ejb.EJBException {
		String ret="";
		int count =0;
		EDXActivityLogDT dt = new EDXActivityLogDT();
		try {	
			
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
		
		//check if the imported template already exists..
		count = templateDAO.checkforTemplateNm(aList.get(1));
		if(count==0){		
			   codeSetNmColl = templateDAO.getCodeSetNmColl();
				Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
				//Long activityLogUid = edxActivityLogDAOImpl.insertActivityLogUid(templateNm);
				
				dt =  templateDAO.InsertVocabSql(vList,"",currentUser,activityLogUid,templateNm, codeSetNmColl);
				
				
				if(dt.getEdxActivityLogUid()==null){				
					 
					dt =  templateDAO.InsertImportSql(aList,"",currentUser,activityLogUid,templateNm,dt,codeSetNmColl,dt.getNewaddedCodeSets());
					
				    if(dt.getSourceUid() != null){
				    	ret = dt.getSourceUid().toString();
				    	dt.setSourceUid(null); 
				    	WriteImportSqlLog(dt);	
				    	
				    }else{
				    	// cntx.setRollbackOnly();      				    	  	         
				    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
		            	 ret ="error";
						 String errorString = "PageManagementProxyEJB.InsertImportSql:  Error while inserting the import sql ";
						 throw new NedssAppLogException();	
				    	
				    }
				} else{
					// cntx.setRollbackOnly();
					 dt.setEdxActivityLogUid(activityLogUid);
	            	// edxActivityLogDAOImpl.insertEDXActivityLog(dt);
	            	 ret ="error";
					 String errorString = "PageManagementProxyEJB.InsertImportSql:  Error while inserting the import vocab sql";
					 throw new NedssAppLogException();
				}
			}
			else{
				logger.fatal("Cannot import the template. As the template name for the template being imported already exists.");
				ret = "templateAlreadyExists";
				  dt.setEdxActivityLogUid(activityLogUid);
		    	  dt.setSourceUid(null);
		    	  dt.setTargetUid(null);
		    	  dt.setDocType("Template");
		    	  dt.setDocName(templateNm);
		    	  dt.setRecordStatusCd("Failure");
		    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		    	  dt.setException("Import unsuccessful as the template "+templateNm +" already exists in the system");
		    	 
		    	  dt.setImpExpIndCd("I");
		    	  dt.setSourceTypeCd(null);
		    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
		    	  dt.setTargetTypeCd("Template");
		    	  dt.setBusinessObjLocalId(null);	
		    	  //dt.setEDXActivityLogDTDetails(null);
		    	  WriteImportSqlLog(dt);		    	 
			}
		} catch(NedssAppLogException nale){
			//WriteImportSqlLog(dt);
			dt.setEdxActivityLogUid(activityLogUid); 
			 dt.setEdxActivityLogUid(activityLogUid); 	
	    	 ret = "error";   
	    	 cntx.setRollbackOnly();
			  throw new NedssAppLogException(dt);
		}
		catch(Exception e){	
			 logger.fatal("PageManagementProxyEJB.InsertImportSql: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		  
		}
		return ret;
    }
	
	public ArrayList<Object> getActivityLogCollection(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			ArrayList<Object> activityLogColl = templateDAO.getActivityLogCollection();
			return activityLogColl;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getActivityLogCollection: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getAllDsmActivityLogCollection(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			ArrayList<Object> activityLogColl = templateDAO.getAllDsmActivityLogCollection();
			return activityLogColl;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getAllDsmActivityLogCollection: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		
		}
	}
	
	public ArrayList<Object> getDsmActivityLogCollection(DsmLogSearchDT dsmLogSearchDT, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			ArrayList<Object> activityLogColl = templateDAO.getDsmActivityLogCollection(dsmLogSearchDT);
			return activityLogColl;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getDsmActivityLogCollection: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public EDXActivityLogDT getLatestDsmActivityLog(String algorithmName, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			EDXActivityLogDT edxActivityLogDT = templateDAO.getLatestDsmActivityLog(algorithmName);
			return edxActivityLogDT;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getLatestDsmActivityLog: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public ArrayList<Object> getDsmActivityLogDetailCollection(Long activityUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
			ArrayList<Object> activityLogColl = templateDAO.getDsmActivityLogDetailCollection(activityUid);
			return activityLogColl;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getDsmActivityLogDetailCollection: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public void updateConditionCode() throws RemoteException, Exception{
		try{
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			pageManagementDAOImpl.updateConditionCode();
			
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.updateConditionCode: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public void updatePageDetails(WaTemplateVO waTempVO)throws java.rmi.RemoteException,javax.ejb.EJBException,Exception {
		try{
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			pageManagementDAOImpl.updateWaTemplate(waTempVO.getWaTemplateDT());
			pageManagementDAOImpl.reinitializePageCondMapping(waTempVO);
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.updatePageDetails: "+ ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	public WaTemplateVO getPageDetails(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																														    javax.ejb.EJBException, 
																														    javax.ejb.CreateException, 
																														    NEDSSSystemException, 
																														    NEDSSConcurrentDataException {
	
			if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getPageDetails:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
			}
			logger.info("PageManagementProxyEJB.getPageDetails:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");
			
			WaTemplateVO waTemplateVO = new WaTemplateVO();
			
			try {
				PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
				
				waTemplateVO.setWaTemplateDT(pageManagementDAOImpl.findWaTemplate(waTemplateUid));
				waTemplateVO.setWaRdbMetadataDTColl(pageManagementDAOImpl.getWaRdbMetadaByTemplate(waTemplateUid));
				// To get port req Ind code from condition code table
				
				// get the PageCondMapping and set it here
				waTemplateVO.setWaPageCondMappingDTColl(pageManagementDAOImpl.getPageCondMappingColl(waTemplateUid));
			
			
			} catch(Exception e){
				logger.fatal("PageManagementProxyEJB.getPageDetails: WaTemplate uid: " + waTemplateUid + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			
			return waTemplateVO;
	}
	
	

	public PageMetadataVO getPageMetadata(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
																														    javax.ejb.EJBException, 
																														    javax.ejb.CreateException, 
																														    NEDSSSystemException, 
																														    NEDSSConcurrentDataException {
	
			if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION)) {
			String errorString = "PageManagementProxyEJB.getPageMetadata:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
			logger.info(errorString);
			throw new NEDSSSystemException(errorString);
			}
			logger.info("PageManagementProxyEJB.getPageMetadata:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");
			PageMetadataVO pageMetadataVO = new PageMetadataVO();
			
			try {
				PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
				pageMetadataVO.setPageMetadataColl(pageManagementDAOImpl.getPageMetadataByTemplate(waTemplateUid));
			
				
				pageMetadataVO.setPageVocabularyColl(pageManagementDAOImpl.getPageVocabularyByTemplate(waTemplateUid));
				pageMetadataVO.setPageQuestionVocabularyColl(pageManagementDAOImpl.getPageQuestionVocabularyByTemplate(waTemplateUid));

			} catch(Exception e){
				logger.fatal("PageManagementProxyEJB.getPageMetadata: WaTemplate uid: " + waTemplateUid + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
			
			return pageMetadataVO;
	}
	
	
	
	public Map<Object,Object> findBatchRecords(String invFrmCd,NBSSecurityObj nbsSecurityObj) throws RemoteException, javax.ejb.EJBException{
		Map<Object,Object> batchMap = new HashMap<Object,Object>();
		try{
			
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			batchMap = pageManagementDAOImpl.findBatchRecords(invFrmCd);
			
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.findBatchRecords: " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
		
		}
		return batchMap;
	}
	
	public Map<Object,Object> getBatchMap(Long waTemplateUid,NBSSecurityObj nbsSecurityObj) throws RemoteException, javax.ejb.EJBException{
		Map<Object,Object> batchMap = new HashMap<Object,Object>();
		try{
			
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			batchMap = pageManagementDAOImpl.getBatchMap(waTemplateUid);
			
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.getBatchMap: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		
		}
		return batchMap;
	}
	
    public Collection<Object> getWaTemplateUidByPageNm(String templateNm) throws java.rmi.RemoteException,
            javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException
    {

        Collection<Object> templateUidsByTemplateNm = new ArrayList<Object>();
        try
        {
            PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
            templateUidsByTemplateNm = pageManagementDAOImpl.findWaTemplateUidByPageNm(templateNm);
        }
        catch (Exception ex)
        {
        	logger.fatal("PageManagementProxyEJB.getWaTemplateUidByPageNm: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
        }
        return templateUidsByTemplateNm;
    }
	
    public WaTemplateDT getWaTemplate(String conditionCd, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {

        if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION))
        {
            String errorString = "PageManagementProxyEJB.getWaTemplate:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
            logger.info(errorString);
            throw new NEDSSSystemException(errorString);
        }
        logger.info("PageManagementProxyEJB.getWaTemplate:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

        WaTemplateDT waTemplateDT = new WaTemplateDT();

        try
        {
            PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
            waTemplateDT = pageManagementDAOImpl.findWaTemplateByCondition(conditionCd);

        }
        catch (Exception e)
        {
        	logger.fatal("PageManagementProxyEJB.getWaTemplate: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
        }

        return waTemplateDT;
    }
	
    public WaTemplateDT getWaTemplateByCondTypeBusObj(String conditionCd, String templateType, String busObj,
            NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,
            javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException
    {
        WaTemplateDT waTemplateDT = new WaTemplateDT();

        try
        {
            PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
            waTemplateDT = pageManagementDAOImpl.findWaTemplateByConditionTypeAndBusinessObj(conditionCd, templateType,
                    busObj);
        }
        catch (Exception e)
        {
        	logger.fatal("PageManagementProxyEJB.getWaTemplateByCondTypeBusObj: ConditionCd: " + conditionCd + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
        }
        return waTemplateDT;
    }	
    
    
	public void ejbActivate() {}
	public void ejbPassivate() {}
	public void ejbCreate() throws java.rmi.RemoteException,javax.ejb.CreateException {}
	public void ejbRemove() {}
	//public void setSessionContext(SessionContext cntx)throws java.rmi.RemoteException, javax.ejb.EJBException {}
	
	/**
	* getSessionContext
	*/
    public javax.ejb.SessionContext getSessionContext()
    {
        return cntx;
    }
	/**
	* setSessionContext
	*/
    public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException, javax.ejb.EJBException
    {
        cntx = ctx;
    }
	
    public String getPublishedCondition(String conditionCd, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {

        if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION))
        {
            String errorString = "PageManagementProxyEJB.getPublishedCondition:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is false";
            logger.info(errorString);
            throw new NEDSSSystemException(errorString);
        }
        logger.info("PageManagementProxyEJB.getPublishedCondition:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION) is true");

        String publishedCd = "F";

        try
        {
            ConditionDAOImpl conditionDAOImpl = new ConditionDAOImpl();
            publishedCd = conditionDAOImpl.getPublishedCondition(conditionCd);

        }
        catch (Exception e)
        {
           logger.fatal("PageManagementProxyEJB.getPublishedCondition: ConditionCd: " + conditionCd + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
            
        } 
        return publishedCd;
    }
	
    public ArrayList<Object> getActivityLogColl(Date fromDate, Date toDate, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {

        if (!nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.EPILINKADMIN))
        {
            String errorString = "PageManagementProxyEJB.getActivityLogColl:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.EPILINKADMIN) is false";
            logger.info(errorString);
            throw new NEDSSSystemException(errorString);
        }
        logger.info("PageManagementProxyEJB.getActivityLogColl:  nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.EPILINKADMIN) is true");

        ArrayList<Object> epilinkDTColl;

        try
        {

            EpilinkActivityLogDaoImpl epilinkActivityLogDaoImpl = new EpilinkActivityLogDaoImpl();
            epilinkDTColl = epilinkActivityLogDaoImpl.getEpilinkDTCollection(fromDate, toDate);

        }
        catch (Exception e)
        { 
            logger.fatal("PageManagementProxyEJB.getActivityLogColl: " + e.getMessage(), e);
            throw new NEDSSSystemException(e.getMessage(), e);
        }

        return epilinkDTColl;
    }
    
    public Long getPHCUidForLoaclId (String localid,NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, java.rmi.RemoteException, javax.ejb.EJBException {
    	try{
    	PublicHealthCaseDAOImpl phc =  (PublicHealthCaseDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_DAO_CLASS);
    	Long phcDT=phc.getpublicHealthCaseUid(localid);
    	return phcDT;
    	}
        catch (Exception e)
        { 
    	 logger.fatal("PageManagementProxyEJB.getPHCUidForLoaclId: " + e.getMessage(), e);
         throw new NEDSSSystemException(e.getMessage(), e);
        }
    }
    
    /**
     * @param busObjType
     * @param nbsSecurityObj
     * @return
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @throws EJBException
     */
    public ArrayList<Object> findPageByBusinessObjType(String busObjType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
    	try{
    		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
    		ArrayList<Object> pageList = pageManagementDAOImpl.findPageByBusinessObjType(busObjType);
    		return pageList;
    	}catch(Exception ex){
    		 logger.fatal("PageManagementProxyEJB.findPageByBusinessObjType, busObjType: "+busObjType+", Exception :" + ex.getMessage(), ex);
             throw new NEDSSSystemException(ex.getMessage(), ex);
    	}
    }
    
    
    /**
     * @param nbsSecurityObj
     * @return list of NBS_BUS_OBJ_Metadata
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @throws EJBException
     */
    public ArrayList<Object> getNbsBusObjMetadataList(NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
    	try{
    		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
    		ArrayList<Object> nbsBusObjList = pageManagementDAOImpl.getNbsBusObjMetadataList();
    		return nbsBusObjList;
    	}catch(Exception ex){
    		 logger.fatal("PageManagementProxyEJB.getNbsBusObjMetadataList Exception :" + ex.getMessage(), ex);
             throw new NEDSSSystemException(ex.getMessage(), ex);
    	}
    }
    
    /**
     * @param busObjType
     * @param nbsSecurityObj
     * @return
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @throws EJBException
     */
    public NbsBusObjMetadataDT getNbsBusObjMetadataByBusObjType(String busObjType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
    	try{
    		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
    		NbsBusObjMetadataDT nbsBusObjMetadataDT = pageManagementDAOImpl.getNbsBusObjMetadataByBusObjType(busObjType);
    		return nbsBusObjMetadataDT;
    	}catch(Exception ex){
    		 logger.fatal("PageManagementProxyEJB.getNbsBusObjMetadataByBusObjType, busObjType: "+busObjType+", Exception :" + ex.getMessage(), ex);
             throw new NEDSSSystemException(ex.getMessage(), ex);
    	}
    }
    
    public void loadLookups(NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
    	try{
    		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
    		pageManagementDAOImpl.loadQuestionLookup();
    		pageManagementDAOImpl.loadAnswerLookup();
    	}catch(Exception ex){
    		 logger.fatal("PageManagementProxyEJB.loadQuestionLookup Exception :" + ex.getMessage(), ex);
             throw new NEDSSSystemException(ex.getMessage(), ex);
    	}
    }
    
    public boolean correctMetadataAfterTemplateImport(String templateNm, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
    	try{
    		TemplatesDAOImpl templateDAO = new TemplatesDAOImpl();
    		boolean success = templateDAO.correctMetadataAfterTemplateImport(templateNm);
			return success;
    	}catch(Exception ex){
    		 logger.fatal("PageManagementProxyEJB.correctMetadataAfterTemplateImport Exception :" + ex.getMessage(), ex);
             throw new NEDSSSystemException(ex.getMessage(), ex);
    	}
    }
    
    
    /**
     * @param templateType
     * @param nbsSecurityObj
     * @return
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @throws EJBException
     */
    public ArrayList<Object> findPageByTemplateType(String templateType, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
		try{
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			ArrayList<Object> pageList = pageManagementDAOImpl.findPageByTemplateType(templateType);
			return pageList;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.findPageByTemplateType: "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		
		}
	}
    
    
    /**
     * @param waTemplateUid
     * @param nbsSecurityObj
     * @return
     * @throws NEDSSSystemException
     * @throws RemoteException
     * @throws EJBException
     */
    public ArrayList<Object> findWaUiMetadataForSubFormByWaTemplateUid(Long waTemplateUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, EJBException{
		try{
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			ArrayList<Object> waUiMetadataList = pageManagementDAOImpl.findWaUiMetadataForSubFormByWaTemplateUid(waTemplateUid);
			return waUiMetadataList;
		}catch (Exception ex) {
			logger.fatal("PageManagementProxyEJB.findWaUiMetadataForSubFormByWaTemplateUid: "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		
		}
	}
    
	/**
	 * editAndPublishPages: receives an array of wa_template_uid to be edited and published. The reason for having this method
	 * is for allowing calling it from the UI in future in case a bulk action is added to the manage pages.
	 */
    public String editAndPublishPage(String waTemplateUidString, String user,NBSSecurityObj nbsSecurityObj){
		
    	String newWaUitemplateUidString = "";
		
		try{

			if(waTemplateUidString!=null){
			
					
					//New draft
					Long waTemplateUid = Long.parseLong(waTemplateUidString);
					
					
					
					Timestamp currentTime = new Timestamp(new Date().getTime());

					
					SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
				    String date = DATE_FORMAT.format(currentTime);

					String mode = "Page re-published by "+user+" on "+date+" via bulk re-publish process.";
					
					logger.info("Old waTemplateUid to be edited: "+waTemplateUidString);
					Long newWaTemplateUid = savePublishedPageAsDraft(waTemplateUid, nbsSecurityObj);
					newWaUitemplateUidString = newWaTemplateUid+"";
					logger.info("New waTemplateUid to be published: "+newWaTemplateUid);
					
					//Publish
					
					
					WaTemplateDT waTemplateDT = new WaTemplateDT();
					waTemplateDT.setWaTemplateUid(newWaTemplateUid);
					waTemplateDT.setVersionNote(mode);
					
					publishPage(waTemplateDT, nbsSecurityObj);
					logger.info("waTemplateUid "+newWaTemplateUid+" has been published");
				}
			
			
		}catch(Exception e){
			newWaUitemplateUidString="Failure";
			logger.fatal("PageManagementProxyEJB.editAndPublishPages:" + e.getMessage(),e);
			//throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return newWaUitemplateUidString;
	}
	
  /**
   * getWaTemplateListToRepublish: get aa list of all the waUiTemplateDT on publish mode
   * @param nbsSecurityObj
   * @return
   */
    
    public ArrayList<Object> getWaTemplateListToRepublish(NBSSecurityObj nbsSecurityObj){
    	
    	logger.info("Retrieving all the pages to be republished");
    	ArrayList<Object> pages = null;
    	
    	try{
    		
	    	//Get all the pages on publish mode
			PageManagementDAOImpl pageManagementDAOImpl  =  new PageManagementDAOImpl();
			pages = (ArrayList<Object>)pageManagementDAOImpl.getAllPagesInPublishMode();

	
		}catch(Exception e){
			logger.fatal("PublishAllPages.getWaTemplateListToRepublish:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		
		
		}

	
	return pages;
	
    }
 

 /**
  * getWaUiTemplateUidsToRepublishByTemplateName: get all the wa_template on published mode and with template_nm in listOfTemplates
  * @param listOfTemplates
  * @param nbsSecurityObj
  * @return
  */
 public ArrayList<Object> getWaUiTemplateUidsToRepublishByTemplateName(String listOfTemplates, NBSSecurityObj nbsSecurityObj){
 	
 	logger.info("Retrieving the pages on the list to be republished");
 	ArrayList<Object> pages = null;
 	listOfTemplates=listOfTemplates.replace("\"","");
 	try{
 		
	    	//Get all the pages on publish mode and with template name in the list
			PageManagementDAOImpl pageManagementDAOImpl  =  new PageManagementDAOImpl();
			pages = (ArrayList<Object>)pageManagementDAOImpl.getPagesByTemplateName(listOfTemplates);
			
	
		}catch(Exception e){
			logger.fatal("PublishAllPages.getWaUiTemplateUidsToRepublishByTemplateName:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		
		
		}

	
	return pages;
	
 }
    
 public NbsPageDT findNBSPageDetailsExceptJspPayloadByFormCd(String formCd)  throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException{
	 try {
		 NBSPageDAOImpl nbsPageDAOImpl = new NBSPageDAOImpl();
		 NbsPageDT nbsPageDT = nbsPageDAOImpl.findNBSPageDetailsExceptJspPayload(formCd);
		 return nbsPageDT;
	 }catch(Exception e){
		 logger.fatal("findNBSPageByFormCd:" + e.getMessage(),e);
		 throw new NEDSSSystemException(e.getMessage(),e);
	 }
 }
 
} // PageManagementProxyEJB

