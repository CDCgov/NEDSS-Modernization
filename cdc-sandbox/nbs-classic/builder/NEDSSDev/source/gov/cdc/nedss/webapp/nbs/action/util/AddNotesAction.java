package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.form.util.AddNotesForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AddNotesAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(AddNotesAction.class.getName());

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
        String contactUid = "999999";
        try {
            AddNotesForm form = (AddNotesForm) aForm;
            contactUid = String.valueOf(NBSContext.retrieve(request.getSession(), 
            		NBSConstantUtil.DSContactUID));
            NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");            
            String userId = secObj.getTheUserProfile().getTheUser().getEntryID();
            
            // prepare DT
            CTContactNoteDT ctContactNoteDT = new CTContactNoteDT();
            ctContactNoteDT.setItNew(true);
            ctContactNoteDT.setCtContactNoteUid(new Long(-1));
            ctContactNoteDT.setCtContactUid(new Long(contactUid));
            ctContactNoteDT.setNote(form.getNotes());
            ctContactNoteDT.setAddTime(new Timestamp(new Date().getTime()));
            ctContactNoteDT.setAddUserId(Long.valueOf(userId));
            ctContactNoteDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
            ctContactNoteDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
            ctContactNoteDT.setLastChgUserId(Long.valueOf(userId));
            ctContactNoteDT.setLastChgTime(new Timestamp(new Date().getTime()));
            if (form.getAccessModifier().equalsIgnoreCase(NEDSSConstants.PUBLIC)) {
            	ctContactNoteDT.setPrivateIndCd(NEDSSConstants.FALSE);
            }
            else {
            	ctContactNoteDT.setPrivateIndCd(NEDSSConstants.TRUE);
            }
            
            // persist data
            Long resultUid = saveNote(ctContactNoteDT, request.getSession());
            if(resultUid != null && resultUid.longValue() > 0) {
            	request.setAttribute("confirmation", "true");
            	ctContactNoteDT.setCtContactNoteUid(resultUid);
            	_makeNewNoteRowVar(ctContactNoteDT,request);
            	//request.setAttribute("newNote", _makeNewNoteRow(ctContactNoteDT));
            }
            
            // reset form
            form.reset();
            request.setAttribute("addNotesForm", form);
        } catch (Exception e) {
            logger.error("Error in AddNotesAction.saveForm = " + e);
            request.setAttribute("error", e.getMessage());
        }

        actionForward = mapping.findForward("showForm");
        return actionForward;
    }
    
	private static Long saveNote(CTContactNoteDT dt, HttpSession session) throws Exception 
	{
		MainSessionCommand msCommand = null;
		Long resultUid = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "setContactNote";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { dt };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			resultUid = (Long) arr.get(0);
		} catch (Exception ex) {
			logger.error("Error while processRequest in AddNotesAction: " + ex.toString());	
			throw new Exception(ex);
		}
		
		return resultUid;	
	}    
	
	private static String _makeNewNoteRow(CTContactNoteDT dt) {
		StringBuffer sb = new StringBuffer();
		
		//Date Added
		String dateAdded = StringUtils.formatDate(dt.getAddTime());
		sb.append("<td class=\"dateField\">").append(HTMLEncoder.encodeHtml(dateAdded)).append("</td>");
		
		//Added By
		RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
		String userNm = rsVO.getUserName(dt.getLastChgUserId());		
		sb.append("<td class=\"nameField\">").append(HTMLEncoder.encodeHtml(userNm)).append("</td>");
		
		// Note
		dt.getNote().replaceAll("'", "\'");
		dt.getNote().replaceAll("\"", "\"");
		//Replacing the New Line and Carriage Return 
		FileUploadUtil util = new FileUploadUtil();
		dt.setNote(util.replaceNewLineCarRetn(dt.getNote()));
		
		sb.append("<td>").append(HTMLEncoder.encodeHtml(dt.getNote())).append("</td>");

		// Private Indicator
		sb.append("<td class=\"iconField\">").append(decoratePrivateIndicatorField(HTMLEncoder.encodeHtml(dt.getPrivateIndCd()))).append("</td>");
		
		return sb.toString();
	}
	
	public static String decoratePrivateIndicatorField(String indicatorCode) {
		String retVal = "";	
		if (indicatorCode.equals(NEDSSConstants.TRUE)) {
			retVal = "<img src=\"accept.gif\" alt=\"True\" title =\"True\"> </img>";
		}
		
		return retVal;
	}
	
	private void _makeNewNoteRowVar(CTContactNoteDT dt,HttpServletRequest request) {
		String dateAdded = StringUtils.formatDate(dt.getAddTime());
		RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
		String userNm = rsVO.getUserName(dt.getLastChgUserId());
		dt.getNote().replaceAll("'", "\'");
		dt.getNote().replaceAll("\"", "\"");
		FileUploadUtil util = new FileUploadUtil();
		dt.setNote(util.replaceNewLineCarRetn(dt.getNote()));
		request.setAttribute("newNote", dt.getNote());
		request.setAttribute("userName", userNm);
		request.setAttribute("addedDate", dateAdded);
		request.setAttribute("privateInd", dt.getPrivateIndCd());
	}
}