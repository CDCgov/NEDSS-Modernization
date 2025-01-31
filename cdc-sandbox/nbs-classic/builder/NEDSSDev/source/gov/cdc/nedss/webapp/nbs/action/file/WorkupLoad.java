package gov.cdc.nedss.webapp.nbs.action.file;

/**
 * Title:        WorkupLoad
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xsp file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC
 * @version 1.0
 */

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
//import gov.cdc.nedss.webapp.nbs.form.file.FileDetailsForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.entity.person.dt.PersonInvestgationSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonReportsSummaryDT;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkupLoad
    extends BaseLdf{

   //For logging
   static final LogUtils logger = new LogUtils(WorkupLoad.class.getName());
   private static CachedDropDownValues cdv = new CachedDropDownValues();
    public static Map<Object,Object> caseMap = new HashMap<Object,Object>();

   public static Map<Object, Object> docMap = new HashMap<Object, Object>();
   /**
    * Default Constructor
    */

   public WorkupLoad() {
   }

   /**
    * This method does following:
    * 1. Creates boolean for different permissions and put them in request
    * object. XSP will use them to display or hide buttons.
    * 2. Sets forward action classes according to context for all links.
    * 3. Reads WorkupProxyEKB from back end and sets appropriate values in
    * request object.
    * 4. Forwards to next page according to context.
    * @param ActionMapping mapping
    * @param ActionForm aForm
    * @param HttpServletRequest request
    * @param HttpServletResponse response
    * @return ActionForward
    * @throws IOException, ServletException
    */

   public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {

      WorkupProxyVO workupProxyVO = null;
      ArrayList<Object> vaccineSumArrayList= null;
      ArrayList<Object> labObservationSummaryList = null;
      ArrayList<Object> morbObservationSummaryList = null;
      ArrayList<Object> unprocessedLabObservationSummaryList = null;
      ArrayList<Object> unprocessedMorbObservationSummaryList = null;
      ArrayList<Object>  treatmentSumArrayList= null;
      ArrayList<Object> docSummaryColl=null;
      ArrayList<Object> theContactSummaryVOColl=null;
      Long personUID=null;
      //Temp Fix for varicella PAM
      request.getSession().removeAttribute("SupplementalInfo");
      //NBSSecurityObj secObj = null;
      	HttpSession session = request.getSession(false);

      if (session == null) {
         logger.debug("error no session");

         throw new ServletException("error no session");
      }

      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");

      //to check security permission for summary tab
      boolean checkSummaryPermission = secObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.VIEWWORKUP);
      request.setAttribute("viewSumm", String.valueOf(checkSummaryPermission));

      //to check security permissions for vaccine tab and also its components
      boolean viewVaccine = secObj.getPermission(NBSBOLookup.
                                                 INTERVENTIONVACCINERECORD,
                                                 NBSOperationLookup.VIEW,
                                                 ProgramAreaJurisdictionUtil.
                                                 ANY_PROGRAM_AREA,
                                                 ProgramAreaJurisdictionUtil.
                                                 ANY_JURISDICTION);
      request.setAttribute("viewVacc", String.valueOf(viewVaccine));
      if (!viewVaccine) {
         request.setAttribute("viewVaccError",
             "You do not have access to view the information on this tab");

      }
      boolean editVaccine = secObj.getPermission(NBSBOLookup.
                                                 INTERVENTIONVACCINERECORD,
                                                 NBSOperationLookup.EDIT);
      request.setAttribute("editVacc", String.valueOf(editVaccine));

      boolean addVaccine = secObj.getPermission(NBSBOLookup.
                                                INTERVENTIONVACCINERECORD,
                                                NBSOperationLookup.ADD,
                                                ProgramAreaJurisdictionUtil.
                                                ANY_PROGRAM_AREA,
                                                ProgramAreaJurisdictionUtil.
                                                ANY_JURISDICTION);
      request.setAttribute(NEDSSConstants.ADDVACCINE, String.valueOf(addVaccine));

      //to check security permissions for investigation tab and also its components
      boolean viewInvestigation = secObj.getPermission(NBSBOLookup.
          INVESTIGATION, NBSOperationLookup.VIEW,
                                  ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                       ProgramAreaJurisdictionUtil.
                                       ANY_JURISDICTION);
      request.setAttribute("viewInves", String.valueOf(viewInvestigation));

      boolean editInvestigation = secObj.getPermission(NBSBOLookup.
          INVESTIGATION, NBSOperationLookup.EDIT);
      request.setAttribute("editInves", String.valueOf(editInvestigation));

      boolean addInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,
          NBSOperationLookup.ADD, ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute(NEDSSConstants.ADDINVS, String.valueOf(addInvestigation));
   
      //to check security permissions for merge investigation
      boolean mergeInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,
              NBSOperationLookup.MERGEINVESTIGATION);
      request.setAttribute(NEDSSConstants.MERGEINVS, String.valueOf(mergeInvestigation));

      //to check security permissions for observation tab and also its components
      boolean editObservation = secObj.getPermission(NBSBOLookup.
          OBSERVATIONLABREPORT, NBSOperationLookup.EDIT);
      request.setAttribute("editObs", String.valueOf(editObservation));

      boolean addObservation = secObj.getPermission(NBSBOLookup.
          OBSERVATIONLABREPORT, NBSOperationLookup.ADD,
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(addObservation));

      boolean addMorbidity = secObj.getPermission(NBSBOLookup.
          OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD,
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute(NEDSSConstants.ADDMORB, String.valueOf(addMorbidity));

      boolean viewObservation = secObj.getPermission(NBSBOLookup.
          OBSERVATIONLABREPORT, NBSOperationLookup.VIEW,
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute("viewObs", String.valueOf(viewObservation));

      boolean viewTreatment = secObj.getPermission(NBSBOLookup.
              TREATMENT, NBSOperationLookup.VIEW,
              ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
              ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute("viewTreat", String.valueOf(viewTreatment));

      boolean viewMorbity =  secObj.getPermission(NBSBOLookup.
          OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW,
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute("viewMorb", String.valueOf(viewMorbity));

      boolean viewDoc = secObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW,
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
      request.setAttribute("viewDoc", String.valueOf(viewDoc));

      boolean bDeleteButton = secObj.getPermission(NBSBOLookup.PATIENT,
              NBSOperationLookup.DELETE);
      request.setAttribute(NEDSSConstants.DELETEBUTTON,
              String.valueOf(bDeleteButton));
      request.setAttribute("deleteButtonHref", "/nbs/ViewFile1.do?ContextAction=Delete");

      /*
            boolean viewPermissionMorb = secObj.getPermission(NBSBOLookup.
                OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW);
           request.setAttribute("viewObsMorbs", String.valueOf(viewPermissionMorb));
       */

      //to check security permissions for Demographic tab

      boolean bEditPersonButton = secObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.EDIT);
      request.setAttribute(NEDSSConstants.EDITBUTTON, String.valueOf(bEditPersonButton));

      /*
       PersonVO personVO = null;
           PersonSummaryVO personSummary = (PersonSummaryVO)NBSContext.retrieve(
            session,
            NBSConstantUtil.DSPersonSummary);
       */

      String fileTab = session.getAttribute("DSFileTab") == null ? null : (String) session.getAttribute("DSFileTab");
      if(fileTab != null && fileTab.trim().length() > 0) {
          request.setAttribute("DSFileTab", fileTab);
          session.removeAttribute("DSFileTab");
      } else {
    	  String sessionFileTab = null;
    	  try {
    		  sessionFileTab = (String) NBSContext.retrieve(session, "DSFileTab");
    	  } catch (Exception ex) {
    		  logger.debug("WorkupLoad: DSFileTab not in context.");
    	  }
          if(request.getParameter("uid")==null && sessionFileTab != null){
             request.setAttribute("DSFileTab",sessionFileTab);
             fileTab = sessionFileTab;
          }
          else
              request.setAttribute("DSFileTab","1");
         //if the control comes back upon closing the contact record popup.
          if(request.getAttribute("ContactTabtoFocus")!=null)
              request.setAttribute("DSFileTab", "3");
          else if(request.getAttribute("TabtoFocusForGenericFlow")!=null)
              request.setAttribute("DSFileTab", "2");//File Events Tab
      }

      if(request.getParameter("uid")  != null){
          request.setAttribute("uid",(String)request.getParameter("uid"));
          personUID = new Long (request.getParameter("uid").toString());
          NBSContext.store(session, "DSPatientPersonUID", personUID);
      }
      else{
          request.setAttribute("uid",NBSContext.retrieve(session,"DSPatientPersonUID"));
          personUID =(Long)NBSContext.retrieve(session,"DSPatientPersonUID");
      }

      String contextAction = request.getParameter("ContextAction");

      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

         //context management
      }
      //Set up the tab focus in new file
      if(contextAction!=null && contextAction.equals("ReturnToFileEvents"))
          request.setAttribute("focusTab", "tabs0head1");//File Events Tab
      else
          request.setAttribute("focusTab", "tabs0head0");// File Summary Tab

      if(fileTab!=null && fileTab.equals("2"))
          request.setAttribute("focusTab", "tabs0head2");//Demographics Tab

      if(request.getAttribute("ContactTabtoFocus")!=null)
           request.setAttribute("focusTab", "tabs0head1");//File Events Tab
      else if(request.getAttribute("TabtoFocusForGenericFlow")!=null)
          request.setAttribute("focusTab", "tabs0head1");//File Events Tab
      
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS001", contextAction);
      NBSContext.lookInsideTreeMap(tm);

      String sCurrTask = NBSContext.getCurrentTask(session);
      String previousPage = NBSContext.getPrevPageID(session);
      String rPref = "<A id=\"returnLink\" href=\"/nbs/"+sCurrTask+".do?ContextAction=";

      if( "PS056".equals(previousPage)) {
          String returnLink = rPref+tm.get("ReturnToObservationNeedingReview")+"\">Return to Documents Requiring Review</A>";
          request.setAttribute("returnLink", returnLink);
      }
      if ( "PS116".equals(previousPage)) {
          String returnLink = rPref+tm.get("ReturnToMyInvestigations")+"\">Return to Open Investigations</A>";
          request.setAttribute("returnLink", returnLink);
      }
      if ("PS114".equals(previousPage)) {
          String returnLink = rPref+tm.get("ReturnToNotificationApprovalQueue")+"\">Return to Approval Queue for Initial Notifications</A>";
          request.setAttribute("returnLink", returnLink);
      }
      if ("PS232".equals(previousPage)) {
          String returnLink = rPref+tm.get("ReturnToRejectedNotificationsQueue")+"\">Return to Rejected Notifications Queue</A>";
          request.setAttribute("returnLink", returnLink);
      }
      if ("PS055".equals(previousPage)) {
          String returnLink = rPref+tm.get("ReturnToObservationsNeedingAssignment")+"\">Return to Documents Requiring Security Assignment</A>";
          request.setAttribute("returnLink", returnLink);

          request.setAttribute("linkName", "Return to Documents Requiring Security Assignment");
          request.setAttribute("linkValue", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ReturnToObservationsNeedingAssignment"));
          request.setAttribute("showQueueNoAction", "true");
      }
      if( "PS502".equalsIgnoreCase(previousPage)){
          String returnLink = rPref+tm.get("ReturnToSupervisorReviewQueue")+"\">Return to Supervisor Review Queue</a>";
          request.setAttribute("returnLink", returnLink);
      }

      //buttons
      request.setAttribute("editButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("EditPatient"));

      request.setAttribute("addInvButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("AddInvestigation"));

      request.setAttribute("addLabButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("AddLab"));
      request.setAttribute("addMorbButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("AddMorb"));

      request.setAttribute("addVacButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("AddVaccination"));

      //links summary
      request.setAttribute("obsLabIdSummaryRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ObservationLabIDOnSummary"));
      request.setAttribute("obsMorbIdSummaryRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ObservationMorbIDOnSummary"));
      request.setAttribute("investigationIdSummaryRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("InvestigationIDOnSummary"));
      //links events
      request.setAttribute("obsLabIdEventRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ObservationLabIDOnEvents"));
      request.setAttribute("obsMorbIdEventRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ObservationMorbIDOnEvents"));
      request.setAttribute("investigationIdEventRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("InvestigationIDOnEvents"));
      request.setAttribute("vacIdEventref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get(NBSConstantUtil.ViewVaccination));
      request.setAttribute("treatmentEventRef",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("TreatmentIDOnEvents"));
      request.setAttribute("documentIDOnEvents",
              "/nbs/" + sCurrTask + ".do?ContextAction=" +
              tm.get("DocumentIDOnEvents"));
      request.setAttribute("ContactTracingHref",
              "/nbs/" + sCurrTask + ".do?ContextAction=" +
              tm.get("ContactTracing"));
      request.setAttribute("viewHref",
              "/nbs/" + sCurrTask + ".do?ContextAction=" +
              tm.get("ViewFile"));
      request.setAttribute("viewFileHref",
              "/nbs/" + sCurrTask + ".do?ContextAction=" +
              tm.get("InvestigationID"));


      try {
          PersonVO personVO = null;
          //System.out.println("########################DSPatientPersonUID ==   " + personUID);

         String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
         String sMethod = "getWorkupProxy";
         Object[] oParams = new Object[] {personUID};
         MainSessionHolder holder = new MainSessionHolder();
         MainSessionCommand msCommand = holder.getMainSessionCommand(session);
         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
                                                  oParams);
         workupProxyVO = (WorkupProxyVO) arr.get(0);

         // Storing workupProxyVO in the ObjectStore
         /*
                   NBSContext.store(session, NBSConstantUtil.DSWorkupProxyVO,
                          workupProxyVO);
          */personVO = workupProxyVO.getThePersonVO();
          setPermissionsIfPatientNotActive(personVO.getThePersonDT().getRecordStatusCd(),request);
          CompleteDemographicForm pForm = (CompleteDemographicForm)aForm;
              // Handle HIV Related Question on patient
               if (secObj.getPermission(NBSBOLookup.GLOBAL,
                       NBSOperationLookup.HIVQUESTIONS)) {
                   pForm.getSecurityMap().put(NEDSSConstants.HAS_HIV_PERMISSIONS,
                           NEDSSConstants.TRUE);
               }

               if(!secObj.getPermission(NBSBOLookup.GLOBAL,
                       NBSOperationLookup.HIVQUESTIONS)){
                   pForm.getAttributeMap().put(NEDSSConstants.EHARS_ID, personVO.getThePersonDT().getEharsId());
               }
           if(request.getParameter("mode") != null && request.getParameter("mode").equals("edit"))
               pForm.setActionMode("Edit");
           else
           pForm.setActionMode("View");
           pForm.setPerson(personVO);
           SearchResultPersonUtil util = new SearchResultPersonUtil();


         //ldf
         //createXSP(NEDSSConstants.PATIENT_LDF, personVO.getThePersonDT().getPersonUid(), personVO, null, request);

         ArrayList<Object> InvSumAR = (ArrayList<Object> ) workupProxyVO.
                              getTheInvestigationSummaryVOCollection();
         if(InvSumAR!=null && InvSumAR.size()>0){
             NedssUtils nUtil = new NedssUtils();
             nUtil.sortObjectByColumn("getInvestigationStatusCd", InvSumAR, false);
         }
         this.setInvestigationSummary(InvSumAR, request, NEDSSConstants.SUMMARY, tm, sCurrTask);
         this.setInvestigationSummary(InvSumAR, request, NEDSSConstants.EVENT , tm, sCurrTask);
         logger.debug("VaccinationSummaryList started");
         Collection<PersonReportsSummaryDT> UnprocessedReports = new ArrayList<PersonReportsSummaryDT>();
         //Document section is starting
         docSummaryColl =  (ArrayList<Object> ) workupProxyVO.getTheDocumentSummaryColl();
         ArrayList<Object> unProcessedList = new ArrayList<Object>();
         if(docSummaryColl != null && docSummaryColl.size()>0){
             Iterator<Object> ite = docSummaryColl.iterator();
             while(ite.hasNext()){
                 SummaryDT docSummaryDT = (SummaryDT)ite.next();
                 docMap.put(docSummaryDT.getLocalId(), docSummaryDT.getNbsDocumentUid());
                 if(docSummaryDT.getRecordStatusCd().equals("UNPROCESSED")){
                     unProcessedList.add(docSummaryDT);
                 }
             }
             UnprocessedReports.addAll(this.getDocDisplayList(unProcessedList,request,NEDSSConstants.SUMMARY, tm, sCurrTask));
         }

         if (workupProxyVO != null &&
             workupProxyVO.getTheVaccinationSummaryVOCollection() != null &&
             workupProxyVO.getTheVaccinationSummaryVOCollection().values() != null)
         {
             logger.debug(workupProxyVO.getTheVaccinationSummaryVOCollection());
             vaccineSumArrayList= new ArrayList<Object>(workupProxyVO.
                getTheVaccinationSummaryVOCollection().values());

             if(vaccineSumArrayList!=null){
	            Collections.sort( vaccineSumArrayList, new Comparator()
	 	        {
	 	        public int compare( Object a, Object b )
	 	           {
	 	            return(((VaccinationSummaryVO)b).getCreateDate()).compareTo( ((VaccinationSummaryVO) a).getCreateDate());
	 	           }
	 	        } );
             }
             
             if (secObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD,NBSOperationLookup.VIEW)){
            	 this.setVaccinationDTList(vaccineSumArrayList, request,tm, sCurrTask);
             }else{
            	 Collection<PersonReportsSummaryDT> eventVaccinationDTList = new ArrayList<PersonReportsSummaryDT>();
            	 request.setAttribute("eventVaccinationDTList", eventVaccinationDTList);
             	 request.setAttribute("eventVaccinationDTListSize", new Integer(eventVaccinationDTList.size()).toString());
             }
            	 
         }

         logger.debug("TreatmentSummaryList started");


        labObservationSummaryList = (ArrayList<Object> ) workupProxyVO.getTheLabReportSummaryVOCollection();
        morbObservationSummaryList = (ArrayList<Object> ) workupProxyVO.getTheMorbReportSummaryVOCollection();
        setMorbidityConditionDescription(morbObservationSummaryList);
        unprocessedLabObservationSummaryList = (ArrayList<Object> ) workupProxyVO.getTheUnprocessedLabReportSummaryVOCollection();
        unprocessedMorbObservationSummaryList = (ArrayList<Object> ) workupProxyVO.getTheUnprocessedMorbReportSummaryVOCollection();
        setMorbidityConditionDescription(unprocessedMorbObservationSummaryList);

        if (workupProxyVO != null &&
             workupProxyVO.getTheTreatmentSummaryVOCollection() != null &&
             workupProxyVO.getTheTreatmentSummaryVOCollection().values() != null) {
            logger.debug(workupProxyVO.getTheTreatmentSummaryVOCollection());
             treatmentSumArrayList= new ArrayList<Object>(workupProxyVO.
                getTheTreatmentSummaryVOCollection().values());

            request.setAttribute("openTreatmentList", treatmentSumArrayList);
            //this.setTreatmentList(treatmentSumArrayList, morbObservationSummaryList, request);
            this.setTreatmentDTList(treatmentSumArrayList, morbObservationSummaryList, request, tm, sCurrTask);
         }

         //this.setInvestigationList(InvSumAR, request);

        if(labObservationSummaryList != null)
        {
                    logger.debug("the if condition is :" + (labObservationSummaryList.size() > 0));
        }

        if ((unprocessedLabObservationSummaryList != null && unprocessedLabObservationSummaryList.size() > 0) ||
                (unprocessedMorbObservationSummaryList != null && unprocessedMorbObservationSummaryList.size() > 0) )
        {
                UnprocessedReports.addAll(this.getLabReportsDisplayList(unprocessedLabObservationSummaryList,
                        unprocessedMorbObservationSummaryList,request, NEDSSConstants.SUMMARY, tm, sCurrTask));
         }
        if (unprocessedMorbObservationSummaryList != null &&  unprocessedMorbObservationSummaryList.size() > 0)
        {
                UnprocessedReports.addAll(this.getMorbReportsDisplayList(unprocessedMorbObservationSummaryList, request, NEDSSConstants.SUMMARY, tm, sCurrTask));
        }

        if(UnprocessedReports.size() !=0){
            request.setAttribute("reportListSize", new Integer(UnprocessedReports.size()).toString());
        }else{
               request.setAttribute("reportListSize", "0");
        }

        request.setAttribute("unprocessedReports", UnprocessedReports);
        if(docSummaryColl!=null){
          request.setAttribute("eventSummaryDocList", this.getDocDisplayList(docSummaryColl,request,NEDSSConstants.EVENT, tm, sCurrTask));
            request.setAttribute("eventSummaryDocListSize", new Integer(docSummaryColl.size()).toString());
        }


        // Event Tab

        if ((labObservationSummaryList != null && labObservationSummaryList.size() > 0) ||
                (morbObservationSummaryList != null && morbObservationSummaryList.size() > 0) )
        {
            ArrayList<PersonReportsSummaryDT> eventLabReportList = (ArrayList<PersonReportsSummaryDT>)this.getLabReportsDisplayList(labObservationSummaryList, morbObservationSummaryList, request,NEDSSConstants.EVENT, tm, sCurrTask);
            request.setAttribute("eventLabReportList", eventLabReportList);
            request.setAttribute("eventLabReportListSize", eventLabReportList.size()!=0 ? new Integer(eventLabReportList.size()).toString():"0" );


        }
        if (morbObservationSummaryList != null &&  morbObservationSummaryList.size() > 0) {
            ArrayList<PersonReportsSummaryDT> eventMorbReportList = (ArrayList<PersonReportsSummaryDT>)this.getMorbReportsDisplayList(morbObservationSummaryList, request,NEDSSConstants.EVENT, tm, sCurrTask);
            request.setAttribute("eventMorbReportList", eventMorbReportList);
            request.setAttribute("eventMorbReportListsize", eventMorbReportList.size()!=0?new Integer(eventMorbReportList.size()).toString():"0");

        }

        //contact Named By Patient and contact Named By Patient section is starting
         theContactSummaryVOColl =  (ArrayList<Object> ) workupProxyVO.getTheContactSummaryVOColl();
         if(theContactSummaryVOColl != null && theContactSummaryVOColl.size()>0){
             //this.setContactPatientsList(theContactSummaryVOColl,request);
             this.setContactPatientsDTList(theContactSummaryVOColl,request, tm, sCurrTask);
         }

         //process the patient tab
         ArrayList<Object>   stateList = new ArrayList<Object> ();

         PersonUtil.convertPersonToRequestObj(personVO, request, "ViewFile", stateList);
         PersonUtil.prepareAddressCounties(request, stateList);
         NBSContext.store(session, "DSPatientPersonLocalID", personVO.getThePersonDT().getLocalId());

         //this.convertPersonToRequestObj(personVO, request);
         //prepareAddressCounties(request);

         //  setting request object to tell demographics we are inside workup
         request.setAttribute("workupPage", "true");
         int eventCount = 0;
         if(workupProxyVO.getActiveRevisionUidsColl()!=null && workupProxyVO.getActiveRevisionUidsColl().size()>1)
             eventCount = workupProxyVO.getActiveRevisionUidsColl().size();
         request.setAttribute("EventCount", eventCount);
         request.setAttribute("personVO", personVO);
         LocalFieldGenerator.loadPatientLDFs(pForm.getPamClientVO(), NEDSSConstants.VIEW_LOAD_ACTION, request,":");
         util.DisplayInfoforViewFile(personVO,pForm,request,"");

      }
      catch (NumberFormatException ne) {
         ne.printStackTrace();
         return (mapping.findForward("error"));
      }
      catch (Exception ex) {

         if (session == null) {
            return (mapping.findForward("error"));
         }
         ex.printStackTrace();
         session.setAttribute("error",
             "View Workup is not available due to error in application" +
                              ex.toString() + "\n: " + ex.getMessage());

         return (mapping.findForward("error"));
      }

      //TransferOwnership Confirmation Msg related stuff
      String confMsg = session.getAttribute("pamTOwnershipConfMsg") == null ? null : (String) session.getAttribute("pamTOwnershipConfMsg");
      if(confMsg != null && confMsg.trim().length() > 0) {

          request.setAttribute("pamTOwnershipMsg", confMsg);
          session.removeAttribute("pamTOwnershipConfMsg");
      }
      String confMsgforDoc = session.getAttribute("docTOwnershipConfMsg") == null ? null : (String) session.getAttribute("docTOwnershipConfMsg");
      if(confMsgforDoc != null && confMsgforDoc.trim().length() > 0) {

          request.setAttribute("docTOwnershipConfMsg", confMsgforDoc);
          session.removeAttribute("docTOwnershipConfMsg");
      }
      String confMsgPage = session.getAttribute("pageTOwnershipConfMsg") == null ? null : (String) session.getAttribute("pageTOwnershipConfMsg");
      if(confMsgPage != null && confMsgPage.trim().length() > 0) {

          request.setAttribute("pageTOwnershipConfMsg", confMsgPage);
          session.removeAttribute("pageTOwnershipConfMsg");
      }

     // request.setAttribute("BaseForm", pForm);
      request.setAttribute("PageTitle", "Patient File");

      if("print".equalsIgnoreCase(contextAction)){
          request.setAttribute("mode", contextAction );
          return mapping.findForward("printView");
      }else{
          return (mapping.findForward("XSP"));
      }
   }

    private void setMorbidityConditionDescription(ArrayList<Object>  morbidityList)
    {
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();

        if(morbidityList == null || morbidityList.size() == 0)
        {
            return;
        }

        try
        {
            for (int i = 0; i < morbidityList.size() ; i++ )
            {
                MorbReportSummaryVO vo = (MorbReportSummaryVO) morbidityList.get(i);

                vo.setConditionDescTxt((String)
                    cachedDropDownValues.getConditionCodes().get(vo.getCondition()));
            }
        }
        catch(Exception e)
        {
            logger.error("Error while populating the condition code descriptions" + e);
        }
    }

    public void setInvestigationSummary(
    		Collection<Object>investigationSummaryVOCollection,    
    		HttpServletRequest request, 
    		String tabName,
    		TreeMap<Object,Object> tm,
    		String sCurrTask)
    {

    	Collection<PersonInvestgationSummaryDT> investigationSummaryList = new ArrayList<PersonInvestgationSummaryDT>();
    	Collection<PersonInvestgationSummaryDT> investigationEventList = new ArrayList<PersonInvestgationSummaryDT>();

    	if (investigationSummaryVOCollection  == null) {
    		logger.debug("investigation summary collection arraylist is null");
    	}
    	else {
    		try {
    			Iterator<Object>  itr = investigationSummaryVOCollection.iterator();

    			while (itr.hasNext()) {
    				PersonInvestgationSummaryDT dt = new PersonInvestgationSummaryDT();

    				InvestigationSummaryVO investigation = (InvestigationSummaryVO) itr.next();

    				if (investigation != null && investigation.getPublicHealthCaseUid() != null)
    				{
    					caseMap.put(investigation.getLocalId(), investigation.getPublicHealthCaseUid());
    					String str = investigation.getActivityFromTime() == null ? "No Date " :StringUtils.formatDate(investigation.getActivityFromTime());
    					if(tabName.equals(NEDSSConstants.SUMMARY)){
    						str = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnSummary")+"&publicHealthCaseUID="+investigation.getPublicHealthCaseUid() + "\">"+str+"</a>";
    					}
    					else
    						str = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID="+investigation.getPublicHealthCaseUid() + "\">"+str+"</a>";
    					dt.setStartDate(str);
    					if(investigation.getNotifRecordStatusCd()!=null && (investigation.getNotifRecordStatusCd().equalsIgnoreCase(NEDSSConstants.REJECTED)||(investigation.getNotifRecordStatusCd().equalsIgnoreCase(NEDSSConstants.PEND_APPR)))){
    						dt.setNotification("<b><font color=\"brown\">"+investigation.getNotifRecordStatusCd()+"</font></b>");
    					}
    					else{
    						dt.setNotification((investigation.getNotifRecordStatusCd() == null) ?
    								"" : investigation.getNotifRecordStatusCd());
    					}
    					String investigatorFirstName=investigation.getInvestigatorFirstName()==null?"":investigation.getInvestigatorFirstName();
    					String investigatorLastName=investigation.getInvestigatorLastName()==null?"":investigation.getInvestigatorLastName();
    					dt.setInvestigator(investigatorFirstName+" "+investigatorLastName);
    					dt.setConditions( (investigation.getConditionCodeText() == null) ?
    							"" : investigation.getConditionCodeText());

    					dt.setCaseStatus( (investigation.getCaseClassCodeTxt() == null) ?
    							"" : investigation.getCaseClassCodeTxt());

    					dt.setJurisdiction( (investigation.
    							getJurisdictionDescTxt() == null) ?
    									"" :   investigation.getJurisdictionDescTxt());

    					dt.setInvestigationId( (investigation.getLocalId() == null) ?
    							"" : investigation.getLocalId());
    					if(investigation.getInvestigationStatusCd().equals("O")){
    						String status = "<b><font color=\"#006000\">Open</font></b>";
    						dt.setStatus(status);
    					}else{
    						dt.setStatus(investigation.getInvestigationStatusDescTxt());
    					}
    					String investigationFormCd = null;
    					CommonAction commonAction = new CommonAction();
    	                HashMap<?,?> cdProgAreaMap = commonAction.getPHCConditionAndProgArea(investigation.getPublicHealthCaseUid(),request.getSession());
    	                if (cdProgAreaMap != null)
    	                    investigationFormCd = commonAction.getInvestigationFormCd((String) cdProgAreaMap
    	                                .get(NEDSSConstants.CONDITION_CD), (String) cdProgAreaMap
    	                                .get(NEDSSConstants.PROG_AREA_CD));
    	               // NBSContext.store(session,NBSConstantUtil.DSInvestigationFormCd,investigationFormCd);
    	                if (investigationFormCd != null && investigationFormCd.startsWith("PG_")) {
      					  	dt.setMergeInvStatus(NEDSSConstants.TRUE);
      				  	}
    	                else
    	                	dt.setMergeInvStatus(NEDSSConstants.FALSE);
    				}
    				 
    				
    				if(investigation.getInvestigationStatusDescTxt() != null &&
    						investigation.getInvestigationStatusCd().equals("O")){
    					investigationSummaryList.add(dt);
    				}
    				if(investigation.getCoinfectionId() != null)  dt.setCoinfectionId(investigation.getCoinfectionId());
    				investigationEventList.add(dt);

    			}
    		} catch (Exception ex) {
    			logger.error("Exception encountered in setInvestigationSummary: ", ex);
    			ex.printStackTrace();
    		}
    		if(tabName.equals(NEDSSConstants.SUMMARY)){
    			request.setAttribute("investigationSummaryDtList", investigationSummaryList);
    			request.setAttribute("investigationSummarySize",investigationSummaryList.size()==0?"0":new Integer(investigationSummaryList.size()).toString());
    		}
    		else{
    			request.setAttribute("strInvestigationEventList", investigationEventList);
    			request.setAttribute("strInvestigationEventSize",investigationEventList.size()==0?"0":new Integer(investigationEventList.size()).toString());
    		}

    	}
    }

    @SuppressWarnings("null")
    private Collection<PersonReportsSummaryDT> getLabReportsDisplayList(ArrayList<Object> LabSummaryList,ArrayList<Object> morbSummaryList,
    		HttpServletRequest request, String tabName, TreeMap<Object,Object> tm,String sCurrTask)
    {
    	Collection<PersonReportsSummaryDT> summaryLabReportList = new ArrayList<PersonReportsSummaryDT>();
    	if (LabSummaryList == null || LabSummaryList.size() == 0) {
    		logger.debug("Observation summary collection arraylist is null");
    	}
    	else {
    		try {
    			Iterator<Object>  obsIterator = LabSummaryList.iterator();

    			while (obsIterator.hasNext())
    			{
    				logger.debug("Inside iterator.hasNext()...");
    				LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO)obsIterator.next();
    				String processingDecision = "";
    				if (labReportSummaryVO.getProcessingDecisionCd() != null){
    					processingDecision = CachedDropDowns.getCodeDescTxtForCd(labReportSummaryVO.getProcessingDecisionCd(),NEDSSConstants.NBS_NO_ACTION_RSN);
    					
    					if(processingDecision.isEmpty())
    						processingDecision = CachedDropDowns.getCodeDescTxtForCd(labReportSummaryVO.getProcessingDecisionCd(), NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
    					
    				}
    				PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    				dt.setProgArea(labReportSummaryVO.getProgramArea());
    				dt.setJurisdiction(labReportSummaryVO.getJurisdiction());

    				String eventType = null;
    				if(tabName.equals(NEDSSConstants.SUMMARY)){
    					eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationLabIDOnSummary")+"&observationUID="+labReportSummaryVO.getObservationUid() + "\">Lab Report</a>";
    				}
    				else{
    					eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationLabIDOnEvents")+"&observationUID="+labReportSummaryVO.getObservationUid() + "\">Lab Report</a>";
    				}
    				if(labReportSummaryVO.isLabFromDoc()){
    					eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+labReportSummaryVO.getUid() + "\">Lab Report</a>";
    				}
    				dt.setEventType(eventType);
    				String startDate = labReportSummaryVO.getDateReceived()==null?"No Date":
    					StringUtils.formatDate(labReportSummaryVO.getDateReceived());
    				if(tabName.equals(NEDSSConstants.EVENT)){
    					if(labReportSummaryVO.isLabFromDoc()){
    						startDate = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+labReportSummaryVO.getUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
    					}
    					else
    						startDate="<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationLabIDOnEvents")+"&observationUID="+labReportSummaryVO.getObservationUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
    				}
    				else{
    					startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(labReportSummaryVO.getDateReceived());
    				}

    				dt.setDateReceived(startDate+"<br>"+processingDecision);

    				//Append Electronic Ind
    				if(labReportSummaryVO.getElectronicInd()!=null && labReportSummaryVO.getElectronicInd().equals("Y")){
    					dt.setEventType(eventType+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
    					if(tabName.equals(NEDSSConstants.EVENT))
    						dt.setDateReceived(startDate+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">"+"<br>"+processingDecision);
    				}
    				String provider = this.getProviderFullName(labReportSummaryVO.getProviderPrefix(), labReportSummaryVO.getProviderFirstName(), labReportSummaryVO.getProviderLastName(), labReportSummaryVO.getProviderSuffix());
    				provider = provider==null?"":"<b>Ordering Provider:</b><br>"+provider;
    				String orderingFacility =  labReportSummaryVO.getOrderingFacility() == null ? "" : "<b>Ordering Facility:</b><br>" + labReportSummaryVO.getOrderingFacility();;
    				String facility = labReportSummaryVO.getReportingFacility()==null?"":"<b>Reporting Facility:</b><br>"+labReportSummaryVO.getReportingFacility();
    				dt.setProviderFacility(facility+"<br>"+provider+ "<br>" + orderingFacility);
    				String dateCollected=null;
    				if(tabName.equals(NEDSSConstants.SUMMARY))
    					dateCollected= labReportSummaryVO.getDateCollected()==null?"No Date":
    						"<b>Date Collected:</b><br>"+StringUtils.formatDate(labReportSummaryVO.getDateCollected());
    				else
    					dateCollected= labReportSummaryVO.getDateCollected()==null?"No Date":
    						StringUtils.formatDate(labReportSummaryVO.getDateCollected());
    				dt.setDateCollected(dateCollected);
    				if(labReportSummaryVO.isLabFromDoc())
    					dt.setDescription(labReportSummaryVO.getResultedTestString());
    				else
    					dt.setDescription(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
    				String URL = null;
    				if(tabName.equals(NEDSSConstants.SUMMARY)){
    					URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnSummary")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    				}
    				else{
    					URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    				}
    				dt.setAssociatedWith(this.getAssociatedString(labReportSummaryVO.getAssociationsMap(),URL));

    				dt.setEventId(labReportSummaryVO.getLocalId() == null ? "" :labReportSummaryVO.getLocalId());

    				if(labReportSummaryVO.getTheResultedTestSummaryVOCollection() != null)
    				{
    					//ObservationSummaryUtil summaryUtil = new ObservationSummaryUtil();
    					//sbuffLabList.append(summaryUtil.convertResultedTest(labReportSummaryVO.getTheResultedTestSummaryVOCollection(), false));
    				}
    				summaryLabReportList.add(dt);
    			} //while
    		} catch (Exception ex) {
    			logger.error("Exception encountered in setInvestigationSummary: ", ex);
    			ex.printStackTrace();
    		}                

    	}//else

    	return summaryLabReportList;
    } //setLabReports

    private Collection<PersonReportsSummaryDT> getMorbReportsDisplayList (ArrayList<Object>  morbSummaryList, HttpServletRequest request, String tabName, TreeMap<Object,Object> tm,String sCurrTask)
    {
    	Collection<PersonReportsSummaryDT> summaryMorbReportList = new ArrayList<PersonReportsSummaryDT>();

    	if (morbSummaryList == null) {
    		logger.debug("Observation summary collection arraylist is null");
    	}
    	else {
    		try {
    			Iterator<Object>  obsIterator = morbSummaryList.iterator();

    			while (obsIterator.hasNext()) {

    				MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO)obsIterator.next();
    				if(morbReportSummaryVO.isMorbFromDoc())
    					morbReportSummaryVO.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_PROCESSED);
    				String processingDecision = "";
    				if (morbReportSummaryVO.getProcessingDecisionCd() != null){
    					
    					processingDecision = CachedDropDowns
            					.getCodeDescTxtForCd(
            							morbReportSummaryVO
            							.getProcessingDecisionCd(),
            							NEDSSConstants.NBS_NO_ACTION_RSN);
    					if(processingDecision.isEmpty())
    						processingDecision = CachedDropDowns
        					.getCodeDescTxtForCd(
        							morbReportSummaryVO
        							.getProcessingDecisionCd(),
        							NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
    				}
    				if (morbReportSummaryVO.getRecordStatusCd()!=null &&
    						!morbReportSummaryVO.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_MORB_PROCESS))
    				{
    					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    					String eventType = null;
    					if(tabName.equals(NEDSSConstants.SUMMARY)){
    						eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationMorbIDOnSummary")+"&observationUID="+morbReportSummaryVO.getObservationUid() + "\">Morb Report</a>";
    					}
    					else{
    						eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationMorbIDOnEvents")+"&observationUID="+morbReportSummaryVO.getObservationUid() + "\">Morb Report</a>";
    					}
    					if(morbReportSummaryVO.isMorbFromDoc()){
    						eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+morbReportSummaryVO.getUid() + "\">Morb Report</a>";
    					}
    					dt.setEventType(eventType);
    					String startDate = morbReportSummaryVO.getDateReceived()==null?"No Date":
    						StringUtils.formatDate(morbReportSummaryVO.getDateReceived());
    					if(tabName.equals(NEDSSConstants.EVENT)){
    						if(morbReportSummaryVO.isMorbFromDoc()){
    							startDate = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+morbReportSummaryVO.getUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
    						}
    						else
    							startDate="<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("ObservationMorbIDOnEvents")+"&observationUID="+morbReportSummaryVO.getObservationUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
    					}
    					else{
    						startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(morbReportSummaryVO.getDateReceived());
    					}
    					dt.setDateReceived(startDate+"<br>"+processingDecision);

    					//Append Electronic Ind
    					if(morbReportSummaryVO.isMorbFromDoc()){
    						dt.setEventType(eventType+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
    						if(tabName.equals(NEDSSConstants.EVENT))
    							dt.setDateReceived(startDate+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">"+"<br>"+processingDecision);
    					}

    					String provider = this.getProviderFullName(morbReportSummaryVO.getProviderPrefix(), morbReportSummaryVO.getProviderFirstName(), morbReportSummaryVO.getProviderLastName(), morbReportSummaryVO.getProviderSuffix());
    					String facility = morbReportSummaryVO.getReportingFacility();
    					
    					if(tabName.equals(NEDSSConstants.SUMMARY)){
    						provider = provider==null?"":provider.isEmpty()?"":"<b>Ordering Provider:</b><br>"+provider;
    						facility = facility==null?"":facility.isEmpty()?"":"<b>Reporting Facility:</b><br>"+facility;
    						dt.setProviderFacility(facility+"<br>"+provider);
    					}
    					else
    						dt.setProviderFacility((provider==null?"":provider));
    					String dateCollected=null;
    					if(tabName.equals(NEDSSConstants.SUMMARY))
    						dateCollected = morbReportSummaryVO.getReportDate()==null?"No Date":
    							"<b>Report Date:</b><br>"+StringUtils.formatDate(morbReportSummaryVO.getReportDate());
    					else
    						dateCollected = morbReportSummaryVO.getReportDate()==null?"No Date":
    							StringUtils.formatDate(morbReportSummaryVO.getReportDate());
    					dt.setDateCollected(dateCollected);
    					dt.setDescription(morbReportSummaryVO.getConditionDescTxt() == null ? "" :
    						"<b>"+morbReportSummaryVO.getConditionDescTxt()+"</b>");
    					dt.setEventId(morbReportSummaryVO.getLocalId() == null ? "" :
    						morbReportSummaryVO.getLocalId());
    					String URL = null;
    					if(tabName.equals(NEDSSConstants.SUMMARY)){
    						URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnSummary")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    					}
    					else{
    						URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    					}
    					dt.setJurisdiction(morbReportSummaryVO.getJurisdiction());
    					dt.setAssociatedWith(this.getAssociatedString(morbReportSummaryVO.getAssociationsMap(),URL));
    					StringBuffer desc = new StringBuffer(dt.getDescription());
    					// Lab reports created within morb report
    					boolean flag = true;
    					if((morbReportSummaryVO.getTheLabReportSummaryVOColl() != null &&
    							morbReportSummaryVO.getTheLabReportSummaryVOColl().size() > 0))
    					{
    						ArrayList<Object> labFromMorbList = (ArrayList<Object>)morbReportSummaryVO.getTheLabReportSummaryVOColl();
    						if (labFromMorbList == null || labFromMorbList.size() == 0) {
    							logger.debug("Observation summary collection arraylist is null");
    						}
    						else {
    							Iterator<Object>  labIterator = labFromMorbList.iterator();
    							while (labIterator.hasNext())
    							{
    								flag = false;
    								LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO)labIterator.next();
    								desc.append(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
    							}//While
    							dt.setDescription(desc.toString());
    						}
    					}
    					// Treatments created within morb report
    					if((morbReportSummaryVO.getTheTreatmentSummaryVOColl() != null &&
    							morbReportSummaryVO.getTheTreatmentSummaryVOColl().size() > 0))
    					{
    						ArrayList<Object> treatmentFromMorbList = (ArrayList<Object>)morbReportSummaryVO.getTheTreatmentSummaryVOColl();

    						if (treatmentFromMorbList == null || treatmentFromMorbList.size() == 0) {
    							logger.debug("Observation summary collection arraylist is null");
    						}
    						else {
    							NedssUtils nUtil = new NedssUtils();
    							nUtil.sortObjectByColumn("getCustomTreatmentNameCode", morbReportSummaryVO.getTheTreatmentSummaryVOColl(), true);
    							if(flag)
    								desc.append("<br>");
    							desc.append("<b>Treatment Info:</b><UL>");
    							Iterator<Object>  treatmentIterator = treatmentFromMorbList.iterator();
    							while (treatmentIterator.hasNext())
    							{
    								logger.debug("Inside iterator.hasNext()...");
    								TreatmentSummaryVO treatment = (TreatmentSummaryVO)treatmentIterator.next();
    								desc.append(treatment.getCustomTreatmentNameCode() == null ? "" :
    									"<LI><b>"+treatment.getCustomTreatmentNameCode()+"</b></LI>");
    							}//While
    							desc.append("<UL>");
    							dt.setDescription(desc.toString());
    						}

    					}
    					summaryMorbReportList.add(dt);
    				}
    			} //while
    		} catch (Exception ex) {
    			logger.error("Exception encountered in getMorbReportsDisplayList: ", ex);
    			ex.printStackTrace();
    		}
    	}
    	return summaryMorbReportList;
    }

    private Collection<PersonReportsSummaryDT> getDocDisplayList(ArrayList<Object>  docSummaryList, HttpServletRequest request, String tabName,TreeMap<Object,Object> tm,String sCurrTask)
    {
    	Collection<PersonReportsSummaryDT> summaryDocList = new ArrayList<PersonReportsSummaryDT>();

    	if (docSummaryList == null) {
    		logger.debug("Document summary collection arraylist is null");
    	}
    	else {
    		try {
    			Iterator<Object>  docIterator = docSummaryList.iterator();

    			while (docIterator.hasNext()) {
    				SummaryDT docSummaryDT = (SummaryDT)docIterator.next();
    				String processingDecision = "";
    				if (docSummaryDT.getProcessingDecisionCd() != null){
    					processingDecision = CachedDropDowns.getCodeDescTxtForCd(docSummaryDT.getProcessingDecisionCd(),NEDSSConstants.NBS_NO_ACTION_RSN);
    					
    					if(processingDecision.isEmpty())
    						processingDecision = CachedDropDowns.getCodeDescTxtForCd(docSummaryDT.getProcessingDecisionCd(), NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
    					
    				}
    				PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    				if(docSummaryDT.getDocType() != null && docSummaryDT.getDocType().length() > 0)
    					docSummaryDT.setDocTypeConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocType(),"PUBLIC_HEALTH_EVENT"));
    				if (docSummaryDT.getDocEventTypeCd() != null) {
    					String docTypeCd = docSummaryDT.getDocEventTypeCd();
    					if (docTypeCd.equals(NEDSSConstants.LABRESULT_CODE))
    						docSummaryDT.setDocTypeConditionDescTxt("Lab Report");
    					else if (docTypeCd.equals(NEDSSConstants.MORBIDITY_CODE))
    						docSummaryDT.setDocTypeConditionDescTxt("Morb Report");
    					else if (docTypeCd.equals(NEDSSConstants.CLASS_CD_CONTACT))
    						docSummaryDT
    						.setDocTypeConditionDescTxt("Contact Record");
    				}
    				
    				
    				if(docSummaryDT.getDocPurposeCd() != null && docSummaryDT.getDocPurposeCd().length() > 0)
    					docSummaryDT.setDocPurposeCdConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocPurposeCd(),"NBS_DOC_PURPOSE"));
    				if(docSummaryDT.getDocStatusCd() != null && docSummaryDT.getDocStatusCd().length() > 0)
    					docSummaryDT.setDocStatusCdConditionDescTxt(cdv.getCodeShortDescTxt(docSummaryDT.getDocStatusCd(),"NBS_DOC_STATUS"));
    				if(!tabName.equals(NEDSSConstants.EVENT)){
    					String eventType = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+docSummaryDT.getNbsDocumentUid() + "\">"+docSummaryDT
    							.getDocTypeConditionDescTxt()+"</a>";
    					dt.setEventType(eventType);
    				}
    				else
    					dt.setEventType(docSummaryDT.getDocTypeConditionDescTxt());
    				String startDate = docSummaryDT.getLastChgTime()==null?"No Date"://Fatima: this is what needs to be changed to fix it to date received from patient file > summary
    					StringUtils.formatDate(docSummaryDT.getLastChgTime());
    				if(tabName.equals(NEDSSConstants.EVENT)){
    					startDate="<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("DocumentIDOnEvents")+"&nbsDocumentUid="+docSummaryDT.getNbsDocumentUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(docSummaryDT.getLastChgTime());
    				}
    				else{
    					startDate = startDate+"<br>"+StringUtils.formatDatewithHrMin(docSummaryDT.getLastChgTime());
    				}
    				
    				dt.setDateReceived(startDate+"<br>"+processingDecision);
    				dt.setDescription(docSummaryDT.getCdDescTxt() == null ? "" : "<b>"+docSummaryDT.getCdDescTxt());
    				if(tabName.equals(NEDSSConstants.SUMMARY))
    					dt.setProviderFacility(docSummaryDT.getSendingFacilityNm() == null ? "" : "<b>Sending Facility</b><br>"+docSummaryDT.getSendingFacilityNm());
    				else
    					dt.setProviderFacility(docSummaryDT.getSendingFacilityNm() == null ? "" : docSummaryDT.getSendingFacilityNm());
    				String dateCollected=null;
    				if(tabName.equals(NEDSSConstants.SUMMARY))
    					dateCollected = docSummaryDT.getAddTime()==null?"No Date":
    						"<b>Date Reported:</b><br>"+StringUtils.formatDate(docSummaryDT.getAddTime());
    				else
    					dateCollected = docSummaryDT.getAddTime()==null?"No Date":
    						StringUtils.formatDate(docSummaryDT.getAddTime());
    				dt.setDateCollected(dateCollected);
    				String URL = null;
    				if(tabName.equals(NEDSSConstants.SUMMARY)){
    					URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnSummary")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    				}
    				else{
    					URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    				}

    				dt.setAssociatedWith(this.getAssociatedString(docSummaryDT.getAssociationMap(),URL));
    				dt.setEventId(docSummaryDT.getLocalId() == null ? "" : docSummaryDT.getLocalIdForUpdatedAndNewDoc());

    				summaryDocList.add(dt);
    				/*if (!tabName.equals(NEDSSConstants.SUMMARY)) {
					CDAEventSummaryParser cdaparser = new CDAEventSummaryParser();
					cdaparser.parseTreatmentsFromCDADoc(request, docSummaryDT);
					}*/
    			} //while
    		} catch (Exception ex) {
    			logger.error("Exception encountered in getDocDisplayList: ", ex);
    			ex.printStackTrace();
    		}
    	}
    	return summaryDocList;
    } //getDocDisplayList

    private void setVaccinationDTList(Collection<Object> vaccinationSummaryVOColl,HttpServletRequest request, TreeMap<Object,Object> tm,String sCurrTask) {

    	Collection<PersonReportsSummaryDT> eventVaccinationDTList = new ArrayList<PersonReportsSummaryDT>();

    	if (vaccinationSummaryVOColl == null) {
    		logger.debug("vaccination summary collection arraylist is null");
    	}
    	else {
    		try {
    			Iterator<Object>  itr = vaccinationSummaryVOColl.iterator();

    			while (itr.hasNext()) {

    				VaccinationSummaryVO vaccination = (VaccinationSummaryVO) itr.next();

    				if (vaccination != null && vaccination.getInterventionUid() != null) {
    					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    					String startDate =vaccination.getCreateDate()==null?"No Date":
    						StringUtils.formatDate(vaccination.getCreateDate());
    					startDate = "<a href=\"javascript:contactRecordPopUp('/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&actUid="+vaccination.getInterventionUid()+"&Action=DSFilePath')\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(vaccination.getCreateDate());
    					
    					if(NEDSSConstants.ELECTRONIC_IND_VACCINATION.equals(vaccination.getElectronicInd())){
    						dt.setDateReceived(startDate+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
    					}else{
    						dt.setDateReceived(startDate);
    					}
    					
    					String provider = this.getProviderFullName(vaccination.getProviderPrefix(), vaccination.getProviderFirstName(), vaccination.getProviderLastName(), vaccination.getProviderSuffix());
    					dt.setProviderFacility((provider==null?"":provider));

    					String dateCollected = vaccination.getActivityFromTime()==null?"No Date":
    					StringUtils.formatDate(vaccination.getActivityFromTime());
    					dt.setDateCollected(dateCollected);

    					dt.setDescription(vaccination.getVaccineAdministered() == null ? "" :
    						"<b>"+vaccination.getVaccineAdministered());
    					String URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    					dt.setAssociatedWith(this.getAssociatedString(vaccination.getAssociationsMap(),URL));
    					dt.setEventId(vaccination.getLocalId());
    					
    					eventVaccinationDTList.add(dt);
    				}
    			}

    		} catch (Exception ex) {
    			logger.error("Exception encountered in setVaccinationDTList: ", ex);
    			ex.printStackTrace();
    		}
    	} //else
    	request.setAttribute("eventVaccinationDTList", eventVaccinationDTList);
    	request.setAttribute("eventVaccinationDTListSize", new Integer(eventVaccinationDTList.size()).toString());
    }

    @SuppressWarnings("unchecked")
    private void setTreatmentDTList(Collection<Object> treatmentSummaryVOColl, Collection<Object>  morbSummaryVOColl, HttpServletRequest request,TreeMap<Object,Object> tm,String sCurrTask) {

    	Collection<PersonReportsSummaryDT> eventTreatmentDTList = new ArrayList<PersonReportsSummaryDT>();

    	if (treatmentSummaryVOColl == null) {
    		logger.debug("treatment summary collection arraylist is null");
    	}
    	else {
    		try {

    			Iterator<Object>  itr = treatmentSummaryVOColl.iterator();
    			while (itr.hasNext()) {

    				TreatmentSummaryVO treatment = (TreatmentSummaryVO) itr.next();

    				if (treatment != null && treatment.getTreatmentUid() != null) {
    					PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    					String startDate = "No Date";
    					if(treatment.getNbsDocumentUid()!=null){
    						startDate = treatment.getCreateDate() == null ? "No Date"
    								: StringUtils.formatDate(treatment.getCreateDate());
    						startDate = "<a href=\"/nbs/LoadViewDocument2.do?method=cdaDocumentView&docId="
    								+ treatment.getNbsDocumentUid()+"&eventId="+treatment.getLocalId()
    								+ "&eventType="+NEDSSConstants.TREATMENT_ACT_TYPE_CD
    								+ "\" target=\"_blank\">" + startDate + "</a>";
    						dt.setDateReceived(startDate
    								+ "<br>"
    								+ StringUtils.formatDatewithHrMin(treatment.getCreateDate())
    								+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");

    					}else{
    						startDate =treatment.getCreateDate()==null?"No Date":
    							StringUtils.formatDate(treatment.getCreateDate());

    						startDate =    "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("TreatmentIDOnEvents")+"&treatmentUID="+treatment.getTreatmentUid() + "\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(treatment.getCreateDate());
    						dt.setDateReceived(startDate);
    					}

    					String dateCollected = treatment.getActivityFromTime()==null?"No Date":
    						StringUtils.formatDate(treatment.getActivityFromTime());
    					dt.setDateCollected(dateCollected);
    					String provider = this.getProviderFullName(treatment.getProviderPrefix(), treatment.getProviderFirstName(), treatment.getProviderLastName(), treatment.getProviderSuffix());
    					dt.setProviderFacility((provider==null?"":provider));
    					String URL = "<a href=\"/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationIDOnEvents")+"&publicHealthCaseUID=replaceUID\">replaceLocalID</a>";
    					dt.setAssociatedWith(this.getAssociatedString(treatment.getAssociationMap(),URL));
    					dt.setDescription(treatment.getCustomTreatmentNameCode() == null ? "" :
    						"<b>"+treatment.getCustomTreatmentNameCode());
    					dt.setEventId(treatment.getLocalId() == null ?"" :treatment.getLocalId());
    					eventTreatmentDTList.add(dt);
    				}
    			}
    		} catch (Exception ex) {
    			logger.error("Exception encountered in setTreatmentDTList: ", ex);
    			ex.printStackTrace();
    		}                
    	}

    	request.setAttribute("eventTreatmentDTList", eventTreatmentDTList);
    	request.setAttribute("eventTreatmentDTListSize", new Integer(eventTreatmentDTList.size()).toString());
    }



    private void setContactPatientsDTList(Collection<Object> contactSummaryVOColl,HttpServletRequest request, TreeMap<Object,Object> tm,String sCurrTask)
    {
    	CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
    	Map<Object,Object> contactNamedByPatDTListMap = new HashMap<Object,Object>();
    	Map<Object,Object> patNamedByContactDTListMap = new HashMap<Object,Object>();
    	int i=0;
    	if (contactSummaryVOColl == null) {
    		logger.debug("contact summary named by patient collection  is null");
    	}
    	else {
    		try {
    			Iterator<Object>  itr = contactSummaryVOColl.iterator();
    			while (itr.hasNext()) {
    				CTContactSummaryDT contactsumDT = (CTContactSummaryDT) itr.next();
    				PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
    				if(contactsumDT != null && contactsumDT.isContactNamedByPatient()){
    					String startDate =contactsumDT.getCreateDate()==null?"No Date":
    						StringUtils.formatDate(contactsumDT.getCreateDate());
    					startDate = "<a href=\"javascript:contactRecordPopUp('/nbs/ContactTracing.do?method=viewContact&contactRecordUid="+contactsumDT.getCtContactUid()+"&mode=View&DSInvestigationLocalID="+contactsumDT.getSubjectPhcLocalId()+"&Action=DSFilePath&DSInvestigationCondition="+contactsumDT.getSubjectPhcCd()+"&SourceDispositionCd="+contactsumDT.getSourceDispositionCd()+"&SourceConditionCd="+contactsumDT.getSourceConditionCd()+"&SourceInterviewStatusCd="+contactsumDT.getSourceInterviewStatusCd()+"')\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(contactsumDT.getCreateDate());
    					dt.setCreateDate(startDate);

    					String priority=(contactsumDT.getPriorityCd() == null) ?
    							"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,contactsumDT.getPriorityCd());

    					String disposition = getContactsDispositionDescription(contactsumDT);
    					String cdDescTxt = contactsumDT.getContactPhcCd()==null?"":cachedDropDownValues.getConditionDesc(contactsumDT.getContactPhcCd());
    					dt.setDescription(getContactRecordDesc(cdDescTxt,priority,disposition));
    					Timestamp namedOnDate = null;
    					if(contactsumDT.getNamedOnDate()!=null)
    						namedOnDate=contactsumDT.getNamedOnDate();
    					else if(contactsumDT.getInterviewDate()!=null)
    						namedOnDate = contactsumDT.getInterviewDate();
    					else
    						namedOnDate = contactsumDT.getCreateDate();
    					dt.setDateNamed(namedOnDate==null?"No Date":StringUtils.formatDate(namedOnDate));
    					dt.setNamedBy("<a href=\"/nbs/LoadViewFile1.do?ContextAction=ViewFile&uid="+contactsumDT.getContactMprUid()+"\">"+getContactsNamedByPatientNameString(contactsumDT));
    					String publicHealthLocalId = contactsumDT.getContactPhcLocalId()==null?"":contactsumDT.getContactPhcLocalId();
    					String conditiondDesc = contactsumDT.getContactPhcCd()==null?"":cachedDropDownValues.getConditionDesc(contactsumDT.getContactPhcCd());
    					StringBuffer associatedWith = new StringBuffer(publicHealthLocalId.equals("")?"":"<a href=\"/nbs/ViewFile1.do?ContextAction=InvestigationIDOnEvents&publicHealthCaseUID="+contactsumDT.getContactEntityPhcUid()+"\">"+publicHealthLocalId+"</a><br><b>"+conditiondDesc+"</b>");
    					if(contactsumDT.getAssociatedMap()!=null){
    						for(Object id:contactsumDT.getAssociatedMap().keySet()){
    							associatedWith.append( "<a href=\"/nbs/LoadViewDocument2.do?method=cdaDocumentView&docId="
    									+contactsumDT.getAssociatedMap().get(id)+ "&eventType="+NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE+ "\" target=\"_blank\">" + id + "</a><br>");
    						}
    					}
    					dt.setAssociatedWith(associatedWith.toString());
    					dt.setEventId(getEventIdDisplay(contactsumDT));
    					if(contactNamedByPatDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))!=null){
    						((ArrayList<Object>)contactNamedByPatDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))).add(dt);
    					}
    					else{
    						ArrayList<Object> list = new ArrayList<Object>();
    						list.add(dt);
    						contactNamedByPatDTListMap.put(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()), list);
    					}
    				}
    				if(contactsumDT != null && contactsumDT.isPatientNamedByContact()){
    					String startDate =contactsumDT.getCreateDate()==null?"No Date":
    						StringUtils.formatDate(contactsumDT.getCreateDate());
    					startDate = "<a href=\"javascript:contactRecordPopUp('/nbs/ContactTracing.do?method=viewContact&contactRecordUid="+contactsumDT.getCtContactUid()+"&mode=View&DSInvestigationLocalID="+contactsumDT.getSubjectPhcLocalId()+"&Action=DSFilePath&DSInvestigationCondition="+contactsumDT.getSubjectPhcCd()+"&SourceDispositionCd="+contactsumDT.getSourceDispositionCd()+"&SourceConditionCd="+contactsumDT.getSourceConditionCd()+"&SourceInterviewStatusCd="+contactsumDT.getSourceInterviewStatusCd()+"')\">"+startDate+"</a>"+"<br>"+StringUtils.formatDatewithHrMin(contactsumDT.getCreateDate());
    					dt.setCreateDate(startDate);

    					String priority=(contactsumDT.getPriorityCd() == null) ?
    							"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,contactsumDT.getPriorityCd());

    					String disposition = getContactsDispositionDescription(contactsumDT);
    					String cdDescTxt = contactsumDT.getSubjectPhcCd()==null?"":cachedDropDownValues.getConditionDesc(contactsumDT.getSubjectPhcCd());
    					dt.setDescription(getContactRecordDesc(cdDescTxt,priority,disposition));
    					Timestamp namedOnDate = null;
    					if(contactsumDT.getNamedOnDate()!=null)
    						namedOnDate=contactsumDT.getNamedOnDate();
    					else if(contactsumDT.getInterviewDate()!=null)
    						namedOnDate = contactsumDT.getInterviewDate();
    					else
    						namedOnDate = contactsumDT.getCreateDate();
    					dt.setDateNamed(namedOnDate==null?"No Date":StringUtils.formatDate(namedOnDate));
    					String name = getContactsNamedByContactNameString(contactsumDT);
    					dt.setNamedBy("<a href=\"/nbs/LoadViewFile1.do?ContextAction=ViewFile&uid="+contactsumDT.getSubjectMprUid()+"\">"+name+"</a>");
    					String publicHealthLocalId = contactsumDT.getSubjectPhcLocalId()==null?"":contactsumDT.getSubjectPhcLocalId();
    					String conditiondDesc = contactsumDT.getSubjectPhcCd()==null?"":cachedDropDownValues.getConditionDesc(contactsumDT.getSubjectPhcCd());
    					StringBuffer associatedWith = new StringBuffer(publicHealthLocalId.equals("")?"":"<a href=\"/nbs/ViewFile1.do?ContextAction=InvestigationIDOnEvents&publicHealthCaseUID="+contactsumDT.getSubjectEntityPhcUid()+"\">"+publicHealthLocalId+"</a><br><b>"+conditiondDesc+"</b>");
    					if(contactsumDT.getAssociatedMap()!=null){
    						for(Object id:contactsumDT.getAssociatedMap().keySet()){
    							associatedWith.append( "<br/><a href=\"/nbs/LoadViewDocument2.do?method=cdaDocumentView&docId="
    									+contactsumDT.getAssociatedMap().get(id)+ "&eventType="+NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE+ "\" target=\"_blank\">" + id + "</a>");
    						}
    					}
    					dt.setAssociatedWith(associatedWith.toString());
    					dt.setEventId(getEventIdDisplay(contactsumDT));
    					if(patNamedByContactDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))!=null){
    						((ArrayList<Object>)patNamedByContactDTListMap.get(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()))).add(dt);
    					}
    					else{
    						ArrayList<Object> list = new ArrayList<Object>();
    						list.add(dt);
    						patNamedByContactDTListMap.put(cdv.getConditionDesc(contactsumDT.getSubjectPhcCd()), list);
    					}
    				}
    				i++;
    			}
    		} catch (Exception ex) {
    			logger.error("Exception encountered in setContactPatientsDTList: ", ex);
    			ex.printStackTrace();
    		}                   
    	}
    	request.setAttribute("contactNamedByPatListMap",  contactNamedByPatDTListMap);
    	request.setAttribute("patNamedByContactsListMap", patNamedByContactDTListMap);
    	request.setAttribute("contactDTListSize", new Integer(i).toString());
    }
    /*
     * CON101893000GA01
     * Field Followup (P1)
     */
    private String getEventIdDisplay(CTContactSummaryDT contactsumDT) {
    	StringBuffer stBuff = new StringBuffer("");
    	CachedDropDownValues cddV = new CachedDropDownValues();
    	try {
    		stBuff.append("<b>"+contactsumDT.getLocalId()+"</b>");
    		if (contactsumDT.getContactProcessingDecisionCd() != null && !contactsumDT.getContactProcessingDecisionCd().isEmpty()) {
    			String processingDecision = cddV.getDescForCode( CTConstants.StdNbsProcessingDecisionCRLookupCodeset,contactsumDT.getContactProcessingDecisionCd());
    			String referralBasis = contactsumDT.getContactReferralBasisCd()==null?"":contactsumDT.getContactReferralBasisCd();
    			if (processingDecision != null && !processingDecision.isEmpty()) {
    				stBuff.append("<br><b>"+processingDecision+"(" +referralBasis +")</b>");
    			} else logger.warn("getEventDisplay() can't find processing decision description for code=" +contactsumDT.getContactProcessingDecisionCd());
    		}
    	} catch (Exception ex) {
    		logger.error("Exception encountered in getEventIdDisplay: ", ex);
    		ex.printStackTrace();
    	}
    	return stBuff.toString();
    }

	/*
     * i.e. Sly, Beth
     *   or Sly, Beth (with Randall, Dan, Jr Init w/out Ix)
     */
    private String getContactsNamedByPatientNameString(
    		CTContactSummaryDT contactsumDT) {
    	StringBuffer stBuff = new StringBuffer("");
    	try {
    		if (contactsumDT.getContactName() != null)
    			stBuff.append(contactsumDT.getContactName());
    		stBuff.append("</a>"); //end anchor
    		if (contactsumDT.getOtherInfectedPatientName() != null) {
    			stBuff.append(" (").append(contactsumDT.getOtherInfectedPatientName());
    			if (contactsumDT.getNamedDuringInterviewUid() != null && contactsumDT.getNamedDuringInterviewUid().equals(CTConstants.StdInitiatedWithoutInterviewLong))
    				stBuff.append(":").append(CTConstants.StdInitiatedWithoutInterviewStr);
    			stBuff.append(")");
    		}
    	} catch (Exception ex) {
    		logger.error("Exception encountered in getContactsNamedByPatientNameString: ", ex);
    		ex.printStackTrace();
    	}  	
    	return stBuff.toString();
    }

	/*
     * i.e. Sly, Beth
     *   or  Init w/out (with Randall, Dan, Jr)
     *   or Bolt, Mary(Sly, Beth with Freeman, John)
     */
    private String getContactsNamedByContactNameString(
    		CTContactSummaryDT contactsumDT) {
    	StringBuffer stBuff = new StringBuffer("");
    	Boolean thirdParty = false;
    	Boolean otherInfected = false;
    	try {
    		if (contactsumDT.getNamedDuringInterviewUid() != null && contactsumDT.getNamedDuringInterviewUid().equals(CTConstants.StdInitiatedWithoutInterviewLong))
    			thirdParty = true;
    		if (contactsumDT.getOtherInfectedPatientName()!= null && !contactsumDT.getOtherInfectedPatientName().isEmpty())
    			otherInfected = true;
    		if (contactsumDT.isOtherNamedByPatient()) {
    			if (contactsumDT.getSubjectName() != null)
    				stBuff.append(contactsumDT.getSubjectName());
    			stBuff.append(" (");
    			if (contactsumDT.getContactName() != null)
    				stBuff.append(contactsumDT.getContactName());
    			if (contactsumDT.getOtherInfectedPatientName() != null)
    				stBuff.append(" with ").append(contactsumDT.getOtherInfectedPatientName());
    			stBuff.append(")");
    		} else {
    			if (!otherInfected && !thirdParty)
    				stBuff.append(contactsumDT.getSubjectName());
    			if (thirdParty)
    				stBuff.append(CTConstants.StdInitiatedWithoutInterviewStr).append(" (");
    			if (otherInfected && !thirdParty)
    				stBuff.append(contactsumDT.getSubjectName()).append(" (");
    			if (!otherInfected && thirdParty)
    				stBuff.append("with ").append(contactsumDT.getSubjectName());
    			if (otherInfected)
    				stBuff.append(" with ").append(contactsumDT.getOtherInfectedPatientName());
    			if (otherInfected || thirdParty)
    				stBuff.append(")");
    		}
    	} catch (Exception ex) {
    		logger.error("Exception encountered in getContactsNamedByContactNameString: ", ex);
    		ex.printStackTrace();
    	}

    	return stBuff.toString();
    }
    /*
     * i.e. Disposition: Infected - Not Treated)
     */
    private String getContactsDispositionDescription(
    		CTContactSummaryDT contactsumDT) {
    	CachedDropDownValues cddV = new CachedDropDownValues();
    	boolean stdProgramArea = false;
    	String theDispoDesc = "";
    	try {
    		if (contactsumDT.getProgAreaCd() != null)
    			stdProgramArea = PropertyUtil.isStdOrHivProgramArea(contactsumDT.getProgAreaCd()) ;

    		String theDispoCd = contactsumDT.getDispositionCd();

    		if (theDispoCd != null && !theDispoCd.isEmpty()) {
    			if (stdProgramArea)
    				theDispoDesc = cddV.getDescForCode( CTConstants.StdNbsDispositionLookupCodeset,theDispoCd);
    			else
    				theDispoDesc = cddV.getDescForCode( CTConstants.NonStdNbsDispositionLookupCodeset,theDispoCd);
    			if (theDispoDesc == null || theDispoDesc.isEmpty())
    				logger.warn("getContactsDispositionDescription() ?? code lookup failed for code=" +theDispoCd);
    		}
    	} catch (Exception ex) {
    		logger.error("Exception encountered in getContactsDispositionDescription: ", ex);
    		ex.printStackTrace();
    	}
    	contactsumDT.setDisposition(theDispoDesc);
    	return theDispoDesc;
    }


	/*
    *  getAssociatedString - get the associated local uid of the associated case and the condition
    *  Note: This was modified for STD to also return the Processing Decision is the association was to a closed case.
    *
    */
    public String getAssociatedString(Map<Object,Object> associationMap, String URL){
    	StringBuffer sb = new StringBuffer("");
    	CachedDropDownValues cddv = new CachedDropDownValues();
    	if(associationMap!=null && associationMap.size()>0){
    		try {
    			Iterator<Object> ite = associationMap.keySet().iterator();
    			while(ite.hasNext()){
    				String caseId = (String)ite.next();

    				if (caseId.contains("-")) {  //Processing Decision for Previous Investigation
    					continue;
    				} else if (caseId.contains("DOC") && docMap.get(caseId)!=null) {// for Associated Documents
    					String URL1 = URL
    							.replaceAll("replaceUID",
    									((Long) docMap.get(caseId)).toString())
    									.replaceAll("replaceLocalID", caseId)
    									.replaceAll("InvestigationIDOnEvents",
    											"DocumentIDOnEvents")
    											.replaceAll("publicHealthCaseUID", "nbsDocumentUid");
    					sb.append(URL1 + "<br>");
    					
    					if(associationMap
    							.get(caseId)!=null)
    						sb.append("<b>");
    						sb.append(cddv.getConditionDesc((String) associationMap
    								.get(caseId)) + "</b><br>");
    				}
    				else if(caseId.contains(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE)){

    				}
    				else if( caseMap.get(caseId)!=null) {
    					String URL1 = URL.replaceAll("replaceUID", ((Long)caseMap.get(caseId)).toString());
    					String URL2 = URL1.replaceAll("replaceLocalID", caseId);
    					sb.append(URL2+"<br>");
    					sb.append("<b>"+cddv.getConditionDesc((String)associationMap.get(caseId))+"</b><br>");
    					String dispositionCd = (String) associationMap.get(caseId + "-" + (String)associationMap.get(caseId));
    					if (dispositionCd != null && !dispositionCd.isEmpty()) {
    						sb.append(cddv.getCodeShortDescTxt(dispositionCd, NEDSSConstants.STD_CREATE_INV_LAB_UNKCOND_PROC_DECISION) + "<br>");
    					}
    				}
    			}
    		} catch (Exception ex) {
    			logger.error("Exception encountered in getAssociatedString: ", ex);
    			ex.printStackTrace();
    		}
    	}
    	return sb.toString();
    }

    public String getProviderFullName(String prefix,String firstNm,String lastNm, String sufix){
       StringBuffer sb = new StringBuffer("");
       if(prefix!=null && !prefix.equals("")){
           sb.append(prefix).append(" ");
       }
       if(firstNm!=null && !firstNm.equals("")){
           sb.append(firstNm).append(" ");
       }
       if(lastNm!=null && !lastNm.equals("")){
           sb.append(lastNm).append(" ");
       }
       if(sufix!=null && !sufix.equals("")){
           sb.append(sufix).append(" ");
       }
       if(sb.toString().trim().equals(""))
           return null;
       else
       return sb.toString();
    }

    public String getContactRecordDesc(String condition, String priority, String disposition){
       StringBuffer sb = new StringBuffer("");
       if(condition!=null && !condition.trim().equals("")){
           sb.append("<b>").append(condition).append("</b><br>");
       }
       if(priority!=null && !priority.trim().equals("")){
           sb.append("<b>Priority:</b> ").append(priority).append("<br>");
       }
       if(disposition!=null && !disposition.trim().equals("")){
           sb.append("<b>Disposition:</b> ").append(disposition);
       }
       return sb.toString();
    }

    public void setPermissionsIfPatientNotActive(String recordStatusCd,HttpServletRequest request){
       if(recordStatusCd==null)
           return;
       else if(recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_INACTIVE)||recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE)||recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_SUPERCEDED)){
           request.setAttribute(NEDSSConstants.EDITBUTTON, String.valueOf(false));
           request.setAttribute(NEDSSConstants.DELETEBUTTON, String.valueOf(false));
           request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(false));
           request.setAttribute(NEDSSConstants.ADDMORB, String.valueOf(false));
           request.setAttribute(NEDSSConstants.ADDINVS, String.valueOf(false));
           request.setAttribute(NEDSSConstants.ADDVACCINE, String.valueOf(false));
           request.setAttribute(NEDSSConstants.ADDLAB, String.valueOf(false));
           if(recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE))
               recordStatusCd= NEDSSConstants.RECORD_STATUS_INACTIVE;
           request.setAttribute(NEDSSConstants.RECORDSTATUSCD, "<b><font color=\"#990000\">"+recordStatusCd+"</b></font>");
       }
    }

}
