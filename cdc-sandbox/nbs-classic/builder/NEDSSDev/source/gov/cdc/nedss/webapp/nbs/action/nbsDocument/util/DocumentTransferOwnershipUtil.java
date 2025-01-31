package gov.cdc.nedss.webapp.nbs.action.nbsDocument.util;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.nbsdocument.NbsDocumentForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DocumentTransferOwnershipUtil {

	static final LogUtils logger = new LogUtils(DocumentTransferOwnershipUtil.class.getName());
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	
	public static void loadTransferOwnership(NbsDocumentForm form, HttpServletRequest request){
		NbsDocumentForm docForm = (NbsDocumentForm)form;
		NBSDocumentDT nbsDocDt = docForm.getNbsDocumentDt();
			docForm.clearSelections();
			String DSDocumentLocalID = nbsDocDt.getLocalId();
			String  DSDocumentUid = nbsDocDt.getNbsDocumentUid().toString();
			String progAreaCd = nbsDocDt.getProgAreaCd();
			String progAreaDesc = null;
			if(progAreaCd != null)
				progAreaDesc = cdv.getProgramAreaDesc(progAreaCd);
			String jurisCd = nbsDocDt.getJurisdictionCd();
			String jurisDesc = null;
			if(jurisCd != null)
				jurisDesc = cdv.getJurisdictionDesc(jurisCd);
			docForm.getAttributeMap().put("DSDocumentLocalID", DSDocumentLocalID);
			if(jurisDesc != null)
			{
				docForm.getSummaryDt().setJurisdiction(jurisCd);
				docForm.getAttributeMap().put("oldJurisdiction", jurisDesc);
			}
			if(progAreaDesc != null)
			{
				docForm.getSummaryDt().setProgramArea(progAreaDesc);
				docForm.getAttributeMap().put("oldProgramAreaCd",progAreaCd);
				docForm.getAttributeMap().put("oldProgramArea", progAreaDesc);
			}
			docForm.getAttributeMap().put("DSDocumentUid", DSDocumentUid);
			request.setAttribute("DSDocumentLocalID", DSDocumentLocalID);
			if(jurisDesc != null)
				request.setAttribute("oldJurisdiction", jurisDesc);
			if(progAreaDesc != null)
				request.setAttribute("oldProgramArea", progAreaDesc);	
	}
	
	public ActionForward transferOwnershipSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		NbsDocumentForm docForm = (NbsDocumentForm)form;
		ActionForward forward;
		try {
			forward = storeTransferOwnership(mapping, docForm, request, response);
			
		} catch (Exception e) {
			logger.error("Error while transferOwnership for investigation Case: " + e.toString());
			throw new ServletException("Error while transferOwnership for investigation Case: "+e.getMessage(),e);
		}		
		return forward;
	}	
	
	public static ActionForward storeTransferOwnership(ActionMapping mapping, NbsDocumentForm form, HttpServletRequest request, HttpServletResponse res) throws Exception {
		NbsDocumentForm docForm = (NbsDocumentForm)form;
		NBSDocumentVO nbsDocVo = new NBSDocumentVO();
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		String contextAction = "";
		String clickHereLink = null;
		HttpSession session = request.getSession();
		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS233", contextAction);
		Long personUID = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
		String documentUID = HTMLEncoder.encodeHtml((String) docForm.getAttributeMap().get("DSDocumentUid"));
		String url = null;
		if(currentTask.equalsIgnoreCase("ViewDocument1"))
		{
			url = "/nbs/LoadObsNeedingAssignment1.do?method=loadQueue&ContextAction=" + tm.get("ReturnToObservationsNeedingAssignment");
			clickHereLink = "/nbs/LoadViewFile1.do?ContextAction=ViewFile";  
		}else if(currentTask.equalsIgnoreCase("ViewDocument2"))
		{
			url = "/nbs/LoadNewLabReview1.do?method=loadQueue&ContextAction="+tm.get("TransferOwnership");
			clickHereLink = "/nbs/NewLabReview1.do?ContextAction=" + tm.get("ViewFile")+"&MPRUid="+personUID+"&observationUID="+documentUID;
		}
		else
		{	
			contextAction = NEDSSConstants.ReturnToFileSummary;
			url = "/nbs/LoadViewFile1.do?ContextAction=" + contextAction;	
		}
		String oldJurisdDesc = (String) docForm.getAttributeMap().get("oldJurisdiction");
		String newJurisd = request.getParameter("juris") == null ? "" : (String)request.getParameter("juris"); 
		String newJurisdDesc = cdv.getJurisdictionDesc(newJurisd);
		String newProgArea = request.getParameter("progArea") == null ? "" : (String)request.getParameter("progArea");
		if(newProgArea.equals("null"))
			newProgArea = (String) docForm.getAttributeMap().get("oldProgramAreaCd");
		String newProgAreaDesc = cdv.getProgramAreaDesc(newProgArea);
		String docUid = docForm.getAttributeMap().get("DSDocumentUid") == null ? "" : (String) docForm.getAttributeMap().get("DSDocumentUid");
		Long DSDocumentUid = Long.valueOf(docUid) ;
		String DSDocumentLocalID = docForm.getAttributeMap().get("DSDocumentLocalID") == null ? "" : (String) docForm.getAttributeMap().get("DSDocumentLocalID"); 
		StringBuffer sb = new StringBuffer();
		String firstNm = null;
		String lastNm = null;
		
		
		try {
			nbsDocVo = setTransferOwnership(DSDocumentUid, newJurisd,newProgArea, session);
			firstNm = nbsDocVo.getPatientVO().getThePersonDT().getFirstNm() == null ? "" : nbsDocVo.getPatientVO().getThePersonDT().getFirstNm();
			lastNm = nbsDocVo.getPatientVO().getThePersonDT().getLastNm() == null ? "" : nbsDocVo.getPatientVO().getThePersonDT().getLastNm();
		}
		catch (gov.cdc.nedss.exception.NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",e);
			return mapping.findForward("dataerror");
		}

		catch (Exception e) {
			logger.fatal("ERROR , The error has occured, please recheck! ", e);
			throw new ServletException("ERROR , The error has occured, please recheck! "+e.getMessage(),e);
		}	
		if(currentTask.equalsIgnoreCase("ViewDocument2"))
		{
			sb.append("Case Report ").append(DSDocumentLocalID).append(" for ").append(firstNm).append(" ").append(lastNm).append(" has been successfully transferred to ")
				.append(newJurisdDesc).append(" jurisdiction with a program area of ").append(newProgAreaDesc).append(".").append(" ");
					request.getSession().setAttribute("clickHereLink", clickHereLink);
			
		}else if(currentTask.equalsIgnoreCase("ViewDocument1"))
		{
			sb.append("Case Report ").append(DSDocumentLocalID).append(" for ").append(firstNm).append(" ").append(lastNm).append(" has been successfully transferred to ")
			.append(newJurisdDesc).append(" jurisdiction with a program area of ").append(newProgAreaDesc).append(".").append(" ");
			request.getSession().setAttribute("clickHereLink", clickHereLink);
		
		}
		else
		{
			sb.append("Case Report ").append(DSDocumentLocalID).append(" has been successfully transferred from ").append(oldJurisdDesc).append(" Jurisdiction to ").append(newJurisdDesc).append(" Jurisdiction.");
		}
		request.getSession().setAttribute("docTOwnershipConfMsg", HTMLEncoder.encodeHtml(sb.toString()));
		request.getSession().setAttribute("DSFileTab","3");
		res.sendRedirect(url);
		return null;

	}
	
	private static NBSDocumentVO setTransferOwnership(Long nbsDocUid, String newJurisdictionCode,String newProgAreaCode, HttpSession ses)
			throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSAppConcurrentDataException, Exception
		{
			NBSDocumentDT nbsDocumentDT = null;
			NBSDocumentDT newNbsDocumentDT = null;
			
			NBSDocumentActionUtil util = new NBSDocumentActionUtil();
			 NBSDocumentVO nbsDocVo = new NBSDocumentVO();
			//nbsDocumentDT 
			try{
				nbsDocVo = util.getNBSDocument(ses,nbsDocUid);
			}
			catch(Exception e){
				 throw new ServletException(e.toString());
			 }
			
			nbsDocumentDT = nbsDocVo.getNbsDocumentDT();
			Long DSPatientPersonUID= nbsDocVo.getPatientVO().getThePersonDT().getPersonParentUid();
			if(DSPatientPersonUID!=null)
				NBSContext.store(ses, NBSConstantUtil.DSPatientPersonUID, DSPatientPersonUID);
			
			logger.info("user has add permissions for setDocumentProxy");
				
			nbsDocumentDT.setItDirty(true);
			nbsDocumentDT.setJurisdictionCd(newJurisdictionCode);
			if(newProgAreaCode != null)
				nbsDocumentDT.setProgAreaCd(newProgAreaCode);
			nbsDocVo.setFromSecurityQueue(true);
			//nbsDocVo.getNbsDocumentDT().setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
			Date dat = new Date();
			Timestamp aupdateTime = new Timestamp(dat.getTime());
			nbsDocVo.getNbsDocumentDT().setRecordStatusTime(aupdateTime);
			nbsDocVo.getNbsDocumentDT().setLastChgTime(aupdateTime);
			nbsDocVo = util.setNBSDocumentVOforUpdate(nbsDocVo);
			try{
				util.setDocumentforUpdateforTransferOwnership(ses,nbsDocVo);
			}catch(Exception e){
				throw new ServletException(e.toString());
			}
			return nbsDocVo;
	}


}
