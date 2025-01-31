package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;

/**
 * Title:        LabReportLoad.java
 * Description:	This is a action class for the structs implementation for Add lab
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */


public class LabReportLoad
    extends BaseLdf
{

    //For logging
    static final LogUtils logger = new LogUtils(LabReportLoad.class.getName());


    public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {

        HttpSession session = request.getSession(false);
        boolean ldfCreatedForPatient = false;
        if (session == null)
        {
            logger.fatal("error no session");

            return mapping.findForward("login");
        }
        CommonAction ca = new CommonAction();
        ca.setSTDandHIVPAsToRequest(request);
        String contextAction = null;
        contextAction = request.getParameter("ContextAction");
        String conditionCode = null;
        TreeMap<Object,Object> tm = new TreeMap<Object, Object>();
        
        if(aForm.getClass().getName().equalsIgnoreCase("gov.cdc.nedss.webapp.nbs.form.observation.ObservationForm")) {
           ObservationForm form = (ObservationForm)aForm;
           form.reset(mapping, request);
           form.reset();
           if(form.getResultedTestVOCollection() != null)
           //System.out.println("collection size is " + form.getResultedTestVOCollection().size());
           form.setResultedTestVOCollection(null);
         }

        if (contextAction == null)
          contextAction = (String) request.getAttribute("ContextAction");

        String sCurrTask = NBSContext.getCurrentTask(session);

        NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");

       boolean checkEntitySearch = secObj.getPermission(NBSBOLookup.PATIENT,
           NBSOperationLookup.FIND);
       request.setAttribute("checkFile", String.valueOf(checkEntitySearch));

       // Permission check for ProgramArea, Juridition and Shared inidicator.


       String userTypeValue = secObj.getTheUserProfile().getTheUser().getUserType();
       boolean retainCheckbox;
       if (userTypeValue != null) {
         boolean SearchPermission = secObj.getPermission("PATIENT","FIND");
         boolean displayFacility =    userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
         request.setAttribute("displayFacility",String.valueOf(displayFacility));

              request.setAttribute("SearchPermission",
                                   String.valueOf(SearchPermission));
              try {
                Long userReportingFacilityUid = secObj.getTheUserProfile().getTheUser().
                    getReportingFacilityUid();
                if (userReportingFacilityUid != null) {
                  Map<Object,Object> results = getOrganization(String.valueOf(
                      userReportingFacilityUid.longValue()), session);
                  request.setAttribute("reportingFacilityUID",
                                       userReportingFacilityUid.toString());
                  String Clia = CommonLabUtil.getCliaValue(userReportingFacilityUid.toString(), session);
                  if(Clia==null || Clia.trim().length()==0)
                      Clia=NEDSSConstants.DEFAULT;
                  request.setAttribute("labId", Clia);

                  if(sCurrTask.startsWith("ManageObservation"))
                    conditionCode =(String) NBSContext.retrieve(session,NBSConstantUtil.DSConditionCode);
                  CommonLabUtil.getDropDownLists(userReportingFacilityUid.toString(),
                                             NEDSSConstants.ORDERED_TEST_LOOKUP +NEDSSConstants.RESULTED_TEST_LOOKUP,
                                             conditionCode, NEDSSConstants.PROGRAM_AREA_NONE,
                                             null, null, request);

                  if (results != null) {
                    request.setAttribute("reportingSourceDetails",
                                         (String) results.get("result"));
                  }
                }
                else {
                  request.setAttribute("reportingSourceDetails",
                      "There is no Reporting Facility selected.");
                 request.setAttribute("orderingOrgDetails", "There is no Ordering Facility selected.");
                 request.setAttribute("providerDetails", "There is no Ordering Provider selected.");


                 // return (mapping.findForward("error"));
                }

              }
              catch (Exception e) {
                e.printStackTrace();
              }
            //}

       }
       if (contextAction.equalsIgnoreCase("AddLabDataEntry")){
          session.setAttribute("patientFalse","false");
          session.setAttribute("reportingLabFalse","false");
        }
       PersonVO retainPersonVO= null;
       LabInformationVO labInformationVO = null;

       String patientFlase = (String)session.getAttribute("patientFalse");
       boolean patient = false;

       try {
         if (patientFlase != null && patientFlase.equals("true")){
           try {
             retainPersonVO = (PersonVO) NBSContext.retrieve(session,
                 "DSPatientPersonVO");
             createXSP(NEDSSConstants.PATIENT_LDF, retainPersonVO, null, request);
             ldfCreatedForPatient = true;
           }
           catch (Exception e) {}

           patient = true;
         }
       }
       catch (Exception e) {
         // do nothing
       }

       boolean facility = false;
       String facilityValue = (String)session.getAttribute("reportingLabFalse");
       try {
         if (facilityValue != null && facilityValue.equals("true")){
           labInformationVO = (LabInformationVO) NBSContext.retrieve(session,
                    "DSLabInformation");

           facility = true;
         }
       }
       catch (Exception e) {
         // do nothing
       }


       tm = NBSContext.getPageContext(session, "PS015", contextAction);
       sCurrTask = NBSContext.getCurrentTask(session);
       if(sCurrTask.equalsIgnoreCase("AddObservationLab1")||
           sCurrTask.equalsIgnoreCase("AddObservationLab3")||
           sCurrTask.equalsIgnoreCase("AddObservationLab4")){

          String programArea =(String) NBSContext.retrieve(session,NBSConstantUtil.DSProgramArea);
          conditionCode =(String) NBSContext.retrieve(session,NBSConstantUtil.DSConditionCode);
          //System.out.println("conditionCode in add lab is :" + conditionCode);
          request.setAttribute("conditionCode", conditionCode );
          request.setAttribute("programAreaCd1", programArea);
          String jurisdiction = (String) NBSContext.retrieve(session,NBSConstantUtil.DSJurisdiction);
          request.setAttribute("jurisdictionCd1", jurisdiction);
          boolean AddObservationLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
                                                             NBSOperationLookup.ADD,
                                                             programArea,jurisdiction);
            if (AddObservationLab == false) {
              session.setAttribute("error", "Failed at security checking.");
              throw new ServletException("Failed at security checking.");
            }
            else{
              request.setAttribute("trueReadOnly", "true");
            }

        }
        else if(sCurrTask.equalsIgnoreCase("AddObservationLab2"))
        {
          boolean check2 = secObj.getPermission(NBSBOLookup.
          									OBSERVATIONLABREPORT,
                                          NBSOperationLookup.ADD, "ANY",
                                          "ANY");
          if (check2 == false) {
            session.setAttribute("error", "Failed at security checking.");
            throw new ServletException("Failed at security checking.");
          }

          Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
          PersonVO personVO = this.findMasterPatientRecord(mprUid, session, secObj);
          request.setAttribute("jurisdictionCd", personVO.getDefaultJurisdictionCd());
          if(!ldfCreatedForPatient)
          {
            createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
            ldfCreatedForPatient = true;
          }
        }

        if (contextAction.equalsIgnoreCase("AddLabDataEntry") ||
            contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")){

	boolean permissionForProgJuriShar = secObj.getPermission(NBSBOLookup.
           OBSERVATIONLABREPORT,
           NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);
       request.setAttribute("countiesInState",PersonUtil.getCountiesByState(PersonUtil.getDefaultStateCd()));
       request.setAttribute("PermissionForProgJuriShar",
                            String.valueOf(permissionForProgJuriShar));
       request.setAttribute("programAreaCd", String.valueOf("NONE"));
       request.setAttribute("jurisdictionCd", String.valueOf("NONE"));

       retainCheckbox = true;
       request.setAttribute("retainCheckbox", String.valueOf(retainCheckbox));
       tm = NBSContext.getPageContext(session, "PS192", contextAction);
       sCurrTask = NBSContext.getCurrentTask(session);
       if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1"))
       {
          request.setAttribute("ContextAction", tm.get("Submit"));
       }
       if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1") ||
           sCurrTask.equalsIgnoreCase("AddObservationLab2")) {

         boolean AddObservationLab = secObj.getPermission(NBSBOLookup.
             OBSERVATIONLABREPORT,
             NBSOperationLookup.ADD,
             "ANY", "ANY");
         if (AddObservationLab == false) {
           session.setAttribute("error", "Failed at security checking.");
           throw new ServletException("Failed at security checking.");
         }
       }


       try {
         //this is for leftNav show personLcaolId when retain person checked
         request.setAttribute("showPersonLocalId", "false");
         if (patient) {
           if (retainPersonVO != null) {

             this.convertPersonToRequestObj(retainPersonVO, request);
             request.setAttribute("personUid", retainPersonVO.getThePersonDT().getPersonParentUid().toString());
             request.setAttribute("patientLocalId", retainPersonVO.getThePersonDT().getLocalId());
             request.setAttribute("patientDisplayLocalId", PersonUtil.getDisplayLocalID(retainPersonVO.getThePersonDT().getLocalId()));

             Collection<Object>  names = retainPersonVO.getThePersonNameDTCollection();
             if (names != null) {
              Iterator<Object>  iter = names.iterator();
               while (iter.hasNext()) {
                 PersonNameDT name = (PersonNameDT) iter.next();
                 if (name != null) {
                   if (name != null && name.getNmUseCd() != null &&
                       name.getNmUseCd().equals(NEDSSConstants.LEGAL)) {
                     String patientName = "";
                     if (name.getFirstNm() != null)
                       patientName = name.getFirstNm();
                     if (name.getLastNm() != null)
                       patientName =  patientName + " " + name.getLastNm();
                     request.setAttribute("patientName", patientName);
                   }
                 }
               }
             }

             Collection<Object>  ids = retainPersonVO.getTheEntityIdDTCollection();
           if (ids != null) {
            Iterator<Object>  iter = ids.iterator();
             String entity = null;
             while (iter.hasNext()) {
               EntityIdDT id = (EntityIdDT) iter.next();
               if (id != null) {
                 request.setAttribute("patientSSN", id.getRootExtensionTxt());
               }
             }
           }

           Collection<Object>  addresses = retainPersonVO.getTheEntityLocatorParticipationDTCollection();
           if (addresses != null) {
           Iterator<Object>  iter = addresses.iterator();
            while (iter.hasNext()) {
              EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.next();
              if (elp != null) {
                PostalLocatorDT postal = elp.getThePostalLocatorDT();
                if (postal != null) {
                  StringBuffer address = new StringBuffer();

                  if (postal.getStreetAddr1() != null && !postal.getStreetAddr1().equals("")){
                    address.append(postal.getStreetAddr1());
                    if ((postal.getCityDescTxt() != null && !postal.getCityDescTxt().equals("") ) ||
                        (postal.getStateCd() != null && !postal.getStateCd().equals("")) ||
                        (postal.getZipCd() != null && !postal.getZipCd().equals(""))){
                      address.append(", ");
                    }
                  }
                  if (postal.getCityDescTxt() != null && !postal.getCityDescTxt().equals("") ){
                    address.append(postal.getCityDescTxt());
                    if ( (postal.getStateCd() != null && !postal.getStateCd().equals("")) ||
                        (postal.getZipCd() != null && !postal.getZipCd().equals(""))){
                      address.append(", ");
                    }
                  }
                  if (postal.getStateCd() != null && !postal.getStateCd().equals("")){

                    CachedDropDownValues srtValues = new CachedDropDownValues();
                    TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
                    String desc = "";
                    if (postal.getStateCd() != null && treemap.get(postal.getStateCd()) != null){
                      desc = (String) treemap.get(postal.getStateCd());
                    }

                    address.append(desc);
                    if ((postal.getZipCd() != null && !postal.getZipCd().equals(""))){
                      address.append(", ");
                    }
                  }
                  if (postal.getZipCd() != null && !postal.getZipCd().equals("")){
                    address.append(postal.getZipCd());
                  }

                  request.setAttribute("patientAddress", address.toString());

                }
              }
            }
          }


             if (retainPersonVO.getThePersonDT().getBirthTime() != null)
               request.setAttribute("birthTime", StringUtils.formatDate(retainPersonVO.getThePersonDT().getBirthTime()));

             if (retainPersonVO.getThePersonDT().getCurrSexCd() != null)
               request.setAttribute("currSexCd", retainPersonVO.getThePersonDT().getCurrSexCd());


             //this is for leftNav show personLcaolId when retain person checked
             request.setAttribute("showPersonLocalId", "true");
           }
           request.setAttribute("DSFileTab","2");
         }
         else
         {
           request.setAttribute("DSFileTab", "1");
           request.setAttribute("DEM162",PersonUtil.getDefaultStateCd());
           request.setAttribute("defaultStateFlag","true");
         }

         if (facility) {
           if (labInformationVO != null) {
             Map<Object,Object> map = labInformationVO.getDescriptionText();
             String reportingSourceDetails = (String) map.get("1");
             String reportingFacilityUID = (String) map.get("0");
             String reportingLabId = (String) map.get("2");

             //System.out.println("reportingFacilityUID is :" +reportingFacilityUID);
             request.setAttribute("reportingSourceDetails",
                                  reportingSourceDetails);

             request.setAttribute("labId", reportingLabId);

             request.setAttribute("reportingFacilityUID", reportingFacilityUID);
             String Clia = CommonLabUtil.getCliaValue(reportingFacilityUID, session);
             CommonLabUtil.getDropDownLists(reportingFacilityUID,
                                   NEDSSConstants.ORDERED_TEST_LOOKUP +NEDSSConstants.RESULTED_TEST_LOOKUP,
                                   conditionCode, NEDSSConstants.PROGRAM_AREA_NONE,
                                   null,
                                   null,
                                   request);
           }
         }
       }
       catch (Exception ne) {
         //do nothing, since for the first time load, there will be no personVO in the objectStore
       }

       if (patient && facility) {
         NBSContext.store(session, "DSFileTab", "2");
       }

       request.setAttribute("formHref",
                            "/nbs/" + sCurrTask + ".do");
       //this will set ContextAction for leftnav and file also
       request.setAttribute("ContextAction",tm.get("Submit"));


       request.setAttribute("Cancel",
                            "/nbs/" + sCurrTask + ".do?ContextAction=" +
                               tm.get("Cancel"));

          //ErrorMessageHelper.setErrMsgToRequest(request, "PS192");
          //ErrorMessageHelper.setErrMsgToRequest(request,"PS198");
          ArrayList<Object> list = new ArrayList<Object> ();
          list.add("PS192");
          list.add("PS198");
          ErrorMessageHelper.setErrMsgToRequest(request, list);


        }
        else if (contextAction.equalsIgnoreCase("AddLab")){
         retainCheckbox = false;
         request.setAttribute("retainCheckbox",String.valueOf(retainCheckbox));
         Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
         request.setAttribute("personUID", mprUid.toString());
         tm = NBSContext.getPageContext(session, "PS015", contextAction);
         sCurrTask = NBSContext.getCurrentTask(session);
        // System.out.println("\n\n after getPageCOntext load " + sCurrTask);
         ArrayList<Object> stateList = new ArrayList<Object> ();
         PersonVO pvo = new PersonVO();
         pvo = this.findMasterPatientRecord(mprUid, session, secObj);
         if(!ldfCreatedForPatient)
         {
           createXSP(NEDSSConstants.PATIENT_LDF, pvo, null, request);
           ldfCreatedForPatient = true;
         }
         try {
         PersonUtil.convertPersonToRequestObj(pvo,request,"AddPatientFromEvent",stateList);
         }catch(Exception ex) {
 			throw new ServletException(ex.getMessage());
 		}
         ArrayList<Object> list = new ArrayList<Object> ();
         list.add("PS015");
         list.add("ps187");
         ErrorMessageHelper.setErrMsgToRequest(request,list);
         request.setAttribute("formHref", "/nbs/"+ sCurrTask+ ".do");
         request.setAttribute("ContextAction",tm.get("Submit"));
         //request.setAttribute("formHref", "/nbs/"+ sCurrTask+ ".do");
         request.setAttribute("Submit", "/nbs/"+ sCurrTask+ ".do?ContextAction=" + tm.get("Submit"));
         request.setAttribute("Cancel", "/nbs/"+ sCurrTask+ ".do?ContextAction=" + tm.get("Cancel"));
         request.setAttribute("SubmitAndCreateInvestigation", "/nbs/"+ sCurrTask+ ".do?ContextAction=" + tm.get("SubmitAndCreateInvestigation"));
         request.setAttribute("SubmitNoViewAccess", "/nbs/"+ sCurrTask+ ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
         request.setAttribute("description", "");
     }

     boolean checkInvestigationAddPermission = secObj.getPermission(
                                                                    NBSBOLookup.INVESTIGATION,
                                                                    NBSOperationLookup.ADD,
                                                                    ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                                                    ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
                                                                    ProgramAreaJurisdictionUtil.SHAREDISTRUE);

         request.setAttribute("ExternalFlag",String.valueOf(checkInvestigationAddPermission));
         //setting values if no information is set
         //request.setAttribute("reportingSourceDetails", "There is no Reporting Facility selected.");
         request.setAttribute("orderingOrgDetails", "There is no Ordering Facility selected.");
         request.setAttribute("providerDetails", "There is no Ordering Provider selected.");
         //gets the jurisdiction from security object and sets it to request
         CommonLabUtil commonLabUtil = new CommonLabUtil();
         commonLabUtil.getNBSSecurityJurisdictionsPA(request, secObj, contextAction);
         request.setAttribute("mode","ADD");

         ObservationForm obsForm =(ObservationForm)aForm;
         obsForm.reset();
         /**
          * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
       	commonLabUtil.loadLabLDFAdd(request);
        */
         String retCheckBox  = (String)request.getAttribute("retainCheckbox");
         if(retCheckBox == null||!retCheckBox.equalsIgnoreCase("true"))
           {
             //commonLabUtil.loadPatientLDFAdd(request); do nothing as the Patient related LDF are already populated
           }else
             {
               if(!ldfCreatedForPatient)
               {
                 createXSP(NEDSSConstants.PATIENT_LDF, retainPersonVO, null, request);
                 ldfCreatedForPatient = true;
               }
             }
         
         // set tab order before we forward, xz 11/01/2004
         sCurrTask = NBSContext.getCurrentTask(session);
         if (sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1"))
         {
            request.setAttribute("DSFileTab", new Integer(NEDSSConstants.EVENT_TAB_PATIENT_ORDER).toString());
         }
         else
         {
            request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultLabTabOrder()).toString());
         }        

        return (mapping.findForward("XSP"));
    }
    private PersonVO findMasterPatientRecord(Long mprUId, HttpSession session, NBSSecurityObj secObj)
    {

      PersonVO personVO = null;
      MainSessionCommand msCommand = null;

      try
      {

          String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
          String sMethod = "getMPR";
          Object[] oParams = new Object[] { mprUId };
          MainSessionHolder holder = new MainSessionHolder();
          msCommand = holder.getMainSessionCommand(session);

          ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          personVO = (PersonVO)arr.get(0);
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
          if (session == null)
          {
              logger.error("Error: no session, please login");
          }

          logger.fatal("personVO: ", ex);
      }

      return personVO;
  }
  private Map<Object,Object> getOrganization(String organizationUid, HttpSession session)
      throws    Exception
  {
    Map<Object,Object> returnMap = new HashMap<Object,Object>();
    StringBuffer result = new StringBuffer("");
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "getOrganization";
    Object[] oParams = new Object[] {
        new Long(organizationUid)};

    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
    if (organizationVO != null) {

      if (organizationVO.getTheOrganizationNameDTCollection() != null) {
       Iterator<Object>  orgNameIt = organizationVO.
            getTheOrganizationNameDTCollection().
            iterator();
        while (orgNameIt.hasNext()) {
          OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.
              next();
          result.append(orgName.getNmTxt());
        }
      }
      if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
        if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
         Iterator<Object>  orgLocatorIt = organizationVO.
              getTheEntityLocatorParticipationDTCollection().iterator();
          while (orgLocatorIt.hasNext()) {
            EntityLocatorParticipationDT entityLocatorDT = (
                EntityLocatorParticipationDT) orgLocatorIt.next();
            if (entityLocatorDT != null) {
              PostalLocatorDT postaLocatorDT = entityLocatorDT.
                  getThePostalLocatorDT();
              if (postaLocatorDT != null) {
                if (postaLocatorDT.getStreetAddr1() != null) {
                  result.append("<br/>").append(postaLocatorDT.
                                               getStreetAddr1());
                }
                if (postaLocatorDT.getStreetAddr2() != null) {
                  result.append("<br/>").append(postaLocatorDT.
                                               getStreetAddr2());
                }
                if (postaLocatorDT.getCityDescTxt() != null) {
                  result.append("<br/>").append(postaLocatorDT.getCityDescTxt());

                }
                  if( postaLocatorDT.getStateCd() != null)
               {
                 CachedDropDownValues cdv = new CachedDropDownValues();
                 try {
                   result.append(", ").append(cdv.
                       getCachedStateCodeList().
                       get(postaLocatorDT.getStateCd()).
                       toString());
                 }
                 catch (Exception ex) {
                 }


                }
                if (postaLocatorDT.getZipCd() != null) {
                  result.append(" ").append(postaLocatorDT.getZipCd());

                }
              }
            }
            TeleLocatorDT telelocatorDT = entityLocatorDT.
                getTheTeleLocatorDT();
            if (telelocatorDT != null) {
              if (telelocatorDT.getPhoneNbrTxt() != null) {
                result.append("<br/>").append(telelocatorDT.
                                             getPhoneNbrTxt());
              }
              if (telelocatorDT.getExtensionTxt() != null) {
                result.append(" Ext. ").append(telelocatorDT.
                                               getExtensionTxt());
              }
              break;
            }
          }
        }
      }
    }

    returnMap.put("UID", organizationUid);
    returnMap.put("result", result.toString());
    return returnMap;

  }
  public void convertPersonToRequestObj(PersonVO personVO,
                                      HttpServletRequest request)
  {

    if (personVO != null && personVO.getThePersonDT() != null) {

      PersonDT personDT = personVO.getThePersonDT();

      request.setAttribute("DEM115",
                           StringUtils.formatDate(personDT.getBirthTime()));
      request.setAttribute("DEM216",
                           (String) personDT.getAgeReported());
      request.setAttribute("DEM218",
                           personDT.getAgeReportedUnitCd());
      request.setAttribute("DEM128",
                           StringUtils.formatDate(personDT.getDeceasedTime()));
      request.setAttribute("DEM113", personDT.getCurrSexCd());

      request.setAttribute("DEM140", personDT.getMaritalStatusCd());

      request.setAttribute("DEM196", personDT.getDescription());

      request.setAttribute("DEM155", personDT.getEthnicGroupInd());

      this.setPersonELPToRequest(personVO, request);
      this.setPersonEthnicity(personVO, request);
      this.setPersonNameToRequest(personVO, request);
      this.setPersonRaceToRequest(personVO, request);
      this.setPersonSSNToRequest(personVO, request);

    }
  }
  private void setPersonELPToRequest(PersonVO personVO,
                                     HttpServletRequest request)
  {
    Collection<Object>  addresses = personVO.
        getTheEntityLocatorParticipationDTCollection();

    if (addresses != null)
    {
     Iterator<Object>  iter = addresses.iterator();
      while (iter.hasNext())
      {
        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) iter.
            next();
        if (elp != null)
        {
         // if (
           //   elp.getClassCd().equals("PST"))
          //{
            PostalLocatorDT postal = elp.getThePostalLocatorDT();
            if (postal != null)
            {
              request.setAttribute("defaultStateFlag","false");
              request.setAttribute("DEM159", postal.getStreetAddr1());
              request.setAttribute("DEM161", postal.getCityDescTxt());
              request.setAttribute("DEM162", postal.getStateCd());
              request.setAttribute("DEM163", postal.getZipCd());
              request.setAttribute("DEM165", postal.getCntyCd());
              request.setAttribute("countiesInState",
                                   PersonUtil.
                                   getCountiesByState(postal.getStateCd()));
            }
          //}
        }
        if (elp.getClassCd().equals("TELE"))
        {
          TeleLocatorDT tele = elp.getTheTeleLocatorDT();
          if (tele != null)
          {
            request.setAttribute("DEM177", tele.getPhoneNbrTxt());
          }
        }
      }
    }
  }

  private void setPersonEthnicity(PersonVO personVO,
                                  HttpServletRequest request)
  {
    //set ethnicity
    Collection<Object>  ethnicities = personVO.getThePersonEthnicGroupDTCollection();
    if (ethnicities != null)
    {
     Iterator<Object>  iter = ethnicities.iterator();

      if (iter != null)
      {
        while (iter.hasNext())
        {
          PersonEthnicGroupDT ethnic = (PersonEthnicGroupDT) iter.
              next();
          request.setAttribute("DEM155",
                               ethnic.getEthnicGroupDescTxt());

        }
      }
    }
  }
 private void setPersonNameToRequest(PersonVO personVO,
                                    HttpServletRequest request)
 {
  //personName collection
  PersonDT personDT = personVO.getThePersonDT();
  Collection<Object>  names = personVO.getThePersonNameDTCollection();
  if (names != null)
  {
   Iterator<Object>  iter = names.iterator();
    while (iter.hasNext())
    {
      PersonNameDT name = (PersonNameDT) iter.next();
      if (name != null)
      {
        if (name != null && name.getNmUseCd() != null &&
            name.getNmUseCd().equals(NEDSSConstants.LEGAL))
        {
          request.setAttribute("DEM102", name.getLastNm());
          request.setAttribute("DEM104", name.getFirstNm());
          request.setAttribute("DEM105", name.getMiddleNm());
          request.setAttribute("DEM107", name.getNmSuffix());
        }
      }
    }
  }
 }

 private void setPersonRaceToRequest(PersonVO personVO,
                                    HttpServletRequest request)
 {
  Collection<Object>  races = personVO.getThePersonRaceDTCollection();
  if (races != null)
  {
   Iterator<Object>  iter = races.iterator();
    while (iter.hasNext())
    {
      PersonRaceDT raceDT = (PersonRaceDT) iter.next();

      if (raceDT.getRaceCd().equals("U"))
      {
        request.setAttribute("unknownRace", "y");
      }
      if (raceDT.getRaceCd().equals("1002-5"))
      {
        request.setAttribute("americanIndianController", "y");
      }
      if (raceDT.getRaceCd().equals("2028-9"))
      {
        request.setAttribute("asianController", "y");
      }
      if (raceDT.getRaceCd().equals("2054-5"))
      {
        request.setAttribute("africanAmericanController", "y");
      }
      if (raceDT.getRaceCd().equals("2076-8"))
      {
        request.setAttribute("hawaiianController", "y");
      }
      if (raceDT.getRaceCd().equals("2106-3"))
      {
        request.setAttribute("whiteController", "y");
      }
      if (raceDT.getRaceCd().equals("2131-1"))
      {
        request.setAttribute("OtherRace", "y");
        request.setAttribute("OtherRaceDescText", raceDT.getRaceDescTxt());
      }
    }
  }
 }

 private void setPersonSSNToRequest(PersonVO personVO,
                                   HttpServletRequest request)
 {
  //get SSN from entityId collection
  Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
  if (ids != null) {
   Iterator<Object>  iter = ids.iterator();
    String entity = null;
    while (iter.hasNext()) {
      EntityIdDT id = (EntityIdDT) iter.next();
      if (id != null) {
        request.setAttribute("DEM133", id.getRootExtensionTxt());
      }
    }
  }
 }


}
