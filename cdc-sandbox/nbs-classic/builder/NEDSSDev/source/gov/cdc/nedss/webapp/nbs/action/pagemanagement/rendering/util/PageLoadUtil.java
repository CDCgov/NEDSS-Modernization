package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonReportsSummaryDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSrchResultVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.dt.PrePopMappingDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManager;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerHome;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.SRTFilterKeys;
import gov.cdc.nedss.systemservice.exception.SRTCacheManagerException;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules.PageRulesGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.FileUploadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NavigatorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;




/**
 * Utility class to build PageClientVO from PAMProxyVO(backend VO)
 *
 * @author nmallela
 *         <p>
 *         Copyright: Copyright (c) 2008
 *         </p>
 *         <p>
 *         Company: Computer Sciences Corporation
 *         </p>
 *         PamLoadUtil.java Aug 6, 2008
 * @version
 * @updatedByAuthor Pradeep Sharma
 * @company: SAIC
 * @version 4.5
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class PageLoadUtil {
	static final LogUtils logger = new LogUtils(PageLoadUtil.class.getName());
	public static final String ACTION_PARAMETER = "method";
	public final String HIV = "HIV";
	private Map<Object, Object> questionMap;
	private Map<Object, Object> questionKeyMap;
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private Map<Object, Object> hivQuestionMap;
	private Map<Object, Object> coinfectionQuestionMap;
	private Map<Object, Object> coinfectionQuestionKeyMap;
	

	public  Map<Object, Object> loadQuestions(String invFormCd) throws Exception {

		if (QuestionsCache.dmbMap.containsKey(invFormCd))
			questionMap = (Map<Object, Object>) QuestionsCache.dmbMap
					.get(invFormCd);
		else if (!QuestionsCache.dmbMap.containsKey(invFormCd)
				&& propertyUtil.getServerRestart() != null
				&& propertyUtil.getServerRestart().equals("F"))
			questionMap = (Map<Object, Object>) QuestionsCache
					.getDMBQuestionMapAfterPublish().get(invFormCd);
		else
			questionMap = new HashMap<Object, Object>();
		if (questionMap == null)
			throw new Exception("\n *************** Question Cache for "
					+ invFormCd + " is empty!!! *************** \n");
		logger.debug("********#Question Map Size: " + questionMap.size() + " for form: " + invFormCd +" Question Map :"+questionMap.toString());
		
		return questionMap;
	}

	public void loadQuestionKeys(String invFormCd) throws NEDSSAppException {
		try {

			if (QuestionsCache.dmbMap.containsKey(invFormCd))
				setQuestionMap((Map<Object, Object>) QuestionsCache.dmbMap
						.get(invFormCd));
			else if (!QuestionsCache.dmbMap.containsKey(invFormCd)
					&& propertyUtil.getServerRestart() != null
					&& propertyUtil.getServerRestart().equals("F"))
				setQuestionMap((Map<Object, Object>) QuestionsCache
						.getDMBQuestionMapAfterPublish().get(invFormCd));
			else
				setQuestionMap(new HashMap<Object, Object>());
			if (getQuestionMap() == null)
				throw new Exception("\n *************** Question Cache for "
						+ invFormCd + " is empty!!! *************** \n");
			logger.debug("********#Question Map Size: " + getQuestionMap().size() + " for form: " + invFormCd);
			
			if (getQuestionMap() == null)
				return;
			setQuestionKeyMap(new HashMap<Object, Object>());
			hivQuestionMap = new HashMap<Object, Object>();
			coinfectionQuestionMap = new HashMap<Object, Object>();
			setCoinfectionQuestionKeyMap(new HashMap<Object, Object>());

			Iterator iter = getQuestionMap().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) getQuestionMap()
						.get(key);
				getQuestionKeyMap().put(metaData.getNbsQuestionUid(), key);
				// separate out the HIV information questions
				if (metaData.getSubGroupNm() != null
						&& metaData.getSubGroupNm().trim().equals(HIV)) {
					hivQuestionMap.put(metaData.getNbsQuestionUid(), metaData);
				}
				if (metaData.getCoinfectionIndCd() != null
						&& metaData.getCoinfectionIndCd().equals(
								NEDSSConstants.TRUE)) {
					coinfectionQuestionMap.put(metaData.getQuestionIdentifier(),
							metaData);
					getCoinfectionQuestionKeyMap().put(metaData.getNbsQuestionUid(),
							metaData);
				}
			}
			logger.debug("********#Question Key Map Size: " + getQuestionKeyMap().size() + " for form: " + invFormCd);
		} catch (Exception e) {
			logger.fatal("loadQuestionKeys" +invFormCd);
			throw new NEDSSAppException("Exception thrown for loadQuestionKeys : invFormCd ", e);
		}
		logger.debug("********#Question Key Map Size: " + questionKeyMap.size() + " for form: " + invFormCd + " Question Key Map: "+questionKeyMap.toString());
	}

	/**
	 * This method retrieves the Patient Revision Information on the create load
	 * instance from the backend, constructs and returns a PAMClientVO
	 *
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PageClientVO.PageClientVO
	 */
	public void createLoadUtil(PageForm form, PageActProxyVO pageProxyVO, HttpServletRequest request)
			throws Exception {
		try {
 			form.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			form.getAttributeMap().clear();
			form.setFormFieldMap(new HashMap<Object, Object>());
			form.setErrorTabs(new String[0]);
			form.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			HttpSession session = request.getSession();
			PageClientVO clientVO = new PageClientVO();
			// loadQuestionMap
			String invFormCd = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationFormCd);
			Map<Object, Object> questionMap  = this.loadQuestions(invFormCd);
			form.setPageFormCd(invFormCd);
			form.getAttributeMap().put("header", "Create Investigation");
			form.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
			form.getAttributeMap().put("Submit", "Submit");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");

			Long mprUid = (Long) NBSContext.retrieve(session,
					NBSConstantUtil.DSPersonSummary);
			PersonVO personVO = findMasterPatientRecord(mprUid, session);
			if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,
					NBSOperationLookup.HIVQUESTIONS)) {
				handlePatientHIVQuestions(form, personVO);
			}
			ClientUtil.setPatientInformation(form.getActionMode(), personVO,
					clientVO, request, form.getPageFormCd());
			
			form.setPageClientVO(clientVO);
			setInvInfoForCreate(form, request.getSession());
			String currentTask = this.setContextForCreate(personVO, request, form);
			this.populateLabMorbValues(form, currentTask, request);
			setUpdatedValues(form, request);
			populateContactTracing(form, request);
			this.setJurisdictionForCreate(form, nbsSecurityObj, session);
			// investigation history/audit log summary
			PageProxyVO proxyVO = null;
			DecoratorUtil util = new DecoratorUtil();
			//Passing empty proxyVO to create blank Investigation History table at Add Investigation page
			Collection<Object> emptyCollection=Collections.EMPTY_LIST;
			String invHistoryHTML = util.buildInvHistoryList(emptyCollection);
			request.setAttribute("invHistoryTable", invHistoryHTML);
			//Passing empty ArrayList to create blank  Notification History table at Add Investigation page
			ArrayList<Object> notifSummary = null;
			String notifDisplayHTML = util.buildNotificationList(notifSummary);
			request.setAttribute("notificationListTable", notifDisplayHTML);

		    Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());

			request.setAttribute("addTime", currentTime);
			PageRulesGenerator reUtils = new PageRulesGenerator();
			form.setFormFieldMap(reUtils.initiateForm(questionMap,
					(BaseForm) form, (ClientVO) form.getPageClientVO()));
			// Builds a list of multiselects for the js
			// retrieveMultiSelectQIds(form);
			setCommonSecurityForCreateEditLoad(form,nbsSecurityObj, request,
					null);
			// String invFormCd = (String)
			// NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSInvestigationFormCd);

			String sCurrentTask = NBSContext.getCurrentTask(session);
			if ((sCurrentTask.equals("CreateInvestigation10") || sCurrentTask
					.equals("CreateInvestigation11"))
					&& NBSContext.retrieve(request.getSession(),
							"DSDocumentUID") != null) {
				loadQuestionKeys(invFormCd);
				this.loadCreateFromDocument(form, request);
				setProcessingDecisionAndReferralBasisEICR(form,request);//ND-17832
				setUpdatedValues(form, request);
			} else {
				populateBatchRecords(form, invFormCd, request.getSession(),
						new HashMap());
			}
			
			ClientUtil.setPersonIdDetails(personVO, form);

			try {
				String coinfectionID = ((CoinfectionSummaryVO) NBSContext
						.retrieve(session,
								NBSConstantUtil.DSCoinfectionInvSummVO))
						.getCoinfectionId();
				String investigationType = (String) NBSContext.retrieve(
						session, NBSConstantUtil.DSInvestigationType);
				if (coinfectionID != null
						&& investigationType != null
						&& investigationType
								.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF)) {
					this.loadQuestionKeys(invFormCd);
					this.loadCoinfectionData(form, pageProxyVO, request);
				}
			} catch (Exception ex) {
				logger.debug("Context exception related to co-infection while fetching CoinfectionID and  investigationType from NBSContext Object Store "
						+ ex.getMessage());
				//ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Error while loading Create " + form.getPageFormCd()
					+ " Page: " + e.toString(), e);
			throw new Exception(e.toString());
		}
	}

	/**
	 * This method retrieves fields to populate on the generic window and
	 * constructs a ClientVO for the front end. It assumes the formCd and
	 * businessObjectType are filled in on the form.
	 *
	 * @param form
	 * @param request
	 *
	 */
	public void createGenericLoadUtil(PageForm pageForm,
			HttpServletRequest request) throws Exception {
		try {
			//for SRT dropdown values
		    Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			//Note on edit the next line is reset to the addTime of the event i.e. getInterviewDate()
			request.setAttribute("addTime", currentTime);

			pageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			pageForm.getAttributeMap().clear();
			pageForm.setFormFieldMap(new HashMap<Object, Object>());
			pageForm.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			pageForm.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			PageClientVO clientVO = new PageClientVO();
			pageForm.setPageClientVO(clientVO);
			// loadQuestionMap
			String pageFormCd = pageForm.getPageFormCd();
			logger.debug("createGenericLoadUtil--> Begin loading Page for "
					+ pageForm.getBusinessObjectType() + " Form Cd=: "
					+ pageFormCd);
			this.loadQuestions(pageFormCd);
			this.loadQuestionKeys(pageFormCd);
			// form.getAttributeMap().put("header", "Create Investigation");
			pageForm.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
			pageForm.getAttributeMap().put("Submit", "Submit");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			// use DSPatientRevisionUID or DSPatientPersonUID
			String patientUidStr = null;
			try{
				patientUidStr = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSPatientRevisionUID);
			}catch(NullPointerException ex){
				logger.error("Exception : "+ex.getMessage());
			}
			Long patientUid = null;
			if (patientUidStr == null || patientUidStr.isEmpty()){
				try{
				patientUid = (Long) NBSContext.retrieve(session,NBSConstantUtil.DSPatientPersonUID);
				}catch(NullPointerException ex){
					logger.error("Exception : "+ex.getMessage());
				}
			}
			else
				patientUid = new Long(patientUidStr);

			//Long patientUid = new Long(patientUidStr);
			PersonVO patientVO = null;
			if(patientUid!=null)
				patientVO = findMasterPatientRecord(patientUid, session);
			PersonVO investigatorVO = null;
			investigatorVO = PageManagementCommonActionUtil
					.getInvestigatorIfPresent(session, nbsSecurityObj);
			loadGenericDefaultEntitiesForCreate(pageForm, patientVO,
					investigatorVO);

			PageRulesGenerator reUtils = new PageRulesGenerator();
			pageForm.setFormFieldMap(reUtils.initiateForm(this.loadQuestions(pageFormCd),
					(BaseForm) pageForm, (ClientVO) pageForm.getPageClientVO()));
			if(patientVO!=null)
				ClientUtil.setPatientInformation(pageForm.getActionMode(), patientVO,
					clientVO, request, pageForm.getPageFormCd());
			this.setCommonGenericSecurityForCreateEdit(pageForm, nbsSecurityObj,
					request);
			this.setUpdatedValues(pageForm, request);
			this.populateBatchRecords(pageForm, pageFormCd, request.getSession(),
					new HashMap());
			if(patientVO!=null){
				ClientUtil.setPersonIdDetails(patientVO, pageForm);
				//PageCreateHelper.setPatientForEventCreate(patientUid, tempID, null, pageForm, request, userId);
				populatePatientSummary(pageForm, patientVO, null, pageForm.getBusinessObjectType(), request);
			}
			if( NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(pageForm.getBusinessObjectType())){
				PageManagementCommonActionUtil.setValidationContextForInterview(request);
				PageManagementCommonActionUtil.setCoinfectionContext(request, (ClientVO) pageForm.getPageClientVO(), NEDSSConstants.COINFECTION_FOR_INTERVIEW_EXISTS);
			}else if(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE.equals(pageForm.getBusinessObjectType())){
				LabViewCommonUtil.populatePatientSummary(pageForm, patientVO, null, pageForm.getBusinessObjectType(), request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Error while loading Create generic "
					+ pageForm.getPageFormCd() + " Page: " + e.toString(), e);
			e.printStackTrace();
			throw new Exception(e.toString());
		}
	}

	/**
	 * viewLoadUtil method retrieves the PamProxyVO from the EJB and sets to
	 * PageClientVO, attribute of PageForm
	 *
	 * @param form
	 * @param request
	 */
	public PageProxyVO viewLoadUtil(PageForm form,
			HttpServletRequest request) throws Exception {

		PageProxyVO proxyVO = null;
		try {
			form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			form.setFormFieldMap(new HashMap<Object, Object>());
			form.setErrorTabs(new String[0]);
			form.getStateList();
			form.getCountryList();
			//form.getAttributeMap()
			if (request.getAttribute("mode") == null)
				form.getAttributeMap().clear();
			HttpSession session = request.getSession();
			String invFormCd = form.getPageFormCd();
			this.loadQuestionKeys(invFormCd);
			String contextAction = request.getParameter("ContextAction");
			if (request.getParameter("mode") == null)
				request.getParameterMap().clear();
			String sPublicHealthCaseUID = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationUid);
			request.setAttribute("DSInvUid", sPublicHealthCaseUID);
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			// **Setting the publichealthcaseUid to session. Loosing the value
			// from request
			// ** when it comes to attachment
			request.getSession().setAttribute("DSInvUid", sPublicHealthCaseUID);
			proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());


			Collection<Object> notificationSummaryVOCollection = ((PageActProxyVO) proxyVO)
					.getTheNotificationSummaryVOCollection();
			Iterator<Object> iterNot = notificationSummaryVOCollection
					.iterator();
			while (iterNot.hasNext()) {
				NotificationSummaryVO notificationSummaryVO = (NotificationSummaryVO) iterNot
						.next();
				if (notificationSummaryVO.isHistory != null
						&& !notificationSummaryVO.isHistory.equals("T")
						&& !(notificationSummaryVO
								.getRecordStatusCd()
								.trim()
								.equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) || notificationSummaryVO
								.getRecordStatusCd()
								.trim()
								.equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED))) {
					form.getAttributeMap().put("NotificationExists", "true");
				}

			}
			// Handle HIV Questions
			if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,
					NBSOperationLookup.HIVQUESTIONS)) {
				this.handleHIVQuestions(form, ((PageActProxyVO) proxyVO).getPageVO()
						.getPamAnswerDTMap());
				Map<Object, Object> batchMap = this.findBatchRecords(invFormCd,
						session);
				this.handleHIVBatchQuestions(form, batchMap,
						((PageActProxyVO) proxyVO).getPageVO()
								.getPageRepeatingAnswerDTMap());
				handlePatientHIVQuestions(form,
						getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO));
			}
			// Load common PAT, INV answers and put it in answerMap for UI &
			// Rules to work
			this.setCommonAnswersForViewEdit(form, proxyVO, request);
			// Pam Specific Answers
			this.setMSelectCBoxAnswersForViewEdit(form,
					this.updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
							.getPamAnswerDTMap()));
			logger.debug("********#Answer Map Size :"
					+ ((PageActProxyVO) proxyVO).getPageVO().getPamAnswerDTMap().size() + " for Investigation ID: "
					+ ((PageActProxyVO) proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
			// set PageProxyVO to ClientVO
			form.getPageClientVO().setOldPageProxyVO(proxyVO);
			Map<Object, Object> map = setContextForView(form, contextAction,
					request, request.getSession());
			populatePageAssocations(proxyVO, sPublicHealthCaseUID, map,
					request, form);
			this.setCommonSecurityForView(form, proxyVO, nbsSecurityObj, request);
			
			String programAreaCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getProgAreaCd();
			boolean stdProgramArea = false;
			if (programAreaCd != null)
				stdProgramArea = PropertyUtil.isStdOrHivProgramArea(programAreaCd) ;
			// Co-infection link in header
			Long phcUid=Long.parseLong(sPublicHealthCaseUID);
			StringBuffer coInfString = new StringBuffer("");
			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
			if (stdProgramArea) {
				ArrayList<Object> coInfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListPHC(phcUid, request);
				if (coInfectionInvList != null && coInfectionInvList.size() > 1) {
					for (Object coInfectionSummaryVO : coInfectionInvList) {
						String pageCondition = (String) proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT()
								.getCdDescTxt();
						String phcLink = "<a  href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ map.get("ContactCase").toString() + "&publicHealthCaseUID="
								+ String.valueOf(((CoinfectionSummaryVO) coInfectionSummaryVO).getPublicHealthCaseUid())
								+ "\">" + ((CoinfectionSummaryVO) coInfectionSummaryVO).getCondition() + "</a>";
						if (!pageCondition.equals(((CoinfectionSummaryVO) coInfectionSummaryVO).getCondition())) {
							coInfString.append(phcLink).append(" | ");
						}
					}
				}
				if (coInfectionInvList != null && coInfectionInvList.size() > 1 && coInfString != null
						&& coInfString.length() >= 3) {
					coInfString.setLength(coInfString.length() - 3);
				}
				form.getAttributeMap().put("coInfectionConditionList", coInfString.toString());

			}
			// check if an Interview Page is present for this condition
			String interviewFormCd = PageManagementCommonActionUtil
					.checkIfPublishedPageExists(request,
							NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
			if (interviewFormCd != null && !interviewFormCd.isEmpty()) {
				form.getAttributeMap().put("InterviewPageExists", "true");
			}

			// set the notification status details in request
			ArrayList<Object> nsColl = (ArrayList<Object>) ((PageActProxyVO) proxyVO)
					.getTheNotificationSummaryVOCollection();
			Iterator<Object> nsCollIter = nsColl.iterator();
			logger.info("# of notifications = " + nsColl.size());
			if (nsColl.size() > 0) {
				// get the status and date of the first notification in the
				// collection (i.e., the latest one)
				// and set it in request scope
				Timestamp latestStatusTime = null;
				String latestStatusCode = null;
				while (nsCollIter.hasNext()) {
					NotificationSummaryVO sVO = (NotificationSummaryVO) nsCollIter
							.next();
					if (sVO.getCdNotif()!=null && sVO.getCdNotif().equalsIgnoreCase(
							NEDSSConstants.CLASS_CD_NOTF)) {
						latestStatusTime = ((NotificationSummaryVO) nsColl
								.get(0)).getRecordStatusTime();
						latestStatusCode = ((NotificationSummaryVO) nsColl
								.get(0)).getRecordStatusCd();
						if (sVO.getRecordStatusTime() != null) {
							if (sVO.getRecordStatusTime().after(
									latestStatusTime)) {
								latestStatusTime = sVO.getRecordStatusTime();
								latestStatusCode = sVO.getRecordStatusCd();
							}
						}
					}
				}
				logger.info("latestStatusCode = " + latestStatusTime
						+ "; latestStatusTime = "
						+ StringUtils.formatDate(latestStatusTime));
				request.setAttribute("notificationStatus", latestStatusCode);
				request.setAttribute("notificationDate",
						StringUtils.formatDate(latestStatusTime));

			}

			_loadEntities(form, proxyVO, request);
			fireRulesOnViewLoad(form);
			if (form.getPageClientVO().getAnswer("INV154") == null)
				form.getAttributeMap().put("INV156_STATE",
						PropertyUtil.getInstance().getNBS_STATE_CODE());
			else
				form.getAttributeMap().put("INV156_STATE",
						form.getPageClientVO().getAnswer("INV154"));

			if (form.getPageClientVO().getAnswer("INV154") == null)
				form.setDwrStateSiteCounties(CachedDropDowns
						.getCountyCodes(PropertyUtil.getInstance()
								.getNBS_STATE_CODE()));
			else
				form.setDwrStateSiteCounties(CachedDropDowns
						.getCountyCodes(form.getPageClientVO().getAnswer(
								"INV154")));

			if (form.getPageClientVO().getAnswer("DEM162") == null)
				form.getAttributeMap().put("DEM165_STATE",
						PropertyUtil.getInstance().getNBS_STATE_CODE());
			else
				form.getAttributeMap().put("DEM165_STATE",
						form.getPageClientVO().getAnswer("DEM162"));


			if (form.getPageClientVO().getAnswer("DEM162_W") == null)
                form.getAttributeMap().put("DEM165_W_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
            else
                form.getAttributeMap().put("DEM165_W_STATE", form.getPageClientVO().getAnswer("DEM162_W"));

			if (form.getPageClientVO().getAnswer("MTH166") == null)
                form.getAttributeMap().put("MTH168_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
            else
                form.getAttributeMap().put("MTH168_STATE", form.getPageClientVO().getAnswer("MTH166"));

			//State and County of Exposure
			if (form.getPageClientVO().getAnswer("INV503") == null)
                form.getAttributeMap().put("INV505_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
            else
                form.getAttributeMap().put("INV505_STATE", form.getPageClientVO().getAnswer("INV503"));

			//Reporting county
                form.getAttributeMap().put("NOT113_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());


			if (form.getPageClientVO().getAnswer("DEM162") == null)
				form.setDwrStateSiteCounties(CachedDropDowns
						.getCountyCodes(PropertyUtil.getInstance()
								.getNBS_STATE_CODE()));
			else
				form.setDwrStateSiteCounties(CachedDropDowns
						.getCountyCodes(form.getPageClientVO().getAnswer(
								"DEM162")));
			populateBatchRecords(form, invFormCd, request.getSession(),
					((PageActProxyVO) proxyVO).getPageVO()
							.getPageRepeatingAnswerDTMap());
			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);
			ClientUtil.setPersonIdDetails(personVO, form);
			// condition link from Associate Lab or Morb to one or more
			// Investigations
			// no buttons on this one...
			if (sCurrentTask.equalsIgnoreCase("ViewInvestigation7")
					|| sCurrentTask.equalsIgnoreCase("ViewInvestigation8")
					|| sCurrentTask.equalsIgnoreCase("ViewInvestigation9")
					|| sCurrentTask.equalsIgnoreCase("ViewInvestigation10")
					|| sCurrentTask.equalsIgnoreCase("ViewInvestigation11")
					|| sCurrentTask.equalsIgnoreCase("ViewInvestigation12")) {
				form.getSecurityMap().put("editInv", "false");
				form.getSecurityMap().put("checkManageEvents", "false");
				form.getSecurityMap().put("deleteInvestigation", "false");
				request.setAttribute("checkToAddContactTracing", "false");
				request.setAttribute("manageAssoPerm", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.viewLoadUtil: PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

		return proxyVO;
	}


	/**
	 * viewGenericLoadUtil method retrieves the PamProxyVO from the EJB and sets
	 * to PageClientVO answers and attribute map of PageForm Note that the
	 * actUid and businessObjectType are expected to be passed in request params
	 *
	 * @param form
	 * @param request
	 * @returns PageProxyVO
	 */
	public PageProxyVO viewGenericLoadUtil(PageForm form,
			HttpServletRequest request) throws Exception {
		PageActProxyVO proxyVO = null;
		try {


			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			form.getAttributeMap().clear();
			// Init form
			form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			form.setFormFieldMap(new HashMap<Object, Object>());
			form.setErrorTabs(new String[0]);
			// Get the published pageFormCd for the current condition and passed
			// bus obj type
			String businessObjectType = form.getBusinessObjectType();
			String formCd = form.getPageFormCd();
			logger.debug("viewGenericLoadUtil--> Begin loading View Page for "
					+ businessObjectType + " Form Cd=: " + formCd);
			// Get the metadata
			this.loadQuestionKeys(formCd);
			String contextAction = request.getParameter("ContextAction");
			// Not sure if we need associated PHC..
			// String sPublicHealthCaseUID =
			// (String)NBSContext.retrieve(session,
			// NBSConstantUtil.DSInvestigationUid);
			// request.setAttribute("DSInvUid", sPublicHealthCaseUID);
			// The act uid (i.e Interview) must be passed in..
			String actUidStr = request.getParameter("actUid");
			// if parameter not there, could be coming from Edit or Create to
			// View
			if (actUidStr == null && form.getPageClientVO() != null
					&& form.getPageClientVO().getOldPageProxyVO() != null) {
				if (businessObjectType
						.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE)) {
					Long actUid = form.getPageClientVO().getOldPageProxyVO()
							.getInterviewVO().getTheInterviewDT()
							.getInterviewUid();
					if (actUid != null)
						actUidStr = actUid.toString();
				} else if (businessObjectType
						.equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE)) {
					Long actUid = form.getPageClientVO().getOldPageProxyVO()
							.getInterventionVO()
							.getTheInterventionDT()
							.getInterventionUid();
					if (actUid != null)
						actUidStr = actUid.toString();
				}
			}
			proxyVO = (PageActProxyVO) getProxyObject(actUidStr,
					form.getBusinessObjectType(), session);
			// the jsp page gets and puts questions to clientVO answer map
			// and also the attribute map for Participations and header items
			PageClientVO clientVO = new PageClientVO();
			form.setPageClientVO(clientVO);
			// Look for any participations in the question map and load them to
			// the attribute map
			this.loadGenericEntities((BaseForm) form, proxyVO, this.getQuestionMap(), request);
			// Handle HIV Questions
			if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,
					NBSOperationLookup.HIVQUESTIONS)) {
				this.handleHIVQuestions(form, ((PageActProxyVO) proxyVO).getPageVO()
						.getAnswerDTMap());
				Map<Object, Object> batchMap = this.findBatchRecords(formCd, session);
				this.handleHIVBatchQuestions(form, batchMap,
						((PageActProxyVO) proxyVO).getPageVO()
								.getPageRepeatingAnswerDTMap());
				// handlePatientHIVQuestions(form,
				// getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO));
			}
			// Load answers for Business Object Type and put it in answerMap for
			// UI & Rules to work
			//PageManagementCommonActionUtil.setCommonAnswersForGenericViewEdit(
			//		form, proxyVO, request);

			// Page Specific Answers from associated answer table
			this.setMSelectCBoxAnswersForViewEdit((BaseForm) form,
					this.updateMapWithQIds(proxyVO.getPageVO().getAnswerDTMap()),
					(ClientVO) form.getPageClientVO());
			// Get repeating subsection data
			fireRulesOnViewLoad(form);
			this.populateBatchRecords(form, formCd, session, proxyVO.getPageVO()
					.getPageRepeatingAnswerDTMap());


			// save PageProxyVO to ClientVO
			form.getPageClientVO().setOldPageProxyVO(proxyVO);

			if (NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()))
				PageManagementCommonActionUtil
					.setCoinfectionContextForView( request, form, businessObjectType, actUidStr, NEDSSConstants.COINFECTION_INV_EXISTS);
			if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType())){
				//Set patient info to view
				//PersonVO personVO = getPersonVO(NEDSSConstants.SUBJECT_OF_VACCINE, proxyVO);
				//Long patientUid = getPatientIdFromNBSActEntity(proxyVO);
				//PersonVO patientVO = findPatientRecord(patientUid, session);
				//Long patientUid = personVO.getThePersonDT().getPersonUid();
				//PersonVO patientVO = findPatientRecord(patientUid, session);
				PersonVO personVO = this.getGenericPersonVO(NEDSSConstants.SUBJECT_OF_VACCINE, proxyVO);
				if(personVO!=null){
					ClientUtil.setPatientInformation(form.getActionMode(), personVO,clientVO, request, form.getPageFormCd());
					populatePatientSummary(form, personVO, proxyVO, form.getBusinessObjectType(), request);
					ClientUtil.setPersonIdDetails(personVO, form);
				}
			}
			// Load answers for Business Object Type and put it in answerMap for
			// UI & Rules to work
			PageManagementCommonActionUtil.setCommonAnswersForGenericViewEdit(
					form, proxyVO, request);
			// set permissions for Edit/Delete buttons
			setCommonGenericSecurityForView(form, nbsSecurityObj, request);

			request.setAttribute("actUid", actUidStr);
			String genericViewEditUrlStr = "/nbs/PageAction.do?method=editGenericLoad&mode=Edit&businessObjectType="
					+ businessObjectType + "&actUid=" + actUidStr;
			request.setAttribute("genericViewEditUrl", genericViewEditUrlStr);
			String genericViewDeleteUrlStr = "/nbs/PageAction.do?method=deleteGenericSubmit&businessObjectType="
					+ businessObjectType + "&actUid=" + actUidStr;
			request.setAttribute("genericViewDeleteUrl",
					genericViewDeleteUrlStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.viewGenericLoadUtil: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
		return (PageProxyVO) proxyVO;
	}

	/**
	 * loadGenericEntities() retrieves Participations' with types of PRVs and
	 * ORGs associated with the Form ToDoo: Add PLC (Place) support.
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 * @throws Exception
	 */
	public TreeMap<Object, Object> loadGenericEntities(BaseForm form, PageProxyVO proxyVO, Map<Object, Object> theQuestionMap, HttpServletRequest request) throws Exception
    {
		TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
		try {
			//TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
			String subjectClassCd = "";
			Collection<Object> participantList = new ArrayList<Object>();
			Iterator quesIt = this.getQuestionMap().values().iterator();
			while (quesIt.hasNext())
			{
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) quesIt.next();
				if (StringUtils.isEmpty(metaData.getPartTypeCd()) || !"PART".equalsIgnoreCase(metaData.getDataType() ) )
					continue;
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
						.get(metaData.getPartTypeCd()+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+metaData.getQuestionIdentifier());
				if (parTypeVO != null)
					subjectClassCd = parTypeVO.getSubjectClassCd();
				else
				{
					subjectClassCd = "";
					logger.debug("loadGenericEntities: Found an unknown participation type while loading View? For "
							+ metaData.getPartTypeCd());
				}
				if (NEDSSConstants.CLASS_CD_PSN.equalsIgnoreCase(subjectClassCd))
				{
					PersonVO personVO = getGenericPersonVO(metaData.getPartTypeCd(), proxyVO);
					// handle person
					if (personVO != null && personVO.getThePersonDT().getPersonUid()>0)
					{
						String uidSt = personVO.getThePersonDT().getPersonUid()
								.toString()
								+ "|"
								+ personVO.getThePersonDT().getVersionCtrlNbr()
										.toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid", uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display=helper.makePRVDisplayString(personVO, true);
						String display1=helper.makePRVDisplayString(personVO);
						
						if (personVO.getThePersonDT().getCd() != null
								&& personVO.getThePersonDT().getCd()
										.equalsIgnoreCase(NEDSSConstants.PAT)){
							String display2=helper.makePatientDisplayString(personVO);
							form.getAttributeMap().put(
									parTypeVO.getQuestionIdentifier()
											+ "SearchResult",display2);
						}
						else
							form.getAttributeMap().put(
									parTypeVO.getQuestionIdentifier()
											+ "SearchResult",display1);
						if (parTypeVO.getTypeCd() != null
								&& !parTypeVO.getTypeDescTxt().toUpperCase()
										.contains("SUBJECT")) {
							ParticipantListDisplay participant = new ParticipantListDisplay();
							participant.setTitle(parTypeVO.getTypeDescTxt());
							participant.setDetail(display);
							participantList.add(participant);
						}
					}
				}
				else if ( NEDSSConstants.CLASS_CD_ORG.equals(subjectClassCd))
				{
					OrganizationVO organizationVO = getGenericOrganizationVO( parTypeVO.getTypeCd(), form.getBusinessObjectType(), proxyVO);
					if (organizationVO != null)
					{
						String uidSt = organizationVO.getTheOrganizationDT()
								.getOrganizationUid().toString()
								+ "|"
								+ organizationVO.getTheOrganizationDT()
										.getVersionCtrlNbr().toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid", uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display=helper.makeORGDisplayString(organizationVO,true);
						String display1=helper.makeORGDisplayString(organizationVO);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "SearchResult",
								display1);
						
						ParticipantListDisplay participant = new ParticipantListDisplay();
						participant.setTitle(parTypeVO.getTypeDescTxt());
						participant.setDetail(display);
						participantList.add(participant);
					}
				} else
					logger.debug("Unsupported subjectClassCd loading View -> "
							+ subjectClassCd);
				if (participantList.size() > 0) {
					String sortMethod = "getTitle";
					NedssUtils util = new NedssUtils();
					util.sortObjectByColumn(sortMethod, participantList);
					request.setAttribute("participantList", participantList);
				}

			} // while Ques Map has next..
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.loadGenericEntities: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
		return participationTypeCaseMap;
	}

	/**
	 * loadGenericEntitiesforCreate() Load the Patient and Investigator
	 * information into the Attribute Map if present
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 * @throws Exception
	 */
	private static void loadGenericDefaultEntitiesForCreate(BaseForm form,
			PersonVO patientVO, PersonVO investigatorVO) throws Exception {
		try {
			if(form.getGenericType()!=null && form.getGenericType().equals(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE)){
				if (patientVO != null ) {
					String uidSt = patientVO.getThePersonDT().getPersonUid()
							.toString()
							+ "|"
							+ patientVO.getThePersonDT().getVersionCtrlNbr()
									.toString();
					form.getAttributeMap().put(PageConstants.LAB_PATIENT + "Uid",
							uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					form.getAttributeMap().put(
							PageConstants.LAB_PATIENT + "SearchResult",
							helper.makePatientDisplayString(patientVO));
				}
				
			}else {
				if (patientVO != null ) {
					String uidSt = patientVO.getThePersonDT().getPersonUid()
							.toString()
							+ "|"
							+ patientVO.getThePersonDT().getVersionCtrlNbr()
									.toString();
					form.getAttributeMap().put(PageConstants.INTERVIEWEE + "Uid",
							uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					form.getAttributeMap().put(
							PageConstants.INTERVIEWEE + "SearchResult",
							helper.makePatientDisplayString(patientVO));
				}
				if (investigatorVO != null  && patientVO.getThePersonDT().getPersonUid()>0) {
					String uidSt = investigatorVO.getThePersonDT().getPersonUid()
							.toString()
							+ "|"
							+ patientVO.getThePersonDT().getVersionCtrlNbr()
									.toString();
					form.getAttributeMap().put(PageConstants.INTERVIEWER + "Uid",
							uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					form.getAttributeMap().put(
							PageConstants.INTERVIEWER + "SearchResult",
							helper.makePRVDisplayString(investigatorVO));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.loadGenericDefaultEntitiesForCreate: PersonUid: " + patientVO.getThePersonDT().getPersonUid() + ", PageFormCd: " + form.getPageFormCd() + ", Investigator PersonUid: " + investigatorVO.getThePersonDT().getPersonUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * _loadEntities retrieves Participations' and NBSActEntities' with types of
	 * PRVs and ORGs associated with Tuberculosis
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 * @throws Exception
	 */
	public static void _loadEntities(PageForm form, PageProxyVO proxyVO,
			HttpServletRequest request) throws NEDSSAppException {

		try {
			// Put Investigator Name in the map
			ArrayList<Object> participantList = new ArrayList<Object>();
			PersonVO investigatorPersonVO = getPersonVO(
					NEDSSConstants.PHC_INVESTIGATOR, proxyVO);
			if (investigatorPersonVO != null) {
				StringBuffer stBuff = new StringBuffer("");
				if (investigatorPersonVO.getThePersonNameDTCollection() != null) {

					Iterator personNameIt = investigatorPersonVO
							.getThePersonNameDTCollection().iterator();

					while (personNameIt.hasNext()) {
						PersonNameDT personNameDT = (PersonNameDT) personNameIt
								.next();
						if (personNameDT.getNmUseCd()!=null && personNameDT.getNmUseCd().equalsIgnoreCase("L")) {

							stBuff.append((personNameDT.getFirstNm() == null) ? ""
									: (personNameDT.getFirstNm() + " "));
							stBuff.append((personNameDT.getLastNm() == null) ? ""
									: (personNameDT.getLastNm()));
						}
					}
				}
				form.getAttributeMap().put("investigatorName",
						stBuff.toString());
			} // InvestgrOfPHC

			// ///////////////////////////////////////////////////////////////////////////////
			// Iterate through the Case Participation Types to see if they are
			// present
			// and if they are put them in the attribute map
			// ///////////////////////////////////////////////////////////////////////////////
			TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns
					.getParticipationTypeList();
			Iterator parTypeIt = participationTypeCaseMap.values().iterator();
			while (parTypeIt.hasNext()) {
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) parTypeIt
						.next();
				// handle person
				if (parTypeVO.getSubjectClassCd().equals(
						NEDSSConstants.CLASS_CD_PSN)) {
					PersonVO personVO = getPersonVO(parTypeVO.getTypeCd(),
							proxyVO);
					if (personVO != null && personVO.getThePersonDT().getPersonUid()>0) {
						String uidSt = personVO.getThePersonDT().getPersonUid()
								.toString()
								+ "|"
								+ personVO.getThePersonDT().getVersionCtrlNbr()
										.toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid",
								uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display = helper.makePRVDisplayString(personVO,true);
						String display1 = helper.makePRVDisplayString(personVO);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier()
										+ "SearchResult",display1);
						if (parTypeVO.getTypeCd() != null
								&& !parTypeVO.getTypeDescTxt().toUpperCase()
								.contains("SUBJECT")) {
							ParticipantListDisplay participant = new ParticipantListDisplay();
							participant.setTitle(parTypeVO.getTypeDescTxt());
							participant.setDetail(display);
							participantList.add(participant);
						}
					}
				} else if (parTypeVO.getSubjectClassCd().equals(
						NEDSSConstants.CLASS_CD_ORG)) {
					OrganizationVO organizationVO = getOrganizationVO(
							parTypeVO.getTypeCd(), proxyVO);
					if (organizationVO != null) {
						String uidSt = organizationVO.getTheOrganizationDT()
								.getOrganizationUid().toString()
								+ "|"
								+ organizationVO.getTheOrganizationDT()
										.getVersionCtrlNbr().toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid",
								uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display = helper.makeORGDisplayString(organizationVO,true);
						String display1 = helper.makeORGDisplayString(organizationVO);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier()
										+ "SearchResult",
										display1);
						
						ParticipantListDisplay participant = new ParticipantListDisplay();
						participant.setTitle(parTypeVO.getTypeDescTxt());
						participant.setDetail(display);
						participantList.add(participant);
					}

				} else
					logger.debug("_loadEntities: Found an unknown participation type while loading View??");
				
				if (participantList.size() > 0) {
					String sortMethod = "getTitle";
					NedssUtils util = new NedssUtils();

					util.sortObjectByColumn(sortMethod, participantList);
					request.setAttribute("participantList", participantList);
				}
			} // while
		} catch (Exception e) {
			logger.fatal("Exception occured in PageLoadUtil._loadEntities: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	} // _loadEntities()


	/**
	 * editLoadUtil method retrieves the PageProxyVO from the EJB and sets to
	 * PageClientVO, attribute of PageForm
	 *
	 * @param form
	 * @param request
	 */
	public PageProxyVO editLoadUtil(PageForm form,
			HttpServletRequest request) throws Exception {

		PageProxyVO proxyVO = null;

		try {
			form.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			form.getAttributeMap().put("ReadOnlyJursdiction",
					"ReadOnlyJursdiction");
			form.setFormFieldMap(new HashMap<Object, Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			String invFormCd = form.getPageFormCd();
			this.loadQuestionKeys(invFormCd);
			form.setPageFormCd(invFormCd);
			form.getAttributeMap().put("header", "Edit Investigation");
			form.getAttributeMap().put(ACTION_PARAMETER, "editSubmit");
			String sPublicHealthCaseUID = (String) NBSContext.retrieve(
					request.getSession(), NBSConstantUtil.DSInvestigationUid);
			proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());
			String noReqNotifCheck = (String) form.getAttributeMap().get(
					PageConstants.NO_REQ_FOR_NOTIF_CHECK);
			if (!(noReqNotifCheck != null && noReqNotifCheck.equals("false")))
				this.setNNDIndicator(proxyVO, form);
			if (!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,
					NBSOperationLookup.HIVQUESTIONS)) {
				this.handleHIVQuestions(form, ((PageActProxyVO) proxyVO).getPageVO()
						.getPamAnswerDTMap());
				@SuppressWarnings("unchecked")
				Map<Object, Object> batchMap = this.findBatchRecords(invFormCd,
						session);
				this.handleHIVBatchQuestions(form, batchMap,
						((PageActProxyVO) proxyVO).getPageVO()
								.getPageRepeatingAnswerDTMap());
				this.handlePatientHIVQuestions(form,
						getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO));
			}

			//the parameter will only be there if change condition
			String conditionCd = (String)request.getParameter("newCondition");
			if(conditionCd==null || conditionCd.trim().equals("")){
				conditionCd = ((PageActProxyVO) proxyVO)
						.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			}
			else{//for change condition
				PublicHealthCaseDT phcDT = ((PageActProxyVO) proxyVO)
				.getPublicHealthCaseVO().getThePublicHealthCaseDT();
				phcDT.setCd(conditionCd);
				phcDT.setCdDescTxt(CachedDropDowns.getConditionDesc(conditionCd));
				CachedDropDownValues cdv = new CachedDropDownValues();
				String programArea = cdv.getProgramAreaCd(conditionCd);
				phcDT.setProgAreaCd(programArea);
				phcDT.setCaseClassCd(null);
			}

			// Display Read-Only CoInfection Conditions
			Long phcUid=Long.parseLong(sPublicHealthCaseUID);
			StringBuffer coInfString = new StringBuffer("");
			String programAreaCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getProgAreaCd();
			boolean stdProgramArea = false;
			if (programAreaCd != null)
				stdProgramArea = PropertyUtil.isStdOrHivProgramArea(programAreaCd) ;
			if (stdProgramArea) {
				ArrayList<Object> coInfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListPHC(phcUid, request);
				if (coInfectionInvList != null && coInfectionInvList.size() > 1) {
					for (Object coInfectionSummaryVO : coInfectionInvList) {
						String pageCondition = (String) proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT()
								.getCdDescTxt();
						String phcLink = ((CoinfectionSummaryVO) coInfectionSummaryVO).getCondition();
						if (!pageCondition.equals(((CoinfectionSummaryVO) coInfectionSummaryVO).getCondition())) {
							coInfString.append(phcLink).append(" | ");
						}
					}
				}
				if (coInfectionInvList != null && coInfectionInvList.size() > 1 && coInfString.length() > 2) {
					coInfString.setLength(coInfString.length() - 3);
				}
				form.getAttributeMap().put("coInfectionConditionList", coInfString.toString());
			}

			// DEM, INV specific common answers back to pamclientvo to support
			// UI / Rules
			this.setCommonAnswersForViewEdit(form, proxyVO, request);
			// Pam Specific Answers
			/**
			 * Pradeep : Commented out the following code as this seems odd as the same call is made twince in the same method
			setMSelectCBoxAnswersForViewEdit(form,
					updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
							.getPamAnswerDTMap(), pageLoadUtil.questionKeyMap));
			*/
			logger.debug("********#Answer Map Size :"
					+ ((PageActProxyVO) proxyVO).getPageVO().getPamAnswerDTMap().size() + " for Investigation ID: "
					+ ((PageActProxyVO) proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
			// set PageProxyVO to ClientVO
			form.getPageClientVO().setOldPageProxyVO(proxyVO);

			request.setAttribute("addTime", proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime());
			// Supplemental Info
			Map<Object, Object> map = new HashMap<Object, Object>();
			this.populatePageAssocations(proxyVO, sPublicHealthCaseUID, map,
					request, form);
			String sCurrentTask = setContextForEdit(form, request,
					request.getSession());
			form.getAttributeMap().put("sCurrentTask", sCurrentTask);
			this.setUpdatedValues(form, request);
			this.setJurisdictionForEdit(form, nbsSecurityObj, proxyVO);

			// set the notification status details in request
			ArrayList<Object> nsColl = (ArrayList<Object>) ((PageActProxyVO) proxyVO)
					.getTheNotificationSummaryVOCollection();
			Iterator<Object> nsCollIter = nsColl.iterator();
			logger.info("# of notifications = " + nsColl.size());
			if (nsColl.size() > 0) {
				// get the status and date of the first notification in the
				// collection (i.e., the latest one)
				// and set it in request scope
				Timestamp latestStatusTime = null;
				String latestStatusCode = null;
				while (nsCollIter.hasNext()) {
					NotificationSummaryVO sVO = (NotificationSummaryVO) nsCollIter
							.next();
					if (sVO.getCdNotif()!=null && sVO.getCdNotif().equalsIgnoreCase(
							NEDSSConstants.CLASS_CD_NOTF)) {
						latestStatusTime = ((NotificationSummaryVO) nsColl
								.get(0)).getRecordStatusTime();
						latestStatusCode = ((NotificationSummaryVO) nsColl
								.get(0)).getRecordStatusCd();
						if (sVO.getRecordStatusTime().after(latestStatusTime)) {
							latestStatusTime = sVO.getRecordStatusTime();
							latestStatusCode = sVO.getRecordStatusCd();
						}

					}
				}
				logger.info("latestStatusCode = " + latestStatusTime
						+ "; latestStatusTime = "
						+ StringUtils.formatDate(latestStatusTime));
				request.setAttribute("notificationStatus", latestStatusCode);
				request.setAttribute("notificationDate",
						StringUtils.formatDate(latestStatusTime));
			}
			this.setCommonSecurityForCreateEditLoad(form, nbsSecurityObj, request,
					proxyVO);

			_loadEntities(form, proxyVO, request);
			this.setMSelectCBoxAnswersForViewEdit(form,
					this.updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
							.getPamAnswerDTMap()));
			this.fireRulesOnEditLoad(form, request);

			String conditionDesc = CachedDropDowns.getConditionDesc(conditionCd);
			form.setPageTitle("Edit Investigation: " + conditionDesc, request);
			this.populateBatchRecords(form, invFormCd, request.getSession(),
					((PageActProxyVO) proxyVO).getPageVO()
							.getPageRepeatingAnswerDTMap());
			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);
			ClientUtil.setPersonIdDetails(personVO, form);
			return proxyVO;

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.editLoadUtil: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	public PageProxyVO editGenericLoadUtil(PageForm form,
			HttpServletRequest request) throws Exception {

		PageActProxyVO proxyVO = null;

		try {
			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			form.getAttributeMap().clear();
			PageClientVO clientVO = new PageClientVO();
			form.setPageClientVO(clientVO);
			form.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			form.setFormFieldMap(new HashMap<Object, Object>());
			form.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			form.setErrorTabs(new String[0]);
			String businessObjectType = form.getBusinessObjectType();
			String formCd = form.getPageFormCd();
			logger.debug("editGenericLoadUtil--> Begin loading Edit Page for "
					+ businessObjectType + " Form Cd=: " + formCd);


			this.loadQuestionKeys(formCd);
			// form.setContactFormCd(invFormCd);
			// form.getAttributeMap().put("header", "Edit Interview");
			form.getAttributeMap().put(ACTION_PARAMETER, "editGenericSubmit");
			String actUidStr = request.getParameter("actUid");
			proxyVO = (PageActProxyVO) getProxyObject(actUidStr,
					form.getBusinessObjectType(), session);
			// save PageProxyVO to ClientVO
			form.getPageClientVO().setOldPageProxyVO(proxyVO);
			// Look for any participations in the question map and load them to
			// the attribute map
			this.loadGenericEntities((BaseForm) form, proxyVO, this.getQuestionMap(),request);
			// DEM, IXS specific common answers back to clientVO to support UI /
			// Rules
			PageManagementCommonActionUtil.setCommonAnswersForGenericViewEdit(
					form, proxyVO, request);
			// Page Specific Answers from associated answer table
			this.setMSelectCBoxAnswersForViewEdit((BaseForm) form,
					this.updateMapWithQIds(proxyVO.getPageVO().getAnswerDTMap()),
					(ClientVO) form.getPageClientVO());
			// String userId =
			// nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			//setUpdatedValues((BaseForm) form, request, clientVO);

			this.fireRulesOnEditLoad((BaseForm) form, clientVO, request);
			this.populateBatchRecords(form, form.getPageFormCd(), session,
					((PageActProxyVO) proxyVO).getPageVO()
							.getPageRepeatingAnswerDTMap());

			if( NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(form.getBusinessObjectType()))
				PageManagementCommonActionUtil.setValidationContextForInterview(request);
			else if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType())){
				//Long patientUid = getPatientIdFromNBSActEntity(proxyVO);
				//PersonVO patientVO = findPatientRecord(patientUid, session);
				PersonVO personVO = this.getGenericPersonVO(NEDSSConstants.SUBJECT_OF_VACCINE, proxyVO);
				if(personVO!=null){
					ClientUtil.setPatientInformation(form.getActionMode(), personVO,clientVO, request, form.getPageFormCd());
					this.setUpdatedValues(form, request);
					populatePatientSummary(form, personVO, proxyVO, form.getBusinessObjectType(), request);
					ClientUtil.setPersonIdDetails(personVO, form);
				}
				if (proxyVO.getInterventionVO() != null && proxyVO.getInterventionVO().getTheInterventionDT() !=null && proxyVO.getInterventionVO().getTheInterventionDT().getAddTime() != null)
					request.setAttribute("addTime", proxyVO.getInterventionVO().getTheInterventionDT().getAddTime());  //for SRT dropdown effective date functionality
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.editGenericLoadUtil: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
		return proxyVO;
	}

	/**
	 * Fires the rules on View Page. Called by appropriate PAMs
	 *
	 * @param form
	 * @throws NEDSSAppException
	 */
	public void fireRulesOnViewLoad(PageForm form) throws NEDSSAppException {

		try {
			PageRulesGenerator reUtils = new PageRulesGenerator();
			String formCd = form.getPageFormCd();
			form.setFormFieldMap(reUtils.initiateForm(this.loadQuestions(formCd), (BaseForm) form,
					(ClientVO) form.getPageClientVO()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageLoadUtil.fireRulesOnViewLoad: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * Fires the rules on Edit Page. Called by appropriate PAMs
	 *
	 * @param form
	 * @throws NEDSSAppException
	 */
	public void fireRulesOnEditLoad(PageForm form,HttpServletRequest req)
			throws NEDSSAppException {

		try {
			if (form.getAttributeMap().containsKey(PageConstants.REQ_FOR_NOTIF)
					&& (form.getAttributeMap().containsKey(
							PageConstants.NO_REQ_FOR_NOTIF_CHECK) && form
							.getAttributeMap()
							.get(PageConstants.NO_REQ_FOR_NOTIF_CHECK)
							.equals("false"))) {
				// Also retrieve the NotifReqMap from session and put it in
				// attributeMap
				Map<Object, Object> notifReqMap = req.getSession()
						.getAttribute("NotifReqMap") == null ? new TreeMap<Object, Object>()
						: (TreeMap<Object, Object>) req.getSession()
								.getAttribute("NotifReqMap");
				form.getAttributeMap().put("NotifReqMap", notifReqMap);
				req.getSession().removeAttribute("NotifReqMap");
			}

			PageRulesGenerator reUtils = new PageRulesGenerator();
			String formCd = form.getPageFormCd();
			Map<Object, Object> formFieldMap = reUtils.initiateForm(
					this.loadQuestions(formCd), (BaseForm) form,
					(ClientVO) form.getPageClientVO());

			form.setFormFieldMap(formFieldMap);
			Map<Object, Object> errorTabs = new HashMap<Object, Object>();

			if (formFieldMap != null && formFieldMap.size() > 0) {
				Collection<Object> errorColl = formFieldMap.values();
				Iterator<Object> ite = errorColl.iterator();
				ArrayList<Object> errors = new ArrayList<Object>();
				while (ite.hasNext()) {
					FormField fField = (FormField) ite.next();
					if (fField.getErrorStyleClass() != null
							&& fField.getErrorStyleClass().equals(
									RuleConstants.REQUIRED_FIELD_CLASS)) {
						if (fField.getErrorMessage() != null
								&& fField.getErrorMessage().size() > 0)
							errors.add(fField);
						if (fField.getTabId() != null)
							errorTabs.put(fField.getTabId().toString(), fField
									.getTabId().toString());
					}
				}

				form.setErrorList(errors);
				form.setFormFieldMap(formFieldMap);
				if (errorTabs != null) {
					form.setErrorTabs(errorTabs.values().toArray());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.fireRulesOnEditLoad: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * New Generic Version - Fires the rules on Edit Page. Called by appropriate
	 * Case pages and Contact, Interview pages
	 *
	 * @param form
	 * @throws NEDSSAppException
	 */
	public void fireRulesOnEditLoad(BaseForm form, ClientVO clientVO,
			HttpServletRequest req) throws NEDSSAppException {

		try {
			if (form.getAttributeMap().containsKey(PageConstants.REQ_FOR_NOTIF)
					&& (form.getAttributeMap().containsKey(
							PageConstants.NO_REQ_FOR_NOTIF_CHECK) && form
							.getAttributeMap()
							.get(PageConstants.NO_REQ_FOR_NOTIF_CHECK)
							.equals("false"))) {
				// Also retrieve the NotifReqMap from session and put it in
				// attributeMap
				Map<Object, Object> notifReqMap = req.getSession()
						.getAttribute("NotifReqMap") == null ? new TreeMap<Object, Object>()
						: (TreeMap<Object, Object>) req.getSession()
								.getAttribute("NotifReqMap");
				form.getAttributeMap().put("NotifReqMap", notifReqMap);
				req.getSession().removeAttribute("NotifReqMap");
			}

			PageRulesGenerator reUtils = new PageRulesGenerator();

			String formCd = form.getPageFormCd();
			Map<Object, Object> formFieldMap = reUtils.initiateForm(
			this.loadQuestions(formCd), (BaseForm) form, clientVO);

			form.setFormFieldMap(formFieldMap);
			Map<Object, Object> errorTabs = new HashMap<Object, Object>();

			if (formFieldMap != null && formFieldMap.size() > 0) {
				Collection<Object> errorColl = formFieldMap.values();

				if(errorColl!=null){
				Iterator<Object> ite = errorColl.iterator();
				ArrayList<Object> errors = new ArrayList<Object>();
				while (ite.hasNext()) {
					FormField fField = (FormField) ite.next();
					if (fField.getErrorStyleClass() != null
							&& fField.getErrorStyleClass().equals(
									RuleConstants.REQUIRED_FIELD_CLASS)) {
						if (fField.getErrorMessage() != null
								&& fField.getErrorMessage().size() > 0)
							errors.add(fField);
						if (fField.getTabId() != null)
							errorTabs.put(fField.getTabId().toString(), fField
									.getTabId().toString());
					}
				}

				form.setErrorList(errors);
				}
				form.setFormFieldMap(formFieldMap);
				if (errorTabs != null) {
					form.setErrorTabs(errorTabs.values().toArray());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.fireRulesOnEditLoad: PageFormCd: " + form.getPageFormCd() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * Fires the rules on Create Page. Called by appropriate PAMs
	 *
	 * @param form
	 * @throws NEDSSAppException
	 */
	public static void fireRulesOnCreateLoad(PageForm form,PageLoadUtil pageLoadUtil ,
			HttpServletRequest req) throws NEDSSAppException {

		try {
			String formCd = form.getPageFormCd();
			PageRulesGenerator reUtils = new PageRulesGenerator();
			Map<Object, Object> formFieldMap = reUtils.initiateForm(pageLoadUtil.loadQuestions(formCd),
					(BaseForm) form, (ClientVO) form.getPageClientVO());

			form.setFormFieldMap(formFieldMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.fireRulesOnCreateLoad: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * setCommonAnswersForViewEdit loads all the DEMs, INVs common across all
	 * PAMs collects them from multiple tables part of PageProxyVO and stuffs in
	 * AnswerMap to support UI / Rules
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 */
	public void setCommonAnswersForViewEdit(PageForm form,
			PageProxyVO proxyVO, HttpServletRequest request)
			throws NEDSSAppException {
		try {
			PageClientVO clientVO = new PageClientVO();
			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);
			ClientUtil.setPatientInformation(form.getActionMode(), personVO,
					clientVO, request, form.getPageFormCd());
			form.setPageClientVO(clientVO);
			// loadCommonInvestigationAnswers
			this.setInvestigationInformationOnForm(form, proxyVO);

		} catch (NEDSSAppException e) {
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.setCommonAnswersForViewEdit: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

	}

	public static CTContactProxyVO getCTContactProxyObject(Long CtContactUid,
			HttpSession session) throws Exception {

		CTContactProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {

			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getContactProxyVO";
			Object[] oParams = new Object[] { CtContactUid };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			proxy = (CTContactProxyVO) arr.get(0);

		} catch (Exception e) {
			logger.fatal("Exception occured in PageLoadUtil.getCTContactProxyObject: CtContactUid: " + CtContactUid + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}

		return proxy;
	}

	public void setInvestigationInformationOnForm(PageForm form,
			PageProxyVO proxyVO) throws NEDSSAppException {

		try {
			PublicHealthCaseDT dt = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT();
			PageClientVO clientVO = form.getPageClientVO();
			if (clientVO.getAnswer(PageConstants.JURISDICTION) == null)
				clientVO.setAnswer(PageConstants.JURISDICTION,
						dt.getJurisdictionCd());
			clientVO.setAnswer(PageConstants.PROGRAM_AREA, dt.getProgAreaCd());
			if (clientVO.getAnswer(PageConstants.INV_STATUS_CD) == null)
				clientVO.setAnswer(PageConstants.INV_STATUS_CD,
						dt.getInvestigationStatusCd());
			CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
			if (dt.getInvestigationStatusCd() != null
					&& dt.getCurrProcessStateCd() != null)
				form.getAttributeMap().put(
						"investigationStatus",
						cachedDropDownValues.getCodeShortDescTxt(
								dt.getInvestigationStatusCd(), "PHC_IN_STS")
								+ " ("
								+ cachedDropDownValues.getCodeShortDescTxt(
										dt.getCurrProcessStateCd(),
										"CM_PROCESS_STAGE") + ")");
			else if (dt.getInvestigationStatusCd() != null)
				form.getAttributeMap().put(
						"investigationStatus",
						cachedDropDownValues.getCodeShortDescTxt(
								dt.getInvestigationStatusCd(), "PHC_IN_STS"));
			if (dt.getRptFormCmpltTime_s() != null)
				clientVO.setAnswer(PageConstants.DATE_REPORTED,
						dt.getRptFormCmpltTime_s());
			if (form.getActionMode()!=null && form.getActionMode().equalsIgnoreCase(NEDSSConstants.CREATE_LOAD_ACTION)) {
				//default start date if null on create 2015-03-13 per Jennifer
				String startDate = dt.getActivityFromTime_s();
				if (startDate == null || startDate.isEmpty())
					startDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
				clientVO.setAnswer(PageConstants.INV_START_DATE, startDate);
			} else {
				clientVO.setAnswer(PageConstants.INV_START_DATE,
						dt.getActivityFromTime_s());
			}

			clientVO.setAnswer(PageConstants.MMWR_WEEK, dt.getMmwrWeek());

			clientVO.setAnswer(PageConstants.MMWR_YEAR, dt.getMmwrYear());
			clientVO.setAnswer(PageConstants.CASE_LOCAL_ID, dt.getLocalId());
			clientVO.setAnswer(PageConstants.CASE_ADD_TIME,
					StringUtils.formatDate(dt.getAddTime()));
			clientVO.setAnswer(PageConstants.CASE_ADD_USERID,
					StringUtils.replaceNull(dt.getAddUserId()));
			String sharedInd = null;
			if (dt.getSharedInd() != null && dt.getSharedInd().equals("T"))
				sharedInd = "1";
			if (clientVO.getAnswer(PageConstants.SHARED_IND) == null)
				clientVO.setAnswer(PageConstants.SHARED_IND, sharedInd);
			if (dt.getCd() != null)
				clientVO.setAnswer(PageConstants.CONDITION_CD, dt.getCd());
			clientVO.setAnswer(PageConstants.RECORD_STATUS_CD,
					dt.getRecordStatusCd());
			clientVO.setAnswer(PageConstants.RECORD_STATUS_TIME,
					StringUtils.formatDate(dt.getRecordStatusTime()));
			clientVO.setAnswer(PageConstants.STATUS_CD, dt.getStatusCd());
			clientVO.setAnswer(PageConstants.PROGRAM_JURISDICTION_OID,
					StringUtils.replaceNull(dt.getProgramJurisdictionOid()));
			clientVO.setAnswer(PageConstants.VERSION_CTRL_NBR,
					StringUtils.replaceNull(dt.getVersionCtrlNbr()));
			if (dt.getCaseClassCd() != null)
				clientVO.setAnswer(PageConstants.CASE_CLS_CD,
						dt.getCaseClassCd());
			clientVO.setAnswer(PageConstants.TUB_GEN_COMMENTS, dt.getTxt());
			// These questions are specific to Varicella
			if (dt.getRptToCountyTime_s() != null) {
				clientVO.setAnswer(PageConstants.DATE_REPORTED_TO_COUNTY,
						dt.getRptToCountyTime_s());
			}
			if (dt.getRptToStateTime_s() != null) {
				clientVO.setAnswer(PageConstants.DATE_REPORTED_TO_STATE,
						dt.getRptToStateTime_s());
			}
			if (dt.getDiagnosisTime_s() != null) {
				clientVO.setAnswer(PageConstants.DIAGNOSIS_DATE,
						dt.getDiagnosisTime_s());
			}
			if (dt.getEffectiveFromTime_s() != null) {
				clientVO.setAnswer(PageConstants.ILLNESS_ONSET_DATE,
						dt.getEffectiveFromTime_s());
			}
			if (dt.getPatAgeAtOnset() != null) {
				clientVO.setAnswer(PageConstants.PAT_AGE_AT_ONSET,
						dt.getPatAgeAtOnset());
			}
			if (dt.getPatAgeAtOnsetUnitCd() != null) {
				clientVO.setAnswer(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE,
						dt.getPatAgeAtOnsetUnitCd());
			}

			// These questions are for extending PHC table for common fields -
			// ODS changes
			if (dt.getInvestigatorAssignedTime_s() != null) {
				clientVO.setAnswer(
						PageConstants.DATE_ASSIGNED_TO_INVESTIGATION,
						dt.getInvestigatorAssignedTime_s());
			}
			if (dt.getHospitalizedIndCd() != null) {
				clientVO.setAnswer(PageConstants.WAS_THE_PATIENT_HOSPITALIZED,
						dt.getHospitalizedIndCd());
			}
			if (dt.getHospitalizedAdminTime_s() != null) {
				clientVO.setAnswer(PageConstants.ADMISSION_DATE,
						dt.getHospitalizedAdminTime_s());
			}
			if (dt.getHospitalizedDischargeTime_s() != null) {
				clientVO.setAnswer(PageConstants.DISCHARGE_DATE,
						dt.getHospitalizedDischargeTime_s());
			}
			//For pre-populating data from PHDC document
			if (dt.getHospitalizedIndCd() == null
					&& (dt.getHospitalizedAdminTime_s() != null || dt
							.getHospitalizedDischargeTime_s() != null))
				clientVO.setAnswer(PageConstants.WAS_THE_PATIENT_HOSPITALIZED,
						NEDSSConstants.YES);
			if (dt.getHospitalizedAdminTime_s() != null && dt.getHospitalizedDischargeTime_s() != null) {
				if (dt.getHospitalizedDurationAmt() == null) {
					clientVO.setAnswer(PageConstants.DURATION_OF_STAY, getDateDiffrence(dt.getHospitalizedAdminTime_s(),dt.getHospitalizedDischargeTime_s()));
				}else{
					clientVO.setAnswer(PageConstants.DURATION_OF_STAY,dt.getHospitalizedDurationAmt().toString());
				}
			}

			if (dt.getPregnantIndCd() != null) {
				clientVO.setAnswer(PageConstants.PREGNANCY_STATUS,
						dt.getPregnantIndCd());
			}
			if (dt.getOutcomeCd() != null) {
				clientVO.setAnswer(PageConstants.DID_THE_PATIENT_DIE,
						dt.getOutcomeCd());
			}
			if (dt.getDayCareIndCd() != null) {
				clientVO.setAnswer(
						PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY,
						dt.getDayCareIndCd());
			}
			if (dt.getFoodHandlerIndCd() != null) {
				clientVO.setAnswer(PageConstants.IS_THIS_PERSON_FOOD_HANDLER,
						dt.getFoodHandlerIndCd());
			}
			if (dt.getImportedCountryCd() != null) {
				clientVO.setAnswer(PageConstants.IMPORTED_COUNTRY,
						dt.getImportedCountryCd());
			}
			if (dt.getImportedStateCd() != null) {
				clientVO.setAnswer(PageConstants.IMPORTED_STATE,
						dt.getImportedStateCd());
			}
			if (dt.getImportedCityDescTxt() != null) {
				clientVO.setAnswer(PageConstants.IMPORTED_CITY,
						dt.getImportedCityDescTxt());
			}
			if (dt.getImportedCountyCd() != null) {
				clientVO.setAnswer(PageConstants.IMPORTED_COUNTY,
						dt.getImportedCountyCd());
			}
			if (dt.getDeceasedTime_s() != null) {
				clientVO.setAnswer(PageConstants.INVESTIGATION_DEATH_DATE,
						dt.getDeceasedTime_s());
			}
			if (dt.getOutbreakInd() != null) {
				clientVO.setAnswer(PageConstants.OUTBREAK_INDICATOR,
						dt.getOutbreakInd());
			}
			if (dt.getOutbreakName() != null) {
				clientVO.setAnswer(PageConstants.OUTBREAK_NAME,
						dt.getOutbreakName());
			}
			if (dt.getRptSourceCd() != null)
				clientVO.setAnswer(PageConstants.REPORTING_SOURCE,
						dt.getRptSourceCd());
			if (dt.getEffectiveToTime() != null)
				clientVO.setAnswer(PageConstants.ILLNESS_END_DATE,
						StringUtils.formatDate(dt.getEffectiveToTime()));
			if (dt.getEffectiveDurationAmt() != null)
				clientVO.setAnswer(PageConstants.ILLNESS_DURATION,
						dt.getEffectiveDurationAmt());
			if (dt.getEffectiveDurationUnitCd() != null)
				clientVO.setAnswer(PageConstants.ILLNESS_DURATION_UNITS,
						dt.getEffectiveDurationUnitCd());
			if (dt.getDiseaseImportedCd() != null)
				clientVO.setAnswer(PageConstants.DISEASE_IMPORT_CD,
						dt.getDiseaseImportedCd());
			if (dt.getTransmissionModeCd() != null)
				clientVO.setAnswer(PageConstants.TRANSMISN_MODE_CD,
						dt.getTransmissionModeCd());
			if (dt.getDetectionMethodCd() != null)
				clientVO.setAnswer(PageConstants.DETECTION_METHOD_CD,
						dt.getDetectionMethodCd());
			// Added for Rel4.5
			if (dt.getActivityToTime() != null)
				clientVO.setAnswer(PageConstants.INV_CLOSED_DATE,
						StringUtils.formatDate(dt.getActivityToTime()));
			if (dt.getReferralBasisCd() != null)
				clientVO.setAnswer(PageConstants.REFERRAL_BASIS_CD,
						dt.getReferralBasisCd());
			if (dt.getCurrProcessStateCd() != null)
				clientVO.setAnswer(PageConstants.CURR_PROCESS_STAGE_CD,
						dt.getCurrProcessStateCd());

			// Added for Contact Tracing
			if (dt.getPriorityCd() != null)
				clientVO.setAnswer(PageConstants.CONTACT_PRIORITY,
						dt.getPriorityCd());
			if (dt.getInfectiousFromDate() != null)
				clientVO.setAnswer(PageConstants.INFECTIOUS_PERIOD_FROM,
						StringUtils.formatDate(dt.getInfectiousFromDate()));
			if (dt.getInfectiousToDate() != null)
				clientVO.setAnswer(PageConstants.INFECTIOUS_PERIOD_TO,
						StringUtils.formatDate(dt.getInfectiousToDate()));
			if (dt.getContactInvStatus() != null)
				clientVO.setAnswer(PageConstants.CONTACT_STATUS,
						dt.getContactInvStatus());
			if (dt.getContactInvTxt() != null)
				clientVO.setAnswer(PageConstants.CONTACT_COMMENTS,
						dt.getContactInvTxt());
			// Confirmation Methods
			this._confirmationMethodsForViewEdit(form, proxyVO);

			Collection<Object> coll = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getTheActIdDTCollection();
			if (coll != null && coll.size() > 0) {
				Iterator<Object> iter = coll.iterator();
				while (iter.hasNext()) {
					ActIdDT adt = (ActIdDT) iter.next();
					String typeCd = adt.getTypeCd() == null ? "" : adt
							.getTypeCd();
					String value = adt.getRootExtensionTxt() == null ? "" : adt
							.getRootExtensionTxt();
					if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.STATE_CASE, value);
					} else if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_CITY_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.COUNTY_CASE, value);
					} else if (typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)
							&& !value.equals("")) {
						clientVO.setAnswer(PageConstants.LEGACY_CASE_ID, value);
					}
				}
			}
			if (proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null) {
				CaseManagementDT caseDT = proxyVO.getPublicHealthCaseVO()
						.getTheCaseManagementDT();

				String formCd = form.getPageFormCd();
				Collection<Object> collection = this.loadQuestions(formCd).values();
				Iterator<Object> iter = collection.iterator();
				while (iter.hasNext()) {
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) iter
							.next();
					if (metaData.getDataLocation() != null
							&& metaData
									.getDataLocation()
									.toLowerCase()
									.indexOf(
											DataTables.CASE_MANAGEMENT_TABLE
													.toLowerCase() + ".") > -1) {
						caseDT.setCaseManagementDTPopulated(true);
						try {
							populateClientVO(metaData, caseDT,
									clientVO.getAnswerMap());
						} catch (NEDSSAppException e) {
							logger.fatal("PageLoadUtil.setInvestigationInformationOnForm NEDSSAppException  while populateClientVO! please check "
									+ e.toString(), e);
							throw new NEDSSAppException(e.toString());
						}
					}

				}

			}

		} catch (NEDSSAppException e) {
			logger.fatal("Exception occured in PageLoadUtil.setInvestigationInformationOnForm: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException("Exception occured in PageLoadUtil.setInvestigationInformationOnForm: PageFormCd:", e);
		} catch (Exception e) {
			logger.fatal("Exception occured in PageLoadUtil.setInvestigationInformationOnForm: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException("Exception occured in PageLoadUtil.setInvestigationInformationOnForm: PageFormCd:", e);
		}

	}
	
	public Map<Object, Object> updateMapWithQIds(
			Map<Object, Object> answerMap) throws NEDSSAppException {
		try {
			
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					Long key = (Long) iter.next();
					returnMap.put(this.getQuestionKeyMap().get(key), answerMap.get(key));
				}
			}

			return returnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	// TODO: replace with Generic Function
	public static void setPamSpecificAnswersForViewEdit(PageForm form,
			Map<Object, Object> answerMap, Map<Object, Object> returnMap) throws NEDSSAppException {

		try {
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					String questionId = (String) iter.next();
					Object object = answerMap.get(questionId);
					if (object instanceof NbsAnswerDT) {
						NbsAnswerDT answerDT = (NbsAnswerDT) object;

						String otherCoded = "";
						String otherText = "";
						String otherUnit = "";
						String otherUnitVal = "";
						String answer = answerDT.getAnswerTxt();
						if (answer != null && answer.trim().indexOf("OTH^") > -1) {
							// otherCoded=answer.substring(0,
							// answer.trim().indexOf("OTH^"));
							logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit: "+questionId+", "+"OTH");
							
							returnMap.put(questionId, "OTH");
							
							
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								otherText = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit: "+questionId + "Oth"+", "+otherText);
								
								returnMap.put(questionId + "Oth", otherText);
								
								
								
							}
						} else if (answer != null && answer.trim().indexOf("^") > 0) {
							otherUnitVal = answer.substring(0, answer.trim()
									.indexOf("^"));
							logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit: "+questionId +", "+otherUnitVal);
							
							returnMap.put(questionId, otherUnitVal);
							
							
							
							
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("^") + 1)) {
								otherUnit = answer.substring(
										answer.indexOf("^") + 1, answer.length());
								logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit: "+questionId + "Unit" +", "+otherUnit);
								
								returnMap.put(questionId + "Unit", otherUnit);
								
								
								
							}
						} else{
							logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit: "+questionId +", "+answerDT.getAnswerTxt());
							
							returnMap.put(questionId, answerDT.getAnswerTxt());
							
						}
					}
					
					
				}
				logger.debug("PageLoadUtil.setPamSpecificAnswersForViewEdit RETURN MAP: "+returnMap.toString());
				form.getPageClientVO().getAnswerMap().putAll(returnMap);
				// form.getPageClientVO().setPamAnswerMap(returnMap);
				setRaceAnswersToClientVO(form.getPageClientVO(), answerMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.setPamSpecificAnswersForViewEdit: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * setPamSpecificAnswersForViewEdit (Generic Version) This method parses out
	 * other and unit values and adds them back into the answer map. Also sets
	 * the Race check boxes.
	 *
	 * @param answerMap
	 * @param returnMap
	 * @param clientVO
	 * @return returnMap
	 * @throws NEDSSAppException
	 */
	public static void setPamSpecificAnswersForViewEdit(
			Map<Object, Object> answerMap, Map<Object, Object> returnMap,
			ClientVO clientVO) throws NEDSSAppException {

		try {
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					String questionId = (String) iter.next();
					Object object = answerMap.get(questionId);
					if (object instanceof NbsAnswerDT) {
						NbsAnswerDT answerDT = (NbsAnswerDT) object;

						String otherCoded = "";
						String otherText = "";
						String otherUnit = "";
						String otherUnitVal = "";
						String answer = answerDT.getAnswerTxt();

						if (answer != null && answer.trim().indexOf("OTH^") > -1) {
							// otherCoded=answer.substring(0,
							// answer.trim().indexOf("OTH^"));
							returnMap.put(questionId, "OTH");
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								otherText = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								returnMap.put(questionId + "Oth", otherText);
							}
						} else if (answer != null && answer.trim().indexOf("^") > 0) {
							otherUnitVal = answer.substring(0, answer.trim()
									.indexOf("^"));
							returnMap.put(questionId, otherUnitVal);
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("^") + 1)) {
								otherUnit = answer.substring(
										answer.indexOf("^") + 1, answer.length());
								if (answer != null
										&& (answer.trim().length() > answer.trim()
												.indexOf("OTH^") + 4)) {
									otherText = answer.substring(
											answer.indexOf("OTH^") + 4,
											answer.length());
									returnMap.put(questionId + "Unit", otherUnit);
								}
								else{
									if(otherUnit!=null){
										returnMap.put(questionId+"Unit", otherUnit);
									}
								}
							}
						} else {
							returnMap.put(questionId, answerDT.getAnswerTxt());
						}

					}
				}
				clientVO.getAnswerMap().putAll(returnMap);
				// form.getPageClientVO().setPamAnswerMap(returnMap);
				setRaceAnswersToClientVO(clientVO, answerMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.setPamSpecificAnswersForViewEdit: " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	public  void setPamSpecificAnswersForCoinfection(
			Map<Object, Object> answerMap, Map<Object, Object> returnMap,
			ClientVO clientVO) throws NEDSSAppException {

		try {
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					String questionId = (String) iter.next();
					Object object = answerMap.get(questionId);
					if (object instanceof NbsAnswerDT
							&& this.coinfectionQuestionMap != null
							&& this.coinfectionQuestionMap.containsKey(questionId)) {
						NbsAnswerDT answerDT = (NbsAnswerDT) object;

						String otherCoded = "";
						String otherText = "";
						String otherUnit = "";
						String otherUnitVal = "";
						String answer = answerDT.getAnswerTxt();

						if (answer != null && answer.trim().indexOf("OTH^") > -1) {
							// otherCoded=answer.substring(0,
							// answer.trim().indexOf("OTH^"));
							returnMap.put(questionId, "OTH");
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								otherText = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								returnMap.put(questionId + "Oth", otherText);
							}
						} else if (answer != null && answer.trim().indexOf("^") > 0) {
							otherUnitVal = answer.substring(0, answer.trim()
									.indexOf("^"));
							returnMap.put(questionId, otherUnitVal);
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("^") + 1)) {
								otherUnit = answer.substring(
										answer.indexOf("^") + 1, answer.length());
								if (answer != null
										&& (answer.trim().length() > answer.trim()
												.indexOf("OTH^") + 4)) {
									otherText = answer.substring(
											answer.indexOf("OTH^") + 4,
											answer.length());
									returnMap.put(questionId + "Unit", otherUnit);
								}
							}
						} else {
							returnMap.put(questionId, answerDT.getAnswerTxt());
						}

					}
				}
				clientVO.getAnswerMap().putAll(returnMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	// TODO: replace with generic Function
	private static void setRaceAnswersToClientVO(PageClientVO clientVO,
			Map<Object, Object> answerMap) throws NEDSSAppException {
		try {
			Object obj = answerMap.get(PageConstants.RACE);
			if (obj != null && obj instanceof ArrayList<?>) {
				ArrayList<Object> raceArrayList = (ArrayList<Object>) obj;
				Iterator<Object> iter = raceArrayList.iterator();
				while (iter.hasNext()) {
					NbsAnswerDT answerDT = (NbsAnswerDT) iter.next();
					String answer = answerDT.getAnswerTxt();
					if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.UNKNOWN))
						clientVO.setUnKnownRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN))
						clientVO.setAfricanAmericanRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
						clientVO.setAmericanIndianAlskanRace(1);
					else if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.WHITE))
						clientVO.setWhiteRace(1);
					else if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.ASIAN))
						clientVO.setAsianRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
						clientVO.setHawaiianRace(1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * setRaceAnswersToClientVO (Generic Version) Set the Race check boxes if
	 * set.
	 *
	 * @param clientVO
	 * @param answerMap
	 * @throws NEDSSAppException
	 */
	private static void setRaceAnswersToClientVO(ClientVO clientVO,
			Map<Object, Object> answerMap) throws NEDSSAppException {
		try {
			Object obj = answerMap.get(PageConstants.RACE);
			if (obj != null && obj instanceof ArrayList<?>) {
				ArrayList<Object> raceArrayList = (ArrayList<Object>) obj;
				Iterator<Object> iter = raceArrayList.iterator();
				while (iter.hasNext()) {
					NbsAnswerDT answerDT = (NbsAnswerDT) iter.next();
					String answer = answerDT.getAnswerTxt();
					if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.UNKNOWN))
						clientVO.setUnKnownRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN))
						clientVO.setAfricanAmericanRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
						clientVO.setAmericanIndianAlskanRace(1);
					else if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.WHITE))
						clientVO.setWhiteRace(1);
					else if (answer != null
							&& answer.equalsIgnoreCase(NEDSSConstants.ASIAN))
						clientVO.setAsianRace(1);
					else if (answer != null
							&& answer
									.equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
						clientVO.setHawaiianRace(1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	private static void setInvInfoForCreate(PageForm form, HttpSession session) throws NEDSSAppException {
		try {
			Map<Object, Object> answerMap = new HashMap<Object, Object>();
			String programAreaCd = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationProgramArea);
			String conditionCd = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationCondition);
			form.getAttributeMap().put("headerConditionCode", conditionCd); //needed for STD GT November 2017
			form.getAttributeMap2().put("headerConditionCode", conditionCd);
			answerMap.put(PageConstants.INV_STATUS_CD, NEDSSConstants.STATUS_OPEN);

			answerMap.put(PageConstants.PROGRAM_AREA, programAreaCd);
			answerMap.put(PageConstants.CONDITION_CD, conditionCd);
			answerMap.put(PageConstants.SHARED_IND, "1");
			answerMap.put(PageConstants.CASE_LOCAL_ID, "");
			form.getPageClientVO().getAnswerMap().putAll(answerMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.setInvInfoForCreate: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/**
	 * This method retrieves the complete PAM Information from the backend,
	 * constructs and returns PAMClientVO
	 *
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PageClientVO.PageClientVO
	 */
	public static PageClientVO retrieveEditViewLoadVO(PageForm form,
			HttpServletRequest request) {
		return new PageClientVO();
	}

	/**
	 * Retrieves Master Patient Record Information
	 *
	 * @param mprUId
	 * @param session
	 * @return
	 * @throws NEDSSAppException
	 */
	public static PersonVO findMasterPatientRecord(Long mprUId,
			HttpSession session) throws NEDSSAppException {
		PersonVO personVO = null;
		MainSessionCommand msCommand = null;
		try {
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getMPR";
			Object[] oParams = new Object[] { mprUId };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			personVO = (PersonVO) arr.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
				logger.fatal("Exception occured in PageLoadUtil.findMasterPatientRecord: mprUId: " + mprUId + ", " + ex.getMessage(), ex);
	        	throw new NEDSSAppException(ex.getMessage(), ex);
			}
			logger.fatal("personVO: ", ex);
		}
		return personVO;
	}

	/**
	 * Retrieves Patient Person Record Information
	 *
	 * @param patientUid
	 *            personUid
	 * @param session
	 * @return
	 * @throws NEDSSAppException
	 */
	public static PersonVO findPatientRecord(Long patientUid,
			HttpSession session) throws NEDSSAppException {
		PersonVO personVO = null;
		MainSessionCommand msCommand = null;
		try {
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getPerson";
			Object[] oParams = new Object[] { patientUid };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			personVO = (PersonVO) arr.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}
			logger.fatal("Exception occured in PageLoadUtil.findMasterPatientRecord: patientUid: " + patientUid + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
		return personVO;
	}

	public static PageProxyVO getProxyObject(String sPublicHealthCaseUID,
			HttpSession session) throws NEDSSAppException {
		PageProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {
			Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "getPageProxyVO";
			Object[] oParams = new Object[] { NEDSSConstants.CASE,
					publicHealthCaseUID };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			proxy = (PageProxyVO) arr.get(0);
			translateCaseAnswerDTsToAnswerDTs(proxy);
		} catch (Exception ex) {
			logger.fatal("Exception occured in PageLoadUtil.getProxyObject: PublicHealthCaseUID: " + sPublicHealthCaseUID + ", " + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}

		return proxy;
	}

	public static PageProxyVO getProxyObject(String actUidStr,
			String businessObjectType, HttpSession session) throws NEDSSAppException {
		PageProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {
			Long actUID = new Long(actUidStr);
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "getPageProxyVO";
			Object[] oParams = new Object[] { businessObjectType, actUID };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			proxy = (PageProxyVO) arr.get(0);

		} catch (Exception ex) {
			logger.fatal("Exception occured in PageLoadUtil.getProxyObject: actUid: " + actUidStr + ", " + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
		return proxy;
	}

	public static Map<Object, Object> setContextForView(PageForm form,
			String contextAction, HttpServletRequest request,
			HttpSession session) throws NEDSSAppException {
		try {
			// clear Links if any stored
			form.getAttributeMap().remove("linkName");
			form.getAttributeMap().remove("linkValue");
			form.getAttributeMap().remove("linkName1");
			form.getAttributeMap().remove("linkValue1");
			Map<Object, Object> map = new HashMap<Object, Object>();

			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form
					.getPageClientVO().getOldPageProxyVO());

			Collection<Object> DSContactColl = new ArrayList<Object>();
			if (((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO())
					.getTheCTContactSummaryDTCollection() != null) {
				DSContactColl = ((PageActProxyVO) form.getPageClientVO()
						.getOldPageProxyVO()).getTheCTContactSummaryDTCollection();
			}
			NBSContext.store(session, NBSConstantUtil.DSContactColl, DSContactColl);
			// see if the EPI Link ID (Lot Nbr) is present and place into session
			// context
			if ((((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO())
					.getPublicHealthCaseVO() != null)
					&& (((PageActProxyVO) form.getPageClientVO()
							.getOldPageProxyVO()).getPublicHealthCaseVO()
							.getTheCaseManagementDT() != null)
					&& (((PageActProxyVO) form.getPageClientVO()
							.getOldPageProxyVO()).getPublicHealthCaseVO()
							.getTheCaseManagementDT().getEpiLinkId() != null)) {
				String lotNbr = ((PageActProxyVO) form.getPageClientVO()
						.getOldPageProxyVO()).getPublicHealthCaseVO()
						.getTheCaseManagementDT().getEpiLinkId();
				NBSContext.store(session, NBSConstantUtil.DSLotNbr, lotNbr);
			}
			Long DSPatientPersonUID = personVO.getThePersonDT()
					.getPersonParentUid();
			if (DSPatientPersonUID != null)
				NBSContext.store(session, NBSConstantUtil.DSPatientPersonUID,
						DSPatientPersonUID);
			String DSPatientPersonUIDs = DSPatientPersonUID.toString();
			request.setAttribute("DSPatientPersonUID", DSPatientPersonUIDs);


			setNameCurrentSexDOB(form, request, personVO);


			form.getAttributeMap().put(
					"patientLocalId",
					PersonUtil.getDisplayLocalID(personVO.getThePersonDT()
							.getLocalId()));



			form.getAttributeMap().put(
					"caseLocalId",
					((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO())
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLocalId());
			PublicHealthCaseDT phcDT = ((PageActProxyVO) form.getPageClientVO()
					.getOldPageProxyVO()).getPublicHealthCaseVO()
					.getThePublicHealthCaseDT();
			request.setAttribute("caseLocalId", phcDT.getLocalId());
			request.setAttribute("caseUid", phcDT.getPublicHealthCaseUid());
			CachedDropDownValues cdv = new CachedDropDownValues();
			String caseStatus = cdv.getDescForCode("PHC_CLASS",
					((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO())
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getCaseClassCd());
			caseStatus = (caseStatus == null || caseStatus.trim().length() == 0 ? " "
					: caseStatus);
			form.getAttributeMap().put("caseClassCd", caseStatus);
			request.setAttribute("createdDate",
					StringUtils.formatDate(phcDT.getAddTime()));
			request.setAttribute("createdBy", phcDT.getAddUserName());
			request.setAttribute("updatedDate",
					StringUtils.formatDate(phcDT.getLastChgTime()));
			request.setAttribute("updatedBy", phcDT.getLastChgUserName());

			setLastChangeUserAndTime(form, request);

			String sCurrentTask = null;
			if (contextAction == null) {
				String ContactTracing = (String) NBSContext.retrieve(
						request.getSession(), NBSConstantUtil.DSInvestigationPath);
				contextAction = ContactTracing;
			}
			if (contextAction != null) {
				TreeMap<Object, Object> tm = NBSContext.getPageContext(session,
						"PS036", contextAction);
				String urlForViewFile = tm.get("ReturnToContactFileEvents")
						.toString();
				String urlFOrContactCase = tm.get("ContactCase").toString();
				map.put("urlForViewFile", urlForViewFile);
				map.put("ContactCase", urlFOrContactCase);
				ErrorMessageHelper.setErrMsgToRequest(request);
				sCurrentTask = NBSContext.getCurrentTask(session);
				form.getAttributeMap().put("CurrentTask", sCurrentTask);
				form.getAttributeMap().put(
						"Submit",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("Submit"));
				form.getAttributeMap().put("Cancel",
						"/nbs/" + sCurrentTask + ".do?ContextAction=Cancel");
				form.getAttributeMap().put("deleteButtonHref",
						"/nbs/PageAction.do?method=deleteSubmit");
				form.getAttributeMap().put(
						"SubmitNoViewAccess",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("SubmitNoViewAccess"));
				form.getAttributeMap().put(
						"Sort",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("Sort"));
				form.getAttributeMap().put(
						"InvestigationID",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("InvestigationID"));
				form.getAttributeMap().put(
						"ReturnToViewInvestigation",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToViewInvestigation"));
				form.getAttributeMap().put(
						"InvestigationIDOnSummary",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("InvestigationIDOnSummary"));
				form.getAttributeMap().put(
						"InvestigationIDOnInv",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("InvestigationIDOnInv"));
				form.getAttributeMap().put(
						"coInfectionID", form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId());
				form.getAttributeMap().put(
						"createNotification",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("CreateNotification"));
				form.getAttributeMap().put(
						"CreateNotificationDisplay",
						tm.get("CreateNotification") == null ? "NOT_DISPLAYED" : tm
								.get("CreateNotification"));

				form.getAttributeMap().put(
						"caseReporting",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("CaseReporting"));
				form.getAttributeMap().put(
						"CaseReportingDisplay",
						tm.get("CaseReporting") == null ? "NOT_DISPLAYED" : tm
								.get("CaseReporting"));

				form.getAttributeMap().put(
						"TransferOwnership",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("TransferOwnership") + "&invFormCd="
								+ form.getPageFormCd());
				form.getAttributeMap().put(
						"TransferOwnershipDisplay",
						tm.get("TransferOwnership") == null ? "NOT_DISPLAYED" : tm
								.get("TransferOwnership"));

				form.getAttributeMap().put(
						NBSConstantUtil.ViewVaccinationFromInv,
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get(NBSConstantUtil.ViewVaccinationFromInv));
				form.getAttributeMap().put(
						"ObservationLabID",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ObservationLabID"));
				form.getAttributeMap().put(
						"ObservationMorbID",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ObservationMorbID"));
				form.getAttributeMap().put(
						"treatmentEventRef",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("TreatmentID"));

				form.getAttributeMap().put("PrintPage",
						"/nbs/LoadViewInvestigation1.do?ContextAction=PrintPage");

				form.getAttributeMap().put(
						"DocumentIDFromInv",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("DocumentIDFromInv"));

				if(contextAction.equalsIgnoreCase("CompareInvestigations")){

					form.getAttributeMap().put(
							"linkValueCancelCompare",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToFileEvents") + "&invFormCd="
									+ form.getPageFormCd() + "");

					form.getAttributeMap().put(
							"linkValueMergeInvestigations",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ "EditMerge"+ "&invFormCd="
									+ form.getPageFormCd() + "");

				}
				if (sCurrentTask.equalsIgnoreCase("ViewInvestigation3")) {
					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToFileEvents") + "&invFormCd="
									+ form.getPageFormCd() + "\">"
									+ "Return To File: Events" + "</A>");
					form.getAttributeMap().put(
							"deleteButtonHref",
							" /nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("ReturnToFileEvents"));
					form.getAttributeMap().put("linkValue1", "");

				}else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation2") ) {
					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("FileSummary") + "&invFormCd="
									+ form.getPageFormCd() + "\">" + "View File"
									+ "</A>");
					
					//Custom queues: In future, the context action will depend on the object type? Right now only investigation is available
					
					 
		    		 String custom = (String)request.getAttribute("custom");
		    		 String queueName =  (String)request.getAttribute("queueName");
		    		 String reportType =  (String)request.getAttribute("reportType");//TODO: it will need to be read from somewhere else when this is extended for other business object types
		    	
		    		 
		    		//Showing the investigation from custom queues
		    		if(custom!=null && custom.equalsIgnoreCase("true")){
		    			
		    			String pageNumberCustom = (String)request.getSession().getAttribute("pageNumberCustom");
		    			request.getSession().removeAttribute("pageNumberCustom");
		    			
		    			if(pageNumberCustom==null)
		    				pageNumberCustom = "1";
						form.getAttributeMap().put(
								"linkReturnToCustomQueue",
								"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
										+ tm.get("investigation") + "&custom=" +custom+"&queueName="+queueName+"&reportType="+reportType+"&pageNumber="+pageNumberCustom+"\">" + "Return to Search Results"
										+ "</A>");
		    		}
					form.getAttributeMap().put(
							"deleteButtonHref",
							"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("FileSummary"));
					try {
						DSQueueObject queueObject = (DSQueueObject) NBSContext
								.retrieve(session, "DSQueueObject");
						if (queueObject.getDSQueueType() != null
								&& queueObject
										.getDSQueueType()
										.equals(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
							form.getAttributeMap()
									.put("linkValue1",
											"<A href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToMyInvestigations")
													+ "\">"
													+ NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS
													+ "</A>");
						} else if (queueObject.getDSQueueType() != null
								&& queueObject.getDSQueueType().equals(
										NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
							form.getAttributeMap()
									.put("linkValue1",
											"<A href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToObservationNeedingReview")
													+ "\">"
													+ "Return to Documents Requiring Review"
													+ "</A>");
							form.getAttributeMap().put("linkName1",
									"Return to Documents Requiring Review");
						}
					} catch (Exception ex) {
						logger.info("Link: cannot be shown in this context");
						form.getAttributeMap().put("linkValue1", "");
					}
				} else if(sCurrentTask.equalsIgnoreCase("ViewInvestigation13")){

					/*
					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("FileSummary") + "&invFormCd="
									+ form.getPageFormCd() + "\">" + "View File"
									+ "</A>");*/
/*
					form.getAttributeMap().put(
							"deleteButtonHref",
							"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("FileSummary"));*/
					try {
						DSQueueObject queueObject = (DSQueueObject) NBSContext
								.retrieve(session, "DSQueueObject");
						if (queueObject.getDSQueueType() != null
								&& queueObject
										.getDSQueueType()
										.equals(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
							form.getAttributeMap()
									.put("linkValue1",
											"<A href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToMyInvestigations")
													+ "\">"
													+ NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS
													+ "</A>");
						} else if (queueObject.getDSQueueType() != null
								&& queueObject.getDSQueueType().equals(
										NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
							if(contextAction.equalsIgnoreCase("ViewInv")){
								form.getAttributeMap()
										.put("linkValue1",
												"<A href=\"/nbs/"
														+ sCurrentTask
														+ ".do?ContextAction="
														+ tm.get("ReturnToObservationNeedingReview")
														+ "\">"
														+ "Return to Documents Requiring Review"
														+ "</A>");
								form.getAttributeMap().put("linkName1",
										"Return to Documents Requiring Review");
							}
							form.getAttributeMap().put(
									"linkValue",
									"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
											+ tm.get("ReturnToFileEvents") + "&invFormCd="
											+ form.getPageFormCd() + "\">"
											+ "Return To File: Events" + "</A>");
							//add ContextAction=ReturnToFileEvents
							form.getAttributeMap().put("linkName",
									"Return To File: Events");
						}




					} catch (Exception ex) {
						logger.info("Link: cannot be shown in this context");
						form.getAttributeMap().put("linkValue1", "");
					}

				}else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation1")) {
					form.getAttributeMap().put("linkValue1", "");
					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToFileSummary") + "&invFormCd="
									+ form.getPageFormCd() + "\">"
									+ "Return to File: Summary" + "</A>");

					form.getAttributeMap().put(
							"deleteButtonHref",
							"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("ReturnToFileSummary"));
				} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation4")) {

					try {
						DSQueueObject queueObject = (DSQueueObject) NBSContext
								.retrieve(session, "DSQueueObject");
						if (NEDSSConstants.MESSAGE_QUEUE.equals(queueObject
								.getDSQueueType())) {
							form.getAttributeMap()
									.put("linkName",
											"Return to Approval Queue for Initial Notifications");
							form.getAttributeMap()
									.put("linkValue1",
											"<A href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToMessageQueue")
													+ "\">"
													+ NEDSSConstants.RETURN_TO_MESSAGE_QUEUE
													+ "</A>");
							form.getAttributeMap().put(
									"deleteButtonHref",
									"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
											+ tm.get("ReturnToMessageQueue"));
					} else if (NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL
								.equals(queueObject.getDSQueueType())) {
							form.getAttributeMap()
									.put("linkName",
											"Return to Approval Queue for Initial Notifications");

							form.getAttributeMap()
									.put("linkValue",
											"<A href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToReviewNotifications")
													+ "&invFormCd="
													+ form.getPageFormCd()
													+ "\">"
													+ "Return to Approval Queue for Initial Notifications"
													+ "</A>");
							form.getAttributeMap()
									.put("deleteButtonHref",
											"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
													+ tm.get("ReturnToReviewNotifications"));
						} else if (NEDSSConstants.SUPERVISOR_REVIEW_QUEUE
								.equals(queueObject.getDSQueueType())) {
							form.getAttributeMap()
									.put("linkValue1",
											"<a href=\"/nbs/"
													+ sCurrentTask
													+ ".do?ContextAction="
													+ tm.get("ReturnToSupervisorReviewQueue")
													+ "\">"
													+ NEDSSConstants.RETURN_TO_SUPERVISOR_REVIEW_QUEUE
													+ "</a>");
							form.getAttributeMap()
									.put("deleteButtonHref",
											"/nbs/PamAction.do?method=deleteSubmit&ContextAction="
													+ tm.get("ReturnToSupervisorReviewQueue"));
						}

					} catch (Exception ex) {
						logger.info("Link: cannot be shown in this context");
						form.getAttributeMap().put("linkValue1", "");
					}

				} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation5")) {
					form.getAttributeMap().put("linkName",
							"Return to Updated Notifications Queue");

					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToReviewUpdatedNotifications")
									+ "&invFormCd=" + form.getPageFormCd() + "\">"
									+ "Return to Updated Notifications Queue"
									+ "</A>");

					form.getAttributeMap().put(
							"deleteButtonHref",
							"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("ReturnToReviewUpdatedNotifications"));
				} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation6")) {
					form.getAttributeMap().put("linkName",
							"Return to Rejected Notifications Queue");

					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToRejectedNotifications")
									+ "\">"
									+ "Return to Rejected Notifications Queue"
									+ "</A>");

					form.getAttributeMap().put(
							"deleteButtonHref",
							"/nbs/PageAction.do?method=deleteSubmit&ContextAction="
									+ tm.get("ReturnToRejectedNotifications"));
				} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation7")
						|| sCurrentTask.equalsIgnoreCase("ViewInvestigation8")
						|| sCurrentTask.equalsIgnoreCase("ViewInvestigation9")
						|| sCurrentTask.equalsIgnoreCase("ViewInvestigation10")
						|| sCurrentTask.equalsIgnoreCase("ViewInvestigation11")
						|| sCurrentTask.equalsIgnoreCase("ViewInvestigation12")) {
					// associate Lab or Morb to Investigations
					form.getAttributeMap().put("linkName",
							NEDSSConstants.RETURN_TO_ASSOCIATE_TO_INVESTIGATIONS);

					form.getAttributeMap().put(
							"linkValue",
							"<A href=\"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToAssociateToInvestigations")
									+ "\">"
									+ "Return to Associate to Investigations"
									+ "</A>");
				}

				form.getAttributeMap().put(
						"ManageVaccinationDisplay",
						tm.get("ManageVaccinations") == null ? "NOT_DISPLAYED" : tm
								.get("ManageVaccinations"));

				form.getAttributeMap().put(
						"Edit",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("Edit") + "&invFormCd="
								+ form.getPageFormCd());

				form.getAttributeMap().put(
						"ManageEvents",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ManageEvents") + "&invFormCd="
								+ form.getPageFormCd());
				form.getAttributeMap().put(
						"ManageEventsDisplay",
						tm.get("ManageEvents") == null ? "NOT_DISPLAYED" : tm
								.get("ManageEvents"));
			}

			try {
				String rejectedDeleteString = NBSContext.retrieve(session,
						NBSConstantUtil.DSRejectedDeleteString) == null ? null
						: (String) NBSContext.retrieve(session,
								NBSConstantUtil.DSRejectedDeleteString);
				request.setAttribute("deleteError", rejectedDeleteString);
			} catch (Exception e) {
				logger.debug("PageLoadUtil.setCommonAnswersForViewEdit NedssAppException thrown. Seems like DSRejectedDeleteString is not in the context");
				//logger.debug("\n" + e.toString());
			}

			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageLoadUtil.setContextForView: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	public static void setNameCurrentSexDOB(PageForm form, HttpServletRequest request,PersonVO personVO){


		Collection<Object> nms = personVO.getThePersonNameDTCollection();
		String strPName = "";
		String strFName = "";
		String strMName = "";
		String strLName = "";
		String DOB = "";
		String CurrSex = "";
		String nmSuffix = "";
		if (nms != null) {
			Iterator<Object> itname = nms.iterator();
			Timestamp mostRecentNameAOD = null;
			while (itname.hasNext()) {
				PersonNameDT name = (PersonNameDT) itname.next();
				// for personInfo
				if (name != null
						&& name.getNmUseCd() != null
						&& name.getNmUseCd().equals(NEDSSConstants.LEGAL)
						&& name.getStatusCd() != null
						&& name.getStatusCd().equals(
								NEDSSConstants.STATUS_ACTIVE)
						&& name.getRecordStatusCd() != null
						&& name.getRecordStatusCd().equals(
								NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					if (mostRecentNameAOD == null
							|| (name.getAsOfDate() != null && !name
									.getAsOfDate().before(mostRecentNameAOD))) {
						strFName = "";
						strMName = "";
						strLName = "";
						nmSuffix = "";
						mostRecentNameAOD = name.getAsOfDate();
						if (name.getFirstNm() != null)
							strFName = name.getFirstNm();
						if (name.getMiddleNm() != null)
							strMName = name.getMiddleNm();
						if (name.getLastNm() != null)
							strLName = name.getLastNm();
						if (name.getNmSuffix() != null)
							nmSuffix = name.getNmSuffix();
					}
				}

			}
		}
		strPName = strFName + " " + strMName + " " + strLName;
		if (null == strPName || strPName.equalsIgnoreCase("null")) {
			strPName = "";
		}

		form.getAttributeMap().put("patientLocalName", strPName);
		request.setAttribute("patientLocalName", strPName);
		CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
		request.setAttribute("patientSuffixName",
				cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));

		if (personVO.getThePersonDT().getBirthTime() != null) {
			// DOB =
			// PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
			java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat(
					"MM/dd/yyyy", java.util.Locale.US);
			DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
		}
		form.getAttributeMap().put("patientDOB", DOB);
		request.setAttribute("patientDOB", DOB);

		if (personVO.getThePersonDT().getCurrSexCd() != null)
			CurrSex = personVO.getThePersonDT().getCurrSexCd();
		if (CurrSex.equalsIgnoreCase("F"))
			CurrSex = "Female";
		if (CurrSex.equalsIgnoreCase("M"))
			CurrSex = "Male";
		if (CurrSex.equalsIgnoreCase("U"))
			CurrSex = "Unknown";
		form.getAttributeMap().put("patientCurrSex", CurrSex);
		request.setAttribute("patientCurrSex", CurrSex);
		// String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()


	}


	/**
	 * populatePamAssocations sets all the Lab, Morb, Treatment, Notification
	 * and Inv History Associations
	 *
	 * @param proxy
	 * @param sPublicHealthCaseUID
	 * @param request
	 * @param form
	 * @throws Exception
	 */
	public void populatePageAssocations(PageProxyVO proxy,
			String sPublicHealthCaseUID, Map<Object, Object> map,
			HttpServletRequest request, PageForm form) throws Exception {
		try {
			ArrayList<Object> obsLabSummary = new ArrayList<Object>();
			ArrayList<Object> obsMorbSummary = null;
			ArrayList<Object> treatments = new ArrayList<Object>();
			ArrayList<Object> notifSummary = null;
			ArrayList<Object> vaccination = null;
			ArrayList<Object> theInterviewSummary = null;
			ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
			ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();
			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
			if (proxy != null) {
				PageActProxyVO pgActProxy = (PageActProxyVO) proxy;
				// Morb Report
				obsMorbSummary = (ArrayList<Object>) pgActProxy
						.getTheMorbReportSummaryVOCollection();
				if (obsMorbSummary != null) {
					HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
					Iterator<Object> ite = obsMorbSummary.iterator();
					while (ite.hasNext()) {
						MorbReportSummaryVO mrsVO = (MorbReportSummaryVO) ite
								.next();
						// to handle the lab reports created through Morb report.
						boolean flag = true;
						StringBuffer desc = new StringBuffer(
								mrsVO.getConditionDescTxt() == null ? "" : "<b>"
										+ mrsVO.getConditionDescTxt() + "</b>");
						if (mrsVO.getTheLabReportSummaryVOColl() != null
								&& mrsVO.getTheLabReportSummaryVOColl().size() > 0) {
							Iterator<Object> labIte = mrsVO
									.getTheLabReportSummaryVOColl().iterator();
							while (labIte.hasNext()) {
								flag = false;
								LabReportSummaryVO labSummaryVO = (LabReportSummaryVO) labIte
										.next();
								desc.append(DecoratorUtil
										.getResultedTestsStringForWorup(labSummaryVO
												.getTheResultedTestSummaryVOCollection()));

								// labSummaryVO.setLabFromMorb(true);
								// UI should show the local id of the morb report
								// and the link should take user to morb report
								// though the record shows up in the lab list
								// labSummaryVO.setObservationUid(mrsVO.getObservationUid());
								// labSummaryVO.setLocalId(mrsVO.getLocalId());
							}
							// obsLabSummary.addAll(mrsVO.getTheLabReportSummaryVOColl());
						}
						if (mrsVO.getTheTreatmentSummaryVOColl() != null
								&& mrsVO.getTheTreatmentSummaryVOColl().size() > 0) {
							if (mrsVO.getTheTreatmentSummaryVOColl() == null
									|| mrsVO.getTheTreatmentSummaryVOColl().size() == 0) {
								logger.debug("Observation summary collection arraylist is null");
							} else {
								NedssUtils nUtil = new NedssUtils();
								nUtil.sortObjectByColumn(
										"getCustomTreatmentNameCode",
										mrsVO.getTheTreatmentSummaryVOColl(), true);
								if (flag)
									desc.append("<br>");
								desc.append("<b>Treatment Info:</b><UL>");
								Iterator<Object> treatmentIterator = mrsVO
										.getTheTreatmentSummaryVOColl().iterator();
								while (treatmentIterator.hasNext()) {
									logger.debug("Inside iterator.hasNext()...");
									TreatmentSummaryVO treatment = (TreatmentSummaryVO) treatmentIterator
											.next();
									desc.append(treatment
											.getCustomTreatmentNameCode() == null ? ""
											: "<LI><b>"
													+ treatment
															.getCustomTreatmentNameCode()
													+ "</b></LI>");
								}// While
								desc.append("<UL>");
							}
						}

						mrsVO.setConditionDescTxt(desc.toString());
//						if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask)){
//							mrsVO.setActionLink(dateLink(mrsVO.getDateReceived())+"<br>"+ StringUtils
//									.formatDatewithHrMin(mrsVO
//											.getDateReceived()));
//						}
//						else{
						parameterMap.put("ContextAction", "ObservationMorbID");
						parameterMap.put("observationUID",
								(mrsVO.getObservationUid()).toString());
						parameterMap.put("method", "viewSubmit");
						mrsVO.setActionLink(getHiperlink(
								dateLink(mrsVO.getDateReceived()), sCurrentTask,
								parameterMap, form.getActionMode()));
						//}
						parameterMap = new HashMap<Object, Object>();

						if(mrsVO.isMorbFromDoc()){
							String startDate = mrsVO.getDateReceived() == null ? "No Date"
									: StringUtils.formatDate(mrsVO
											.getDateReceived());
						if((form.getActionMode()!=null && form.getActionMode()
								.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) || form.getActionMode()
								.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))){
								mrsVO.setActionLink( startDate+ "<br>"+ StringUtils
										.formatDatewithHrMin(mrsVO
											.getDateReceived())+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
							}
							else{
							mrsVO.setActionLink( "<a href=\"/nbs/"
									+ sCurrentTask
									+ ".do?ContextAction=DocumentIDFromInv"
									+ "&method=viewSubmit&nbsDocumentUid="
									+ mrsVO.getUid()
									+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
											.formatDatewithHrMin(mrsVO
													.getDateReceived())+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
							}
						}
					}
				}
				request.setAttribute("observationSummaryMorbList", obsMorbSummary);
				form.getAttributeMap().put("CurrentTask", sCurrentTask);

				// ER16652 Start
				if (obsLabSummary != null && obsLabSummary.size() > 0) {
					obsLabSummary
							.addAll(pgActProxy.getTheLabReportSummaryVOCollection() == null ? new ArrayList<Object>()
									: pgActProxy
											.getTheLabReportSummaryVOCollection());
				} else {
					obsLabSummary = (ArrayList<Object>) pgActProxy
							.getTheLabReportSummaryVOCollection();
				}

			setLabReportsDisplayList(obsLabSummary, request, sCurrentTask, form.getActionMode());
				// ER16652 End

				/*if (obsLabSummary != null) {
					HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
					Iterator<Object> ite = obsLabSummary.iterator();
					while (ite.hasNext()) {
						LabReportSummaryVO lrsVO = (LabReportSummaryVO) ite.next();
						if (lrsVO.isLabFromMorb())
							parameterMap.put("ContextAction", "ObservationMorbID");
						else
							parameterMap.put("ContextAction", "ObservationLabID");
						parameterMap.put("observationUID",
								(lrsVO.getObservationUid()).toString());
						parameterMap.put("method", "viewSubmit");
						lrsVO.setActionLink(DecoratorUtil.getOrderedTestString(
								getHiperlink(dateLink(lrsVO.getDateReceived()),
										sCurrentTask, parameterMap,
										form.getActionMode()), lrsVO));
						lrsVO.setResultedTestString(DecoratorUtil
								.getResultedTestsString(lrsVO
										.getTheResultedTestSummaryVOCollection()));
						parameterMap = new HashMap<Object, Object>();
					}
				}
				request.setAttribute("observationSummaryLabList", obsLabSummary);
				*/

				// Treatment

				// ER16652 Start
				if (treatments != null && treatments.size() > 0) {
					treatments
							.addAll(pgActProxy.getTheTreatmentSummaryVOCollection() == null ? new ArrayList<Object>()
									: pgActProxy
											.getTheTreatmentSummaryVOCollection());
				} else {
					treatments = (ArrayList<Object>) pgActProxy
							.getTheTreatmentSummaryVOCollection();
				}
				// ER16652 End

				if (treatments != null) {
					HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
					Iterator<Object> ite = treatments.iterator();
					while (ite.hasNext()) {
						TreatmentSummaryVO tsVO = (TreatmentSummaryVO) ite.next();
						// tsVO.getLocalId()
						if(tsVO.getNbsDocumentUid()!=null){
							String startDate = tsVO.getActivityFromTime() == null ? "No Date"
									: StringUtils.formatDate(tsVO.getActivityFromTime());
						if(form.getActionMode()!=null && form.getActionMode()
								.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) || form.getActionMode()
								.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))
							startDate = startDate+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">";
						else
							startDate = "<a href=\"/nbs/LoadViewDocument2.do?method=cdaDocumentView&docId="
									+ tsVO.getNbsDocumentUid()+"&eventId="+tsVO.getLocalId()
									+ "&eventType="+NEDSSConstants.TREATMENT_ACT_TYPE_CD
									+ "\" target=\"_blank\">" + startDate + "</a>"
									+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">";

							tsVO.setActionLink(startDate);
						}
						else{
//							if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask)){
//								tsVO.setActionLink(dateLink(tsVO.getActivityFromTime()));
//							}else{
								parameterMap.put("ContextAction", "TreatmentID");
								parameterMap.put("treatmentUID", tsVO.getTreatmentUid()
										.toString());
								parameterMap.put("method", "viewSubmit");
								parameterMap.put("invFormCd", form.getPageFormCd());
								tsVO.setActionLink(getHiperlink(
										dateLink(tsVO.getActivityFromTime()), sCurrentTask,
										parameterMap, form.getActionMode()));
							//}
						parameterMap = new HashMap<Object, Object>();
						}
					}
				}
				request.setAttribute("treatmentList", treatments);

				// notifications
				DecoratorUtil util = new DecoratorUtil();
				notifSummary = (ArrayList<Object>) pgActProxy
						.getTheNotificationSummaryVOCollection();
				String notifDisplayHTML = util.buildNotificationList(notifSummary);
				request.setAttribute("notificationListTable", notifDisplayHTML);

				// investigation history/audit log summary
				String invHistoryHTML = util.buildInvHistoryList(pgActProxy
						.getTheInvestigationAuditLogSummaryVOCollection());
				request.setAttribute("invHistoryTable", invHistoryHTML);

				// Vaccination
				vaccination = (ArrayList<Object>) pgActProxy
						.getTheVaccinationSummaryVOCollection();
				if (vaccination != null) {
					Iterator<Object> ite = vaccination.iterator();
					while (ite.hasNext()) {
						VaccinationSummaryVO vacVO = (VaccinationSummaryVO) ite.next();
						PageLoadUtil.createVaccinationLinkForSupplementalInfo(vacVO, form.getActionMode());
					}
				}
				request.setAttribute("vaccinationList", vaccination);
				CachedDropDownValues cdv = new CachedDropDownValues();
				// Document
				ArrayList<Object> document = (ArrayList<Object>) pgActProxy
						.getTheDocumentSummaryVOCollection();
				if (document != null) {
					HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
					Iterator<Object> ite = document.iterator();
					while (ite.hasNext()) {
						SummaryDT doc = (SummaryDT) ite.next();
						if(doc.getDocType() != null && doc.getDocType().length() > 0)
							doc.setDocType(cdv.getCodeShortDescTxt(doc.getDocType(),"PUBLIC_HEALTH_EVENT"));
						if (doc.getDocEventTypeCd() != null) {
							String docTypeCd = doc.getDocEventTypeCd();
							if (docTypeCd.equals(NEDSSConstants.LABRESULT_CODE))
								doc.setDocType("Lab Report");
							else if (docTypeCd.equals(NEDSSConstants.MORBIDITY_CODE))
								doc.setDocType("Morb Report");
							else if (docTypeCd.equals(NEDSSConstants.CLASS_CD_CONTACT))
								doc.setDocType("Contact Record");
						}
//					if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask) ){
//							doc.setActionLink(dateLink(doc.getAddTime()));
//						}
//						else{
						parameterMap.put("ContextAction",
								NBSConstantUtil.DocumentIDFromInv);
						parameterMap.put("nbsDocumentUid", doc.getNbsDocumentUid()
								.toString());
						parameterMap.put("method", "viewSubmit");
						doc.setActionLink(getHiperlink(dateLink(doc.getLastChgTime()),
								sCurrentTask, parameterMap, form.getActionMode()));
						//}
						parameterMap = new HashMap<Object, Object>();
					}
				}
				request.setAttribute("documentSummaryList", document);

				FileUploadUtil fileUtil = new FileUploadUtil();
				try {
					ArrayList<Object> attachmentDTs = (ArrayList<Object>) pgActProxy
							.getNbsAttachmentDTColl();
					if (attachmentDTs != null && attachmentDTs.size() > 0)
						FileUploadUtil.updateAttachmentsForView(attachmentDTs);
					request.setAttribute("nbsAttachments", attachmentDTs);
				} catch (Exception e) {
					logger.fatal("Error in gettng the attachment from the proxyVO: "+e.getMessage(), e);
					throw new Exception(e.getMessage());
				}
				try {
					NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request
							.getSession().getAttribute("NBSSecurityObject");
					ArrayList<Object> noteDTs = (ArrayList<Object>) ((PageActProxyVO) proxy)
							.getNbsNoteDTColl();
					if (noteDTs != null && noteDTs.size() > 0)
						noteDTs = fileUtil.updateNotesForPrivateInd(noteDTs,
								nbsSecurityObj);
					FileUploadUtil.updateNotesForView(noteDTs);
					request.setAttribute("nbsNotes", noteDTs);
				} catch (Exception e) {
					logger.fatal("Error in gettng the Notes from the proxyVO: "+e.getMessage(), e);
					throw new Exception(e.getMessage());
				}

				// Interviews
				theInterviewSummary = (ArrayList<Object>) pgActProxy
						.getTheInterviewSummaryDTCollection();
				if (theInterviewSummary != null) {
					String populateInterviewURL = "/nbs/PageAction.do?method=viewGenericLoad&mode=View&Action=DSInvestigationPath&businessObjectType=IXS&actUid=";
					Iterator<Object> ite = theInterviewSummary.iterator();
					while (ite.hasNext()) {
						String theIXSLink = "";
						InterviewSummaryDT ixsDT = (InterviewSummaryDT) ite.next();
						if (form.getActionMode().equalsIgnoreCase(
								NEDSSConstants.VIEW_LOAD_ACTION))
							theIXSLink = "<a href=\"javascript:populateInterviewPopUp("
									+ ixsDT.getInterviewUid()
									+ ")\">"
									+ dateLink(ixsDT.getInterviewDate()) + "</a>";
						else if (form.getActionMode().equalsIgnoreCase(
								NEDSSConstants.EDIT_LOAD_ACTION))
							theIXSLink = dateLink(ixsDT.getInterviewDate());
						ixsDT.setDateActionLink(theIXSLink);
					}
					request.setAttribute("populateInterviewURL",
							populateInterviewURL);
					NBSContext.store(request.getSession(),
							NBSConstantUtil.DSInterviewList, theInterviewSummary);
					request.setAttribute("interviewList", theInterviewSummary);
				}

				// Contract Tracking Contract Records
			    Collection<Object> contactRecords = (Collection<Object>) pgActProxy.getTheCTContactSummaryDTCollection();

			    CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();

			    String contextAction = request.getParameter("ContextAction");
			    contextAction = "ViewFile";// set in case of view person record
			    // TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS114", contextAction);
			    // NBSContext.lookInsideTreeMap(tm);
			    // String sCurrTask = NBSContext.getCurrentTask(session);
			    String conditionCd = pgActProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			    if (conditionCd != null)
			        form.getAttributeMap().put("headerConditionCode", conditionCd);
			    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile");
			    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + contextAction;
			    // String viewHref = "/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID");
			    String contactUrl = "/nbs/" + sCurrentTask + ".do?ContextAction=PatientSearch";
			    String populateContactRecord = "/nbs/ContactTracing.do?method=viewContact&mode=View&Action=DSInvestigationPath&DSInvestigationCondition="
			            + conditionCd;
			    String managectAssoUrl = "/nbs/" + sCurrentTask + ".do?ContextAction=ManageCtAssociation";
			    request.setAttribute("contactUrl", contactUrl);
			    request.setAttribute("populateContactRecord", populateContactRecord);
			    request.setAttribute("managectAssoUrl", managectAssoUrl);
			    InvestigationUtil.storeContactContextInformation(request.getSession(),
			            ((PageActProxyVO) proxy).getPublicHealthCaseVO(),
			            ((PageActProxyVO) proxy).getThePersonVOCollection());
			    if (contactRecords != null)
			    {
			        Iterator<Object> ite = contactRecords.iterator();
			        while (ite.hasNext())
			        {
			            CTContactSummaryDT ctSumDT = (CTContactSummaryDT) ite.next();

			            Timestamp namedOnDate = null;
			            if(ctSumDT.getNamedOnDate()!=null)
			          	  namedOnDate=ctSumDT.getNamedOnDate();
			            else if(ctSumDT.getInterviewDate()!=null)
			          	  namedOnDate = ctSumDT.getInterviewDate();
			            else
			          	  namedOnDate = ctSumDT.getCreateDate();
			            ctSumDT.setNamedOnDate(namedOnDate);

			            String conLink = null;
			            if (form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION)) {
			            	String sourceConditionCd  = ( (ctSumDT.getSourceConditionCd() == null)  ? "" :ctSumDT.getSourceConditionCd());
			            	String sourceDispositionCd  = ( (ctSumDT.getSourceDispositionCd() == null)  ? "" :ctSumDT.getSourceDispositionCd());
			            	String sourceInterviewStatusCd = ( (ctSumDT.getSourceInterviewStatusCd() == null)  ? "" :ctSumDT.getSourceInterviewStatusCd());
			            	String sourceCurrentSexCd  = ( (ctSumDT.getSourceCurrentSexCd() == null)  ? "" :ctSumDT.getSourceCurrentSexCd());
			                conLink = "<a href=\"javascript:populateContactRecordPopUp2(" + ctSumDT.getCtContactUid()
			                		+ ",'" + sourceConditionCd+ "','" +sourceDispositionCd+"','"+sourceInterviewStatusCd+"','"+sourceCurrentSexCd
			                        + "')\">" + getContactRecordIdDisplay(ctSumDT, true);
			            } else if (form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
			                conLink = getContactRecordIdDisplay(ctSumDT, false);
			            ctSumDT.setLocalId(conLink);
			            ctSumDT.setPriority((ctSumDT.getPriorityCd() == null) ? "" : cachedDropDownValues.getDescForCode(
			                    NEDSSConstants.CONTACT_PRIORITY, ctSumDT.getPriorityCd()));

			            ctSumDT.setDisposition((ctSumDT.getDispositionCd() == null) ? "" : getContactsDispositionDescription(ctSumDT));

			            // String phcLink = "<a  href=\"" +
			            // viewHref+"&publicHealthCaseUID="+
			            // String.valueOf(ctSumDT.getSubjectEntityPhcUid()) + "\">"+ ctSumDT.getLocalId() + "</a>";
			            // ctSumDT.setPhcLocalID(phcLink);
			            String viewFileUrl = "";
			            String contactCaseUrl = "";
			            if (map.get("urlForViewFile") != null)
			            {
			                viewFileUrl = map.get("urlForViewFile").toString();
			                contactCaseUrl = map.get("ContactCase").toString();
			            }
			            if (ctSumDT.isContactNamedByPatient()
			                    && form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION))
			            {
			                String patLink = "<a href=\"/nbs/" + sCurrentTask + ".do?ContextAction=" + viewFileUrl
			                        + "&uid=" + String.valueOf(ctSumDT.getContactMprUid()) + "\">" + getContactsNamedByPatientNameString(ctSumDT,true);
			                // String patLink =
			                // "<a href=\"/nbs/LoadViewFile1.do?ContextAction=ViewFile&uid="+
			                // String.valueOf(ctSumDT.getContactEntityUid()) + "\">"
			                // + ctSumDT.getName() + "</a>";
			                ctSumDT.setName(patLink);
			                if (ctSumDT.getContactPhcLocalId() != null)
			                {
			                    String phcLink = "<a  href=\"/nbs/" + sCurrentTask + ".do?ContextAction=" + contactCaseUrl
			                            + "&publicHealthCaseUID=" + String.valueOf(ctSumDT.getContactEntityPhcUid())
			                            + "\">" + ctSumDT.getContactPhcLocalId() + "</a>";
			                    ctSumDT.setContactPhcLocalId(phcLink);
			                }
			                contactNamedByPatList.add(ctSumDT);
			            }
			            else if (ctSumDT.isContactNamedByPatient()
			                    && form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
			            {
			                String patLink = getContactsNamedByPatientNameString(ctSumDT,false);
			                ctSumDT.setName(patLink);
			                if (ctSumDT.getContactPhcLocalId() != null)
			                {
			                    String phcLink = ctSumDT.getContactPhcLocalId();
			                    ctSumDT.setContactPhcLocalId(phcLink);
			                }
			                contactNamedByPatList.add(ctSumDT);
			            }

			            if (ctSumDT.isPatientNamedByContact()
			                    && form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION))
			            {
			            	String namedByStr = getContactsNamedByContactNameString(ctSumDT);
			                String patLink = "<a href=\"/nbs/" + sCurrentTask + ".do?ContextAction=" + viewFileUrl
			                        + "&uid=" + String.valueOf(ctSumDT.getSubjectMprUid()) + "\">" + namedByStr
			                        + "</a>";
			                ctSumDT.setNamedBy(patLink);
			                if (ctSumDT.getSubjectPhcLocalId() != null)
			                {
			                    String phcLink = "<a  href=\"/nbs/" + sCurrentTask + ".do?ContextAction=" + contactCaseUrl
			                            + "&publicHealthCaseUID=" + String.valueOf(ctSumDT.getSubjectEntityPhcUid())
			                            + "\">" + ctSumDT.getSubjectPhcLocalId() + "</a>";
			                    ctSumDT.setSubjectPhcLocalId(phcLink);
			                }
			                patNamedByContactsList.add(ctSumDT);
			            }
			            else if (ctSumDT.isPatientNamedByContact()
			                    && form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
			            {
			                String patLink = getContactsNamedByContactNameString(ctSumDT);
			                ctSumDT.setNamedBy(patLink);
			                if (ctSumDT.getSubjectPhcLocalId() != null)
			                {
			                    String phcLink = ctSumDT.getSubjectPhcLocalId();
			                    ctSumDT.setSubjectPhcLocalId(phcLink);
			                }
			                patNamedByContactsList.add(ctSumDT);
			            }

			        }
			    }
			}
			request.setAttribute("contactNamedByPatList", contactNamedByPatList);
			request.setAttribute("patNamedByContactsList", patNamedByContactsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageLoadUtil.populatePageAssocations: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + sPublicHealthCaseUID + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	public static void setLabReportsDisplayList(
			ArrayList<Object> LabSummaryList, HttpServletRequest request, String sCurrTask, String actionMode) throws NEDSSAppException {
		try {
			Collection<PersonReportsSummaryDT> summaryLabReportList = new ArrayList<PersonReportsSummaryDT>();
			if (LabSummaryList == null || LabSummaryList.size() == 0) {
				logger.debug("Observation summary collection arraylist is null");
			} else {
				Iterator<Object> obsIterator = LabSummaryList.iterator();

				while (obsIterator.hasNext()) {
					logger.debug("Inside iterator.hasNext()...");
					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) obsIterator
							.next();
					String processingDecision = "";
					if (labReportSummaryVO.getProcessingDecisionCd() != null)
						processingDecision = CachedDropDowns
								.getCodeDescTxtForCd(
										labReportSummaryVO
												.getProcessingDecisionCd(),
										NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
					dt.setProgArea(labReportSummaryVO.getProgramArea());
					String startDate = labReportSummaryVO.getDateReceived() == null ? "No Date"
							: StringUtils.formatDate(labReportSummaryVO
									.getDateReceived());
					if (labReportSummaryVO.isLabFromDoc()) {
					if((actionMode!=null && actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) || actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))){
							startDate = startDate+ "<br>"+ StringUtils
									.formatDatewithHrMin(labReportSummaryVO
											.getDateReceived());
						}
						else
						startDate = "<a href=\"/nbs/"
								+ sCurrTask
								+ ".do?ContextAction=DocumentIDFromInv"
								+ "&method=viewSubmit&nbsDocumentUid="
								+ labReportSummaryVO.getUid()
								+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
										.formatDatewithHrMin(labReportSummaryVO
												.getDateReceived());
					} else{
					if((actionMode!=null && actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) || actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))){
							startDate = startDate+ "<br>"+ StringUtils
									.formatDatewithHrMin(labReportSummaryVO
											.getDateReceived());
						}
						else{
						startDate = "<a href=\"/nbs/"
								+ sCurrTask
								+ ".do?ContextAction=ObservationLabID"
								+ "&method=viewSubmit&observationUID="
								+ labReportSummaryVO.getObservationUid()
								+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
										.formatDatewithHrMin(labReportSummaryVO
												.getDateReceived());
						}
					}
					if(processingDecision!=null && processingDecision.trim().length()>0)
						processingDecision = "<br>"+ processingDecision;
					// Append Electronic Ind
					if (labReportSummaryVO.getElectronicInd() != null
							&& labReportSummaryVO.getElectronicInd().equals("Y"))
						dt.setDateReceived(startDate
								+ processingDecision
								+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
					else
						dt.setDateReceived(startDate + processingDecision);
					String provider = getProviderFullName(
							labReportSummaryVO.getProviderPrefix(),
							labReportSummaryVO.getProviderFirstName(),
							labReportSummaryVO.getProviderLastName(),
							labReportSummaryVO.getProviderSuffix());
					provider = provider == null ? ""
							: "<b>Ordering Provider:</b><br>" + provider;
					String facility = labReportSummaryVO.getReportingFacility() == null ? ""
							: "<b>Reporting Facility:</b><br>"
									+ labReportSummaryVO.getReportingFacility();
					dt.setProviderFacility(facility + "<br>" + provider);
					String dateCollected = labReportSummaryVO.getDateCollected() == null ? "No Date"
								: StringUtils.formatDate(labReportSummaryVO
										.getDateCollected());
					dt.setDateCollected(dateCollected);
					if (labReportSummaryVO.isLabFromDoc())
						dt.setDescription(labReportSummaryVO
								.getResultedTestString());
					else
						dt.setDescription(DecoratorUtil
								.getResultedTestsStringForWorup(labReportSummaryVO
										.getTheResultedTestSummaryVOCollection()));

					dt.setEventId(labReportSummaryVO.getLocalId() == null ? ""
							: labReportSummaryVO.getLocalId());

					summaryLabReportList.add(dt);
				} // while
				request.setAttribute("observationSummaryLabList", summaryLabReportList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

        private static String getProviderFullName(String prefix,String firstNm,String lastNm, String sufix) throws NEDSSAppException{
            try {
				StringBuffer sb = new StringBuffer("");
				if(prefix!=null && !prefix.equals("")){
				    sb.append(prefix).append(" ");
				}
				if(firstNm!=null && !firstNm.equals("")){
				    sb.append(firstNm).append(" ");
				}
				if(lastNm!=null && !lastNm.equals("")){
				    sb.append(lastNm).append(" ");
				}
				if(sufix!=null && !sufix.equals("")){
				    sb.append(sufix).append(" ");
				}
				if(sb.toString().trim().equals(""))
				    return null;
				else
				return sb.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.fatal("firstNm: " + firstNm + ", lastNm: " + lastNm + ", " + e.getMessage(), e);
				throw new NEDSSAppException(e.getMessage(), e);
			}
         }

	  /*
     * CON101893000GA01
     * Field Followup (P1)
     */
    private static String getContactRecordIdDisplay(CTContactSummaryDT contactsumDT, boolean isLink) throws NEDSSAppException {
    	try {
			StringBuffer stBuff = new StringBuffer("");
			CachedDropDownValues cddV = new CachedDropDownValues();
			stBuff.append(contactsumDT.getLocalId());
			if (isLink) stBuff.append("</a>");
			if (contactsumDT.getContactProcessingDecisionCd() != null && !contactsumDT.getContactProcessingDecisionCd().isEmpty()) {
				String processingDecision = cddV.getDescForCode( CTConstants.StdNbsProcessingDecisionCRLookupCodeset,contactsumDT.getContactProcessingDecisionCd());
				String referralBasis = contactsumDT.getContactReferralBasisCd()==null?"":contactsumDT.getContactReferralBasisCd();
				if (processingDecision != null && !processingDecision.isEmpty()) {
					stBuff.append("<br><b>"+processingDecision+"(" +referralBasis +")</b>");
				} else logger.warn("getEventDisplay() can't find processing decision description for code=" +contactsumDT.getContactProcessingDecisionCd());
			}
			return stBuff.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ContactEntityPhcUid: " + contactsumDT.getContactEntityPhcUid() + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/*
     * i.e. Sly, Beth
     *   or Sly, Beth (with Randall, Dan, Jr Init w/out Ix)
     */
    private static String getContactsNamedByPatientNameString(
			CTContactSummaryDT contactsumDT, boolean endAnchor) throws NEDSSAppException {
    	try {
			StringBuffer stBuff = new StringBuffer("");
			Boolean thirdParty = false;
			Boolean otherInfected = false;
			if (contactsumDT.getNamedDuringInterviewUid() != null && contactsumDT.getNamedDuringInterviewUid().equals(CTConstants.StdInitiatedWithoutInterviewLong))
				thirdParty = true;
			if (contactsumDT.getOtherInfectedPatientName()!= null && !contactsumDT.getOtherInfectedPatientName().isEmpty())
				otherInfected = true;

			if (contactsumDT.getContactName() != null)
				stBuff.append(contactsumDT.getContactName());
			if (endAnchor) stBuff.append("</a>");
			if (thirdParty && !otherInfected)
				stBuff.append(" (").append(CTConstants.StdInitiatedWithoutInterviewStr).append(")");
			if (otherInfected) {
			    	stBuff.append(" (with ").append(contactsumDT.getOtherInfectedPatientName());
			    	if (thirdParty)
			    		stBuff.append(": ").append(CTConstants.StdInitiatedWithoutInterviewStr);
			    	stBuff.append(")");
			}
			return stBuff.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ContactEntityPhcUid: " + contactsumDT.getContactEntityPhcUid() + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	/*
     * i.e. Sly, Beth
     *   or  Init w/out (with Randall, Dan, Jr)
     *   or Bolt, Mary(Sly, Beth with Freeman, John)
     */
    private static String getContactsNamedByContactNameString(
			CTContactSummaryDT contactsumDT) throws NEDSSAppException {
    	try {
			StringBuffer stBuff = new StringBuffer("");
			Boolean thirdParty = false;
			Boolean otherInfected = false;
			if (contactsumDT.getNamedDuringInterviewUid() != null && contactsumDT.getNamedDuringInterviewUid().equals(CTConstants.StdInitiatedWithoutInterviewLong))
				thirdParty = true;
			if (contactsumDT.getOtherInfectedPatientName()!= null && !contactsumDT.getOtherInfectedPatientName().isEmpty())
				otherInfected = true;
			if (contactsumDT.isOtherNamedByPatient()) {
				if (contactsumDT.getSubjectName() != null)
					stBuff.append(contactsumDT.getSubjectName());
				stBuff.append(" (");
				if (contactsumDT.getContactName() != null)
					stBuff.append(contactsumDT.getContactName());
				if (contactsumDT.getOtherInfectedPatientName() != null)
					stBuff.append(" with ").append(contactsumDT.getOtherInfectedPatientName());
				stBuff.append(")");
			} else {
				if (!otherInfected && !thirdParty)
					stBuff.append(contactsumDT.getSubjectName());
				if (thirdParty)
					stBuff.append(CTConstants.StdInitiatedWithoutInterviewStr).append(" (");
				if (otherInfected && !thirdParty)
					stBuff.append(contactsumDT.getSubjectName()).append(" (");
				if (!otherInfected && thirdParty)
					stBuff.append("with ").append(contactsumDT.getSubjectName());
				if (otherInfected)
					stBuff.append(" with ").append(contactsumDT.getOtherInfectedPatientName());
				if (otherInfected || thirdParty)
					stBuff.append(")");
			}
			return stBuff.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ContactEntityPhcUid: " + contactsumDT.getContactEntityPhcUid() + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

    /*
     * i.e. Disposition: Infected - Not Treated)
     */
    private static String getContactsDispositionDescription(
			CTContactSummaryDT contactsumDT) throws NEDSSAppException {
    	try {
			CachedDropDownValues cddV = new CachedDropDownValues();
			boolean stdProgramArea = false;
			if (contactsumDT.getProgAreaCd() != null)
				stdProgramArea = PropertyUtil.isStdOrHivProgramArea(contactsumDT.getProgAreaCd()) ;

			String theDispoCd = contactsumDT.getDispositionCd();
			String theDispoDesc = "";
			if (theDispoCd != null && !theDispoCd.isEmpty()) {
				if (stdProgramArea)
					theDispoDesc = cddV.getDescForCode( CTConstants.StdNbsDispositionLookupCodeset,theDispoCd);
				else
					theDispoDesc = cddV.getDescForCode( CTConstants.NonStdNbsDispositionLookupCodeset,theDispoCd);
				if (theDispoDesc == null || theDispoDesc.isEmpty())
					logger.warn("getContactsDispositionDescription() ?? code lookup failed for code=" +theDispoCd);
			}
			contactsumDT.setDisposition(theDispoDesc);
			return theDispoDesc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ContactEntityPhcUid: " + contactsumDT.getContactEntityPhcUid() + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}



	protected static String dateLink(java.sql.Timestamp timestamp) throws NEDSSAppException {
		try {
			Date date = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			if (timestamp != null) {
				date = new Date(timestamp.getTime());
			}
			if (date == null) {
				return "No Date";
			} else {
				return formatter.format(date);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("timestamp: " + timestamp + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	public static String getHiperlink(String displayNm, String currentTask,
			HashMap<Object, Object> parameterMap, String actionMode) throws NEDSSAppException {
		try {
			if (actionMode != null
					&& (actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) || actionMode
							.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))) {
				return displayNm;
			} else {
				String url = NavigatorUtil.getLink(currentTask + ".do", displayNm,
						parameterMap);
				return url;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("displayNm: " + displayNm + ", currentTask: " + currentTask + ", " + e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	protected static String setContextForEdit(PageForm form,
			HttpServletRequest request, HttpSession session) throws Exception {
		String sCurrentTask;
		try {
			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form
					.getPageClientVO().getOldPageProxyVO());

			form.getAttributeMap().put(
					"patientLocalId",
					PersonUtil.getDisplayLocalID(personVO.getThePersonDT()
							.getLocalId()));
			form.getAttributeMap().put(
					"caseLocalId",
					((PageActProxyVO) form.getPageClientVO()
							.getOldPageProxyVO()).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getLocalId());

			Collection<Object> nms = personVO.getThePersonNameDTCollection();
			String strPName = "";
			String strFName = "";
			String strMName = "";
			String strLName = "";
			String DOB = "";
			String CurrSex = "";
			String nmSuffix = "";
			if (nms != null) {
				Iterator<Object> itname = nms.iterator();
				Timestamp mostRecentNameAOD = null;
				while (itname.hasNext()) {
					PersonNameDT name = (PersonNameDT) itname.next();

					// for personInfo
					if (name != null
							&& name.getNmUseCd() != null
							&& name.getNmUseCd().equals(NEDSSConstants.LEGAL)
							&& name.getStatusCd() != null
							&& name.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)
							&& name.getRecordStatusCd() != null
							&& name.getRecordStatusCd().equals(
									NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						if (mostRecentNameAOD == null
								|| (name.getAsOfDate() != null && !name
										.getAsOfDate()
										.before(mostRecentNameAOD))) {
							strFName = "";
							strMName = "";
							strLName = "";
							nmSuffix = "";
							mostRecentNameAOD = name.getAsOfDate();
							if (name.getFirstNm() != null)
								strFName = name.getFirstNm();
							if (name.getMiddleNm() != null)
								strMName = name.getMiddleNm();
							if (name.getLastNm() != null)
								strLName = name.getLastNm();
							if (name.getNmSuffix() != null)
								nmSuffix = name.getNmSuffix();
						}
					}
				}
			}
			strPName = strFName + " " + strMName + " " + strLName;
			if (null == strPName || strPName.equalsIgnoreCase("null")) {
				strPName = "";
			}

			form.getAttributeMap().put("patientLocalName", strPName);
			request.setAttribute("patientLocalName", strPName);
			CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
			request.setAttribute("patientSuffixName", cachedDropDownValues
					.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));

			if (personVO.getThePersonDT().getBirthTime() != null) {
				// DOB =
				// PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
				java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat(
						"MM/dd/yyyy", java.util.Locale.US);
				DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
			}
			form.getAttributeMap().put("patientDOB", DOB);
			request.setAttribute("patientDOB", DOB);

			if (personVO.getThePersonDT().getCurrSexCd() != null)
				CurrSex = personVO.getThePersonDT().getCurrSexCd();
			if (CurrSex.equalsIgnoreCase("F"))
				CurrSex = "Female";
			if (CurrSex.equalsIgnoreCase("M"))
				CurrSex = "Male";
			if (CurrSex.equalsIgnoreCase("U"))
				CurrSex = "Unknown";
			form.getAttributeMap().put("patientCurrSex", CurrSex);
			request.setAttribute("patientCurrSex", CurrSex);
			// String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

			// set notifications (createdBy/Date, updatedBy/Date) in request.
			PublicHealthCaseDT phcDT = ((PageActProxyVO) form.getPageClientVO()
					.getOldPageProxyVO()).getPublicHealthCaseVO()
					.getThePublicHealthCaseDT();
			request.setAttribute("createdDate",
					StringUtils.formatDate(phcDT.getAddTime()));
			request.setAttribute("createdBy", phcDT.getAddUserName());
			request.setAttribute("updatedDate",
					StringUtils.formatDate(phcDT.getLastChgTime()));
			request.setAttribute("updatedBy", phcDT.getLastChgUserName());

			setLastChangeUserAndTime(form, request);

			String passedContextAction = request.getParameter("ContextAction");
			TreeMap<Object, Object> tm = NBSContext.getPageContext(session,
					"PS023", passedContextAction);
			ErrorMessageHelper.setErrMsgToRequest(request);
			sCurrentTask = NBSContext.getCurrentTask(session);

			form.getAttributeMap().put("ContextAction", passedContextAction);
			form.getAttributeMap().put("CurrentTask", sCurrentTask);
			form.getAttributeMap().put(
					"formHref",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("Submit"));
			/*
			if(sCurrentTask.equalsIgnoreCase("MergeInvestigation1")){

				form.getAttributeMap().put(
						"linkValueCancelCompare",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ "ReturnToFileEvents" + "&invFormCd="
								+ form.getPageFormCd() + "");

				form.getAttributeMap().put(
						"linkValueMergeInvestigations",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ "EditMerge"+ "&invFormCd="
								+ form.getPageFormCd() + "");

			}*/



			form.getAttributeMap().put(
					"Cancel",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("Cancel"));
			form.getAttributeMap().put(
					"ReturnToFileEvents",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("ReturnToFileEvents"));

			form.getAttributeMap().put(
					"SubmitNoViewAccess",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("SubmitNoViewAccess"));
			form.getAttributeMap().put(
					"DiagnosedCondition",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("DiagnosedCondition"));
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setContextForEdit Exception thrown:" + e.getMessage(),e);
			throw new Exception( e.getMessage(),e);
		}
		return sCurrentTask;
	}

	private String setContextForCreate(PersonVO personVO,
			HttpServletRequest request, PageForm form) throws Exception {

		String sCurrentTask = "";
		try {
			String localId = personVO.getThePersonDT().getLocalId();
			form.getAttributeMap().put("patientLocalId",
					PersonUtil.getDisplayLocalID(localId));
			Collection<Object> nms = personVO.getThePersonNameDTCollection();
			String strPName = "";
			String strFName = "";
			String strMName = "";
			String strLName = "";
			String DOB = "";
			String CurrSex = "";
			String nmSuffix = "";
			if (nms != null) {
				Iterator<Object> itname = nms.iterator();
				Timestamp mostRecentNameAOD = null;
				while (itname.hasNext()) {
					PersonNameDT name = (PersonNameDT) itname.next();

					// for personInfo
					if (name != null
							&& name.getNmUseCd() != null
							&& name.getNmUseCd().equals(NEDSSConstants.LEGAL)
							&& name.getStatusCd() != null
							&& name.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)
							&& name.getRecordStatusCd() != null
							&& name.getRecordStatusCd().equals(
									NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						if (mostRecentNameAOD == null
								|| (name.getAsOfDate() != null && !name
										.getAsOfDate()
										.before(mostRecentNameAOD))) {
							strFName = "";
							strMName = "";
							strLName = "";
							nmSuffix = "";
							mostRecentNameAOD = name.getAsOfDate();
							if (name.getFirstNm() != null)
								strFName = name.getFirstNm();
							if (name.getMiddleNm() != null)
								strMName = name.getMiddleNm();
							if (name.getLastNm() != null)
								strLName = name.getLastNm();
							if (name.getNmSuffix() != null)
								nmSuffix = name.getNmSuffix();

						}
					}

				}
			}
			strPName = strFName + " " + strMName + " " + strLName;
			if (null == strPName || strPName.equalsIgnoreCase("null")) {
				strPName = "";
			}

			form.getAttributeMap().put("patientLocalName", strPName);
			request.setAttribute("patientLocalName", strPName);
			CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
			request.setAttribute("patientSuffixName", cachedDropDownValues
					.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));

			if (personVO.getThePersonDT().getBirthTime() != null) {
				// DOB =
				// PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
				java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat(
						"MM/dd/yyyy", java.util.Locale.US);
				DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
			}
			form.getAttributeMap().put("patientDOB", DOB);
			request.setAttribute("patientDOB", DOB);

			if (personVO.getThePersonDT().getCurrSexCd() != null)
				CurrSex = personVO.getThePersonDT().getCurrSexCd();
			if (CurrSex.equalsIgnoreCase("F"))
				CurrSex = "Female";
			if (CurrSex.equalsIgnoreCase("M"))
				CurrSex = "Male";
			if (CurrSex.equalsIgnoreCase("U"))
				CurrSex = "Unknown";
			form.getAttributeMap().put("patientCurrSex", CurrSex);
			request.setAttribute("patientCurrSex", CurrSex);
			// String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

			form.getPageClientVO().setAnswer(PageConstants.PATIENT_LOCAL_ID,
					localId);
			String passedContextAction = request.getParameter("ContextAction");
			TreeMap<Object, Object> tm = NBSContext.getPageContext(
					request.getSession(), "PS020", passedContextAction);
			ErrorMessageHelper.setErrMsgToRequest(request);
			sCurrentTask = NBSContext.getCurrentTask(request.getSession());
			form.getAttributeMap().put("CurrentTask", sCurrentTask);
			form.getAttributeMap().put("formHref",
					"/nbs/" + sCurrentTask + ".do");
			if (request.getSession().getAttribute("PrevPageId") != null
					&& ((String) request.getSession()
							.getAttribute("PrevPageId")).equals("PS233")) {
				if ((sCurrentTask.equals("CreateInvestigation11") || sCurrentTask
						.equals("CreateInvestigation10"))
						&& (NBSContext.retrieve(request.getSession(),
								NBSConstantUtil.DSDocConditionCD) != null)) {
					form.getAttributeMap().put(
							"Cancel",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("CancelToViewDoc"));
				}
			} else {
				form.getAttributeMap().put(
						"Cancel",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("Cancel"));
			}

			form.getAttributeMap().put(
					"DiagnosedCondition",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("DiagnosedCondition"));
			form.getAttributeMap().put(
					"SubmitNoViewAccess",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("SubmitNoViewAccess"));
			form.getAttributeMap().put("ContextAction", tm.get("Submit"));
			request.getSession().removeAttribute("PrevPageId");
			// Referral Basis is used for STD programs..
			String stdReferralBasis = "";
			String processingDecision = null;
			try {
				stdReferralBasis = (String) NBSContext.retrieve(
						request.getSession(), NBSConstantUtil.DSReferralBasis);
				processingDecision = (String) NBSContext.retrieve(
						request.getSession(),
						NBSConstantUtil.DSProcessingDecision);
			} catch (Exception e) {
				logger.debug("No STD Referral Basis or Processing Decision");
				logger.debug("PageLoadUtil.setCommonAnswersForViewEdit NedssAppException thrown. Please check");
				// throw new NEDSSAppException(e.toString());
			}
			if (processingDecision != null) {
				form.getAttributeMap().put(NEDSSConstants.PROCESSING_DECISION,
						processingDecision);
			}
			if (stdReferralBasis != null && stdReferralBasis != "") {
				form.getPageClientVO().getAnswerMap()
						.put(NEDSSConstants.REFERRAL_BASIS, stdReferralBasis);
				if (sCurrentTask.equals("CreateInvestigation1")) {
					form.getPageClientVO()
							.getAnswerMap()
							.put(NEDSSConstants.REFERRAL_BASIS_OOJ,
									stdReferralBasis);
				}
			}
			if (sCurrentTask.equals("CreateInvestigation1")) {
				this.getDefaultInvestigatorDetailsIfPresent(request, form);
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setContextForCreate Exception thrown:"
					+  e.getMessage(),e);
			throw new Exception( e.getMessage(),e);
		}
		return sCurrentTask;
	}

	@SuppressWarnings("unchecked")
	private void populateLabMorbValues(PageForm form,
			String sCurrentTask, HttpServletRequest request) throws Exception {

		try {
			form.getAttributeMap().remove("ReadOnlyJursdiction");
			if (sCurrentTask.equals("CreateInvestigation2")
					|| sCurrentTask.equals("CreateInvestigation3")
					|| sCurrentTask.equals("CreateInvestigation4")
					|| sCurrentTask.equals("CreateInvestigation5")
					|| sCurrentTask.equals("CreateInvestigation6")
					|| sCurrentTask.equals("CreateInvestigation7")
					|| sCurrentTask.equals("CreateInvestigation8")
					|| sCurrentTask.equals("CreateInvestigation9")) {
				try {
					String jurisdiction = (String) NBSContext.retrieve(
							request.getSession(),
							NBSConstantUtil.DSInvestigationJurisdiction);
					form.getPageClientVO().setAnswer(
							PageConstants.JURISDICTION, jurisdiction);
					form.getAttributeMap().put("ReadOnlyJursdiction",
							"ReadOnlyJursdiction");
				} catch (Exception e) {
					logger.error("Error in populateLabMorbValues while getting jurisdiction value");
					logger.fatal("PageLoadUtil.populateLabMorbValues Exception thrown:"
							+ e.getMessage(), e);
					throw new Exception(e);

				}
			}
			if (sCurrentTask.equals("CreateInvestigation5")
					|| sCurrentTask.equals("CreateInvestigation6")
					|| sCurrentTask.equals("CreateInvestigation7")
					|| sCurrentTask.equals("CreateInvestigation8")) {
				try {
					// this is from morb and for generic investigation only
					TreeMap<Object, Object> DSMorbMap = (TreeMap<Object, Object>) NBSContext
							.retrieve(request.getSession(),
									NBSConstantUtil.DSMorbMap);
					String INV111 = (String) DSMorbMap.get("INV111");
					form.getPageClientVO().setAnswer(
							PageConstants.DATE_REPORTED, INV111);
					// form.getPageClientVO().getPamAnswerMap().put(PageConstants.DATE_REPORTED,
					// INV111);
					String INV183ORG = (String) DSMorbMap.get("INV183ORG");
					// form.getPageClientVO().getAnswerMap().put("INV182",
					// INV182PRV);
					form.getAttributeMap().put("INV183SearchResult", INV183ORG);
					String INV183UID = (String) DSMorbMap.get("INV183UID");
					form.getAttributeMap().put("INV183Uid", INV183UID);

					String INV181PRV = (String) DSMorbMap.get("INV181PRV");
					// form.getPageClientVO().getAnswerMap().put("INV182",
					// INV182PRV);
					form.getAttributeMap().put("INV181SearchResult", INV181PRV);
					String INV181UID = (String) DSMorbMap.get("INV181UID");
					form.getAttributeMap().put("INV181Uid", INV181UID);

					String INV182PRV = (String) DSMorbMap.get("INV182PRV");
					// form.getPageClientVO().getAnswerMap().put("INV182",
					// INV182PRV);
					form.getAttributeMap().put("INV182SearchResult", INV182PRV);
					String INV182UID = (String) DSMorbMap.get("INV182UID");
					form.getAttributeMap().put("INV182Uid", INV182UID);

					String INV184ORG = (String) DSMorbMap.get("INV184ORG");
					// form.getPageClientVO().getAnswerMap().put("INV182",
					// INV182PRV);
					form.getAttributeMap().put("INV184SearchResult", INV184ORG);
					String INV184UID = (String) DSMorbMap.get("INV184UID");
					form.getAttributeMap().put("INV184Uid", INV184UID);

					String INV128 = (String) DSMorbMap.get("INV128");
					form.getPageClientVO().setAnswer(
							PageConstants.WAS_THE_PATIENT_HOSPITALIZED, INV128);

					String INV132 = (String) DSMorbMap.get("INV132");
					form.getPageClientVO().setAnswer(
							PageConstants.ADMISSION_DATE, INV132);

					String INV133 = (String) DSMorbMap.get("INV133");
					form.getPageClientVO().setAnswer(
							PageConstants.DISCHARGE_DATE, INV133);



					// added by jayasudha to display the diffrence between the dates on 17/02/2017.


				      if(INV132!=null && !INV132.equals("") && INV133!=null && !INV133.equals("")  ){
				    		 String  daysCount =   PageLoadUtil.getDateDiffrence(INV132 ,INV133);
				    		 form.getPageClientVO().setAnswer(PageConstants.DURATION_OF_STAY, daysCount);
				    	     }
				      // ednded by jayasudha


					String INV136 = (String) DSMorbMap.get("INV136");
					form.getPageClientVO().setAnswer(
							PageConstants.DIAGNOSIS_DATE, INV136);

					String INV137 = (String) DSMorbMap.get("INV137");
					form.getPageClientVO().setAnswer(
							PageConstants.ILLNESS_ONSET_DATE, INV137);

					String INV145 = (String) DSMorbMap.get("INV145");
					form.getPageClientVO().setAnswer(
							PageConstants.DID_THE_PATIENT_DIE, INV145);

					if (DSMorbMap.get("INV178") != null) {
						String INV178 = (String) DSMorbMap.get("INV178");
						form.getPageClientVO().setAnswer(
								PageConstants.PREGNANCY_STATUS, INV178);
					}

					if (DSMorbMap.get("NBS128") != null) {
						Integer NBS128 = (Integer) DSMorbMap.get("NBS128");
						form.getPageClientVO()
								.setAnswer(PageConstants.PREGNANT_WEEKS,
										NBS128.toString());
					}
					String INV148 = (String) DSMorbMap.get("INV148");
					form.getPageClientVO()
							.setAnswer(
									PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY,
									INV148);

					String INV149 = (String) DSMorbMap.get("INV149");
					form.getPageClientVO().setAnswer(
							PageConstants.IS_THIS_PERSON_FOOD_HANDLER, INV149);


					String processingDecision = (String) DSMorbMap
							.get(NEDSSConstants.PROCESSING_DECISION);
					if (processingDecision != null) {
						form.getAttributeMap()
								.put(NEDSSConstants.PROCESSING_DECISION,
										(String) DSMorbMap
												.get(NEDSSConstants.PROCESSING_DECISION));
						this.getDefaultInvestigatorDetailsIfPresent(request, form);
					}
					// Coming from Morb Report - set the Referral Basis to T2
					// for Morb
					form.getPageClientVO()
							.getAnswerMap()
							.put(NEDSSConstants.REFERRAL_BASIS,
									NEDSSConstants.REFERRAL_BASIS_MORB);
				} catch (Exception e) {
					logger.fatal("Exception in populateLabMorbValues: " + e.getMessage(), e);
					e.printStackTrace();
				}

			} else if (sCurrentTask.equals("CreateInvestigation2")
					|| sCurrentTask.equals("CreateInvestigation3")
					|| sCurrentTask.equals("CreateInvestigation4")
					|| sCurrentTask.equals("CreateInvestigation9")) {
				try {
					// this is from lab and for generic investigation only
					TreeMap<Object, Object> labMap = (TreeMap<Object, Object>) NBSContext
							.retrieve(request.getSession(),
									NBSConstantUtil.DSLabMap);
					String INV111 = (String) labMap.get("INV111");
					// form.getPageClientVO().getAnswerMap().put(PageConstants.DATE_REPORTED,
					// INV111);
					form.getPageClientVO().setAnswer(
							PageConstants.DATE_REPORTED, INV111);
					String INV182PRV = (String) labMap.get("INV182PRV");
					// form.getPageClientVO().getAnswerMap().put("INV182",
					// INV182PRV);
					form.getAttributeMap().put("INV182SearchResult", INV182PRV);
					String INV182UID = (String) labMap.get("INV182UID");
					form.getAttributeMap().put("INV182Uid", INV182UID);
					// form.getPageClientVO().getAnswerMap().put("INV182UiD",
					// INV182UID);
					String INV183ORG = (String) labMap.get("INV183ORG");
					form.getPageClientVO().getAnswerMap()
							.put("INV183", INV183ORG);
					String NBS291ORG = (String) labMap.get("NBS291ORG");
					form.getPageClientVO().getAnswerMap()
					.put("NBS291", NBS291ORG);
					form.getAttributeMap().put("INV183SearchResult", INV183ORG);
					form.getAttributeMap().put("NBS291SearchResult", NBS291ORG);
					String INV183UID = (String) labMap.get("INV183UID");
					// form.getPageClientVO().getAnswerMap().put("INV183Uid",
					// INV183UID);
					form.getAttributeMap().put("INV183Uid", INV183UID);
					//gst ici 10/31/2016 Physician Ordering Clinic
					String NBS291UID = (String) labMap.get("NBS291UID");
					if (NBS291UID != null)
						form.getAttributeMap().put("NBS291Uid", NBS291UID);
					if (labMap.get("INV178") != null) {
						String INV178 = (String) labMap.get("INV178");
						form.getPageClientVO().setAnswer(
								PageConstants.PREGNANCY_STATUS, INV178);
					}
					if (labMap.get("NBS128") != null) {
						Integer NBS128 = (Integer) labMap.get("NBS128");
						form.getPageClientVO()
								.setAnswer(PageConstants.PREGNANT_WEEKS,
										NBS128.toString());
					}
					String processingDecision = (String) labMap
							.get(NEDSSConstants.PROCESSING_DECISION);
					if(processingDecision!=null){
						form.getAttributeMap()
								.put(NEDSSConstants.PROCESSING_DECISION,
										(String) labMap
												.get(NEDSSConstants.PROCESSING_DECISION));
						this.getDefaultInvestigatorDetailsIfPresent(request, form);
					}
					// Coming from Lab Report - set the Referral Basis to T1
					// for Lab
					form.getPageClientVO()
							.getAnswerMap()
							.put(NEDSSConstants.REFERRAL_BASIS,
									NEDSSConstants.REFERRAL_BASIS_LAB);
					populateFromPrePopMapping((TreeMap<Object,Object>)labMap.get(NEDSSConstants.LAB_FORM_CD),form);
					
				} catch (Exception e) {
					logger.error("Error in populateLabMorbValues while in else loop");
					logger.fatal("PageLoadUtil.populateLabMorbValues Exception thrown:"
							+  e.getMessage(),e);
					throw new Exception( e.getMessage(),e);

				}
			}
		} catch (Exception e) {
			logger.error("Error in populateLabMorbValues in outer try/catch loop");
			logger.fatal("PageLoadUtil.populateLabMorbValues Exception thrown:"
					+  e.getMessage(),e);
			throw new Exception( e.getMessage(),e);

		}
	}
	
	@SuppressWarnings("unchecked")
	public void populateFromPrePopMapping(TreeMap<Object, Object> prePopMap, PageForm form) throws Exception {
		try {

			if (prePopMap == null || prePopMap.size() == 0)
				return;
			TreeMap<Object, Object> toPrePopMap = (TreeMap<Object, Object>) QuestionsCache
					.getToPrePopFormMapping(form.getPageFormCd());

			if (toPrePopMap != null && toPrePopMap.size() > 0) {
				Collection<Object> toPrePopColl = toPrePopMap.values();
				if (toPrePopColl != null && toPrePopColl.size() > 0) {
					for (Object obj : toPrePopColl) {
						PrePopMappingDT toPrePopMappingDT = (PrePopMappingDT) obj;
						String mappingKey = toPrePopMappingDT.getFromAnswerCode() == null
								? toPrePopMappingDT.getFromQuestionIdentifier()
								: toPrePopMappingDT.getFromQuestionIdentifier() + "$"
										+ toPrePopMappingDT.getFromAnswerCode();
						if (prePopMap.containsKey(mappingKey)) {

							if (toPrePopMappingDT.getToDataType() != null
									&& toPrePopMappingDT.getToDataType().equals(NEDSSConstants.DATE_DATATYPE)) {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
									SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
									String stringDate = (String) prePopMap.get(mappingKey);
									if (stringDate != null && stringDate.length() > 8)
										stringDate = stringDate.substring(0, 8);
									Date date = formatter.parse(stringDate);
									form.getPageClientVO().getAnswerMap()
											.put(toPrePopMappingDT.getToQuestionIdentifier(), sdf.format(date));
								} catch (Exception ex) {
									logger.error("Could not convert to date from value :" + prePopMap.get(mappingKey));
								}
							}

							else if (toPrePopMappingDT.getToAnswerCode() != null)
								form.getPageClientVO().getAnswerMap().put(toPrePopMappingDT.getToQuestionIdentifier(),
										toPrePopMappingDT.getToAnswerCode());
							else
								form.getPageClientVO().getAnswerMap().put(toPrePopMappingDT.getToQuestionIdentifier(),
										prePopMap.get(mappingKey));
						}
					}
				}
			} else
				logger.debug("No pre-pop mapping for Code: " + form.getPageFormCd());

		} catch (Exception e) {
			logger.error("Error in populateFromPrePopMapping");
			logger.fatal("PageLoadUtil.populateFromPrePopMapping Exception thrown:" + e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * getGenericPersonVO - look for a particular type in the Participation
	 * collection and retrieve the corresponding PersonVO from the
	 * PersonVOCollection
	 *
	 * @param typeCd
	 *            - participation type cd i.e.IntrvwerOfInterview
	 * @param pageProxyVO
	 *            - PageActProxyVO
	 * @return personVO or null if not found
	 */
    public PersonVO getGenericPersonVO(String type_cd, PageProxyVO proxyVO) throws NEDSSAppException
    {
        Collection<Object> participationDTCollection = null;
        Collection<Object> personVOCollection = null;
        try
        {
            participationDTCollection = ((PageActProxyVO) proxyVO).getTheParticipationDTCollection();
            personVOCollection = ((PageActProxyVO) proxyVO).getThePersonVOCollection();
            if (participationDTCollection != null)
            {
                return getPerson(type_cd, participationDTCollection, personVOCollection);
            }
        }
        catch (Exception e)
        {
            logger.fatal("PageLoadUtil.getGenericPersonVO Exception thrown:" +  e.getMessage(),e);
            throw new NEDSSAppException( e.getMessage(),e);
        }
        return null;
    }

    public static PersonVO getPersonVO(String type_cd, PageProxyVO proxyVO) throws NEDSSAppException
    {
        Collection<Object> participationDTCollection = null;
        Collection<Object> personVOCollection = null;
        try
        {
            participationDTCollection = ((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
                    .getTheParticipationDTCollection();
            personVOCollection = ((PageActProxyVO) proxyVO).getThePersonVOCollection();
            if (participationDTCollection != null)
            {
                return getPerson(type_cd, participationDTCollection, personVOCollection);
            }
        }
        catch (Exception e)
        {
            logger.fatal("PageLoadUtil.getPersonVO Exception thrown:" +  e.getMessage(),e);
            throw new NEDSSAppException( e.getMessage(),e);
        }
        return null;
    }

	public static PersonVO getPerson(String type_cd,
			Collection<Object> participationDTCollection,
			Collection<Object> personVOCollection) throws NEDSSAppException {
		try {
			ParticipationDT participationDT;
			PersonVO personVO ;
			Iterator<Object> anIterator1 = null;
			Iterator<Object> anIterator2 = null;
			for (anIterator1 = participationDTCollection.iterator(); anIterator1
					.hasNext();) {
				participationDT = (ParticipationDT) anIterator1.next();
				if (participationDT.getTypeCd() != null
						&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
					for (anIterator2 = personVOCollection.iterator(); anIterator2
							.hasNext();) {
						personVO = (PersonVO) anIterator2.next();
						if (personVO.getThePersonDT().getPersonUid()
								.longValue() == participationDT
								.getSubjectEntityUid().longValue()) {
							return personVO;
						} else
							continue;
					}
				} else
					continue;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getPerson Exception thrown:" +  e.getMessage(),e);
            throw new NEDSSAppException( e.getMessage(),e);
		}
	}

	public static List<PersonVO> getPersonVOCollection(
			String type_cd, PageProxyVO proxyVO) throws NEDSSAppException {
		Collection<Object> participationDTCollection = null;
		Collection<Object> personVOCollection = null;
		List<PersonVO> personVOs = new ArrayList<PersonVO>();
		try {
			ParticipationDT participationDT = null;
			PersonVO personVO = null;
			participationDTCollection = ((PageActProxyVO) proxyVO).getTheParticipationDTCollection();
			personVOCollection = ((PageActProxyVO) proxyVO)
					.getThePersonVOCollection();
			if (participationDTCollection != null && personVOCollection != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null
							&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
						for (anIterator2 = personVOCollection.iterator(); anIterator2
								.hasNext();) {
							personVO = (PersonVO) anIterator2.next();
							if(personVO.getThePersonDT().getPersonUid()!= null && participationDT
									.getSubjectEntityUid() != null){
								if (personVO.getThePersonDT().getPersonUid()
										.longValue() == participationDT
										.getSubjectEntityUid().longValue()) {
									personVOs.add(personVO);
								}
							} else
								continue;
						}
					} else
						continue;
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.getPersonVO Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return personVOs;
	}

	public static OrganizationVO getOrganizationVO(String type_cd,
			PageProxyVO proxyVO) throws Exception {

		Collection<Object> organizationVOCollection = null;
		OrganizationVO organizationVO = null;
		Collection<Object> participationDTCollection = null;
		ParticipationDT participationDT = null;
		try {
			participationDTCollection = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getTheParticipationDTCollection();
			organizationVOCollection = ((PageActProxyVO) proxyVO)
					.getTheOrganizationVOCollection();
			if (participationDTCollection != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null
							&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
						for (anIterator2 = organizationVOCollection.iterator(); anIterator2
								.hasNext();) {
							organizationVO = (OrganizationVO) anIterator2
									.next();
							if (organizationVO.getTheOrganizationDT()
									.getOrganizationUid().longValue() == participationDT
									.getSubjectEntityUid().longValue()) {
								return organizationVO;
							} else
								continue;
						}
					} else
						continue;
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.getOrganizationVO Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return null;
	}

	public static OrganizationVO getGenericOrganizationVO(
			String type_cd,
			String busObjType,
			PageProxyVO proxyVO) throws Exception {

		Collection<Object> organizationVOCollection = null;
		OrganizationVO organizationVO = null;
		Collection<Object> participationDTCollection = null;
		ParticipationDT participationDT = null;
		try {
			if (busObjType.equalsIgnoreCase(NEDSSConstants.CASE))
				participationDTCollection = ((PageActProxyVO) proxyVO)
						.getPublicHealthCaseVO().getTheParticipationDTCollection();
			else
				participationDTCollection = ((PageActProxyVO) proxyVO)
						.getTheParticipationDTCollection();
			organizationVOCollection = ((PageActProxyVO) proxyVO)
					.getTheOrganizationVOCollection();
			if (participationDTCollection != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null
							&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
						for (anIterator2 = organizationVOCollection.iterator(); anIterator2
								.hasNext();) {
							organizationVO = (OrganizationVO) anIterator2
									.next();
							if (organizationVO.getTheOrganizationDT()
									.getOrganizationUid().longValue() == participationDT
									.getSubjectEntityUid().longValue()) {
								return organizationVO;
							} else
								continue;
						}
					} else
						continue;
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.getGenericOrganizationVO Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return null;
	}


	private void setUpdatedValues(PageForm form, HttpServletRequest req) throws Exception {
		try {
			Map<Object, Object> answerMap = form.getPageClientVO().getAnswerMap();
			if (answerMap != null && answerMap.size() > 0) {
				String stateCd = (String) answerMap.get(PageConstants.STATE);
				form.getOnLoadCityList(stateCd, req);
				form.getDwrCountiesForState(stateCd);
				String importedStateCd = (String) answerMap
						.get(PageConstants.IMPORTED_STATE);
				form.getOnLoadCityList(importedStateCd, req);
				form.getDwrImportedCountiesForState(importedStateCd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setUpdatedValues Exception thrown:" + e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	public void setUpdatedValues(BaseForm form, HttpServletRequest req,
			ClientVO clientVO) throws NEDSSAppException {
		try {
			Map<Object, Object> answerMap = clientVO.getAnswerMap();
			if (answerMap != null && answerMap.size() > 0) {
				String stateCd = (String) answerMap.get(PageConstants.STATE);
				form.getOnLoadCityList(stateCd, req);
				form.getDwrCountiesForState(stateCd);
				String importedStateCd = (String) answerMap
						.get(PageConstants.IMPORTED_STATE);
				form.getOnLoadCityList(importedStateCd, req);
				form.getDwrImportedCountiesForState(importedStateCd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setUpdatedValues Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private static void populateContactTracing(PageForm form,
			HttpServletRequest req) throws NEDSSAppException {
		try {
			// Contract Tracking Contract Records
			CTContactSummaryDT contactRecords = new CTContactSummaryDT();
			ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
			ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();
			// contactNamedByPatList.add(contactRecords);
			// patNamedByContactsList.add(contactRecords);
			req.setAttribute("contactNamedByPatList", contactNamedByPatList);
			req.setAttribute("patNamedByContactsList", patNamedByContactsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.populateContactTracing Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	protected void setCommonSecurityForCreateEditLoad(PageForm form,
			NBSSecurityObj nbsSecurityObj, HttpServletRequest req,
			PageProxyVO proxyVO) throws Exception {
		String conditionCd = "";
		try {
			if (proxyVO == null)
				conditionCd = (String) NBSContext.retrieve(req.getSession(),
						NBSConstantUtil.DSInvestigationCondition);
			else
				conditionCd = ((PageActProxyVO) proxyVO)
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getCd();
			boolean viewContactTracing = nbsSecurityObj.getPermission(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);

			boolean addContactTracing = nbsSecurityObj.getPermission(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.ADD);

			boolean HIVQuestionPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);
			if (HIVQuestionPermission)
				form.getSecurityMap().put("hasHIVPermissions",
						NEDSSConstants.TRUE);
			String ContactTracingByConditionCd = this.getConditionTracingEnableInd(conditionCd);
			form.getSecurityMap().put("ContactTracingEnableInd",
					ContactTracingByConditionCd);
			form.getSecurityMap().put("checkToViewContactTracing",
					String.valueOf(viewContactTracing));

			String SupplementalInfoByConditionCd="Y";
			String viewSupplementalInfo="true";
			form.getSecurityMap().put("SupplementalInfoEnableInd",
					SupplementalInfoByConditionCd);
			form.getSecurityMap().put("checkToViewSupplementalInfo",
					viewSupplementalInfo);

			req.setAttribute("checkToAddContactTracing", new Boolean(
					addContactTracing).toString());
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setCommonSecurityForCreateEditLoad Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	protected void setCommonSecurityForView(PageForm form,
			PageProxyVO proxyVO, NBSSecurityObj nbsSecurityObj,
			HttpServletRequest req) throws Exception {

		try {
			String programAreaCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getProgAreaCd();
			String jurisdictionCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getJurisdictionCd();
			String sharedInd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getSharedInd();
			String conditionCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

			// created required checks for security in the front end
			boolean checkViewFilePermission = nbsSecurityObj.getPermission(
					NBSBOLookup.PATIENT, NBSOperationLookup.VIEWWORKUP);
			boolean checkViewEditInvPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT,
					programAreaCd, jurisdictionCd, sharedInd);

			boolean checkViewTransferPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.TRANSFERPERMISSIONS, programAreaCd,
					jurisdictionCd, sharedInd);

			boolean checkViewObsLabPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW);


			boolean checkViewObsMorbPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					NBSOperationLookup.VIEW);

			boolean assocLabPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean assocDocPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ASSOCIATEDOCUMENTS,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);
			boolean assocMorbPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);
			boolean checkViewEditObsLabPermission = nbsSecurityObj
					.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.EDIT);

			boolean bManageTreatment = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ASSOCIATETREATMENTS,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean checkViewTreatmentPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);

			boolean checkCreateNotific = nbsSecurityObj.getPermission(
					NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean checkCreateNeedsApprovalNotific = nbsSecurityObj
					.getPermission(NBSBOLookup.NOTIFICATION,
							NBSOperationLookup.CREATENEEDSAPPROVAL);

			boolean checkCaseReporting = nbsSecurityObj.getPermission(
					NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATE,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean checkCaseReportingNeedsApprovalNotific = nbsSecurityObj
					.getPermission(NBSBOLookup.CASEREPORTING,
							NBSOperationLookup.CREATENEEDSAPPROVAL);

			boolean deleteInvestigation = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION, NBSOperationLookup.DELETE,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean manageVaccination = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS,
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getProgAreaCd(),
					((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getJurisdictionCd(),
					sharedInd);

			boolean checkViewVaccinationPermission = nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);

			boolean viewDocInvestigation = nbsSecurityObj.getPermission(
					NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW);

			boolean viewContactTracing = nbsSecurityObj.getPermission(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);

			boolean addContactTracing = nbsSecurityObj.getPermission(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.ADD);
			boolean manageAssoPerm = nbsSecurityObj.getPermission(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.MANAGE);

			boolean changeCondition = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.CHANGECONDITION);

			boolean mergeInvestigation = nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.MERGEINVESTIGATION);


			boolean isSTDProgramArea = PropertyUtil
					.isStdOrHivProgramArea(programAreaCd);
			//if (isSTDProgramArea) { //per Jit 2017-01-05 now that DC Export is ready, allow Share for STD
				// //checkViewTransferPermission=false;
			//	checkCaseReporting = false;
			//}

			boolean HIVQuestionPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);
			if (HIVQuestionPermission)
				form.getSecurityMap().put("hasHIVPermissions",
						NEDSSConstants.TRUE);

			String viewContactTracingByConditionCd = this.getConditionTracingEnableInd(conditionCd);
			// Interview Investigation Permissions
			boolean viewInterview = nbsSecurityObj.getPermission(
					NBSBOLookup.INTERVIEW, NBSOperationLookup.VIEW);
			if (viewInterview)
				form.getSecurityMap().put("viewInterviewPermission",
						String.valueOf(viewInterview));
			boolean addInterview = nbsSecurityObj.getPermission(
					NBSBOLookup.INTERVIEW, NBSOperationLookup.ADD);
			if(req.getParameter("mode")!=null && req.getParameter("mode").equalsIgnoreCase("print"))
                addInterview=false;
				form.getSecurityMap().put("addInterviewPermission",
						String.valueOf(addInterview));

			form.getSecurityMap().put("deleteInvestigation",
					String.valueOf(deleteInvestigation));
			form.getSecurityMap().put("checkFile",
					String.valueOf(checkViewFilePermission));
			boolean showTransferOwnerShipButton = showTransferOwnerShipButton(
					proxyVO, nbsSecurityObj);
			form.getSecurityMap().put(
					"checkTransfer",
					String.valueOf((checkViewTransferPermission)
							&& (showTransferOwnerShipButton)));
			boolean showNotificationCreateButton = showCreateNotificationButton(
					proxyVO, nbsSecurityObj);
			form.getSecurityMap()
					.put("checkManageNotific",
							String.valueOf((checkCreateNotific || checkCreateNeedsApprovalNotific)
									&& (showNotificationCreateButton)));
			// boolean showcaseRepCreateButton =
			// showCaseReportingButton(proxyVO, nbsSecurityObj);
			form.getSecurityMap()
					.put("checkCaseReporting",
							String.valueOf((checkCaseReporting || checkCaseReportingNeedsApprovalNotific)));

			form.getSecurityMap().put(
					"checkManageEvents",
					String.valueOf(assocLabPermission || assocMorbPermission
							|| manageVaccination || bManageTreatment || assocDocPermission));

			form.getSecurityMap().put("editInv",
					String.valueOf(checkViewEditInvPermission));

			// to check for Observation Display box to be displayed or not
			form.getSecurityMap().put("ObsDisplay",
					String.valueOf(checkViewObsLabPermission));
			// to check for Observation Display box to be displayed or not
			form.getSecurityMap().put("MorbDisplay",
					String.valueOf(checkViewObsMorbPermission));
			form.getSecurityMap().put("TreatmentDisplay",
					String.valueOf(checkViewTreatmentPermission));
			form.getSecurityMap().put("checkManageVacc",
					String.valueOf(manageVaccination));
			form.getSecurityMap().put("VaccinationDisplay",
					String.valueOf(checkViewVaccinationPermission));
			form.getSecurityMap().put("checkToViewDocument",
					String.valueOf(viewDocInvestigation));
			form.getSecurityMap().put("checkToViewContactTracing",
					String.valueOf(viewContactTracing));
			form.getSecurityMap().put("ContactTracingEnableInd",
					viewContactTracingByConditionCd);

			String SupplementalInfoByConditionCd="Y";
			String viewSupplementalInfo="true";
			form.getSecurityMap().put("SupplementalInfoEnableInd",
					SupplementalInfoByConditionCd);
			form.getSecurityMap().put("checkToViewSupplementalInfo",
					viewSupplementalInfo);

			form.getSecurityMap().put("printCDCForm", "NOT_DISPLAYED");
			form.getSecurityMap().put("shareButton", "DISPLAYED");
			boolean printSTDCDCForms = false;
			String printCDCFormType = "CongenitalSyphilis";
			if (isSTDProgramArea && !conditionCd.equals(CTConstants.CongenitalSyphilisConditionCode))
				printSTDCDCForms = true;
			
			
			if(conditionCd!=null && conditionCd.equals("102201")){
				printSTDCDCForms = true;
				printCDCFormType="TB";
				form.setFormName("RVCT");
			}
			
			if(conditionCd!=null &&  conditionCd.equals("502582")){
				printSTDCDCForms = true;
				printCDCFormType="LTBI";
				form.setFormName("TBLISS");//It will pobably be a different one
			}
			
			
			
			form.getSecurityMap().put("printCDCFRForm",
					String.valueOf(printSTDCDCForms));
			
			form.getSecurityMap().put("printCDCFRFormType",
					String.valueOf(printCDCFormType));
			
			
			req.setAttribute("checkToAddContactTracing", new Boolean(
					addContactTracing).toString());
			req.setAttribute("manageAssoPerm",
					new Boolean(manageAssoPerm).toString());
			// don't allow Change Condition unless there are other conditions in
			// the family
			if (changeCondition == false)
				form.getSecurityMap().put("checkChangeCondition",
						String.valueOf(changeCondition));
			else {
				ArrayList<Object> condFamily = form.getConditionFamilyList(
						conditionCd, nbsSecurityObj, req);
				if (condFamily.size() > 1) {
					form.getSecurityMap().put("checkChangeCondition",
							String.valueOf(changeCondition));
					form.setConditionFamilyList(condFamily);
				} else
					form.getSecurityMap().put("checkChangeCondition", "false");
			}


		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setCommonSecurityForView Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}
	/**
	 * setProcessingDecisionAndReferralBasisEICR: this method was created as part of the defect fix for ND-17832.
	 * It sets the value of processing decision to FF if it is the one selected, otherwise, it will be NF.
	 * Also, it sets the value of Referral Basis = T2 (this is provisional, in future we should be able to select the Referral Basis from the UI).
	 * @param form
	 * @param req
	 */
	private void setProcessingDecisionAndReferralBasisEICR(PageForm form, HttpServletRequest req){
		try{
			String processingDecision2 =   (String)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSProcessingDecision);
			if (processingDecision2 != null) {
			
				form.getAttributeMap()
						.put(NEDSSConstants.PROCESSING_DECISION,
								processingDecision2);
				getDefaultInvestigatorDetailsIfPresent(req, form);
				
				// Coming from Morb Report - set the Referral Basis to T2
				// for Morb
				
				form.getPageClientVO().getAnswerMap().put(NEDSSConstants.REFERRAL_BASIS, NEDSSConstants.REFERRAL_BASIS_MORB);
				
				//If finds referrerBasis from context then set it again
				String referrerBasis = (String) NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSReferralBasis);
				
				form.getPageClientVO().getAnswerMap().put(NEDSSConstants.REFERRAL_BASIS, referrerBasis);
				
				if (!NEDSSConstants.REFERRAL_BASIS_MORB.equals(referrerBasis)) { // If Referral Basis != T2 then set NBS270 = NBS110 (referral basis)
					form.getPageClientVO().getAnswerMap().put(NEDSSConstants.REFERRAL_BASIS_OOJ,referrerBasis);
				} else { // If Referral Basis == T2 then set NBS270 = null
					form.getPageClientVO().getAnswerMap().put(NEDSSConstants.REFERRAL_BASIS_OOJ, null);
				}
			}
		}catch(Exception e){
			//This exception is thrown when it is a case report but not a eICR (we didn't store processing decision in the nbs context). The Case Report should have already a processing decision value.
			logger.fatal("PageLoadUtil.setProcessingDecisionAndReferralBasisEICR Exception thrown:" + e.getMessage(),e);
		
		}
	}
	public static boolean showCreateNotificationButton(PageProxyVO proxy,
			NBSSecurityObj securityObj) throws NEDSSAppException {
		try {
			if (((PageActProxyVO) proxy).getTheNotificationSummaryVOCollection() != null) {
				//If case is HIV/AIDS do not show the Create Notification button
				String progAreaCd=proxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
				if(PropertyUtil.getInstance().isHIVProgramArea(progAreaCd)){
					return false;
				}
				//no notification for Syphilis unknown - cleanup per Jennifer Nov 2017
				String conditionCd=proxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
				if( conditionCd != null && CTConstants.SyphilisUnknownConditionCode.equals(conditionCd))
	            {
	                return false;
	            }
//				String s = CachedDropDowns.getCodeDescTxtForCd(cd, "STD_SYPHILIS_CODE_LIST");
//		    	if( !StringUtils.isEmpty(s) && "10316".equals(cd))
//			    {
//			    	Collection csReports = ((PageActProxyVO)proxy).getTheCSSummaryVOCollection();
//					if (csReports.size() == 0)
//					{
//			        return false;
//					}
//			    }
//			    Collection persons = ((PageActProxyVO)proxy).getThePersonVOCollection();
//			    Iterator personsIter = persons.iterator();
//				while (personsIter.hasNext()) {
//					PersonVO personVO=(PersonVO) personsIter.next();
//					String personCode = personVO.getThePersonDT().getCd();
//	    		if(personCode!=null && personCode.equals("PAT")) {
//						String age = personVO.getThePersonDT().getAgeReported();
//						if (StringUtils.isEmpty(age))
//						{
//							return true;
//						}
//						else if( "700".equals(cd) && Integer.parseInt(age) < 10 )
//			            {
//			                return false;
//			            }
//					}
//				}
				for (Iterator<Object> anIterator = ((PageActProxyVO) proxy)
						.getTheNotificationSummaryVOCollection().iterator(); anIterator
						.hasNext();) {
					NotificationSummaryVO notSumVO = (NotificationSummaryVO) anIterator
							.next();
					if (notSumVO.getAutoResendInd() != null
							&& ((notSumVO.getCdNotif().equals(
									NEDSSConstants.CLASS_CD_NOTF) && notSumVO
									.getAutoResendInd().equalsIgnoreCase("T")))) {
						return false;
					}
				}
			}
			return true;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.showCreateNotificationButton Exception thrown:" + e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	protected static void setCommonGenericSecurityForView(PageForm form,
			NBSSecurityObj nbsSecurityObj, HttpServletRequest req)
			throws Exception {
		try {
				String businessObjLookupName = NBSBOLookup.INTERVIEW;

				if(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType())){
					businessObjLookupName = NBSBOLookup.INTERVIEW;
				}else if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType())){
					businessObjLookupName = NBSBOLookup.INTERVENTIONVACCINERECORD;
				}

				boolean HIVQuestionPermission = nbsSecurityObj.getPermission(
						NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);
				if (HIVQuestionPermission)
					form.getSecurityMap().put("hasHIVPermissions",NEDSSConstants.TRUE);

				boolean deleteInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.DELETE);
				if (deleteInterview)
					form.getSecurityMap().put("deleteGenericPermission",String.valueOf(deleteInterview));

				boolean editInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.EDIT);
				if (editInterview)
					form.getSecurityMap().put("editGenericPermission",String.valueOf(editInterview));

				// Vaccination electronic Indicator - if its Y then do not show edit button on vaccination page.
				if(NBSBOLookup.INTERVENTIONVACCINERECORD.equals(businessObjLookupName) && NEDSSConstants.ELECTRONIC_IND_VACCINATION.equals(form.getAttributeMap().get(PageConstants.ELECTRONIC_IND))){
					form.getSecurityMap().put("editGenericPermission",String.valueOf(false));
				}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setCommonGenericSecurityForView Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	protected void setCommonGenericSecurityForCreateEdit(PageForm form,
			NBSSecurityObj nbsSecurityObj, HttpServletRequest req)
			throws Exception {
		try {
			String businessObjLookupName = NBSBOLookup.INTERVIEW;

			if(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType())){
				businessObjLookupName = NBSBOLookup.INTERVIEW;
			}else if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType())){
				businessObjLookupName = NBSBOLookup.INTERVENTIONVACCINERECORD;
			}

			boolean HIVQuestionPermission = nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL, NBSOperationLookup.HIVQUESTIONS);

			if (HIVQuestionPermission)
				form.getSecurityMap().put("hasHIVPermissions",NEDSSConstants.TRUE);
			boolean deleteInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.DELETE);
			if (deleteInterview)
				form.getSecurityMap().put("deleteGenericPermission",String.valueOf(deleteInterview));

			boolean editInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.EDIT);
			if (editInterview)
				form.getSecurityMap().put("editGenericPermission",String.valueOf(editInterview));
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setCommonGenericSecurityForView Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public static boolean showTransferOwnerShipButton(PageProxyVO proxy,
			NBSSecurityObj securityObj) {
		if (((PageActProxyVO) proxy).isOOSystemPendInd() == true)
			return false;
		else
			return true;
	}

	// TODO: replace with generic function
	public void setAnswerArrayMapAnswers(PageForm form,
			ArrayList<Object> multiSelects, Map<Object, Object> answerMap) throws NEDSSAppException {
		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();

			for (int k = 0; k < multiSelects.size(); k++) {
				Object obj = answerMap.get(multiSelects.get(k));

				if (obj != null && obj instanceof ArrayList<?>) {
					ArrayList<Object> multiList = (ArrayList<Object>) obj;
					String[] answerList = new String[multiList.size()];
					for (int i = 0; i < multiList.size(); i++) {
						NbsAnswerDT answerDT = (NbsAnswerDT) multiList.get(i);
						String answer = answerDT.getAnswerTxt();
						if (answer != null && answer.trim().indexOf("OTH^") == 0) { // put
																					// the
																					// other
																					// answer
																					// from
																					// answerArrayMap
																					// to
																					// answerMap
							answerList[i] = answer.substring(0, answer.trim()
									.indexOf("^"));
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								String otherAnswer = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								form.getPageClientVO()
										.getAnswerMap()
										.put(multiSelects.get(k) + "Oth",
												otherAnswer);
							}
						} else
							answerList[i] = answer;
					}
					returnMap.put(multiSelects.get(k), answerList);
					form.getPageClientVO().getArrayAnswerMap().putAll(returnMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setAnswerArrayMapAnswers Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void setAnswerArrayMapAnswers(ArrayList<Object> multiSelects,
			Map<Object, Object> answerMap, ClientVO clientVO) throws NEDSSAppException {
		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();

			for (int k = 0; k < multiSelects.size(); k++) {
				Object obj = answerMap.get(multiSelects.get(k));

				if (obj != null && obj instanceof ArrayList<?>) {
					ArrayList<Object> multiList = (ArrayList<Object>) obj;
					String[] answerList = new String[multiList.size()];
					for (int i = 0; i < multiList.size(); i++) {
						NbsAnswerDT answerDT = (NbsAnswerDT) multiList.get(i);
						String answer = answerDT.getAnswerTxt();
						if (answer != null && answer.trim().indexOf("OTH^") == 0) { // put
																					// the
																					// other
																					// answer
																					// from
																					// answerArrayMap
																					// to
																					// answerMap
							answerList[i] = answer.substring(0, answer.trim()
									.indexOf("^"));
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								String otherAnswer = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								clientVO.getAnswerMap().put(
										multiSelects.get(k) + "Oth", otherAnswer);
							}
						} else
							answerList[i] = answer;
					}
					returnMap.put(multiSelects.get(k), answerList);
					clientVO.getArrayAnswerMap().putAll(returnMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setAnswerArrayMapAnswers Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void setAnswerArrayMapAnswersForCoinfection(
			ArrayList<Object> multiSelects, Map<Object, Object> answerMap,
			ClientVO clientVO) throws NEDSSAppException {
		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();

			for (int k = 0; k < multiSelects.size(); k++) {
				Object obj = answerMap.get(multiSelects.get(k));

				if (obj != null && obj instanceof ArrayList<?>
						&& coinfectionQuestionMap != null
						&& coinfectionQuestionMap.containsKey(multiSelects.get(k))) {
					ArrayList<Object> multiList = (ArrayList<Object>) obj;
					String[] answerList = new String[multiList.size()];
					for (int i = 0; i < multiList.size(); i++) {
						NbsAnswerDT answerDT = (NbsAnswerDT) multiList.get(i);
						String answer = answerDT.getAnswerTxt();
						if (answer != null && answer.trim().indexOf("OTH^") == 0) { // put
																					// the
																					// other
																					// answer
																					// from
																					// answerArrayMap
																					// to
																					// answerMap
							answerList[i] = answer.substring(0, answer.trim()
									.indexOf("^"));
							if (answer != null
									&& (answer.trim().length() > answer.trim()
											.indexOf("OTH^") + 4)) {
								String otherAnswer = answer
										.substring(answer.indexOf("OTH^") + 4,
												answer.length());
								clientVO.getAnswerMap().put(
										multiSelects.get(k) + "Oth", otherAnswer);
							}
						} else
							answerList[i] = answer;
					}
					returnMap.put(multiSelects.get(k), answerList);
					clientVO.getArrayAnswerMap().putAll(returnMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setAnswerArrayMapAnswersForCoinfection Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	// TODO: Replace with generic function without AnswerDT
	public void setCheckBoxAnswersWithCodeSet(
			Map<Object, Object> answerMap, ArrayList<Object> checkboxes,
			Map<Object, Object> returnMap) throws NEDSSAppException {
		try {
			CachedDropDownValues cdv = new CachedDropDownValues();
			Map<?, ?> map = cdv.getCodedValuesAsTreeMap("TF_PAM");
			for (int i = 0; i < checkboxes.size(); i++) {
				Object obj = answerMap.get(checkboxes.get(i));
				if (obj instanceof NbsAnswerDT) {
					NbsAnswerDT dt = (NbsAnswerDT) obj;
					String answer = dt.getAnswerTxt();
					if (answer != null
							&& answer.equalsIgnoreCase((String) map.get("True")))
						returnMap.put(checkboxes.get(i), "1");
					else
						returnMap.put(checkboxes.get(i), "0");
				}
			}
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setCheckBoxAnswersWithCodeSet Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void setCheckBoxAnswersWithCodeSetGeneric(
			Map<Object, Object> answerMap, ArrayList<Object> checkboxes,
			Map<Object, Object> returnMap) throws NEDSSAppException {
		try {
			CachedDropDownValues cdv = new CachedDropDownValues();
			Map<?, ?> map = cdv.getCodedValuesAsTreeMap("TF_PAM");
			for (int i = 0; i < checkboxes.size(); i++) {
				Object obj = answerMap.get(checkboxes.get(i));
				if (obj instanceof NbsAnswerDT) {
					NbsAnswerDT dt = (NbsAnswerDT) obj;
					String answer = dt.getAnswerTxt();
					if (answer != null
							&& answer.equalsIgnoreCase((String) map.get("True")))
						returnMap.put(checkboxes.get(i), "1");
					else
						returnMap.put(checkboxes.get(i), "0");
				}
			}
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.setCheckBoxAnswersWithCodeSetGeneric Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void setJurisdictionForCreate(PageForm form,
			NBSSecurityObj nbsSecurityObj, HttpSession session)
			throws Exception {
		try {
			String programAreaCd = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationProgramArea);
			String conditionCd = (String) NBSContext.retrieve(session,
					NBSConstantUtil.DSInvestigationCondition);

			this.setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
			//INV169 can be a read-only hidden field for use with rules.
			form.getPageClientVO().setAnswer(PageConstants.CONDITION_CD, conditionCd);
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setJurisdictionForCreate Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private void setJurisdictionForEdit(PageForm form,
			NBSSecurityObj nbsSecurityObj, PageProxyVO proxyVO)
			throws Exception {
		try {
			String programAreaCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getProgAreaCd();
			String conditionCd = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

			this.setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setJurisdictionForEdit Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	// TODO: Replace with generic function in PageManagementCommon where called
	private void setJurisdiction(PageForm form, String programAreaCd,
			String conditionCd, NBSSecurityObj nbsSecurityObj) throws Exception {
		try {
			CachedDropDownValues cdv = new CachedDropDownValues();

			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('"
					+ programAreaCd + "')", conditionCd);
			if (programAreaVO == null) // level 2 condition for Hepatitis
										// Diagnosis
				programAreaVO = cdv.getProgramAreaCondition("('"
						+ programAreaCd + "')", 2, conditionCd);

			String programAreaJurisdictions = nbsSecurityObj
					.getProgramAreaJurisdictions(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.VIEW);
			StringBuffer sb = new StringBuffer();

			if (programAreaJurisdictions != null
					&& programAreaJurisdictions.length() > 0) {

				StringTokenizer st = new StringTokenizer(
						programAreaJurisdictions, "|");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					if (token.lastIndexOf("$") >= 0) {

						String programArea = token.substring(0,
								token.lastIndexOf("$"));
						if (programArea != null
								&& programArea.equals(programAreaVO
										.getStateProgAreaCode())) {
							String juris = token.substring(token
									.lastIndexOf("$") + 1);
							sb.append(juris).append("|");
						}
					}
				}
				form.getAttributeMap().put("NBSSecurityJurisdictions",
						sb.toString());
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setJurisdiction Exception thrown:" + e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	/**
	 * setRVCTAnswersForViewEdit retrieves RVCT Answers from NBS_Answers and
	 * puts in the form.
	 *
	 * @param form
	 * @param answerMap
	 * @throws Exception
	 */
	// TODO: replace with generic version below
	public void setMSelectCBoxAnswersForViewEdit(PageForm form,
			Map<Object, Object> answerMap) throws NEDSSAppException {
		
		try {
			logger.debug("********#Current answerMap size :-" + answerMap.size());
			
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			// Multiselect answers
			ArrayList<Object> multiSelects = this.retrieveMultiSelectQIds(form);
			this.setAnswerArrayMapAnswers(form, multiSelects, answerMap);
			// Load all PAM Specific answers to form
			setPamSpecificAnswersForViewEdit(form, answerMap, returnMap);
			ArrayList<Object> checkboxes = retrieveCheckboxQIds(form);
			this.setCheckBoxAnswersWithCodeSet(answerMap, checkboxes, returnMap);
			form.getPageClientVO().getAnswerMap().putAll(returnMap);
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setMSelectCBoxAnswersForViewEdit Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void setMSelectCBoxAnswersForViewEdit(BaseForm form,
			Map<Object, Object> answerMap, ClientVO clientVO)
			throws NEDSSAppException {
		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			// Multiselect answers
			ArrayList<Object> multiSelects = this.retrieveMultiSelectQIds(form);
			this.setAnswerArrayMapAnswers(multiSelects, answerMap, clientVO);
			// Load all PAM Specific answers to form
			setPamSpecificAnswersForViewEdit(answerMap, returnMap, clientVO);
			ArrayList<Object> checkboxes = this.retrieveCheckboxQIds(form);
			this.setCheckBoxAnswersWithCodeSetGeneric(answerMap, checkboxes,
					returnMap);
			clientVO.getAnswerMap().putAll(returnMap);
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setMSelectCBoxAnswersForViewEdit Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	// TODO: replace with Generic function
	public ArrayList<Object> retrieveMultiSelectQIds(PageForm form,PageLoadUtil pageLoadUtil)
			throws Exception {
		try {
			ArrayList<Object> returnList = new ArrayList<Object>();
			returnList.addAll(pageLoadUtil.retrieveQIdentifiersByType(1013, form, pageLoadUtil));
			returnList.addAll(pageLoadUtil.retrieveQIdentifiersByType(1025, form, pageLoadUtil));
			return returnList;
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveMultiSelectQIds Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	public ArrayList<Object> retrieveMultiSelectQIds(BaseForm form)
			throws Exception {
		try {
			ArrayList<Object> returnList = new ArrayList<Object>();
			returnList.addAll(this.retrieveQIdentifiersByType(1013, form));
			returnList.addAll(this.retrieveQIdentifiersByType(1025, form));
			return returnList;
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveMultiSelectQIds Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	// TODO: replace with Generic function
	public  ArrayList<Object> retrieveCheckboxQIds(PageForm form )
			throws Exception {
		try {
			return (this.retrieveQIdentifiersByType(1001, form));
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveCheckboxQIds Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	public ArrayList<Object> retrieveCheckboxQIds(BaseForm form)
			throws Exception {
		try {
			return (this.retrieveQIdentifiersByType(1001, form));
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveCheckboxQIds Exception thrown:"
					+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	// TODO: Replace with Generic version
	private ArrayList<Object> retrieveQIdentifiersByType(
			int nbsComponentUid, PageForm form,PageLoadUtil pageLoadUtil) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			if (pageLoadUtil.getQuestionMap() == null)
				return list;
			StringBuffer js = new StringBuffer("");
			Iterator<Object> iter = pageLoadUtil.getQuestionMap().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) pageLoadUtil.getQuestionMap()
						.get(key);
				String qId = metaData.getQuestionIdentifier() == null ? ""
						: metaData.getQuestionIdentifier();
				Long compUid = metaData.getNbsUiComponentUid() == null ? new Long(
						0) : metaData.getNbsUiComponentUid();
				if (compUid.intValue() == nbsComponentUid) {
					if (!qId.startsWith("DEM"))
						list.add(qId);
					js.append(qId).append("|");
				}
			}
			if (nbsComponentUid == 1013) {
				String jsSt = js.toString();
				if (jsSt.length() > 0) {
					jsSt = jsSt.substring(0, (jsSt.length() - 1));
					form.getAttributeMap().put("selectEltIdsArray", jsSt);
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveQIdentifiersByType Exception thrown:"
					+ e.getMessage(),e);
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return list;
	}

	private ArrayList<Object> retrieveQIdentifiersByType(
			int nbsComponentUid, BaseForm form) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			if (this.getQuestionMap() == null)
				return list;
			StringBuffer js = new StringBuffer("");
			Iterator<Object> iter = this.getQuestionMap().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) this.getQuestionMap()
						.get(key);
				String qId = metaData.getQuestionIdentifier() == null ? ""
						: metaData.getQuestionIdentifier();
				Long compUid = metaData.getNbsUiComponentUid() == null ? new Long(
						0) : metaData.getNbsUiComponentUid();
				if (compUid.intValue() == nbsComponentUid) {
					if (!qId.startsWith("DEM"))
						list.add(qId);
					js.append(qId).append("|");
				}
			}
			if (nbsComponentUid == 1013) {
				String jsSt = js.toString();
				if (jsSt.length() > 0) {
					jsSt = jsSt.substring(0, (jsSt.length() - 1));
					form.getAttributeMap().put("selectEltIdsArray", jsSt);
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveQIdentifiersByType Exception thrown:"
					+ e.getMessage(),e);
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return list;
	}

	/**
	 * _confirmationMethodsForViewEdit extracts Confirmation Method
	 * Collection<Object> from PHCase and sets it to form
	 *
	 * @param form
	 * @param proxyVO
	 * @throws Exception
	 */
	private void _confirmationMethodsForViewEdit(PageForm form,
			PageProxyVO proxyVO) throws NEDSSAppException {
		try {
			ArrayList<Object> multiList = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO()
					.getTheConfirmationMethodDTCollection() == null ? new ArrayList<Object>()
					: (ArrayList<Object>) ((PageActProxyVO) proxyVO)
							.getPublicHealthCaseVO()
							.getTheConfirmationMethodDTCollection();
			if (multiList.size() > 0) {
				String[] answerList = new String[multiList.size()];
				for (int i = 0; i < multiList.size(); i++) {
					ConfirmationMethodDT cm = (ConfirmationMethodDT) multiList
							.get(i);
					String answer = cm.getConfirmationMethodCd();
					answerList[i] = answer;
					// Confirmation Date
					if (cm.getConfirmationMethodTime() != null)
						form.getPageClientVO()
								.getAnswerMap()
								.put(PageConstants.CONFIRM_DATE,
										StringUtils.formatDate(cm
												.getConfirmationMethodTime()));
				}
				form.getPageClientVO().getArrayAnswerMap()
						.put(PageConstants.CONFIRM_METHOD_CD, answerList);
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.retrieveQIdentifiersByType Exception thrown:"
					+ e.getMessage(),e);
			e.printStackTrace();
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private void setNNDIndicator(PageProxyVO proxyVO, PageForm form)
			throws Exception {
		try {
			form.getAttributeMap().put(PageConstants.NO_REQ_FOR_NOTIF_CHECK,
					"true");
			Collection<Object> actRelationshipDTColl = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO()
					.getTheActRelationshipDTCollection();
			if (actRelationshipDTColl != null
					&& actRelationshipDTColl.size() > 0) {
				Iterator<Object> iter = actRelationshipDTColl.iterator();
				while (iter.hasNext()) {
					ActRelationshipDT dt = (ActRelationshipDT) iter.next();
					if (dt != null && dt.isNNDInd()) {
						form.getAttributeMap().put(
								PageConstants.NO_REQ_FOR_NOTIF_CHECK, "false");
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.setNNDIndicator Exception thrown:" + e.getMessage(),e);
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
	}

	public String getConditionTracingEnableInd(String conditonCd) throws NEDSSAppException {
		try {
			CachedDropDownValues cdv1 = new CachedDropDownValues();
			String enableContactTab = cdv1.getConditionTracingEnableInd(conditonCd);
			return enableContactTab;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getConditionTracingEnableInd Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void printNotes(PageForm pageForm,
			HttpServletRequest request, HttpServletResponse res)
			throws Exception {
		try {
			PageProxyVO proxyVO = null;
			String sPublicHealthCaseUID = (String) request.getSession()
					.getAttribute("DSInvUid");
			try {
				proxyVO = getProxyObject(sPublicHealthCaseUID,
						request.getSession());
			} catch (Exception e) {
				logger.fatal("Error while getting the Page Association"
						+ e.getMessage(), e);
				throw new ServletException();
			}
			request.setAttribute("mode", request.getParameter("mode"));
			ArrayList<Object> noteDTs = (ArrayList<Object>) ((PageActProxyVO) proxyVO)
					.getNbsNoteDTColl();

			if (noteDTs != null && noteDTs.size() > 0) {
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request
						.getSession().getAttribute("NBSSecurityObject");
				FileUploadUtil fileUtil = new FileUploadUtil();
				ArrayList<Object> nNotesDTs = fileUtil
						.updateNotesForPrivateInd(noteDTs, nbsSecurityObj);
				fileUtil.updateNotesForView(nNotesDTs);
				request.setAttribute("nbsNotes", nNotesDTs);
			}

			PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, pageForm
					.getPageClientVO().getOldPageProxyVO());

			setNameCurrentSexDOB(pageForm, request, personVO);

			PublicHealthCaseDT phcDT = (PublicHealthCaseDT) ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT();
			request.setAttribute("createdDate",
					StringUtils.formatDate(phcDT.getAddTime()));
			request.setAttribute("createdBy", phcDT.getAddUserName());
			request.setAttribute("updatedDate",
					StringUtils.formatDate(phcDT.getLastChgTime()));
			request.setAttribute("updatedBy", phcDT.getLastChgUserName());
			setLastChangeUserAndTime(pageForm, request);

		} catch (Exception e) {
			logger.fatal("PageLoadUtil.printNotes Exception thrown:" + e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	/**
	 * Get the SubSecStructure map and the Repeating Batch answers The structure
	 * of the SubSecStructure map (batchMap)is: 0=QuesId, 1=NBS Ques Uid,
	 * 2=Appear in header Y,N, 3=header label 4=%ColWidth 5=component uid,
	 * 6=code set nm The Batch Entry Map (batchMapList) is the set of Batch
	 * Entries with the subsection name, id, ques id and value. Multi Select
	 * example value: 3$$Falciparum||1$$Not Determined$MulOth$1#MulOth#|| Select
	 * example: 45$$South Carolina Other examples: [TRAVEL12=4$sn$W$val$Weeks,
	 * TRAVEL11=324$$Guinea, TRAVEL08=08/20/2012]
	 *
	 * @param form
	 * @throws Exception
	 */
	public void populateBatchRecords(BaseForm form, String invFormCd,
			HttpSession session, Map repeatingAnswerMap) throws Exception {
			String debugNBSQuestionUid = "";
			String debugAnswerTxt = "";
		try {
			Map<Object, ArrayList<BatchEntry>> batchMaplist = new HashMap<Object, ArrayList<BatchEntry>>();
			if (form.getBatchEntryMap() == null)
				form.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());

			Map<Object, Object> batchMap = findBatchRecords(invFormCd, session);
			String countryCodeValue = "";
			String stateCodeValue = "";
			String SubSectionNm = "";
			// if(repeatingAnswerMap != null && repeatingAnswerMap.size() > 0) {
			// Iterator<Object> iter = repeatingAnswerMap.entrySet().iterator();
			// while(iter.hasNext()) {
			// Map.Entry<Object,Object> pairs =
			// (Map.Entry<Object,Object>)iter.next();

			if (batchMap != null && batchMap.size() > 0) {
				Iterator<Entry<Object, Object>> ite = batchMap.entrySet()
						.iterator();
				while (ite.hasNext()) {
					// walk through each subsection that is in the batchmap
					// structure map
					ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
					Map.Entry<Object, Object> pairs1 = (Map.Entry<Object, Object>) ite
							.next();
					SubSectionNm = pairs1.getKey().toString();
					// these batched are handled somewhere else.
					if(SubSectionNm!=null && (SubSectionNm.equals(PageConstants.ENTITY_ID_SUBSECTION) || SubSectionNm.equals(PageConstants.RESULTED_TEST_BATCH_CONTAINER))){
							  continue;
					  }
					String batch[][] = (String[][]) pairs1.getValue();
					// get the structure map for the questions in the subsection
					int numBatchRecordsinSubSec = 0;
					try {
						if (repeatingAnswerMap != null
								&& repeatingAnswerMap.size() > 0) {
							// walk through and see if there are any answers for
							// the questions in this subsection
							for (int num = 0; num < repeatingAnswerMap.size(); num++) {
								for (int batchsize = 0; batchsize < batch.length; batchsize++) {
									ArrayList<NbsAnswerDT> repeatColl = new ArrayList<NbsAnswerDT>();

									if (batch[batchsize][1] != null) {
										// if the nbs_question_uid is present,
										// there could be an answer
										// get the answer if it is present based
										// on the nbs_question_uid
										// multiselects store a DT for each
										// selected value hence the arraylist
										// Find out if we have any answers out
										// there
										repeatColl = (ArrayList<NbsAnswerDT>) repeatingAnswerMap
												.get(new Long(
														batch[batchsize][1]));
										if (repeatColl != null
												&& repeatColl.size() > 0) {
											for (int c1 = 0; c1 < repeatColl
													.size(); c1++) {
												NbsAnswerDT dt = repeatColl
														.get(c1);
												if (numBatchRecordsinSubSec < dt
														.getAnswerGroupSeqNbr())
													numBatchRecordsinSubSec = dt
															.getAnswerGroupSeqNbr();
											}
										}
									}

								}
							}
							// look again to see if we have any multi-select
							// present in the answer map
							if (numBatchRecordsinSubSec == 0) {
								for (int num = 0; num < repeatingAnswerMap
										.size(); num++) {
									for (int batchsize = 0; batchsize < batch.length; batchsize++) {
										ArrayList<NbsAnswerDT> repeatColl = new ArrayList<NbsAnswerDT>();
										if (batch[batchsize][1] != null
												&& batch[batchsize][5]
														.equals("1013")) {
											repeatColl = (ArrayList<NbsAnswerDT>) repeatingAnswerMap
													.get(new Long(
															batch[batchsize][1]));
										}
										if (repeatColl != null
												&& repeatColl.size() > 0) {
											for (int c1 = 0; c1 < repeatColl
													.size(); c1++) {
												NbsAnswerDT dt = repeatColl
														.get(c1);
												if (dt.getSeqNbr() != null
														&& 0 == dt.getSeqNbr())
													numBatchRecordsinSubSec = numBatchRecordsinSubSec + 1;

											}
										}
									}
									if (numBatchRecordsinSubSec > 0)
										break;
								}
							}
						}
					} catch (Exception e) {
						if (SubSectionNm != null)
							logger.error("Error: populateBatchRecord got error SubSectionNm = "+SubSectionNm);
						logger.fatal("populateBatchRecords Exception thrown in internal batch: "
								+ e.getMessage(),e);
						throw new Exception(e.getMessage(),e);
					}
					try {
						int countloop = 0;
						// if we have any answers process them into the be
						// answermap
						for (int count = 0; count < numBatchRecordsinSubSec; count++) {
							BatchEntry be = new BatchEntry();
							NbsAnswerDT dt = new NbsAnswerDT();
							ArrayList<NbsAnswerDT> dtList = new ArrayList<NbsAnswerDT>();
							int preSeqNbr = -1;
							Long previousValue=null;
							// walk through the subsection batch
							for (int i = 0; i < batch.length; i++) {
								if (repeatingAnswerMap != null
										&& repeatingAnswerMap.size() > 0) {
									ArrayList<NbsAnswerDT> repeatColl = new ArrayList<NbsAnswerDT>();
									if (batch[i][1] != null) {
										debugNBSQuestionUid = batch[i][1];
										// if nbs_question_uid present, try to
										// get the answer if it is there
										repeatColl = (ArrayList<NbsAnswerDT>) repeatingAnswerMap
												.get(new Long(batch[i][1])); }
									// We have an answer - process it
									if (repeatColl != null
											&& repeatColl.size() > 0) {
										// If it NOT a multiselect
										if (!batch[i][5].equals("1013")) {
											for (int arrSize = 0; arrSize < repeatColl
													.size(); arrSize++) {
												dt = repeatColl.get(arrSize);
												if (dt.getAnswerGroupSeqNbr() == count + 1)
													arrSize = repeatColl.size();
											}
											if (dt.getAnswerGroupSeqNbr() != null
													&& dt.getAnswerGroupSeqNbr() != count + 1)
												dt = new NbsAnswerDT();
										} // not multiselect
										if (batch[i][5].equals("1013")) {
											// if MultiSelect
											for (int arrSize = 0; arrSize < repeatColl
													.size(); arrSize++) {
												dt = repeatColl.get(arrSize);
												if (dt.getAnswerGroupSeqNbr() == count + 1) {
													dtList.add(dt);
												}
											}
										} // is multiselect

										// if it is a single select then from
										// the stored code add the descriptive
										// text to the answer map
										if (batch[i][5] != null
												&& batch[i][5].toString()
														.equalsIgnoreCase(
																"1007")) {
											HashMap<Object, Object> formMap = new HashMap<Object, Object>();
											formMap = (HashMap<Object, Object>) form
													.getFormFieldMap();
											FormField ff = (FormField) formMap
													.get(batch[i][0]);
											ArrayList<Object> srtValues = new ArrayList<Object>();
											if (ff.getLabel() != null
													&& ff.getLabel().contains(
															"Country"))
												countryCodeValue = dt
														.getAnswerTxt();
											else if (ff.getLabel() != null
													&& ff.getLabel().contains(
															"State"))
												stateCodeValue = dt
														.getAnswerTxt();
											srtValues = (ArrayList<Object>) form
													.getCodedValue(ff
															.getCodeSetNm());
											if (ff.getCodeSetNm() != null
													&& ff.getCodeSetNm()
															.equals("COUNTY_CCD"))
												srtValues = (ArrayList<Object>) form
														.getDwrCountiesForState(stateCodeValue);

											DropDownCodeDT ddDT = new DropDownCodeDT();
											for (int i1 = 0; i1 < srtValues
													.size(); i1++) {
												ddDT = (DropDownCodeDT) srtValues
														.get(i1);
												String str = dt.getAnswerTxt();
												if (str != null)
													debugAnswerTxt = str;
												if (str != null
														&& str.indexOf("^") != -1) {
													if (ddDT.getKey()
															.equalsIgnoreCase(
																	"OTH")
															&& ddDT.getKey()
																	.equals(str
																			.substring(
																					0,
																					str.indexOf("^")))) {
														be.getAnswerMap()
																.put(batch[i][0],
																		ddDT.getKey()
																				+ "$$"
																				+ ddDT.getValue()
																				+ ":"
																				+ str.substring(str
																						.indexOf("^") + 1));
														break;
													} else if (ddDT
															.getKey()
															.equals(str.substring(
																	str.indexOf("^"),
																	str.length()))) {
														be.getAnswerMap()
																.put(batch[i][0],
																		str.substring(
																				0,
																				str.indexOf("^"))
																				+ "$sn$"
																				+ ddDT.getKey()
																				+ "$val$"
																				+ ddDT.getValue());
														break;
													}
												} else {
													if (ddDT.getKey().equals(
															dt.getAnswerTxt())) {
														debugAnswerTxt = dt.getAnswerTxt();
														be.getAnswerMap()
																.put(batch[i][0],
																		dt.getAnswerTxt()
																				+ "$$"
																				+ ddDT.getValue());
														// pairs.setValue(ddDT.getKey()+"$$"+ddDT.getValue());
														break;
													}
												}
											}

										} else if (batch[i][5] != null
												&& batch[i][5].toString()
														.equalsIgnoreCase(
																"1013")) {
											
											
											// handling multi-select
											String mulVal = "";
											
											
											for (int cloop = 0; cloop < dtList
													.size(); cloop++) { //mulVal = "";
												Long nbsQuestionUid = dtList.get(
														cloop).getNbsQuestionUid();
												if( previousValue !=null && !previousValue.equals(nbsQuestionUid))
													mulVal = "";
													
												previousValue = nbsQuestionUid;
												String values = dtList.get(
														cloop).getAnswerTxt();
												if (values != null)
													debugAnswerTxt = values;
												ArrayList<Object> srtValues = new ArrayList<Object>();
												HashMap<Object, Object> formMap = new HashMap<Object, Object>();
												formMap = (HashMap<Object, Object>) form
														.getFormFieldMap();
												FormField ff = (FormField) formMap
														.get(batch[i][0]);
												srtValues = (ArrayList<Object>) form
														.getCodedValue(ff
																.getCodeSetNm());
												DropDownCodeDT ddDT = new DropDownCodeDT();
												for (int i1 = 0; i1 < srtValues
														.size(); i1++) {
													ddDT = (DropDownCodeDT) srtValues
															.get(i1);
													if (values != null
															&& values
																	.indexOf("^") != -1) {
														// values =
														// values.substring(0,
														// values.indexOf("$MulOth$"));
														if (ddDT.getKey()
																.equals(values
																		.substring(
																				0,
																				values.indexOf("^")))) {
															mulVal = mulVal
																	+ ddDT.getKey()
																	+ "$$"
																	+ ddDT.getValue()
																	+ "$MulOth$"
																	+ values.substring(
																			values.indexOf("^") + 1,
																			values.length())
																	+ "#MulOth#"
																	+ "||";
															break;
														}
													} else if (ddDT.getKey()
															.equals(values)) {
														mulVal = mulVal
																+ ddDT.getKey()
																+ "$$"
																+ ddDT.getValue()
																+ "||";
														break;
													}
												}
											}
											be.getAnswerMap().put(batch[i][0],
													mulVal);
											
											
										}
										// for the rolling notes, we will pull
										// out the date and user from the note
										// and put them in separate fields
										else if (batch[i][5] != null
												&& batch[i][5].toString()
														.equalsIgnoreCase(
																"1019")) {
											// handling rolling note
											String str = "";
											if (dt.getAnswerTxt() != null)
												str = dt.getAnswerTxt();
											debugAnswerTxt = str;
											String theUser = str.substring(0,
													str.indexOf("~"));
											String theDate = str.substring(
													str.indexOf("~") + 1,
													str.lastIndexOf("~~"));
											String theNote = str.substring(
													str.lastIndexOf("~~") + 2,
													str.length());
											be.getAnswerMap().put(batch[i][0],
													theNote);
											be.getAnswerMap().put(
													batch[i][0] + "Date",
													theDate);
											be.getAnswerMap().put(
													batch[i][0] + "User",
													theUser);
										}
										// be.getAnswerMap().put(batch[i][0],
										// dt.getAnswerTxt()+"$$"+"Indigenous, within jurisdiction");
										// else if component is not null
										else if (batch[i][5] != null) {
											String str = dt.getAnswerTxt();
											if (str != null)
												debugAnswerTxt = str;
											if (str != null
													&& str.indexOf("^") != -1) {
												HashMap<Object, Object> formMap = new HashMap<Object, Object>();
												formMap = (HashMap<Object, Object>) form
														.getFormFieldMap();
												ArrayList<Object> srtValues = new ArrayList<Object>();
												if (batch[i][6] != null) {
													srtValues = (ArrayList<Object>) form
														.getCodedValue(batch[i][6]);
												}
												DropDownCodeDT ddDT = new DropDownCodeDT();
												for (int i1 = 0; i1 < srtValues
														.size(); i1++) {
													ddDT = (DropDownCodeDT) srtValues
															.get(i1);
													if (ddDT.getKey()
															.equals(str.substring(
																	str.indexOf("^") + 1,
																	str.length()))) {
														be.getAnswerMap()
																.put(batch[i][0],
																		str.substring(
																				0,
																				str.indexOf("^"))
																				+ "$sn$"
																				+ ddDT.getKey()
																				+ "$val$"
																				+ ddDT.getValue());
														break;
													}
												}
												if (be.getAnswerMap().get(
														batch[i][0]) == null) {
													be.getAnswerMap().put(
															batch[i][0],
															dt.getAnswerTxt());
													if ("PLACE"
															.equalsIgnoreCase(batch[i][3])) {
														Map p = PlaceUtil
																.getThePlaceParticipation(
																		dt.getAnswerTxt(),
																		session);
														be.getAnswerMap()
																.put(batch[i][0]
																		+ "Disp",
																		(String) p
																				.get("placeStr"));
														// Postal Locator/Tele
														// Locator might be
														// deactivated.
														be.getAnswerMap()
																.put(batch[i][0],
																		(String) p
																				.get("uidStr"));
													}
												}
											} else
												be.getAnswerMap().put(
														batch[i][0],
														dt.getAnswerTxt());
										}
										be.setId(1);
									}

								}
							}
							if (be.getId() == 1) {
								be.setSubsecNm(SubSectionNm);
								be.setId(PageForm.getNextId());

								alist.add(be);
							}
						}// number of batch records in subsection

					} catch (Exception e) {
						if (debugNBSQuestionUid != null)
							logger.error("Error: populateBatchRecord got error on nbs_question_uid = "+debugNBSQuestionUid);
						if (debugAnswerTxt != null)
							logger.error("Error: populateBatchRecord got error on answer_txt = "+debugAnswerTxt);
						logger.fatal("populateBatchRecords Exception thrown in internal batch2: "
								+ e.getMessage(),e);
						e.printStackTrace();
						throw new Exception(e.getMessage(),e);
					}
					if(form.getBatchEntryMap().containsKey(SubSectionNm) && ((ArrayList<BatchEntry>)form.getBatchEntryMap().get(SubSectionNm)).size()==0)
						form.getBatchEntryMap().put(SubSectionNm, alist);
					else if(alist!=null && alist.size()>0)
						batchMaplist.put(SubSectionNm, alist);
				}// walking through the Batchmap

			}
			form.getBatchEntryMap().putAll(batchMaplist);
			form.setSubSecStructureMap(batchMap); // structure of the batch
													// entries
		} catch (Exception e) {
			if (debugNBSQuestionUid != null)
				logger.error("Error: populateBatchRecord got error on nbs_question_uid = "+debugNBSQuestionUid);
			if (debugAnswerTxt != null)
				logger.error("Error: populateBatchRecord got error on answer_txt = "+debugAnswerTxt);
			logger.fatal("populateBatchRecords Exception thrown: " + e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}

	}

	/**
	 * findBatchRecords - find any Repeating Batch records for the specified
	 * Page Form Code Un-parses and adds Date and User for Rolling Notes
	 * components (1019) Returns the BatchMap 0=quesId
	 * 1=nbsQuesUid,2=appearInHeader, 3=Header Nm, 4=ColWidth, 5=ComponentUid,
	 * 6=CodeSetNm i.e. 1015000 STD491 Y Place 80 1017 NULL
	 *
	 * @param pageFormCd
	 * @param session
	 * @return batchMap
	 * @throws NEDSSAppException
	 */
	@SuppressWarnings("rawtypes")
	public Map findBatchRecords(String invFrmCd, HttpSession session) throws NEDSSAppException {
		try {
			Map<Object, Object> batchMap = new HashMap<Object, Object>();
			MainSessionCommand msCommand = null;
			try {
				// String sBeanJndiName = "EntityProxyEJBRef";
				String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
				String sMethod = "findBatchRecords";
				Object[] oParams = new Object[] { invFrmCd };
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				batchMap = (Map<Object, Object>) arr.get(0);

			} catch (Exception ex) {
				ex.printStackTrace();
				if (session == null) {
					logger.error("Error: no session, please login");
				}
				logger.fatal("personVO: ", ex);
				logger.fatal("PageLoadUtil.findBatchRecords Exception thrown:"
						+ ex.getMessage(),ex);
				throw new NEDSSAppException(ex.getMessage(),ex);
			}
			// For Rolling Notes we are adding in metadata
			// The assumption is made that it is the only thing in the subsection
			// The User and Date are stored at the start of the note so there is no
			// nbs_question_uid
			// so the second value in the batchmap is null
			if (batchMap != null && batchMap.size() > 0) {
				Iterator<Entry<Object, Object>> ite = batchMap.entrySet()
						.iterator();
				while (ite.hasNext()) {
					// get the batch metadata for the subsection
					Map.Entry<Object, Object> pairs1 = (Map.Entry<Object, Object>) ite
							.next();
					String SubSectionNm = pairs1.getKey().toString();
					String batch[][] = (String[][]) pairs1.getValue();
					for (int i = 0; i < batch.length; i++) {
						if (batch[i][5] != null
								&& batch[i][5].toString().equalsIgnoreCase("1019")) {
							// add the Date as if it was in the question metadata
							// for the Repeating Batch header to work correctly
							int batchLoc = i + 1;
							String dateMeta[] = { batch[i][0] + "Date", null, "Y",
									"Date", "10", "1008", null };
							batch[batchLoc] = dateMeta;
							// add the User as if it was in the question metadata
							// for the Repeating Batch header to work correctly
							String userMeta[] = { batch[i][0] + "User", null, "Y",
									"Added/Updated By", "20", "1008", null };
							batch[++batchLoc] = userMeta;
							// pairs1.setValue(batch);
							break;
						}
					} // for
				} // while iter
			} // if not null
			return batchMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.findBatchRecords Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private void loadCreateFromDocument(PageForm form,
			HttpServletRequest request) throws NEDSSAppException {
		try {
			Long nbsDocumentUid = (Long) NBSContext.retrieve(
					request.getSession(), "DSDocumentUID");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request
					.getSession().getAttribute("NBSSecurityObject");
			String conditionCd = (String) NBSContext.retrieve(
					request.getSession(),
					NBSConstantUtil.DSInvestigationCondition);
			if (nbsDocumentUid != null) {
				Object obj = InvestigationUtil.createProxyObject(
						nbsDocumentUid, conditionCd, nbsSecurityObj);
				if (obj != null) {
					PageActProxyVO pageProxyVO = (PageActProxyVO) obj;
					InvestigationUtil.setEntities(pageProxyVO, nbsSecurityObj,
							request);
					pageProxyVO.getPublicHealthCaseVO()
							.setTheParticipationDTCollection(
									pageProxyVO
											.getTheParticipationDTCollection());
					
					String programAreaCd = (String) NBSContext.retrieve(
							request.getSession(),
							NBSConstantUtil.DSInvestigationProgramArea);
					
					if("STD".equals(programAreaCd) || "HIV".equals(programAreaCd))
						populateUserSelectedProcessingDecisionForDocument(pageProxyVO, request);
					
					this.setCommonAnswersForViewEdit(form, pageProxyVO, request);
					// Page Specific Answers
					this.setMSelectCBoxAnswersForViewEdit(form, pageProxyVO
							.getPageVO().getPamAnswerDTMap());

					
					pageProxyVO.getPublicHealthCaseVO()
							.getThePublicHealthCaseDT()
							.setProgAreaCd(programAreaCd);
					this.setInvestigationInformationOnForm(form, pageProxyVO);
					fireRulesOnEditLoad(form, request);
					_loadEntities(form, pageProxyVO, request);
					this.populateBatchRecords(form, form.getPageFormCd(), request.getSession(),
							((PageActProxyVO) pageProxyVO).getPageVO()
									.getPageRepeatingAnswerDTMap());
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.loadCreateFromDocument Exception thrown: "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	/**
	 * @param pageProxyVO
	 * @param request
	 * @throws NEDSSAppException
	 * 
	 * 
	 * ND-32460 - populate use selected processing decision in Current Process Stage and Init Follow Up.
	 */
	private void populateUserSelectedProcessingDecisionForDocument(PageActProxyVO pageProxyVO, HttpServletRequest request) throws NEDSSAppException {
		try {
			String currProcessStateCd = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSProcessingDecision);
			pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCurrProcessStateCd(currProcessStateCd);
			pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setInitFollUp(currProcessStateCd);
			
		} catch (Exception ex) {
			logger.fatal("PageLoadUtil.populateUserSelectedProcessingDecisionForDocument Exception thrown: "+ ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public void loadCoinfectionData(PageForm form, PageActProxyVO newPageProxyVO, 
			HttpServletRequest request) throws NEDSSAppException {
		try {
			// For Release 4.5, the functionality is limited to nbscaseAnswer
			// questions(single entry and repeating questions)
			Long coinfectionInvestigationId = null;
			PageProxyVO pageProxyVO = null; // pageProxyVO of co-infection investigation
			try {
				coinfectionInvestigationId = ((CoinfectionSummaryVO) NBSContext
						.retrieve(request.getSession(),
								NBSConstantUtil.DSCoinfectionInvSummVO))
						.getPublicHealthCaseUid();
			} catch (Exception ex) {
				logger.debug("Coinfection investigation is not found in context :"
						+ ex.toString());
			}
			if (coinfectionInvestigationId != null) {
				pageProxyVO = getProxyObject(
						coinfectionInvestigationId.toString(),
						form.getBusinessObjectType(), request.getSession());
				Map<Object, Object> answerMap = this.updateMapWithQIds(((PageActProxyVO) pageProxyVO)
						.getPageVO().getPamAnswerDTMap());
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				// Multiselect answers
				ArrayList<Object> multiSelects = this.retrieveMultiSelectQIds(form);
				this.setAnswerArrayMapAnswersForCoinfection(multiSelects, answerMap,
						form.getPageClientVO());
				// Load all PAM Specific answers to form
				this.setPamSpecificAnswersForCoinfection(answerMap, returnMap,
						form.getPageClientVO());
				this.populateBatchRecords((BaseForm) form, form.getPageFormCd(),
						request.getSession(),
						this.getCoinfectionQuestions(((PageActProxyVO) pageProxyVO)
								.getPageVO().getPageRepeatingAnswerDTMap()));
				
				// Load Case_management data
				this.populateCoinfectionQuestionAnswerForCoreTables(form, pageProxyVO, newPageProxyVO);

			}

		} catch (Exception e) {
			logger.fatal("PageLoadUtil.loadCoinfectionData Exception thrown: "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public  Map<Object, Object> getCoinfectionQuestions(
			Map<Object, Object> answermap) throws NEDSSAppException {

		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			if (answermap == null)
				return returnMap;
			for (Object questionId : answermap.keySet()) {
				if (this.getCoinfectionQuestionKeyMap() != null
						&& this.getCoinfectionQuestionKeyMap().containsKey(questionId))
					returnMap.put(questionId, answermap.get(questionId));
			}
			return returnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getCoinfectionQuestions Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}


	/**
	 * @return
	 * @throws NEDSSAppException
	 */
	private   Map<Object, Object> getConifectionQuestionsForCoreTables() throws NEDSSAppException{
		try{
			Map<Object,Object> populateValueFromCoreTablesMap = new HashMap<Object, Object>();
			
			for (Object questionId : this.coinfectionQuestionMap.keySet()) {
				NbsQuestionMetadata metaData = (NbsQuestionMetadata)this.coinfectionQuestionMap.get(questionId);
				if(metaData!=null && !metaData.getDataLocation().contains("NBS_CASE_ANSWER.")){
					populateValueFromCoreTablesMap.put(questionId, metaData.getDataLocation());
				}	
			}
			return populateValueFromCoreTablesMap;
		}catch(Exception ex){
			logger.fatal("PageLoadUtil.getConifectionQuestionsForCoreTables Exception thrown:"+ ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}

	
	/**
	 * @param form
	 * @param pageProxyVO
	 * @param dbLocationContains i.e. "CASE_MANAGEMENT."
	 * @throws NEDSSAppException
	 */
	private void populateCoinfectionQuestionAnswerForCoreTables(PageForm form, PageProxyVO coinfectionPageProxyVO, PageActProxyVO newPageProxyVO) throws NEDSSAppException{
		try{
			Map<Object,Object> populateValueFromOtherTablesMap = getConifectionQuestionsForCoreTables();			
			for (Object key : populateValueFromOtherTablesMap.keySet()) {
				String dbLocation = (String) populateValueFromOtherTablesMap.get(key);
				if(dbLocation!=null && dbLocation.contains(".")){
					String tableName = dbLocation.substring(0, dbLocation.indexOf("."));
					String columnName = dbLocation.substring(dbLocation.indexOf(".")+1,dbLocation.length());
					String getterMethod = DynamicBeanBinding.getGetterName(columnName);
					
					if(getterMethod!=null){
						Map<Object, Object> dataLocationToBeanMap = populateDataLocationToBeanMap(coinfectionPageProxyVO);
						Object dtObject = dataLocationToBeanMap.get(tableName);
						
						if(dtObject!=null){
							String value = DynamicBeanBinding.getValueForMethod(dtObject,getterMethod,dtObject.getClass().getName());
							
							if(value!=null){
								Map<Object, Object> dataLocationToBeanMapForNewPageActProxyVo = populateDataLocationToBeanMap(newPageProxyVO);
								Object dtObject1 = dataLocationToBeanMapForNewPageActProxyVo.get(tableName);
								DynamicBeanBinding.populateBean(dtObject1, columnName, value);
							}
						}
					}else{
						logger.debug("getterMethod does not found from columnName: "+columnName +", not populating coinfection questions.");
					}
					
				}
			}
		}catch(Exception ex){
			logger.fatal("PageLoadUtil.populateCoinfectionQuestionAnswerForCoreTables Exception thrown:"+ ex.getMessage(),ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @deprecated Use {@link
	 *             DynamicBeanBinding#transferBeanValuesForView(Map<Object,
	 *             Object>,Object,Map<Object, Object>,String)} instead
	 */
	@SuppressWarnings({ "unused" })
	public static void transferBeanValuesForView(
			Map<Object, Object> questionMap, Object bean,
			Map<Object, Object> answerMap, String prefix) throws Exception {
		try {
			DynamicBeanBinding.transferBeanValuesForView(questionMap, bean,
					answerMap, prefix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void populateClientVO(NbsQuestionMetadata dt, Object ob,
			Map<Object, Object> map) throws NEDSSAppException {
		String datalocation = capitalize(dt.getDataLocation());
		Method methods[] = ob.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodNm = method.getName();
			String methodTypeNm = method.getReturnType().getName();
			try {
				if (methodNm.equals("get" + datalocation)) {

					if (methodTypeNm.equals("java.lang.String")) {
						Object obj = method.getDefaultValue();
						Object objr = method.invoke(ob, (Object[]) null);
						if (objr == null) {
							map.put(dt.getQuestionIdentifier(), (Object[]) null);
							logger.debug("The object here is null " + objr);
						} else {
							map.put(dt.getQuestionIdentifier(), objr);
							logger.debug("The object here is not null " + objr);
						}
					}
					/*
					 * else if (methodTypeNm.equals("java.lang.Timestamp")) {
					 * Object obj =method.getDefaultValue(); Object objr
					 * =method.invoke(ob, new Object[]{ null }).toString(); }
					 */
					else if (methodTypeNm.equals("java.sql.Timestamp")) {
						Object obj = method.getDefaultValue();
						Object objr = method.invoke(ob, (Object[]) null);
						if (objr == null) {
							map.put(dt.getQuestionIdentifier(), (Object[]) null);
							logger.debug("The object here is null " + objr);
						} else {
							Timestamp time = (Timestamp) objr;
							map.put(dt.getQuestionIdentifier(),
									StringUtils.formatDate(time));
							logger.debug("The object here is not for time: "
									+ objr);
						}
					} else if (methodTypeNm.equals("java.lang.Long")) {
						Object obj = method.getDefaultValue();
						Object objr = method.invoke(ob, (Object[]) null);
						if (objr == null) {
							map.put(dt.getQuestionIdentifier(), objr);
							System.out.println("The Long object here is null "
									+ objr);
						} else {
							Long longValue = (Long) objr;
							System.out
									.println("The Long object here is not null "
											+ objr);
							map.put(dt.getQuestionIdentifier(), longValue);
						}
					}
				}
			} catch (IllegalArgumentException e) {
				logger.fatal("PageLoadUtil.populateClientVO IllegalArgumentException thrown: "
						+ e.getMessage(),e);
				throw new NEDSSAppException(e.getMessage(),e);
			} catch (IllegalAccessException e) {
				logger.fatal("PageLoadUtil.populateClientVO IllegalAccessException thrown: "
						+ e.getMessage(),e);
				throw new NEDSSAppException(e.getMessage(),e);
			} catch (InvocationTargetException e) {
				logger.fatal("PageLoadUtil.populateClientVO InvocationTargetException thrown: "
						+ e.getMessage(),e);
				throw new NEDSSAppException(e.getMessage(),e);
			}
		}
	}

	private static String capitalize(String dataLocation)
			throws NEDSSAppException {
		String methodName = "";
		try {
			methodName = dataLocation
					.substring(dataLocation.indexOf(".") + 1,
							dataLocation.length()).replaceAll("_", " ")
					.toLowerCase();
			String[] tokens = methodName.split("\\s");
			methodName = "";

			for (int i = 0; i < tokens.length; i++) {
				char capLetter = Character.toUpperCase(tokens[i].charAt(0));
				methodName += capLetter
						+ tokens[i].substring(1, tokens[i].length());
			}
			logger.debug("System.out.println is" + methodName);
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.capitalize Exception thrown: "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
		return methodName;
	}

	public void handleHIVQuestions(PageForm pForm,
			Map<Object, Object> answerMap) throws NEDSSAppException {
		try {
			if (this.hivQuestionMap != null && answerMap != null) {
				Iterator<Object> keyset = this.hivQuestionMap.keySet().iterator();
				while (keyset.hasNext()) {
					Object key = keyset.next();
					// if answerMap contains HIV answer set it to form to
					// retrieve during submit.
					if (answerMap.containsKey(key)) {
						((PageForm) pForm).getPageClientVO().getHivAnswerMap()
								.put(key, answerMap.get(key));
						answerMap.remove(key);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.handleHIVQuestions Exception thrown: "
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void handleHIVBatchQuestions(PageForm pForm,
			Map<Object, Object> batchMap, Map<Object, Object> batchAnswerMap) throws NEDSSAppException {
		try {
			if (this.hivQuestionMap != null && batchAnswerMap != null
					&& batchMap != null) {
				// Iterator<Object> keyset = batchMap.keySet().iterator();

				Iterator<Entry<Object, Object>> keyset = batchMap.entrySet()
						.iterator();
				while (keyset.hasNext()) {
					// walk through each subsection that is in
					// the batchmap structure map
					Map.Entry<Object, Object> pairs1 = (Map.Entry<Object, Object>) keyset
							.next();
					// get the structure map for the questions in the subsection
					String batch[][] = (String[][]) pairs1.getValue();
					ArrayList<Long> repeatQuestionColl = new ArrayList<Long>();
					boolean sectionHasHIVQuestions = false;
					for (int batchsize = 0; batchsize < batch.length; batchsize++) {
						if (batch[batchsize][1] != null) {
							// Add all the questions for this batch to a list
							repeatQuestionColl
									.add(new Long(batch[batchsize][1]));
							// if any of the question is HIV question mark the
							// flag as true
							if (this.hivQuestionMap.containsKey(new Long(
									batch[batchsize][1])))
								sectionHasHIVQuestions = true;
						}
					}
					// for all the questions in batch which has one or more HIV
					// questions, keep the backup of answers in
					// action form(HivAnswerBatchMap) to set it back during
					// submit of page.
					if (sectionHasHIVQuestions && repeatQuestionColl.size() > 0) {
						Iterator<Long> ite = repeatQuestionColl.iterator();
						while (ite.hasNext()) {
							Long key = ite.next();
							// Add the answers to place holder in action
							// form(HivAnswerBatchMap) to set it
							// back in proxyVO during submit
							pForm.getPageClientVO().getHivAnswerBatchMap()
									.put(key, batchAnswerMap.get(key));
							// remove it from batch answer map
							batchAnswerMap.remove(key);
						}

					}
				}
			}
		} catch (NumberFormatException e) {
			logger.fatal("Exception in handleHIVBatchQuestions: " + e.getMessage(),e);
			e.printStackTrace();
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void handlePatientHIVQuestions(PageForm pForm,
			PersonVO personVO) throws NEDSSAppException {
		try {
			if (personVO.getThePersonDT().getEharsId() != null)
				((PageForm) pForm)
						.getPageClientVO()
						.getHivAnswerMap()
						.put(PageConstants.EHARS_ID,
								personVO.getThePersonDT().getEharsId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.handlePatientHIVQuestions Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void handlePatientHIVQuestions(ClientVO clientVO,
			PersonVO personVO) throws NEDSSAppException {
		try {
			if (personVO.getThePersonDT().getEharsId() != null)
				((ClientVO) clientVO).getHivAnswerMap().put(PageConstants.EHARS_ID,
						personVO.getThePersonDT().getEharsId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.handlePatientHIVQuestions Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void getDefaultInvestigatorDetailsIfPresent(
			HttpServletRequest request, PageForm form) throws Exception {
		try {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			NBSSecurityObj nbsSecurityObj = null;
			HttpSession session = request.getSession();
			PersonVO personVO = null;
			MainSessionCommand msCommand = null;
			try {
				nbsSecurityObj = (NBSSecurityObj) session
						.getAttribute("NBSSecurityObject");
			} catch (Exception e) {
				logger.warn("Exception getting security obj in getDefaultInvestigatorDetailsIfPresent: "
						+ e);
			}
			if (nbsSecurityObj == null
					|| nbsSecurityObj.getTheUserProfile().getTheUser()
							.getProviderUid() == null)
				return;

			try {
				Long providerUID = nbsSecurityObj.getTheUserProfile()
						.getTheUser().getProviderUid();
				String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
				String sMethod = "getProvider";
				Object[] oParams = new Object[] { providerUID };

				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
						sMethod, oParams);
				personVO = (PersonVO) arr.get(0);
			} catch (NumberFormatException e) {
				logger.error("Error in getDefaultInvestigatorDetailsIfPresent: provider UID format wrong?"+e.getMessage(), e);
				return;
			} catch (Exception ex) {
				if (session == null) {
					logger.error("Error: no session, please login");
				}
				logger.fatal("Error in getDefaultInvestigatorDetailsIfPresent() getting provider: "+ex.getMessage(),ex);
				ex.printStackTrace();
				return;
			} finally {
				msCommand = null;
			}

			String providerUidStr = nbsSecurityObj.getTheUserProfile()
					.getTheUser().getProviderUid().toString();
			String versionCtrlNbr = "1"; // this is only called on create for
											// now...
			form.getAttributeMap().put(
					NEDSSConstants.CURRENT_INVESTIGATOR + "Uid", // Investigator
					(providerUidStr + "|" + versionCtrlNbr));
			if(getQuestionMap().containsKey(NEDSSConstants.INITIAL_INVESTIGATOR))
			form.getAttributeMap().put(
					NEDSSConstants.INITIAL_INVESTIGATOR + "Uid", // initial
																	// followup
																	// Investigator
					(providerUidStr + "|" + versionCtrlNbr));
			QuickEntryEventHelper qeeh = new QuickEntryEventHelper();
			String srchResultStr = qeeh.makePRVDisplayString(personVO);
			form.getAttributeMap().put(
					NEDSSConstants.CURRENT_INVESTIGATOR + "SearchResult",
					srchResultStr);
			if(getQuestionMap().containsKey(NEDSSConstants.INITIAL_INVESTIGATOR))
			form.getAttributeMap().put(
					NEDSSConstants.INITIAL_INVESTIGATOR + "SearchResult",
					srchResultStr);
			return;
		} catch (Exception e) {
			logger.fatal("PageLoadUtil.buildInvestigatorResult exception thrown "
					+ e.getMessage(),e);
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
	}

	public static void translateCaseAnswerDTsToAnswerDTs(PageProxyVO pageProxyVO)
			throws NEDSSAppException {
		PageActProxyVO proxyVO = (PageActProxyVO) pageProxyVO;
		Map<Object, Object> answerMap = proxyVO.getPageVO().getAnswerDTMap();
		Map<Object, Object> pamAnswerMap = proxyVO.getPageVO()
				.getPamAnswerDTMap();
		Map<Object, Object> repeatingAnswerMap = proxyVO.getPageVO()
				.getPageRepeatingAnswerDTMap();
		try {
			if (answerMap != null && answerMap.keySet().size() > 0) {
				Iterator<Object> ite = answerMap.keySet().iterator();
				while (ite.hasNext()) {
					Object key = ite.next();
					if (answerMap.get(key) instanceof NbsCaseAnswerDT) {
						NbsCaseAnswerDT caseAnswerDT = (NbsCaseAnswerDT) answerMap
								.get(key);
						NbsAnswerDT ansDT = caseAnswerDT;
						answerMap.put(key, ansDT);
					}
				}
			}
			if (pamAnswerMap != null && pamAnswerMap.keySet().size() > 0) {
				Iterator<Object> ite = pamAnswerMap.keySet().iterator();
				while (ite.hasNext()) {
					Object key = ite.next();
					if (pamAnswerMap.get(key) instanceof NbsCaseAnswerDT) {
						NbsCaseAnswerDT caseAnswerDT = (NbsCaseAnswerDT) pamAnswerMap
								.get(key);
						NbsAnswerDT ansDT = caseAnswerDT;
						pamAnswerMap.put(key, ansDT);
					}
				}
			}
			if (repeatingAnswerMap != null
					&& repeatingAnswerMap.keySet().size() > 0) {
				Iterator<Object> ite = repeatingAnswerMap.keySet().iterator();
				while (ite.hasNext()) {
					Object key = ite.next();
					if (repeatingAnswerMap.get(key) instanceof NbsCaseAnswerDT) {
						NbsCaseAnswerDT caseAnswerDT = (NbsCaseAnswerDT) repeatingAnswerMap
								.get(key);
						NbsAnswerDT ansDT = caseAnswerDT;
						repeatingAnswerMap.put(key, ansDT);
					}
				}
			}
		} catch (Exception ex) {
			logger.fatal("Exception while translating AnswerDTs To CaseAnswerDTs :"
					+ ex.getMessage(),ex);
			ex.printStackTrace();
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}

	public static boolean isMotherSyphilis(PageActProxyVO pgActProxy, PageForm form) throws NEDSSAppException
    {
        try {
			boolean isSyphilis = false;
			boolean brInd = false;
			String cd = pgActProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			String s = CachedDropDowns.getCodeDescTxtForCd(cd, "STD_SYPHILIS_CODE_LIST");
			if( !StringUtils.isEmpty(s) && !CTConstants.CongenitalSyphilisConditionCode.equals(cd))
			{
			    isSyphilis = true;
			}
        String pind = pgActProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPregnantIndCd();
			if(NEDSSConstants.YES.equalsIgnoreCase(pind))
			{
			    brInd = true;
			}
			Map m = form.getPageClientVO().getAnswerMap();
			if( NEDSSConstants.YES.equalsIgnoreCase((String)m.get("NBS216") ) )  //// Pregnant at exam
			{
			    brInd = true;
			}

			if( NEDSSConstants.YES.equalsIgnoreCase((String)m.get("NBS218") ) )  //// Pregnant at interview
			{
			    brInd = true;
			}

			if( NEDSSConstants.YES.equalsIgnoreCase((String)m.get("NBS221") ) )  //// Birth in the past year
			{
			    brInd = true;
			}
			return ( isSyphilis && brInd );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.isMotherSyphilis Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
    }

    /**
     * Get the list of candidates for co-infection. These are open investigations in the same co-infection group.
     * @param conditionCd
     * @param mprUid
     * @param request
     * @return CoinfectionSummaryVO ArrayList
     * @throws NEDSSAppConcurrentDataException
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getCoinfectionInvList(String conditionCd,
			Long mprUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
			if (conditionCd == null) {
				return coinfectionInvList;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getCoinfectionInvList";

				Object[] oParams = new Object[] { mprUid, conditionCd };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					coinfectionInvList = (ArrayList<Object>) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading coinfection investigations list: "+ e.toString(), e);
				e.printStackTrace();
				throw new ServletException(
						"Error while loading coinfection investigations list:"
								+ e.getMessage(), e);
			}

			return coinfectionInvList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getCoinfectionInvList Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}
	/**
	 * Get the list of Investigations associated with the co-infection Id of the passed
	 * public health case uid. Note: The specified investigation is also returned in the list
	 * providing it has a co_infection id.
	 * @param publicHealthCaseUid
	 * @param request
	 * @return CoinfectionSummaryVO ArrayList - list of all investigations containing the same
	 * 			co-infection as the specified investigation
	 * @throws NEDSSAppConcurrentDataException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getSpecificCoinfectionInvList(
			Long publicHealthCaseUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
			if (publicHealthCaseUid == null) {
				return coinfectionInvList;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getSpecificCoinfectionInvList";

				Object[] oParams = new Object[] { publicHealthCaseUid };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					coinfectionInvList = (ArrayList<Object>) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading specific coinfection investigations list: "+ e.toString(), e);
				e.printStackTrace();
				throw new ServletException(
						"Error while loading specific coinfection investigations list:"
								+ e.getMessage(), e);
			}

			return coinfectionInvList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getSpecificCoinfectionInvList Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}
	@SuppressWarnings("unchecked")
	public static Integer getSpecificCoinfectionInvListCount(
			Long publicHealthCaseUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			Integer count = null;
			if (publicHealthCaseUid == null) {
				return count;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getSpecificCoinfectionInvListCount";

				Object[] oParams = new Object[] { publicHealthCaseUid };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					count = (Integer) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading specific coinfection investigations list Count: "+ e.toString(), e);
				e.printStackTrace();
				throw new ServletException(
						"Error while loading specific coinfection investigations list Count:"
								+ e.getMessage(), e);
			}

			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getSpecificCoinfectionInvList Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}
	public static ArrayList<Object> getSpecificCoinfectionInvListPHC(
			Long publicHealthCaseUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
			if (publicHealthCaseUid == null) {
				return coinfectionInvList;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getSpecificCoinfectionInvListPHC";

				Object[] oParams = new Object[] { publicHealthCaseUid };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					coinfectionInvList = (ArrayList<Object>) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading specific coinfection investigations list PHC: "+ e.toString(), e);
				e.printStackTrace();
				throw new ServletException(
						"Error while loading specific coinfection investigations list PHC:"
								+ e.getMessage(), e);
			}

			return coinfectionInvList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getSpecificCoinfectionInvListPHC Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getOpenInvList(String conditionCd,
			Long mprUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
			if (conditionCd == null) {
				return coinfectionInvList;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getOpenInvList";

				Object[] oParams = new Object[] { mprUid, conditionCd };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					coinfectionInvList = (ArrayList<Object>) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading open investigations list: "+ e.toString(), e);
				throw new ServletException(
						"Error while loading open investigations list:"
								+ e.getMessage(), e);
			}

			return coinfectionInvList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getOpenInvList Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public void checkForChangeCondition(PageForm pageForm,
			HttpServletRequest request) throws NEDSSAppException {
		try {
			if(pageForm.getPageClientVO().getOldPageProxyVO() != null){
			String oldConditionDesc = pageForm.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCdDescTxt();
			pageForm.getAttributeMap().put("oldConditionDesc", oldConditionDesc);
			String conditionCd = (String) request.getParameter("newCondition");
			if (conditionCd != null) {
				CachedDropDownValues cdv = new CachedDropDownValues();
				String programArea = cdv.getProgramAreaCd(conditionCd);
				NBSContext.store(request.getSession(),
						NBSConstantUtil.DSInvestigationCondition, conditionCd);
				NBSContext.store(request.getSession(),
						NBSConstantUtil.DSInvestigationProgramArea, programArea);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.checkForChangeCondition Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	/**
	 * Get the list of Investigations associated with the co-infection Id of the passed
	 * person uid. Note: The specified investigation is also returned in the list
	 * providing it has a co_infection id.
	 * @param personUid
	 * @param request
	 * @return CoinfectionSummaryVO ArrayList - list of all investigations containing the same
	 * 			co-infection as the specified investigation
	 * @throws NEDSSAppConcurrentDataException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getPersonInvList(
			Long personUid, HttpServletRequest request)
			throws NEDSSAppConcurrentDataException, Exception {
		try {
			ArrayList<Object> invList = new ArrayList<Object>();
			if (personUid == null) {
				return invList;
			}
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			try {
				msCommand = holder.getMainSessionCommand(session);
				String sMethod = "getPersonInvestigationSummary";

				Object[] oParams = new Object[] { personUid };
				resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
				if (resultUIDArr != null && resultUIDArr.size() > 0)
					invList = (ArrayList<Object>) resultUIDArr.get(0);
			} catch (Exception e) {
				logger.fatal("Error while loading specific coinfection investigations list: "+ e.toString(), e);
				e.printStackTrace();
				throw new ServletException(
						"Error while loading specific coinfection investigations list:"
								+ e.getMessage(), e);
			}

			return invList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getPersonInvList Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	public static String  getProcessingDecisionForCoinfectionContacts(Collection<Object> contactInvSummary, String conditionCd,HttpServletRequest request) throws NEDSSAppException{
		try {
			ArrayList<Object> conditionCodes = null;

			Timestamp earliestDate = null;
			String recordSearchClosureCandidate = null;
			Long recordSearchClosureInvestigation = null;
			InvestigationSummaryVO rscInvSummVO = null;
			Iterator<Object> itr = contactInvSummary.iterator();
			while (itr.hasNext()) {
				InvestigationSummaryVO phc = (InvestigationSummaryVO) itr.next();
				//not same condition or in condition family - skip
				if (phc.getCd() == null || (!CachedDropDowns.doesConceptCodeBelongToCodeSet(phc.getCd(), "CASE_DIAGNOSIS_SUBSET"+conditionCd) && !conditionCd.equals(phc.getCd())))
						continue;
				//if it has not been dispositioned, skip
				if (phc.getDisposition() == null || phc.getDisposition().isEmpty())
					    continue;
				if (earliestDate == null) {
					if (phc.getLocalId() != null && phc.getAddTime() != null) {
						recordSearchClosureCandidate = phc.getLocalId();
						recordSearchClosureInvestigation = phc.getPublicHealthCaseUid();
						earliestDate = phc.getAddTime();
						rscInvSummVO = phc;
					}
				} else if (phc.getAddTime() != null && earliestDate.after(phc.getAddTime())) {
					if (phc.getLocalId() != null) {
					     earliestDate = phc.getAddTime();
					     recordSearchClosureCandidate = phc.getLocalId();
					     recordSearchClosureInvestigation = phc.getPublicHealthCaseUid();
					     rscInvSummVO = phc;
					}
				}
			} //while hasNext
			//if RSC - we set the contactDt.contactEntityPhcUid if it doesn't have a value;
			if (recordSearchClosureInvestigation != null){
				//form.getAttributeMap().put(CTConstants.RecordSearchClosureInvestigation, recordSearchClosureInvestigation);
				NBSContext.store(request.getSession(), NBSConstantUtil.DSRecordSearchClosureInvSummVO, rscInvSummVO);
			}

			String rscLocalId = recordSearchClosureCandidate;


			String secondaryReferralInvestigation="";


			//Timestamp earliestDate = null;
			//String secondaryReferralInvestigation = null;
			InvestigationSummaryVO srInvSummVO = null;
			itr = contactInvSummary.iterator();
			while (itr.hasNext()) {
				InvestigationSummaryVO phc = (InvestigationSummaryVO) itr.next();
				if (phc.getCd() == null || (!CachedDropDowns.doesConceptCodeBelongToCodeSet(phc.getCd(), "CASE_DIAGNOSIS_SUBSET"+conditionCd) && !conditionCd.equals(phc.getCd())))
						continue;
				//if investigation status not open or null skip
				if (phc.getInvestigationStatusCd() == null || !phc.getInvestigationStatusCd().equalsIgnoreCase("O")) //open
						continue;
				//if it has been dispositioned, skip
				if (phc.getDisposition() != null && !phc.getDisposition().isEmpty())
					    continue;
				if (earliestDate == null) {
					if (phc.getLocalId() != null && phc.getAddTime() != null) {
						secondaryReferralInvestigation = phc.getLocalId();
						srInvSummVO = phc;
						earliestDate = phc.getAddTime();
					}
				} else if (phc.getAddTime() != null && earliestDate.after(phc.getAddTime())) {
					if (phc.getLocalId() != null) {
					     earliestDate = phc.getAddTime();
					     srInvSummVO = phc;
						 secondaryReferralInvestigation = phc.getLocalId();
					}
				}
			} //while hasNext
			if(srInvSummVO!=null)
				NBSContext.store(request.getSession(), NBSConstantUtil.DSSecondaryReferralInvSummVO, srInvSummVO);
			//secondaryReferralInvestigation;

			String StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionNoInv;
			if (secondaryReferralInvestigation != null &&
					!secondaryReferralInvestigation.isEmpty())
				StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionNoInvDispo;
			else if (recordSearchClosureCandidate!= null &&
					!recordSearchClosureCandidate.isEmpty())
				StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionInvDispoClosed;
			//set the code set to use for the Processing Decision
			request.setAttribute("StdCrProcessingDecisionCodeset", StdCrProcessingDecisionCodeset);


			return StdCrProcessingDecisionCodeset;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("PageLoadUtil.getProcessingDecisionForCoinfectionContacts Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

	private static void setLastChangeUserAndTime(PageForm pageForm, HttpServletRequest request) throws NEDSSAppException{
		try{
			String lastUpdatedDate=(String)request.getAttribute("updatedDate");
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date parsedTimeStamp = dateFormat.parse(lastUpdatedDate);
			Timestamp timestampLastUpdatedDate = new Timestamp(parsedTimeStamp.getTime());


			Collection<Object> actRelationshipDTColl = ((PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO())
					.getPublicHealthCaseVO().getTheActRelationshipDTCollection();



			List<Object> actRelationshipDTCollList = new ArrayList<Object>( actRelationshipDTColl );
			//Sort collection to get last change first
			Collections.sort( actRelationshipDTCollList, new Comparator()
	        {
	        public int compare( Object a, Object b )
	           {
	            return((Timestamp) ((ActRelationshipDT)b).getLastChgTime()).compareTo((Timestamp) ((ActRelationshipDT) a).getLastChgTime());
	           }
	        } );

			if (actRelationshipDTCollList != null && actRelationshipDTCollList.size() > 0) {
				NBSAuthHelper helper = new NBSAuthHelper();
				Iterator<Object> iter = actRelationshipDTCollList.iterator();
				while (iter.hasNext()) {
					ActRelationshipDT dt = (ActRelationshipDT) iter.next();
					if (dt != null && dt.getLastChgTime()!=null && dt.getLastChgUserId()!=null) {

						if(timestampLastUpdatedDate.compareTo((Timestamp)dt.getLastChgTime())==-1){//if timestampLastUpdatedDate<dt.getLastChgTime()

							//Last change user id from public health case
							Long lastChangeUserId = ((PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO())
									.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId();

							//Last change date from public health case
							Timestamp lastChangeDate = ((PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO())
											.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();

							request.setAttribute("updatedDate", StringUtils.formatDate(lastChangeDate));
							request.setAttribute("updatedBy", dt.getLastChgUserId() == null ? "" : helper.getUserName(lastChangeUserId));

							//request.setAttribute("updatedDate", StringUtils.formatDate(dt.getLastChgTime()));
							//request.setAttribute("updatedBy", dt.getLastChgUserId() == null ? "" : helper.getUserName(dt.getLastChgUserId()));
						}
						break;

					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception while getting lateChangeUser and lastChangeTime from ActRelationship "+ex.getMessage(), ex);
			throw new NEDSSAppException("Exception while getting lateChangeUser and lastChangeTime from ActRelationship "+ex.getMessage(), ex);
		}
	}


	public static String getDateDiffrence(String inputString1 ,String inputString2 ){
		long diff = 0;
		long difference = 0;
		String returnDateDiff = "";
		SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
		    Date date1 = myFormat.parse(inputString1);
		    Date date2 = myFormat.parse(inputString2);
		     diff = date2.getTime() - date1.getTime();
		     difference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		     returnDateDiff = String.valueOf(difference);
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		return returnDateDiff;
	}

	public static void populatePatientSummary(PageForm form, PersonVO personVO, PageActProxyVO proxyVO, String busObjType, HttpServletRequest request)
    {
    	try {

			Collection<Object>  nms = personVO.getThePersonNameDTCollection();
			String strPName ="";
			String strFName="";
			String strMName="";
			String strLName="";
			String CurrSex = "";
			String nmSuffix = "";
			if(nms != null)
			{
				Iterator<Object>  itname = nms.iterator();
				Timestamp mostRecentNameAOD = null;
				while (itname.hasNext()) {
					PersonNameDT name = (PersonNameDT) itname.next();

					// for personInfo
					if (name != null
							&& name.getNmUseCd() != null
							&& name.getNmUseCd().equals(NEDSSConstants.LEGAL)
							&& name.getStatusCd() != null
							&& name.getStatusCd().equals(
									NEDSSConstants.STATUS_ACTIVE)
							&& name.getRecordStatusCd() != null
							&& name.getRecordStatusCd().equals(
									NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						if (mostRecentNameAOD == null
								|| (name.getAsOfDate() != null && !name
										.getAsOfDate()
										.before(mostRecentNameAOD))) {
							strFName = "";
							strMName = "";
							strLName = "";
							nmSuffix = "";
							mostRecentNameAOD = name.getAsOfDate();
							if (name.getFirstNm() != null)
								strFName = name.getFirstNm();
							if (name.getMiddleNm() != null)
								strMName = name.getMiddleNm();
							if (name.getLastNm() != null)
								strLName = name.getLastNm();
							if (name.getNmSuffix() != null)
								nmSuffix = name.getNmSuffix();

						}
					}
				}
			}
			strPName = strFName +" "+strMName+ " "+strLName;
			if(null == strPName || strPName.equalsIgnoreCase("null")){
				strPName ="";
			}

			form.getAttributeMap().put("FullName", strPName);
			request.setAttribute("FullName", strPName);

			form.getAttributeMap().put("DOB",  StringUtils.formatDate(personVO.getThePersonDT().getBirthTime()));
			request.setAttribute("DOB", StringUtils.formatDate(personVO.getThePersonDT().getBirthTime()));
			PersonUtil.setCurrentAgeToRequest(request, personVO.getThePersonDT());
			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
			request.setAttribute("patientSuffixName", cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));


			if(personVO.getThePersonDT().getCurrSexCd() != null)
				 CurrSex = personVO.getThePersonDT().getCurrSexCd();
				if(CurrSex.equalsIgnoreCase("F"))
					CurrSex = "Female";
				if(CurrSex.equalsIgnoreCase("M"))
					CurrSex = "Male";
				if(CurrSex.equalsIgnoreCase("U"))
					CurrSex = "Unknown";
			form.getAttributeMap().put("CurrentSex", CurrSex);
			request.setAttribute("CurrentSex", CurrSex);

			form.getAttributeMap().put("PatientId",personVO.getThePersonDT().getLocalId()== null? "" : PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));

			if(proxyVO!=null){
				NBSAuthHelper helper = new NBSAuthHelper();
				if(proxyVO.getInterventionVO()!=null && proxyVO.getInterventionVO().getTheInterventionDT()!=null){
					InterventionDT interventionDT = proxyVO.getInterventionVO().getTheInterventionDT();
					form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_LOCAL_ID,interventionDT.getLocalId()== null ? "" : interventionDT.getLocalId());
					form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_CREATED_ON,interventionDT.getAddTime()== null ? "" : StringUtils.formatDate(interventionDT.getAddTime()));
					form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_CREATED_BY,interventionDT.getAddUserId() == null ? "" : helper.getUserName(interventionDT.getAddUserId()));
					form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_UPDATED_ON,interventionDT.getLastChgTime()== null ? "" : StringUtils.formatDate(interventionDT.getLastChgTime()));
					form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_UPDATED_BY,interventionDT.getLastChgUserId() == null ? "" : helper.getUserName(interventionDT.getLastChgUserId()));
	
					String vaccineType = interventionDT.getMaterialCd();
					if(vaccineType!=null){
						vaccineType = ": "+cachedDropDownValues.getCodeShortDescTxt(vaccineType, "VAC_NM");
					}else{
						vaccineType = "";
					}
					form.getAttributeMap().put(PageConstants.VACCINE_TYPE,vaccineType);
					
					form.getAttributeMap().put(PageConstants.ELECTRONIC_IND,interventionDT.getElectronicInd());
				}
			}

		} catch (NumberFormatException e) {
						logger.error("Error: "+e.getMessage(), e);
			e.printStackTrace();
		} catch (NEDSSSystemException e) {
						logger.error("Error: "+e.getMessage(), e);
			e.printStackTrace();
		}

    }

	public static Long getPatientIdFromNBSActEntity(PageActProxyVO pageActProxyVo) throws NEDSSAppException{
		Long patientId = null;
		try{
			Collection<Object> actEntityDTColl = pageActProxyVo.getPageVO().getActEntityDTCollection();
			if (actEntityDTColl != null) {
				for (Iterator<Object> anIterator = actEntityDTColl.iterator(); anIterator.hasNext();) {
					NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) anIterator.next();
					if (NEDSSConstants.SUBJECT_OF_VACCINE.equals(nbsActEntityDT.getTypeCd())) {
						patientId = nbsActEntityDT.getEntityUid();
					}
				}
				logger.debug("patient ID: "+patientId);
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(),ex);
			throw new NEDSSAppException("Exception while getting PatientUd from NBSActEntity"+ex.getMessage(), ex);
		}
		return patientId;
	}


	/**
	 * @param pageForm
	 * @param requestParam
	 * @param parentWindowName
	 * @param permissionName
	 * @param sessionParam
	 * @param request
	 * @throws NEDSSAppException
	 */
	public static void overrridePermissionToHideButton(PageForm pageForm, String requestParam, String parentWindowName, String permissionName, String sessionParam,HttpServletRequest request) throws NEDSSAppException{
		try{
			String parentWindow = request.getParameter(requestParam);
            if(parentWindow!=null && parentWindowName.equals(parentWindow)){
            	pageForm.getSecurityMap().put(permissionName,String.valueOf(false));
            	NBSContext.store(request.getSession(), sessionParam, parentWindowName);
            }
            try{
            	parentWindow = (String) NBSContext.retrieve(request.getSession(), sessionParam);
            	if(parentWindow!=null && parentWindowName.equals(parentWindow)){
                	pageForm.getSecurityMap().put(permissionName,String.valueOf(false));
            	}
            }catch(Exception ex){

            }
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(),ex);
			throw new NEDSSAppException("Exception overrridePermissionToHideButton"+ex.getMessage(), ex);
		}
	}


	public static void createVaccinationLinkForSupplementalInfo(VaccinationSummaryVO vacVO, String actionMode) throws NEDSSAppException{
		try{
			if (actionMode != null
					&& (actionMode.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) ||
							actionMode.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))) {
				String dateLink = dateLink(vacVO.getActivityFromTime());
				
				if(NEDSSConstants.ELECTRONIC_IND_VACCINATION.equals(vacVO.getElectronicInd())){
					vacVO.setActionLink(dateLink+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
				}else{
					vacVO.setActionLink(dateLink);
				}
			}else{
				String popupLink = "<a href=\"javascript:contactRecordPopUp('/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&actUid="+vacVO.getInterventionUid()+"&Action=InvPath&ParentWindow=SupplementalInfo')\">"+
						dateLink(vacVO.getActivityFromTime())+"</a>";
				
				if(NEDSSConstants.ELECTRONIC_IND_VACCINATION.equals(vacVO.getElectronicInd())){
					vacVO.setActionLink(popupLink+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
				}else{
					vacVO.setActionLink(popupLink);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(),ex);
			throw new NEDSSAppException("Exception createVaccinationLinkForSupplementalInfo"+ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param proxyVO
	 * @return
	 * @throws NEDSSAppException
	 * 
	 * For future needs if any other table has coninfection questions and need to prepopulate in newly created investigation form, add that table name and DT in dataLocationToBeanMap
	 */
	private static Map<Object, Object> populateDataLocationToBeanMap(PageProxyVO proxyVO) throws NEDSSAppException{
		Map<Object, Object> dataLocationToBeanMap = new HashMap<Object, Object>();
		try{
			dataLocationToBeanMap.put(RenderConstants.CASE_MANAGEMENT, proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT());
			dataLocationToBeanMap.put(RenderConstants.PUBLIC_HEALTH_CASE, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
			
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(),ex);
			throw new NEDSSAppException("Exception populateDataLocationToBeanMap"+ex.getMessage(), ex);
		}
		return dataLocationToBeanMap;
	}

	public Map<Object, Object> getCoinfectionQuestionKeyMap() {
		return coinfectionQuestionKeyMap;
	}

	public void setCoinfectionQuestionKeyMap(Map<Object, Object> coinfectionQuestionKeyMap) {
		this.coinfectionQuestionKeyMap = coinfectionQuestionKeyMap;
	}

	public Map<Object, Object> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<Object, Object> questionMap) {
		this.questionMap = questionMap;
	}

	public Map<Object, Object> getQuestionKeyMap() {
		return questionKeyMap;
	}

	public void setQuestionKeyMap(Map<Object, Object> questionKeyMap) {
		this.questionKeyMap = questionKeyMap;
	}

	
	/**
	   * Generic method used by different methods that returns drop down list which
	   * needs to be further tied to different elements.
	   * @param labId : String
	   * @param type : String
	   * @param progAreaCode : String
	   * @param orderedTestCode : String
	   * @param resultedTestCode : String
	   * @param conditionCode : String
	   * @param request : HttpServletRequest
	   * @return : String
	   */
	  public static void cachedValues(String labId, String type, String conditionCode, String progAreaCode, String orderedTestCode, String resultedTestCode, HttpServletRequest request, ArrayList<Object> labResultedData) {
	    Collection<Object>  alist = new ArrayList<Object> ();
	    Map<String,String> map = new HashMap<String,String>();

	    if (type.equals(NEDSSConstants.ORDERED_TEST_LOOKUP) || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
	      if ( (conditionCode != null && !conditionCode.trim().equalsIgnoreCase(""))
	              && !conditionCode.equalsIgnoreCase("undefined")) {
	           map.put(SRTFilterKeys.CONDITION_CODE, conditionCode);
	         }
	      else if ( (progAreaCode != null && progAreaCode.trim().equalsIgnoreCase(""))
	           || (progAreaCode != null && progAreaCode.equalsIgnoreCase("undefined"))
	           || (progAreaCode != null && progAreaCode.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE))) {
	        progAreaCode = null;
	      }
	      else {
	        map.put(SRTFilterKeys.PROGRAM_AREA_CODE, progAreaCode);
	      }
	    }
	    if (!type.equals(NEDSSConstants.TREATMENTS_LOOKUP) || !type.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {
	      if (labId != null && labId.trim().equalsIgnoreCase("")) {
	        labId = null;
	      }
	      map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
	    }
	    if (type.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP) || type.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)
	        || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
	      if (orderedTestCode == null || (orderedTestCode != null && orderedTestCode.trim().equalsIgnoreCase(""))) {
	        orderedTestCode = null;
	      }
	      if(orderedTestCode!= null && !orderedTestCode.trim().equalsIgnoreCase("")){
	        map.put(SRTFilterKeys.ORDERED_TEST_CODE, orderedTestCode);
	        if(map.get(SRTFilterKeys.PROGRAM_AREA_CODE)!= null && type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
	          map.remove(SRTFilterKeys.PROGRAM_AREA_CODE);
	    }
	    // else
	   //   map.put(SRTFilterKeys.ORDERED_TEST_CODE, NEDSSConstants.LAB_TESTCODE_NI);

	    }
	    if (type.equals(NEDSSConstants.ORGANISM_LOOKUP) || type.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP) || type.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

	      if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
	        resultedTestCode = null;
	      }
	      map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
	    }
	    if (type.equals(NEDSSConstants.DRUG_LOOKUP) ) {

	      if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
	        resultedTestCode = null;
	      }
	       map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
	      map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
	    }

	    alist = getObjectListFromProxy(map, type, request);

	    ArrayList<Object> list = new ArrayList<Object> ();
	    StringBuffer sLabOptions = new StringBuffer("");
	    StringBuffer sLabOptionsNonIndent = new StringBuffer("");
	    
	    ArrayList <Object> labResultedDataNotInded = new ArrayList<Object>();
	    if (alist != null) {
	     Iterator<Object>  itr = alist.iterator();
	      sLabOptions.append("$|");
	      sLabOptionsNonIndent.append("$|");
	      int maxDropDownList = 0;
	      if (propertyUtil.getMaxDropDownCount() != null )
	      {
	        maxDropDownList = new Integer(propertyUtil.getMaxDropDownCount()).intValue();
	      }
	      int i = 0;
	      int j = 0;

	      DropDownCodeDT dDownDTBlank = null;
          
	      dDownDTBlank = new DropDownCodeDT();
	      dDownDTBlank.setKey("");
	      dDownDTBlank.setValue("");
          labResultedData.add(dDownDTBlank);
          
	      while (itr.hasNext()) {
	        SRTCode sRTLabTestDT = (SRTCode) itr.next();
	        //if(sRTLabTestDT.getIndentLevel()!= null && type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
	       // {
	       //   logger.debug("The resulted Test lookup is fine");
	       // }
	       String key = sRTLabTestDT.getCode();
	       String res = sRTLabTestDT.getDesc();
	       if((sRTLabTestDT.getIndentLevel()!= null)&& (sRTLabTestDT.getIndentLevel().compareTo(new Long(1)) == 0))
	       {
	            //logger.debug("indent lebvel nbr is :" +sRTLabTestDT.getIndentLevel());
	            i++;
	            if (res != null) {
	            	
	            	 DropDownCodeDT dDownDT = null;
                    
                         dDownDT = new DropDownCodeDT();
                         dDownDT.setKey(key);
                         dDownDT.setValue(res);
                         labResultedData.add(dDownDT);
                     
                    
                     
	             // sLabOptions.append(key).append("$").append(res).append("|");

	            }
	            if (i == maxDropDownList)
	            {
	              logger.debug("The max count of indentLevelNbr has been reached :" + i);
	              break;
	            }list.add(sRTLabTestDT);
	        }
	        else
	        {
	          j++;
	          if (j == maxDropDownList)
	          {
	            logger.debug("The maxcount has been reached:" + j);
	            continue;
	          }if (res != null) {
	        	  
	        	  

	            	 DropDownCodeDT dDownDT = null;
                 
                      dDownDT = new DropDownCodeDT();
                      dDownDT.setKey(key);
                      dDownDT.setValue(res);
                      labResultedDataNotInded.add(dDownDT);
                  
	           // sLabOptionsNonIndent.append(key).append("$").append(res).append("|");

	          }

	        }

	      }

	    }
	    if(list.size()==0)
	    	labResultedData = labResultedDataNotInded;
	  //  return labResultedData;
	  }
	  
	  
	  /**
	   * This method extracts the data from the backend based on the Map<Object,Object> with desired keys
	   * and values. The filter decides which method to be called on the SRTCacheManagerEJB
	   * to get the collection of objects.
	   * @param map : Map
	   * @param filter : String
	   * @param request : HttpServletRequest
	   * @return : Collection
	   */
	  public static Collection<Object>  getObjectListFromProxy(Map<String,String> map, String filter, HttpServletRequest request) {
	    Collection<Object>  list = null;
	    SRTCacheManager srtManager = null;
	    //String filter = request.getParameter("Filter");
	    try {
	      NedssUtils nedssUtils = new NedssUtils();
	      Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
	      SRTCacheManagerHome home = (SRTCacheManagerHome) PortableRemoteObject.narrow(objref, SRTCacheManagerHome.class);
	      srtManager = home.create();
	      if (filter.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
	        //list = (ArrayList<Object> ) srtManager.getOrderedTests(map);
	        list = srtManager.getResultedTests(map);
	      }
	      else if (filter.equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_LOOKUP)) {

	        list = srtManager.getOrderedTests(map);
	        //list = (ArrayList<Object> )srtManager.getResultedTests(map);
	      }
	      else if (filter.equals(NEDSSConstants.NBS_ELEMENTSUID_LOOKUP)) {

	        list = srtManager.getNBSDisplayElementIDs(map);
	      }
	      else if (filter.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

	        list = srtManager.getLabResults(map);
	      }

	      else if (filter.equals(NEDSSConstants.DRUG_LOOKUP)) {

	         list = srtManager.getResultedDrugTests(map);
	       }

	     else if (filter.equals(NEDSSConstants.ORGANISM_LOOKUP)) {

	        list = srtManager.getOrganisms(map);
	      }
	      else if (filter.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)) {

	        list = srtManager.getAnatomicSites(map);
	      }
	      else if (filter.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP)) {

	        list = srtManager.getSpecimenSources(map);
	      }
	      else if (filter.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP)) {

	        list = srtManager.getUnits(map);
	      }
	      else if (filter.equals(NEDSSConstants.INVESTIGATION_REPORTING_LOOKUP)) {

	        list = srtManager.getInvestigationReportingSource(map);
	      }
	      else if (filter.equals(NEDSSConstants.TREATMENTS_LOOKUP)) {

	        list = srtManager.getTreatments(map);
	      }
	      else if (filter.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {

	        list = srtManager.getTreatmentDrugs(map);
	      }

	      else {
	        logger.error("This Situation should not happen! Filter doesn't exist:" + filter);
	      }
	    }
	    catch (CreateException e) {
	    logger.error("PageLoadUtil:CreateException thrown from SRT CachemanageEjb " +e);
		}
		catch (RemoteException e) {
	    logger.error("PageLoadUtil:RemoteException thrown from SRT CachemanageEjb " +e);
		}
	    catch (SRTCacheManagerException e) {
		logger.error("PageLoadUtil:SRTCacheManagerException thrown from SRT CachemanageEjb " +e);
	    }

	    return list;
	  }
	  


	  /**
	   * This method defines the behavior of the code when the entity search criteria is used
	   * to populated the Ordered tests and resuled Tests from the backend.
	   * @param labId :String
	   * @param targetResulted : String
	   * @param targetOrdered : String
	   * @param target : String
	   * @param programAreaCode : String
	   * @param conditionCode : String
	   * @param dropdownInd boolean value
	   * @param request : HttpServletRequest
	   * @param response : HttpServletResponse
	   * @throws IOException
	   */
	  public static void  entitySearchRouteLab(String labId, String targetResulted, String targetOrdered, String target, String conditionCode, String programAreaCode, boolean dropdownInd, HttpServletRequest request, ArrayList<Object> labResultedData) throws
	      IOException {
	    
	    String sLabOptionsResulted = "";
	   // String sLabOptionsOrdered = "";
	    if(dropdownInd)
	    {
	      if (!target.equals("")) {
	        PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request, labResultedData);
	       
	        /////////////////////////////////
	       
	     //   if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
	      //    sLabOptionsOrdered = PageLoadUtil.cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request);

	      //  }
	    }
	  }
	 //  return sLabOptionsResulted;

	  }
	  
	  public static void entitySearchRouteOrdered(String labId, String targetResulted, String targetOrdered, String target, String conditionCode, String programAreaCode, boolean dropdownInd, HttpServletRequest request, ArrayList<Object> labOrderedData) throws
      IOException {
    
   // String sLabOptionsResulted = "";
    String sLabOptionsOrdered = "";
    if(dropdownInd)
    {
      if (!target.equals("")) {
      //  sLabOptionsResulted = PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request);
       
        /////////////////////////////////
       
        if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
          PageLoadUtil.cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request, labOrderedData);

        }
    }
  }
  // return sLabOptionsOrdered;

  }
	  
	  
	/**
	   * This method defines the behavior of the code whenever the program area changes,
	   * the resulted tests and ordered tests needs to be changed
	   * @param programAreaCode :String
	   * @param labId : String
	   * @param targetResulted : String
	   * @param targetOrdered : String
	   * @param target :String
	   * @param request : HttpServletRequest
	   * @param response : HttpServletRequest
	   * @throws IOException
	   */
	  public static void progAreaCodeDropDownsOrdered(String programAreaCode, String labId, String targetResulted, String targetOrdered, String target, HttpServletRequest request, ArrayList<Object> labOrderedData) {
   
	    String sLabOptionsOrdered = "";
	   
	    String conditionCode  =getCorrectParameter(request,"conditionCode");
	    //String sLabOptionsResulted = null;
	  /*  if(conditionCode!= null && conditionCode.trim()!="" && !conditionCode.equalsIgnoreCase("undefined"))
	    {
	       //logger.debug("\n\n\n\nconditionCode is :" + conditionCode);
	       sLabOptionsResulted = PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, null, null, null, request);
	    }
	    else
	      sLabOptionsResulted = PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, null, programAreaCode, null, null, request);
*/
	   
	    if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
	      PageLoadUtil.cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, null, programAreaCode, null, null, request, labOrderedData);
	    }
	  // return sLabOptionsOrdered;
  }
	  
	  
		public static void getResultedTestArray(String target, String targetOrdered, String targetResulted,
				String type, String code, String reportUID, String uid, String programAreaCode, String conditionCode,
				String dropdownCheckerParam, ArrayList<Object> labResultedData,ArrayList<Object> labOrderedData){
		

			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			
		    boolean dropdownChecker = true;
		    String CliaWithConditions = "";
		    //String sLabOptionsResulted = "";
		    //String sLabOptionsOrdered = "";	    

		    if(conditionCode == null)
		    	conditionCode ="";
		    
		    if(programAreaCode == null)
		    	programAreaCode = "";

		    if ( (reportUID != null && reportUID.trim().equalsIgnoreCase("")) && (programAreaCode != null && programAreaCode.trim().equalsIgnoreCase(""))) {
		      dropdownChecker = false;
		    }

		    if (dropdownCheckerParam != null && dropdownCheckerParam.equals("false")) {
		      dropdownChecker = false;
		    }
		    //////////////////while program area changes ///////////////////////////////
		    if ( (type.indexOf("ProgramAreaCode") != -1)) {
		    	PageLoadUtil.progAreaCodeDropDownsOrdered(code, uid, targetResulted, targetOrdered, target, request, labOrderedData);
		    	PageLoadUtil.progAreaCodeDropDownsResulted(code, uid, targetResulted, targetOrdered, target, request, labResultedData);
		    	
		    }
		    //////////////////while ordered test changes////////////////////////////////
		    else if (type.indexOf("OrderedTestChange") != -1) {
		      //orderedTestChanges(code, uid, targetResulted, target, request, response);
		    }
		    //////////entity search route///////////////////////////////////////////////
		    else if (type.indexOf("entitySearchRoute") != -1) {
		      try {
		        String cliaNumber = "";
		        if (target.indexOf("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") > -1) {
		          cliaNumber = NEDSSConstants.DEFAULT;
		        }
		        else {
		          cliaNumber = PageLoadUtil.getCliaValue(uid, request.getSession());
		        }
		        if (cliaNumber == null || cliaNumber == "") {
		          cliaNumber = NEDSSConstants.DEFAULT;
		        }
		        PageLoadUtil.entitySearchRouteLab(cliaNumber, targetResulted, targetOrdered, target, conditionCode, programAreaCode, dropdownChecker, request, labResultedData);
		        PageLoadUtil.entitySearchRouteOrdered(cliaNumber, targetResulted, targetOrdered, target, conditionCode, programAreaCode, dropdownChecker, request, labOrderedData);
			     
		      }
		      catch (Exception ex1) {
		        logger.error("Exception raised while population values via entitySearchRoute");
		      }
		    }
		    ///////////////handle the case under every other condition//////////////////
		    else {
		      try {
		        if ( (type.indexOf("entity-table-Org") != -1)) {
		          HashMap<Object,Object> map = (HashMap<Object,Object>) PageLoadUtil.getOrgValue(code, request.getSession());
		          String uidString = "";
		          if (map!=null && (map.get("UID")!=null || code != null)) {
		        	  if(map.get("UID")==null)
		        		  uidString=code;
		        	  else
		        		  uidString = map.get("UID").toString();
		           // uidValue = ( (Long) map.get("UID")).toString();
		            if (target.indexOf("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") > -1) {
		              CliaWithConditions = NEDSSConstants.DEFAULT;
		            }
		            else {
		              CliaWithConditions = PageLoadUtil.getCliaValue(uidString, request.getSession());
		            }
		            if (CliaWithConditions == null) {
		              CliaWithConditions = NEDSSConstants.DEFAULT;
		            }
		          }
		         // result = map.get("result").toString();
		      //    resultForSpecialCondition = map.get("result").toString();
		        }
		        if (type.indexOf("entity-table-Prov") != -1) {
		          HashMap<Object,Object> map = (HashMap<Object,Object>) PageLoadUtil.getPersonValue(type, code, request.getSession());
		        //  if (map.get("UID") != null) {
		        //    uidValue = ( (Long) map.get("UID")).toString();
		         // }
		         // result = map.get("result").toString();
		        }
		    //    if ( (result.length() == 0) || (result.trim().length() == 0)) {
		     //      dropdownChecker = false;
		    //    }

		      }
		      catch (Exception ex) {
		        logger.error("there was error in getting values from findOrganization or findProvider:" + ex);
		      }

		      // logger.debug("selectOptions is :" + selectOptions.toString());
		      /////////////////////////////////////////////////////////////////////////
		      HttpSession session = request.getSession(true);
		      NBSSecurityObj nbsSecurityObject = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

		      //Map map = null;
		      if ( (type.indexOf("entity-table-Org-ReportingOrganizationUID") != -1)) {//entity-table-Org-ReportingOrganizationUID
		      

		        if (dropdownChecker) {
		          PageLoadUtil.cachedValues(CliaWithConditions, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode,programAreaCode, null, null, request, labResultedData);

		          
		          if (targetOrdered != null && !targetOrdered.equals("")) 
		        	  PageLoadUtil.cachedValues(CliaWithConditions, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request,labOrderedData);
		            
		        }
		
		      }
		    }
			//return sLabOptionsResulted+"^^^^ORDEREDRESULT^^^^"+sLabOptionsOrdered;
			
			
			System.out.println("");
		}
		
		/**
		 * getOrganismList: returns an array of DropDownCodeDT which each of the organism elements
		 * @param organismDropdownList
		 */
		public static void getOrganismList(ArrayList<Object> organismDropdownList){
			
			
			CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
			
			String organismString = cachedDropdownValues.getOrganismList();
			
			if(organismString!=null && !organismString.isEmpty()){
				String[] elements = organismString.split("\\|");
			
				
				if(elements!=null){
					
					 DropDownCodeDT dDownDTBlank = null;
	                 //Adding a blank element
					 dDownDTBlank = new DropDownCodeDT();
					 dDownDTBlank.setKey("");
					 dDownDTBlank.setValue("");
                     organismDropdownList.add(dDownDTBlank);
                     
                     
					for(int i=0;i<elements.length;i++){
						String elem = elements[i];
						String[] keyValue = elem.split("\\$");
						String key = keyValue[0];
						String value = keyValue[1];
						
						
						 DropDownCodeDT dDownDT = null;
		                    
	                     dDownDT = new DropDownCodeDT();
	                     dDownDT.setKey(key);
	                     dDownDT.setValue(value);
	                     organismDropdownList.add(dDownDT);
	                     
					}
				}
			}
			//format from String to dropdown

		}
		
		public static void getDrugTestList(ArrayList<Object> drugTestDropdownList){
			
			
			CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
			
			String drugTestString = cachedDropdownValues.getDrugNames();
			
			if(drugTestString!=null && !drugTestString.isEmpty()){
				String[] elements = drugTestString.split("\\|");
			
				
				if(elements!=null){
					
					 DropDownCodeDT dDownDTBlank = null;
	                 //Adding a blank element
					 dDownDTBlank = new DropDownCodeDT();
					 dDownDTBlank.setKey("");
					 dDownDTBlank.setValue("");
					 drugTestDropdownList.add(dDownDTBlank);
                     
                     
					for(int i=0;i<elements.length;i++){
						String elem = elements[i];
						String[] keyValue = elem.split("\\$");
						String key = keyValue[0];
						String value = keyValue[1];
						
						
						 DropDownCodeDT dDownDT = null;
		                    
	                     dDownDT = new DropDownCodeDT();
	                     dDownDT.setKey(key);
	                     dDownDT.setValue(value);
	                     drugTestDropdownList.add(dDownDT);
	                     
					}
				}
			}
			//format from String to dropdown

		}
		
		
		public static void getCodedResultList(ArrayList<Object> codedResultDropdownList){
			
			
			CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
			
			String codedResultString = cachedDropdownValues.getCodedResultValue();
			
			if(codedResultString!=null && !codedResultString.isEmpty()){
				String[] elements = codedResultString.split("\\|");
			
				
				if(elements!=null){
					
					 DropDownCodeDT dDownDTBlank = null;
	                 //Adding a blank element
					 dDownDTBlank = new DropDownCodeDT();
					 dDownDTBlank.setKey("");
					 dDownDTBlank.setValue("");
					 codedResultDropdownList.add(dDownDTBlank);
                     
                     
					for(int i=0;i<elements.length;i++){
						String elem = elements[i];
						String[] keyValue = elem.split("\\$");
						String key = keyValue[0];
						String value = keyValue[1];
						
						
						 DropDownCodeDT dDownDT = null;
		                    
	                     dDownDT = new DropDownCodeDT();
	                     dDownDT.setKey(key);
	                     dDownDT.setValue(value);
	                     codedResultDropdownList.add(dDownDT);
	                     
					}
				}
			}
			//format from String to dropdown

		}
		
		
	public static void getCodedResultSuscList(ArrayList<Object> codedResultDropdownList){
			
			
			CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
			
			String codedResultString = cachedDropdownValues.getCodedResultValueSusc();
			
			if(codedResultString!=null && !codedResultString.isEmpty()){
				String[] elements = codedResultString.split("\\|");
			
				
				if(elements!=null){
					
					 DropDownCodeDT dDownDTBlank = null;
	                 //Adding a blank element
					 dDownDTBlank = new DropDownCodeDT();
					 dDownDTBlank.setKey("");
					 dDownDTBlank.setValue("");
					 codedResultDropdownList.add(dDownDTBlank);
                     
                     
					for(int i=0;i<elements.length;i++){
						String elem = elements[i];
						String[] keyValue = elem.split("\\$");
						String key = keyValue[0];
						String value = keyValue[1];
						
						
						 DropDownCodeDT dDownDT = null;
		                    
	                     dDownDT = new DropDownCodeDT();
	                     dDownDT.setKey(key);
	                     dDownDT.setValue(value);
	                     codedResultDropdownList.add(dDownDT);
	                     
					}
				}
			}
			//format from String to dropdown

		}
	

	
	

	public static void getResultMethodSuscListELR(ArrayList<Object> resultMethodDropdownList){
		
		
		CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
		
		String resultMethodString = cachedDropdownValues.getResultMethodValueSusc();
		
		if(resultMethodString!=null && !resultMethodString.isEmpty()){
			String[] elements = resultMethodString.split("\\|");
		
			
			if(elements!=null){
				
				 DropDownCodeDT dDownDTBlank = null;
                 //Adding a blank element
				 dDownDTBlank = new DropDownCodeDT();
				 dDownDTBlank.setKey("");
				 dDownDTBlank.setValue("");
				 resultMethodDropdownList.add(dDownDTBlank);
                 
                 
				for(int i=0;i<elements.length;i++){
					String elem = elements[i];
					String[] keyValue = elem.split("\\$");
					String key = keyValue[0];
					String value = keyValue[1];
					
					
					 DropDownCodeDT dDownDT = null;
	                    
                     dDownDT = new DropDownCodeDT();
                     dDownDT.setKey(key);
                     dDownDT.setValue(value);
                     resultMethodDropdownList.add(dDownDT);
                     
				}
			}
		}
		//format from String to dropdown

	}
	
	
	
	public static void getResultMethodSuscList(ArrayList<Object> codedResultDropdownList){
		
		
		CachedDropDownValues cachedDropdownValues = new CachedDropDownValues();
		
		String codedResultString = cachedDropdownValues.getCodedValues("OBS_METH_SUSC");
		
		if(codedResultString!=null && !codedResultString.isEmpty()){
			String[] elements = codedResultString.split("\\|");
		
			
			if(elements!=null){
				
				 DropDownCodeDT dDownDTBlank = null;
                 //Adding a blank element
				 dDownDTBlank = new DropDownCodeDT();
				 dDownDTBlank.setKey("");
				 dDownDTBlank.setValue("");
				 codedResultDropdownList.add(dDownDTBlank);
                 
                 
				for(int i=0;i<elements.length;i++){
					String elem = elements[i];
					String[] keyValue = elem.split("\\$");
					String key = keyValue[0];
					String value = keyValue[1];
					
					
					 DropDownCodeDT dDownDT = null;
	                    
                     dDownDT = new DropDownCodeDT();
                     dDownDT.setKey(key);
                     dDownDT.setValue(value);
                     codedResultDropdownList.add(dDownDT);
                     
				}
			}
		}
		//format from String to dropdown

	}

	
		
		
		/**
		 * organisnRequiredIndicator: this method has been copied from NedssOrganismLookupServlet
		 * @param testNameCondition
		 * @param labClia
		 * @param labUid
		 * @param progAreaCd
		 * @param session
		 * @return
		 */
		 public static boolean organismRequiredIndicator(String testNameCondition, String labClia, Long labUid, String progAreaCd, HttpSession session) {
			    MainSessionCommand msCommand = null;
			    boolean labIndicator = false;

			    

			    //civil00012111 - BB - the method called from this doesn't use CLIA or the Lab UID, but in the future
			    //if it does and the CLIA and lab uid are not available, then the DEFAULT clia should be used
			    if ( labUid == null && labClia == null )
			      labClia = NEDSSConstants.DEFAULT;

			    //civil00012111 - BB - reverse order, check for null labUid first - if it is null, then
			    //CLIA cannot be null as last conditional would have caught that and set CLIA to DEFAULT.
			    if ( labUid != null || labClia.equalsIgnoreCase(NEDSSConstants.DEFAULT) ) {
			      try {
			        logger.debug("observationUID inside organisnRequiredIndicator method is: " + labUid);

			        String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			        String sMethod = "getOrganismReqdIndicatorForResultedTest";
			        Object[] oParams = new Object[] {testNameCondition, labClia, labUid, progAreaCd};
			        MainSessionHolder holder = new MainSessionHolder();
			        msCommand = holder.getMainSessionCommand(session);

			        ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			        labIndicator = ( (Boolean) arr.get(0)).booleanValue();
			      }
			      catch (Exception ex) {

			        if (session == null) {
			          logger.debug("Error: no session, please login");

			        }
			        logger.fatal("getLabResultProxyVO: ", ex);
			      }
			    }
			    else {
			      logger.error("NedssOrganismLookupServlet: observationUID is null, and labId is not DEFAULT");
			      return labIndicator;
			    }
			    return labIndicator;
			  }

	  public static void progAreaCodeDropDownsResulted(String programAreaCode, String labId, String targetResulted, String targetOrdered, String target, HttpServletRequest request, ArrayList<Object> labResultedData) {
		   
		   // String sLabOptionsOrdered = "";
		   
		    String conditionCode  =getCorrectParameter(request,"conditionCode");
		    String sLabOptionsResulted = null;
		    if(conditionCode!= null && conditionCode.trim()!="" && !conditionCode.equalsIgnoreCase("undefined"))
		    {
		       //logger.debug("\n\n\n\nconditionCode is :" + conditionCode);
		       PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, null, null, null, request, labResultedData);
		    }
		    else
		      PageLoadUtil.cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, null, programAreaCode, null, null, request, labResultedData);

		   
		  //  if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
		 //     sLabOptionsOrdered = PageLoadUtil.cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, null, programAreaCode, null, null, request);
		//    }
		 //   return labResultedData;
	  }
	  
	
	/**
	   *
	   * @param code : String
	   * @param session : HttpSession
	   * @return Map
	   * @throws Exception
	   */
	  public static Map<Object,Object> getOrgValue(String code, HttpSession session) throws Exception {
	    Map<Object,Object> returnMap = new HashMap<Object,Object>();
	    Long organizationUID = null;
	    Integer versionCtrlNbr= null;
	    //String CliaWithConditions = "";
	    StringBuffer result = new StringBuffer("");
	    OrganizationSearchVO orgSearchVO = new OrganizationSearchVO();
	    orgSearchVO.setRootExtensionTxt(code);
	    orgSearchVO.setTypeCd("QEC");
	    ArrayList<?> alist = null;
	    MainSessionCommand msCommand = null;
	    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    String sMethod = "findOrganization";
	    Object[] oParams = new Object[] {orgSearchVO, new Integer(propertyUtil.getNumberOfRows()), new Integer(0)};
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    alist = (ArrayList<?> ) arr.get(0);
	   Iterator<?>  it = alist.iterator();
	    if (alist != null) {
	      while (it.hasNext()) {
	        DisplayOrganizationList displayOrganizationList = (DisplayOrganizationList) it.next();
	        if (displayOrganizationList != null) {
	          ArrayList<Object> list = displayOrganizationList.getList();
	          if (list != null) {
	           Iterator<Object>  orgSearchResultIt = list.iterator();
	            while (orgSearchResultIt.hasNext()) {
	              OrganizationSrchResultVO srchResultVO = (OrganizationSrchResultVO) orgSearchResultIt.next();
	              versionCtrlNbr= srchResultVO.getVersionCtrlNbr();
	              organizationUID = srchResultVO.getOrganizationUID();
	              if (srchResultVO.getOrganizationNameColl() != null) {
	               Iterator<Object>  orgNameIt = srchResultVO.getOrganizationNameColl().iterator();
	                while (orgNameIt.hasNext()) {
	                  OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.next();
	             //     result.append(orgName.getNmTxt()==null?"":orgName.getNmTxt());
	                }
	              }
	        

	              
	            }
	          }
	        }
	      }
	    }
	    returnMap.put("UID", organizationUID);
	    returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
		
	  //  returnMap.put("result", result.toString());

	    return returnMap;

	  }
	/**
	   * This method gets the laboratory_id for for given organization uid(uid).
	   * @param uid : String value representing the organization uid.
	   * @param session : HttpSession
	   * @return : String
	   */
	  public static String getCliaValue(String uid, HttpSession session) {
	    Long orgUIDLong = new Long(uid);
	    MainSessionCommand msCommand = null;
	    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    String sMethod = "organizationCLIALookup";
	    Object[] oParams = new Object[] {orgUIDLong};

	    String CliaNumber = null;
	    try {
	      MainSessionHolder holder = new MainSessionHolder();
	      msCommand = holder.getMainSessionCommand(session);
	      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	      CliaNumber = (String) arr.get(0);
	    }
	    catch (Exception ex) {
	      logger.error("NedssCodeLookupServlet:There was some error in getting clia number from the database"+ ex);
	    }
	    return CliaNumber;

	  }
	  
	  
	  
	  /**
	   * For a given Quick entry code, gets the person uid and result from the backend.
	   * Type defines the different types of person that returns different types of data
	   * from the backend.
	   * @param type : String
	   * @param code : String
	   * @param session : HttpSession
	   * @return : Map
	   * @throws Exception
	   */
	  public static Map<Object,Object> getPersonValue(String type, String code, HttpSession session) throws Exception {
	    Map<Object,Object> returnMap = new HashMap<Object,Object>();
	    Long personUID = null;
	    Integer versionCtrlNbr= null;
	    ProviderSearchVO provSearchVO = new ProviderSearchVO();
	    provSearchVO.setRootExtensionTxt(code);
	    provSearchVO.setTypeCd("QEC");
	    ArrayList<?> alist = null;
	    MainSessionCommand msCommand = null;
	    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    String sMethod = "findProvider";
	    Object[] oParams = new Object[] {provSearchVO, new Integer(propertyUtil.getNumberOfRows()), new Integer(0)};
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    alist = (ArrayList<?> ) arr.get(0);
	   Iterator<?>  it = alist.iterator();
	    if (alist != null) {
	      while (it.hasNext()) {
	        DisplayPersonList displayPersonList = (DisplayPersonList) it.next();
	        if (displayPersonList != null) {
	          ArrayList<Object> list = displayPersonList.getList();
	          if (list != null) {
	           Iterator<Object>  provSearchResultIt = list.iterator();
	            while (provSearchResultIt.hasNext()) {
	              ProviderSrchResultVO srchResultVO = (ProviderSrchResultVO) provSearchResultIt.next();
	              personUID = srchResultVO.getPersonUID();
	              versionCtrlNbr= srchResultVO.getVersionCtrlNbr();
	            }
	          }
	        }
	      }
	    }
	    
	    returnMap.put("UID", personUID);
	    returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
		
	    //returnMap.put("result", result.toString());
	    return returnMap;
	  }

	  
	  /**
	   * getCorrectParameter(): new function to resolve issue with other browsers. The paramname is amp;paramname
	   * 
	   */
	  
	  public static String getCorrectParameter(HttpServletRequest request, String parameter){
		  
		  String param = request.getParameter(parameter);
		  
		  if(param == null)
			  param=request.getParameter("amp;"+parameter);
		  
		  return param;
		    
	  }
	
	  
	  /**
	   * getSusceptibilitySubForm: returns the isolate subFormPage from the session using the key received as a parameter
	   * @param key
	   * @return
	   */
	  public static PageSubForm getSusceptibilitySubForm(String key){
		  
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
    	HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
		  PageSubForm subForm = null;
		  String susKey ="NBS_LAB222Button_"+key;

		if(subFormHashMap!=null)
			subForm = (PageSubForm)subFormHashMap.get(susKey);
					
		return subForm;
	  }

	  /**
	   * getIsolateSubForm: returns the isolate subFormPage from the session using the key received as a parameter
	   * @param key
	   * @return
	   */
	  public static PageSubForm getIsolateSubForm(String key){

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();

		HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
			  
		PageSubForm subForm = null;
		String susKey ="NBS_LAB329Button_"+key;

		if(subFormHashMap!=null)
			subForm = (PageSubForm)subFormHashMap.get(susKey);
					
		return subForm;
	  }
	  
	  /**
	   * getValueFromCodeValuePair: when this method receives strings like Y$$Yes (code$$value), it will return only the value, removing the code and the separator $$
	   * @param codeValue
	   * @return
	   */
	  public static String getValueFromCodeValuePair(String codeValue){
		  
		  
		  if(codeValue!=null && !codeValue.equalsIgnoreCase("")){
				if(codeValue.indexOf("$$")!=-1)
					codeValue = codeValue.substring(codeValue.indexOf("$$")+2);
				
			}
		  
		  
		  return codeValue;
	  }
	  
	  
	  /**
	   * getCodeFromCodeValuePair: when this method receives strings like Y$$Yes (code$$value), it will return only the code, removing the value and the separator $$
	   * @param codeValue
	   * @return
	   */
	  
	  public static String getCodeFromCodeValuePair(String codeValue){
		  
		  
		  if(codeValue!=null && !codeValue.equalsIgnoreCase("")){
				if(codeValue.indexOf("$$")!=-1)
					codeValue = codeValue.substring(0,codeValue.indexOf("$$"));
				
			}
		  
		  
		  return codeValue;
	  }
	  
	  /**
		 * getSusceptibilityData: returns the susceptibility data associated to the rowKey that will be appended to the organism cell in Resulted Test Batch Entry in Lab Reports.
		 * It will show as Show Reflex Test Results (link) and once the mouse is over, it will show the Drug - Interpretation, and the numeric value, units, coded result and result method.
		 * @param rowKkey
		 * @return
		 */
	  
		public static String getSusceptibilityData(String rowKey){
			

		   String  BOLD_START                = "<b>";
		   String  BOLD_END                  = "</b>";
		   String  UL_START                  = "<ul style='list-style-type:disc;'>";
		   String  UL_END                    = "</ul>";
		   String  BR                        = "<BR>";
		   String  SINGLE_SPACE              = "&nbsp;";
		   String  LI_START                  = "<li style='margin-left:-15px'>";
		   String  LI_END                    = "</li>";
		   String  COLON                     = ":";

		   PageSubForm susceptSubForm = PageLoadUtil.getSusceptibilitySubForm(rowKey);
		
		   String drugName = "";
		   String interpretation = "";
		   String numericResultValue1 = "";
		   String susceptData = "";
		   String units = "";
		   String codedResult = "";
		   String resultMethod = "";
		   String lowRange = "";
		   String highRange = "";
		   String status = "";
		   String textResult = "";
		   
		   
		  	if(susceptSubForm!=null){
		  		
		  		
		  		if(susceptSubForm.getBatchEntryMap()!=null && susceptSubForm.getBatchEntryMap().get("NBS_UI_2")!=null){
		  			ArrayList<BatchEntry> batchEntry = susceptSubForm.getBatchEntryMap().get("NBS_UI_2");
		  			int countSuscept = batchEntry.size();
		  		
		  			 StringBuffer buf1 = new StringBuffer("");
		  			 if(countSuscept>0){
			  			 buf1.append("<br><div onmouseout='showHideReflex(this)' onmouseover='showHideReflex(this)'><a style='cursor:pointer'>Show Reflex Tests</a><br><br><div style='display:none'>");
			  	      
			  			 buf1.append(UL_START);
			  			 
			  			for(int i=0; i<countSuscept; i++){
			  				drugName = batchEntry.get(i).getAnswerMap().get("NBS_LAB110");
			  				interpretation = batchEntry.get(i).getAnswerMap().get("NBS378");
			  				numericResultValue1 = batchEntry.get(i).getAnswerMap().get("NBS369");
			  				units= batchEntry.get(i).getAnswerMap().get("NBS372");
			  				codedResult= batchEntry.get(i).getAnswerMap().get("NBS367");
			  				resultMethod= batchEntry.get(i).getAnswerMap().get("NBS377");
			  				lowRange= batchEntry.get(i).getAnswerMap().get("NBS373");
			  				highRange= batchEntry.get(i).getAnswerMap().get("NBS374");
			  				status = batchEntry.get(i).getAnswerMap().get("NBS376");
			  				textResult = batchEntry.get(i).getAnswerMap().get("nbs365");
			  				
			  				drugName = PageLoadUtil.getValueFromCodeValuePair (drugName);
			  				units = PageLoadUtil.getValueFromCodeValuePair (units);
			  				codedResult = PageLoadUtil.getValueFromCodeValuePair (codedResult);
			  				resultMethod = PageLoadUtil.getCodeFromCodeValuePair (resultMethod);
			  				interpretation = PageLoadUtil.getValueFromCodeValuePair (interpretation);
			  				lowRange = PageLoadUtil.getValueFromCodeValuePair (lowRange);
			  				highRange = PageLoadUtil.getValueFromCodeValuePair (highRange);
			  				status = PageLoadUtil.getValueFromCodeValuePair (status);
			  				textResult = PageLoadUtil.getValueFromCodeValuePair (textResult);
			  				
			  				if(drugName!=null && drugName.indexOf("(")>0)
			  					drugName = drugName.substring(0, drugName.indexOf("(")-1);

			  				buf1.append(LI_START);
		  		             buf1.append(BOLD_START);
		  		             buf1.append(drugName == null ? "" : drugName);//.append(SINGLE_SPACE);
		  		             buf1.append(BOLD_END);
				  		           
			
						  	                 
		  					if (resultMethod != null && !resultMethod.equalsIgnoreCase("")){
				  	           
		  				    	//Result method
						  		 buf1.append(BOLD_START);
				  		         buf1.append("(").append(resultMethod).append(")");
				  		         buf1.append(BOLD_END);
				  	   
		  					}    
						  	                 
				  			
				  			 
				  			 //Numeric result
				  			 boolean isResults = false;
				  			 
			  					if (numericResultValue1 != null && !numericResultValue1.equalsIgnoreCase("")){
			  						isResults = true;
			  						 buf1.append(COLON);	
			  			  		         buf1.append(numericResultValue1 == null ? "" : SINGLE_SPACE+numericResultValue1);
			  			  		     	 buf1.append(units == null ? "" : SINGLE_SPACE+units);
			  			  		     	 buf1.append(SINGLE_SPACE);
						  		         
					  	          
			  					} 
			  					
			  					//Reference Range
			  					
			  					if(lowRange!=null && lowRange!="" && highRange!=null && highRange!=""){
			  						
						  		       buf1.append("(").append(lowRange == null ? "" :lowRange)
                                       .append(highRange == null ? "" : "-" + highRange).append(")");
			  					}
			  					
			  					
			  					if (codedResult != null && !codedResult.equalsIgnoreCase("")){
			  						isResults = true;
			  						 buf1.append(COLON);
		  			  		         buf1.append(codedResult == null ? "" : SINGLE_SPACE+codedResult+SINGLE_SPACE);
					  		       
			  					} 
			  					
			  					
			  					if (textResult != null && !textResult.equalsIgnoreCase("")){
			  						isResults = true;
			  						 buf1.append(COLON);
		  			  		         buf1.append(textResult == null ? "" : SINGLE_SPACE+textResult+SINGLE_SPACE);
					  		        
			  					} 
			  					
			  				
						  		   /*  if (status != null)
			                                buf1.append(" - (").append(status).append(")").append(COLON);*/
						  		     
					  	                 
						  		//   buf1.append(BR);//Added
					  	           
						  		   if(interpretation!=null && !interpretation.equalsIgnoreCase("")){
					  		             if (interpretation != null)
					  		             {
					  		            	 if(isResults)
					  		            		buf1.append("- ");
					  		            	 else
					  		            		 buf1.append(": ");
					  		            	 buf1.append("(");
					  		                 buf1.append(interpretation == null ? "" : interpretation);
					  		                 buf1.append(")");
					  		             }
					  		           }
					  		             
					  		           buf1.append(BR);//Added
					  		           buf1.append(LI_END);
			  						
			  					
			  				
			  				
			  			}
			  			
			  			
			         buf1.append(UL_END);
			         
			         
			         buf1.append("</div></div>");
		  		 }
		         susceptData = buf1.toString();
		  		}
		  		
	         
		  	}
			return susceptData;
			
			
		}
		

		/**
		 * deleteSubFormFromSession: delete the subFormPage from session which key is the key received as a parameter
		 * @param key
		 * @throws NEDSSAppException
		 */
		
		public static void deleteSubFormFromSession(String key) throws NEDSSAppException {
			
			WebContext ctx = WebContextFactory.get();
		    HttpServletRequest req = ctx.getHttpServletRequest();
			HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) req.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
			
			String susKey ="NBS_LAB222Button_"+key;
			String isoKey ="NBS_LAB329Button_"+key;
			
		
			if(subFormHashMap!=null){
				if(subFormHashMap.get(susKey)!=null)
					subFormHashMap.remove(susKey);
				if(subFormHashMap.get(isoKey)!=null)
					subFormHashMap.remove(isoKey);
			}

		}
		
		
		public static String extractAddressAsString(PersonVO personVO) throws NEDSSAppException{
			StringBuffer homeAddress = new StringBuffer("");
			try{
				Collection<Object> entityCollections = (ArrayList<Object>) personVO.getTheEntityLocatorParticipationDTCollection();
				
				if(null == entityCollections || entityCollections.isEmpty()) return "";
				
				if(entityCollections.size()>0){
					 NedssUtils nUtil = new NedssUtils();
		        	 nUtil.sortObjectByColumn("getAsOfDate", entityCollections, false);
				}
				
		        Iterator<Object> locatorIt = entityCollections.iterator();
		        while (locatorIt.hasNext())
		        {
	            	EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) locatorIt.next();
	                if ("H".equalsIgnoreCase(entityLocatorDT.getCd())
	                        && "PST".equalsIgnoreCase(entityLocatorDT.getClassCd())
	                        && "ACTIVE".equalsIgnoreCase(entityLocatorDT.getRecordStatusCd())
	                        && "H".equalsIgnoreCase(entityLocatorDT.getUseCd()))
	                {
	                    PostalLocatorDT postalDT = entityLocatorDT.getThePostalLocatorDT();
	                    if (postalDT != null)
	                    {
	                    	CachedDropDownValues cache = new CachedDropDownValues();
                            homeAddress.append(postalDT.getStreetAddr1() == null ? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(postalDT.getStreetAddr1()));
                            homeAddress.append(postalDT.getStreetAddr2() == null ? HTMLEncoder.encodeHtml("") : " "+ HTMLEncoder.encodeHtml(postalDT.getStreetAddr2()));
                            if(homeAddress.length()>0 && postalDT.getCityDescTxt()!=null)
                            	homeAddress.append(", ");
                            homeAddress.append(postalDT.getCityDescTxt() == null ? HTMLEncoder.encodeHtml("") : " "+ HTMLEncoder.encodeHtml(postalDT.getCityDescTxt()));
                            if(homeAddress.length()>0 && postalDT.getStateCd()!=null)
                            	homeAddress.append(", ");
                            homeAddress.append(postalDT.getStateCd() == null ? HTMLEncoder.encodeHtml("") : " "+ HTMLEncoder.encodeHtml(cache.getStateAbbreviationByCode(postalDT.getStateCd())));
                            homeAddress.append(postalDT.getZipCd() == null ? HTMLEncoder.encodeHtml("") : " " + HTMLEncoder.encodeHtml(postalDT.getZipCd()));
                            break;
                        }
	                }
		        }
			}catch(Exception ex){
				logger.error("Exception : "+ ex.getMessage(), ex);
				throw new NEDSSAppException(ex.getMessage(),ex);
			}
			return homeAddress.toString();
		}
		
	public static void prepareDisplayOrigDocList(
			Collection<Object> origDocList, String receiveDate, HttpServletRequest request)
			throws NEDSSAppException {
		try {
			if (origDocList == null)
				return;
			int j = origDocList.size();
			for (Object edxDT : origDocList) {
				String viewLink = "<a href=\"javascript:openELRViewer('"
						+ ((EDXDocumentDT) edxDT).getEDXDocumentUid()
						+ "','"
						+ receiveDate
						+ "')\">"
						+ StringUtils.formatDate(((EDXDocumentDT) edxDT)
								.getAddTime())
						+ " "
						+ StringUtils
								.formatDatewithHrMin(((EDXDocumentDT) edxDT)
										.getAddTime()) + "</a>";
				((EDXDocumentDT) edxDT).setViewLink(viewLink);
				((EDXDocumentDT) edxDT).setVersionNbr(j-- + "");
				request.setAttribute("origDocList", origDocList);
			}
		} catch (Exception e) {
			String message = "error while preparing display collection for original documents";
			e.printStackTrace();
			throw new NEDSSAppException(message + " " + e.getMessage());
		}

	}
		
	  }
