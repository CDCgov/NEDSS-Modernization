package gov.cdc.nedss.webapp.nbs.action.contacttracing.util;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAnswerDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonInvestgationSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssTimeLogger;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO.CTContactClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules.PageRulesGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.ParticipantListDisplay;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.MessageLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Struts Util Class for the View Add Edit Contact
 *  Modified for release 4.5 to handle both Dynamic and Legacy Contact Pages
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Liedos</p>
 * ContactTracingAction.java
 * Oct 26, 2009
 * @version
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */

public class CTContactLoadUtil {

	public CTContactLoadUtil() {
	}
	static final LogUtils logger = new LogUtils(CTContactLoadUtil.class.getName());

	public static Map<?,?> questionMap;
	public static final String ACTION_PARAMETER	= "method";
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static Map<Object,Object> questionKeyMap;
	/**
	 * This method retrieves the Patient Revision Information on the Add load instance from the backend,
	 * constructs and returns a CtContactClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO
	 */
	public static void addLegacyLoadUtil(CTContactForm form, HttpServletRequest request,HttpServletResponse response, String personUid) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "addLegacyLoadUtil");
		try {
			form.clearAll();
			form.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			form.getAttributeMap().clear();
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			CTContactClientVO ctClientVO = new CTContactClientVO();
			form.setPageFormCd(NBSConstantUtil.CONTACT_REC);
			String formCd = NBSConstantUtil.CONTACT_REC;
			loadQuestions(NBSConstantUtil.CONTACT_REC);
			form.getAttributeMap().put("header", "Add Contact Record");
			form.getAttributeMap().put(ACTION_PARAMETER, "AddContactSubmit");
			form.getAttributeMap().put("Submit", "Submit");

			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long mprUid = new Long(personUid);
			form.setMprUid(mprUid);
			String actionMode = form.getActionMode();
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			CTContactProxyVO proxoVO = new CTContactProxyVO();
			PersonVO personVO = new PersonVO();
			boolean newPat = false;
			if(mprUid>0){
				personVO = findMasterPatientRecord(mprUid, session);
				populatePatientSummary(form, proxoVO, personVO, userId, conditionCd, newPat, request);
			}else{
				newPat = true;
				populatePatientSummary(form, proxoVO, personVO, userId, conditionCd, newPat,request);
			}
			ClientUtil.setPatientInformation(actionMode, personVO, ctClientVO, request, formCd);

			form.setcTContactClientVO(ctClientVO);

			setContactInfoForCreate(form, request.getSession());

			setUpdatedValues(form, request);

			form.setFormFieldMap(initiateForm(questionMap, form));

			// populate investigator of the investigation in contact popup
			String investigatorUid = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigatorUid);
			PersonVO investigatorPersonVO = new PersonVO();
			if(investigatorUid != null && !investigatorUid.equals(""))
			{
				investigatorPersonVO = prePopulateInvestigator(investigatorUid, nbsSecurityObj, request);
				if (investigatorPersonVO != null) {
					String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
					form.getAttributeMap().put("CON137Uid", uidSt);
					form.getAttributeMap().put("CONINV180Uid", uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					form.getAttributeMap().put("CON137SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
				}
			}

			setJurisdiction( form, session,nbsSecurityObj);
			form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
			form.getcTContactClientVO().setAnswer(CTConstants.DEM_DATA_AS_OF, StringUtils.formatDate(new Timestamp(new Date().getTime())));
			form.setPageTitle(NBSPageConstants.ADD_CONTACT, request);
			// prepopulate patient tab with the search criteria, while adding a new patient
			Map<Object,Object> searchMap = new HashMap<Object, Object>();
			if(request.getParameter("addNew")!= null && request.getParameter("addNew").equalsIgnoreCase("true"))
			{
				searchMap = (Map<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientMap);
				if(!searchMap.isEmpty()&& searchMap.size()>0)
					prePopulatePatientTab(searchMap, ctClientVO);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "addLoadUtil", start);
			//for SRT dropdown values
		    Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			request.setAttribute("addTime", currentTime);
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}


	/**
	 * This method retrieves Page information and the Patient Revision Information on the Add load instance from the backend,
	 * Assumes pageFormCd is set in the form. Fills in the answer map.
	 * @param form
	 * @param request
	 * @return void
	 */
	public static void addContactPageLoadUtil(CTContactForm form, HttpServletRequest request, HttpServletResponse response, String personUid) throws Exception {

		try {
			form.clearAll();
			form.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			CTContactClientVO ctClientVO = new CTContactClientVO();
			String formCd = form.getPageFormCd();
			PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
			pageLoadUtil.loadQuestions(formCd);
			form.getAttributeMap().put("header", "Add Contact Record");
			form.getAttributeMap().put(ACTION_PARAMETER, "AddContactSubmit");
			form.getAttributeMap().put("Submit", "Submit");

			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Long mprUid = new Long(personUid);
			form.setMprUid(mprUid);

			String actionMode = form.getActionMode();
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			CTContactProxyVO proxoVO = new CTContactProxyVO();
			PersonVO personVO = new PersonVO();
			boolean newPat = false;
			if(mprUid>0){
				personVO = findMasterPatientRecord(mprUid, session);
				populatePatientSummary(form, proxoVO, personVO, userId, conditionCd, newPat, request);
			}else{
				newPat = true;
				populatePatientSummary(form, proxoVO, personVO, userId, conditionCd, newPat,request);
			}
			ClientUtil.setPatientInformation(actionMode, personVO, ctClientVO, request, formCd);
			if(!nbsSecurityObj.getPermission(NBSBOLookup.GLOBAL,NBSOperationLookup.HIVQUESTIONS)){
				pageLoadUtil.handlePatientHIVQuestions((ClientVO) ctClientVO, personVO);
			}
			form.setcTContactClientVO(ctClientVO);

			setContactInfoForCreate(form, request.getSession());

			setUpdatedValues(form, request);

			//setFormFieldMap
			PageRulesGenerator reUtils = new PageRulesGenerator();
			form.setFormFieldMap(reUtils.initiateForm(pageLoadUtil.getQuestionMap(),  (BaseForm) form, (ClientVO) form.getcTContactClientVO()));

			// populate investigator of the investigation in contact popup
			String investigatorUid = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigatorUid);
			PersonVO investigatorPersonVO = null;
			if(investigatorUid != null && !investigatorUid.equals(""))
				investigatorPersonVO = prePopulateInvestigator(investigatorUid, nbsSecurityObj, request);
			else
				investigatorPersonVO = PageManagementCommonActionUtil.getDefaultInvestigatorIfPresent(session, nbsSecurityObj);
			if (investigatorPersonVO != null) {
					String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
					form.getAttributeMap().put("CON137Uid", uidSt);
					form.getAttributeMap().put("CONINV180Uid", uidSt);
					QuickEntryEventHelper helper = new QuickEntryEventHelper();
					form.getAttributeMap().put("CON137SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
			}
			String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			PageManagementCommonActionUtil.setJurisdiction( (BaseForm) form, NBSBOLookup.CT_CONTACT, programAreaCd, conditionCd, nbsSecurityObj);
			form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
			form.getcTContactClientVO().setAnswer(PageConstants.DEM_DATA_AS_OF, StringUtils.formatDate(new Timestamp(new Date().getTime())));
			form.setPageTitle(NBSPageConstants.ADD_CONTACT, request);
			// pre-populate patient tab with the search criteria, while adding a new patient
			Map<Object,Object> searchMap = new HashMap<Object, Object>();
			if(request.getParameter("addNew")!= null && request.getParameter("addNew").equalsIgnoreCase("true"))
			{
				searchMap = (Map<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientMap);
				if(!searchMap.isEmpty()&& searchMap.size()>0)
					prePopulatePatientTab(searchMap, ctClientVO);
			}
			form.setBatchEntryMap(new HashMap<Object,ArrayList<BatchEntry>>());
			pageLoadUtil.populateBatchRecords(form, formCd,
					request.getSession(), new HashMap());

			ClientUtil.setPersonIdDetails(personVO, form);
			//to do batch...
			Map<Object,Object> subbatchMap = pageLoadUtil.findBatchRecords(form.getPageFormCd(),request.getSession());
			request.setAttribute("SubSecStructureMap", subbatchMap);
			boolean stdProgramArea = PropertyUtil.isStdOrHivProgramArea(programAreaCd) ;
			form.setStdContact(stdProgramArea);

			Long personUID  = personVO.getThePersonDT().getPersonParentUid();
			if(stdProgramArea && personUID !=null){
				 request.setAttribute("viewInves", String.valueOf(stdProgramArea));
				 NBSContext.store(request.getSession(), "viewInves", String.valueOf(stdProgramArea));
				 String recordSearchDisposition = loadSupplimentalInvestigationInfo(form, personUID, conditionCd, request);
				 //if there is a record search closure candidate, indicate the disposition
				 if (recordSearchDisposition != null)
					 form.getcTContactClientVO().getAnswerMap().put(CTConstants.Disposition, recordSearchDisposition);
				 //in the future, we should be adding a contact from the interview.
				 form.getAttributeMap().put(CTConstants.StdInterviewSelectedKey, form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamedDuring));
				 //Added for co-infection
				 ArrayList<Object> coinfectionInvList = pageLoadUtil.getCoinfectionInvList(conditionCd, personUID, request);
				 
				
					
					
					
				 if((coinfectionInvList!=null && coinfectionInvList.size()>0)){
					form.getAttributeMap().put("coInfectionInv", true);
					form.getAttributeMap().put(NEDSSConstants.CONDITION_CD, conditionCd);
				 }

			}
			if(stdProgramArea){
				 // On a create, the interview list should be in the context
				 form.getAttributeMap().put("InterviewList", (ArrayList<Object>)  NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInterviewList));
				 String lotNbr = (String) NBSContext.retrieve(session, NBSConstantUtil.DSLotNbr);
				 if (lotNbr != null)
					 form.getcTContactClientVO().setAnswer(CTConstants.ContactFFLotNbr, lotNbr);
			}
			//for SRT dropdown values
		    Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			request.setAttribute("addTime", currentTime);

		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}


	private static void prePopulatePatientTab(Map<Object, Object>searchMap, CTContactClientVO ctClientVO)
	{
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "prePopulatePatientTab");
		try {
			Map<Object,Object> answerMap = ctClientVO.getAnswerMap();
			answerMap.put(PamConstants.LAST_NM, (String)searchMap.get("LASTNM"));
			answerMap.put(PamConstants.FIRST_NM, (String)searchMap.get("FIRSTNM"));
			answerMap.put(PamConstants.DOB,searchMap.get("DOB").toString());
			answerMap.put(PamConstants.CURR_SEX,(String)searchMap.get("SEX"));
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "prePopulatePatientTab", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static PersonVO prePopulateInvestigator(String investigatorUid, NBSSecurityObj nbsSecurityObj, HttpServletRequest request)throws NEDSSAppConcurrentDataException, Exception
	{
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "prePopulateInvestigator");
		HttpSession session = request.getSession();
		PersonVO person = null;
		try {
			MainSessionCommand msCommand = null;
			Long UID = new Long(investigatorUid.trim());
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getProvider";
			Object[] oParams = new Object[] {UID};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
					sMethod, oParams);
			person = (PersonVO) arr.get(0);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "prePopulateInvestigator", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

		return person;

	}

	public static void loadQuestions(String invFormCd){
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "loadQuestions");
		try {
			if(QuestionsCache.getContactQuestionMap()!=null)
				questionMap = (Map<?, ?> )QuestionsCache.getContactQuestionMap().get(invFormCd);
			else
				questionMap = new HashMap<Object,Object>();
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "loadQuestions", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Retrieves Master Patient Record Information
	 * @param mprUId
	 * @param session
	 * @return
	 */
	private static PersonVO findMasterPatientRecord(Long mprUId, HttpSession session) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "findMasterPatientRecord");

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
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "findMasterPatientRecord", start);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}

			logger.fatal("personVO: ", ex);
		}
		return personVO;
	}



	private static void setContactInfoForCreate(CTContactForm form, HttpSession session) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setContactInfoForCreate");
		try {
			Map<Object,Object> answerMap = new HashMap<Object,Object>();

			String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			String jurisdiction =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationJurisdiction);
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			answerMap.put(CTConstants.Status, NEDSSConstants.STATUS_OPEN);
			answerMap.put(CTConstants.JURISDICTION_CD,jurisdiction);
			form.getAttributeMap().put("NBSSecurityJurisdictions",jurisdiction);
			answerMap.put(CTConstants.PROGRAM_AREA, programAreaCd);
			answerMap.put(CTConstants.SHARED_IND, "1");
			
			//In STD Contact, disposition Code drives the Referral Basis Options
			try {
				String dispositionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdDispositionCd);
				if (dispositionCd != null)
					form.getAttributeMap().put(CTConstants.SourceDispositionCd, dispositionCd);
				String interviewStatusCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdInterviewStatusCd);
				if (interviewStatusCd != null)
					form.getAttributeMap().put(CTConstants.SourceInterviewStatusCd, interviewStatusCd);
				String currentSexCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSPatientCurrentSexCd);
				if (currentSexCd != null)
					form.getAttributeMap().put(CTConstants.SourceCurrentSexCd, currentSexCd);
			} catch (Exception ex) {
				logger.debug("Context exception related STD session variables which will not be in non-STD from NBSContext Object Store "
						+ ex.getMessage());
			}
			
			if (conditionCd != null)
				form.getAttributeMap().put(CTConstants.SourceConditionCd, conditionCd);			
			form.getcTContactClientVO().getAnswerMap().putAll(answerMap);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setContactInfoForCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private static void setUpdatedValues(CTContactForm form, HttpServletRequest req) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setUpdatedValues");
		try {
			Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();
			if(answerMap != null && answerMap.size() > 0) {
				 String stateCd = (String) answerMap.get(PamConstants.STATE);
				 form.getOnLoadCityList(stateCd, req);
				 form.getDwrCountiesForState(stateCd);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setUpdatedValues", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param questionMap
	 * @param form
	 * @return
	 */
	public static Map<Object,Object> initiateForm(Map<?,?> questionMap, CTContactForm form) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "initiateForm");
		Map<Object,Object> answerMap =  form.getcTContactClientVO().getAnswerMap();
		Map<Object,Object> answerArrayMap = form.getcTContactClientVO().getArrayAnswerMap();
		String actionMode = form.getActionMode();
		String formCd = form.getPageFormCd();

		Map<Object,Object> formFieldMap = new HashMap<Object,Object>();
		try {
			//String formCd = null;
			if (questionMap != null && questionMap.size() > 0) {
				Collection<?>  qColl = questionMap.values();
				Iterator<?> ite = qColl.iterator();
				while (ite.hasNext()) {
					FormField fField = new FormField();
					NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite
					.next();
					if(qMetadata.getAList() != null)
						fField.setAList(qMetadata.getAList());
					//				if(formCd == null)
					//					formCd = qMetadata.getInvestigationFormCd();
					if(actionMode != null && actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION))
						fField.setDefaultValue(qMetadata.getDefaultValue());
					if(answerMap.get(qMetadata.getQuestionIdentifier())==null && actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION)){
						fField.setValue(qMetadata.getDefaultValue());
						answerMap.put(qMetadata.getQuestionIdentifier(), qMetadata.getDefaultValue());
					}
					else
						fField.setValue(answerMap
								.get(qMetadata.getQuestionIdentifier()));
					fField.setFieldId(qMetadata.getQuestionIdentifier());
					if(qMetadata.getQuestionIdentifier()== null)
						fField.setFieldId(qMetadata.getNbsUiMetadataUid().toString());


					try {
						if (answerArrayMap != null && fField.getFieldId() != null
								&& answerArrayMap.containsKey(fField.getFieldId())) {
							if (answerArrayMap.get(fField.getFieldId()) != null) {
								List<Object> multiSelVals = new ArrayList<Object> ();
								String[] answers = (String[]) answerArrayMap
								.get(fField.getFieldId());
								if (answers != null && answers.length > 0) {
									for (int i = 0; i < answers.length; i++) {
										if(answers[i] != null){
											multiSelVals.add(answers[i]);

										}
									}

									fField.getState().setMultiSelVals(multiSelVals);
								}
							}
						}
					} catch (Exception e) {
						logger.error("Error in initiate form ..answerArrayMap");
						e.printStackTrace();
					}
					//Autocomplete
					fField.setFieldAutoCompId(qMetadata.getQuestionIdentifier() + "_textbox");
					fField.setFieldAutoCompBtn(qMetadata.getQuestionIdentifier() + "_button");
					fField.setLabel(qMetadata.getQuestionLabel());
					fField.setTooltip(qMetadata.getQuestionToolTip());
					fField.setFieldType(qMetadata.getDataType());
					fField.setTabId(qMetadata.getTabId());
					fField.setCodeSetNm(qMetadata.getCodeSetNm());
					if (qMetadata.getEnableInd() != null
							&& qMetadata.getEnableInd().equals("F"))
						fField.getState().setEnabled(false);
					else
						fField.getState().setEnabled(true);
					if(qMetadata.getRequiredInd() != null){
						if(qMetadata.getRequiredInd().equals("T")){
							fField.getState().setRequired(true);
							fField.getState().setRequiredIndClass(CTConstants.REQUIRED_FIELD_CLASS);
						}else if((qMetadata.getRequiredInd()!= null && qMetadata.getRequiredInd().equals("TE")) && ((actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))||(actionMode.equals(NEDSSConstants.EDIT_LOAD_ACTION)))){
							fField.getState().setRequired(true);
							fField.getState().setRequiredIndClass(CTConstants.REQUIRED_FIELD_CLASS);
						}else// if(qMetadata.getRequiredInd().equals("F")){
						{
							fField.getState().setRequired(false);
							fField.getState().setRequiredIndClass(CTConstants.NOT_REQUIRED_FIELD_CLASS);
						}
					}
					if(qMetadata.getFutureDateInd() != null)
					{
						if(qMetadata.getFutureDateInd().equals("T"))

							fField.getState().setFutureDtInd(true);
						else
							fField.getState().setFutureDtInd(false);
					}
					// This is for the hide and show logic of the fields in the page
					if(qMetadata.getDisplayInd() != null){
						if(qMetadata.getDisplayInd().equals("T")){
							fField.getState().setVisible(true);
						}
						else
							fField.getState().setVisible(false);
					}
					if(qMetadata.getQuestionRequiredNnd() != null && qMetadata.getQuestionRequiredNnd().equals("R")){
						fField.setQuestionRequiredNnd(qMetadata.getQuestionRequiredNnd());
					}
					ArrayList<?> aList = (ArrayList<?> )CachedDropDowns.getCodedValueForType(qMetadata
							.getCodeSetNm()).clone();
					fField.getState().setValues(aList);

					formFieldMap.put(qMetadata.getQuestionIdentifier(), fField);
				}
			}
			CTRulesEngineUtil ctUtil = new CTRulesEngineUtil();
			if ((formCd != null)
					&& formFieldMap != null
					&& formFieldMap.size() > 0
					&& actionMode != null
					&& (actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION) || actionMode
							.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))) {

				// this.getRequiredField(fieldColl, formFieldMap);
				ctUtil.fireRules(null,  form, formFieldMap);
			}
			if (formFieldMap != null && formFieldMap.size() > 0
					&& actionMode != null
					&& !actionMode.equals(NEDSSConstants.VIEW_LOAD_ACTION)) {

				Set<Object> fieldKeys = formFieldMap.keySet();
				Iterator<Object> fIte = fieldKeys.iterator();
				Map<Object,Object> onChgRuleMap = QuestionsCache.getRuleMap();
				Map<?,?> ruleMap = (TreeMap<?,?>) onChgRuleMap.get(formCd);
				while (fIte.hasNext()) {
					String fieldId = (String) fIte.next();
					if (fieldId != null && ruleMap != null
							&& ruleMap.containsKey(fieldId))
						ctUtil.fireRules((String) fieldId, form, formFieldMap);
				}
			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "initiateForm", start);
		return formFieldMap;
	}

	/**
	 * createHandler
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static CTContactProxyVO createHandler(CTContactForm form, HttpServletRequest req) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "createHandler");
		CTContactProxyVO proxyVO = new CTContactProxyVO();
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			HttpSession session = req.getSession();
			Long tempID = -1L;
			handleFormRules(form,NEDSSConstants.CREATE_SUBMIT_ACTION);
			if(form.getErrorList()!=null && form.getErrorList().size()>0){
					form.setPageTitle(NBSPageConstants.ADD_CONTACT, req);
			}
			else if (form.getErrorList()==null || form.getErrorList().size()==0) {

				String sCurrentTask = NBSContext.getCurrentTask(req.getSession());
				//persist CtContactVO
				tempID = setCtContactVOForCreate(tempID, form, req, proxyVO,  userId);

				//persist PatientRevision from Answers
				Long patientUid = form.getMprUid();
				setPatientForEventCreate(patientUid, tempID, proxyVO, form, req, userId);


				//persist all answers
				setContactSpecifcAnswersForCreateEdit(proxyVO, form, session, userId);

				// contact Specific Participations
				int tempEntityID = -1;
				setEntitiesForCreateEdit(form, proxyVO, tempID, "0", userId);
				setEntitiesForCreate(proxyVO, form, proxyVO.getcTContactVO().getcTContactDT().getCtContactUid(),userId, req);
				// send Proxy to EJB To Persist
				proxyVO.setItNew(true);
				proxyVO.setItDirty(false);
				Long ctContactUid = sendProxyToEJB(proxyVO, req, sCurrentTask);
				if(ctContactUid!=null)
					NBSContext.store(session, NBSConstantUtil.DSContactUID, ctContactUid.toString());
			}
			req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "createHandler", start);
		return proxyVO;
	}

	/**
	 * createPageHandler
	 * @param CTContactForm
	 * @param request
	 * @throws Exception
	 */
	public static CTContactProxyVO createPageHandler(CTContactForm form,
			HttpServletRequest req) throws Exception {
		PageProxyVO pgProxyVO =null;
		CTContactProxyVO proxyVO = new CTContactProxyVO();
		
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession()
					.getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser()
					.getEntryID();
			Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser()
					.getProviderUid();
			HttpSession session = req.getSession();
			PageCreateHelper pch  = new PageCreateHelper();
			pch.loadQuestions(form.getPageFormCd());
			pch.handleFormRules((BaseForm) form,
					(ClientVO) form.getcTContactClientVO(),
					NEDSSConstants.CREATE_SUBMIT_ACTION);
			if (form.getErrorList() != null && form.getErrorList().size() > 0)
				form.setPageTitle(NBSPageConstants.ADD_CONTACT, req);
			if (form.getErrorList() == null || form.getErrorList().size() == 0) {
				String sCurrentTask = NBSContext.getCurrentTask(req
						.getSession());
				Long tempID = -1L;
				proxyVO.setItNew(true);
				proxyVO.setItDirty(false);
				tempID = setCtContactVOForCreate(tempID, form, req, proxyVO,
						userId);
				// persist PatientRevision from Answers
				Long patientUid = form.getMprUid();
				setPatientForEventPageCreate(patientUid, tempID, proxyVO, form,
						req, userId);

				proxyVO.getcTContactVO()
						.setCtContactAnswerDTMap(pch
								.setPageSpecifcAnswersForCreateEdit(form,
										(ClientVO) form.getcTContactClientVO(),
										session, userId));
				setEntitiesForCreateEdit(form, proxyVO, tempID, "0", userId);
				setEntitiesForCreate(proxyVO, form, proxyVO.getcTContactVO()
						.getcTContactDT().getCtContactUid(), userId, req);
				proxyVO.getcTContactVO().setRepeatingAnswerDTMap(
						pch.setRepeatingQuestionsBatch(form,
								form.getPageFormCd(), userId, providerUid));
				//On STD see if we need to set the associated contact investigation if Record Search Closure
				 boolean stdProgramArea = PropertyUtil.isStdOrHivProgramArea( proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd()) ;
				 if (stdProgramArea
						 && proxyVO.getcTContactVO().getcTContactDT().getContactEntityPhcUid() == null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd() != null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd().equalsIgnoreCase(CTConstants.RecordSearchClosure)) {
					if (form.getAttributeMap().containsKey(CTConstants.RecordSearchClosureInvestigation)){
							proxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid((Long)form.getAttributeMap().get(CTConstants.RecordSearchClosureInvestigation));
							//for record Search Closure set the contact epi link id to be the epi link id of record Search Closure investigation
							try{
							//subject lot number
							String lotNbr = (String) NBSContext.retrieve(session, NBSConstantUtil.DSLotNbr);
							InvestigationSummaryVO invSummVO = (InvestigationSummaryVO)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSRecordSearchClosureInvSummVO);
							proxyVO.getcTContactVO().getcTContactDT().setSubEntityEpilinkId(lotNbr);
							proxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(invSummVO.getEpiLinkId());
							proxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(invSummVO.getPublicHealthCaseUid());
							}catch(Exception ex){
								logger.info("InvestigationSummaryVO is not in the context for Record Search Closure Processing Decision");
							}
					}


				 } //stdProgArea set RSC Investigation
				 
				 //stdProgArea for SecondaryReferral	
				 if (stdProgramArea
						 && proxyVO.getcTContactVO().getcTContactDT().getContactEntityPhcUid() == null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd() != null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd().equalsIgnoreCase(CTConstants.SecondaryReferral)) {
						try{
							//Subject lot number
							String lotNbr = (String) NBSContext.retrieve(session, NBSConstantUtil.DSLotNbr);
							InvestigationSummaryVO invSummVO = (InvestigationSummaryVO)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSSecondaryReferralInvSummVO);
							proxyVO.getcTContactVO().getcTContactDT().setSubEntityEpilinkId(lotNbr);
							proxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(invSummVO.getEpiLinkId());
							proxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(invSummVO.getPublicHealthCaseUid());
						}catch(Exception ex){
							logger.info("InvestigationSummaryVO is not in the context for Secondary Referral Processing Decision");
						}
				 }  //SecondaryReferral
					 
					 				 
				 
				 if (stdProgramArea
						 && form.getcTContactClientVO().getAnswerMap().containsKey(CTConstants.NamingBetween)) {
						 String namingBetween = (String) form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamingBetween);
						 if (namingBetween.equals("OTHPAT")) {
							 	if (form.getAttributeMap().containsKey(CTConstants.StdThirdParthEntityUid)) {
							 		Long thirdPartyEntityUid = new Long ((String)form.getAttributeMap().get(CTConstants.StdThirdParthEntityUid));
							 		proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityUid(thirdPartyEntityUid);
							 	}
							 	if (form.getAttributeMap().containsKey(CTConstants.StdThirdParthEntityPhcUid)) {
							 		Long thirdPartyEntityPhcUid = new Long ((String)form.getAttributeMap().get(CTConstants.StdThirdParthEntityPhcUid));
							 		proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityPhcUid(thirdPartyEntityPhcUid);
							 	}

							 	//New Cluster & sec ref Message
							 	if(form.getAttributeMap().containsKey(CTConstants.StdThirdParthInvestigatorLocalId) ) {
								 	String	thirdParthInvestigatoMPRUid = (String)form.getAttributeMap().get(CTConstants.StdThirdParthInvestigatorLocalId);
								 	if(thirdParthInvestigatoMPRUid != null && providerUid != null &&
								 			Long.parseLong(thirdParthInvestigatoMPRUid) != providerUid.longValue()){
								 		Collection<InvestigationSummaryVO> investigationEventList;
									 		if(req.getSession().getAttribute("thirdPartyInvestigationSummaryVOCollection") != null){
									 			investigationEventList =(Collection<InvestigationSummaryVO>)req.getSession().getAttribute("thirdPartyInvestigationSummaryVOCollection");
									 			req.getSession().setAttribute("thirdPartyInvestigationSummaryVOCollection", null);
									 		}else{
									 			investigationEventList =(Collection<InvestigationSummaryVO>)NBSContext.retrieve(req.getSession(), "thirdPartyInvestigationSummaryVOCollection");
									 		}
								 			InvestigationSummaryVO invVo = new  InvestigationSummaryVO();
								 			Long phc2 = new Long((String)form.getAttributeMap().get(CTConstants.StdThirdParthEntityPhcUid));
								 			for(InvestigationSummaryVO vo :investigationEventList){
								 				Long  phc1 = vo.getPublicHealthCaseUid();
								 				if(phc1.longValue() == phc2.longValue())invVo = vo;

								 			}
								 			if(invVo != null){
//								 				String lotNbr = (String) NBSContext.retrieve(session, NBSConstantUtil.DSLotNbr);
//								 				if(! lotNbr.equals(invVo.getEpiLinkId()))
								 				 MessageLogUtil.createAddMessageLogDT(new Long(thirdParthInvestigatoMPRUid), proxyVO, nbsSecurityObj, invVo, MessageConstants.NEW_CLUSTER_TO_YOUR_CASE_KEY, MessageConstants.NEW_CLUSTER_TO_YOUR_CASE);

								 				MessageLogUtil.createAddMessageLogDT(new Long(thirdParthInvestigatoMPRUid), proxyVO, nbsSecurityObj, invVo, MessageConstants.NEW_SECONDARY_ADDED_KEY, MessageConstants.NEW_SECONDARY_ADDED);

								 			}
									 	}


							 	}

						 } else { //if THSPAT shouldn't be set
							 proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityUid(null);
							 proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityPhcUid(null);
						 }
				 } //Naming Between
				 if (stdProgramArea
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd() != null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd().equalsIgnoreCase(CTConstants.FieldFollowup)) {
					 pgProxyVO = stdPopulateInvestigationFromContact(form, req, proxyVO, nbsSecurityObj );
					 if(pgProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null){
						 // Subject and contact will have same lot number as subject
						 proxyVO.getcTContactVO().getcTContactDT().setSubEntityEpilinkId(pgProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId());
						 proxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(pgProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId());
					 }

				 }
				 String investigatorStr = (String) form.getAttributeMap().get(CTConstants.ContactFFInvestigatorUid);
				 Long investigatorid=0L;
				 if (investigatorStr != null && !investigatorStr.isEmpty()) {
						String uid = splitUid(investigatorStr);
						 investigatorid = new Long(uid);

					}
					String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
					logger.debug("createPageHandler - conditionCd is : " + conditionCd +"investigatorStr is : " + investigatorStr + "providerUid is  : "  + providerUid);
					Boolean isEq = Boolean.FALSE;
					if( providerUid == null ){
						isEq = Boolean.FALSE;
					}else{
						isEq = providerUid.equals(investigatorid);
					}
					if(investigatorStr != null && conditionCd != null && !isEq){
						MessageLogUtil.createNotificationForSecondaryReferral(req,conditionCd,investigatorid,proxyVO,nbsSecurityObj);
						
					}


				 Long ctContactUid = null;
				 if(pgProxyVO!=null) {
					 Object[] oParams = new Object[] { NEDSSConstants.CONTACT_AND_CASE, proxyVO, pgProxyVO};
						String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
						String sMethod = "setAutoContactPageProxyVO";
						ctContactUid = (Long)CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, req.getSession());
						if(ctContactUid!=null)
							NBSContext.store(session, NBSConstantUtil.DSContactUID, ctContactUid.toString());
				 }else {
					 ctContactUid = sendProxyToEJB(proxyVO, req, sCurrentTask);
				 }
				if (ctContactUid != null)
					NBSContext.store(session, NBSConstantUtil.DSContactUID,
							ctContactUid.toString());
			}

			req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return proxyVO;
	}





	private static void setEntitiesForCreateEdit(CTContactForm form, CTContactProxyVO proxyVO, Long revisionPatientUID, String versionCtrlNbr, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setEntitiesForCreateEdit");
		Long contactUID = proxyVO.getcTContactVO().getcTContactDT().getCtContactUid();

		Collection<Object>  entityColl = new ArrayList<Object> ();
		int vcNum = Integer.valueOf(versionCtrlNbr).intValue() + 1;
		// patient PHC participation
		NbsActEntityDT entityDT = createContactEntity(contactUID, String.valueOf(revisionPatientUID), String.valueOf(vcNum), NEDSSConstants.CONTACT_ENTITY, userId);
		//For update
		if(entityDT.getActUid().longValue() > 0) {
			entityDT.setItNew(false);
			entityDT.setItDirty(true);
			entityDT.setItDelete(false);
			NbsActEntityDT oldDT = getNbsCaseEntity(NEDSSConstants.CONTACT_ENTITY,form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getActEntityDTCollection() );
			entityDT.setNbsActEntityUid(oldDT.getNbsActEntityUid());

		}
		entityColl.add(entityDT);

		proxyVO.getcTContactVO().setActEntityDTCollection(entityColl);
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setEntitiesForCreateEdit", start);
	}
	private static NbsActEntityDT getNbsCaseEntity(String typeCd, Collection<Object>  entityDTCollection) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "getNbsCaseEntity");
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
    	NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "getNbsCaseEntity", start);
    	return null;
    }

	public static NbsActEntityDT createContactEntity(Long actUid, String subjectEntityUid, String versionCtrlNbr, String typeCd, String userId) {

		NbsActEntityDT entityDT = new NbsActEntityDT();
		entityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		entityDT.setAddUserId(Long.valueOf(userId));
		entityDT.setEntityUid(Long.valueOf(subjectEntityUid));
		entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
		entityDT.setActUid(actUid);
		entityDT.setTypeCd(typeCd);
		return entityDT;
	}

	public static Long sendProxyToEJB(CTContactProxyVO proxyVO, HttpServletRequest request, String sCurrentTask) throws NEDSSAppConcurrentDataException, Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "sendProxyToEJB");
		HttpSession session = request.getSession();
		Long ctContactUid = null;
		try {
			MainSessionCommand msCommand = null;
			Long publicHealthCaseUID = null;
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();

			Object[] oParams = { proxyVO };
			String sMethod = "setContactProxyVO";
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				ctContactUid = (Long) resultUIDArr.get(0);
			}
			if(ctContactUid!=null)
				NBSContext.store(session, NBSConstantUtil.DSContactUID, ctContactUid.toString());
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	 	NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "sendProxyToEJB", start);
		return ctContactUid;
	}

	/*
	 *  Call the EJB to get the list of Interviews associated with the public health case.
	 *  Used by STD Contact to build the list of interviews you can associated the contact with.
	 *  Selected interview is stored in the ct_contact.namedDuringInterviewUid field.
	 */
	public static Collection<Object> getInterviewSummary(HttpSession session,Long publicHealthCaseUid, String programArea)throws Exception{
		Collection<Object>  interviewColl = new ArrayList<Object> ();
		MainSessionCommand msCommand = null;

		try{
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getInterviewSummaryforInvestigation";
			Object[] oParams =new Object[] {publicHealthCaseUid,programArea};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				interviewColl = (Collection)aList.get(0);
			}catch (Exception ex) {
				logger.fatal("Error in getInterviewSummary in Contact Action: ", ex);
				throw new Exception(ex.toString());
			}
			 return interviewColl;

	}


	/**
	 * setPatientForEventPageCreate - calls PageCreateHelper.setPatientForEventCreate
	 * Handles the Contact demographic information (names, addresses, race telephone, etc.)
	 * @param patientUid
	 * @param tempId
	 * @param proxyVO
	 * @param form
	 * @param request
	 * @param userid
	 */
	private static void setPatientForEventPageCreate(Long patientUid, Long tempID, CTContactProxyVO proxyVO, CTContactForm form, HttpServletRequest req, String userId) {
		PersonVO personVO = new PersonVO();

		personVO.getThePersonDT().setItNew(true);
		personVO.getThePersonDT().setPersonParentUid(patientUid);
		personVO.getThePersonDT().setPersonUid(new Long(tempID));
		personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
		personVO.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
		personVO.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		personVO.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);

		//persist PersonVO Components
		PageCreateHelper.setPatientForEventCreate(personVO, (ClientVO) form.getcTContactClientVO(), userId);
		personVO.getThePersonDT().setItNew(true);
		personVO.getThePersonDT().setItDirty(false);
		personVO.setItNew(true);
		personVO.setItDirty(false);
		proxyVO.setContactPersonVO(personVO);
		form.getcTContactProxyVO().setContactPersonVO(personVO);

	}

	private static void setPatientForEventCreate(Long patientUid, Long tempID, CTContactProxyVO proxyVO, CTContactForm form, HttpServletRequest req, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventCreate");
		PersonVO personVO = new PersonVO();
		Collection<Object>  patientColl = new ArrayList<Object> ();

		personVO.getThePersonDT().setItNew(true);
		personVO.getThePersonDT().setPersonParentUid(patientUid);
		personVO.getThePersonDT().setPersonUid(new Long(tempID));
		personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
		personVO.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
		personVO.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		personVO.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);

		//persist PersonVO Components
		setPatientForEventCreate(personVO, form, proxyVO, userId);
		personVO.getThePersonDT().setItNew(true);
		personVO.getThePersonDT().setItDirty(false);
		personVO.setItNew(true);
		personVO.setItDirty(false);
		proxyVO.setContactPersonVO(personVO);
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventCreate", start);
	}

	private static void setPatientForEventCreate(PersonVO personVO, CTContactForm form, CTContactProxyVO proxyVO, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventCreate");
		Map<Object,Object> aodMap = new HashMap<Object,Object>();

		try {
			setDemographicInfoForCreate(aodMap, personVO, form.getcTContactClientVO().getAnswerMap());
			setNames(aodMap, personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			setAddressesForCreate(aodMap, personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			setEthnicityForCreate(personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			setRaceForCreate(personVO, form.getcTContactClientVO(), proxyVO, userId);
			setTelephones(aodMap, personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			form.getcTContactClientVO().getAnswerMap().putAll(aodMap);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}
	private static void setDemographicInfoForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setDemographicInfoForCreate");
		try {
			String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
			//General Comments
			String generalComments = answerMap.get(PamConstants.GEN_COMMENTS) == null ? null : (String) answerMap.get(PamConstants.GEN_COMMENTS);
			if(generalComments != null && generalComments.trim().length() > 0) {
				personVO.getThePersonDT().setDescription(generalComments);
				personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);
				aodMap.put(PamConstants.DEM_DATA_AS_OF, asOfDate);
			}
			//Sex and BirthInfoAsOf
			String dob = getVal(answerMap.get(PamConstants.DOB));
			String repAge = getVal(answerMap.get(PamConstants.REP_AGE));
			String bSex = getVal(answerMap.get(PamConstants.BIRTH_SEX));
			String cSex = getVal(answerMap.get(PamConstants.CURR_SEX));

			if(dob != "" || repAge != "" || bSex != "" || cSex != "") {
				setCommonDemographicInfo(personVO, answerMap);
				personVO.getThePersonDT().setAsOfDateSex_s(asOfDate);
				aodMap.put(PamConstants.SEX_AND_BIRTH_INFORMATION_AS_OF, asOfDate);
			}

			//Mortality InfoAsOf
			String deceased = answerMap.get(PamConstants.IS_PAT_DECEASED) == null ? null : (String) answerMap.get(PamConstants.IS_PAT_DECEASED);
			if(deceased != null && deceased.trim().length() > 0) {
				personVO.getThePersonDT().setDeceasedIndCd(deceased);
				personVO.getThePersonDT().setAsOfDateMorbidity_s(asOfDate);
				aodMap.put(PamConstants.MORTALITY_INFORMATION_AS_OF, asOfDate);
			}

			//MARITAL_STATUS_AS_OF
			String marital = answerMap.get(PamConstants.MAR_STAT) == null ? null : (String) answerMap.get(PamConstants.MAR_STAT);
			String priOccupation = answerMap.get(PamConstants.PRIMARY_OCCUPATION) == null ? null : (String) answerMap.get(PamConstants.PRIMARY_OCCUPATION);
			String priLanguage = answerMap.get(PamConstants.PRIMARY_LANGUAGE) == null ? null : (String) answerMap.get(PamConstants.PRIMARY_LANGUAGE);
			String speaksEnglish = answerMap.get(PageConstants.SPEAKS_ENGLISH) == null ? null : (String) answerMap.get(PageConstants.SPEAKS_ENGLISH);
			if((marital != null && marital.trim().length() > 0) || (priOccupation != null && priOccupation.trim().length() > 0) || (priLanguage != null && priLanguage.trim().length() > 0) || (speaksEnglish != null && speaksEnglish.trim().length() > 0)) {
				personVO.getThePersonDT().setMaritalStatusCd(marital);
				personVO.getThePersonDT().setPrimLangCd(priLanguage);
				personVO.getThePersonDT().setOccupationCd(priOccupation);
				personVO.getThePersonDT().setSpeaksEnglishCd(speaksEnglish);
				personVO.getThePersonDT().setAsOfDateGeneral_s(asOfDate);
				aodMap.put(PamConstants.MARITAL_STATUS_AS_OF, asOfDate);
			}
			//EthnicityAsOf and Unknown Specify
			String ethnicity = answerMap.get(PamConstants.ETHNICITY) == null ? null : (String) answerMap.get(PamConstants.ETHNICITY);
			String unknownSpecify = answerMap.get(PageConstants.ETHNICITY_UNK_REASON) == null ? null : (String) answerMap.get(PageConstants.ETHNICITY_UNK_REASON);
			if((ethnicity != null && ethnicity.trim().length() > 0) || (unknownSpecify != null && unknownSpecify.trim().length() > 0)) {
				personVO.getThePersonDT().setEthnicGroupInd(ethnicity);
				personVO.getThePersonDT().setEthnicUnkReasonCd(unknownSpecify);
				personVO.getThePersonDT().setAsOfDateEthnicity_s(asOfDate);
				aodMap.put(PamConstants.ETHNICITY_AS_OF, asOfDate);
			}

			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setDemographicInfoForCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void setCommonDemographicInfo(PersonVO personVO, Map<Object,Object> answerMap) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setCommonDemographicInfo");
		try {
			String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
			personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);

			personVO.getThePersonDT().setBirthTime(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(PamConstants.DOB))));
			if(personVO.getThePersonDT().getBirthTime() != null) {
				personVO.getThePersonDT().setBirthTimeCalc(personVO.getThePersonDT().getBirthTime());
			}
			personVO.getThePersonDT().setAgeReported(getVal(answerMap.get(PamConstants.REP_AGE)));
			personVO.getThePersonDT().setAgeReportedUnitCd(getVal(answerMap.get(PamConstants.REP_AGE_UNITS)));
			personVO.getThePersonDT().setDeceasedTime(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(PamConstants.DECEASED_DATE))));
			personVO.getThePersonDT().setBirthGenderCd(getVal(answerMap.get(PamConstants.BIRTH_SEX)));
			personVO.getThePersonDT().setCurrSexCd(getVal(answerMap.get(PamConstants.CURR_SEX)));
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setCommonDemographicInfo", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static void setNames(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setNames");
		try {
			Long personUID = personVO.getThePersonDT().getPersonUid();
			String lastNm = getVal(answerMap.get(PamConstants.LAST_NM));
			String firstNm = getVal(answerMap.get(PamConstants.FIRST_NM));
			String middleNm = getVal(answerMap.get(PamConstants.MIDDLE_NM));
			String suffix = getVal(answerMap.get(PamConstants.SUFFIX));
			String alias = getVal(answerMap.get(PamConstants.ALIAS_NICK_NAME));
			String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));


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
						aodMap.put(PamConstants.NAME_INFORMATION_AS_OF, asOfDate);

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

					pdts.add(pdt);

				}

				if (alias != null && !alias.trim().equals("")) {

					if(aodMap != null)
						aodMap.put(PamConstants.NAME_INFORMATION_AS_OF, asOfDate);

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
					pdts.add(pAliasDt);

				}
				personVO.setThePersonNameDTCollection(pdts);

			}else {
				answerMap.remove(PamConstants.NAME_INFORMATION_AS_OF);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setNames", start);
		} catch (NumberFormatException e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}


	}

	private static void setAddressesForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setAddressesForCreate");
		try {
			Collection<Object>  arrELP = personVO.
			getTheEntityLocatorParticipationDTCollection();
			if (arrELP == null) {
				arrELP = new ArrayList<Object> ();

			}
			Long personUID = personVO.getThePersonDT().getPersonUid();

			String city = getVal(answerMap.get(PamConstants.CITY));
			String street1 = getVal(answerMap.get(PamConstants.ADDRESS_1));
			String street2 = getVal(answerMap.get(PamConstants.ADDRESS_2));
			String zip = getVal(answerMap.get(PamConstants.ZIP));
			String state = getVal(answerMap.get(PamConstants.STATE));
			String token = null;
			if(state.equals("nedss.properties:NBS_STATE_CODE"))
			{
				/* StringTokenizer st = new StringTokenizer(state, ":");
				 while (st.hasMoreTokens())
				    {
					 token = st.nextToken();
				    }
				 state = PropertyUtil.getInstance().getNBS_STATE_CODE();*/
				 state = "";
			}

			String county = getVal(answerMap.get(PamConstants.COUNTY));
			String country = getVal(answerMap.get(PamConstants.COUNTRY));
			String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
			String cityLimits = getVal(answerMap.get(PamConstants.WITHIN_CITY_LIMITS));
			String birthCuntry = getVal(answerMap.get(PamConstants.BIRTH_COUNTRY));

			if ( (city != null && !city.equals("")) ||
					(street1 != null && !street1.equals("")) ||
					(street2 != null && !street2.equals("")) ||
					(zip != null && !zip.equals("")) ||
					(county != null && !county.equals("")) ||
					(state != null && !state.equals("")) ||
					(cityLimits != null && !cityLimits.equals("")))
			{
				aodMap.put(PamConstants.ADDRESS_INFORMATION_AS_OF, asOfDate);

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
				pl.setStreetAddr1(street1);
				pl.setStreetAddr2(street2);
				pl.setZipCd(zip);
				pl.setStateCd(state);
				pl.setCityDescTxt(city);
				pl.setCntyCd(county);
				pl.setCntryCd(country);
				pl.setWithinCityLimitsInd(cityLimits);
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

				elp.setThePostalLocatorDT(pl);
				arrELP.add(elp);

			}

			if(birthCuntry != null && !birthCuntry.equals(""))
			{
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
				elp.setUseCd(NEDSSConstants.BIRTH);
				elp.setAsOfDate_s(asOfDate);

				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(new Timestamp(new Date().getTime()));
				pl.setAddUserId(Long.valueOf(userId));
				pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
				pl.setCntryCd(birthCuntry);
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

				elp.setThePostalLocatorDT(pl);
				arrELP.add(elp);
			}

			logger.info("Number of address in setBasicAddresses: " + arrELP.size());
			personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setAddressesForCreate", start);
		} catch (NumberFormatException e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void setEthnicityForCreate(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		try {
			Collection<Object>  ethnicityColl = new ArrayList<Object> ();
			personVO.setThePersonEthnicGroupDTCollection(ethnicityColl);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void setRaceForCreate(PersonVO personVO, CTContactClientVO clientVO, CTContactProxyVO proxyVO, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setRaceForCreate");
		try {
			Long aPersonUid = personVO.getThePersonDT().getPersonUid();

			String asOfDate = getVal(clientVO.getAnswerMap().get(PamConstants.RACE_INFORMATION_AS_OF));
			if(asOfDate.equals("") )
				asOfDate = getVal(clientVO.getAnswerMap().get(PamConstants.DEM_DATA_AS_OF));

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

			String[] asian = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_ASIAN);
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


			String[] hawaii = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_HAWAII);
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


			if (raceColl.size() > 0) {
				personVO.setThePersonRaceDTCollection(raceColl);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setRaceForCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	public static void setTelephones(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setTelephones");
		try {
			String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
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
			String homePhone = getVal(answerMap.get(PamConstants.H_PHONE));
			String homeExt = getVal(answerMap.get(PamConstants.H_PHONE_EXT));
			String workPhone = getVal(answerMap.get(PamConstants.W_PHONE));
			String workExt = getVal(answerMap.get(PamConstants.W_PHONE_EXT));
			String cellPh = getVal(answerMap.get(PamConstants.CELL_PHONE));
			String email = getVal(answerMap.get(PamConstants.EMAIL));

			if ((!homePhone.equals("")) || (!workPhone.equals("")) || (!homeExt.equals("")) || (!workExt.equals("")) || (!cellPh.equals("")) || (!email.equals("")))
				aodMap.put(PamConstants.TELEPHONE_INFORMATION_AS_OF, asOfDate);

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
			if ((workPhone != null && !workPhone.equals(""))) {
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
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setTelephones", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static void setIds(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		try {
			String patientSSN = getVal(answerMap.get(PamConstants.SSN));
			String asOfDate = null;
			asOfDate = getVal(answerMap.get(PamConstants.SSN_AS_OF));
			if(asOfDate.equals(""))
			{
				asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
			}

			if(patientSSN != null && patientSSN.trim().length() > 0) {
				if(aodMap != null)
					aodMap.put(PamConstants.SSN_AS_OF, asOfDate);

				EntityIdDT iddt = null;
				iddt = new EntityIdDT();
				iddt.setEntityIdSeq(new Integer(1));
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
				ArrayList<Object> idList = new ArrayList<Object> ();
				idList.add(iddt);
				personVO.setTheEntityIdDTCollection(idList);

			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setContactSpecifcAnswersForCreateEdit(CTContactProxyVO proxyVO, CTContactForm form, HttpSession session, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setContactSpecifcAnswersForCreateEdit");
		try {
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();
			Map<Object,Object> oldMap =  new HashMap<Object,Object>();
			if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION))
				oldMap = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getCtContactAnswerDTMap();

			setAnswerArrayMapAnswers(form, returnMap, userId);
			if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_SUBMIT_ACTION)){
				updateNbsAnswersForDirty(proxyVO, oldMap, returnMap);
				//proxyVO.getcTContactVO().setCtContactAnswerDTMap(returnMap);
			}
			if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.CREATE_SUBMIT_ACTION)){
				proxyVO.getcTContactVO().setCtContactAnswerDTMap(returnMap);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setContactSpecifcAnswersForCreateEdit", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}


	private static void setAnswerArrayMapAnswers(CTContactForm form, Map<Object,Object> returnMap, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setAnswerArrayMapAnswers");
		try {
			Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();
			if(answerMap != null && answerMap.size() > 0) {

				Iterator<Object> anIter = answerMap.keySet().iterator();
				while(anIter.hasNext()) {
					ArrayList<Object> answerList = new ArrayList<Object> ();
					String questionId = getVal(anIter.next());
						if(questionId.equals(CTConstants.ExposureType) || questionId.equals(CTConstants.ExposureSiteType) || questionId.equals(CTConstants.FirstExposureDate)|| questionId.equals(CTConstants.LastExposureDate))
						{
							String answers = (String)answerMap.get(questionId);
							if(answers == null || (answers == "")) continue;
							CTContactAnswerDT answerDT = new CTContactAnswerDT();
							answerDT.setSeqNbr(0); //gst- changed was 1
							answerDT.setAnswerTxt(answers);
							if(questionMap.get(questionId) != null) {

								NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)questionMap.get(questionId);
								if(qMetadata != null) {
									answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
									answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
									answerList.add(answerDT);
								}
							} else {
								logger.error("QuestionId: " + questionId  + " is not found in contact Answers");
							}
							returnMap.put(questionId, answerList);
					}
				}
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setAnswerArrayMapAnswers", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}



	private static Long setCtContactVOForCreate(Long tempID, CTContactForm form, HttpServletRequest req, CTContactProxyVO proxy, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setCtContactVOForCreate");
		try {
			Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();

			CTContactVO ctContactVO = new CTContactVO();
			ctContactVO.getcTContactDT().setCtContactUid(new Long(tempID--));
			ctContactVO.getcTContactDT().setProgAreaCd(getVal(form.getcTContactClientVO().getAnswerMap().get(CTConstants.PROGRAM_AREA)));
			ctContactVO.getcTContactDT().setJurisdictionCd(getVal(form.getcTContactClientVO().getAnswerMap().get(CTConstants.JURISDICTION_CD)));
			//ctContactVO.getcTContactDT().setSharedIndCd(getVal(answerMap.get(CTConstants.SHARED_IND)));

			String sharedInd = null;
			if(answerMap.get(CTConstants.SHARED_IND)!= null && answerMap.get(CTConstants.SHARED_IND).equals("1"))
				sharedInd = "T";
			else if((answerMap.get(CTConstants.SHARED_IND)!= null && !answerMap.get(CTConstants.SHARED_IND).equals("1"))||answerMap.get(CTConstants.SHARED_IND)== null)
				sharedInd = "F";
			ctContactVO.getcTContactDT().setSharedIndCd(sharedInd);

			ctContactVO.getcTContactDT().setContactStatus(getVal(answerMap.get(CTConstants.Status)));
			ctContactVO.getcTContactDT().setPriorityCd(getVal(answerMap.get(CTConstants.Priority)));
			ctContactVO.getcTContactDT().setGroupNameCd(getVal(answerMap.get(CTConstants.GroupId)));
			ctContactVO.getcTContactDT().setDispositionCd(getVal(answerMap.get(CTConstants.Disposition)));
			ctContactVO.getcTContactDT().setDispositionDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DispositionDate))));
			ctContactVO.getcTContactDT().setProcessingDecisionCd(getVal(answerMap.get(CTConstants.ProcessingDecision)));
			ctContactVO.getcTContactDT().setContactReferralBasisCd(getVal(answerMap.get(CTConstants.ReferralBasis)));
			String namedDuringStr = (String) answerMap.get(CTConstants.NamedDuring);
			if (namedDuringStr != null && !namedDuringStr.isEmpty())
				ctContactVO.getcTContactDT().setNamedDuringInterviewUid(Long.valueOf(namedDuringStr));
			ctContactVO.getcTContactDT().setRelationshipCd(getVal(answerMap.get(CTConstants.Relationship)));
			ctContactVO.getcTContactDT().setHealthStatusCd(getVal(answerMap.get(CTConstants.HealthStatus)));
			ctContactVO.getcTContactDT().setTxt(getVal(answerMap.get(CTConstants.GeneralComment)));
			ctContactVO.getcTContactDT().setSymptomCd(getVal(answerMap.get(CTConstants.AnySignsOrSymptoms)));
			ctContactVO.getcTContactDT().setSymptomTxt(getVal(answerMap.get(CTConstants.SignsSymptomsNotes)));
			ctContactVO.getcTContactDT().setSymptomOnsetDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.SymptomOnsetDate))));
			ctContactVO.getcTContactDT().setRiskFactorCd(getVal(answerMap.get(CTConstants.RiskFactorsForIllness)));
			ctContactVO.getcTContactDT().setRiskFactorTxt(getVal(answerMap.get(CTConstants.RiskFactorNotes)));
			ctContactVO.getcTContactDT().setEvaluationCompletedCd(getVal(answerMap.get(CTConstants.TestingEvaluationCompleted)));
			ctContactVO.getcTContactDT().setEvaluationDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DateOfEvaluation))));
			ctContactVO.getcTContactDT().setEvaluationTxt(getVal(answerMap.get(CTConstants.EvaluationFindings)));
			ctContactVO.getcTContactDT().setTreatmentInitiatedCd(getVal(answerMap.get(CTConstants.WasTreatmentInitiatedForIllness)));
			ctContactVO.getcTContactDT().setTreatmentStartDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.TreatmentStartDate))));
			ctContactVO.getcTContactDT().setTreatmentNotStartRsnCd(getVal(answerMap.get(CTConstants.ReasonTreatmentNotStarted)));
			ctContactVO.getcTContactDT().setTreatmentEndCd(getVal(answerMap.get(CTConstants.WasTreatmentCompleted)));
			ctContactVO.getcTContactDT().setTreatmentEndDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.TreatmentEndDate))));
			ctContactVO.getcTContactDT().setTreatmentNotEndRsnCd(getVal(answerMap.get(CTConstants.ReasonTreatmentNotCompleted)));
			ctContactVO.getcTContactDT().setTreatmentTxt(getVal(answerMap.get(CTConstants.TreatmentNotes)));
			ctContactVO.getcTContactDT().setNamedOnDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DateNamed))));

			ctContactVO.getcTContactDT().setRecordStatusTime(new Timestamp(new Date().getTime()));
			ctContactVO.getcTContactDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			//answerMap.put(PamConstants.CONDITION_CD, programAreaVO.getConditionCd());
			ctContactVO.getcTContactDT().setItNew(true);
			ctContactVO.getcTContactDT().setItDirty(false);
			String subjectPatientRevisionStUid=(String)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSPatientRevisionUID);
			Long subjectPatientRevisionUid = new Long(subjectPatientRevisionStUid);

			ctContactVO.getcTContactDT().setSubjectEntityUid(subjectPatientRevisionUid);
			String subjectPatientRevisionPhcUid=(String)NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationUid);
			ctContactVO.getcTContactDT().setSubjectEntityPhcUid(new Long(subjectPatientRevisionPhcUid));
			//ctContactVO.getcTContactDT().setAddTime(new Timestamp(new Date().getTime()));
			ctContactVO.getcTContactDT().setAddUserId(Long.valueOf(userId));
			ctContactVO.getcTContactDT().setLastChgTime(new Timestamp(new Date().getTime()));
			ctContactVO.getcTContactDT().setLastChgUserId(Long.valueOf(userId));
			//ctContactVO.getcTContactDT().setSharedIndCd(getVal(answerMap.get(CTConstants.SHARED_IND)));

			// These questions are for extending PHC table for common fields - ODS changes
			if(getVal(answerMap.get(CTConstants.DateAssigned))!=null )
			{
				ctContactVO.getcTContactDT().setInvestigatorAssignedDate_s(getVal(answerMap.get(CTConstants.DateAssigned)));
			}

			ctContactVO.setItNew(true);
			ctContactVO.setItDirty(false);

			proxy.setcTContactVO(ctContactVO);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setCtContactVOForCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return tempID;
	}


	/**
	 * setEntitiesForEdit creates Participations' and NBSActEntities' with types of PRVs and ORGs associated with Tuberculosis
	 * @param proxyVO
	 * @param form
	 * @param revisionPatientUID
	 * @param userId
	 * @param request
	 */
	private static void setEntitiesForCreate(CTContactProxyVO proxyVO, CTContactForm form, Long contactUid, String userId, HttpServletRequest request) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setEntitiesForCreate");
		try {
			Collection<Object>  entityColl = new ArrayList<Object> ();
			//Investigator
			String prvUid = form.getAttributeMap().get("CON137Uid") == null ? "" : (String) form.getAttributeMap().get("CON137Uid");
			String uid = splitUid(prvUid);
			String versionCtrlNbr = splitVerCtrlNbr(prvUid);
			if (!prvUid.trim().equals(""))
			{
				NbsActEntityDT actEntityDtInv = new NbsActEntityDT();
				actEntityDtInv.setTypeCd(NEDSSConstants.CONTACT_PROVIDER);
				actEntityDtInv.setActUid(new Long(contactUid));
				actEntityDtInv.setEntityUid(new Long(uid));
				actEntityDtInv.setAddUserId(new Long(userId));
				actEntityDtInv.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
				proxyVO.getcTContactVO().getActEntityDTCollection().add(actEntityDtInv);
			}
			//Dispositioned By
			String dispoUidStr = form.getAttributeMap().get(CTConstants.DispositionedBy+"Uid") == null ? "" : (String) form.getAttributeMap().get(CTConstants.DispositionedBy+"Uid");
			String dispoUid = splitUid(dispoUidStr);
			String dispoVersionCtrlNbr = splitVerCtrlNbr(dispoUidStr);
			if (!dispoUidStr.trim().equals(""))
			{
				NbsActEntityDT actEntityDtInv = new NbsActEntityDT();
				actEntityDtInv.setTypeCd(NEDSSConstants.CONTACT_DISPOSITIONED_BY);
				actEntityDtInv.setActUid(new Long(contactUid));
				actEntityDtInv.setEntityUid(new Long(dispoUid));
				actEntityDtInv.setAddUserId(new Long(userId));
				actEntityDtInv.setEntityVersionCtrlNbr(new Integer(dispoVersionCtrlNbr));
				proxyVO.getcTContactVO().getActEntityDTCollection().add(actEntityDtInv);
			}
			//Infected Third Party By
				//not storing actEntity for otherInfectedPatinet (CON142)
			//Reporting organization
			String strOrganizationUID = form.getAttributeMap().get("CON106Uid") == null ? "" : (String) form.getAttributeMap().get("CON106Uid");
			String orgUid = splitUid(strOrganizationUID);
			String versionCtrlNbrOrg = splitVerCtrlNbr(strOrganizationUID);
			if (!strOrganizationUID.trim().equals(""))
			{
				NbsActEntityDT actEntityDtOrg = new NbsActEntityDT();
				actEntityDtOrg.setTypeCd(NEDSSConstants.CONTACT_ORGANIZATION);
				actEntityDtOrg.setActUid(new Long(contactUid));
				actEntityDtOrg.setEntityUid(new Long(orgUid));
				actEntityDtOrg.setAddUserId(new Long(userId));
				actEntityDtOrg.setEntityVersionCtrlNbr(new Integer(versionCtrlNbrOrg));
				proxyVO.getcTContactVO().getActEntityDTCollection().add(actEntityDtOrg);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setEntitiesForCreate", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	public static String splitVerCtrlNbr(String strInvestigatorUID) {
		String versionCtrlNbr = "";
		try {
			if(strInvestigatorUID.indexOf("|") == -1)
				return "1";
			versionCtrlNbr = strInvestigatorUID.substring(strInvestigatorUID.indexOf("|")+1);
		} catch (Exception e) {
			logger.error("Error while retrieving versionCtrlNbr from EntityUid: " + e.toString());
		}
		return versionCtrlNbr;
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

	public static CTContactProxyVO viewLoadUtil(CTContactForm form, HttpServletRequest request,String contactRecordUID) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "viewLoadUtil");
		try {
			form.setPageFormCd(NBSConstantUtil.CONTACT_REC);//setting form code for contact tracing.
			CTContactProxyVO proxyVO = viewContactLoadUtil(form, request,contactRecordUID);
			//Check if any session attribute "SupplementalInfo" exists, if so, set default tabid to Supplemental Tab.

			form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
			_loadEntities(form, proxyVO, request);
			form.setFormFieldMap(initiateForm(questionMap, form));
			form.setPageTitle(NBSPageConstants.VIEW_CONTACT, request);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "viewLoadUtil", start);
			return proxyVO;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static CTContactProxyVO viewPageLoadUtil(CTContactForm form, HttpServletRequest request,String contactRecordUID) throws Exception {
		try {

			CTContactProxyVO proxyVO = viewContactPageLoadUtil(form, request, contactRecordUID);
			//Check if any session attribute "SupplementalInfo" exists, if so, set default tabid to Supplemental Tab.

			form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
			_loadEntities(form, proxyVO, request);
			form.setPageTitle(NBSPageConstants.VIEW_CONTACT, request);
			request.setAttribute("SubSecStructureMap", form.getSubSecStructureMap());
			return proxyVO;
		} catch (Exception e) {
						logger.error("Contact ViewPageLoadUtil Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	public static CTContactProxyVO viewContactLoadUtil(CTContactForm form, HttpServletRequest request,String contactRecordUID) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "viewContactLoadUtil");
		CTContactProxyVO proxyVO = null;
		try {
			form.clearAll();
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
			if(contactRecordUID == null){
				contactRecordUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSContactUID);
			}
			if(contactRecordUID != null)
				proxyVO=populateProxyVO(contactRecordUID, request);

			if(contactRecordUID!=null)
				NBSContext.store(session, NBSConstantUtil.DSContactUID, contactRecordUID);

			//Load contact answers and put it in answerMap for UI & Rules to work
			setCommonAnswersForViewEdit(form, proxyVO, request);
			setMSelectCBoxAnswersForViewEdit(form, proxyVO.getcTContactVO().getCtContactAnswerDTMap());
			//set ContactProxyVO to ClientVO
			form.getcTContactClientVO().setOldCtContactProxyVO((CTContactProxyVO)proxyVO.deepCopy());
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			 String conditionCd = request.getParameter(NBSConstantUtil.DSInvestigationCondition);
			if(conditionCd == null || (conditionCd != null && conditionCd.trim().equalsIgnoreCase(""))|| conditionCd.equalsIgnoreCase("null"))
				conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			boolean newPat = false;

			//gt-Added for Congenital Syphillis November 2017
			try {
				String dispositionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdDispositionCd);
				if (dispositionCd != null)
					form.getAttributeMap().put(CTConstants.SourceDispositionCd, dispositionCd);
				String interviewStatusCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdInterviewStatusCd);
				if (interviewStatusCd != null)
					form.getAttributeMap().put(CTConstants.SourceInterviewStatusCd, interviewStatusCd);
				if (conditionCd != null)
					form.getAttributeMap().put(CTConstants.SourceConditionCd, conditionCd);	
				String currentSexCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSPatientCurrentSexCd);
				if (currentSexCd != null)
					form.getAttributeMap().put(CTConstants.SourceCurrentSexCd, currentSexCd);
	    	 } catch (Exception ex) {
	    		 logger.debug("viewContactLoadUtil DSPatientCurrentSexCd not in scope.");
	    	 }
			populatePatientSummary(form,proxyVO, proxyVO.getContactPersonVO(), userId, conditionCd, newPat, request);

			setCommonSecurityForView(form, proxyVO, nbsSecurityObj);
			setJurisdictionForView(form,proxyVO, conditionCd,nbsSecurityObj,request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading View " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "viewContactLoadUtil", start);
		return proxyVO;
	}
	/**
	 * viewContactPageLoadUtil - View Dynamic Contact Page.
	 * @param form
	 * @param request
	 * @param contactRecordUid
	 */
	public static CTContactProxyVO viewContactPageLoadUtil(CTContactForm form, HttpServletRequest request,String contactRecordUID) throws Exception {
		CTContactProxyVO proxyVO = null;
		try {
			form.clearAll(); //clear attribute map
			form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			form.getStateList();
			form.getCountryList();
			//if(request.getAttribute("mode") == null)
			//	form.getAttributeMap().clear();
			HttpSession session = request.getSession();
			String contactFormCd = form.getPageFormCd();
			PageLoadUtil pageLoadUtil = new PageLoadUtil();
			//PageLoadUtil.loadQuestions(contactFormCd);
			pageLoadUtil.loadQuestionKeys(contactFormCd);
			String contextAction = request.getParameter("ContextAction");
			if(request.getParameter("mode")==null)
				request.getParameterMap().clear();
			if(contactRecordUID == null){
				contactRecordUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSContactUID);
			}
			if(contactRecordUID != null) {
				proxyVO=populateProxyVO(contactRecordUID, request);
				NBSContext.store(session, NBSConstantUtil.DSContactUID, contactRecordUID);
			}
			
			String currentTask = NBSContext.getCurrentTask(session);
			if (currentTask.startsWith("ViewFile")) {
				Long subjectInvestigation = proxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid();
				String subjectProgramAreaCd = proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd();
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, subjectInvestigation.toString());
				NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, subjectProgramAreaCd);
			}
			//Load contact answers and put it in answerMap for UI & Rules to work
			setCommonAnswersForViewEdit(form, proxyVO, request);
			pageLoadUtil.setMSelectCBoxAnswersForViewEdit((BaseForm)form, pageLoadUtil.updateMapWithQIds(proxyVO.getcTContactVO().getCtContactAnswerDTMap()), (ClientVO)form.getcTContactClientVO());


			//set ContactProxyVO to ClientVO
			form.getcTContactClientVO().setOldCtContactProxyVO((CTContactProxyVO)proxyVO.deepCopy());
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			 String conditionCd = request.getParameter(NBSConstantUtil.DSInvestigationCondition);
			if(conditionCd == null || (conditionCd != null && conditionCd.trim().equalsIgnoreCase(""))|| conditionCd.equalsIgnoreCase("null"))
				conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			boolean newPat = false;
			//setFormFieldMap
			PageRulesGenerator reUtils = new PageRulesGenerator();
			form.setFormFieldMap(reUtils.initiateForm(pageLoadUtil.getQuestionMap(),  (BaseForm) form, (ClientVO) form.getcTContactClientVO()));
			pageLoadUtil.populateBatchRecords(form, contactFormCd, request.getSession(), proxyVO.getcTContactVO().getRepeatingAnswerDTMap());
			
			populatePatientSummary(form,proxyVO, proxyVO.getContactPersonVO(), userId, conditionCd, newPat, request);
			ClientUtil.setPersonIdDetails(proxyVO.getContactPersonVO(), form);
			setCommonSecurityForView(form, proxyVO, nbsSecurityObj);
			setJurisdictionForView(form, proxyVO, conditionCd,nbsSecurityObj, request);

			 //only Std and HIV show the Investigation Summary on the Contact Supplemental page
			 boolean stdProgramArea = PropertyUtil.isStdOrHivProgramArea( proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd()) ;
			 request.setAttribute("viewInves", String.valueOf(stdProgramArea));
			 NBSContext.store(request.getSession(), "viewInves", String.valueOf(stdProgramArea));
			 Long personUID  = proxyVO.getContactPersonVO().getThePersonDT().getPersonParentUid();

			 // look at the investigations this contact has
			 if(stdProgramArea && personUID !=null){
				 String recordSearchDisposition = loadSupplimentalInvestigationInfo(form, personUID, conditionCd, request);
				 //if there is a record search closure candidate, indicate the disposition
				 form.getcTContactClientVO().getAnswerMap().put(CTConstants.Disposition, recordSearchDisposition);
			}
			if(stdProgramArea){
				 String namedDuringKey = (String) form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamedDuring);
				 if (namedDuringKey != null) {
					 Collection<Object> interviewList = getInterviewSummary(session, proxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid(), proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd() );
					 form.getAttributeMap().put(CTConstants.StdInterviewSelectedValue, getTheNamedInterviewDisplayText(namedDuringKey, interviewList));
				 }
				String sourceInterviewStatusCd = request.getParameter(CTConstants.SourceInterviewStatusCd);  //passed from File Workup
				if (sourceInterviewStatusCd != null && !sourceInterviewStatusCd.isEmpty()) {
					form.getAttributeMap().put(CTConstants.SourceInterviewStatusCd, sourceInterviewStatusCd);
					NBSContext.store(request.getSession(),  NBSConstantUtil.DSStdInterviewStatusCd, sourceInterviewStatusCd);
				}
				String sourceDispositionCd = request.getParameter(CTConstants.SourceDispositionCd);  //passed from File Workup
				if (sourceDispositionCd != null && !sourceDispositionCd.isEmpty()) {
					form.getAttributeMap().put(CTConstants.SourceDispositionCd, sourceDispositionCd);
					NBSContext.store(request.getSession(),  NBSConstantUtil.DSStdDispositionCd, sourceDispositionCd);
				}
				String sourceConditionCd = request.getParameter(CTConstants.SourceConditionCd);  //passed from File Workup
				if (sourceConditionCd != null && !sourceConditionCd.isEmpty()) {
					form.getAttributeMap().put(CTConstants.SourceConditionCd, sourceConditionCd);
					NBSContext.store(request.getSession(),  NBSConstantUtil.DSSourceConditionCd, sourceConditionCd);
				}
				String sourceCurrentSexCd = request.getParameter(CTConstants.SourceCurrentSexCd);  //passed from File Workup
				if (sourceCurrentSexCd != null  && !sourceCurrentSexCd.isEmpty()) {
					form.getAttributeMap().put(CTConstants.SourceCurrentSexCd, sourceCurrentSexCd);
					NBSContext.store(request.getSession(),  NBSConstantUtil.DSPatientCurrentSexCd, sourceCurrentSexCd);
				}	
				Long contactInv = proxyVO.getcTContactVO().getcTContactDT().getContactEntityPhcUid();
				String procDecisionCd = proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd();
				if (contactInv != null && procDecisionCd != null && procDecisionCd.equals(CTConstants.FieldFollowup))
					form.getAttributeMap().put(CTConstants.DeleteStdContactInvCheck, procDecisionCd);
				 //disposition is a replicated field, under some conditions, it should be translated

				//To show "Associate Investigation" button if there are more then one open co-infection investigations.
				form.getAttributeMap().put(NEDSSConstants.COINFECTION_INV_EXISTS, true);

				checkDispositionForTranslation(form);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading View " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}




	public static void loadQuestionKeys(String invFormCd) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "loadQuestionKeys");
		questionKeyMap = new HashMap<Object,Object>();
		Iterator<?> iter = questionMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
			questionKeyMap.put(metaData.getNbsQuestionUid(), key);
		}
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "loadQuestionKeys", start);
	}
	/**
	 * setCommonAnswersForViewEdit loads all the DEMs, INVs common across all PAMs
	 * collects them from multiple tables part of PamProxyVO and stuffs in AnswerMap to support UI / Rules
	 * @param form
	 * @param proxyVO
	 * @param request
	 */
	public static void setCommonAnswersForViewEdit(CTContactForm form, CTContactProxyVO proxyVO, HttpServletRequest request) {
		try {
			CTContactClientVO clientVO = new CTContactClientVO();
			PersonVO personVO = proxyVO.getContactPersonVO();
			ClientUtil.setPatientInformation(form.getActionMode(), personVO, clientVO, request, form.getPageFormCd());
			form.setcTContactClientVO(clientVO);
			//loadCommonInvestigationAnswers
			setContactInformationOnForm(form, proxyVO);
            if (form.getcTContactClientVO().getAnswer("DEM162") == null)
                form.getAttributeMap().put("DEM165_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
            else
                form.getAttributeMap().put("DEM165_STATE", form.getcTContactClientVO().getAnswer("DEM162"));

            if (form.getcTContactClientVO().getAnswer("DEM162") == null)
                form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance()
                        .getNBS_STATE_CODE()));
            else
                form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getcTContactClientVO().getAnswer("DEM162_W")));
            if (form.getcTContactClientVO().getAnswer("DEM162_W") == null)
                form.getAttributeMap().put("DEM165_W_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
            else
                form.getAttributeMap().put("DEM165_W_STATE", form.getcTContactClientVO().getAnswer("DEM162_W"));
            
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}


	public static void setMSelectCBoxAnswersForViewEdit(CTContactForm form, Map<Object,Object> answerMap) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setMSelectCBoxAnswersForViewEdit");
		try {
			setMultiSelectToAnswerArrayMap(answerMap, form);
			setRaceAnswersToClientVO(form.getcTContactClientVO(), answerMap);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setMSelectCBoxAnswersForViewEdit", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}
	private static void setMultiSelectToAnswerArrayMap(Map<Object,Object> answerMap, CTContactForm form) {
		try {
			ArrayList<Object> list = new ArrayList<Object> ();
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			Iterator<?>  iter = questionMap.keySet().iterator();
			while(iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				String qId = metaData.getQuestionIdentifier() == null ? "" : metaData.getQuestionIdentifier();
				Long nbsQUid = metaData.getNbsQuestionUid() == null ? new Long(0) : metaData.getNbsQuestionUid();
				if(answerMap.containsKey(nbsQUid))
				{
					Object obj = answerMap.get(nbsQUid);
					if(obj instanceof CTContactAnswerDT) {
						CTContactAnswerDT conAnsDt= (CTContactAnswerDT) obj;
						form.getcTContactClientVO().setAnswer(qId, conAnsDt.getAnswerTxt());
					} else  {//assume arrayList
						ArrayList<?> arrayList = (ArrayList<?>)answerMap.get(nbsQUid);
						CTContactAnswerDT conAnsDt= (CTContactAnswerDT)arrayList.get(0);
						form.getcTContactClientVO().setAnswer(qId, conAnsDt.getAnswerTxt());
					}
				}

			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	//gst- Modified legacy method to work with AnswerDTs with seqNbr of zero - don't assume arrayList
	public static void setAnswerArrayMapAnswers(CTContactForm form, ArrayList<Object> multiSelects, Map<Object,Object> answerMap) {

		try {
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			//Initialize
			for(int k=0; k < multiSelects.size(); k++) {
				Object obj = answerMap.get(multiSelects.get(k));

				if( obj != null && obj instanceof ArrayList<?>) {
					ArrayList<?> multiList = (ArrayList<?> ) obj;
					String [] answerList = new String[multiList.size()];
					for(int i=0; i<multiList.size(); i++) {
						CTContactAnswerDT answerDT = (CTContactAnswerDT)multiList.get(i);
						String answer = answerDT.getAnswerTxt();
						answerList[i] = answer;
					}
					returnMap.put(multiSelects.get(k), answerList);
					form.getcTContactClientVO().getArrayAnswerMap().putAll(returnMap);

				}
			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setContactSpecificAnswersForViewEdit(CTContactForm form, Map<Object,Object> answerMap, Map<Object,Object> returnMap) {

		try {
			if(answerMap != null && answerMap.size() > 0) {
				Iterator<Object>  iter = answerMap.keySet().iterator();
				while(iter.hasNext()) {
					String questionId = (String) iter.next();
					Object object = answerMap.get(questionId);
					if(object instanceof CTContactAnswerDT) {
						CTContactAnswerDT answerDT = (CTContactAnswerDT) object;
						returnMap.put(questionId, answerDT.getAnswerTxt());
					}
				}
				form.getcTContactClientVO().getAnswerMap().putAll(returnMap);
				setRaceAnswersToClientVO(form.getcTContactClientVO(), answerMap);
			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private static void setRaceAnswersToClientVO(CTContactClientVO clientVO, Map<Object,Object> answerMap) {

		try {
			Object obj = answerMap.get(PamConstants.RACE);
			if(obj != null && obj instanceof ArrayList<?>) {
				ArrayList<?> raceArrayList = (ArrayList<?> ) obj;
				Iterator<?>  iter = raceArrayList.iterator();
				while(iter.hasNext()) {
					CTContactAnswerDT answerDT = (CTContactAnswerDT) iter.next();
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
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setCheckBoxAnswersWithCodeSetForLoad(Map<Object,Object> answerMap,ArrayList<Object> checkboxes, Map<Object,Object> returnMap) {

		try {
			Map<?,?> map = cdv.getCodedValuesAsTreeMap("TF_PAM");
			for(int i=0; i < checkboxes.size(); i++) {
				Object obj = answerMap.get(checkboxes.get(i));
				if(obj instanceof CTContactAnswerDT) {
					CTContactAnswerDT dt = (CTContactAnswerDT)obj;
					String answer = dt.getAnswerTxt();
					if(answer != null && answer.equalsIgnoreCase((String)map.get("True")))
						returnMap.put(checkboxes.get(i), "1");
					else
						returnMap.put(checkboxes.get(i), "0");
				}
			}
		} catch (NEDSSSystemException e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setContactInformationOnForm(CTContactForm form, CTContactProxyVO proxyVO) {
		try {
			CTContactDT dt = proxyVO.getcTContactVO().getcTContactDT();
			CTContactClientVO clientVO = form.getcTContactClientVO();
			clientVO.setAnswer(CTConstants.JURISDICTION_CD, dt.getJurisdictionCd());
			clientVO.setAnswer(CTConstants.PROGRAM_AREA, dt.getProgAreaCd());
			clientVO.setAnswer(CTConstants.Status, dt.getContactStatus());
			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
			if(dt.getContactStatus() != null)
				form.getAttributeMap().put("contactStatus", cachedDropDownValues.getCodeShortDescTxt(dt.getContactStatus(), "PHC_IN_STS"));
			//for STD Contact the Disposition and Disposition Date are from the Contacts Investigation if there is one
			clientVO.setAnswer(CTConstants.Disposition,dt.getDispositionCd()== null ? "" : dt.getDispositionCd());
			clientVO.setAnswer(CTConstants.DispositionDate, dt.getDispositionDate()== null ? "" :  StringUtils.formatDate(dt.getDispositionDate()));
			clientVO.setAnswer(CTConstants.ProcessingDecision,dt.getProcessingDecisionCd()== null ? "" : dt.getProcessingDecisionCd());
			clientVO.setAnswer(CTConstants.ReferralBasis,dt.getContactReferralBasisCd()== null ? "" : dt.getContactReferralBasisCd());
			Long namedDuringIxs = dt.getNamedDuringInterviewUid();
			if (namedDuringIxs != null) {
				String namedDuringStr = namedDuringIxs.toString();
				clientVO.setAnswer(CTConstants.NamedDuring, namedDuringStr);
			} else clientVO.setAnswer(CTConstants.NamedDuring,"");

			clientVO.setAnswer(CTConstants.Priority,dt.getPriorityCd()== null ? "" : dt.getPriorityCd());
			clientVO.setAnswer(CTConstants.GroupId,dt.getGroupNameCd()== null ? "" : dt.getGroupNameCd());
			clientVO.setAnswer(CTConstants.DateNamed,dt.getNamedOnDate()== null ? "" : StringUtils.formatDate(dt.getNamedOnDate()));
			clientVO.setAnswer(CTConstants.Relationship,dt.getRelationshipCd()== null ? "" : dt.getRelationshipCd());
			clientVO.setAnswer(CTConstants.HealthStatus,dt.getHealthStatusCd()== null ? "" : dt.getHealthStatusCd());
			clientVO.setAnswer(CTConstants.ExposureSite,dt.getExposureSiteTypeCd()== null ? "" : dt.getExposureSiteTypeCd());
			clientVO.setAnswer(CTConstants.GeneralComment,dt.getTxt()== null ? "" : dt.getTxt());
			clientVO.setAnswer(CTConstants.AnySignsOrSymptoms,dt.getSymptomCd()== null ? "" : dt.getSymptomCd());
			clientVO.setAnswer(CTConstants.SymptomOnsetDate,dt.getSymptomOnsetDate()== null ? "" : StringUtils.formatDate(dt.getSymptomOnsetDate()));
			clientVO.setAnswer(CTConstants.SignsSymptomsNotes,dt.getSymptomTxt()== null ? "" : dt.getSymptomTxt());
			clientVO.setAnswer(CTConstants.RiskFactorsForIllness,dt.getRiskFactorCd()== null ? "" : dt.getRiskFactorCd());
			clientVO.setAnswer(CTConstants.RiskFactorNotes,dt.getRiskFactorTxt()== null ? "" : dt.getRiskFactorTxt());
			clientVO.setAnswer(CTConstants.TestingEvaluationCompleted,dt.getEvaluationCompletedCd()== null ? "" : dt.getEvaluationCompletedCd());
			clientVO.setAnswer(CTConstants.DateOfEvaluation,dt.getEvaluationDate()== null ? "" : StringUtils.formatDate(dt.getEvaluationDate()));
			clientVO.setAnswer(CTConstants.EvaluationFindings,dt.getEvaluationTxt()== null ? "" : dt.getEvaluationTxt());
			clientVO.setAnswer(CTConstants.WasTreatmentInitiatedForIllness,dt.getTreatmentInitiatedCd()== null ? "" : dt.getTreatmentInitiatedCd());
			clientVO.setAnswer(CTConstants.TreatmentStartDate,dt.getTreatmentStartDate()== null ? "" : StringUtils.formatDate(dt.getTreatmentStartDate()));
			clientVO.setAnswer(CTConstants.ReasonTreatmentNotStarted,dt.getTreatmentNotStartRsnCd()== null ? "" : dt.getTreatmentNotStartRsnCd());
			clientVO.setAnswer(CTConstants.WasTreatmentCompleted,dt.getTreatmentEndCd()== null ? "" : dt.getTreatmentEndCd());
			clientVO.setAnswer(CTConstants.TreatmentEndDate,dt.getTreatmentEndDate()== null ? "" : StringUtils.formatDate(dt.getTreatmentEndDate()));
			clientVO.setAnswer(CTConstants.ReasonTreatmentNotCompleted,dt.getTreatmentNotEndRsnCd()== null ? "" : dt.getTreatmentNotEndRsnCd());
			clientVO.setAnswer(CTConstants.TreatmentNotes,dt.getTreatmentTxt()== null ? "" : dt.getTreatmentTxt());

			 String sharedInd = null;
			  if(dt.getSharedIndCd()!= null && dt.getSharedIndCd().equals("T"))
				  sharedInd="1";
			  clientVO.setAnswer(CTConstants.SHARED_IND, sharedInd);

			  if(dt.getInvestigatorAssignedDate_s()!= null)
			  {
				  clientVO.setAnswer(CTConstants.DateAssigned,dt.getInvestigatorAssignedDate_s());
			  }
			//clientVO.setAnswer(CTConstants.SHARED_IND, dt.getSharedIndCd()== null ? "" : dt.getSharedIndCd());
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Legacy: editLoadUtil method retrieves the PamProxyVO from the EJB and sets to PamClientVO, attribute of PamForm
	 * @param form
	 * @param request
	 */
	//legacy contact pages
	public static CTContactProxyVO editLoadUtil(CTContactForm form, HttpServletRequest request) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editLoadUtil");
		try {
			CTContactProxyVO proxyVO = editContactLoadUtil(form, request);
			_loadEntities(form, proxyVO, request);
			form.setFormFieldMap(initiateForm(questionMap, form));
			form.setPageTitle(NBSPageConstants.EDIT_CONTACT, request);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editLoadUtil", start);
			return proxyVO;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * NEW
	 * editLoadPageUtil method retrieves the PamProxyVO from the EJB and sets to PamClientVO, attribute of PamForm
	 * @param form
	 * @param request
	 */
	public static CTContactProxyVO editLoadPageUtil(CTContactForm form, HttpServletRequest request) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editLoadUtil");
		try {
			CTContactProxyVO proxyVO = editContactPageLoadUtil(form, request);
			//gst check this
			_loadEntities(form, proxyVO, request);
			form.setPageTitle(NBSPageConstants.EDIT_CONTACT, request);
			request.setAttribute("SubSecStructureMap", form.getSubSecStructureMap());
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editLoadUtil", start);
			return proxyVO;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}


	//legacy
	public static CTContactProxyVO editContactLoadUtil(CTContactForm form, HttpServletRequest request) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editContactLoadUtil");
		CTContactProxyVO proxyVO = null;

		try {
			form.clearAll();
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
			form.getAttributeMap().put(ACTION_PARAMETER, "editContactSubmit");
			String ctContactUid = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSContactUID);
			proxyVO = populateProxyVO(ctContactUid, request);
			//DEM, CON specific common answers back to CtContactclientvo to support UI / Rules
			setCommonAnswersForViewEdit(form, proxyVO, request);
			//Contact Specific Answers
			setMSelectCBoxAnswersForViewEdit(form, proxyVO.getcTContactVO().getCtContactAnswerDTMap());
			//set CtContactProxyVO to ClientVO
			form.getcTContactClientVO().setOldCtContactProxyVO((CTContactProxyVO)proxyVO.deepCopy());
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			setUpdatedValues(form, request);
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			boolean newPat = false;
			populatePatientSummary(form,proxyVO, proxyVO.getContactPersonVO(), userId, conditionCd, newPat,request);
			setJurisdiction( form, session,nbsSecurityObj);

			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editContactLoadUtil", start);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Edit " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}

	public static CTContactProxyVO editContactPageLoadUtil(CTContactForm form, HttpServletRequest request) throws Exception {
		CTContactProxyVO proxyVO = null;

		try {
			form.clearAll();
			form.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			form.getAttributeMap().put("ReadOnlyJursdiction", "ReadOnlyJursdiction");
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();

			String formCd = form.getPageFormCd();
			PageLoadUtil pageLoadUtil= new PageLoadUtil();
			pageLoadUtil.loadQuestionKeys(formCd);

			form.getAttributeMap().put("header", "Edit Investigation");
			form.getAttributeMap().put(ACTION_PARAMETER, "editContactSubmit");

			String ctContactUid = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSContactUID);
			proxyVO = populateProxyVO(ctContactUid, request);
			//DEM, CON specific common answers back to CtContactclientvo to support UI / Rules
			setCommonAnswersForViewEdit(form, proxyVO, request);
			//Contact Specific Answers
			pageLoadUtil.setMSelectCBoxAnswersForViewEdit((BaseForm)form, pageLoadUtil.updateMapWithQIds(proxyVO.getcTContactVO().getCtContactAnswerDTMap()), (ClientVO)form.getcTContactClientVO());

			//set CtContactProxyVO to ClientVO
			form.getcTContactClientVO().setOldCtContactProxyVO((CTContactProxyVO)proxyVO.deepCopy());
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			if (form.getcTContactClientVO().getAnswerMap().containsKey(CTConstants.NamedDuring)) {
				form.getAttributeMap().put(CTConstants.StdNamedDuringVal, form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamedDuring));
			}
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			pageLoadUtil.setUpdatedValues((BaseForm)form, request,(ClientVO)form.getcTContactClientVO());
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			boolean newPat = false;
			populatePatientSummary(form,proxyVO, proxyVO.getContactPersonVO(), userId, conditionCd, newPat,request);
			setJurisdiction( form, session,nbsSecurityObj);
			pageLoadUtil.fireRulesOnEditLoad((BaseForm)form,(ClientVO) form.getcTContactClientVO(), request);
			pageLoadUtil.populateBatchRecords(form, formCd, request.getSession(), proxyVO.getcTContactVO().getRepeatingAnswerDTMap());
			ClientUtil.setPersonIdDetails(proxyVO.getContactPersonVO(), form);
			 // if STD Contact Record look at the investigations this contact has
			 //only Std and HIV show the Investigation Summary on the Contact Supplemental page
			 boolean stdProgramArea = PropertyUtil.isStdOrHivProgramArea( proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd()) ;
			 request.setAttribute("viewInves", String.valueOf(stdProgramArea));
			 NBSContext.store(request.getSession(), "viewInves", String.valueOf(stdProgramArea));
			 Long personUID  = proxyVO.getContactPersonVO().getThePersonDT().getPersonParentUid();
			 if(stdProgramArea && personUID !=null){
				 String recordSearchDisposition = loadSupplimentalInvestigationInfo(form, personUID, conditionCd, request);
				 //disposition is a replicated field, under some conditions, it should be translated
				 checkDispositionForTranslation(form);
			 }
			 if(stdProgramArea){
				 form.getAttributeMap().put(CTConstants.StdInterviewSelectedKey, form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamedDuring));
				 //We need to retrieve Interview List for the Named (during interview) dropdown kludge
				 Collection<Object> interviewList = getInterviewSummary(session, proxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid(), proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd() );
				 form.getAttributeMap().put("InterviewList", (ArrayList<Object>)  interviewList);
					//For STD Contact, disposition Code drives the Referral Basis Options
				    //If coming from File, it is loaded as an attribute
			     if (!form.getAttributeMap().containsKey(CTConstants.SourceDispositionCd)) {
			    	 String dispositionCd = null;
			    	 try {
			    		 dispositionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdDispositionCd);
			    	 } catch (Exception ex) {
			    		 logger.debug("editContactPageLoadUtil DSStdDispositionCd not in scope.");
			    	 }
					if (dispositionCd != null) {
						form.getAttributeMap().put(CTConstants.SourceDispositionCd, dispositionCd);
					}
			     }
			     if (!form.getAttributeMap().containsKey(CTConstants.SourceInterviewStatusCd)) {
			    	 String interviewStatusCd = null;
			    	 try {
			    		 interviewStatusCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSStdInterviewStatusCd);
			    	 } catch (Exception ex) {
			    		 logger.debug("editContactPageLoadUtil DSStdInterviewStatusCd not in scope.");
			    	 }			    		 
					 if (interviewStatusCd != null)
							form.getAttributeMap().put(CTConstants.SourceInterviewStatusCd, interviewStatusCd);
			     }  
			     if (!form.getAttributeMap().containsKey(CTConstants.SourceConditionCd)) {
			    	 String sourceConditionCd = null;
			    	 try {
			    		 sourceConditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSSourceConditionCd);
			    	 } catch (Exception ex) {
			    		 logger.debug("editContactPageLoadUtil DSSourceConditionCd not in scope.");
			    	 }			    		 
					 if (sourceConditionCd != null) {
							form.getAttributeMap().put(CTConstants.SourceConditionCd, sourceConditionCd);
					 }
			     }
			     if (!form.getAttributeMap().containsKey(CTConstants.SourceCurrentSexCd)) {
			    	 String sourceCurrentSexCd = null;
			    	 try {
			    		 sourceCurrentSexCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSPatientCurrentSexCd);
			    	 } catch (Exception ex) {
			    		 logger.debug("editContactPageLoadUtil DSPatientCurrentSexCd not in scope.");
			    	 }			    		 
					 if (sourceCurrentSexCd != null)
							form.getAttributeMap().put(CTConstants.SourceCurrentSexCd, sourceCurrentSexCd);
			     }			
			     
			     
			 }
			 CTContactDT contactDT = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getcTContactDT();
				if (contactDT.getAddTime() != null)
					request.setAttribute("addTime", contactDT.getAddTime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Edit " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}

	/*
	 * DispositionCd is a readonly field on the contact record that comes from the Contacts disposition.
	 * The contact record disposition can be updated in the background when the investigation disposition changes.
	 * According to the complex business requirements, we should translate for the contact disposition if the
	 * Porcessing Decision is SR or RSC.
	 * The same data really should never live in two places, it is not good design because it gets out of synch
	 * due to timing differences...
	 */
	private static void checkDispositionForTranslation(CTContactForm form) {

			String dispoCd = (String) form.getcTContactClientVO().getAnswerMap().get(CTConstants.Disposition);
			String procDecision = (String) form.getcTContactClientVO().getAnswerMap().get(CTConstants.ProcessingDecision);
			if (procDecision != null &&
					(procDecision.equals(CTConstants.RecordSearchClosure) || procDecision.equals(CTConstants.SecondaryReferral))) {
				if (dispoCd.equals("A")) //preventative treatment
					form.getcTContactClientVO().getAnswerMap().put(CTConstants.Disposition, "Z");
				else if (dispoCd.equals("C")) //infected brought to treat)
					form.getcTContactClientVO().getAnswerMap().put(CTConstants.Disposition, "E");
			}
	}


	protected static String setContextForEdit(CTContactForm form, HttpServletRequest request, HttpSession session) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setContextForEdit");
		try {
			PersonVO personVO = form.getcTContactClientVO().getOldCtContactProxyVO().getContactPersonVO();

			form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));

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
			NBSAuthHelper helper = new NBSAuthHelper();
			CTContactDT contactDT = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getcTContactDT();
			request.setAttribute("createdDate", StringUtils.formatDate(contactDT.getAddTime()));
			//for SRT dropdown values
			if (contactDT.getAddTime() != null)
				request.setAttribute("addTime", contactDT.getAddTime());
			//helper.getUserName(contactDT.getAddUserId())
			request.setAttribute("createdBy", contactDT.getAddUserName());
			request.setAttribute("updatedDate", StringUtils.formatDate(contactDT.getLastChgTime()));
			//helper.getUserName(contactDT.getLastChgUserId())
			request.setAttribute("updatedBy", contactDT.getLastChgUserName());

			String passedContextAction = request.getParameter("ContextAction");
			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS023", passedContextAction);
			ErrorMessageHelper.setErrMsgToRequest(request);
			String sCurrentTask = NBSContext.getCurrentTask(session);

			form.getAttributeMap().put("ContextAction", passedContextAction);
			form.getAttributeMap().put("CurrentTask", sCurrentTask);

			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setContextForEdit", start);
			return sCurrentTask;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *    retrieves Participations' and NBSActEntities' with types of PRVs and ORGs associated with Tuberculosis
	 * @param form
	 * @param proxyVO
	 * @param request
	 */
	public static void _loadEntities(CTContactForm form, CTContactProxyVO proxyVO, HttpServletRequest request) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "_loadEntities");
		try {
			Collection<Object> entitycoll = proxyVO.getcTContactVO().getActEntityDTCollection();
			TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
			Collection<Object> participantList = new ArrayList<Object>();
			//Investigator
			PersonVO investigatorPersonVO = getPersonVO(NEDSSConstants.CONTACT_PROVIDER, proxyVO);
			if (investigatorPersonVO != null) {
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
						.get(NEDSSConstants.CONTACT_PROVIDER+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+CTConstants.ContactInvestigator);
				String uidSt =  investigatorPersonVO.getThePersonDT().getPersonUid().toString() + "|" + investigatorPersonVO.getThePersonDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put(CTConstants.ContactInvestigator+"Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				form.getAttributeMap().put(CTConstants.ContactInvestigator+"SearchResult", helper.makePRVDisplayString(investigatorPersonVO));
				StringBuffer stBuff = new StringBuffer("");
				if (investigatorPersonVO.getThePersonNameDTCollection() != null) {

					Iterator personNameIt = investigatorPersonVO.getThePersonNameDTCollection()
							.iterator();

					while (personNameIt.hasNext()) {
						PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
						if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {

							stBuff.append((personNameDT.getFirstNm() == null) ? ""
									: (personNameDT.getFirstNm() + " "));
							stBuff.append((personNameDT.getLastNm() == null) ? ""
									: (personNameDT.getLastNm()));
						}
					}
				}
				form.getAttributeMap().put("investigatorName",stBuff.toString());
				String display = helper.makePRVDisplayString(investigatorPersonVO,true );
				if (parTypeVO.getTypeCd() != null
						&& !parTypeVO.getTypeCd().toUpperCase()
								.contains("SUBJ")) {
					ParticipantListDisplay participant = new ParticipantListDisplay();
					participant.setTitle(parTypeVO.getTypeDescTxt());
					participant.setDetail(display);
					participantList.add(participant);
				}
			}
			//Dispositioned by
			PersonVO dispositionPersonVO = getPersonVO(NEDSSConstants.CONTACT_DISPOSITIONED_BY, proxyVO);
			
			if (dispositionPersonVO  != null) {
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
						.get(NEDSSConstants.CONTACT_DISPOSITIONED_BY+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+CTConstants.DispositionedBy);
				String uidSt =  dispositionPersonVO .getThePersonDT().getPersonUid().toString() + "|" + dispositionPersonVO .getThePersonDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put(CTConstants.DispositionedBy+"Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				String display = helper.makePRVDisplayString(dispositionPersonVO,true );
				String display1 = helper.makePRVDisplayString(dispositionPersonVO );
				form.getAttributeMap().put(CTConstants.DispositionedBy+"SearchResult", display1);
				if (parTypeVO.getTypeCd() != null
						&& !parTypeVO.getTypeCd().toUpperCase()
								.contains("SUBJ")) {
					ParticipantListDisplay participant = new ParticipantListDisplay();
					participant.setTitle(parTypeVO.getTypeDescTxt());
					participant.setDetail(display);
					participantList.add(participant);
				}
			}

			//Other Infected Patient
			Long otherInfectedPatientUid = proxyVO.getcTContactVO().getcTContactDT().getThirdPartyEntityUid();
			if (otherInfectedPatientUid != null) {
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
						.get(NEDSSConstants.CONTACT_OTHER_INFECTED_PATIENT+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+CTConstants.OtherInfectedPatient);
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)request.getSession().getAttribute("NBSSecurityObject");
				PersonVO otherInfectedPatient = getPerson(otherInfectedPatientUid, request, nbsSecurityObj);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				String display = helper.makePatientDisplayString(otherInfectedPatient);
				if (otherInfectedPatient != null) {
					form.getAttributeMap().put(CTConstants.OtherInfectedPatient+"Uid", otherInfectedPatientUid);
					form.getAttributeMap().put(CTConstants.OtherInfectedPatient+"SearchResult", display);
				}
				if (parTypeVO.getTypeCd() != null
						&& !parTypeVO.getTypeCd().toUpperCase()
								.contains("SUBJ")) {
					ParticipantListDisplay participant = new ParticipantListDisplay();
					participant.setTitle(parTypeVO.getTypeDescTxt());
					participant.setDetail(display);
					participantList.add(participant);
				}
			}

			//Reporting Hospital
			OrganizationVO organizationVO = getOrganizationVO(NEDSSConstants.CONTACT_ORGANIZATION,proxyVO);
			if (organizationVO != null) {
				ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
						.get(NEDSSConstants.CONTACT_ORGANIZATION+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+CTConstants.ExposureSite);
				String uidSt =  organizationVO.getTheOrganizationDT().getOrganizationUid().toString() + "|" + organizationVO.getTheOrganizationDT().getVersionCtrlNbr().toString();
				form.getAttributeMap().put("CON106Uid", uidSt);
				QuickEntryEventHelper helper = new QuickEntryEventHelper();
				String display = helper.makeORGDisplayString(organizationVO,true);
				String display1 = helper.makeORGDisplayString(organizationVO);
				form.getAttributeMap().put("CON106SearchResult", display1);
				if (parTypeVO.getTypeCd() != null
						&& !parTypeVO.getTypeCd().toUpperCase()
								.contains("SUBJ")) {
					ParticipantListDisplay participant = new ParticipantListDisplay();
					participant.setTitle(parTypeVO.getTypeDescTxt());
					participant.setDetail(display);
					participantList.add(participant);
				}
			}
			if (participantList.size() > 0) {
				String sortMethod = "getTitle";
				NedssUtils util = new NedssUtils();
				util.sortObjectByColumn(sortMethod, participantList);
				request.setAttribute("participantList", participantList);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "_loadEntities", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static PersonVO getPerson(Long personUid,
			HttpServletRequest request,
			NBSSecurityObj nbsSecurityObj) {
        PersonVO personVO = null;
        MainSessionCommand msCommand = null;
        try
        {

            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "getPerson";
            Object[] oParams = new Object[] { personUid };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(request.getSession());
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            personVO = (PersonVO) arr.get(0);
        }
        catch (NumberFormatException e) {
			logger.error("Number format err in CTContactLoadUtil.getPerson(" + personUid.toString() + ")");
			}
        catch (Exception ex)
        {
        	logger.warn("CTContactLoadUtil.getPerson(" + personUid.toString() + ") had error");
            ex.printStackTrace();
        }
        return personVO;
	}


	public static OrganizationVO getOrganizationVO(String type_cd, CTContactProxyVO proxyVO) {

		try {
			Collection<Object>  organizationVOCollection  = null;
			OrganizationVO organizationVO = null;
			Collection<Object>  entityDtCollection  = null;
			NbsActEntityDT actEntityDt = null;
			entityDtCollection  = proxyVO.getcTContactVO().getActEntityDTCollection();
			organizationVOCollection  = proxyVO.getOrganizationVOCollection();
			if (entityDtCollection  != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = entityDtCollection.iterator(); anIterator1.hasNext();) {
					actEntityDt = (NbsActEntityDT) anIterator1.next();
					if (actEntityDt.getTypeCd() != null && (actEntityDt.getTypeCd().equalsIgnoreCase(type_cd))) {
						for (anIterator2 = organizationVOCollection.iterator(); anIterator2.hasNext();) {
							organizationVO = (OrganizationVO) anIterator2.next();
							if (organizationVO.getTheOrganizationDT().getOrganizationUid().longValue() == actEntityDt
									.getEntityUid().longValue()) {
								return organizationVO;
							} else
								continue;
						}
					} else
						continue;
				}
			}
			return null;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static PersonVO getPersonVO(String type_cd, CTContactProxyVO proxyVO) {
		try {
			Collection<Object>  nbsActEntityDtCOllection  = null;
			Collection<Object>  personVOCollection  = null;
			NbsActEntityDT actEntity = null;
			PersonVO personVO = null;
			nbsActEntityDtCOllection  = proxyVO.getcTContactVO().getActEntityDTCollection();
			personVOCollection  = proxyVO.getPersonVOCollection();
			if (nbsActEntityDtCOllection  != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = nbsActEntityDtCOllection.iterator(); anIterator1.hasNext();) {
					actEntity = (NbsActEntityDT) anIterator1.next();
					if (actEntity.getTypeCd() != null && (actEntity.getTypeCd().equalsIgnoreCase(type_cd))) {
						for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();) {
							personVO = (PersonVO) anIterator2.next();
							if (personVO.getThePersonDT().getPersonUid().longValue() == actEntity.getEntityUid().longValue()) {
								return personVO;
							} else
								continue;
						}
					} else
						continue;
				}
			}
			return null;
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public static CTContactProxyVO populateProxyVO(String contactRecordUID,HttpServletRequest request)
	throws IOException, ServletException {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "populateProxyVO");
		CTContactProxyVO ctProxyVO = null;

		try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();

			Object[] oParams = { new Long(contactRecordUID) };
			String sMethod = "getContactProxyVO";
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				ctProxyVO = (CTContactProxyVO) resultUIDArr.get(0);
			}
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "populateProxyVO", start);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error while loading Contact Record Case: "+e.getMessage(),e);
		}
		return ctProxyVO;
	}

	/**
	 * editHandler - Legacy
	 * @param form
	 * @param req		
	 * @throws Exception
	 */
	public static CTContactProxyVO editHandler(CTContactForm form, HttpServletRequest req)
			throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editHandler");
		CTContactProxyVO proxyVO = null;
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			proxyVO = editContactHandler(form, req);
			if (form.getErrorList().size() == 0) {

				CTContactVO contactVO = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO();
				// RVCT Edit Participations

				sendProxyToEJB(proxyVO, req, null);
			} else {
				form.setPageTitle(NBSPageConstants.EDIT_CONTACT, req);
			}
			req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editHandler", start);
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while editHandler() Case: "+e.getMessage(),e);
		}
		return proxyVO;
	}

	/**
	 * editPageHandler - dynamic pages
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static CTContactProxyVO editPageHandler(CTContactForm form, HttpServletRequest req)
			throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editPageHandler");
		CTContactProxyVO proxyVO = null;
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			proxyVO = editContactPageHandler(form, req);
			if (form.getErrorList().size() == 0) {
				sendProxyToEJB(proxyVO, req, null);
			} else {
				form.setPageTitle(NBSPageConstants.EDIT_CONTACT, req);
			}
			req.setAttribute("ActionMode", HTMLEncoder.encodeHtml(form.getActionMode()));
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editPageHandler", start);
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while editHandler() Case: "+e.getMessage(),e);
		}
		return proxyVO;
	}



	/**
	 * setEntitiesForEdit creates Participations' and NBSActEntities' with types of PRVs and ORGs associated with Tuberculosis
	 * @param proxyVO
	 * @param form
	 * @param revisionPatientUID
	 * @param userId
	 * @param request
	 */
	private static void setEntitiesForEdit(CTContactProxyVO proxyVO, CTContactForm form, Long contactUid, String userId, HttpServletRequest request, Collection<Object> oldActEntityDTColl) {

		try {
			Collection<Object>  entityColl = new ArrayList<Object> ();
			//Investigator
			String prvUid = form.getAttributeMap().get("CON137Uid") == null ? "" : (String) form.getAttributeMap().get("CON137Uid");
			createOrDeleteEntities(prvUid, form, proxyVO, NEDSSConstants.CONTACT_PROVIDER,userId, oldActEntityDTColl);
			String dispoUid = form.getAttributeMap().get("CON147Uid") == null ? "" : (String) form.getAttributeMap().get("CON147Uid");
			createOrDeleteEntities(dispoUid, form, proxyVO, NEDSSConstants.CONTACT_DISPOSITIONED_BY,userId, oldActEntityDTColl);
			String otherPatientUid = form.getAttributeMap().get("CON142Uid") == null ? "" : (String) form.getAttributeMap().get("CON142Uid");
			createOrDeleteEntities(otherPatientUid, form, proxyVO, NEDSSConstants.CONTACT_OTHER_INFECTED_PATIENT,userId, oldActEntityDTColl);
			//Reporting Hospital
			String strOrganizationUID = form.getAttributeMap().get("CON106Uid") == null ? "" : (String) form.getAttributeMap().get("CON106Uid");
			createOrDeleteEntities(strOrganizationUID, form, proxyVO, NEDSSConstants.CONTACT_ORGANIZATION,userId, oldActEntityDTColl);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}


	public static void createOrDeleteEntities(String newEntityUid, CTContactForm form, CTContactProxyVO proxyVO, String typeCd, String userId, Collection<Object> oldActEntityDTColl)
    {
		 try {
			logger.debug(" newEntityUid = " + newEntityUid + " old");
			    if(newEntityUid != null && newEntityUid.indexOf("|") == -1) {
			    	newEntityUid = newEntityUid + "|1";
			    }
				String uid = splitUid(newEntityUid);
				String versionCtrlNbr = splitVerCtrlNbr(newEntityUid);
				CTContactVO contactVO = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO();

			    Collection<Object>  newEntityCollection  = new ArrayList<Object> ();
			    Long ctContactUid = contactVO.getcTContactDT().getCtContactUid();

			    NbsActEntityDT oldActEntityDT = getActEntity(typeCd,oldActEntityDTColl);

			    //there is no old entity
			    if(oldActEntityDT == null)
			    {
			        if(uid != null && !uid.trim().equals(""))
			        {
			            logger.info("old par in null and create new only: " + uid);
			            newEntityCollection.add(createContactEntity(ctContactUid, uid, versionCtrlNbr, typeCd, userId));
			        }
			    } else {// if an old entity is there
			        Long oldEntityUid = oldActEntityDT.getEntityUid();
			        if(uid != null && !uid.trim().equals("") && !uid.equals(oldEntityUid.toString()))// if old entity has removed and a new one has added
			        {
			            //Delete and Create PamCaseEntity
			        	newEntityCollection.add(deleteContactCaseCaseEntity(typeCd, oldEntityUid, oldActEntityDTColl));
			            newEntityCollection.add(createContactEntity(ctContactUid, uid, versionCtrlNbr, typeCd, userId));
			        }
			        else if((uid == null || (uid != null && uid.trim().equals(""))))// if old entity has deleted but no new entity has created
			        {
			            logger.warn("no EntityUid (is cleared) or not set for typeCd: " + typeCd);
			            newEntityCollection.add(deleteContactCaseCaseEntity(typeCd, oldEntityUid, oldActEntityDTColl));
			        }
			    }
			    if(newEntityCollection.size() > 0) {
			    	proxyVO.getcTContactVO().getActEntityDTCollection().addAll(newEntityCollection);
			    }
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

    }


	private static NbsActEntityDT deleteContactCaseCaseEntity(String typeCd, Long oldUid, Collection<Object>  oldEntityDTCollection) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "deleteContactCaseCaseEntity");
    	NbsActEntityDT entityDT = null;
        try {
			if(oldEntityDTCollection  != null && oldEntityDTCollection.size() > 0) {
			   Iterator<Object>  anIterator = null;
			    for(anIterator = oldEntityDTCollection.iterator(); anIterator.hasNext();) {
			    	entityDT = (NbsActEntityDT)anIterator.next();
			        if(entityDT.getTypeCd().trim().equals(typeCd) && entityDT.getEntityUid().toString().equals(oldUid.toString())) {
			            logger.debug("deleting contactCaseEntity for typeCd: " + typeCd + " for investigation: " + entityDT.getEntityUid());
			            entityDT.setItDelete(true);
			            //entityDT.setItDirty(false);
			            entityDT.setItNew(false);
			            return entityDT;
			        }
			        else {
			            continue;
			        }
			    }
			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "deleteContactCaseCaseEntity", start);
        return entityDT;
    }

	private static NbsActEntityDT getActEntity(String type_cd, Collection<Object>  oldActEntityCollection)
    {

	NbsActEntityDT actEntityDT = null;
	try {
		if(oldActEntityCollection  != null && oldActEntityCollection.size() > 0)
		{
		   Iterator<Object>  anIterator = null;
		    for(anIterator = oldActEntityCollection.iterator(); anIterator.hasNext();)
		    {
		    	actEntityDT = (NbsActEntityDT)anIterator.next();
			if((actEntityDT.getEntityUid() != null) && type_cd.equals(actEntityDT.getTypeCd()))
			{
			   logger.debug("actEntityDT exist for investigation: " + actEntityDT.getActUid() + " subjectEntity: " +  actEntityDT.getEntityUid());
			   return  actEntityDT;
			}
			else continue;
		    }
		}
	} catch (Exception e) {
					logger.error("Error: "+e.getMessage());
		e.printStackTrace();
	}
	return null;
    }

	/**
	 * editContactHandler - Legacy
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static CTContactProxyVO editContactHandler(CTContactForm form, HttpServletRequest req) throws Exception {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "editContactHandler");
		CTContactProxyVO proxyVO = null;

		try {
			form.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			form.setErrorList(new ArrayList<Object>());
			String invFormCd = form.getPageFormCd();
			loadQuestions(invFormCd);
			handleRaceForRules(form.getcTContactClientVO());
			handleFormRules(form,NEDSSConstants.EDIT_SUBMIT_ACTION);
			proxyVO = new CTContactProxyVO();
			if(form.getErrorList()==null || form.getErrorList().size()==0){
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
				proxyVO.setItDirty(true);
				proxyVO.setItNew(false);
				setContactForEdit(form, req, proxyVO, userId);

				PersonVO personVO =  form.getcTContactClientVO().getOldCtContactProxyVO().getContactPersonVO();

				CTContactVO contactVO = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO();
				Collection<Object> oldActEntityDTColl = contactVO.getActEntityDTCollection();
				personVO.setItDirty(true);
				personVO.setItNew(false);
				setPatientForEventEdit(form, personVO, proxyVO, req, userId);
				//persist all answers
				setContactSpecifcAnswersForCreateEdit(proxyVO, form, session, userId);
				setEntitiesForCreateEdit(form, proxyVO, personVO.getThePersonDT().getPersonUid(), personVO.getThePersonDT().getVersionCtrlNbr().toString(),  userId);

				setEntitiesForEdit(proxyVO, form, contactVO.getcTContactDT().getCtContactUid(), userId,req, oldActEntityDTColl);
				Collection<Object>  patientColl = new ArrayList<Object> ();
				patientColl.add(personVO);
				proxyVO.setContactPersonVO(personVO);

			} else {
				CTContactProxyVO prxyVO = form.getcTContactClientVO().getOldCtContactProxyVO();
				String contactUid = prxyVO.getcTContactVO().getcTContactDT().getCtContactUid().toString();
			}

			// set investigation createdBy/Date, updatedBy/Date in request.
			CTContactDT conDT = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getcTContactDT();
			req.setAttribute("createdDate", StringUtils.formatDate(conDT.getAddTime()));
			req.setAttribute("createdBy", conDT.getAddUserName());
			req.setAttribute("updatedDate", StringUtils.formatDate(conDT.getLastChgTime()));
			req.setAttribute("updatedBy", conDT.getLastChgUserName());
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "editContactHandler", start);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Submitting Create " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}

	/**
	 * editContactPageHandler - dynamic pages
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public static CTContactProxyVO editContactPageHandler(CTContactForm form, HttpServletRequest req) throws Exception {
		CTContactProxyVO proxyVO = null;

		try {
			form.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			form.setErrorList(new ArrayList<Object>());
			String invFormCd = form.getPageFormCd();
			PageCreateHelper pch = new PageCreateHelper();
			pch.loadQuestions(invFormCd);
			PageCreateHelper.handleRaceForRules((ClientVO)form.getcTContactClientVO());
			pch.handleFormRules((BaseForm) form,(ClientVO)form.getcTContactClientVO(), NEDSSConstants.EDIT_SUBMIT_ACTION);
			proxyVO = new CTContactProxyVO();
			Long providerUid=null;  //Provider associated with logged in User
			if(form.getErrorList()==null || form.getErrorList().size()==0){
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
				providerUid= nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
				proxyVO.setItDirty(true);
				proxyVO.setItNew(false);
				setContactForEdit(form, req, proxyVO, userId);

				PersonVO personVO =  form.getcTContactClientVO().getOldCtContactProxyVO().getContactPersonVO();
				personVO.setItDirty(true);
				personVO.setItNew(false);

				CTContactVO oldContactVO = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO();
				Collection<Object> oldActEntityDTColl = oldContactVO.getActEntityDTCollection();

				PageCreateHelper.setPatientForEventEdit((BaseForm) form, personVO, (ClientVO) form.getcTContactClientVO(), req, userId);
				//setPatientForEventEdit(form, personVO, proxyVO, req, userId);
				//persist all answers

				//Map<Object, Object> oldAnswers =  form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getCtContactAnswerDTMap();
				Map<Object, Object> newAnswers = pch
						.setPageSpecifcAnswersForCreateEdit((BaseForm) form,
								(ClientVO) form.getcTContactClientVO(),
								session, userId);
			proxyVO.getcTContactVO().setCtContactAnswerDTMap(newAnswers);
			//updateNBSAnswersForDirty
//				proxyVO.getcTContactVO()
//					.getCtContactAnswerDTMap()
//						.putAll(PageCreateHelper.updateNbsAnswersForDirty(oldAnswers, newAnswers));

				setEntitiesForCreateEdit(form, proxyVO, personVO.getThePersonDT().getPersonUid(), personVO.getThePersonDT().getVersionCtrlNbr().toString(),  userId);

				setEntitiesForEdit(proxyVO, form, oldContactVO.getcTContactDT().getCtContactUid(), userId,req, oldActEntityDTColl);
				
				form.getcTContactProxyVO().setContactPersonVO(personVO);
				//proxyVO.getcTContactVO().setRepeatingAnswerDTMap
				proxyVO.getcTContactVO().setRepeatingAnswerDTMap(
						pch.setRepeatingQuestionsBatch((BaseForm)form,
								form.getPageFormCd(), userId, providerUid));

				Collection<Object>  patientColl = new ArrayList<Object> ();
				patientColl.add(personVO);
				proxyVO.setContactPersonVO(personVO);

				 boolean stdProgramArea = PropertyUtil.isStdOrHivProgramArea( proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd()) ;
				 if (stdProgramArea
						 && proxyVO.getcTContactVO().getcTContactDT().getContactEntityPhcUid() == null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd() != null
						 && proxyVO.getcTContactVO().getcTContactDT().getProcessingDecisionCd().equalsIgnoreCase(CTConstants.RecordSearchClosure)) {
					if (form.getAttributeMap().containsKey(CTConstants.RecordSearchClosureInvestigation))
							proxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid((Long)form.getAttributeMap().get(CTConstants.RecordSearchClosureInvestigation));
				 } //stdProgArea set RSC Investigation
				 //Do we need to set Other Infected Patient?
				 if (stdProgramArea
						 && form.getcTContactClientVO().getAnswerMap().containsKey(CTConstants.NamingBetween)) {
						 String namingBetween = (String) form.getcTContactClientVO().getAnswerMap().get(CTConstants.NamingBetween);
						 if (namingBetween.equals("OTHPAT")) {
							 	if (form.getAttributeMap().containsKey(CTConstants.StdThirdParthEntityUid)) {
							 		Long thirdPartyEntityUid = new Long ((String)form.getAttributeMap().get(CTConstants.StdThirdParthEntityUid));
							 		proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityUid(thirdPartyEntityUid);
							 	}
							 	if (form.getAttributeMap().containsKey(CTConstants.StdThirdParthEntityPhcUid)) {
							 		Long thirdPartyEntityPhcUid = new Long ((String)form.getAttributeMap().get(CTConstants.StdThirdParthEntityPhcUid));
							 		proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityPhcUid(thirdPartyEntityPhcUid);
							 	}
						 } else { //if THSPAT shouldn't be set
							 proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityUid(null);
							 proxyVO.getcTContactVO().getcTContactDT().setThirdPartyEntityPhcUid(null);
						 }
				 }
			} else {
				CTContactProxyVO prxyVO = form.getcTContactClientVO().getOldCtContactProxyVO();
				String contactUid = prxyVO.getcTContactVO().getcTContactDT().getCtContactUid().toString();
			}


			// set investigation createdBy/Date, updatedBy/Date in request.
			CTContactDT conDT = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getcTContactDT();
			req.setAttribute("createdDate", StringUtils.formatDate(conDT.getAddTime()));
			req.setAttribute("createdBy", conDT.getAddUserName());
			req.setAttribute("updatedDate", StringUtils.formatDate(conDT.getLastChgTime()));
			req.setAttribute("updatedBy", conDT.getLastChgUserName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Submitting Edit " +  form.getPageFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}




	private static void setContactForEdit(CTContactForm form, HttpServletRequest req, CTContactProxyVO proxy, String userId) {

		try {
			Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();
			CTContactVO ctContactVO = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO();
			ctContactVO.getcTContactDT().setJurisdictionCd(getVal(answerMap.get(CTConstants.JURISDICTION_CD)));
			ctContactVO.getcTContactDT().setProgAreaCd(getVal(form.getcTContactClientVO().getAnswerMap().get(CTConstants.PROGRAM_AREA)));

			String sharedInd = null;
			if(answerMap.get(CTConstants.SHARED_IND)!= null && answerMap.get(CTConstants.SHARED_IND).equals("1"))
				sharedInd = "T";
			else if((answerMap.get(CTConstants.SHARED_IND)!= null && !answerMap.get(CTConstants.SHARED_IND).equals("1"))||answerMap.get(CTConstants.SHARED_IND)== null)
				sharedInd = "F";
			ctContactVO.getcTContactDT().setSharedIndCd(sharedInd);

			ctContactVO.getcTContactDT().setContactStatus(getVal(answerMap.get(CTConstants.Status)));
			ctContactVO.getcTContactDT().setPriorityCd(getVal(answerMap.get(CTConstants.Priority)));
			ctContactVO.getcTContactDT().setGroupNameCd(getVal(answerMap.get(CTConstants.GroupId)));
			ctContactVO.getcTContactDT().setDispositionCd(getVal(answerMap.get(CTConstants.Disposition)));
			ctContactVO.getcTContactDT().setDispositionDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DispositionDate))));
			ctContactVO.getcTContactDT().setRelationshipCd(getVal(answerMap.get(CTConstants.Relationship)));
			ctContactVO.getcTContactDT().setHealthStatusCd(getVal(answerMap.get(CTConstants.HealthStatus)));
			ctContactVO.getcTContactDT().setTxt(getVal(answerMap.get(CTConstants.GeneralComment)));
			ctContactVO.getcTContactDT().setSymptomCd(getVal(answerMap.get(CTConstants.AnySignsOrSymptoms)));
			ctContactVO.getcTContactDT().setSymptomOnsetDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.SymptomOnsetDate))));
			ctContactVO.getcTContactDT().setSymptomTxt(getVal(answerMap.get(CTConstants.SignsSymptomsNotes)));
			ctContactVO.getcTContactDT().setRiskFactorCd(getVal(answerMap.get(CTConstants.RiskFactorsForIllness)));
			ctContactVO.getcTContactDT().setRiskFactorTxt(getVal(answerMap.get(CTConstants.RiskFactorNotes)));
			ctContactVO.getcTContactDT().setEvaluationCompletedCd(getVal(answerMap.get(CTConstants.TestingEvaluationCompleted)));
			ctContactVO.getcTContactDT().setEvaluationDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DateOfEvaluation))));
			ctContactVO.getcTContactDT().setEvaluationTxt(getVal(answerMap.get(CTConstants.EvaluationFindings)));
			ctContactVO.getcTContactDT().setTreatmentInitiatedCd(getVal(answerMap.get(CTConstants.WasTreatmentInitiatedForIllness)));
			ctContactVO.getcTContactDT().setTreatmentStartDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.TreatmentStartDate))));
			ctContactVO.getcTContactDT().setTreatmentNotStartRsnCd(getVal(answerMap.get(CTConstants.ReasonTreatmentNotStarted)));
			ctContactVO.getcTContactDT().setTreatmentEndCd(getVal(answerMap.get(CTConstants.WasTreatmentCompleted)));
			ctContactVO.getcTContactDT().setTreatmentEndDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.TreatmentEndDate))));
			ctContactVO.getcTContactDT().setTreatmentNotEndRsnCd(getVal(answerMap.get(CTConstants.ReasonTreatmentNotCompleted)));
			ctContactVO.getcTContactDT().setTreatmentTxt(getVal(answerMap.get(CTConstants.TreatmentNotes)));
			ctContactVO.getcTContactDT().setNamedOnDate(StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.DateNamed))));
			ctContactVO.getcTContactDT().setInvestigatorAssignedDate_s(getVal(answerMap.get(CTConstants.DateAssigned)));
			ctContactVO.getcTContactDT().setProcessingDecisionCd(getVal(answerMap.get(CTConstants.ProcessingDecision)));
			ctContactVO.getcTContactDT().setContactReferralBasisCd(getVal(answerMap.get(CTConstants.ReferralBasis)));
			String namedDuringStr = (String) answerMap.get(CTConstants.NamedDuring);
			if (namedDuringStr != null && !namedDuringStr.isEmpty())
				ctContactVO.getcTContactDT().setNamedDuringInterviewUid(Long.valueOf(namedDuringStr));
			ctContactVO.getcTContactDT().setRecordStatusTime(new Timestamp(new Date().getTime()));
			ctContactVO.getcTContactDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

			ctContactVO.getcTContactDT().setItNew(false);
			ctContactVO.getcTContactDT().setItDirty(true);

			ctContactVO.getcTContactDT().setAddUserId(Long.valueOf(userId));
			ctContactVO.getcTContactDT().setLastChgTime(new Timestamp(new Date().getTime()));
			ctContactVO.getcTContactDT().setLastChgUserId(Long.valueOf(userId));
			ctContactVO.setItNew(false);
			ctContactVO.setItDirty(true);

			proxy.setcTContactVO(ctContactVO);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}


	private static void setPatientForEventEdit(CTContactForm form, PersonVO personVO, CTContactProxyVO proxyVO, HttpServletRequest request, String userId) {
		long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventEdit");
		try {
			setDemographicInfoForEdit(personVO, form.getcTContactClientVO().getAnswerMap());
			setNamesForEdit(personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			setEntityLocatorParticipationsForEdit(personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			setRaceForEdit(personVO, form.getcTContactClientVO(), proxyVO, userId);
			setEthnicityForEdit(personVO, form.getcTContactClientVO().getAnswerMap(), userId);
			personVO.getThePersonDT().setItNew(false);
			personVO.getThePersonDT().setItDirty(true);
			personVO.setItNew(false);
			personVO.setItDirty(true);
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "setPatientForEventEdit", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 	On Edit Submit, NBS Answers should be marked as itNew, itDirty or itDelete based on the changes...
	 * @param form
	 * @param proxyVO
	 */
	private static void updateNbsAnswersForDirty(CTContactProxyVO proxyVO, Map<Object,Object> oldAnswers, Map<Object,Object> newAnswers ) {

		try {
			//Map<Object,Object> oldAnswers = form.getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getCtContactAnswerDTMap();
			//Map<Object,Object> newAnswers = proxyVO.getcTContactVO().getCtContactAnswerDTMap();
			//Iterate through the newAnswers and mark it accordingly
			//1. If present in new and not present in old - mark it NEW
			//2. If present in both - mark it DIRTY
			//3. If present in old and not present in new - mark it DELETE
			Iterator<Object> iter = newAnswers.keySet().iterator();
			while(iter.hasNext()) {
				String qId = getVal(iter.next()) ;
				Object obj = newAnswers.get(qId);
				if(obj instanceof ArrayList<?>) {
					ArrayList<Object> answerList = (ArrayList<Object> ) obj;
					checkAnswerListForDirty(oldAnswers, answerList);
				} else {
					CTContactAnswerDT dt = (CTContactAnswerDT) obj;
					Long qUid = (Long) dt.getNbsQuestionUid();
					if(oldAnswers.get(qUid) == null) {
						dt.setItNew(true);
						dt.setItDirty(false);
						dt.setItDelete(false);
					} else {
						CTContactAnswerDT oldDT = (CTContactAnswerDT) oldAnswers.get(qUid);
						dt.setItDirty(true);
						dt.setItNew(false);
						dt.setItDelete(false);
						dt.setCtContactAnswerUid(oldDT.getCtContactAnswerUid());
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
					ArrayList<?> answerList = (ArrayList<?> ) oldObj;
					markAnswerListForDelete(answerList);
				} else {
					CTContactAnswerDT dt = (CTContactAnswerDT)oldObj;
					dt.setItDelete(true);
					dt.setItNew(false);
					dt.setItDirty(false);
				}

			}

			proxyVO.getcTContactVO().getCtContactAnswerDTMap().putAll(oldAnswers);
			proxyVO.getcTContactVO().getCtContactAnswerDTMap().putAll(newAnswers);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private static void checkAnswerListForDirty(Map<Object,Object> oldAnswers, ArrayList<Object> list) {
		try {
			ArrayList<Object> tempList = new ArrayList<Object> ();
			ArrayList<Object> oldAList = null;
			Iterator<Object> iter = list.iterator();
			while(iter.hasNext()) {
				CTContactAnswerDT dt = (CTContactAnswerDT)iter.next();
				Long qUid = (Long) dt.getNbsQuestionUid();
				oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
				if(oldAList != null && oldAList.size() > 0) {
					Iterator<Object> oldIter = oldAList.iterator();
					while(oldIter.hasNext()) {
						CTContactAnswerDT oldDT = (CTContactAnswerDT)oldIter.next();
						 if(oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt())) {
								dt.setItDirty(true);
								dt.setItNew(false);
								dt.setItDelete(false);
								dt.setCtContactAnswerUid(oldDT.getCtContactAnswerUid());
								tempList.add(oldDT);
						 } else if(dt.getCtContactAnswerUid() == null ){
								dt.setItNew(true);
								dt.setItDirty(false);
								dt.setItDelete(false);
						 }
					 }
				}
			}
			//remove tempList entries from oldMap
			if(oldAList != null)
				oldAList.removeAll(tempList);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void markAnswerListForDelete(ArrayList<?> list) {
		try {
			Iterator<?> iter = list.iterator();
			while(iter.hasNext()) {
				CTContactAnswerDT dt = (CTContactAnswerDT)iter.next();
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void setDemographicInfoForEdit(PersonVO personVO, Map<Object,Object> answerMap) {

		try {
			String dob = getVal(answerMap.get(PamConstants.DOB));
			String repAge = getVal(answerMap.get(PamConstants.REP_AGE));
			String bSex = getVal(answerMap.get(PamConstants.BIRTH_SEX));
			String cSex = getVal(answerMap.get(PamConstants.CURR_SEX));
			String priOccupation = answerMap.get(PamConstants.PRIMARY_OCCUPATION) == null ? "" : (String) answerMap.get(PamConstants.PRIMARY_OCCUPATION);
			String priLanguage = answerMap.get(PamConstants.PRIMARY_LANGUAGE) == null ? "" : (String) answerMap.get(PamConstants.PRIMARY_LANGUAGE);

			if(dob != "" || repAge != "" || bSex != "" || cSex != "") {
				personVO.getThePersonDT().setAsOfDateSex(setPDate(answerMap, PamConstants.SEX_AND_BIRTH_INFORMATION_AS_OF));

			} else {
				personVO.getThePersonDT().setAsOfDateSex_s(null);
				answerMap.remove(PamConstants.SEX_AND_BIRTH_INFORMATION_AS_OF);
			}

			String deceased = getVal(answerMap.get(PamConstants.IS_PAT_DECEASED));
			if(deceased.equals("")) {
				personVO.getThePersonDT().setAsOfDateMorbidity_s(null);
				answerMap.remove(PamConstants.MORTALITY_INFORMATION_AS_OF);
			} else
				personVO.getThePersonDT().setAsOfDateMorbidity(setPDate(answerMap, PamConstants.MORTALITY_INFORMATION_AS_OF));

			String marital = getVal(answerMap.get(PamConstants.MAR_STAT));
			if(marital!= "" || priOccupation != "" || priLanguage != "") {
				personVO.getThePersonDT().setAsOfDateGeneral(setPDate(answerMap, PamConstants.MARITAL_STATUS_AS_OF));
			} else{
				personVO.getThePersonDT().setAsOfDateGeneral_s(null);
				answerMap.remove(PamConstants.MARITAL_STATUS_AS_OF);
			}


			String ethnicity = getVal(answerMap.get(PamConstants.ETHNICITY));
			if(ethnicity.equals("")) {
				personVO.getThePersonDT().setAsOfDateEthnicity_s(null);
				answerMap.remove(PamConstants.ETHNICITY_AS_OF);
			} else
				personVO.getThePersonDT().setAsOfDateEthnicity(setPDate(answerMap, PamConstants.ETHNICITY_AS_OF));

			personVO.getThePersonDT().setDescription(getVal(answerMap.get(PamConstants.GEN_COMMENTS)));
			personVO.getThePersonDT().setCurrSexCd(getVal(answerMap.get(PamConstants.CURR_SEX)));
			personVO.getThePersonDT().setPreferredGenderCd(getVal(answerMap.get(PageConstants.SEX_UNKNOWN_REASON)));
			personVO.getThePersonDT().setAdditionalGenderCd(getVal(answerMap.get(PageConstants.TRANSGENDER_INFORMATION)));
			personVO.getThePersonDT().setDeceasedIndCd(getVal(answerMap.get(PamConstants.IS_PAT_DECEASED)));
			personVO.getThePersonDT().setMaritalStatusCd(getVal(answerMap.get(PamConstants.MAR_STAT)));
			personVO.getThePersonDT().setEthnicGroupInd(getVal(answerMap.get(PamConstants.ETHNICITY)));
			personVO.getThePersonDT().setOccupationCd(getVal(answerMap.get(PamConstants.PRIMARY_OCCUPATION)));
			personVO.getThePersonDT().setPrimLangCd(getVal(answerMap.get(PamConstants.PRIMARY_LANGUAGE)));

			setCommonDemographicInfo(personVO, answerMap);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}
	 private static Timestamp setPDate(Map<Object,Object> answerMap, String id) {

	    	Object obj = answerMap.get(id);
	    	if(obj != null)
	    		return StringUtils.stringToStrutsTimestamp(obj.toString());

	    	return null;
	    }

	 public static void setNamesForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

			try {
				Collection<Object>  names = personVO.getThePersonNameDTCollection();
				Collection<Object>  pdts = new ArrayList<Object> ();
				String lastNm = getVal(answerMap.get(PamConstants.LAST_NM));
				String firstNm = getVal(answerMap.get(PamConstants.FIRST_NM));
				String middleNm = getVal(answerMap.get(PamConstants.MIDDLE_NM));
				String suffix = getVal(answerMap.get(PamConstants.SUFFIX));
				String alias = getVal(answerMap.get(PamConstants.ALIAS_NICK_NAME));
				String asOfDate = getVal(answerMap.get(PamConstants.NAME_INFORMATION_AS_OF));

				if (names != null && names.size() > 0) {
					Iterator<Object> ite = names.iterator();
					while (ite.hasNext()) {
						PersonNameDT pdt = (PersonNameDT) ite.next();
						if ((lastNm == null || lastNm.trim().equals("")) && (firstNm == null || firstNm.trim().equals(""))
								&& (middleNm == null || middleNm.trim().equals("")) && (alias == null || alias.trim().equals(""))
								&& (suffix == null || suffix.trim().equals(""))) {
							pdt.setItNew(false);
							pdt.setItDirty(false);
							pdt.setItDelete(true);
							answerMap.remove(PamConstants.NAME_INFORMATION_AS_OF);
						} else if(pdt.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME)){
							pdt.setItNew(false);
							pdt.setItDirty(true);
							pdt.setLastNm(lastNm);
							pdt.setFirstNm(firstNm);
							pdt.setMiddleNm(middleNm);
							pdt.setNmSuffix(suffix);
							pdt.setStatusCd("A");
							pdt.setLastChgTime(new Timestamp(new Date().getTime()));
							pdt.setAsOfDate_s(asOfDate);
						}else if(pdt.getNmUseCd().equals(NEDSSConstants.ALIAS_NAME))
						{
							pdt.setItNew(false);
							pdt.setItDirty(true);
							pdt.setFirstNm(alias);
							pdt.setStatusCd("A");
							pdt.setLastChgTime(new Timestamp(new Date().getTime()));
							pdt.setAsOfDate_s(asOfDate);
						}
						pdts.add(pdt);
					}

				} else {
					setNames(null, personVO, answerMap, userId);
				}

				if(names != null && names.size()==1 && alias != null && !alias.equals(""))
				{
					Iterator<Object> ite = names.iterator();
					while (ite.hasNext()) {
						PersonNameDT pdt = (PersonNameDT) ite.next();
						Long personUID = personVO.getThePersonDT().getPersonUid();
						if(!pdt.getNmUseCd().equals(NEDSSConstants.ALIAS_NAME) && (alias != null || alias != ""))
						{
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
							pdts.add(pAliasDt);
						}
					}
				}
				if(names != null && names.size() > 0)
					personVO.setThePersonNameDTCollection(pdts);
			} catch (NumberFormatException e) {
							logger.error("Error: "+e.getMessage());
				e.printStackTrace();
			}
		}
	 public static void setRaceForEdit(PersonVO personVO, CTContactClientVO clientVO, CTContactProxyVO proxyVO, String userId) {
			//remove the added one for rules if any...
			try {
				clientVO.getAnswerMap().remove(PamConstants.RACE);
				Map<Object,Object> answerMap = clientVO.getAnswerMap();
				Long personUID = personVO.getThePersonDT().getPersonUid();
				String asOfDate = getVal(answerMap.get(PamConstants.RACE_INFORMATION_AS_OF));
				if(clientVO.getUnKnownRace() == 0 && clientVO.getAfricanAmericanRace() == 0 && clientVO.getAmericanIndianAlskanRace() == 0 && clientVO.getAsianRace() == 0 && clientVO.getHawaiianRace() == 0 && clientVO.getWhiteRace() == 0)
					answerMap.remove(PamConstants.RACE_INFORMATION_AS_OF);

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
						  clientVO.getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_ASIAN);
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
						  clientVO.getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_HAWAII);
					  }

					  String[] asianList = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_ASIAN);
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
					  String[] hawaiiList = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_HAWAII);
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
					}
				}
			} catch (Exception e) {
							logger.error("Error: "+e.getMessage());
				e.printStackTrace();
			}

		}
	 public static void setEntityLocatorParticipationsForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

			try {
				Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
				if(arrELP!=null){
					Iterator<Object> it = arrELP.iterator();
					while(it.hasNext()){
						EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT)it.next();
						entityLocPartDT.setItDirty(true);
						entityLocPartDT.setItNew(false);
					}
				}


				String homePhone = getVal(answerMap.get(PamConstants.H_PHONE));
				String homeExt = getVal(answerMap.get(PamConstants.H_PHONE_EXT));
				String workPhone = getVal(answerMap.get(PamConstants.W_PHONE));
				String workExt = getVal(answerMap.get(PamConstants.W_PHONE_EXT));
				String cell = getVal(answerMap.get(PamConstants.CELL_PHONE));
				String email = getVal(answerMap.get(PamConstants.EMAIL));


				if(homePhone.equals("") && homeExt.equals("") && workPhone.equals("") && workExt.equals("") && cell.equals("") && cell.equals("")&& email.equals("") && email.equals(""))
					answerMap.remove(PamConstants.TELEPHONE_INFORMATION_AS_OF);

				String city = getVal(answerMap.get(PamConstants.CITY));
				String street1 = getVal(answerMap.get(PamConstants.ADDRESS_1));
				String street2 = getVal(answerMap.get(PamConstants.ADDRESS_2));
				String zip = getVal(answerMap.get(PamConstants.ZIP));
				String state = getVal(answerMap.get(PamConstants.STATE));
				String birthCntry = getVal(answerMap.get(PamConstants.BIRTH_COUNTRY));
				String token = null;
				if(state.equals("nedss.properties:NBS_STATE_CODE"))
				{
					 StringTokenizer st = new StringTokenizer(state, ":");
					 while (st.hasMoreTokens())
					    {
						 token = st.nextToken();
					    }
					 state = PropertyUtil.getInstance().getNBS_STATE_CODE();
				}

				String county = getVal(answerMap.get(PamConstants.COUNTY));
				String country = getVal(answerMap.get(PamConstants.COUNTRY));
				String asOfDate = getVal(answerMap.get(PamConstants.ADDRESS_INFORMATION_AS_OF));
				String cityLimits = getVal(answerMap.get(PamConstants.WITHIN_CITY_LIMITS));

				if (arrELP == null) {
					arrELP = new ArrayList<Object> ();
				}
				// home address
				EntityLocatorParticipationDT elp = getEntityLocatorParticipation(personVO, NEDSSConstants.POSTAL,
						NEDSSConstants.HOME, NEDSSConstants.HOME);
				if (elp != null ) {
					if ((city == null || city.equals("")) && (street1 == null || street1.equals(""))
							&& (street2 == null || street2.equals("")) && (zip == null || zip.equals(""))
							&& (county == null || county.equals("")) && (state == null || state.equals(""))
							&& (cityLimits == null || cityLimits.equals(""))) {

						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
						elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
						elp.setItNew(false);
						elp.setItDirty(true);
						elp.setItDelete(true);
						elp.getThePostalLocatorDT().setLastChgUserId(Long.valueOf(userId));
						elp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
						elp.getThePostalLocatorDT().setCityDescTxt(city);
						elp.getThePostalLocatorDT().setStreetAddr1(street1);
						elp.getThePostalLocatorDT().setStreetAddr2(street2);
						elp.getThePostalLocatorDT().setZipCd(zip);
						elp.getThePostalLocatorDT().setStateCd(state);
						elp.getThePostalLocatorDT().setCntyCd(county);
						elp.getThePostalLocatorDT().setCntryCd(country);
						elp.getThePostalLocatorDT().setWithinCityLimitsInd(cityLimits);
						answerMap.remove(PamConstants.ADDRESS_INFORMATION_AS_OF);
					} else { //addressAsOfDate is not null
						elp.setLastChgUserId(Long.valueOf(userId));
						elp.setLastChgTime(new Timestamp(new Date().getTime()));
						elp.setItNew(false);
						elp.setItDirty(true);
						elp.getThePostalLocatorDT().setLastChgUserId(Long.valueOf(userId));
						elp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
						elp.getThePostalLocatorDT().setCityDescTxt(city);
						elp.getThePostalLocatorDT().setStreetAddr1(street1);
						elp.getThePostalLocatorDT().setStreetAddr2(street2);
						elp.getThePostalLocatorDT().setZipCd(zip);
						elp.getThePostalLocatorDT().setStateCd(state);
						elp.getThePostalLocatorDT().setCntyCd(county);
						elp.getThePostalLocatorDT().setCntryCd(country);
						elp.getThePostalLocatorDT().setWithinCityLimitsInd(cityLimits);
						elp.getThePostalLocatorDT().setItNew(false);
						elp.getThePostalLocatorDT().setItDirty(true);
						// Added this to make the record from Inactive to Active
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				        elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					}
				} else { //new address need to be created
					if ( ((city != null && !city.equals("")) || (street1 != null && !street1.equals(""))
									|| (street2 != null && !street2.equals("")) || (zip != null && !zip.equals(""))
									|| (county != null && !county.equals("")) || ((state != null && !state.equals("")) ))) {

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
						elpDT.setAsOfDate_s(asOfDate);

						PostalLocatorDT pl = new PostalLocatorDT();
						pl.setCityDescTxt(city);
						pl.setStreetAddr1(street1);
						pl.setStreetAddr2(street2);
						pl.setItNew(false);
						pl.setItDirty(true);
						pl.setAddTime(new Timestamp(new Date().getTime()));
						pl.setAddUserId(Long.valueOf(userId));
						pl.setCityDescTxt(city);
						pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
						pl.setStreetAddr1(street1);
						pl.setStreetAddr2(street2);
						pl.setZipCd(zip);
						pl.setStateCd(state);
						pl.setCntyCd(county);
						pl.setCntryCd(country);
						pl.setWithinCityLimitsInd(cityLimits);
						pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

						elpDT.setThePostalLocatorDT(pl);
						arrELP.add(elpDT);
					}
				}

				//home telephone
				elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.HOME, NEDSSConstants.PHONE);
				asOfDate = getVal(answerMap.get(PamConstants.TELEPHONE_INFORMATION_AS_OF));
				if (elp != null) {

					if ((homePhone == null || homePhone.equals("")) && (homeExt == null || homeExt.equals(""))) {
						//for edit when telephoneAsOfDate = null means blank telephone, then delete telephone
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
						elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
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
				} else if((homePhone!=null && homePhone!="")|| (homeExt!=null && homeExt!=""))
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
					teleDTHome.setItNew(false);
					teleDTHome.setItDirty(true);
					teleDTHome.setExtensionTxt(homeExt);
					teleDTHome.setPhoneNbrTxt(homePhone);
					teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					elpHome.setTheTeleLocatorDT(teleDTHome);
					arrELP.add(elpHome);
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
				} else if((cell != null && cell!=("")))
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
				elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.WORK_PHONE,NEDSSConstants.PHONE);

				if (elp != null) {
					if ((workPhone == null || workPhone.equals("")) && (workExt == null || workExt.equals(""))) {
						//for edit when worktelephoneAsOfDate = null means blank telephone, then delete telephone
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
						elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
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
				} else if((workPhone!=null && workPhone!="")|| (workExt!=null && workExt!=""))
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
					teleDTWork.setItNew(false);
					teleDTWork.setItDirty(true);

					teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					elpWork.setTheTeleLocatorDT(teleDTWork);
					arrELP.add(elpWork);
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
				} else if((email!=null && email!=""))
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


				elp = getEntityLocatorParticipation(personVO, NEDSSConstants.POSTAL, NEDSSConstants.BIRTH, NEDSSConstants.BIRTHCD);

				if(elp != null)
				{
					if ((birthCntry == null || birthCntry.equals(""))) {

						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
						elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
						elp.setItNew(false);
						elp.setItDirty(true);
						elp.setItDelete(true);
						//answerMap.remove(PamConstants.ADDRESS_INFORMATION_AS_OF);
					} else { //addressAsOfDate is not null
						elp.setLastChgUserId(Long.valueOf(userId));
						elp.setLastChgTime(new Timestamp(new Date().getTime()));
						elp.setItNew(false);
						elp.setItDirty(true);
						elp.getThePostalLocatorDT().setLastChgUserId(Long.valueOf(userId));
						elp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));

						elp.getThePostalLocatorDT().setCntryCd(birthCntry);
						elp.getThePostalLocatorDT().setWithinCityLimitsInd(cityLimits);
						elp.getThePostalLocatorDT().setItNew(false);
						elp.getThePostalLocatorDT().setItDirty(true);
						// Added this to make the record from Inactive to Active
						elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				        elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					}


				}else { //new address need to be created
					if ((birthCntry != null && birthCntry != ""))  {

						EntityLocatorParticipationDT elpDT = new EntityLocatorParticipationDT();
						elpDT.setItNew(true);
						elpDT.setItDirty(false);
						elpDT.setAddTime(new Timestamp(new Date().getTime()));
						elpDT.setAddUserId(Long.valueOf(userId));
						elpDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
						elpDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						elpDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
						elpDT.setCd(NEDSSConstants.BIRTHCD);
						elpDT.setClassCd(NEDSSConstants.POSTAL);
						elpDT.setUseCd(NEDSSConstants.BIRTH);
						elpDT.setAsOfDate_s(asOfDate);
						PostalLocatorDT pl = new PostalLocatorDT();
						pl.setItNew(false);
						pl.setItDirty(true);
						pl.setAddTime(new Timestamp(new Date().getTime()));
						pl.setAddUserId(Long.valueOf(userId));
						pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
						pl.setCntryCd(birthCntry);
						pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						elpDT.setThePostalLocatorDT(pl);
						arrELP.add(elpDT);
}
}


				personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
			} catch (Exception e) {
							logger.error("Error: "+e.getMessage());
				e.printStackTrace();
			}
		}
	 private static EntityLocatorParticipationDT getEntityLocatorParticipation(PersonVO personVO, String classCd,
				String useCd, String cd) {
			try {
				Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
				if (arrELP == null || arrELP.size() == 0) {
					return null;
				} else {
					Iterator<Object> itrAddress = arrELP.iterator();
					while (itrAddress.hasNext()) {

						EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) itrAddress.next();
						if (elp.getClassCd() != null && elp.getClassCd().equals(classCd) && elp.getUseCd() != null
								&& elp.getUseCd().equals(useCd) && elp.getLocatorUid() != null && elp.getCd() != null && elp.getCd().equals(cd)) {
							return elp;
						}
					}
				}
			} catch (Exception e) {
							logger.error("Error: "+e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
	 private static void setEthnicityForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		   try {
			if(personVO.getThePersonEthnicGroupDTCollection() == null)
				   setEthnicityForCreate(personVO, answerMap, userId);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	   }

	 private static void setIdsForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		   String patientSSN = getVal(answerMap.get(PamConstants.SSN));
		   if(patientSSN.equals(""))
			   answerMap.remove(PamConstants.SSN_AS_OF);
		   String asOfDate = getVal(answerMap.get(PamConstants.SSN_AS_OF));
		   if(personVO.getTheEntityIdDTCollection() == null || (personVO.getTheEntityIdDTCollection() != null && personVO.getTheEntityIdDTCollection().size() == 0))
			   setIds(null, personVO, answerMap, userId);
		   else {
			  Iterator<Object>  iter = personVO.getTheEntityIdDTCollection().iterator();
			   while(iter.hasNext()) {
				   EntityIdDT iddt = (EntityIdDT) iter.next();
				   iddt.setItNew(false);
				   iddt.setItDirty(true);
				   iddt.setLastChgTime(new Timestamp(new Date().getTime()));
				   iddt.setLastChgUserId(Long.valueOf(userId));
			       iddt.setRootExtensionTxt(patientSSN);
			       iddt.setAsOfDate_s(asOfDate);
			   }
		   }
	}
    public static void handleFormRules(CTContactForm form, String actionMode) {
    	long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "handleFormRules");
		//CTRulesEngineUtil ctReUtil = new CTRulesEngineUtil();
		try {
			Map<Object,Object> errorTabs = new HashMap<Object,Object>();
			Map<Object,Object> formFieldMap = initiateForm(questionMap, form);
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
			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "handleFormRules", start);
		} catch (Exception e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
    private static void handleRaceForRules(CTContactClientVO clientVO) {
		if(clientVO.getUnKnownRace() == 1 || clientVO.getAfricanAmericanRace() == 1 || clientVO.getAmericanIndianAlskanRace() == 1 || clientVO.getAsianRace() == 1 || clientVO.getHawaiianRace() == 1  || clientVO.getWhiteRace() == 1)
			clientVO.setAnswer(PamConstants.RACE, PamConstants.RACE);
    }


    private static void populatePatientSummary(CTContactForm form,CTContactProxyVO proxyVo, PersonVO personVO, String userId, String conditionCd, boolean newPat, HttpServletRequest request)
    {
    	NBSAuthHelper helper = new NBSAuthHelper();
    	try {
			if(!newPat)
			{

				CTContactDT ctContactDt = proxyVo.getcTContactVO().getcTContactDT();
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
					while (itname.hasNext()) {
						PersonNameDT nameDT = (PersonNameDT) itname.next();

						if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
							if(nameDT.getFirstNm() != null)
								strFName = nameDT.getFirstNm();
							if(nameDT.getMiddleNm() != null)
								strMName =  nameDT.getMiddleNm();
							if(nameDT.getLastNm() != null)
								strLName =   nameDT.getLastNm();
							if(nameDT.getNmSuffix() != null)
				        		  nmSuffix =   nameDT.getNmSuffix();

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
				form.getAttributeMap().put("LocalId",ctContactDt.getLocalId()== null ? "" : ctContactDt.getLocalId());
				form.getAttributeMap().put("Condition",conditionCd== null ? "" : cdv.getConditionDesc(conditionCd));
				//form.getAttributeMap().put("CONDITION_CD",conditionCd== null ? "" : conditionCd);
				form.getAttributeMap().put("Disposition",ctContactDt.getDispositionCd()== null ? "" : cdv.getDescForCode("NBS_DISPO",ctContactDt.getDispositionCd()));
				form.getAttributeMap().put("CreatedOn",ctContactDt.getAddTime()== null ? "" : StringUtils.formatDate(ctContactDt.getAddTime()));
				form.getAttributeMap().put("CreatedBy",ctContactDt.getAddUserId() == null ? "" : helper.getUserName(ctContactDt.getAddUserId()));
				form.getAttributeMap().put("UpdatedOn",ctContactDt.getLastChgTime()== null ? "" : StringUtils.formatDate(ctContactDt.getLastChgTime()));
				form.getAttributeMap().put("UpdatedBy",ctContactDt.getLastChgUserId() == null ? "" : helper.getUserName(ctContactDt.getLastChgUserId()));
			}
			else
			{
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				form.getAttributeMap().put("Condition",conditionCd== null ? "" : cdv.getConditionDesc(conditionCd));
				form.getAttributeMap().put("CreatedOn",formatter.format(date));
				form.getAttributeMap().put("CreatedBy",userId == null ? "" : helper.getUserName(Long.valueOf(userId)));

			}
		} catch (NumberFormatException e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		} catch (NEDSSSystemException e) {
						logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

    }

    public static ActionForward  deleteContact(ActionMapping mapping, CTContactForm form, HttpServletRequest request,
            HttpServletResponse response)throws IOException, ServletException {
    	long start = NedssTimeLogger.generateTimeDiffStartLog(CTContactLoadUtil.class.getCanonicalName(), "deleteContact");
    	CTContactProxyVO proxyVO = null;
		try {
			form.setActionMode(NEDSSConstants.DEL);
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
			String invFormCd = form.getPageFormCd();
			if (invFormCd.startsWith(NBSConstantUtil.ContactRecordFormPrefix)) {
				PageLoadUtil pageLoadUtil  = new PageLoadUtil();
				pageLoadUtil.loadQuestionKeys(invFormCd);
			} else { //legacy
				loadQuestions(invFormCd);
				loadQuestionKeys(invFormCd);
			}
			proxyVO = form.getcTContactClientVO().getOldCtContactProxyVO();
			// set itDelete to true and itDirty to false
			proxyVO.setItDelete(true);
			proxyVO.setItDirty(true);

			Long ctContactUid = sendProxyToEJB(proxyVO, request, sCurrentTask);

			NedssTimeLogger.generateTimeDiffEndLog(CTContactLoadUtil.class.getCanonicalName(), "deleteContact", start);
    	  }catch (Exception e) {
				logger.error("Exception happened in contactDeleteUtil"+ e);
				e.printStackTrace();
				throw new ServletException("Exception happened in contactDeleteUtil :"+e.getMessage(),e);
			}
	    	  return mapping.findForward("delete");
	      }
    protected static void setCommonSecurityForView(CTContactForm form, CTContactProxyVO proxyVO, NBSSecurityObj nbsSecurityObj) {

    	 boolean viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	              NBSOperationLookup.VIEW);

    	 boolean editContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	               NBSOperationLookup.EDIT,
	               proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd(),
	               proxyVO.getcTContactVO().getcTContactDT().getJurisdictionCd(), proxyVO.getcTContactVO().getcTContactDT().getSharedInd());

    	 boolean deleteContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	               NBSOperationLookup.DELETE,
	               proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd(),
	               proxyVO.getcTContactVO().getcTContactDT().getJurisdictionCd(), proxyVO.getcTContactVO().getcTContactDT().getSharedInd());

    	 boolean manageContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
	              NBSOperationLookup.MANAGE);

    	 form.getSecurityMap().put("viewContactTracingPermission", String.valueOf(viewContactTracing));
    	 form.getSecurityMap().put("editContactTracingPermission", String.valueOf(editContactTracing));
    	 form.getSecurityMap().put("deleteContactTracingPermission", String.valueOf(deleteContactTracing));
    	 form.getSecurityMap().put("manageContactTracingPermission", String.valueOf(manageContactTracing));


    }


	private static void setJurisdiction(CTContactForm form,  HttpSession session,NBSSecurityObj nbsSecurityObj) {

		try {
			String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" +programAreaCd + "')", conditionCd);
			if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
				programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", 2, conditionCd);
			if(programAreaVO==null)
				logger.error("In CTContactLoadUtil.setJurisdiction the programAreaVO is null.Please check:conditionCd:-"+conditionCd +" and programAreaCd:-"+programAreaCd);

			String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
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
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static void setJurisdictionForView(CTContactForm form, CTContactProxyVO proxyVO, String  conditionCd, NBSSecurityObj nbsSecurityObj,HttpServletRequest request) {

		try {
			String programAreaCd = proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd();
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" +programAreaCd + "')", conditionCd);
			if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
				programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", 2, conditionCd);
			if(programAreaVO==null)
				logger.error("In CTContactLoadUtil.setJurisdiction the programAreaVO is null.Please check:conditionCd:-"+conditionCd +" and programAreaCd:-"+programAreaCd);
			String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
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
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * getInvestigationSummary - load summary of the contacts investigations
	 * 		for the supplemental page
	 */
	public static Collection<Object> getInvestigationSummary(HttpServletRequest request, Long personUID)  {
		ArrayList<Object> invSumAR = null;
		try {
	  String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
      String sMethod = "getPersonInvestigationSummary";
      Object[] oParams = new Object[] {personUID};
      MainSessionHolder holder = new MainSessionHolder();
      MainSessionCommand msCommand = holder.getMainSessionCommand(request.getSession());
      ArrayList<?> arr;

		arr = msCommand.processRequest(sBeanJndiName, sMethod,
		                                           oParams);
		invSumAR  = (ArrayList<Object>) arr.get(0);
		if(invSumAR!=null && invSumAR.size()>0){
			NedssUtils nUtil = new NedssUtils();
			nUtil.sortObjectByColumn("getInvestigationStatusCd", invSumAR, false);
		}

		} catch (NEDSSConcurrentDataException e) {
			logger.error("Data concurrency error while loading Investigation Summary for Contact: " + e.toString());
		} catch (Exception e) {
			logger.error("Exception occurred while loading Investigation Summary for Contact: " + e.toString());
		}
		return invSumAR;
	}
	/*
	 * For STD Contact Records, the supplemental page has an investigation summary.
	 * Candidates for Record Search Closure and Secondary Referral are marked.
	 */
	public static String loadSupplimentalInvestigationInfo(
			CTContactForm form,
			Long personUID,
			String conditionCd,
			HttpServletRequest request) {
	 String secondaryReferralInvestigation = null;
	 String recordSearchClosureCandidate = null;
	 String recordSearchDisposition = ""; //if any
	 Collection<Object> investigationSummaryVOCollection =
			   getInvestigationSummary(request, personUID);
	 secondaryReferralInvestigation = checkForSecondaryReferral(investigationSummaryVOCollection, conditionCd, request);
	 if (secondaryReferralInvestigation == null || secondaryReferralInvestigation.isEmpty()) {
		   recordSearchClosureCandidate = checkForRecordSearchClosureCandidate (
				  form, investigationSummaryVOCollection, conditionCd, request);

	 }
	 recordSearchDisposition = setInvestigationSummary(investigationSummaryVOCollection,
			   recordSearchClosureCandidate,
			   secondaryReferralInvestigation,
			   request);
	 return recordSearchDisposition;
	}
	/*
	 * A secondary referral investigation exists if there is an open
	 * and not dispositioned investigation in the same condition group
	 */
	public static String checkForSecondaryReferral(
			Collection<Object> investigationSummaryVOCollection,
			String conditionCd,
			HttpServletRequest request) {
		Timestamp mostRecentDate = null;
		String secondaryReferralInvestigation = null;
		InvestigationSummaryVO srInvSummVO = null;
		Iterator<Object> itr = investigationSummaryVOCollection.iterator();
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
			if (mostRecentDate == null) {
				if (phc.getLocalId() != null && phc.getAddTime() != null) {
					secondaryReferralInvestigation = phc.getLocalId();
					srInvSummVO = phc;
					mostRecentDate = phc.getAddTime();
				}
			} else if (phc.getAddTime() != null && mostRecentDate.before(phc.getAddTime())) {
				if (phc.getLocalId() != null) {
				     mostRecentDate = phc.getAddTime();
				     srInvSummVO = phc;
					 secondaryReferralInvestigation = phc.getLocalId();
				}
			}
		} //while hasNext
		if(srInvSummVO!=null)
			NBSContext.store(request.getSession(), NBSConstantUtil.DSSecondaryReferralInvSummVO, srInvSummVO);
		return secondaryReferralInvestigation;
	}


	public static InvestigationSummaryVO checkForSecondaryReferral(HttpServletRequest request,
			Collection<Object> investigationSummaryVOCollection,
			String conditionCd
			) {
		Timestamp mostRecentDate = null;
		InvestigationSummaryVO secondaryReferralInvestigation = null;
		Iterator<Object> itr = investigationSummaryVOCollection.iterator();
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
			if (mostRecentDate == null) {
				if (phc.getLocalId() != null && phc.getAddTime() != null) {
					secondaryReferralInvestigation = phc;
					mostRecentDate = phc.getAddTime();
				}
			} else if (phc.getAddTime() != null && mostRecentDate.before(phc.getAddTime())) {
				if (phc.getLocalId() != null) {
				     mostRecentDate = phc.getAddTime();
					 secondaryReferralInvestigation = phc;
				}
			}
		} //while hasNext
		return secondaryReferralInvestigation;
	}
	/*
	 * A RSC candidate exists if there is an open
	 * open or closed dispositioned investigation in the same condition group
	 */
	public static String checkForRecordSearchClosureCandidate(
			CTContactForm form,
			Collection<Object> investigationSummaryVOCollection,
			String conditionCd,
			HttpServletRequest request) {
		Timestamp mostRecentDate = null;
		String recordSearchClosureCandidate = null;
		Long recordSearchClosureInvestigation = null;
		InvestigationSummaryVO rscInvSummVO = null;
		Iterator<Object> itr = investigationSummaryVOCollection.iterator();
		while (itr.hasNext()) {
			InvestigationSummaryVO phc = (InvestigationSummaryVO) itr.next();
			//not same condition or in condition family - skip
			if (phc.getCd() == null || (!CachedDropDowns.doesConceptCodeBelongToCodeSet(phc.getCd(), "CASE_DIAGNOSIS_SUBSET"+conditionCd) && !conditionCd.equals(phc.getCd())))
					continue;
			//if it has not been dispositioned, skip
			if (phc.getDisposition() == null || phc.getDisposition().isEmpty())
				    continue;
			if (mostRecentDate == null) {
				if (phc.getLocalId() != null && phc.getAddTime() != null) {
					recordSearchClosureCandidate = phc.getLocalId();
					recordSearchClosureInvestigation = phc.getPublicHealthCaseUid();
					mostRecentDate = phc.getAddTime();
					rscInvSummVO = phc;
				}
			} else if (phc.getAddTime() != null && mostRecentDate.before(phc.getAddTime())) {
				if (phc.getLocalId() != null) {
				     mostRecentDate = phc.getAddTime();
				     recordSearchClosureCandidate = phc.getLocalId();
				     recordSearchClosureInvestigation = phc.getPublicHealthCaseUid();
				     rscInvSummVO = phc;
				}
			}
		} //while hasNext
		//if RSC - we set the contactDt.contactEntityPhcUid if it doesn't have a value;
		if (recordSearchClosureInvestigation != null){
			form.getAttributeMap().put(CTConstants.RecordSearchClosureInvestigation, recordSearchClosureInvestigation);
			NBSContext.store(request.getSession(), NBSConstantUtil.DSRecordSearchClosureInvSummVO, rscInvSummVO);
		}
		return recordSearchClosureCandidate;
	}


	/*
	 * setInvestigationSummary - Set the list of investigations for the supplemental page
	 * retrieved using wumSqlQuery into Request
	 * @param investigationSummaryVOCollection
	 * @param request
	 */
	public static String setInvestigationSummary(
			Collection<Object> investigationSummaryVOCollection,
			String recordSearchClosureCandidate,
			String secondaryReferralInvestigation,
			HttpServletRequest request) {
		String recordSearchDisposition = "";
		Collection<PersonInvestgationSummaryDT> investigationEventList = new ArrayList<PersonInvestgationSummaryDT>();
		CachedDropDownValues cddV = new CachedDropDownValues();

		if (investigationSummaryVOCollection == null) {
			logger.debug("investigation summary collection arraylist is null");
		} else {
			Iterator<Object> itr = investigationSummaryVOCollection.iterator();

			while (itr.hasNext()) {
				PersonInvestgationSummaryDT dt = new PersonInvestgationSummaryDT();

				InvestigationSummaryVO investigation = (InvestigationSummaryVO) itr
						.next();

				if (investigation != null
						&& investigation.getPublicHealthCaseUid() != null) {

					String investigatorFirstName = investigation
							.getInvestigatorFirstName() == null ? ""
							: investigation.getInvestigatorFirstName();
					String investigatorLastName = investigation
							.getInvestigatorLastName() == null ? ""
							: investigation.getInvestigatorLastName();
					dt.setInvestigator(investigatorFirstName + " "
							+ investigatorLastName);
					dt.setConditions((investigation.getConditionCodeText() == null) ? ""
							: investigation.getConditionCodeText());

					dt.setCaseStatus((investigation.getCaseClassCodeTxt() == null) ? ""
							: investigation.getCaseClassCodeTxt());

					dt.setJurisdiction((investigation.getJurisdictionDescTxt() == null) ? ""
							: investigation.getJurisdictionDescTxt());

					dt.setInvestigationId((investigation.getLocalId() == null) ? ""
							: investigation.getLocalId());
					if (investigation.getInvestigationStatusCd().equals("O")) {
						String status = "<b><font color=\"#006000\">Open</font></b>";
						dt.setStatus(status);
					} else {
						dt.setStatus(investigation
								.getInvestigationStatusDescTxt());
					}

					String theDispoCd = investigation.getDisposition();
					String theDispoDesc = "";
					if (theDispoCd != null && !theDispoCd.isEmpty()) {
						theDispoDesc = cddV.getDescForCode( CTConstants.StdNbsDispositionLookupCodeset,theDispoCd);
					}
					dt.setDisposition(theDispoDesc);
					if (recordSearchClosureCandidate!= null &&
							!recordSearchClosureCandidate.isEmpty() &&
							recordSearchClosureCandidate.equals(dt.getInvestigationId())) {
								dt.setRscSecRef("RSC");
								if (theDispoCd != null) {
									 if (theDispoCd.equals("A")) //preventative treatment
										 recordSearchDisposition = "Z"; //prev preventative treated
									 else if (theDispoCd.equals("C")) //infected brought to treat
										 recordSearchDisposition = "E"; //prev treated
									 else
										 recordSearchDisposition = theDispoCd;
								}
					}

					if (secondaryReferralInvestigation != null &&
						!secondaryReferralInvestigation.isEmpty() &&
						secondaryReferralInvestigation.equals(dt.getInvestigationId()))
							dt.setRscSecRef("SR");

					investigationEventList.add(dt);
				} //not null
			} //while hasNext

			request.setAttribute("strContactInvestigationList",
					investigationEventList);
			request.getSession().setAttribute("strContactInvestigationList",
					investigationEventList);
			request.getSession().setAttribute("strContactInvestigationSummVOList",
					investigationSummaryVOCollection);
			request.setAttribute("strInvestigationEventSize",
					investigationEventList.size() == 0 ? "0" : new Integer(
							investigationEventList.size()).toString());
			//The codeset to use for Processing Decision depends on if there is an investigation
			//of the same condition out there for the contact.
			String StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionNoInv;
			if (secondaryReferralInvestigation != null &&
					!secondaryReferralInvestigation.isEmpty())
				StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionNoInvDispo;
			else if (recordSearchClosureCandidate!= null &&
					!recordSearchClosureCandidate.isEmpty())
				StdCrProcessingDecisionCodeset = CTConstants.StdCrProcessingDecisionInvDispoClosed;
			//set the code set to use for the Processing Decision
			request.setAttribute("StdCrProcessingDecisionCodeset", StdCrProcessingDecisionCodeset);
		}
		return recordSearchDisposition; //if any
	}
	/*
	 * stdPopulateInvestigationFromContact - create a PageActProxyVO from the contact information
	 * to automatically create a 'mini' investigation from the STD Contact per the design requirements.
	 *
	 */
	private static PageProxyVO stdPopulateInvestigationFromContact(
			CTContactForm form, HttpServletRequest req,
			CTContactProxyVO ctProxyVO, NBSSecurityObj nbsSecurityObj) {
		Long investigatorUid = 0L;
		Integer investigatorVersion = 1;
		Long contactUid = ctProxyVO.getContactPersonVO().thePersonDT.getPersonUid();
		Integer contactVersion = ctProxyVO.getContactPersonVO().thePersonDT.getVersionCtrlNbr();
		if (contactVersion == null)
			contactVersion = 1;
		PageActProxyVO proxyVO = new PageActProxyVO();
		proxyVO.setItNew(true);
		//Set PublicHealthCase Information

		ProgramAreaVO programAreaVO = PageCreateHelper.getProgAreaVO(req.getSession());
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

		//contact FF investigator is required
		String investigatorStr = (String) form.getAttributeMap().get("CONINV180Uid");
		if (investigatorStr != null && !investigatorStr.isEmpty()) {
			String uid = splitUid(investigatorStr);
			investigatorUid = new Long(uid);
			String versionNbr = splitVerCtrlNbr(investigatorStr);
			investigatorVersion = new Integer(versionNbr);
		}
		try {
		//fill in the PublicHealthCase and Case Management
		proxyVO.setPublicHealthCaseVO (stdSetPublicHealthCaseForCreate(
									form, programAreaVO, userId, req));



		//fill in co-infection questions if creating investigation in co-infection group
		String investigationType = form.getcTContactClientVO().getAnswer("investigationType");
		if(investigationType!=null && investigationType.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF))
			setCoinfectionData(proxyVO,req);

		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContact:Exception related to populating Public Health Case " +ex.getMessage());
		}
		try {


		//set participations
		Timestamp fromTime = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime();
		proxyVO.setTheParticipationDTCollection(stdSetParticipationsForCreate(
									 contactUid, investigatorUid, fromTime, userId));

		// create new_assignment message

		Long providerUid=nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
		proxyVO.setCurrentInvestigator(investigatorUid.toString());
		String programArea=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
		if(PropertyUtil.isStdOrHivProgramArea(programArea))
			MessageLogUtil.createMessageLogForParticipant(proxyVO, NEDSSConstants.PHC_INVESTIGATOR ,  null,providerUid);


		//set NBS Act Entities
		proxyVO.getPageVO().setActEntityDTCollection(stdSetNbsActEntityForCreate(
				contactUid, contactVersion, investigatorUid, investigatorVersion, userId));



		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContact:Exception related to Participations and Act Entities " +ex.getMessage());
		}
		//copy subject person over from Contact
		Collection<Object>  personVOCollection  = new ArrayList<Object>();
		try {
			PersonVO subjectVO = (PersonVO) ctProxyVO.getContactPersonVO().deepCopy();
			personVOCollection.add(subjectVO);
			proxyVO.setThePersonVOCollection(personVOCollection);
		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContact:Exception related to Contact Person " +ex.getMessage());
		}
//		try {
//
//			String originalInvestigatorUid = (String) NBSContext.retrieve(
//					req.getSession(), NBSConstantUtil.DSInvestigatorUid);
//			Long invUid = new Long(originalInvestigatorUid);
//			Long providerUid=nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
//			if (providerUid.longValue() != invUid.longValue()) {
//				// Add message to the queue
//
////				MessageLogUtil.createNewClusterMessage(nbsSecurityObj, proxyVO, invUid);
//
//			}
////			if(ctProxyVO.getcTContactVO().getcTContactDT().getThirdPartyEntityPhcUid() ! = null){
////			CTContactLoadUtil.
////			}
//		} catch (Exception e) {
//			logger.error("Unable to store the Error message in stdPopulateInvestigationFromContact for = "
//					+ "\ninvestigatorUid" + investigatorUid);
//		}

		return proxyVO;
	}




	private static void setCoinfectionData(PageActProxyVO proxyVO,HttpServletRequest req){
		try{

			CoinfectionSummaryVO csVO = ((CoinfectionSummaryVO)NBSContext.retrieve(req.getSession(),
	       	          NBSConstantUtil.DSCoinfectionInvSummVO));
			PageProxyVO coInfpageProxyVO = PageLoadUtil.getProxyObject(csVO.getPublicHealthCaseUid().toString(), NEDSSConstants.CASE, req.getSession());
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + csVO.getProgAreaCd() + "')", csVO.getConditionCd());
			String investigationFormCd = programAreaVO.getInvestigationFormCd();
			PageLoadUtil pageLoadUtil = new PageLoadUtil();
			pageLoadUtil.loadQuestionKeys(investigationFormCd);
			Map<Object, Object> answerMap  = ((PageActProxyVO)coInfpageProxyVO).getPageVO().getPamAnswerDTMap();
			Map<Object, Object> repeatingAnswerMap  = ((PageActProxyVO)coInfpageProxyVO).getPageVO().getPageRepeatingAnswerDTMap();
			Map<Object, Object> coInfectionQuestionMap = pageLoadUtil.getCoinfectionQuestionKeyMap();
			proxyVO.getPageVO().setPageRepeatingAnswerDTMap(new HashMap<Object,Object>());
			proxyVO.getPageVO().setPamAnswerDTMap(new HashMap<Object,Object>());
			if(coInfectionQuestionMap!=null && coInfectionQuestionMap.size()>0){
				Iterator<Object> ite = coInfectionQuestionMap.keySet().iterator();
				while(ite.hasNext()){
					Long qId = (Long)ite.next();
					if(answerMap.containsKey(qId)){
						Object obj = answerMap.get(qId);
						if(obj instanceof NbsAnswerDT){
							((NbsAnswerDT)obj).setItNew(true);
							((NbsAnswerDT)obj).setItDelete(false);
							((NbsAnswerDT)obj).setItDirty(false);
							proxyVO.getPageVO().getPamAnswerDTMap().put(qId, obj);
						}
						else if(obj instanceof ArrayList<?>){
							ArrayList<?> aList = (ArrayList<?>)obj;
							for(Object answer : aList){
								((NbsAnswerDT)answer).setItNew(true);
								((NbsAnswerDT)answer).setItDelete(false);
								((NbsAnswerDT)answer).setItDirty(false);
							}
							proxyVO.getPageVO().getPamAnswerDTMap().put(qId, aList);
						}
					}
					if(repeatingAnswerMap.containsKey(qId)){
						Object obj = repeatingAnswerMap.get(qId);
						if(obj instanceof NbsAnswerDT){
							((NbsAnswerDT)obj).setItNew(true);
							((NbsAnswerDT)obj).setItDelete(false);
							((NbsAnswerDT)obj).setItDirty(false);
							proxyVO.getPageVO().getPageRepeatingAnswerDTMap().put(qId, obj);
						}
						else if(obj instanceof ArrayList<?>){
							ArrayList<?> aList = (ArrayList<?>)obj;
							for(Object answer : aList){
								((NbsAnswerDT)answer).setItNew(true);
								((NbsAnswerDT)answer).setItDelete(false);
								((NbsAnswerDT)answer).setItDirty(false);
							}
							proxyVO.getPageVO().getPageRepeatingAnswerDTMap().put(qId, obj);
						}
					}
				}
			}

		}catch(Exception ex){
			logger.error("Exception while filling in co-infection questions :"+ex.getMessage());
		}
	}


	private static Collection<Object> stdSetParticipationsForCreate(
			Long patientUid, Long investigatorUid, Timestamp fromTime, String userId) {
		Collection<Object>  participationCollection  = new ArrayList<Object>();
		Long phcUid = -1L;

		ParticipationDT invPartDT = createParticipation(phcUid, patientUid, fromTime, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.PHC_PATIENT);
		participationCollection.add(invPartDT);
		invPartDT = createParticipation(phcUid, investigatorUid, fromTime, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.PHC_INVESTIGATOR);
		participationCollection.add(invPartDT);
		invPartDT = createParticipation(phcUid, investigatorUid, fromTime, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.STD_INITIAL_FOLLOWUP_INVESTIGATOR);
		participationCollection.add(invPartDT);
		invPartDT = createParticipation(phcUid, investigatorUid, fromTime, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.STD_FIELD_FOLLOWUP_INVESTIGATOR);
		participationCollection.add(invPartDT);
		invPartDT = createParticipation(phcUid, investigatorUid, fromTime, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.FIELD_FOLLOWUP_INVESTIGATOR);
		participationCollection.add(invPartDT);

		return participationCollection;
	}

	public static ParticipationDT createParticipation(Long actUid, Long subjectUid, Timestamp fromTime, String subjectClassCd, String typeCd) {

		ParticipationDT participationDT = new ParticipationDT();
		participationDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
		participationDT.setActUid(actUid);
		participationDT.setSubjectClassCd(subjectClassCd);
		participationDT.setSubjectEntityUid(subjectUid);
		participationDT.setTypeCd(typeCd.trim());
		participationDT.setTypeDescTxt(cdv.getDescForCode("PAR_TYPE", typeCd.trim()));
		participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		if (fromTime != null)
			participationDT.setFromTime(fromTime);
		else
			participationDT.setFromTime(new java.sql.Timestamp(new Date().getTime()));
		participationDT.setItNew(true);
		participationDT.setItDirty(false);

		return participationDT;
	}

	private static Collection<Object> stdSetNbsActEntityForCreate(
			Long patientUid, Integer patientVersion, Long investigatorUid,
			Integer investigatorVersion, String userId) {

		Collection<Object>  actEntityCollection  = new ArrayList<Object>();
		Long phcUid = -1L;

		NbsActEntityDT entityDT = createNbsActEntity (phcUid, patientUid, patientVersion, NEDSSConstants.PHC_PATIENT, userId);
		actEntityCollection.add(entityDT);
		entityDT = createNbsActEntity (phcUid, investigatorUid, investigatorVersion, NEDSSConstants.PHC_INVESTIGATOR, userId);
		actEntityCollection.add(entityDT);
		entityDT = createNbsActEntity (phcUid, investigatorUid, investigatorVersion, NEDSSConstants.STD_INITIAL_FOLLOWUP_INVESTIGATOR, userId);
		actEntityCollection.add(entityDT);
		entityDT = createNbsActEntity (phcUid, investigatorUid, investigatorVersion, NEDSSConstants.STD_FIELD_FOLLOWUP_INVESTIGATOR, userId);
		actEntityCollection.add(entityDT);
		entityDT = createNbsActEntity (phcUid, investigatorUid, investigatorVersion, NEDSSConstants.FIELD_FOLLOWUP_INVESTIGATOR, userId);
		actEntityCollection.add(entityDT);

		return actEntityCollection;
	}
	public static NbsActEntityDT createNbsActEntity(
			Long actUid, Long subjectUid, Integer versionCtrlNbr, String typeCd, String userId) {

		NbsActEntityDT entityDT = new NbsActEntityDT();
		entityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		entityDT.setAddUserId(Long.valueOf(userId));
		entityDT.setEntityUid(subjectUid);
		entityDT.setEntityVersionCtrlNbr(versionCtrlNbr);
		entityDT.setActUid(actUid);
		entityDT.setTypeCd(typeCd);
		return entityDT;
	}



	/*
	 * Based on PageCreateHelper.setPublicHealthCaseForCreate(). This creates a mini-investigation
	 * PHC from the STD contact record.
	 */
	private static PublicHealthCaseVO stdSetPublicHealthCaseForCreate(CTContactForm form,
			ProgramAreaVO programAreaVO, String userId,
			HttpServletRequest req) {

		logger.debug("in stdSetPublicHealthCaseForCreate()");
		Map<Object,Object> answerMap = form.getcTContactClientVO().getAnswerMap();
		CoinfectionSummaryVO csVO = null;
		String investigationType = form.getcTContactClientVO().getAnswer("investigationType");
		try{
			csVO = ((CoinfectionSummaryVO)NBSContext.retrieve(req.getSession(),
       	          NBSConstantUtil.DSCoinfectionInvSummVO));
        }catch(Exception ex){
        	logger.debug("Contact FF Generate Investigation - No co-infection " +ex.getMessage());
        }
		//Investigator is required on FF Create
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(-1));
		//Investigator is required on FF Create
		String currentInvestigator = (String) form.getAttributeMap().get(CTConstants.ContactFFInvestigatorUid);
		if (currentInvestigator != null && !currentInvestigator.isEmpty()) {
			String theUid = PageCreateHelper.splitUid(currentInvestigator);
			phcVO.getThePublicHealthCaseDT().setCurrentInvestigatorUid(new Long(theUid));
		}
		//phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime_s(getVal(answerMap.get(PageConstants.DATE_REPORTED)));
		phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(CTConstants.ContactFFInvStartDate)));
		phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
		phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(userId));
		//phcVO.getThePublicHealthCaseDT().setCaseClassCd(getVal(answerMap.get(PageConstants.CASE_CLS_CD)));
		phcVO.getThePublicHealthCaseDT().setCaseTypeCd("I");
		phcVO.getThePublicHealthCaseDT().setCaseClassCd("");
		phcVO.getThePublicHealthCaseDT().setCaseStatusDirty(false);
        //bring referral basis
		phcVO.getThePublicHealthCaseDT().setReferralBasisCd(getVal(answerMap.get(CTConstants.ReferralBasis)));
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		if (phcVO.getThePublicHealthCaseDT().getCd().equals(CTConstants.CongenitalSyphilisConditionCode) && 
				phcVO.getThePublicHealthCaseDT().getReferralBasisCd() != null &&
				phcVO.getThePublicHealthCaseDT().getReferralBasisCd().equals(CTConstants.CongenitalMotherFollowupM2)) {
			phcVO.getThePublicHealthCaseDT().setCd("700");  //mother of CS baby
			programAreaVO.setConditionShortNm("Syphilis, Unknown");
		}
			
		//referral code M2 is 
		if (phcVO.getThePublicHealthCaseDT().getReferralBasisCd() != null && phcVO.getThePublicHealthCaseDT().getReferralBasisCd().equals(CTConstants.CongenitalInfantFollowupM1)) {
				phcVO.getThePublicHealthCaseDT().setCd(CTConstants.CongenitalSyphilisConditionCode);  //baby of Syphilis mom
				programAreaVO.setConditionShortNm("Syphilis, congenital");	
		}
		answerMap.put(PageConstants.CONDITION_CD, phcVO.getThePublicHealthCaseDT().getCd());
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(programAreaVO.getConditionShortNm());
		phcVO.getThePublicHealthCaseDT().setContactInvStatus("");
		phcVO.getThePublicHealthCaseDT().setContactInvTxt("");
		phcVO.getThePublicHealthCaseDT().setCurrProcessStateCd(CTConstants.FieldFollowup);
		phcVO.getThePublicHealthCaseDT().setDayCareIndCd("");
		phcVO.getThePublicHealthCaseDT().setDetectionMethodCd("");
		phcVO.getThePublicHealthCaseDT().setDiseaseImportedCd("");
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationAmt("");
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd("");
		phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd("");
		phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
		phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd("");
		phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt("");
		phcVO.getThePublicHealthCaseDT().setImportedCountyCd("");
		phcVO.getThePublicHealthCaseDT().setImportedCountryCd("");
		phcVO.getThePublicHealthCaseDT().setImportedStateCd("");
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN );
		phcVO.getThePublicHealthCaseDT().setJurisdictionCd(getVal(answerMap.get(CTConstants.JURISDICTION_CD)));
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		phcVO.getThePublicHealthCaseDT().setStatusCd("A");
		phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
		if (csVO != null && investigationType != null && investigationType.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF))
			phcVO.getThePublicHealthCaseDT().setCoinfectionId(csVO.getCoinfectionId());
		phcVO.setCoinfectionCondition(CachedDropDowns.getConditionCoinfectionMap().containsKey(phcVO.getThePublicHealthCaseDT().getCd())? true:false);
		String sharedInd = null;
		if(answerMap.get(CTConstants.SHARED_IND)!= null && answerMap.get(CTConstants.SHARED_IND).equals("1")) 
			sharedInd = "T";
		else sharedInd = "F";
		phcVO.getThePublicHealthCaseDT().setSharedInd(sharedInd);

		RulesEngineUtil ruleEngUtil = new RulesEngineUtil();
		String invStartDate = getVal(answerMap.get(CTConstants.ContactFFInvStartDate));
		if (invStartDate != null && !invStartDate.isEmpty()) {
			int[] weekAndYear = ruleEngUtil.CalcMMWR(invStartDate);
			if (weekAndYear[0] != 0)
				phcVO.getThePublicHealthCaseDT().setMmwrWeek(Integer.toString(weekAndYear[0]));
			if (weekAndYear[1] != 0)
				phcVO.getThePublicHealthCaseDT().setMmwrYear(Integer.toString(weekAndYear[1]));
		}
		// These questions are for extending PHC table for common fields - ODS changes
		if(getVal(answerMap.get(CTConstants.ContactFFInvAssignedDate))!=null )
		{
			Timestamp assignedDate = StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.ContactFFInvAssignedDate)));
			if (assignedDate != null) {
				phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime(assignedDate);
				phcVO.getTheCaseManagementDT().setInitFollUpAssignedDate(assignedDate);
				phcVO.getTheCaseManagementDT().setFollUpAssignedDate(assignedDate);
			}
		}

		//get any case mgt questions
		phcVO.getTheCaseManagementDT().setCaseManagementDTPopulated(true);
		phcVO.getTheCaseManagementDT().setEpiLinkId(getVal(answerMap.get(CTConstants.ContactFFLotNbr)));
		phcVO.getTheCaseManagementDT().setInitFollUp(CTConstants.FieldFollowup);
		phcVO.getTheCaseManagementDT().setSubjComplexion(getVal(answerMap.get(PageConstants.COMPLEXION)));
		phcVO.getTheCaseManagementDT().setSubjHair(getVal(answerMap.get(PageConstants.HAIR)));
		phcVO.getTheCaseManagementDT().setSubjHeight(getVal(answerMap.get(PageConstants.HEIGHT)));
		phcVO.getTheCaseManagementDT().setSubjOthIdntfyngInfo(getVal(answerMap.get(PageConstants.OTHER_IDENTIFYING_INFORMATION)));
		phcVO.getTheCaseManagementDT().setSubjSizeBuild(getVal(answerMap.get(PageConstants.SIZE_BUILD)));
		phcVO.getTheCaseManagementDT().setInternetFollUp(getVal(answerMap.get(CTConstants.ContactFFInternetFollowup)));
		phcVO.getTheCaseManagementDT().setInitFollUpNotifiable(getVal(answerMap.get(CTConstants.ContactFFNotifiable)));

		phcVO.setItNew(true);
		phcVO.setItDirty(false);
		logger.debug("leaving stdSetPublicHealthCaseForCreate()");
		return(phcVO);
	}
	
	
	/*
	 * Used in View shows the Investigator Name and Date unless the value is 999999 Initiated without Interview
	 */
	public static String getTheNamedInterviewDisplayText(String selectedInterviewUid, Collection<Object> theInterviewSummary) {
		String returnText = "";
		if (selectedInterviewUid == null || selectedInterviewUid.isEmpty())
			return returnText;
		if (selectedInterviewUid.equals(CTConstants.StdInitiatedWithoutInterviewKey))
			return CTConstants.StdInitiatedWithoutInterviewValue;
		try {

         if(theInterviewSummary != null) {
        	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Iterator<Object> ite = theInterviewSummary.iterator();
				while (ite.hasNext()) {
					InterviewSummaryDT ixsDT = (InterviewSummaryDT) ite.next();
					if (selectedInterviewUid.equals(ixsDT.getInterviewUid().toString())) {
						if (ixsDT.getInterviewerFullName() != null && ixsDT.getInterviewUid() != null) {
							String interviewerStr = ixsDT.getInterviewerFullName();
							Date date = null;
							if (ixsDT.getInterviewDate() != null)
							{
								date = new Date(ixsDT.getInterviewDate().getTime());
							}
				        if (date != null)
				        {
				            interviewerStr = interviewerStr.concat(" - " + formatter.format(date));
				        }
				        return(interviewerStr);
					  } //not null
				    } //selected equal
				} //has next
           } //Interview Summary not null
		} catch (Exception ex) {
			logger.error("Error in getTheNamedInterviewDisplayText: " + ex.toString());
		}
		return returnText; //could have been deleted
	}
	
	/**
	 * Clone the contact record using an existing contact record for a Co-Infection.
	 * @param subjectPhcUid
	 * @param patientRevisionUid
	 * @param proxyVO
	 * @param processingDecision 
	 * @param request
	 * @return UID of new contact record or null if failed
	 */
	public static CTContactProxyVO cloneContactForCoinfection(
			CoinfectionSummaryVO coinfectionSummaryVO, 
			CTContactProxyVO proxyVO, 
			String processingDecisionCd,
			HttpServletRequest request) {
		
		logger.debug("in cloneContactForCoinfection()");
		//copy the view Contact and convert it to a create proxy
		CTContactProxyVO newContactProxyVO = convertViewContactProxyVOIntoCreate(proxyVO);
		newContactProxyVO.getcTContactVO().getcTContactDT().setProcessingDecisionCd(processingDecisionCd);
		
		//set the subject entity and phc to the coinfection
		newContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityUid(coinfectionSummaryVO.getPatientRevisionUid()); //revision for PHC
		newContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityPhcUid(coinfectionSummaryVO.getPublicHealthCaseUid()); //subject PHC
		
		//update Program Area and Jurisdiction from Coinfection 
		newContactProxyVO.getcTContactVO().getcTContactDT().setProgAreaCd(coinfectionSummaryVO.getProgAreaCd());
		newContactProxyVO.getcTContactVO().getcTContactDT().setJurisdictionCd(coinfectionSummaryVO.getJurisdictionCd());
		newContactProxyVO.getcTContactVO().getcTContactDT().setProgramJurisdictionOid(coinfectionSummaryVO.getProgramJurisdictionOid());
		
		//update the EpiLink in case it differs..
		if (coinfectionSummaryVO.getEpiLinkId() != null) {
			newContactProxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(coinfectionSummaryVO.getEpiLinkId());
			newContactProxyVO.getcTContactVO().getcTContactDT().setSubEntityEpilinkId(coinfectionSummaryVO.getEpiLinkId());
		}
		
		//clear Dispo Code and Date
		newContactProxyVO.getcTContactVO().getcTContactDT().setDispositionCd(null);
		newContactProxyVO.getcTContactVO().getcTContactDT().setDispositionDate(null);
		logger.debug("returning from cloneContactForCoinfection()");
		return newContactProxyVO;
		
	}

	
	/**
	 * The Contact proxyVO from an existing contact record is passed.
	 * Values are changed to ready the contact record for a create.
	 * @param proxyVO
	 * @return
	 */
	private static CTContactProxyVO convertViewContactProxyVOIntoCreate(
			CTContactProxyVO proxyVO) {
		CTContactProxyVO createProxyVO = new CTContactProxyVO();
		try {
			createProxyVO = (CTContactProxyVO)proxyVO.deepCopy();
		} catch (ClassNotFoundException e) {
			logger.error("Class not found for cTContactProxyVO.deepcopy?");
			return null;
		} catch (CloneNotSupportedException e) {
			logger.error("Clone Not Supported for cTContactProxyVO.deepcopy?");
			return null;
		} catch (IOException e) {
			logger.error("Exception for cTContactProxyVO.deepcopy?");
			return null;
		}
		createProxyVO.setItNew(true);
		createProxyVO.setItDelete(false);
		createProxyVO.setItDirty(false);
		if (createProxyVO.getContactPersonVO() != null) {
			createProxyVO.getContactPersonVO().setItNew(true);

		    //reateProxyVO.contactPersonVO.theEntityLocatorParticipationDTCollection
			if (createProxyVO.getContactPersonVO().getTheEntityLocatorParticipationDTCollection() != null) {
				Iterator elpIter = createProxyVO.getContactPersonVO().getTheEntityLocatorParticipationDTCollection().iterator();
				while (elpIter.hasNext()) {
					EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) elpIter.next();
					elpDT.setEntityUid(new Long(-2L));
					elpDT.setItNew(true);
					elpDT.setItDirty(true);
					elpDT.setLocatorUid(null);
					if (elpDT.getTheTeleLocatorDT() != null) {
						elpDT.getTheTeleLocatorDT().setItNew(true);
						elpDT.getTheTeleLocatorDT().setItDirty(true);
						elpDT.getTheTeleLocatorDT().setTeleLocatorUid(null);
					}
					if (elpDT.getThePostalLocatorDT() != null) {
						elpDT.getThePostalLocatorDT().setItNew(true);
						elpDT.getThePostalLocatorDT().setItDirty(true);
						elpDT.getThePostalLocatorDT().setPostalLocatorUid(null);
					}
				}
				//createProxyVO.contactPersonVO.thePersonDT
				if (createProxyVO.getContactPersonVO().getThePersonDT() != null) {
					createProxyVO.getContactPersonVO().getThePersonDT().setItNew(true);
					createProxyVO.getContactPersonVO().getThePersonDT().setPersonUid(new Long(-2L));
				}
				if (createProxyVO.getContactPersonVO().getThePersonNameDTCollection() != null) {
					Iterator nmIter = createProxyVO.getContactPersonVO().getThePersonNameDTCollection().iterator();
					while (nmIter.hasNext()) {
						PersonNameDT nmDT = (PersonNameDT) nmIter.next();
						nmDT.setItNew(true);
						nmDT.setItDirty(true);
						nmDT.setPersonUid(new Long(-2L));
					}
				}
				if (createProxyVO.getContactPersonVO().getThePersonRaceDTCollection() != null) {
					Iterator raceIter = createProxyVO.getContactPersonVO().getThePersonRaceDTCollection().iterator();
					while (raceIter.hasNext()) {
						PersonRaceDT raceDT = (PersonRaceDT) raceIter.next();
						raceDT.setItNew(true);
						raceDT.setItDirty(true);
						raceDT.setPersonUid(new Long(-2L));
					}
				}

			}//ContactPersonVO() != null


			//cTContactVO
			if (createProxyVO.getcTContactVO() != null) {
				createProxyVO.getcTContactVO().setItNew(true);


				//cTContactDT.actEntityDTCollection
				Iterator actEntityIter = createProxyVO.getcTContactVO().getActEntityDTCollection().iterator();
				while (actEntityIter.hasNext()) {
					NbsActEntityDT actEntityDT = (NbsActEntityDT) actEntityIter.next();
					actEntityDT.setActUid(new Long(-1L));
					actEntityDT.setItNew(true);
					actEntityDT.setNbsActEntityUid(null);
					if (actEntityDT.getTypeCd().equalsIgnoreCase("SubjOfContact")) {
						actEntityDT.setEntityUid(new Long(-2L));
						actEntityDT.setEntityVersionCtrlNbr(new Integer(1));
					}
				}
				//getcTContactVO().ContactPersonVO()
				if (createProxyVO.getcTContactVO().getContactPersonVO() != null) {
					createProxyVO.getcTContactVO().getContactPersonVO().setItNew(true);
					createProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setPersonUid(null);
					createProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setVersionCtrlNbr(null);
					//ThePersonNameDT()
					if (createProxyVO.getcTContactVO().getContactPersonVO().getThePersonNameDTCollection() != null) {
						Iterator nmIter = createProxyVO.getcTContactVO().getContactPersonVO().getThePersonNameDTCollection().iterator();
						while (nmIter.hasNext()) {
							PersonNameDT nmDT = (PersonNameDT) nmIter.next();
							nmDT.setItNew(true);
							nmDT.setItDirty(true);
							nmDT.setPersonUid(new Long(-2L));
						}
					}
				} //cTContactVO.contactPersonVO()
				//cTContactVO.cTContactDT
				if (createProxyVO.getcTContactVO().getcTContactDT() != null) {
					createProxyVO.getcTContactVO().getcTContactDT().setItNew(true);
					createProxyVO.getcTContactVO().getcTContactDT().setCtContactUid(new Long(-1L));
					createProxyVO.getcTContactVO().getcTContactDT().setVersionCtrlNbr(null);
				}
				//CtContactAnswerDT
				if (createProxyVO.getcTContactVO().getCtContactAnswerDTMap() != null) {
					Map<Object,Object> ansMap = createProxyVO.getcTContactVO().getCtContactAnswerDTMap();
					Iterator ansIter = ansMap.values().iterator();
					while (ansIter.hasNext()) {
						NbsAnswerDT ansDT = (NbsAnswerDT) ansIter.next();
						ansDT.setActUid(new Long(-1L));
						ansDT.setItNew(true);
						ansDT.setItDirty(false);
						ansDT.setNbsAnswerUid(null);
						//ansDT.setRecordStatusCd(null);
						//ansDT.setRecordStatusTime(null);
						//ansDT.setSeqNbr(new Integer(0));
					}
				}

			}//getcTContactVO() != null
			createProxyVO.setPersonVOCollection(null);	//null on create - this contains the Investigator in View
		}

		return createProxyVO;
	}

	/**
	 * Create the contact record on the backend
	 * @param subjectPhcUid
	 * @param patientRevisionUid
	 * @param proxyVO
	 * @param request
	 * @return UID of new contact record or null if failed
	 */
	public static Long createContactForCoinfection(CTContactProxyVO ctProxyVO, 
			HttpServletRequest request) {		
	
		logger.debug("in  createContactForCoinfection() - sending CTContactProxyVO to backend..");
		Long newCTContactUid = null;
		try {
			newCTContactUid = sendProxyToEJB(ctProxyVO, request, "cloneContact");
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("Concurrent data exception cloning contact");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Exception occurred cloning contact");
			e.printStackTrace();
		}
		logger.debug("returning from createContactForCoinfection() - uid=" + newCTContactUid);
		return newCTContactUid;
    }
	
	
	/**
	 * Create the contact record and the investigation on the backend
	 * @param subjectPhcUid
	 * @param patientRevisionUid
	 * @param proxyVO
	 * @param request
	 * @return UID of new contact record or null if failed
	 */
	public static Long createContactAndInvestigation(CTContactProxyVO ctProxyVO, PageProxyVO pgProxyVO,
			HttpServletRequest request) {	
	 Long ctContactUid = null;
	 
		 Object[] oParams = new Object[] { NEDSSConstants.CONTACT_AND_CASE, ctProxyVO, pgProxyVO};
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "setAutoContactPageProxyVO";
			ctContactUid = (Long)CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			if(ctContactUid!=null)
				NBSContext.store(request.getSession(), NBSConstantUtil.DSContactUID, ctContactUid.toString());
			return ctContactUid;
	 }
	
	
	/**
	 * 
	 * @param investigatorStr
	 * @param coinfectionSummaryVO
	 * @param ctContactClientVO
	 * @param ctProxyVO
	 * @param oldContactPageProxy existing PageProxy of the Contact
	 * @param userId
	 * @param request
	 * @return PageProxyVO to create
	 */
	public static PageProxyVO stdPopulateInvestigationFromContactForCoinfection(
		String investigatorStr, 
		CoinfectionSummaryVO coinfectionSummaryVO,
		CTContactClientVO ctContactClientVO,
		CTContactProxyVO ctProxyVO, 
		PageProxyVO oldFFPageProxy, 
		String userId,
		HttpServletRequest request) {
			
		
		Long investigatorUid = 0L;
		Integer investigatorVersion = 1;
		if (investigatorStr != null && !investigatorStr.isEmpty()) {
			String uid = splitUid(investigatorStr);
			investigatorUid = new Long(uid);
			String versionNbr = splitVerCtrlNbr(investigatorStr);
			investigatorVersion = new Integer(versionNbr);
		}
		
		Long contactUid = ctProxyVO.getContactPersonVO().thePersonDT.getPersonUid();
		Integer contactVersion = ctProxyVO.getContactPersonVO().thePersonDT.getVersionCtrlNbr();
		if (contactVersion == null)
			contactVersion = 1;
		
		PageActProxyVO proxyVO = new PageActProxyVO();
		proxyVO.setItNew(true);
		//Set PublicHealthCase Information
		
		//get the ProgramAreaVO for the condition
		CachedDropDownValues cdv = new CachedDropDownValues();
		ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" +coinfectionSummaryVO.getProgAreaCd() + "')", coinfectionSummaryVO.getConditionCd());


		try {
		//fill in the PublicHealthCase and Case Management
		proxyVO.setPublicHealthCaseVO (stdSetPublicHealthCaseForCreateForCoinfection(
												investigatorStr, 	//investigator for Field Followup
												coinfectionSummaryVO, 	//inv to create for
												ctContactClientVO, 		//from AssocToCoinf form
												ctProxyVO, 	//CtContactProxy ready for create
												oldFFPageProxy,
												userId, 	//userId of logged in user
												request));

		//fill in co-infection questions if creating investigation in co-infection group
		if(oldFFPageProxy != null)
			setCoinfectionData(proxyVO, oldFFPageProxy, programAreaVO);
		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContactForCoinfection:Exception related to populating Public Health Case " +ex.getMessage());
		}
		try {


		//set participations
		Timestamp fromTime = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime();
		proxyVO.setTheParticipationDTCollection(stdSetParticipationsForCreate(
									 contactUid, investigatorUid, fromTime, userId));

		// create new_assignment message

		
		proxyVO.setCurrentInvestigator(investigatorUid.toString());
		String programArea=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
		if(PropertyUtil.isStdOrHivProgramArea(programArea))
			MessageLogUtil.createMessageLogForParticipant(proxyVO, NEDSSConstants.PHC_INVESTIGATOR ,  null, investigatorUid);


		//set NBS Act Entities
		proxyVO.getPageVO().setActEntityDTCollection(stdSetNbsActEntityForCreate(
				contactUid, contactVersion, investigatorUid, investigatorVersion, userId));


		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContact:Exception related to Participations and Act Entities " +ex.getMessage());
		}
		//copy subject person over from Contact
		Collection<Object>  personVOCollection  = new ArrayList<Object>();
		try {
			PersonVO subjectVO = (PersonVO) ctProxyVO.getContactPersonVO().deepCopy();
			personVOCollection.add(subjectVO);
			proxyVO.setThePersonVOCollection(personVOCollection);
		} catch (Exception ex) {
			logger.warn("stdPopulateInvestigationFromContact:Exception related to Contact Person " +ex.getMessage());
		}


		return proxyVO;
	}

	
	/*
	 * Based on PageCreateHelper.setPublicHealthCaseForCreateForCoinfection(). 
	 * This creates a mini-investigation PHC from the STD contact record and a CoInfection case
	 */
	private static PublicHealthCaseVO stdSetPublicHealthCaseForCreateForCoinfection(
			String investigatorStr, 
			CoinfectionSummaryVO coinfectionSummaryVO,
			CTContactClientVO ctContactClientVO,
			CTContactProxyVO ctContactProxyVO, 
			PageProxyVO oldFFPageProxy, 
			String userId,
			HttpServletRequest req) {

		logger.debug("in stdSetPublicHealthCaseForCreateForCoinfection()");
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		try {

			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" +coinfectionSummaryVO.getProgAreaCd() + "')", coinfectionSummaryVO.getConditionCd());
			if(programAreaVO==null)
				logger.error("In stdSetPublicHealthCaseForCreateForCoinfection can not derive programAreaVO.");

			//Investigator is required on FF Create
			phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(-1));
			//Investigator is required on FF Create
			if (investigatorStr != null && !investigatorStr.isEmpty()) {
				String theUid = PageCreateHelper.splitUid(investigatorStr);
				phcVO.getThePublicHealthCaseDT().setCurrentInvestigatorUid(new Long(theUid));
			}
			Map<Object,Object> answerMap = ctContactClientVO.getAnswerMap();
			phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(CTConstants.ContactFFInvStartDate)));

			phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
			phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(userId));
			//phcVO.getThePublicHealthCaseDT().setCaseClassCd(getVal(answerMap.get(PageConstants.CASE_CLS_CD)));
			phcVO.getThePublicHealthCaseDT().setCaseTypeCd("I");
			phcVO.getThePublicHealthCaseDT().setCaseClassCd("");
			phcVO.getThePublicHealthCaseDT().setCaseStatusDirty(false);
			phcVO.getThePublicHealthCaseDT().setCd(coinfectionSummaryVO.getConditionCd());
			//bring referral basis
			phcVO.getThePublicHealthCaseDT().setReferralBasisCd(getVal(ctContactProxyVO.getcTContactVO().getcTContactDT().getContactReferralBasisCd()));
			phcVO.getThePublicHealthCaseDT().setCdDescTxt(coinfectionSummaryVO.getCondition()  );
			phcVO.getThePublicHealthCaseDT().setContactInvStatus("");
			phcVO.getThePublicHealthCaseDT().setContactInvTxt("");
			phcVO.getThePublicHealthCaseDT().setCurrProcessStateCd(CTConstants.FieldFollowup);
		phcVO.getThePublicHealthCaseDT().setDayCareIndCd("");
			phcVO.getThePublicHealthCaseDT().setDetectionMethodCd("");
			phcVO.getThePublicHealthCaseDT().setDiseaseImportedCd("");
			phcVO.getThePublicHealthCaseDT().setEffectiveDurationAmt("");
			phcVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd("");
		phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd("");
			phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
		phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd("");
			phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt("");
		phcVO.getThePublicHealthCaseDT().setImportedCountyCd("");
		phcVO.getThePublicHealthCaseDT().setImportedCountryCd("");
		phcVO.getThePublicHealthCaseDT().setImportedStateCd("");
			phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN );

			phcVO.getThePublicHealthCaseDT().setJurisdictionCd(coinfectionSummaryVO.getJurisdictionCd());
			phcVO.getThePublicHealthCaseDT().setProgAreaCd(coinfectionSummaryVO.getProgAreaCd());
			phcVO.getThePublicHealthCaseDT().setStatusCd("A");
			phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(coinfectionSummaryVO.getProgramJurisdictionOid());
			if (oldFFPageProxy != null && oldFFPageProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId() != null) {
				phcVO.getThePublicHealthCaseDT().setCoinfectionId(oldFFPageProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId());
			}
			phcVO.setCoinfectionCondition(CachedDropDowns.getConditionCoinfectionMap().containsKey(programAreaVO.getConditionCd())? true:false);
			String sharedInd = null;
			if(answerMap.get(CTConstants.SHARED_IND)!= null && answerMap.get(CTConstants.SHARED_IND).equals("1"))
				sharedInd = "T";
			else sharedInd = "F";
			phcVO.getThePublicHealthCaseDT().setSharedInd(ctContactProxyVO.getcTContactVO().getcTContactDT().getSharedIndCd());

			RulesEngineUtil ruleEngUtil = new RulesEngineUtil();
			String invStartDate = getVal(answerMap.get(CTConstants.ContactFFInvStartDate));
			if (invStartDate != null && !invStartDate.isEmpty()) {
				int[] weekAndYear = ruleEngUtil.CalcMMWR(invStartDate);
				if (weekAndYear[0] != 0)
					phcVO.getThePublicHealthCaseDT().setMmwrWeek(Integer.toString(weekAndYear[0]));
				if (weekAndYear[1] != 0)
					phcVO.getThePublicHealthCaseDT().setMmwrYear(Integer.toString(weekAndYear[1]));
			}
			// These questions are for extending PHC table for common fields - ODS changes
			if(getVal(answerMap.get(CTConstants.ContactFFInvAssignedDate))!=null )
			{
				Timestamp assignedDate = StringUtils.stringToStrutsTimestamp(getVal(answerMap.get(CTConstants.ContactFFInvAssignedDate)));
				if (assignedDate != null) {
					phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime(assignedDate);
					phcVO.getTheCaseManagementDT().setInitFollUpAssignedDate(assignedDate);
					phcVO.getTheCaseManagementDT().setFollUpAssignedDate(assignedDate);
				}
			}

			//get any case mgt questions
			phcVO.getTheCaseManagementDT().setCaseManagementDTPopulated(true);
			if (oldFFPageProxy != null && oldFFPageProxy.getPublicHealthCaseVO() != null &&
					oldFFPageProxy.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId() != null) {
				phcVO.getTheCaseManagementDT().setEpiLinkId(oldFFPageProxy.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId());
			} else if (coinfectionSummaryVO.getEpiLinkId() != null)
				phcVO.getTheCaseManagementDT().setEpiLinkId(coinfectionSummaryVO.getEpiLinkId());

			phcVO.getTheCaseManagementDT().setInitFollUp(CTConstants.FieldFollowup);
			phcVO.getTheCaseManagementDT().setSubjComplexion(getVal(ctContactProxyVO.getcTContactVO().getCtContactAnswerDTMap().get(PageConstants.COMPLEXION)));
			phcVO.getTheCaseManagementDT().setSubjHair(getVal(ctContactProxyVO.getcTContactVO().getCtContactAnswerDTMap().get(PageConstants.HAIR)));
			phcVO.getTheCaseManagementDT().setSubjHeight(getVal(ctContactProxyVO.getcTContactVO().getCtContactAnswerDTMap().get(PageConstants.HEIGHT)));
			phcVO.getTheCaseManagementDT().setSubjOthIdntfyngInfo(getVal(ctContactProxyVO.getcTContactVO().getCtContactAnswerDTMap().get(PageConstants.OTHER_IDENTIFYING_INFORMATION)));
			phcVO.getTheCaseManagementDT().setSubjSizeBuild(getVal(ctContactProxyVO.getcTContactVO().getCtContactAnswerDTMap().get(PageConstants.SIZE_BUILD)));
			phcVO.getTheCaseManagementDT().setInternetFollUp(getVal(answerMap.get(CTConstants.ContactFFInternetFollowup)));
			phcVO.getTheCaseManagementDT().setInitFollUpNotifiable(getVal(answerMap.get(CTConstants.ContactFFNotifiable)));

			phcVO.setItNew(true);
			phcVO.setItDirty(false);
		} catch (Exception ex) {
			logger.error("Exception in stdSetPublicHealthCaseForCreateForCoinfection encountered.." + ex.getMessage());
			ex.printStackTrace();
		}


		logger.debug("leaving stdSetPublicHealthCaseForCreateForCoinfection()");
		return(phcVO);
	}

	private static void setCoinfectionData(PageActProxyVO proxyVO, PageProxyVO coInfpageProxyVO,ProgramAreaVO programAreaVO){
		String investigationFormCd = "";
		try{

			investigationFormCd = programAreaVO.getInvestigationFormCd();
			PageLoadUtil pageLoadUtil = new PageLoadUtil();
			pageLoadUtil.loadQuestionKeys(investigationFormCd);
			Map<Object, Object> answerMap  = ((PageActProxyVO)coInfpageProxyVO).getPageVO().getPamAnswerDTMap();
			Map<Object, Object> repeatingAnswerMap  = ((PageActProxyVO)coInfpageProxyVO).getPageVO().getPageRepeatingAnswerDTMap();
			Map<Object, Object> coInfectionQuestionMap = pageLoadUtil.getCoinfectionQuestionKeyMap();
			proxyVO.getPageVO().setPageRepeatingAnswerDTMap(new HashMap<Object,Object>());
			proxyVO.getPageVO().setPamAnswerDTMap(new HashMap<Object,Object>());
			if(coInfectionQuestionMap!=null && coInfectionQuestionMap.size()>0){
				Iterator<Object> ite = coInfectionQuestionMap.keySet().iterator();
				while(ite.hasNext()){
					Long qId = (Long)ite.next();
					if(answerMap.containsKey(qId)){
						Object obj = answerMap.get(qId);
						if(obj instanceof NbsAnswerDT){
							((NbsAnswerDT)obj).setItNew(true);
							((NbsAnswerDT)obj).setItDelete(false);
							((NbsAnswerDT)obj).setItDirty(false);
							proxyVO.getPageVO().getPamAnswerDTMap().put(qId, obj);
						}
						else if(obj instanceof ArrayList<?>){
							ArrayList<?> aList = (ArrayList<?>)obj;
							for(Object answer : aList){
								((NbsAnswerDT)answer).setItNew(true);
								((NbsAnswerDT)answer).setItDelete(false);
								((NbsAnswerDT)answer).setItDirty(false);
							}
							proxyVO.getPageVO().getPamAnswerDTMap().put(qId, aList);
						}
					}
					if(repeatingAnswerMap.containsKey(qId)){
						Object obj = repeatingAnswerMap.get(qId);
						if(obj instanceof NbsAnswerDT){
							((NbsAnswerDT)obj).setItNew(true);
							((NbsAnswerDT)obj).setItDelete(false);
							((NbsAnswerDT)obj).setItDirty(false);
							proxyVO.getPageVO().getPageRepeatingAnswerDTMap().put(qId, obj);
						}
						else if(obj instanceof ArrayList<?>){
							ArrayList<?> aList = (ArrayList<?>)obj;
							for(Object answer : aList){
								((NbsAnswerDT)answer).setItNew(true);
								((NbsAnswerDT)answer).setItDelete(false);
								((NbsAnswerDT)answer).setItDirty(false);
							}
							proxyVO.getPageVO().getPageRepeatingAnswerDTMap().put(qId, obj);
						}
					}
				}
			}

		}catch(Exception ex){
			logger.error("Exception while filling in co-infection questions for form :" + investigationFormCd + " \n"+ex.getMessage());
		}
	}		
		
}
