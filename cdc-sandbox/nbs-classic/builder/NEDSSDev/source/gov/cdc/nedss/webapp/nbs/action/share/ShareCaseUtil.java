package gov.cdc.nedss.webapp.nbs.action.share;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *Title:        ShareCaseUtil
 * Description:  this util class Shares the cases with other facilities
 * Copyright:    Copyright (c) 2009
 * Company:      CSC
 * @author Pradeep Sharma
 *@version 3.0
 *
 */
public class ShareCaseUtil {

	
	static final LogUtils logger = new LogUtils(ShareCaseUtil.class.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	
	
	public static void loadShareCase(PamForm form, HttpServletRequest req) {
		
		String caseId = (String) form.getAttributeMap().get("caseLocalId");
		PublicHealthCaseDT phcDT = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = phcDT.getPublicHealthCaseUid();
		String jurisCd = phcDT.getJurisdictionCd();
		String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
		String localId = phcDT.getLocalId();
		form.getPamClientVO().setAnswer(PamConstants.JURISDICTION, jurisCd);
		form.getAttributeMap().put("caseLocalId", caseId);
		form.getAttributeMap().put("oldJurisdiction", jurisDesc);
		form.getAttributeMap().put("investigationUID", phcUid);
		form.getAttributeMap().put("DSInvestigationLocalID", localId);
		
	}
	
	public static void loadSharePageCase(PageForm form, HttpServletRequest req) {
		
		String caseId = (String) form.getAttributeMap().get("caseLocalId");
		PublicHealthCaseDT phcDT = form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = phcDT.getPublicHealthCaseUid();
		String jurisCd = phcDT.getJurisdictionCd();
		String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
		String localId = phcDT.getLocalId();
		form.getPageClientVO().setAnswer(PamConstants.JURISDICTION, jurisCd);
		form.getAttributeMap().put("caseLocalId", caseId);
		form.getAttributeMap().put("oldJurisdiction", jurisDesc);
		form.getAttributeMap().put("investigationUID", phcUid);
		form.getAttributeMap().put("DSInvestigationLocalID", localId);
		
	}
	
	public ActionForward shareCaseSubmit(ActionMapping mapping, PamForm  form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		String contextAction = "";
		
		String url = "/nbs/" + currentTask + ".do?ContextAction=CaseReporting";		
		String recipient = request.getParameter("exportFacility") == null ? "" : (String)request.getParameter("exportFacility"); 
		String comment = request.getParameter("comment") == null ? "" : (String)request.getParameter("comment"); 
		String DSInvestigationLocalID = form.getAttributeMap().get("DSInvestigationLocalID") == null ? "" : (String) form.getAttributeMap().get("DSInvestigationLocalID"); 
		Long DSInvestigationUid = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(); 
		if(DSInvestigationUid!=null)
		      	NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid, DSInvestigationUid.toString());
		StringBuffer sb = new StringBuffer();
		Long phcUid;
		try {
			phcUid = setShareCase(form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO(),  comment,  recipient, request, NEDSSConstants.EDX_PHCR_DOC_TYPE);
		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}

		catch (Exception e) {
			logger.fatal("Error in ShareCaseUtil.shareCaseSubmit ", e);
			throw new ServletException("Error in Share Case Submit"+e.getMessage(),e);
		}		
		String recFacDesc = CachedDropDowns.getReceivingFacilityDescTxt(recipient);
		if(phcUid.compareTo(new Long(1))==0){
			sb.append(DSInvestigationLocalID).append(" has already been shared with ").append(recFacDesc);
		}else{			
			sb.append(DSInvestigationLocalID).append(" has been successfully shared with ").append(recFacDesc);
		}
		request.getSession().setAttribute(NBSConstantUtil.ConfirmationMsg, sb.toString());
		 
		response.sendRedirect(url);
		return null;
	}
	
	private static Long setShareCase(PublicHealthCaseVO publicHealthCaseVO, String comment,
			String expFacility, HttpServletRequest request, String documentType)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			
			NEDSSAppConcurrentDataException, Exception {
		
		try {
			
		
		String newJurisdictionCode=publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd();
		NotificationProxyVO notProxyVO =InvestigationUtil.setShareNotificationProxyVO(publicHealthCaseVO, expFacility, comment, newJurisdictionCode,request);
		if (documentType == null || documentType.equals(NEDSSConstants.EDX_PHCR_DOC_TYPE))
			notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_SHARE_NOTF);
		else
			notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC);
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
		String sMethod = "shareCase";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
		}
    	Object[] oParams = { notProxyVO};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
     	}catch (Exception e) {
    		logger.error("Exception in setShareCase: " + e.getMessage());
    		e.printStackTrace();
    		throw new Exception("Error occurred setShareCase : "+e.getMessage());
    	}  
	}
	
	public ActionForward shareCasePageSubmit(ActionMapping mapping, PageForm  form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		String contextAction = "";
		
		String url = "/nbs/" + currentTask + ".do?ContextAction=CaseReporting";		
		String recipient = request.getParameter("exportFacility") == null ? "" : (String)request.getParameter("exportFacility"); 
		String comment = request.getParameter("comment") == null ? "" : (String)request.getParameter("comment");
		//Document Type is PHCR or PHDC, if null default to PHCR, PHDC is new 5.1 CDA 
		String documentType = request.getParameter("documentType") == null ? "PHCR" : (String)request.getParameter("documentType");
		String DSInvestigationLocalID = form.getAttributeMap().get("DSInvestigationLocalID") == null ? "" : (String) form.getAttributeMap().get("DSInvestigationLocalID"); 
		Long DSInvestigationUid = form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(); 
		if(DSInvestigationUid!=null)
		      	NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid, DSInvestigationUid.toString());
		StringBuffer sb = new StringBuffer();
		Long phcUid;
		try {
			phcUid = setShareCase(form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO(),  comment,  recipient, request, documentType);
		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}

		catch (Exception e) {
			logger.fatal("Error in ShareCasePageSubmit ", e);
			throw new ServletException("Error in ShareCasePageSubmit "+e.getMessage(),e);
		}		
		String recFacDesc = CachedDropDowns.getReceivingFacilityDescTxt(recipient);
	
		sb.append(DSInvestigationLocalID).append(" has been successfully shared with ").append(recFacDesc);
		request.getSession().setAttribute(NBSConstantUtil.ConfirmationMsg, sb.toString());
		
		response.sendRedirect(url);
		return null;
	}
	
	

	

	
}