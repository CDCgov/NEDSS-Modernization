package gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis;

import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.util.*;

import javax.servlet.http.*;

import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;

public class HepatitisInvestigationUtil extends CommonInvestigationUtil
{

    static final LogUtils logger = new LogUtils(HepatitisInvestigationUtil.class.getName());
    static final  QuickEntryEventHelper helper = new QuickEntryEventHelper();
	
    public HepatitisInvestigationUtil()
    {
    }

    public boolean setGenericRequestForView(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        logger.debug("debug.setGenericRequestForView.begin");
        this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
        this.convertPersonsToRequestHep(investigationProxyVO, request);
        this.convertOrganizationToRequestHep(investigationProxyVO, request);
        this.convertObservationsToRequest(investigationProxyVO, request);
        this.convertNotificationSummaryToRequest(investigationProxyVO, request);
        this.convertObservationSummaryToRequest(investigationProxyVO, request);
        this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
        this.convertTreatmentSummaryToRequest(investigationProxyVO, request);
        this.convertDocumentSummaryToRequest(investigationProxyVO, request);

        if(getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO) != null)
        {
            getVaccinationSummaryRecords(getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getPersonUid(), request);
        }
        // need to determine if there exist supplimental collection , if so show supplimental tab, if not just show hep core
        boolean supplimentalExists = false;
        Boolean ldfExists = (Boolean)request.getAttribute("ldfExists");
        if(ldfExists == null)
          ldfExists = request.getSession().getAttribute("ldfExists") == null ? null : (Boolean)request.getSession().getAttribute("ldfExists");
        Collection<ObservationVO>  theObservationCollection  = investigationProxyVO.getTheObservationVOCollection();
        if(ldfExists!= null && ldfExists.booleanValue())
        {
          request.setAttribute("hasDiagnosis", "true");
          supplimentalExists = true;
        }
        else if(theObservationCollection  != null)
        {
           Iterator<ObservationVO>  anIterator = theObservationCollection.iterator();
            while(anIterator.hasNext())
            {
                ObservationVO observationVO = (ObservationVO)anIterator.next();
                if(observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null
                && observationVO.getTheObservationDT().getCtrlCdDisplayForm().equals(NEDSSConstants.supplemental))
                {
                    request.setAttribute("hasDiagnosis", "true");
                    supplimentalExists = true;
                    break;
                }
            }
        }

