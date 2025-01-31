package gov.cdc.nedss.webapp.nbs.action.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.ManageSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.manage.ManageEventsForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
/**
 * Title:        LoadManageEventAction
 * Description:	This is a action class for the struts implementation for Manage actions
 * Copyright:	Copyright (c) 2009
 * Company: 	Computer Sciences Corporation
 * @author		Pradeep Sharma
 * @version	3.1
 */










import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LoadManageEventsAction  extends DispatchAction {
	static final LogUtils logger = new LogUtils(LoadManageEventsAction.class.getName());
    public static final String ACTION_PARAMETER	= "method";
    CachedDropDownValues cdv = new CachedDropDownValues();
    InvestigationUtil investigationUtil = new InvestigationUtil();
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
    
	public ActionForward viewManageLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		    ManageEventsForm manageEventsForm = (ManageEventsForm) form;
		    
		    
		    String contextAction = request.getParameter("ContextAction");
		    String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	        
	        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS030", contextAction);
	    	NBSContext.lookInsideTreeMap(tm);

	    	String sCurrTask = NBSContext.getCurrentTask(session);
		  
		    // permissions of all associations
			boolean viewLabPermission = nbsSecurityObj.getPermission(
											       NBSBOLookup.OBSERVATIONLABREPORT,
											       NBSOperationLookup.VIEW);
			boolean viewMorbPermission = nbsSecurityObj.getPermission(
													NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
													NBSOperationLookup.VIEW);
			boolean addLabPermission = nbsSecurityObj.getPermission(
											       NBSBOLookup.OBSERVATIONLABREPORT,
											       NBSOperationLookup.ADD);
			boolean addMorbPermission = nbsSecurityObj.getPermission(
													NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
													NBSOperationLookup.ADD);
			boolean addVaccPermission = nbsSecurityObj.getPermission(
													NBSBOLookup.INTERVENTIONVACCINERECORD,
													NBSOperationLookup.ADD);
			boolean addTreatPermission = nbsSecurityObj.getPermission(
													NBSBOLookup.TREATMENT,
													NBSOperationLookup.MANAGE);
			
			boolean assoLab = nbsSecurityObj.getPermission(
			                                         NBSBOLookup.INVESTIGATION,
			                                         NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS);
			boolean assoMorb = nbsSecurityObj.getPermission(
			                                         NBSBOLookup.INVESTIGATION,
			                                         NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS);
			boolean assoTreatment = nbsSecurityObj.getPermission(
													 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTS);
			boolean assoVaccine = nbsSecurityObj.getPermission(
													 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS);
			boolean assoDocument = nbsSecurityObj.getPermission(
					 								 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEDOCUMENTS);
			boolean disassocInitEvent = nbsSecurityObj.getPermission(
					 NBSBOLookup.INVESTIGATION, NBSOperationLookup.DISASSOCIATEINITIATINGEVENT);
			
			boolean editVaccPermission = nbsSecurityObj.getPermission(
					NBSBOLookup.INTERVENTIONVACCINERECORD,
					NBSOperationLookup.EDIT);
			
			if(sCurrTask != null && (sCurrTask.equalsIgnoreCase("ManageEvents4")|| 
					sCurrTask.equalsIgnoreCase("ManageEvents5")|| sCurrTask.equalsIgnoreCase("ManageEvents6")))
			{
				addLabPermission = false;
				addMorbPermission = false;
				addTreatPermission = false;
				addVaccPermission = false;
				editVaccPermission = false;
			}
					
	        request.setAttribute("AddLabPermission", String.valueOf(addLabPermission));
	        request.setAttribute("AddMorbPermission", String.valueOf(addMorbPermission));
	        request.setAttribute("AddTreatPermission", String.valueOf(addTreatPermission));
	        request.setAttribute("AddVaccPermission", String.valueOf(addVaccPermission));
	        request.setAttribute("EditVaccPermission", String.valueOf(editVaccPermission));
	        
	        request.setAttribute("AssoLabPermission", String.valueOf(assoLab));
	        request.setAttribute("AssoMorbPermission", String.valueOf(assoMorb));
	        request.setAttribute("AssoTreatPermission", String.valueOf(assoTreatment));
	        request.setAttribute("AssoVaccPermission", String.valueOf(assoVaccine));
	        request.setAttribute("AssoDocPermission", String.valueOf(assoDocument));
	        request.setAttribute("DisassocInitEventPermission", String.valueOf(disassocInitEvent));
			
	        String queryImmunizationRegistryFlag = propertyUtil.getQueryImmunizationRegistry();
	        request.setAttribute("QueryImmunizationRegistryFlag", queryImmunizationRegistryFlag);
	        
			Long mprUid = (Long)NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
			String strPhcUID  = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	        String strPhcLocalUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationLocalID);
	        String strPersonLocalId  = (String)NBSContext.retrieve(session, NBSConstantUtil.DSPatientPersonLocalID);
	        ManageSummaryVO manageSummaryVO =   investigationUtil.getManageSummaryVOForManage(strPhcUID, mprUid.toString(), session);
	        
	        String caseStatusCd = manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd();
	        String conditionCd = manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
	        String investigationStatus = manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigationStatusCd();
	        String caseStatus = cdv.getDescForCode("PHC_CLASS",caseStatusCd);
	        String condition = cdv.getConditionDesc(conditionCd);
	        ErrorMessageHelper.setErrMsgToRequest(request);
	        request.setAttribute("publicHealthCaseLocalUid", strPhcLocalUID);
	        request.setAttribute("publicHealthCaseUID", strPhcUID);
	        request.setAttribute("personLocalID", PersonUtil.getDisplayLocalID(strPersonLocalId));
	        request.setAttribute("personUID", mprUid.toString());
	        if(investigationStatus!=null && investigationStatus.equals(NEDSSConstants.STATUS_OPEN))
	        	request.setAttribute("STDProgramAreaOpen", String.valueOf(PropertyUtil.isStdOrHivProgramArea(manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd()))) ;
	        if(investigationStatus!=null && !investigationStatus.equals(NEDSSConstants.STATUS_OPEN))
	        	request.setAttribute("STDProgramAreaClosed", String.valueOf(PropertyUtil.isStdOrHivProgramArea(manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd()))) ;

	        if(caseStatus != null)
	        	manageEventsForm.setCaseStatus(caseStatus);
	        else
	        	manageEventsForm.setCaseStatus("NA");
	        if(condition != null)
	        	manageEventsForm.setConditionCd(condition);
	        else
	        	manageEventsForm.setConditionCd("NA");
	        
	        if (contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
	        		contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationMorbID)){
	       	 String observationUID = request.getParameter("observationUID");
	          NBSContext.store(session, NBSConstantUtil.DSObservationUID, observationUID);
	        }
	        NBSContext.store(session, NBSConstantUtil.DSJurisdiction,
	        		manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd());
	        NBSContext.store(session, NBSConstantUtil.DSProgramArea, manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd());
	        NBSContext.store(session, NBSConstantUtil.DSCaseStatus,manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd()== null ? "" :
	        	manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd());
          	NBSContext.store(session, NBSConstantUtil.DSConditionCode,manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd() );
	        Collection<Object>  labSummaryCollection  =manageSummaryVO.getLabSummaryCollection();
	        Collection<Object>  morbSummaryCollection  = manageSummaryVO.getMobReportSummaryVOCollection();
	        Collection<Object>  treatSumColl = manageSummaryVO.getTreatmentSummaryVOCollection();
	        Collection<Object>  vacSumColl=manageSummaryVO.getVaccinationSummaryVOCollection();

	        //The following code is for sorting vaccination by Date Administered
	        /*
	        List<VaccinationSummaryVO> theList = new ArrayList(vacSumColl);
	        
	        Collections.sort(theList, new Comparator() {

	            public int compare(Object arg0, Object arg1) {
	                if (!(arg0 instanceof VaccinationSummaryVO)) {
	                    return -1;
	                }
	                if (!(arg1 instanceof VaccinationSummaryVO)) {
	                    return -1;
	                }

	                VaccinationSummaryVO vac1 = (VaccinationSummaryVO)arg0;
	                VaccinationSummaryVO vac2 = (VaccinationSummaryVO)arg1;

	                if(vac1.getEffectiveFromTime()==null)
	                	return 1;
	                if(vac2.getEffectiveFromTime()==null)
	                	return -1;
	                
	                return (int) -(vac1.getEffectiveFromTime().compareTo(vac2.getEffectiveFromTime()));
	            }
	        });
	        vacSumColl = new ArrayList<Object>(theList);
	        */
	        
	        Collection<Object>  docSumColl = manageSummaryVO.getDocumentCollection();
	        if(labSummaryCollection  == null) labSummaryCollection  = new ArrayList<Object> ();
	        if(morbSummaryCollection  == null) morbSummaryCollection  = new ArrayList<Object> ();
	        investigationUtil.populateAssociatedLabMorbs(manageEventsForm,labSummaryCollection,morbSummaryCollection,sPublicHealthCaseUID, request, manageSummaryVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
	        manageEventsForm.setLabSummaryVOColl(labSummaryCollection);
	        manageEventsForm.setMorbSummaryVOColl(morbSummaryCollection);
	    	  	
	    	request.setAttribute("AddLab",
				     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.AddLab));
	        request.setAttribute("AddMorb",
				     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.AddMorb));
	        request.setAttribute("formHref",
				     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.SUBMIT));
			request.setAttribute("ContextAction", tm.get(NBSConstantUtil.SUBMIT));
			request.setAttribute("cancelButtonHref",
					     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.CANCEL));
			request.setAttribute("ObservationLabIDHref",
					     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" +
					     tm.get(NBSConstantUtil.ObservationLabID));
			request.setAttribute("ObservationMorbIDHref",
					     "/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" +
					     tm.get(NBSConstantUtil.ObservationMorbID));
			//The following two lines for print button on ManageAssociation..refer civil00018245
			request.setAttribute("mode", request.getParameter("mode"));
			request.setAttribute("PrintPage", "/nbs/LoadManageEvents1.do?method=viewManageLoad&ContextAction=PrintPage&mode=print");
    
			
	        //These lines of code are for treatment
			if(treatSumColl == null) treatSumColl = new ArrayList<Object> ();
	        investigationUtil.populateAssociatedTreatments(manageEventsForm, treatSumColl, sPublicHealthCaseUID, request);
	        manageEventsForm.setTreatmentSummaryVOColl(treatSumColl);
	        
	        request.setAttribute("AddTreatment",
	        		"/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.AddTreatment));
	        request.setAttribute("viewTreatmentHref",
	        		"/nbs/" + sCurrTask + ".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.ViewTreatment));
	        
	        if(vacSumColl == null) vacSumColl = new ArrayList<Object> ();
	        investigationUtil.populateAssociatedVaccinations(manageEventsForm, vacSumColl, sPublicHealthCaseUID, request);
	        manageEventsForm.setVaccinationSummaryVOColl(vacSumColl);
	        
	        //request.setAttribute("AddVaccination","/nbs/" + sCurrTask +".do?method=viewManageSubmit&ContextAction=" + tm.get(NBSConstantUtil.AddVaccination));
	        
			NBSContext.store(session, NBSConstantUtil.DSInvestigationPath, "ManageEvents");
			  
	        if(docSumColl == null) docSumColl = new ArrayList<Object> ();
	        investigationUtil.setAssociationsFlagForDoc(manageSummaryVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection(), docSumColl);
	        investigationUtil.populateAssociatedDocument(manageEventsForm, docSumColl, sPublicHealthCaseUID, request);
	        manageEventsForm.setDocumentSummaryVOColl(docSumColl);
	        
	        //Associate summary information
		    java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
	        PersonVO personVo = manageSummaryVO.getPatientVO();
	        String fullNm = getPatientFullName(personVo);
	        manageEventsForm.setPersonVo(personVo);
	        manageEventsForm.setFullNm(fullNm);
	        request.setAttribute("fullNm", fullNm);
	        
	        CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
	        Collection<Object>  nms = personVo.getThePersonNameDTCollection();
	        String nmSuffix = "";
			if(nms != null)
		     {
		      Iterator<Object>  itname = nms.iterator();
		       while (itname.hasNext()) {
		         PersonNameDT nameDT = (PersonNameDT) itname.next();

		         if (nameDT.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
		        	  if(nameDT.getNmSuffix() != null)
		        		  nmSuffix =   nameDT.getNmSuffix();
		         }
		       }
		     }
	        
	       	request.setAttribute("suffix", cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX"));
	        
	        if(personVo.getThePersonDT().getCurrSexCd() != null){
	        	manageEventsForm.setSex(cdv.getCodeShortDescTxt(personVo.getThePersonDT().getCurrSexCd(), NEDSSConstants.SEX));
	        	request.setAttribute("sex", cdv.getCodeShortDescTxt(personVo.getThePersonDT().getCurrSexCd(), NEDSSConstants.SEX));
	        }
	        
	        if(personVo.getThePersonDT().getBirthTime() != null){
	        	manageEventsForm.setDob(sdfInput.format(personVo.getThePersonDT().getBirthTime()));
	        	request.setAttribute("dob", sdfInput.format(personVo.getThePersonDT().getBirthTime()));
	        }
	        PersonUtil.setCurrentAgeToRequest(request, personVo.getThePersonDT());
	        
	        Map<Object, Object> map = PersonUtil.setPatientDetailsForIIS(personVo);
	        NBSContext.store(session, "PersonDetails", map);
	        
	        if(request.getSession().getAttribute("vaccinationImportSucessMessage")!=null){
	        	request.setAttribute("vaccineImportSuccess","true");
	        	request.setAttribute("vaccinationImportSucessMessage",request.getSession().getAttribute("vaccinationImportSucessMessage"));
	        	request.getSession().removeAttribute("vaccinationImportSucessMessage");
	        }else if(request.getSession().getAttribute("vaccinationImportFailureMessage")!=null){
	        	request.setAttribute("vaccineImportSuccess","false");
	        	request.setAttribute("vaccinationImportFailureMessage",request.getSession().getAttribute("vaccinationImportFailureMessage"));
	        	request.getSession().removeAttribute("vaccinationImportFailureMessage");
	        }
	        
	        manageEventsForm.setPageTitle(NBSPageConstants.LOAD_EVENTS, request);
			
		} catch (Exception e) {
			logger.error("viewManageLoad Error " + e);
			e.printStackTrace();
		}
		return (mapping.findForward("ManageEvent"));
	}// end of 1 st method
	
	public ActionForward viewManageSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ActionForward forwardNew = new ActionForward();
		try {
		String contextAction = request.getParameter("ContextAction");
		HttpSession session = request.getSession(false);
		Long mprUid = (Long)NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
	    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		if (session == null)
		{
			logger.fatal("error no session, go back to login screen");
			return mapping.findForward("login");
		}
		
		if (contextAction == null)
		{
			contextAction = (String) request.getAttribute("ContextAction");
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.AddLab))
		{
			return mapping.findForward(contextAction);
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.ViewObservationLab))
		{
			String observationUID = request.getParameter("observationUID");
			NBSContext.store(session, NBSConstantUtil.DSObservationUID, observationUID);
			return mapping.findForward(contextAction);
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.ViewObservationMorb))
		{
			String observationUID = request.getParameter("observationUID");
			NBSContext.store(session, NBSConstantUtil.DSObservationUID, observationUID);
			return mapping.findForward(contextAction);

		}else if (contextAction.equalsIgnoreCase(NBSConstantUtil.ViewTreatment))
		{
			logger.debug("\n\nNow inside ViewTreatmentBlock\n\n");
			String treatmentUID = request.getParameter("treatmentUID");
			logger.debug("\n\ntreatmentUID: " + treatmentUID + "\n\n");
			NBSContext.store(session,NBSConstantUtil.DSTreatmentUID, treatmentUID);
			logger.debug("the contextAction is: " + contextAction);
			ActionForward af = mapping.findForward(contextAction);
			StringBuffer strURL = null;
			if(af != null)
			{
				logger.debug("\n\nThe af.getPath is: " + af.getPath());
				strURL = new StringBuffer(af.getPath());
			}
			else
			{
				logger.debug("\n\nThe af is null.\n\n");
			}
			strURL.append("?ContextAction=" + contextAction + "&personUID=").append(mprUid);
			forwardNew.setPath(strURL.toString());
			forwardNew.setRedirect(true);
			return forwardNew;
		}
		/*else if (contextAction.equalsIgnoreCase(NBSConstantUtil.ViewVaccination))
		{
			String interventionUID = request.getParameter("interventionUID");
			NBSContext.store(session,NBSConstantUtil.DSVaccinationUID, interventionUID);
			ActionForward af = mapping.findForward(contextAction);
			StringBuffer strURL = new StringBuffer(af.getPath());
			strURL.append("?ContextAction=" + contextAction + "&personUID=").append(mprUid);
			forwardNew.setPath(strURL.toString());
			forwardNew.setRedirect(true);
			return forwardNew;
		}*/
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.ViewDocument))
		{
			String nbsDocumentUid = request.getParameter("nbsDocumentUid");
			NBSContext.store(session,NBSConstantUtil.DSObservationUID, nbsDocumentUid);
			ActionForward af = mapping.findForward(contextAction);
			StringBuffer strURL = new StringBuffer(af.getPath());
			strURL.append("?method=loadDocument&ContextAction=" + contextAction +"&nbsDocumentUid="+nbsDocumentUid+"&personUID=").append(mprUid);
			forwardNew.setPath(strURL.toString());
			forwardNew.setRedirect(true);
			return forwardNew;
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.AddMorb))
		{
			return mapping.findForward(contextAction);
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.AddTreatment))
		{
			return mapping.findForward(contextAction);
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.AddVaccination))
		{
			return mapping.findForward(contextAction);
		}
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
		{
			ActionForward af = mapping.findForward(contextAction);
			StringBuffer strURL = new StringBuffer(af.getPath());
			strURL.append("?ContextAction=" + contextAction);
			forwardNew.setPath(strURL.toString());
			return forwardNew;
		}  
		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT))
		{
			boolean assoLab = nbsSecurityObj.getPermission(
			                    NBSBOLookup.INVESTIGATION,
			                    NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS);
			boolean assoMorb = nbsSecurityObj.getPermission(
			                    NBSBOLookup.INVESTIGATION,
			                    NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS);
			boolean assoVaccine = nbsSecurityObj.getPermission(
								 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS);
			boolean assoDocument = nbsSecurityObj.getPermission(
								 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATEDOCUMENTS);
			boolean assoTreatment = nbsSecurityObj.getPermission(
					 NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTS);
			Collection<Object>  labSummaryCollection  = new ArrayList<Object> ();
			Collection<Object>  newEventSummaryCollection= new ArrayList<Object> ();
			Collection<Object>  morbLabReportSumColl = new ArrayList<Object> ();
			Collection<Object>  vaccinationSumColl = new ArrayList<Object> ();
			Collection<Object>  documentSumColl = new ArrayList<Object> ();
			Collection<Object>  treatmentSumColl = new ArrayList<Object> ();

			ManageEventsForm manageEventsForm = (ManageEventsForm) form;

			String investigationUID  = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
			String investigationLocalUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationLocalID);

			request.setAttribute("publicHealthCaseUID",investigationUID);
			request.setAttribute("publicHealthCaseLocalUid", investigationLocalUID);
			labSummaryCollection  = manageEventsForm.getLabSummaryVOColl();
			morbLabReportSumColl = manageEventsForm.getMorbSummaryVOColl();
			documentSumColl = manageEventsForm.getDocumentSummaryVOColl();
			vaccinationSumColl = manageEventsForm.getVaccinationSummaryVOColl();
			treatmentSumColl = manageEventsForm.getTreatmentSummaryVOColl();
			Long investigationUidLong = new Long(investigationUID);
			String[] checkBoxIds = manageEventsForm.getSelectedcheckboxIds();
			if(checkBoxIds !=null){

				for(int i = 0 ; i<checkBoxIds.length ; i++){
					String observationUID = checkBoxIds[i];
					StringTokenizer st = new StringTokenizer(observationUID,"|"); 
					while (st.hasMoreTokens()) { 
						String token = st.nextToken();
						newEventSummaryCollection.add((token.toString()));
					}				
				}
			}
			if(assoLab)
				labSummaryCollection  = investigationUtil.setIsAssociationIsTouchedFlagAsPerFrontEnd(labSummaryCollection,newEventSummaryCollection, request);
			if(assoMorb)
				morbLabReportSumColl = investigationUtil.setMorbIsAssociationIsTouchedFlagAsPerFrontEnd(morbLabReportSumColl,newEventSummaryCollection,request);
			// treatmentSumColl = investigationUtil.setTreatIsAssociationIsTouchedFlagAsPerFrontEnd(treatmentSumColl,newEventSummaryCollection,request);
			if(assoVaccine)
				vaccinationSumColl = investigationUtil.setVacciIsAssociationIsTouchedFlagAsPerFrontEnd(vaccinationSumColl,newEventSummaryCollection,request);
			if(assoDocument)
				documentSumColl = investigationUtil.setDocIsAssociationIsTouchedFlagAsPerFrontEnd(documentSumColl,newEventSummaryCollection,request);
			if(assoTreatment)
				treatmentSumColl = investigationUtil.setTreatIsAssociationIsTouchedFlagAsPerFrontEnd(treatmentSumColl,newEventSummaryCollection,request);
			labSummaryCollection.addAll(morbLabReportSumColl);
			if(investigationUtil.setAssociations(investigationUID, labSummaryCollection, vaccinationSumColl, documentSumColl, treatmentSumColl, session))
			{
				return mapping.findForward(contextAction);
			}
			else
			{
				session.setAttribute("error", "Application Error while manage associations");
				// throw new ServletException("Application Error while lab and morb obseravation associations");
			}
		}
		else
		{
			session.setAttribute("error", "No suitable Context Action found");
			//throw new ServletException("No suitable Context Action found");
		}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return forwardNew;
	}
	private String getPatientFullName(PersonVO personVO) {
    	StringBuffer buff = new StringBuffer();
    	if ( personVO.getThePersonDT().getFirstNm() != null && personVO.getThePersonDT().getFirstNm() != "")
    		buff.append(personVO.getThePersonDT().getFirstNm());
    	if (personVO.getThePersonDT().getLastNm() != null
    			&& personVO.getThePersonDT().getFirstNm() != null
    			&& personVO.getThePersonDT().getLastNm() != ""
    			&& personVO.getThePersonDT().getFirstNm() != "")
    		buff.append("  ");
    	if (personVO.getThePersonDT().getLastNm() != null
    			&& personVO.getThePersonDT().getLastNm() != "")
    		buff.append(personVO.getThePersonDT().getLastNm());
    	String patientFullName = buff.toString();
    	return patientFullName;
    }
	
}
	
