package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        CoreDemographic
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;

/**
 * This class is used for populating a PersonVO object that will be
 * utilized in populating a the "View Person" screen.
 */
public class PatientCreateLoad
     extends BaseLdf{

   //For logging
   /**
    * An instance of the LogUtils object.
    */
   static final LogUtils logger = new LogUtils(PatientCreateLoad.class.getName());

   /**
    * Constructor for the PersonLoad class.
    */
   public PatientCreateLoad() {
   }

   /**
    * This is the method that gets executed first when a request
    * is made to this class.
    *
    * @exception IOException
    * @exception ServletException
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return an ActionForward
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {

      ArrayList<Object> stateList = null;
      
      request.getSession().removeAttribute("result");
      CompleteDemographicForm personForm = (CompleteDemographicForm) form;
      personForm.reset();

      HttpSession session = request.getSession(false);

      if (session == null) {
         logger.debug("error no session");

         throw new ServletException("error no session");
      }

      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
		// Handle HIV Related Question on patient
		if (secObj.getPermission(NBSBOLookup.GLOBAL,
				NBSOperationLookup.HIVQUESTIONS)) {
			personForm.getSecurityMap().put(NEDSSConstants.HAS_HIV_PERMISSIONS,
					NEDSSConstants.TRUE);
		}
      String contextAction = request.getParameter("ContextAction");

      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

      }

      String sCurrentTask = NBSContext.getCurrentTask(session);


      TreeMap<Object,Object> tm = null;


      if (contextAction.equalsIgnoreCase("AddExtended")) {
         ErrorMessageHelper.setErrMsgToRequest(request, "ps169");
         tm = NBSContext.getPageContext(session, "PS169", contextAction);
         personForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
         ((CompleteDemographicForm)form).setPageTitle("Add Patient - Extended", request);

      }
      else {
    	 ((CompleteDemographicForm)form).setPageTitle("Add Patient - Basic", request);
    	 ((CompleteDemographicForm)form).setActionMode(NEDSSConstants.CREATE_EXTEND_ACTION);
         ErrorMessageHelper.setErrMsgToRequest(request, "ps166");
         tm = NBSContext.getPageContext(session, "PS166", contextAction);
         PersonVO personVO = new PersonVO();
      }

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("formHref", "/nbs/" + HTMLEncoder.encodeHtml(sCurrTask) + ".do");
      request.setAttribute("ContextAction", tm.get("Submit"));
      request.setAttribute("cancelButtonHref",
                           "/nbs/" + HTMLEncoder.encodeHtml(sCurrTask) + ".do?ContextAction=" +
                        		   HTMLEncoder.encodeHtml(tm.get("Cancel").toString()));
      request.setAttribute("addButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("AddExtended"));
      request.setAttribute("defaultStateFlag", "true");
      try {
         PatientSearchVO patientSearchVO = (PatientSearchVO) NBSContext.
                                           retrieve(session, "DSSearchCriteria");
         if (patientSearchVO != null) {
            if (patientSearchVO.getLastNameOperator().equals("=") || patientSearchVO.getLastNameOperator().equals("CT") ||
            		patientSearchVO.getLastNameOperator().equals("SW")) {
               request.setAttribute("lastNameOne", patientSearchVO.getLastName());

            }
            if (patientSearchVO.getSsn() != null) {
               request.setAttribute("ssn", patientSearchVO.getSsn());

               /*
                 if (patientSearchVO.getTypeCd()!=null && patientSearchVO.getTypeCd().equals("SS"))
                    request.setAttribute("ssn" , patientSearchVO.getRootExtensionTxt());
                */
            }
            if (patientSearchVO.getFirstNameOperator().equals("=") || patientSearchVO.getFirstNameOperator().equals("CT") ||
            		patientSearchVO.getFirstNameOperator().equals("SW")) {
               request.setAttribute("firstNameOne",
                                    patientSearchVO.getFirstName());

            }
            request.setAttribute("currSexCd", patientSearchVO.getCurrentSex());

            if (patientSearchVO.getBirthTimeOperator().equals("=") || patientSearchVO.getBirthTimeOperator().equals("CT")) {

               if ( (patientSearchVO.getBirthTimeDay() != null &&
                    patientSearchVO.getBirthTimeDay().trim().length() != 0) &&
                  (patientSearchVO.getBirthTimeMonth() != null &&
                   patientSearchVO.getBirthTimeMonth().trim().length() != 0) &&
                  (patientSearchVO.getBirthTimeYear() != null &&
                   patientSearchVO.getBirthTimeYear().trim().length() != 0))
              {
                request.setAttribute("birthTime",
                                     PersonUtil.getBirthDate(patientSearchVO.
                    getBirthTimeMonth(), patientSearchVO.getBirthTimeDay(),
                    patientSearchVO.getBirthTimeYear()));
                request.setAttribute("currentAge",
                                     patientSearchVO.getAgeReported());
                request.setAttribute("currentAgeUnitCd",
                                     patientSearchVO.getAgeReportedUnitCd());
              }
            }

            if ( (patientSearchVO.getTypeCd() != null &&
                  patientSearchVO.getTypeCd().trim().length() > 0) &&
                (patientSearchVO.getRootExtensionTxt() != null &&
                 patientSearchVO.getRootExtensionTxt().trim().length() > 0)) {

               request.setAttribute("typeOne", patientSearchVO.getTypeCd());
               request.setAttribute("idValueOne",
                                    patientSearchVO.getRootExtensionTxt());
               StringBuffer sbIds = new StringBuffer("");
               sbIds.append("person.entityIdDT_s[i].typeCd").append(NEDSSConstants.BATCH_PART).append(patientSearchVO.getTypeCd()).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].typeDescTxt").append(NEDSSConstants.BATCH_PART).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].assigningAuthorityCd").append(NEDSSConstants.BATCH_PART).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].assigningAuthorityDescTxt").append(NEDSSConstants.BATCH_PART).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].rootExtensionTxt").append(NEDSSConstants.BATCH_PART).append(patientSearchVO.getRootExtensionTxt()).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].statusCd").append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].entityIdSeq").append(NEDSSConstants.BATCH_PART).append(NEDSSConstants.BATCH_SECT);
               sbIds.append("person.entityIdDT_s[i].entityUid").append(NEDSSConstants.BATCH_PART).append(NEDSSConstants.BATCH_SECT);
               sbIds.append(NEDSSConstants.BATCH_LINE);

               request.setAttribute("ids", sbIds.toString());
            }

            if (patientSearchVO.getStreetAddr1Operator()!=null &&(patientSearchVO.getStreetAddr1Operator().equals("=") || patientSearchVO.getStreetAddr1Operator().equals("CT"))) {
               request.setAttribute("addressOne",
                                    patientSearchVO.getStreetAddr1());

            }
            if (patientSearchVO.getCityDescTxtOperator()!=null &&(patientSearchVO.getCityDescTxtOperator().equals("=") || patientSearchVO.getCityDescTxtOperator().equals("CT"))) {
               request.setAttribute("cityOne", patientSearchVO.getCityDescTxt());

            }
            //if search criteria is not searching on state default the state
            if (patientSearchVO.getState() != null &&
                !patientSearchVO.getState().trim().equals(""))
            {
              request.setAttribute("stateOne", patientSearchVO.getState());
              request.setAttribute("addressCounties",
                                   PersonUtil.
                                   getCountiesByState(patientSearchVO.getState()));

            }
            else if(sCurrTask !=null && !sCurrTask.equals("AddPatientExtended1"))
            {
              request.setAttribute("stateOne", PersonUtil.getDefaultStateCd());
              request.setAttribute("addressCounties",
                                   PersonUtil.
                                   getCountiesByState(PersonUtil.getDefaultStateCd()));
            }

            if (patientSearchVO.getZipCd() != null) {
               request.setAttribute("zipOne", patientSearchVO.getZipCd());
            }


            request.setAttribute("CDM-spanishOrigin",
                                 patientSearchVO.getEthnicGroupInd());

            //String findRace = patientSearchVO.getRaceCd();
            if(patientSearchVO.getRaceCd()!=null)
            	PersonUtil.setAttributeRaceCD(request, patientSearchVO.getRaceCd());

            // need to put something in the county drop down
            request.setAttribute("birthCountiesInState", "+|");
            request.setAttribute("deathCountiesInState", "+|");
         }
      }
      catch (NullPointerException ne) {
         logger.info("DSSearchCriteria is null");
      }
      
    	  PersonVO personVO = null;
          try{
    	  personVO = (PersonVO) NBSContext.retrieve(session,
          "DSPatientPersonVO");  
          }
          catch(NullPointerException ex){
        	  logger.info("DSPatientPersonVO is null in Context"); 
          }
          if(personVO==null)
        	  personVO = personForm.getPerson();
        try {
        SearchResultPersonUtil searchUtil = new SearchResultPersonUtil();
        searchUtil.DisplayInfoforViewFile(personVO,personForm, request,"Create");
        PersonUtil util = new PersonUtil();
        util.getBirthAddress(personVO,personForm);
        util.getDeceasedAddress(personVO,personForm);
        request.setAttribute("personVO", personVO);
		 //places NEWPAT_LDFS into request if LDFs are present for the Patient
        if(contextAction!=null && contextAction.equalsIgnoreCase("AddExtended"))
        	LocalFieldGenerator.loadPatientLDFs(personForm.getPamClientVO(), NEDSSConstants.EDIT_LOAD_ACTION, request,":");
        else
		 LocalFieldGenerator.loadPatientLDFs(personForm.getPamClientVO(), NEDSSConstants.CREATE_LOAD_ACTION, request,":");
		personForm.setPerson(personVO);

       if(contextAction!=null && contextAction.equalsIgnoreCase("AddExtended")){
    	   PersonUtil.convertPersonToRequestObj(personVO, request, contextAction, null);
    	   setAsofDates(personVO.getThePersonDT());
       	  }
        }
      catch (Exception ne) {

         logger.info("Patient create load: could not laod patient in context: "+contextAction);
         ne.printStackTrace();
         return mapping.findForward("error");

      }
      return mapping.findForward("XSP");


   }
   public void setAsofDates(PersonDT personDT){
	   if(personDT.getAsOfDateEthnicity_s() == null || personDT.getAsOfDateEthnicity_s().equals(""))
		   personDT.setAsOfDateEthnicity_s(personDT.getAsOfDateAdmin_s()==null?(StringUtils.formatDate(new Timestamp(new Date().getTime()))):personDT.getAsOfDateAdmin_s());
	   if(personDT.getAsOfDateSex_s() == null || personDT.getAsOfDateSex_s().equals(""))
		   personDT.setAsOfDateSex_s(personDT.getAsOfDateAdmin_s()==null?(StringUtils.formatDate(new Timestamp(new Date().getTime()))):personDT.getAsOfDateAdmin_s());
	   if(personDT.getAsOfDateMorbidity_s() == null || personDT.getAsOfDateMorbidity_s().equals(""))
		   personDT.setAsOfDateMorbidity_s(personDT.getAsOfDateAdmin_s()==null?(StringUtils.formatDate(new Timestamp(new Date().getTime()))):personDT.getAsOfDateAdmin_s());
	   if(personDT.getAsOfDateGeneral_s() == null || personDT.getAsOfDateGeneral_s().equals(""))
		   personDT.setAsOfDateGeneral_s(personDT.getAsOfDateAdmin_s()==null?(StringUtils.formatDate(new Timestamp(new Date().getTime()))):personDT.getAsOfDateAdmin_s());
	   }
	   

}
