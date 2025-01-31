package gov.cdc.nedss.webapp.nbs.action.pam.lab;

import gov.cdc.nedss.entity.observation.dt.CodedResultDT;
import gov.cdc.nedss.entity.observation.dt.LoincResultDT;
import gov.cdc.nedss.entity.observation.dt.ObservationNameDT;
import gov.cdc.nedss.entity.observation.util.DisplayObservationList;
import gov.cdc.nedss.entity.observation.vo.LoincSrchResultVO;
import gov.cdc.nedss.entity.observation.vo.ObservationSrchResultVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
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

public class LabSingleSelectLink extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(LabSingleSelectLink.class.getName());
	
	/**
	 * labTestSearchLoad: shows resulted test popup screen, or organism popup screen, etc, depending on the question identifier.
	 * In order to show the Resulted test screen, question_identifier = LAB100
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		String forward = "";
		try {
			LabResultedTestForm manageForm = (LabResultedTestForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("SEARCHLIST", "LOCAL");
			
			String questionIdentifier = request.getParameter("identifier");
			
			request.setAttribute("identifier", questionIdentifier);
			
			//The following code might be used in future, but for now, we are showing one screen of another depending on the exact
			//question identifier. For Resulted test: LAB100. For my testing purpose, I'll change it to GA26000 and GA26001
			//But it needs to be changed to LAB100;
			
			/*
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "getCodeSetGroupIdFromQuestionIdentifier";
			Object[] oParams = new Object[]{questionIdentifier};
			HttpSession session = request.getSession(false);
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			String codeSetGroupId=(String)arrList.get(0);
			*/
			//If the code_set_group_id of the questionIdentifier == 6310, then open the Resulted test
			
			if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_LAB220))
				forward = "labResultedTestSearch";
			if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORGANISM_LAB278))
				forward = "organismSearch";
			if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_LAB112))
				forward = "orderedTestSearch";
			if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.DRUGD_TEST_LAB110))
				forward = "drugTestSearch";
			
			
		} catch (Exception e) {
			logger.error("Exception in labTestSearchLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} 
		
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.LABTEST_SEARCH);
		return (mapping.findForward(forward));
		
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
			String id = request.getParameter("identifier");
			updateActivityLogLinks(resultedTestList, searchType, id);
			request.setAttribute("identifier", id);
			request.setAttribute("resultedTestList", resultedTestList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("SearchCriteria", testName);
			request.setAttribute("ResultsCount", resultedTestList.size());
			request.setAttribute("RefineSearchLink", "SingleSelectLinkPB.do?method=refineSearch&identifier="+id);
	        request.setAttribute("NewSearchLink", "SingleSelectLinkPB.do?method=searchLoad&identifier="+id);
			
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
	
	
	
	
	/**
	 * organismSearchSubmit: search from the Organism single select and returns the results that will be shown in the page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
public ActionForward organismSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
			labResultedTestForm.setActionMode(null);
			PropertyUtil propertyUtil= PropertyUtil.getInstance();
			
			String clia = (String)request.getParameter("labId");
			if(clia==null)
				clia="DEFAULT";//The legacy page contained a hidden labId element which value was DEFAULT
			Long labId = null;
			Integer cacheNumber = new Integer(propertyUtil.getNumberOfRows());
			//TODO: CHANGE THIS!!
		    if (request.getParameter("Org-ReportingOrganizationUID") != null){
					labId = new Long((String)request.getParameter("Org-ReportingOrganizationUID"));
				}
		    
		    
			String testName = labResultedTestForm.getSearchCriteria("LABTEST");
			String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "findOrganismsByName";
			Object[] oParams = new Object[]{clia, labId, testName, searchType, cacheNumber, 0};
			HttpSession session = request.getSession(false);
			
	
                    
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
			
			String id = request.getParameter("identifier");
			request.setAttribute("identifier", id);
			DisplayObservationList list = new DisplayObservationList();
					
			if(resultedTestList.size()>0){
				
				list = (DisplayObservationList)resultedTestList.get(0);
						 
			}
			
			
			
			ArrayList<Object> organismList = updateOrganismList(list.getPersonListsInList());
			updateActivityLogLinks(organismList, searchType, id);
			
			
			request.setAttribute("resultedTestList", organismList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("SearchCriteria", HTMLEncoder.encodeHtml(testName));
			request.setAttribute("ResultsCount", list.getPersonListsInList().size());
			request.setAttribute("RefineSearchLink", "SingleSelectLinkPB.do?method=refineSearch&identifier="+id);
	        request.setAttribute("NewSearchLink", "SingleSelectLinkPB.do?method=searchLoad&identifier="+id);
			
//			labResultedTestForm.getAttributeMap().put("resultedTestList",
//					resultedTestList);
			labResultedTestForm.getAttributeMap().put("queueCount",
					String.valueOf(resultedTestList.size()));
		} catch (Exception e) {
			logger.error("Exception in labTestSearchSubmit: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("labOrganismResults"));
		} 
		
		return (mapping.findForward("labOrganismResults"));
		
	}
	

public ActionForward orderedTestSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	try {
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.setActionMode(null);
		PropertyUtil propertyUtil= PropertyUtil.getInstance();
		
		String labId = (String)request.getParameter("labId");

		Long labIdNumber = null;
		String type = "";
		Integer cacheNumber = new Integer(propertyUtil.getNumberOfRows());
		String clia = "";
		//TODO: CHANGE THIS!!
	   // if (request.getParameter("Org-ReportingOrganizationUID") != null){
		//		labId = new Long((String)request.getParameter("Org-ReportingOrganizationUID"));
			//}
		
	    if(labId!=null && labId!="" && labId!="undefined" && labId.indexOf("|")!=-1 ){
	    	labId=labId.substring(0,labId.indexOf("|"));
	    	labIdNumber = Long.parseLong(labId);
	    	
	    	clia = PageLoadUtil.getCliaValue(labId, request.getSession());
	    	
	    }
	    
	    
	    String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");
	/*	if (searchType.equals("Ordered") || searchType.equals("Resulted")){
			type = searchType + "TestName";
		}else{
			type = searchType + "sByName";
		}
		
		*/
		String testName = labResultedTestForm.getSearchCriteria("LABTEST");
		//String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod = "findOrderedTestName";
		Object[] oParams = new Object[]{clia, labIdNumber, testName, searchType, cacheNumber, 0};
		HttpSession session = request.getSession(false);
		

                
		MainSessionHolder holder = new MainSessionHolder();
		MainSessionCommand msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
		
		String id =request.getParameter("identifier");
		
		DisplayObservationList list = new DisplayObservationList();
				
		if(resultedTestList.size()>0){
			
			list = (DisplayObservationList)resultedTestList.get(0);
					 
		}
		
		
		
		ArrayList<Object> organismList = updateOrderedTestList(list.getPersonListsInList(), searchType);
		updateActivityLogLinks(organismList, searchType, id);
		
		request.setAttribute("identifier",id);
		request.setAttribute("resultedTestList", organismList);
		request.setAttribute("searchType", searchType);
		request.setAttribute("SearchCriteria",testName);
		request.setAttribute("ResultsCount", list.getPersonListsInList().size());
		request.setAttribute("RefineSearchLink", "SingleSelectLinkPB.do?method=refineSearch&identifier="+id);
        request.setAttribute("NewSearchLink", "SingleSelectLinkPB.do?method=searchLoad&identifier="+id);
		
//		labResultedTestForm.getAttributeMap().put("resultedTestList",
//				resultedTestList);
		labResultedTestForm.getAttributeMap().put("queueCount",
				String.valueOf(resultedTestList.size()));
	} catch (Exception e) {
		logger.error("Exception in labTestSearchSubmit: " + e.getMessage());
		request.setAttribute("error", e.getMessage());
		return (mapping.findForward("labOrderedTestResults"));
	} 
	
	return (mapping.findForward("labOrderedTestResults"));
	
}

