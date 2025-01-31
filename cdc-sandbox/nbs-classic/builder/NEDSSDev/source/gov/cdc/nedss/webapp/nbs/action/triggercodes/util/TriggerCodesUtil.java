package gov.cdc.nedss.webapp.nbs.action.triggercodes.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.importandexport.util.ReceivingSystemUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

public class TriggerCodesUtil {
	final static LogUtils logger = new LogUtils(ReceivingSystemUtil.class
			.getName());
	QueueUtil queueUtil = new QueueUtil();

	
	public static ArrayList<Object> getTriggerCodesList(HttpSession session)
		throws Exception {

	ArrayList<Object> recevingSysList = null;
	MainSessionCommand msCommand = null;

		try {
			String sBeanJndiName = JNDINames.TRIGGER_CODE_EJB;
			String sMethod = "getTriggerCodes";
			// Object[] oParams = new Object[] {dt};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					null);
			recevingSysList = (ArrayList<Object> ) arr.get(0);
			 
			//recevingSysList = null;
		} catch (Exception ex) {
			logger.fatal("Error in getExportAlgorithm in Action Util: ", ex);
			throw new Exception(ex.toString());
		
	}
		
	return recevingSysList;
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
	
	
	
	public static Map<Object,Object> setReceivingSystems(HttpSession session, TriggerCodesDT dt)throws Exception {
			MainSessionCommand msCommand = null;
			Map<Object, Object> errorMap = null;
			try{
			String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
			String sMethod = "insertReceivingSystems";
			Object[] oParams =new Object[] {dt};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<Object> arr =(ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((arr != null) && (arr.size() > 0)) {
			errorMap = (HashMap<Object, Object>) arr.get(0);
				}
			} catch (Exception ex) {
				logger.error("Error in sendProxyToEJBValidate for Notifications: " + ex.toString());
				throw new Exception(ex.toString());
			}
			return errorMap;

}
	
	
	
	
	
	public static String getReceivingSystem(HttpSession session, TriggerCodesDT dt)throws Exception {
		MainSessionCommand msCommand = null;
		String uid ;
		try{
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
		String sMethod = "selectReceivingSystem";
		Object[] oParams =new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<Object> arr =(ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		
		uid = (String) arr.get(0);
		
		} catch (Exception ex) {
			logger.error("Error in sendProxyToEJBValidate for Notifications: " + ex.toString());
			throw new Exception(ex.toString());
		}
		return uid;

}
	
	
	public Map<Object,Object> updateTriggerCode(HttpSession session, TriggerCodesDT dt)throws Exception {
		MainSessionCommand msCommand = null;
		Map<Object, Object> errorMap = null;
		try{
		String sBeanJndiName = JNDINames.TRIGGER_CODE_EJB;
		String sMethod = "updateTriggerCode";
		Object[] oParams =new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if ((arr != null) && (arr.size() > 0)) {
			errorMap = (HashMap<Object, Object>) arr.get(0);
				}
		} catch (Exception ex) {
			logger.fatal("Error in setExportAlgorithm in Action Util: ", ex);
			//throw new Exception(ex.toString());
		
		}
		return errorMap;
}
	public Map<Object,Object> createTriggerCode(HttpSession session, TriggerCodesDT dt)throws Exception {
		MainSessionCommand msCommand = null;
		Map<Object, Object> errorMap = null;
		try{
		String sBeanJndiName = JNDINames.TRIGGER_CODE_EJB;
		String sMethod = "createTriggerCode";
		Object[] oParams =new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		if ((arr != null) && (arr.size() > 0)) {
			errorMap = (HashMap<Object, Object>) arr.get(0);
				}
		} catch (Exception ex) {
			logger.fatal("Error in setExportAlgorithm in Action Util: ", ex);
			//throw new Exception(ex.toString());
		
		}
		return errorMap;
}
	
	
	
	public  TriggerCodesDT setReceivingSysForCreateEdit(TriggerCodesDT dt, HttpSession session, String actionMode){
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
		return dt;
	    }
	
	/*public Collection<Object>  getFilteredReceivingSystems(Collection<Object>  receivingSysDTColl,
			Map<Object, Object> searchCriteriaMap) {
		try {
    	String[] owner = (String[]) searchCriteriaMap.get("Owner");
    	String[] sender = (String[]) searchCriteriaMap.get("Sender");
		String[] recipient = (String[]) searchCriteriaMap.get("Recipient");
		String[] transfer = (String[]) searchCriteriaMap.get("Transfer");
		String[] reportType = (String[]) searchCriteriaMap.get("ReportType");
				
				
		Map<Object, Object> ownerMap = new HashMap<Object,Object>();
		Map<Object, Object> senderMap = new HashMap<Object,Object>();
		Map<Object, Object> recipientMap = new HashMap<Object,Object>();
		Map<Object, Object> transferMap = new HashMap<Object,Object>();
		Map<Object, Object> reportTypeMap = new HashMap<Object,Object>();
		
		if (owner != null && owner.length > 0)
			ownerMap = queueUtil.getMapFromStringArray(owner);
		
		if (sender != null && sender.length > 0)
			senderMap = queueUtil.getMapFromStringArray(sender);
		if (recipient != null && recipient.length > 0)
			recipientMap = queueUtil.getMapFromStringArray(recipient);
		if (transfer != null && transfer.length >0)
			transferMap = queueUtil.getMapFromStringArray(transfer);
		if (reportType != null && reportType.length >0)
			reportTypeMap = queueUtil.getMapFromStringArray(reportType);
		
		
		if(ownerMap != null && ownerMap.size()>0)
			receivingSysDTColl = filterRecSysOwner(
					receivingSysDTColl, ownerMap);
		if (recipientMap != null && recipientMap.size()>0)
			receivingSysDTColl = filterRecSysReceipient(
					receivingSysDTColl, recipientMap);
		if (senderMap != null && senderMap.size()>0)
			receivingSysDTColl = filterRecSysSender(
					receivingSysDTColl, senderMap);
		if (transferMap != null && transferMap.size()>0)
			receivingSysDTColl = filterRecSysTransfer(
					receivingSysDTColl, transferMap);
		if (reportTypeMap != null && reportTypeMap.size()>0)
			receivingSysDTColl = filterRecSysReportType(
					receivingSysDTColl, reportTypeMap);
		} catch (Exception ex) {
			logger.error("Error in getFilteredReceivingSystems: "+ex.getMessage());
			ex.printStackTrace();
		}		
		return receivingSysDTColl;
		
	}*/
	/*
	public Collection<Object>  filterRecSysOwner(
			Collection<Object>  receivingSysDTColl, Map<Object,Object> ownerMap) {
		Collection<Object>  newOwnerColl = new ArrayList<Object> ();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReceivingSystemOwner() != null
						&& ownerMap != null
						&& ownerMap.containsKey(dt.getReceivingSystemOwner())) {
					newOwnerColl.add(dt);
				}
				if(dt.getReceivingSystemOwner() == null || dt.getReceivingSystemOwner().equals("")){
					if(ownerMap != null && ownerMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newOwnerColl.add(dt);
					}
				}

			}

		}
		} catch (Exception ex) {
			logger.error("Error in filterRecSysOwner: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return newOwnerColl;

	}*/
	/*public Collection<Object>  filterRecSysReportType(
			Collection<Object>  receivingSysDTColl, Map<Object,Object> reportTypeMap) {
		Collection<Object>  newOwnerColl = new ArrayList<Object> ();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReportType() != null
						&& reportTypeMap != null
						&& reportTypeMap.containsKey(dt.getReportType())) {
					newOwnerColl.add(dt);
				}
				if(dt.getReportType() == null || dt.getReportType().equals("")){
					if(reportTypeMap != null && reportTypeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newOwnerColl.add(dt);
					}
				}

			}

		}
		} catch (Exception ex) {
			logger.error("Error in filterRecSysReportType: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return newOwnerColl;

	}*/
	/*
	public Collection<Object>  filterRecSysSender(
			Collection<Object>  receivingSysDTColl, Map<Object,Object> senderMap) {
		Collection<Object>  newOwnerColl = new ArrayList<Object> ();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				
				if (dt.getSendingIndCd() != null
						&& senderMap != null
						&& senderMap.containsKey(dt.getSendingIndCd())) {
					newOwnerColl.add(dt);
				}
				if(dt.getSendingIndCd() == null || dt.getSendingIndCd().equals("")){
					if(senderMap != null && senderMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newOwnerColl.add(dt);
					}
				}

			}

		}
		} catch (Exception ex) {
			logger.error("Error in filterRecSysSender: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return newOwnerColl;

	}*/
	/*public Collection<Object>  filterRecSysReceipient(
			Collection<Object>  receivingSysDTColl, Map<Object,Object> recipientMap) {
		Collection<Object>  newOwnerColl = new ArrayList<Object> ();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReceivingIndCd() != null
						&& recipientMap != null
						&& recipientMap.containsKey(dt.getReceivingIndCd())) {
					newOwnerColl.add(dt);
				}
				if(dt.getReceivingIndCd() == null || dt.getReceivingIndCd().equals("")){
					if(recipientMap != null && recipientMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newOwnerColl.add(dt);
					}
				}

			}

		}
		} catch (Exception ex) {
			logger.error("Error in filterRecSysReceipient: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return newOwnerColl;

	}*/
	
	/*public Collection<Object>  filterRecSysTransfer(
			Collection<Object>  receivingSysDTColl, Map<Object,Object> transferMap) {
		Collection<Object>  newOwnerColl = new ArrayList<Object> ();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getAllowTransferIndCd() != null
						&& transferMap != null
						&& transferMap.containsKey(dt.getAllowTransferIndCd())) {
					newOwnerColl.add(dt);
					
				}
				if(dt.getAllowTransferIndCd() == null || dt.getAllowTransferIndCd().equals("")){
					if(transferMap != null && transferMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newOwnerColl.add(dt);
					}
				}

			}

		}
		} catch (Exception ex) {
			logger.error("Error in filterRecSysTransfer: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return newOwnerColl;

	}*/
	/*public ArrayList<Object> getOwnerDropDowns(Collection<Object>  receivingSysDTColl) {
		Map<Object, Object> ownerMap = new HashMap<Object,Object>();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReceivingSystemOwner()!= null) {
					ownerMap.put(dt.getReceivingSystemOwner(), dt.getReceivingSystemOwner());
							
				}
				if(dt.getReceivingSystemOwner() == null || dt.getReceivingSystemOwner().trim().equals("")){
					ownerMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}
		}
		} catch (Exception ex) {
			logger.error("Error in getOwnerDropDowns: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return queueUtil.getUniqueValueFromMap(ownerMap);
	}*/
	
	
	/*
	public ArrayList<Object> getSenderDropDowns(Collection<Object>  receivingSysDTColl) {
		Map<Object, Object> senderMap = new HashMap<Object,Object>();
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getSendingIndCd()!= null) {
					senderMap.put(dt.getSendingIndCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getSendingIndCd(),"YN"));
							
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(senderMap);
	}
	*//*
	public ArrayList<Object> getRecipientDropDowns(Collection<Object>  receivingSysDTColl) {
		Map<Object, Object> senderMap = new HashMap<Object,Object>();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReceivingIndCd()!= null) {
					senderMap.put(dt.getReceivingIndCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getReceivingIndCd(),"YN"));
							
				}
				if(dt.getReceivingIndCd() == null || dt.getReceivingIndCd().trim().equals("")){
					senderMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		} catch (Exception ex) {
			logger.error("Error in getRecipientDropDowns: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return queueUtil.getUniqueValueFromMap(senderMap);
	}*/
	/*
	public ArrayList<Object> getTransferDropDowns(Collection<Object>  receivingSysDTColl) {
		Map<Object, Object> transferMap = new HashMap<Object,Object>();
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getAllowTransferIndCd()!= null) {
					transferMap.put(dt.getAllowTransferIndCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
							
				}
				if(dt.getAllowTransferIndCd() == null || dt.getAllowTransferIndCd().trim().equals("")){
					transferMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(transferMap);
	}*/
	/*
	public ArrayList<Object> getReportTypeDropDowns(Collection<Object>  receivingSysDTColl) {
		Map<Object, Object> reportTypeMap = new HashMap<Object,Object>();
		try {
		if (receivingSysDTColl != null) {
			Iterator<Object> iter = receivingSysDTColl.iterator();
			while (iter.hasNext()) {
				TriggerCodesDT dt = (TriggerCodesDT) iter.next();
				if (dt.getReportType()!= null) {
					reportTypeMap.put(dt.getReportType(), CachedDropDowns.getCodeDescTxtForCd(dt.getReportType(),"PUBLIC_HEALTH_EVENT"));
							
				}
				if(dt.getReportType() == null || dt.getReportType().trim().equals("")){
					reportTypeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}
		}
		} catch (Exception ex) {
			logger.error("Error in getReportTypeDropDowns: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return queueUtil.getUniqueValueFromMap(reportTypeMap);
	}*/
	public String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		try {
		if(methodName != null) {
			if(methodName.equals("getReceivingSystemNm"))
				sortOrdrStr = "System Name";
			else if(methodName.equals("getReceivingSystemShortName"))
				sortOrdrStr = "Short Name";
			else if(methodName.equals("getReceivingSystemOwner"))
				sortOrdrStr = "Facility Name";
			else if(methodName.equals("getSendingIndCd"))
				sortOrdrStr = "Sender";
			else if(methodName.equals("getReceivingIndCd"))				
				sortOrdrStr = "Recipient";				
			else if(methodName.equals("getAllowTransferIndCd"))				
				sortOrdrStr = "Transfer";
			else if(methodName.equals("getReportType"))				
				sortOrdrStr = "Report Type";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";
		} catch (Exception ex) {
			logger.error("Error in getSortCriteria: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return sortOrdrStr;
			
	}
	private static boolean cvgPagination(HttpServletRequest request) {
		boolean found = false;
		Enumeration<String> enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String paramName = (String) enm.nextElement();
			if(paramName != null && paramName.startsWith("d-")) {
				found = true;
				break;
			}
		}
		
		return found;
	}
	public static void handleErrors(Exception e, HttpServletRequest request, String actionType,String fileds) {
		
		//handle special scenario for ImportExport Manage Systems pagination after submit
		if(cvgPagination(request)) return;		
		if(e.toString().indexOf("SQLException") != -1 || e.toString().indexOf("SQLServerException") != -1) {
			if(actionType != null && actionType.equalsIgnoreCase("edit")) {
				request.setAttribute("error", "Database error while updating. Please check the values and try again.");
			} else if(actionType != null && actionType.equalsIgnoreCase("create")){
				if(e.toString().indexOf("PK")!= -1){
				   request.setAttribute("error", "A record already exists with this "+fileds+". Please enter a unique "+fileds+" to create a new record.");
				}
				else{
					request.setAttribute("error", "Database error while creating. Please check the values and try again.");
				}
			} else if(actionType != null && actionType.equalsIgnoreCase("search")) {
				
				request.setAttribute("error", "Database error while searching. Please check the values and try again.");				
			} else {
			request.setAttribute("error", e.getMessage());
		}
	  }
	}

	public static String setReceivingSystemsDup(HttpSession session, TriggerCodesDT dt) {
		MainSessionCommand msCommand = null;
		String t = "";
		try{
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_DAO_CLASS;
		String sMethod = "UniqueFieldCheckForRecSysm";
		Object[] oParams =new Object[] {dt};
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		 t =(String) arr.get(0);
		} catch (Exception ex) {
			logger.debug("setReceivingSystemsDup excep calling UniqueFieldCheckForRecSysm");
		}
		return t;
}
}
