package gov.cdc.nedss.webapp.nbs.action.importandexport.Admin;

import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.action.importandexport.util.ImpAndExpConstants;
import gov.cdc.nedss.webapp.nbs.form.importandexport.ReceivingFacilityForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ImportAndExportAdmin extends DispatchAction {
	

    public ImportAndExportAdmin()
    {
    }

    public ActionForward manageAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	ReceivingFacilityForm manageForm = (ReceivingFacilityForm) form;
    	manageForm.clearSelections();
    	manageForm.setOldManageList(null);
    	request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.IAE_ADMINISTRATION);
    	HttpSession session = request.getSession();
    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
	        "NBSSecurityObject");
	    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.IMPORTEXPORTADMIN);
	    if (securityCheck != true) {
	      session.setAttribute("error", "Failed at security checking.");
	      throw new ServletException("Failed at security checking.");
	    }  	
    	
        return mapping.findForward("manageAdmin");
    }	

}
