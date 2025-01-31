package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util;

import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.pagemanagement.wa.xml.util.JspBeautifier;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.FileUploadUtil;
import gov.cdc.nedss.webapp.nbs.form.util.AddNotesForm;

import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import gov.cdc.nedss.util.HTMLEncoder;

public class NBSAddNotesAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(NBSAddNotesAction.class.getName());

    public ActionForward showForm(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) 
    {
    	AddNotesForm notesForm = new AddNotesForm();
		notesForm.reset();
		request.setAttribute("addNotesForm", notesForm);
		
		ActionForward actionForward = mapping.findForward("showForm");
		return actionForward;
	}
    
    public ActionForward saveForm(ActionMapping mapping, 
    		ActionForm aForm, 
    		HttpServletRequest request, 
    		HttpServletResponse response)
    {
        ActionForward actionForward = null;
        String publicHealthCaseUid = "999999";
        try {
            AddNotesForm form = (AddNotesForm) aForm;
           
            NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");            
            String userId = secObj.getTheUserProfile().getTheUser().getEntryID();
            
            // prepare DT
            NBSNoteDT nbsNoteDT = new NBSNoteDT();
            nbsNoteDT.setItNew(true);
            nbsNoteDT.setNbsNoteUid(new Long(-1));
            publicHealthCaseUid = (String)request.getSession().getAttribute("DSInvUid"); 
            if(publicHealthCaseUid != null)
            	nbsNoteDT.setNoteParentUid(Long.parseLong(publicHealthCaseUid));  
            nbsNoteDT.setNote(form.getNotes());
            nbsNoteDT.setAddTime(new Timestamp(new Date().getTime()));
            nbsNoteDT.setAddUserId(Long.valueOf(userId));
            nbsNoteDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
            nbsNoteDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
            nbsNoteDT.setLastChgUserId(Long.valueOf(userId));
            nbsNoteDT.setLastChgTime(new Timestamp(new Date().getTime()));
            // This will be in SRT in the future release
            nbsNoteDT.setTypeCd(NEDSSConstants.INVESTIGATION_CD);
            if (form.getAccessModifier().equalsIgnoreCase(NEDSSConstants.PUBLIC)) {
            	nbsNoteDT.setPrivateIndCd(NEDSSConstants.FALSE);
            }
            else {
            	nbsNoteDT.setPrivateIndCd(NEDSSConstants.TRUE);
            }
            
            // persist data
            Long resultUid = saveNote(nbsNoteDT, request.getSession());
            if(resultUid != null && resultUid.longValue() > 0) {
            	request.setAttribute("confirmation", "true");
            	nbsNoteDT.setNbsNoteUid(resultUid);
            	//request.setAttribute("newNote", _makeNewNoteRow(nbsNoteDT));
            	_makeNewNoteRowVar(nbsNoteDT,request);
            }
            
            // reset form
            form.reset();
            request.setAttribute("addNotesForm", form);
        } catch (Exception e) {
            logger.error("Error in NBSAddNotesAction.saveForm = " + e);
            request.setAttribute("error", e.getMessage());
        }

        actionForward = mapping.findForward("showForm");
        return actionForward;
    }
    
	private static Long saveNote(NBSNoteDT dt, HttpSession session) throws Exception 
	{
		MainSessionCommand msCommand = null;
		Long resultUid = null;
		try {
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "setNBSNote";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { dt };
			Object returnObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, session);
			resultUid = (Long)returnObj;
		} catch (Exception ex) {
			logger.error("Error while processRequest(Calling the back end method in AddNotesAction: " + ex.toString());	
			throw new Exception();
		}
		
		return resultUid;	
	}    
	
	private static String _makeNewNoteRow(NBSNoteDT dt) {
		StringBuffer sb = new StringBuffer();
		
		//Date Added
		String dateAdded = StringUtils.formatDatewithHrMinSec(dt.getAddTime());
		//String dateAdded = StringUtils.formatDate(dt.getAddTime());
		sb.append("<td class=\"dateField\">").append(HTMLEncoder.encodeHtml(dateAdded)).append("</td>");
		
		//Added By
		RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
		String userNm = rsVO.getUserName(dt.getLastChgUserId());		
		sb.append("<td class=\"nameField\">").append(HTMLEncoder.encodeHtml(userNm)).append("</td>");
		
		// Note
		JspBeautifier.replace(dt.getNote(), "\n", " ");
		JspBeautifier.replace(dt.getNote(), "\r", " ");
		JspBeautifier.replace(dt.getNote(), "\r\n", " ");
		JspBeautifier.replace(dt.getNote(), "'", " \'");
		JspBeautifier.replace(dt.getNote(), "\"", " \"");
		dt.getNote().replaceAll("\\s\\s+|\\n|\\r", " ");
		
		//Replacing the New Line and Carriage Return 
		FileUploadUtil util = new FileUploadUtil();
		dt.setNote(util.replaceNewLineCarRetn(dt.getNote()));
		
		sb.append("<td>").append(HTMLEncoder.encodeHtml(dt.getNote())).append("</td>");

		// Private Indicator
		sb.append("<td class=\"iconField\">").append(decoratePrivateIndicatorField(dt.getPrivateIndCd())).append("</td>");
		
		return sb.toString();
	}
	
	public static String decoratePrivateIndicatorField(String indicatorCode) {
		String retVal = "";	
		if (indicatorCode.equals(NEDSSConstants.TRUE)) {
			retVal = "<img src=\"accept.gif\" alt=\"True\" title=\"True\"> </img>";
		}
		
		return retVal;
	}
	
	private void _makeNewNoteRowVar(NBSNoteDT dt,HttpServletRequest request) {
		String dateAdded = StringUtils.formatDatewithHrMinSec(dt.getAddTime());
		RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
		String userNm = rsVO.getUserName(dt.getLastChgUserId());
		JspBeautifier.replace(dt.getNote(), "\n", " ");
		JspBeautifier.replace(dt.getNote(), "\r", " ");
		JspBeautifier.replace(dt.getNote(), "\r\n", " ");
		JspBeautifier.replace(dt.getNote(), "'", " \'");
		JspBeautifier.replace(dt.getNote(), "\"", " \"");
		dt.getNote().replaceAll("\\s\\s+|\\n|\\r", " ");
		
		FileUploadUtil util = new FileUploadUtil();
		dt.setNote(util.replaceNewLineCarRetn(dt.getNote()));
		
		request.setAttribute("newNote", dt.getNote());
		request.setAttribute("userName", userNm);
		request.setAttribute("addedDate", dateAdded);
		request.setAttribute("privateInd", dt.getPrivateIndCd());
	}

}