        logger.debug("debug.ejenkin6.setGenericRequestForView.end");
        return supplimentalExists;
    }

    public void convertProxyToRequestForDiagnosis( InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
        this.convertObservationsToRequest(investigationProxyVO, request);
        String dateAssignedToInvestigation =  request.getParameter("dateAssignedToInvestigation");
        request.setAttribute("dateAssignedToInvestigation", dateAssignedToInvestigation);

    }


    /***************************************************************************
     * storing the multiple selects in whole form
     */
    public void setMultipleSelects(HttpServletRequest request, Collection<ObservationVO>  obsColl)
    {
        logger.debug("debug.ejenkin6.setMultipleSelects.begin");
        ObservationVO obsVO = null;
        ObsValueCodedDT obsValueCodedDT = null;
        //  reasonForTesting
        String[] arrayReasonForTesting = request.getParameterValues("HEP100");
        this.setMultipleValueToObservation(arrayReasonForTesting, "HEP100", obsColl);
        //  type of contact
        String[] arrayTypeOfContact = request.getParameterValues("typeOfContact");
        this.setMultipleValueToObservation(arrayTypeOfContact, "HEP130", obsColl);
        //  where did they travel
        String[] arrayWhereDidTheyTravel = request.getParameterValues("whereDidTheyTravel");
        this.setMultipleValueToObservation(arrayWhereDidTheyTravel, "HEP140", obsColl);
        //  where did they travel 2
        String[] arrayWhereDidTheyTravel2 = request.getParameterValues("whereDidTheyTravel2");
        this.setMultipleValueToObservation(arrayWhereDidTheyTravel2, "HEP142", obsColl);
//        logger.info("Create VO report after calls to setMultipletoObs");
//        VOTester.createReport(obsColl, "after setMult to obs");
        //  typeOfContactB
        String[] arrayTypeOfContactB = request.getParameterValues("typeOfContactB");
        this.setMultipleValueToObservation(arrayTypeOfContactB, "HEP153", obsColl);
        //  tattoos

        String[] arrayTattoos = request.getParameterValues("tattoos");
        this.setMultipleValueToObservation(arrayTattoos, "HEP172", obsColl);
        //  piercings
        String[] arrayPiercings = request.getParameterValues("piercings");
        this.setMultipleValueToObservation(arrayPiercings, "HEP175", obsColl);
        //  typeOfFacility
        String[] arrayTypeOfFacility = request.getParameterValues("typeOfFacility");
        this.setMultipleValueToObservation(arrayTypeOfFacility, "HEP182", obsColl);
        //  typeOfContactC
        String[] arrayTypeOfContactC = request.getParameterValues("typeOfContactC");
        this.setMultipleValueToObservation(arrayTypeOfContactC, "HEP193", obsColl);
        //  tattoos HEPC
        String[] arrayTattoosHepC = request.getParameterValues("tattoosHepC");
        this.setMultipleValueToObservation(arrayTattoosHepC, "HEP204", obsColl);
        //  piercings HEPC
        String[] arrayPiercingsHepC = request.getParameterValues("piercingsHepC");
        this.setMultipleValueToObservation(arrayPiercingsHepC, "HEP207", obsColl);
        //  typeOfFacility HEPC
        String[] arrayTypeOfFacilityHepC = request.getParameterValues("typeOfFacilityHepC");
        this.setMultipleValueToObservation(arrayTypeOfFacilityHepC, "HEP222", obsColl);
        //  set mother's race and ethnicity
       String[] ethnicities = request.getParameterValues("ethnicities");
       this.setMultipleValueToObservation(ethnicities, "HEP257", obsColl);
       String[] arrayLevel1Race = request.getParameterValues("level1race");
       this.setMultipleValueToObservation(arrayLevel1Race, "HEP239", obsColl);
       String[] arrayLevel2Race = request.getParameterValues("level2race");
       this.setMultipleValueToObservation(arrayLevel2Race, "HEP256", obsColl);

    }

        /***************************************************************************
         * storing the multiple selects in whole form
         */
        public void setMultipleSelectForEdit(HttpServletRequest request, Collection<ObservationVO>  obsColl, Map<Object,Object> obsMap)
        {
            logger.debug("debug.ejenkin6.setMultipleSelects.begin");
            ObservationVO obsVO = null;
            ObsValueCodedDT obsValueCodedDT = null;
            //  reasonForTesting
            String[] arrayReasonForTesting = request.getParameterValues("HEP100");
            super.setMultipleToObservationForEdit(arrayReasonForTesting, "HEP100", obsColl, obsMap);
            //  type of contact
            String[] arrayTypeOfContact = request.getParameterValues("HEP130");
            super.setMultipleToObservationForEdit(arrayTypeOfContact, "HEP130", obsColl, obsMap);
            //  where did they travel
            String[] arrayWhereDidTheyTravel = request.getParameterValues("HEP140");
            super.setMultipleToObservationForEdit(arrayWhereDidTheyTravel, "HEP140", obsColl, obsMap);
            //  where did they travel 2
            String[] arrayWhereDidTheyTravel2 = request.getParameterValues("HEP142");
            super.setMultipleToObservationForEdit(arrayWhereDidTheyTravel2, "HEP142", obsColl, obsMap);
//        logger.info("Create VO report after calls to setMultipletoObs");
//        VOTester.createReport(obsColl, "after setMult to obs");
            //  typeOfContactB
            String[] arrayTypeOfContactB = request.getParameterValues("HEP153");
            super.setMultipleToObservationForEdit(arrayTypeOfContactB, "HEP153", obsColl, obsMap);
            //  tattoos

            String[] arrayTattoos = request.getParameterValues("HEP172");
            super.setMultipleToObservationForEdit(arrayTattoos, "HEP172", obsColl, obsMap);
            //  piercings
            String[] arrayPiercings = request.getParameterValues("HEP175");
            super.setMultipleToObservationForEdit(arrayPiercings, "HEP175", obsColl, obsMap);
            //  typeOfFacility
            String[] arrayTypeOfFacility = request.getParameterValues("HEP182");
            super.setMultipleToObservationForEdit(arrayTypeOfFacility, "HEP182", obsColl, obsMap);
            //  typeOfContactC
            String[] arrayTypeOfContactC = request.getParameterValues("HEP193");
            super.setMultipleToObservationForEdit(arrayTypeOfContactC, "HEP193", obsColl, obsMap);
            //  tattoos HEPC
            String[] arrayTattoosHepC = request.getParameterValues("HEP204");
            super.setMultipleToObservationForEdit(arrayTattoosHepC, "HEP204", obsColl, obsMap);
            //  piercings HEPC
            String[] arrayPiercingsHepC = request.getParameterValues("HEP207");
            super.setMultipleToObservationForEdit(arrayPiercingsHepC, "HEP207", obsColl, obsMap);
            //  typeOfFacility HEPC
            String[] arrayTypeOfFacilityHepC = request.getParameterValues("HEP222");
            super.setMultipleToObservationForEdit(arrayTypeOfFacilityHepC, "HEP222", obsColl, obsMap);
            //  set mother's race and ethnicity
           String[] ethnicities = request.getParameterValues("HEP257");
           super.setMultipleToObservationForEdit(ethnicities, "HEP257", obsColl, obsMap);
           String[] arrayLevel1Race = request.getParameterValues("HEP239");
           super.setMultipleToObservationForEdit(arrayLevel1Race, "HEP239", obsColl, obsMap);
           String[] arrayLevel2Race = request.getParameterValues("HEP256");
           super.setMultipleToObservationForEdit(arrayLevel2Race, "HEP256", obsColl, obsMap);

        }

       public void setMultiSelectReload(HttpServletRequest request)
        {

          String[] arrayReasonForTesting = request.getParameterValues("HEP100");
          if(arrayReasonForTesting != null)
          {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arrayReasonForTesting.length; i++)
            {
               //don't store empty strings in parameter string array
               if (!(arrayReasonForTesting[i].trim()).equals(""))
               {
                 sb.append(arrayReasonForTesting[i]).append("|");
               }
           }
           if(sb.length() > 0)
           request.setAttribute("HEP100", sb.toString());
          }

        }


    /**********************************************************************************
     * supplimental
     */
    public int setSuplimentalObseravationsAndARForEdit(InvestigationForm investigationForm, String investigationFormCd, HttpServletRequest request, int tempID)
    {
        logger.debug("debug.ejenkin6.setSuplimentalObseravationsAndARForEdit.begin");
//        CachedDropDownValues srtc = new CachedDropDownValues();
        Collection<ObservationVO>  supplimentObservations = investigationForm.getSupplementalCollection();
        Collection<ObservationVO>  theObservationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipCollection   = investigationForm.getProxy().getTheActRelationshipDTCollection();
        if(actRelationshipCollection   == null)
        actRelationshipCollection   = new ArrayList<Object> ();
        ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_HEP");
        if(formObservationVO != null)
        {
            formObservationVO.getTheObservationDT().setCd(investigationFormCd);
            formObservationVO.getTheObservationDT().setItDirty(true);
            formObservationVO.getTheObservationDT().setItNew(false);
            formObservationVO.setItDirty(true);
            formObservationVO.setItNew(false);
        }
        if(supplimentObservations != null && supplimentObservations.size() > 0 && theObservationVOCollection  != null)
        {
          TreeMap<Object,Object> obsActTreeMap = super.setObservations(supplimentObservations, investigationForm.getObservationMap(), "T",formObservationVO.getTheActRelationshipDTCollection(), formObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q, request, tempID);
          theObservationVOCollection.addAll((ArrayList<ObservationVO> )obsActTreeMap.get("observations"));
          actRelationshipCollection.addAll((ArrayList<Object> )obsActTreeMap.get("actrelations"));
          tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();

            if(actRelationshipCollection.size() > 0)
            {
//                logger.info("setTheActRelationshipDTCollection  supplimental obseravtions for Edit");
                investigationForm.getProxy().setTheActRelationshipDTCollection(actRelationshipCollection);
            }
        }
        else
        {
//            logger.info("no supplimentObservations for this investigation: " + investigationFormCd);
        }
        logger.debug("debug.ejenkin6.setSuplimentalObseravationsAndARForEdit.end");
        return tempID;
    }

    public void setSuplimentalObseravationsToFormOnLoad(InvestigationForm investigationForm, HttpServletRequest request)
    {
        logger.debug("debug.ejenkin6.setSuplimentalObseravationsToFormOnLoad.begin");
//        logger.debug("in setSuplimentalObseravationsToFormOnLoad");
        //request.setAttribute("hasDiagnosis", "false");
        Collection<Object>  supplObservationCollection  = new ArrayList<Object> ();
        Collection<ObservationVO>  theObservationCollection  = investigationForm.getOldProxy().getTheObservationVOCollection();
        if(theObservationCollection  != null)
        {
           Iterator<ObservationVO>  anIterator = theObservationCollection.iterator();
            while(anIterator.hasNext())
            {
                ObservationVO observationVO = (ObservationVO)anIterator.next();
                if(observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null && observationVO.getTheObservationDT().getCtrlCdDisplayForm().equals(NEDSSConstants.supplemental))
                {
                    supplObservationCollection.add(observationVO);
                }
            }
        }
        if(supplObservationCollection.size() > 0)
        {
//            logger.info("supplObservationCollection.size(): " + supplObservationCollection.size());
 //           investigationForm.setSupplementalCollection(supplObservationCollection);
           // request.setAttribute("hasDiagnosis", "true");
        }
        else
        {
            //request.setAttribute("hasDiagnosis", "false");
        }
        logger.debug("debug.ejenkin6.setSuplimentalObseravationsToFormOnLoad.end");
    }

    public int setSupplimentObservationAndARForCreate(InvestigationForm investigationForm, int tempID, String investigationFormCd, HttpServletRequest request)
    {

        Collection<ObservationVO>  supplimentObservations = investigationForm.getSupplementalCollection();
        Collection<ObservationVO>  theObservationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipCollection   = investigationForm.getProxy().getTheActRelationshipDTCollection();
        ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_HEP");
        if(formObservationVO != null)
        {
            formObservationVO.getTheObservationDT().setCd(investigationFormCd);
            formObservationVO.getTheObservationDT().setItNew(true);
            formObservationVO.getTheObservationDT().setItDirty(false);
            formObservationVO.setItDirty(false);
            formObservationVO.setItNew(true);
        }
        if(supplimentObservations != null && supplimentObservations.size() > 0 && theObservationVOCollection  != null)
        {
          TreeMap<Object,Object> obsActTreeMap = super.setObservations(supplimentObservations, new TreeMap(), "T",formObservationVO.getTheActRelationshipDTCollection(), formObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q, request, tempID);
          theObservationVOCollection.addAll((ArrayList<ObservationVO> )obsActTreeMap.get("observations"));
          actRelationshipCollection.addAll((ArrayList<Object> )obsActTreeMap.get("actrelations"));
          tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();
        }
        else
        {
//            logger.info("no supplimentObservations for this investigation: " + investigationFormCd);
        }
        logger.debug("debug.ejenkin6.setSupplimentObservationAndARForCreate.end");
        return tempID;
    }


    public void prepareEntitiesForReload(HttpServletRequest request)
    {
        StringTokenizer parsedString = null;
        String stringToParse = "";
        
        //Investigator
        stringToParse = request.getParameter("investigator.personUid-values") == null ? "" : request.getParameter("investigator.personUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("investigatorUID", request.getParameter("investigator.personUid"));
	        request.setAttribute("investigatorDemographics", stringToParse);
	        request.setAttribute("investigator.personUid-values", stringToParse);
        }
        
        //  reporter org
        stringToParse = request.getParameter("reportingOrg.organizationUID-values") == null ? "" : request.getParameter("reportingOrg.organizationUID-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("reportingOrgUID", request.getParameter("reportingOrg.organizationUID"));
	        request.setAttribute("reportingOrgDemographics", stringToParse);
	        request.setAttribute("reportingOrg.organizationUID-values", stringToParse);
        }
        
        //reporter person
        stringToParse = request.getParameter("reporter.personUid-values") == null ? "" : request.getParameter("reporter.personUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("reporterPersonUid", request.getParameter("reporter.personUid"));
	        request.setAttribute("reporterDemographics", stringToParse);
	        request.setAttribute("reporter.personUid-values", stringToParse);
        }    
        //physician
        stringToParse = request.getParameter("physician.personUid-values") == null ? "" : request.getParameter("physician.personUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("physicianPersonUid", request.getParameter("physician.personUid"));
	        request.setAttribute("physicianDemographics", stringToParse);
	        request.setAttribute("physician.personUid-values", stringToParse);
        }    
        // hospital organization
        stringToParse = request.getParameter("hospitalOrg.organizationUID-values") == null ? "" : request.getParameter("hospitalOrg.organizationUID-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("hospitalOrgUID", request.getParameter("hospitalOrg.organizationUID"));
	        request.setAttribute("hospitalDemographics", stringToParse);
	        request.setAttribute("hospitalOrg.organizationUID-values", stringToParse);
        }    
        // reason for testing
        String[] reasons = request.getParameterValues("HEP100");
        StringBuffer reasonsBuffer = new StringBuffer("");
        if(reasons != null)
        {
            for(int i = 0; i < reasons.length; i++)
            {
                String strVal = reasons[i];
                reasonsBuffer.append(strVal).append("|");
            }
        }
        request.setAttribute("HEP100", reasonsBuffer.toString());
        request.setAttribute("dateAssignedToInvestigation", request.getParameter("dateAssignedToInvestigation"));
        //  hidden values
        request.setAttribute("NBSSecurityJurisdictions", request.getParameter("NBSSecurityJurisdictions").toString());
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd").toString());
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.progAreaCd", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.progAreaCd").toString());
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cdDescTxt", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cdDescTxt").toString());
        request.setAttribute("ContextAction", request.getParameter("ContextAction").toString());
        request.setAttribute("subjectUID", request.getParameter("subjectUID").toString());
        logger.debug("debug.ejenkin6.prepareEntitiesForReload.end");
    }

    private String getCountyDescByState(String stateCd, String countyCd)
    {
        logger.debug("debug.ejenkin6.getCountyDescByState.begin");
        String countyDesc = "";
        if(stateCd != null)
        {
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<?,?> treemap = null;
            treemap = srtValues.getCountyCodes(stateCd);
            if(countyCd != null && treemap.get(countyCd) != null)
            {
                countyDesc = (String)treemap.get(countyCd);
            }
        }
        logger.debug("debug.ejenkin6.getCountyDescByState.end");
        return countyDesc;
    }

    /**
     * [picks which diagnosis will be displayed to the user]
     * @param param1 [string array containing the diagnosis selected ]
     * @param param2  request object
     * @param param3  Person UID for calling vaccination proxy
     */
    private void configureDiagnosis(String[] arrDiagnosis, HttpServletRequest request, Long personUID, boolean supplimentalExists)
    {
        logger.debug("debug.ejenkin6.configureDiagnosis.begin");
        String showTabFlag = request.getParameter("hiddenShowTabFlag");
        if
        (
            (arrDiagnosis != null)
        &&  ((showTabFlag == null) || (!showTabFlag.trim().equalsIgnoreCase("No")))
        &&  supplimentalExists == true
        )
        {
            for(int i = 0, len = arrDiagnosis.length; i < len; i++)
            {
                String diagnosis = arrDiagnosis[i];
                if(diagnosis == null)
                {
                    diagnosis = "NI";
                }
                if(diagnosis != null)
                {
                    // request.setAttribute("conditionCd", diagnosis);
                    HttpSession session = request.getSession(false);
                    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
                    if(diagnosis.trim().equals("10110"))
                    {
                        request.setAttribute("hepAShow", "true");
                        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
                        if(nbsSecurityObj.getPermission("INTERVENTIONVACCINERECORD", "VIEW", "ALL", "ALL", "Y"))
                        {
                            getVaccinationSummaryRecords(personUID, request);
                        }
                    }
                    else if(request.getAttribute("hepAShow") == null)
                    {
                        request.setAttribute("hepAShow", "false");
                    }
                    if(diagnosis.trim().equals("10100"))
                    {
                        request.setAttribute("hepBShow", "true");
                        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
                        if(nbsSecurityObj.getPermission("INTERVENTIONVACCINERECORD", "VIEW", "ALL", "ALL", "Y"))
                        {
                            getVaccinationSummaryRecords(personUID, request);
                        }
                    }
                    else if(request.getAttribute("hepBShow") == null)
                    {
                        request.setAttribute("hepBShow", "false");
                    }
                    if(diagnosis.trim().equals("10101"))
                    {
                        request.setAttribute("hepCShow", "true");
                        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
                    }
                    else if(request.getAttribute("hepCShow") == null)
                    {
                        request.setAttribute("hepCShow", "false");
                        //HCV hepatitis C viral
                    }
                    if(diagnosis.trim().equals("10106"))
                    {
                        request.setAttribute("hepChronicShow", "true");
                        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
                    }
                    else if(request.getAttribute("hepChronicShow") == null)
                    {
                        request.setAttribute("hepChronicShow", "false");
                    }
                    if(diagnosis.trim().equals("10104"))
                    {
                        request.setAttribute("hepPerinatalShow", "true");
                        request.setAttribute("showConditionSpecificLDF", new Boolean(true));
                        if(nbsSecurityObj.getPermission("INTERVENTIONVACCINERECORD", "VIEW", "ALL", "ALL", "Y"))
                        {
                            getVaccinationSummaryRecords(personUID, request);
                        }
                    }
                    else if(request.getAttribute("hepPerinatalShow") == null)
                    {
                        request.setAttribute("hepPerinatalShow", "false");
                    }
                }
            }
        }
        else
        {
            request.setAttribute("hepAShow", "false");
            request.setAttribute("hepBShow", "false");
            request.setAttribute("hepCShow", "false");
            request.setAttribute("hepChronicShow", "false");
            request.setAttribute("hepPerinatalShow", "false");
            request.setAttribute("showConditionSpecificLDF", new Boolean(false));
        }
        logger.debug("debug.ejenkin6.configureDiagnosis.end");
    }

    private void convertObservationsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        logger.debug("debug.ejenkin6.convertObservationsToRequest.begin");
        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        Collection<Object>  codedColl = null;
