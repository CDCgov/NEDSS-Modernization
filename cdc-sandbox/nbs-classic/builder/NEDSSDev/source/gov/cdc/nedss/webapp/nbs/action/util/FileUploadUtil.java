package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

public class FileUploadUtil {
	
	static final LogUtils logger = new LogUtils(FileUploadUtil.class.getName());
	
	public static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) paramMap.get(key);
			reqParams.append(key).append("=");
			reqParams.append(value.replaceAll(" ", "%20"));
			reqParams.append("&");
		}
		reqParams.deleteCharAt(reqParams.length() - 1);
		url.append("<a href=javascript:loadAttachment(\"/nbs/");
		url.append(strutsAction);
		url.append(reqParams.toString());
		if(jumperName != null) {
			url.append("#").append(jumperName);
		}
		url.append("\")>").append(displayNm).append("</a>");
		return url.toString();
	}
	

	@SuppressWarnings("unchecked")
	public static String processRequest(Long nbsCaseAttachmentUid, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		String attachmentTxt = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "deleteNbsAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { nbsCaseAttachmentUid };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			attachmentTxt = (String)arr.get(0);

		} catch (Exception ex) {
			logger.error("Error while processRequest in FileUploadAction: " + ex.toString());	
			throw new Exception(ex);
		}
		return attachmentTxt;	
	}   
	
	/**
	 * 
	 * @param attachmentDTs
	 */
	public static void updateAttachmentsForView(Collection<Object> attachmentDTs)
		throws Exception
	{
		try {
			Iterator<Object> iter = attachmentDTs.iterator();
			while(iter.hasNext()) {
				NBSAttachmentDT dt = (NBSAttachmentDT) iter.next();
				Long uId = dt.getLastChgUserId();
				RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
				String userNm = retrieveSummaryVO.getUserName(uId);
				dt.setLastChgUserNm(userNm);
				//SET delete Link
				String delUrl = "<a id=\"td_" + dt.getNbsAttachmentUid() + "\" href=javascript:deleteAttachment('" + dt.getNbsAttachmentUid() + "')>Delete</a>";
				dt.setDeleteLink(delUrl);
				//SET view Link
				HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
				parameterMap.put("nbsAttachmentUid", String.valueOf(dt.getNbsAttachmentUid()));
				parameterMap.put("fileNmTxt", dt.getFileNmTxt());			
				dt.setViewLink(buildHyperLink("InvDownloadFile.do", parameterMap, null, dt.getFileNmTxt()));
			}
		} catch (Exception e) {
			logger.error("Error while _updateAttachmentsForView in Page Load - FileUploadUtil class: " + e.toString());
			throw e;
		}
	}
	@SuppressWarnings("unused")
	public static void updateNotesForView(Collection<Object> notesDTs)
		throws Exception
	{
		try {
			Iterator<Object> iter = notesDTs.iterator();
			while(iter.hasNext()) {
				NBSNoteDT dt = (NBSNoteDT) iter.next();
				Long uId = dt.getLastChgUserId();
				RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
				String userNm = retrieveSummaryVO.getUserName(uId);
				dt.setLastChgUserNm(userNm);
				dt.setPrivateIndCd(AddNotesAction.decoratePrivateIndicatorField(dt.getPrivateIndCd()));
			}
		} catch (Exception e) {
			logger.error("Error while _updateNotesForView in ContactTracingAction: " + e.toString());
			throw e;
		}
	}	
	public static String decoratePrivateIndicatorField(String indicatorCode) {
		String retVal = "";	
		if (indicatorCode.equals(NEDSSConstants.TRUE)) {
			retVal = "<img src=\"accept.gif\" alt=\"True\" title =\"True\"> </img>";
		}
		
		return retVal;
	}

	public String replaceNewLineCarRetn(String orgString){
		
		Pattern ptn = Pattern.compile("(\r\n|\r|\n|\n\r|')");
		Matcher m = ptn.matcher(orgString);
		
		String newString = "";
		if (m.find())
		  newString = m.replaceAll(" ");		
		else 
			newString =  orgString;
		return newString;
	}
	/**
	 * This method will eliminate the private method from the collection
	 * @param notesDTs
	 * @return
	 * @throws Exception
	 */
	public  ArrayList<Object> updateNotesForPrivateInd(Collection<Object> notesDTs, NBSSecurityObj nbsSecurityObj)
	throws Exception{
	
		ArrayList<Object> newNotes = new ArrayList<Object>();
		Long currentUser = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
		try {
		Iterator<Object> iter = notesDTs.iterator();
		while(iter.hasNext()) {
			NBSNoteDT dt = (NBSNoteDT) iter.next();
			Long uId = dt.getLastChgUserId();
							
			if(dt.getPrivateIndCd().equals(NEDSSConstants.FALSE)){
				newNotes.add(dt);
			}else if(dt.getPrivateIndCd().equals(NEDSSConstants.TRUE)){
				if(uId.equals(currentUser)){
					newNotes.add(dt);
				}
			}
		}
		
	} catch (Exception e) {
		logger.error("Error while _updateNotesForPrivateInd in FileUploadUtil: " + e.toString());
		throw e;
	}
	
	return newNotes;
}	
}
