package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement;

import gov.cdc.nedss.entity.observation.dt.CodedResultDT;
import gov.cdc.nedss.entity.observation.dt.LoincResultDT;
import gov.cdc.nedss.entity.observation.dt.ObservationNameDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.LabResultedTestForm;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LabResultedTestLink extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(LabResultedTestLink.class.getName());
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm manageForm = (LabResultedTestForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("SEARCHLIST", "LOCAL");
		} catch (Exception e) {
			logger.error("Exception in labTestSearchLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} 
		
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.LABTEST_SEARCH);
		return (mapping.findForward("labResultedTestSearch"));
		
	}
	
public ActionForward resultCodeSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm manageForm = (LabResultedTestForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("CODESEARCHLIST", "LOCALRESULT");
		} catch (Exception e) {
			logger.error("Exception in resultCodeSearchLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} 
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CODERESULT_SEARCH);
		return (mapping.findForward("labCodeResultSearch"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
			labResultedTestForm.setActionMode(null);
			
			String testName = labResultedTestForm.getSearchCriteria("LABTEST");
			String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "findLabResultedTestName";
			Object[] oParams = new Object[]{"DEFAULT", null, testName, searchType};
			HttpSession session = request.getSession(false);
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
			
			updateActivityLogLinks(resultedTestList, searchType);
			
			request.setAttribute("resultedTestList", resultedTestList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("SearchCriteria", testName);
			request.setAttribute("ResultsCount", resultedTestList.size());
			request.setAttribute("RefineSearchLink", "LabResultedTestLink.do?method=refineSearch");
	        request.setAttribute("NewSearchLink", "LabResultedTestLink.do?method=searchLoad");
			
//			labResultedTestForm.getAttributeMap().put("resultedTestList",
//					resultedTestList);
			labResultedTestForm.getAttributeMap().put("queueCount",
					String.valueOf(resultedTestList.size()));
		} catch (Exception e) {
			logger.error("Exception in labTestSearchSubmit: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("labResultedTestSearch"));
		} 
		
		return (mapping.findForward("labResultedTestResults"));
		
	}
	
public ActionForward codedResultSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
			labResultedTestForm.setActionMode(null);
			
			String testName = HTMLEncoder.encodeHtml(labResultedTestForm.getSearchCriteria("CODEDRESULT"));
			String searchType = HTMLEncoder.encodeHtml(labResultedTestForm.getSearchCriteria("CODESEARCHLIST"));
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "findLabCodedResult";
			Object[] oParams = new Object[]{"DEFAULT", null, testName, searchType};
			HttpSession session = request.getSession(false);
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
			
			updateActivityLogLinks(resultedTestList, searchType);
			
			request.setAttribute("resultedTestList", resultedTestList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("SearchCriteria", testName);
			request.setAttribute("ResultsCount", resultedTestList.size());
			request.setAttribute("RefineSearchLink", "LabResultedTestLink.do?method=refineCodeSearch");
	        request.setAttribute("NewSearchLink", "LabResultedTestLink.do?method=searchCodeLoad");
			
//			labResultedTestForm.getAttributeMap().put("resultedTestList",
//					resultedTestList);
			labResultedTestForm.getAttributeMap().put("queueCount",
					String.valueOf(resultedTestList.size()));
		} catch (Exception e) {
			logger.error("Exception in codedResultSearchSubmit: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("labCodeResultSearch"));
		} 
		
		return (mapping.findForward("labResultedTestResults"));
		
	}
	
	private void updateActivityLogLinks(ArrayList resultedTestList, String searchType)
	{
		Iterator<Object> ite = resultedTestList.iterator();
		while (ite.hasNext()) {
			if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType))
			{
				ObservationNameDT  resultedTestVO = (ObservationNameDT) ite.next();			
				String url = "<a href=\"javascript:populateResultedTestName('" + resultedTestVO.getLabTestDescription() + "','" + resultedTestVO.getLabTestCd() + "');\">"+resultedTestVO.getLabTestCd()+"</a>"; 
				resultedTestVO.setRecordStatusCd(url);
			}else if(NEDSSConstants.TEST_TYPE_LOINC.equals(searchType))
			{
				LoincResultDT  loincResultDT = (LoincResultDT) ite.next();			
				String url = "<a href=\"javascript:populateResultedTestName('" + loincResultDT.getLoincComponentName() + "','" + loincResultDT.getLoincCd() + "');\">"+ loincResultDT.getLoincCd() +"</a>"; 
				loincResultDT.setRecordStatusCd(url);
			}else if(NEDSSConstants.RESULT_TYPE_LOCAL.equals(searchType))
			{
				CodedResultDT  resultDT = (CodedResultDT) ite.next();			
				String url = "<a href=\"javascript:populateCodedResultName('" + resultDT.getLabResultDescription() + "','" + resultDT.getLabResultCd() + "');\">Select</a>"; 
				resultDT.setRecordStatusCd(url);
			}else if(NEDSSConstants.RESULT_TYPE_SNOMED.equals(searchType))
			{
				CodedResultDT  resultDT = (CodedResultDT) ite.next();			
				String url = "<a href=\"javascript:populateCodedResultName('" + resultDT.getSnomedDescTxt() + "','" + resultDT.getSnomedCd() + "');\">Select</a>"; 
				resultDT.setRecordStatusCd(url);
			}
		} 
	}
	
	public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		return (mapping.findForward("labResultedTestSearch"));
	}
	
	public ActionForward refineCodeSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		return (mapping.findForward("labCodeResultSearch"));
	}
	
	public ActionForward searchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		  
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.clearSelections();
		labResultedTestForm.setSearchCriteria("SEARCHLIST", "LOCAL");
		return (mapping.findForward("labResultedTestSearch"));
	}
	
	public ActionForward searchCodeLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		  
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.clearSelections();
		labResultedTestForm.setSearchCriteria("CODESEARCHLIST", "LOCALRESULT");
		return (mapping.findForward("labCodeResultSearch"));
	}
}
