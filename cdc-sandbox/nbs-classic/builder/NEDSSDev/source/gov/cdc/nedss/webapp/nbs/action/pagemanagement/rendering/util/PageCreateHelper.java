package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.util.PageMetaConstants;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.CaseLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules.PageRulesGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.MessageLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageCreateHelper.java
 * Jan 19, 2010
 * @version 4.5
 * @updated by: Pradeep Sharma
 * <p>Company: SAIC</p>
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 * 
 */
public class PageCreateHelper {

	static final LogUtils logger = new LogUtils(PageCreateHelper.class.getName());
	private Map<Object,Object> questionMap;

	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance(); //check STD PA

	public void loadQuestions(String invFormCd){

		if(QuestionsCache.getDMBQuestionMap()!=null)
			setQuestionMap( (Map)QuestionsCache.getDMBQuestionMap().get(invFormCd));
		else if(!QuestionsCache.dmbMap.containsKey(invFormCd))
			setQuestionMap( (Map<Object, Object> )QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd));
		else {
			logger.error("PageCreateHelper: Empty question map?? for form cd = " +invFormCd );
			setQuestionMap( new HashMap<Object,Object>());
		}
	}
	
	public Map<Object, Object> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<Object, Object> questionMap) {
		this.questionMap = questionMap;
	}


	/**
	 *
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PageProxyVO create(PageForm form, HttpServletRequest req) throws Exception {

		PageProxyVO returnVO = null;
		try {
			
			String pageFormCd = form.getPageFormCd();			
			form.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			form.setErrorList(new ArrayList<Object>());
			loadQuestions(pageFormCd);
			handleFormRules(form,NEDSSConstants.CREATE_SUBMIT_ACTION);
			
			if(pageFormCd != null ) {
				
				PageActProxyVO proxyVO = new PageActProxyVO();
				
				if(form.getErrorList()==null || form.getErrorList().size()==0){
					Long tempID = -1L;
					proxyVO.setItNew(true);
					//Set PublicHealthCase Information
					ProgramAreaVO programAreaVO = getProgAreaVO(req.getSession());
					NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
					String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
					Long providerUid=nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
					
	            	

					//TB VerCrit logic
					
					//String conditionCode = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
					//Updated to fix ND-29111
					String conditionCode =(String)form.getAttributeMap().get("headerConditionCode");
				//	if(conditionCode == null)
					//	conditionCode =(String)form.getAttributeMap().get("headerConditionCode");
	            	if(conditionCode != null && conditionCode.equalsIgnoreCase("102201"))//Only call from new TB page (102201)
	            		setVerCritTBLogicOnSubmit(form);
	            	
	            		
	            	
	            	
					//persist PublicHealthCase
					tempID = setPublicHealthCaseForCreate(tempID, form, req, proxyVO, programAreaVO, userId);


	            	
					//persist PatientRevision from Answers
					Long patientUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
					setPatientForEventCreate(patientUid, tempID, proxyVO, form, req, userId);
					if(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null){
						Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
						boolean isCaseManagementDTPopulated= DynamicBeanBinding.transferBeanValues(getQuestionMap(), proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(), answerMap, RenderConstants.CASE_MANAGEMENT);
						if(!isCaseManagementDTPopulated){
							proxyVO.getPublicHealthCaseVO().setTheCaseManagementDT(new CaseManagementDT());
							proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setCaseManagementDTPopulated(false);
						}else{
							//Set epi link id and field record number from co-infection investigation if available
							try{
								CoinfectionSummaryVO csVO = null;
								if (NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSCoinfectionInvSummVO) != null) {
									csVO = (CoinfectionSummaryVO)NBSContext.retrieve(req.getSession(),
											NBSConstantUtil.DSCoinfectionInvSummVO);
								}
								String investigationType = (String) NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationType);
					        	if(investigationType!=null && investigationType.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF) && csVO!=null){
					        		proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setEpiLinkId(csVO.getEpiLinkId());
					        		proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setFieldRecordNumber(csVO.getFieldRecordNumber());
					        	}
					        }catch(Exception ex){
					        	logger.debug("Expected exception retreiving DSCoinfectionInvSummVO:", ex); 
					        	logger.debug("Expected exception: Context exception related to co-infection " +ex.getMessage());
					        }
							handleSupervisorReviewQueueForCreate(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT());
							proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setCaseManagementDTPopulated(true);
					    }
					}
					
					
	            	
					//persist all answers
					setPageSpecifcAnswersForCreateEdit(proxyVO, form, session, userId);
					//set Participations
					setParticipationsForCreate(userId, proxyVO, form, tempID, req);
					//set EntityColl (PAM Specific)
					setEntitiesForCreateEdit(form, proxyVO, tempID, "0", userId);
					
					setRepeatingQuestionsBatch(form,proxyVO,pageFormCd,userId, providerUid);
					String contextAction = req.getParameter("ContextAction");
					
					if(contextAction != null && contextAction.equalsIgnoreCase("SubmitNoViewAccess")) {
						form.setActionMode("SubmitNoViewAccess");
					}
				}
				
				returnVO = (PageProxyVO)proxyVO;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Error while Submitting Create " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return returnVO;
	}

	/**
	 * createGeneric
	 * @param form
	 * @param req
	 * @throws Exception
	 */
    public PageProxyVO createGeneric(PageForm form, HttpServletRequest request) throws Exception
    {
        PageProxyVO returnVO = null;
        try
        {
            NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
            String pageFormCd = form.getPageFormCd();
            form.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
            HttpSession session = request.getSession();
            form.setErrorList(new ArrayList<Object>());
            loadQuestions(pageFormCd);
            handleFormRules(form, NEDSSConstants.CREATE_SUBMIT_ACTION);

            if (pageFormCd != null)
            {
                PageActProxyVO proxyVO = new PageActProxyVO();
                /**Observation specific changes*/
                if(form.getGenericType()!=null && form.getGenericType().equalsIgnoreCase(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE)) {
                    String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
                    Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
                    PageManagementCommonActionUtil.storeCreatedEditedGenericEntities(form, proxyVO, null, getQuestionMap(), userId, providerUid, request);
	
                	
                }else {
	                if (form.getErrorList() == null || form.getErrorList().size() == 0)
	                {
	                    proxyVO.setItNew(true);
	                    
	                    String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
	                    Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
	                    
	                    String invPath = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
	                    if (!invPath.equalsIgnoreCase("DSFilePath"))
	                    {
		                    // set the actRelationship for the event
		                    createActRelationshipToCaseForGeneric(proxyVO, form.getBusinessObjectType(), request);
	                    }
	                    // set the actRelationship(s) for any coinfections
	                    if (form.getPageClientVO().getAnswer(NEDSSConstants.INTERVIEW_COINFECTION_LIST) != null &&
	                    		form.getPageClientVO().getAnswer(NEDSSConstants.INTERVIEW_COINFECTION_LIST).length() > 5) {
	                    	createActRelationshipToCaseForGenericCoinfections(proxyVO, form.getBusinessObjectType(), form.getPageClientVO().getAnswer(NEDSSConstants.INTERVIEW_COINFECTION_LIST), request);
	                    	form.getPageClientVO().setAnswer(NEDSSConstants.INTERVIEW_COINFECTION_LIST,""); //remove the list
	                    }
	                 // persist event entity
	                    PageManagementCommonActionUtil.setCommonAnswersForGenericCreate(form, proxyVO, request, userId);
	                    // persist dynamic answers
	                    Map<Object, Object> newAnswers = setPageSpecifcAnswersForCreateEdit(
	                            (BaseForm) form, (ClientVO) form.getPageClientVO(), session, userId);
	                    proxyVO.getPageVO().setAnswerDTMap(newAnswers);
	
	                    // process participations
	                    PageManagementCommonActionUtil.storeCreatedEditedGenericEntities(form, proxyVO, null, // oldProxy
	                            getQuestionMap(), userId, providerUid, request);
	
	                    setRepeatingQuestionsBatch(form, proxyVO, pageFormCd, userId, providerUid);
	                    String contextAction = request.getParameter("ContextAction");
	                    if (contextAction != null && contextAction.equalsIgnoreCase("SubmitNoViewAccess"))
	                    {
	                        form.setActionMode("SubmitNoViewAccess");
	                    }
	                }
	                returnVO = (PageProxyVO) proxyVO;
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Error while Submitting Create Generic " + form.getPageFormCd() + " Page: " + e.toString());
            throw new Exception(e.toString());
        }
        return returnVO;
    }
	
	
	/**
	 *
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PageActProxyVO editHandler(PageForm form, HttpServletRequest req) throws Exception {
		PageActProxyVO proxyVO = null;


		try {
			form.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			form.setErrorList(new ArrayList<Object>());
			//Load Imported Counties(INV156) based on INV154(Imported State). If INV154 not answered, load INV156 values derived from Default(Current) State
			if(form.getPageClientVO().getAnswer(PageConstants.IMPORTED_STATE)==null)
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
			else
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getPageClientVO().getAnswer(PageConstants.IMPORTED_STATE)));			
			String invFormCd = form.getPageFormCd();
			loadQuestions(invFormCd);
			handleRaceForRules(form.getPageClientVO());
			checkNotificationAssociationToInvestigation(form,req);
			
        	
			handleFormRules(form,NEDSSConstants.EDIT_SUBMIT_ACTION);
			proxyVO = new PageActProxyVO();
			Long providerUid=null;
			if(form.getErrorList()==null || form.getErrorList().size()==0){
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
				providerUid= nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
				proxyVO.setItDirty(true);

				
				//TB VerCrit logic
				//String conditionCode = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
				//Updated to fix ND-29111
				String conditionCode =(String)form.getAttributeMap().get("headerConditionCode");
		
				//	if(conditionCode == null)
		//			conditionCode =(String)form.getAttributeMap().get("headerConditionCode");
            	if(conditionCode != null && conditionCode.equalsIgnoreCase("102201"))//Only call from new TB page (102201)
	        		setVerCritTBLogicOnSubmit(form);
	        	
            	Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
            	String currentInvStatusCd = getVal(answerMap.get(PageConstants.INV_STATUS_CD ));
            	String oldInvStatusCd = form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigationStatusCd();
            	
            	setPublicHealthCaseForEdit(form, req, proxyVO, userId);
            	
            	//boolean updatedFromClosedToOpen = false;
				if(form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO()!=null && form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT() !=null && 
						form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd()!=null && (form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd().equals("STD") || form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd().equals("HIV"))) {
					boolean updatedFromClosedToOpen = checkIfSTDInvestigationOpenedFromClosedStatus(currentInvStatusCd, oldInvStatusCd);
					proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setStdOpenedFromClosed(updatedFromClosedToOpen);
					
				}
	        	
				PersonVO personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());
				PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
				setPatientForEventEdit(form, personVO, proxyVO, req, userId);
			
	        	
				//persist all answers
				setPageSpecifcAnswersForCreateEdit(proxyVO, form, session, userId);
				setParticipationsForEdit(proxyVO, form, phcVO, req);
				//set EntityColl (PAM Specific)
				setEntitiesForCreateEdit(form, proxyVO, personVO.getThePersonDT().getPersonUid(), personVO.getThePersonDT().getVersionCtrlNbr().toString(),  userId);
				String oldFldFollUpDispo= null;
				String newFldFollUpDispo= null;
				if(phcVO.getTheCaseManagementDT()!=null){
					 oldFldFollUpDispo = phcVO.getTheCaseManagementDT().getFldFollUpDispo();
					
					boolean isCaseManagementDTPopulated= DynamicBeanBinding.transferBeanValues(getQuestionMap(), phcVO.getTheCaseManagementDT(), answerMap, RenderConstants.CASE_MANAGEMENT);
					newFldFollUpDispo = phcVO.getTheCaseManagementDT().getFldFollUpDispo();
					
					if(!isCaseManagementDTPopulated){
						phcVO.setTheCaseManagementDT(null);
						//phcVO.getTheCaseManagementDT().setCaseManagementDTPopulated(false);
					}else{
						handleSupervisorReviewQueueForEdit(form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getTheCaseManagementDT(),oldFldFollUpDispo);
						phcVO.getTheCaseManagementDT().setCaseManagementDTPopulated(true);
					}
				}
				proxyVO.setPublicHealthCaseVO(phcVO);
				proxyVO.setTheNotificationSummaryVOCollection(((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getTheNotificationSummaryVOCollection());
				Collection<Object>  patientColl = new ArrayList<Object> ();
				patientColl.add(personVO);
				proxyVO.setThePersonVOCollection(patientColl);
				if(oldFldFollUpDispo == null && newFldFollUpDispo != null ||
						oldFldFollUpDispo!= null && !oldFldFollUpDispo.equals(newFldFollUpDispo)){
					createMessageForNamedBy((PageActProxyVO) proxyVO,req,nbsSecurityObj);
				}
			} else {
				
				PageActProxyVO prxyVO = (PageActProxyVO)form.getPageClientVO().getOldPageProxyVO();
				String phcUid = prxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().toString();
				Map<Object, Object> map = new HashMap<Object, Object>();
				PageLoadUtil pageLoadUtil = new PageLoadUtil();
				pageLoadUtil.populatePageAssocations(prxyVO, phcUid, map,req, form);
			}
			
			//Set the HIV question back into proxy
			if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS))
			 proxyVO.getPageVO().getPamAnswerDTMap().putAll(form.getPageClientVO().getHivAnswerMap());
					
			updateNbsAnswersForDirty(form, proxyVO);
			

			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			setRepeatingQuestionsBatch(form,proxyVO,invFormCd,userId, providerUid);
			
			//Set the HIV batch questions back into proxy
			if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS))
			 proxyVO.getPageVO().getPageRepeatingAnswerDTMap().putAll(form.getPageClientVO().getHivAnswerBatchMap());
			updateNbsRepeatingAnswersForDirty(form, proxyVO);

			// set investigation createdBy/Date, updatedBy/Date in request.
			
			PublicHealthCaseDT phcDT = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT();
			req.setAttribute("createdDate", StringUtils.formatDate(phcDT.getAddTime()));
			req.setAttribute("createdBy", phcDT.getAddUserName());
			req.setAttribute("updatedDate", StringUtils.formatDate(phcDT.getLastChgTime()));
			req.setAttribute("updatedBy", phcDT.getLastChgUserName());

			//set the notification status details in request
			ArrayList<Object> nsColl = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getTheNotificationSummaryVOCollection() == null ? new ArrayList<Object>() : (ArrayList<Object> ) ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getTheNotificationSummaryVOCollection();
			Iterator<Object> nsCollIter = nsColl.iterator();
			logger.info("# of notifications = " + nsColl.size());
			if (nsColl.size() > 0) {
				// get the status and date of the first notification in the collection (i.e., the latest one)
				// and set it in request scope
				Timestamp latestStatusTime = null;
				  String latestStatusCode = null;
				  while (nsCollIter.hasNext()) {
					  NotificationSummaryVO sVO = (NotificationSummaryVO) nsCollIter.next();
					  if(sVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTF)){
						  latestStatusTime = ((NotificationSummaryVO) nsColl.get(0)).getRecordStatusTime();
						  latestStatusCode = ((NotificationSummaryVO) nsColl.get(0)).getRecordStatusCd();
						  if(sVO.getRecordStatusTime()!=null){
						  if (sVO.getRecordStatusTime().after(latestStatusTime)) {
							  latestStatusTime = sVO.getRecordStatusTime();
							  latestStatusCode = sVO.getRecordStatusCd();
						  }
						  }  
					  }
				  }
				logger.info("latestStatusCode = " + latestStatusTime + "; latestStatusTime = " + StringUtils.formatDate(latestStatusTime));
				req.setAttribute("notificationStatus", latestStatusCode);
				req.setAttribute("notificationDate", StringUtils.formatDate(latestStatusTime));
			  }
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Submitting Create " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}

	private boolean checkIfSTDInvestigationOpenedFromClosedStatus(String currentInvStatusCd, String oldInvStatusCd) {
		if ("C".equals(oldInvStatusCd) && "O".equals(currentInvStatusCd))
			return true;
		return false;
	}
	
	/**
	 *
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PageActProxyVO editGenericHandler(PageForm form, HttpServletRequest req) throws Exception {
		PageActProxyVO proxyVO = null;

		try {
			form.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			form.setErrorList(new ArrayList<Object>());

			String pageFormCd = form.getPageFormCd();
			loadQuestions(pageFormCd);
			handleFormRules((BaseForm) form,(ClientVO)form.getPageClientVO(), NEDSSConstants.EDIT_SUBMIT_ACTION);
			
			proxyVO = new PageActProxyVO();
			Long providerUid=null;
			if(form.getErrorList()==null || form.getErrorList().size()==0){
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
				providerUid= nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
				proxyVO.setItDirty(true);
					
				//process entities such as Interview, Vaccination
				PageManagementCommonActionUtil.setCommonAnswersFromGenericEdit(form, proxyVO, req, userId);

				//process answerDT questions
				PageActProxyVO oldProxyVO = (PageActProxyVO) form.getPageClientVO().getOldPageProxyVO();
				Map<Object, Object> oldAnswers = oldProxyVO.getPageVO().getAnswerDTMap();
				Map<Object, Object> newAnswers = setPageSpecifcAnswersForCreateEdit((BaseForm) form,
								(ClientVO) form.getPageClientVO(),
								session, userId);
				proxyVO.getPageVO().setAnswerDTMap(newAnswers);
				//updateNBSAnswersForDirty
				if(oldAnswers != null && newAnswers != null && 
						!NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()) &&
						!NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()))
				{
					proxyVO.getPageVO().getAnswerDTMap().putAll(PageCreateHelper.updateNbsAnswersForDirty(oldAnswers, newAnswers));
				}
				//process participations
				 PageManagementCommonActionUtil.storeCreatedEditedGenericEntities(form, 
							proxyVO, oldProxyVO,
							getQuestionMap(),
							userId, providerUid, req);
			}
			
			//Set the HIV question back into proxy
			if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS));
			 proxyVO.getPageVO().getPamAnswerDTMap().putAll(form.getPageClientVO().getHivAnswerMap());
					
			updateNbsAnswersForDirty(form, proxyVO);
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			setRepeatingQuestionsBatch(form,proxyVO,pageFormCd,userId, providerUid);
			//Set the HIV batch questions back into proxy
			//if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS))
			// proxyVO.getPageVO().getPageRepeatingAnswerDTMap().putAll(form.getPageClientVO().getHivAnswerBatchMap());
			//updateNbsRepeatingAnswersForDirty(form, proxyVO);

		} catch (Exception e) {
			logger.error("Error while Submitting Create " +  form.getPageFormCd() +  " Page: "+ e.getMessage(), e);
			throw new Exception(e.toString());
		}
		return proxyVO;
	}
	
	
	/**
	 *
	 * @param form
	 * @param request
	 * @throws Exception
	 */
	public static void viewHandler(PageForm form, HttpServletRequest request) throws Exception {

		try {
			PersonVO personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());
			PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
			Long DSPatientPersonUID= personVO.getThePersonDT().getPersonParentUid();
			String DSPatientPersonLocalID=personVO.getThePersonDT().getLocalId();

			String DSInvestigationUid=getVal(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
			String DSConditionCode=phcVO.getThePublicHealthCaseDT().getCd();
			String investigationLocalID=phcVO.getThePublicHealthCaseDT().getLocalId();
			String jurisdictionCd=phcVO.getThePublicHealthCaseDT().getJurisdictionCd();
			String progAreaCd=phcVO.getThePublicHealthCaseDT().getProgAreaCd();
			HttpSession session = request.getSession();

			String sContextAction = request.getParameter("ContextAction");

			if(DSInvestigationUid!=null)
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, DSInvestigationUid);

			if(DSPatientPersonUID!=null)
				NBSContext.store(session, NBSConstantUtil.DSPatientPersonUID, DSPatientPersonUID);

			if(DSPatientPersonLocalID!=null)
				NBSContext.store(session, NBSConstantUtil.DSPatientPersonLocalID,DSPatientPersonLocalID );

			//changes made for SRT Filtering : Add Treatment and Add Lab from manage
			if(DSConditionCode!=null)
				NBSContext.store(session, NBSConstantUtil.DSConditionCode,DSConditionCode );

			if (sContextAction.equalsIgnoreCase(NBSConstantUtil.ManageObservations)){
				NBSContext.store(session, NBSConstantUtil.DSInvestigationLocalID, investigationLocalID);
				NBSContext.store(session, NBSConstantUtil.DSJurisdiction, jurisdictionCd);
				//added into context, in order to default the program area code for Adding Observation
				NBSContext.store(session, NBSConstantUtil.DSProgramArea, progAreaCd);
			} else if (sContextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
				sContextAction.equalsIgnoreCase(NBSConstantUtil.ObservationMorbID)){
			 	String observationUID = request.getParameter("observationUID");
			 	NBSContext.store(session, NBSConstantUtil.DSObservationUID, observationUID);
			} else if (sContextAction.equalsIgnoreCase(NBSConstantUtil.FileSummary) ||
				sContextAction.equalsIgnoreCase(NBSConstantUtil.ReturnToFileSummary)){
				NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
			} else if (sContextAction.equalsIgnoreCase("ReturnToFileEvents")){
				NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Submitting View " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
   }

    public static void setPatientForEventEdit(PageForm form, PersonVO personVO, PageActProxyVO proxyVO,
            HttpServletRequest request, String userId)
    {
        personVO.setItDirty(true);
        personVO.getThePersonDT().setItDirty(true);
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
        if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS))
            personVO.getThePersonDT().setEharsId(
                    (String) form.getPageClientVO().getHivAnswerMap().get(PageConstants.EHARS_ID));
        else
            personVO.getThePersonDT().setEharsId(
                    (String) form.getPageClientVO().getAnswerMap().get(PageConstants.EHARS_ID));
        setDemographicInfoForEdit(personVO, form.getPageClientVO().getAnswerMap(), userId);
        setNamesForEdit(personVO, form.getPageClientVO().getAnswerMap(), userId);
        setEntityLocatorParticipationsForEdit(personVO, form.getPageClientVO().getAnswerMap(), userId);
        setRaceForEdit(personVO, form.getPageClientVO(), proxyVO, userId);
        setEthnicityForEdit(personVO, form.getPageClientVO().getAnswerMap(), userId);
        setIdsForEdit(personVO, form.getPageClientVO().getAnswerMap(), userId);

        // LDFs
        personVO.setTheStateDefinedFieldDataDTCollection(extractPatientLDFs(form));
    }
    
	/**
	 * setPatientForEventEdit (generic version)
	 * @param baseForm
	 * @param personVO
	 * @param clientVO (i.e. (ClientVO) form.getcTContactClientVO() or form.getPageClientVO())
	 * @param request
	 * @param userid
	 */
    public static void setPatientForEventEdit(BaseForm form, PersonVO personVO, ClientVO clientVO,
            HttpServletRequest request, String userId)
    {

        personVO.setItDirty(true);
        personVO.getThePersonDT().setItDirty(true);
        HttpSession session = request.getSession();
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
        if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS))
            personVO.getThePersonDT().setEharsId((String) clientVO.getHivAnswerMap().get(PageConstants.EHARS_ID));
        else
            personVO.getThePersonDT().setEharsId((String) clientVO.getAnswerMap().get(PageConstants.EHARS_ID));
        setDemographicInfoForEdit(personVO, clientVO.getAnswerMap(), userId);
        setNamesForEdit(personVO, clientVO.getAnswerMap(), userId);
        setEntityLocatorParticipationsForEdit(personVO, clientVO.getAnswerMap(), userId);
        setRaceForEdit(personVO, clientVO, userId);
        setEthnicityForEdit(personVO, clientVO.getAnswerMap(), userId);
        setIdsForEdit(personVO, clientVO.getAnswerMap(), userId);

        // LDFs
        if (form.getPageFormCd() != null && form.getPageFormCd().startsWith(NBSConstantUtil.InvestigationFormPrefix))
            personVO.setTheStateDefinedFieldDataDTCollection(extractPatientLDFs(form, clientVO));
    }
	
    private void filterCommonAnswers(Map<Object, Object> answerMap) throws Exception
    {
        Field fields[] = PageConstants.class.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i)
        {
            Field field = fields[i];
            String fieldNm = field.getName();
            String fieldTypeNm = field.getType().getName();
            if (fieldTypeNm.equals("java.lang.String"))
            {
                String key = getVal(field.get(fieldNm));
                if (getQuestionMap() != null && getQuestionMap().get(key) != null)
                {

                    NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) getQuestionMap().get(key);
                    if (qMetadata != null && qMetadata.getDataLocation() != null
                            && !qMetadata.getDataLocation().contains(NEDSSConstants.ANSWER_TXT))
                    {
                        answerMap.remove(key);
                    }
                }
            }
        }
    }

	//This method to filter out all other answers from answerMap to AnswerArrayMap
	//TODO:Replace this method with generic method 
	private static void filterOtherMultiSelectAnswerForSubmit(Map<Object,Object> answerMap, PageForm form)
	{
		Map<Object,Object> answerArrayMap = form.getPageClientVO().getArrayAnswerMap();
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		ArrayList<Object> toRemove = new ArrayList<Object>();
		Set<Object> keySet = answerMap.keySet();
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			while(iter.hasNext()) {
				Object questionId = iter.next();
				
				if(questionId.toString().endsWith("Oth")){
					String multiSelQue = questionId.toString().substring(0, questionId.toString().indexOf("Oth"));
					if(answerArrayMap.containsKey(multiSelQue))
					{
						String[] answers = (String[])answerArrayMap.get(multiSelQue);
						for(int i=1; i<=answers.length; i++) {
							String answerTxt = answers[i-1];
							if(answerTxt != null && answerTxt.equals("OTH")){
								String [] othAnswerList = new String[1];
								 othAnswerList[0] = getVal(answerMap.get(questionId));
								 toRemove.add(questionId);
								 returnMap.put(questionId, othAnswerList);
								 form.getPageClientVO().getArrayAnswerMap().putAll(returnMap);
							}
						}
					}
				}
			}
		}
		// remove all other question IDs from answerMap, as they are now in answerArrayMap
		for (int i=0; i< toRemove.size(); i++) {
			String key = (String) toRemove.get(i);
			answerMap.remove(key);
		}
		
	}
	
    private static void filterOtherMultiSelectAnswerForSubmit(Map<Object, Object> answerMap, BaseForm form,
            ClientVO clientVO)
    {
        Map<Object, Object> answerArrayMap = clientVO.getArrayAnswerMap();
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        ArrayList<Object> toRemove = new ArrayList<Object>();
        Set<Object> keySet = answerMap.keySet();
        if (!keySet.isEmpty())
        {
            Iterator<Object> iter = keySet.iterator();
            while (iter.hasNext())
            {
                Object questionId = iter.next();

                if (questionId.toString().endsWith("Oth"))
                {
                    String multiSelQue = questionId.toString().substring(0, questionId.toString().indexOf("Oth"));
                    if (answerArrayMap.containsKey(multiSelQue))
                    {
                        String[] answers = (String[]) answerArrayMap.get(multiSelQue);
                        for (int i = 1; i <= answers.length; i++)
                        {
                            String answerTxt = answers[i - 1];
                            if (answerTxt != null && answerTxt.equals("OTH"))
                            {
                                String[] othAnswerList = new String[1];
                                othAnswerList[0] = getVal(answerMap.get(questionId));
                                toRemove.add(questionId);
                                returnMap.put(questionId, othAnswerList);
                                clientVO.getArrayAnswerMap().putAll(returnMap);
                            }
                        }
                    }
                }
            }
        }
        // remove all other question IDs from answerMap, as they are now in
        // answerArrayMap
        for (int i = 0; i < toRemove.size(); i++)
        {
            String key = (String) toRemove.get(i);
            answerMap.remove(key);
        }

    }
	
	
	public void setPageSpecifcAnswersForCreateEdit(PamVO pageVO, ClientVO clientVO, PageForm form, HttpSession session, String userId) {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(clientVO.getAnswerMap());
		//filters common answers(DEMs and INVs) as they are just used to support UI / Rules
		try {
			filterCommonAnswers(answerMap);
			Map<Object,Object> map = form.getSubSecStructureMap();
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
		        String[][] batchrec = (String[][])pairs.getValue();
		        if(batchrec != null && batchrec.length >0){
		        for(int i=0;i<batchrec.length;i++){
		        	if(batchrec[i][0] != null && !batchrec[i][0].equalsIgnoreCase(""))
		        	   answerMap.remove(batchrec[i][0]);
		          }
		        }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering common answers from AnswerMap: " + e.toString());
		}
		Set<Object> keySet = answerMap.keySet();
		
		filterOtherMultiSelectAnswerForSubmit(answerMap, form);

		setAnswerArrayMapAnswers(form, returnMap, userId);
		setCheckBoxAnswersWithCodeSet(answerMap);
		//setOtherAnswers(form, answerMap, proxyVO);
		try{
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			String other = "";
			String unit = "";
			NbsAnswerDT answerDT = null;
			String questionIdCheckForOth = "";
			while(iter.hasNext()) {
				Object questionId = iter.next();
				Object obj = answerMap.get(questionId);

				if(obj instanceof ArrayList<?>) {
					returnMap.put(questionId, obj);
					continue;
				}
				NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)getQuestionMap().get(questionId);
				
				if(qMetadata.getDataLocation()!=null && !qMetadata.getDataLocation().toLowerCase().startsWith(RenderConstants.NBS_CASE_ANSWER.toLowerCase())){
					continue;
				}
				if(questionId.toString().endsWith("Oth") 
						&& questionId.toString().indexOf(questionIdCheckForOth)==0
						&& answer.equalsIgnoreCase("Oth")){
					other = getVal(answerMap.get(questionId));	
					answer = answer+"^"+other;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
				}
				if(questionId.toString().endsWith("Unit") && questionId.toString().indexOf(questionIdCheckForOth)==0){
					unit = getVal(answerMap.get(questionId));
					if((unit!=null && !unit.trim().equals("")) || (answer!=null && !answer.trim().equals(""))){
					answer = answer+"^"+unit;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
					}
				}
				questionIdCheckForOth=questionId.toString();
				answer = getVal(answerMap.get(questionId));
				
				if( answer!=null && !answer.trim().equals("")) {
					answerDT = new NbsAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);

					if(getQuestionMap().get(questionId) != null) {

						if(qMetadata != null && qMetadata.getNbsQuestionUid() != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							returnMap.put(questionId, answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAGE Answers");
					}
				}

			}
		}
		}catch(Exception e){
			logger.error("Error in executing setPageSpecifcAnswersForCreateEdit method of Page Create Helper: " + e.toString());
		}
		pageVO.setPamAnswerDTMap(returnMap);
	}
    //TODO: see generic below with base form
	private void setPageSpecifcAnswersForCreateEdit(PageActProxyVO proxyVO, PageForm form, HttpSession session, String userId) throws Exception {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(form.getPageClientVO().getAnswerMap());
		//filters common answers(DEMs and INVs) as they are just used to support UI / Rules
		try {
			filterCommonAnswers(answerMap);
			Map<Object,Object> map = form.getSubSecStructureMap();
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
		        String[][] batchrec = (String[][])pairs.getValue();
		        if(batchrec != null && batchrec.length >0){
		        for(int i=0;i<batchrec.length;i++){
		        	if(batchrec[i][0] != null && !batchrec[i][0].equalsIgnoreCase(""))
		        	   answerMap.remove(batchrec[i][0]);
		          }
		        }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering common answers from AnswerMap: " + e.toString());
			throw new Exception(e.getMessage());
		}
		Set<Object> keySet = answerMap.keySet();
		
		filterOtherMultiSelectAnswerForSubmit(answerMap, form);

		setAnswerArrayMapAnswers(form, returnMap, userId);
		setCheckBoxAnswersWithCodeSet(answerMap);
		//setOtherAnswers(form, answerMap, proxyVO);
		try{
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			String other = "";
			String unit = "";
			NbsAnswerDT answerDT = null;
			String questionIdCheckForOth = "";
			while(iter.hasNext()) {
				Object questionId = iter.next();
				Object obj = answerMap.get(questionId);

				if(obj instanceof ArrayList<?>) {
					returnMap.put(questionId, obj);
					continue;
				}
				NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)getQuestionMap().get(questionId);
				
				if(qMetadata!=null && qMetadata.getDataLocation()!=null && !qMetadata.getDataLocation().toLowerCase().startsWith(RenderConstants.NBS_CASE_ANSWER.toLowerCase())){
					continue;
				}
				if(questionId.toString().endsWith("Oth") 
						&& questionId.toString().indexOf(questionIdCheckForOth)==0
						&& answer!=null && answer.equalsIgnoreCase("Oth")){
					other = getVal(answerMap.get(questionId));	
					answer = answer+"^"+other;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
				}
				if(questionId.toString().endsWith("Unit") && questionId.toString().indexOf(questionIdCheckForOth)==0){
					unit = getVal(answerMap.get(questionId));
					if((unit!=null && !unit.trim().equals("")) || (answer!=null && !answer.trim().equals(""))){
					answer = answer+"^"+unit;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
					}
				}
				questionIdCheckForOth=questionId.toString();
				answer = getVal(answerMap.get(questionId));
				
				if( answer!=null && !answer.trim().equals("")) {
					answerDT = new NbsAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);

					if(getQuestionMap().get(questionId) != null) {

						if(qMetadata != null && qMetadata.getNbsQuestionUid() != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							returnMap.put(questionId, answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAGE Answers");
					}
				}

			}
		}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in executing setPageSpecifcAnswersForCreateEdit method of Page Create Helper: " + e.toString());
			throw new Exception(e.getMessage());
		}
		proxyVO.getPageVO().setPamAnswerDTMap(returnMap);
	}

	public Map<Object,Object>  setPageSpecifcAnswersForCreateEdit(BaseForm form, ClientVO clientVO, HttpSession session, String userId) {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		TreeMap<Object,Object> answerMap = new TreeMap<Object,Object>(clientVO.getAnswerMap());
		//filters common answers(DEMs and INVs) as they are just used to support UI / Rules
		try {
			filterCommonAnswers(answerMap);
			Map<Object,Object> map = form.getSubSecStructureMap();
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
		        String[][] batchrec = (String[][])pairs.getValue();
		        if(batchrec != null && batchrec.length >0){
		        for(int i=0;i<batchrec.length;i++){
		        	if(batchrec[i][0] != null && !batchrec[i][0].equalsIgnoreCase(""))
		        	   answerMap.remove(batchrec[i][0]);
		          }
		        }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering common answers from AnswerMap: " + e.toString());
		}
		Set<Object> keySet = answerMap.keySet();
		
		filterOtherMultiSelectAnswerForSubmit(answerMap, form, clientVO);
		setAnswerArrayMapAnswers(form, clientVO, returnMap, userId);
		setCheckBoxAnswersWithCodeSet(answerMap);
		//setOtherAnswers(form, answerMap, proxyVO);
		try{
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			String other = "";
			String unit = "";
			NbsAnswerDT answerDT = null;
			String questionIdCheckForOth = "";
			while(iter.hasNext()) {
				Object questionId = iter.next();
				Object obj = answerMap.get(questionId);

				if(obj instanceof ArrayList<?>) {
					returnMap.put(questionId, obj);
					continue;
				}
				
				
				if(questionId.toString().endsWith("Oth") 
						&& questionId.toString().indexOf(questionIdCheckForOth)==0
						&& answer.equalsIgnoreCase("Oth")){
					other = getVal(answerMap.get(questionId));	
					answer = answer+"^"+other;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
				}
				if(questionId.toString().endsWith("Unit") && questionId.toString().indexOf(questionIdCheckForOth)==0){
					unit = getVal(answerMap.get(questionId));
					if((unit!=null && !unit.trim().equals("")) || (answer!=null && !answer.trim().equals(""))){
					answer = answer+"^"+unit;
					answerDT.setAnswerTxt(answer);
					returnMap.put(questionIdCheckForOth, answerDT);
					}
				}
				
				
				
				if (!getQuestionMap().containsKey(questionId)) {
					logger.debug("skipping question " + questionId + " which is not in questionMap for the Form");
					continue;
				}
				NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)getQuestionMap().get(questionId);
				if( qMetadata == null )
				    continue;
				if(qMetadata.getDataLocation()!=null && !qMetadata.getDataLocation().toUpperCase().contains(RenderConstants.ANSWER_TXT)){
					continue;
				}
				
				questionIdCheckForOth=questionId.toString();
				answer = getVal(answerMap.get(questionId));
				
				if( answer!=null && !answer.trim().equals("")) {
					answerDT = new NbsAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);

					if(getQuestionMap().get(questionId) != null) {

						if(qMetadata != null && qMetadata.getNbsQuestionUid() != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							returnMap.put(questionId, answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAGE Answers");
					}
				}

			}
		}
		}catch(Exception e){
			logger.error("Error in executing setPageSpecifcAnswersForCreateEdit method of Page Create Helper: " + e.toString());
		}
		return returnMap;
	}
	private static Long setPublicHealthCaseForCreate(Long tempID, PageForm form, HttpServletRequest req, PageActProxyVO proxy, ProgramAreaVO programAreaVO, String userId) {

		Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
		String coinfectionID = null;
		CoinfectionSummaryVO coinfectionSummaryVO = null;
		String investigationType = null;
		try{
			if (NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSCoinfectionInvSummVO) != null) {
				coinfectionSummaryVO =(CoinfectionSummaryVO)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSCoinfectionInvSummVO);
				coinfectionID = coinfectionSummaryVO.getCoinfectionId();
			}
			if (NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationType) != null) {
				investigationType = (String) NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationType);
			}
        }catch(Exception ex){
        	logger.debug("Caught exception in setPublicHealthCaseForCreate:", ex);
        	logger.debug("Context exception related to co-infection " +ex.getMessage());
        }
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(tempID--));
		phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED)));
		phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(PageConstants.INV_START_DATE)));
		phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
		phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(userId));
		phcVO.getThePublicHealthCaseDT().setCaseClassCd(getVal(answerMap.get(PageConstants.CASE_CLS_CD)));
		phcVO.getThePublicHealthCaseDT().setCaseTypeCd("I");
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		answerMap.put(PageConstants.CONDITION_CD, programAreaVO.getConditionCd());
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(programAreaVO.getConditionShortNm());
		phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(getVal(answerMap.get(PageConstants.INV_STATUS_CD )));
		phcVO.getThePublicHealthCaseDT().setJurisdictionCd(getVal(answerMap.get(PageConstants.JURISDICTION)));
		phcVO.getThePublicHealthCaseDT().setMmwrWeek(getVal(answerMap.get(PageConstants.MMWR_WEEK)));
		phcVO.getThePublicHealthCaseDT().setMmwrYear(getVal(answerMap.get(PageConstants.MMWR_YEAR)));
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		phcVO.getThePublicHealthCaseDT().setStatusCd("A");
		phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
		if (coinfectionID != null && investigationType != null && investigationType.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF))
			phcVO.getThePublicHealthCaseDT().setCoinfectionId(coinfectionID);
		phcVO.setCoinfectionCondition(CachedDropDowns.getConditionCoinfectionMap().containsKey(programAreaVO.getConditionCd())? true:false);
		String sharedInd = null;
		if(answerMap.get(PageConstants.SHARED_IND)!= null && answerMap.get(PageConstants.SHARED_IND).equals("1"))
			sharedInd = "T";
		else if((answerMap.get(PageConstants.SHARED_IND)!= null && !answerMap.get(PageConstants.SHARED_IND).equals("1"))||answerMap.get(PageConstants.SHARED_IND)== null)
			sharedInd = "F";
		phcVO.getThePublicHealthCaseDT().setSharedInd(sharedInd);

		phcVO.getThePublicHealthCaseDT().setTxt(getVal(answerMap.get(PageConstants.TUB_GEN_COMMENTS)));
		// These questions are specific to Varicella
		if(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_COUNTY))!=null && !getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_COUNTY)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setRptToCountyTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_COUNTY)));
		}
		if(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_STATE))!=null && !getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_STATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setRptToStateTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_STATE)));
		}
		if(getVal(answerMap.get(PageConstants.DIAGNOSIS_DATE))!=null && !getVal(answerMap.get(PageConstants.DIAGNOSIS_DATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setDiagnosisTime_s(getVal(answerMap.get(PageConstants.DIAGNOSIS_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.ILLNESS_ONSET_DATE))!=null && !getVal(answerMap.get(PageConstants.ILLNESS_ONSET_DATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setEffectiveFromTime_s(getVal(answerMap.get(PageConstants.ILLNESS_ONSET_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET))!=null && !getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setPatAgeAtOnset(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET)));
		}
		if(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE))!=null && !getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setPatAgeAtOnsetUnitCd(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE)));
		}
		
		//Added for Rel4.5 
		if(getVal(answerMap.get(PageConstants.INV_CLOSED_DATE))!=null && !getVal(answerMap.get(PageConstants.INV_CLOSED_DATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setActivityToTime_s(getVal(answerMap.get(PageConstants.INV_CLOSED_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.CURR_PROCESS_STAGE_CD))!=null && !getVal(answerMap.get(PageConstants.CURR_PROCESS_STAGE_CD)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setCurrProcessStateCd(getVal(answerMap.get(PageConstants.CURR_PROCESS_STAGE_CD)));
		}
		if(getVal(answerMap.get(PageConstants.REFERRAL_BASIS_CD))!=null && !getVal(answerMap.get(PageConstants.REFERRAL_BASIS_CD)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setReferralBasisCd(getVal(answerMap.get(PageConstants.REFERRAL_BASIS_CD)));
		}//End Rel 4.5
		

		// These questions are for extending PHC table for common fields - ODS changes
		if(getVal(answerMap.get(PageConstants.DATE_ASSIGNED_TO_INVESTIGATION))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime_s(getVal(answerMap.get(PageConstants.DATE_ASSIGNED_TO_INVESTIGATION)));
		}
		if(getVal(answerMap.get(PageConstants.WAS_THE_PATIENT_HOSPITALIZED))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd(getVal(answerMap.get(PageConstants.WAS_THE_PATIENT_HOSPITALIZED)));
		}
		if(getVal(answerMap.get(PageConstants.ADMISSION_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedAdminTime_s(getVal(answerMap.get(PageConstants.ADMISSION_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.DISCHARGE_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedDischargeTime_s(getVal(answerMap.get(PageConstants.DISCHARGE_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.DURATION_OF_STAY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedDurationAmt_s(getVal(answerMap.get(PageConstants.DURATION_OF_STAY)));
		}
		if(getVal(answerMap.get(PageConstants.PREGNANCY_STATUS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setPregnantIndCd(getVal(answerMap.get(PageConstants.PREGNANCY_STATUS)));
		}
		if(getVal(answerMap.get(PageConstants.DID_THE_PATIENT_DIE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutcomeCd(getVal(answerMap.get(PageConstants.DID_THE_PATIENT_DIE)));
		}
		if(getVal(answerMap.get(PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setDayCareIndCd(getVal(answerMap.get(PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY)));
		}
		if(getVal(answerMap.get(PageConstants.IS_THIS_PERSON_FOOD_HANDLER))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd(getVal(answerMap.get(PageConstants.IS_THIS_PERSON_FOOD_HANDLER)));
		}
		if(getVal(answerMap.get(PageConstants.IMPORTED_COUNTRY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCountryCd(getVal(answerMap.get(PageConstants.IMPORTED_COUNTRY)));
		}
		if(getVal(answerMap.get(PageConstants.IMPORTED_STATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedStateCd(getVal(answerMap.get(PageConstants.IMPORTED_STATE)));
		}
		if(getVal(answerMap.get(PageConstants.IMPORTED_CITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt(getVal(answerMap.get(PageConstants.IMPORTED_CITY)));
		}
		if(getVal(answerMap.get(PageConstants.IMPORTED_COUNTY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCountyCd(getVal(answerMap.get(PageConstants.IMPORTED_COUNTY)));
		}
		if(getVal(answerMap.get(PageConstants.INVESTIGATION_DEATH_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setDeceasedTime_s(getVal(answerMap.get(PageConstants.INVESTIGATION_DEATH_DATE)));
		}
		if(getVal(answerMap.get(PageConstants.OUTBREAK_INDICATOR))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutbreakInd(getVal(answerMap.get(PageConstants.OUTBREAK_INDICATOR)));
		}
		if(getVal(answerMap.get(PageConstants.OUTBREAK_NAME))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutbreakName(getVal(answerMap.get(PageConstants.OUTBREAK_NAME)));
		}
		//Added for Contact Tracing 
		if(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_FROM))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInfectiousFromDate_s(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_FROM)));
		}
		if(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_TO))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInfectiousToDate_s(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_TO)));
		}
		if(getVal(answerMap.get(PageConstants.CONTACT_PRIORITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setPriorityCd(getVal(answerMap.get(PageConstants.CONTACT_PRIORITY)));
		}
		if(getVal(answerMap.get(PageConstants.CONTACT_STATUS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setContactInvStatus(getVal(answerMap.get(PageConstants.CONTACT_STATUS)));
		}
		if(getVal(answerMap.get(PageConstants.CONTACT_COMMENTS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setContactInvTxt(getVal(answerMap.get(PageConstants.CONTACT_COMMENTS)));
		}
		
		setAdditionalPhcAnswersForCreateEdit(phcVO, form);
		phcVO.setItNew(true);
		phcVO.setItDirty(false);

		Collection<Object>  theActIdDTCollection  = new ArrayList<Object> ();
		ActIdDT actIDDT = new ActIdDT();
		actIDDT.setItNew(true);
		actIDDT.setActIdSeq(new Integer(1));
		actIDDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
		actIDDT.setRootExtensionTxt(getVal(answerMap.get(PageConstants.STATE_CASE)));
		theActIdDTCollection.add(actIDDT);

		ActIdDT actIDDT1 = new ActIdDT();
		actIDDT1.setItNew(true);
		actIDDT1.setActIdSeq(new Integer(2));
		actIDDT1.setTypeCd(NEDSSConstants.ACT_ID_CITY_TYPE_CD);
		actIDDT1.setRootExtensionTxt(getVal(answerMap.get(PageConstants.COUNTY_CASE)));
		theActIdDTCollection.add(actIDDT1);
		
		ActIdDT actIDDT2 = new ActIdDT();
		actIDDT2.setItNew(true);
		actIDDT2.setActIdSeq(new Integer(3));
		actIDDT2.setTypeCd(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD);
		actIDDT2.setRootExtensionTxt(getVal(answerMap.get(PageConstants.LEGACY_CASE_ID)));
		theActIdDTCollection.add(actIDDT2);

		phcVO.setTheActIdDTCollection(theActIdDTCollection);

		proxy.setPublicHealthCaseVO(phcVO);
		return tempID;

	}

	private static void setPublicHealthCaseForEdit(PageForm form, HttpServletRequest req, PageActProxyVO proxyVO, String userId) {

		Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
		PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
		String caseClassCd = answerMap.get(PageConstants.CASE_CLS_CD) == null ? "" : answerMap.get(PageConstants.CASE_CLS_CD).toString();
		String oldCaseClassCd = phcVO.getThePublicHealthCaseDT().getCaseClassCd();
		phcVO.getThePublicHealthCaseDT().setCaseClassCd(caseClassCd);
		phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED)));
		phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(PageConstants.INV_START_DATE)));
		phcVO.getThePublicHealthCaseDT().setMmwrWeek(getVal(answerMap.get(PageConstants.MMWR_WEEK)));
		phcVO.getThePublicHealthCaseDT().setMmwrYear(getVal(answerMap.get(PageConstants.MMWR_YEAR)));
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(getVal(answerMap.get(PageConstants.INV_STATUS_CD )));
		phcVO.getThePublicHealthCaseDT().setLastChgUserId(Long.valueOf(userId));
		phcVO.getThePublicHealthCaseDT().setLastChgTime(new Timestamp(new Date().getTime()));
		String sharedInd = null;
		if(answerMap.get(PageConstants.SHARED_IND)!= null && answerMap.get(PageConstants.SHARED_IND).equals("1"))
			sharedInd = "T";
		else if((answerMap.get(PageConstants.SHARED_IND)!= null && !answerMap.get(PageConstants.SHARED_IND).equals("1"))||answerMap.get(PageConstants.SHARED_IND)== null)
			sharedInd = "F";
		phcVO.getThePublicHealthCaseDT().setSharedInd(sharedInd);
		phcVO.getThePublicHealthCaseDT().setTxt(getVal(answerMap.get(PageConstants.TUB_GEN_COMMENTS)));
		phcVO.getThePublicHealthCaseDT().setRptToCountyTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_COUNTY)));
		phcVO.getThePublicHealthCaseDT().setRptToStateTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED_TO_STATE)));
		phcVO.getThePublicHealthCaseDT().setDiagnosisTime_s(getVal(answerMap.get(PageConstants.DIAGNOSIS_DATE)));
		phcVO.getThePublicHealthCaseDT().setEffectiveFromTime_s(getVal(answerMap.get(PageConstants.ILLNESS_ONSET_DATE)));
		phcVO.getThePublicHealthCaseDT().setPatAgeAtOnset(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET)));
		phcVO.getThePublicHealthCaseDT().setPatAgeAtOnsetUnitCd(getVal(answerMap.get(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE)));
		phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime_s(getVal(answerMap.get(PageConstants.DATE_ASSIGNED_TO_INVESTIGATION)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd(getVal(answerMap.get(PageConstants.WAS_THE_PATIENT_HOSPITALIZED)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedAdminTime_s(getVal(answerMap.get(PageConstants.ADMISSION_DATE)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedDischargeTime_s(getVal(answerMap.get(PageConstants.DISCHARGE_DATE)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedDurationAmt_s(getVal(answerMap.get(PageConstants.DURATION_OF_STAY)));
		phcVO.getThePublicHealthCaseDT().setPregnantIndCd(getVal(answerMap.get(PageConstants.PREGNANCY_STATUS)));
		phcVO.getThePublicHealthCaseDT().setOutcomeCd(getVal(answerMap.get(PageConstants.DID_THE_PATIENT_DIE)));
		phcVO.getThePublicHealthCaseDT().setDayCareIndCd(getVal(answerMap.get(PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY)));
		phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd(getVal(answerMap.get(PageConstants.IS_THIS_PERSON_FOOD_HANDLER)));
		phcVO.getThePublicHealthCaseDT().setImportedCountryCd(getVal(answerMap.get(PageConstants.IMPORTED_COUNTRY)));
		phcVO.getThePublicHealthCaseDT().setImportedStateCd(getVal(answerMap.get(PageConstants.IMPORTED_STATE)));
		phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt(getVal(answerMap.get(PageConstants.IMPORTED_CITY)));
		phcVO.getThePublicHealthCaseDT().setImportedCountyCd(getVal(answerMap.get(PageConstants.IMPORTED_COUNTY)));
		phcVO.getThePublicHealthCaseDT().setDeceasedTime_s(getVal(answerMap.get(PageConstants.INVESTIGATION_DEATH_DATE)));
		phcVO.getThePublicHealthCaseDT().setOutbreakInd(getVal(answerMap.get(PageConstants.OUTBREAK_INDICATOR)));
		phcVO.getThePublicHealthCaseDT().setOutbreakName(getVal(answerMap.get(PageConstants.OUTBREAK_NAME)));
		//Added for Rel4.5
		phcVO.getThePublicHealthCaseDT().setActivityToTime_s(getVal(answerMap.get(PageConstants.INV_CLOSED_DATE)));
		phcVO.getThePublicHealthCaseDT().setCurrProcessStateCd(getVal(answerMap.get(PageConstants.CURR_PROCESS_STAGE_CD)));
		phcVO.getThePublicHealthCaseDT().setReferralBasisCd(getVal(answerMap.get(PageConstants.REFERRAL_BASIS_CD)));

		//Added for Contact Tracing
		phcVO.getThePublicHealthCaseDT().setInfectiousFromDate_s(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_FROM)));
		phcVO.getThePublicHealthCaseDT().setInfectiousToDate_s(getVal(answerMap.get(PageConstants.INFECTIOUS_PERIOD_TO)));
		phcVO.getThePublicHealthCaseDT().setPriorityCd(getVal(answerMap.get(PageConstants.CONTACT_PRIORITY)));
		phcVO.getThePublicHealthCaseDT().setContactInvStatus(getVal(answerMap.get(PageConstants.CONTACT_STATUS)));
		phcVO.getThePublicHealthCaseDT().setContactInvTxt(getVal(answerMap.get(PageConstants.CONTACT_COMMENTS)));
		//for STD only, the Case Diagnosis has the power to change the condition code
		if (PropertyUtil.isStdOrHivProgramArea(phcVO.getThePublicHealthCaseDT().getProgAreaCd())) {
			String caseDiagnosis = (String) answerMap.get(PageConstants.CASE_DIAGNOSIS);
			if (caseDiagnosis != null) {
				String changedConditionCd = checkForStdConditionChange(caseDiagnosis, phcVO.getThePublicHealthCaseDT().getCd());
				if (changedConditionCd != null && !changedConditionCd.isEmpty()) {
					String conditionDesc =  CachedDropDowns.getConditionDesc(changedConditionCd);
					if (conditionDesc != null && !conditionDesc.isEmpty()) {
						phcVO.getThePublicHealthCaseDT().setCd(changedConditionCd);
						phcVO.getThePublicHealthCaseDT().setCdDescTxt(conditionDesc);
					}
				}
			}
		}
				
		setAdditionalPhcAnswersForCreateEdit(phcVO, form);
		phcVO.setItNew(false);
		phcVO.setItDirty(true);
		setActIdForPublicHealthCaseEdit(phcVO, answerMap);
		boolean caseStatusChanged = caseStatusChanged(oldCaseClassCd, caseClassCd);
		phcVO.getThePublicHealthCaseDT().setCaseStatusDirty(caseStatusChanged);
		proxyVO.setPublicHealthCaseVO(phcVO);

	}

	public static PersonVO setPatientForEventCreate(Long patientUid, Long tempID, PageActProxyVO proxyVO, PageForm form, HttpServletRequest req, String userId) {

		PersonVO personVO = new PersonVO();
		Collection<Object>  patientColl = new ArrayList<Object> ();
		personVO.setItNew(true);
		personVO.getThePersonDT().setItNew(true);
		personVO.getThePersonDT().setPersonParentUid(patientUid);
		personVO.getThePersonDT().setPersonUid(new Long(tempID));
		personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
		personVO.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
		personVO.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		personVO.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
		if(proxyVO.getPageProxyTypeCd()!=null) {
			personVO.setAddReasonCode(proxyVO.getPageProxyTypeCd());
		}
		//persist PersonVO Components
		setPatientForEventCreate(personVO, form, proxyVO, userId);
		
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS))
			personVO.getThePersonDT().setEharsId((String)form.getPageClientVO().getHivAnswerMap().get(PageConstants.EHARS_ID));
		//LDFs
		personVO.setTheStateDefinedFieldDataDTCollection(extractPatientLDFs(form));
		patientColl.add(personVO);

		if (patientColl.size() > 0) {
			proxyVO.setThePersonVOCollection(patientColl);
		}
		return personVO;
	}

	
	
	//ToDo- Set references to Generic version passing clientVO
	private static Collection<Object>  extractPatientLDFs(PageForm form) {
		String actionMode = form.getActionMode();
		ArrayList<Object> returnList = new ArrayList<Object> ();
		ArrayList<Object> toRemove = new ArrayList<Object> ();
		Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
		Iterator<Object> iter = answerMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			if(key != null && key.startsWith("PATLDF_")) {
				String value = answerMap.get(key) == null ? "" : (String) answerMap.get(key);
				if(value.equals(""))
					continue;
				StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
				Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
				stateDT.setLdfUid(ldfUid);
				stateDT.setBusinessObjNm("PAT");
				stateDT.setLdfValue(value);
				if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
					stateDT.setItDirty(true);
				returnList.add(stateDT);
				toRemove.add(key);
			}
		}
		Map<Object,Object> answerArrayMap = form.getPageClientVO().getArrayAnswerMap();
		Iterator<Object> it = answerArrayMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(key.startsWith("PATLDF_")) {
				String[] valueList = (String[])answerArrayMap.get(key);
				  if(valueList != null && valueList.length > 0) {
						StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
						Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
						stateDT.setLdfUid(ldfUid);
						stateDT.setBusinessObjNm("PAT");
						stateDT.setLdfValue(makeAnswerString(valueList));
						if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
							stateDT.setItDirty(true);
						returnList.add(stateDT);
						toRemove.add(key);
				  }
			}
		}
		//Remove LDFs from NBSAnswers
		for (int i=0; i< toRemove.size(); i++) {
			String key = (String) toRemove.get(i);
			answerMap.remove(key);
		}
		return returnList;
	}
	
	/**
	 * extractPatientLDFs (generic version)
	 * @param baseForm
	 * @param clientVO (i.e. (ClientVO) form.getcTContactClientVO())
	 * @return returnlist is for personVO.setTheStateDefinedFieldDataDTCollection()
	 */
	private static Collection<Object>  extractPatientLDFs(BaseForm form, ClientVO clientVO) {
		String actionMode = form.getActionMode();
		ArrayList<Object> returnList = new ArrayList<Object> ();
		ArrayList<Object> toRemove = new ArrayList<Object> ();
		Map<Object,Object> answerMap = clientVO.getAnswerMap();
		Iterator<Object> iter = answerMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			if(key != null && key.startsWith("PATLDF_")) {
				String value = answerMap.get(key) == null ? "" : (String) answerMap.get(key);
				if(value.equals(""))
					continue;
				StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
				Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
				stateDT.setLdfUid(ldfUid);
				stateDT.setBusinessObjNm("PAT");
				stateDT.setLdfValue(value);
				if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
					stateDT.setItDirty(true);
				returnList.add(stateDT);
				toRemove.add(key);
			}
		}
		Map<Object,Object> answerArrayMap = clientVO.getArrayAnswerMap();
		Iterator<Object> it = answerArrayMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(key.startsWith("PATLDF_")) {
				String[] valueList = (String[])answerArrayMap.get(key);
				  if(valueList != null && valueList.length > 0) {
						StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
						Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
						stateDT.setLdfUid(ldfUid);
						stateDT.setBusinessObjNm("PAT");
						stateDT.setLdfValue(makeAnswerString(valueList));
						if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
							stateDT.setItDirty(true);
						returnList.add(stateDT);
						toRemove.add(key);
				  }
			}
		}
		//Remove LDFs from NBSAnswers
		for (int i=0; i< toRemove.size(); i++) {
			String key = (String) toRemove.get(i);
			answerMap.remove(key);
		}
		return returnList;
	}
	
	
	private static String makeAnswerString(String[] valueList) {
		StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < valueList.length; i++) {
				String tkn = valueList[i];
				sb.append(tkn).append("|");
		  }
		return sb.toString();
	}

	//ToDo: Change references to use generic version below passing clientVO
	private static void setPatientForEventCreate(PersonVO personVO, PageForm form, PageActProxyVO proxyVO, String userId) {

		 Map<Object,Object> aodMap = new HashMap<Object,Object>();

		 setDemographicInfoForCreate(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId);
	     setNames(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId);
	     setAddressesForCreate(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId);
	     setEthnicityForCreate(personVO, form.getPageClientVO().getAnswerMap(), userId);
	     setRaceForCreate(personVO, form.getPageClientVO(), proxyVO, userId);
	     setTelephones(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId);
	     setIds(aodMap, personVO, form.getPageClientVO().getAnswerMap(), userId, 1);
	     form.getPageClientVO().getAnswerMap().putAll(aodMap);
	}

	/**
	 * setPatientForEventCreate (generic version)
	 * @param personVO
	 * @param clientVO (i.e. (ClientVO) form.getcTContactClientVO())
	 * @param proxyVO
	 * @param userid
	 */
	public static void setPatientForEventCreate(PersonVO personVO, ClientVO clientVO, String userId) {

		 Map<Object,Object> aodMap = new HashMap<Object,Object>();

		 setDemographicInfoForCreate(aodMap, personVO, clientVO.getAnswerMap(), userId);
	     setNames(aodMap, personVO, clientVO.getAnswerMap(), userId);
	     setAddressesForCreate(aodMap, personVO, clientVO.getAnswerMap(), userId);
	     setEthnicityForCreate(personVO, clientVO.getAnswerMap(), userId);
	     setRaceForCreate(personVO, clientVO, userId);
	     setTelephones(aodMap, personVO, clientVO.getAnswerMap(), userId);
	     setIds(aodMap, personVO, clientVO.getAnswerMap(), userId, 1);
	     clientVO.getAnswerMap().putAll(aodMap);
	}
	private static void setDemographicInfoForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		String dob = getVal(answerMap.get(PageConstants.DOB));
		String repAge = getVal(answerMap.get(PageConstants.REP_AGE));
		String bSex = getVal(answerMap.get(PageConstants.BIRTH_SEX));
		String cSex = getVal(answerMap.get(PageConstants.CURR_SEX));
		String additionalGender = getVal(answerMap.get(PageConstants.ADDITIONAL_GENDER));
		String prefferedGenderCd = getVal(answerMap.get(PageConstants.TRANSGENDER_INFORMATION));

		//gst-added Birth Country for Contact Page
		String birthCountry = answerMap.get(PageConstants.BIRTH_COUNTRY) == null ? null : (String) answerMap.get(PageConstants.BIRTH_COUNTRY);		

		if(dob != "" || repAge != "" || bSex != "" || cSex != "" || additionalGender !=""
				|| (birthCountry != null && !birthCountry.isEmpty()) || (prefferedGenderCd != null && !prefferedGenderCd.isEmpty()) ) {
			personVO.getThePersonDT().setAsOfDateSex(setPDate(answerMap, PageConstants.SEX_AND_BIRTH_INFORMATION_AS_OF));

		} else {
			personVO.getThePersonDT().setAsOfDateSex(null);
			answerMap.remove(PageConstants.SEX_AND_BIRTH_INFORMATION_AS_OF);
		}

		if (birthCountry != null) {
			setTheBirthAddressCountry(personVO, birthCountry, userId, (String) answerMap.get(PageConstants.SEX_AND_BIRTH_INFORMATION_AS_OF));
		}
		
		String deceased = getVal(answerMap.get(PageConstants.IS_PAT_DECEASED));
		if(deceased.equals("")) {
			personVO.getThePersonDT().setAsOfDateMorbidity(null);
			answerMap.remove(PageConstants.MORTALITY_INFORMATION_AS_OF);
		} else
			personVO.getThePersonDT().setAsOfDateMorbidity(setPDate(answerMap, PageConstants.MORTALITY_INFORMATION_AS_OF));

	
		//MARITAL_STATUS_AS_OF
		String marital = answerMap.get(PageConstants.MAR_STAT) == null ? null : (String) answerMap.get(PageConstants.MAR_STAT);
		String priOccupation = answerMap.get(PageConstants.PRIMARY_OCCUPATION) == null ? null : (String) answerMap.get(PageConstants.PRIMARY_OCCUPATION);
		String priLanguage = answerMap.get(PageConstants.PRIMARY_LANGUAGE) == null ? null : (String) answerMap.get(PageConstants.PRIMARY_LANGUAGE);
		String speaksEnglish = answerMap.get(PageConstants.SPEAKS_ENGLISH) == null ? null : (String) answerMap.get(PageConstants.SPEAKS_ENGLISH);
		if((marital != null && marital.trim().length() > 0) || 
				(priOccupation != null && priOccupation.trim().length() > 0) || 
				(priLanguage != null && priLanguage.trim().length() > 0) || 
				(birthCountry != null && birthCountry.trim().length() > 0) ||
				(speaksEnglish != null && speaksEnglish.trim().length() > 0)) {	
			personVO.getThePersonDT().setAsOfDateGeneral(setPDate(answerMap, PageConstants.MARITAL_STATUS_AS_OF));
		} else {
			personVO.getThePersonDT().setAsOfDateGeneral(null);
			answerMap.remove(PageConstants.MARITAL_STATUS_AS_OF);
		}


		String ethnicity = getVal(answerMap.get(PageConstants.ETHNICITY));
		String ethnicUnkReasonCd = getVal(answerMap.get(PageConstants.ETHNICITY_UNK_REASON));
		if(ethnicity.equals("")) {
			personVO.getThePersonDT().setAsOfDateEthnicity_s(null);
		} 
		if (!ethnicity.isEmpty() || !ethnicUnkReasonCd.isEmpty())
			personVO.getThePersonDT().setAsOfDateEthnicity(setPDate(answerMap, PageConstants.ETHNICITY_AS_OF));
		else
			answerMap.remove(PageConstants.ETHNICITY_AS_OF);

		personVO.getThePersonDT().setDescription(getVal(answerMap.get(PageConstants.GEN_COMMENTS)));
		personVO.getThePersonDT().setCurrSexCd(getVal(answerMap.get(PageConstants.CURR_SEX)));
		personVO.getThePersonDT().setAdditionalGenderCd(getVal(answerMap.get(PageConstants.ADDITIONAL_GENDER)));
		personVO.getThePersonDT().setPreferredGenderCd(getVal(answerMap.get(PageConstants.TRANSGENDER_INFORMATION)));
		personVO.getThePersonDT().setSexUnkReasonCd(getVal(answerMap.get(PageConstants.SEX_UNKNOWN_REASON)));		
		personVO.getThePersonDT().setDeceasedIndCd(getVal(answerMap.get(PageConstants.IS_PAT_DECEASED)));
		personVO.getThePersonDT().setMaritalStatusCd(getVal(answerMap.get(PageConstants.MAR_STAT)));
		personVO.getThePersonDT().setPrimLangCd(priLanguage); //added to support contact page
		personVO.getThePersonDT().setSpeaksEnglishCd(getVal(answerMap.get(PageConstants.SPEAKS_ENGLISH)));
		personVO.getThePersonDT().setOccupationCd(priOccupation); 
		personVO.getThePersonDT().setBirthCntryCd(birthCountry); //also set in ELP PL
		personVO.getThePersonDT().setEthnicGroupInd(getVal(answerMap.get(PageConstants.ETHNICITY)));
		personVO.getThePersonDT().setEthnicUnkReasonCd(getVal(answerMap.get(PageConstants.ETHNICITY_UNK_REASON)));

		setCommonDemographicInfo(personVO, answerMap);

	}
	private static void setCommonDemographicInfo(PersonVO personVO, Map<Object,Object> answerMap) {

		String asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
		personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);
		
		personVO.getThePersonDT().setBirthTime(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(PageConstants.DOB))));
		if(personVO.getThePersonDT().getBirthTime() != null) {
			personVO.getThePersonDT().setBirthTimeCalc(personVO.getThePersonDT().getBirthTime());
		}
		personVO.getThePersonDT().setAgeReported(getVal(answerMap.get(PageConstants.REP_AGE)));
		personVO.getThePersonDT().setAgeReportedUnitCd(getVal(answerMap.get(PageConstants.REP_AGE_UNITS)));
		personVO.getThePersonDT().setDeceasedTime(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(PageConstants.DECEASED_DATE))));
		personVO.getThePersonDT().setBirthGenderCd(getVal(answerMap.get(PageConstants.BIRTH_SEX)));
		personVO.getThePersonDT().setCurrSexCd(getVal(answerMap.get(PageConstants.CURR_SEX)));
		personVO.getThePersonDT().setAdditionalGenderCd(getVal(answerMap.get(PageConstants.ADDITIONAL_GENDER)));
		personVO.getThePersonDT().setPreferredGenderCd(getVal(answerMap.get(PageConstants.TRANSGENDER_INFORMATION)));
		personVO.getThePersonDT().setSexUnkReasonCd(getVal(answerMap.get(PageConstants.SEX_UNKNOWN_REASON)));

	}
	private static void setDemographicInfoForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		String asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
		//General Comments
		String generalComments = answerMap.get(PageConstants.GEN_COMMENTS) == null ? null : (String) answerMap.get(PageConstants.GEN_COMMENTS);
		if(generalComments != null && generalComments.trim().length() > 0) {
			personVO.getThePersonDT().setDescription(generalComments);
			personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);
			aodMap.put(PageConstants.DEM_DATA_AS_OF, asOfDate);
		}
		personVO.getThePersonDT().setEharsId(getVal(answerMap.get(PageConstants.EHARS_ID)));
		//Sex and BirthInfoAsOf
		String dob = getVal(answerMap.get(PageConstants.DOB));
		String repAge = getVal(answerMap.get(PageConstants.REP_AGE));
		String bSex = getVal(answerMap.get(PageConstants.BIRTH_SEX));
		String cSex = getVal(answerMap.get(PageConstants.CURR_SEX));
		String additionalGender = getVal(answerMap.get(PageConstants.ADDITIONAL_GENDER));
		String prefferedGenderCd = getVal(answerMap.get(PageConstants.TRANSGENDER_INFORMATION));

		//gst-added Birth Country for Contact Page
		//gst-added Sex Unknown and Trans Info for STD Contact
		String birthCountry = answerMap.get(PageConstants.BIRTH_COUNTRY) == null ? null : (String) answerMap.get(PageConstants.BIRTH_COUNTRY);		

		if(dob != "" || repAge != "" || bSex != "" || cSex != "" || additionalGender!="" 
				|| (birthCountry != null && !birthCountry.trim().isEmpty()) 
				|| additionalGender != "" || prefferedGenderCd != "" ) {
			setCommonDemographicInfo(personVO, answerMap);
			personVO.getThePersonDT().setAsOfDateSex_s(asOfDate);
			aodMap.put(PageConstants.SEX_AND_BIRTH_INFORMATION_AS_OF, asOfDate);
			if (birthCountry != null && !birthCountry.trim().isEmpty()) {
				setTheBirthAddressCountry(personVO, birthCountry, userId, asOfDate);
			}
		}

		//Mortality InfoAsOf
		String deceased = answerMap.get(PageConstants.IS_PAT_DECEASED) == null ? null : (String) answerMap.get(PageConstants.IS_PAT_DECEASED);
		if(deceased != null && deceased.trim().length() > 0) {
			personVO.getThePersonDT().setDeceasedIndCd(deceased);
			personVO.getThePersonDT().setAsOfDateMorbidity_s(asOfDate);
			aodMap.put(PageConstants.MORTALITY_INFORMATION_AS_OF, asOfDate);
		}
  
		//MARITAL_STATUS_AS_OF
		String marital = answerMap.get(PageConstants.MAR_STAT) == null ? null : (String) answerMap.get(PageConstants.MAR_STAT);
		String priOccupation = answerMap.get(PageConstants.PRIMARY_OCCUPATION) == null ? null : (String) answerMap.get(PageConstants.PRIMARY_OCCUPATION);
		String priLanguage = answerMap.get(PageConstants.PRIMARY_LANGUAGE) == null ? null : (String) answerMap.get(PageConstants.PRIMARY_LANGUAGE);
		String speaksEnglish = answerMap.get(PageConstants.SPEAKS_ENGLISH) == null ? null : (String) answerMap.get(PageConstants.SPEAKS_ENGLISH);
		if((marital != null && marital.trim().length() > 0) || 
				(priOccupation != null && priOccupation.trim().length() > 0) || 
				(priLanguage != null && priLanguage.trim().length() > 0) ||
				(speaksEnglish != null && speaksEnglish.trim().length() > 0)) {	
			personVO.getThePersonDT().setMaritalStatusCd(marital);
			personVO.getThePersonDT().setPrimLangCd(priLanguage); //added to support contact page
			personVO.getThePersonDT().setSpeaksEnglishCd(speaksEnglish); //added for STD Contact
			personVO.getThePersonDT().setOccupationCd(priOccupation); //added to support contact page
			personVO.getThePersonDT().setBirthCntryCd(birthCountry); //also in ELP PL.country_cd
			personVO.getThePersonDT().setAsOfDateGeneral_s(asOfDate);
			aodMap.put(PageConstants.MARITAL_STATUS_AS_OF, asOfDate);
		}
		//EthnicityAsOf
		String ethnicity = answerMap.get(PageConstants.ETHNICITY) == null ? null : (String) answerMap.get(PageConstants.ETHNICITY);
		String ethnicUnkReasonCd = getVal(answerMap.get(PageConstants.ETHNICITY_UNK_REASON));
		if((ethnicity != null && ethnicity.trim().length() > 0) || (ethnicUnkReasonCd != null && ethnicUnkReasonCd.trim().length() > 0)) {
			personVO.getThePersonDT().setEthnicGroupInd(ethnicity);
			personVO.getThePersonDT().setEthnicUnkReasonCd(ethnicUnkReasonCd);
			personVO.getThePersonDT().setAsOfDateEthnicity_s(asOfDate);
			aodMap.put(PageConstants.ETHNICITY_AS_OF, asOfDate);
		}
	}
   private static void setNames(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		try {
			Long personUID = personVO.getThePersonDT().getPersonUid();
			String lastNm = getVal(answerMap.get(PageConstants.LAST_NM));
			String firstNm = getVal(answerMap.get(PageConstants.FIRST_NM));
			String middleNm = getVal(answerMap.get(PageConstants.MIDDLE_NM));
			String suffix = getVal(answerMap.get(PageConstants.SUFFIX));
			String alias = getVal(answerMap.get(PageConstants.ALIAS_NICK_NAME));
			String asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));

			
			if( (lastNm != null && !lastNm.trim().equals("")) ||
					(firstNm != null && !firstNm.trim().equals("")) ||
					(middleNm != null && !middleNm.trim().equals("")) ||
					(alias != null && !alias.trim().equals(""))||
					(suffix != null && !suffix.trim().equals("")))
			{
				Collection<Object>  pdts = new ArrayList<Object> ();
				if ( (lastNm != null && !lastNm.trim().equals("")) ||
					(firstNm != null && !firstNm.trim().equals("")) ||
					(middleNm != null && !middleNm.trim().equals("")) ||
						(suffix != null && !suffix.trim().equals(""))) {

					if(aodMap != null)
						aodMap.put(PageConstants.NAME_INFORMATION_AS_OF, asOfDate);

					PersonNameDT pdt = new PersonNameDT();
					pdt.setItNew(true);
					pdt.setItDirty(false);
					pdt.setAddTime(new Timestamp(new Date().getTime()));
					pdt.setAddUserId(Long.valueOf(userId));
					pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
					pdt.setPersonNameSeq(new Integer(1));
					pdt.setStatusTime(new Timestamp(new Date().getTime()));
					pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
					pdt.setPersonUid(personUID);
					pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
					pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					pdt.setLastNm(lastNm);
					pdt.setFirstNm(firstNm);
					pdt.setMiddleNm(middleNm);
					pdt.setAsOfDate_s(asOfDate);
					pdt.setNmSuffix(suffix);
					pdt.setLastChgTime(new Timestamp(new Date().getTime()));
					pdt.setLastChgUserId(Long.valueOf(userId));
					pdt.setAddReasonCd(personVO.getAddReasonCode());
					pdts.add(pdt);
					
				} 
			
				if (alias != null && !alias.trim().equals("")) {

					if(aodMap != null)
						aodMap.put(PageConstants.NAME_INFORMATION_AS_OF, asOfDate);

					PersonNameDT pAliasDt = new PersonNameDT();
					pAliasDt.setItNew(true);
					pAliasDt.setItDirty(false);
					pAliasDt.setAddTime(new Timestamp(new Date().getTime()));
					pAliasDt.setAddUserId(Long.valueOf(userId));
					pAliasDt.setNmUseCd(NEDSSConstants.ALIAS_NAME);
					pAliasDt.setPersonNameSeq(new Integer(2));
					pAliasDt.setStatusTime(new Timestamp(new Date().getTime()));
					pAliasDt.setRecordStatusTime(new Timestamp(new Date().getTime()));
					pAliasDt.setPersonUid(personUID);
					pAliasDt.setRecordStatusCd(NEDSSConstants.ACTIVE);
					pAliasDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					pAliasDt.setAsOfDate_s(asOfDate);
					pAliasDt.setFirstNm(alias);
					pAliasDt.setAddReasonCd(personVO.getAddReasonCode());
					pdts.add(pAliasDt);
						
				}
				personVO.setThePersonNameDTCollection(pdts);
				
			}else {
				answerMap.remove(PageConstants.NAME_INFORMATION_AS_OF);
			}
		} catch (NumberFormatException e) {
        	logger.error("Caught exception in setNames:", e);
        	logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
   }

   private static void setAddressesForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		Collection<Object>  arrELP = personVO.
				getTheEntityLocatorParticipationDTCollection();
		if (arrELP == null) {
		arrELP = new ArrayList<Object> ();
		}
		String city = getVal(answerMap.get(PageConstants.CITY));
		String street1 = getVal(answerMap.get(PageConstants.ADDRESS_1));
		String street2 = getVal(answerMap.get(PageConstants.ADDRESS_2));
		String zip = getVal(answerMap.get(PageConstants.ZIP));
		String state = getVal(answerMap.get(PageConstants.STATE));
		String county = getVal(answerMap.get(PageConstants.COUNTY));
		String country = getVal(answerMap.get(PageConstants.COUNTRY));
		String asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
		if (asOfDate == null || asOfDate.isEmpty())
			asOfDate = getVal(answerMap.get(PageConstants.ADDRESS_INFORMATION_AS_OF));
		String cityLimits = getVal(answerMap.get(PageConstants.WITHIN_CITY_LIMITS));
		String censusTract = getVal(answerMap.get(PageConstants.CENSUS_TRACT));
		String workAsOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
		if (workAsOfDate == null || workAsOfDate.isEmpty())
			workAsOfDate = getVal(answerMap.get(PageConstants.ADDRESS_INFORMATION_AS_OF+"_W"));	
		String workCity = getVal(answerMap.get(PageConstants.CITY+"_W"));
		String workStreet1 = getVal(answerMap.get(PageConstants.ADDRESS_1+"_W"));
		String workStreet2 = getVal(answerMap.get(PageConstants.ADDRESS_2+"_W"));
		String workZip = getVal(answerMap.get(PageConstants.ZIP+"_W"));
		String workState = getVal(answerMap.get(PageConstants.STATE+"_W"));
		String workCounty = getVal(answerMap.get(PageConstants.COUNTY+"_W"));
		String workCountry = getVal(answerMap.get(PageConstants.COUNTRY+"_W"));
		 //only one AsOfDate currently
		String workCensusTract = getVal(answerMap.get(PageConstants.CENSUS_TRACT+"_W"));
		//Address Comments Field
		String addressComments = getVal(answerMap.get(PageConstants.ADDRESS_COMMENTS+"_W"));
		//check home address...
		if(state.equals("nedss.properties:NBS_STATE_CODE"))
			 state = "";
		if ( (city != null && !city.equals("")) ||
		(street1 != null && !street1.equals("")) ||
		(street2 != null && !street2.equals("")) ||
		(zip != null && !zip.equals("")) ||
		(county != null && !county.equals("")) ||
		(state != null && !state.equals("")) ||
		(cityLimits != null && !cityLimits.equals("")) || 
		(censusTract != null && !censusTract.equals("")))
		{
			aodMap.put(PageConstants.ADDRESS_INFORMATION_AS_OF, asOfDate);
			Long personUID = personVO.getThePersonDT().getPersonUid();
			EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
			elp.setItNew(true);
			elp.setItDirty(false);
			elp.setAddTime(new Timestamp(new Date().getTime()));
			elp.setAddUserId(Long.valueOf(userId));
			elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elp.setEntityUid(personUID);
			elp.setCd(NEDSSConstants.HOME);
			elp.setClassCd(NEDSSConstants.POSTAL);
			elp.setUseCd(NEDSSConstants.HOME);
			elp.setAsOfDate_s(asOfDate);

			PostalLocatorDT pl = new PostalLocatorDT();
			pl.setItNew(true);
			pl.setItDirty(false);
			pl.setAddTime(new Timestamp(new Date().getTime()));
			pl.setAddUserId(Long.valueOf(userId));
			pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
			if(street1!= null && street1.trim().length()>0)
				pl.setStreetAddr1(street1);
			if(street2!= null && street2.trim().length()>0)
				pl.setStreetAddr2(street2);
			if(zip!= null && zip.trim().length()>0)
				pl.setZipCd(zip);
			pl.setStateCd(state);
			if(city!= null && city.trim().length()>0)
				pl.setCityDescTxt(city);
			if(county!= null && county.trim().length()>0)
				pl.setCntyCd(county);
			pl.setCensusTract(censusTract);
			pl.setCntryCd(country);
			if(cityLimits!= null && cityLimits.trim().length()>0)
				pl.setWithinCityLimitsInd(cityLimits);
			pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

			elp.setThePostalLocatorDT(pl);
			arrELP.add(elp);
		}

		//check Work Address
		if(workState.equals("nedss.properties:NBS_STATE_CODE"))
			 workState = "";
		if ( (workCity != null && !workCity.equals("")) ||
		(workStreet1 != null && !workStreet1.equals("")) ||
		(workStreet2 != null && !workStreet2.equals("")) ||
		(workZip != null && !workZip.equals("")) ||
		(workCounty != null && !workCounty.equals("")) ||
		(workState != null && !workState.equals("")) ||
		(workCensusTract != null && !workCensusTract.equals("")) ||
		(addressComments!= null && !addressComments.equals("")))
		{
			Long personUID = personVO.getThePersonDT().getPersonUid();
			EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
			elp.setItNew(true);
			elp.setItDirty(false);
			elp.setAddTime(new Timestamp(new Date().getTime()));
			elp.setAddUserId(Long.valueOf(userId));
			elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elp.setEntityUid(personUID);
			elp.setCd(NEDSSConstants.OFFICE_CD);
			elp.setClassCd(NEDSSConstants.POSTAL);
			elp.setUseCd(NEDSSConstants.PRIMARY_BUSINESS);
			elp.setLocatorDescTxt(addressComments);
			if (workAsOfDate != null && !workAsOfDate.isEmpty())
				elp.setAsOfDate_s(workAsOfDate);
			else
				elp.setAsOfDate(new Timestamp(new Date().getTime()));
			PostalLocatorDT pl = new PostalLocatorDT();
			pl.setItNew(true);
			pl.setItDirty(false);
			pl.setAddTime(new Timestamp(new Date().getTime()));
			pl.setAddUserId(Long.valueOf(userId));
			pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
			if(workStreet1!= null && workStreet1.trim().length()>0)
				pl.setStreetAddr1(workStreet1);
			if(workStreet2!= null && workStreet2.trim().length()>0)
				pl.setStreetAddr2(workStreet2);
			if(workZip!= null && workZip.trim().length()>0)
				pl.setZipCd(workZip);
			pl.setStateCd(workState);
			if(workCity!= null && workCity.trim().length()>0)
				pl.setCityDescTxt(workCity);
			if(workCounty!= null && workCounty.trim().length()>0)
				pl.setCntyCd(workCounty);
			pl.setCensusTract(workCensusTract);
			pl.setCntryCd(workCountry);
			pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elp.setThePostalLocatorDT(pl);
			arrELP.add(elp);
		}		
			
			logger.info("Number of address in setAddressesForCreate: " + arrELP.size());
			personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
   }


   private static void setEthnicityForCreate(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	     Collection<Object>  ethnicityColl = new ArrayList<Object> ();
	     personVO.setThePersonEthnicGroupDTCollection(ethnicityColl);
	}

   private static void setEthnicityForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	   if(personVO.getThePersonEthnicGroupDTCollection() == null)
		   setEthnicityForCreate(personVO, answerMap, userId);
   }

   //ToDo: SetRaceForCreate - see generic version below
   private static void setRaceForCreate(PersonVO personVO, PageClientVO clientVO, PageActProxyVO proxyVO, String userId) {

	   Long aPersonUid = personVO.getThePersonDT().getPersonUid();

	   String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
	   if(asOfDate.equals("") )
		   asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.DEM_DATA_AS_OF));

       Collection<Object>  raceColl = new ArrayList<Object> ();
       int seqNo = 0;
		  if(clientVO.getUnKnownRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
	          raceDT.setRaceCd(NEDSSConstants.UNKNOWN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getOtherRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
	          raceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getRefusedToAnswer()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.REFUSED_TO_ANSWER);
	          raceDT.setRaceCd(NEDSSConstants.REFUSED_TO_ANSWER);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getNotAsked()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.NOT_ASKED);
	          raceDT.setRaceCd(NEDSSConstants.NOT_ASKED);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getAfricanAmericanRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
	          raceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getAmericanIndianAlskanRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	          raceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getWhiteRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
	          raceDT.setRaceCd(NEDSSConstants.WHITE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getAsianRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
	          raceDT.setRaceCd(NEDSSConstants.ASIAN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getHawaiianRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	          raceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }

		  String[] asian = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN);
		  if(asian != null && asian.length > 0) {
			  for (int i = 1; i <= asian.length; i++) {
					String asianRaceCd = asian[i-1];
					if(asianRaceCd == null || (asianRaceCd == "")) continue;
			     	PersonRaceDT raceDT = new PersonRaceDT();
			     	raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
			     	raceDT.setRaceCd(asianRaceCd);
				    raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
				    raceDT.setItDelete(false);
				    raceDT.setItDirty(false);
				    raceDT.setAddTime(new Timestamp(new Date().getTime()));
				    raceDT.setAddUserId(Long.valueOf(userId));
				    raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				    raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }


		  String[] hawaii = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII);
		  if(hawaii != null && hawaii.length > 0) {
			  for (int i = 1; i <= hawaii.length; i++) {
					String hawaiiRaceCd = hawaii[i-1];
					if(hawaiiRaceCd == null || (hawaiiRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					raceDT.setRaceCd(hawaiiRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] white = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE);
		  if(white != null && white.length > 0) {
			  for (int i = 1; i <= white.length; i++) {
					String whiteRaceCd = white[i-1];
					if(whiteRaceCd == null || (whiteRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
					raceDT.setRaceCd(whiteRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] afr = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
		  if(afr != null && afr.length > 0) {
			  for (int i = 1; i <= afr.length; i++) {
					String afrRaceCd = afr[i-1];
					if(afrRaceCd == null || (afrRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
					raceDT.setRaceCd(afrRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] aian = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
		  if(aian != null && aian.length > 0) {
			  for (int i = 1; i <= aian.length; i++) {
					String aianRaceCd = aian[i-1];
					if(aianRaceCd == null || (aianRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					raceDT.setRaceCd(aianRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }


		  if (raceColl.size() > 0) {
			  personVO.setThePersonRaceDTCollection(raceColl);
		  }
   }
   
	/**
	 * setRaceForCreate (generic version)
	 * @param personVO
	 * @param clientVO (i.e. (ClientVO) form.getcTContactClientVO())
	 * @param userid
	 */
   private static void setRaceForCreate(PersonVO personVO, ClientVO clientVO, String userId) {

	   Long aPersonUid = personVO.getThePersonDT().getPersonUid();

	   String asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.RACE_INFORMATION_AS_OF));
	   if(asOfDate.equals("") )
		   asOfDate = getVal(clientVO.getAnswerMap().get(PageConstants.DEM_DATA_AS_OF));

       Collection<Object>  raceColl = new ArrayList<Object> ();
       int seqNo = 0;
		  if(clientVO.getUnKnownRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
	          raceDT.setRaceCd(NEDSSConstants.UNKNOWN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getOtherRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
	          raceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getRefusedToAnswer()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setRaceCategoryCd(NEDSSConstants.REFUSED_TO_ANSWER);
	          raceDT.setRaceCd(NEDSSConstants.REFUSED_TO_ANSWER);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getNotAsked()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.NOT_ASKED);
	          raceDT.setRaceCd(NEDSSConstants.NOT_ASKED);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getAfricanAmericanRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
	          raceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getAmericanIndianAlskanRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	          raceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getWhiteRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
	          raceDT.setRaceCd(NEDSSConstants.WHITE);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
	          raceColl.add(raceDT);
		  }
		  if(clientVO.getAsianRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
	          raceDT.setRaceCd(NEDSSConstants.ASIAN);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }
		  if(clientVO.getHawaiianRace()==1){
			  seqNo++;
			  PersonRaceDT raceDT= new PersonRaceDT();
			  raceDT.setItNew(true);
	          raceDT.setItDelete(false);
	          raceDT.setItDirty(false);
	          raceDT.setAddTime(new Timestamp(new Date().getTime()));
	          raceDT.setAddUserId(Long.valueOf(userId));
	          raceDT.setPersonUid(aPersonUid);
	          raceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	          raceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	          raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	          raceDT.setAsOfDate_s(asOfDate);
			  raceColl.add(raceDT);
		  }

		  String[] asian = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN);
		  if(asian != null && asian.length > 0) {
			  for (int i = 1; i <= asian.length; i++) {
					String asianRaceCd = asian[i-1];
					if(asianRaceCd == null || (asianRaceCd == "")) continue;
			     	PersonRaceDT raceDT = new PersonRaceDT();
			     	raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
			     	raceDT.setRaceCd(asianRaceCd);
				    raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
				    raceDT.setItDelete(false);
				    raceDT.setItDirty(false);
				    raceDT.setAddTime(new Timestamp(new Date().getTime()));
				    raceDT.setAddUserId(Long.valueOf(userId));
				    raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				    raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }


		  String[] hawaii = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII);
		  if(hawaii != null && hawaii.length > 0) {
			  for (int i = 1; i <= hawaii.length; i++) {
					String hawaiiRaceCd = hawaii[i-1];
					if(hawaiiRaceCd == null || (hawaiiRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					raceDT.setRaceCd(hawaiiRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] white = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE);
		  if(white != null && white.length > 0) {
			  for (int i = 1; i <= white.length; i++) {
					String whiteRaceCd = white[i-1];
					if(whiteRaceCd == null || (whiteRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
					raceDT.setRaceCd(whiteRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] afr = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
		  if(afr != null && afr.length > 0) {
			  for (int i = 1; i <= afr.length; i++) {
					String afrRaceCd = afr[i-1];
					if(afrRaceCd == null || (afrRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
					raceDT.setRaceCd(afrRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }
		  
		  String[] aian = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
		  if(aian != null && aian.length > 0) {
			  for (int i = 1; i <= aian.length; i++) {
					String aianRaceCd = aian[i-1];
					if(aianRaceCd == null || (aianRaceCd == "")) continue;
		     		PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					raceDT.setRaceCd(aianRaceCd);
					raceDT.setAsOfDate_s(asOfDate);
					raceDT.setItNew(true);
					raceDT.setItDelete(false);
					raceDT.setItDirty(false);
					raceDT.setAddTime(new Timestamp(new Date().getTime()));
					raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			        raceDT.setAddUserId(Long.valueOf(userId));
					raceDT.setPersonUid(aPersonUid);
					raceColl.add(raceDT);
				  }
		  }


		  if (raceColl.size() > 0) {
			  personVO.setThePersonRaceDTCollection(raceColl);
		  }
   }   
   public static void setTelephones(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	   	String asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
		Long personUID = personVO.getThePersonDT().getPersonUid();
		Collection<Object>  arrELP = new ArrayList<Object> ();

		EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTHome = new TeleLocatorDT();
		EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTWork = new TeleLocatorDT();
		EntityLocatorParticipationDT elpCell = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTCell = new TeleLocatorDT();
		EntityLocatorParticipationDT elpEmail = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTEmail = new TeleLocatorDT();
		String homePhone = getVal(answerMap.get(PageConstants.H_PHONE));
		String homeExt = getVal(answerMap.get(PageConstants.H_PHONE_EXT));
		String workPhone = getVal(answerMap.get(PageConstants.W_PHONE));
		String workExt = getVal(answerMap.get(PageConstants.W_PHONE_EXT));
		String cellPh = getVal(answerMap.get(PageConstants.CELL_PHONE));
		String email = getVal(answerMap.get(PageConstants.EMAIL));

		if ((!homePhone.equals("")) || (!workPhone.equals("")) || (!homeExt.equals("")) || (!workExt.equals(""))|| (!cellPh.equals("")) || (!email.equals("")))
			aodMap.put(PageConstants.TELEPHONE_INFORMATION_AS_OF, asOfDate);

		// Home Phone
		if (homePhone != null && !homePhone.equals("")) {
			elpHome.setItNew(true);
			elpHome.setItDirty(false);
			elpHome.setAddTime(new Timestamp(new Date().getTime()));
			elpHome.setAddUserId(Long.valueOf(userId));
			elpHome.setEntityUid(personUID);
			elpHome.setClassCd(NEDSSConstants.TELE);
			elpHome.setCd(NEDSSConstants.PHONE);
			elpHome.setUseCd(NEDSSConstants.HOME);
			elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpHome.setAsOfDate_s(asOfDate);
			teleDTHome.setPhoneNbrTxt(homePhone);
			teleDTHome.setExtensionTxt(homeExt);
			teleDTHome.setItNew(true);
			teleDTHome.setItDirty(false);
			teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
			teleDTHome.setAddUserId(Long.valueOf(userId));
			teleDTHome.setExtensionTxt(homeExt);
			teleDTHome.setPhoneNbrTxt(homePhone);
			teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setTheTeleLocatorDT(teleDTHome);

			arrELP.add(elpHome);
		}

		// Work Phone
		if (workPhone != null && !workPhone.equals("")) {
			elpWork.setItNew(true);
			elpWork.setItDirty(false);
			elpWork.setAddTime(new Timestamp(new Date().getTime()));
			elpWork.setAddUserId(Long.valueOf(userId));
			elpWork.setEntityUid(personUID);
			elpWork.setClassCd(NEDSSConstants.TELE);
			elpWork.setCd(NEDSSConstants.PHONE);
			elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
			elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpWork.setAsOfDate_s(asOfDate);
			teleDTWork.setPhoneNbrTxt(homePhone);
			teleDTWork.setExtensionTxt(homeExt);
			teleDTWork.setItNew(true);
			teleDTWork.setItDirty(false);
			teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
			teleDTWork.setAddUserId(Long.valueOf(userId));
			teleDTWork.setExtensionTxt(workExt);
			teleDTWork.setPhoneNbrTxt(workPhone);
			teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setTheTeleLocatorDT(teleDTWork);
			arrELP.add(elpWork);
		}
		
		// email
		if ((email != null && !email.equals(""))) {
			elpEmail.setItNew(true);
			elpEmail.setItDirty(false);
			elpEmail.setAddTime(new Timestamp(new Date().getTime()));
			elpEmail.setAddUserId(Long.valueOf(userId));
			elpEmail.setEntityUid(personUID);
			elpEmail.setClassCd(NEDSSConstants.TELE);
			elpEmail.setCd(NEDSSConstants.NET);
			elpEmail.setUseCd(NEDSSConstants.HOME);
			elpEmail.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpEmail.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpEmail.setAsOfDate_s(asOfDate);
			teleDTEmail.setItNew(true);
			teleDTEmail.setItDirty(false);
			teleDTEmail.setAddTime(new Timestamp(new Date().getTime()));
			teleDTEmail.setAddUserId(Long.valueOf(userId));
			teleDTEmail.setEmailAddress(email);
			teleDTEmail.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpEmail.setTheTeleLocatorDT(teleDTEmail);
			arrELP.add(elpEmail);
		}
		
		
		// Cell Phone
		if ((cellPh != null && !cellPh.equals(""))) {
			elpCell.setItNew(true);
			elpCell.setItDirty(false);
			elpCell.setAddTime(new Timestamp(new Date().getTime()));
			elpCell.setAddUserId(Long.valueOf(userId));
			elpCell.setEntityUid(personUID);
			elpCell.setClassCd(NEDSSConstants.TELE);
			elpCell.setCd(NEDSSConstants.CELL);
			elpCell.setUseCd(NEDSSConstants.MOBILE);
			elpCell.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpCell.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpCell.setAsOfDate_s(asOfDate);
			teleDTCell.setPhoneNbrTxt(cellPh);
			teleDTCell.setItNew(true);
			teleDTCell.setItDirty(false);
			teleDTCell.setAddTime(new Timestamp(new Date().getTime()));
			teleDTCell.setAddUserId(Long.valueOf(userId));
			teleDTCell.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpCell.setTheTeleLocatorDT(teleDTCell);

			arrELP.add(elpCell);
		}

		if(personVO.getTheEntityLocatorParticipationDTCollection() != null)
			personVO.getTheEntityLocatorParticipationDTCollection().addAll(arrELP);

	}

   private static void setIds(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId, int maxSeq) {

	   String patientSSN = getVal(answerMap.get(PageConstants.SSN));
	   String asOfDate = null;
	   asOfDate = getVal(answerMap.get(PageConstants.SSN_AS_OF));
	   if(asOfDate == null || asOfDate.equals(""))
		   asOfDate = getVal(answerMap.get(PageConstants.DEM_DATA_AS_OF));
	   if(asOfDate == null || asOfDate.equals(""))
		   asOfDate = StringUtils.formatDate(new Timestamp(new Date().getTime()));
	   if(patientSSN != null && patientSSN.trim().length() > 0) {
		   if(aodMap != null)
			   aodMap.put(PageConstants.SSN_AS_OF, asOfDate);
		   EntityIdDT iddt  = new EntityIdDT();
	       iddt.setEntityIdSeq(new Integer(maxSeq));
	       iddt.setAddTime(new Timestamp(new Date().getTime()));
	       iddt.setAddUserId(Long.valueOf(userId));
	       iddt.setLastChgTime(new Timestamp(new Date().getTime()));
	       iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	       iddt.setStatusTime(new Timestamp(new Date().getTime()));
	       iddt.setEntityUid(personVO.getThePersonDT().getPersonUid());
	       iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	       iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	       iddt.setTypeCd("SS");
	       iddt.setTypeDescTxt("Social Security Number");
	       iddt.setAssigningAuthorityCd("SSA");
	       iddt.setAssigningAuthorityDescTxt("Social Security Administration");
	       iddt.setRootExtensionTxt(patientSSN);
	       iddt.setAsOfDate_s(asOfDate);
		   iddt.setItNew(true);
		   iddt.setItDirty(false);
	       if(personVO.getTheEntityIdDTCollection()==null) {
	    	   personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
	       }
	       personVO.getTheEntityIdDTCollection().add(iddt);
	   }
   }

	private static void setParticipationsForCreate(String userId, PageActProxyVO proxyVO, PageForm form, Long revisionPatientUID, HttpServletRequest request) throws NEDSSAppException {

		try{
			Long phcUID = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
	
			Collection<Object>  partsColl = new ArrayList<Object> ();
	
			// patient PHC participation
			ParticipationDT participationDT = createParticipation(null, null, userId, phcUID, String.valueOf(revisionPatientUID), "PSN",
					NEDSSConstants.PHC_PATIENT, NEDSSConstants.CLASS_CD_CASE);
			participationDT.setFromTime(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getEffectiveFromTime());
			partsColl.add(participationDT);
	
			proxyVO.setTheParticipationDTCollection(partsColl);
		}catch(Exception ex){
			logger.fatal("Exception occured in setParticipationsForCreate: ,"+ ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}

	public static ParticipationDT createParticipation(Long addUserId, Timestamp addTime, String userId,Long actUid, String subjectEntityUid, String subjectClassCd, String typeCd, String actClassCd) throws NEDSSAppException {

		ParticipationDT participationDT = new ParticipationDT();
		try{
			participationDT.setActClassCd(actClassCd);
			participationDT.setActUid(actUid);
			if(addUserId==null) {
				participationDT.setAddUserId(Long.valueOf(userId));
				participationDT.setAddTime(new Timestamp(new Date().getTime()));
			}
			else {
					participationDT.setAddUserId(addUserId);
					participationDT.setAddTime(addTime);
						
			}
			participationDT.setLastChgUserId(Long.valueOf(userId));
			participationDT.setLastChgTime(new Timestamp(new Date().getTime()));
			
			participationDT.setSubjectClassCd(subjectClassCd);
			participationDT.setSubjectEntityUid(new Long(subjectEntityUid.trim()));
			participationDT.setTypeCd(typeCd.trim());
			participationDT.setTypeDescTxt(cdv.getDescForCode("PAR_TYPE", typeCd.trim()));
			participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
			participationDT.setItNew(true);
			participationDT.setItDirty(false);
		}catch(Exception ex){
			logger.fatal("Exception occured in createParticipation: ,"+ ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
		}
		return participationDT;
	}

	private static void setEntitiesForCreateEdit(PageForm form, PageActProxyVO proxyVO, Long revisionPatientUID, String versionCtrlNbr, String userId) {

		Long phcUID = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();

		Collection<Object>  entityColl = new ArrayList<Object> ();
		int vcNum = Integer.valueOf(versionCtrlNbr).intValue() + 1;
		// patient PHC participation
		NbsActEntityDT entityDT = createPamCaseEntity(phcUID, String.valueOf(revisionPatientUID), String.valueOf(vcNum), NEDSSConstants.PHC_PATIENT, userId);
		//For update
		if(entityDT.getActUid().longValue() > 0) {
			entityDT.setItNew(false);
			entityDT.setItDirty(true);
			entityDT.setItDelete(false);
			NbsActEntityDT oldDT = getNbsCaseEntity(NEDSSConstants.PHC_PATIENT,((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO().getActEntityDTCollection() );
			entityDT.setNbsActEntityUid(oldDT.getNbsActEntityUid());

		}
		entityColl.add(entityDT);

		proxyVO.getPageVO().setActEntityDTCollection(entityColl);
	}

	public static NbsActEntityDT createPamCaseEntity(Long actUid, String subjectEntityUid, String versionCtrlNbr, String typeCd, String userId) {

		NbsActEntityDT entityDT = new NbsActEntityDT();
		entityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		entityDT.setAddUserId(Long.valueOf(userId));
		entityDT.setEntityUid(Long.valueOf(subjectEntityUid));
		entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
		entityDT.setActUid(actUid);
		entityDT.setTypeCd(typeCd);
		return entityDT;
	}

    public static NbsActEntityDT deletePamCaseEntity(String typeCd, Long oldUid, Collection<Object>  oldEntityDTCollection) {

    	NbsActEntityDT entityDT = null;
        if(oldEntityDTCollection  != null && oldEntityDTCollection.size() > 0) {
           Iterator<Object>  anIterator = null;
            for(anIterator = oldEntityDTCollection.iterator(); anIterator.hasNext();) {
            	entityDT = (NbsActEntityDT)anIterator.next();
                if(entityDT.getTypeCd().trim().equals(typeCd) && entityDT.getEntityUid().toString().equals(oldUid.toString())) {
                    logger.debug("deleting PamCaseEntity for typeCd: " + typeCd + " for investigation: " + entityDT.getEntityUid());
                    entityDT.setItDelete(true);
                    entityDT.setItDirty(false);
                    entityDT.setItNew(false);
                    return entityDT;
                }
                else {
                    continue;
                }
            }
        }

        return entityDT;
    }


	public static Long sendProxyToPamEJB(PageProxyVO proxyVO, HttpServletRequest request, String sCurrentTask) throws NEDSSAppConcurrentDataException, Exception {
		HttpSession session = request.getSession();
		MainSessionCommand msCommand = null;
		Long publicHealthCaseUID = null;
		String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		if (sCurrentTask != null && (sCurrentTask.equals("CreateInvestigation2")
				|| sCurrentTask.equals("CreateInvestigation3")
				|| sCurrentTask.equals("CreateInvestigation4")
				|| sCurrentTask.equals("CreateInvestigation5")
				|| sCurrentTask.equals("CreateInvestigation6")
				|| sCurrentTask.equals("CreateInvestigation7")
				|| sCurrentTask.equals("CreateInvestigation8")
				|| sCurrentTask.equals("CreateInvestigation9"))) {

			String sMethod = "setPamProxyWithAutoAssoc";

		    Object sObservationUID = NBSContext.retrieve(session, NBSConstantUtil.DSObservationUID);
		    Object observationTypeCd = NBSContext.retrieve(session, NBSConstantUtil.DSObservationTypeCd);
		    Long DSObservationUID = new Long(sObservationUID.toString());
		    Object[]  oParams = { proxyVO, DSObservationUID, observationTypeCd.toString()};
		      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		        publicHealthCaseUID = (Long) resultUIDArr.get(0);
		}else{
			Object[] oParams = { proxyVO };
			String sMethod = "setPamProxy";
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				publicHealthCaseUID = (Long) resultUIDArr.get(0);
			}
 
		}
		return publicHealthCaseUID;
	}

	public static void setNamesForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		Collection<Object>  names = personVO.getThePersonNameDTCollection();
		Collection<Object>  pdts = new ArrayList<Object> ();
		String lastNm = getVal(answerMap.get(PageConstants.LAST_NM));
		String firstNm = getVal(answerMap.get(PageConstants.FIRST_NM));
		String middleNm = getVal(answerMap.get(PageConstants.MIDDLE_NM));
		String suffix = getVal(answerMap.get(PageConstants.SUFFIX));
		String asOfDate = getVal(answerMap.get(PageConstants.NAME_INFORMATION_AS_OF));
		String aliasName = getVal(answerMap.get(PageConstants.ALIAS_NICK_NAME));
		boolean legalNamePresent = false;
		boolean aliasNamePresent = false;
		
		if (names != null && names.size() > 0) {
			Iterator<Object> ite = names.iterator();
			while (ite.hasNext()) {
				PersonNameDT pdt = (PersonNameDT) ite.next();
				if (pdt.getNmUseCd() != null && pdt.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME)
						&& pdt.getRecordStatusCd() != null && pdt.getRecordStatusCd().equals(NEDSSConstants.ACTIVE)
						&& pdt.getStatusCd() != null && pdt.getStatusCd().equals(NEDSSConstants.A)) {
					if ((lastNm == null || lastNm.trim().equals("")) && (firstNm == null || firstNm.trim().equals(""))
							&& (middleNm == null || middleNm.trim().equals(""))
							&& (suffix == null || suffix.trim().equals(""))) {
						pdt.setItNew(false);
						pdt.setItDirty(false);
						pdt.setItDelete(true);
						answerMap.remove(PageConstants.NAME_INFORMATION_AS_OF);
					} else {
						pdt.setItNew(false);
						pdt.setItDirty(true);
						pdt.setLastNm(lastNm);
						pdt.setFirstNm(firstNm);
						pdt.setMiddleNm(middleNm);
						pdt.setNmSuffix(suffix);
						pdt.setStatusCd("A");
						pdt.setLastChgTime(new Timestamp(new Date().getTime()));
						pdt.setAsOfDate_s(asOfDate);
					}
					legalNamePresent = true;
					pdts.add(pdt);
				} else if (pdt.getNmUseCd() != null && pdt.getNmUseCd().equals(NEDSSConstants.ALIAS_NAME)
						&& pdt.getRecordStatusCd() != null && pdt.getRecordStatusCd().equals(NEDSSConstants.ACTIVE)
						&& pdt.getStatusCd() != null && pdt.getStatusCd().equals(NEDSSConstants.A)) {
					if (aliasName.trim().isEmpty()) {
						pdt.setItNew(false);
						pdt.setItDirty(false);
						pdt.setItDelete(true);
					} else {
						pdt.setItNew(false);
						pdt.setItDirty(true);
						pdt.setFirstNm(aliasName);
						pdt.setStatusCd("A");
						pdt.setLastChgTime(new Timestamp(new Date().getTime()));
						pdt.setAsOfDate_s(asOfDate);
					}
					aliasNamePresent = true;
					pdts.add(pdt);
				}
			}
			if (!legalNamePresent && (!lastNm.trim().isEmpty() || !firstNm.trim().isEmpty()
							|| !middleNm.trim().isEmpty() || !suffix.trim().isEmpty())) {
				//legal name now present
				PersonNameDT pdt = new PersonNameDT();
				pdt.setItNew(true);
				pdt.setItDirty(false);
				pdt.setAddTime(new Timestamp(new Date().getTime()));
				pdt.setAddUserId(Long.valueOf(userId));
				pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
				pdt.setPersonNameSeq(new Integer(1));
				pdt.setStatusTime(new Timestamp(new Date().getTime()));
				pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
				pdt.setPersonUid(personUID);
				pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
				pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				pdt.setLastNm(lastNm);
				pdt.setFirstNm(firstNm);
				pdt.setMiddleNm(middleNm);
				pdt.setAsOfDate_s(asOfDate);
				pdt.setNmSuffix(suffix);
				pdt.setLastChgTime(new Timestamp(new Date().getTime()));
				pdt.setLastChgUserId(Long.valueOf(userId));
				pdts.add(pdt);
			}
			//new alias present
			if (!aliasNamePresent && !aliasName.trim().isEmpty()) {
				PersonNameDT pAliasDt = new PersonNameDT();
				pAliasDt.setItNew(true);
				pAliasDt.setItDirty(false);
				pAliasDt.setAddTime(new Timestamp(new Date().getTime()));
				pAliasDt.setAddUserId(Long.valueOf(userId));
				pAliasDt.setNmUseCd(NEDSSConstants.ALIAS_NAME);
				pAliasDt.setPersonNameSeq(new Integer(2));
				pAliasDt.setStatusTime(new Timestamp(new Date().getTime()));
				pAliasDt.setRecordStatusTime(new Timestamp(new Date().getTime()));
				pAliasDt.setPersonUid(personUID);
				pAliasDt.setRecordStatusCd(NEDSSConstants.ACTIVE);
				pAliasDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				pAliasDt.setAsOfDate_s(asOfDate);
				pAliasDt.setFirstNm(aliasName);
				pdts.add(pAliasDt);				
			}
			personVO.setThePersonNameDTCollection(pdts);
		} else { //names empty
			setNames(null, personVO, answerMap, userId);
		}
	}

	public static void setEntityLocatorParticipationsForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

		String homePhone = getVal(answerMap.get(PageConstants.H_PHONE));
		String homeExt = getVal(answerMap.get(PageConstants.H_PHONE_EXT));
		String workPhone = getVal(answerMap.get(PageConstants.W_PHONE));
		String workExt = getVal(answerMap.get(PageConstants.W_PHONE_EXT));
		String cell = getVal(answerMap.get(PageConstants.CELL_PHONE));
		String email = getVal(answerMap.get(PageConstants.EMAIL));
		
		
		if(homePhone.equals("") && homeExt.equals("") && workPhone.equals("") && workExt.equals("")&& cell.equals("") && cell.equals("")&& email.equals("") && email.equals(""))
			answerMap.remove(PageConstants.TELEPHONE_INFORMATION_AS_OF);

		String city = getVal(answerMap.get(PageConstants.CITY));
		String street1 = getVal(answerMap.get(PageConstants.ADDRESS_1));
		String street2 = getVal(answerMap.get(PageConstants.ADDRESS_2));
		String zip = getVal(answerMap.get(PageConstants.ZIP));
		String state = getVal(answerMap.get(PageConstants.STATE));
		if(state.equals("nedss.properties:NBS_STATE_CODE"))
			 state = "";
		String county = getVal(answerMap.get(PageConstants.COUNTY));
		String censusTract = getVal(answerMap.get(PageConstants.CENSUS_TRACT));
		String country = getVal(answerMap.get(PageConstants.COUNTRY));
		String asOfDate = getVal(answerMap.get(PageConstants.ADDRESS_INFORMATION_AS_OF));
		String cityLimits = getVal(answerMap.get(PageConstants.WITHIN_CITY_LIMITS));

		String workAsOfDate = getVal(answerMap.get(PageConstants.ADDRESS_INFORMATION_AS_OF+"_W"));
		String workCity = getVal(answerMap.get(PageConstants.CITY+"_W"));
		String workStreet1 = getVal(answerMap.get(PageConstants.ADDRESS_1+"_W"));
		String workStreet2 = getVal(answerMap.get(PageConstants.ADDRESS_2+"_W"));
		String workZip = getVal(answerMap.get(PageConstants.ZIP+"_W"));
		String workState = getVal(answerMap.get(PageConstants.STATE+"_W"));
		if(workState.equals("nedss.properties:NBS_STATE_CODE"))
			workState = "";
		String workCounty = getVal(answerMap.get(PageConstants.COUNTY+"_W"));
		String workCensusTract = getVal(answerMap.get(PageConstants.CENSUS_TRACT+"_W"));
		//Address Comments Field
		String addressComments = getVal(answerMap.get(PageConstants.ADDRESS_COMMENTS+"_W"));
		String workCountry = getVal(answerMap.get(PageConstants.COUNTRY+"_W"));	
		
		if (arrELP == null) {
			arrELP = new ArrayList<Object> ();
		}
		// home address
		EntityLocatorParticipationDT elp = getEntityLocatorParticipation(personVO, NEDSSConstants.POSTAL,
				NEDSSConstants.HOME,NEDSSConstants.HOME);
		if (elp != null ) {
			if ((city == null || city.equals("")) && (street1 == null || street1.equals(""))
					&& (street2 == null || street2.equals("")) && (zip == null || zip.equals(""))
					&& (county == null || county.equals("")) && (state == null || state.equals("")) 
					&& (cityLimits == null || cityLimits.equals("")) && (censusTract == null || censusTract.equals(""))) {
				
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setItNew(false);
				elp.setItDirty(false);
				elp.setItDelete(true);
				answerMap.remove(PageConstants.ADDRESS_INFORMATION_AS_OF);
			} else { //address fields have data
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.setLastChgTime(new Timestamp(new Date().getTime()));
				if (!asOfDate.isEmpty())
					elp.setAsOfDate_s(asOfDate);
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.getThePostalLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				if(city!=null)
					elp.getThePostalLocatorDT().setCityDescTxt(city);
				if(street1!=null)
					elp.getThePostalLocatorDT().setStreetAddr1(street1);
				if(street2!=null)
					elp.getThePostalLocatorDT().setStreetAddr2(street2);
				if(zip!=null)
					elp.getThePostalLocatorDT().setZipCd(zip);
				elp.getThePostalLocatorDT().setStateCd(state);
				if(county!=null)
					elp.getThePostalLocatorDT().setCntyCd(county);
				elp.getThePostalLocatorDT().setCensusTract(censusTract);
				elp.getThePostalLocatorDT().setCntryCd(country);
				if(cityLimits!=null && cityLimits.trim().length()>0)
					elp.getThePostalLocatorDT().setWithinCityLimitsInd(cityLimits);
				elp.getThePostalLocatorDT().setItNew(false);
				elp.getThePostalLocatorDT().setItDirty(true);
				// Added this to make the record from Inactive to Active
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		        elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			}
		} else { //no ELP, new address need to be created
			if ( ( (city != null && !city.equals("")) || (street1 != null && !street1.equals(""))
							|| (street2 != null && !street2.equals("")) || (zip != null && !zip.equals(""))
							|| (county != null && !county.equals("")) || (state != null && !state.equals(""))  
									|| (censusTract != null && !censusTract.equals(""))))   {

				EntityLocatorParticipationDT elpDT = new EntityLocatorParticipationDT();
				elpDT.setItNew(true);
				elpDT.setItDirty(false);
				elpDT.setAddTime(new Timestamp(new Date().getTime()));
				elpDT.setAddUserId(Long.valueOf(userId));
				elpDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elpDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
				elpDT.setCd(NEDSSConstants.HOME);
				elpDT.setClassCd(NEDSSConstants.POSTAL);
				elpDT.setUseCd(NEDSSConstants.HOME);
				if (!asOfDate.isEmpty())				
					elpDT.setAsOfDate_s(asOfDate);

				PostalLocatorDT pl = new PostalLocatorDT();
				if(city!=null && city.trim().length()>0)
					pl.setCityDescTxt(city);
				if(street1!=null && street1.trim().length()>0)
					pl.setStreetAddr1(street1);
				if(street2!=null && street2.trim().length()>0)
					pl.setStreetAddr2(street2);
				if(zip!=null && zip.trim().length()>0)
					pl.setZipCd(zip);
				pl.setStateCd(state);
				if(county!=null && county.trim().length()>0)
					pl.setCntyCd(county);
				pl.setCensusTract(censusTract);
				pl.setCntryCd(country);
				if(cityLimits!=null && cityLimits.trim().length()>0)
					pl.setWithinCityLimitsInd(cityLimits);
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				pl.setRecordStatusTime(new Timestamp(new Date().getTime()));				
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(new Timestamp(new Date().getTime()));
				pl.setAddUserId(Long.valueOf(userId));
				elpDT.setThePostalLocatorDT(pl);
				arrELP.add(elpDT);
			}
		}

		// work address
		EntityLocatorParticipationDT workElp = getEntityLocatorParticipation(personVO, NEDSSConstants.POSTAL,
				NEDSSConstants.PRIMARY_BUSINESS, NEDSSConstants.OFFICE_CD);
		if (workElp != null ) {
			if ((workCity == null || workCity.equals("")) && (workStreet1 == null || workStreet1.equals(""))
					&& (workStreet2 == null || workStreet2.equals("")) && (workZip == null || workZip.equals(""))
					&& (workCounty == null || workCounty.equals("")) && (workState == null || workState.equals("")) 
					&& (workCensusTract == null || workCensusTract.equals(""))
					&& (addressComments == null || addressComments.equals(""))) {
				
				workElp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				workElp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				workElp.setItNew(false);
				workElp.setItDirty(false);
				workElp.setItDelete(true);
				//answerMap.remove(PageConstants.ADDRESS_INFORMATION_AS_OF);
			} else { //addressAsOfDate is not null
				workElp.setLastChgUserId(Long.valueOf(userId));
				workElp.setLastChgTime(new Timestamp(new Date().getTime()));
				if (!workAsOfDate.isEmpty())
					workElp.setAsOfDate_s(workAsOfDate);
				workElp.setItNew(false);
				workElp.setItDirty(true);
				workElp.setLocatorDescTxt(addressComments);
				workElp.getThePostalLocatorDT().setLastChgUserId(Long.valueOf(userId));
				workElp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				if(workCity!=null && workCity.trim().length()>0)
				workElp.getThePostalLocatorDT().setCityDescTxt(workCity);
				if(workStreet1!=null && workStreet1.trim().length()>0)
					workElp.getThePostalLocatorDT().setStreetAddr1(workStreet1);
				if(workStreet2!=null && workStreet2.trim().length()>0)
					workElp.getThePostalLocatorDT().setStreetAddr2(workStreet2);
				if(workZip!=null && workZip.trim().length()>0)
					workElp.getThePostalLocatorDT().setZipCd(workZip);
				workElp.getThePostalLocatorDT().setStateCd(workState);
				workElp.getThePostalLocatorDT().setCntyCd(workCounty);
				workElp.getThePostalLocatorDT().setCensusTract(workCensusTract);
				if(workCountry!=null && workCountry.trim().length()>0)
					workElp.getThePostalLocatorDT().setCntryCd(workCountry);
				workElp.getThePostalLocatorDT().setItNew(false);
				workElp.getThePostalLocatorDT().setItDirty(true);
				// Added this to make the record from Inactive to Active
				workElp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				workElp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			}
		} else { //new work address need to be created
			if (  (workCity != null && !workCity.equals("")) || (workStreet1 != null && !workStreet1.equals(""))
							|| (workStreet2 != null && !workStreet2.equals("")) || (workZip != null && !workZip.equals(""))
							|| (workCounty != null && !workCounty.equals("")) || (workState != null && !workState.equals(""))  
							|| (workCensusTract != null && !workCensusTract.equals(""))
							|| (addressComments != null && !addressComments.equals("")))   {

				EntityLocatorParticipationDT workElocNew = new EntityLocatorParticipationDT();
				workElocNew.setItNew(true);
				workElocNew.setItDirty(false);
				workElocNew.setAddTime(new Timestamp(new Date().getTime()));
				workElocNew.setAddUserId(Long.valueOf(userId));
				workElocNew.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				workElocNew.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				workElocNew.setEntityUid(personVO.getThePersonDT().getPersonUid());
				workElocNew.setCd(NEDSSConstants.OFFICE_CD);
				workElocNew.setClassCd(NEDSSConstants.POSTAL);
				workElocNew.setUseCd(NEDSSConstants.PRIMARY_BUSINESS);
				workElocNew.setLocatorDescTxt(addressComments);
				
				if (!workAsOfDate.isEmpty())
					workElocNew.setAsOfDate_s(workAsOfDate);

				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setStreetAddr1(workStreet1);
				pl.setStreetAddr2(workStreet2);
				pl.setCityDescTxt(workCity);
				pl.setZipCd(workZip);
				pl.setStateCd(workState);
				pl.setCntyCd(workCounty);
				pl.setCensusTract(workCensusTract);
				pl.setCntryCd(workCountry);
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(new Timestamp(new Date().getTime()));
				pl.setAddUserId(Long.valueOf(userId));
				pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);				
				workElocNew.setThePostalLocatorDT(pl);
				arrELP.add(workElocNew);
			}
		}
		
		
		//home telephone
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.HOME , NEDSSConstants.PHONE);
		asOfDate = getVal(answerMap.get(PageConstants.TELEPHONE_INFORMATION_AS_OF));
		if (elp != null) {

			if ((homePhone == null || homePhone.equals("")) && (homeExt == null || homeExt.equals(""))) {
				//for edit when telephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(homePhone);
				elp.getTheTeleLocatorDT().setExtensionTxt(homeExt);
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.setItDelete(true);
			} else { //telephoneAsOfDate is not null
				elp.setAsOfDate_s(asOfDate);
				elp.setLastChgTime(new Timestamp(new Date().getTime()));
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(homePhone);
				elp.getTheTeleLocatorDT().setExtensionTxt(homeExt);
				elp.getTheTeleLocatorDT().setItNew(false);
				elp.getTheTeleLocatorDT().setItDirty(true);
			}//new home telephone need to be created
		} else if((homePhone!=null && !homePhone.equals(""))|| (homeExt!=null && !homeExt.equals("")))
		{
			EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();

			elpHome.setItNew(true);
			elpHome.setItDirty(false);
			elpHome.setAddTime(new Timestamp(new Date().getTime()));
			elpHome.setAddUserId(Long.valueOf(userId));
			elpHome.setEntityUid(personVO.getThePersonDT().getPersonUid());
			elpHome.setClassCd(NEDSSConstants.TELE);
			elpHome.setCd(NEDSSConstants.PHONE);
			elpHome.setUseCd(NEDSSConstants.HOME);
			elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpHome.setAsOfDate_s(asOfDate);

			TeleLocatorDT teleDTHome = new TeleLocatorDT();
			teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
			teleDTHome.setAddUserId(Long.valueOf(userId));
			teleDTHome.setPhoneNbrTxt(homePhone);
			teleDTHome.setExtensionTxt(homeExt);
			teleDTHome.setItNew(true);
			teleDTHome.setItDirty(false);
			teleDTHome.setExtensionTxt(homeExt);
			teleDTHome.setPhoneNbrTxt(homePhone);
			teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setTheTeleLocatorDT(teleDTHome);
			arrELP.add(elpHome);
		}
		
		// Email
		
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.HOME,NEDSSConstants.NET);

		if (elp != null) {
			if (email == null || email.equals("")) {
				//for edit when worktelephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setEmailAddress(email);
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.setItDelete(true);
			} else { //worktelephoneAsOfDate is not null
				elp.setAsOfDate_s(asOfDate);
				elp.setLastChgTime(new Timestamp(new Date().getTime()));
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setEmailAddress(email);
				elp.getTheTeleLocatorDT().setItNew(false);
				elp.getTheTeleLocatorDT().setItDirty(true);

			}//new email need to be created
		} else if((email!=null && !email.equals("")))
		{
			EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
			elpWork.setItNew(true);
			elpWork.setItDirty(false);
			elpWork.setAddTime(new Timestamp(new Date().getTime()));
			elpWork.setAddUserId(Long.valueOf(userId));
			elpWork.setEntityUid(personVO.getThePersonDT().getPersonUid());
			elpWork.setClassCd(NEDSSConstants.TELE);
			elpWork.setCd(NEDSSConstants.NET);
			elpWork.setUseCd(NEDSSConstants.HOME);
			elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpWork.setAsOfDate_s(asOfDate);

			TeleLocatorDT teleDTWork = new TeleLocatorDT();
			teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
			teleDTWork.setAddUserId(Long.valueOf(userId));
			teleDTWork.setEmailAddress(email);
			teleDTWork.setItNew(false);
			teleDTWork.setItDirty(true);

			teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setTheTeleLocatorDT(teleDTWork);
			arrELP.add(elpWork);
		}
		
		
		// Cell Phone
		
		
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.MOBILE, NEDSSConstants.CELL);
		if (elp != null) {

			if ((cell == null || cell.equals("")) ) {
				//for edit when telephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(cell);
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.setItDelete(true);
			} else { //telephoneAsOfDate is not null
				elp.setAsOfDate_s(asOfDate);
				elp.setLastChgTime(new Timestamp(new Date().getTime()));
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(cell);
				elp.getTheTeleLocatorDT().setItNew(false);
				elp.getTheTeleLocatorDT().setItDirty(true);
			}//new home telephone need to be created
		} else if((cell != null && !cell.equals("")))
		{
			EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();

			elpHome.setItNew(true);
			elpHome.setItDirty(false);
			elpHome.setAddTime(new Timestamp(new Date().getTime()));
			elpHome.setAddUserId(Long.valueOf(userId));
			elpHome.setEntityUid(personVO.getThePersonDT().getPersonUid());
			elpHome.setClassCd(NEDSSConstants.TELE);
			elpHome.setCd(NEDSSConstants.CELL);
			elpHome.setUseCd(NEDSSConstants.MOBILE);
			elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpHome.setAsOfDate_s(asOfDate);

			TeleLocatorDT teleDTHome = new TeleLocatorDT();
			teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
			teleDTHome.setAddUserId(Long.valueOf(userId));
			teleDTHome.setPhoneNbrTxt(cell);
			teleDTHome.setItNew(false);
			teleDTHome.setItDirty(true);
			teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setTheTeleLocatorDT(teleDTHome);
			arrELP.add(elpHome);
		}
		//work telephone
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.WORK_PHONE, NEDSSConstants.PHONE);

		if (elp != null) {
			if ((workPhone == null || workPhone.equals("")) && (workExt == null || workExt.equals(""))) {
				//for edit when worktelephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(workPhone);
				elp.getTheTeleLocatorDT().setExtensionTxt(workExt);
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.setItDelete(true);
			} else { //worktelephoneAsOfDate is not null
				elp.setAsOfDate_s(asOfDate);
				elp.setLastChgTime(new Timestamp(new Date().getTime()));
				elp.setLastChgUserId(Long.valueOf(userId));
				elp.setItNew(false);
				elp.setItDirty(true);
				elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
				elp.getTheTeleLocatorDT().setLastChgUserId(Long.valueOf(userId));
				elp.getTheTeleLocatorDT().setPhoneNbrTxt(workPhone);
				elp.getTheTeleLocatorDT().setExtensionTxt(workExt);
				elp.getTheTeleLocatorDT().setItNew(false);
				elp.getTheTeleLocatorDT().setItDirty(true);

			}//new home telephone need to be created
		} else if((workPhone!=null && !workPhone.equals(""))|| (workExt!=null && !workExt.equals("")))
		{
			EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
			elpWork.setItNew(true);
			elpWork.setItDirty(false);
			elpWork.setAddTime(new Timestamp(new Date().getTime()));
			elpWork.setAddUserId(Long.valueOf(userId));
			elpWork.setEntityUid(personVO.getThePersonDT().getPersonUid());
			elpWork.setClassCd(NEDSSConstants.TELE);
			elpWork.setCd(NEDSSConstants.PHONE);
			elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
			elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elpWork.setAsOfDate_s(asOfDate);

			TeleLocatorDT teleDTWork = new TeleLocatorDT();
			teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
			teleDTWork.setAddUserId(Long.valueOf(userId));
			teleDTWork.setPhoneNbrTxt(workPhone);
			teleDTWork.setExtensionTxt(workExt);
			teleDTWork.setItNew(true);
			teleDTWork.setItDirty(false);

			teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setTheTeleLocatorDT(teleDTWork);
			arrELP.add(elpWork);
		}

		personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
	}
	//ToDo - replace with generiv cersion
	public static void setRaceForEdit(PersonVO personVO, PageClientVO clientVO, PageActProxyVO proxyVO, String userId) {
		//remove the added one for rules if any...
		clientVO.getAnswerMap().remove(PageConstants.RACE);
		Map<Object,Object> answerMap = clientVO.getAnswerMap();
		Long personUID = personVO.getThePersonDT().getPersonUid();
		String asOfDate = getVal(answerMap.get(PageConstants.RACE_INFORMATION_AS_OF));
		if(clientVO.getOtherRace() == 0 && clientVO.getNotAsked() == 0 && clientVO.getRefusedToAnswer() == 0 && clientVO.getUnKnownRace() == 0 && clientVO.getOtherRace() == 0 && clientVO.getRefusedToAnswer() == 0 && clientVO.getAfricanAmericanRace() == 0 && clientVO.getAmericanIndianAlskanRace() == 0 && clientVO.getAsianRace() == 0 && clientVO.getHawaiianRace() == 0 && clientVO.getWhiteRace() == 0)
			answerMap.remove(PageConstants.RACE_INFORMATION_AS_OF);

		if (personVO.getThePersonRaceDTCollection() == null || personVO.getThePersonRaceDTCollection().size() == 0) {
			//Races might got added in Edit
			setRaceForCreate(personVO, clientVO, proxyVO, userId);
		}
		//old ones for edit
		else {
			Iterator<Object> iter = personVO.getThePersonRaceDTCollection().iterator();
			if (iter != null) {
				HashMap<Object,Object> hm = new HashMap<Object,Object>();

				while (iter.hasNext()) {
					PersonRaceDT raceDT = (PersonRaceDT) iter.next();
					raceDT.setItDelete(true);
					hm.put(raceDT.getRaceCd(), raceDT);
				}

		       int seqNo = 0;

		       //  UNKNOWN RACE
			   if(clientVO.getUnKnownRace() == 1){
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.UNKNOWN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  //User selected 'UnKnown' Race on Edit
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
					  newraceDT.setRaceCd(NEDSSConstants.UNKNOWN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			   
				// OTHER RACE
				if (clientVO.getOtherRace() == 1) {
					seqNo++;
					PersonRaceDT raceDT = (PersonRaceDT) hm
							.get(NEDSSConstants.OTHER_RACE);
					if (raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
						raceDT.setAsOfDate_s(asOfDate);
						// User selected 'Other' Race on Edit
					} else {
						PersonRaceDT newraceDT = new PersonRaceDT();
						newraceDT.setItNew(true);
						newraceDT.setItDelete(false);
						newraceDT.setItDirty(false);
						newraceDT
								.setAddTime(new Timestamp(new Date().getTime()));
						newraceDT.setPersonUid(personUID);
						newraceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
						newraceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
						newraceDT
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						newraceDT.setAsOfDate_s(asOfDate);
						personVO.getThePersonRaceDTCollection().add(newraceDT);
					}
				}
				
				// REFUSED TO ANSWER
				if (clientVO.getRefusedToAnswer() == 1) {
					seqNo++;
					PersonRaceDT raceDT = (PersonRaceDT) hm
							.get(NEDSSConstants.REFUSED_TO_ANSWER);
					if (raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
						raceDT.setAsOfDate_s(asOfDate);
						// User selected 'Other' Race on Edit
					} else {
						PersonRaceDT newraceDT = new PersonRaceDT();
						newraceDT.setItNew(true);
						newraceDT.setItDelete(false);
						newraceDT.setItDirty(false);
						newraceDT
								.setAddTime(new Timestamp(new Date().getTime()));
						newraceDT.setPersonUid(personUID);
						newraceDT.setRaceCategoryCd(NEDSSConstants.REFUSED_TO_ANSWER);
						newraceDT.setRaceCd(NEDSSConstants.REFUSED_TO_ANSWER);
						newraceDT
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						newraceDT.setAsOfDate_s(asOfDate);
						personVO.getThePersonRaceDTCollection().add(newraceDT);
					}
				}
				//  NOT ASKED
				  if(clientVO.getNotAsked() == 1) {
					  seqNo++;
					  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.NOT_ASKED);
					  if(raceDT != null) {
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
					        raceDT.setAsOfDate_s(asOfDate);
					  } else {
						  PersonRaceDT newraceDT= new PersonRaceDT();
						  newraceDT.setItNew(true);
						  newraceDT.setItDelete(false);
						  newraceDT.setItDirty(false);
						  newraceDT.setPersonUid(personUID);
						  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
						  newraceDT.setRaceCategoryCd(NEDSSConstants.NOT_ASKED);
						  newraceDT.setRaceCd(NEDSSConstants.NOT_ASKED);
						  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						  newraceDT.setAsOfDate_s(asOfDate);
				          personVO.getThePersonRaceDTCollection().add(newraceDT);
					  }
				  } 
			//  AMERICAN INDIAN
			  if(clientVO.getAmericanIndianAlskanRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					  newraceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
			  }
			//  WHITE
			  if(clientVO.getWhiteRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.WHITE);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
					  newraceDT.setRaceCd(NEDSSConstants.WHITE);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_WHITE);
			  }
			//  AFRICAN AMERICAN
			  if(clientVO.getAfricanAmericanRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.AFRICAN_AMERICAN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
					  newraceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
			  }
			//  ASIAN
			  if(clientVO.getAsianRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.ASIAN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
					  newraceDT.setRaceCd(NEDSSConstants.ASIAN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  } else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_ASIAN);
			  }
			//  HAWAIIAN
			  if(clientVO.getHawaiianRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					  newraceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  } else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_HAWAII);
			  }

			  String[] asianList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN);
			  if(asianList != null && asianList.length > 0) {
				  for (int i = 1; i <= asianList.length; i++) {

						String asianRaceCd = asianList[i-1];
						if(asianRaceCd == null || (asianRaceCd == "")) continue;
						PersonRaceDT raceDT = (PersonRaceDT) hm.get(asianRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
					     	PersonRaceDT newraceDT = new PersonRaceDT();
					     	newraceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
					     	newraceDT.setRaceCd(asianRaceCd);
					     	newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					     	newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					     	newraceDT.setAsOfDate_s(asOfDate);
					     	newraceDT.setItNew(true);
					     	newraceDT.setItDelete(false);
					     	newraceDT.setItDirty(false);
					     	newraceDT.setPersonUid(personUID);
						    personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] hawaiiList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII);
			  if(hawaiiList != null && hawaiiList.length > 0) {
				  for (int i = 1; i <= hawaiiList.length; i++) {

					  String hawaiiRaceCd = hawaiiList[i-1];
					  if(hawaiiRaceCd == null || (hawaiiRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(hawaiiRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
				     		newraceDT.setRaceCd(hawaiiRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  
			  String[] whiteList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE);
			  if(whiteList != null && whiteList.length > 0) {
				  for (int i = 1; i <= whiteList.length; i++) {

					  String whiteRaceCd = whiteList[i-1];
					  if(whiteRaceCd == null || (whiteRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(whiteRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
				     		newraceDT.setRaceCd(whiteRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] afrList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
			  if(afrList != null && afrList.length > 0) {
				  for (int i = 1; i <= afrList.length; i++) {

					  String afrRaceCd = afrList[i-1];
					  if(afrRaceCd == null || (afrRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(afrRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
				     		newraceDT.setRaceCd(afrRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] aianList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
			  if(aianList != null && aianList.length > 0) {
				  for (int i = 1; i <= aianList.length; i++) {

					  String aianRaceCd = aianList[i-1];
					  if(aianRaceCd == null || (aianRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(aianRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
				     		newraceDT.setRaceCd(aianRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  
			}
		}

	}
	/**
	 * setRaceForEdit (generic version)
	 * @param personVO
	 * @param clientVO (i.e. (ClientVO) form.getcTContactClientVO())
	 * @param userid
	 */
	public static void setRaceForEdit(PersonVO personVO, ClientVO clientVO, String userId) {
		//remove the added one for rules if any...
		clientVO.getAnswerMap().remove(PageConstants.RACE);
		Map<Object,Object> answerMap = clientVO.getAnswerMap();
		Long personUID = personVO.getThePersonDT().getPersonUid();
		String asOfDate = getVal(answerMap.get(PageConstants.RACE_INFORMATION_AS_OF));
		if(clientVO.getOtherRace() == 0 && clientVO.getNotAsked() == 0 && clientVO.getRefusedToAnswer() == 0 && clientVO.getUnKnownRace() == 0 && clientVO.getOtherRace() == 0 && clientVO.getRefusedToAnswer() == 0 && clientVO.getAfricanAmericanRace() == 0 && clientVO.getAmericanIndianAlskanRace() == 0 && clientVO.getAsianRace() == 0 && clientVO.getHawaiianRace() == 0 && clientVO.getWhiteRace() == 0)
			answerMap.remove(PageConstants.RACE_INFORMATION_AS_OF);

		if (personVO.getThePersonRaceDTCollection() == null || personVO.getThePersonRaceDTCollection().size() == 0) {
			//Races might got added in Edit
			setRaceForCreate(personVO, clientVO, userId);
		}
		//old ones for edit
		else {
			Iterator<Object> iter = personVO.getThePersonRaceDTCollection().iterator();
			if (iter != null) {
				HashMap<Object,Object> hm = new HashMap<Object,Object>();

				while (iter.hasNext()) {
					PersonRaceDT raceDT = (PersonRaceDT) iter.next();
					raceDT.setItDelete(true);
					hm.put(raceDT.getRaceCd(), raceDT);
				}

		       int seqNo = 0;

		       //  UNKNOWN RACE
			   if(clientVO.getUnKnownRace() == 1){
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.UNKNOWN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  //User selected 'UnKnown' Race on Edit
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
					  newraceDT.setRaceCd(NEDSSConstants.UNKNOWN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			   
				// OTHER RACE
				if (clientVO.getOtherRace() == 1) {
					seqNo++;
					PersonRaceDT raceDT = (PersonRaceDT) hm
							.get(NEDSSConstants.OTHER_RACE);
					if (raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
						raceDT.setAsOfDate_s(asOfDate);
						// User selected 'Other' Race on Edit
					} else {
						PersonRaceDT newraceDT = new PersonRaceDT();
						newraceDT.setItNew(true);
						newraceDT.setItDelete(false);
						newraceDT.setItDirty(false);
						newraceDT
								.setAddTime(new Timestamp(new Date().getTime()));
						newraceDT.setPersonUid(personUID);
						newraceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
						newraceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
						newraceDT
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						newraceDT.setAsOfDate_s(asOfDate);
						personVO.getThePersonRaceDTCollection().add(newraceDT);
					}
				}
				
				// REFUSED TO ANSWER
				if (clientVO.getRefusedToAnswer() == 1) {
					seqNo++;
					PersonRaceDT raceDT = (PersonRaceDT) hm
							.get(NEDSSConstants.REFUSED_TO_ANSWER);
					if (raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
						raceDT.setAsOfDate_s(asOfDate);
						// User selected 'Other' Race on Edit
					} else {
						PersonRaceDT newraceDT = new PersonRaceDT();
						newraceDT.setItNew(true);
						newraceDT.setItDelete(false);
						newraceDT.setItDirty(false);
						newraceDT
								.setAddTime(new Timestamp(new Date().getTime()));
						newraceDT.setPersonUid(personUID);
						newraceDT.setRaceCategoryCd(NEDSSConstants.REFUSED_TO_ANSWER);
						newraceDT.setRaceCd(NEDSSConstants.REFUSED_TO_ANSWER);
						newraceDT
								.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						newraceDT.setAsOfDate_s(asOfDate);
						personVO.getThePersonRaceDTCollection().add(newraceDT);
					}
				}
				//  NOT ASKED
				  if(clientVO.getNotAsked() == 1) {
					  seqNo++;
					  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.NOT_ASKED);
					  if(raceDT != null) {
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
					        raceDT.setAsOfDate_s(asOfDate);
					  } else {
						  PersonRaceDT newraceDT= new PersonRaceDT();
						  newraceDT.setItNew(true);
						  newraceDT.setItDelete(false);
						  newraceDT.setItDirty(false);
						  newraceDT.setPersonUid(personUID);
						  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
						  newraceDT.setRaceCategoryCd(NEDSSConstants.NOT_ASKED);
						  newraceDT.setRaceCd(NEDSSConstants.NOT_ASKED);
						  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						  newraceDT.setAsOfDate_s(asOfDate);
				          personVO.getThePersonRaceDTCollection().add(newraceDT);
					  }
				  } 
			//  AMERICAN INDIAN
			  if(clientVO.getAmericanIndianAlskanRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					  newraceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
			  }
			//  WHITE
			  if(clientVO.getWhiteRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.WHITE);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
					  newraceDT.setRaceCd(NEDSSConstants.WHITE);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_WHITE);
			  }
			//  AFRICAN AMERICAN
			  if(clientVO.getAfricanAmericanRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.AFRICAN_AMERICAN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
					  newraceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  }
			  else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
			  }
			//  ASIAN
			  if(clientVO.getAsianRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.ASIAN);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
					  newraceDT.setRaceCd(NEDSSConstants.ASIAN);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  } else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_ASIAN);
			  }
			//  HAWAIIAN
			  if(clientVO.getHawaiianRace() == 1) {
				  seqNo++;
				  PersonRaceDT raceDT= (PersonRaceDT) hm.get(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
				  if(raceDT != null) {
						raceDT.setItNew(false);
						raceDT.setItDirty(true);
						raceDT.setItDelete(false);
				        raceDT.setAsOfDate_s(asOfDate);
				  } else {
					  PersonRaceDT newraceDT= new PersonRaceDT();
					  newraceDT.setItNew(true);
					  newraceDT.setItDelete(false);
					  newraceDT.setItDirty(false);
					  newraceDT.setPersonUid(personUID);
					  newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					  newraceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					  newraceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
					  newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  newraceDT.setAsOfDate_s(asOfDate);
			          personVO.getThePersonRaceDTCollection().add(newraceDT);
				  }
			  } else {
				  clientVO.getArrayAnswerMap().remove(PageConstants.DETAILED_RACE_HAWAII);
			  }

			  String[] asianList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_ASIAN);
			  if(asianList != null && asianList.length > 0) {
				  for (int i = 1; i <= asianList.length; i++) {

						String asianRaceCd = asianList[i-1];
						if(asianRaceCd == null || (asianRaceCd == "")) continue;
						PersonRaceDT raceDT = (PersonRaceDT) hm.get(asianRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
					     	PersonRaceDT newraceDT = new PersonRaceDT();
					     	newraceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
					     	newraceDT.setRaceCd(asianRaceCd);
					     	newraceDT.setAddTime(new Timestamp(new Date().getTime()));
					     	newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					     	newraceDT.setAsOfDate_s(asOfDate);
					     	newraceDT.setItNew(true);
					     	newraceDT.setItDelete(false);
					     	newraceDT.setItDirty(false);
					     	newraceDT.setPersonUid(personUID);
						    personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] hawaiiList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_HAWAII);
			  if(hawaiiList != null && hawaiiList.length > 0) {
				  for (int i = 1; i <= hawaiiList.length; i++) {

					  String hawaiiRaceCd = hawaiiList[i-1];
					  if(hawaiiRaceCd == null || (hawaiiRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(hawaiiRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
				     		newraceDT.setRaceCd(hawaiiRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  
			  String[] whiteList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_WHITE);
			  if(whiteList != null && whiteList.length > 0) {
				  for (int i = 1; i <= whiteList.length; i++) {

					  String whiteRaceCd = whiteList[i-1];
					  if(whiteRaceCd == null || (whiteRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(whiteRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
				     		newraceDT.setRaceCd(whiteRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] afrList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN);
			  if(afrList != null && afrList.length > 0) {
				  for (int i = 1; i <= afrList.length; i++) {

					  String afrRaceCd = afrList[i-1];
					  if(afrRaceCd == null || (afrRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(afrRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
				     		newraceDT.setRaceCd(afrRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			  String[] aianList = clientVO.getAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE);
			  if(aianList != null && aianList.length > 0) {
				  for (int i = 1; i <= aianList.length; i++) {

					  String aianRaceCd = aianList[i-1];
					  if(aianRaceCd == null || (aianRaceCd == "")) continue;
					  PersonRaceDT raceDT = (PersonRaceDT) hm.get(aianRaceCd);
						if (raceDT != null) { //true if exists already in collection
							raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							raceDT.setItNew(false);
							raceDT.setItDirty(true);
							raceDT.setItDelete(false);
							raceDT.setAsOfDate_s(asOfDate);
						} else {
				     		PersonRaceDT newraceDT = new PersonRaceDT();
				     		newraceDT.setRaceCategoryCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
				     		newraceDT.setRaceCd(aianRaceCd);
				     		newraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				     		newraceDT.setAsOfDate_s(asOfDate);
				     		newraceDT.setItNew(true);
				     		newraceDT.setItDelete(false);
				     		newraceDT.setItDirty(false);
				     		newraceDT.setPersonUid(personUID);
				     		newraceDT.setAddTime(new Timestamp(new Date().getTime()));
				     		personVO.getThePersonRaceDTCollection().add(newraceDT);
						}
				  }
			  }
			}
		}

	}
	private static void setIdsForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		   String patientSSN = getVal(answerMap.get(PageConstants.SSN));
		   if(patientSSN.equals(""))
			   answerMap.remove(PageConstants.SSN_AS_OF);
		   String asOfDate = getVal(answerMap.get(PageConstants.SSN_AS_OF));
		   boolean doesSSNExists = false;
		   int maxSeqNbr=1;
		   if( personVO.getTheEntityIdDTCollection() != null && personVO.getTheEntityIdDTCollection().size() >0) {
			   Iterator<Object>  iter = personVO.getTheEntityIdDTCollection().iterator();
			   while(iter.hasNext()) {
				   EntityIdDT iddt = (EntityIdDT) iter.next();
				   if(iddt.getEntityIdSeq()>maxSeqNbr)
						maxSeqNbr = iddt.getEntityIdSeq();
				   if(iddt.getTypeCd()!=null && iddt.getTypeCd().equalsIgnoreCase("SS")){
					   doesSSNExists=true;
					  
				   }
			   }
		   }
		   if(!doesSSNExists) 
			   setIds(null, personVO, answerMap, userId, maxSeqNbr+1);
		   else {
			  Iterator<Object>  iter = personVO.getTheEntityIdDTCollection().iterator();
			   while(iter.hasNext()) {
				   EntityIdDT iddt = (EntityIdDT) iter.next();
				   if(iddt.getTypeCd()!=null && iddt.getTypeCd().equalsIgnoreCase("SS")){
				   iddt.setItNew(false);
				   iddt.setItDirty(true);
				   iddt.setLastChgTime(new Timestamp(new Date().getTime()));
				   iddt.setLastChgUserId(Long.valueOf(userId));
			       iddt.setRootExtensionTxt(patientSSN);
			       iddt.setAsOfDate_s(asOfDate);
				   }
			   }
		   }
	}

	private static EntityLocatorParticipationDT getEntityLocatorParticipation(PersonVO personVO, String classCd,
			String useCd, String cd) {
		Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
		if (arrELP == null || arrELP.size() == 0) {
			return null;
		} else {
			Iterator<Object> itrAddress = arrELP.iterator();
			while (itrAddress.hasNext()) {

				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) itrAddress.next();
				if (elp.getRecordStatusCd() != null && elp.getRecordStatusCd().equals(NEDSSConstants.ACTIVE)
						&& elp.getStatusCd() != null && elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
						&& elp.getClassCd() != null && elp.getClassCd().equals(classCd) && elp.getUseCd() != null
						&& elp.getUseCd().equals(useCd) && elp.getLocatorUid() != null && elp.getCd() != null
						&& elp.getCd().equals(cd)) {
					return elp;
				}
			}
		}
		return null;
	}
    private static void setParticipationsForEdit(PageActProxyVO proxyVO, PageForm form, PublicHealthCaseVO phcVO, HttpServletRequest request)
    {
      Collection<Object>  oldParticipationCollection  = phcVO.getTheParticipationDTCollection();
      Collection<Object>  participationDTCollection  = new ArrayList<Object> ();

      ParticipationDT patientPHCPart = getParticipation(NEDSSConstants.PHC_PATIENT, "PSN", oldParticipationCollection);
      patientPHCPart.setFromTime(phcVO.getThePublicHealthCaseDT().getEffectiveFromTime());
      participationDTCollection.add(patientPHCPart);

      proxyVO.setTheParticipationDTCollection(participationDTCollection);
    }

    /**
     * createOrDeleteParticipation CREATES, DELETES Participations and NBSCaseEntityDT's
     * per Pradeep, on new Generics we don't send the deletes, they are automatically deleted
     * @param newEntityUid
     * @param form
     * @param proxyVO
     * @param typeCd
     * @param classCd
     * @param userId
     * @throws NEDSSAppException 
     */
    public static void createOrDeleteParticipation(Long addUserId, Timestamp addTime, String newEntityUid, PageForm form, PageActProxyVO proxyVO, String typeCd, String classCd, String userId, Long providerUid) throws NEDSSAppException
    {
        try {
			logger.debug(" newEntityUid = " + newEntityUid + " old");
			if(newEntityUid != null && newEntityUid.indexOf("|") == -1) {
				newEntityUid = newEntityUid + "|1";
			}
			String uid = null;
			String versionCtrlNbr = "1";
			if (newEntityUid != null) {
				uid = splitUid(newEntityUid);
				versionCtrlNbr = splitVerCtrlNbr(newEntityUid);
			}

			PublicHealthCaseVO phcVO = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
			String programArea = phcVO.getThePublicHealthCaseDT().getProgAreaCd();
			Collection<Object>  oldParCollection  = phcVO.getTheParticipationDTCollection();
			Collection<Object>  oldEntityCollection  = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO().getActEntityDTCollection();
			Long publicHealthCaseUID = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
			Collection<Object>  newParCollection  = new ArrayList<Object> ();
			Collection<Object>  newEntityCollection  = new ArrayList<Object> ();

			ParticipationDT oldParticipationDT = getParticipation(typeCd, classCd, oldParCollection);
      
			if(typeCd.equalsIgnoreCase(NEDSSConstants.PHC_INVESTIGATOR))
				proxyVO.setCurrentInvestigator(uid); 
			if(typeCd.equalsIgnoreCase(NEDSSConstants.PHC_FIELD_FOLLO_UP_SUPVSR))
				proxyVO.setFieldSupervisor(uid);
			if(typeCd.equalsIgnoreCase(NEDSSConstants.PHC_CASE_SUPRVSR))
				proxyVO.setCaseSupervisor(uid);
			if(PropertyUtil.isStdOrHivProgramArea(programArea))
				MessageLogUtil.createMessageLogForParticipant(proxyVO,  typeCd,  oldParticipationDT, providerUid);
			
        
			if(oldParticipationDT == null)
			{
			    if(uid != null && !uid.trim().equals(""))
			    {
			        logger.info("old par in null and create new only: " + uid);
			        newParCollection.add(createParticipation(addUserId, addTime, userId, publicHealthCaseUID, uid, classCd, typeCd, NEDSSConstants.CLASS_CD_CASE));
			        newEntityCollection.add(createPamCaseEntity(publicHealthCaseUID, uid, versionCtrlNbr, typeCd, userId));
			    }
			} else {
			    Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
			    if(uid != null && !uid.trim().equals("") && !uid.equals(oldEntityUid.toString()))
			    {
			        logger.info("participation changed newEntityUid: " + uid + " for typeCd " + typeCd);
			        newParCollection.add(deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
			        newParCollection.add(createParticipation(addUserId, addTime, userId, publicHealthCaseUID, uid, classCd, typeCd, NEDSSConstants.CLASS_CD_CASE));

			        //Delete and Create PamCaseEntity
			        newEntityCollection.add(deletePamCaseEntity(typeCd, oldEntityUid, oldEntityCollection));
			        newEntityCollection.add(createPamCaseEntity(publicHealthCaseUID, uid, versionCtrlNbr, typeCd, userId));
			    }
			    else if((uid == null || (uid != null && uid.trim().equals(""))))
			    {
			        logger.warn("no EntityUid (is cleared) or not set for typeCd: " + typeCd);
			        newParCollection.add(deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
			        newEntityCollection.add(deletePamCaseEntity(typeCd, oldEntityUid, oldEntityCollection));
			    }
			}
			if(newParCollection.size() > 0)	proxyVO.getTheParticipationDTCollection().addAll(newParCollection);
			if(newEntityCollection.size() > 0) {
				proxyVO.getPageVO().getActEntityDTCollection().addAll(newEntityCollection);
			} else {
				ArrayList<NbsActEntityDT> entityDTCol = getNbsCaseEntityRecords(typeCd, oldEntityCollection); //List used duplicte records check
				if(entityDTCol != null  && !entityDTCol.isEmpty() & entityDTCol.size() == 1) {
					NbsActEntityDT entityDT=entityDTCol.get(0);
			    	entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
			    	NbsActEntityDT existingEntityDT = getNbsCaseEntity(entityDT.getTypeCd(),proxyVO.getPageVO().getActEntityDTCollection());
			    	if(existingEntityDT == null) 	proxyVO.getPageVO().getActEntityDTCollection().add(entityDT);
			    	else {
			   			proxyVO.getPageVO().getActEntityDTCollection().remove(existingEntityDT);
			    		proxyVO.getPageVO().getActEntityDTCollection().add(entityDT);		
			    	}
				} else if(entityDTCol != null  && !entityDTCol.isEmpty() & entityDTCol.size() > 1) {
					Collections.sort(entityDTCol, new Comparator<NbsActEntityDT>() {
					    public int compare(NbsActEntityDT act1, NbsActEntityDT act2) {
					    	int comp=act1.getNbsActEntityUid().compareTo(act1.getNbsActEntityUid());
					    	if(comp == 0) comp=act1.getAddTime().compareTo(act2.getAddTime());
					        return comp;
					    }
					});
					
					NbsActEntityDT entityDT=entityDTCol.get(0);
					entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
			    	proxyVO.getPageVO().getActEntityDTCollection().add(entityDT);
			    	entityDTCol.remove(0);
			    	Iterator<NbsActEntityDT> iter = entityDTCol.iterator();
			    	while(iter.hasNext()) {
			    		NbsActEntityDT deleteEntityDT=	(NbsActEntityDT) iter.next();
			    		deleteEntityDT.setItDelete(true);
			    		deleteEntityDT.setItDirty(false);
			    		deleteEntityDT.setItNew(false);
			    		proxyVO.getPageVO().getActEntityDTCollection().add(deleteEntityDT);
			    	}
				} else {}
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			logger.error("MessageLogUtil.createOrDeleteParticipation NumberFormatException thrown, "
					+ e);
			throw new NEDSSAppException(e.toString());
		} catch (NEDSSAppException e) {
			logger.error(e);
			logger.error("MessageLogUtil.createOrDeleteParticipation NEDSSAppException thrown, "
					+ e);
			throw new NEDSSAppException(e.toString());
		}
    }



	public static ParticipationDT deleteParticipation(String typeCd, String classCd, Long oldUid, Collection<Object>  oldParticipationDTCollection)
    {

        ParticipationDT participationDT = null;
        if(oldParticipationDTCollection  != null && oldParticipationDTCollection.size() > 0)
        {
           Iterator<Object>  anIterator = null;
            for(anIterator = oldParticipationDTCollection.iterator(); anIterator.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator.next();
                if(participationDT.getTypeCd().trim().equals(typeCd) && participationDT.getSubjectClassCd().trim().equals(classCd) && participationDT.getSubjectEntityUid().toString().equals(oldUid.toString()))
                {
                    logger.debug("deleting participation for typeCd: " + typeCd + " for investigation: " + participationDT.getActUid());
                    participationDT.setItDelete(true);
                    participationDT.setItDirty(false);
                    participationDT.setItNew(false);

                    return participationDT;
                }
                else
                {
                    continue;
                }
            }
        }

        return participationDT;
    }
    public static ParticipationDT getParticipation(String type_cd, String classCd,Collection<Object>  participationDTCollection)
    {

	ParticipationDT participationDT = null;
	if(participationDTCollection  != null && participationDTCollection.size() > 0)
	{
	   Iterator<Object>  anIterator = null;
	    for(anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
	    {
		participationDT = (ParticipationDT)anIterator.next();
		if(participationDT.getSubjectEntityUid() != null && classCd.equals(participationDT.getSubjectClassCd()) && type_cd.equals(participationDT.getTypeCd()))
		{
		   logger.debug("participation exist for investigation: " + participationDT.getActUid() + " subjectEntity: " +  participationDT.getSubjectEntityUid());
		   return  participationDT;
		}
		else continue;
	    }
	}
	return null;
    }

    public static NbsActEntityDT getNbsCaseEntity(String typeCd, Collection<Object>  entityDTCollection) {

    	if(entityDTCollection  != null && entityDTCollection.size() > 0) {
    		Iterator<Object> iter = entityDTCollection.iterator();
    		while(iter.hasNext()) {
    			NbsActEntityDT entityDT = (NbsActEntityDT) iter.next();
    			if(entityDT.getEntityUid() != null && entityDT.getTypeCd().equalsIgnoreCase(typeCd)) {
    				entityDT.setItDirty(true);
    				return entityDT;
    			}
    		}
    	}
    	return null;
    }


    public static ArrayList<NbsActEntityDT> getNbsCaseEntityRecords(String typeCd, Collection<Object>  entityDTCollection) {
    	ArrayList<NbsActEntityDT> actEntity = new ArrayList<NbsActEntityDT>();
    	if(entityDTCollection  != null && entityDTCollection.size() > 0) {
    		Iterator<Object> iter = entityDTCollection.iterator();
    		while(iter.hasNext()) {
    			NbsActEntityDT entityDT = (NbsActEntityDT) iter.next();
    			if(entityDT.getEntityUid() != null && entityDT.getTypeCd().equalsIgnoreCase(typeCd)) {
    				entityDT.setItDirty(true);
    				actEntity.add(entityDT);
       			}
    		}
    	}
    	
    	return actEntity;
    }


	
	@SuppressWarnings("unchecked")
	public static void setActIdForPublicHealthCaseEdit(PublicHealthCaseVO phcVO, Map<Object,Object> answerMap )
			throws NEDSSSystemException {
		try {
			
			boolean doesStateCaseIdExists = false;
			boolean doesCityCaseIdExists = false;
			boolean doesLegacyCaseIdExists = false;
			String stateID = getVal(answerMap.get(PageConstants.STATE_CASE));
			String cityID = getVal(answerMap.get(PageConstants.COUNTY_CASE));
			String legacyID = getVal(answerMap.get(PageConstants.LEGACY_CASE_ID));
			
			Collection<Object> ActIds = phcVO.getTheActIdDTCollection();
			if(ActIds!=null && ActIds.size()>0){
				for(Object actIdDT: ActIds){
					ActIdDT actId= (ActIdDT)actIdDT;
					if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_STATE_TYPE_CD)){
						actId.setRootExtensionTxt(stateID);
						doesStateCaseIdExists=true;
					}
					else if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_CITY_TYPE_CD)){
						actId.setRootExtensionTxt(cityID);
						doesCityCaseIdExists=true;
					}
					if(actId.getTypeCd()!=null && actId.getTypeCd().equals(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)){
						actId.setRootExtensionTxt(legacyID);
						doesLegacyCaseIdExists=true;
					}
				}
			}
			PublicHealthCaseDT phcDT = phcVO.getThePublicHealthCaseDT();
			if(ActIds==null)
				phcVO.setTheActIdDTCollection(new ArrayList<Object>());
			
			if(!doesStateCaseIdExists && stateID!=null && stateID.trim().length()>0){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(1);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
				actIdDT.setRootExtensionTxt(stateID);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
			
			if(!doesCityCaseIdExists &&  cityID!=null && cityID.trim().length()>0){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(2);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_CITY_TYPE_CD);
				actIdDT.setRootExtensionTxt(cityID);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
			
			if(!doesLegacyCaseIdExists && legacyID!=null && legacyID.trim().length()>0){
				ActIdDT actIdDT = new ActIdDT();
				actIdDT.setActIdSeq(3);
				actIdDT.setActUid(phcDT.getPublicHealthCaseUid());
				actIdDT.setTypeCd(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD);
				actIdDT.setRootExtensionTxt(legacyID);
				actIdDT.setRecordStatusTime(phcDT.getAddTime());
				actIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				actIdDT.setAddTime(phcDT.getAddTime());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setAddUserId(phcDT.getAddUserId());
				actIdDT.setLastChgTime(phcDT.getLastChgTime());
				actIdDT.setLastChgUserId(phcDT.getLastChgUserId());
				phcVO.getTheActIdDTCollection().add(actIdDT);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorTxt = "Error while updating Act Ids for CDA Document"+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}


    public void handleFormRules(PageForm form, String actionMode) {
		PageRulesGenerator reUtil = new PageRulesGenerator();
		Map<Object,Object> errorTabs = new HashMap<Object,Object>();
		Map<Object,Object> formFieldMap = reUtil.initiateForm(getQuestionMap(), (BaseForm) form, (ClientVO) form.getPageClientVO());
		if (formFieldMap != null && formFieldMap.size() > 0) {
			Collection<Object>  errorColl = formFieldMap.values();
			Iterator<Object> ite = errorColl.iterator();
			ArrayList<Object> errors = new ArrayList<Object> ();
			while (ite.hasNext()) {
				FormField fField = (FormField) ite.next();
				//fField != null added for the intrgrating with the new UI.
				if (fField != null && fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
				//if (fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
					if(fField.getErrorMessage()!=null && fField.getErrorMessage().size()>0)
					errors.add(fField);
					if (fField.getTabId() != null)
						errorTabs.put(fField.getTabId().toString(), fField
								.getTabId().toString());
				}
			}

			form.setErrorList(errors);
			form.setFormFieldMap(formFieldMap);
			if(errorTabs!=null){
				form.setErrorTabs(errorTabs.values().toArray());
			}
		}
	}
    //generic version
    public void handleFormRules(BaseForm form, ClientVO clientVO, String actionMode) {
		PageRulesGenerator reUtil = new PageRulesGenerator();
		Map<Object,Object> errorTabs = new HashMap<Object,Object>();
		Map<Object,Object> formFieldMap = reUtil.initiateForm(getQuestionMap(), (BaseForm) form, clientVO);
		if (formFieldMap != null && formFieldMap.size() > 0) {
			Collection<Object>  errorColl = formFieldMap.values();
			Iterator<Object> ite = errorColl.iterator();
			ArrayList<Object> errors = new ArrayList<Object> ();
			while (ite.hasNext()) {
				FormField fField = (FormField) ite.next();
				//fField != null added for the integrating with the new UI.
				if (fField != null && fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
					if(fField.getErrorMessage()!=null && fField.getErrorMessage().size()>0)
					errors.add(fField);
					if (fField.getTabId() != null)
						errorTabs.put(fField.getTabId().toString(), fField
								.getTabId().toString());
				}
			}

			form.setErrorList(errors);
			form.setFormFieldMap(formFieldMap);
			if(errorTabs!=null){
				form.setErrorTabs(errorTabs.values().toArray());
			}
		}
	}
    
    
    private static Timestamp setPDate(Map<Object,Object> answerMap, String id) {

    	Object obj = answerMap.get(id);
    	if(obj != null)
    		return StringUtils.stringToStrutsTimestamp(obj.toString());

    	return null;
    }
//TODO: Replace with Generic Function
    private void setAnswerArrayMapAnswers(PageForm form, Map<Object,Object> returnMap, String userId) {

    	Map<Object,Object> answerArrayMap = form.getPageClientVO().getArrayAnswerMap();
    	
    	if(answerArrayMap != null && answerArrayMap.size() > 0) {

			Iterator<Object> anIter = answerArrayMap.keySet().iterator();
			while(anIter.hasNext()) {				
				String questionId = getVal(anIter.next());				
				
				Map<Object,Object> map = form.getSubSecStructureMap();
				Iterator<?> it = map.entrySet().iterator();
				while (it.hasNext()) {
			        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
			        String[][] batchrec = (String[][])pairs.getValue();
			        if(batchrec != null && batchrec.length >0){
			        for(int i=0;i<batchrec.length;i++){
			        	if(batchrec[i][0] != null && !batchrec[i][0].equalsIgnoreCase("") && batchrec[i][0].toString().equalsIgnoreCase(questionId) )
			        		answerArrayMap.remove(batchrec[i][0]);
			          }
			        }
				}
			}
    	}
    	
		if(answerArrayMap != null && answerArrayMap.size() > 0) {

			Iterator<Object> anIter = answerArrayMap.keySet().iterator();
			while(anIter.hasNext()) {
				ArrayList<Object> answerList = new ArrayList<Object> ();
				String questionId = getVal(anIter.next());
				if(questionId.equals(PageConstants.DETAILED_RACE_ASIAN) || questionId.equals(PageConstants.DETAILED_RACE_HAWAII) || questionId.equals(PageConstants.CONFIRM_METHOD_CD))
					continue;				
				
				String[] answers = (String[])answerArrayMap.get(questionId);
				String otherAnswer=null;
				String unitAnswer=null;
				if(answerArrayMap.get(questionId+"Oth")!=null){
					otherAnswer=((String[])answerArrayMap.get(questionId+"Oth"))[0].toString();
				}
				if(answerArrayMap.get(questionId+"Unit")!=null){
					unitAnswer=answerArrayMap.get(questionId+"Unit").toString();
				}
				for(int i=1; i<=answers.length; i++) {
					String answerTxt = answers[i-1];
					if(answerTxt == null || (answerTxt == "")) continue;
					NbsAnswerDT answerDT = new NbsAnswerDT();
					answerDT.setSeqNbr(new Integer(i));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					if(otherAnswer!=null && answerTxt.equals("OTH"))
						answerTxt=answerTxt+"^"+otherAnswer;
					if(unitAnswer!=null)
						answerTxt=answerTxt+"Unit^"+unitAnswer;
					
					answerDT.setAnswerTxt(answerTxt);
					//answerList.add(answerDT); //TO DELETE this line once QUESTION_CACHE IS AVAILABLE (NBS_case_answer NOT NULL columns ?? back end break??) 
					
					if(getQuestionMap() != null && getQuestionMap().get(questionId) != null) {

						NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)getQuestionMap().get(questionId);
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							answerList.add(answerDT);
						}
					} else {
						if (!questionId.endsWith("Oth")) //handled above
							logger.error("QuestionId: " + questionId  + " is not found in PAM Answers");
					}
				}
				returnMap.put(questionId, answerList);
			}
		}
    }

    
    private void setAnswerArrayMapAnswers(BaseForm form, ClientVO clientVO, Map<Object,Object> returnMap, String userId) {

    	Map<Object,Object> answerArrayMap = clientVO.getArrayAnswerMap();
    	
    	if(answerArrayMap != null && answerArrayMap.size() > 0) {

			Iterator<Object> anIter = answerArrayMap.keySet().iterator();
			while(anIter.hasNext()) {				
				String questionId = getVal(anIter.next());				
				
				Map<Object,Object> map = form.getSubSecStructureMap();
				Iterator<?> it = map.entrySet().iterator();
				while (it.hasNext()) {
			        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
			        String[][] batchrec = (String[][])pairs.getValue();
			        if(batchrec != null && batchrec.length >0){
			        for(int i=0;i<batchrec.length;i++){
			        	if(batchrec[i][0] != null && !batchrec[i][0].equalsIgnoreCase("") && batchrec[i][0].toString().equalsIgnoreCase(questionId) )
			        		answerArrayMap.remove(batchrec[i][0]);
			          }
			        }
				}
			}
    	}
    	
		if(answerArrayMap != null && answerArrayMap.size() > 0) {

			Iterator<Object> anIter = answerArrayMap.keySet().iterator();
			while(anIter.hasNext()) {
				ArrayList<Object> answerList = new ArrayList<Object> ();
				String questionId = getVal(anIter.next());
				if(questionId.equals(PageConstants.DETAILED_RACE_ASIAN) || questionId.equals(PageConstants.DETAILED_RACE_HAWAII) || questionId.equals(PageConstants.CONFIRM_METHOD_CD))
					continue;				
				
				String[] answers = (String[])answerArrayMap.get(questionId);
				String otherAnswer=null;
				String unitAnswer=null;
				if(answerArrayMap.get(questionId+"Oth")!=null){
					otherAnswer=((String[])answerArrayMap.get(questionId+"Oth"))[0].toString();
				}
				if(answerArrayMap.get(questionId+"Unit")!=null){
					unitAnswer=answerArrayMap.get(questionId+"Unit").toString();
				}
				for(int i=1; i<=answers.length; i++) {
					String answerTxt = answers[i-1];
					if(answerTxt == null || (answerTxt == "")) continue;
					NbsAnswerDT answerDT = new NbsAnswerDT();
					answerDT.setSeqNbr(new Integer(i));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					if(otherAnswer!=null && answerTxt.equals("OTH"))
						answerTxt=answerTxt+"^"+otherAnswer;
					if(unitAnswer!=null)
						answerTxt=answerTxt+"Unit^"+unitAnswer;
					
					answerDT.setAnswerTxt(answerTxt);
					//answerList.add(answerDT); //TO DELETE this line once QUESTION_CACHE IS AVAILABLE (NBS_case_answer NOT NULL columns ?? back end break??) 
					
					if(getQuestionMap() != null && getQuestionMap().get(questionId) != null) {

						NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)getQuestionMap().get(questionId);
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							answerList.add(answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in Page Answers");
					}
				}
				returnMap.put(questionId, answerList);
			}
		}
    }
    
    
    private void setCheckBoxAnswersWithCodeSet(Map<Object,Object> answerMap) {

    	if(getQuestionMap() == null) return;
    	ArrayList<Object> chkboxList  = new ArrayList<Object> ();
    	Iterator<Object> iter = this.getQuestionMap().keySet().iterator();
    	while(iter.hasNext()) {
    		NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) getQuestionMap().get(iter.next());
    		if(qMetadata.getDataType() != null && qMetadata.getDataType().equalsIgnoreCase("Boolean")) {
    			chkboxList.add(qMetadata.getQuestionIdentifier());
    		}
    	}
    	Map<?,?> map = cdv.getCodedValuesAsTreeMap("TF_PAM");
    	for(int i=0; i < chkboxList.size(); i++) {
    		String answer = answerMap.get(chkboxList.get(i)) == null ? null : (String)answerMap.get(chkboxList.get(i));
        	if(answer != null && answer.equals("1"))
        		answerMap.put(chkboxList.get(i), map.get("True"));
        	else
        		answerMap.put(chkboxList.get(i), map.get("False"));
    	}
    }

    private static void handleRaceForRules(PageClientVO clientVO) {
		if(clientVO.getNotAsked() == 1 || clientVO.getRefusedToAnswer() == 1 || clientVO.getOtherRace() == 1 || clientVO.getUnKnownRace() == 1 || clientVO.getAfricanAmericanRace() == 1 || clientVO.getAmericanIndianAlskanRace() == 1 || clientVO.getAsianRace() == 1 || clientVO.getHawaiianRace() == 1  || clientVO.getWhiteRace() == 1)
			clientVO.setAnswer(PageConstants.RACE, PageConstants.RACE);
    }

    //generic version
    public static void handleRaceForRules(ClientVO clientVO) {
		if(clientVO.getNotAsked() == 1 || clientVO.getRefusedToAnswer() == 1 || clientVO.getOtherRace() == 1 || clientVO.getUnKnownRace() == 1 || clientVO.getAfricanAmericanRace() == 1 || clientVO.getAmericanIndianAlskanRace() == 1 || clientVO.getAsianRace() == 1 || clientVO.getHawaiianRace() == 1  || clientVO.getWhiteRace() == 1)
			clientVO.setAnswer(PageConstants.RACE, PageConstants.RACE);
    }
    
    
	private static boolean caseStatusChanged(String oldCaseStatus, String newCaseStatus) {

		if (oldCaseStatus == null && (newCaseStatus == null || newCaseStatus == "")) {
			return false;
		} else if (
			(oldCaseStatus == null && newCaseStatus != null)
				|| (oldCaseStatus != null && (newCaseStatus == null || newCaseStatus == ""))) {
			return true;
		} else if (oldCaseStatus.equalsIgnoreCase(newCaseStatus)) {
			return false;
		} else {
			return true;
		}
	}

	public static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}
	public static ProgramAreaVO getProgAreaVO(HttpSession session) {

		String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
		String programArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
		ProgramAreaVO programAreaVO = null;

		programAreaVO = cdv.getProgramAreaCondition("('" +programArea + "')", conditionCd);
		if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
			   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, conditionCd);
		return programAreaVO;
	}

	public static void setContextForCreate(PageActProxyVO proxyVO, Long phcUid, ProgramAreaVO programArea, HttpSession session) {
		// context store
		String investigationJurisdiction = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
		NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, phcUid.toString());
		NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
		String progArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
		NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, progArea);
	}

	public static void setLDFs(PageForm form, PageClientVO clientVO, HttpServletRequest request) throws Exception {
		//LDF Specific Stuff
		try {
			Map<Object,Object> answerMap = clientVO.getAnswerMap();
			String actionMode = form.getActionMode() == null ? NEDSSConstants.CREATE_LOAD_ACTION : form.getActionMode();

		} catch (Exception e) {
			logger.error("Error in setLDFs in PamStoreUtil : " + e.toString());
			//throw new Exception(e.toString());
		}
	}

	/**
	 * 	On Edit Submit, NBS Answers should be marked as itNew, itDirty or itDelete based on the changes...
	 * @param form
	 * @param proxyVO
	 */
	public static void updateNbsAnswersForDirty(PageForm form, PageProxyVO proxyVO ) {

		Map<Object,Object> oldAnswers = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO().getPamAnswerDTMap();
		Map<Object,Object> newAnswers = ((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap();
		
		//If Interview
		if(
				NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()) || 
					NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()) || 
					NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()))
		       
        {
            Iterator<Object> iter = newAnswers.keySet().iterator();
            while (iter.hasNext())
            {
                String qId = (String) iter.next();
                Object obj = newAnswers.get(qId);
                if (obj instanceof ArrayList<?>)
                {
                    ArrayList<Object> answerList = (ArrayList<Object>) obj;

                    checkAnswerListForDirty(oldAnswers, answerList, "");
                }
                else
                {
                    NbsAnswerDT dt = (NbsAnswerDT) obj;
                    dt.setItNew(true);
                    dt.setItDirty(false);
                    dt.setItDelete(false);
                }
            }
            return;
        }
		
		//Iterate through the newAnswers and mark it accordingly
		//1. If present in new and not present in old - mark it NEW
		//2. If present in both - mark it DIRTY
		//3. If present in old and not present in new - mark it DELETE
		Iterator<Object> iter = newAnswers.keySet().iterator();
		while(iter.hasNext()) {
			String qId = (String) iter.next();
			Object obj = newAnswers.get(qId);
			if(obj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) obj;

				checkAnswerListForDirty(oldAnswers, answerList,"");
			} else {
				NbsAnswerDT dt = (NbsAnswerDT) obj;
				Long qUid = (Long) dt.getNbsQuestionUid();
				if(oldAnswers.get(qUid) == null) {
					dt.setItNew(true);
					dt.setItDirty(false);
					dt.setItDelete(false);
				} else {
					NbsAnswerDT oldDT = (NbsAnswerDT) oldAnswers.get(qUid);
					dt.setItDirty(true);
					dt.setItNew(false);
					dt.setItDelete(false);
					dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
					//remove it from oldMap so that the leftovers in oldMap are DELETE candidates
					oldAnswers.remove(qUid);
				}
			}
		}
		//For the leftovers in the oldAnswers, mark them all to DELETE
		Iterator<Object> iter1 = oldAnswers.keySet().iterator();
		while(iter1.hasNext()) {
			Long qUid = (Long) iter1.next();
			Object oldObj = oldAnswers.get(qUid);
			if(oldObj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) oldObj;
				Map<Object, Object> messageLogMap = new HashMap<Object, Object> ();		

				markAnswerListForDelete(answerList);
			} else {
				NbsAnswerDT dt = (NbsAnswerDT)oldObj;
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		}
		
		//Add all from old to the new
		((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap().putAll(oldAnswers);
	}


	/**
	 * Generic Version of updateNbsAnswersForDirty
	 * 	On Edit Submit, NBS Answers should be marked as itNew, itDirty or itDelete based on the changes...
	 * @param form
	 * @param proxyVO
	 */
	public static Map<Object, Object> updateNbsAnswersForDirty(Map<Object,Object> oldAnswers, Map<Object,Object> newAnswers ) {
		//Iterate through the newAnswers and mark it accordingly
		//1. If present in new and not present in old - mark it NEW
		//2. If present in both - mark it DIRTY
		//3. If present in old and not present in new - mark it DELETE
		Iterator<Object> iter = newAnswers.keySet().iterator();
		while(iter.hasNext()) {
			String qId = (String) iter.next();
			Object obj = newAnswers.get(qId);
			if(obj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) obj;

				checkAnswerListForDirtyGeneric(oldAnswers, answerList,"");
			} else {
				NbsAnswerDT dt = (NbsAnswerDT) obj;
				Long qUid = (Long) dt.getNbsQuestionUid();
				if(oldAnswers.get(qUid) == null) {
					dt.setItNew(true);
					dt.setItDirty(false);
					dt.setItDelete(false);
				} else {
					NbsAnswerDT oldDT = (NbsAnswerDT) oldAnswers.get(qUid);
					dt.setItDirty(true);
					dt.setItNew(false);
					dt.setItDelete(false);
					dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
					//remove it from oldMap so that the leftovers in oldMap are DELETE candidates
					oldAnswers.remove(qUid);
				}
			}
		}
		//For the leftovers in the oldAnswers, mark them all to DELETE
		Iterator<Object> iter1 = oldAnswers.keySet().iterator();
		while(iter1.hasNext()) {
			Long qUid = (Long) iter1.next();
			Object oldObj = oldAnswers.get(qUid);
			if(oldObj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) oldObj;
				Map<Object, Object> messageLogMap = new HashMap<Object, Object> ();		

				markAnswerListForDeleteGeneric(answerList);
			} else {
				NbsAnswerDT dt = (NbsAnswerDT)oldObj;
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		}
		
		//Add all from old to the new
		return(oldAnswers);
	}	
	
	
	private static void checkForUpdatedSupOrCaseSupComments(Map<Object, Object> oldAnswers, Map<Object, Object> newAnswers, PageForm form) {
		form.setUpdatedRepeatingCommentsMap(new HashMap<Object, Object>());
		
		if(form.getQuestionMetadataMap()!=null){
			Map<Object, Object> messageLogMap = new HashMap<Object, Object>();
			Set<Object> set = form.getQuestionMetadataMap().keySet();
			if(set!=null && set.size()>0){
				Iterator<Object> iter = set.iterator();
				while(iter.hasNext()){
					NbsQuestionMetadata metadata=(NbsQuestionMetadata)iter.next();
					String questionUid= metadata.getNbsQuestionUid().toString();
					if(( newAnswers.get(questionUid)==null) && oldAnswers.get(metadata.getNbsQuestionUid())==null){
						//no comment scenario
						continue;
					}else if((oldAnswers.get(metadata.getNbsQuestionUid())!=null && newAnswers.get(questionUid)==null)
							||(oldAnswers.get(metadata.getNbsQuestionUid())==null && newAnswers.get(questionUid)!=null)){
						//write log for (add from no comment)/(delete to no comment) scenario
						messageLogMap.put(metadata.getNbsQuestionUid(), "UPDATED");
						continue;
					}else if(oldAnswers.values()!=null && newAnswers.values()!=null  ){
						Collection<Object> newAnswerColl = (ArrayList<Object>)newAnswers.get(questionUid);
						Collection<Object> oldAnswerColl = (ArrayList<Object>)oldAnswers.get(metadata.getNbsQuestionUid());
						Collection<String> updatedRepeatComments = new ArrayList<String>();
						Iterator<Object> newAnsIter = newAnswerColl.iterator();
						while(newAnsIter.hasNext()){
							NbsAnswerDT  caseDT = (NbsAnswerDT)newAnsIter.next();
							updatedRepeatComments.add(caseDT.getAnswerTxt());
						}

						Iterator<Object> oldAnsIter = oldAnswerColl.iterator();
						while(oldAnsIter.hasNext()){
							NbsAnswerDT  caseDT = (NbsAnswerDT)oldAnsIter.next();
							if(updatedRepeatComments.contains(caseDT.getAnswerTxt())){
								updatedRepeatComments.remove(caseDT.getAnswerTxt());
							}else{
								messageLogMap.put(metadata.getNbsQuestionUid(), "UPDATED");
								break;
							}
							
						}
						if(updatedRepeatComments.size()>0){
							messageLogMap.put(metadata.getNbsQuestionUid(), "UPDATED");
						}
					}else{
							logger.debug("checkForUpdatedSupOrCaseSupComments: Not message written!");		
					}
				}
			form.getUpdatedRepeatingCommentsMap().putAll(messageLogMap);
			}
		}
	}
	private static void markAnswerListForDelete(ArrayList<Object> list ) {
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			NbsAnswerDT dt = (NbsAnswerDT)iter.next();
			dt.setItDelete(true);
			dt.setItNew(false);
			dt.setItDirty(false);
			
		}
	}
	
	
    //generic version of markAnswerListForDelete
	private static void markAnswerListForDeleteGeneric(ArrayList<Object> list ) {
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			NbsAnswerDT dt = (NbsAnswerDT)iter.next();
			dt.setItDelete(true);
			dt.setItNew(false);
			dt.setItDirty(false);
			
		}
	}
	
	public static void checkAnswerListForDirty(Map<Object,Object> oldAnswers, ArrayList<Object> list, String strRepeating) {
		ArrayList<Object> tempList = new ArrayList<Object> ();
		ArrayList<Object> oldAList = null;
		Iterator<Object> iter = list.iterator();
		if(strRepeating.equals("")){
		while(iter.hasNext()) {
			NbsAnswerDT dt = (NbsAnswerDT)iter.next();
			Long qUid = (Long) dt.getNbsQuestionUid();
			oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
			if(oldAList != null && oldAList.size() > 0) {
				Iterator<Object> oldIter = oldAList.iterator();
				while(oldIter.hasNext()) {
					 NbsAnswerDT oldDT = (NbsAnswerDT)oldIter.next();
					 if(oldDT.getAnswerTxt() != null && dt.getAnswerTxt() != null && oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt())) {
							dt.setItDirty(true);
							dt.setItNew(false);
							dt.setItDelete(false);
							dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
							tempList.add(oldDT);
					 } else if(dt.getNbsAnswerUid() == null ){
							dt.setItNew(true);
							dt.setItDirty(false);
							dt.setItDelete(false);
							
							
					 }
				 }
			}
		}
		}else if(strRepeating.equals("Repeating")){
			while(iter.hasNext()) {
				NbsAnswerDT dt = (NbsAnswerDT)iter.next();
				Long qUid = (Long) dt.getNbsQuestionUid();
				oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
				if(oldAList != null && oldAList.size() > 0) {
					Iterator<Object> oldIter = oldAList.iterator();
					boolean isUpdateRepeatingAnswer = false;
					while(oldIter.hasNext()) {
						 NbsAnswerDT oldDT = (NbsAnswerDT)oldIter.next();
						 //Corrected bug in next line 9-4-2013 (GT & Jit)
						 if(oldDT.getAnswerTxt() != null && dt.getAnswerTxt() != null && oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt()) 
								 && dt.getAnswerGroupSeqNbr() != null && oldDT.getAnswerGroupSeqNbr() != null && dt.getAnswerGroupSeqNbr().intValue() == 
								 oldDT.getAnswerGroupSeqNbr().intValue()) {
								dt.setItDirty(true);
								dt.setItNew(false);
								dt.setItDelete(false);
								dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
								tempList.add(oldDT);
						 } else if(dt.getNbsAnswerUid() == null ){
								dt.setItNew(true);
								dt.setItDirty(false);
								dt.setItDelete(false);
								isUpdateRepeatingAnswer = true; 
						 }
					 }
				}
			}			
		}
		//remove tempList entries from oldMap
		if(oldAList != null)
			oldAList.removeAll(tempList);
	}
	/////////////////////////////////////////////////////////////////////////////////
	//Generic version of checkAnswerListForDirty uses AnswerDT
	//
	private static void checkAnswerListForDirtyGeneric(Map<Object,Object> oldAnswers, ArrayList<Object> list, String strRepeating) {
		ArrayList<Object> tempList = new ArrayList<Object> ();
		ArrayList<Object> oldAList = null;
		Iterator<Object> iter = list.iterator();
		if(strRepeating.equals("")){
		while(iter.hasNext()) {
			NbsAnswerDT dt = (NbsAnswerDT)iter.next();
			Long qUid = (Long) dt.getNbsQuestionUid();
			oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
			if(oldAList != null && oldAList.size() > 0) {
				Iterator<Object> oldIter = oldAList.iterator();
				while(oldIter.hasNext()) {
					 NbsAnswerDT oldDT = (NbsAnswerDT)oldIter.next();
					 if(oldDT.getAnswerTxt() != null && dt.getAnswerTxt() != null && oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt())) {
							dt.setItDirty(true);
							dt.setItNew(false);
							dt.setItDelete(false);
							dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
							tempList.add(oldDT);
					 } else if(dt.getNbsAnswerUid() == null ){
							dt.setItNew(true);
							dt.setItDirty(false);
							dt.setItDelete(false);
					 }
				 }
			}
		}
		}else if(strRepeating.equals("Repeating")){
			while(iter.hasNext()) {
				NbsAnswerDT dt = (NbsAnswerDT)iter.next();
				Long qUid = (Long) dt.getNbsQuestionUid();
				oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
				if(oldAList != null && oldAList.size() > 0) {
					Iterator<Object> oldIter = oldAList.iterator();
					boolean isUpdateRepeatingAnswer = false;
					while(oldIter.hasNext()) {
						 NbsAnswerDT oldDT = (NbsAnswerDT)oldIter.next();
						 //Corrected bug in next line 9-4-2013 (GT & Jit)
						 if(oldDT.getAnswerTxt() != null && dt.getAnswerTxt() != null && oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt()) 
								 && dt.getAnswerGroupSeqNbr() != null && oldDT.getAnswerGroupSeqNbr() != null && dt.getAnswerGroupSeqNbr().intValue() == 
								 oldDT.getAnswerGroupSeqNbr().intValue()) {
								dt.setItDirty(true);
								dt.setItNew(false);
								dt.setItDelete(false);
								dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
								tempList.add(oldDT);
						 } else if(dt.getNbsAnswerUid() == null ){
								dt.setItNew(true);
								dt.setItDirty(false);
								dt.setItDelete(false);
								isUpdateRepeatingAnswer = true; 
						 }
					 }
				}
			}			
		}
		//remove tempList entries from oldMap
		if(oldAList != null)
			oldAList.removeAll(tempList);
	}
	

	public static String splitUid(String strInvestigatorUID) {
		String uid = "";
		try {
			if(strInvestigatorUID.indexOf("|") == -1)
				return strInvestigatorUID;

			uid = strInvestigatorUID.substring(0, strInvestigatorUID.indexOf("|"));
		} catch (Exception e) {
			logger.error("Error while retrieving uid from EntityUid: " + e.toString());
		}
		return uid;
	}
	public static String splitVerCtrlNbr(String strInvestigatorUID) {
		String versionCtrlNbr = "";
		try {

			if(strInvestigatorUID.indexOf("|") == -1)
				return "1";
			versionCtrlNbr = strInvestigatorUID.substring(strInvestigatorUID.indexOf("|")+1);
			
			if(versionCtrlNbr== null || versionCtrlNbr.isEmpty())
				return "1";
			
		} catch (Exception e) {
			logger.error("Error while retrieving versionCtrlNbr from EntityUid: " + e.toString());
		}
		return versionCtrlNbr;
	}

/* This method checks the association related to Investigation.
 * It validates required NND fields before it saves the data.
 *
 */
	public static void checkNotificationAssociationToInvestigation(PageForm form, HttpServletRequest req) throws NEDSSAppConcurrentDataException, Exception {
		Map<Object,Object> notificationReqFields = new TreeMap<Object,Object>();
		String formCd = getInvFormCd(req, form);
		if (form.getPageClientVO().getOldPageProxyVO() != null && ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getAssociatedNotificationsInd()) {
			Collection<Object>  notifReqColl = null;
			if (QuestionsCache.getQuestionMapEJBRef() != null) {
				notifReqColl = QuestionsCache.getQuestionMapEJBRef()
						.retrieveQuestionRequiredNnd(formCd);
			}
			if (notifReqColl != null) {
				Iterator<Object> ite = notifReqColl.iterator();
				while (ite.hasNext()) {
					NbsQuestionMetadata questionMetadata = (NbsQuestionMetadata) ite
							.next();
					String fieldValue = (String) form.getPageClientVO()
							.getAnswerMap().get(
									questionMetadata.getQuestionIdentifier());
					// needs to handle this in metadata,as these values are not in the questionsMap
					// Need to revisit this area after 2.0
					if (!(questionMetadata.getQuestionIdentifier().equals("DEM197")||
							questionMetadata.getQuestionIdentifier().equals("INV168")||
							questionMetadata.getQuestionIdentifier().equals("INV169")||
							questionMetadata.getQuestionIdentifier().equals("TUB266"))
							&& (fieldValue == null || fieldValue.trim().equals(""))) {
						notificationReqFields.put(questionMetadata
								.getQuestionIdentifier(), questionMetadata
								.getQuestionLabel());
					}
				}
			}
			if(notificationReqFields.size() > 0) {
				form.getAttributeMap().put("REQ_FOR_NOTIF", "true");
				form.getAttributeMap().put("NotifReqMap", notificationReqFields);
			}/*else {
				form.getAttributeMap().remove("REQ_FOR_NOTIF");
				form.getAttributeMap().remove("NotifReqMap");
			}*/
		}

	}
	
	/**
	 * setAdditionalPhcAnswersForCreateEdit creates ConfirmationMethods & Other answers
	 * @param proxyVO
	 * @param form
	 */
	private static void setAdditionalPhcAnswersForCreateEdit(PublicHealthCaseVO phcVO, PageForm form) {
		
		Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
		Map<Object,Object> answerArrayMap = form.getPageClientVO().getArrayAnswerMap();
		String reportingSrc = getVal(answerMap.get(PageConstants.REPORTING_SOURCE));
		phcVO.getThePublicHealthCaseDT().setRptSourceCd(reportingSrc);
		phcVO.getThePublicHealthCaseDT().setEffectiveToTime_s(getVal(answerMap.get(PageConstants.ILLNESS_END_DATE)));
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationAmt(getVal(answerMap.get(PageConstants.ILLNESS_DURATION)));
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd(getVal(answerMap.get(PageConstants.ILLNESS_DURATION_UNITS)));
		String diseaseImportCd = getVal(answerMap.get(PageConstants.DISEASE_IMPORT_CD));
		phcVO.getThePublicHealthCaseDT().setDiseaseImportedCd(diseaseImportCd);
		String transModeCd = getVal(answerMap.get(PageConstants.TRANSMISN_MODE_CD));
		phcVO.getThePublicHealthCaseDT().setTransmissionModeCd(transModeCd);
		phcVO.getThePublicHealthCaseDT().setTransmissionModeDescTxt(transModeCd);
		String detectionMethodCd = getVal(answerMap.get(PageConstants.DETECTION_METHOD_CD));
		phcVO.getThePublicHealthCaseDT().setDetectionMethodCd(detectionMethodCd);
		_confirmationMethodList(phcVO, answerMap, answerArrayMap);
		
	}
	
	
	private static void _confirmationMethodList(PublicHealthCaseVO phcVO, Map<Object,Object> answerMap, Map<Object,Object> answerArrayMap) {

		Collection<Object>  confMethodColl = new ArrayList<Object> ();

		String confirmationDate = getVal(answerMap.get(PageConstants.CONFIRM_DATE));
		String[] answers = (String[])answerArrayMap.get(PageConstants.CONFIRM_METHOD_CD);
		if(answers != null && answers.length > 0) {
			for(int i=1; i<=answers.length; i++) {
				String answerTxt = answers[i-1];
				if(answerTxt != null && !answerTxt.equals("")) {
		            ConfirmationMethodDT cm = new ConfirmationMethodDT();
		            cm.setConfirmationMethodCd(answerTxt);
		            //cm.setConfirmationMethodDescTxt(cdv.getDescForCode("PHC_CONF_M", answerTxt));
		            cm.setConfirmationMethodTime_s(confirmationDate);
		            cm.setItNew(true);
		            confMethodColl.add(cm);			
				} else {
		            ConfirmationMethodDT cm = new ConfirmationMethodDT();
		            cm.setConfirmationMethodTime_s(confirmationDate);
		            cm.setConfirmationMethodCd("NA");
		            cm.setItNew(true);
		            confMethodColl.add(cm);				
				}
			}			
		} else if(!confirmationDate.equals("")){
	        ConfirmationMethodDT cm = new ConfirmationMethodDT();
	        cm.setConfirmationMethodCd("NA");
	        cm.setConfirmationMethodTime_s(confirmationDate);
	        cm.setItNew(true);
	        confMethodColl.add(cm);			
		}
	      if (confMethodColl.size() > 0) 
	        phcVO.setTheConfirmationMethodDTCollection(confMethodColl);
	      else
	        phcVO.setTheConfirmationMethodDTCollection(null);
	}
	
	/**
	 * _setEntitiesForCreate method called by individual PAMs to create Participations' and NBSActEntitys' 
	 * @param proxyVO
	 * @param form
	 * @param revisionPatientUID
	 * @param userId
	 * @param request
	 * @param entityUid
	 * @param type
	 * @param subjectClassCd
	 * @throws NEDSSAppException  
	 */
    public static void _setEntitiesForCreate(PageProxyVO proxyVO, PageForm form, String userId, String entityUid, String type, String subjectClassCd, Long actUid , String programAreaCode, String objectType, HttpServletRequest request) throws NEDSSAppException {
    	logger.debug("in setEntitiesForCreate() - type = " +type);;
		//Long phcUID = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		//String programArea = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();

		Collection<Object>  partsColl = new ArrayList<Object> ();
		
		try {
			logger.debug("_setEntitiesForCreate calling splitUid()");
			String uid = splitUid(entityUid);
			logger.debug("_setEntitiesForCreate returning from splitUid()");
			logger.debug("_setEntitiesForCreate calling splitVerCtrlNbr()");
			String versionCtrlNbr = splitVerCtrlNbr(entityUid);
			logger.debug("_setEntitiesForCreate returning from splitVerCtrlNbr()");
			if (!entityUid.trim().equals("") && actUid != null) {
				ParticipationDT invPartDT = createParticipation(null, null, userId, actUid, uid, subjectClassCd,type, objectType);
				PageActProxyVO actProxyVO= (PageActProxyVO)proxyVO;
				if(type.equalsIgnoreCase(NEDSSConstants.PHC_INVESTIGATOR))
					actProxyVO.setCurrentInvestigator(uid); 
				if(type.equalsIgnoreCase(NEDSSConstants.PHC_FIELD_FOLLO_UP_SUPVSR))
					actProxyVO.setFieldSupervisor(uid);
				if(type.equalsIgnoreCase(NEDSSConstants.PHC_CASE_SUPRVSR))
					actProxyVO.setCaseSupervisor(uid);
				logger.debug("_setEntitiesForCreate getting providerUid");
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
				Long providerUid=nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
				logger.debug("_setEntitiesForCreate have providerUid");
				if(PropertyUtil.isStdOrHivProgramArea(programAreaCode) && !form.getBusinessObjectType().equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE))
					MessageLogUtil.createMessageLogForParticipant(actProxyVO,  type,  null,providerUid);
				//Retrieve Date Assigned to Investigation (INV110) from PHC
				Timestamp fromTime = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigatorAssignedTime();
				invPartDT.setFromTime(fromTime);
				partsColl.add(invPartDT);
				
				 boolean alreadyInParticipation = findInParticipationCollection (proxyVO, type, subjectClassCd);
	             if(!alreadyInParticipation)
	            	 ((PageActProxyVO)proxyVO).getTheParticipationDTCollection().addAll(partsColl);

				//NBSActEntity
	             if(!objectType.equals(NEDSSConstants.CLASS_CD_OBS)){
	            	 boolean alreadyInActEntity = findInActEntityCollection (proxyVO, type, (new Long(uid)).longValue());
	            	 if(!alreadyInActEntity){
	            	 NbsActEntityDT entityDT = createPamCaseEntity(actUid, uid, versionCtrlNbr, type, userId);
	            	 ((PageActProxyVO)proxyVO).getPageVO().getActEntityDTCollection().add(entityDT);
	            	 }
	             }
				logger.debug("_setEntitiesForCreate - completed");
			}
		} catch (Exception e) {
			logger.error("NEDSSAppException throw for_setEntitiesForCreate:"+ e);
			logger.error("Error while updating _setEntitiesForCreate for subjectClassCd:" +subjectClassCd+" \nfor userId:"+ userId +"\ntype:" + type+"\nsubjectClassCd:"+ subjectClassCd);
			throw new NEDSSAppException("Error thrown:"+e);
		}
	}
    
    /**
     * findInParticipationCollection: this method was created in order to resolve ND-12765. It checks if the participation
     * that is about to be added is already in the participation collection
     * @param pageProxyVO
     * @param parTypeVO
     * @return
     */
    private static boolean findInParticipationCollection (PageProxyVO pageProxyVO, String type, String subjectClassCd){
    	
    	boolean alreadyInParticipation = false;
    	Collection<java.lang.Object> parts = ((PageActProxyVO)pageProxyVO).getTheParticipationDTCollection();
    	
    	Iterator it = parts.iterator();
    	
    	while(it.hasNext() && !alreadyInParticipation){
    		
    		ParticipationDT part = (ParticipationDT)(it.next());
    		
    		if(part!=null && part.getTypeCd()!=null && part.getTypeCd().equalsIgnoreCase(type)
    		&& part.getSubjectClassCd()!=null && part.getSubjectClassCd().equalsIgnoreCase(subjectClassCd))
    			
    			alreadyInParticipation=true;
    	}
    	
    	return alreadyInParticipation;
    }
    
	private static boolean findInActEntityCollection(PageProxyVO pageProxyVO,
			String type, long entityUid) {

		boolean alreadyInActEntity = false;
		Collection<java.lang.Object> actEntities = ((PageActProxyVO) pageProxyVO)
				.getPageVO().getActEntityDTCollection();
		if (actEntities != null && actEntities.size() > 0) {
			Iterator<Object> it = actEntities.iterator();

			while (it.hasNext() && !alreadyInActEntity) {

				NbsActEntityDT part = (NbsActEntityDT) (it.next());

				if (part != null && part.getTypeCd() != null
						&& part.getTypeCd().equalsIgnoreCase(type)
						&& part.getEntityUid() != null
						&& part.getEntityUid().longValue() == entityUid) {

					alreadyInActEntity = true;
					return alreadyInActEntity;
				}
			}
		}
		return alreadyInActEntity;
	}
	
    public static void createActRelationshipForDoc(String sCurrentTask,PageActProxyVO proxyVO, HttpServletRequest request){
    	 try {
			HttpSession session = request.getSession();
			  Object DSDocumentUID = NBSContext.retrieve(session, NBSConstantUtil.DSDocumentUID);
			  ActRelationshipDT actDoc = new ActRelationshipDT();
			  actDoc.setItNew(true);
			  actDoc.setSourceActUid(new Long(DSDocumentUID.toString()));
			  actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
			  actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  actDoc.setTargetActUid(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			  actDoc.setTargetClassCd(NEDSSConstants.CASE);
			  actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
			  actDoc.setTypeCd(NEDSSConstants.DocToPHC);
			  if(proxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection()==null){
				  Collection<Object>  coll = new ArrayList<Object> ();
				  coll.add(actDoc);
				proxyVO.getPublicHealthCaseVO().setTheActRelationshipDTCollection(coll);
			  }
			  else
				  proxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection().add(actDoc);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException throw createActRelationshipForDoc:"+ e);
			logger.error("NumberFormatException throw createActRelationshipForDoc for sCurrentTask:"+sCurrentTask );
		}
    }
    
	/**
	 * createActRelationshipToCaseForGeneric
	 * @param proxyVO
	 * @param typeCd i.e. IXS for Interview
	 * @param request
	 */
    public static void createActRelationshipToCaseForGeneric(PageProxyVO proxyVO, String typeCd, HttpServletRequest request){
    	try {
			HttpSession session = request.getSession();
			String DSInvestigationUid = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
			ActRelationshipDT actRelDT = new ActRelationshipDT();
			actRelDT.setItNew(true);
			actRelDT.setSourceActUid(new Long(-1));
			if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(typeCd)){
				actRelDT.setSourceClassCd(NEDSSConstants.INTERVENTION_CLASS_CODE);
				actRelDT.setTypeCd(NEDSSConstants.AR_TYPE_CODE);
			}else{
				actRelDT.setSourceClassCd(NEDSSConstants.OBSERVATION_CLASS_CODE);
				actRelDT.setTypeCd(typeCd);
				actRelDT.setAddReasonCd(NEDSSConstants.IXS_ADD_REASON_CD);
			}
			
			actRelDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			actRelDT.setTargetActUid(new Long(DSInvestigationUid));
			actRelDT.setTargetClassCd(NEDSSConstants.CASE);
			actRelDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			
			if(((PageActProxyVO)proxyVO).getTheActRelationshipDTCollection()==null){
				Collection<Object>  coll = new ArrayList<Object> ();
				coll.add(actRelDT);
				((PageActProxyVO)proxyVO).setTheActRelationshipDTCollection(coll);
			} else {
				((PageActProxyVO)proxyVO).getTheActRelationshipDTCollection().add(actRelDT);
			}
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException throw createActRelationshipToCaseForGeneric:"+ e);
			logger.error("NumberFormatException throw createActRelationshipToCaseForGeneric for typeCd:"+typeCd );
		}
        
    }     
    
	/**
	 * createActRelationshipToCaseForGenericCoinfections
	 * @param proxyVO
	 * @param typeCd i.e. IXS for Interview
	 * @param request
	 */
    public static void createActRelationshipToCaseForGenericCoinfections(PageProxyVO proxyVO, String typeCd, String coinfectionListStr, HttpServletRequest request){
    		ArrayList<Long> coinfList = parseCoinfectionList(coinfectionListStr);
    		
			HttpSession session = request.getSession();
			String DSInvestigationUid = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
			Long sourceInvestigationUid = new Long(DSInvestigationUid);
    		if (coinfList != null && !coinfList.isEmpty()) {
    			Iterator coinfIter = coinfList.iterator();
    			while (coinfIter.hasNext()) {
    				Long investigationUid = (Long) coinfIter.next();
    				if (sourceInvestigationUid.compareTo(investigationUid) == 0) //skip originator
    					continue;
    				try {
    					ActRelationshipDT actRelDT = new ActRelationshipDT();
    					actRelDT.setItNew(true);
    					actRelDT.setSourceActUid(new Long(-1));
    					actRelDT.setSourceClassCd(NEDSSConstants.OBSERVATION_CLASS_CODE);
    					actRelDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    					actRelDT.setTargetActUid(investigationUid);
    					actRelDT.setTargetClassCd(NEDSSConstants.CASE);
    					actRelDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
    					actRelDT.setTypeCd(typeCd);
    					if(((PageActProxyVO)proxyVO).getTheActRelationshipDTCollection()==null){
    						Collection<Object>  coll = new ArrayList<Object> ();
    						coll.add(actRelDT);
    						((PageActProxyVO)proxyVO).setTheActRelationshipDTCollection(coll);
    					} else {
    						((PageActProxyVO)proxyVO).getTheActRelationshipDTCollection().add(actRelDT);
    					}
    				} catch (NumberFormatException e) {
    					logger.error("NumberFormatException throw createActRelationshipToCaseForGeneric:"+ e);
    					logger.error("NumberFormatException throw createActRelationshipToCaseForGeneric for typeCd:"+typeCd );
    				} //try	
    			}  //has next
    		} //list not empty
    }     
    
    
	public static String getInvFormCd(HttpServletRequest req, PageForm form) {
		HttpSession session = req.getSession();
		String investigationFormCd = null;
		try {
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", conditionCd);
			investigationFormCd = programAreaVO.getInvestigationFormCd();

		} catch (Exception e) {

			try {
				investigationFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);				
			} catch (Exception e1) {
				logger.info("INV FORM CD is not present in Context: " + e.toString());
			}
		} 
		if(investigationFormCd == null) 
			investigationFormCd =  form.getPageFormCd();
		else
			form.setPageFormCd(investigationFormCd);
		//Log
		if(investigationFormCd == null || (investigationFormCd != null && investigationFormCd.equals(""))) {
			logger.error("Error while retrieving investigationFormCd from Context / PamForm: ");
		}
		return investigationFormCd;

	}   
	//TODO: replace with generic function
	public void  setRepeatingQuestionsBatch(PageForm form,PageActProxyVO proxyVO,String pageFormCd,String userId, Long providerUid) throws NEDSSAppException{
		Map<Object, ArrayList<BatchEntry>> batchmap = form.getBatchEntryMap();
		Iterator<Entry<Object, ArrayList<BatchEntry>>> it = batchmap.entrySet().iterator();
		Map<Object,Object> pamRepeatingAnswerDTMap = new HashMap<Object,Object>();
		ArrayList<NbsAnswerDT> dtList = new ArrayList<NbsAnswerDT>();
		Map.Entry<Object, ArrayList<BatchEntry>> entityIDPairs = null;
		String hasHIVPermissions = (String)form.getSecurityMap().get(NEDSSConstants.HAS_HIV_PERMISSIONS);
		boolean exists = false;
		//gst: questionMap is already in scope...4/12/2017 5.1.0.1
		//questionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(pageFormCd);

		NbsQuestionMetadata fieldSupervisorNotesMetaData=null;
		NbsQuestionMetadata caseSupervisorNotesMetaData=null;
		while(it.hasNext()){			
			  Map.Entry<Object, ArrayList<BatchEntry>> pairs = (Map.Entry<Object, ArrayList<BatchEntry>>)it.next();
			  if(pairs.getKey().equals(PageConstants.ENTITY_ID_SUBSECTION)){
				  entityIDPairs=pairs;
				  continue;
			  }
			  //
			  if(pairs.getKey().equals(PageConstants.RESULTED_TEST_BATCH_CONTAINER))
				  continue;
			  ArrayList<BatchEntry> blist = pairs.getValue();
			  
			  int n = blist.size();
			    for (int pass=1; pass < n; pass++) {  // count how many times
			        // This next loop becomes shorter and shorter
			        for (int i=0; i < n-pass; i++) {
			            if (blist.get(i).getAnswerGrpSeqID() > blist.get(i+1).getAnswerGrpSeqID()) {
			            	 BatchEntry be1 = blist.get(i);
			            	 BatchEntry be2 = blist.get(i+1);
			                // exchange elements
			               // int temp = x[i]; 
			            	blist.remove(i);			            	
			                blist.add(i, be2); 
			                blist.remove(i+1);
			                blist.add(i+1, be1); 
			                //blist.get(i+1) = be;
			            }
			        }
			    }			  
			  for(int i=0;i<blist.size();i++) {
				  BatchEntry be = blist.get(i);			
				  
				  Map<String, String> answerMap = be.getAnswerMap();
				  Iterator<Entry<String, String>> itt = answerMap.entrySet().iterator();
					while(itt.hasNext()){	
						  String ismulti=""; 
						  Map.Entry<String, String> pairs1 = (Map.Entry<String, String>)itt.next();
						  NbsQuestionMetadata metaData = (NbsQuestionMetadata)getQuestionMap().get(pairs1.getKey());
						  
						  if (metaData == null) continue; //no related nbs_question 
						  Long uiComponentType = metaData.getNbsUiComponentUid();
						  String val = pairs1.getValue();
						  //check if this is a rolling note and if it is get the associated user and date to put into the answer txt
						  if (val != null && !val.isEmpty() && uiComponentType != null && uiComponentType.equals(PageMetaConstants.ROLLINGNOTE)){

							if( metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)){
								fieldSupervisorNotesMetaData = metaData;	
							}else
							if( metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.CASE_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)){
								caseSupervisorNotesMetaData = metaData;	
							}
							  
							  String newVal;
							  String dateVal="";
							  String userVal="";
							  String theQuesId = pairs1.getKey();
							  Iterator<Entry<String, String>> ansIt = answerMap.entrySet().iterator();
								while(ansIt.hasNext()){
									Map.Entry<String, String> rnPairs = (Map.Entry<String, String>)ansIt.next();
									if (rnPairs.getKey().equalsIgnoreCase(theQuesId + "Date"))
										dateVal = rnPairs.getValue();
									else if (rnPairs.getKey().equalsIgnoreCase(theQuesId + "User"))
										userVal = rnPairs.getValue();
								}
								if (dateVal == null || dateVal.isEmpty()) {
									logger.error("Error in PageCreateHelper.setRepeatingQuestionsBatch() - dateVal missing?");
									continue;
								}
								if (userVal == null || userVal.isEmpty()) {
									logger.error("Error in PageCreateHelper.setRepeatingQuestionsBatch() - userVal missing?");
									continue;
								}
							
								NbsAnswerDT dt = new NbsAnswerDT();
								dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
								dt.setAnswerGroupSeqNbr(i+1);
								dt.setSeqNbr(new Integer(0));
								dt.setAddTime(new Timestamp(new Date().getTime()));						
								dt.setAddUserId(Long.valueOf(userId));
								if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
									dt.setLastChgUserId(Long.valueOf(userId));									
								dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());
								dt.setAnswerTxt(userVal + "~" + dateVal + "~~" + val);
								dtList.add(dt);
									
						  } //end of rolling note
						  else if(val != null && val.indexOf("||") != -1 && uiComponentType != null && uiComponentType.equals(PageMetaConstants.MULTISELECT )){
							    int seq=1;
							    ismulti ="yes";
							  String  mulVal ="";
								val = val.substring(0, val.length()-2);								
								 while(val.indexOf("||") != -1){
									 NbsAnswerDT dt = new NbsAnswerDT();
									  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
									  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
										String val1 =  val.substring(0, val.indexOf("||"));									

										val = val.substring(val.indexOf("||")+2);
										if(val1.indexOf("$MulOth$")!= -1){
											 dt.setAnswerTxt(val1.substring(0,val1.indexOf("$$"))+"^"+val1.substring(val1.indexOf("$MulOth$")+8,val1.indexOf("#MulOth#")));
										}else{

											 dt.setAnswerTxt(val1.substring(0,val1.indexOf("$$")));
										}
										 
										dt.setAnswerGroupSeqNbr(i+1);							  
										  dt.setSeqNbr(seq);
											dt.setAddTime(new Timestamp(new Date().getTime()));						
											dt.setAddUserId(Long.valueOf(userId));
											if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
												dt.setLastChgUserId(Long.valueOf(userId));									
											dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											seq =  seq+1;		
										  dtList.add(dt);										
										}	
	
								 NbsAnswerDT dt = new NbsAnswerDT();
								  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
								  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
								  if(val.indexOf("$MulOth$")!= -1){
										 dt.setAnswerTxt(val.substring(0,val.indexOf("$$"))+"^"+val.substring(val.indexOf("$MulOth$")+8,val.indexOf("#MulOth#")));
									}else{

										 dt.setAnswerTxt(val.substring(0,val.indexOf("$$")));
									}
								  //dt.setAnswerTxt(val.substring(0,val.indexOf("$$")));
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(seq);
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
									seq =  seq+1;		
								  dtList.add(dt);
								
			      		 }else if(val != null && val.indexOf("$$") != -1 && val.indexOf(":") != -1 && val.indexOf("OTH") != -1){
			      			 NbsAnswerDT dt = new NbsAnswerDT();
							 // NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			            		val = val.substring(0,val.indexOf("$$")) +"^"+ val.substring(val.indexOf(":")+1,val.length());    		
				      		    dt.setAnswerTxt(val);
				      		  // dt.setAnswerTxt(pairs1.getValue());
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(new Integer(0));
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											
								  dtList.add(dt);
					    }else if(val != null && val.indexOf("$sn$") != -1){
					    	 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
				      		   val = val.substring(0,val.indexOf("$sn$"))+"^"+val.substring(val.indexOf("$sn$")+4,val.indexOf("$val$"));
					      		 dt.setAnswerTxt(val);
					      		 // dt.setAnswerTxt(pairs1.getValue());
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(new Integer(0));
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											
								  dtList.add(dt);
					     }else if(val != null && val.indexOf("$$") != -1  ){
					    	 if(val.indexOf("$$") != 0){
					    	 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			      		   val = val.substring(0,val.indexOf("$$"));
			      		 dt.setAnswerTxt(val);
			      		 // dt.setAnswerTxt(pairs1.getValue());
						  dt.setAnswerGroupSeqNbr(i+1);							  
						  dt.setSeqNbr(new Integer(0));
							dt.setAddTime(new Timestamp(new Date().getTime()));						
							dt.setAddUserId(Long.valueOf(userId));
							if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
								dt.setLastChgUserId(Long.valueOf(userId));									
							dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
									
						       dtList.add(dt);
					    	 }
			      		 }else if(val != null && !val.equals("")){
			      			 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			      			dt.setAnswerTxt(val);
			      			 // dt.setAnswerTxt(pairs1.getValue());
							  dt.setAnswerGroupSeqNbr(i+1);							  
							  dt.setSeqNbr(new Integer(0));
								dt.setAddTime(new Timestamp(new Date().getTime()));						
								dt.setAddUserId(Long.valueOf(userId));
								if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
									dt.setLastChgUserId(Long.valueOf(userId));									
								dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
										
							  dtList.add(dt);
			      		 }
						  
						  
												  
					}
					}	  
		}
		ArrayList<NbsAnswerDT> list = new ArrayList<NbsAnswerDT>();
		for(int i=0;i<dtList.size();i++)
		{
			ArrayList<NbsAnswerDT> addlist = new ArrayList<NbsAnswerDT>();
			String QID = dtList.get(i).getNbsQuestionUid().toString();
			for(int count=0;count<list.size();count++){			   	
				if(QID.equalsIgnoreCase(list.get(count).getNbsQuestionUid().toString())){
					count++;
					exists = true;
					break;
					
				 }
			}				
			if(!exists){	
			for(int j=0;j<dtList.size();j++)
				{
					
					if(QID.equalsIgnoreCase(dtList.get(j).getNbsQuestionUid().toString())){
						list.add(dtList.get(j));
						addlist.add(dtList.get(j));	
						
					}
					
				}
				
				pamRepeatingAnswerDTMap.put(QID, addlist);	
			 }
			exists=false;
			}	
		//Collection<Object> supervisorComments =new ArrayList<Object>();
		Collection<Object> fieldSupervisorComments =new ArrayList<Object>();
		Collection<Object> caseSupervisorComments =new ArrayList<Object>();
		if(fieldSupervisorNotesMetaData!=null && fieldSupervisorNotesMetaData.getNbsQuestionUid()!=null){
		
			fieldSupervisorComments=(ArrayList<Object>)pamRepeatingAnswerDTMap.get(fieldSupervisorNotesMetaData.getNbsQuestionUid().toString());
			if (fieldSupervisorComments != null)
				form.getQuestionMetadataMap().put(fieldSupervisorNotesMetaData, fieldSupervisorComments);
		
		}
		if(caseSupervisorNotesMetaData!=null && caseSupervisorNotesMetaData.getNbsQuestionUid()!=null){
			caseSupervisorComments=(ArrayList<Object>)pamRepeatingAnswerDTMap.get(caseSupervisorNotesMetaData.getNbsQuestionUid().toString());
			if (caseSupervisorComments != null)
				form.getQuestionMetadataMap().put(caseSupervisorNotesMetaData, caseSupervisorComments);
		
		}
		proxyVO.getPageVO().setPageRepeatingAnswerDTMap(pamRepeatingAnswerDTMap);
		// Handle Entity ID repeating block
		if(proxyVO.getThePersonVOCollection()!=null){
			Iterator<Object> ite = proxyVO.getThePersonVOCollection().iterator();
			while (ite.hasNext()) {
				PersonVO person = (PersonVO) ite.next();
				if (person.getThePersonDT().getCd().equals(NEDSSConstants.PAT)
						&& (person.getThePersonDT().getPersonUid() == null || person.getThePersonDT().getPersonParentUid() == null || person
								.getThePersonDT().getPersonParentUid().longValue() != person
								.getThePersonDT().getPersonUid().longValue())) {
					PersonVO oldPersonVO = null;
					PageLoadUtil pageLoadUtil = new PageLoadUtil();
					if(form.getActionMode()!=null && form.getActionMode().toUpperCase().contains((NEDSSConstants.EDIT).toUpperCase())){
						if(form.getBusinessObjectType()!=null && (form.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE) || form.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.CASE)))
							oldPersonVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());
						else if(form.getBusinessObjectType()!=null && form.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE))
							oldPersonVO = pageLoadUtil.getGenericPersonVO(NEDSSConstants.SUBJECT_OF_VACCINE, form.getPageClientVO().getOldPageProxyVO());
						else if(form.getBusinessObjectType()!=null && form.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE))
							oldPersonVO = pageLoadUtil.getGenericPersonVO(NEDSSConstants.PAR110_TYP_CD, form.getPageClientVO().getOldPageProxyVO());
					}
					setIDBatchToEntityIDCollection(person, oldPersonVO,entityIDPairs, hasHIVPermissions);
					break;
				}
			}
		}
	}
	
	
	public static void setIDBatchToEntityIDCollection(PersonVO person, PersonVO oldPersonVO,
			Map.Entry<Object, ArrayList<BatchEntry>> entityIDPairs,String hasHIVPermissions) throws NEDSSAppException {
		
		try{
			if(entityIDPairs==null)
				return;
		ArrayList<BatchEntry> beList = entityIDPairs.getValue();
		Iterator<BatchEntry> itrBatchEntry = beList.iterator();
		int maxSeqNbr = 0;
		Map<String, Object> idMap = new HashMap<String, Object>();
		Collection<Object> idList = person.getTheEntityIdDTCollection();
		Timestamp currentDate = new Timestamp(new Date().getTime());
		if (idList == null) {
			idList = new ArrayList<Object>();
			person.setTheEntityIdDTCollection(idList);
		}
		else{
			Iterator<Object> ite = person.getTheEntityIdDTCollection().iterator();
			while(ite.hasNext()){
				EntityIdDT id = (EntityIdDT)ite.next();
				if(id.getEntityIdSeq()>maxSeqNbr)
					maxSeqNbr = id.getEntityIdSeq();
			}
		}
		
		if(oldPersonVO!=null && oldPersonVO.getTheEntityIdDTCollection()!=null && oldPersonVO.getTheEntityIdDTCollection().size()>0){
			Iterator<Object> ite = oldPersonVO.getTheEntityIdDTCollection().iterator();
			while(ite.hasNext()){
				EntityIdDT id = (EntityIdDT)ite.next();
				idMap.put(id.getEntityUid().longValue()+""+id.getEntityIdSeq(), id);
				if(id.getEntityIdSeq()>maxSeqNbr)
					maxSeqNbr = id.getEntityIdSeq();
			}
		}
		while (itrBatchEntry.hasNext()) {
			BatchEntry idAnswer = (BatchEntry) itrBatchEntry.next();
			EntityIdDT entityIdDt = null;
			
			//Check for updated IDs
			if(idAnswer.getLocalId()!=null && idMap.containsKey(idAnswer.getLocalId())){
				entityIdDt = (EntityIdDT)idMap.get(idAnswer.getLocalId());
				entityIdDt.setItDirty(true);
				entityIdDt.setItNew(false);
				entityIdDt.setItDelete(false);
				entityIdDt.setLastChgTime(currentDate);
				idMap.remove(idAnswer.getLocalId());
			}
			else{//New IDs
				entityIdDt = new EntityIdDT();
				entityIdDt.setEntityIdSeq(++maxSeqNbr);
				entityIdDt.setAddTime(currentDate);
				entityIdDt.setLastChgTime(currentDate);
				entityIdDt.setRecordStatusTime(currentDate);
				entityIdDt.setStatusTime(currentDate);
				entityIdDt.setEntityUid(person.getThePersonDT().getPersonUid());
				entityIdDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				entityIdDt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				entityIdDt.setItNew(true);
				entityIdDt.setItDirty(false);
			}
			Map<String, String> answerMap = idAnswer.getAnswerMap();
			String asOfDate = answerMap.get(PageConstants.PATIENT_ENTITY_ID_AS_OF);
			if(asOfDate!=null)
				entityIdDt.setAsOfDate(StringUtils.stringToStrutsTimestamp(asOfDate));
			else if (person.getThePersonDT().getAsOfDateAdmin() != null)
				entityIdDt.setAsOfDate(person.getThePersonDT()
						.getAsOfDateAdmin());
			else
				entityIdDt.setAsOfDate(currentDate);
			String val = answerMap.get(PageConstants.PATIENT_ENTITY_ID_TYPE);
			if (val != null && val.indexOf("$$") != -1
					&& val.indexOf(":") != -1 && val.indexOf("OTH") != -1) {
				String value1 = val.substring(0, val.indexOf("$$"));
				String value2 = val.substring(val.indexOf(":") + 1,
						val.length());
				entityIdDt.setTypeCd(value1);
				entityIdDt.setTypeDescTxt(value2);
			} else if (val != null && val.indexOf("$$") != -1) {
				entityIdDt.setTypeCd(val.substring(0, val.indexOf("$$")));
				entityIdDt.setTypeDescTxt(val.substring(val.indexOf("$$") + 2,
						val.length()));
			}

			String aVal = answerMap
					.get(PageConstants.PATIENT_ENTITY_ID_ASSIGNING_AUTHORITY);
			if (aVal != null && !aVal.equals("$$") && aVal.indexOf("$$") != -1){
				entityIdDt.setAssigningAuthorityCd(aVal.substring(0,
						aVal.indexOf("$$")));
				entityIdDt.setAssigningAuthorityDescTxt(aVal.substring(val.indexOf("$$") + 2,
						aVal.length()));
			}
			entityIdDt.setRootExtensionTxt(answerMap
					.get(PageConstants.PATIENT_ENTITY_ID_VALUE));
			idList.add(entityIdDt);
		} // hasNext
		PersonUtil.setAssigningAuthorityforIds(idList, NEDSSConstants.EI_AUTH);
		//for IDS to be deleted.
		Iterator<Object> remainingIds = idMap.values().iterator();
		while (remainingIds.hasNext()) {
			EntityIdDT id = (EntityIdDT) remainingIds.next();
			if (id.getRecordStatusCd() != null
					&& id.getRecordStatusCd().equals(
							NEDSSConstants.RECORD_STATUS_ACTIVE)
					&& id.getTypeCd() != null && !id.getTypeCd().equals("SS")
					&& !id.getTypeCd().equals(NEDSSConstants.PARTNER_SERVICES_ID)) {
				id.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				id.setLastChgTime(currentDate);
				id.setRecordStatusTime(currentDate);
				id.setStatusTime(currentDate);
				id.setItDelete(true);
				id.setItDirty(false);
				id.setItNew(false);
				idList.add(id);
			}

			else if (id.getTypeCd().equals(NEDSSConstants.PARTNER_SERVICES_ID) && hasHIVPermissions != null
					&& hasHIVPermissions.equals("T")) {
				id.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				id.setLastChgTime(currentDate);
				id.setRecordStatusTime(currentDate);
				id.setStatusTime(currentDate);
				id.setItDelete(true);
				id.setItDirty(false);
				id.setItNew(false);
				idList.add(id);
			}

		}
		} catch (Exception ex) {
			throw new NEDSSAppException(
					"Exception while creating entity IDs collection from repeating block in patient :"
							+ ex.getMessage());
		}
		}
		
	public Map<Object,Object>  setRepeatingQuestionsBatch(BaseForm form,String pageFormCd,String userId, Long providerUid) throws NEDSSAppException{
		Map<Object, ArrayList<BatchEntry>> batchmap = form.getBatchEntryMap();
		Iterator<Entry<Object, ArrayList<BatchEntry>>> it = batchmap.entrySet().iterator();
		Map<Object,Object> pamRepeatingAnswerDTMap = new HashMap<Object,Object>();
		ArrayList<NbsAnswerDT> dtList = new ArrayList<NbsAnswerDT>();
		boolean exists = false;
		Map.Entry<Object, ArrayList<BatchEntry>> entityIDPairs = null;
		String hasHIVPermissions = (String)form.getSecurityMap().get(NEDSSConstants.HAS_HIV_PERMISSIONS);
		//gst: questionMap is already in scope...4/12/2017 5.1.0.1
		//questionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(pageFormCd);
		//NbsQuestionMetadata supervisorNotesMetaData=null;
		NbsQuestionMetadata fieldSupervisorNotesMetaData=null;
		NbsQuestionMetadata caseSupervisorNotesMetaData=null;
		while(it.hasNext()){			
			  Map.Entry<Object, ArrayList<BatchEntry>> pairs = (Map.Entry<Object, ArrayList<BatchEntry>>)it.next();
			  ArrayList<BatchEntry> blist = pairs.getValue();
			  if(pairs.getKey().equals(PageConstants.ENTITY_ID_SUBSECTION)){
				  entityIDPairs=pairs;
				  continue;
			  }else if(pairs.getKey().equals(PageConstants.RESULTED_TEST_BATCH_CONTAINER)){
				  //ignore!!! This code is specific to lab and is taken care of in the lab section
				  continue;
			  }
			  int n = blist.size();
			    for (int pass=1; pass < n; pass++) {  // count how many times
			        // This next loop becomes shorter and shorter
			        for (int i=0; i < n-pass; i++) {
			            if (blist.get(i).getAnswerGrpSeqID() > blist.get(i+1).getAnswerGrpSeqID()) {
			            	 BatchEntry be1 = blist.get(i);
			            	 BatchEntry be2 = blist.get(i+1);
			                // exchange elements
			               // int temp = x[i]; 
			            	blist.remove(i);			            	
			                blist.add(i, be2); 
			                blist.remove(i+1);
			                blist.add(i+1, be1); 
			                //blist.get(i+1) = be;
			            }
			        }
			    }			  
			  for(int i=0;i<blist.size();i++) {	
				  BatchEntry be = blist.get(i);			
				  
				  Map<String, String> answerMap = be.getAnswerMap();
				  Iterator<Entry<String, String>> itt = answerMap.entrySet().iterator();
					while(itt.hasNext()){	
						  String ismulti=""; 
						  Map.Entry<String, String> pairs1 = (Map.Entry<String, String>)itt.next();
						  NbsQuestionMetadata metaData = (NbsQuestionMetadata)getQuestionMap().get(pairs1.getKey());
						  
						  
							  
						  
						  if (metaData == null) continue; //no related nbs_question 
						  Long uiComponentType = metaData.getNbsUiComponentUid();
						  String val = pairs1.getValue();
						  //check if this is a rolling note and if it is get the associated user and date to put into the answer txt
						  if (val != null && !val.isEmpty() && uiComponentType != null && uiComponentType.equals(PageMetaConstants.ROLLINGNOTE)){

							if( metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.FIELD_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)){
								fieldSupervisorNotesMetaData = metaData;
									
							}else
							if( metaData.getQuestionIdentifier().equalsIgnoreCase(MessageConstants.CASE_SUPERVISOR_REVIEW_COMMENTS_QUESTION_IDENTIFIER)){
								caseSupervisorNotesMetaData = metaData;
									
							}
							  
							  String newVal;
							  String dateVal="";
							  String userVal="";
							  String theQuesId = pairs1.getKey();
							  Iterator<Entry<String, String>> ansIt = answerMap.entrySet().iterator();
								while(ansIt.hasNext()){
									Map.Entry<String, String> rnPairs = (Map.Entry<String, String>)ansIt.next();
									if (rnPairs.getKey().equalsIgnoreCase(theQuesId + "Date"))
										dateVal = rnPairs.getValue();
									else if (rnPairs.getKey().equalsIgnoreCase(theQuesId + "User"))
										userVal = rnPairs.getValue();
								}
								
								if (dateVal == null || dateVal.isEmpty()) {
									logger.error("Error in PageCreateHelper.setRepeatingQuestionsBatch(baseForm) - dateVal missing?");
									continue;
								}
								if (userVal == null || userVal.isEmpty()) {
									logger.error("Error in PageCreateHelper.setRepeatingQuestionsBatch(baseForm) - userVal missing?");
									continue;
								}
								NbsAnswerDT dt = new NbsAnswerDT();
								dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
								dt.setAnswerGroupSeqNbr(i+1);
								dt.setSeqNbr(new Integer(0));
								dt.setAddTime(new Timestamp(new Date().getTime()));						
								dt.setAddUserId(Long.valueOf(userId));
								if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
									dt.setLastChgUserId(Long.valueOf(userId));									
								dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());
								dt.setAnswerTxt(userVal + "~" + dateVal + "~~" + val);
								dtList.add(dt);
								
								
								
						  } //end of rolling note
						  else if(val != null && val.indexOf("||") != -1 && uiComponentType != null && uiComponentType.equals(PageMetaConstants.MULTISELECT )){
							    int seq=1;
							    ismulti ="yes";
							  String  mulVal ="";
								val = val.substring(0, val.length()-2);								
								 while(val.indexOf("||") != -1){
									 NbsAnswerDT dt = new NbsAnswerDT();
									  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
									  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
										String val1 =  val.substring(0, val.indexOf("||"));									

										val = val.substring(val.indexOf("||")+2);
										if(val1.indexOf("$MulOth$")!= -1){
											 dt.setAnswerTxt(val1.substring(0,val1.indexOf("$$"))+"^"+val1.substring(val1.indexOf("$MulOth$")+8,val1.indexOf("#MulOth#")));
										}else{

											 dt.setAnswerTxt(val1.substring(0,val1.indexOf("$$")));
										}
										 
										dt.setAnswerGroupSeqNbr(i+1);							  
										  dt.setSeqNbr(seq);
											dt.setAddTime(new Timestamp(new Date().getTime()));						
											dt.setAddUserId(Long.valueOf(userId));
											if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
												dt.setLastChgUserId(Long.valueOf(userId));									
											dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											seq =  seq+1;		
										  dtList.add(dt);										
										}	
	
								 NbsAnswerDT dt = new NbsAnswerDT();
								  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
								  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
								  if(val.indexOf("$MulOth$")!= -1){
										 dt.setAnswerTxt(val.substring(0,val.indexOf("$$"))+"^"+val.substring(val.indexOf("$MulOth$")+8,val.indexOf("#MulOth#")));
									}else{

										 dt.setAnswerTxt(val.substring(0,val.indexOf("$$")));
									}
								  //dt.setAnswerTxt(val.substring(0,val.indexOf("$$")));
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(seq);
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
									seq =  seq+1;		
								  dtList.add(dt);
								
			      		 }else if(val != null && val.indexOf("$$") != -1 && val.indexOf(":") != -1  && val.indexOf("OTH") != -1){
			      			 NbsAnswerDT dt = new NbsAnswerDT();
							 // NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			            		val = val.substring(0,val.indexOf("$$")) +"^"+ val.substring(val.indexOf(":")+1,val.length());    		
				      		    dt.setAnswerTxt(val);
				      		  // dt.setAnswerTxt(pairs1.getValue());
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(new Integer(0));
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											
								  dtList.add(dt);
					    }else if(val != null && val.indexOf("$sn$") != -1){
					    	 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
				      		   val = val.substring(0,val.indexOf("$sn$"))+"^"+val.substring(val.indexOf("$sn$")+4,val.indexOf("$val$"));
					      		 dt.setAnswerTxt(val);
					      		 // dt.setAnswerTxt(pairs1.getValue());
								  dt.setAnswerGroupSeqNbr(i+1);							  
								  dt.setSeqNbr(new Integer(0));
									dt.setAddTime(new Timestamp(new Date().getTime()));						
									dt.setAddUserId(Long.valueOf(userId));
									if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
										dt.setLastChgUserId(Long.valueOf(userId));									
									dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
											
								  dtList.add(dt);
					     }else if(val != null && val.indexOf("$$") != -1  ){
					    	 if(val.indexOf("$$") != 0){
					    	 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			      		   val = val.substring(0,val.indexOf("$$"));
			      		 dt.setAnswerTxt(val);
			      		 // dt.setAnswerTxt(pairs1.getValue());
						  dt.setAnswerGroupSeqNbr(i+1);							  
						  dt.setSeqNbr(new Integer(0));
							dt.setAddTime(new Timestamp(new Date().getTime()));						
							dt.setAddUserId(Long.valueOf(userId));
							if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
								dt.setLastChgUserId(Long.valueOf(userId));									
							dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
									
						       dtList.add(dt);
					    	 }
			      		 }else if(val != null && !val.equals("")){
			      			 NbsAnswerDT dt = new NbsAnswerDT();
							  //NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(pairs1.getKey());
							  dt.setNbsQuestionUid(metaData.getNbsQuestionUid());
			      			dt.setAnswerTxt(val);
			      			 // dt.setAnswerTxt(pairs1.getValue());
							  dt.setAnswerGroupSeqNbr(i+1);							  
							  dt.setSeqNbr(new Integer(0));
								dt.setAddTime(new Timestamp(new Date().getTime()));						
								dt.setAddUserId(Long.valueOf(userId));
								if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
									dt.setLastChgUserId(Long.valueOf(userId));									
								dt.setNbsQuestionVersionCtrlNbr(metaData.getQuestionVersionNbr());				
										
							  dtList.add(dt);
			      		 }
						  
						  
												  
					}
					}	  
		}
		ArrayList<NbsAnswerDT> list = new ArrayList<NbsAnswerDT>();
		for(int i=0;i<dtList.size();i++)
		{
			ArrayList<NbsAnswerDT> addlist = new ArrayList<NbsAnswerDT>();
			String QID = dtList.get(i).getNbsQuestionUid().toString();
			for(int count=0;count<list.size();count++){			   	
				if(QID.equalsIgnoreCase(list.get(count).getNbsQuestionUid().toString())){
					count++;
					exists = true;
					break;
					
				 }
			}				
			if(!exists){	
			for(int j=0;j<dtList.size();j++)
				{
					
					if(QID.equalsIgnoreCase(dtList.get(j).getNbsQuestionUid().toString())){
						list.add(dtList.get(j));
						addlist.add(dtList.get(j));	
						
					}
					
				}
				
				pamRepeatingAnswerDTMap.put(QID, addlist);	
			 }
			exists=false;
			}	
		//Collection<Object> supervisorComments =new ArrayList<Object>();
		Collection<Object> fieldSupervisorComments =new ArrayList<Object>();
		Collection<Object> caseSupervisorComments =new ArrayList<Object>();
		if(fieldSupervisorNotesMetaData!=null && fieldSupervisorNotesMetaData.getNbsQuestionUid()!=null){
		
			fieldSupervisorComments=(ArrayList<Object>)pamRepeatingAnswerDTMap.get(fieldSupervisorNotesMetaData.getNbsQuestionUid().toString());
			if (fieldSupervisorComments != null)
				form.getQuestionMetadataMap().put(fieldSupervisorNotesMetaData, fieldSupervisorComments);
		
		}
		if(caseSupervisorNotesMetaData!=null && caseSupervisorNotesMetaData.getNbsQuestionUid()!=null){
			caseSupervisorComments=(ArrayList<Object>)pamRepeatingAnswerDTMap.get(caseSupervisorNotesMetaData.getNbsQuestionUid().toString());
			if(caseSupervisorComments!=null)
				form.getQuestionMetadataMap().put(caseSupervisorNotesMetaData, caseSupervisorComments);
		
		}
		
		// Handle Entity ID repeating block
		PersonVO personVO = ((CTContactForm) form).getcTContactProxyVO()
				.getContactPersonVO();
		PersonVO oldPersonVO = null;
		if(((CTContactForm) form).getcTContactClientVO()
				.getOldCtContactProxyVO()!=null)
			oldPersonVO = ((CTContactForm) form).getcTContactClientVO()
				.getOldCtContactProxyVO().getContactPersonVO();
		setIDBatchToEntityIDCollection(personVO, oldPersonVO, entityIDPairs,hasHIVPermissions);
		
		return pamRepeatingAnswerDTMap;		
	}
	

	/**
	 * 	On Edit Submit, NBS Answers should be marked as itNew, itDirty or itDelete based on the changes...
	 * @param form
	 * @param proxyVO
	 */
	private static void updateNbsRepeatingAnswersForDirty(PageForm form, PageProxyVO proxyVO ) {

		Map<Object,Object> oldRepeatingAnswers = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO().getPageRepeatingAnswerDTMap();
		Map<Object,Object> newRepeatingAnswers = ((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap();
		checkForUpdatedSupOrCaseSupComments(oldRepeatingAnswers, newRepeatingAnswers, form);
		//Map<Object, Object> messageLogMap = new HashMap<Object, Object> ();		
		//Iterate through the newAnswers and mark it accordingly
		//1. If present in new and not present in old - mark it NEW
		//2. If present in both - mark it DIRTY
		//3. If present in old and not present in new - mark it DELETE
		
		Iterator<Object> iter = newRepeatingAnswers.keySet().iterator();
		while(iter.hasNext()) {
			String qId = (String) iter.next();
			Object obj = newRepeatingAnswers.get(qId);
				
			if(obj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) obj;
				
				checkAnswerListForDirty(oldRepeatingAnswers, answerList,"Repeating");
			} else {
				NbsAnswerDT dt = (NbsAnswerDT) obj;
				Long qUid = (Long) dt.getNbsQuestionUid();
				if(oldRepeatingAnswers.get(qUid) == null) {
					dt.setItNew(true);
					dt.setItDirty(false);
					dt.setItDelete(false);
				} else {
					NbsAnswerDT oldDT = (NbsAnswerDT) oldRepeatingAnswers.get(qUid);
					dt.setItDirty(true);
					dt.setItNew(false);
					dt.setItDelete(false);
					dt.setNbsAnswerUid(oldDT.getNbsAnswerUid());
					//remove it from oldMap so that the leftovers in oldMap are DELETE candidates
					oldRepeatingAnswers.remove(qUid);
				}
			}
		}
		//For the leftovers in the oldAnswers, mark them all to DELETE
		Iterator<Object> iter1 = oldRepeatingAnswers.keySet().iterator();
		while(iter1.hasNext()) {
			Long qUid = (Long) iter1.next();
			Object oldObj = oldRepeatingAnswers.get(qUid);
			if(oldObj instanceof ArrayList<?>) {
				ArrayList<Object> answerList = (ArrayList<Object> ) oldObj;
				
				markAnswerListForDelete(answerList);
			} else {
				NbsAnswerDT dt = (NbsAnswerDT)oldObj;
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		}
		//Add all from old to the new
		//((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap().putAll(oldRepeatingAnswers);
		((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap().putAll(oldRepeatingAnswers);
	}
	
    /*
     * For some of the STD conditions such as the Syphilis conditions the
     * Diagnosis field (NBS136) can change the condition for the PHC. We check
     * the Case Diagnosis SRT to see if the concept code associated with the
     * diagnosis code matches our public_health_case.cd. If not, we return the
     * new code.
     */
    private static String checkForStdConditionChange(String caseDiagnosis, String existingConditionCd)
    {
        boolean canChange = false;

        // see if this diagnosis is one that can have a change of condition
        for (int i = 0; i < PageConstants.DIAGNOSIS_CAN_CHANGE_CONDITION.length; ++i)
        {
            String thisStdDiagCode = PageConstants.DIAGNOSIS_CAN_CHANGE_CONDITION[i];
            if (caseDiagnosis.equals(thisStdDiagCode))
            {
                canChange = true;
                break;
            }
        }
        // if not a code that can change the condition return
        if (!canChange)
            return null;
        String newConditionCode = "";
        // find the concept_code which is the PHIN condition code for the diagnosis
        // should match the current condition code, if it doesn't, we have a condition cd change.
        try
        {
            Coded coded = new Coded();
            coded.setCode(caseDiagnosis);
            coded.setCodesetName(PageConstants.CASE_DIAGNOSIS_CODESET);
            coded.setCodesetTableName(DataTables.CODE_VALUE_GENERAL);
            NotificationSRTCodeLookupTranslationDAOImpl lookupDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
            lookupDAO.retrieveSRTCodeInfo(coded);
            if (coded.getCode() != null && coded.getCodeDescription() != null && coded.getCodeSystemCd() != null)
            {
                newConditionCode = coded.getCode();
            }
        }
        catch (NEDSSSystemException ex)
        {
            String errorMessage = "Reverse lookup for CASE Diagnosis <" + caseDiagnosis + "> failed looking in  "
                    + DataTables.CODE_VALUE_GENERAL + "." + PageConstants.CASE_DIAGNOSIS_CODESET;
        	logger.error("Caught exception in checkForStdConditionChange:", ex);

            logger.debug(ex.getMessage() + errorMessage);
        }
        if (newConditionCode.isEmpty())
            return null;
        if (existingConditionCd.equalsIgnoreCase(newConditionCode))
            return null;
        // user changed code using CASE DIAGNOSIS
        return newConditionCode;
    }

	/*
	 * setTheBirthAddressCountry() Updates existing Postal Locator country or adds a new Postal Locator and Entity Locator Participation
	 * Note that birth country is in the Person record - but the official location is in a ELP with use_cd = 'BIR, class_cd = PST and cd = F
	 * @param PersonVO
	 * @param String birthCountry - the code
	 * @param String userId
	 * @param String asOfDate
	 */
	private static void setTheBirthAddressCountry(PersonVO personVO, String birthCountry, String userId, String asOfDate) {
		logger.debug("Setting the birth country ELP: " + birthCountry);
		Long personUID = personVO.getThePersonDT().getPersonUid();
		Collection<Object>  arrELP = personVO.
									getTheEntityLocatorParticipationDTCollection();

		if (arrELP != null) {
			  Iterator<Object>  iter = arrELP.iterator();
			   while (iter.hasNext()) {
			          EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
			                                             iter.next();
			          if (elp != null) {
			             if (elp.getCd() != null && elp.getCd().equals(NEDSSConstants.BIRTHCD)
			            	 && elp.getClassCd() != null && elp.getClassCd().equals(NEDSSConstants.POSTAL)  
			                 && elp.getUseCd() != null && elp.getUseCd().equals(NEDSSConstants.BIRTHPLACE) 
			                 && elp.getRecordStatusCd() != null && elp.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) { 
			            	 logger.debug("Existing birth country Entity Locator record exists.. ");
			                PostalLocatorDT postalLoc = elp.getThePostalLocatorDT();
			                String existingCountryCd = postalLoc.getCntryCd();
			                if (birthCountry != null && birthCountry.equals(existingCountryCd))
			                	return; //no change
			                if (birthCountry == null || birthCountry.isEmpty()) {
			                	postalLoc.setCntryCd(birthCountry);
			                	postalLoc.setItDelete(true);
			                	elp.setItDelete(true);
			                } else {
			                	postalLoc.setCntryCd(birthCountry);
			                	postalLoc.setItDirty(true);
			                	postalLoc.setItNew(false);
			                	elp.setItDirty(true);
			                	elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			                	if (asOfDate != null)
			                		elp.setAsOfDate_s(asOfDate);
			                }
			                return; //update to existing
			             } //is Birth Address 
			          } //elp not null
			   } //hasNext
		}  
		if (birthCountry == null || birthCountry.trim().isEmpty())
			return;		
		if (arrELP == null) {
		arrELP = new ArrayList<Object> ();		
		}
		//Add a new ELP for the Birth Country
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setAddTime(new Timestamp(new Date().getTime()));
		elp.setAddUserId(Long.valueOf(userId));
		elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setEntityUid(personUID);
		elp.setCd(NEDSSConstants.BIRTHCD);
		elp.setClassCd(NEDSSConstants.POSTAL);
		elp.setUseCd(NEDSSConstants.BIRTHPLACE);
		if (asOfDate != null)
			elp.setAsOfDate_s(asOfDate);

		PostalLocatorDT pl = new PostalLocatorDT();
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setAddTime(new Timestamp(new Date().getTime()));
		pl.setAddUserId(Long.valueOf(userId));
		pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
		pl.setCntryCd(birthCountry);
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

		elp.setThePostalLocatorDT(pl);
		arrELP.add(elp);
		logger.info("Added birth address. Number of address in setTheBirthAddress: " + arrELP.size());
		personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
	} //setTheBirthAddress()
	
	
	  public static void createMessageForNamedBy(PageActProxyVO proxyVO, HttpServletRequest request, NBSSecurityObj nbsSecurityObj) throws Exception  {
		  Collection<Object> coll = new ArrayList();
		  //get associated contacts
		  String investigationUid = (String) NBSContext.retrieve(
					request.getSession(),
					NBSConstantUtil.DSInvestigationUid);
		  
		  CTContactSummaryDAO impl = new CTContactSummaryDAO();
		  
			Collection temp =   impl.getContactListForInvestigation(new Long(investigationUid), nbsSecurityObj);
			for(Object o: temp){
				if(((CTContactSummaryDT)o).isPatientNamedByContact()){	
					coll.add(o);
				}
			}
			
			if(coll.isEmpty()){
		    	Long mprUid = ((Long) NBSContext.retrieve(
						request.getSession(),
						NBSConstantUtil.DSPatientPersonUID));
		    	
		    	
		    	
		    	String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
				String sMethod = "getNamedAsContactSummaryByCondition";
				Object[] oParams =new Object[] {new Long(investigationUid),mprUid};
				
				coll = (Collection<Object>) CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod ,request.getSession());
			  }
//		  }
	    	if(!coll.isEmpty()){
	    		for(Object o:coll){
	    			CTContactSummaryDT ctDt = (CTContactSummaryDT)o;
	    			try {
	    				Boolean allDispositioned = Boolean.TRUE;
	    				if(ctDt.getSubjectEntityPhcUid() != null){
	    				
	    					Object obj = CaseLoadUtil.getProxyObject(String.valueOf(ctDt.getSubjectEntityPhcUid()), request.getSession());
	    					PageActProxyVO contactProxyVo = obj instanceof PageActProxyVO? (PageActProxyVO)obj: null;
//						InvestigationProxyVO vo =PageStoreUtil.getInvestigationProxy(ctDt.getSubjectEntityPhcUid(),request);
	    					
							if(contactProxyVo != null){
								for(Object oCtSumDT: contactProxyVo.getTheCTContactSummaryDTCollection()){
									CTContactSummaryDT ctSumDT = (CTContactSummaryDT)oCtSumDT;
									if(ctSumDT.isContactNamedByPatient() && ctSumDT.getContactEntityPhcUid() != null  && Long.parseLong(investigationUid) != ctSumDT.getContactEntityPhcUid().longValue()
											&&( ctSumDT.getInvDispositionCd() == null || ctSumDT.getInvDispositionCd().isEmpty())){
										allDispositioned = Boolean.FALSE;
									}
									
								}
								
								if(allDispositioned){
									//Create a Message 
									createNotification( contactProxyVo,proxyVO);
								}
							}
	    				}
					} catch (Exception e) {
						logger.error(e.getMessage());
						logger.error("Caught exception in createMessageForNamedBy:", e);
						throw new Exception(e);
					}
	    			
	    		}
	    	}
	    }
	    
		private static void createNotification(PageActProxyVO contactProxyVo, PageActProxyVO proxyVO) {
			Long assignto= 0L;
			try {
				
				MessageLogDT messageLogDT = MessageLogUtil.createMessageLogDT(contactProxyVo, MessageConstants.DISPOSITION_SPECIFIED);
				assignto = (Long) (messageLogDT.getAssignedToUid() == null? 0L : messageLogDT.getAssignedToUid());
				if(messageLogDT.getAssignedToUid() == null){
					PersonVO investgrOfPHC = PageLoadUtil.getPersonVO("InvestgrOfPHC", contactProxyVo);
					PersonVO contactPerson = PageLoadUtil.getPersonVO("SubjOfPHC", contactProxyVo);
					
					if(investgrOfPHC != null){
						assignto = investgrOfPHC.getThePersonDT().getPersonParentUid();
						messageLogDT.setAssignedToUid(investgrOfPHC.getThePersonDT().getPersonParentUid());
					}
					if(contactPerson != null){
						 Long personUid = contactPerson.getThePersonDT().getPersonParentUid();
						 messageLogDT.setPersonUid(personUid);
					}
				}
				if(assignto != 0)
				proxyVO.getMessageLogDTMap().put(MessageConstants.DISPOSITION_SPECIFIED_KEY + assignto, messageLogDT);

				
			} catch (Exception e) {
				logger.error("Unable to store message. the Error message in stdPopulateInvestigationFromContact for = "
						+ "\ninvestigatorUid" + assignto);
			}

		}

	private static void handleSupervisorReviewQueueForEdit(CaseManagementDT cdt, String  oldFupDispo) {

		if (cdt != null) {
			boolean isQ = isSupervisorQueueReady(cdt.getFldFollUpDispo());
			String newFFUDispo = cdt.getFldFollUpDispo();

			// Field Closure
			if (("FF".equalsIgnoreCase(cdt.getInitFollUp()) || "OOJ"
					.equalsIgnoreCase(cdt.getInitFollUp())) && isQ) {
				if (!"ACCEPT".equalsIgnoreCase(cdt.getCaseReviewStatus())) {
					cdt.setCaseReviewStatus("Ready");
					cdt.setCaseReviewStatusDate(new Timestamp(System
							.currentTimeMillis()));
					//cdt.setCaseClosedDate(null);//ND-31479: Fatima do we need this??
				}
			}
			// if Field Closure and follow-up disposition is one of { "G", "H",
			// "J", "K", "L", "Q" } and old disposition
			// does not match the new disposition and the case is not in review
			// queue already.
			if ("FF".equalsIgnoreCase(cdt.getInitFollUp())
					&& isQ
					&& newFFUDispo != null
					&& (oldFupDispo == null || !newFFUDispo.equals(oldFupDispo))
					&& cdt.getCaseReviewStatus() != null
					&& !cdt.getCaseReviewStatus().equals("Ready")) {
				cdt.setCaseReviewStatus("Ready");
				cdt.setCaseReviewStatusDate(new Timestamp(System
						.currentTimeMillis()));
			//	cdt.setCaseClosedDate(null);//ND-31479
			}
			// Case Closure
			if (cdt.getCaseClosedDate() != null
					&& !"ACCEPT".equalsIgnoreCase(cdt.getCaseReviewStatus())) {
				cdt.setCaseReviewStatus("Ready");
				cdt.setCaseReviewStatusDate(new Timestamp(System
						.currentTimeMillis()));
			}
		}

	}
	
	private static void handleSupervisorReviewQueueForCreate(CaseManagementDT cdt){
        // Field Closure
        if ("FF".equalsIgnoreCase(cdt.getInitFollUp()) && isSupervisorQueueReady(cdt.getFldFollUpDispo()))
        {
            cdt.setCaseReviewStatus("Ready");
            cdt.setCaseReviewStatusDate(new Timestamp(System.currentTimeMillis()));
         //   cdt.setCaseClosedDate(null);//ND-31479
        }
        // Case Closure
        if (cdt.getCaseClosedDate() != null && "REJECT".equalsIgnoreCase(cdt.getCaseReviewStatus()))
        {
            cdt.setCaseReviewStatus("Ready");
            cdt.setCaseReviewStatusDate(new Timestamp(System.currentTimeMillis()));
        }

	}

	private static boolean isSupervisorQueueReady(String fldFlupCd) {
		String[] crCodeArr = new String[] { "G", "H", "J", "K", "L", "Q" };

		if (Arrays.asList(crCodeArr).contains(fldFlupCd)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static ArrayList<Long> parseCoinfectionList(String coinfStr) {
	   		ArrayList<Long> invList = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(coinfStr,":"); 
			while (st.hasMoreTokens()) { 
				String token = st.nextToken();
				try {
					Long invUid = new Long (token);
					invList.add(invUid);
				} catch (Exception e) {
					logger.error("Error parsing coinfection list " + coinfStr);
					e.printStackTrace();
				}
					
			}				
		
			return invList;
  }	
	
	public static ArrayList<Object> getLabProgramAreaList(
			HttpServletRequest request) {
		ArrayList<Object> returnArray = new ArrayList<Object>();
		try{
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession()
				.getAttribute("NBSSecurityObject");
		TreeMap<Object, Object> treeMap = nbsSecurityObj.getProgramAreas(
				NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ADD);
		String sContextAction = request.getParameter("ContextAction");
		DropDownCodeDT ddDT = null;
		if (treeMap != null) {
			Set<Object> s = new TreeSet<Object>(treeMap.values());
			if (sContextAction.equalsIgnoreCase("AddLabDataEntry")
					|| sContextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
				ddDT = new DropDownCodeDT();
				ddDT.setKey(NEDSSConstants.PROGRAM_AREA_NONE);
				ddDT.setValue("Unknown");
				returnArray.add(ddDT);
			}
			Iterator<Object> it = s.iterator();
			while (it.hasNext()) {
				String sortedValue = (String) it.next();
				Iterator<?> anIterator = treeMap.entrySet().iterator();

				while (anIterator.hasNext()) {
					Map.Entry map = (Map.Entry) anIterator.next();
					if ((String) map.getValue() == sortedValue) {

						ddDT = new DropDownCodeDT();
						ddDT.setKey((String) map.getKey());
						ddDT.setValue((String) map.getValue());
						returnArray.add(ddDT);

					}
				}
			}
		}
		}catch(Exception ex){
			logger.error("Error while getting program Areas for Lab Report");
		}
		return returnArray;
	}
	
	public static ArrayList<Object> getJurisdictionListWithUnknown(
			HttpServletRequest request) {
		ArrayList<Object> returnArray = new ArrayList<Object>();
		boolean unknownflag = false;
		try {
			String sContextAction = request.getParameter("ContextAction");
			DropDownCodeDT ddDT = null;
			if (sContextAction.equalsIgnoreCase("AddLabDataEntry")
					|| sContextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
				ddDT = new DropDownCodeDT();
				ddDT.setKey("");
				ddDT.setValue("");
				returnArray.add(ddDT);
				ddDT = new DropDownCodeDT();
				ddDT.setKey(NEDSSConstants.JURISDICTION_NONE);
				ddDT.setValue("Unknown");
				returnArray.add(ddDT);
				unknownflag = true;
			}
			ArrayList<Object> defaultList = CachedDropDowns
					.getJurisdictionList();
			if (defaultList != null && defaultList.size() > 0) {
				for (Object ddDT1 : defaultList) {
					if (((DropDownCodeDT) ddDT1).getKey().equals("")
							&& unknownflag)
						continue;
					else
						returnArray.add(ddDT1);
				}
			}
		} catch (Exception ex) {
			logger.error("Error while getting Jurisdictions list for Lab Report");
		}
		return returnArray;
	}
	
	
	/************************************************************VERCRIT METHODS: begin*************************************************************************/
	/**
	 * setVerCritTBLogicOnSubmit: this method will go through each of the VerCrit rules to set the corresponding values in the Case Verification and Case Status.
	 * @param form
	 */
	
    public static void setVerCritTBLogicOnSubmit(PageForm form){

		//Rule 1
		if(validateRule1(form)){//0 - Not a Verified Case
			form.getPageClientVO().getAnswerMap().put("INV1115","PHC162");
			form.getPageClientVO().getAnswerMap().put("INV163","N");
		}else//Rule 2
			if(validateRule2(form)){//1 - Positive Culture
					//THEN INV1115 (Case Verification) = PHC97 (1 - Positive Culture) AND INV163 (Case Status) = C (Confirmed)
					form.getPageClientVO().getAnswerMap().put("INV1115","PHC97");//1 - Positive Culture
					form.getPageClientVO().getAnswerMap().put("INV163","C");//Confirmed
			
					
			}else//Rule 3
				if(validateRule3(form)){//1A - Positive NAA
					//THEN INV1115 (Case Verification) = PHC653 AND INV163 (Case Status) = C (Confirmed)
					form.getPageClientVO().getAnswerMap().put("INV1115","PHC653");//(1A - Positive NAA)
					form.getPageClientVO().getAnswerMap().put("INV163","C");//Confirmed
	
				}else//Rule 4
					if(validateRule4(form))//2 - Positive Smear/Tissue
					{
						//THEN INV1115 (Case Verification) = PHC98 (2 - Positive Smear/Tissue) AND INV163 (Case Status) = C (Confirmed)
						form.getPageClientVO().getAnswerMap().put("INV1115","PHC98");//(2 - Positive Smear/Tissue) 
						form.getPageClientVO().getAnswerMap().put("INV163","C");//Confirmed
		
					}else//Rule 5
						if(validateRule5_part1(form)//3 - Clinical Case Definition: Pulmonary Disease Criteria (check first)
								|| 	validateRule5_part2(form))//3 - Clinical Case Definition: Extra Pulmonary Disease Criteria (check if above criteria not met)
						{
							//THEN INV1115 (Case Verification) = PHC654 (3 - Clinical Case Definition) AND INV163 (Case Status) = C (Confirmed)
							form.getPageClientVO().getAnswerMap().put("INV1115","PHC654");//PHC654 (3 - Clinical Case Definition) 
							form.getPageClientVO().getAnswerMap().put("INV163","C");//Confirmed
			
						}else//Rule 6
							if(validateRule6(form))//Verified by Provider By Data Entry
							{
								form.getPageClientVO().getAnswerMap().put("INV163","C");//Confirmed
				
							}else//Rule 7
								if(validateRule7(form))//0 - Not a Verified Case
								{
									form.getPageClientVO().getAnswerMap().put("INV163","N");//N (Not a Case)
					
								}else//Rule 8
									if(validateRule8(form))//S - Suspected
									{
										form.getPageClientVO().getAnswerMap().put("INV163","S");//S (Suspected)
						
									}	
							//If none true, then suspect.
							else{
								//if the case verification and case status do not match an algorithm, they should be set to suspect by default.
								form.getPageClientVO().getAnswerMap().put("INV1115","415684004");
								form.getPageClientVO().getAnswerMap().put("INV163","S");
							}
    }
    


    /**
     * validateRule1: this is the VerCrit Rule 1 (Not a Verified Case)
     * @param form
     * @return
     */
    
    public static boolean validateRule1(PageForm form){
    	
    	
    	boolean found = false;    	
    	String INV1140 = getValueFromForm(form, "INV1140");//Reason Therapy stopped

    	//Rule 1
    	if(INV1140!=null && INV1140.equalsIgnoreCase("PHC72")){//Never Started
    		found = true;
    	}
    				
    	return found;   	
    }
    
    /**
     * validateRule2: this is the VerCrit Rule 2 (1-Positive Culture)
     * @param form
     * @return
     */
    
    public static boolean validateRule2(PageForm form){
    	
    	boolean found = false;
    	String subsectionId = "NBS_UI_GA27017";
    	
    	String TUB122 = getValueFromForm(form, "TUB122");//Sputum Culture Results
    	String TUB130 = getValueFromForm(form, "TUB130");//Other Culture Result
		
		HashMap <String, ArrayList<String>> codeValuesRule2 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values2_1 = new ArrayList<String>();
		ArrayList<String> values2_2 = new ArrayList<String>();
		

		values2_1.add("50941-4");//Culture
		values2_2.add("10828004");//Positive
		
		codeValuesRule2.put("INV290",values2_1);//Test type
		codeValuesRule2.put("INV291",values2_2);//Test result
				
    	if((TUB122!=null && TUB122.equalsIgnoreCase("10828004")) || //Positive
		(TUB130!=null && TUB130.equalsIgnoreCase("10828004")) || //Positive
		(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule2))){
			found = true;
			
		}
		
		return found;
    }
    
    /**
     * validateRule3: this is the VerCrit Rule 3 (1A - Positive NAA)
     * @param form
     * @return
     */
    public static boolean validateRule3(PageForm form){
    	
    	
    	boolean found = false;
    	String subsectionId = "NBS_UI_GA27017";
    	
		String TUB135 =  getValueFromForm(form, "TUB135");//Nucleic Acid Amplification Results
		
		HashMap <String, ArrayList<String>> codeValuesRule3 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values3_1 = new ArrayList<String>();
		ArrayList<String> values3_2 = new ArrayList<String>();
		

		values3_1.add("LAB673");//NAA Test
		values3_2.add("10828004");//Positive
		
		codeValuesRule3.put("INV290",values3_1);//Test Type
		codeValuesRule3.put("INV291",values3_2);//Test Result
		
    	
		if((TUB135!=null && TUB135.equalsIgnoreCase("10828004")) || //Positive
				(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule3)))
			found = true;
			
    	
    	return found;
    }
    
    /**
     * validateRule4: this is the VerCrit Rule 4 (2 - Positive Smear/Tissue)
     * @param form
     * @return
     */
    
    public static boolean validateRule4(PageForm form){
    	
    	boolean found = false;
    	String subsectionId = "NBS_UI_GA27017";
    	
    	String TUB120= getValueFromForm(form, "TUB120");//Sputum Smear Result
		String TUB126= getValueFromForm(form, "TUB126");//Other Smear Pathology Cytology Results
	 	String TUB122 = getValueFromForm(form, "TUB122");//Sputum Culture Results
    	String TUB130 = getValueFromForm(form, "TUB130");//Other Culture Result
		String TUB135 = getValueFromForm(form, "TUB135");//Nucleic Acid Amplification Results
		

		HashMap <String, ArrayList<String>> codeValuesRule4_1 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_1_1 = new ArrayList<String>();
		ArrayList<String> values4_1_2 = new ArrayList<String>();
		

		values4_1_1.add("20431-3");//Smear
		values4_1_2.add("10828004");//Positive
		
		codeValuesRule4_1.put("INV290",values4_1_1);
		codeValuesRule4_1.put("INV291",values4_1_2);

		HashMap <String, ArrayList<String>> codeValuesRule4_2 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_2_1 = new ArrayList<String>();
		ArrayList<String> values4_2_2 = new ArrayList<String>();
		

		values4_2_1.add("10525-4");//Cytology
		values4_2_2.add("10828004");//Positive
		
		codeValuesRule4_2.put("INV290",values4_2_1);
		codeValuesRule4_2.put("INV291",values4_2_2);

		HashMap <String, ArrayList<String>> codeValuesRule4_3 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_3_1 = new ArrayList<String>();
		ArrayList<String> values4_3_2 = new ArrayList<String>();
		

		values4_3_1.add("50595-8");//Pathology
		values4_3_2.add("10828004");//Positive
		
		codeValuesRule4_3.put("INV290",values4_3_1);
		codeValuesRule4_3.put("INV291",values4_3_2);

		HashMap <String, ArrayList<String>> codeValuesRule4_4 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_4_1 = new ArrayList<String>();
		ArrayList<String> values4_4_2 = new ArrayList<String>();
		

		values4_4_1.add("LAB719");//Pathology/Cytology
		values4_4_2.add("10828004");//Positive
		
		codeValuesRule4_4.put("INV290",values4_4_1);
		codeValuesRule4_4.put("INV291",values4_4_2);
		
		HashMap <String, ArrayList<String>> codeValuesRule4_5 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values4_5_1 = new ArrayList<String>();
		ArrayList<String> values4_5_2= new ArrayList<String>();
		ArrayList<String> values4_5_3= new ArrayList<String>();
		

		values4_5_1.add("50941-4");//Culture
		
		values4_5_2.add("385660001");
		values4_5_2.add("UNK");
		values4_5_2.add("82334004");
		values4_5_2.add("443390004");
		values4_5_2.add("410530007");
		values4_5_2.add("PHC2092");
		
		values4_5_3.add("119334006");//Sputum
		
		codeValuesRule4_5.put("INV290",values4_5_1);
		codeValuesRule4_5.put("INV291",values4_5_2);
		codeValuesRule4_5.put("LAB165",values4_5_3);//Specimen Source Site

		
		HashMap <String, ArrayList<String>> codeValuesRule4_7 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values4_7_1 = new ArrayList<String>();
		ArrayList<String> values4_7_2= new ArrayList<String>();
		ArrayList<String> values4_7_3= new ArrayList<String>();
		

		values4_7_1.add("50941-4");//Culture
		
		values4_7_2.add("385660001");
		values4_7_2.add("UNK");
		values4_7_2.add("82334004");
		values4_7_2.add("443390004");
		values4_7_2.add("410530007");
		values4_7_2.add("PHC2092");
		
		values4_7_3.add("119334006");//Sputum
		
		codeValuesRule4_7.put("INV290",values4_7_1);
		codeValuesRule4_7.put("INV291",values4_7_2);
		codeValuesRule4_7.put("NOT_CONTAIN_LAB165",values4_7_3);//Specimen Source Site


		HashMap <String, ArrayList<String>> codeValuesRule4_5_1 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_5_1_1 = new ArrayList<String>();
		

		values4_5_1_1.add("50941-4");//Culture		
		codeValuesRule4_5_1.put("INV290",values4_5_1_1);

		HashMap <String, ArrayList<String>> codeValuesRule4_6 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values4_6_1 = new ArrayList<String>();
		ArrayList<String> values4_6_2= new ArrayList<String>();
		

		values4_6_1.add("LAB673");//Culture
		
		values4_6_2.add("385660001");
		values4_6_2.add("UNK");
		values4_6_2.add("82334004");
		values4_6_2.add("443390004");
		values4_6_2.add("410530007");
		values4_6_2.add("PHC2092");
		
		codeValuesRule4_6.put("INV290",values4_6_1);
		codeValuesRule4_6.put("INV291",values4_6_2);

		HashMap <String, ArrayList<String>> codeValuesRule4_6_1 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values4_6_1_1 = new ArrayList<String>();
		

		values4_6_1_1.add("LAB673");//Nucleic Acid Amplification Test	
		codeValuesRule4_6_1.put("INV290",values4_6_1_1);
		
		/*********/

		ArrayList<String> TUB122Values = new ArrayList<String>();
		
		TUB122Values.add("385660001");
		TUB122Values.add("UNK");
		TUB122Values.add("82334004");
		TUB122Values.add("443390004");
		TUB122Values.add("410530007");
		TUB122Values.add("PHC2092");
		
		ArrayList<String> TUB130Values = new ArrayList<String>();
		
		TUB130Values.add("385660001");
		TUB130Values.add("UNK");
		TUB130Values.add("82334004");
		TUB130Values.add("443390004");
		TUB130Values.add("410530007");
		TUB130Values.add("PHC2092");
		TUB130Values.add("");

		ArrayList<String> TUB135Values = new ArrayList<String>();
		
		TUB135Values.add("385660001");
		TUB135Values.add("UNK");
		TUB135Values.add("82334004");
		TUB135Values.add("443390004");
		TUB135Values.add("410530007");
		TUB135Values.add("PHC2092");
		
    	if(((TUB120!=null && TUB120.equalsIgnoreCase("10828004")) || // Sputum Smear Result = 10828004 (Positive)
			(TUB126!=null && TUB126.equalsIgnoreCase("10828004")) || // (Other Smear Pathology Cytology Results) = 10828004 (Positive))
			checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_1)||
			checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_2)||
			checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_3)||
			checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_4)) &&

			(TUB122Values.contains(TUB122) ||
			(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_5)))&&
	
			((TUB130Values.contains(TUB130) || TUB130 == null) ||//to allow blanks
			(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_7))) && //Or the test type = Culture doesn't exist within the repeating block
	
			(TUB135Values.contains(TUB135) ||
			(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule4_6)))) 
				
    			found = true;
			
    	
    	return found;
	
    }
    
    /**
     * validateRule5_part1: this is the first part of the rule 5 (Clinical Case Definition - Pulmonary Disease Criteria)
     * @param form
     * @return
     */
    
    public static boolean validateRule5_part1(PageForm form){
    	
    	boolean found = false;
    	String subsectionId = "NBS_UI_GA27017";
    	String subsectionIdChestStudy = "NBS_UI_GA28004";
    	
    	String TUB122= getValueFromForm(form, "TUB122");//Sputum Culture Result
    	String TUB130= getValueFromForm(form, "TUB130");//Other Culture Results
    	String TUB135= getValueFromForm(form, "TUB135");//Nucleic Acid Amplification Results
    	String TUB141= getValueFromForm(form, "TUB141");//Initial Chest X-Ray Result
    	String TUB144= getValueFromForm(form, "TUB144");//Initial Chest CT Scan Result
    	String TUB147= getValueFromForm(form, "TUB147");//Tuberculin Skin Test Result
    	String TUB150= getValueFromForm(form, "TUB150");//IGRA Qualitative Test Result
    	
    	
		HashMap <String, ArrayList<String>> codeValuesRule5_1 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_1_1 = new ArrayList<String>();//Culture
		ArrayList<String> values5_1_2 = new ArrayList<String>();//Test Result
		ArrayList<String> values5_1_3 = new ArrayList<String>();//Specimen Source
		
		

		values5_1_1.add("50941-4");
		
		values5_1_2.add("260385009");
		values5_1_2.add("385660001");
		values5_1_2.add("UNK");
		values5_1_2.add("82334004");
		values5_1_2.add("443390004");
		values5_1_2.add("410530007");
		values5_1_2.add("PHC2092");
		
		
		values5_1_3.add("119334006");//Sputum
		
		
		codeValuesRule5_1.put("INV290",values5_1_1);//Test Type
		codeValuesRule5_1.put("INV291",values5_1_2);//Test Result
		codeValuesRule5_1.put("LAB165",values5_1_3);//Specimen Source Site

		HashMap <String, ArrayList<String>> codeValuesRule5_2 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_2_1 = new ArrayList<String>();
		

		values5_2_1.add("50941-4");//Culture		
		codeValuesRule5_2.put("INV290",values5_2_1);//Test Type

    	ArrayList<String> INV1133Values = new ArrayList<String>();
		
    	INV1133Values.add("281778006");
    	INV1133Values.add("39607008");
    	INV1133Values.add("3120008");
    	
    	
    	ArrayList<String> TUB122Values = new ArrayList<String>();
		
    	TUB122Values.add("260385009");
    	TUB122Values.add("385660001");
    	TUB122Values.add("UNK");
    	TUB122Values.add("82334004");
    	TUB122Values.add("443390004");
    	TUB122Values.add("410530007");
    	TUB122Values.add("PHC2092");
    	
    	
    	
    	ArrayList<String> TUB130Values = new ArrayList<String>();
		
    	TUB130Values.add("260385009");
    	TUB130Values.add("385660001");
    	TUB130Values.add("UNK");
    	TUB130Values.add("82334004");
    	TUB130Values.add("443390004");
    	TUB130Values.add("410530007");
    	TUB130Values.add("PHC2092");
    	TUB130Values.add("");
    	
    	
    	
    	ArrayList<String> TUB135Values = new ArrayList<String>();
    	
    	TUB135Values.add("260385009");
    	TUB135Values.add("385660001");
    	TUB135Values.add("UNK");
    	TUB135Values.add("82334004");
    	TUB135Values.add("443390004");
    	TUB135Values.add("410530007");
    	TUB135Values.add("PHC2092");

		HashMap <String, ArrayList<String>> codeValuesRule5_3 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_3_1 = new ArrayList<String>();
		ArrayList<String> values5_3_2 = new ArrayList<String>();
		

		values5_3_1.add("LAB673");
		
		values5_3_2.add("260385009");
		values5_3_2.add("385660001");
		values5_3_2.add("UNK");
		values5_3_2.add("82334004");
		values5_3_2.add("443390004");
		values5_3_2.add("410530007");
		values5_3_2.add("PHC2092");
		
		codeValuesRule5_3.put("INV290",values5_3_1);//Test Type
		codeValuesRule5_3.put("INV291",values5_3_2);//Test Result

		
		HashMap <String, ArrayList<String>> codeValuesRule5_6 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values5_6_1 = new ArrayList<String>();
		ArrayList<String> values5_6_2= new ArrayList<String>();
		ArrayList<String> values5_6_3= new ArrayList<String>();
		

		values5_6_1.add("50941-4");//Culture
		
		values5_6_2.add("260385009");
		values5_6_2.add("385660001");
		values5_6_2.add("UNK");
		values5_6_2.add("82334004");
		values5_6_2.add("443390004");
		values5_6_2.add("410530007");
		values5_6_2.add("PHC2092");
		
		values5_6_3.add("119334006");//Sputum
		
		codeValuesRule5_6.put("INV290",values5_6_2);//Test Type
		codeValuesRule5_6.put("INV291",values5_6_2);//Test Result
		codeValuesRule5_6.put("NOT_CONTAIN_LAB165",values5_6_3);//Specimen Source Site

		HashMap <String, ArrayList<String>> codeValuesRule5_4 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_4_1 = new ArrayList<String>();
		ArrayList<String> values5_4_2 = new ArrayList<String>();
		

		values5_4_1.add("169069000");
		values5_4_1.add("399208008");
		
		values5_4_2.add("PHC1873");
		
		//Test type doesn't matter
		codeValuesRule5_4.put("LAB677",values5_4_1);//Type of Chest Study
		codeValuesRule5_4.put("LAB678",values5_4_2);//Result of Chest Study


		HashMap <String, ArrayList<String>> codeValuesRule5_5 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_5_1 = new ArrayList<String>();
		ArrayList<String> values5_5_2 = new ArrayList<String>();
		

		values5_5_1.add("TB119");
		
		values5_5_2.add("10828004");//10828004 (Positive))]
		
		codeValuesRule5_5.put("INV290",values5_5_1);//Test Type
		codeValuesRule5_5.put("INV291",values5_5_2);//Test Result
		
		
		HashMap <String, ArrayList<String>> codeValuesRule5_7 = new HashMap<String, ArrayList<String>>();
		ArrayList<String> values5_7_1 = new ArrayList<String>();
		ArrayList<String> values5_7_2 = new ArrayList<String>();
		

		values5_7_1.add("LAB671");
		values5_7_1.add("LAB672");
		values5_7_1.add("LAB720");
		values5_7_1.add("71773-6");
		
		values5_7_2.add("10828004");//10828004 (Positive))]
		
		codeValuesRule5_7.put("INV290",values5_7_1);//Test Type
		codeValuesRule5_7.put("INV291",values5_7_2);//Test Result
		
		
		
		HashMap<String, String> criterias = new HashMap<String, String>();
		
		

    	criterias.put("6038","Y");
    	criterias.put("9384","Y");
    	criterias.put("8987","Y");
    	criterias.put("4110","Y");
    	criterias.put("10109","Y");
    	criterias.put("55672","Y");
    	criterias.put("35617","Y");
    	criterias.put("4127","Y");
    	criterias.put("641","Y");
    	criterias.put("6099","Y");
    	criterias.put("78903","Y");
    	criterias.put("2551","Y");
    	criterias.put("82122","Y");
    	criterias.put("7623","Y");
    	criterias.put("139462","Y");
    	criterias.put("PHC1888","Y");
    	criterias.put("3007","Y");
    	criterias.put("7833","Y");
    	criterias.put("190376","Y");
    	criterias.put("1364504","Y");
    	criterias.put("PHC1889","Y");
    	criterias.put("2592","Y");
    	criterias.put("2198359","Y");
    	criterias.put("NBS456","Y");
	
	
	
    	
    	
    	if((validateIfMultiIsPartOfValues(form, INV1133Values,"INV1133"))&&
    			
    			
    		(TUB122Values.contains(TUB122) ||
    		(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule5_1)))&&
    			
    		
    		
    		((TUB130Values.contains(TUB130) || TUB130 == null) ||//to allow blanks
    		(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule5_6))) &&
		
    	
    		(TUB135Values.contains(TUB135)||
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_3))&&
    		
    		
    		((TUB141!=null && TUB141.equalsIgnoreCase("PHC1873") || //(Consistent with TB)
    		(TUB144!=null && TUB144.equalsIgnoreCase("PHC1873") || //(Consistent with TB)
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionIdChestStudy, codeValuesRule5_4)))) &&
    		
    			
    		(((TUB147!=null && TUB147.equalsIgnoreCase("10828004")) ||//Tuberculin Skin Test Result = Positive
    		(checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule5_5))) ||
    		
    		
    		((TUB150!=null && TUB150.equalsIgnoreCase("10828004")) ||//(IGRA Qualitative Test Result) = 10828004 (Positive)
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId,codeValuesRule5_7)))&&
    		
    		
    		//at least 2 criterias met:
    		validateIfNCriteriasMet(form, criterias, 2))
    		
    			found=true;
    	
		
    	
    	return found;   	
    }

    
    /**
     * validateRule5_part2: this is the second part of the rule 5 (Clinical Case Definition - Extra Pulmonary Disease Criteria)
     * @param form
     * @return
     */
    
   public static boolean validateRule5_part2(PageForm form){
    	
    	boolean found = false;
    	String subsectionId = "NBS_UI_GA27017";
    	
    	String INV1133 = getValueFromForm(form, "INV1133");
    	String TUB122 = getValueFromForm(form, "TUB122");
    	String TUB130 = getValueFromForm(form, "TUB130");
    	String TUB135 = getValueFromForm(form, "TUB135");
    	String TUB147 = getValueFromForm(form, "TUB147");
    	String TUB150 = getValueFromForm(form, "TUB150");
    	
    	
    	ArrayList<String> INV1133Values = new ArrayList<String>();
		

    	INV1133Values.add("23451007");
    	INV1133Values.add("362102006");
    	INV1133Values.add("53505006");
    	INV1133Values.add("66754008");
    	INV1133Values.add("87612001");
    	INV1133Values.add("59820001");
    	//INV1133Values.add("PHC5");//ND-26685
    	INV1133Values.add("110522009");
    	INV1133Values.add("14016003");
    	INV1133Values.add("12738006");
    	INV1133Values.add("76752008");
    	INV1133Values.add("17401000");
    	INV1133Values.add("71854001");
    	INV1133Values.add("38848004");
    	INV1133Values.add("110547006");
    	INV1133Values.add("32849002");
    	INV1133Values.add("16014003");
    	INV1133Values.add("PHC4");
    	INV1133Values.add("C0230999");
    	INV1133Values.add("28231008");
    	INV1133Values.add("80891009");
    	INV1133Values.add("110611003");
    	INV1133Values.add("48477009");
    	INV1133Values.add("10200004");	
    	INV1133Values.add("PHC2");
    	INV1133Values.add("PHC3");
    	INV1133Values.add("1231004");
    	INV1133Values.add("110708006");
    	INV1133Values.add("123851003");
    	INV1133Values.add("45206002");
    	INV1133Values.add("71836000");
    	INV1133Values.add("OTH");
    	INV1133Values.add("15776009");
    	INV1133Values.add("120228005");
    	INV1133Values.add("76848001");
    	INV1133Values.add("83670000");
    	INV1133Values.add("54066008");
    	INV1133Values.add("56329008");
    	INV1133Values.add("110973009");
    	INV1133Values.add("34402009");
    	INV1133Values.add("385294005");
    	INV1133Values.add("39937001");
    	INV1133Values.add("2748008");
    	INV1133Values.add("78961009");
    	INV1133Values.add("69695003");
    	INV1133Values.add("21514008");
    	INV1133Values.add("281777001");
    	INV1133Values.add("69831007");
    	INV1133Values.add("25087005");
    	INV1133Values.add("71966008");
    	INV1133Values.add("9875009");
    	INV1133Values.add("297261005");
    	INV1133Values.add("21974007");
    	INV1133Values.add("303337002");
    	INV1133Values.add("44567001");
	
    	ArrayList<String> TUB122Values = new ArrayList<String>();
    	
    	TUB122Values.add("260385009");
    	TUB122Values.add("385660001");
    	TUB122Values.add("UNK");
    	TUB122Values.add("82334004");
    	TUB122Values.add("443390004");
    	TUB122Values.add("410530007");
    	TUB122Values.add("PHC2092");
	 
    	ArrayList<String> TUB130Values = new ArrayList<String>();

    	TUB130Values.add("260385009");
    	TUB130Values.add("385660001");
    	TUB130Values.add("UNK");
    	TUB130Values.add("82334004");
    	TUB130Values.add("443390004");
    	TUB130Values.add("410530007");
    	TUB130Values.add("PHC2092");
    	TUB130Values.add("");
    	
	
		HashMap <String, ArrayList<String>> codeValuesRule5_1 = new HashMap<String, ArrayList<String>>();

		ArrayList<String> values5_1_1 = new ArrayList<String>();
		ArrayList<String> values5_1_2= new ArrayList<String>();
		ArrayList<String> values5_1_3= new ArrayList<String>();
		

		values5_1_1.add("50941-4");//Culture
		
		
		values5_1_2.add("260385009");
		values5_1_2.add("385660001");
		values5_1_2.add("UNK");
		values5_1_2.add("82334004");
		values5_1_2.add("443390004");
		values5_1_2.add("410530007");
		values5_1_2.add("PHC2092");
		
		values5_1_3.add("119334006");//Sputum
		
		
		codeValuesRule5_1.put("INV290",values5_1_1);
		codeValuesRule5_1.put("INV291",values5_1_2);
		codeValuesRule5_1.put("LAB165",values5_1_3);
		
		
		
		HashMap <String, ArrayList<String>> codeValuesRule5_4 = new HashMap<String, ArrayList<String>>();

		ArrayList<String> values5_4_1 = new ArrayList<String>();
		ArrayList<String> values5_4_2= new ArrayList<String>();
		ArrayList<String> values5_4_3= new ArrayList<String>();
		

		values5_4_1.add("50941-4");//Culture
		
		values5_4_2.add("260385009");
		values5_4_2.add("385660001");
		values5_4_2.add("UNK");
		values5_4_2.add("82334004");
		values5_4_2.add("443390004");
		values5_4_2.add("410530007");
		values5_4_2.add("PHC2092");
		
		values5_4_3.add("119334006");//Sputum
		
		
		codeValuesRule5_4.put("INV290",values5_4_1);
		codeValuesRule5_4.put("INV291",values5_4_2);
		codeValuesRule5_4.put("NOT_CONTAIN_LAB165",values5_4_3);
		
		
		
		
		
		
		HashMap <String, ArrayList<String>> codeValuesRule5_2 = new HashMap<String, ArrayList<String>>();

		ArrayList<String> values5_2_1 = new ArrayList<String>();
		ArrayList<String> values5_2_2= new ArrayList<String>();

		values5_2_1.add("LAB673");//Nucleic Acid Amplification Test
		
		values5_2_2.add("260385009");
		values5_2_2.add("385660001");
		values5_2_2.add("UNK");
		values5_2_2.add("82334004");
		values5_2_2.add("443390004");
		values5_2_2.add("410530007");
		values5_2_2.add("PHC2092");
		
		
		codeValuesRule5_2.put("INV290",values5_2_1);
		codeValuesRule5_2.put("INV291",values5_2_2);
		
		ArrayList<String> TUB135Values = new ArrayList<String>();
		
		TUB135Values.add("260385009");
		TUB135Values.add("385660001");
		TUB135Values.add("UNK");
		TUB135Values.add("82334004");
		TUB135Values.add("443390004");
		TUB135Values.add("410530007");
		TUB135Values.add("PHC2092");
		
		HashMap <String, ArrayList<String>> codeValuesRule5_3 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values5_3_1 = new ArrayList<String>();
		ArrayList<String> values5_3_2= new ArrayList<String>();
		

		values5_3_1.add("TB119");//Culture

		values5_3_2.add("10828004");
		
		
		codeValuesRule5_3.put("INV290",values5_3_1);
		codeValuesRule5_3.put("INV291",values5_3_2);
		
		
		HashMap <String, ArrayList<String>> codeValuesRule5_5 = new HashMap<String, ArrayList<String>>();
		
		
		ArrayList<String> values5_5_1 = new ArrayList<String>();
		ArrayList<String> values5_5_2= new ArrayList<String>();
		

		values5_5_1.add("LAB671");
		values5_5_1.add("LAB672");
		values5_5_1.add("LAB720");
		values5_5_1.add("71773-6");

		values5_5_2.add("10828004");
		
		codeValuesRule5_5.put("INV290",values5_5_1);
		codeValuesRule5_5.put("INV291",values5_5_2);
		
		
		
		HashMap<String, String> criterias = new HashMap<String, String>();
	
    	criterias.put("6038","Y");
    	criterias.put("9384","Y");
    	criterias.put("8987","Y");
    	criterias.put("4110","Y");
    	criterias.put("10109","Y");
    	criterias.put("55672","Y");
    	criterias.put("35617","Y");
    	criterias.put("4127","Y");
    	criterias.put("641","Y");
    	criterias.put("6099","Y");
    	criterias.put("78903","Y");
    	criterias.put("2551","Y");
    	criterias.put("82122","Y");
    	criterias.put("7623","Y");
    	criterias.put("139462","Y");
    	criterias.put("PHC1888","Y");
    	criterias.put("3007","Y");
    	criterias.put("7833","Y");
    	criterias.put("190376","Y");
    	criterias.put("1364504","Y");
    	criterias.put("PHC1889","Y");
    	criterias.put("2592","Y");
    	criterias.put("2198359","Y");
    	criterias.put("NBS456","Y");
	
    	if(validateIfMultiIsPartOfValues(form, INV1133Values,"INV1133")&&
    			
    			
    		(TUB122Values.contains(TUB122) ||
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_1)) &&
    		
    	
    		((TUB130Values.contains(TUB130) || TUB130 == null) ||//to allow blanks
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_4)) &&
    		
    		
    		(TUB135Values.contains(TUB135) ||
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_2)) && 	
    			
    		((((TUB147!=null && TUB147.equalsIgnoreCase("10828004")) ||	//Tuberculin Skin Test Result=Positive
    		checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_3))) ||
    			

    		((TUB150!=null && TUB150.equalsIgnoreCase("10828004")) ||	//IGRA Qualitative Test Result=10828004	
    	    checkIfRowValuesExistInRepeatingBlock(form, subsectionId, codeValuesRule5_5))) &&
    	    		
    	    		
    	    		
    		validateIfNCriteriasMet(form, criterias, 2))
    			found = true;

    	
    	return found;
    	
    	
    	
   }
   
   
   /**
    * validateRule6: this is the VerCrit Rule 6: 4 - Verified by Provider By Data Entry
    * @param form
    * @return
    */
   
   public static boolean validateRule6(PageForm form){
   	
   	
   	boolean found = false;    	
   	String INV1115 = getValueFromForm(form, "INV1115");//Case Verification

   	//Rule 6
   	if(INV1115!=null && INV1115.equalsIgnoreCase("PHC165")){//Verified by Provider By Data Entry
   		found = true;
   	}
   				
   	return found;   	
   }
   
   
   /**
    * validateRule7: this is the VerCrit Rule 7: 0 - Not a Verified Case By Data Entry
    * @param form
    * @return
    */
   
   public static boolean validateRule7(PageForm form){
   	
   	
   	boolean found = false;    		
   	String INV1115 = getValueFromForm(form, "INV1115");//Case Verification
   
   	//Rule 7
   	if(INV1115!=null && INV1115.equalsIgnoreCase("PHC162")){//0 - Not a Verified Case
   		found = true;
   	}
   				
   	return found;   	
   }
   
   
   /**
    * validateRule8: this is the VerCrit Rule 8: 5 - Suspected By Data Entry
    * @param form
    * @return
    */
   
   public static boolean validateRule8(PageForm form){
   	
   	
   	boolean found = false;    	
   	String INV1115 = getValueFromForm(form, "INV1115");//Case Verification
   
   	//Rule 8
   	if(INV1115!=null && INV1115.equalsIgnoreCase("415684004")){//5 - Suspected
   		found = true;
   	}
   				
   	return found;   	
   }
   
   
   
   
    /**
     * checkIfValueExistInRepeatingBlock: this method will return true if any of the rows within the subsection which id is received as a parameter (for example, NBS_UI_GA27017 - Lab Interpretive Test Results)
     * has all the pairs code-values expected. The pairs code / values are stored in a HashMap <String, ArrayList<String>> that way, for a specific code, we can see if it contains any of the values in the ArrayList (or if there's only 1 value in the ArrayList, if it is equal).
     * 
     * The moment one of the rows contains all the code/value (or contain value) pairs, the loop will stop, and the method will return true. If those values are not found within the repeating block, false will be returned.
     * 
     * Example of codes and values arraylists is:
     * 
     * This method has been implemented this way to make it more generic in case in future we can use it.
     * @param form
     * @return
     */
    

    public static boolean checkIfRowValuesExistInRepeatingBlock(PageForm form, String subsectionId, HashMap<String, ArrayList<String>> codeValues){
    	
    	boolean valuesFoundInRepeatingBlock = false;
    	
    	ArrayList<BatchEntry> subsectionElements = form.getBatchEntryMap().get(subsectionId);
	
    	if(subsectionElements!=null){
    		for(int i = 0; i< subsectionElements.size() && !valuesFoundInRepeatingBlock; i++){
    			
    			BatchEntry row = subsectionElements.get(i);
    			HashMap<String, String> map = (HashMap<String, String>)row.getAnswerMap();
    			boolean valuesInARowFound= true;
    			
    			
    			for (Map.Entry<String, ArrayList<String>> entry : codeValues.entrySet()) {
    			    String code = entry.getKey();
    			    boolean notContain = false;
    			    
    			    if(code!=null && code.startsWith("NOT_CONTAIN_")){//The values in the array should not be selected in the repeating block in order to match
    			    	code = code.substring(12);
    			    	notContain = true;
    			    }
    				String value = map.get(code);//from the repeating block
        			String valueString = getCodeFromCodeDescription(value);
        			
    			    ArrayList<String> values = (ArrayList<String>)entry.getValue();
 
    			    if(!notContain){//it should contain the value
    			    	if(values==null || !values.contains(valueString))
    			    		valuesInARowFound=false;
    			    }else//it should not contain the value
    			    	if(values!=null && values.contains(valueString))
    			    		valuesInARowFound=false;
    			}

    			if(valuesInARowFound==true)
    				valuesFoundInRepeatingBlock = true;
    					
    			
    		}
    		
    		
    		
    	}
	
		return valuesFoundInRepeatingBlock;
    }
    
    

    /**
     * getCodeFromCodeDescription: the parameter received is formatted as Code$$Description. This method will return the Code.
     * @param codeDescription
     * @return
     */
    
    public static String getCodeFromCodeDescription (String codeDescription){
    	
    	String testTypeString = "";
		
		if(codeDescription!=null &&  codeDescription.split("\\$\\$")!=null && codeDescription.split("\\$\\$").length>0)
			testTypeString = codeDescription.split("\\$\\$")[0];
    	
    	return testTypeString;
    	
    }
    
    /**
     * validateIfNCriteriasMet: this method will return true if at least N criterias are met. N is received as a parameter.
     * @param form
     * @param criterias
     * @param numberOfCriteriasToMeet
     * @return
     */
    
    public static boolean validateIfNCriteriasMet(PageForm form, HashMap<String, String> criterias, int numberOfCriteriasToMeet){
    	
    	
    	boolean criteriasMet = false;
    	int numberCriteriasMet = 0;
		
    	if(criterias!=null){
			for (Map.Entry<String, String> entry : criterias.entrySet()) {
				
				if(entry!=null){
				    String code = entry.getKey();
				    String value = entry.getValue();
				
			
				    String valueFromForm = getValueFromForm(form,code);
				    
				    if(valueFromForm!=null && valueFromForm.equalsIgnoreCase(value))
				    	numberCriteriasMet++;
				    
				    if(numberCriteriasMet==numberOfCriteriasToMeet){
				    	criteriasMet = true;
				    	break;
				    }
				}
			    
			}
    	}
    	return criteriasMet;    	
    }
    
    /**
     * getValueFromForm: this method will return the value on the page for the code received as a parameter
     * @param form
     * @param code
     * @return
     */
    
    
    public static String getValueFromForm(PageForm form, String code){
    	
	  String value = null;
	  
	  if(form!=null && form.getPageClientVO()!=null){
		  Map<Object, Object> map = form.getPageClientVO().getAnswerMap();

		  if(map!= null){
			  value = (String)map.get(code);		  
		  }
	  }
	  
	  return value;
    }
    
    	
    
    /**
     * validateIfMultiIsPartOfValues: returns true if any of the values selected from the Multi question is part of the array of valid values
     * @param form
     * @param validValues
     * @param code
     * @return
     */
    
    public static boolean validateIfMultiIsPartOfValues(PageForm form, ArrayList<String> validValues, String code){
    	
  	boolean found = false;
  	
    String[] valuesSelected= (String[])form.getPageClientVO().getAnswerArrayMap().get(code);
  
    if(valuesSelected!=null)
	    for(int i = 0; i<valuesSelected.length && !found; i++){
	    	String valueSelected = valuesSelected[i];
	    	if(validValues!=null && validValues.contains(valueSelected))
	    		found = true;
	    	
	    }
  	  return found;
      }
    
    /************************************************************VERCRIT METHODS: end*************************************************************************/
	
    
    
}
		
