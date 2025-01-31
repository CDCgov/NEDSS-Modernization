package gov.cdc.nedss.webapp.nbs.action.pam.susceptibilities;

import gov.cdc.nedss.util.LogUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class SusceptibilitiesAction extends DispatchAction
{
	static final LogUtils logger = new LogUtils(SusceptibilitiesAction.class.getName());

	/**
	 * susceptibilitiesLoad: this method is used to open the susceptibilities pop up from TB investigations.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward susceptibilitiesLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
		String id = (String)request.getParameter("id");
		request.setAttribute("id",id);
		String contextAction= request.getParameter("ContextAction");
		
		return (mapping.findForward(contextAction));
	
	}
}



