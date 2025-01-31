package gov.cdc.nedss.webapp.nbs.action.summary.util;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.AggregateSummaryDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.summary.AggregateSummaryForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Utility Class for Aggregate Summary
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * AggregateSummaryUtil.java
 * Aug 6, 2009
 * @version
 */
public class AggregateSummaryUtil {

	static LogUtils logger = new LogUtils(AggregateSummaryUtil.class.getName());
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static Map<Object,Object> questionKeyMap;
	public static Map<?,?> questionMap;
	
	public static Map<?,?> getQuestionMap() {
		return questionMap;
	}
	
	public static Map<Object,Object> getQuestionKeyMap() {
		return questionKeyMap;
	}
	
	private static void loadQuestions(String invFormCd){

		if(QuestionsCache.getQuestionMap()!=null) {
			questionMap = (Map<?,?>)QuestionsCache.getQuestionMap().get(invFormCd);
			loadQuestionKeys(invFormCd);
		} else
			questionMap = new HashMap<Object,Object>();
	}	
	
	private static void loadQuestionKeys(String invFormCd) {

		questionKeyMap = new HashMap<Object,Object>();
		Iterator<?> iter = questionMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			Object obj = questionMap.get(key);
			if(obj instanceof NbsQuestionMetadata) {
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
				questionKeyMap.put(metaData.getNbsQuestionUid(), key);				
			}
		}
	} 
	
	
	/**
	 * Once a table is created, the only thing that changes is the answer values(structure remains same until Server Restart)
	 * @param form
	 * @param summaryMap
	 * @return
	 */
	private static void _setAggregateTables(AggregateSummaryForm form) {
		
		if(form.getCategoryTableList().size() == 0) {
			Collection<?>  summaryList = retrieveAggregateSummaryFromQMap(questionMap);
			Iterator<?> iter = summaryList.iterator();
			while(iter.hasNext()) {
				CategoryTable ct = new CategoryTable();
				ArrayList<?> tableList = (ArrayList<?> ) iter.next();
				ArrayList<Object> headerList = _makeHeaders(tableList);
				ArrayList<Object> recordList = _makeRows(tableList);
				_populateTableInformation(ct,tableList);
				ct.setHeaders(headerList);
				ct.setRecords(recordList);
				form.setCategoryTable(ct);
			}
		}
	}
	
	/**
	 * Table Headers
	 * @param list
	 * @return
	 */
	private static ArrayList<Object> _makeHeaders(Collection<?>  list) {
		
		ArrayList<Object> returnList = new ArrayList<Object> ();
		//Create a pseudo Object with no Fillers(may change later -- TBD)
		returnList.add(new AggregateSummaryDT());
		
		Iterator<?> iter = list.iterator();
		long headerUid = 0L;
		long oldHeaderUid = 0L;
		int i = 0;
		while(iter.hasNext()) {
			AggregateSummaryDT dt = (AggregateSummaryDT) iter.next();
			headerUid = dt.getNbsIndicatorUid() == null ? 0L : dt.getNbsIndicatorUid().longValue();
			if(i == 0)
				oldHeaderUid = headerUid;
			if(i > 0) {
				if(headerUid == oldHeaderUid)
					break;
			}
			i++;
			returnList.add(dt);			
		}
		return returnList;
	}
	
	/**
	 * Table Rows
	 * @param list
	 * @return
	 */
	private static ArrayList<Object> _makeRows(Collection<?>  list) {
		ArrayList<Object> returnList = new ArrayList<Object> ();
		ArrayList<Object> rowList = new ArrayList<Object> ();
		Iterator<?> iter = list.iterator();
		long columnUid = 0L;
		long oldColumnUid = 0L;
		int i = 0;
		while(iter.hasNext()) {
			AggregateSummaryDT dt = (AggregateSummaryDT) iter.next();
			columnUid = dt.getNbsAggregateUid() == null ? 0L : dt.getNbsAggregateUid().longValue();
			if(i == 0) {
				oldColumnUid = columnUid;
				_prepareNewRow(rowList, dt);				
			}	
			if(columnUid == oldColumnUid) {				
				rowList.add(dt);
			} else {
				i = 0;
				oldColumnUid = columnUid;
				returnList.add(rowList);
				rowList = new ArrayList<Object> ();
				_prepareNewRow(rowList, dt);
				rowList.add(dt);
			}
			i++;			
		}
		returnList.add(rowList);
		return returnList;
		
	}
	
	private static void _populateTableInformation(CategoryTable ct, Collection<?>  list) {
		Iterator<?> iter = list.iterator();
		while(iter.hasNext()) {
			AggregateSummaryDT dt = (AggregateSummaryDT) iter.next();
			ct.setNbsTableUid(dt.getNbsTableUid());
			ct.setTableName(dt.getNbsTableName());
			break;
		}
		
	}
	
	private static void _prepareNewRow(ArrayList<Object> rowList, AggregateSummaryDT dt) {
		
		//Clone the dt and set it as 1st Entry as it will be used just to populate Column Headers
		AggregateSummaryDT chDT = new AggregateSummaryDT();
		chDT.setAggregateLabel(dt.getAggregateLabel());
		chDT.setAggregateToolTip(dt.getAggregateToolTip());
		rowList.add(chDT);		
	}
	
	/**
	 * retrieveAggregateSummaryFromQMap retrieves the Collection<Object>  of AggregateSummaryDTs (1 - Many relationship exists between NbsQuestionMetadata and AggregateSummaryDT)  
	 * @param summaryMap
	 * @return
	 */
	private static Collection<?>  retrieveAggregateSummaryFromQMap(Map<?,?> summaryMap) {
		
		Collection<Object>  summaryList = new ArrayList<Object> ();
		Iterator<?> iter = summaryMap.values().iterator();
		while(iter.hasNext()) {
			  Object object = iter.next();
			  //see if the Value of the Map<Object,Object> is an ArrayList(Collection<Object>  of Summary), then start building components
			  if(object instanceof ArrayList<?>) {
				  	  summaryList.add(object);
			  }
		}		
		return summaryList;
	}
	
	/**
	 * createLoadUtil
	 * @param form
	 * @param summaryMap
	 */
	public static void createLoadUtil(AggregateSummaryForm form, HttpServletRequest req) {
		
		//Reset answerMap
		form.getAnswerMap().clear();
		form.getAttributeMap().clear();
		Iterator<Object> iter = form.getSearchMap().keySet().iterator();
		while(iter.hasNext()) {
			String key = getVal(iter.next());
			if(questionMap.get(key) != null) {
				String ans = getVal(form.getSearchMap().get(key));
				if(!ans.equals("")) {
					form.setAnswer(key, ans);
				}				
			}			
		}	
		//Update formFields based on actionMode (SUM113,SUM115,SUM116 needs to be enabled in CREATE mode, not in EDIT mode)
		_updateFormFields(form);
		//Reset answers in CategoryTable (table structure will be still restored)
		_resetCategoryTable(form);

		//sort results (if any)
		_sortResults(form, req);
	}
	
	/**
	 * Iterates through CategoryTable and resets answers
	 * @param form
	 */
	private static void _resetCategoryTable(AggregateSummaryForm form) {
		
		Iterator<Object> it = form.getCategoryTableList().iterator();
		while(it.hasNext()) {
			CategoryTable table = (CategoryTable) it.next();
			 Collection<Object>  records = table.getRecords();
			Iterator<Object>  iter = records.iterator();
			 while(iter.hasNext()) {
				 ArrayList<?> list = (ArrayList<?> ) iter.next();
				Iterator<?>  iter1 = list.iterator();
				 while(iter1.hasNext()) {
					 AggregateSummaryDT dt = (AggregateSummaryDT) iter1.next();
					 dt.setAnswerTxt("");
				 }
			 }			
		}

	}
	
	private static void _updateFormFields(AggregateSummaryForm form) {
		Map<Object,Object> ffMap = form.getFormFieldMap();
		String mode = form.getActionMode();
		Iterator<Object> iter = ffMap.values().iterator();
		while(iter.hasNext()) {
			FormField fField = (FormField) iter.next();
			String qId = getVal(fField.getFieldId());
			//Set MMWR Week, MMWR Year and Surviellance Method Enabled in CREATE mode, readonly in EDIT mode
			if(qId.equals("SUM101") || qId.equals("SUM102") || qId.equals("SUM117")) {
				if(mode != null && (mode.equalsIgnoreCase(NEDSSConstants.CREATE) || mode.equalsIgnoreCase(NEDSSConstants.VIEW)))
					fField.getState().setEnabled(true);
				else if(mode != null && mode.equalsIgnoreCase(NEDSSConstants.EDIT))
					fField.getState().setEnabled(false);				
			}
		}
		
		
	}
	
	/**
	 * createSubmitUtil
	 * @param form
	 * @param contextAction
	 */
	public static Long createSubmitUtil(AggregateSummaryForm form, String contextAction, HttpServletRequest req) {
		
		Long phcUid = null;
		int tempID = -1;
		Map<Object,Object> answerMap = form.getAnswerMap();
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		
		TreeMap<Object,Object>   ConditionCdprogramAreaTm = null;
        String progAreaCd = "";
        CachedDropDownValues cd = new CachedDropDownValues();
        try {
			ConditionCdprogramAreaTm = cd.getSummaryReportConditionCodeProgAreaCd(form.getStrProgramAreas());
		} catch (Exception e) {
			logger.error("Error while retrieving ConditionCdProgramArea TreeMap: " + e.toString());
			e.printStackTrace();
		}
        if(ConditionCdprogramAreaTm.containsKey(form.getConditionCd()))
             progAreaCd = (String)ConditionCdprogramAreaTm.get(form.getConditionCd());
		
		
		SummaryReportProxyVO proxyVO = new SummaryReportProxyVO();
		proxyVO.setItNew(true); proxyVO.setItDirty(false);
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		phcVO.setItNew(true);phcVO.setItDirty(false);
		PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();		
		publicHealthCaseDT.setItNew(true);
		publicHealthCaseDT.setItDirty(false);
		publicHealthCaseDT.setPublicHealthCaseUid(new Long(tempID--));
		publicHealthCaseDT.setAddTime(new Timestamp(new Date().getTime()));
		publicHealthCaseDT.setAddUserId(Long.valueOf(userId));
		publicHealthCaseDT.setCaseTypeCd(NEDSSConstants.CASETYPECD_AGGREGATE_SUMMARY); //A : for Aggregate
		publicHealthCaseDT.setCdDescTxt(cdv.getConditionDesc(form.getConditionCd()));
		publicHealthCaseDT.setGroupCaseCnt(new Integer(1));
		//publicHealthCaseDT.setInvestigationStatusCd("O");
		publicHealthCaseDT.setCd(getVal(answerMap.get("SUM106")));
		//publicHealthCaseDT.setCaseClassCd(getVal(answerMap.get("SUM116"))); CaseStatus is always defaulted to 'C'
		publicHealthCaseDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_CONFIRMED);
		publicHealthCaseDT.setMmwrWeek(getVal(answerMap.get("SUM102")));
		publicHealthCaseDT.setMmwrYear(getVal(answerMap.get("SUM101")));
		publicHealthCaseDT.setRptCntyCd(getVal(answerMap.get("SUM100")));
		publicHealthCaseDT.setTxt(getVal(answerMap.get("SUM105")));
		//publicHealthCaseDT.setRptFormCmpltTime_s(getVal(answerMap.get("SUM113")));
		publicHealthCaseDT.setCountIntervalCd(NEDSSConstants.AGG_SUM_COUNT_INTERVAL_CD);		
		publicHealthCaseDT.setProgAreaCd(progAreaCd);
		publicHealthCaseDT.setStatusCd("A");
		phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
		proxyVO.setThePublicHealthCaseVO(phcVO);
		
		setAnswersForCreateEdit(proxyVO, form, userId);
		
		if(contextAction.equalsIgnoreCase("SubmitAndSendNotif")) {
			setActrelationshipForNotification(proxyVO, nbsSecurityObj);
			_updatePhcWithNotifLastChgTime(proxyVO);
		}
		//Finally, EJB call to persist !
		try {
			phcUid = sendProxyToPamEJB(form, proxyVO, req);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling EJB from AggregateSummaryUtil: " + e.toString());
		}
		return phcUid;
	}
	
	/**
	 * 
	 * @param form
	 * @param contextAction
	 * @param req
	 * @return
	 */
	public static boolean editSubmitUtil(AggregateSummaryForm form, String contextAction, HttpServletRequest req) {
		
		boolean success = false;
		Map<Object,Object> answerMap = form.getAnswerMap();
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		
		SummaryReportProxyVO proxyVO = new SummaryReportProxyVO();
		
		proxyVO.setItNew(false); proxyVO.setItDirty(true);
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();		
		phcVO.setItNew(false);phcVO.setItDirty(true);
		PublicHealthCaseDT publicHealthCaseDT = form.getOldProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = publicHealthCaseDT.getPublicHealthCaseUid();
		publicHealthCaseDT.setItNew(false);
		publicHealthCaseDT.setItDirty(true);
		publicHealthCaseDT.setLastChgTime(new Timestamp(new Date().getTime()));
		publicHealthCaseDT.setLastChgUserId(Long.valueOf(userId));
		//User can only edit Comments
		publicHealthCaseDT.setTxt(getVal(answerMap.get("SUM105")));
		publicHealthCaseDT.setStatusCd("A");
		phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
		proxyVO.setThePublicHealthCaseVO(phcVO);
		proxyVO.setTheNotificationVOCollection(form.getOldProxyVO().getTheNotificationVOCollection());
		
		setAnswersForCreateEdit(proxyVO, form, userId);
		
		if(contextAction.equalsIgnoreCase("SubmitAndSendNotif")) {
			setActrelationshipForNotification(proxyVO, nbsSecurityObj);
			_updatePhcWithNotifLastChgTime(proxyVO);
		}
		updateNbsAnswersForDirty(form, proxyVO);
		
		//Finally, EJB call to persist !
		try {
			phcUid = sendProxyToPamEJB(form, proxyVO, req);
			if(phcUid != null) 	success = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling EJB from AggregateSummaryUtil: " + e.toString());
		}
		return success;
	}

	/**
	 * 
	 * @param form
	 * @param contextAction
	 * @param req
	 * @return
	 */
	public static boolean deleteSummary(AggregateSummaryForm form,HttpServletRequest req) {
		
		boolean isDeleted = false;
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		
		SummaryReportProxyVO proxyVO = form.getOldProxyVO();
		proxyVO.setItDelete(true);
		PublicHealthCaseVO phcVO = form.getOldProxyVO().getPublicHealthCaseVO();
		phcVO.setItDelete(true);
		PublicHealthCaseDT publicHealthCaseDT = phcVO.getThePublicHealthCaseDT();
		Long phcUid = publicHealthCaseDT.getPublicHealthCaseUid();
		publicHealthCaseDT.setItNew(false);
		publicHealthCaseDT.setItDirty(false);
		publicHealthCaseDT.setItDelete(true);
		
		//Check if Notification exists, if so, mark the status_cd as "LOG_DEL"
		if(form.getOldProxyVO().getTheNotificationVOCollection() != null && form.getOldProxyVO().getTheNotificationVOCollection().size() > 0) {
			Iterator<Object> iter = form.getOldProxyVO().getTheNotificationVOCollection().iterator();
			while(iter.hasNext()) {
				NotificationVO nVO = (NotificationVO) iter.next();
				nVO.setItDelete(true);nVO.setItNew(false);
				nVO.getTheNotificationDT().setItDelete(true);
				nVO.getTheNotificationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
			}
			proxyVO.setTheNotificationVOCollection(form.getOldProxyVO().getTheNotificationVOCollection());

		}
		
		//Finally, EJB call to persist !
		try {
			phcUid = sendProxyToPamEJB(form, form.getOldProxyVO(), req);
			if(phcUid != null) 	isDeleted = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling EJB from AggregateSummaryUtil: " + e.toString());
		}
		
		return isDeleted;
	}
	/**
	 * 
	 * @param form
	 * @param req
	 * @return
	 */
	public static Long createNotification(AggregateSummaryForm form,HttpServletRequest req) {
		
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		PublicHealthCaseVO phcVO = form.getOldProxyVO().getPublicHealthCaseVO();
		PublicHealthCaseDT publicHealthCaseDT = phcVO.getThePublicHealthCaseDT();
		Long phcUid = publicHealthCaseDT.getPublicHealthCaseUid();
		publicHealthCaseDT.setItNew(false);
		publicHealthCaseDT.setItDirty(true);
		publicHealthCaseDT.setItDelete(false);
		
		setActrelationshipForNotification(form.getOldProxyVO(), nbsSecurityObj);
		_updatePhcWithNotifLastChgTime(form.getOldProxyVO());
		
		//Finally, EJB call to persist !
		try {
			phcUid = sendProxyToPamEJB(form, form.getOldProxyVO(), req);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling EJB from AggregateSummaryUtil: " + e.toString());
		}
		return phcUid;
		
	}	
	
	/**
	 * 
	 * @param summaryReportProxyVO
	 */
	private static void setActrelationshipForNotification(SummaryReportProxyVO summaryReportProxyVO, NBSSecurityObj nbsSecurityObj) {
          int tempID = -1;
          Collection<Object>  notificationColl = new ArrayList<Object> ();
          Collection<Object>  actRelashonshipDTColl =  new ArrayList<Object> ();
          PublicHealthCaseVO  phcVO = summaryReportProxyVO.getThePublicHealthCaseVO();
          NotificationVO notificationVO = new NotificationVO();
          NotificationDT notificationDT = new NotificationDT();
          notificationDT.setNotificationUid(new Long(tempID--));
          notificationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          notificationDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
          notificationDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
          //LastChgTime needs to be populated on add (to be consistent with the rest of the system as it is required for messaging)
          notificationDT.setLastChgTime(new Timestamp(new java.util.Date().getTime()));
          notificationDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
          notificationDT.setRecordStatusTime(new Timestamp(new java.util.Date().getTime()));
          notificationDT.setItNew(true);
          notificationDT.setCaseConditionCd(phcVO.getThePublicHealthCaseDT().getCd());
          notificationDT.setCaseClassCd(phcVO.getThePublicHealthCaseDT().getCaseClassCd());
          notificationDT.setProgAreaCd(phcVO.getThePublicHealthCaseDT().getProgAreaCd());
          //NAGG - Notification for AggregateSummary ??Change TBD
          notificationDT.setCd(NEDSSConstants.AGGREGATE_NOTIFICATION_CD);	  
          //Should recordStatusCd be set here -- StateModel?????  
          notificationDT.setRecordStatusCd(NEDSSConstants.APPROVED_STATUS);
          notificationDT.setAutoResendInd(NEDSSConstants.NOTIFICATION_AUTO_RESEND_OFF);
          notificationVO.setTheNotificationDT(notificationDT);
          notificationVO.setItNew(true);
          
          notificationColl.add(notificationVO);
          summaryReportProxyVO.setTheNotificationVOCollection(notificationColl);
          
          ActRelationshipDT actNotification = new ActRelationshipDT();
          actNotification.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          actNotification.setSourceActUid(notificationVO.getTheNotificationDT().getNotificationUid());
          actNotification.setSourceClassCd(NEDSSConstants.CLASS_CD_NOTIFICATION);
          actNotification.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          actNotification.setTargetActUid(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
          actNotification.setTargetClassCd(NEDSSConstants.CLASS_CD_CASE);
          actNotification.setTypeCd(NEDSSConstants.ACT128_TYP_CD);
          actNotification.setItNew(true);
          actNotification.setItDirty(false);

          actRelashonshipDTColl.add(actNotification);
          summaryReportProxyVO.setTheActRelationshipDTCollection(actRelashonshipDTColl);
   }
	
	/**
	 * loadSummaryData serves Edit & View Purpose
	 * @param form
	 * @param req
	 */
	public static void loadSummaryData(AggregateSummaryForm form, HttpServletRequest req) {
		
		//Set Security for DELETE AGGREGATE SUMMARY Permission a.w.a EDIT Permission in ViewMode
		if(form.getActionMode() != null && form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW)) {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
	        boolean addEditPermission = nbsSecurityObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.ADD);
	        boolean checkDeletePermission = nbsSecurityObj.getPermission(NBSBOLookup.SUMMARYREPORT, NBSOperationLookup.DELETE);
	        form.getAttributeMap().put("addButton", String.valueOf(addEditPermission));
	        form.getAttributeMap().put("deleteButton", String.valueOf(checkDeletePermission));
		}

		String phcUid = req.getParameter("phcUid") == null ? "" : req.getParameter("phcUid");
		if(phcUid.equals("")) {
			phcUid = req.getAttribute("phcUid") == null ? "" : req.getAttribute("phcUid").toString();
		}
		SummaryReportProxyVO proxyVO = getProxyFromPamEJB(Long.valueOf(phcUid), req);
		//Set proxy to old Obj
		form.setOldProxyVO(proxyVO);
		//Load proxy to Form
		Collection<Object>  answerColl = proxyVO.getThePublicHealthCaseVO().getNbsAnswerCollection();
		Map<Object,Object> qIdsMap = updateMapWithQIds(answerColl);
		//Load Spec'd out answers
		setPhcAnswersForViewEdit(proxyVO, form);
		
		setAnswersForViewEdit(form, qIdsMap);
		//Load GRID answers to TABLE
		_LoadAnswersToTable(form, qIdsMap, req);
		
		//Disble NotificationButton if a notification exists and vice versa
		if(proxyVO.getTheNotificationVOCollection() != null && proxyVO.getTheNotificationVOCollection().size() > 0) {
			form.getAttributeMap().put("disabled","disabled");
			form.getAttributeMap().put("NotificationExists","true");
		} else {
			form.getAttributeMap().remove("disabled");
			form.getAttributeMap().put("NotificationExists","false");
		}
		
		  form.getAttributeMap().put("SUM100_STATE",PropertyUtil.getInstance().getNBS_STATE_CODE());

		  _sortResults(form, req);
		  
		//Update formFields based on actionMode (SUM113,SUM115,SUM116 needs to be enabled in CREATE mode, not in EDIT mode)
		_updateFormFields(form);		
	}
	
	/**
	 * 
	 * @param proxyVO
	 * @param form
	 */
	private static void setPhcAnswersForViewEdit(SummaryReportProxyVO proxyVO, AggregateSummaryForm form) {
		PublicHealthCaseDT dt = proxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT();
		form.setAnswer("SUM106", getVal(dt.getCd()));
		if(form.getActionMode() != null && form.getActionMode().equalsIgnoreCase(NEDSSConstants.VIEW))
			form.setAnswer("SUM102", AggregateSearchUtil.formatMMWrWeek(dt, form));
		else
			form.setAnswer("SUM102", getVal(dt.getMmwrWeek()));
		
		form.setAnswer("SUM101", getVal(dt.getMmwrYear()));
		form.setAnswer("SUM100", getVal(dt.getRptCntyCd()));
		form.setAnswer("SUM105", getVal(dt.getTxt()));
		//form.setAnswer("SUM113", StringUtils.formatDate(dt.getRptFormCmpltTime()));
		//form.setAnswer("SUM115", getVal(dt.getCountIntervalCd()));
		
	}
	
	private static SummaryReportProxyVO getProxyFromPamEJB(Long phcUid, HttpServletRequest request)  {
		
		MainSessionCommand msCommand = null;
		SummaryReportProxyVO proxyVO = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getAggregateSummary";
			Object[] oParams = { phcUid };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {				
				proxyVO = (SummaryReportProxyVO) resultUIDArr.get(0);
				logger.info("Get AggregateSummary worked!!! publicHealthCaseUid = "+ proxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR calling mainsession control", e);
		}

		return proxyVO;
		
		
	}
	/**
	 * 
	 * @param notificationProxyVO
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
    private static Long sendProxyToPamEJB(AggregateSummaryForm form, SummaryReportProxyVO proxyVO, HttpServletRequest request)  {

		MainSessionCommand msCommand = null;
		Long phcUid = null;

		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "setAggregateSummary";
			Object[] oParams = { proxyVO };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				logger.info("Create AggregateSummary worked!!! publicHealthCaseUid = "+ resultUIDArr.get(0));
				phcUid = (Long) resultUIDArr.get(0);
			}

		} catch (NEDSSAppException e) {
			e.printStackTrace();
			if(e.toString().indexOf("SummaryReport") != -1) {
				logger.error("Duplicate Entry Found while Creating/Updating AggregateSummary: ", e);
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("aggregateReport.duplcateReport"));
				request.setAttribute("aggregateReport_errorMessages", messages);				
			} else {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("aggregateReport.saveError", e.getMessage()));
				request.setAttribute("aggregateReport_errorMessages", messages);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR calling mainsession control", e);
		}

		return phcUid;
	}	


    
	private static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}	
	
	/**
	 * Creates NbsCaseAnswers for regular answers a.w.a Counts Table Answers
	 * @param proxyVO
	 * @param form
	 * @param userId
	 */
	private static void setAnswersForCreateEdit(SummaryReportProxyVO proxyVO, AggregateSummaryForm form, String userId) {
		Collection<Object>  answerColl = new ArrayList<Object> ();
		Map<Object,Object> answerMap = form.getAnswerMap();
		Set<Object> keySet = answerMap.keySet();
		if(!keySet.isEmpty()) {
			Iterator<Object> iter = keySet.iterator();
			String answer = null;
			while(iter.hasNext()) {
				String questionId = String.valueOf(iter.next());
				String qIdentifier = ""; // for Summary
				answer = getVal(answerMap.get(questionId));
				
				if( answer!= "" && answer.trim().length() > 0) {

					NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
					answerDT.setSeqNbr(new Integer(0));
					answerDT.setAddTime(new Timestamp(new Date().getTime()));
					answerDT.setAddUserId(Long.valueOf(userId));
					if(form.getActionMode().equalsIgnoreCase(NEDSSConstants.EDIT))
						answerDT.setLastChgUserId(Long.valueOf(userId));
					answerDT.setAnswerTxt(answer);
					//see if questionId has an "_"
					if(questionId.indexOf("_") != -1) {
						qIdentifier = questionId.substring(0, questionId.indexOf("_"));
					}
					
					if(questionMap.get(questionId) != null || questionMap.get(qIdentifier) != null) {
						//Format of SummaryData Table elements' questionId is: SUMXXX_<tableMetaDataUid> ex: SUM999_3
						NbsQuestionMetadata qMetadata = null;
						if(questionId.indexOf("_") != -1) {
							Long tableMetadataUid = Long.valueOf(questionId.substring(questionId.indexOf("_")+1));							
							qMetadata = (NbsQuestionMetadata)questionMap.get(qIdentifier);
							//also, a special attribute called nbsTableMetaDataUid<Long> (newly added for R2.2) to answerDT							
							answerDT.setNbsTableMetadataUid(tableMetadataUid);
						} else {
							qMetadata = (NbsQuestionMetadata)questionMap.get(questionId);	
						}
						if(qMetadata != null) {
							answerDT.setNbsQuestionUid(qMetadata.getNbsQuestionUid());
							answerDT.setNbsQuestionVersionCtrlNbr(qMetadata.getQuestionVersionNbr());
							//Add only those answers to AnswerColl whose dataLocation is  NBS_Case_Answer.answer_txt
							if(qMetadata.getNbsTableUid() != null || (qMetadata.getDataLocation() != null && qMetadata.getDataLocation().equalsIgnoreCase("NBS_Case_Answer.answer_txt")))
								answerColl.add(answerDT);
						}
					} else {
						logger.error("QuestionId: " + questionId  + " is not found in Case Answers");
					}
				}

			}
		}	
		proxyVO.getThePublicHealthCaseVO().setNbsAnswerCollection(answerColl);
		
	}
	
	/**
	 * Sets the appropriate formCd based on ConditionCd (retrived from Popup) and loads Metadata accordingly
	 * @param conditionCd
	 * @param form
	 */
	public static void loadSearchSpecificMetaData(String conditionCd, AggregateSummaryForm form) {
		//Clear Selections
		form.clearSelections();
		
		if(conditionCd.equals(NEDSSConstants.NOVEL_INFLUENZA_FLU)) {
			form.setFormCd(NBSConstantUtil.INV_FORM_FLU);
			form.setConditionCd(conditionCd);
			//Also load the conditionCd to searchMap as this will be prepopulated on the UI
			form.getSearchMap().put("SUM106", conditionCd);
			form.getSearchMap().put("SUM101", String.valueOf(new GregorianCalendar().get(Calendar.YEAR)));			
			form.getSearchMap().put("SUM100", "STWIDE"); //For 3.0 this will be hardcoded (any Code??)
			form.getSearchMap().put("SUM116", NEDSSConstants.CASE_CLASS_CODE_CONFIRMED); //For 3.0 this will default to CONFIRMED
		}
		//Loads the questions based on FormCd
		loadQuestions(form.getFormCd());
		_setAggregateTables(form);
		
		Map<Object,Object> formFieldMap = new HashMap<Object,Object>();
		if (questionMap != null && questionMap.size() > 0) {
			Collection<?>  qColl = questionMap.values();
			Iterator<?> ite = qColl.iterator();
			while (ite.hasNext()) {
				FormField fField = new FormField();
				Object obj = ite.next();
				if(obj instanceof NbsQuestionMetadata) {
					NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) obj;
					fField.setFieldId(qMetadata.getQuestionIdentifier());
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
							fField.getState().setRequiredIndClass(RuleConstants.REQUIRED_FIELD_CLASS);
						}else// if(qMetadata.getRequiredInd().equals("F")){
						{
							fField.getState().setRequired(false);
							fField.getState().setRequiredIndClass(RuleConstants.NOT_REQUIRED_FIELD_CLASS);
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
		}
		if(formFieldMap.size() > 0) {
			form.setFormFieldMap(formFieldMap);
		}
	}
	
 
	 private static void setAnswersForViewEdit(AggregateSummaryForm form, Map<Object,Object> answerMap) {
		 
		  Map<Object,Object> returnMap = new TreeMap<Object,Object>();
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
			  form.getAnswerMap().putAll(returnMap);
		  }
	 }
	 
	 
	 /**
	  * Returns a Map<Object,Object> in format KEY<QuestionUid>, VALUE<QuestionIdentifier>
	  * @param answerColl
	  * @return
	  */
	 private static Map<Object,Object> updateMapWithQIds(Collection<Object>  answerColl) {
		  Map<Object,Object> returnMap = new HashMap<Object,Object>();
		  if(answerColl != null && answerColl.size() > 0) {
			 Iterator<Object>  iter = answerColl.iterator();
			  while(iter.hasNext()) {
				  NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT) iter.next();
				  Long qUid = answerDT.getNbsQuestionUid();
				  //For Summary Table Answers see if tableMetadataUid <> NULL and update in format SUMXXX_1
				  if(answerDT.getNbsTableMetadataUid() != null) {
					  String id = questionKeyMap.get(qUid) + "_" + answerDT.getNbsTableMetadataUid(); 
					  returnMap.put(id, answerDT.getAnswerTxt());
				  } else {
					  returnMap.put(questionKeyMap.get(qUid), answerDT);  
				  }
				  
			  }
		  }
	
		  return returnMap;
	 }
	 
	 /**
	  * Map<Object,Object> <SUMXXX_tableMetadataUid, NbsCaseAnswerDT> answerMap
	  * @param form
	  * @param answerMap
	  */
	 public static void _LoadAnswersToTable(AggregateSummaryForm form, Map<Object,Object> answerMap, HttpServletRequest req) {
		 
			Iterator<Object> it = form.getCategoryTableList().iterator();
			while(it.hasNext()) {
				CategoryTable table = (CategoryTable) it.next();
				 Collection<Object>  records = table.getRecords();
				Iterator<Object>  iter = records.iterator();
				 while(iter.hasNext()) {
					 ArrayList<?> list = (ArrayList<?> ) iter.next();
					Iterator<?>  iter1 = list.iterator();
					 while(iter1.hasNext()) {
						 AggregateSummaryDT dt = (AggregateSummaryDT) iter1.next();
						 String key = dt.getQuestionIdentifier() + "_" + dt.getNbsTableMetaDataUid();
						 String val = getVal(answerMap.get(key)); 
						 if(!val.equals("")) {
							 dt.setAnswerTxt(val);
						 } else {
							 dt.setAnswerTxt("");
						 }
					 }
				 }
			}
		req.setAttribute("categoryTables", form.getCategoryTableList());
	 }
	 
		/**
		 * 	On Edit Submit, NBS Answers should be marked as itNew, itDirty or itDelete based on the changes...
		 * @param form
		 * @param proxyVO
		 */
		private static void updateNbsAnswersForDirty(AggregateSummaryForm form, SummaryReportProxyVO proxyVO ) {

			Map<Object,Object> oldAnswers = updateMapWithTabMdataUids(form.getOldProxyVO().getThePublicHealthCaseVO().getNbsAnswerCollection());
			Collection<Object>  newAnswers = proxyVO.getThePublicHealthCaseVO().getNbsAnswerCollection();
			//Iterate through the newAnswers and mark it accordingly
			//1. If present in new and not present in old - mark it NEW
			//2. If present in both - mark it DIRTY
			//3. If present in old and not present in new - mark it DELETE
			Iterator<Object> iter = newAnswers.iterator();
			while(iter.hasNext()) {
				Object obj = iter.next();
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT) obj;
					Long tabMetaUid = (Long) (dt.getNbsTableMetadataUid() == null ? dt.getNbsQuestionUid() : dt.getNbsTableMetadataUid());
					if(oldAnswers.get(tabMetaUid) == null) {
						dt.setItNew(true);
						dt.setItDirty(false);
						dt.setItDelete(false);
					} else {
						NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldAnswers.get(tabMetaUid);
						dt.setItDirty(true);
						dt.setItNew(false);
						dt.setItDelete(false);
						dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
						//remove it from oldMap so that the leftovers in oldMap are DELETE candidates
						oldAnswers.remove(tabMetaUid);
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
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT)oldObj;
					dt.setItDelete(true);
					dt.setItNew(false);
					dt.setItDirty(false);
				}
			}
			//Add all from old to the new
			proxyVO.getThePublicHealthCaseVO().getNbsAnswerCollection().addAll(oldAnswers.values());
		}

		private static void markAnswerListForDelete(ArrayList<?> list) {
			Iterator<?> iter = list.iterator();
			while(iter.hasNext()) {
				NbsCaseAnswerDT dt = (NbsCaseAnswerDT)iter.next();
				dt.setItDelete(true);
				dt.setItNew(false);
				dt.setItDirty(false);
			}
		}
		/**
		 * Returns a Map<Object,Object> in format KEY<NbsTableMetadataUid>, VALUE<NbsCaseAnswerDT>
		 * @param answerColl
		 * @return
		 */
		private static Map<Object,Object> updateMapWithTabMdataUids(Collection<Object>  answerColl) {
			  Map<Object,Object> returnMap = new HashMap<Object,Object>();
			  if(answerColl != null && answerColl.size() > 0) {
				 Iterator<Object>  iter = answerColl.iterator();
				  while(iter.hasNext()) {
					  NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT) iter.next();
					  Long tmUid = answerDT.getNbsTableMetadataUid();
					  //This step is REQUIRED to handle NON-TABLE specific Answers (for which TableMetadataUid is NULL) 
					  if(tmUid == null) {
						  tmUid = answerDT.getNbsQuestionUid();
					  }
					  //For Summary Table Answers see if tableMetadataUid <> NULL and update in format SUMXXX_1
						  returnMap.put(tmUid, answerDT);  
				  }
			  }
		
			  return returnMap;
		 }
	 

		
	 	private static void _sortResults(AggregateSummaryForm form, HttpServletRequest req) {
			  //Sorting needs to be taken care
			boolean existing = req.getParameter("existing") == null ? false : true;
			AggregateSearchUtil.sortResults(form, form.getManageList(), existing, req);	 		
	 	}
	
	 	private static void _updatePhcWithNotifLastChgTime(SummaryReportProxyVO proxyVO) {
	 		
	 		PublicHealthCaseDT phcDT = proxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT();	 		
	 		if(proxyVO.getTheNotificationVOCollection() != null && proxyVO.getTheNotificationVOCollection().size() > 0) {
				Iterator<Object> iter = proxyVO.getTheNotificationVOCollection().iterator();
				NotificationVO nVO = (NotificationVO) iter.next();
				NotificationDT dt = nVO.getTheNotificationDT();
				String endDateofWeek="";
				//update phc with Notif's LastChange Time
				//phcDT.setRptFormCmpltTime(dt.getLastChgTime());			
				
				GregorianCalendar cal = new GregorianCalendar();		       
				ArrayList<?> weekList =  getMmwrWeeks(String.valueOf(new Integer(phcDT.getMmwrYear()).intValue()));
				Iterator<?> it = weekList.iterator();
				int count = 0;
				while(it.hasNext()){					
					DropDownCodeDT  drpdownDT = (DropDownCodeDT)it.next();	
					if(count == 0){
						count++;
						continue;
					}
					String strmmwr = drpdownDT.getValue();
					if(new Integer(strmmwr.substring(0, 2)).intValue() == new Integer(phcDT.getMmwrWeek()).intValue()){
						 endDateofWeek = strmmwr.substring(strmmwr.indexOf("-"), strmmwr.indexOf(")") );
						 
						 try{
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								java.util.Date parsedDate = dateFormat.parse(endDateofWeek.substring(1).trim());
								java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
					 			phcDT.setRptFormCmpltTime(timestamp);				
								phcDT.setRptToStateTime(dt.getLastChgTime());
								}catch(java.text.ParseException e){
									logger.error("Error while parsing date "+endDateofWeek);
									
								}
						break;
					}
					
				}
				
	 			
	 		}	 		
	 		
	 	}
	 	
	 	public static ArrayList<Object> getMmwrWeeks(String pYear) {
	 		Map<Object,Object> mmwrWeekMap = new HashMap<Object,Object>();
	 		ArrayList<Object> mmwrWeekList = new ArrayList<Object> ();	
	 		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			try {
				if(mmwrWeekMap.get(pYear) == null) {
					
					DropDownCodeDT dt = new DropDownCodeDT();
					dt.setKey(""); dt.setValue("");			
					mmwrWeekList.add(dt);
					
				    //  Define constants.
				    int SECOND = 1000;
				    int MINUTE = 60 * SECOND;
				    int HOUR = 60 * MINUTE;
				    int DAY = 24 * HOUR;
				    int SIX_DAYS = 6 * DAY;
				    int WEEK = 7 * DAY;
				    //  Convert to date object.
				    Date varDate = formatter.parse("12/31/" + pYear);
				    Calendar cal = Calendar.getInstance();
					cal.setTime(varDate);		    
				    long varTime = varDate.getTime();
				    long varDay = cal.get(Calendar.DAY_OF_WEEK);

				    //  Get January 1st of given year.
				    Date varJan1Date = formatter.parse("01/01/" + cal.get(Calendar.YEAR));
				    Calendar calJan1 = Calendar.getInstance();
				    calJan1.setTime(varJan1Date);
				    int varJan1Day = calJan1.get(Calendar.DAY_OF_WEEK);
				    long varJan1Time = calJan1.getTimeInMillis();
				    //  Create temp variables.
				    long t = varJan1Time;
				    long tSAT = 0;
				    Date d = null;
				    int h = 0;
				    String s = "";
				    long wTemp = 0;
				    //  MMWR Year.
				    int y = calJan1.get(Calendar.YEAR);
				    //  MMWR Week.
				    int w = 0;
				    //  Find first day of MMWR Year.
				   
				    if(varJan1Day <= 4)
				    {
				        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
				        t -= ((varJan1Day-1) * DAY);
				    } else
				    {
				        //  If THU, FRI, or SAT, go forward to nearest Sunday.
				        t += ((7 - (varJan1Day-1)) * DAY);
				    }
				        //  Loop through each week until we reach the given date.
				    while(t <= varTime) {
				        //  Increment the week counter.
				        w++;
				        //  Adjust for daylight savings time as necessary.
				        d = new Date(t);
				        Calendar cal1 = Calendar.getInstance();
				        s = "";
				        cal1.setTime(d);
				        h = cal1.get(Calendar.HOUR);
				        if(h == 1)
				        {
				            t -= HOUR;
				        }
				        if(h == 23 || h == 11)
				        {
				            t += HOUR;
				        }
				        if(w < 10)
				        {
				            s += "0";
				        }
				        s += w + " (" + formatter.format(d);
				        tSAT = t + SIX_DAYS;
				        //  Adjust for daylight savings time as necessary.
				        d = new Date(tSAT);
				        cal1.setTime(d);
				        h = cal1.get(Calendar.HOUR);
				        if(h == 1)
				        {
				            tSAT -= HOUR;
				        }
				        if(h == 23 || h == 11)
				        {
				            tSAT += HOUR;
				        }
				        d = new Date(tSAT);
				        s += " - " + formatter.format(d) + ")";
				        //  Move on to the next week.
				        t += WEEK;
				        //  Adjust for daylight savings time as necessary.
				        d = new Date(t);
				        cal1 = Calendar.getInstance();
				        cal1.setTime(d);
				        h = cal1.get(Calendar.HOUR);	            
				        if(h == 1)
				        {
				            t -= HOUR;
				        }
				        if(h == 23 || h == 11)
				        {
				            t += HOUR;
				        }
				        //  Rule #4.
				        if( (w == 53) && (varDay < 3) )
				        {
				            break;
				        }
				        //  Zero pad left.
				        wTemp = (w < 10) ? (Integer.valueOf("0").intValue() + w) : w;
				        dt = new DropDownCodeDT();
				        dt.setKey(String.valueOf(w)); dt.setValue(s);
				        mmwrWeekList.add(dt);
				        
				        mmwrWeekMap.put(pYear, mmwrWeekList);
				    }
				    
				} else {
					return (ArrayList<Object> ) mmwrWeekMap.get(pYear);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return mmwrWeekList;
		}
}
	

