package gov.cdc.nedss.webapp.nbs.action.exportcasenotification.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerNNDFieldsDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class CaseNotificationUtil {

	final static LogUtils logger = new LogUtils(CaseNotificationUtil.class
			.getName());

	public static ArrayList<?>  getExportAlgorithm(HttpSession session)
			throws Exception {

		ArrayList<?>  algorithmList = null;
		MainSessionCommand msCommand = null;

		try {
			String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
			String sMethod = "getExportCaseNotifAlgorithm";
			// Object[] oParams = new Object[] {dt};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?>  arr = msCommand.processRequest(sBeanJndiName, sMethod,
					null);
			algorithmList = (ArrayList<?> ) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in getExportAlgorithm in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

		return algorithmList;
	}

	public void setExportAlgorithm(HttpSession session, ExportAlgorithmDT dt)
			throws Exception {
		MainSessionCommand msCommand = null;
		try{
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
		String sMethod = "insertExportAlgorithm";
		Object[] oParams =new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?>  arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		} catch (Exception ex) {
			logger.fatal("Error in setExportAlgorithm in Action Util: ", ex);
			//throw new Exception(ex.toString());

		}

	}
	public ArrayList<Object>  getExportTriggerFieldDDs( ArrayList<?>  triggerList)
		throws Exception {
		   
	    
			ArrayList<Object>  ddList = new ArrayList<Object> ();
			if(triggerList != null){
				Iterator<?> iter = triggerList.iterator();
				while(iter.hasNext()){
					ExportTriggerNNDFieldsDT etrNNDFlds = (ExportTriggerNNDFieldsDT)iter.next();
					DropDownCodeDT ddDT = new DropDownCodeDT();
					ddDT.setKey(etrNNDFlds.getNbsquestionuid().toString());
					ddDT.setValue(etrNNDFlds.getQuestionLabel());	
					ddList.add(ddDT);
				}
			}
			
			return ddList;
   }
	
	
	public ArrayList<?> getExportTriggerFields(HttpSession session) throws Exception{
		  ArrayList<?> triggerList = new ArrayList<Object> ();
		MainSessionCommand msCommand = null;
		try{
			String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
			String sMethod = "getTriggerFields";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,null);
				triggerList = (ArrayList<?> ) arr.get(0);
			} catch (Exception ex) {
				logger.fatal("Error in getExportAlgorithm in Action Util: ", ex);
					throw new Exception(ex.toString());
			}
			
			return triggerList;
	}

	public ArrayList<Object> getReceivingSystemList(HttpSession session)
			throws Exception {
		ArrayList<Object> recFacilityList = null;
		ArrayList<?> algorithmList = null;
		Map<Object, Object> recFacMap = new HashMap<Object,Object>();
		algorithmList = getExportAlgorithm(session);
		if (algorithmList != null) {
			Iterator<?> iter = algorithmList.iterator();
			while (iter.hasNext()) {
				ExportAlgorithmDT exADT = (ExportAlgorithmDT) iter.next();
				recFacMap.put(exADT.getReceivingSystem(), exADT
						.getReceivingSystem());
			}

		}

		return null;
	}
	
	public  ExportAlgorithmDT setExportAlgorithmforCreateEdit(ExportAlgorithmDT dt, HttpSession session, String actionMode){
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		java.util.Date addDate= new java.util.Date();
		Timestamp aAddTime = new Timestamp(addDate.getTime());
		dt.setAddTime(aAddTime); 
		dt.setLastChgTime(aAddTime);
		dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
		dt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		if(actionMode != null){
			if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){
				dt.setItNew(true);
				dt.setItDirty(false);
			}else if(actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)){ 
				dt.setItNew(false);
				dt.setItDirty(true);
			}
		}		
		ArrayList<Object> triggerList = dt.getTriggerDTList();
		//just clearing to get the updated value 
		dt.setTriggerDTList(new ArrayList<Object>());
		if(triggerList != null && triggerList.size()>0){
			Iterator<Object> iter = triggerList.iterator();
			while(iter.hasNext()){
				ExportTriggerDT exTrDt = (ExportTriggerDT)iter.next();
				exTrDt.setAddTime(aAddTime);
				exTrDt.setLastChgTime(aAddTime);
				exTrDt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
				exTrDt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
				if(actionMode != null){
					if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){
						exTrDt.setItNew(true);
						exTrDt.setItDirty(false);
					}else if(actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)){ 
						exTrDt.setItNew(false);
						exTrDt.setItDirty(true);
					}
				}
				dt.getTriggerDTList().add(exTrDt);
		}
		}
		return dt;
	    }
	
	public  ExportTriggerDT setExportAlgthmTriggerforCreateEdit(ExportTriggerDT dt, HttpSession session){
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		java.util.Date addDate= new java.util.Date();
		Timestamp aAddTime = new Timestamp(addDate.getTime());
		dt.setAddTime(aAddTime); 
		dt.setLastChgTime(aAddTime);
		dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));	
		dt.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		dt.setItNew(true);
		dt.setItDirty(false);
		return dt;
	}
	
	
	
	public static void makeExportAlgLink(ExportAlgorithmDT dt, String type) throws Exception{

		HashMap<Object, Object> parameterMap = new HashMap<Object,Object>();
		String algName = URLEncoder.encode(dt.getAlgorithmName(), "ISO-8859-1");
		String algNameUid = URLEncoder.encode(dt.getExportAlgorithmUid().toString(), "ISO-8859-1");
		parameterMap.put("algName", algName);
		parameterMap.put("algNameUid", algNameUid);
		if(type.equals("VIEW")) {			
			parameterMap.put("method", "viewBatchExport");
			dt.setViewLink(buildHyperLink("ExportCaseNotification.do", parameterMap, "ExpAlg", "View"));
		} else {
			parameterMap.put("method", "editBatchExport");
			dt.setEditLink(buildHyperLink("ExportCaseNotification.do", parameterMap, "ExpAlg", "Edit"));
		}		
	}	
	
	private static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		Iterator<Object> iter = paramMap.keySet().iterator();
		try {
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = (String) paramMap.get(key);
				reqParams.append(key).append("=");
				reqParams.append(value);
				reqParams.append("&");
			}
			reqParams.deleteCharAt(reqParams.length() - 1);
			url.append("<a href='/nbs/");
			url.append(strutsAction);
			url.append(reqParams.toString());
			if(jumperName != null) {
				url.append("#").append(jumperName);
			}
			url.append("'>").append(displayNm).append("</a>");
		} catch (Exception e) {
			logger.error("Error while buildHyperLink: " + e.toString());
			e.printStackTrace();
		}
		return url.toString();
	}
	
	public ArrayList<Object> getTriggerList(Long nbsQuestionUid){

		NbsQuestionDT nbsQuestionDT =null;
		ArrayList<Object> triggerfiledList = new ArrayList<Object> ();
		try {
			NBSQuestionDAOImpl nBSQuestionDAOImpl = new NBSQuestionDAOImpl();
			nbsQuestionDT = nBSQuestionDAOImpl.findNBSQuestion(nbsQuestionUid);
			CachedDropDowns cdd = new CachedDropDowns();
			if(nbsQuestionDT != null && nbsQuestionDT.getDataType() != null){
				if(nbsQuestionDT.getDataType().equals("Text") || (nbsQuestionDT.getDataType().equals("Coded"))){
					triggerfiledList = cdd.getCodedValueForType("SEARCH_ALPHA");
				}else if(nbsQuestionDT.getDataType().equals("Date")){
					triggerfiledList = cdd.getCodedValueForType("SEARCH_DOB");
				}else if(nbsQuestionDT.getDataType().equals("Numeric")){
					triggerfiledList = CachedDropDowns.getCodedValueForType("SEARCH_NUM");
				}
			} 
		}catch (Exception ex) {
			logger.error("Exception in getTriggerList()" + ex.getMessage());
			throw new NEDSSSystemException(ex.getMessage());			
		}		

		return triggerfiledList;

	}
	
	public ArrayList<Object> getFilterSRTValues(Long nbsQuestionUid, HttpSession session) throws Exception{

		ArrayList<Object> srtValues = new ArrayList<Object> ();
		try {
			Collection<Object>  questions = getQuestionsWithSRTS(session);
			if(questions != null){
				Iterator<Object> ite = questions.iterator();
				while (ite.hasNext()) {
					ExportTriggerNNDFieldsDT questionDT = (ExportTriggerNNDFieldsDT) ite
							.next();
					if(questionDT.getNbsquestionuid().compareTo(nbsQuestionUid) == 0){
						srtValues = CachedDropDowns.getCodedValueForType(questionDT.getCodeSetName());
						break;  
					}


				}
			}
		}catch (Exception ex) {
			logger.error("Exception in getFilterSRTValues)" + ex.getMessage());
		}			

		return srtValues;
	}
	
	public ArrayList<Object> getQuestionsWithSRTS(HttpSession session) throws Exception{

		ArrayList<Object> filterSRTs = null;
		MainSessionCommand msCommand = null;
		CaseNotificationDAOImpl cnDao = new CaseNotificationDAOImpl();

		try {
			filterSRTs = (ArrayList<Object> )cnDao.getSRTCodeSets();
		} catch (Exception ex) {
			logger.fatal("Error in getQuestionsWithSRTS in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

		return filterSRTs;
	}
	
	public String getRecordSatusCodeDesc(String recordStatusCd){
		String recordStatusCdDesc = "";
		if(recordStatusCd != null){
			if(recordStatusCd.equals(NEDSSConstants.STATUS_ACTIVE))
				recordStatusCdDesc=NEDSSConstants.RECORD_STATUS_ACTIVE;
			else if(recordStatusCd.equals(NEDSSConstants.STATUS_INACTIVE))
				recordStatusCdDesc=NEDSSConstants.RECORD_STATUS_INACTIVE;
			}
		return recordStatusCdDesc;
	}
	
	public String getCaseReportDesc(String caseReport){
		String caseReportDesc = "";
		if(caseReport != null){
			if(caseReport.equals(ExportCaseNotificationConstants.CASE_REPORT_CD));
				caseReportDesc=ExportCaseNotificationConstants.CASE_REPORT_DESC;
			
			}
		return caseReportDesc;
	}

	

}
