package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util;

import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DsmLogSearchDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DsmActivityLogUtil {

	static final LogUtils logger = new LogUtils(
			DsmActivityLogUtil.class.getName());
	QueueUtil queueUtil = new QueueUtil();

	public static ArrayList<Object> retrieveAllDsmActivityLogs(
			HttpSession session) throws Exception {
		ArrayList<Object> activityLogList = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getAllDsmActivityLogCollection";
			// Object[] oParams = new Object[] {dt};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			arr = msCommand.processRequest(sBeanJndiName, sMethod, null);
			activityLogList = (ArrayList<Object>) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in retrieveAllActivityLogs in Action Util: ",
					ex);
			throw new Exception(ex.toString());

		}
		return activityLogList;
	}
	
	public static ArrayList<Object> retrieveDsmActivityLogs(DsmLogSearchDT dsmLogSearchDT,
			HttpSession session) throws Exception {
		ArrayList<Object> activityLogList = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getDsmActivityLogCollection";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[]  oParams = {dsmLogSearchDT};
			arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			activityLogList = (ArrayList<Object>) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in retrieveDsmActivityLogs in Action Util: ",
					ex);
			throw new Exception(ex.toString());

		}
		return activityLogList;
	}
	
	public static EDXActivityLogDT retrieveLatestDsmActivityLog(String algorithmName,
			HttpSession session) throws Exception {
		EDXActivityLogDT activityLogDT = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getLatestDsmActivityLog";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[]  oParams = {algorithmName};
			arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			activityLogDT = (EDXActivityLogDT) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in retrieveLatestDsmActivityLog in Action Util: ",
					ex);
			throw new Exception(ex.toString());

		}
		return activityLogDT;
	}
	
	public static ArrayList<Object> retrieveActivityLogDetailCollection(
			Long activityUid, HttpSession session) throws Exception {
		ArrayList<Object> activityDetailLogList = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getDsmActivityLogDetailCollection";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[]  oParams = {activityUid};
			arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			activityDetailLogList = (ArrayList<Object>) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in retrieveActivityLogDetailCollection in Action Util: ",
					ex);
			throw new Exception(ex.toString());

		}
		return activityDetailLogList;
	}
	
	public static void updateToHtmlFormat(ArrayList<?> logDetailList)
	{
		if (logDetailList != null && logDetailList.size() != 0)
		{
			for (int i = 0; i < logDetailList.size(); i++)
			{
				EDXActivityDetailLogDT detailLog = (EDXActivityDetailLogDT) logDetailList.get(i);
				String comment = detailLog.getComment();
				String logType = detailLog.getLogType();
				if(comment != null && !comment.equals(""))
					detailLog.setCommentHtml(htmlifyString(comment));
				if(logType!=null && logType.equalsIgnoreCase("Failure"))
				{
					String statusHtml = "<FONT COLOR='CC0000'>" + logType + "</FONT>";
					detailLog.setLogTypeHtml(statusHtml);
				}else{
					detailLog.setLogTypeHtml(logType);
				}
			}
		}
	}
	
	private static String htmlifyString(String inputString) {
		inputString = inputString.replaceAll("\n", "<br>");
		inputString = inputString.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        return inputString;
  }


	public static void updateActivityLogLinks(ArrayList<Object> activityLogList,
			HttpServletRequest request) throws Exception {
		try {

			if (activityLogList != null && activityLogList.size() != 0) {
				for (int i = 0; i < activityLogList.size(); i++) {
					EDXActivityLogDT dt = (EDXActivityLogDT) activityLogList.get(i);

					HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
					String edxActivityLogUid = null;
					edxActivityLogUid = dt.getEdxActivityLogUid().toString();
					String algorithmName = dt.getAlgorithmName();
					String actionDescription = dt.getAlgorithmAction();
					Long eventId = dt.getTargetUid();
					String businessObjLocalId = dt.getBusinessObjLocalId();
					String messageId = dt.getMessageId();
					String sourceNm = dt.getSrcName();
					String entityNm = dt.getEntityNm();
					String accessionNo = dt.getAccessionNbr();
					if(eventId != null && eventId.longValue()<=0)
					{
						eventId = null;
						dt.setTargetUid(eventId);
					}
					
					parameterMap.put("edxActivityLogUid", edxActivityLogUid);
					String recStatusTime = formatDate(dt.getRecordStatusTime(),
							"MM/dd/yyyy HH:mm:ss.S");
					String algorithmNameStr = algorithmName;
					String actionDescriptionStr = actionDescription;
					String eventIdStr = "" + eventId;
					String messageIdStr = "" + messageId;
					String sourceNmStr = "" + sourceNm;
					String entityNmStr = "" + entityNm;
					String accessionNoStr = "" + accessionNo;
					String businessObjLocalIdStr = "" + businessObjLocalId;
					if(algorithmName == null)
						algorithmNameStr = "";
					if(actionDescription == null)
						actionDescriptionStr = "";
					if(eventId == null)
						eventIdStr = "";
					if(messageId == null)
						messageIdStr = "";
					if(sourceNm == null)
						sourceNmStr = "";
					if(entityNm == null)
						entityNmStr = "";
					if(accessionNo == null)
						accessionNoStr = "";
					if(businessObjLocalId == null)
						businessObjLocalIdStr = "";
					String url = "/nbs/LoadDSMActivityLog.do?method=viewActivityLogDetails&edxActivityLogUid="
							+ edxActivityLogUid + "&algorithmName1=" + algorithmNameStr
							+ "&action1=" + actionDescriptionStr + "&eventId1=" + eventIdStr + "&processedTime1=" + recStatusTime + "&messageId1=" + messageIdStr
							+  "&srcName1=" + sourceNmStr +  "&entityNm1=" + entityNmStr +  "&accessionNbr1=" + accessionNoStr + "&businessObjLocalId1=" + businessObjLocalIdStr;
					String viewUrl = "";
					url = url.replaceAll("'", "\\\\'");		// Defect #4942 fix
					if (dt.getImpExpIndCd() != null
							&& dt.getImpExpIndCd().equals(
									NEDSSConstants.IMPORT_CD))
						viewUrl = "<a href=\"javascript:viewActitivityLogDetails('"
								+ url + "');\"> " + recStatusTime + " </a>";
					else
						viewUrl = recStatusTime;
					dt.setViewLink(viewUrl);
					if(dt.getRecordStatusCd()!=null && dt.getRecordStatusCd().equalsIgnoreCase("Failure"))
					{
						String statusHtml = "<FONT COLOR='CC0000'>" + dt.getRecordStatusCd() + "</FONT>";
						dt.setRecordStatusCdHtml(statusHtml);
					}else
						dt.setRecordStatusCdHtml(dt.getRecordStatusCd());
					dt.setExceptionShort(dt.getException());
				}
			}
		} catch (Exception ex) {
			logger.fatal("Error in updateActivityLogLinks in Action Util: ", ex);
			throw new Exception(ex.toString());
		}
	}

	public static String formatDate(java.sql.Timestamp timestamp, String format) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timestamp != null) {
			date = new Date(timestamp.getTime());
		}
		if (date == null) {
			return "";
		} else {
			return formatter.format(date);
		}
	}

	public static String getSortCriteriaForActivityLog(String sortOrder,
			String methodName) throws Exception {
		try {
			String sortOrdrStr = null;
			if (methodName != null) {
				if (methodName.equals("getRecordStatusTime"))
					sortOrdrStr = "Processed Time";
				else if (methodName.equals("getTargetUid"))
					sortOrdrStr = "Event ID";
				else if (methodName.equals("getAlgorithmAction"))
					sortOrdrStr = "Action";
				else if (methodName.equals("getAlgorithmName"))
					sortOrdrStr = "Algorithm Name";
				else if (methodName.equals("getRecordStatusCd"))
					sortOrdrStr = "Status";
				else if (methodName.equals("getException"))
					sortOrdrStr = "Exception Text";
				else if (methodName.equals("getMessageId"))
					sortOrdrStr = "Message ID";
				else if (methodName.equals("getSrcName"))
					sortOrdrStr = "Reporting Facility";
				else if (methodName.equals("getEntityNm"))
					sortOrdrStr = "Patient Name";
				else if (methodName.equals("getAccessionNbr"))
					sortOrdrStr = "Accession#";
				else if (methodName.equals("getBusinessObjLocalId"))
					sortOrdrStr = "Observation ID";

			} else {
				sortOrdrStr = "Processed Time";
			}

			if (sortOrder == null
					|| (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr + " in ascending order ";
			else if (sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr + " in descending order ";

			return sortOrdrStr;
		} catch (Exception ex) {
			logger.fatal(
					"Error in getSortCriteriaForActivityLog in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

	}

	public static ArrayList<Object> getActivityLogCollection(HttpSession session)
			throws Exception {

		ArrayList<Object> activityLogList = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getActivityLogCollection";
			// Object[] oParams = new Object[] {dt};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			arr = msCommand.processRequest(sBeanJndiName, sMethod, null);
			activityLogList = (ArrayList<Object>) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in getTemplateLib in Action Util: ", ex);
			throw new Exception(ex.toString());

		}
		return activityLogList;
	}

	public ArrayList<Object> getLogActionNmDropDowns(
			Collection<Object> activityLogColl) throws Exception {
		try {
			Map<Object, Object> actionNmMap = new HashMap<Object, Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT) iter
							.next();
					String actionId = edxActivityLogDT.getActionId();
					String actionDescription = CachedDropDowns.getCodeDescTxtForCd(actionId, "NBS_EVENT_ACTION");
					edxActivityLogDT.setAlgorithmAction(actionDescription);
					if (edxActivityLogDT.getAlgorithmAction() != null && !edxActivityLogDT.getAlgorithmAction().trim()
							.equals("")) {
						actionNmMap.put(edxActivityLogDT.getAlgorithmAction(),
								edxActivityLogDT.getAlgorithmAction());
					}
					if (edxActivityLogDT.getAlgorithmAction() == null
							|| edxActivityLogDT.getAlgorithmAction().trim()
									.equals("")) {
						actionNmMap.put(NEDSSConstants.BLANK_KEY,
								NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(actionNmMap);
		} catch (Exception ex) {
			logger.fatal("Error in getTemplateNmDropDowns in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

	}
	
	public ArrayList<Object> getLogAlgorithmNmDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> algorithmNameMap = new HashMap<Object,Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
					if (edxActivityLogDT.getAlgorithmName()!= null) {
						
						
						algorithmNameMap.put(edxActivityLogDT.getAlgorithmName(),edxActivityLogDT.getAlgorithmName());
					}
					if(edxActivityLogDT.getAlgorithmName() == null || edxActivityLogDT.getAlgorithmName().trim().equals("")){
						algorithmNameMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
		}
		return queueUtil.getUniqueValueFromMap(algorithmNameMap);
		}catch (Exception ex) {
			logger.fatal("Error in getSourceNmDropDowns in Action Util: ", ex);
			throw new Exception(ex.toString());
		
		}
}
	
	public ArrayList<Object> getLogStatusDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
					if (edxActivityLogDT.getRecordStatusCd()!= null) {
						
						
						statusMap.put(edxActivityLogDT.getRecordStatusCd(),edxActivityLogDT.getRecordStatusCd());
					}
					if(edxActivityLogDT.getRecordStatusCd() == null || edxActivityLogDT.getRecordStatusCd().trim().equals("")){
						statusMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
					}
				}
		}
		return queueUtil.getUniqueValueFromMap(statusMap);
		}catch (Exception ex) {
			logger.fatal("Error in getLogStatusDropDowns in Action Util: ", ex);
			throw new Exception(ex.toString());
		
		}
}
	
	public ArrayList<Object> getEventIDDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> eventTypeMap = new HashMap<Object,Object>();
//			if (activityLogColl != null) {
//				java.util.Iterator<Object> iter = activityLogColl.iterator();
//				while (iter.hasNext()) {
//					EDXActivityLogDT edxActivityLogDT = (EDXActivityLogDT)iter.next();
//					if (edxActivityLogDT.getDocType()!= null) {
//						
//						
//						eventTypeMap.put(edxActivityLogDT.getDocType(),edxActivityLogDT.getDocType());
//					}
//					if(edxActivityLogDT.getDocType() == null || edxActivityLogDT.getDocType().trim().equals("")){
//						eventTypeMap.put(NEDSSConstants.DATE_BLANK_KEY, NEDSSConstants.BLANK_VALUE);
//					}
//				}
//		}
		return queueUtil.getUniqueValueFromMap(eventTypeMap);
		}catch (Exception ex) {
			logger.fatal("Error in getTypeDropDowns in Action Util: ", ex);
			throw new Exception(ex.toString());
		
		}
	}
	
	public ArrayList<Object> getExceptionTextDropDowns(Collection<Object>  activityLogColl) throws Exception {
		try{
			Map<Object, Object> exceptionTextMap = new HashMap<Object,Object>();
		return queueUtil.getUniqueValueFromMap(exceptionTextMap);
		}catch (Exception ex) {
			logger.fatal("Error in getExceptionTextDropDowns in Action Util: ", ex);
			throw new Exception(ex.toString());
		
		}
	}
}
