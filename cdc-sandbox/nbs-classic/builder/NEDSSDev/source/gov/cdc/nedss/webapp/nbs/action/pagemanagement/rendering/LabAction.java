package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;


/**
 * Struts Action Class to handle PageManagement Module and redirect to the appropriate Page
 * actionHandler for Laboratory Information
 * LabAction.java
 * Jan 04, 2010
 * @version 1.0
 * @author Pradeep Kumar Sharma
 * @company GDIT
 * 
 */


public class LabAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(LabAction.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

		/**
		 * Redirects to the appropriate load util based on the form code
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public ActionForward createLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
	    IOException, ServletException  
	    {
	        String actionForward = "default";
	        try
	        {
	            PageForm pageForm = (PageForm) form;
	            String busObjType = NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;	            
	            String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
	            if (formCd == null || formCd.isEmpty())
	            {
	                throw new ServletException("No published form? For business object: " + busObjType);
	            }
	            
	            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
	            PageLoadUtil pageLoadUtil = new PageLoadUtil();
	            pageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
	            pageForm.setBusinessObjectType(busObjType);
	            pageForm.setPageFormCd(formCd);
	            // publish to the tmp dir if not there..
	            PagePublisher publisher = PagePublisher.getInstance();
	            // Copy the JSPs to the ear folder only when the server Restart is F
	            String serverRestart = propertyUtil.getServerRestart();
	            if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE))
	            {
	                try
	                {
	                    logger.info("Try publishing the generic page from page create load");
	                    boolean success = publisher.publishPage(formCd);
	                    logger.info("Published the page from page create generic load :" + success);
	                }
	                catch (Exception e)
	                {
	                	logger.fatal("Exception while publishing page:"+e.getMessage(), e);
	                    throw new ServletException("Error while copying generic page from NEDSS to Temp Folder: "
	                            + e.getMessage(), e);
	                }
	            }

	            String action = request.getParameter("Action");
	            if(action!=null && action.equalsIgnoreCase("DSFilePath") ){
	            	NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationPath, "DSFilePath");
	            }
	            
	            pageLoadUtil.createGenericLoadUtil(pageForm, request);

	            Map<Object, Object> subbatchMap = pageLoadUtil.findBatchRecords(formCd, request.getSession());
	            request.setAttribute("SubSecStructureMap", subbatchMap);
	            request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
	            _setRenderDir(request, formCd);
	            pageForm.setPageTitle("Add " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE, request);
	            
	        }
	        catch (Exception e)
	        {
	        	logger.fatal("Exception in PageAction.createGenericLoad: " + e.getMessage(), e);
	            throw new ServletException("Error while create generic load Page: " + e.getMessage(), e);
	        }
	        return (mapping.findForward(actionForward));
	    }
		
		
		/**
		 * createSubmit - Submit a new investigation
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward createSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws
	    IOException, ServletException {

			String actionForward = "";
			PageProxyVO thisPageProxyVO = null;
			try {
				PageForm pageForm = (PageForm) form;
				String formCd = getInvFormCd(request, pageForm);

				if(formCd != null ) {
					String contextAction = request.getParameter("ContextAction");
					if (contextAction != null && contextAction.equalsIgnoreCase(NBSConstantUtil.SubmitNoViewAccess))
						actionForward = contextAction; //could be "SubmitNoViewAccess" if no permission in jurisdiction
					else
						actionForward = "insertDMBPam";
					//process the store investigation request
					thisPageProxyVO = 	PageStoreUtil.createHandler(pageForm, request);
				}
				// set the success messages
				if (formCd != null && formCd.trim().length() != 0 &&
						pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
					request.setAttribute("success_messages", messages);
				}

				request.setAttribute("formCode", formCd);

			} catch (Exception e) {
				logger.fatal("Exception in PageAction.createSubmit: " + e.getMessage(), e);
				e.printStackTrace();
				throw new ServletException("Error while Saving Page Case: "+e.getMessage(),e);
			}

			return (mapping.findForward(actionForward));
		}

		private void _setRenderDir(HttpServletRequest request, String pageFormCd)
	    {
	        String renderDir = pageFormCd;
	        logger.debug("Setting render dir to: " + renderDir);
	        request.setAttribute("renderDir", renderDir);
	    }
	
		private static String getInvFormCd(HttpServletRequest req, PageForm form) {
			HttpSession session = req.getSession();
			String investigationFormCd = null;
			try {
				String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
				String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
				CachedDropDownValues cdv = new CachedDropDownValues();
				ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", conditionCd);
				if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
					   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, conditionCd);
				investigationFormCd = programAreaVO.getInvestigationFormCd();

			} catch (Exception e) {

				try {
					investigationFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);
				} catch (Exception e1) {
					logger.info("INV FORM CD is not present in Context: " + e.toString());
				}
			}
			if(investigationFormCd == null)
				investigationFormCd =  form.getPageFormCd();
			else
				form.setPageFormCd(investigationFormCd);
			//Log
			if(investigationFormCd == null || (investigationFormCd != null && investigationFormCd.equals(""))) {
				logger.error("Error while retrieving investigationFormCd from Context / PamForm: ");
			}
			return investigationFormCd;

		}

	}