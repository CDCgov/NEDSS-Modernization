package gov.cdc.nedss.webapp.nbs.action.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SystemAdministrationAction extends Action {
	
	static final LogUtils logger = new LogUtils(SystemAdministrationAction.class.getName());
	
	public SystemAdministrationAction() {
	}
	
	public ActionForward execute(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
		throws IOException, ServletException{
			String permSetUrl = "/nbs/loadPermissionSets.do?"+PageConstants.OPERATIONTYPE+"="+NEDSSConstants.LOAD_PERMISSION_SETS;
				
			request.setAttribute("permSetUrl", permSetUrl);
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,"System Management Main Menu");
			/*
			 *  Removing the Session Attributes when you come back to the main menu
			 *  In Page Edit/Library/Preview we are using 2 different Struts forms because of Preview.  
			 *  So ended up using some session objects  
			 */
			
			request.getSession().removeAttribute("ccMap");
			request.getSession().removeAttribute("waTemplateUid");
			request.getSession().removeAttribute("pgPageName");
			request.getSession().removeAttribute("from");	
			request.getSession().removeAttribute("templateNm");	
			
			ActionForward actionForward = mapping.findForward("systemAdmin");
			return actionForward;
		}

}