//        logger.debug(" inside convertObservataionsToRequest coll size = " + obsColl.size());
        if(obsColl != null)
        {
           Iterator<ObservationVO>  iter = obsColl.iterator();
            while(iter.hasNext())
            {
                ObservationVO obsVO = (ObservationVO)iter.next();
                String obsCode = (obsVO.getTheObservationDT().getCd() == null ? null : obsVO.getTheObservationDT()
                     .getCd().trim());
                if(obsVO.getTheObservationDT().getUid() != null)
                {
//                    logger.debug("obsUid=" + obsVO.getTheObservationDT().getUid().toString());
                }
                if(obsCode != null)
                {
                    //get the coded values
                    codedColl = obsVO.getTheObsValueCodedDTCollection();
                    if(codedColl != null)
                    {
                       Iterator<Object>  codedIter = codedColl.iterator();
                        StringBuffer multiSelect = new StringBuffer();
                        while(codedIter.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)codedIter.next();
                            multiSelect.append(obsValueCodedDT.getCode()).append("|");
//                            logger.debug("Mark after append of |");
                            if(codedColl.size() == 1)
                            {
                                request.setAttribute(obsCode, (obsValueCodedDT.getCode() == null ? "" : obsValueCodedDT.getCode()));
                                request.setAttribute(obsCode + "-desc", (obsValueCodedDT.getCodeSystemDescTxt() == null ? "" : obsValueCodedDT.getCodeSystemDescTxt()));
                                request.setAttribute(obsCode + "-originalTxt", (obsValueCodedDT.getOriginalTxt() == null ? "" : obsValueCodedDT.getOriginalTxt()));
                            }
                        }
                        if(codedColl.size() > 1)
                        {
                           //System.out.println("obsCode   = " + obsCode + "      =  " + multiSelect.toString());
                            //overwrite the existing attribute
                            //request.setAttribute(obsCode + "-originalTxt", multiSelect.toString());
                            request.setAttribute(obsCode, multiSelect.toString());
                        }
                    }
                    //get date
                    Collection<Object>  dateColl = obsVO.getTheObsValueDateDTCollection();
                    if(dateColl != null)
                    {
                       Iterator<Object>  dateIter = dateColl.iterator();
                        if(dateIter.hasNext())
                        {
                            ObsValueDateDT obsDateCodedDT = (ObsValueDateDT)dateIter.next();
                            request.setAttribute(obsCode+"-fromTime", formatDate(obsDateCodedDT.getFromTime()));
                            request.setAttribute(obsCode + "-toTime", (obsDateCodedDT.getToTime() == null ? "" : formatDate(obsDateCodedDT.getToTime())));
                            request.setAttribute(obsCode + "-durationAmt", (obsDateCodedDT.getDurationAmt() == null ? "" : obsDateCodedDT.getDurationAmt()));
                            request.setAttribute(obsCode + "-durationUnit", (obsDateCodedDT.getDurationUnitCd() == null ? "" : obsDateCodedDT.getDurationUnitCd()));
                        }
                    }
                    //get text
                    Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
                    if(textColl != null)
                    {
                       Iterator<Object>  textIter = textColl.iterator();
                        if(textIter.hasNext())
                        {
                            ObsValueTxtDT obsTextCodedDT = (ObsValueTxtDT)textIter.next();
                            request.setAttribute(obsCode + "-txt", (obsTextCodedDT.getValueTxt() == null ? "" : obsTextCodedDT.getValueTxt()));
                            request.setAttribute(obsCode+"-valueTxt", (obsTextCodedDT.getValueTxt()==null? "":obsTextCodedDT.getValueTxt()));
                        }
                    }
                    // get numeric
                    Collection<Object>  numColl = obsVO.getTheObsValueNumericDTCollection();
                    if(numColl != null)
                    {
                       Iterator<Object>  numIter = numColl.iterator();
                        if(numIter.hasNext())
                        {
                            ObsValueNumericDT obsNumCodedDT = (ObsValueNumericDT)numIter.next();
                            request.setAttribute(obsCode + "-numericValue1", (obsNumCodedDT.getNumericValue1() == null ? "" : String.valueOf(obsNumCodedDT.getNumericValue1().intValue())));

                            request.setAttribute(obsCode + "-numericValue2", (obsNumCodedDT.getNumericValue1() == null ? "" : String.valueOf(obsNumCodedDT.getNumericValue2())));
                            request.setAttribute(obsCode + "-numericUnit", (obsNumCodedDT.getNumericUnitCd() == null ? "" : String.valueOf(obsNumCodedDT.getNumericUnitCd())));
                        }
                    }
                }
            }
        }
        logger.debug("debug.ejenkin6.convertObservationsToRequest.end");
    }

    private void convertOrganizationToRequestHep(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        logger.debug("debug.convertOrganizationToRequestHep.begin");
        StringBuffer sb = new StringBuffer();
        
		
		String displayString="";
		//Reporting Organization
		OrganizationVO reportingOrganizationVO = this.getOrganizationVO(
				NEDSSConstants.PHC_REPORTING_SOURCE, investigationProxyVO);

		if (reportingOrganizationVO != null) {

			request.setAttribute("reportingOrgUID", reportingOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(reportingOrganizationVO);
			request.setAttribute("reportingOrgDemographics", displayString);
			request.setAttribute("reportingOrg.organizationUID-values", displayString);
		
		} else {
			request.setAttribute("reportingOrgDemographics", "There is no Reporting Source selected.");
			request.setAttribute("reportingOrg.organizationUID-values", "There is no Reporting Source selected.");
		}	

		// Hospital
		OrganizationVO hospitalOrganizationVO = this.getOrganizationVO(
				NEDSSConstants.HOSPITAL_NAME_ADMITTED, investigationProxyVO);
		
		if (hospitalOrganizationVO != null) {
			request.setAttribute("hospitalOrgUID", hospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(hospitalOrganizationVO);
			request.setAttribute("hospitalDemographics", displayString);
			request.setAttribute("hospitalOrg.organizationUID-values", displayString);
			
		} else {
			request.setAttribute("hospitalDemographics", "There is no Hospital selected.");
			request.setAttribute("hospitalOrg.organizationUID-values", "There is no Hospital selected.");
		}	


        logger.debug("convertOrganizationToRequestHep.end");
    }

    private void convertPersonsToRequestHep(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        logger.debug("debug.ejenkin6.convertPersonsToRequestHep.begin");
        StringBuffer sb = new StringBuffer();
        PersonVO patientPersonVO = this.getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO);
        if(patientPersonVO != null)
        {
            String pFirstName = "";
            String pLastName = "";
            request.setAttribute("patientPersonUid", patientPersonVO.getThePersonDT().getPersonUid());
            PersonNameDT patientPersonNameDT = this.getPersonNameDT(NEDSSConstants.LEGAL_NAME, patientPersonVO);
            request.setAttribute("personUID", String.valueOf(patientPersonVO.getThePersonDT().getPersonUid()));
            request.setAttribute("personLocalID", patientPersonVO.getThePersonDT().getLocalId());
            if(patientPersonNameDT != null)
            {
                String strPatientName = (((patientPersonNameDT.getFirstNm() == null) ? "" : patientPersonNameDT.getFirstNm()) + " " + ((patientPersonNameDT.getLastNm() == null) ? "" : patientPersonNameDT.getLastNm()));
                request.setAttribute("patientName", strPatientName);
                request.setAttribute("patientDateOfBirth", this.formatDate(patientPersonVO.getThePersonDT().getBirthTime()));
                request.setAttribute("patientCurrentSex", patientPersonVO.getThePersonDT().getCurrSexCd());
                pFirstName = patientPersonNameDT.getFirstNm();
                pLastName = patientPersonNameDT.getLastNm();

            }
            else
            {
                request.setAttribute("patientDateOfBirth", "");
            }
        }
        
        String displayString="";
		//Investigator

		PersonVO investigatorPersonVO = getPersonVO(
				NEDSSConstants.PHC_INVESTIGATOR, investigationProxyVO);
		logger
				.debug("convertProxyToRequestObj() before if investigatorPersonVO: "
						+ investigatorPersonVO);

		if (investigatorPersonVO != null) {

			request.setAttribute("investigatorUID", investigatorPersonVO
					.getThePersonDT().getPersonUid());
			displayString = helper.makePRVDisplayString(investigatorPersonVO);
				request.setAttribute("investigatorDemographics", displayString);
				request.setAttribute("investigator.personUid-values", displayString);
				
				
		}else {
			request.setAttribute("investigatorDemographics", "There is no Investigator selected.");
			request.setAttribute("investigator.personUid-values", "There is no Investigator selected.");
		}

		//Reporter
		PersonVO reportingPersonVO = getPersonVO(NEDSSConstants.PHC_REPORTER,
				investigationProxyVO);
		if (reportingPersonVO != null) {

			
			request.setAttribute("reporterPersonUid", reportingPersonVO
					.getThePersonDT().getPersonUid());

			displayString = helper.makePRVDisplayString(reportingPersonVO);
			request.setAttribute("reporterDemographics", displayString);
			request.setAttribute("reporter.personUid-values", displayString);
			

		} else {
			request.setAttribute("reporterDemographics","There is no Reporter selected.");
			request.setAttribute("reporter.personUid-values", "There is no Reporter selected.");
		}	


		//Physician
		PersonVO physicianPersonVO = getPersonVO(NEDSSConstants.PHC_PHYSICIAN,
				investigationProxyVO);
		if (physicianPersonVO != null) {

			request.setAttribute("physicianPersonUid", physicianPersonVO
					.getThePersonDT().getPersonUid());
			
			displayString = helper.makePRVDisplayString(physicianPersonVO);
			request.setAttribute("physicianDemographics",  displayString);
			request.setAttribute("physician.personUid-values",  displayString);

		} else {
			request.setAttribute("physicianDemographics","There is no Physician selected.");
			request.setAttribute("physician.personUid-values","There is no Physician selected.");
	}
        
        logger.debug("convertPersonsToRequestHep.end");
    }

    public static void showConditionSpecificLDF(String conditionCd, HttpServletRequest request)
    {
      if(conditionCd!= null)
         conditionCd = conditionCd.trim().intern();

      if((conditionCd =="10105") ||(conditionCd =="10102") ||(conditionCd =="10103") ||(conditionCd =="10481") )
      {
           request.setAttribute("showConditionSpecificLDF", new Boolean(true));
      }
      }

}