public ActionForward drugTestSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	try {
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.setActionMode(null);
		PropertyUtil propertyUtil= PropertyUtil.getInstance();
		
		String clia = (String)request.getParameter("labId");

		if(clia==null)
			clia="DEFAULT";//The legacy page contained a hidden labId element which value was DEFAULT
		
		
		Long labId = null;
		String type = "";
		Integer cacheNumber = new Integer(propertyUtil.getNumberOfRows());
		//TODO: CHANGE THIS!!
	    if (request.getParameter("Org-ReportingOrganizationUID") != null){
				labId = new Long((String)request.getParameter("Org-ReportingOrganizationUID"));
			}
	    
	    
	    String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");

		String testName = labResultedTestForm.getSearchCriteria("LABTEST");
		//String searchType = labResultedTestForm.getSearchCriteria("SEARCHLIST");
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod = "findDrugTestNameOrCode";
		Object[] oParams = new Object[]{clia, labId, testName, searchType, cacheNumber, 0};
		HttpSession session = request.getSession(false);
         
		MainSessionHolder holder = new MainSessionHolder();
		MainSessionCommand msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
		
		String id =request.getParameter("identifier");
		request.setAttribute("identifier", id);
		
		DisplayObservationList list = new DisplayObservationList();
				
		if(resultedTestList.size()>0){
			
			list = (DisplayObservationList)resultedTestList.get(0);
					 
		}
		
		
		
		ArrayList<Object> organismList = updateOrderedTestList(list.getPersonListsInList(), searchType);
		updateActivityLogLinks(organismList, searchType, id);
		
		
		request.setAttribute("resultedTestList", organismList);
		request.setAttribute("searchType", searchType);
		request.setAttribute("SearchCriteria",testName);
		request.setAttribute("ResultsCount", list.getPersonListsInList().size());
		request.setAttribute("RefineSearchLink", "SingleSelectLinkPB.do?method=refineSearch&identifier="+id);
        request.setAttribute("NewSearchLink", "SingleSelectLinkPB.do?method=searchLoad&identifier="+id);
		
//		labResultedTestForm.getAttributeMap().put("resultedTestList",
//				resultedTestList);
		labResultedTestForm.getAttributeMap().put("queueCount",
				String.valueOf(resultedTestList.size()));
	} catch (Exception e) {
		logger.error("Exception in drugTestSearchSubmit: " + e.getMessage());
		request.setAttribute("error", e.getMessage());
		return (mapping.findForward("labDrugTestResults"));
	} 
	
	return (mapping.findForward("labDrugTestResults"));
	
}


