package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
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
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class to construct PAMProxyVO out of PamClientVO and delegates to PamProxyEJB for persistance.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PamStoreUtil.java
 * Aug 7, 2008
 * @version
 * 
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class PamStoreUtil {

	static final LogUtils logger = new LogUtils(PamStoreUtil.class.getName());
	private Map<Object,Object> questionMap;
	public static CachedDropDownValues cdv = new CachedDropDownValues();

	public Map<Object, Object> loadQuestions(String invFormCd){

		 Map<Object, Object> questionMap = new  HashMap<Object, Object>();
		if(QuestionsCache.getQuestionMap()!=null)
			questionMap = (Map)QuestionsCache.getQuestionMap().get(invFormCd);
		else
			questionMap = new HashMap<Object,Object>();
		
		return questionMap;
	}

	/**
	 *
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PamProxyVO createHandler(PamForm form, HttpServletRequest req) throws Exception {

		PamProxyVO proxyVO = null;
		try {
			form.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			form.setErrorList(new ArrayList<Object>());
			//Load Imported Counties(INV156) based on INV154(Imported State). If INV154 not answered, load INV156 values derived from Default(Current) State
			if(form.getPamClientVO().getAnswer(PamConstants.IMPORTED_STATE)==null)
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
			else
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getPamClientVO().getAnswer(PamConstants.IMPORTED_STATE)));
			String invFormCd = form.getPamFormCd();
			this.loadQuestions(invFormCd);
			setQuestionMap(this.loadQuestions(invFormCd));

			//LDFs
			setLDFs(form, form.getPamClientVO(), req);

			handleFormRules(form,NEDSSConstants.CREATE_SUBMIT_ACTION);
			proxyVO = new PamProxyVO();

			if(form.getErrorList()==null || form.getErrorList().size()==0){
				Long tempID = -1L;
				proxyVO.setItNew(true);
				//Set PublicHealthCase Information
				ProgramAreaVO programAreaVO = getProgAreaVO(req.getSession());
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

				//persist PublicHealthCase
				tempID = setPublicHealthCaseForCreate(tempID, form, req, proxyVO, programAreaVO, userId);

				//persist PatientRevision from Answers
				Long patientUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
				setPatientForEventCreate(patientUid, tempID, proxyVO, form, req, userId);

				//persist all answers
				this.setPamSpecifcAnswersForCreateEdit(proxyVO, form, session, userId, questionMap);

				//set Participations
				this.setParticipationsForCreate(proxyVO, form, tempID, req);
				//set EntityColl (PAM Specific)
				this.setEntitiesForCreateEdit(form, proxyVO, tempID, "0", userId);

				String contextAction = req.getParameter("ContextAction");
				if(contextAction != null && contextAction.equalsIgnoreCase("SubmitNoViewAccess")) {
					form.setActionMode("SubmitNoViewAccess");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Submitting Create " +  form.getPamFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
		return proxyVO;
	}

	/**
	 *
	 * @param form
	 * @param req
	 * @throws Exception
	 */
	public PamProxyVO editHandler(PamForm form, HttpServletRequest req) throws Exception {
		PamProxyVO proxyVO = null;
		PamLoadUtil pamLoadUtil = new PamLoadUtil();
		try {
			form.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			HttpSession session = req.getSession();
			form.setErrorList(new ArrayList<Object>());
			//Load Imported Counties(INV156) based on INV154(Imported State). If INV154 not answered, load INV156 values derived from Default(Current) State
			if(form.getPamClientVO().getAnswer(PamConstants.IMPORTED_STATE)==null)
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE()));
			else
				form.setDwrStateSiteCounties(CachedDropDowns.getCountyCodes(form.getPamClientVO().getAnswer(PamConstants.IMPORTED_STATE)));			
			String invFormCd = form.getPamFormCd();
			setQuestionMap(this.loadQuestions(invFormCd));
			handleRaceForRules(form.getPamClientVO());
			checkNotificationAssociationToInvestigation(form,req);
			handleFormRules(form,NEDSSConstants.EDIT_SUBMIT_ACTION);
			proxyVO = new PamProxyVO();
			if(form.getErrorList()==null || form.getErrorList().size()==0){
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
				String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
				proxyVO.setItDirty(true);
				setPublicHealthCaseForEdit(form, req, proxyVO, userId);

				PersonVO personVO = pamLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPamClientVO().getOldPamProxyVO());
				PublicHealthCaseVO phcVO = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO();
				setPatientForEventEdit(form, personVO, proxyVO, req, userId);
				//persist all answers
				setPamSpecifcAnswersForCreateEdit(proxyVO, form, session, userId);
				setParticipationsForEdit(proxyVO, form, phcVO, req);
				//set EntityColl (PAM Specific)
				setEntitiesForCreateEdit(form, proxyVO, personVO.getThePersonDT().getPersonUid(), personVO.getThePersonDT().getVersionCtrlNbr().toString(),  userId);
				proxyVO.setPublicHealthCaseVO(phcVO);
				proxyVO.setTheNotificationSummaryVOCollection(form.getPamClientVO().getOldPamProxyVO().getTheNotificationSummaryVOCollection());
				Collection<Object>  patientColl = new ArrayList<Object> ();
				patientColl.add(personVO);
				proxyVO.setThePersonVOCollection(patientColl);
			} else {
				
				PamProxyVO prxyVO = form.getPamClientVO().getOldPamProxyVO();
				String phcUid = prxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().toString();
				Map<Object, Object> map = new HashMap<Object, Object>();
				pamLoadUtil.populatePamAssocations(prxyVO, phcUid, map,req, form);
				setLDFs(form, form.getPamClientVO(), req);
			}
			updateNbsAnswersForDirty(form, proxyVO);

			// set investigation createdBy/Date, updatedBy/Date in request.
			PublicHealthCaseDT phcDT = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
			req.setAttribute("createdDate", StringUtils.formatDate(phcDT.getAddTime()));
			req.setAttribute("createdBy", phcDT.getAddUserName());
			req.setAttribute("updatedDate", StringUtils.formatDate(phcDT.getLastChgTime()));
			req.setAttribute("updatedBy", phcDT.getLastChgUserName());

			//set the notification status details in request
			ArrayList<Object> nsColl = form.getPamClientVO().getOldPamProxyVO().getTheNotificationSummaryVOCollection() == null ? new ArrayList<Object>() : (ArrayList<Object> ) form.getPamClientVO().getOldPamProxyVO().getTheNotificationSummaryVOCollection();
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
			logger.error("Error while Submitting Create " +  form.getPamFormCd() +  " Page: "+ e.toString());
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
	public static void viewHandler(PamForm form, HttpServletRequest request) throws Exception {

		try {
			PamLoadUtil pamLoadUtil = new PamLoadUtil();
			PersonVO personVO = pamLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, form.getPamClientVO().getOldPamProxyVO());
			PublicHealthCaseVO phcVO = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO();
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
			logger.error("Error while Submitting View " +  form.getPamFormCd() +  " Page: "+ e.toString());
			throw new Exception(e.toString());
		}
   }

	private static void setPatientForEventEdit(PamForm form, PersonVO personVO, PamProxyVO proxyVO, HttpServletRequest request, String userId) {

			personVO.setItDirty(true);
			personVO.getThePersonDT().setItDirty(true);

			setDemographicInfoForEdit(personVO, form.getPamClientVO().getAnswerMap());
			setNamesForEdit(personVO, form.getPamClientVO().getAnswerMap(), userId);
			setEntityLocatorParticipationsForEdit(personVO, form.getPamClientVO().getAnswerMap(), userId);
			setRaceForEdit(personVO, form.getPamClientVO(), proxyVO, userId);
			setEthnicityForEdit(personVO, form.getPamClientVO().getAnswerMap(), userId);
			setIdsForEdit(personVO, form.getPamClientVO().getAnswerMap(), userId);

			//LDFs
			personVO.setTheStateDefinedFieldDataDTCollection(extractPatientLDFs(form));
	}

	private static void filterCommonAnswers(Map<Object,Object> answerMap) throws Exception {

    	Field fields[] = PamConstants.class.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			Field field = fields[i];
			String fieldNm = field.getName();
			String fieldTypeNm = field.getType().getName();
			if (fieldTypeNm.equals("java.lang.String")) {
				String key = getVal(field.get(fieldNm));
				answerMap.remove(key);
			}
		}
	}

	public void setPamSpecifcAnswersForCreateEdit(PamProxyVO proxyVO, PamForm form, HttpSession session, String userId, Map<Object, Object> questionMap) {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		//filters common answers(DEMs and INVs) as they are just used to support UI / Rules
		try {
			filterCommonAnswers(answerMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering common answers from AnswerMap: " + e.toString());
		}
		Set<Object> keySet = answerMap.keySet();

		this.setAnswerArrayMapAnswers(form, returnMap, userId);
		this.setCheckBoxAnswersWithCodeSet(answerMap, questionMap);
		//setOtherAnswers(form, answerMap, proxyVO);
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			while(iter.hasNext()) {
				Object questionId = iter.next();
				Object obj = answerMap.get(questionId);

				if(obj instanceof ArrayList<?>) {
					returnMap.put(questionId, obj);
					continue;
				}
					answer = getVal(answerMap.get(questionId));

				if( answer!= "" && answer.trim().length() > 0) {

					NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);

					if(questionMap.get(questionId) != null) {

						NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)questionMap.get(questionId);
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							returnMap.put(questionId, answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAM Answers");
					}
				}

			}
		}
		proxyVO.getPamVO().setPamAnswerDTMap(returnMap);
	}

	

	public void setPamSpecifcAnswersForCreateEdit(PamProxyVO proxyVO, PamForm form, HttpSession session, String userId) {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		//filters common answers(DEMs and INVs) as they are just used to support UI / Rules
		try {
			filterCommonAnswers(answerMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering common answers from AnswerMap: " + e.toString());
		}
		Set<Object> keySet = answerMap.keySet();

		this.setAnswerArrayMapAnswers(form, returnMap, userId);
		this.setCheckBoxAnswersWithCodeSet(answerMap, questionMap);
		//setOtherAnswers(form, answerMap, proxyVO);
		if(!keySet.isEmpty()) {

			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			while(iter.hasNext()) {
				Object questionId = iter.next();
				Object obj = answerMap.get(questionId);

				if(obj instanceof ArrayList<?>) {
					returnMap.put(questionId, obj);
					continue;
				}
					answer = getVal(answerMap.get(questionId));

				if( answer!= "" && answer.trim().length() > 0) {

					NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);

					if(getQuestionMap().get(questionId) != null) {

						NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)questionMap.get(questionId);
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							returnMap.put(questionId, answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAM Answers");
					}
				}

			}
		}
		proxyVO.getPamVO().setPamAnswerDTMap(returnMap);
	}


	private static Long setPublicHealthCaseForCreate(Long tempID, PamForm form, HttpServletRequest req, PamProxyVO proxy, ProgramAreaVO programAreaVO, String userId) {

		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();

		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(tempID--));
		phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED)));
		phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(PamConstants.INV_START_DATE)));
		phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
		phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(userId));
		phcVO.getThePublicHealthCaseDT().setCaseClassCd(getVal(answerMap.get(PamConstants.CASE_CLS_CD)));
		phcVO.getThePublicHealthCaseDT().setCaseTypeCd("I");
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		answerMap.put(PamConstants.CONDITION_CD, programAreaVO.getConditionCd());
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(programAreaVO.getConditionShortNm());
		phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(getVal(answerMap.get(PamConstants.INV_STATUS_CD )));
		phcVO.getThePublicHealthCaseDT().setJurisdictionCd(getVal(answerMap.get(PamConstants.JURISDICTION)));
		phcVO.getThePublicHealthCaseDT().setMmwrWeek(getVal(answerMap.get(PamConstants.MMWR_WEEK)));
		phcVO.getThePublicHealthCaseDT().setMmwrYear(getVal(answerMap.get(PamConstants.MMWR_YEAR)));
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		phcVO.getThePublicHealthCaseDT().setStatusCd("A");
		phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(programAreaVO.getProgramJurisdictionOid());
		
		String sharedInd = null;
		if(answerMap.get(PamConstants.SHARED_IND)!= null && answerMap.get(PamConstants.SHARED_IND).equals("1"))
			sharedInd = "T";
		else if((answerMap.get(PamConstants.SHARED_IND)!= null && !answerMap.get(PamConstants.SHARED_IND).equals("1"))||answerMap.get(PamConstants.SHARED_IND)== null)
			sharedInd = "F";
		phcVO.getThePublicHealthCaseDT().setSharedInd(sharedInd);

		phcVO.getThePublicHealthCaseDT().setTxt(getVal(answerMap.get(PamConstants.TUB_GEN_COMMENTS)));
		// These questions are specific to Varicella
		if(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_COUNTY))!=null && !getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_COUNTY)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setRptToCountyTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_COUNTY)));
		}
		if(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_STATE))!=null && !getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_STATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setRptToStateTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_STATE)));
		}
		if(getVal(answerMap.get(PamConstants.DIAGNOSIS_DATE))!=null && !getVal(answerMap.get(PamConstants.DIAGNOSIS_DATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setDiagnosisTime_s(getVal(answerMap.get(PamConstants.DIAGNOSIS_DATE)));
		}
		if(getVal(answerMap.get(PamConstants.ILLNESS_ONSET_DATE))!=null && !getVal(answerMap.get(PamConstants.ILLNESS_ONSET_DATE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setEffectiveFromTime_s(getVal(answerMap.get(PamConstants.ILLNESS_ONSET_DATE)));
		}
		if(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET))!=null && !getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setPatAgeAtOnset(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET)));
		}
		if(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET_UNIT_CODE))!=null && !getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET_UNIT_CODE)).equals(""))
		{
			phcVO.getThePublicHealthCaseDT().setPatAgeAtOnsetUnitCd(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET_UNIT_CODE)));
		}

		// These questions are for extending PHC table for common fields - ODS changes
		if(getVal(answerMap.get(PamConstants.DATE_ASSIGNED_TO_INVESTIGATION))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime_s(getVal(answerMap.get(PamConstants.DATE_ASSIGNED_TO_INVESTIGATION)));
		}
		if(getVal(answerMap.get(PamConstants.WAS_THE_PATIENT_HOSPITALIZED))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd(getVal(answerMap.get(PamConstants.WAS_THE_PATIENT_HOSPITALIZED)));
		}
		if(getVal(answerMap.get(PamConstants.ADMISSION_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedAdminTime_s(getVal(answerMap.get(PamConstants.ADMISSION_DATE)));
		}
		if(getVal(answerMap.get(PamConstants.DISCHARGE_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedDischargeTime_s(getVal(answerMap.get(PamConstants.DISCHARGE_DATE)));
		}
		if(getVal(answerMap.get(PamConstants.DURATION_OF_STAY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setHospitalizedDurationAmt_s(getVal(answerMap.get(PamConstants.DURATION_OF_STAY)));
		}
		if(getVal(answerMap.get(PamConstants.PREGNANCY_STATUS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setPregnantIndCd(getVal(answerMap.get(PamConstants.PREGNANCY_STATUS)));
		}
		if(getVal(answerMap.get(PamConstants.DID_THE_PATIENT_DIE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutcomeCd(getVal(answerMap.get(PamConstants.DID_THE_PATIENT_DIE)));
		}
		if(getVal(answerMap.get(PamConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setDayCareIndCd(getVal(answerMap.get(PamConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY)));
		}
		if(getVal(answerMap.get(PamConstants.IS_THIS_PERSON_FOOD_HANDLER))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd(getVal(answerMap.get(PamConstants.IS_THIS_PERSON_FOOD_HANDLER)));
		}
		if(getVal(answerMap.get(PamConstants.IMPORTED_COUNTRY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCountryCd(getVal(answerMap.get(PamConstants.IMPORTED_COUNTRY)));
		}
		if(getVal(answerMap.get(PamConstants.IMPORTED_STATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedStateCd(getVal(answerMap.get(PamConstants.IMPORTED_STATE)));
		}
		if(getVal(answerMap.get(PamConstants.IMPORTED_CITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt(getVal(answerMap.get(PamConstants.IMPORTED_CITY)));
		}
		if(getVal(answerMap.get(PamConstants.IMPORTED_COUNTY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setImportedCountyCd(getVal(answerMap.get(PamConstants.IMPORTED_COUNTY)));
		}
		if(getVal(answerMap.get(PamConstants.INVESTIGATION_DEATH_DATE))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setDeceasedTime_s(getVal(answerMap.get(PamConstants.INVESTIGATION_DEATH_DATE)));
		}
		if(getVal(answerMap.get(PamConstants.OUTBREAK_INDICATOR))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutbreakInd(getVal(answerMap.get(PamConstants.OUTBREAK_INDICATOR)));
		}
		if(getVal(answerMap.get(PamConstants.OUTBREAK_NAME))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setOutbreakName(getVal(answerMap.get(PamConstants.OUTBREAK_NAME)));
		}
		//Added for Contact Tracing 
		if(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_FROM))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInfectiousFromDate_s(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_FROM)));
		}
		if(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_TO))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setInfectiousToDate_s(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_TO)));
		}
		if(getVal(answerMap.get(PamConstants.CONTACT_PRIORITY))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setPriorityCd(getVal(answerMap.get(PamConstants.CONTACT_PRIORITY)));
		}
		if(getVal(answerMap.get(PamConstants.CONTACT_STATUS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setContactInvStatus(getVal(answerMap.get(PamConstants.CONTACT_STATUS)));
		}
		if(getVal(answerMap.get(PamConstants.CONTACT_COMMENTS))!=null )
		{
			phcVO.getThePublicHealthCaseDT().setContactInvTxt(getVal(answerMap.get(PamConstants.CONTACT_COMMENTS)));
		}
		
		setAdditionalPhcAnswersForCreateEdit(phcVO, form);
		phcVO.setItNew(true);
		phcVO.setItDirty(false);

		Collection<Object>  theActIdDTCollection  = new ArrayList<Object> ();
		ActIdDT actIDDT = new ActIdDT();
		actIDDT.setItNew(true);
		actIDDT.setActIdSeq(new Integer(1));
		actIDDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
		actIDDT.setRootExtensionTxt(getVal(answerMap.get(PamConstants.STATE_CASE)));
		theActIdDTCollection.add(actIDDT);

		ActIdDT actIDDT1 = new ActIdDT();
		actIDDT1.setItNew(true);
		actIDDT1.setActIdSeq(new Integer(2));
		actIDDT1.setTypeCd("CITY");
		actIDDT1.setRootExtensionTxt(getVal(answerMap.get(PamConstants.COUNTY_CASE)));
		theActIdDTCollection.add(actIDDT1);

		phcVO.setTheActIdDTCollection(theActIdDTCollection);

		proxy.setPublicHealthCaseVO(phcVO);
		return tempID;

	}

	private static void setPublicHealthCaseForEdit(PamForm form, HttpServletRequest req, PamProxyVO proxyVO, String userId) {

		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		PublicHealthCaseVO phcVO = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO();
		String caseClassCd = answerMap.get(PamConstants.CASE_CLS_CD) == null ? "" : answerMap.get(PamConstants.CASE_CLS_CD).toString();
		String oldCaseClassCd = phcVO.getThePublicHealthCaseDT().getCaseClassCd();
		phcVO.getThePublicHealthCaseDT().setCaseClassCd(caseClassCd);
		phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED)));
		phcVO.getThePublicHealthCaseDT().setActivityFromTime_s(getVal(answerMap.get(PamConstants.INV_START_DATE)));
		phcVO.getThePublicHealthCaseDT().setMmwrWeek(getVal(answerMap.get(PamConstants.MMWR_WEEK)));
		phcVO.getThePublicHealthCaseDT().setMmwrYear(getVal(answerMap.get(PamConstants.MMWR_YEAR)));
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(getVal(answerMap.get(PamConstants.INV_STATUS_CD )));
		phcVO.getThePublicHealthCaseDT().setLastChgUserId(Long.valueOf(userId));
		phcVO.getThePublicHealthCaseDT().setLastChgTime(new Timestamp(new Date().getTime()));
		String sharedInd = null;
		if(answerMap.get(PamConstants.SHARED_IND)!= null && answerMap.get(PamConstants.SHARED_IND).equals("1"))
			sharedInd = "T";
		else if((answerMap.get(PamConstants.SHARED_IND)!= null && !answerMap.get(PamConstants.SHARED_IND).equals("1"))||answerMap.get(PamConstants.SHARED_IND)== null)
			sharedInd = "F";
		phcVO.getThePublicHealthCaseDT().setSharedInd(sharedInd);

		phcVO.getThePublicHealthCaseDT().setTxt(getVal(answerMap.get(PamConstants.TUB_GEN_COMMENTS)));
		phcVO.getThePublicHealthCaseDT().setRptToCountyTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_COUNTY)));
		phcVO.getThePublicHealthCaseDT().setRptToStateTime_s(getVal(answerMap.get(PamConstants.DATE_REPORTED_TO_STATE)));
		phcVO.getThePublicHealthCaseDT().setDiagnosisTime_s(getVal(answerMap.get(PamConstants.DIAGNOSIS_DATE)));
		phcVO.getThePublicHealthCaseDT().setEffectiveFromTime_s(getVal(answerMap.get(PamConstants.ILLNESS_ONSET_DATE)));
		phcVO.getThePublicHealthCaseDT().setPatAgeAtOnset(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET)));
		phcVO.getThePublicHealthCaseDT().setPatAgeAtOnsetUnitCd(getVal(answerMap.get(PamConstants.PAT_AGE_AT_ONSET_UNIT_CODE)));
		phcVO.getThePublicHealthCaseDT().setInvestigatorAssignedTime_s(getVal(answerMap.get(PamConstants.DATE_ASSIGNED_TO_INVESTIGATION)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedIndCd(getVal(answerMap.get(PamConstants.WAS_THE_PATIENT_HOSPITALIZED)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedAdminTime_s(getVal(answerMap.get(PamConstants.ADMISSION_DATE)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedDischargeTime_s(getVal(answerMap.get(PamConstants.DISCHARGE_DATE)));
		phcVO.getThePublicHealthCaseDT().setHospitalizedDurationAmt_s(getVal(answerMap.get(PamConstants.DURATION_OF_STAY)));
		phcVO.getThePublicHealthCaseDT().setPregnantIndCd(getVal(answerMap.get(PamConstants.PREGNANCY_STATUS)));
		phcVO.getThePublicHealthCaseDT().setOutcomeCd(getVal(answerMap.get(PamConstants.DID_THE_PATIENT_DIE)));
		phcVO.getThePublicHealthCaseDT().setDayCareIndCd(getVal(answerMap.get(PamConstants.IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY)));
		phcVO.getThePublicHealthCaseDT().setFoodHandlerIndCd(getVal(answerMap.get(PamConstants.IS_THIS_PERSON_FOOD_HANDLER)));
		phcVO.getThePublicHealthCaseDT().setImportedCountryCd(getVal(answerMap.get(PamConstants.IMPORTED_COUNTRY)));
		phcVO.getThePublicHealthCaseDT().setImportedStateCd(getVal(answerMap.get(PamConstants.IMPORTED_STATE)));
		phcVO.getThePublicHealthCaseDT().setImportedCityDescTxt(getVal(answerMap.get(PamConstants.IMPORTED_CITY)));
		phcVO.getThePublicHealthCaseDT().setImportedCountyCd(getVal(answerMap.get(PamConstants.IMPORTED_COUNTY)));
		phcVO.getThePublicHealthCaseDT().setDeceasedTime_s(getVal(answerMap.get(PamConstants.INVESTIGATION_DEATH_DATE)));
		phcVO.getThePublicHealthCaseDT().setOutbreakInd(getVal(answerMap.get(PamConstants.OUTBREAK_INDICATOR)));
		phcVO.getThePublicHealthCaseDT().setOutbreakName(getVal(answerMap.get(PamConstants.OUTBREAK_NAME)));
		//Added for Contact Tracing
		phcVO.getThePublicHealthCaseDT().setInfectiousFromDate_s(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_FROM)));
		phcVO.getThePublicHealthCaseDT().setInfectiousToDate_s(getVal(answerMap.get(PamConstants.INFECTIOUS_PERIOD_TO)));
		phcVO.getThePublicHealthCaseDT().setPriorityCd(getVal(answerMap.get(PamConstants.CONTACT_PRIORITY)));
		phcVO.getThePublicHealthCaseDT().setContactInvStatus(getVal(answerMap.get(PamConstants.CONTACT_STATUS)));
		phcVO.getThePublicHealthCaseDT().setContactInvTxt(getVal(answerMap.get(PamConstants.CONTACT_COMMENTS)));
				
		setAdditionalPhcAnswersForCreateEdit(phcVO, form);
		phcVO.setItNew(false);
		phcVO.setItDirty(true);
		setActIdForPublicHealthCaseEdit(phcVO, answerMap);
		boolean caseStatusChanged = caseStatusChanged(oldCaseClassCd, caseClassCd);
		phcVO.getThePublicHealthCaseDT().setCaseStatusDirty(caseStatusChanged);
		proxyVO.setPublicHealthCaseVO(phcVO);

	}


	private static void setPatientForEventCreate(Long patientUid, Long tempID, PamProxyVO proxyVO, PamForm form, HttpServletRequest req, String userId) {

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
		//persist PersonVO Components
		setPatientForEventCreate(personVO, form, proxyVO, userId);

		//LDFs
		personVO.setTheStateDefinedFieldDataDTCollection(extractPatientLDFs(form));
		patientColl.add(personVO);

		if (patientColl.size() > 0) {
			proxyVO.setThePersonVOCollection(patientColl);
		}
	}

	private static Collection<Object>  extractPatientLDFs(PamForm form) {
		String actionMode = form.getActionMode();
		ArrayList<Object> returnList = new ArrayList<Object> ();
		ArrayList<Object> toRemove = new ArrayList<Object> ();
		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
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
		Map<Object,Object> answerArrayMap = form.getPamClientVO().getArrayAnswerMap();
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

	private static void setPatientForEventCreate(PersonVO personVO, PamForm form, PamProxyVO proxyVO, String userId) {

		 Map<Object,Object> aodMap = new HashMap<Object,Object>();

		 setDemographicInfoForCreate(aodMap, personVO, form.getPamClientVO().getAnswerMap());
	     setNames(aodMap, personVO, form.getPamClientVO().getAnswerMap(), userId);
	     setAddressesForCreate(aodMap, personVO, form.getPamClientVO().getAnswerMap(), userId);
	     setEthnicityForCreate(personVO, form.getPamClientVO().getAnswerMap(), userId);
	     setRaceForCreate(personVO, form.getPamClientVO(), proxyVO, userId);
	     setTelephones(aodMap, personVO, form.getPamClientVO().getAnswerMap(), userId);
	     setIds(aodMap, personVO, form.getPamClientVO().getAnswerMap(), userId);
	     form.getPamClientVO().getAnswerMap().putAll(aodMap);
	}

	private static void setDemographicInfoForEdit(PersonVO personVO, Map<Object,Object> answerMap) {

		String dob = getVal(answerMap.get(PamConstants.DOB));
		String repAge = getVal(answerMap.get(PamConstants.REP_AGE));
		String bSex = getVal(answerMap.get(PamConstants.BIRTH_SEX));
		String cSex = getVal(answerMap.get(PamConstants.CURR_SEX));

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
		if(marital.equals("")) {
			personVO.getThePersonDT().setAsOfDateGeneral_s(null);
			answerMap.remove(PamConstants.MARITAL_STATUS_AS_OF);
		} else
			personVO.getThePersonDT().setAsOfDateGeneral(setPDate(answerMap, PamConstants.MARITAL_STATUS_AS_OF));

		String ethnicity = getVal(answerMap.get(PamConstants.ETHNICITY));
		if(ethnicity.equals("")) {
			personVO.getThePersonDT().setAsOfDateEthnicity_s(null);
			answerMap.remove(PamConstants.ETHNICITY_AS_OF);
		} else
			personVO.getThePersonDT().setAsOfDateEthnicity(setPDate(answerMap, PamConstants.ETHNICITY_AS_OF));

		personVO.getThePersonDT().setDescription(getVal(answerMap.get(PamConstants.GEN_COMMENTS)));
		personVO.getThePersonDT().setCurrSexCd(getVal(answerMap.get(PamConstants.CURR_SEX)));
		personVO.getThePersonDT().setDeceasedIndCd(getVal(answerMap.get(PamConstants.IS_PAT_DECEASED)));
		personVO.getThePersonDT().setMaritalStatusCd(getVal(answerMap.get(PamConstants.MAR_STAT)));
		personVO.getThePersonDT().setEthnicGroupInd(getVal(answerMap.get(PamConstants.ETHNICITY)));

		setCommonDemographicInfo(personVO, answerMap);

	}
	private static void setCommonDemographicInfo(PersonVO personVO, Map<Object,Object> answerMap) {

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

	}
	private static void setDemographicInfoForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap) {

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
		if(marital != null && marital.trim().length() > 0) {
			personVO.getThePersonDT().setMaritalStatusCd(marital);
			personVO.getThePersonDT().setAsOfDateGeneral_s(asOfDate);
			aodMap.put(PamConstants.MARITAL_STATUS_AS_OF, asOfDate);
		}
		//EthnicityAsOf
		String ethnicity = answerMap.get(PamConstants.ETHNICITY) == null ? null : (String) answerMap.get(PamConstants.ETHNICITY);
		if(ethnicity != null && ethnicity.trim().length() > 0) {
			personVO.getThePersonDT().setEthnicGroupInd(ethnicity);
			personVO.getThePersonDT().setAsOfDateEthnicity_s(asOfDate);
			aodMap.put(PamConstants.ETHNICITY_AS_OF, asOfDate);
		}
	}
   private static void setNames(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	      Long personUID = personVO.getThePersonDT().getPersonUid();
	      String lastNm = getVal(answerMap.get(PamConstants.LAST_NM));
	      String firstNm = getVal(answerMap.get(PamConstants.FIRST_NM));
	      String middleNm = getVal(answerMap.get(PamConstants.MIDDLE_NM));
	      String suffix = getVal(answerMap.get(PamConstants.SUFFIX));
	      String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));

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
	         Collection<Object>  pdts = new ArrayList<Object> ();
	         pdts.add(pdt);
	         personVO.setThePersonNameDTCollection(pdts);
	      } else {
	    	  answerMap.remove(PamConstants.NAME_INFORMATION_AS_OF);
	      }
	   }

   private static void setAddressesForCreate(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		String city = getVal(answerMap.get(PamConstants.CITY));
		String street1 = getVal(answerMap.get(PamConstants.ADDRESS_1));
		String street2 = getVal(answerMap.get(PamConstants.ADDRESS_2));
		String zip = getVal(answerMap.get(PamConstants.ZIP));
		String state = getVal(answerMap.get(PamConstants.STATE));
		String county = getVal(answerMap.get(PamConstants.COUNTY));
		String country = getVal(answerMap.get(PamConstants.COUNTRY));
		String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
		String cityLimits = getVal(answerMap.get(PamConstants.WITHIN_CITY_LIMITS));

		if ( (city != null && !city.equals("")) ||
		(street1 != null && !street1.equals("")) ||
		(street2 != null && !street2.equals("")) ||
		(zip != null && !zip.equals("")) ||
		(county != null && !county.equals("")) ||
		(state != null && !state.equals("")) ||
		(cityLimits != null && !cityLimits.equals("")))
		{
			aodMap.put(PamConstants.ADDRESS_INFORMATION_AS_OF, asOfDate);
			Long personUID = personVO.getThePersonDT().getPersonUid();
			Collection<Object>  arrELP = personVO.
			getTheEntityLocatorParticipationDTCollection();
			if (arrELP == null) {
			arrELP = new ArrayList<Object> ();

			}
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
			logger.info("Number of address in setBasicAddresses: " + arrELP.size());
			personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
		}
   }

   private static void setEthnicityForCreate(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	     Collection<Object>  ethnicityColl = new ArrayList<Object> ();
	     personVO.setThePersonEthnicGroupDTCollection(ethnicityColl);
	}

   private static void setEthnicityForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	   if(personVO.getThePersonEthnicGroupDTCollection() == null)
		   setEthnicityForCreate(personVO, answerMap, userId);
   }

   private static void setRaceForCreate(PersonVO personVO, PamClientVO clientVO, PamProxyVO proxyVO, String userId) {

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
   }
   public static void setTelephones(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

	   	String asOfDate = getVal(answerMap.get(PamConstants.DEM_DATA_AS_OF));
		Long personUID = personVO.getThePersonDT().getPersonUid();
		Collection<Object>  arrELP = new ArrayList<Object> ();

		EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTHome = new TeleLocatorDT();
		EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDTWork = new TeleLocatorDT();
		String homePhone = getVal(answerMap.get(PamConstants.H_PHONE));
		String homeExt = getVal(answerMap.get(PamConstants.H_PHONE_EXT));
		String workPhone = getVal(answerMap.get(PamConstants.W_PHONE));
		String workExt = getVal(answerMap.get(PamConstants.W_PHONE_EXT));

		if ((!homePhone.equals("")) || (!workPhone.equals("")) || (!homeExt.equals("")) || (!workExt.equals("")))
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
		if(personVO.getTheEntityLocatorParticipationDTCollection() != null)
			personVO.getTheEntityLocatorParticipationDTCollection().addAll(arrELP);

	}

   private static void setIds(Map<Object,Object> aodMap, PersonVO personVO, Map<Object,Object> answerMap, String userId) {

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
   }

	private static void setParticipationsForCreate(PamProxyVO proxyVO, PamForm form, Long revisionPatientUID, HttpServletRequest request) {

		Long phcUID = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();

		Collection<Object>  partsColl = new ArrayList<Object> ();

		// patient PHC participation
		ParticipationDT participationDT = createParticipation(phcUID, String.valueOf(revisionPatientUID), "PSN",
				NEDSSConstants.PHC_PATIENT);
		participationDT.setFromTime(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getEffectiveFromTime());
		partsColl.add(participationDT);

		proxyVO.setTheParticipationDTCollection(partsColl);

	}

	public static ParticipationDT createParticipation(Long actUid, String subjectEntityUid, String subjectClassCd, String typeCd) {

		ParticipationDT participationDT = new ParticipationDT();
		participationDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
		participationDT.setActUid(actUid);
		participationDT.setSubjectClassCd(subjectClassCd);
		participationDT.setSubjectEntityUid(new Long(subjectEntityUid.trim()));
		participationDT.setTypeCd(typeCd.trim());
		participationDT.setTypeDescTxt(cdv.getDescForCode("PAR_TYPE", typeCd.trim()));
		participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		participationDT.setItNew(true);
		participationDT.setItDirty(false);

		return participationDT;
	}

	private static void setEntitiesForCreateEdit(PamForm form, PamProxyVO proxyVO, Long revisionPatientUID, String versionCtrlNbr, String userId) {

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
			NbsActEntityDT oldDT = getNbsCaseEntity(NEDSSConstants.PHC_PATIENT,form.getPamClientVO().getOldPamProxyVO().getPamVO().getActEntityDTCollection() );
			entityDT.setNbsActEntityUid(oldDT.getNbsActEntityUid());

		}
		entityColl.add(entityDT);

		proxyVO.getPamVO().setActEntityDTCollection(entityColl);
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

    private static NbsActEntityDT deletePamCaseEntity(String typeCd, Long oldUid, Collection<Object>  oldEntityDTCollection) {

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


	public static Long sendProxyToPamEJB(PamProxyVO proxyVO, HttpServletRequest request, String sCurrentTask) throws NEDSSAppConcurrentDataException, Exception {
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

		Collection<Object>  names = personVO.getThePersonNameDTCollection();
		Collection<Object>  pdts = new ArrayList<Object> ();
		String lastNm = getVal(answerMap.get(PamConstants.LAST_NM));
		String firstNm = getVal(answerMap.get(PamConstants.FIRST_NM));
		String middleNm = getVal(answerMap.get(PamConstants.MIDDLE_NM));
		String suffix = getVal(answerMap.get(PamConstants.SUFFIX));
		String asOfDate = getVal(answerMap.get(PamConstants.NAME_INFORMATION_AS_OF));

		if (names != null && names.size() > 0) {
			Iterator<Object> ite = names.iterator();
			while (ite.hasNext()) {
				PersonNameDT pdt = (PersonNameDT) ite.next();
				if ((lastNm == null || lastNm.trim().equals("")) && (firstNm == null || firstNm.trim().equals(""))
						&& (middleNm == null || middleNm.trim().equals(""))
						&& (suffix == null || suffix.trim().equals(""))) {
					pdt.setItNew(false);
					pdt.setItDirty(false);
					pdt.setItDelete(true);
					answerMap.remove(PamConstants.NAME_INFORMATION_AS_OF);
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
				pdts.add(pdt);
			}
			personVO.setThePersonNameDTCollection(pdts);
		} else {
			setNames(null, personVO, answerMap, userId);
		}
	}

	public static void setEntityLocatorParticipationsForEdit(PersonVO personVO, Map<Object,Object> answerMap, String userId) {

		Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

		String homePhone = getVal(answerMap.get(PamConstants.H_PHONE));
		String homeExt = getVal(answerMap.get(PamConstants.H_PHONE_EXT));
		String workPhone = getVal(answerMap.get(PamConstants.W_PHONE));
		String workExt = getVal(answerMap.get(PamConstants.W_PHONE_EXT));
		if(homePhone.equals("") && homeExt.equals("") && workPhone.equals("") && workExt.equals(""))
			answerMap.remove(PamConstants.TELEPHONE_INFORMATION_AS_OF);

		String city = getVal(answerMap.get(PamConstants.CITY));
		String street1 = getVal(answerMap.get(PamConstants.ADDRESS_1));
		String street2 = getVal(answerMap.get(PamConstants.ADDRESS_2));
		String zip = getVal(answerMap.get(PamConstants.ZIP));
		String state = getVal(answerMap.get(PamConstants.STATE));
		String county = getVal(answerMap.get(PamConstants.COUNTY));
		String country = getVal(answerMap.get(PamConstants.COUNTRY));
		String asOfDate = getVal(answerMap.get(PamConstants.ADDRESS_INFORMATION_AS_OF));
		String cityLimits = getVal(answerMap.get(PamConstants.WITHIN_CITY_LIMITS));

		if (arrELP == null) {
			arrELP = new ArrayList<Object> ();
		}
		// home address
		EntityLocatorParticipationDT elp = getEntityLocatorParticipation(personVO, NEDSSConstants.POSTAL,
				NEDSSConstants.HOME);
		if (elp != null ) {
			if ((city == null || city.equals("")) && (street1 == null || street1.equals(""))
					&& (street2 == null || street2.equals("")) && (zip == null || zip.equals(""))
					&& (county == null || county.equals("")) && (state == null || state.equals("")) 
					&& (cityLimits == null || cityLimits.equals(""))) {
				
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setItNew(false);
				elp.setItDirty(false);
				elp.setItDelete(true);
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
				pl.setItNew(true);
				pl.setItDirty(false);
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
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.HOME);
		asOfDate = getVal(answerMap.get(PamConstants.TELEPHONE_INFORMATION_AS_OF));
		if (elp != null) {

			if ((homePhone == null || homePhone.equals("")) && (homeExt == null || homeExt.equals(""))) {
				//for edit when telephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setItNew(false);
				elp.setItDirty(false);
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
			teleDTHome.setItNew(true);
			teleDTHome.setItDirty(false);
			teleDTHome.setExtensionTxt(homeExt);
			teleDTHome.setPhoneNbrTxt(homePhone);
			teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpHome.setTheTeleLocatorDT(teleDTHome);
			arrELP.add(elpHome);
		}
		//work telephone
		elp = getEntityLocatorParticipation(personVO, NEDSSConstants.TELE, NEDSSConstants.WORK_PHONE);

		if (elp != null) {
			if ((workPhone == null || workPhone.equals("")) && (workExt == null || workExt.equals(""))) {
				//for edit when worktelephoneAsOfDate = null means blank telephone, then delete telephone
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
				elp.setItNew(false);
				elp.setItDirty(false);
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
			teleDTWork.setItNew(true);
			teleDTWork.setItDirty(false);

			teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			elpWork.setTheTeleLocatorDT(teleDTWork);
			arrELP.add(elpWork);
		}

		personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
	}
	public static void setRaceForEdit(PersonVO personVO, PamClientVO clientVO, PamProxyVO proxyVO, String userId) {
		//remove the added one for rules if any...
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

	private static EntityLocatorParticipationDT getEntityLocatorParticipation(PersonVO personVO, String classCd,
			String useCd) {
		Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
		if (arrELP == null || arrELP.size() == 0) {
			return null;
		} else {
			Iterator<Object> itrAddress = arrELP.iterator();
			while (itrAddress.hasNext()) {

				EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) itrAddress.next();
				if (elp.getClassCd() != null && elp.getClassCd().equals(classCd) && elp.getUseCd() != null
						&& elp.getUseCd().equals(useCd) && elp.getLocatorUid() != null) {
					return elp;
				}
			}
		}
		return null;
	}
    private static void setParticipationsForEdit(PamProxyVO proxyVO, PamForm form, PublicHealthCaseVO phcVO, HttpServletRequest request)
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
     * @param newEntityUid
     * @param form
     * @param proxyVO
     * @param typeCd
     * @param classCd
     * @param userId
     */
    public static void createOrDeleteParticipation(String newEntityUid, PamForm form, PamProxyVO proxyVO, String typeCd, String classCd, String userId)
    {
        logger.debug(" newEntityUid = " + newEntityUid + " old");
        if(newEntityUid != null && newEntityUid.indexOf("|") == -1) {
        	newEntityUid = newEntityUid + "|1";
        }
		String uid = splitUid(newEntityUid);
		String versionCtrlNbr = splitVerCtrlNbr(newEntityUid);


        PublicHealthCaseVO phcVO = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO();
        Collection<Object>  oldParCollection  = phcVO.getTheParticipationDTCollection();
        Collection<Object>  oldEntityCollection  = form.getPamClientVO().getOldPamProxyVO().getPamVO().getActEntityDTCollection();
        Long publicHealthCaseUID = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
        Collection<Object>  newParCollection  = new ArrayList<Object> ();
        Collection<Object>  newEntityCollection  = new ArrayList<Object> ();

        ParticipationDT oldParticipationDT = getParticipation(typeCd, classCd, oldParCollection);
        if(oldParticipationDT == null)
        {
            if(uid != null && !uid.trim().equals(""))
            {
                logger.info("old par in null and create new only: " + uid);
                newParCollection.add(createParticipation(publicHealthCaseUID, uid, classCd, typeCd));
                newEntityCollection.add(createPamCaseEntity(publicHealthCaseUID, uid, versionCtrlNbr, typeCd, userId));
            }
        } else {
            Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
            if(uid != null && !uid.trim().equals("") && !uid.equals(oldEntityUid.toString()))
            {
                logger.info("participation changed newEntityUid: " + uid + " for typeCd " + typeCd);
                newParCollection.add(deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
                newParCollection.add(createParticipation(publicHealthCaseUID, uid, classCd, typeCd));

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
        if(newParCollection.size() > 0)
        	proxyVO.getTheParticipationDTCollection().addAll(newParCollection);
        if(newEntityCollection.size() > 0) {
        	proxyVO.getPamVO().getActEntityDTCollection().addAll(newEntityCollection);
        } else {
        	NbsActEntityDT entityDT = getNbsCaseEntity(typeCd, oldEntityCollection);
        	if(entityDT != null) {
            	entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
            	proxyVO.getPamVO().getActEntityDTCollection().add(entityDT);
        	}
        }
    }


    private static ParticipationDT deleteParticipation(String typeCd, String classCd, Long oldUid, Collection<Object>  oldParticipationDTCollection)
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
    private static ParticipationDT getParticipation(String type_cd, String classCd,Collection<Object>  participationDTCollection)
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

    private static NbsActEntityDT getNbsCaseEntity(String typeCd, Collection<Object>  entityDTCollection) {

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



    private static void setActIdForPublicHealthCaseEdit(PublicHealthCaseVO phcVO, Map<Object,Object> answerMap)
    {
      if(phcVO.getTheActIdDTCollection() != null)
      {
          ActIdDT actIdDT = null;
         Iterator<Object>  itr = phcVO.getTheActIdDTCollection().iterator();
          while(itr.hasNext())
          {
              actIdDT = (ActIdDT) itr.next();
              if(actIdDT.getTypeCd() != null && actIdDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD))
            	  actIdDT.setRootExtensionTxt(getVal(answerMap.get(PamConstants.STATE_CASE)));
              else if(actIdDT.getTypeCd() != null && actIdDT.getTypeCd().equalsIgnoreCase("CITY"))
            	  actIdDT.setRootExtensionTxt(getVal(answerMap.get(PamConstants.COUNTY_CASE)));
              actIdDT.setItDirty(true);
              actIdDT.setItNew(false);
          }

      }

    }

    public void handleFormRules(PamForm form, String actionMode) throws NEDSSAppException {
		try {
			RulesEngineUtil reUtil = new RulesEngineUtil();
			Map<Object,Object> errorTabs = new HashMap<Object,Object>();
			Map<Object,Object> formFieldMap = reUtil.initiateForm(questionMap, form);
			if (formFieldMap != null && formFieldMap.size() > 0) {
				Collection<Object>  errorColl = formFieldMap.values();
				Iterator<Object> ite = errorColl.iterator();
				ArrayList<Object> errors = new ArrayList<Object> ();
				while (ite.hasNext()) {
					FormField fField = (FormField) ite.next();
					//fField != null added for the intrgrating with the new UI.
					if (fField != null && fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
					//if (fField.getErrorStyleClass()!=null && fField.getErrorStyleClass().equals(RuleConstants.REQUIRED_FIELD_CLASS)) {
						if(fField.getErrorMessage()!=null && fField.getErrorMessage().size()>0) {
							//if we can, rewrite error message into a link
							if (fField.getFieldId() != null && fField.getTabId() != null 
									&& (fField.getFieldType().equals(RuleConstants.DATATYPE_CODE) || fField.getFieldType().equals(NEDSSConstants.DATATYPE_DATE)) ) {
								ArrayList<Object> errorMessage = fField.getErrorMessage();
								int tabInt = fField.getTabId();
								tabInt = --tabInt;
								String theTabChar = "0";
								if (tabInt > 0)
									theTabChar = theTabChar.valueOf(tabInt);
								String theErrFocusId = fField.getFieldId().toString();
								String fieldWithoutTextbox ="";
								if (fField.getFieldType().equals(RuleConstants.DATATYPE_CODE)){
									fieldWithoutTextbox = theErrFocusId;
									theErrFocusId = theErrFocusId + "_textbox";
								}
								String errMsg = "<a href=\"javascript: selectTab(0,5," + 	theTabChar + ",'ongletTextEna','ongletTextDis','ongletTextErr',null,null);if(getElementByIdOrByName('" + theErrFocusId +"')==undefined || getElementByIdOrByName('" + theErrFocusId +"')==null) getElementByIdOrByName('" + fieldWithoutTextbox +"').focus(); else getElementByIdOrByName('" + theErrFocusId +"').focus();\">" + errorMessage.get(0) + "</a>";
								errorMessage.set (0, (Object) errMsg);
								fField.setErrorMessage(errorMessage);
							}
						errors.add(fField);
						}
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
		}catch (Exception e) {
			logger.fatal("PamStoreUtil.handleFormRules Exception in " , e);
			logger.fatal("PamStoreUtil.handleFormRules " , e.getMessage());
			throw new NEDSSAppException(e.getMessage(), e);
		}

	}

    private static Timestamp setPDate(Map<Object,Object> answerMap, String id) {

    	Object obj = answerMap.get(id);
    	if(obj != null)
    		return StringUtils.stringToStrutsTimestamp(obj.toString());

    	return null;
    }

    private void setAnswerArrayMapAnswers(PamForm form, Map<Object,Object> returnMap, String userId) {

    	Map<Object,Object> answerArrayMap = form.getPamClientVO().getArrayAnswerMap();
		if(answerArrayMap != null && answerArrayMap.size() > 0) {

			Iterator<Object> anIter = answerArrayMap.keySet().iterator();
			while(anIter.hasNext()) {
				ArrayList<Object> answerList = new ArrayList<Object> ();
				String questionId = getVal(anIter.next());
				if(questionId.equals(PamConstants.DETAILED_RACE_ASIAN) || questionId.equals(PamConstants.DETAILED_RACE_HAWAII) || questionId.equals(PamConstants.CONFIRM_METHOD_CD))
					continue;
				String[] answers = (String[])answerArrayMap.get(questionId);
				for(int i=1; i<=answers.length; i++) {
					String answerTxt = answers[i-1];
					if(answerTxt == null || (answerTxt == "")) continue;
					NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
					answerDT.setSeqNbr(new Integer(i));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT_LOAD_ACTION))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answerTxt);
					if(getQuestionMap().get(questionId) != null) {

						NbsQuestionMetadata qMetadata = (NbsQuestionMetadata)this.getQuestionMap().get(questionId);
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							answerList.add(answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in PAM Answers");
					}
				}
				returnMap.put(questionId, answerList);
			}
		}
    }

    


    private void setCheckBoxAnswersWithCodeSet(Map<Object,Object> answerMap) {

    	ArrayList<Object> chkboxList  = new ArrayList<Object> ();
    	Iterator<Object> iter = this.getQuestionMap().keySet().iterator();
    	while(iter.hasNext()) {
    		NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) this.getQuestionMap().get(iter.next());
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
    
    
    private void setCheckBoxAnswersWithCodeSet(Map<Object,Object> answerMap, Map<Object, Object> questionMap ) {

    	ArrayList<Object> chkboxList  = new ArrayList<Object> ();
    	Iterator<Object> iter = questionMap.keySet().iterator();
    	while(iter.hasNext()) {
    		NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) questionMap.get(iter.next());
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

    private static void handleRaceForRules(PamClientVO clientVO) {
		if(clientVO.getUnKnownRace() == 1 || clientVO.getAfricanAmericanRace() == 1 || clientVO.getAmericanIndianAlskanRace() == 1 || clientVO.getAsianRace() == 1 || clientVO.getHawaiianRace() == 1  || clientVO.getWhiteRace() == 1)
			clientVO.setAnswer(PamConstants.RACE, PamConstants.RACE);
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
		return programAreaVO;
	}

	public static void setContextForCreate(PamProxyVO proxyVO, Long phcUid, ProgramAreaVO programArea, HttpSession session) {
		// context store
		String investigationJurisdiction = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
		NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, phcUid.toString());
		NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
		String progArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
		NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, progArea);
	}

	public static void setLDFs(PamForm form, PamClientVO clientVO, HttpServletRequest request) throws Exception {
		//LDF Specific Stuff
		try {
			Map<Object,Object> answerMap = clientVO.getAnswerMap();
			String actionMode = form.getActionMode() == null ? NEDSSConstants.CREATE_LOAD_ACTION : form.getActionMode();
			LocalFieldGenerator.makeLdfHtml(form.getPamFormCd(), actionMode, answerMap, clientVO, request,":");

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
	private static void updateNbsAnswersForDirty(PamForm form, PamProxyVO proxyVO ) {

		Map<Object,Object> oldAnswers = form.getPamClientVO().getOldPamProxyVO().getPamVO().getPamAnswerDTMap();
		Map<Object,Object> newAnswers = proxyVO.getPamVO().getPamAnswerDTMap();
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
				checkAnswerListForDirty(oldAnswers, answerList);
			} else {
				NbsCaseAnswerDT dt = (NbsCaseAnswerDT) obj;
				Long qUid = (Long) dt.getNbsQuestionUid();
				if(oldAnswers.get(qUid) == null) {
					dt.setItNew(true);
					dt.setItDirty(false);
					dt.setItDelete(false);
				} else {
					NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldAnswers.get(qUid);
					dt.setItDirty(true);
					dt.setItNew(false);
					dt.setItDelete(false);
					dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
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
				markAnswerListForDelete(answerList);
			} else {
				NbsCaseAnswerDT dt = (NbsCaseAnswerDT)oldObj;
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		}
		//Add all from old to the new
		proxyVO.getPamVO().getPamAnswerDTMap().putAll(oldAnswers);
	}

	private static void markAnswerListForDelete(ArrayList<Object> list) {
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			NbsCaseAnswerDT dt = (NbsCaseAnswerDT)iter.next();
			dt.setItDelete(true);
			dt.setItNew(false);
			dt.setItDirty(false);
		}
	}

	private static void checkAnswerListForDirty(Map<Object,Object> oldAnswers, ArrayList<Object> list) {
		ArrayList<Object> tempList = new ArrayList<Object> ();
		ArrayList<Object> oldAList = null;
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			NbsCaseAnswerDT dt = (NbsCaseAnswerDT)iter.next();
			Long qUid = (Long) dt.getNbsQuestionUid();
			oldAList = (ArrayList<Object> )oldAnswers.get(qUid);
			if(oldAList != null && oldAList.size() > 0) {
				Iterator<Object> oldIter = oldAList.iterator();
				while(oldIter.hasNext()) {
					 NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT)oldIter.next();
					 if(oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt())) {
							dt.setItDirty(true);
							dt.setItNew(false);
							dt.setItDelete(false);
							dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
							tempList.add(oldDT);
					 } else if(dt.getNbsCaseAnswerUid() == null ){
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
		} catch (Exception e) {
			logger.error("Error while retrieving versionCtrlNbr from EntityUid: " + e.toString());
		}
		return versionCtrlNbr;
	}

/* This method checks the association related to Investigation.
 * It validates required NND fields before it saves the data.
 *
 */
	public static void checkNotificationAssociationToInvestigation(
			PamForm form, HttpServletRequest req)
			throws NEDSSAppConcurrentDataException, Exception {
		Map<Object,Object> notificationReqFields = new TreeMap<Object,Object>();
		InvestigationUtil investigationUtil = new InvestigationUtil();
		String formCd = investigationUtil.getInvFormCd(req, form);
		if (form.getPamClientVO().getOldPamProxyVO() != null
				&& form.getPamClientVO().getOldPamProxyVO()
						.getAssociatedNotificationsInd()) {
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
					String fieldValue = (String) form.getPamClientVO()
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
	private static void setAdditionalPhcAnswersForCreateEdit(PublicHealthCaseVO phcVO, PamForm form) {
		
		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		Map<Object,Object> answerArrayMap = form.getPamClientVO().getArrayAnswerMap();
		String reportingSrc = getVal(answerMap.get(PamConstants.REPORTING_SOURCE));
		phcVO.getThePublicHealthCaseDT().setRptSourceCd(reportingSrc);
		phcVO.getThePublicHealthCaseDT().setEffectiveToTime_s(getVal(answerMap.get(PamConstants.ILLNESS_END_DATE)));
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationAmt(getVal(answerMap.get(PamConstants.ILLNESS_DURATION)));
		phcVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd(getVal(answerMap.get(PamConstants.ILLNESS_DURATION_UNITS)));
		String diseaseImportCd = getVal(answerMap.get(PamConstants.DISEASE_IMPORT_CD));
		phcVO.getThePublicHealthCaseDT().setDiseaseImportedCd(diseaseImportCd);
		String transModeCd = getVal(answerMap.get(PamConstants.TRANSMISN_MODE_CD));
		phcVO.getThePublicHealthCaseDT().setTransmissionModeCd(transModeCd);
		String detectionMethodCd = getVal(answerMap.get(PamConstants.DETECTION_METHOD_CD));
		phcVO.getThePublicHealthCaseDT().setDetectionMethodCd(detectionMethodCd);
		_confirmationMethodList(phcVO, answerMap, answerArrayMap);
		
	}
	
	
	private static void _confirmationMethodList(PublicHealthCaseVO phcVO, Map<Object,Object> answerMap, Map<Object,Object> answerArrayMap) {

		Collection<Object>  confMethodColl = new ArrayList<Object> ();

		String confirmationDate = getVal(answerMap.get(PamConstants.CONFIRM_DATE));
		String[] answers = (String[])answerArrayMap.get(PamConstants.CONFIRM_METHOD_CD);
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
	 */
    public static void _setEntitiesForCreate(PamProxyVO proxyVO, PamForm form, int revisionPatientUID, String userId, HttpServletRequest request, String entityUid, String type, String subjectClassCd) {

		Long phcUID = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();

		Collection<Object>  partsColl = new ArrayList<Object> ();
		
		String uid = splitUid(entityUid);
		String versionCtrlNbr = splitVerCtrlNbr(entityUid);
		if (!entityUid.trim().equals("") && phcUID != null) {
			ParticipationDT invPartDT = createParticipation(phcUID, uid, subjectClassCd,type);
			//Retrieve Date Assigned to Investigation (INV110) from PHC
			Timestamp fromTime = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigatorAssignedTime();
			invPartDT.setFromTime(fromTime);
			partsColl.add(invPartDT);
			proxyVO.getTheParticipationDTCollection().addAll(partsColl);

			//NBSActEntity
			NbsActEntityDT entityDT = createPamCaseEntity(phcUID, uid, versionCtrlNbr, type, userId);
			proxyVO.getPamVO().getActEntityDTCollection().add(entityDT);
		}
	}
	
    public static void createActRelationshipForDoc(String sCurrentTask,PamProxyVO proxyVO, HttpServletRequest request){
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
    }
    
    public Map<Object, Object> getQuestionMap() {
    	return questionMap;
    }

    public void setQuestionMap(Map<Object, Object> questionMap) {
    	this.questionMap = questionMap;
    }
    
 }


