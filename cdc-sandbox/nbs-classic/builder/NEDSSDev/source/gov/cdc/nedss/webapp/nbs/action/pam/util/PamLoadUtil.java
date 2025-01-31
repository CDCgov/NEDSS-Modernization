package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
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
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.FileUploadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NavigatorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.form.ruleadmin.RuleAdminForm;
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
 * Utility class to build PamClientVO from PAMProxyVO(backend VO)
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PamLoadUtil.java
 * Aug 6, 2008
 * @version
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class PamLoadUtil {

	static final LogUtils logger = new LogUtils(PamLoadUtil.class.getName());
	public static final String ACTION_PARAMETER	= "method";
	//public static CachedDropDownValues cdv = new CachedDropDownValues();
	private Map<Object,Object> questionMap;
	private Map<Object,Object> questionKeyMap;
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	@SuppressWarnings("unchecked")
	public Map<Object, Object> loadQuestions(String invFormCd) throws Exception {
		if(QuestionsCache.getQuestionMap()!=null)
			questionMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(invFormCd);
		else
			questionMap = new HashMap<Object,Object>();
		if(questionMap == null)
			throw new Exception("\n *************** Question Cache for " + invFormCd + " is empty!!! *************** \n");
		
		return questionMap;
	}

	@SuppressWarnings("unchecked")
	public void loadQuestionKeys(String invFormCd) throws NEDSSAppException {
		try {
			this.setQuestionMap((Map<Object, Object>) QuestionsCache.getQuestionMap().get(invFormCd));
			if (getQuestionMap() == null) {
				logger.debug("********#Question Map Size: " + getQuestionMap().size() + " for form: " + invFormCd);
				throw new Exception(
						"\n *************** Question Cache for " + invFormCd + " is empty!!! *************** \n");
			}
			setQuestionKeyMap(new HashMap<Object, Object>());
			Iterator<Object> iter = getQuestionMap().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) getQuestionMap().get(key);
				getQuestionKeyMap().put(metaData.getNbsQuestionUid(), key);
			}
		} catch (Exception e) {
			logger.fatal("loadQuestionKeys" + invFormCd);
			throw new NEDSSAppException("Exception thrown for loadQuestionKeys : invFormCd ", e);
		}

	}
	/**
	 * This method retrieves the Patient Revision Information on the create load instance from the backend,
	 * constructs and returns a PAMClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PamClientVO.PamClientVO
	 */
	public void createLoadUtil(PamForm form, HttpServletRequest request) throws Exception {

		try {
			form.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			form.getAttributeMap().clear();
			form.setFormFieldMap(new HashMap<Object,Object>());
			form.setErrorTabs(new String[0]);
			HttpSession session = request.getSession();
			PamClientVO clientVO = new PamClientVO();
			// loadQuestionMap
			String invFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);
			//loadQuestions(invFormCd);
			Map<Object, Object> questionMap  = this.loadQuestions(invFormCd);
			this.setQuestionMap(questionMap);//ND-16009
			form.setPamFormCd(invFormCd);
			form.getAttributeMap().put("header", "Create Investigation");
			form.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
			form.getAttributeMap().put("Submit", "Submit");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

			Long mprUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
			PersonVO personVO = findMasterPatientRecord(mprUid, session);
			
			ClientUtil.setPatientInformation(form.getActionMode(), personVO, clientVO, request, form.getPamFormCd());
			form.setPamClientVO(clientVO);
			setInvInfoForCreate(form, request.getSession());
			String currentTask = this.setContextForCreate(personVO,request,form);
			this.populateLabMorbValues(form, currentTask, request);
			this.setUpdatedValues(form, request);
			populateContactTracing(form, request);
			this.setJurisdictionForCreate(form, nbsSecurityObj, session);
			//LDFs
			setLDFs(form, null, request);
			RulesEngineUtil reUtils = new RulesEngineUtil();
			form.setFormFieldMap(reUtils.initiateForm(questionMap, form));
			//Builds a list of multiselects for the js
		//	retrieveMultiSelectQIds(form, this);//Removed because it was commited out in PageLoadUtil.createLoadUtil
			this.setCommonSecurityForCreateEditLoad(form, nbsSecurityObj, request,null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Create " +  form.getPamFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
	}
	/**
	 * viewLoadUtil method retrieves the PamProxyVO from the EJB and sets to
	 * PamClientVO, attribute of PamForm
	 *
	 * @param form
	 * @param request
	 */
	  public PamProxyVO viewLoadUtil(PamForm form, HttpServletRequest request) throws Exception {

		  PamProxyVO proxyVO = null;
		  try {
			  form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			  form.setFormFieldMap(new HashMap<Object,Object>());
			  form.setErrorTabs(new String[0]);
			  form.getStateList();
			  form.getCountryList();
			  if(request.getAttribute("mode") == null)
				  form.getAttributeMap().clear();
			  HttpSession session = request.getSession();	
			  String invFormCd = form.getPamFormCd();
			  this.setQuestionMap(this.loadQuestions(invFormCd));
			  this.loadQuestionKeys(invFormCd);
			  String contextAction = request.getParameter("ContextAction");
			  if(request.getParameter("mode")==null)
				  request.getParameterMap().clear();
			  String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
			  request.setAttribute("DSInvUid", sPublicHealthCaseUID);
			  //For Notes and Attachment-  The request value is lost when it comes to note and attachment pages
			  request.getSession().setAttribute("DSInvUid", sPublicHealthCaseUID);
			 
			  
			  proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());
			  Collection<Object>   notificationSummaryVOCollection=proxyVO.getTheNotificationSummaryVOCollection();
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
			  this.setCommonAnswersForViewEdit(form, proxyVO, request);
			  //Pam Specific Answers
			  this.setMSelectCBoxAnswersForViewEdit(form, updateMapWithQIds(proxyVO.getPamVO().getPamAnswerDTMap()));
			  //set PamProxyVO to ClientVO
			  form.getPamClientVO().setOldPamProxyVO(proxyVO);
			  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			  Map<Object, Object> map =setContextForView(form, contextAction,request, request.getSession());
			  populatePamAssocations(proxyVO, sPublicHealthCaseUID,map, request,form);
			  setCommonSecurityForView(form, proxyVO, nbsSecurityObj,request);
			  //LDFs
			  setLDFs(form, proxyVO.getPamVO().getPamAnswerDTMap(), request);
			  
			  // set the notification status details in request
			  ArrayList<Object> nsColl = (ArrayList<Object> ) proxyVO.getTheNotificationSummaryVOCollection();
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
			  //condition link from Associate Lab or Morb to one or more Investigations
			  //no buttons on this one...
			  String sCurrentTask = NBSContext.getCurrentTask(session);
			  if ( sCurrentTask.equalsIgnoreCase("ViewInvestigation7") || sCurrentTask.equalsIgnoreCase("ViewInvestigation8") ||
					  sCurrentTask.equalsIgnoreCase("ViewInvestigation9")  ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation10") ||
					  sCurrentTask.equalsIgnoreCase("ViewInvestigation11") ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation12") ) 
			  {
				  form.getSecurityMap().put("editInv", "false");
				  form.getSecurityMap().put("checkManageEvents", "false");
				  form.getSecurityMap().put("deleteInvestigation", "false");
				  form.getSecurityMap().put("checkTransfer", "false");
				  request.setAttribute("checkToAddContactTracing", "false");
				  request.setAttribute("manageAssoPerm", "false");
			  }

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading View " +  form.getPamFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		form.getAttributeMap().put("investigatorName",getInvestigatorName(proxyVO));
		return proxyVO;
	  }
	  
	  private static String getInvestigatorName(PamProxyVO proxyVO) {
		  PersonVO investigatorPersonVO = getPersonVO(NEDSSConstants.PHC_INVESTIGATOR, proxyVO);
		  StringBuffer stBuff = new StringBuffer("");
		  if (investigatorPersonVO != null && investigatorPersonVO.getThePersonNameDTCollection() != null) {
			  Iterator personNameIt = investigatorPersonVO.getThePersonNameDTCollection().iterator();
			  while (personNameIt.hasNext()) {
				  PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				  if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
					  stBuff.append((personNameDT.getFirstNm() == null) ? "" : (personNameDT.getFirstNm() + " "));
					  stBuff.append((personNameDT.getLastNm() == null) ? "" : (personNameDT.getLastNm()));
				  }
			  }
		  }		  
		  return stBuff.toString();
	  }

	/**
	 * editLoadUtil method retrieves the PamProxyVO from the EJB and sets to PamClientVO, attribute of PamForm
	 * @param form
	 * @param request
	 */
	  public PamProxyVO editLoadUtil(PamForm form, HttpServletRequest request) throws Exception {

		  PamProxyVO proxyVO = null;

		  try {
			form.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			  form.getAttributeMap().put("ReadOnlyJursdiction", "ReadOnlyJursdiction");
			  form.setFormFieldMap(new HashMap<Object,Object>());
			  form.setErrorTabs(new String[0]);
			  HttpSession session = request.getSession();
			  String invFormCd = form.getPamFormCd();
			  this.loadQuestions(invFormCd);
			  this.loadQuestionKeys(invFormCd);
			  form.setPamFormCd(invFormCd);
			  form.getAttributeMap().put("header", "Edit Investigation");
			  form.getAttributeMap().put(ACTION_PARAMETER, "editSubmit");
			  String sPublicHealthCaseUID = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
			  proxyVO = getProxyObject(sPublicHealthCaseUID, request.getSession());
			  String noReqNotifCheck =(String)form.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK);
			  if(!(noReqNotifCheck != null && noReqNotifCheck.equals("false")))
				  setNNDIndicator(proxyVO,form);
			   //DEM, INV specific common answers back to pamclientvo to support UI / Rules
			  setCommonAnswersForViewEdit(form, proxyVO, request);
			  //Pam Specific Answers
			  this.setMSelectCBoxAnswersForViewEdit(form, updateMapWithQIds(proxyVO.getPamVO().getPamAnswerDTMap()));
			  //set PamProxyVO to ClientVO
			  form.getPamClientVO().setOldPamProxyVO(proxyVO);
			  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			  //Supplemental Info
			  Map<Object, Object> map = new HashMap<Object, Object>();
			  populatePamAssocations(proxyVO, sPublicHealthCaseUID, map, request, form);
			  String sCurrentTask = setContextForEdit(form, request, request.getSession());
			  form.getAttributeMap().put("sCurrentTask", sCurrentTask);
			  this.setUpdatedValues(form, request);
			  setJurisdictionForEdit(form, nbsSecurityObj, proxyVO);
			  //LDFs
			  setLDFs(form, proxyVO.getPamVO().getPamAnswerDTMap(), request);
			  
			  // set the notification status details in request
			  ArrayList<Object> nsColl = (ArrayList<Object> ) proxyVO.getTheNotificationSummaryVOCollection();
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
			  this.setCommonSecurityForCreateEditLoad(form, nbsSecurityObj, request,proxyVO);

		  } catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading Edit " +  form.getPamFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		  }
		  return proxyVO;
	  }

	  /**
	   * Fires the rules on View Page. Called by appropriate PAMs
	   * @param form
	 * @throws NEDSSAppException 
	   */
  
	  public void fireRulesOnViewLoad(PamForm form, PamLoadUtil pamLoadUtil) throws NEDSSAppException {

		  RulesEngineUtil reUtils = new RulesEngineUtil();
		  form.setFormFieldMap(reUtils.initiateForm(pamLoadUtil.getQuestionMap(), form));
	  }

	  /**
	   * Fires the rules on Edit Page. Called by appropriate PAMs
	   * @param form
	 * @throws NEDSSAppException 
	   */
	  public void fireRulesOnEditLoad(PamForm form, HttpServletRequest req) throws NEDSSAppException{

		  try {
			if(form.getAttributeMap().containsKey(PamConstants.REQ_FOR_NOTIF) && (form.getAttributeMap().containsKey(PamConstants.NO_REQ_FOR_NOTIF_CHECK)&&form.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK).equals("false"))){
				   //Also retrieve the NotifReqMap from session and put it in attributeMap
				   Map<Object,Object> notifReqMap = req.getSession().getAttribute("NotifReqMap") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) req.getSession().getAttribute("NotifReqMap");
				   form.getAttributeMap().put("NotifReqMap", notifReqMap);
				   req.getSession().removeAttribute("NotifReqMap");
			  }

		  RulesEngineUtil reUtils = new RulesEngineUtil();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(this.getQuestionMap(), form);

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
		} catch (NEDSSAppException e) {
			logger.fatal("PamLoadUtil.handleFormRules Exception in " , e);
			logger.fatal("PamLoadUtil.handleFormRules " , e.getMessage());
			throw new NEDSSAppException(e.getMessage(), e);
		}
	  }
	  
	  
	  public void fireRulesOnEditLoad(PamForm form, HttpServletRequest req, PamLoadUtil pamLoadUtil) throws NEDSSAppException{

		  if(form.getAttributeMap().containsKey(PamConstants.REQ_FOR_NOTIF) && (form.getAttributeMap().containsKey(PamConstants.NO_REQ_FOR_NOTIF_CHECK)&&form.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK).equals("false"))){
			   //Also retrieve the NotifReqMap from session and put it in attributeMap
			   Map<Object,Object> notifReqMap = req.getSession().getAttribute("NotifReqMap") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) req.getSession().getAttribute("NotifReqMap");
			   form.getAttributeMap().put("NotifReqMap", notifReqMap);
			   req.getSession().removeAttribute("NotifReqMap");
		  }

		  RulesEngineUtil reUtils = new RulesEngineUtil();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(pamLoadUtil.getQuestionMap(), form);

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
	 * @throws NEDSSAppException 
	   */
	  public void fireRulesOnCreateLoad(PamForm form, HttpServletRequest req) throws NEDSSAppException{

		  RulesEngineUtil reUtils = new RulesEngineUtil();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(this.getQuestionMap(), form);

		  form.setFormFieldMap(formFieldMap);
	  }

	  public void fireRulesOnCreateLoad(PamForm form, HttpServletRequest req, PamLoadUtil pamLoadUtil) throws NEDSSAppException{

		  RulesEngineUtil reUtils = new RulesEngineUtil();
		  Map<Object,Object> formFieldMap = reUtils.initiateForm(pamLoadUtil.getQuestionMap(), form);

		  form.setFormFieldMap(formFieldMap);
	  }
	  

	  /**
	   * setCommonAnswersForViewEdit loads all the DEMs, INVs common across all PAMs
	   * collects them from multiple tables part of PamProxyVO and stuffs in AnswerMap to support UI / Rules
	   * @param form
	   * @param proxyVO
	   * @param request
	   */
	  public void setCommonAnswersForViewEdit(PamForm form, PamProxyVO proxyVO, HttpServletRequest request) {
		  PamClientVO clientVO = new PamClientVO();
		  PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);
		  ClientUtil.setPatientInformation(form.getActionMode(), personVO, clientVO, request,form.getPamFormCd());
			form.setPamClientVO(clientVO);
			//loadCommonInvestigationAnswers
		  setInvestigationInformationOnForm(form, proxyVO);
	  }

	  public void setInvestigationInformationOnForm(PamForm form, PamProxyVO proxyVO) {

		  PublicHealthCaseDT dt = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
		  PamClientVO clientVO = form.getPamClientVO();
		  clientVO.setAnswer(PamConstants.JURISDICTION, dt.getJurisdictionCd());
		  clientVO.setAnswer(PamConstants.PROGRAM_AREA, dt.getProgAreaCd());
		  clientVO.setAnswer(PamConstants.INV_STATUS_CD, dt.getInvestigationStatusCd());
		  CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
		  if(dt.getInvestigationStatusCd()!= null)
			  form.getAttributeMap().put("investigationStatus", cachedDropDownValues.getCodeShortDescTxt(dt.getInvestigationStatusCd(), "PHC_IN_STS"));
		  if(dt.getRptFormCmpltTime_s() != null)
			  clientVO.setAnswer(PamConstants.DATE_REPORTED, dt.getRptFormCmpltTime_s());
		  clientVO.setAnswer(PamConstants.INV_START_DATE, dt.getActivityFromTime_s());
		  clientVO.setAnswer(PamConstants.MMWR_WEEK, dt.getMmwrWeek());
		  clientVO.setAnswer(PamConstants.MMWR_YEAR, dt.getMmwrYear());
		  clientVO.setAnswer(PamConstants.CASE_LOCAL_ID, dt.getLocalId());
		  clientVO.setAnswer(PamConstants.CASE_ADD_TIME, StringUtils.formatDate(dt.getAddTime()));
		  clientVO.setAnswer(PamConstants.CASE_ADD_USERID, StringUtils.replaceNull(dt.getAddUserId()));
		 // clientVO.setAnswer(PamConstants.CASE_LC_USERTIME, StringUtils.formatDate(dt.getLastChgTime()));
		//  clientVO.setAnswer(PamConstants.CASE_LC_USERID, StringUtils.replaceNull(dt.getLastChgUserId()));
		  String sharedInd = null;
		  if(dt.getSharedInd()!= null && dt.getSharedInd().equals("T"))
			  sharedInd="1";
		  clientVO.setAnswer(PamConstants.SHARED_IND, sharedInd);

		  clientVO.setAnswer(PamConstants.CONDITION_CD, dt.getCd());
		  clientVO.setAnswer(PamConstants.RECORD_STATUS_CD, dt.getRecordStatusCd());
		  clientVO.setAnswer(PamConstants.RECORD_STATUS_TIME, StringUtils.formatDate(dt.getRecordStatusTime()));
		  clientVO.setAnswer(PamConstants.STATUS_CD, dt.getStatusCd());
		  clientVO.setAnswer(PamConstants.PROGRAM_JURISDICTION_OID, StringUtils.replaceNull(dt.getProgramJurisdictionOid()));
		  clientVO.setAnswer(PamConstants.VERSION_CTRL_NBR, StringUtils.replaceNull(dt.getVersionCtrlNbr()));
		  clientVO.setAnswer(PamConstants.CASE_CLS_CD, dt.getCaseClassCd());
		  clientVO.setAnswer(PamConstants.TUB_GEN_COMMENTS, dt.getTxt());
		  // These questions are specific to Varicella
		  if(dt.getRptToCountyTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DATE_REPORTED_TO_COUNTY,dt.getRptToCountyTime_s());
		  }
		  if(dt.getRptToStateTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DATE_REPORTED_TO_STATE,dt.getRptToStateTime_s());
		  }
		  if(dt.getDiagnosisTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DIAGNOSIS_DATE,dt.getDiagnosisTime_s());
		  }
		  if(dt.getEffectiveFromTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.ILLNESS_ONSET_DATE,dt.getEffectiveFromTime_s());
		  }
		  if(dt.getPatAgeAtOnset()!= null)
		  {
			  clientVO.setAnswer(PamConstants.PAT_AGE_AT_ONSET,dt.getPatAgeAtOnset());
		  }
		  if(dt.getPatAgeAtOnsetUnitCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.PAT_AGE_AT_ONSET_UNIT_CODE,dt.getPatAgeAtOnsetUnitCd());
		  }

		 // These questions are for extending PHC table for common fields - ODS changes
		  if(dt.getInvestigatorAssignedTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DATE_ASSIGNED_TO_INVESTIGATION,dt.getInvestigatorAssignedTime_s());
		  }
		  if(dt.getHospitalizedIndCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.WAS_THE_PATIENT_HOSPITALIZED,dt.getHospitalizedIndCd());
		  }
		  if(dt.getHospitalizedAdminTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.ADMISSION_DATE,dt.getHospitalizedAdminTime_s());
		  }
		  if(dt.getHospitalizedDischargeTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DISCHARGE_DATE,dt.getHospitalizedDischargeTime_s());
		  }
		  if(dt.getHospitalizedDurationAmt()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DURATION_OF_STAY,dt.getHospitalizedDurationAmt().toString());
		  }
		  if(dt.getPregnantIndCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.PREGNANCY_STATUS,dt.getPregnantIndCd());
		  }
		  if(dt.getOutcomeCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.DID_THE_PATIENT_DIE,dt.getOutcomeCd());
		  }
		  if(dt.getDayCareIndCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY,dt.getDayCareIndCd());
		  }
		  if(dt.getFoodHandlerIndCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IS_THIS_PERSON_FOOD_HANDLER,dt.getFoodHandlerIndCd());
		  }
		  if(dt.getImportedCountryCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IMPORTED_COUNTRY,dt.getImportedCountryCd());
		  }
		  if(dt.getImportedStateCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IMPORTED_STATE,dt.getImportedStateCd());
		  }
		  if(dt.getImportedCityDescTxt()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IMPORTED_CITY,dt.getImportedCityDescTxt());
		  }
		  if(dt.getImportedCountyCd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.IMPORTED_COUNTY,dt.getImportedCountyCd());
		  }
		  if(dt.getDeceasedTime_s()!= null)
		  {
			  clientVO.setAnswer(PamConstants.INVESTIGATION_DEATH_DATE,dt.getDeceasedTime_s());
		  }
		  if(dt.getOutbreakInd()!= null)
		  {
			  clientVO.setAnswer(PamConstants.OUTBREAK_INDICATOR,dt.getOutbreakInd());
		  }
		  if(dt.getOutbreakName()!= null)
		  {
			  clientVO.setAnswer(PamConstants.OUTBREAK_NAME,dt.getOutbreakName());
		  }
		  if(dt.getRptSourceCd() != null) clientVO.setAnswer(PamConstants.REPORTING_SOURCE, dt.getRptSourceCd());
		  if(dt.getEffectiveToTime() != null) clientVO.setAnswer(PamConstants.ILLNESS_END_DATE, StringUtils.formatDate(dt.getEffectiveToTime()));
		  if(dt.getEffectiveDurationAmt() != null) clientVO.setAnswer(PamConstants.ILLNESS_DURATION , dt.getEffectiveDurationAmt());
		  if(dt.getEffectiveDurationUnitCd() != null) clientVO.setAnswer(PamConstants.ILLNESS_DURATION_UNITS, dt.getEffectiveDurationUnitCd());
		  if(dt.getDiseaseImportedCd() != null) clientVO.setAnswer(PamConstants.DISEASE_IMPORT_CD, dt.getDiseaseImportedCd());
		  if(dt.getTransmissionModeCd() != null) clientVO.setAnswer(PamConstants.TRANSMISN_MODE_CD, dt.getTransmissionModeCd());
		  if(dt.getDetectionMethodCd() != null) clientVO.setAnswer(PamConstants.DETECTION_METHOD_CD, dt.getDetectionMethodCd());
		  //Added for Contact Tracing
		  if(dt.getPriorityCd() != null) clientVO.setAnswer(PamConstants.CONTACT_PRIORITY, dt.getPriorityCd());
		  if(dt.getInfectiousFromDate() != null) clientVO.setAnswer(PamConstants.INFECTIOUS_PERIOD_FROM,  StringUtils.formatDate(dt.getInfectiousFromDate()));
		  if(dt.getInfectiousToDate() != null) clientVO.setAnswer(PamConstants.INFECTIOUS_PERIOD_TO, StringUtils.formatDate(dt.getInfectiousToDate()));
		  if(dt.getContactInvStatus() != null) clientVO.setAnswer(PamConstants.CONTACT_STATUS, dt.getContactInvStatus());
		  if(dt.getContactInvTxt() != null) clientVO.setAnswer(PamConstants.CONTACT_COMMENTS, dt.getContactInvTxt());
		 //Confirmation Methods
		  _confirmationMethodsForViewEdit(form, proxyVO);		  

		  Collection<Object>  coll = proxyVO.getPublicHealthCaseVO().getTheActIdDTCollection();
		  if(coll != null && coll.size() > 0) {
			 Iterator<Object>  iter = coll.iterator();
			  while(iter.hasNext()) {
				  ActIdDT adt = (ActIdDT) iter.next();
				  String typeCd = adt.getTypeCd() == null ? "" : adt.getTypeCd();
				  String value = adt.getRootExtensionTxt() == null ? "" : adt.getRootExtensionTxt();
				  if(typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD) && !value.equals("")) {
					  clientVO.setAnswer(PamConstants.STATE_CASE, value);
				  } else if(typeCd.equalsIgnoreCase("CITY") && !value.equals("")) {
					  clientVO.setAnswer(PamConstants.COUNTY_CASE, value);
				  }
			  }
		  }
	  }

	  public Map<Object,Object> updateMapWithQIds(Map<Object,Object> answerMap) {
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  if(answerMap != null && answerMap.size() > 0) {
			 Iterator<Object>  iter = answerMap.keySet().iterator();
			  while(iter.hasNext()) {
				  Long key = (Long) iter.next();
				  returnMap.put(this.getQuestionKeyMap().get(key), answerMap.get(key));
			  }
		  }

		  return returnMap;
	  }
	  
	  public static Map<Object,Object> updateCreateMapWithQIds(Map<Object,Object> answerMap) {
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  if(answerMap != null && answerMap.size() > 0) {
			 Iterator<Object>  iter = answerMap.keySet().iterator();
			  while(iter.hasNext()) {
				  String key = (String) iter.next();
				  returnMap.put(key, answerMap.get(key));
			  }
		  }

		  return returnMap;
	  }
	  public static void setPamSpecificAnswersForViewEdit(PamForm form, Map<Object,Object> answerMap, Map<Object,Object> returnMap) {

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
			  form.getPamClientVO().getAnswerMap().putAll(returnMap);
			  //form.getPamClientVO().setPamAnswerMap(returnMap);
			  setRaceAnswersToClientVO(form.getPamClientVO(), answerMap);
		  }
	  }

	  private static void setRaceAnswersToClientVO(PamClientVO clientVO, Map<Object,Object> answerMap) {

		  Object obj = answerMap.get(PamConstants.RACE);
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

	private static void setInvInfoForCreate(PamForm form, HttpSession session) {

		Map<Object,Object> answerMap = new HashMap<Object,Object>();

		String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
		String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
		answerMap.put(PamConstants.INV_STATUS_CD, NEDSSConstants.STATUS_OPEN);

		answerMap.put(PamConstants.PROGRAM_AREA, programAreaCd);
		answerMap.put(PamConstants.CONDITION_CD, conditionCd);
		answerMap.put(PamConstants.SHARED_IND, "1");
		answerMap.put(PamConstants.CASE_LOCAL_ID, "");
		form.getPamClientVO().getAnswerMap().putAll(answerMap);
	}


	/**
	 * This method retrieves the complete PAM Information from the backend, constructs and returns PAMClientVO
	 * @param form
	 * @param request
	 * @return gov.cdc.nedss.webapp.nbs.action.pam.vo.PamClientVO.PamClientVO
	 */
	public static PamClientVO retrieveEditViewLoadVO(PamForm form, HttpServletRequest request) {

		return new PamClientVO();
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

	public static PamProxyVO getProxyObject(String sPublicHealthCaseUID, HttpSession session) {

		PamProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {
			Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getPamProxy";
			Object[] oParams = new Object[] { publicHealthCaseUID };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			proxy = (PamProxyVO) arr.get(0);

			} catch (Exception ex) {
			logger.fatal("getProxy: ", ex);
		}

		return proxy;
	}
	public static Map<Object, Object> setContextForView(PamForm form, String contextAction,
			HttpServletRequest request, HttpSession session) {
		//clear Links if any stored
		form.getAttributeMap().remove("linkName");form.getAttributeMap().remove("linkValue");form.getAttributeMap().remove("linkName1");form.getAttributeMap().remove("linkValue1");
		Map<Object, Object> map =new HashMap<Object, Object> ();
		
		  PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPamClientVO().getOldPamProxyVO());

		  Collection<Object> DSContactColl= new ArrayList<Object>();
		  if(form.getPamClientVO().getOldPamProxyVO().getTheCTContactSummaryDTCollection()!=null){
			  DSContactColl= form.getPamClientVO().getOldPamProxyVO().getTheCTContactSummaryDTCollection();
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
		    String nmSuffix = "";
			/*if(nms != null)
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
		     }*/
		    if(nms != null)
		     {
		      Iterator<Object>  itname = nms.iterator();
		      Timestamp mostRecentNameAOD = null;
		       while (itname.hasNext()) {
		         PersonNameDT name = (PersonNameDT) itname.next();

		         /*if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
		        	 if(nameDT.getFirstNm() != null)
		        	 strFName = nameDT.getFirstNm();
		        	 if(nameDT.getMiddleNm() != null)
		        		 strMName =  nameDT.getMiddleNm();
		        	  if(nameDT.getLastNm() != null)
		        		  strLName =   nameDT.getLastNm();
		        	  if(nameDT.getNmSuffix() != null)
		        		  nmSuffix =   nameDT.getNmSuffix();

		         }*/
		         // for personInfo
	             if (name != null && name.getNmUseCd() != null &&
	                 name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
	                 name.getStatusCd() != null &&
	                 name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
	                 name.getRecordStatusCd() != null &&
	                 name.getRecordStatusCd().
	                 equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	               if (mostRecentNameAOD == null ||
	                   (name.getAsOfDate() != null &&
	                   !name.getAsOfDate().before(mostRecentNameAOD))) {
	            	      strFName="";
	           	          strMName="";
	           	          strLName="";
	           	          nmSuffix ="";
	                 mostRecentNameAOD = name.getAsOfDate();                 
	                 if(name.getFirstNm() != null)
	    	        	 strFName = name.getFirstNm();
	    	        	 if(name.getMiddleNm() != null)
	    	        		 strMName =  name.getMiddleNm();
	    	        	  if(name.getLastNm() != null)
	    	        		  strLName =   name.getLastNm();
	    	        	  if(name.getNmSuffix() != null)
	    	        		  nmSuffix =   name.getNmSuffix();
	  			 
	  		
	               }
	             }
		         
		       }
		     }
			strPName = strFName +" "+strMName+ " "+strLName;
			if(null == strPName || strPName.equalsIgnoreCase("null")){
				strPName ="";
			}

			form.getAttributeMap().put("patientLocalName", strPName);
			request.setAttribute("patientLocalName", strPName);
			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
			request.setAttribute("patientSuffixName", cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));


			if(personVO.getThePersonDT().getBirthTime() != null){
			//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
			java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
			DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
			}
			form.getAttributeMap().put("patientDOB", DOB);
			request.setAttribute("patientDOB", DOB);

			if(personVO.getThePersonDT().getCurrSexCd() != null)
			 CurrSex = personVO.getThePersonDT().getCurrSexCd();
			if(CurrSex.equalsIgnoreCase("F"))
				CurrSex = "Female";
			if(CurrSex.equalsIgnoreCase("M"))
				CurrSex = "Male";
			if(CurrSex.equalsIgnoreCase("U"))
				CurrSex = "Unknown";
			form.getAttributeMap().put("patientCurrSex", CurrSex);
			request.setAttribute("patientCurrSex", CurrSex);
			//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));
		form.getAttributeMap().put("caseLocalId", form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
		PublicHealthCaseDT phcDT = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		request.setAttribute("caseLocalId", phcDT.getLocalId());
		request.setAttribute("caseUid", phcDT.getPublicHealthCaseUid());
		CachedDropDownValues cdv = new CachedDropDownValues();
		String caseStatus = cdv.getDescForCode("PHC_CLASS" ,form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd());
		caseStatus = (caseStatus == null || caseStatus.trim().length()==0 ? " " : caseStatus);
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


		form.getAttributeMap().put("TransferOwnership", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("TransferOwnership") + "&invFormCd=" + form.getPamFormCd());
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
					+ ".do?ContextAction=" + tm.get("ReturnToFileEvents") + "&invFormCd=" + form.getPamFormCd()
					+ "\">" + "Return To File: Events" + "</A>");
			form.getAttributeMap().put("deleteButtonHref"," /nbs/PamAction.do?method=deleteSubmit&ContextAction=" + tm.get("ReturnToFileEvents"));
			form.getAttributeMap().put("linkValue1", "");

		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation2")) {
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("FileSummary") + "&invFormCd=" + form.getPamFormCd() + "\">"
					+ "View File" + "</A>");

			
			
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
								+ tm.get("investigation") + "&custom=" +custom+"&queueName="+queueName+"&reportType="+reportType+"&pageNumber="+pageNumberCustom+ "\">" + "Return to Search Results"
								+ "</A>");
   		}
   		
   		
   		
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
		} else if( sCurrentTask.equalsIgnoreCase("ViewInvestigation13")){
			/*
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("FileSummary") + "&invFormCd=" + form.getPamFormCd() + "\">"
					+ "View File" + "</A>");*/

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
					if(contextAction.equalsIgnoreCase("ViewInv")){
						form.getAttributeMap().put("linkValue1", "<A href=\"/nbs/"
								+ sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToObservationNeedingReview")
								+ "\">" + "Return to Documents Requiring Review"
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
			
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation1")) {
			form.getAttributeMap().put("linkValue1", "");
			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToFileSummary") + "&invFormCd=" + form.getPamFormCd()
					+ "\">" + "Return to File: Summary" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction=" + tm.get("ReturnToFileSummary"));
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation4")) {
			form.getAttributeMap().put("linkName",
					"Return to Approval Queue for Initial Notifications");

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToReviewNotifications") + "&invFormCd=" + form.getPamFormCd()
					+ "\">" + "Return to Approval Queue for Initial Notifications" + "</A>");

			form.getAttributeMap().put("deleteButtonHref", "/nbs/PamAction.do?method=deleteSubmit&ContextAction="
					+ tm.get("ReturnToReviewNotifications") );
		} else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation5")) {
			form.getAttributeMap().put("linkName",
					"Return to Updated Notifications Queue");

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToReviewUpdatedNotifications") + "&invFormCd=" + form.getPamFormCd()
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
		} else if ( sCurrentTask.equalsIgnoreCase("ViewInvestigation7") || sCurrentTask.equalsIgnoreCase("ViewInvestigation8") ||
				  sCurrentTask.equalsIgnoreCase("ViewInvestigation9")  ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation10") ||
				  sCurrentTask.equalsIgnoreCase("ViewInvestigation11") ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation12") ) {
			//associate Lab or Morb to Investigations
			form.getAttributeMap().put("linkName",
					NEDSSConstants.RETURN_TO_ASSOCIATE_TO_INVESTIGATIONS);

			form.getAttributeMap().put("linkValue", "<A href=\"/nbs/" + sCurrentTask
					+ ".do?ContextAction=" + tm.get("ReturnToAssociateToInvestigations")
					+ "\">" + "Return to Associate to Investigations" + "</A>");
		}

		form.getAttributeMap().put("ManageVaccinationDisplay", tm.get("ManageVaccinations") == null ? "NOT_DISPLAYED" : tm.get("ManageVaccinations"));

		form.getAttributeMap().put("Edit", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("Edit") + "&invFormCd=" + form.getPamFormCd());

		form.getAttributeMap().put("ManageEvents", "/nbs/" + sCurrentTask
				+ ".do?ContextAction=" + tm.get("ManageEvents") + "&invFormCd=" + form.getPamFormCd());
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
	 * @throws Exception 
	 */
	public void populatePamAssocations(PamProxyVO proxy, String sPublicHealthCaseUID, Map<Object, Object> map, HttpServletRequest request, PamForm form) throws Exception{
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
				obsMorbSummary = (ArrayList<Object> )proxy.getTheMorbReportSummaryVOCollection();
				if (obsMorbSummary != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = obsMorbSummary.iterator();
					while (ite.hasNext()) {
						MorbReportSummaryVO mrsVO = (MorbReportSummaryVO) ite.next();
		            	StringBuffer desc = new StringBuffer(mrsVO.getConditionDescTxt() == null ? "" : 
	            			"<b>"+mrsVO.getConditionDescTxt()+"</b>");
		            	boolean flag = true;
						 if(mrsVO.getTheLabReportSummaryVOColl()!=null && mrsVO.getTheLabReportSummaryVOColl().size()>0){
							Iterator<Object>  labIte = mrsVO.getTheLabReportSummaryVOColl().iterator();
							 while(labIte.hasNext()){
								 flag = false;
								 LabReportSummaryVO labSummaryVO = (LabReportSummaryVO)labIte.next();
	 							 desc.append(DecoratorUtil.getResultedTestsStringForWorup(labSummaryVO.getTheResultedTestSummaryVOCollection()));

								 //labSummaryVO.setLabFromMorb(true);
								 //UI should show the local id of the morb report and the link should take user to morb report
								 // though the record shows up in the lab list
								 //labSummaryVO.setObservationUid(mrsVO.getObservationUid());
								 //labSummaryVO.setLocalId(mrsVO.getLocalId());
							 }
							 //obsLabSummary.addAll(mrsVO.getTheLabReportSummaryVOColl());
						 }
						 if(mrsVO.getTheTreatmentSummaryVOColl()!=null && mrsVO.getTheTreatmentSummaryVOColl().size()>0){
			 					if (mrsVO.getTheTreatmentSummaryVOColl() == null || mrsVO.getTheTreatmentSummaryVOColl().size() == 0) {
			 						logger.debug("Observation summary collection arraylist is null");
			 						}
			 						else {
			 		 		        	 NedssUtils nUtil = new NedssUtils();
			 		 		        	 nUtil.sortObjectByColumn("getCustomTreatmentNameCode", mrsVO.getTheTreatmentSummaryVOColl(), true);
			 							if(flag)
			 								desc.append("<br>");
			 							desc.append("<b>Treatment Info:</b><UL>");
			 							Iterator<Object>  treatmentIterator = mrsVO.getTheTreatmentSummaryVOColl().iterator();
			 						    while (treatmentIterator.hasNext()) 
			 							{
			 								logger.debug("Inside iterator.hasNext()...");		    	
			 								TreatmentSummaryVO treatment = (TreatmentSummaryVO)treatmentIterator.next();
			 								desc.append(treatment.getCustomTreatmentNameCode() == null ? "" :
			 					            	"<LI><b>"+treatment.getCustomTreatmentNameCode()+"</b></LI>");
			 							}//While
			 						   desc.append("<UL>");
			 						}
						 }
						mrsVO.setConditionDescTxt(desc.toString());
//						if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask)){
//							mrsVO.setActionLink(dateLink(mrsVO.getDateReceived()));
//						}
//						else{
						parameterMap.put("ContextAction", "ObservationMorbID");
						parameterMap.put("observationUID", (mrsVO.getObservationUid()).toString());
						parameterMap.put("method", "viewSubmit");
						mrsVO.setActionLink(getHiperlink(dateLink(mrsVO
								.getDateReceived()), sCurrentTask, parameterMap, form.getActionMode()));
						//}
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("observationSummaryMorbList",obsMorbSummary);
				form.getAttributeMap().put("CurrentTask",sCurrentTask);

				// ER16652 Start
				if(obsLabSummary!=null && obsLabSummary.size()>0) {
					obsLabSummary.addAll(proxy.getTheLabReportSummaryVOCollection() == null ? new ArrayList<Object>() : proxy.getTheLabReportSummaryVOCollection());
				}else{
					obsLabSummary = (ArrayList<Object> ) proxy.getTheLabReportSummaryVOCollection();
				}
				// ER16652 End
				PageLoadUtil.setLabReportsDisplayList(obsLabSummary, request, sCurrentTask, form.getActionMode());
				/*if (obsLabSummary != null) {
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
				*/


				//Treatment

				// ER16652 Start
				if(treatments!=null && treatments.size()>0){
					treatments.addAll(proxy.getTheTreatmentSummaryVOCollection() == null ? new ArrayList<Object>() : proxy.getTheTreatmentSummaryVOCollection());
				}else{
					treatments = (ArrayList<Object> ) proxy.getTheTreatmentSummaryVOCollection();
				}
				// ER16652 End

				if(treatments != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = treatments.iterator();
					while (ite.hasNext()) {
						TreatmentSummaryVO tsVO = (TreatmentSummaryVO) ite.next();
						//tsVO.getLocalId()
//						if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask)){
//							tsVO.setActionLink(dateLink(tsVO.getActivityFromTime()));
//						}else{
						parameterMap.put("ContextAction", "TreatmentID");
						parameterMap.put("treatmentUID", tsVO.getTreatmentUid().toString());
						parameterMap.put("method", "viewSubmit");
						parameterMap.put("invFormCd", form.getPamFormCd());
						tsVO.setActionLink(getHiperlink(dateLink(tsVO.getActivityFromTime()), sCurrentTask, parameterMap, form.getActionMode()));
						//}
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("treatmentList",treatments);

				// notifications
				DecoratorUtil util = new DecoratorUtil();
				notifSummary = (ArrayList<Object> ) proxy.getTheNotificationSummaryVOCollection();
				String notifDisplayHTML = util.buildNotificationList(notifSummary);
				request.setAttribute("notificationListTable",notifDisplayHTML);

				// investigation history/audit log summary
				String invHistoryHTML = util.buildInvHistoryList(proxy.getTheInvestigationAuditLogSummaryVOCollection());
				request.setAttribute("invHistoryTable",invHistoryHTML);

				//Vaccination
				vaccination = (ArrayList<Object> ) proxy.getTheVaccinationSummaryVOCollection();
				if(vaccination != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = vaccination.iterator();
					while (ite.hasNext()) {
						VaccinationSummaryVO vacVO = (VaccinationSummaryVO) ite.next();
//						
						PageLoadUtil.createVaccinationLinkForSupplementalInfo(vacVO, form.getActionMode());
					}
				}
				request.setAttribute("vaccinationList",vaccination);
				//Document
				ArrayList<Object> document = (ArrayList<Object> ) proxy.getTheDocumentSummaryVOCollection();
				if(document != null) {
					HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
					Iterator<Object> ite = document.iterator();
					while (ite.hasNext()) {
						SummaryDT doc = (SummaryDT) ite.next();
//						if(sCurrentTask==null || InvestigationUtil.isViewInvFromQueueContext(sCurrentTask)){
//							doc.setActionLink(dateLink(doc.getAddTime()));
//						}
//						else{
						parameterMap.put("ContextAction", NBSConstantUtil.DocumentIDFromInv);
						parameterMap.put("nbsDocumentUid", doc.getNbsDocumentUid().toString());
						parameterMap.put("method", "viewSubmit");
						doc.setActionLink(getHiperlink(dateLink(doc.getLastChgTime()), sCurrentTask, parameterMap, form.getActionMode()));
						//}
						parameterMap = new HashMap<Object,Object>();
					}
				}
				request.setAttribute("documentSummaryList",document);
				
				FileUploadUtil fileUtil = new FileUploadUtil();
				try{
					ArrayList<Object> attachmentDTs = (ArrayList<Object> ) ((PamProxyVO)proxy).getNbsAttachmentDTColl();				
					if(attachmentDTs != null && attachmentDTs.size() > 0)
						fileUtil.updateAttachmentsForView(attachmentDTs);
					request.setAttribute("nbsAttachments", attachmentDTs);
				}catch(Exception e){
					logger.error("Error in gettng the attachment from the proxyVO");
					throw new Exception(e.getMessage());
				}
				try{
					NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)request.getSession().getAttribute(
					"NBSSecurityObject");
					ArrayList<Object> noteDTs = (ArrayList<Object> ) ((PamProxyVO)proxy).getNbsNoteDTColl();				
					if(noteDTs != null && noteDTs.size() > 0)
						noteDTs = fileUtil.updateNotesForPrivateInd(noteDTs, nbsSecurityObj); 
						fileUtil.updateNotesForView(noteDTs);
					request.setAttribute("nbsNotes", noteDTs);
					}catch(Exception e){
						logger.error("Error in gettng the Notes from the proxyVO");
						throw new Exception(e.getMessage());
					}
				
						
			//Contract Tracking Contract Records
			Collection<Object> contactRecords = (Collection<Object> ) proxy.getTheCTContactSummaryDTCollection();
			
			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
			// HttpSession session = request.getSession();
			 String contextAction = request.getParameter("ContextAction");
			 contextAction = "ViewFile";//set in case of view person record
			 //TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS114", contextAction);
			// NBSContext.lookInsideTreeMap(tm);
		     //String sCurrTask = NBSContext.getCurrentTask(session);
		     String conditionCd = proxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile");
		    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + contextAction;
		    // String viewHref = "/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID");
		     String contactUrl="/nbs/"+sCurrentTask+".do?ContextAction=PatientSearch";
		     String populateContactRecord = "/nbs/ContactTracing.do?method=viewContact&mode=View&Action=DSInvestigationPath&DSInvestigationCondition="+conditionCd;
		     String managectAssoUrl="/nbs/"+sCurrentTask+".do?ContextAction=ManageCtAssociation";
		     request.setAttribute("contactUrl", contactUrl);
		     request.setAttribute("populateContactRecord", populateContactRecord);
		     request.setAttribute("managectAssoUrl", managectAssoUrl);
		     InvestigationUtil.storeContactContextInformation(request.getSession(),proxy.getPublicHealthCaseVO(),proxy.getThePersonVOCollection());
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
					//	String patLink = "<a href=\"/nbs/"+ sCurrentTask+ ".do?ContextAction=" + viewFileUrl+"&uid="+ String.valueOf(ctSumDT.getContactMprUid()) + "\">" + ctSumDT.getName() + "</a>";
						//String patLink = "<a href=\"/nbs/LoadViewFile1.do?ContextAction=ViewFile&uid="+ String.valueOf(ctSumDT.getContactEntityUid()) + "\">" + ctSumDT.getName() + "</a>";
						
						String patLink = "<a href=\"/nbs/" + sCurrentTask + ".do?ContextAction=" + viewFileUrl
			                        + "&uid=" + String.valueOf(ctSumDT.getContactMprUid()) + "\">" + getContactsNamedByPatientNameString(ctSumDT,true);
			              
						
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

	protected static String setContextForEdit(PamForm form, HttpServletRequest request, HttpSession session) {

		PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPamClientVO().getOldPamProxyVO());

		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId()));
		form.getAttributeMap().put("caseLocalId", form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());

		Collection<Object>  nms = personVO.getThePersonNameDTCollection();
		String strPName ="";
	    String strFName="";
	    String strMName="";
	    String strLName="";
	    String DOB="";
	    String CurrSex = "";
	    String nmSuffix = "";
		/*if(nms != null)
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
	     }*/
	    if(nms != null)
	     {
	      Iterator<Object>  itname = nms.iterator();
	      Timestamp mostRecentNameAOD = null;
	       while (itname.hasNext()) {
	         PersonNameDT name = (PersonNameDT) itname.next();

	         /*if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
	        	 if(nameDT.getFirstNm() != null)
	        	 strFName = nameDT.getFirstNm();
	        	 if(nameDT.getMiddleNm() != null)
	        		 strMName =  nameDT.getMiddleNm();
	        	  if(nameDT.getLastNm() != null)
	        		  strLName =   nameDT.getLastNm();
	        	  if(nameDT.getNmSuffix() != null)
	        		  nmSuffix =   nameDT.getNmSuffix();

	         }*/
	         // for personInfo
            if (name != null && name.getNmUseCd() != null &&
                name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
                name.getStatusCd() != null &&
                name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                name.getRecordStatusCd() != null &&
                name.getRecordStatusCd().
                equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
              if (mostRecentNameAOD == null ||
                  (name.getAsOfDate() != null &&
                  !name.getAsOfDate().before(mostRecentNameAOD))) {
           	      strFName="";
          	          strMName="";
          	          strLName="";
          	          nmSuffix ="";
                mostRecentNameAOD = name.getAsOfDate();                 
                if(name.getFirstNm() != null)
   	        	 strFName = name.getFirstNm();
   	        	 if(name.getMiddleNm() != null)
   	        		 strMName =  name.getMiddleNm();
   	        	  if(name.getLastNm() != null)
   	        		  strLName =   name.getLastNm();
   	        	  if(name.getNmSuffix() != null)
   	        		  nmSuffix =   name.getNmSuffix();
 			 
 		
              }
            }
	         
	       }
	     }
		strPName = strFName +" "+strMName+ " "+strLName;
		if(null == strPName || strPName.equalsIgnoreCase("null")){
			strPName ="";
		}
		CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
		form.getAttributeMap().put("patientLocalName", strPName);
		request.setAttribute("patientLocalName", strPName);
		request.setAttribute("patientSuffixName", cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));


		if(personVO.getThePersonDT().getBirthTime() != null){
		//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
		java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
		DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
		}
		form.getAttributeMap().put("patientDOB", DOB);
		request.setAttribute("patientDOB", DOB);

		if(personVO.getThePersonDT().getCurrSexCd() != null)
		 CurrSex = personVO.getThePersonDT().getCurrSexCd();
		if(CurrSex.equalsIgnoreCase("F"))
			CurrSex = "Female";
		if(CurrSex.equalsIgnoreCase("M"))
			CurrSex = "Male";
		if(CurrSex.equalsIgnoreCase("U"))
			CurrSex = "Unknown";
		form.getAttributeMap().put("patientCurrSex", CurrSex);
		request.setAttribute("patientCurrSex", CurrSex);
		//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()

		// set notifications (createdBy/Date, updatedBy/Date) in request.
		PublicHealthCaseDT phcDT = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
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

	private static String setContextForCreate(PersonVO personVO, HttpServletRequest request,PamForm form) {

		String localId = personVO.getThePersonDT().getLocalId();
		form.getAttributeMap().put("patientLocalId", PersonUtil.getDisplayLocalID(localId));
		Collection<Object>  nms = personVO.getThePersonNameDTCollection();
		String strPName ="";
	    String strFName="";
	    String strMName="";
	    String strLName="";
	    String DOB="";
	    String CurrSex = "";
	    String nmSuffix = "";
		if(nms != null)
	     {
	      Iterator<Object>  itname = nms.iterator();
	      Timestamp mostRecentNameAOD = null;
	     
	       while (itname.hasNext()) {
	    	   PersonNameDT name = (PersonNameDT) itname.next();

	        /* if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
	        	 if(nameDT.getFirstNm() != null)
	        	 strFName = nameDT.getFirstNm();
	        	 if(nameDT.getMiddleNm() != null)
	        		 strMName =  nameDT.getMiddleNm();
	        	  if(nameDT.getLastNm() != null)
	        		  strLName =   nameDT.getLastNm();
	        	  if(nameDT.getNmSuffix() != null)
	        		  nmSuffix =   nameDT.getNmSuffix();

	         }*/
	         
	         // for personInfo
             if (name != null && name.getNmUseCd() != null &&
                 name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
                 name.getStatusCd() != null &&
                 name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                 name.getRecordStatusCd() != null &&
                 name.getRecordStatusCd().
                 equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
               if (mostRecentNameAOD == null ||
                   (name.getAsOfDate() != null &&
                   !name.getAsOfDate().before(mostRecentNameAOD))) {
            	      strFName="";
           	          strMName="";
           	          strLName="";
           	          nmSuffix ="";
                 mostRecentNameAOD = name.getAsOfDate();                 
                 if(name.getFirstNm() != null)
    	        	 strFName = name.getFirstNm();
    	        	 if(name.getMiddleNm() != null)
    	        		 strMName =  name.getMiddleNm();
    	        	  if(name.getLastNm() != null)
    	        		  strLName =   name.getLastNm();
    	        	  if(name.getNmSuffix() != null)
    	        		  nmSuffix =   name.getNmSuffix();
  			 
  		
               }
             }

	       }
	     }
		strPName = strFName +" "+strMName+ " "+strLName;
		if(null == strPName || strPName.equalsIgnoreCase("null")){
			strPName ="";
		}

		form.getAttributeMap().put("patientLocalName", strPName);
		request.setAttribute("patientLocalName", strPName);
		CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
		request.setAttribute("patientSuffixName", cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));

		if(personVO.getThePersonDT().getBirthTime() != null){
		//DOB = PersonUtil.getBirthDate(""+personVO.getThePersonDT().getBirthTime().getMonth(),""+personVO.getThePersonDT().getBirthTime().getDate(),""+personVO.getThePersonDT().getBirthTime().getYear());
		java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
		DOB = sdfInput.format(personVO.getThePersonDT().getBirthTime());
		}
		form.getAttributeMap().put("patientDOB", DOB);
		request.setAttribute("patientDOB", DOB);

		if(personVO.getThePersonDT().getCurrSexCd() != null)
		 CurrSex = personVO.getThePersonDT().getCurrSexCd();
		if(CurrSex.equalsIgnoreCase("F"))
			CurrSex = "Female";
		if(CurrSex.equalsIgnoreCase("M"))
			CurrSex = "Male";
		if(CurrSex.equalsIgnoreCase("U"))
			CurrSex = "Unknown";
		form.getAttributeMap().put("patientCurrSex", CurrSex);
		request.setAttribute("patientCurrSex", CurrSex);
		//String DOB1= personVO.getThePersonDT().getBirthTime().getMonth()


		form.getPamClientVO().setAnswer(PamConstants.PATIENT_LOCAL_ID, localId);
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

	private void populateLabMorbValues(PamForm form, String sCurrentTask, HttpServletRequest request){

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
        	form.getPamClientVO().setAnswer(PamConstants.JURISDICTION, jurisdiction);
        	form.getAttributeMap().put("ReadOnlyJursdiction", "ReadOnlyJursdiction");
        }
		if(sCurrentTask.equals("CreateInvestigation5") || sCurrentTask.equals("CreateInvestigation6") || sCurrentTask.equals("CreateInvestigation7")|| sCurrentTask.equals("CreateInvestigation8"))
        {
			//this is from morb and for generic investigation only
			TreeMap<Object,Object> DSMorbMap = (TreeMap<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSMorbMap);
			// Prepopulate from morb to Investigation
			String INV111 = (String) DSMorbMap.get("INV111");
			form.getPamClientVO().setAnswer(PamConstants.DATE_REPORTED, INV111);
			// organization
			String INV183ORG = (String) DSMorbMap.get("INV183ORG");			
			form.getAttributeMap().put("INV218SearchResult", INV183ORG);
			String INV183UID = (String) DSMorbMap.get("INV183UID");
			form.getAttributeMap().put("INV218Uid", INV183UID);
			//Reporting provider
			String INV181PRV = (String) DSMorbMap.get("INV181PRV");			
			form.getAttributeMap().put("INV225SearchResult", INV181PRV);
			String INV181UID = (String) DSMorbMap.get("INV181UID");
			form.getAttributeMap().put("INV225Uid", INV181UID);
			//Reporting Physician
			String INV182PRV = (String) DSMorbMap.get("INV182PRV");			
			form.getAttributeMap().put("INV247SearchResult", INV182PRV);
			String INV182UID = (String) DSMorbMap.get("INV182UID");
			form.getAttributeMap().put("INV247Uid", INV182UID);
			// Hospital
			String INV184ORG = (String) DSMorbMap.get("INV184ORG");			
			form.getAttributeMap().put("INV233SearchResult", INV184ORG);
			String INV184UID = (String) DSMorbMap.get("INV184UID");
			form.getAttributeMap().put("INV233Uid", INV184UID);
			
			String INV128 = (String) DSMorbMap.get("INV128");
			form.getPamClientVO().setAnswer(PamConstants.WAS_THE_PATIENT_HOSPITALIZED, INV128);
			
			String INV132 = (String) DSMorbMap.get("INV132");
			form.getPamClientVO().setAnswer(PamConstants.ADMISSION_DATE, INV132);
			
			String INV133 = (String) DSMorbMap.get("INV133");
			form.getPamClientVO().setAnswer(PamConstants.DISCHARGE_DATE, INV133);
			
			String INV136 = (String) DSMorbMap.get("INV136");
			form.getPamClientVO().setAnswer(PamConstants.DIAGNOSIS_DATE, INV136);
			
			String INV137 = (String) DSMorbMap.get("INV137");
			form.getPamClientVO().setAnswer(PamConstants.ILLNESS_ONSET_DATE, INV137);
			
			String INV145 = (String) DSMorbMap.get("INV145");
			form.getPamClientVO().setAnswer(PamConstants.DID_THE_PATIENT_DIE, INV145);
			
			String INV178 = (String) DSMorbMap.get("INV178");
			form.getPamClientVO().setAnswer(PamConstants.PREGNANCY_STATUS, INV178);
			
			String INV148 = (String) DSMorbMap.get("INV148");
			form.getPamClientVO().setAnswer(PamConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY, INV148);
			
			String INV149 = (String) DSMorbMap.get("INV149");
			form.getPamClientVO().setAnswer(PamConstants.IS_THIS_PERSON_FOOD_HANDLER, INV149);

        }
		else if(sCurrentTask.equals("CreateInvestigation2") || sCurrentTask.equals("CreateInvestigation3") || sCurrentTask.equals("CreateInvestigation4") || sCurrentTask.equals("CreateInvestigation9"))
        {
          //this is from lab and for generic investigation only
			TreeMap<Object,Object> labMap = (TreeMap<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSLabMap);
			// Prepopulate from Lab to Investigation
			String INV111 = (String) labMap.get("INV111");
			form.getPamClientVO().setAnswer(PamConstants.DATE_REPORTED, INV111);
			// Physician
			String INV182PRV = (String) labMap.get("INV182PRV");			
			form.getAttributeMap().put("INV247SearchResult", INV182PRV);
			String INV182UID = (String) labMap.get("INV182UID");
			form.getAttributeMap().put("INV247Uid", INV182UID);
			// Organization
			String INV183ORG = (String) labMap.get("INV183ORG");			
			form.getPamClientVO().getAnswerMap().put("INV218SearchResult", INV183ORG);
			form.getAttributeMap().put("INV218SearchResult", INV183ORG);
			String INV183UID = (String) labMap.get("INV183UID");			
			form.getAttributeMap().put("INV218Uid", INV183UID);

	    }
	}


	public static PersonVO getPersonVO(String type_cd, PamProxyVO proxyVO) {
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  personVOCollection  = null;
		ParticipationDT participationDT = null;
		PersonVO personVO = null;
		participationDTCollection  = proxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		personVOCollection  = proxyVO.getThePersonVOCollection();
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

	public static OrganizationVO getOrganizationVO(String type_cd, PamProxyVO proxyVO) {

		Collection<Object>  organizationVOCollection  = null;
		OrganizationVO organizationVO = null;
		Collection<Object>  participationDTCollection  = null;
		ParticipationDT participationDT = null;
		participationDTCollection  = proxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		organizationVOCollection  = proxyVO.getTheOrganizationVOCollection();
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


	private void setUpdatedValues(PamForm form, HttpServletRequest req) {

		  Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		  if(answerMap != null && answerMap.size() > 0) {
			  String stateCd = (String) answerMap.get(PamConstants.STATE);
			  form.getOnLoadCityList(stateCd, req);
			  form.getDwrCountiesForState(stateCd);
		  }

	}
	
	private void populateContactTracing(PamForm form, HttpServletRequest req) {

		//Contract Tracking Contract Records
		CTContactSummaryDT contactRecords = new CTContactSummaryDT();
		ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
		ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();
		//contactNamedByPatList.add(contactRecords);
		//patNamedByContactsList.add(contactRecords);
		req.setAttribute("contactNamedByPatList",contactNamedByPatList);
		req.setAttribute("patNamedByContactsList",patNamedByContactsList);

	}
	protected void setCommonSecurityForCreateEditLoad(PamForm form, NBSSecurityObj nbsSecurityObj, HttpServletRequest req, PamProxyVO proxyVO ) {
		String conditionCd ="";
		if(proxyVO == null)
		 conditionCd = (String) NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSInvestigationCondition);
		else
			 conditionCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		boolean viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
				NBSOperationLookup.VIEW);

		boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
				NBSOperationLookup.ADD);
		String ContactTracingByConditionCd = getConditionTracingEnableInd(conditionCd);
		 form.getSecurityMap().put("ContactTracingEnableInd", ContactTracingByConditionCd);
		form.getSecurityMap().put("checkToViewContactTracing", String.valueOf(viewContactTracing));
		req.setAttribute("checkToAddContactTracing",new Boolean(addContactTracing).toString());
		
	}
	
	
	  protected void setCommonSecurityForView(PamForm form, PamProxyVO proxyVO, NBSSecurityObj nbsSecurityObj, HttpServletRequest req) {

		   String programAreaCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
	       String jurisdictionCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
	       String sharedInd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();
	       String conditionCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

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
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	         boolean assocMorbPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	        boolean checkViewEditObsLabPermission = nbsSecurityObj.getPermission(
								NBSBOLookup.OBSERVATIONLABREPORT,
								NBSOperationLookup.EDIT);

	        boolean bManageTreatment = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	                NBSOperationLookup.ASSOCIATETREATMENTS,
	                proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	                proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);


	        boolean checkViewTreatmentPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);
	        
	       boolean checkCreateNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
							      NBSOperationLookup.CREATE, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							      proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	       boolean checkCreateNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
							      NBSOperationLookup.CREATENEEDSAPPROVAL);
	       
	       boolean checkCaseReporting = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
							      NBSOperationLookup.CREATE, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							      proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
			
			boolean checkCaseReportingNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
							      NBSOperationLookup.CREATENEEDSAPPROVAL);



	       boolean deleteInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.DELETE,
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	       boolean manageVaccination = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
	               NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS,
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	       
	       boolean checkViewVaccinationPermission = nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
	       
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
	        form.getSecurityMap().put("VaccinationDisplay",String.valueOf(checkViewVaccinationPermission));
	        form.getSecurityMap().put("checkToViewDocument", String.valueOf(viewDocInvestigation));
	        form.getSecurityMap().put("checkToViewContactTracing", String.valueOf(viewContactTracing));
	        form.getSecurityMap().put("ContactTracingEnableInd", viewContactTracingByConditionCd);
	        form.getSecurityMap().put("printCDCForm", "DISPLAYED");
	        form.getSecurityMap().put("shareButton", "DISPLAYED");
	        req.setAttribute("checkToAddContactTracing",new Boolean(addContactTracing).toString());
	        req.setAttribute("manageAssoPerm",new Boolean(manageAssoPerm).toString());

	    }

		public static boolean showCreateNotificationButton(PamProxyVO proxy, NBSSecurityObj securityObj) {

			if (proxy.getTheNotificationSummaryVOCollection() != null) {

			for (Iterator<Object> anIterator = proxy.getTheNotificationSummaryVOCollection().iterator(); anIterator.hasNext();) {

				NotificationSummaryVO notSumVO = (NotificationSummaryVO) anIterator.next();
				if (notSumVO.getAutoResendInd() != null && 
						((notSumVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF) && notSumVO.getAutoResendInd().equalsIgnoreCase("T")))) {
						return false;
					}
				}
			}
		 return true;
		}
		
		public static boolean showTransferOwnerShipButton(PamProxyVO proxy, NBSSecurityObj securityObj) {

			 if(proxy.isOOSystemPendInd() == true)
				 return false;
			 else
				 return true;
		}

		public void setAnswerArrayMapAnswers(PamForm form, ArrayList<Object> multiSelects, Map<Object,Object> answerMap) {

			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			//Initialize
			//form.getPamClientVO().setPamArrayAnswerMap(returnMap);
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
			  	  form.getPamClientVO().getArrayAnswerMap().putAll(returnMap);

				}
			}
		}

	    public void setCheckBoxAnswersWithCodeSet(Map<Object,Object> answerMap,ArrayList<Object> checkboxes, Map<Object,Object> returnMap) {

	    	CachedDropDownValues cdv = new CachedDropDownValues();
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

		public void setJurisdictionForCreate(PamForm form, NBSSecurityObj nbsSecurityObj, HttpSession session) {

			String programAreaCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);

			setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
		}
		private void setJurisdictionForEdit(PamForm form, NBSSecurityObj nbsSecurityObj, PamProxyVO proxyVO) {

			String programAreaCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
			String conditionCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();

			setJurisdiction(form, programAreaCd, conditionCd, nbsSecurityObj);
		}

		private void setJurisdiction(PamForm form, String programAreaCd, String conditionCd, NBSSecurityObj nbsSecurityObj) {

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
		 * viewRuleAdminLoadUtil method retrieves the PamProxyVO from the EJB and sets to
		 * PamClientVO, attribute of RuleAdminForm
		 *
		 * @param form
		 * @param request
		 */
		  public void viewRuleAdminLoadUtil(RuleAdminForm form, HttpServletRequest request) throws Exception {
			  form.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			  form.setFormFieldMap(new HashMap<Object,Object>());
			  form.setErrorTabs(new String[0]);
			  form.setTabId(String.valueOf(PropertyUtil.getInstance().getDefaultInvTabOrder()-1));
			  form.getStateList();
			  form.getAttributeMap().remove(PamConstants.REQ_FOR_NOTIF);
			  form.getAttributeMap().remove(PamConstants.NO_REQ_FOR_NOTIF_CHECK);
			  String invFormCd = form.getPamFormCd();
			  this.loadQuestions(invFormCd);
			  this.loadQuestionKeys(invFormCd);
			  form.setPamFormCd(invFormCd);
			  RulesEngineUtil reUtils = new RulesEngineUtil();
			  form.setFormFieldMap(reUtils.initiateForm(this.getQuestionKeyMap(), form));
		  }

		/**
		 * previewLDF is to view the LDFs added for the FormCd
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public void previewLDF(PamForm form,HttpServletRequest request) {

			try {
				String formCd = form.getPamFormCd();
				if(formCd != null) {
					form.setActionMode("Preview");
					if(formCd != null && formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_RVCT))
						form.setPageTitle("Preview Tuberculosis LDFs", request);
					else if(formCd != null && formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_VAR))
						form.setPageTitle("Preview Varicella LDFs", request);

					//Set the URL for the Return to Link
					HttpSession session = request.getSession();
					String page = session.getAttribute("page") == null ? "" : (String)session.getAttribute("page");
					String pageId = session.getAttribute("PageID") == null ? "" : (String)session.getAttribute("PageID");
				    String boName = session.getAttribute("businessObjectNm") == null ? "" : (String) session.getAttribute("businessObjectNm");
				    String condCd = session.getAttribute("conditionCd") == null ? "" : (String) session.getAttribute("conditionCd");
					form.getAttributeMap().put("backToManage", "/nbs/LDFLoad.do?page=" + page + "&PageID="+ pageId + "&businessObjectNm=" + boName + "&conditionCd="+ condCd);

					this.loadQuestions(formCd);
					//Load the question map for the appropriate FormCd.
					RulesEngineUtil reUtils = new RulesEngineUtil();
					try {
						form.setFormFieldMap(reUtils.initiateForm(getQuestionMap(), form));
					} catch (Exception e) {
						logger.error("Exception while Loading Rules & FormFieldMap in previewLDF: " + e.getMessage());
					}

					LocalFieldGenerator.makeLdfHtml(formCd, "Preview", new TreeMap<Object,Object>(), new PamClientVO(), request,":");
				}
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				logger.error("Exception in previewLDF: " + e.getMessage());
			}
		}


		  /**
		   * setRVCTAnswersForViewEdit retrieves RVCT Answers from NBS_Answers and puts in the form.
		   * @param form
		   * @param answerMap
		   */
		  public void setMSelectCBoxAnswersForViewEdit(PamForm form, Map<Object,Object> answerMap) throws NEDSSAppException {

			  try{
			  Map<Object,Object> returnMap = new HashMap<Object,Object>();
			  //Multiselect answers
			  ArrayList<Object> multiSelects = retrieveMultiSelectQIds(form);
			  this.setAnswerArrayMapAnswers(form, multiSelects, answerMap);
			  //Load all PAM Specific answers to form
			  setPamSpecificAnswersForViewEdit(form, answerMap, returnMap);
			  ArrayList<Object> checkboxes = retrieveCheckboxQIds(form);
			  this.setCheckBoxAnswersWithCodeSet(answerMap, checkboxes, returnMap);
			  form.getPamClientVO().getAnswerMap().putAll(returnMap);

		  } catch (Exception e) {
				logger.fatal("PamLoadUtil.setMSelectCBoxAnswersForViewEdit Exception thrown:"
						+ e.getMessage(),e);
				throw new NEDSSAppException(e.getMessage(),e);
			}
		  }

		  public void setMSelectCBoxAnswersForViewEdit(PamForm form, Map<Object,Object> answerMap, PamLoadUtil pamLoadUtil) throws NEDSSAppException {

			  try{
			  Map<Object,Object> returnMap = new HashMap<Object,Object>();
			  //Multiselect answers
			  ArrayList<Object> multiSelects = retrieveMultiSelectQIds(form, pamLoadUtil);
			  this.setAnswerArrayMapAnswers(form, multiSelects, answerMap);
			  //Load all PAM Specific answers to form
			  setPamSpecificAnswersForViewEdit(form, answerMap, returnMap);
			  ArrayList<Object> checkboxes = retrieveCheckboxQIds(form, pamLoadUtil);
			  this.setCheckBoxAnswersWithCodeSet(answerMap, checkboxes, returnMap);
			  form.getPamClientVO().getAnswerMap().putAll(returnMap);

		  } catch (Exception e) {
				logger.fatal("PamLoadUtil.setMSelectCBoxAnswersForViewEdit Exception thrown:"
						+ e.getMessage(),e);
				throw new NEDSSAppException(e.getMessage(),e);
			}
		  }

		  
		  public ArrayList<Object> retrieveMultiSelectQIds(PamForm form, PamLoadUtil pamLoadUtil) {

			  return(retrieveQIdentifiersByType(1013,form, pamLoadUtil));
		  }

		  public ArrayList<Object> retrieveCheckboxQIds(PamForm form) {

			  return(retrieveQIdentifiersByType(1001, form));
		  }
		  

		  public ArrayList<Object> retrieveMultiSelectQIds(PamForm form) {

			  return(retrieveQIdentifiersByType(1013,form));
		  }

		  public ArrayList<Object> retrieveCheckboxQIds(PamForm form, PamLoadUtil pamLoadUtil) {

			  return(retrieveQIdentifiersByType(1001, form, pamLoadUtil));
		  }

		  private ArrayList<Object> retrieveQIdentifiersByType(int nbsComponentUid, PamForm form, PamLoadUtil pamLoadUtil) {
			  ArrayList<Object> list = new ArrayList<Object> ();
			  StringBuffer js = new StringBuffer("");
			 Iterator<Object>  iter = pamLoadUtil.getQuestionMap() .keySet().iterator();
				while(iter.hasNext()) {
					String key = (String) iter.next();
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) pamLoadUtil.getQuestionMap() .get(key);
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
					jsSt = jsSt.substring(0, (jsSt.length()-1));
					form.getAttributeMap().put("selectEltIdsArray", jsSt);
				}
				return list;
		  }

		  
		  private ArrayList<Object> retrieveQIdentifiersByType(int nbsComponentUid, PamForm form) {
			  ArrayList<Object> list = new ArrayList<Object> ();
			  StringBuffer js = new StringBuffer("");
			 Iterator<Object>  iter = getQuestionMap() .keySet().iterator();
				while(iter.hasNext()) {
					String key = (String) iter.next();
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) getQuestionMap() .get(key);
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
					jsSt = jsSt.substring(0, (jsSt.length()-1));
					form.getAttributeMap().put("selectEltIdsArray", jsSt);
				}
				return list;
		  }

		  


		public static void setLDFs(PamForm form, Map<Object,Object> answerMap, HttpServletRequest request) throws Exception {
			//LDF Specific Stuff
			try {
				if(answerMap == null)
					answerMap = new TreeMap<Object,Object>();
				String actionMode = form.getActionMode() == null ? NEDSSConstants.CREATE_LOAD_ACTION : form.getActionMode();
				LocalFieldGenerator.makeLdfHtml(form.getPamFormCd(), actionMode, answerMap, form.getPamClientVO(), request,":");

			} catch (Exception e) {
				logger.error("Error in setLDFs while loading LDFs in PamLoadUtil: " + e.toString());
				//throw new Exception(e.toString());
			}
		}

		  
	  /**
	   * _confirmationMethodsForViewEdit extracts Confirmation Method Collection<Object>   from PHCase and sets it to form
	   * @param form
	   * @param proxyVO
	   */
	  private static void _confirmationMethodsForViewEdit(PamForm form, PamProxyVO proxyVO) {
	  	  ArrayList<Object> multiList = proxyVO.getPublicHealthCaseVO().getTheConfirmationMethodDTCollection() == null ? new ArrayList<Object>() :(ArrayList<Object> ) proxyVO.getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
	  	  if(multiList.size() > 0) {
		  	  String [] answerList = new String[multiList.size()];
		  	  for(int i=0; i<multiList.size(); i++) {
		  		  ConfirmationMethodDT cm = (ConfirmationMethodDT)multiList.get(i);
				  String answer = cm.getConfirmationMethodCd();
				  answerList[i] = answer;
				  //Confirmation Date
				  if(cm.getConfirmationMethodTime() != null)
					  form.getPamClientVO().getAnswerMap().put(PamConstants.CONFIRM_DATE, StringUtils.formatDate(cm.getConfirmationMethodTime()));
		  	  }
		  	  form.getPamClientVO().getArrayAnswerMap().put(PamConstants.CONFIRM_METHOD_CD, answerList);	  		  
	  	  }
		}		  
		
	  
	  private void setNNDIndicator(PamProxyVO proxyVO, PamForm form){
		  form.getAttributeMap().put(PamConstants.NO_REQ_FOR_NOTIF_CHECK, "true");              	
		  Collection<Object>  actRelationshipDTColl = proxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
		  if(actRelationshipDTColl != null && actRelationshipDTColl.size()>0){
		     Iterator<Object>  iter = actRelationshipDTColl.iterator();
              while(iter.hasNext()) {	
              	ActRelationshipDT dt = (ActRelationshipDT) iter.next();
                  	if(dt != null && dt.isNNDInd()){
                  		form.getAttributeMap().put(PamConstants.NO_REQ_FOR_NOTIF_CHECK, "false");
                  		break;
                  	}
                  		
              }
			  
		  }
		  
		   
		  
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
		
}
