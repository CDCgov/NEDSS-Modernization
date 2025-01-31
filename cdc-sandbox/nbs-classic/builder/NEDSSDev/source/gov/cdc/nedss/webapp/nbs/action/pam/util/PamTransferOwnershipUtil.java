package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PamTransferOwnershipUtil {
	
	static final LogUtils logger = new LogUtils(PamTransferOwnershipUtil.class.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	
	
	public static void loadTransferOwnership(PamForm form, HttpServletRequest req) {
		
		String caseId = (String) form.getAttributeMap().get("caseLocalId");
		PublicHealthCaseDT phcDT = form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
		Long phcUid = phcDT.getPublicHealthCaseUid();
		String jurisCd = phcDT.getJurisdictionCd();
		String jurisDesc = cdv.getJurisdictionDesc(jurisCd);
		form.getPamClientVO().setAnswer(PamConstants.JURISDICTION, jurisCd);
		form.getAttributeMap().put("caseLocalId", caseId);
		form.getAttributeMap().put("oldJurisdiction", jurisDesc);
		form.getAttributeMap().put("investigationUID", phcUid);
		
	}
	
	public static ActionForward storeTransferOwnership(ActionMapping mapping, PamForm form, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> allowedUrls = new ArrayList<String>();
		String url = (String) form.getAttributeMap().get("linkValue");
		allowedUrls.add(url.substring(url.indexOf("\"") + 1, url.indexOf(">") - 1));
		String oldJurisdDesc = (String) form.getAttributeMap().get("oldJurisdiction");
		String newJurisd = req.getParameter(PamConstants.JURISDICTION); 
		String newJurisdDesc = cdv.getJurisdictionDesc(newJurisd);
		Long phcUid = (Long) form.getAttributeMap().get("investigationUID");
		String localId = (String) form.getAttributeMap().get("caseLocalId");
		StringBuffer sb = new StringBuffer();
		String exportFacility = req.getParameter("exportFacility") == null ? "" : (String)req.getParameter("exportFacility"); 
		String comment = req.getParameter("comment") == null ? "" : (String)req.getParameter("comment"); 
		
		try {
			
			if(exportFacility!=null && exportFacility.trim().length()>0){
				setExportOwnership(form.getPamClientVO().getOldPamProxyVO().getPublicHealthCaseVO(), exportFacility, comment,
						newJurisd, req);
			}else{
				setTransferOwnership(phcUid, form, newJurisd, req);
			}
		} catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR -. NEDSSAppConcurrentDataException, PamTransferOwnershipUtil.The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}
		catch(Exception e){
			logger.fatal("ERROR , PamTransferOwnershipUtil.The error has occured, please recheck! ", e);
			throw new ServletException("ERROR , PamTransferOwnershipUtil.The error has occured, please recheck! "+e.getMessage(),e);
		}	
		if(exportFacility != null && !exportFacility.equals(""))
			sb.append(localId).append(" has been successfully submitted for transfer from ").append(oldJurisdDesc).append(" to ").append(newJurisdDesc).append(".");
		else
			sb.append(localId).append(" has been successfully transferred from ").append(oldJurisdDesc).append(" Jurisdiction to ").append(newJurisdDesc).append(" Jurisdiction.");
		req.getSession().setAttribute("DSFileTab","3");
		req.getSession().setAttribute("pamTOwnershipConfMsg", sb.toString());
		String returnURL = url.substring(url.indexOf("\"")+1, url.indexOf(">")-1);
		if(NedssUtils.isLocalPath(returnURL) && allowedUrls.contains(returnURL)) res.sendRedirect(returnURL);
		return null;
	}
	
	
    private static Long setTransferOwnership(Long investigationUID, PamForm form,
			String newJurisdictionCode, HttpServletRequest req)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {

    	PamProxyVO pamProxyVO = form.getPamClientVO().getOldPamProxyVO();
    	String pamFormCd = form.getPamFormCd();
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
		String sMethod = "transferOwnership";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(req.getSession());
		}
    	Object obj = new Object();
    	
		try {			
			Map<Object,Object> questionMap = (Map<Object,Object>) QuestionsCache.getQuestionMap().get(pamFormCd);
			obj =  questionMap.get(PamConstants.JURISDICTION);
			
		} catch (Exception e) {
			logger.error("Error in TransferOwnershipSubmitInv while fetching TUB237 from QuestionMap: " + e.toString());
		}
    	
    	Object[] oParams = { investigationUID, pamProxyVO, newJurisdictionCode, null};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
	}
	

    private static Long setExportOwnership(PublicHealthCaseVO publicHealthCaseVO, String exportFacility, String comment,
			String newJurisdictionCode, HttpServletRequest request)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppConcurrentDataException, Exception {
		NotificationProxyVO notProxyVO =InvestigationUtil.setShareNotificationProxyVO(publicHealthCaseVO, exportFacility, comment, newJurisdictionCode, request);
		notProxyVO.getTheNotificationVO().getTheNotificationDT().setCd(NEDSSConstants.CLASS_CD_EXP_NOTF);

		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
		String sMethod = "exportOwnership";
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		
		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
		}
    	Object[] oParams = { notProxyVO, newJurisdictionCode};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
	}

	

	
}