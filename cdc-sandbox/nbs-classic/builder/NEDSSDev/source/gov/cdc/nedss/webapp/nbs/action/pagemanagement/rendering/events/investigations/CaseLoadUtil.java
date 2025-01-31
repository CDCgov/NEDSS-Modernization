package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules.PageRulesGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.NavigatorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class to render CREATE / VIEW / EDIT 'INVESTIGATION' Type Pages added from Builder
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * CaseLoadUtil.java
 * Jan 20, 2010
 * @version 1.0
 */
public class CaseLoadUtil {

	static final LogUtils logger = new LogUtils(CaseLoadUtil.class.getName());
	public static final String ACTION_PARAMETER	= "method";
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static Map<Object,Object> questionMap;
	public static Map<Object,Object> questionKeyMap;
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	@SuppressWarnings("unchecked")
	public static void loadQuestions(String invFormCd){

		if(QuestionsCache.getDMBQuestionMap()!=null)
			questionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(invFormCd);
		else
			questionMap = new HashMap<Object,Object>();

	}

	@SuppressWarnings("unchecked")
	public static void loadQuestionKeys(String invFormCd) {

		questionKeyMap = new HashMap<Object,Object>();
		Iterator iter = questionMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
			questionKeyMap.put(metaData.getNbsQuestionUid(), key);
		}
	}
	/**
	 * This method retrieves the Patient Revision Information on the create load instance from the backend,
	 * constructs and returns a PageClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PageClientVO.PageClientVO
	 */
	public static void createLoadUtil(PageForm form, HttpServletRequest request) throws Exception {

		try {
			form.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			form.getAttributeMap().clear();
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			PageClientVO clientVO = new PageClientVO();
			// loadQuestionMap
			String invFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);
			loadQuestions(form.getPageFormCd());
			form.setPageFormCd(invFormCd);
			form.getAttributeMap().put("header", "Create Investigation");
			form.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
			form.getAttributeMap().put("Submit", "Submit");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

			Long mprUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
			PersonVO personVO = findMasterPatientRecord(mprUid, session);
			
			ClientUtil.setPatientInformation(form.getActionMode(), personVO, clientVO, request, form.getPageFormCd());
			form.setPageClientVO(clientVO);
			setInvInfoForCreate(form, request.getSession());
			String currentTask = setContextForCreate(personVO,request,form);
			//Uncomment Populating LabMorb as required (and Configured)
			//populateLabMorbValues(form, currentTask, request);
			//setUpdatedValues(form, request);
			//populateContactTracing(form, request);
			setJurisdictionForCreate(form, nbsSecurityObj, session);

			PageRulesGenerator reUtils = new PageRulesGenerator();
			form.setFormFieldMap(reUtils.initiateForm(questionMap,  (BaseForm) form, (ClientVO) form.getPageClientVO()));
			//Builds a list of multiselects for the js
			//retrieveMultiSelectQIds(form);
			//setCommonSecurityForCreateEditLoad(form, nbsSecurityObj, request,null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Create " +  form.getPageFormCd() +  " Page: "+ e.toString());
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
	  public static PageProxyVO viewLoadUtil(PageForm form, HttpServletRequest request) throws Exception {

		  PageProxyVO proxyVO = null;
		  try {
			  form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			  form.setFormFieldMap(new HashMap<Object,Object>());
			  form.setErrorTabs(new String[0]);
			  form.getStateList();
			  form.getCountryList();
			  if(request.getAttribute("mode") == null)
				  form.getAttributeMap().clear();
			  HttpSession session = request.getSession();	
			  String invFormCd = form.getPageFormCd();
			  loadQuestions(invFormCd);
			  loadQuestionKeys(invFormCd);
			  String contextAction = request.getParameter("ContextAction");
			  if(request.getParameter("mode")==null)
				  request.getParameterMap().clear();
			  String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
			  request.setAttribute("DSInvUid", sPublicHealthCaseUID);
			 
			  
			  proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());
			  Collection<Object>   notificationSummaryVOCollection=((PageActProxyVO)proxyVO).getTheNotificationSummaryVOCollection();
			 Iterator<Object>  iterNot = notificationSummaryVOCollection.iterator();
    		  while(iterNot.hasNext()){
				  NotificationSummaryVO notificationSummaryVO = (NotificationSummaryVO)iterNot.next();
				  if(notificationSummaryVO.isHistory!=null && !notificationSummaryVO.isHistory.equals("T") &&
						  !(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) || notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED)))
				  {
					  form.getAttributeMap().put("NotificationExists", "true");
				  }
				  
    		  }
			  //Load common PAT, INV answers and put it in answerMap for UI & Rules to work
			  setCommonAnswersForViewEdit(form, proxyVO, request);
			  //Pam Specific Answers
			  setMSelectCBoxAnswersForViewEdit(form, updateMapWithQIds(((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap()));
			  //set PageProxyVO to ClientVO
			  form.getPageClientVO().setOldPageProxyVO(proxyVO);
			  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			  Map<Object, Object> map =setContextForView(form, contextAction,request, request.getSession());
			  populatePageAssocations(proxyVO, sPublicHealthCaseUID,map, request,form);
			  setCommonSecurityForView(form, proxyVO, nbsSecurityObj,request);
			  
			  // set the notification status details in request
			  ArrayList<Object> nsColl = (ArrayList<Object> ) ((PageActProxyVO)proxyVO).getTheNotificationSummaryVOCollection();
			 Iterator<Object>  nsCollIter = nsColl.iterator();
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
				  request.setAttribute("notificationStatus", latestStatusCode);
				  request.setAttribute("notificationDate", StringUtils.formatDate(latestStatusTime));
				  
			  }
			  
			  fireRulesOnViewLoad(form);
			  
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading View " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	  }

	/**
	 * editLoadUtil method retrieves the PageProxyVO from the EJB and sets to PageClientVO, attribute of PageForm
	 * @param form
	 * @param request
	 */
	  public static PageProxyVO editLoadUtil(PageForm form, HttpServletRequest request) throws Exception {

		  PageProxyVO proxyVO = null;

		  try {
			form.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			  form.getAttributeMap().put("ReadOnlyJursdiction", "ReadOnlyJursdiction");
			  form.setFormFieldMap(new HashMap<Object,Object>());
			  form.setErrorTabs(new String[0]);
			  HttpSession session = request.getSession();
			  String invFormCd = form.getPageFormCd();
			  loadQuestions(invFormCd);
			  loadQuestionKeys(invFormCd);
			  form.setPageFormCd(invFormCd);
			  form.getAttributeMap().put("header", "Edit Investigation");
			  form.getAttributeMap().put(ACTION_PARAMETER, "editSubmit");
			  String sPublicHealthCaseUID = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
			  proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());
			  String noReqNotifCheck =(String)form.getAttributeMap().get(PageConstants.NO_REQ_FOR_NOTIF_CHECK);
			  if(!(noReqNotifCheck != null && noReqNotifCheck.equals("false")))
				  setNNDIndicator(proxyVO,form);
			   //DEM, INV specific common answers back to pamclientvo to support UI / Rules
			  setCommonAnswersForViewEdit(form, proxyVO, request);
			  //Pam Specific Answers
			  setMSelectCBoxAnswersForViewEdit(form, updateMapWithQIds(((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap()));
			  //set PageProxyVO to ClientVO
			  form.getPageClientVO().setOldPageProxyVO(proxyVO);
			  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			  //Supplemental Info
			  Map<Object, Object> map = new HashMap<Object, Object>();
			  populatePageAssocations(proxyVO, sPublicHealthCaseUID, map, request, form);
			  String sCurrentTask = setContextForEdit(form, request, request.getSession());
			  form.getAttributeMap().put("sCurrentTask", sCurrentTask);
			  setUpdatedValues(form, request);
			  setJurisdictionForEdit(form, nbsSecurityObj, proxyVO);
			  
			  // set the notification status details in request
			  ArrayList<Object> nsColl = (ArrayList<Object> ) ((PageActProxyVO)proxyVO).getTheNotificationSummaryVOCollection();
			 Iterator<Object>  nsCollIter = nsColl.iterator();
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
						  if (sVO.getRecordStatusTime().after(latestStatusTime)) {
							  latestStatusTime = sVO.getRecordStatusTime();
							  latestStatusCode = sVO.getRecordStatusCd();
						  }
						  
					  }
				  }
				  logger.info("latestStatusCode = " + latestStatusTime + "; latestStatusTime = " + StringUtils.formatDate(latestStatusTime));
				  request.setAttribute("notificationStatus", latestStatusCode);
				  request.setAttribute("notificationDate", StringUtils.formatDate(latestStatusTime));
			  }
			  setCommonSecurityForCreateEditLoad(form, nbsSecurityObj, request,proxyVO);

			  //_loadEntities(form, proxyVO, request);
			  setMSelectCBoxAnswersForViewEdit(form, updateMapWithQIds(((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap()));		  
			  fireRulesOnEditLoad(form, request);
			  form.setPageTitle(NBSPageConstants.EDIT_VARICELLA, request);
			  return proxyVO;			  
			  
		  } catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Edit " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		  }
	  }

	  /**
	   * Fires the rules on View Page. Called by appropriate PAMs
	   * @param form
	   */
	  public static void fireRulesOnViewLoad(PageForm form) {

		  PageRulesGenerator reUtils = new PageRulesGenerator();
		  form.setFormFieldMap(reUtils.initiateForm(questionMap,  (BaseForm) form, (ClientVO) form.getPageClientVO()));
	  }

	  /**
	   * Fires the rules on Edit Page. Called by appropriate PAMs
	   * @param form
	   */
	  public static void fireRulesOnEditLoad(PageForm form, HttpServletRequest req) {

		  if(form.getAttributeMap().containsKey(PageConstants.REQ_FOR_NOTIF) && (form.getAttributeMap().containsKey(PageConstants.NO_REQ_FOR_NOTIF_CHECK)&&form.getAttributeMap().get(PageConstants.NO_REQ_FOR_NOTIF_CHECK).equals("false"))){
			   //Also retrieve the NotifReqMap from session and put it in attributeMap
			   Map<Object,Object> notifReqMap = req.getSession().getAttribute("NotifReqMap") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) req.getSession().getAttribute("NotifReqMap");
			   form.getAttributeMap().put("NotifReqMap", notifReqMap);
			   req.getSession().removeAttribute("NotifReqMap");
		  }

		  PageRulesGenerator reUtils = new PageRulesGenerator();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(questionMap,  (BaseForm) form, (ClientVO) form.getPageClientVO());

		  form.setFormFieldMap(formFieldMap);
		  Map<Object,Object> errorTabs = new HashMap<Object,Object>();

			if (formFieldMap != null && formFieldMap.size() > 0) {
				Collection<Object>  errorColl = formFieldMap.values();
				Iterator<Object> ite = errorColl.iterator();
				ArrayList<Object> errors = new ArrayList<Object> ();
				while (ite.hasNext()) {
					FormField fField = (FormField) ite.next();
					if (fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
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
	  
	  
	  /**
	   * Fires the rules on Create Page. Called by appropriate PAMs
	   * @param form
	   */
	  public static void fireRulesOnCreateLoad(PageForm form, HttpServletRequest req) {

		  PageRulesGenerator reUtils = new PageRulesGenerator();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(questionMap,  (BaseForm) form, (ClientVO) form.getPageClientVO());

		  form.setFormFieldMap(formFieldMap);
	  }


	  /**
	   * setCommonAnswersForViewEdit loads all the DEMs, INVs common across all PAMs
	   * collects them from multiple tables part of PageProxyVO and stuffs in AnswerMap to support UI / Rules
	   * @param form
	   * @param proxyVO
	   * @param request
	   */
	  public static void setCommonAnswersForViewEdit(PageForm form, PageProxyVO proxyVO, HttpServletRequest request) {
		  PageClientVO clientVO = new PageClientVO();
		  PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);
		  ClientUtil.setPatientInformation(form.getActionMode(), personVO, clientVO, request,form.getPageFormCd());
			form.setPageClientVO(clientVO);
			//loadCommonInvestigationAnswers
		  setInvestigationInformationOnForm(form, proxyVO);
	  }

	  public static void setInvestigationInformationOnForm(PageForm form, PageProxyVO proxyVO) {

		  PublicHealthCaseDT dt = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT();
		  PageClientVO clientVO = form.getPageClientVO();
		  clientVO.setAnswer(PageConstants.JURISDICTION, dt.getJurisdictionCd());
		  clientVO.setAnswer(PageConstants.PROGRAM_AREA, dt.getProgAreaCd());
		  clientVO.setAnswer(PageConstants.INV_STATUS_CD, dt.getInvestigationStatusCd());
		  clientVO.setAnswer(PageConstants.DATE_REPORTED, dt.getRptFormCmpltTime_s());
		  clientVO.setAnswer(PageConstants.INV_START_DATE, dt.getActivityFromTime_s());
		  clientVO.setAnswer(PageConstants.MMWR_WEEK, dt.getMmwrWeek());
		  clientVO.setAnswer(PageConstants.MMWR_YEAR, dt.getMmwrYear());
		  clientVO.setAnswer(PageConstants.CASE_LOCAL_ID, dt.getLocalId());
		  clientVO.setAnswer(PageConstants.CASE_ADD_TIME, StringUtils.formatDate(dt.getAddTime()));
		  clientVO.setAnswer(PageConstants.CASE_ADD_USERID, StringUtils.replaceNull(dt.getAddUserId()));
		 // clientVO.setAnswer(PageConstants.CASE_LC_USERTIME, StringUtils.formatDate(dt.getLastChgTime()));
		//  clientVO.setAnswer(PageConstants.CASE_LC_USERID, StringUtils.replaceNull(dt.getLastChgUserId()));
		  String sharedInd = null;
		  if(dt.getSharedInd()!= null && dt.getSharedInd().equals("T"))
			  sharedInd="1";
		  clientVO.setAnswer(PageConstants.SHARED_IND, sharedInd);

		  clientVO.setAnswer(PageConstants.CONDITION_CD, dt.getCd());
		  clientVO.setAnswer(PageConstants.RECORD_STATUS_CD, dt.getRecordStatusCd());
		  clientVO.setAnswer(PageConstants.RECORD_STATUS_TIME, StringUtils.formatDate(dt.getRecordStatusTime()));
		  clientVO.setAnswer(PageConstants.STATUS_CD, dt.getStatusCd());
		  clientVO.setAnswer(PageConstants.PROGRAM_JURISDICTION_OID, StringUtils.replaceNull(dt.getProgramJurisdictionOid()));
		  clientVO.setAnswer(PageConstants.VERSION_CTRL_NBR, StringUtils.replaceNull(dt.getVersionCtrlNbr()));
		  clientVO.setAnswer(PageConstants.CASE_CLS_CD, dt.getCaseClassCd());
		  clientVO.setAnswer(PageConstants.TUB_GEN_COMMENTS, dt.getTxt());
		  // These questions are specific to Varicella
		  if(dt.getRptToCountyTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DATE_REPORTED_TO_COUNTY,dt.getRptToCountyTime_s());
		  }
		  if(dt.getRptToStateTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DATE_REPORTED_TO_STATE,dt.getRptToStateTime_s());
		  }
		  if(dt.getDiagnosisTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DIAGNOSIS_DATE,dt.getDiagnosisTime_s());
		  }
		  if(dt.getEffectiveFromTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.ILLNESS_ONSET_DATE,dt.getEffectiveFromTime_s());
		  }
		  if(dt.getPatAgeAtOnset()!= null)
		  {
			  clientVO.setAnswer(PageConstants.PAT_AGE_AT_ONSET,dt.getPatAgeAtOnset());
		  }
		  if(dt.getPatAgeAtOnsetUnitCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.PAT_AGE_AT_ONSET_UNIT_CODE,dt.getPatAgeAtOnsetUnitCd());
		  }

		 // These questions are for extending PHC table for common fields - ODS changes
		  if(dt.getInvestigatorAssignedTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DATE_ASSIGNED_TO_INVESTIGATION,dt.getInvestigatorAssignedTime_s());
		  }
		  if(dt.getHospitalizedIndCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.WAS_THE_PATIENT_HOSPITALIZED,dt.getHospitalizedIndCd());
		  }
		  if(dt.getHospitalizedAdminTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.ADMISSION_DATE,dt.getHospitalizedAdminTime_s());
		  }
		  if(dt.getHospitalizedDischargeTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DISCHARGE_DATE,dt.getHospitalizedDischargeTime_s());
		  }
		  if(dt.getHospitalizedDurationAmt()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DURATION_OF_STAY,dt.getHospitalizedDurationAmt().toString());
		  }
		  if(dt.getPregnantIndCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.PREGNANCY_STATUS,dt.getPregnantIndCd());
		  }
		  if(dt.getOutcomeCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.DID_THE_PATIENT_DIE,dt.getOutcomeCd());
		  }
		  if(dt.getDayCareIndCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY,dt.getDayCareIndCd());
		  }
		  if(dt.getFoodHandlerIndCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IS_THIS_PERSON_FOOD_HANDLER,dt.getFoodHandlerIndCd());
		  }
		  if(dt.getImportedCountryCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IMPORTED_COUNTRY,dt.getImportedCountryCd());
		  }
		  if(dt.getImportedStateCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IMPORTED_STATE,dt.getImportedStateCd());
		  }
		  if(dt.getImportedCityDescTxt()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IMPORTED_CITY,dt.getImportedCityDescTxt());
		  }
		  if(dt.getImportedCountyCd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.IMPORTED_COUNTY,dt.getImportedCountyCd());
		  }
		  if(dt.getDeceasedTime_s()!= null)
		  {
			  clientVO.setAnswer(PageConstants.INVESTIGATION_DEATH_DATE,dt.getDeceasedTime_s());
		  }
		  if(dt.getOutbreakInd()!= null)
		  {
			  clientVO.setAnswer(PageConstants.OUTBREAK_INDICATOR,dt.getOutbreakInd());
		  }
		  if(dt.getOutbreakName()!= null)
		  {
			  clientVO.setAnswer(PageConstants.OUTBREAK_NAME,dt.getOutbreakName());
		  }
		  if(dt.getRptSourceCd() != null) clientVO.setAnswer(PageConstants.REPORTING_SOURCE, dt.getRptSourceCd());
		  if(dt.getEffectiveToTime() != null) clientVO.setAnswer(PageConstants.ILLNESS_END_DATE, StringUtils.formatDate(dt.getEffectiveToTime()));
		  if(dt.getEffectiveDurationAmt() != null) clientVO.setAnswer(PageConstants.ILLNESS_DURATION , dt.getEffectiveDurationAmt());
		  if(dt.getEffectiveDurationUnitCd() != null) clientVO.setAnswer(PageConstants.ILLNESS_DURATION_UNITS, dt.getEffectiveDurationUnitCd());
		  if(dt.getDiseaseImportedCd() != null) clientVO.setAnswer(PageConstants.DISEASE_IMPORT_CD, dt.getDiseaseImportedCd());
		  if(dt.getTransmissionModeCd() != null) clientVO.setAnswer(PageConstants.TRANSMISN_MODE_CD, dt.getTransmissionModeCd());
		  if(dt.getDetectionMethodCd() != null) clientVO.setAnswer(PageConstants.DETECTION_METHOD_CD, dt.getDetectionMethodCd());
		  //Added for Contact Tracing
		  if(dt.getPriorityCd() != null) clientVO.setAnswer(PageConstants.CONTACT_PRIORITY, dt.getPriorityCd());
		  if(dt.getInfectiousFromDate() != null) clientVO.setAnswer(PageConstants.INFECTIOUS_PERIOD_FROM,  StringUtils.formatDate(dt.getInfectiousFromDate()));
		  if(dt.getInfectiousToDate() != null) clientVO.setAnswer(PageConstants.INFECTIOUS_PERIOD_TO, StringUtils.formatDate(dt.getInfectiousToDate()));
		  if(dt.getContactInvStatus() != null) clientVO.setAnswer(PageConstants.CONTACT_STATUS, dt.getContactInvStatus());
		  if(dt.getContactInvTxt() != null) clientVO.setAnswer(PageConstants.CONTACT_COMMENTS, dt.getContactInvTxt());
		 //Confirmation Methods
		  _confirmationMethodsForViewEdit(form, proxyVO);		  

		  Collection<Object>  coll = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheActIdDTCollection();
		  if(coll != null && coll.size() > 0) {
			 Iterator<Object>  iter = coll.iterator();
			  while(iter.hasNext()) {
				  ActIdDT adt = (ActIdDT) iter.next();
				  String typeCd = adt.getTypeCd() == null ? "" : adt.getTypeCd();
				  String value = adt.getRootExtensionTxt() == null ? "" : adt.getRootExtensionTxt();
				  if(typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD) && !value.equals("")) {
					  clientVO.setAnswer(PageConstants.STATE_CASE, value);
				  } else if(typeCd.equalsIgnoreCase("CITY") && !value.equals("")) {
					  clientVO.setAnswer(PageConstants.COUNTY_CASE, value);
				  }
			  }
		  }
	  }

	  public static Map<Object,Object> updateMapWithQIds(Map<Object,Object> answerMap) {
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  if(answerMap != null && answerMap.size() > 0) {
			 Iterator<Object>  iter = answerMap.keySet().iterator();
			  while(iter.hasNext()) {
				  Long key = (Long) iter.next();
				  returnMap.put(questionKeyMap.get(key), answerMap.get(key));
			  }
		  }

		  return returnMap;
	  }
	  public static void setPamSpecificAnswersForViewEdit(PageForm form, Map<Object,Object> answerMap, Map<Object,Object> returnMap) {

		  if(answerMap != null && answerMap.size() > 0) {
			 Iterator<Object>  iter = answerMap.keySet().iterator();
			  while(iter.hasNext()) {
				  String questionId = (String) iter.next();
				  Object object = answerMap.get(questionId);
				  if(object instanceof NbsCaseAnswerDT) {
					  NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT) object;
					  returnMap.put(questionId, answerDT.getAnswerTxt());
				  }
			  }
			  form.getPageClientVO().getAnswerMap().putAll(returnMap);
			  //form.getPageClientVO().setPamAnswerMap(returnMap);
			  setRaceAnswersToClientVO(form.getPageClientVO(), answerMap);
		  }
	  }

	  private static void setRaceAnswersToClientVO(PageClientVO clientVO, Map<Object,Object> answerMap) {

		  Object obj = answerMap.get(PageConstants.RACE);
		  if(obj != null && obj instanceof ArrayList<?>) {
		  	  ArrayList<Object> raceArrayList = (ArrayList<Object> ) obj;
		  	 Iterator<Object>  iter = raceArrayList.iterator();
		  	  while(iter.hasNext()) {
		  		NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT) iter.next();
				  String answer = answerDT.getAnswerTxt();
				  if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.UNKNOWN))
					  clientVO.setUnKnownRace(1);
				  else if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN))
					  clientVO.setAfricanAmericanRace(1);
				  else if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
					  clientVO.setAmericanIndianAlskanRace(1);
				  else if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.WHITE))
					  clientVO.setWhiteRace(1);
				  else if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.ASIAN))
					  clientVO.setAsianRace(1);
				  else if(answer != null && answer.equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
					  clientVO.setHawaiianRace(1);
		  	  }
		  }
	  }

	private static void setInvInfoForCreate(PageForm form, HttpSession session) {

		Map<Object,Object> answerMap = new HashMap<Object,Object>();

		String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
		String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
		answerMap.put(PageConstants.INV_STATUS_CD, NEDSSConstants.STATUS_OPEN);

		answerMap.put(PageConstants.PROGRAM_AREA, programAreaCd);
		answerMap.put(PageConstants.CONDITION_CD, conditionCd);
		answerMap.put(PageConstants.SHARED_IND, "1");
		answerMap.put(PageConstants.CASE_LOCAL_ID, "");
		form.getPageClientVO().getAnswerMap().putAll(answerMap);
	}


	/**
	 * This method retrieves the complete PAM Information from the backend, constructs and returns PAMClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PageClientVO.PageClientVO
	 */
	public static PageClientVO retrieveEditViewLoadVO(PageForm form, HttpServletRequest request) {

		return new PageClientVO();
	}



 
 
	/**
	 * Retrieves Master Patient Record Information
	 * @param mprUId
	 * @param session
	 * @return
	 */
	private static PersonVO findMasterPatientRecord(Long mprUId, HttpSession session) {

		PersonVO personVO = null;
		MainSessionCommand msCommand = null;
		try {
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getMPR";
			Object[] oParams = new Object[] { mprUId };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			personVO = (PersonVO) arr.get(0);

		} catch (Exception ex) {
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}

			logger.fatal("personVO: ", ex);
		}
		return personVO;
	}

	public static PageProxyVO getProxyObject(String sPublicHealthCaseUID, HttpSession session) {

		PageProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {
			Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "getPageProxyVO";
			Object[] oParams = new Object[] { NEDSSConstants.CASE, publicHealthCaseUID };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			proxy = (PageProxyVO) arr.get(0);

			} catch (Exception ex) {
			logger.fatal("getProxy: ", ex);
		}

		return proxy;
	}
	public static Map<Object, Object> setContextForView(PageForm form, String contextAction,
			HttpServletRequest request, HttpSession session) {
		//clear Links if any stored
		form.getAttributeMap().remove("linkName");form.getAttributeMap().remove("linkValue");form.getAttributeMap().remove("linkName1");form.getAttributeMap().remove("linkValue1");
		Map<Object, Object> map =new HashMap<Object, Object> ();
		
		  PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());

		  Collection<Object> DSContactColl= new ArrayList<Object>();
		  if(((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getTheCTContactSummaryDTCollection()!=null){
			  DSContactColl= ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getTheCTContactSummaryDTCollection();
		  }
		  NBSContext.store(session, NBSConstantUtil.DSContactColl, DSContactColl);
		  
		  Long DSPatientPersonUID= personVO.getThePersonDT().getPersonParentUid();
		  if(DSPatientPersonUID!=null)			  
			  NBSContext.store(session, NBSConstantUtil.DSPatientPersonUID, DSPatientPersonUID);
		  String DSPatientPersonUIDs = DSPatientPersonUID.toString();
		  request.setAttribute("DSPatientPersonUID", DSPatientPersonUIDs);
		  
		  Collection<Object>  nms = personVO.getThePersonNameDTCollection();
			String strPName ="";
		    String strFName="";
		    String strMName="";
		    String strLName="";
		    String DOB="";
		    String CurrSex = "";
			if(nms != null)
		     {
		      Iterator<Object>  itname = nms.iterator();
		       while (itname.hasNext()) {
		         PersonNameDT nameDT = (PersonNameDT) itname.next();

		         if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
		        	 if(nameDT.getFirstNm() != null)
		        	 strFName = nameDT.getFirstNm();
		        	 if(nameDT.getMiddleNm() != null)
		        		 strMName =  nameDT.getMiddleNm();
		        	  if(nameDT.getLastNm() != null)
		        		  strLName =   nameDT.getLastNm();

		         }
		       }
		     }
			strPName = strFName +" "+strMName+ " "+strLName;
			if(null == strPName || strPName.equalsIgnoreCase("null")){
				strPName ="";
			}

			form.getAttributeMap().put("patientLocalName", strPName);


			if(personVO.getThePersonDT().getBirthTime() != null){
			//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
			java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
			DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
			}
			form.getAttributeMap().put("patientDOB", DOB);

			if(personVO.getThePersonDT().getCurrSexCd() != null)
			 CurrSex = personVO.getThePersonDT().getCurrSexCd();
			if(CurrSex.equalsIgnoreCase("F"))
				CurrSex = "Female";
			if(CurrSex.equalsIgnoreCase("M"))
				CurrSex = "Male";
			if(CurrSex.equalsIgnoreCase("U"))
				CurrSex = "Unknown";
			form.getAttributeMap().put("patientCurrSex", CurrSex);
			//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));
		form.getAttributeMap().put("caseLocalId", ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
		PublicHealthCaseDT phcDT = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT();
		request.setAttribute("caseLocalId", phcDT.getLocalId());
		request.setAttribute("caseUid", phcDT.getPublicHealthCaseUid());
		String caseStatus = cdv.getDescForCode("PHC_CLASS" ,((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd());
		caseStatus = (caseStatus == null || caseStatus.trim().length()==0 ? "NA" : caseStatus);
		form.getAttributeMap().put("caseClassCd",caseStatus);
		request.setAttribute("createdDate", StringUtils.formatDate(phcDT.getAddTime()));
		request.setAttribute("createdBy", phcDT.getAddUserName());

		request.setAttribute("updatedDate", StringUtils.formatDate(phcDT.getLastChgTime()));
		request.setAttribute("updatedBy", phcDT.getLastChgUserName());
		String sCurrentTask = null;
		if(contextAction==null){
			String ContactTracing = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
			contextAction=ContactTracing;
		}
		if(contextAction!=null){
		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS036", contextAction);
		String urlForViewFile=tm.get("ReturnToContactFileEvents").toString();
		String urlFOrContactCase=tm.get("ContactCase").toString();
		map.put("urlForViewFile", urlForViewFile);
		map.put("ContactCase", urlFOrContactCase);
		ErrorMessageHelper.setErrMsgToRequest(request);
		sCurrentTask = NBSContext.getCurrentTask(session);
		form.getAttributeMap().put("CurrentTask", sCurrentTask);
		form.getAttributeMap().put("Submit", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("Submit"));
		form.getAttributeMap().put("Cancel", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=Cancel");
		form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit");
		form.getAttributeMap().put("SubmitNoViewAccess", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
		form.getAttributeMap().put("Sort", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("Sort"));
		form.getAttributeMap().put("InvestigationID", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("InvestigationID"));
		form.getAttributeMap().put("ReturnToViewInvestigation", "/nbs/"
				+ sCurrentTask + ".do?ContextAction="
				+ tm.get("ReturnToViewInvestigation"));
		form.getAttributeMap().put("InvestigationIDOnSummary", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("InvestigationIDOnSummary"));
		form.getAttributeMap().put("InvestigationIDOnInv", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("InvestigationIDOnInv"));
		form.getAttributeMap().put("createNotification", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("CreateNotification"));
		form.getAttributeMap().put("CreateNotificationDisplay", tm.get("CreateNotification") == null ? "NOT_DISPLAYED" : tm.get("CreateNotification"));
		
		form.getAttributeMap().put("caseReporting", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("CaseReporting"));
		form.getAttributeMap().put("CaseReportingDisplay", tm.get("CaseReporting") == null ? "NOT_DISPLAYED" : tm.get("CaseReporting"));
		//String programAreaCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();

		form.getAttributeMap().put("TransferOwnership", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("TransferOwnership") + "&invFormCd=" + form.getPageFormCd());
		form.getAttributeMap().put("TransferOwnershipDisplay", tm.get("TransferOwnership") == null ? "NOT_DISPLAYED" : tm.get("TransferOwnership"));

		form.getAttributeMap().put(NBSConstantUtil.ViewVaccinationFromInv, "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get(NBSConstantUtil.ViewVaccinationFromInv));
		form.getAttributeMap().put("ObservationLabID", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("ObservationLabID"));
		form.getAttributeMap().put("ObservationMorbID", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("ObservationMorbID"));
		form.getAttributeMap().put("treatmentEventRef", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("TreatmentID"));

		form.getAttributeMap().put("PrintPage",
				"/nbs/LoadViewInvestigation1.do?ContextAction=PrintPage");

		form.getAttributeMap().put("DocumentIDFromInv", "/nbs/" + sCurrentTask
						+ ".do?ContextAction=" + tm.get("DocumentIDFromInv"));

		if (sCurrentTask.equalsIgnoreCase("ViewInvestigation3")) {
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToFileEvents") + "&invFormCd=" + form.getPageFormCd()
					+ "\">" + "Return To File: Events" + "</A>");
			form.getAttributeMap().put("deleteButtonHref"," /nbs/PamAction.do?method=deleteSubmit&ContextAction=" + tm.get("ReturnToFileEvents"));
			form.getAttributeMap().put("linkValue1", "");

		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation2")) {
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("FileSummary") + "&invFormCd=" + form.getPageFormCd() + "\">"
					+ "View File" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction=" + tm.get("FileSummary"));
			try {
				DSQueueObject queueObject = (DSQueueObject) NBSContext
						.retrieve(session, "DSQueueObject");
				if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
					form.getAttributeMap().put("linkValue1", "<A href=\"/nbs/"
							+ sCurrentTask + ".do?ContextAction="
							+ tm.get("ReturnToMyInvestigations") + "\">"
							+ NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS
							+ "</A>");
				} else if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
					form.getAttributeMap().put("linkValue1", "<A href=\"/nbs/"
							+ sCurrentTask + ".do?ContextAction="
							+ tm.get("ReturnToObservationNeedingReview")
							+ "\">" + "Return to Documents Requiring Review"
							+ "</A>");
					form.getAttributeMap().put("linkName1",
							"Return to Documents Requiring Review");
				}
			} catch (Exception ex) {
				logger.info("Link: cannot be shown in this context");
				form.getAttributeMap().put("linkValue1", "");
			}
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation1")) {
			form.getAttributeMap().put("linkValue1", "");
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToFileSummary") + "&invFormCd=" + form.getPageFormCd()
					+ "\">" + "Return to File: Summary" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction=" + tm.get("ReturnToFileSummary"));
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation4")) {
			
			
			try {
				DSQueueObject queueObject = (DSQueueObject) NBSContext
						.retrieve(session, "DSQueueObject");
				if (queueObject.getDSQueueType() != null && queueObject.getDSQueueType().equals(NEDSSConstants.MESSAGE_QUEUE)) {
					
					form.getAttributeMap().put("linkName","Return to Approval Queue for Initial Notifications");
					form.getAttributeMap().put("linkValue1", "<A href=\"/nbs/"+ sCurrentTask + ".do?ContextAction="+ tm.get("ReturnToMessageQueue") + "\">"+ NEDSSConstants.RETURN_TO_MESSAGE_QUEUE+ "</A>");
					form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="+ tm.get("ReturnToMessageQueue") );
					} else if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
					form.getAttributeMap().put("linkName","Return to Approval Queue for Initial Notifications");

					form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
							+ ".do?ContextAction=" + tm.get("ReturnToReviewNotifications") + "&invFormCd=" + form.getPageFormCd()
							+ "\">" + "Return to Approval Queue for Initial Notifications" + "</A>");
					form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="+ tm.get("ReturnToMessageQueue") );
					
				}
			} catch (Exception ex) {
				logger.info("Link: cannot be shown in this context");
				form.getAttributeMap().put("linkValue1", "");
			}
			
			
			
			form.getAttributeMap().put("linkName",
					"Return to Approval Queue for Initial Notifications");

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToReviewNotifications") + "&invFormCd=" + form.getPageFormCd()
					+ "\">" + "Return to Approval Queue for Initial Notifications" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="
					+ tm.get("ReturnToReviewNotifications") );
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation5")) {
			form.getAttributeMap().put("linkName",
					"Return to Updated Notifications Queue");

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToReviewUpdatedNotifications") + "&invFormCd=" + form.getPageFormCd()
					+ "\">" + "Return to Updated Notifications Queue" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="
					+ tm.get("ReturnToReviewUpdatedNotifications"));
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation6")) {
			form.getAttributeMap().put("linkName",
					"Return to Rejected Notifications Queue");

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToRejectedNotifications")
					+ "\">" + "Return to Rejected Notifications Queue" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="
					+ tm.get("ReturnToRejectedNotifications"));
		}

		form.getAttributeMap().put("ManageVaccinationDisplay", tm.get("ManageVaccinations") == null ? "NOT_DISPLAYED" : tm.get("ManageVaccinations"));

		form.getAttributeMap().put("Edit", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("Edit") + "&invFormCd=" + form.getPageFormCd());

		form.getAttributeMap().put("ManageEvents", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("ManageEvents") + "&invFormCd=" + form.getPageFormCd());
		form.getAttributeMap().put("ManageEventsDisplay", tm.get("ManageEvents") == null ? "NOT_DISPLAYED" : tm.get("ManageEvents"));
		}

        try {
			String rejectedDeleteString  =  NBSContext.retrieve(session,NBSConstantUtil.DSRejectedDeleteString) == null ? null : (String)NBSContext.retrieve(session,NBSConstantUtil.DSRejectedDeleteString);
			request.setAttribute("deleteError",rejectedDeleteString );
		} catch (Exception e) {}

		return map;
	}

	/**
	 * populatePamAssocations sets all the Lab, Morb, Treatment, Notification and Inv History Associations
	 * @param proxy
	 * @param sPublicHealthCaseUID
	 * @param request
	 * @param form
	 */
	public static void populatePageAssocations(PageProxyVO proxy, String sPublicHealthCaseUID, Map<Object, Object> map, HttpServletRequest request, PageForm form){
		ArrayList<Object> obsLabSummary = new ArrayList<Object> ();
	  	ArrayList<Object> obsMorbSummary = null;
	  	ArrayList<Object> treatments = new ArrayList<Object> ();
	  	ArrayList<Object> notifSummary = null;
	  	ArrayList<Object> vaccination = null;
	  	ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
		ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();
        String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
        String urlForViewFile="";
        String viewForward="";
			if (proxy != null) {

				//Morb Report
				obsMorbSummary = (ArrayList<Object> )((PageActProxyVO)proxy).getTheMorbReportSummaryVOCollection();
				if (obsMorbSummary != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = obsMorbSummary.iterator();
					while (ite.hasNext()) {
						MorbReportSummaryVO mrsVO = (MorbReportSummaryVO) ite.next();
						// to handle the lab reports created through Morb report.
						 if(mrsVO.getTheLabReportSummaryVOColl()!=null && mrsVO.getTheLabReportSummaryVOColl().size()>0){
							Iterator<Object>  labIte = mrsVO.getTheLabReportSummaryVOColl().iterator();
							 while(labIte.hasNext()){
								 LabReportSummaryVO labSummaryVO = (LabReportSummaryVO)labIte.next();
								 labSummaryVO.setLabFromMorb(true);
								 //UI should show the local id of the morb report and the link should take user to morb report
								 // though the record shows up in the lab list
								 labSummaryVO.setObservationUid(mrsVO.getObservationUid());
								 labSummaryVO.setLocalId(mrsVO.getLocalId());
							 }
							 obsLabSummary.addAll(mrsVO.getTheLabReportSummaryVOColl());
						 }
						 if(mrsVO.getTheTreatmentSummaryVOColl()!=null && mrsVO.getTheTreatmentSummaryVOColl().size()>0){
							 treatments.addAll(mrsVO.getTheTreatmentSummaryVOColl());
						 }

						parameterMap.put("ContextAction", "ObservationMorbID");
						parameterMap.put("observationUID", (mrsVO.getObservationUid()).toString());
						parameterMap.put("method", "viewSubmit");
						mrsVO.setActionLink(getHiperlink(dateLink(mrsVO
								.getDateReceived()), sCurrentTask, parameterMap, form.getActionMode()));
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("observationSummaryMorbList",obsMorbSummary);
				form.getAttributeMap().put("CurrentTask",sCurrentTask);

				// ER16652 Start
				if(obsLabSummary!=null && obsLabSummary.size()>0) {
					obsLabSummary.addAll(((PageActProxyVO)proxy).getTheLabReportSummaryVOCollection() == null ? new ArrayList<Object>() : ((PageActProxyVO)proxy).getTheLabReportSummaryVOCollection());
				}else{
					obsLabSummary = (ArrayList<Object> ) ((PageActProxyVO)proxy).getTheLabReportSummaryVOCollection();
				}
				// ER16652 End

				if (obsLabSummary != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = obsLabSummary.iterator();
					while (ite.hasNext()) {
					LabReportSummaryVO lrsVO = (LabReportSummaryVO) ite.next();
					if(lrsVO.isLabFromMorb())
						parameterMap.put("ContextAction", "ObservationMorbID");
					else
						parameterMap.put("ContextAction", "ObservationLabID");
					parameterMap.put("observationUID", (lrsVO
							.getObservationUid()).toString());
					parameterMap.put("method", "viewSubmit");
					lrsVO.setActionLink(DecoratorUtil.getOrderedTestString(getHiperlink(dateLink(lrsVO
							.getDateReceived()), sCurrentTask, parameterMap, form.getActionMode()),lrsVO));
					lrsVO.setResultedTestString(DecoratorUtil
							.getResultedTestsString(lrsVO
									.getTheResultedTestSummaryVOCollection()));
					parameterMap = new HashMap<Object,Object>();
				}
				}
				request.setAttribute("observationSummaryLabList",obsLabSummary);


				//Treatment

				// ER16652 Start
				if(treatments!=null && treatments.size()>0){
					treatments.addAll(((PageActProxyVO)proxy).getTheTreatmentSummaryVOCollection() == null ? new ArrayList<Object>() : ((PageActProxyVO)proxy).getTheTreatmentSummaryVOCollection());
				}else{
					treatments = (ArrayList<Object> ) ((PageActProxyVO)proxy).getTheTreatmentSummaryVOCollection();
				}
				// ER16652 End

				if(treatments != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = treatments.iterator();
					while (ite.hasNext()) {
						TreatmentSummaryVO tsVO = (TreatmentSummaryVO) ite.next();
						//tsVO.getLocalId()
						parameterMap.put("ContextAction", "TreatmentID");
						parameterMap.put("treatmentUID", tsVO.getTreatmentUid().toString());
						parameterMap.put("method", "viewSubmit");
						parameterMap.put("invFormCd", form.getPageFormCd());
						tsVO.setActionLink(getHiperlink(dateLink(tsVO.getActivityFromTime()), sCurrentTask, parameterMap, form.getActionMode()));
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("treatmentList",treatments);

				// notifications
				DecoratorUtil util = new DecoratorUtil();
				notifSummary = (ArrayList<Object> ) ((PageActProxyVO)proxy).getTheNotificationSummaryVOCollection();
				String notifDisplayHTML = util.buildNotificationList(notifSummary);
				request.setAttribute("notificationListTable",notifDisplayHTML);

				// investigation history/audit log summary
				String invHistoryHTML = util.buildInvHistoryList(((PageActProxyVO)proxy).getTheInvestigationAuditLogSummaryVOCollection());
				request.setAttribute("invHistoryTable",invHistoryHTML);

				//Vaccination
				vaccination = (ArrayList<Object> ) ((PageActProxyVO)proxy).getTheVaccinationSummaryVOCollection();
				if(vaccination != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = vaccination.iterator();
					while (ite.hasNext()) {
						VaccinationSummaryVO vacVO = (VaccinationSummaryVO) ite.next();
						parameterMap.put("ContextAction", NBSConstantUtil.ViewVaccinationFromInv);
						parameterMap.put("interventionUID", vacVO.getInterventionUid().toString());
						parameterMap.put("method", "viewSubmit");
						vacVO.setActionLink(getHiperlink(dateLink(vacVO.getActivityFromTime()), sCurrentTask, parameterMap, form.getActionMode()));
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("vaccinationList",vaccination);
				//Document
				ArrayList<Object> document = (ArrayList<Object> ) ((PageActProxyVO)proxy).getTheDocumentSummaryVOCollection();
				if(document != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = document.iterator();
					while (ite.hasNext()) {
						SummaryDT doc = (SummaryDT) ite.next();
						parameterMap.put("ContextAction", NBSConstantUtil.DocumentIDFromInv);
						parameterMap.put("nbsDocumentUid", doc.getNbsDocumentUid().toString());
						parameterMap.put("method", "viewSubmit");
						doc.setActionLink(getHiperlink(dateLink(doc.getAddTime()), sCurrentTask, parameterMap, form.getActionMode()));
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("documentSummaryList",document);
						
			//Contract Tracking Contract Records
			Collection<Object> contactRecords = (Collection<Object> ) ((PageActProxyVO)proxy).getTheCTContactSummaryDTCollection();
			
			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
			// HttpSession session = request.getSession();
			 String contextAction = request.getParameter("ContextAction");
			 contextAction = "ViewFile";//set in case of view person record
			 //TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS114", contextAction);
			// NBSContext.lookInsideTreeMap(tm);
		     //String sCurrTask = NBSContext.getCurrentTask(session);
		     String conditionCd = ((PageActProxyVO)proxy).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile");
		    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + contextAction;
		    // String viewHref = "/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID");
		     String contactUrl="/nbs/"+sCurrentTask+".do?ContextAction=PatientSearch";
		     String populateContactRecord = "/nbs/ContactTracing.do?method=viewContact&mode=View&Action=DSInvestigationPath&DSInvestigationCondition="+conditionCd;
		     String managectAssoUrl="/nbs/"+sCurrentTask+".do?ContextAction=ManageCtAssociation";
		     request.setAttribute("contactUrl", contactUrl);
		     request.setAttribute("populateContactRecord", populateContactRecord);
		     request.setAttribute("managectAssoUrl", managectAssoUrl);
		     InvestigationUtil.storeContactContextInformation(request.getSession(),((PageActProxyVO)proxy).getPublicHealthCaseVO(),((PageActProxyVO)proxy).getThePersonVOCollection());
		    if(contactRecords != null) {
			Iterator<Object> ite = contactRecords.iterator();
				while (ite.hasNext()) {
					CTContactSummaryDT ctSumDT = (CTContactSummaryDT) ite.next();
					String conLink = null;
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION))
						conLink = "<a href=\"javascript:populateContactRecordPopUp("+ctSumDT.getCtContactUid()+")\">" + ctSumDT.getLocalId() + "</a>";
					else if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						conLink =  ctSumDT.getLocalId();
					ctSumDT.setLocalId(conLink);
					ctSumDT.setPriority((ctSumDT.getPriorityCd() == null) ?
                            "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,ctSumDT.getPriorityCd()));
	          		 
					ctSumDT.setDisposition((ctSumDT.getDispositionCd()== null) ?  
	          				"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION,ctSumDT.getDispositionCd()));
					//String phcLink = "<a  href=\"" + viewHref+"&publicHealthCaseUID="+ String.valueOf(ctSumDT.getSubjectEntityPhcUid()) + "\">"+ ctSumDT.getLocalId() + "</a>";
					//ctSumDT.setPhcLocalID(phcLink);
					String 	viewFileUrl ="";
					String contactCaseUrl="";
					if(map.get("urlForViewFile")!=null){
						viewFileUrl =map.get("urlForViewFile").toString();
						contactCaseUrl =map.get("ContactCase").toString();
					}
					if(ctSumDT.isContactNamedByPatient() && form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION)){
						String patLink = "<a href=\"/nbs/"+ sCurrentTask+ ".do?ContextAction=" + viewFileUrl+"&uid="+ String.valueOf(ctSumDT.getContactMprUid()) + "\">" + ctSumDT.getName() + "</a>";
						//String patLink = "<a href=\"/nbs/LoadViewFile1.do?ContextAction=ViewFile&uid="+ String.valueOf(ctSumDT.getContactEntityUid()) + "\">" + ctSumDT.getName() + "</a>";
						ctSumDT.setName(patLink);
						if(ctSumDT.getContactPhcLocalId() != null){
							String phcLink = "<a  href=\"/nbs/"+ sCurrentTask+ ".do?ContextAction="+contactCaseUrl+"&publicHealthCaseUID="+ String.valueOf(ctSumDT.getContactEntityPhcUid()) + "\">"+ ctSumDT.getContactPhcLocalId() + "</a>";
							ctSumDT.setContactPhcLocalId(phcLink);
						}
						contactNamedByPatList.add(ctSumDT);
					}else if(ctSumDT.isContactNamedByPatient() && form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
					{
						String patLink = ctSumDT.getName() ;
						ctSumDT.setName(patLink);
						if(ctSumDT.getContactPhcLocalId() != null){
							String phcLink =ctSumDT.getContactPhcLocalId();
							ctSumDT.setContactPhcLocalId(phcLink);
						}
						contactNamedByPatList.add(ctSumDT);
					}
					
					if(ctSumDT.isPatientNamedByContact()&& form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW_LOAD_ACTION)){
						String patLink = "<a href=\"/nbs/"+ sCurrentTask+ ".do?ContextAction=" + viewFileUrl+"&uid="+ String.valueOf(ctSumDT.getSubjectMprUid()) + "\">" + ctSumDT.getNamedBy() + "</a>";
						ctSumDT.setNamedBy(patLink);
						if(ctSumDT.getSubjectPhcLocalId()!= null){
						String phcLink = "<a  href=\"/nbs/"+ sCurrentTask+ ".do?ContextAction="+contactCaseUrl+"&publicHealthCaseUID="+ String.valueOf(ctSumDT.getSubjectEntityPhcUid()) + "\">"+ ctSumDT.getSubjectPhcLocalId() + "</a>";
						ctSumDT.setSubjectPhcLocalId(phcLink);
						}
						patNamedByContactsList.add(ctSumDT);
					}else if(ctSumDT.isPatientNamedByContact()&& form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
					{
						String patLink =  ctSumDT.getNamedBy();
						ctSumDT.setNamedBy(patLink);
						if(ctSumDT.getSubjectPhcLocalId()!= null){
							String phcLink =ctSumDT.getSubjectPhcLocalId();
							ctSumDT.setSubjectPhcLocalId(phcLink);
						}
						patNamedByContactsList.add(ctSumDT);
					}

					
				}
			}
			
		}
			request.setAttribute("contactNamedByPatList",contactNamedByPatList);
			request.setAttribute("patNamedByContactsList",patNamedByContactsList);
			
	}
	protected static String dateLink(java.sql.Timestamp timestamp) {
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
	}
	public static String getHiperlink(String displayNm, String currentTask,HashMap<Object,Object> parameterMap, String actionMode) {
		
		if(actionMode != null && (actionMode.equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION) ||  actionMode.equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION)) ) {
			return displayNm;
		} else {
			String url = NavigatorUtil.getLink(currentTask+".do", displayNm, parameterMap);
			return url;
		}
	}

	protected static String setContextForEdit(PageForm form, HttpServletRequest request, HttpSession session) {

		PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPageClientVO().getOldPageProxyVO());

		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));
		form.getAttributeMap().put("caseLocalId", ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());

		Collection<Object>  nms = personVO.getThePersonNameDTCollection();
		String strPName ="";
	    String strFName="";
	    String strMName="";
	    String strLName="";
	    String DOB="";
	    String CurrSex = "";
		if(nms != null)
	     {
	      Iterator<Object>  itname = nms.iterator();
	       while (itname.hasNext()) {
	         PersonNameDT nameDT = (PersonNameDT) itname.next();

	         if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
	        	 if(nameDT.getFirstNm() != null)
	        	 strFName = nameDT.getFirstNm();
	        	 if(nameDT.getMiddleNm() != null)
	        		 strMName =  nameDT.getMiddleNm();
	        	  if(nameDT.getLastNm() != null)
	        		  strLName =   nameDT.getLastNm();

	         }
	       }
	     }
		strPName = strFName +" "+strMName+ " "+strLName;
		if(null == strPName || strPName.equalsIgnoreCase("null")){
			strPName ="";
		}

		form.getAttributeMap().put("patientLocalName", strPName);


		if(personVO.getThePersonDT().getBirthTime() != null){
		//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
		java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
		DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
		}
		form.getAttributeMap().put("patientDOB", DOB);

		if(personVO.getThePersonDT().getCurrSexCd() != null)
		 CurrSex = personVO.getThePersonDT().getCurrSexCd();
		if(CurrSex.equalsIgnoreCase("F"))
			CurrSex = "Female";
		if(CurrSex.equalsIgnoreCase("M"))
			CurrSex = "Male";
		if(CurrSex.equalsIgnoreCase("U"))
			CurrSex = "Unknown";
		form.getAttributeMap().put("patientCurrSex", CurrSex);
		//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

		// set notifications (createdBy/Date, updatedBy/Date) in request.
		PublicHealthCaseDT phcDT = ((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO().getThePublicHealthCaseDT();
		request.setAttribute("createdDate", StringUtils.formatDate(phcDT.getAddTime()));
		request.setAttribute("createdBy", phcDT.getAddUserName());
		request.setAttribute("updatedDate", StringUtils.formatDate(phcDT.getLastChgTime()));
		request.setAttribute("updatedBy", phcDT.getLastChgUserName());

		String passedContextAction = request.getParameter("ContextAction");
		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS023", passedContextAction);
	        ErrorMessageHelper.setErrMsgToRequest(request);
		String sCurrentTask = NBSContext.getCurrentTask(session);

		form.getAttributeMap().put("ContextAction", passedContextAction);
		form.getAttributeMap().put("CurrentTask", sCurrentTask);
		form.getAttributeMap().put("formHref", "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Submit"));
		form.getAttributeMap().put("Cancel",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
		form.getAttributeMap().put("SubmitNoViewAccess",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
		form.getAttributeMap().put("DiagnosedCondition",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("DiagnosedCondition"));


		return sCurrentTask;
    }

	private static String setContextForCreate(PersonVO personVO, HttpServletRequest request,PageForm form) {

		String localId = personVO.getThePersonDT().getLocalId();
		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(localId));
		Collection<Object>  nms = personVO.getThePersonNameDTCollection();
		String strPName ="";
	    String strFName="";
	    String strMName="";
	    String strLName="";
	    String DOB="";
	    String CurrSex = "";
		if(nms != null)
	     {
	      Iterator<Object>  itname = nms.iterator();
	       while (itname.hasNext()) {
	         PersonNameDT nameDT = (PersonNameDT) itname.next();

	         if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
	        	 if(nameDT.getFirstNm() != null)
	        	 strFName = nameDT.getFirstNm();
	        	 if(nameDT.getMiddleNm() != null)
	        		 strMName =  nameDT.getMiddleNm();
	        	  if(nameDT.getLastNm() != null)
	        		  strLName =   nameDT.getLastNm();

	         }
	       }
	     }
		strPName = strFName +" "+strMName+ " "+strLName;
		if(null == strPName || strPName.equalsIgnoreCase("null")){
			strPName ="";
		}

		form.getAttributeMap().put("patientLocalName", strPName);


		if(personVO.getThePersonDT().getBirthTime() != null){
		//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
		java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
		DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
		}
		form.getAttributeMap().put("patientDOB", DOB);

		if(personVO.getThePersonDT().getCurrSexCd() != null)
		 CurrSex = personVO.getThePersonDT().getCurrSexCd();
		if(CurrSex.equalsIgnoreCase("F"))
			CurrSex = "Female";
		if(CurrSex.equalsIgnoreCase("M"))
			CurrSex = "Male";
		if(CurrSex.equalsIgnoreCase("U"))
			CurrSex = "Unknown";
		form.getAttributeMap().put("patientCurrSex", CurrSex);
		//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()


		form.getPageClientVO().setAnswer(PageConstants.PATIENT_LOCAL_ID, localId);
		String passedContextAction = request.getParameter("ContextAction");
		TreeMap<Object,Object> tm = NBSContext.getPageContext(request.getSession(), "PS020",
				passedContextAction);
		ErrorMessageHelper.setErrMsgToRequest(request);
		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
		form.getAttributeMap().put("CurrentTask", sCurrentTask);
		form.getAttributeMap().put("formHref", "/nbs/" + sCurrentTask + ".do");
		if(request.getSession().getAttribute("PrevPageId")!=null 
				 && ((String)request.getSession().getAttribute("PrevPageId")).equals("PS233")){
			if((sCurrentTask.equals("CreateInvestigation11") || sCurrentTask.equals("CreateInvestigation10"))
				   && (NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSDocConditionCD)!= null)){
				form.getAttributeMap().put("Cancel", "/nbs/" + sCurrentTask
						+ ".do?ContextAction=" + tm.get("CancelToViewDoc"));
			}
		}else{		
			form.getAttributeMap().put("Cancel", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("Cancel"));
		}
		form.getAttributeMap().put("DiagnosedCondition", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("DiagnosedCondition"));
		form.getAttributeMap().put("SubmitNoViewAccess", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
		form.getAttributeMap().put("ContextAction", tm.get("Submit"));
		request.getSession().removeAttribute("PrevPageId");	

		return sCurrentTask;
	}

	private static void populateLabMorbValues(PageForm form, String sCurrentTask, HttpServletRequest request){

		form.getAttributeMap().remove("ReadOnlyJursdiction");
		if(sCurrentTask.equals("CreateInvestigation2")
		           || sCurrentTask.equals("CreateInvestigation3")
		           || sCurrentTask.equals("CreateInvestigation4")
		           || sCurrentTask.equals("CreateInvestigation5")
		           || sCurrentTask.equals("CreateInvestigation6")
		           || sCurrentTask.equals("CreateInvestigation7")
		           || sCurrentTask.equals("CreateInvestigation8")
		           || sCurrentTask.equals("CreateInvestigation9"))
        {
            String jurisdiction = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationJurisdiction);
        	form.getPageClientVO().setAnswer(PageConstants.JURISDICTION, jurisdiction);
        	form.getAttributeMap().put("ReadOnlyJursdiction", "ReadOnlyJursdiction");
        }
		if(sCurrentTask.equals("CreateInvestigation5") || sCurrentTask.equals("CreateInvestigation6") || sCurrentTask.equals("CreateInvestigation7")|| sCurrentTask.equals("CreateInvestigation8"))
        {
			//this is from morb and for generic investigation only
			TreeMap<Object,Object> DSMorbMap = (TreeMap<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSMorbMap);
			String INV111 = (String) DSMorbMap.get("INV111");
			//form.getPageClientVO().getPamAnswerMap().put(PageConstants.DATE_REPORTED, INV111);
        }
		else if(sCurrentTask.equals("CreateInvestigation2") || sCurrentTask.equals("CreateInvestigation3") || sCurrentTask.equals("CreateInvestigation4") || sCurrentTask.equals("CreateInvestigation9"))
        {
          //this is from lab and for generic investigation only
			TreeMap<Object,Object> labMap = (TreeMap<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSLabMap);
			String INV111 = (String) labMap.get("INV111");
			//form.getPageClientVO().getPamAnswerMap().put(PageConstants.DATE_REPORTED, INV111);
	    }
	}


	public static PersonVO getPersonVO(String type_cd, PageProxyVO proxyVO) {
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  personVOCollection  = null;
		ParticipationDT participationDT = null;
		PersonVO personVO = null;
		participationDTCollection  = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheParticipationDTCollection();
		personVOCollection  = ((PageActProxyVO)proxyVO).getThePersonVOCollection();
		if (participationDTCollection  != null) {
			Iterator<Object> anIterator1 = null;
			Iterator<Object> anIterator2 = null;
			for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();) {
				participationDT = (ParticipationDT) anIterator1.next();
				if (participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
					for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();) {
						personVO = (PersonVO) anIterator2.next();
						if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT
								.getSubjectEntityUid().longValue()) {
							return personVO;
						} else
							continue;
					}
				} else
					continue;
			}
		}
		return null;
	}

	public static OrganizationVO getOrganizationVO(String type_cd, PageProxyVO proxyVO) {

		Collection<Object>  organizationVOCollection  = null;
		OrganizationVO organizationVO = null;
		Collection<Object>  participationDTCollection  = null;
		ParticipationDT participationDT = null;
		participationDTCollection  = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheParticipationDTCollection();
		organizationVOCollection  = ((PageActProxyVO)proxyVO).getTheOrganizationVOCollection();
		if (participationDTCollection  != null) {
			Iterator<Object> anIterator1 = null;
			Iterator<Object> anIterator2 = null;
			for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();) {
				participationDT = (ParticipationDT) anIterator1.next();
				if (participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
					for (anIterator2 = organizationVOCollection.iterator(); anIterator2.hasNext();) {
						organizationVO = (OrganizationVO) anIterator2.next();
						if (organizationVO.getTheOrganizationDT().getOrganizationUid().longValue() == participationDT
								.getSubjectEntityUid().longValue()) {
							return organizationVO;
						} else
							continue;
					}
				} else
					continue;
			}
		}
		return null;
	}


	private static void setUpdatedValues(PageForm form, HttpServletRequest req) {

		  Map<Object,Object> answerMap = form.getPageClientVO().getAnswerMap();
		  if(answerMap != null && answerMap.size() > 0) {
			  String stateCd = (String) answerMap.get(PageConstants.STATE);
			  form.getOnLoadCityList(stateCd, req);
			  form.getDwrCountiesForState(stateCd);
		  }

	}
	
	private static void populateContactTracing(PageForm form, HttpServletRequest req) {

		//Contract Tracking Contract Records
		CTContactSummaryDT contactRecords = new CTContactSummaryDT();
		ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
		ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();
		//contactNamedByPatList.add(contactRecords);
		//patNamedByContactsList.add(contactRecords);
		req.setAttribute("contactNamedByPatList",contactNamedByPatList);
		req.setAttribute("patNamedByContactsList",patNamedByContactsList);

	}
	protected static void setCommonSecurityForCreateEditLoad(PageForm form, NBSSecurityObj nbsSecurityObj, HttpServletRequest req, PageProxyVO proxyVO ) {
		String conditionCd ="";
		if(proxyVO == null)
		 conditionCd = (String) NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationCondition);
		else
			 conditionCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		boolean viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
				NBSOperationLookup.VIEW);

		boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
				NBSOperationLookup.ADD);
		String ContactTracingByConditionCd = getConditionTracingEnableInd(conditionCd);
		 form.getSecurityMap().put("ContactTracingEnableInd", ContactTracingByConditionCd);
		form.getSecurityMap().put("checkToViewContactTracing", String.valueOf(viewContactTracing));
		req.setAttribute("checkToAddContactTracing",new Boolean(addContactTracing).toString());
		
	}
	
	
	  protected static void setCommonSecurityForView(PageForm form, PageProxyVO proxyVO, NBSSecurityObj nbsSecurityObj, HttpServletRequest req) {

		   String programAreaCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
	       String jurisdictionCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
	       String sharedInd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();
	       String conditionCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

	       //created required checks for security in the front end
	       boolean checkViewFilePermission = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
									       NBSOperationLookup.VIEWWORKUP);
	       boolean checkViewEditInvPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
										  NBSOperationLookup.EDIT, programAreaCd, jurisdictionCd, sharedInd);

	       boolean checkViewTransferPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
										  NBSOperationLookup.TRANSFERPERMISSIONS, programAreaCd, jurisdictionCd, sharedInd);

	       boolean checkViewObsLabPermission = nbsSecurityObj.getPermission(
							    NBSBOLookup.OBSERVATIONLABREPORT,
							    NBSOperationLookup.VIEW);
	       boolean checkViewObsMorbPermission = nbsSecurityObj.getPermission(
							    NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							    NBSOperationLookup.VIEW);

	       boolean assocLabPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	         boolean assocMorbPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	        boolean checkViewEditObsLabPermission = nbsSecurityObj.getPermission(
								NBSBOLookup.OBSERVATIONLABREPORT,
								NBSOperationLookup.EDIT);

	        boolean bManageTreatment = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	                NBSOperationLookup.ASSOCIATETREATMENTS,
	                ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	                ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	        boolean checkViewTreatmentPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);
	        
	       boolean checkCreateNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
							      NBSOperationLookup.CREATE, ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							      ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	       boolean checkCreateNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
							      NBSOperationLookup.CREATENEEDSAPPROVAL);
	       
	       boolean checkCaseReporting = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
							      NBSOperationLookup.CREATE, ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							      ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
			
			boolean checkCaseReportingNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
							      NBSOperationLookup.CREATENEEDSAPPROVAL);



	       boolean deleteInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.DELETE,
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	       boolean manageVaccination = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS,
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	       
	       boolean viewDocInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,
		              NBSOperationLookup.VIEW);
	       
	       boolean viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
		              NBSOperationLookup.VIEW);
	       
	       boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
		              NBSOperationLookup.ADD);
	   	   boolean manageAssoPerm = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
				NBSOperationLookup.MANAGE);
	   	
	   	   String viewContactTracingByConditionCd = getConditionTracingEnableInd(conditionCd);
	   	  
			form.getSecurityMap().put("deleteInvestigation", String.valueOf(deleteInvestigation));


			form.getSecurityMap().put("checkFile", String.valueOf(checkViewFilePermission));
			boolean showTransferOwnerShipButton = showTransferOwnerShipButton(proxyVO, nbsSecurityObj);
			form.getSecurityMap().put("checkTransfer", String.valueOf((checkViewTransferPermission ) && (showTransferOwnerShipButton)));
	        boolean showNotificationCreateButton = showCreateNotificationButton(proxyVO, nbsSecurityObj);
	        form.getSecurityMap().put("checkManageNotific", String.valueOf((checkCreateNotific || checkCreateNeedsApprovalNotific) &&(showNotificationCreateButton)));
	       // boolean showcaseRepCreateButton = showCaseReportingButton(proxyVO, nbsSecurityObj);
	        form.getSecurityMap().put("checkCaseReporting", String.valueOf((checkCaseReporting || checkCaseReportingNeedsApprovalNotific)));

	        form.getSecurityMap().put("checkManageEvents", String.valueOf(assocLabPermission || assocMorbPermission||manageVaccination ||bManageTreatment));

	        form.getSecurityMap().put("editInv", String.valueOf(checkViewEditInvPermission));

			//to check for Observation Display box to be displayed or not
	        form.getSecurityMap().put("ObsDisplay", String.valueOf(checkViewObsLabPermission));
			//to check for Observation Display box to be displayed or not
	        form.getSecurityMap().put("MorbDisplay", String.valueOf(checkViewObsMorbPermission));
	        form.getSecurityMap().put("TreatmentDisplay", String.valueOf(checkViewTreatmentPermission));
	        form.getSecurityMap().put("checkManageVacc", String.valueOf(manageVaccination));
	        form.getSecurityMap().put("checkToViewDocument", String.valueOf(viewDocInvestigation));
	        form.getSecurityMap().put("checkToViewContactTracing", String.valueOf(viewContactTracing));
	        form.getSecurityMap().put("ContactTracingEnableInd", viewContactTracingByConditionCd);
	        req.setAttribute("checkToAddContactTracing",new Boolean(addContactTracing).toString());
	        req.setAttribute("manageAssoPerm",new Boolean(manageAssoPerm).toString());

	    }

		public static boolean showCreateNotificationButton(PageProxyVO proxy, NBSSecurityObj securityObj) {

			if (((PageActProxyVO)proxy).getTheNotificationSummaryVOCollection() != null) {

			for (Iterator<Object> anIterator = ((PageActProxyVO)proxy).getTheNotificationSummaryVOCollection().iterator(); anIterator.hasNext();) {

				NotificationSummaryVO notSumVO = (NotificationSummaryVO) anIterator.next();
				if (notSumVO.getAutoResendInd() != null && 
						((notSumVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF) && notSumVO.getAutoResendInd().equalsIgnoreCase("T")))) {
						return false;
					}
				}
			}
		 return true;
		}
		
		public static boolean showTransferOwnerShipButton(PageProxyVO proxy, NBSSecurityObj securityObj) {

			 if(((PageActProxyVO)proxy).isOOSystemPendInd() == true)
				 return false;
			 else
				 return true;
		}

		public static void setAnswerArrayMapAnswers(PageForm form, ArrayList<Object> multiSelects, Map<Object,Object> answerMap) {

			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			//Initialize
			//form.getPageClientVO().setPamArrayAnswerMap(returnMap);
			for(int k=0; k < multiSelects.size(); k++) {
				Object obj = answerMap.get(multiSelects.get(k));

				if( obj != null && obj instanceof ArrayList<?>) {
			  	  ArrayList<Object> multiList = (ArrayList<Object> ) obj;
			  	  String [] answerList = new String[multiList.size()];
			  	  for(int i=0; i<multiList.size(); i++) {
			  		NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)multiList.get(i);
					  String answer = answerDT.getAnswerTxt();
					  answerList[i] = answer;
			  	  }
			  	  returnMap.put(multiSelects.get(k), answerList);
			  	  form.getPageClientVO().getArrayAnswerMap().putAll(returnMap);

				}
			}
		}

	    public static void setCheckBoxAnswersWithCodeSet(Map<Object,Object> answerMap,ArrayList<Object> checkboxes, Map<Object,Object> returnMap) {

	    	Map<?,?> map = cdv.getCodedValuesAsTreeMap("TF_PAM");
	    	for(int i=0; i < checkboxes.size(); i++) {
	    		Object obj = answerMap.get(checkboxes.get(i));
	    		if(obj instanceof NbsCaseAnswerDT) {
	    			NbsCaseAnswerDT dt = (NbsCaseAnswerDT)obj;
	    			String answer = dt.getAnswerTxt();
		        	if(answer != null && answer.equalsIgnoreCase((String)map.get("True")))
		        		returnMap.put(checkboxes.get(i), "1");
		        	else
		        		returnMap.put(checkboxes.get(i), "0");
	    		}
	    	}
	    }

		public  static void setJurisdictionForCreate(PageForm form, NBSSecurityObj nbsSecurityObj, HttpSession session) {

			String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);

			setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
		}
		private static void setJurisdictionForEdit(PageForm form, NBSSecurityObj nbsSecurityObj, PageProxyVO proxyVO) {

			String programAreaCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
			String conditionCd = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

			setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
		}

		private static void setJurisdiction(PageForm form, String programAreaCd, String conditionCd, NBSSecurityObj nbsSecurityObj) {

	        CachedDropDownValues cdv = new CachedDropDownValues();
	        ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" +programAreaCd + "')", conditionCd);

			String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			StringBuffer sb = new StringBuffer();

			if (programAreaJurisdictions != null && programAreaJurisdictions.length() > 0) {

				StringTokenizer st = new StringTokenizer(programAreaJurisdictions,"|");
				while (st.hasMoreTokens()) {

					String token = st.nextToken();
					if (token.lastIndexOf("$") >= 0) {

						String programArea = token.substring(0, token.lastIndexOf("$"));
						if (programArea != null&& programArea.equals(programAreaVO.getStateProgAreaCode())) {

							String juris = token.substring(token.lastIndexOf("$") + 1);
							sb.append(juris).append("|");
						}
					}
				}
				form.getAttributeMap().put("NBSSecurityJurisdictions", sb.toString());
			}

		}


		  /**
		   * setRVCTAnswersForViewEdit retrives RVCT Answers from NBS_Answers and puts in the form.
		   * @param form
		   * @param answerMap
		   */
		  public static void setMSelectCBoxAnswersForViewEdit(PageForm form, Map<Object,Object> answerMap) {

			  Map<Object,Object> returnMap = new HashMap<Object,Object>();
			  //Multiselect answers
			  ArrayList<Object> multiSelects = retrieveMultiSelectQIds(form);
			  setAnswerArrayMapAnswers(form, multiSelects, answerMap);
			  //Load all PAM Specific answers to form
			  setPamSpecificAnswersForViewEdit(form, answerMap, returnMap);
			  ArrayList<Object> checkboxes = retrieveCheckboxQIds(form);
			  setCheckBoxAnswersWithCodeSet(answerMap, checkboxes, returnMap);
			  form.getPageClientVO().getAnswerMap().putAll(returnMap);

		  }

		  public static ArrayList<Object> retrieveMultiSelectQIds(PageForm form) {

			  return(retrieveQIdentifiersByType(1013,form));
		  }

		  public static ArrayList<Object> retrieveCheckboxQIds(PageForm form) {

			  return(retrieveQIdentifiersByType(1001, form));
		  }

		  private static ArrayList<Object> retrieveQIdentifiersByType(int nbsComponentUid, PageForm form) {
			  ArrayList<Object> list = new ArrayList<Object> ();
			  StringBuffer js = new StringBuffer("");
			 Iterator<Object>  iter = questionMap.keySet().iterator();
				while(iter.hasNext()) {
					String key = (String) iter.next();
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
					String qId = metaData.getQuestionIdentifier() == null ? "" : metaData.getQuestionIdentifier();
					Long compUid = metaData.getNbsUiComponentUid() == null ? new Long(0) : metaData.getNbsUiComponentUid();
					if(compUid.intValue() == nbsComponentUid) {
						if(!qId.startsWith("DEM"))
							list.add(qId);
						js.append(qId).append("|");
					}
				}
				if(nbsComponentUid == 1013) {
					String jsSt = js.toString();
					if(jsSt.length() > 0) {
						jsSt = jsSt.substring(0, (jsSt.length()-1));
						form.getAttributeMap().put("selectEltIdsArray", jsSt);						
					}
				}
				return list;
		  }
		  
	  /**
	   * _confirmationMethodsForViewEdit extracts Confirmation Method Collection<Object>   from PHCase and sets it to form
	   * @param form
	   * @param proxyVO
	   */
	  private static void _confirmationMethodsForViewEdit(PageForm form, PageProxyVO proxyVO) {
	  	  ArrayList<Object> multiList = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheConfirmationMethodDTCollection() == null ? new ArrayList<Object>() :(ArrayList<Object> ) ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
	  	  if(multiList.size() > 0) {
		  	  String [] answerList = new String[multiList.size()];
		  	  for(int i=0; i<multiList.size(); i++) {
		  		  ConfirmationMethodDT cm = (ConfirmationMethodDT)multiList.get(i);
				  String answer = cm.getConfirmationMethodCd();
				  answerList[i] = answer;
				  //Confirmation Date
				  if(cm.getConfirmationMethodTime() != null)
					  form.getPageClientVO().getAnswerMap().put(PageConstants.CONFIRM_DATE, StringUtils.formatDate(cm.getConfirmationMethodTime()));
		  	  }
		  	  form.getPageClientVO().getArrayAnswerMap().put(PageConstants.CONFIRM_METHOD_CD, answerList);	  		  
	  	  }
		}		  
		
	  
	  private static void setNNDIndicator(PageProxyVO proxyVO, PageForm form){
		  
		  /*
		  form.getAttributeMap().put(PageConstants.NO_REQ_FOR_NOTIF_CHECK, "true");              	
		  Collection<Object>  actRelationshipDTColl = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getTheActRelationshipDTCollection();
		  if(actRelationshipDTColl != null && actRelationshipDTColl.size()>0){
		     Iterator<Object>  iter = actRelationshipDTColl.iterator();
              while(iter.hasNext()) {	
              	ActRelationshipDT dt = (ActRelationshipDT) iter.next();
                  	if(dt != null && dt.isNNDInd()){
                  		form.getAttributeMap().put(PageConstants.NO_REQ_FOR_NOTIF_CHECK, "false");
                  		break;
                  	}
                  		
              }
			  
		  }
		  */
	  }
	  
	  public static  String getConditionTracingEnableInd(String conditonCd) {

			String enableContactTab ="";	
			CachedDropDownValues cdv1 = new CachedDropDownValues();
			try {				
				TreeMap<Object,Object>  conditionList = cdv1.getConditionTracingEnableInd();
				if (conditionList != null){
					  if(conditonCd!=null && conditionList.get(conditonCd)!=null)
						  enableContactTab = (String)conditionList.get(conditonCd);					    
					}

				} catch (Exception ex) {
				logger.fatal("getProxy: ", ex);
			}

			return enableContactTab;
		}
		
}