public ArrayList<Object>  updateOrganismList(ArrayList<Object> listOrg){
	
	ArrayList<Object> organismList = new ArrayList<Object>();
	
	for(int i=0; i< listOrg.size(); i++){
		ObservationSrchResultVO obs = (ObservationSrchResultVO)listOrg.get(i);
		ObservationNameDT obsNameDT = (ObservationNameDT)((ArrayList<Object>)obs.getPersonNameColl()).get(0);
		organismList.add(obsNameDT);
	}
	return organismList;
}

public ArrayList<Object> updateOrderedTestList(ArrayList<Object> listOrg, String type){
	
	ArrayList<Object> orderedTestList = new ArrayList<Object>();
	
	if(type!=null && type.equalsIgnoreCase("LOCAL")){
		for(int i=0; i< listOrg.size(); i++){
			ObservationSrchResultVO loinc = (ObservationSrchResultVO)listOrg.get(i);
			ObservationNameDT obsNameDT = (ObservationNameDT)((ArrayList<Object>)loinc.getPersonNameColl()).get(0);
			orderedTestList.add(obsNameDT);
		}
	}
	else{//LOINC
		for(int i=0; i< listOrg.size(); i++){
			LoincSrchResultVO loinc = (LoincSrchResultVO)listOrg.get(i);
			LoincResultDT obsNameDT = (LoincResultDT)((ArrayList<Object>)loinc.getLoincColl()).get(0);
			orderedTestList.add(obsNameDT);
		}
	}
	return orderedTestList;
}



