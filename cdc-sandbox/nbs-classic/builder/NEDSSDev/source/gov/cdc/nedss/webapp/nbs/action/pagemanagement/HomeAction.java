package gov.cdc.nedss.webapp.nbs.action.pagemanagement;

import gov.cdc.nedss.util.LogUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

public class HomeAction extends Action {
	
	static final LogUtils logger = new LogUtils(HomeAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		try{
			// any activity to prepare the Page Management Home 
			request.setAttribute("PageTitle", "Page Management");
		}catch(Exception e){
			logger.error("Exception in HomeAction: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			
		}
		return (mapping.findForward("home"));
	}
}