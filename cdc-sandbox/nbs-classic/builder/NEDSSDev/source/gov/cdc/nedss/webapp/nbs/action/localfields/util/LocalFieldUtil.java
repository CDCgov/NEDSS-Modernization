package gov.cdc.nedss.webapp.nbs.action.localfields.util;

import gov.cdc.nedss.localfields.dt.LocalFieldsDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.localfields.LocalFieldsForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LocalFieldUtil {

	static final LogUtils logger = new LogUtils(LocalFieldUtil.class.getName());
	static final String NOT_AVAILABLE = "N/A";
	
	public static Object processRequest(Object[] oParams, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.LOCAL_FIELDS_EJB;
			String sMethod = "processLDFMetaDataRequest";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				logger.error("getProxy: ", ex);	
				throw new Exception(ex);
			}
			return obj;	
		}	
		
	public static void handleErrors(Exception e, HttpServletRequest request, String actionType,String fileds) {
		
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
	  } else {
		  request.setAttribute("error", "Exception while Updating LDF: " + e.getMessage());
	  }
	}
	
	public static void makeLDFLink(LocalFieldsDT dt, String type) throws Exception{

		HashMap<Object, Object> parameterMap = new HashMap<Object,Object>();
		Long uiMetadataUid = dt.getNbsUiMetadataUid();
		Long nbsQuestionUid = dt.getNbsQuestionUid();
		parameterMap.put("uiMetadataUid", uiMetadataUid);
		if(type.equals("VIEW")) {			
			parameterMap.put("method", "viewLDF");
			dt.setViewLink(buildHyperLink("LocalFields.do", parameterMap, "localField", "View"));
		} else if(type.equals("EDIT")) {
			parameterMap.put("method", "editLDF");
			dt.setEditLink(buildHyperLink("LocalFields.do", parameterMap, "localField", "Edit"));
		} else if(type.equals("DELETE")) {
			String lnk = "<a href=\"#\" onclick=\"deleteLocalField(this,'/nbs/LocalFields.do?uiMetadataUid=" + uiMetadataUid + "&questionUid=" + nbsQuestionUid +"&method=deleteLDF')\">Delete</a>";
			dt.setDeleteLink(lnk);			
		}
	}	

	
	private static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = String.valueOf(paramMap.get(key));
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
		return url.toString();
	}
	
	/**
	 * populates Tab, Section and Subsection Names in the Display Table
	 * @param dt
	 * @param manageForm
	 */
	public static void populateParents(LocalFieldsDT dt, LocalFieldsForm manageForm) {
		Long parentUid = dt.getParentUid();
		if(parentUid == null) {
			dt.setTab(NOT_AVAILABLE);dt.setSection(NOT_AVAILABLE);dt.setSubSection(NOT_AVAILABLE);
			return;
		}
		//parentUid may be either a subsection or a tab
		ArrayList<Object> list = CachedDropDowns.getAvailableTabs(manageForm.getFormCd());
		//Tab Iterator
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			DropDownCodeDT ddcDT = (DropDownCodeDT) iter.next();
			if(ddcDT.getKey().equals("")) continue;
			Long uid = Long.valueOf(ddcDT.getKey());
			ArrayList<Object> alist = CachedDropDowns.getLDFSections(uid);
			//If there are no sections, to the minimum, set the tab info
			if(alist != null && alist.size() == 1) {
				dt.setTab(ddcDT.getValue());
				dt.setSection(NOT_AVAILABLE);
				dt.setSubSection(NOT_AVAILABLE);
				return;
			}
			//Section Iterator
			Iterator<Object> iter1 = alist.iterator();
			while(iter1.hasNext()) {
				DropDownCodeDT secDT = (DropDownCodeDT) iter1.next();
				if(secDT.getKey().equals("")) continue;
				Long sUid = Long.valueOf(secDT.getKey());
				ArrayList<Object> ssList = CachedDropDowns.getLDFSubSections(sUid);
				if(ssList != null && ssList.size() > 1) {
					//SubSection Iterator
					Iterator<Object> iter2 = ssList.iterator();
					while(iter2.hasNext()) {
						DropDownCodeDT ssecDT = (DropDownCodeDT) iter2.next();
						if(ssecDT.getKey().equals("")) continue;
						Long ssUid = Long.valueOf(ssecDT.getKey());
						if(parentUid.compareTo(ssUid) == 0) {
							dt.setTab(ddcDT.getValue());
							dt.setSection(secDT.getValue());
							dt.setSubSection(ssecDT.getValue());
							return;
						}						
					}
				}	
			}
		}
	}

}