public ActionForward codedResultSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
			labResultedTestForm.setActionMode(null);
			
			String testName = labResultedTestForm.getSearchCriteria("CODEDRESULT");
			String searchType = labResultedTestForm.getSearchCriteria("CODESEARCHLIST");
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "findLabCodedResult";
			Object[] oParams = new Object[]{"DEFAULT", null, testName, searchType};
			HttpSession session = request.getSession(false);
			MainSessionHolder holder = new MainSessionHolder();
			MainSessionCommand msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			ArrayList<?> resultedTestList = (ArrayList<?> )arrList.get(0);
			String id = request.getParameter("identifier");
			updateActivityLogLinks(resultedTestList, searchType, id);
			request.setAttribute("identifier", id);
			request.setAttribute("resultedTestList", resultedTestList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("SearchCriteria", testName);
			request.setAttribute("ResultsCount", resultedTestList.size());
			request.setAttribute("RefineSearchLink", "SingleSelectLinkPB.do?method=refineCodeSearch&identifier="+id);
	        request.setAttribute("NewSearchLink", "SingleSelectLinkPB.do?method=searchCodeLoad&identifier="+id);
			
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
	
	private void updateActivityLogLinks(ArrayList resultedTestList, String searchType, String id)
	{
		Iterator<Object> ite = resultedTestList.iterator();
		while (ite.hasNext()) {
			if(NEDSSConstants.TEST_TYPE_LOCAL.equals(searchType))//Resulted test
			{
				ObservationNameDT  resultedTestVO = (ObservationNameDT) ite.next();			
				String url = "<a href=\"javascript:populateCodedWithSearchValue('" + resultedTestVO.getLabTestDescription() + "','" + resultedTestVO.getLabTestCd() + "','"+id+"');\">"+resultedTestVO.getLabTestCd()+"</a>"; 
				resultedTestVO.setRecordStatusCd(url);
			}else if(NEDSSConstants.TEST_TYPE_LOINC.equals(searchType))
			{
				LoincResultDT  loincResultDT = (LoincResultDT) ite.next();			
				String url = "<a href=\"javascript:populateCodedWithSearchValue('" + loincResultDT.getLoincComponentName() + "','" + loincResultDT.getLoincCd() + "','"+id+"');\">"+loincResultDT.getLoincCd() +"</a>"; 
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
		String questionIdentifier = request.getParameter("identifier");
		String forward = "";
		request.setAttribute("identifier", questionIdentifier);
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_LAB220))
			forward = "labResultedTestSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORGANISM_LAB278))
			forward = "organismSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_LAB112))
			forward = "orderedTestSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.DRUGD_TEST_LAB110))
			forward = "drugTestSearch";
		
		
		
		return (mapping.findForward(forward));
	}
	
	public ActionForward refineCodeSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		return (mapping.findForward("labCodeResultSearch"));
	}
	
	public ActionForward searchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		  
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.clearSelections();
		labResultedTestForm.setSearchCriteria("SEARCHLIST", "LOCAL");
		
		
		String questionIdentifier = request.getParameter("identifier");
		request.setAttribute("identifier", questionIdentifier);
		String forward = "";
		
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_LAB220))
			forward = "labResultedTestSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORGANISM_LAB278))
			forward = "organismSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_LAB112))
			forward = "orderedTestSearch";
		if(questionIdentifier.equalsIgnoreCase(NEDSSConstants.DRUGD_TEST_LAB110))
			forward = "drugTestSearch";
		
		return (mapping.findForward(forward));
	}
	
	public ActionForward searchCodeLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		  
		LabResultedTestForm labResultedTestForm = (LabResultedTestForm) form;
		labResultedTestForm.clearSelections();
		labResultedTestForm.setSearchCriteria("CODESEARCHLIST", "LOCALRESULT");
		return (mapping.findForward("labCodeResultSearch"));
	}
}
