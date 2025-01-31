package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Resets SRT Caching by updating relevant SRT db tables. 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ResetCacheAction.java
 * Jun 22, 2008
 * @version
 */
public class ResetCacheAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(ResetCacheAction.class.getName());
	
	/**
	 * resetLabMappingCache runs the nbs_srte.Labtest_Progarea_sp stored procedure
	 * to populate the nbs_srte.Labtest_Progarea_Mapping table appropriately.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
    public ActionForward resetLabMappingCache(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SRT_ADMINISTRATION);
		try {
			Object[] searchParams = {};
			Object[] oParams = new Object[] { JNDINames.RESET_CACHE_DAO_CLASS, "resetLabMappingCache", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			String msg = dtList.get(0).toString();
			//String count = msg.substring(msg.indexOf("|")+1);			
            if(dtList != null){
            	if(msg.indexOf("SUCCESS") != -1)
            		request.setAttribute("confirmMsg", "Labtest Program Area Mapping Cache has been successfully reset. Please restart Wildfly to reflect the changes.");
            	if(msg.indexOf("FAILURE") != -1)
            		request.setAttribute("confirmMsg", "Failure while resetting Labtest Program Area Mapping Cache !! Please contact your SRT Administrator.");
            	
            }
		} catch (Exception e) {
			logger.error("Exception in resetLabMappingCache: " + e.getMessage());
			e.printStackTrace();
			if(e.toString().indexOf("SQLException") != -1) 
				request.setAttribute("confirmMsg", "Database Error while resetting Labtest Program Area Mapping Cache. Please try again.");
			else
				request.setAttribute("confirmMsg", e.getMessage());
		} 
		return (mapping.findForward("default"));    	
    }	

    /**
     * resets all the codesetnames cached from nbs_srte.CodeValueGeneral table
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward resetCodeValueGeneralCache(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SRT_ADMINISTRATION);
		try {
			CachedDropDownValues.resetSRTCache();
			CachedDropDowns.resetSRTCache();
			request.setAttribute("confirmMsg", "Code Value General Cache has been successfully reset.");
		} catch (Exception e) {
			logger.error("Exception in resetCodeValueGeneralCache: " + e.getMessage());
			e.printStackTrace();
			if (e.toString().indexOf("SQLException") != -1)
				request.setAttribute("confirmMsg","Database Error while resetting Code Value General Cache. Please try again.");
			else
				request.setAttribute("confirmMsg", e.getMessage());
		} 
		return (mapping.findForward("default"));    	
    }    
}
