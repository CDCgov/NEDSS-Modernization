package gov.cdc.nedss.webapp.nbs.action.pam;

import java.io.IOException;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.RVCT.RVCTLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.RVCT.RVCTPrintUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.RVCT.RVCTStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.Varicella.VaricellaLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.Varicella.VaricellaPrintUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.Varicella.VaricellaStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamDeleteUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamTransferOwnershipUtil;
import gov.cdc.nedss.webapp.nbs.action.share.ShareCaseUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * Struts Action Class to handle new PAMs (TB, Varicella, etc) and redirect to the appropriate
 * actionHandler based on the InvFormCd
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PamAction.java
 * Aug 8, 2008
 * 
 * @version
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class PamAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(PamAction.class.getName());
	
	/**
	 * Redirects to the appropriate load util based on the form code
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
    IOException, ServletException  {
		String actionForward = "";
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			PamForm pamForm = (PamForm) form;
			pamForm.initializeForm(mapping, request);
		
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				RVCTLoadUtil rvctLoadUtil = new RVCTLoadUtil();
				rvctLoadUtil.createLoadUtil(pamForm, request,response);
				actionForward = "createTBPam";
			}
			//Varicella
		   else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR))
		   {
			  // VaricellaLoadUtil.createLoadUtil(pamForm, request);
			   VaricellaLoadUtil varicellaLoadUtil = new VaricellaLoadUtil ();
			   varicellaLoadUtil.createLoadUtil(pamForm, request, response);
			   actionForward = "createVarPam";
		   }
			request.setAttribute("formCode", formCd);
			
		} catch (Exception e) {
			logger.error("Exception in PamAction.createLoad: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while create load PAM Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward(actionForward));
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws
    IOException, ServletException {
		
		String actionForward = "";
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			PamForm pamForm = (PamForm) form;
			
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				actionForward = "insertTBPam";
				RVCTStoreUtil rvctStoreUtil = new RVCTStoreUtil();
				rvctStoreUtil.createHandler(pamForm, request);
				if(pamForm.getErrorList()!=null && pamForm.getErrorList().size()>0)
					actionForward = "createTBPam";
				if(pamForm.getActionMode() != null && pamForm.getActionMode().equalsIgnoreCase("SubmitNoViewAccess"))
					actionForward = "SubmitNoViewAccess";				
			}else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)) {//Varicella
				actionForward = "insertVarPam";
				
				VaricellaStoreUtil varicellaStoreUtil = new VaricellaStoreUtil();
				varicellaStoreUtil.createHandler(pamForm, request);
				if(pamForm.getErrorList()!=null && pamForm.getErrorList().size()>0)
					actionForward = "createVarPam";
				if(pamForm.getActionMode() != null && pamForm.getActionMode().equalsIgnoreCase("SubmitNoViewAccess"))
					actionForward = "SubmitNoViewAccess";
			}

			// set the success messages
			if (formCd != null && formCd.trim().length() != 0 &&
					pamForm.getErrorList()== null || pamForm.getErrorList().size() <= 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
				request.setAttribute("success_messages", messages);
			}

			request.setAttribute("formCode", formCd);
			
		} catch (Exception e) {
			logger.error("Exception in PamAction.createSubmit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while Saving PAM Case: "+e.getMessage(),e);
		}
		
		return (mapping.findForward(actionForward));
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException{
		
		String actionForward = "";
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			PamForm pamForm = (PamForm) form;
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				actionForward = "editTBPam";
				pamForm.initializeForm(mapping, request);
				RVCTLoadUtil rvctLoadUtil = new RVCTLoadUtil();
				rvctLoadUtil.editLoadUtil(pamForm, request);
			}else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR))
			{
				actionForward = "editVarPam";
				pamForm.initializeForm(mapping, request);
				VaricellaLoadUtil varicellaLoadUtil = new VaricellaLoadUtil();
				varicellaLoadUtil.editLoadUtil(pamForm, request);
			}
			request.setAttribute("formCode", formCd);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in PamAction.editLoad: " + e.getMessage());
			throw new ServletException("Error while loading edit PAM Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward(actionForward));
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {
		
		String actionForward = "";
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			PamForm pamForm = (PamForm) form;
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				actionForward = "editSubmit";
				RVCTStoreUtil rvctStoreUtil = new RVCTStoreUtil();
				rvctStoreUtil.editHandler(pamForm, request);
				if(pamForm.getErrorList()!=null && pamForm.getErrorList().size()>0)
					actionForward = "editTBPam";				
			}else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR))
			{
				actionForward = "editVarSubmit";
				VaricellaStoreUtil varicellaStoreUtil = new VaricellaStoreUtil();
				varicellaStoreUtil.editHandler(pamForm, request);
				if(pamForm.getErrorList()!=null && pamForm.getErrorList().size()>0)
					actionForward = "editVarPam";	
				
			}

			// set the success messages
			if (formCd != null && formCd.trim().length() != 0 &&
					pamForm.getErrorList()== null || pamForm.getErrorList().size() <= 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
				request.setAttribute("success_messages", messages);
			}

			request.setAttribute("formCode", formCd);
		}
		 catch (NEDSSAppConcurrentDataException ncde)
	      {
	        logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
	                     ncde);
	        return mapping.findForward("dataerror");
	      }catch (Exception e) {
	    	 logger.error("Exception in PamAction.editSubmit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while edit saving PAM Case: "+e.getMessage(),e);
		}
		return (mapping.findForward(actionForward));
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {
		
		String actionForward = "";
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			actionForward = "viewTBPam";
			PamForm pamForm = (PamForm) form;
			String contextAction = request.getParameter("ContextAction");
			if(contextAction!=null && contextAction.equals("Cancel"))
			{
				pamForm.getAttributeMap().remove("REQ_FOR_NOTIF");
				pamForm.getAttributeMap().remove("NotifReqMap");
			}
			boolean reqForNotif = pamForm.getAttributeMap().containsKey(PamConstants.REQ_FOR_NOTIF);
			String noReqForNotifCheck =null;
			if(pamForm.getAttributeMap().containsKey(PamConstants.NO_REQ_FOR_NOTIF_CHECK))
				 noReqForNotifCheck = (String)pamForm.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK);
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			
			request.setAttribute("mode", request.getParameter("mode"));
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				actionForward = "viewTBPam";
				RVCTLoadUtil rvctLoadUtil = new RVCTLoadUtil();
				rvctLoadUtil.viewLoadUtil(pamForm, request);	
			}else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)) {//Varicella
				actionForward = "viewVarPam";
				VaricellaLoadUtil varicellaLoadUtil = new VaricellaLoadUtil();
				varicellaLoadUtil.viewLoadUtil(pamForm, request);	
			}
			request.setAttribute("formCode", formCd);
			
			if(reqForNotif == true && (noReqForNotifCheck != null && noReqForNotifCheck.equalsIgnoreCase("false")))
			{
				request.setAttribute("REQ_FOR_NOTIF", PamConstants.REQ_FOR_NOTIF);
			}
						
		} catch (Exception e) {
			logger.error("Exception in PamAction.viewLoad: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while loading PAM Case: "+e.getMessage(),e);
		}
		

		return (mapping.findForward(actionForward));
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		
		RVCTStoreUtil rvctStoreUtil = new RVCTStoreUtil();
		InvestigationUtil investigationUtil = new InvestigationUtil();
		try {
			PamForm pamForm = (PamForm) form;
			String formCd = investigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				rvctStoreUtil.viewHandler(pamForm, request);	
			}
			request.setAttribute("formCode", formCd);
		} catch (Exception e) {
			logger.error("Exception in PamAction.viewSubmit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while view Submitting PAM Case: "+e.getMessage(),e);
		}
		
		String sContextAction = request.getParameter("ContextAction");
		return (mapping.findForward(sContextAction));
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void printLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
	  
	  PamForm pamForm = (PamForm) form;
	  InvestigationUtil investigationUtil = new InvestigationUtil();
	  String formCd = investigationUtil.getInvFormCd(request, pamForm);
		//Tuberculosis
		if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
			RVCTPrintUtil.printForm(pamForm, request, response);	
		}//Varicella
		else if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)) {
			VaricellaPrintUtil.printForm(pamForm, request, response);
		}
		request.setAttribute("formCode", formCd);
	  
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward deleteSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
	   ActionForward forward;
	try {
		PamForm pamForm = (PamForm) form;
		PamDeleteUtil pamDeleteUtil = new PamDeleteUtil();
		  forward = pamDeleteUtil.deletePam(mapping,pamForm, request, response);
	} catch (Exception e) {
		logger.error("Exception in PamAction.deleteSubmit: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("Error while view deleteSubmit of  PAM Case: "+e.getMessage(),e);
	}
		return forward;
	}	
	
	public ActionForward transferOwnershipLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		PamForm pamForm = (PamForm) form;
		try {
			PamTransferOwnershipUtil pamTransferOwnershipUtil = new PamTransferOwnershipUtil();
			pamTransferOwnershipUtil.loadTransferOwnership(pamForm, request);
		} catch (Exception e) {
			logger.error("Exception in PamAction.transferOwnership: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while loading transferOwnership for PAM Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward("transferOwnership"));
	}	
	public ActionForward sharePamCaseLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		PamForm pamForm = (PamForm) form;
		try {
			ShareCaseUtil shareCaseUtil = new ShareCaseUtil();
			shareCaseUtil.loadShareCase(pamForm, request);
		} catch (Exception e) {
			logger.error("Exception in PamAction.sharePamCaseLoad: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while loading sharePamCaseLoad  for PAM Case: "+e.getMessage(),e);
		}		
		return (mapping.findForward("shareCaseReport"));
	}	
	public ActionForward sharePamCaseSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		ActionForward forward;
		PamForm pamForm = (PamForm) form;
		try {
			ShareCaseUtil util = new ShareCaseUtil();
			forward=util.shareCaseSubmit(mapping, pamForm, request, response);
		} catch (Exception e) {
			logger.error("Exception in PamAction.sharePamCaseSubmit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while loading sharePamCaseSubmit for PAM Case: "+e.getMessage(),e);
		}		
		return forward;
	}	
	
	public ActionForward transferOwnershipSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		PamForm pamForm = (PamForm) form;
		ActionForward forward;
		try {
			PamTransferOwnershipUtil pamTransferOwnershipUtil = new PamTransferOwnershipUtil();
			forward = pamTransferOwnershipUtil.storeTransferOwnership(mapping, pamForm, request, response);
		} catch (Exception e) {
			logger.error("Exception in PamAction.transferOwnershipSubmit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("Error while transferOwnership for PAM Case: "+e.getMessage(),e);
		}		
		return forward;
	}	
	/**
	 * Previews page by the ldf pageset id
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward previewLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		
		String actionForward = "";
		try {
			PamForm pamForm = (PamForm) form;
			String formCd = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationFormCd);
			if(formCd != null && formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_RVCT)) {
				actionForward = "createTBPam";
			} else if(formCd != null && formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_VAR)) {
				actionForward = "createVarPam";
			}
			pamForm.setPamFormCd(formCd);
			PamLoadUtil pamLoadUtil = new PamLoadUtil();
			pamLoadUtil.previewLDF(pamForm, request);
			request.setAttribute("formCode", formCd);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error while previewing LDFs: "+e.getMessage(),e);
		}		
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward printNotes(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException,Exception {
		  
		  PamForm pamForm = (PamForm) form;
		  InvestigationUtil investigationUtil = new InvestigationUtil();
		  String formCd = investigationUtil.getInvFormCd(request, pamForm);
		  RVCTPrintUtil.printNotes(pamForm, request, response);	
		  request.setAttribute("formCode", formCd);
		  return (mapping.findForward("printNotes"));
		  
		}
	public ActionForward createNotification(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException,Exception {
		  
		   PamForm pamForm = (PamForm) form;
		  return (mapping.findForward("createNotification"));
		  
		}
		
	
}