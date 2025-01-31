package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules.PageRulesGenerator;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for PageSubForm
 *
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * PageSubAction.java
 * November 5, 2018
 * @version 1.0
 */

public class PageSubFormLoadUtil {
	static final LogUtils logger = new LogUtils(PageSubFormLoadUtil.class.getName());
	public static final String ACTION_PARAMETER = "method";
	public static final String HIV = "HIV";
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static Map<Object, Object> questionMap;
	public static Map<Object, Object> questionKeyMap;
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	public static Map<Object, Object> hivQuestionMap;
	public static Map<Object, Object> coinfectionQuestionMap;
	public static Map<Object, Object> coinfectionQuestionKeyMap;
	

	public static void loadQuestions(String invFormCd) throws Exception {

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
	}

	public static void loadQuestionKeys(String invFormCd) {
		if (questionMap == null)
			return;
		questionKeyMap = new HashMap<Object, Object>();
		hivQuestionMap = new HashMap<Object, Object>();
		coinfectionQuestionMap = new HashMap<Object, Object>();
		coinfectionQuestionKeyMap = new HashMap<Object, Object>();

		Iterator iter = questionMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
					.get(key);
			questionKeyMap.put(metaData.getNbsQuestionUid(), key);
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
				coinfectionQuestionKeyMap.put(metaData.getNbsQuestionUid(),
						metaData);
			}
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
	public static void createGenericLoadUtil(PageSubForm pageForm,
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
			loadQuestions(pageFormCd);
			loadQuestionKeys(pageFormCd);
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
			/*Long patientUid = null;
			if (patientUidStr == null || patientUidStr.isEmpty())
				patientUid = (Long) NBSContext.retrieve(session,NBSConstantUtil.DSPatientPersonUID);
			else
				patientUid = new Long(patientUidStr);

			//Long patientUid = new Long(patientUidStr);
			PersonVO patientVO = null;
			patientVO = findMasterPatientRecord(patientUid, session);*/
			PersonVO investigatorVO = null;
			investigatorVO = PageManagementCommonActionUtil
					.getInvestigatorIfPresent(session, nbsSecurityObj);
		//	loadGenericDefaultEntitiesForCreate(pageForm, patientVO,
		//			investigatorVO);

			PageRulesGenerator reUtils = new PageRulesGenerator();
			pageForm.setFormFieldMap(reUtils.initiateForm(questionMap,
					(BaseForm) pageForm, (ClientVO) pageForm.getPageClientVO()));

			//ClientUtil.setPatientInformation(pageForm.getActionMode(), patientVO,
		//			clientVO, request, pageForm.getPageFormCd());
			setCommonGenericSecurityForCreateEdit(pageForm, nbsSecurityObj,
					request);
			setUpdatedValues(pageForm, request);
			populateBatchRecords(pageForm, pageFormCd, request.getSession(),
					new HashMap());
			//ClientUtil.setPersonIdDetails(patientVO, pageForm);
				//PageCreateHelper.setPatientForEventCreate(patientUid, tempID, null, pageForm, request, userId);
		//	populatePatientSummary(pageForm, patientVO, null, pageForm.getBusinessObjectType(), request);
			if( NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(pageForm.getBusinessObjectType())){
				PageManagementCommonActionUtil.setValidationContextForInterview(request);
				PageManagementCommonActionUtil.setCoinfectionContext(request, (ClientVO) pageForm.getPageClientVO(), NEDSSConstants.COINFECTION_FOR_INTERVIEW_EXISTS);
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
	 * loadGenericEntities() retrieves Participations' with types of PRVs and
	 * ORGs associated with the Form ToDoo: Add PLC (Place) support.
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 * @throws Exception
	 */
	/*public static void loadGenericEntities(BaseForm form, PageProxyVO proxyVO,
			Map<Object, Object> theQuestionMap, HttpServletRequest request) throws Exception
    {
		try {
			TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
			String subjectClassCd = "";
			Collection<Object> participantList = new ArrayList<Object>();
			Iterator quesIt = theQuestionMap.values().iterator();
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
					if (personVO != null)
					{
						String uidSt = personVO.getThePersonDT().getPersonUid()
								.toString()
								+ "|"
								+ personVO.getThePersonDT().getVersionCtrlNbr()
										.toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid", uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						
						String display1=helper.makePRVDisplayString(personVO, true);
						String display2=helper.makePRVDisplayString(personVO);
						if (personVO.getThePersonDT().getCd() != null
								&& personVO.getThePersonDT().getCd()
										.equalsIgnoreCase(NEDSSConstants.PAT)){
							String display=helper.makePatientDisplayString(personVO);
							form.getAttributeMap().put(
									parTypeVO.getQuestionIdentifier()
											+ "SearchResult",display);
						}
						else
							form.getAttributeMap().put(
									parTypeVO.getQuestionIdentifier()
											+ "SearchResult",display2);
						if (parTypeVO.getTypeCd() != null
								&& !parTypeVO.getTypeCd().toUpperCase()
										.contains("SUBJ")) {
							ParticipantListDisplay participant = new ParticipantListDisplay();
							participant.setTitle(parTypeVO.getTypeDescTxt());
							participant.setDetail(display1);
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
						String display=helper.makeORGDisplayString(organizationVO);
						String display1=helper.makeORGDisplayString(organizationVO,true);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "SearchResult",
								display);
						
						ParticipantListDisplay participant = new ParticipantListDisplay();
						participant.setTitle(parTypeVO.getTypeDescTxt());
						participant.setDetail(display1);
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
			logger.fatal("Exception occured in PageSubFormLoadUtil.loadGenericEntities: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

	}
*/
	/**
	 * _loadEntities retrieves Participations' and NBSActEntities' with types of
	 * PRVs and ORGs associated with Tuberculosis
	 *
	 * @param form
	 * @param proxyVO
	 * @param request
	 * @throws Exception
	 */
	/*public static void _loadEntities(PageSubForm form, PageProxyVO proxyVO,
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
			CachedDropDowns cdd = new CachedDropDowns();
			TreeMap<Object, Object> participationTypeCaseMap = cdd
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
					if (personVO != null) {
						String uidSt = personVO.getThePersonDT().getPersonUid()
								.toString()
								+ "|"
								+ personVO.getThePersonDT().getVersionCtrlNbr()
										.toString();
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier() + "Uid",
								uidSt);
						QuickEntryEventHelper helper = new QuickEntryEventHelper();
						String display = helper.makePRVDisplayString(personVO);
						String display1 = helper.makePRVDisplayString(personVO, true);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier()
										+ "SearchResult",display);
						if (parTypeVO.getTypeCd() != null
								&& !parTypeVO.getTypeCd().toUpperCase()
										.contains("SUBJ")) {
							ParticipantListDisplay participant = new ParticipantListDisplay();
							participant.setTitle(parTypeVO.getTypeDescTxt());
							participant.setDetail(display1);
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
						String display = helper.makeORGDisplayString(organizationVO);
						String display1 = helper.makeORGDisplayString(organizationVO);
						form.getAttributeMap().put(
								parTypeVO.getQuestionIdentifier()
										+ "SearchResult",
										display);
						
						ParticipantListDisplay participant = new ParticipantListDisplay();
						participant.setTitle(parTypeVO.getTypeDescTxt());
						participant.setDetail(display1);
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
			logger.fatal("Exception occured in PageSubFormLoadUtil._loadEntities: PageFormCd: " + form.getPageFormCd() + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
	} // _loadEntities()
*/

	

	
	private static void setUpdatedValues(PageSubForm form, HttpServletRequest req) throws Exception {
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
			logger.fatal("PageSubFormLoadUtil.setUpdatedValues Exception thrown:" + e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}
	
	protected static void setCommonGenericSecurityForCreateEdit(PageSubForm form,
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
			
// Remove/Hide the delete button for Release 6.0
//			boolean deleteInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.DELETE);
//			if (deleteInterview)
//				form.getSecurityMap().put("deleteGenericPermission",String.valueOf(deleteInterview));

			boolean editInterview = nbsSecurityObj.getPermission(businessObjLookupName, NBSOperationLookup.EDIT);
			if (editInterview)
				form.getSecurityMap().put("editGenericPermission",String.valueOf(editInterview));
		} catch (Exception e) {
			logger.fatal("PageSubFormLoadUtil.setCommonGenericSecurityForView Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
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
	public static void populateBatchRecords(BaseForm form, String invFormCd,
			HttpSession session, Map repeatingAnswerMap) throws Exception {
			String debugNBSQuestionUid = "";
			String debugAnswerTxt = "";
		try {
			
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
								be.setId(PageSubForm.getNextId());

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
				}// walking through the Batchmap

			}
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
	public static Map findBatchRecords(String invFrmCd, HttpSession session) throws NEDSSAppException {
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
				logger.fatal("PageSubFormLoadUtil.findBatchRecords Exception thrown:"
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
			logger.fatal("PageSubFormLoadUtil.findBatchRecords Exception thrown:"
					+ e.getMessage(),e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

}
