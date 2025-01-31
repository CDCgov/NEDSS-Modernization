package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PageTransferOwnershipUtil {

	    
	static final LogUtils logger = new LogUtils(PageTransferOwnershipUtil.class
			.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();

	public static void loadTransferOwnership(PageForm form,
			HttpServletRequest req) {

		String caseId = (String) form.getAttributeMap().get("caseLocalId");
		PageProxyVO proxyVO = form.getPageClientVO().getOldPageProxyVO();
		PublicHealthCaseDT phcDT = ((PageActProxyVO) proxyVO)
				.getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = phcDT.getPublicHealthCaseUid();
		String jurisCd = phcDT.getJurisdictionCd();
		String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
		form.getPageClientVO().setAnswer(PageConstants.JURISDICTION, jurisCd);
		form.getAttributeMap().put("caseLocalId", caseId);
		form.getAttributeMap().put("oldJurisdiction", jurisDesc);
		form.getAttributeMap().put("investigationUID", phcUid);

	}

	public static ActionForward storeTransferOwnership(ActionMapping mapping,
			PageForm form, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		List<String> allowedUrls = new ArrayList<String>();
		String url = (String) form.getAttributeMap().get("linkValue");
		allowedUrls.add(url.substring(url.indexOf("\"") + 1, url.indexOf(">") - 1));
		
		String oldJurisdDesc = (String) form.getAttributeMap().get(
				"oldJurisdiction");
		String newJurisd = req.getParameter(PageConstants.JURISDICTION);
		String newJurisdDesc = cdv.getJurisdictionDesc(newJurisd);
		Long phcUid = (Long) form.getAttributeMap().get("investigationUID");
		String localId = (String) form.getAttributeMap().get("caseLocalId");
		StringBuffer sb = new StringBuffer();
		String exportFacility = req.getParameter("exportFacility") == null ? ""
				: (String) req.getParameter("exportFacility");
		String comment = req.getParameter("comment") == null ? ""
				: (String) req.getParameter("comment");
		//Document Type is PHCR or PHDC, if null default to PHCR, PHDC is new 5.1 CDA
		String documentType = req.getParameter("documentType") == null ? NEDSSConstants.EDX_PHCR_DOC_TYPE : (String)req.getParameter("documentType");
		try {
			if (exportFacility != null && exportFacility.trim().length() > 0) {
				setExportOwnership(form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO(), exportFacility, comment,
						newJurisd, documentType, req);
			} else {
				setTransferOwnership(phcUid, form, newJurisd, req);
			}

		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger
					.fatal(
							"ERROR -. NEDSSAppConcurrentDataException, PageTransferOwnershipUtil.The data has been modified by another user, please recheck! ",
							e);
			return mapping.findForward("dataerror");
		} catch (Exception e) {
			logger
					.fatal(
							"ERROR , PageTransferOwnershipUtil.The error has occured, please recheck! ",
							e);
			e.printStackTrace();
			throw new ServletException(
					"ERROR , PageTransferOwnershipUtil.The error has occured, please recheck! "
							+ e.getMessage(), e);
		}
		sb.append(localId).append(" has been successfully transferred from ")
				.append(oldJurisdDesc).append(" Jurisdiction to ").append(
						newJurisdDesc).append(" Jurisdiction.");
		req.getSession().setAttribute("DSFileTab", "3");
		req.getSession().setAttribute("pageTOwnershipConfMsg", sb.toString());

		String returnURL = url.substring(url.indexOf("\"") + 1, url
				.indexOf(">") - 1);
		
		if(NedssUtils.isLocalPath(returnURL) && allowedUrls.contains(returnURL))  res.sendRedirect(returnURL);
		return null;
	}

	private static Long setTransferOwnership(Long investigationUID,
			PageForm form, String newJurisdictionCode, HttpServletRequest req)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {

		PageProxyVO pageProxyVO = form.getPageClientVO().getOldPageProxyVO();
		String pageFormCd = form.getPageFormCd();
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		String sMethod = "transferOwnership";
		ArrayList<?> resultUIDArr = new ArrayList<Object>();

		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(req.getSession());
		}
		Object obj = new Object();

		try {
			Map<Object, Object> questionMap = (Map<Object, Object>) QuestionsCache
					.getDMBQuestionMap().get(pageFormCd);
			obj = questionMap.get(PageConstants.JURISDICTION);

		} catch (Exception e) {
			logger
					.fatal("Error in TransferOwnershipSubmitInv while fetching INV107 from QuestionMap: "
							+ e.toString());
			e.printStackTrace();
		}

		Object[] oParams = { NEDSSConstants.CASE, investigationUID,
				pageProxyVO, newJurisdictionCode, null };
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
	}
	
	   private static Long setExportOwnership(PublicHealthCaseVO publicHealthCaseVO, String exportFacility, String comment,
				String newJurisdictionCode, String documentType, HttpServletRequest request)
				throws java.rmi.RemoteException, javax.ejb.EJBException,
				NEDSSAppConcurrentDataException, Exception {
			NotificationProxyVO notProxyVO =InvestigationUtil.setShareNotificationProxyVO(publicHealthCaseVO, exportFacility, comment, newJurisdictionCode, request);
			if (documentType.equals(NEDSSConstants.EDX_PHCR_DOC_TYPE))
				notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_EXP_NOTF);
			else if (documentType.equals(NEDSSConstants.EDX_PHDC_DOC_TYPE))
				notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC);
			else 
				logger.error("PageTransferOwnershipUtil.setExportOwnership -> Unknown Document Type of " +documentType);
			try {
			MainSessionCommand msCommand = null;
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "exportOwnership";
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			
			if (msCommand == null) {
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(request.getSession());
			}
	    	Object[] oParams = { NEDSSConstants.CASE, notProxyVO, newJurisdictionCode };
	        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				Long result = (Long) resultUIDArr.get(0);
				return result;
			} else
				return null;
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Data Concurrency Exception in setExportOwnership call to EJB: " + e.getMessage());
					e.printStackTrace();
					throw new ServletException("Data Concurrency Exception in setExportOwnership call : "+e.getMessage());
				} catch (Exception e) {
					logger.error("Exception in setExportOwnership call to EJB: " + e.getMessage());
					e.printStackTrace();
					throw new ServletException("Exception occurred in setExportOwnership call : "+e.getMessage());
				}
		}

}
