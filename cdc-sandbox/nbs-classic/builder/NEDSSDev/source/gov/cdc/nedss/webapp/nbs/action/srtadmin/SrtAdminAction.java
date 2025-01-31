package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SrtAdminAction extends DispatchAction {
	

    public SrtAdminAction()
    {
    }

    public ActionForward manageAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	SrtManageForm manageForm = (SrtManageForm) form;
    	manageForm.clearSelections();
    	manageForm.setOldManageList(null);
    	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SRT_ADMINISTRATION);
    	return mapping.findForward("manageAdmin");
    }	

}
