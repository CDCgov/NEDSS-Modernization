package gov.cdc.nedss.webapp.nbs.action.epilink;

import gov.cdc.nedss.proxy.ejb.epilink.dt.EpilinkDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EpilinkLogUtil {

	static final LogUtils logger = new LogUtils(EpilinkLogUtil.class.getName());
	
	QueueUtil queueUtil = new QueueUtil();
	

	public static String getSortCriteriaForActivityLog(String sortOrder,
			String methodName) throws Exception {
		try {
			String sortOrdrStr = null;

			if (methodName != null) {
				if (methodName.equals("getProcessedDate"))
					sortOrdrStr = "Processed Date";
				else if (methodName.equals("getUserName"))
					sortOrdrStr = "User Name";
				else if (methodName.equals("getOldEpilink"))
					sortOrdrStr = "Old Epi-Link Id";
				else if (methodName.equals("getNewEpilink"))
					sortOrdrStr = "New Epi-Link Id";
				
			} else {
				sortOrdrStr = "Processed Date";
			}

			if (sortOrder == null
					|| (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr + " in ascending order ";
			else if (sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr + " in descending order ";

			return sortOrdrStr;
		} catch (Exception ex) {
			logger.fatal(
					"Error in EpilinkLogUtil.getSortCriteriaForActivityLog in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

	}

	
	public static ArrayList<Object> retrieveAllEpilinkActivityLogs(Date fromDate, Date toDate,
			HttpSession session) throws Exception {
		ArrayList<Object> activityLogList = null;
		MainSessionCommand msCommand = null;
		ArrayList<?> arr = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getActivityLogColl";
			 Object[] oParams = new Object[] {fromDate,toDate};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			activityLogList = (ArrayList<Object>) arr.get(0);
		} catch (Exception ex) {
			logger.fatal("Error in retrieveAllActivityLogs in EpilinkLogUtil: ",
					ex);
			throw new Exception(ex.toString());

		}
		return activityLogList;
	}
	
	
	public  ArrayList<Object> getProcessedDateDropDowns(
			Collection<Object> activityLogColl) throws Exception {
		try {
			Map<Object, Object> processedDateMap = new HashMap<Object, Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EpilinkDT epilinkdt = (EpilinkDT) iter
							.next();
					Date processedDate = epilinkdt.getProcessedDate();
					if (processedDate != null ) {
						processedDateMap.put(DateFormat.getDateInstance(DateFormat.MEDIUM).format(processedDate),
								DateFormat.getDateInstance(DateFormat.MEDIUM).format(processedDate));
					}
					if (processedDate == null) {
						processedDateMap.put(NEDSSConstants.BLANK_KEY,
								NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(processedDateMap);
		} catch (Exception ex) {
			logger.fatal("Error in EpilinkLogUtil.getProcessedDateDropDowns : ", ex);
			throw new Exception(ex.toString());

		}

	}
	
	
	public  ArrayList<Object> getOldEpilinkDropDowns(
			Collection<Object> activityLogColl) throws Exception {
		try {
			Map<Object, Object> oldepilinkNmMap = new HashMap<Object, Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EpilinkDT epilinkDT = (EpilinkDT) iter
							.next();
					String oldEpilink = epilinkDT.getOldEpilinkId();
					if (oldEpilink != null && !oldEpilink.trim()
							.equals("")) {
						oldepilinkNmMap.put(oldEpilink,
								oldEpilink);
					}
					if (oldEpilink == null
							|| oldEpilink.trim()
									.equals("")) {
						oldepilinkNmMap.put(NEDSSConstants.BLANK_KEY,
								NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(oldepilinkNmMap);
		} catch (Exception ex) {
			logger.fatal("Error in getOldEpilinkDropDowns in  Util: ", ex);
			throw new Exception(ex.toString());

		}

	}
	
	public ArrayList<Object> getNewEpilinkDropDowns(
			Collection<Object> activityLogColl) throws Exception {
		try {
			Map<Object, Object> newEpilinkNmMap = new HashMap<Object, Object>();
			if (activityLogColl != null) {
				java.util.Iterator<Object> iter = activityLogColl.iterator();
				while (iter.hasNext()) {
					EpilinkDT epilinkDT = (EpilinkDT) iter
							.next();
					String newEpilink = epilinkDT.getNewEpilinkId();
					if (newEpilink != null && !newEpilink.trim()
							.equals("")) {
						newEpilinkNmMap.put(newEpilink,
								newEpilink);
					}
					if (newEpilink == null
							|| newEpilink.trim()
									.equals("")) {
						newEpilinkNmMap.put(NEDSSConstants.BLANK_KEY,
								NEDSSConstants.BLANK_VALUE);
					}
				}
			}
			return queueUtil.getUniqueValueFromMap(newEpilinkNmMap);
		} catch (Exception ex) {
			logger.fatal("Error in getOldEpilinkDropDowns in  Util: ", ex);
			throw new Exception(ex.toString());

		}

	}
	
	
	
	
}
