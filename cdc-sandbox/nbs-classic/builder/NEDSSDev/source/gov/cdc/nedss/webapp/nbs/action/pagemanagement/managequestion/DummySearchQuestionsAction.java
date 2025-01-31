package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managequestion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DummySearchQuestionsAction extends DispatchAction 
{
	public ActionForward viewResults(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		try{
			// setting search results in scope
		}catch(Exception e){
			throw new ServletException(e.getMessage());
		}
		
		return (mapping.findForward("viewResults"));
	}
}