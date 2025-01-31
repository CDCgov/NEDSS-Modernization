package gov.cdc.nedss.webapp.nbs.action.iisquery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSObjectStore;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.vaccination.iis.dt.PatientSearchResultDT;
import gov.cdc.nedss.vaccination.iis.dt.VaccinationSearchResultDT;
import gov.cdc.nedss.webapp.nbs.action.iisquery.util.IISQueryUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.iisquery.IISQueryForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class IISQueryAction extends DispatchAction{
	static final LogUtils logger = new LogUtils(IISQueryAction.class.getName());
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	PropertyUtil properties = PropertyUtil.getInstance();
	PortPageUtil portPageUtil = new PortPageUtil();
	
	public ActionForward loadPatientIISQueryPopUp(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		try{
			IISQueryForm iisQueryForm = (IISQueryForm)form;
			
			HttpSession session = request.getSession();
			
			PatientSearchVO patientSearch = new PatientSearchVO();
			
			IISQueryUtil.populatePatientIISQueryPopUp(patientSearch, session);
			
			iisQueryForm.setPersonSearch(patientSearch);
			
	    	return mapping.findForward("patientIISQueryPopUp");
		}catch(Exception e){
    		logger.fatal("Exception :"+e.getMessage(), e);
    		throw new ServletException(e.getMessage());
    	}
    }
	
	public ActionForward searchPatientFromIIS(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		IISQueryForm iisQueryForm = (IISQueryForm)form;
		try{
			
			Collection<Object> patientSearchList = new ArrayList<Object>();
			QueueDT queueDT = IISQueryUtil.fillPatientSearchResultQueueDT();
			iisQueryForm.setQueueDT(queueDT);
			ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
			iisQueryForm.setQueueCollection(queueCollection);
	    	String queueString = genericQueueUtil.convertToString(queueDT);
	    	iisQueryForm.setStringQueueCollection(queueString);
	    	request.setAttribute("queueCollection",queueCollection);
	    	request.setAttribute("stringQueueCollection",queueString);
	    	
	    	boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				iisQueryForm.clearAll();
	    		request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));	// selects all the check boxes.
			}
						
			String qtyLimitedRequest = properties.getIISPatientSearchMaxQuantity();
			
			//converting PatientSearchVO to PatientSearchResultDT, building Q34 query using PatientSearchResultDT
			PatientSearchResultDT patient = IISQueryUtil.getPatientSearchResultDTForIISQuery(iisQueryForm.getPersonSearch());
			String queryResponseStr = IISQueryUtil.searchPatientFromIIS(patient, null, null, null, null, IISQueryUtil.PATIENT, qtyLimitedRequest);
			String errorResponse = IISQueryUtil.checkForErrorResponse(queryResponseStr, iisQueryForm);
			
			String translatedErrorResponse = "";
			if(IISQueryUtil.NO_ERROR.equals(errorResponse) || "OK".equals(errorResponse)){
				IISQueryUtil.extractPIDandProcess(queryResponseStr, patientSearchList);
				request.setAttribute("isError",false);
			}else{
				translatedErrorResponse = IISQueryUtil.tralateErrorResponse(errorResponse, iisQueryForm.getErrorAttributeMap(), qtyLimitedRequest);
				request.setAttribute("feedbackErroMessage",translatedErrorResponse);
				request.setAttribute("isError",true);
				
				if("TM".equals(errorResponse)){
					request.setAttribute("feedbackErroMessage",translatedErrorResponse);
					IISQueryUtil.extractPIDandProcess(queryResponseStr, patientSearchList);
					if(patientSearchList.size()>0 && patientSearchList.size()<Integer.parseInt(qtyLimitedRequest)){
						request.setAttribute("isError",false);
						request.setAttribute("feedbackErroMessage","");
					}
				}
				
				//Not actual exception, setting error response as exception text
				String exceptionTxt = errorResponse+" - "+translatedErrorResponse;
				EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", exceptionTxt, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_SOURCE_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_MESSAGE_ID), iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_TYPE), IISQueryUtil.IIS_DOC_TYPE);
				PortPageUtil util = new PortPageUtil();
				util.createEdxActivityLog(edxActivityLogDT, request);
				
				if("MSA".equals(iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_SEGMENT))){
					request.setAttribute("infoBox","errors");
				}else{
					request.setAttribute("infoBox","info");
				}
			}
			
	    	GenericForm genericForm= IISQueryUtil.translateFromPatientSearchResultDTToGenericSummaryDisplayVO(iisQueryForm);
	    	
	    	boolean existing = request.getParameter("existing") == null ? false : true;
			String defaultSort = "getRegistryPatientID";
			genericQueueUtil.sortQueue(genericForm, patientSearchList, existing, request,defaultSort);
			
			if(existing)
				genericQueueUtil.filterQueue(genericForm, request,iisQueryForm.getCLASS_NAME());
			
			//To make sure SelectAll is checked, see if no criteria is applied
			if(iisQueryForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			
			iisQueryForm.setPatientSearchList(patientSearchList);
			iisQueryForm.getAttributeMap().put("queueCount", String.valueOf(patientSearchList.size()));
			request.setAttribute("queueCount", String.valueOf(patientSearchList.size()));
			request.setAttribute("patientSearchList", patientSearchList);
			Integer queueSize = properties.getQueueSize(NEDSSConstants.IIS_PATIENT_SEARCH_RESULT_QUEUE_SIZE);
			iisQueryForm.getAttributeMap().put("queueSize",queueSize);
			
			iisQueryForm.initializeDropDowns(patientSearchList);
			
			Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
			iisQueryForm.setAttributeMap(map);
			
			PatientSearchVO patientSearch = iisQueryForm.getPersonSearch();
			if(patientSearch.getLastName()!=null)
				request.setAttribute("patientLastName", HTMLEncoder.encodeHtml(patientSearch.getLastName()));
			if(patientSearch.getBirthTimeMonth()!=null && patientSearch.getBirthTimeDay()!=null && patientSearch.getBirthTimeYear()!=null){
				request.setAttribute("patientDOB", patientSearch.getBirthTimeMonth()+"/"+patientSearch.getBirthTimeDay()+"/"+patientSearch.getBirthTimeYear());
			}
			if(patientSearchList!=null)
				request.setAttribute("patientSearchListSize", patientSearchList.size());
			
			request.setAttribute("RefineSearchLink", "IISQuery.do?method=refineSearch");
			
			String scString = IISQueryUtil.buildSearchCriteriaString(iisQueryForm.getPersonSearch());
	    	request.setAttribute("SearchCriteria", scString);
	    	
			return PaginationUtil.paginate(iisQueryForm,request,"patientIISQueryResult",mapping);
		}catch(Exception e){
    		logger.fatal("Exception :"+e.getMessage(), e);
    		
    		String errorMsgToWrite = IISQueryUtil.convertExceptionStackTraceToString(e);
			EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", errorMsgToWrite, null, null, null, iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
			PortPageUtil util = new PortPageUtil();
			util.createEdxActivityLog(edxActivityLogDT, request);
			String errorMsg = IISQueryUtil.GENERIC_ERROR_MSG_IN_CASE_OF_EXCEPTION;

			throw new NEDSSSystemException(errorMsg,e);
    	}
    }
	
	public ActionForward filterPatientSearchListSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		IISQueryForm iisQueryForm = (IISQueryForm)form;
		
		try{
			ArrayList<Object> patientSearchList = (ArrayList<Object> ) iisQueryForm.getPatientSearchList();
			iisQueryForm.setPatientSearchList(patientSearchList);
			GenericForm genericForm= IISQueryUtil.translateFromPatientSearchResultDTToGenericSummaryDisplayVO(iisQueryForm);
			Collection<Object>  filteredPatientSearchList = genericQueueUtil.filterQueue(genericForm, request ,iisQueryForm.getCLASS_NAME());
			
			if(filteredPatientSearchList==null){
				filteredPatientSearchList = iisQueryForm.getPatientSearchList();
			}
			
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search")); // Selects checkboxes based on applied filter
			iisQueryForm.getAttributeMap().put("patientSearchList",filteredPatientSearchList);
			iisQueryForm.getAttributeMap().put("queueList",filteredPatientSearchList);
			request.setAttribute("queueList", filteredPatientSearchList);
			request.setAttribute("patientSearchList", filteredPatientSearchList);
			request.setAttribute("queueCount", String.valueOf(filteredPatientSearchList.size()));
			iisQueryForm.getAttributeMap().put("queueCount", String.valueOf(filteredPatientSearchList.size()));
			iisQueryForm.getAttributeMap().put("PageNumber", "1");
			request.setAttribute("queueCollection",iisQueryForm.getQueueCollection());
            request.setAttribute("stringQueueCollection",iisQueryForm.getStringQueueCollection());
            request.setAttribute("RefineSearchLink", "IISQuery.do?method=refineSearch");
            
            String scString = IISQueryUtil.buildSearchCriteriaString(iisQueryForm.getPersonSearch());
	    	request.setAttribute("SearchCriteria", scString);
	    	
		}catch(Exception ex){
			logger.fatal("Exception: "+ ex.toString(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return PaginationUtil.paginate(iisQueryForm, request, "patientIISQueryResult",mapping);
		
	}
	
	public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
		try {
			IISQueryForm iisQueryForm = (IISQueryForm)form;

			Integer birthOrder = iisQueryForm.getPersonSearch().getBirthOrder();
			request.setAttribute("birthOrder", birthOrder);
			
			return (mapping.findForward("patientIISQueryPopUp"));
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage(), ex);
			throw new ServletException("Error while refineSearch: "+ex.getMessage(),ex);
		}
		
	}
	
	public ActionForward searchVaccinationsForPatientFromIIS(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		IISQueryForm iisQueryForm = (IISQueryForm)form;
		try{
			
			Collection<Object> vaccinationSearchList = new ArrayList<Object>();
			QueueDT queueDT = IISQueryUtil.fillVaccinationSearchResultQueueDT();
			iisQueryForm.setQueueDT(queueDT);
			ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
			iisQueryForm.setQueueCollection(queueCollection);
	    	String queueString = genericQueueUtil.convertToString(queueDT);
	    	iisQueryForm.setStringQueueCollection(queueString);
	    	request.setAttribute("queueCollection",queueCollection);
	    	request.setAttribute("stringQueueCollection",queueString);
	    	
	    	IISQueryUtil.setPatientDetailsForVaccinationResultHeader(request);
	    	String registryPatientIDStr = request.getParameter("registryPatientIDStr");
	    	String assigningAuthorities = request.getParameter("aa");
	    	String identifierTypeCodes = request.getParameter("itc");
	    	String assigningFacilities = request.getParameter("af");
	    	String registryPatientIDs = request.getParameter("ri");
	    	
	    	if(registryPatientIDs == null){ // When user clicks refine search then registryPatientIDs is null
	    		registryPatientIDs = iisQueryForm.getSelectedPatient().getListOfregistryPatientID();
	    		assigningAuthorities = iisQueryForm.getSelectedPatient().getAssigningAuthorities();
	    		identifierTypeCodes = iisQueryForm.getSelectedPatient().getIdentifierTypeCodes();
	    		assigningFacilities = iisQueryForm.getSelectedPatient().getAssigningFacilities();
	    	}
	    	
	    	String patientDOB = request.getParameter("dob");
	    	
	     	if(patientDOB!=null && patientDOB.contains("/")){
	     		StringTokenizer st1 = new StringTokenizer(patientDOB, "/"); 
	     		iisQueryForm.getPersonSearch().setBirthTimeMonth(st1.nextToken());
				iisQueryForm.getPersonSearch().setBirthTimeDay(st1.nextToken());
				iisQueryForm.getPersonSearch().setBirthTimeYear(st1.nextToken());
	     	}
	     	
	     	iisQueryForm.getPersonSearch().setMotherFirstName("");
	     	iisQueryForm.getPersonSearch().setMotherLastName("");
	     	
	     	for (Iterator iterator = iisQueryForm.getPatientSearchList().iterator(); iterator.hasNext();){
	    		PatientSearchResultDT patient = (PatientSearchResultDT) iterator.next();
	    		if(registryPatientIDStr!=null && registryPatientIDStr.equals(patient.getRegistryPatientID())){
	    			iisQueryForm.setSelectedPatient(patient);
	    			patient.setListOfregistryPatientID(registryPatientIDs);
	    			patient.setIdentifierTypeCodes(identifierTypeCodes);
	    			patient.setAssigningAuthorities(assigningAuthorities);
	    			patient.setAssigningFacilities(assigningFacilities);
	    		}
	    	}
	     	
	     	String qtyLimitedRequest = properties.getIISVaccinationSearchMaxQuantity();
	     	
	    	String queryResponseStr = IISQueryUtil.searchPatientFromIIS(iisQueryForm.getSelectedPatient(), registryPatientIDs, assigningAuthorities, identifierTypeCodes, assigningFacilities, IISQueryUtil.VACCINE, qtyLimitedRequest);
	    	
	    	logger.info("Vaccination queryResponseStr: "+queryResponseStr);
	    	
	    	iisQueryForm.setVaccinationResponseHL7Msg(queryResponseStr);
	    	
	    	String errorResponse = IISQueryUtil.checkForErrorResponse(queryResponseStr, iisQueryForm);
			
			String translatedErrorResponse = "";
			if(IISQueryUtil.NO_ERROR.equals(errorResponse) || "OK".equals(errorResponse)){
				request.setAttribute("isError",false);
				IISQueryUtil.extractRXAandProcess(queryResponseStr, vaccinationSearchList, iisQueryForm, request);
			}else{
				translatedErrorResponse = IISQueryUtil.tralateErrorResponse(errorResponse, iisQueryForm.getErrorAttributeMap(), qtyLimitedRequest);
				
				request.setAttribute("feedbackErroMessage",translatedErrorResponse);
				request.setAttribute("isError",true);
				
				if("TM".equals(errorResponse)){
					IISQueryUtil.extractRXAandProcess(queryResponseStr, vaccinationSearchList, iisQueryForm, request);
					
					if(vaccinationSearchList.size()>0 && vaccinationSearchList.size()<Integer.parseInt(qtyLimitedRequest)){
						request.setAttribute("isError",false);
						request.setAttribute("feedbackErroMessage","");
					}
				}
				
				if("MSA".equals(iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_SEGMENT))){
					request.setAttribute("infoBox","errors");
				}else{
					request.setAttribute("infoBox","info");
				}
				
				//Not actual exception, setting error response as exception text
				String exceptionTxt = errorResponse+" - "+translatedErrorResponse;
				EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", exceptionTxt, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_SOURCE_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_MESSAGE_ID), iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_TYPE), IISQueryUtil.IIS_DOC_TYPE);
				portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
			}
			
	    	
	    	GenericForm genericForm= IISQueryUtil.translateFromVaccineSearchResultDTToGenericSummaryDisplayVO(iisQueryForm);
	    	
	    	boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				iisQueryForm.clearAll();
	    		request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));	// selects all the check boxes.
			}
			
			//Sort descending
			genericForm.getAttributeMap().put("sortOrder","2");
			genericForm.getAttributeMap().put("methodName","getVaccAdminDt");
	    	boolean existing = request.getParameter("existing") == null ? false : true;
			String defaultSort = "getVaccAdminDt";
			genericQueueUtil.sortQueue(genericForm, vaccinationSearchList, existing, request,defaultSort);
			
			if(existing)
				genericQueueUtil.filterQueue(genericForm, request,iisQueryForm.getCLASS_NAME_VACC());
			
			//To make sure SelectAll is checked, see if no criteria is applied
			if(iisQueryForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			
			iisQueryForm.setVaccinationSearchList(vaccinationSearchList);
			iisQueryForm.getAttributeMap().put("queueCount", String.valueOf(vaccinationSearchList.size()));
			request.setAttribute("queueCount", String.valueOf(vaccinationSearchList.size()));
			request.setAttribute("vaccinationSearchList", vaccinationSearchList);
			Integer queueSize = properties.getQueueSize(NEDSSConstants.IIS_VACCINATION_SEARCH_RESULT_QUEUE_SIZE);
			iisQueryForm.getAttributeMap().put("queueSize",queueSize);
			
			iisQueryForm.initializeVaccinationDropDowns(vaccinationSearchList);
			
			Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
			iisQueryForm.setAttributeMap(map);
			
			if(vaccinationSearchList!=null)
				request.setAttribute("vaccinationSearchListSize", vaccinationSearchList.size());
            
			return PaginationUtil.paginate(iisQueryForm,request,"vaccineIISQueryResult",mapping);
		}catch(Exception ex){
			logger.fatal("Exception: "+ ex.toString(), ex);
			String errorMsgToWrite = IISQueryUtil.convertExceptionStackTraceToString(ex);
			EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", errorMsgToWrite, null, null, null, iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
			portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
			String errorMsg = IISQueryUtil.GENERIC_ERROR_MSG_IN_CASE_OF_EXCEPTION;
			throw new NEDSSSystemException(errorMsg, ex);
		}
    }
	
	
	public ActionForward filterVaccinationSearchListSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		IISQueryForm iisQueryForm = (IISQueryForm)form;
		
		try{
			ArrayList<Object> vaccinationSearchList = (ArrayList<Object> ) iisQueryForm.getVaccinationSearchList();
			iisQueryForm.setVaccinationSearchList(vaccinationSearchList);
			GenericForm genericForm= IISQueryUtil.translateFromVaccineSearchResultDTToGenericSummaryDisplayVO(iisQueryForm);
			Collection<Object>  filteredVaccinationSearchList = genericQueueUtil.filterQueue(genericForm, request ,iisQueryForm.getCLASS_NAME_VACC());
			
			if(filteredVaccinationSearchList==null){
				filteredVaccinationSearchList = iisQueryForm.getVaccinationSearchList();
			}
			
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search")); // Selects checkboxes based on applied filter
			iisQueryForm.getAttributeMap().put("vaccinationSearchList",filteredVaccinationSearchList);
			iisQueryForm.getAttributeMap().put("queueList",filteredVaccinationSearchList);
			request.setAttribute("queueList", filteredVaccinationSearchList);
			request.setAttribute("vaccinationSearchList", filteredVaccinationSearchList);
			request.setAttribute("queueCount", String.valueOf(filteredVaccinationSearchList.size()));
			iisQueryForm.getAttributeMap().put("queueCount", String.valueOf(filteredVaccinationSearchList.size()));
			iisQueryForm.getAttributeMap().put("PageNumber", "1");
			request.setAttribute("queueCollection",iisQueryForm.getQueueCollection());
            request.setAttribute("stringQueueCollection",iisQueryForm.getStringQueueCollection());
            IISQueryUtil.setPatientDetailsForVaccinationResultHeader(request);
		}catch(Exception ex){
			logger.fatal("Exception: "+ ex.toString(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return PaginationUtil.paginate(iisQueryForm, request, "vaccineIISQueryResult",mapping);
		
	}
	
	
	public ActionForward importSelectedVaccinations(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		IISQueryForm iisQueryForm = (IISQueryForm)form;
		try{
			Collection<Object> vaccinationSearchList = new ArrayList<Object>();
			
			String[] selectedCheckBoxes = request.getParameterValues("selectCheckBox");
			
			PatientSearchResultDT selectedPatient = iisQueryForm.getSelectedPatient();
			Long edxDocumentParentUid = null;
			Long edxDocumentUid = null;
			
			ArrayList <VaccinationSearchResultDT> existingAssociatedVaccList = IISQueryUtil.checkIfVaccinationAssociatedWithOtherPatient(selectedCheckBoxes, iisQueryForm, request);
			
			if(existingAssociatedVaccList.size()>0){
				String errorMessageForEDXActivityLog = "The following vaccination record(s) indicated for import are already associated to another patient in the system and cannot be associated to the current patient:\n";
				String errorMessage = "The following vaccination record(s) indicated for import are already associated to another patient in the system and cannot be associated to the current patient:<ul>";
				for(int i=0;i<existingAssociatedVaccList.size();i++){
					VaccinationSearchResultDT vaccination = existingAssociatedVaccList.get(i);
					errorMessage = errorMessage+"<li>["+vaccination.getVaccinationIdentifier()+"]: ["+vaccination.getVaccType()+"] (["+vaccination.getVaccAdminDtS()+"])</li>";
					errorMessageForEDXActivityLog = errorMessageForEDXActivityLog + "["+vaccination.getVaccinationIdentifier()+"]: ["+vaccination.getVaccType()+"] (["+vaccination.getVaccAdminDtS()+"])\n";
				}
				
				EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", errorMessageForEDXActivityLog, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_SOURCE_NM), iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_MESSAGE_ID), iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, iisQueryForm.getErrorAttributeMap().get(IISQueryUtil.ERROR_RSP_DOC_TYPE), IISQueryUtil.IIS_DOC_TYPE);
				portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
				
				request.setAttribute("isErrorBeforeImport",true);
				request.setAttribute("erroMessage",errorMessage+"</ul>");

				return mapping.findForward("vaccineIISQueryResultForError");
			}
			
			for(int i=0;i<selectedCheckBoxes.length;i++){
				
				if(i==0){
					EDXDocumentDT edxDocumentDT = IISQueryUtil.populateEDXDocument(iisQueryForm.getVaccinationResponseHL7Msg(), null, null);
					edxDocumentParentUid = IISQueryUtil.createEDXDocument(edxDocumentDT, request);
				}
				
				VaccinationSearchResultDT vaccination = iisQueryForm.getVaccinationMap().get(selectedCheckBoxes[i]);
				
				Long interventionUid = null;
				
				try{
					EDXEventProcessDT edxEventProcessDT1 = IISQueryUtil.getEDXEventProcessDTBySourceIdandEventType(vaccination.getVaccinationIdentifier(), IISQueryUtil.DOC_EVENT_TYPE_CD_VAC, request);
					if(edxEventProcessDT1 != null && edxEventProcessDT1.getSourceEventId()!=null){
						logger.info("Updating existing vaccination.");
						//Update existing vaccination and patient revision.
						
						interventionUid = edxEventProcessDT1.getNbsEventUid();
						
						//Read existing vaccination, pageActProxyVO
						PageActProxyVO proxyActProxyVO = (PageActProxyVO) PageLoadUtil.getProxyObject(String.valueOf(interventionUid),NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE, request.getSession());
						
						//go through person, intervention
						
						//Update vaccination
						
						//create extra participation if it doesn't exist before.
						
						IISQueryUtil.populateAndUpdatePageActProxyVO(proxyActProxyVO, iisQueryForm, vaccination, request);
						
						//Create EDXDocument - same actid but add_time will change.
						
						EDXDocumentDT edxDocumentDT = IISQueryUtil.populateEDXDocument(vaccination.getHl7msg(), interventionUid, edxDocumentParentUid);
						edxDocumentUid = IISQueryUtil.createEDXDocument(edxDocumentDT, request);
						
						//Create EDXActivityLog - document updated (1st time document created)
						
						EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(edxDocumentUid, interventionUid, "Success", null, vaccination.getVaccDocNm(), vaccination.getVaccSourceNm(), vaccination.getVaccMessageId(), selectedPatient.getLastName()+" "+selectedPatient.getFirstName(), vaccination.getVaccinationIdentifier(), IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
						portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
						
						//Create or Update EDXEventProcessDT - don't do anything.
						
					}else{
						
						logger.info("Creating new vaccination.");
						//Create vaccination
						interventionUid = IISQueryUtil.populateAndCreatePageActProxyVOForIntervention(iisQueryForm, vaccination, request);
						
						EDXDocumentDT edxDocumentDT = IISQueryUtil.populateEDXDocument(vaccination.getHl7msg(), interventionUid, edxDocumentParentUid);
						edxDocumentUid = IISQueryUtil.createEDXDocument(edxDocumentDT, request);
						EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(edxDocumentUid, interventionUid, "Success", null, vaccination.getVaccDocNm(), vaccination.getVaccSourceNm(), vaccination.getVaccMessageId(), selectedPatient.getLastName()+" "+selectedPatient.getFirstName(), vaccination.getVaccinationIdentifier(), IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
						portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
						
						NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
						String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
						EDXEventProcessDT edxEventProcessDT = IISQueryUtil.populateEDXEventProcessDT(edxDocumentUid, interventionUid, vaccination.getVaccinationIdentifier(), Long.valueOf(userId));
						IISQueryUtil.createEDXEventProcess(edxEventProcessDT, request);
						
					}
				}catch(Exception ex){
					logger.fatal("Exception: "+ ex.toString(), ex);
					throw new Exception(ex.getMessage(), ex);
				}
				
			 }
						
			request.setAttribute("closePopup", "true");
			
			request.getSession().setAttribute("vaccinationImportSucessMessage", "The <b>"+selectedCheckBoxes.length+"</b> selected vaccination record(s) have been successfully imported and associated with this patient/case.");
			return PaginationUtil.paginate(iisQueryForm, request, "vaccineIISQueryResult",mapping);
		}catch(Exception ex){
			request.getSession().setAttribute("vaccinationImportFailureMessage", "Issues were encountered importing the selected vaccination records. Please try again. If the issue persists, please contact your system administrator.");
			logger.fatal("Exception: "+ ex.toString(), ex);
			request.setAttribute("closePopup", "true");
			
			String errorMsgToWrite = IISQueryUtil.convertExceptionStackTraceToString(ex);
			EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", errorMsgToWrite, null, null, null, iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
			portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
			
			return PaginationUtil.paginate(iisQueryForm, request, "vaccineIISQueryResult",mapping);
		}
    }
	
	
}
