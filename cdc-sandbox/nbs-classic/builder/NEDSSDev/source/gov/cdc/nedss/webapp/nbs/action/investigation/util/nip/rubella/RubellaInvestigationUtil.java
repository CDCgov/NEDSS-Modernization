

package gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella;



import java.util.*;

import javax.servlet.http.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;


import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * Title:         RubellaInvestigationUtil
 * Description:   this class retrieves data from EJB and puts them into request
 *                object for use in the xml file
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        Nedss Team
 * @version       1.0
 */


public class RubellaInvestigationUtil extends CommonInvestigationUtil
{

   static final LogUtils logger = new LogUtils(RubellaInvestigationUtil.class.getName());
   static final  QuickEntryEventHelper helper = new QuickEntryEventHelper();
    /**
     * This is constructor
     *
     */
   public RubellaInvestigationUtil()
   {
   }


        /**
      * Get values from request object and put it to appropriate object
      * @param InvestigationProxyVO the investigationProxyVO
      * @param HttpServletRequest the request
      */
   public void setGenericRequestForView(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {

       super.convertPublicHealthCaseToRequest(investigationProxyVO, request);
       this.convestPersonsToRequest(investigationProxyVO, request);
       this.convestOrganizationToRequest(investigationProxyVO, request);
       this.convertObservationsToRequest(investigationProxyVO, request);
       this.convertNotificationSummaryToRequest(investigationProxyVO, request);
       this.convertObservationSummaryToRequest(investigationProxyVO, request);
       this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
       this.convertTreatmentSummaryToRequest(investigationProxyVO, request);
       this.convertDocumentSummaryToRequest(investigationProxyVO, request);

       getVaccinationSummaryRecords(getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getPersonUid(), request);

   }


   /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
   public void convertObservationsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        if (obsColl != null)
        {
         Iterator<ObservationVO>  iter = obsColl.iterator();
          while (iter.hasNext())	
          {
            ObservationVO obsVO = (ObservationVO) iter.next();

            String obsCode = obsVO.getTheObservationDT().getCd();
            if(obsCode != null)
            {
              //get the coded values
              Collection<Object>  codedColl = obsVO.getTheObsValueCodedDTCollection();
              if(codedColl!=null)
              {
               Iterator<Object>  codedIter = codedColl.iterator();
                if(codedIter.hasNext())
                {
                  ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)codedIter.next());
                  request.setAttribute(obsCode, obsValueCodedDT.getCode());
                  request.setAttribute(obsCode+"-desc", (obsValueCodedDT.getCodeSystemDescTxt()==null? "":obsValueCodedDT.getCodeSystemDescTxt()));
                 if(obsCode.equals("INV154"))
                 {
                    String code =  obsValueCodedDT.getCode();
                    request.setAttribute("importedCountyInState", this.getCountiesByState(code));
                 }
                }
              }
              //get date
              Collection<Object>  dateColl = obsVO.getTheObsValueDateDTCollection();
              if(dateColl!=null)
              {
               Iterator<Object>  dateIter = dateColl.iterator();
                if(dateIter.hasNext())
                {
                  ObsValueDateDT obsDateCodedDT = ((ObsValueDateDT)dateIter.next());
                  request.setAttribute(obsCode+"-fromTime", (obsDateCodedDT.getFromTime()==null? "":formatDate(obsDateCodedDT.getFromTime())));
                  request.setAttribute(obsCode+"-toTime", (obsDateCodedDT.getToTime()==null? "":formatDate(obsDateCodedDT.getToTime())));
                  request.setAttribute(obsCode+"-durationAmt", (obsDateCodedDT.getDurationAmt()==null? "":obsDateCodedDT.getDurationAmt()));
                  request.setAttribute(obsCode+"-durationUnit", (obsDateCodedDT.getDurationUnitCd()==null? "":obsDateCodedDT.getDurationUnitCd()));
                }
              }
              //get text
              Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
              if(textColl!=null)
              {
               Iterator<Object>  textIter = textColl.iterator();
                if(textIter.hasNext())
                {
                  ObsValueTxtDT obsTextCodedDT = ((ObsValueTxtDT)textIter.next());
                  request.setAttribute(obsCode+"-valueTxt", (obsTextCodedDT.getValueTxt()==null? "":obsTextCodedDT.getValueTxt()));
                }
              }
              // get numeric
              Collection<Object>  numColl = obsVO.getTheObsValueNumericDTCollection();
              if(numColl!=null)
              {
               Iterator<Object>  numIter = numColl.iterator();
                if(numIter.hasNext())
                {
                  ObsValueNumericDT obsNumCodedDT = ((ObsValueNumericDT)numIter.next());
                  request.setAttribute(obsCode+"-numericValue1", (obsNumCodedDT.getNumericValue1()==null? "":obsNumCodedDT.getNumericValue1().toString()));
                  ////##!! System.out.println("obsNumCodedDT.getNumericValue1()  :"+obsNumCodedDT.getNumericValue1());
                  request.setAttribute(obsCode+"-numericUnitCd", (obsNumCodedDT.getNumericUnitCd()==null?"":String.valueOf(obsNumCodedDT.getNumericUnitCd())));
                }
              }
            }
          }
        }

   }

   /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
   private void convestPersonsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
   	PersonVO patientPersonVO = this.getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO);
            if(patientPersonVO != null)
            {

                String pFirstName = "";
                String pLastName = "";

                request.setAttribute("patientPersonUid", patientPersonVO.getThePersonDT().getPersonUid());
                PersonNameDT patientPersonNameDT = this.getPersonNameDT(NEDSSConstants.LEGAL_NAME, patientPersonVO);
                request.setAttribute("personUID",String.valueOf(patientPersonVO.getThePersonDT().getPersonUid()));
                request.setAttribute("personLocalID",patientPersonVO.getThePersonDT().getLocalId());
                if (patientPersonNameDT != null)
                {
                    String strPatientName = ((patientPersonNameDT.getLastNm() == null) ? "" : patientPersonNameDT.getLastNm()) + " " + ((patientPersonNameDT.getFirstNm() == null) ?  "" : patientPersonNameDT.getFirstNm());
                    request.setAttribute("patientName", strPatientName);
                    request.setAttribute("patientDateOfBirth", this.formatDate(patientPersonVO.getThePersonDT().getBirthTime()));
                    request.setAttribute("patientCurrentSex", patientPersonVO.getThePersonDT().getCurrSexCd());
                    pFirstName = patientPersonNameDT.getFirstNm();
                    pLastName = patientPersonNameDT.getLastNm();
                }

            }
    		//Investigator

    		PersonVO investigatorPersonVO = getPersonVO(
    				NEDSSConstants.PHC_INVESTIGATOR, investigationProxyVO);
    		logger
    				.debug("convertProxyToRequestObj() before if investigatorPersonVO: "
    						+ investigatorPersonVO);

    		if (investigatorPersonVO != null) {

    			request.setAttribute("investigatorUID", investigatorPersonVO
    					.getThePersonDT().getPersonUid());

    				request.setAttribute("investigatorDemographics", helper.makePRVDisplayString(investigatorPersonVO));
    		}else {
    			request.setAttribute("investigatorDemographics",
    			"There is no Investigator selected.");
    		}

    		//Reporter
    		PersonVO reportingPersonVO = getPersonVO(NEDSSConstants.PHC_REPORTER,
    				investigationProxyVO);
    		if (reportingPersonVO != null) {

    			request.setAttribute("reporterPersonUid", reportingPersonVO
    					.getThePersonDT().getPersonUid());

    				request.setAttribute("reporterDemographics", helper.makePRVDisplayString(reportingPersonVO));

    		} else {
    			request.setAttribute("reporterDemographics",
    			"There is no Reporter selected.");
    		}	


    		//Physician
    		PersonVO physicianPersonVO = getPersonVO(NEDSSConstants.PHC_PHYSICIAN,
    				investigationProxyVO);
    		if (physicianPersonVO != null) {

    			request.setAttribute("physicianPersonUid", physicianPersonVO
    					.getThePersonDT().getPersonUid());

    				request.setAttribute("physicianDemographics",  helper.makePRVDisplayString(physicianPersonVO));

    		} else {
    			request.setAttribute("physicianDemographics",
    			"There is no Physician selected.");
    	}
   }


      /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
   private void convestOrganizationToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
	//Reporting Organization
	OrganizationVO reportingOrganizationVO = this.getOrganizationVO(
			NEDSSConstants.PHC_REPORTING_SOURCE, investigationProxyVO);

	if (reportingOrganizationVO != null) {

		request.setAttribute("reportingOrgUID", reportingOrganizationVO
				.getTheOrganizationDT().getOrganizationUid());


			request.setAttribute("reportingOrgDemographics", helper.makeORGDisplayString(reportingOrganizationVO));
	
	} else {
		request.setAttribute("reportingOrgDemographics",
		"There is no Reporting Source selected.");
	}	

	// Hospital
	OrganizationVO hospitalOrganizationVO = this.getOrganizationVO(
			NEDSSConstants.HOSPITAL_NAME_ADMITTED, investigationProxyVO);

	if (hospitalOrganizationVO != null) {
		request.setAttribute("hospitalOrgUID", hospitalOrganizationVO
				.getTheOrganizationDT().getOrganizationUid());

		request.setAttribute("hospitalDemographics", helper.makeORGDisplayString(hospitalOrganizationVO));
	} else {
		request.setAttribute("hospitalDemographics",
		"There is no Hospital selected.");
	}	

   	
   }

        /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
   private void  convestPublicHealthCaseToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
        String conditionCd = null;
        String programAreaCd = "";
        if(investigationProxyVO != null)
        {
            PublicHealthCaseDT publicHealthCaseDT = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
            if(publicHealthCaseDT != null)
            {
                request.setAttribute("publicHealthCaseUid", publicHealthCaseDT.getPublicHealthCaseUid());
                request.setAttribute("publicHealthCaseLocalUid", publicHealthCaseDT.getLocalId());
                request.setAttribute("investigationStatus", publicHealthCaseDT.getInvestigationStatusCd());
                request.setAttribute("jurisdiction", publicHealthCaseDT.getJurisdictionCd());
                request.setAttribute("programAreaCd", publicHealthCaseDT.getProgAreaCd());
                request.setAttribute("dateOfReport", this.formatDate(publicHealthCaseDT.getRptFormCmpltTime()));
                request.setAttribute("reportingSourceType", publicHealthCaseDT.getRptSourceCd());
                request.setAttribute("rptSourceDescTxt", publicHealthCaseDT.getRptSourceCdDescTxt());
                request.setAttribute("reportingCountyDate", this.formatDate(publicHealthCaseDT.getRptToCountyTime()));
                request.setAttribute("reportingStateDate", this.formatDate(publicHealthCaseDT.getRptToStateTime()));
                request.setAttribute("caseConfidentiality", publicHealthCaseDT.getConfidentialityCd());
                request.setAttribute("diagnosisDate",  this.formatDate(publicHealthCaseDT.getDiagnosisTime()));
                request.setAttribute("illnessOnsetDate", this.formatDate(publicHealthCaseDT.getEffectiveFromTime()));
                request.setAttribute("illnessEndDate", this.formatDate(publicHealthCaseDT.getEffectiveToTime()));
                request.setAttribute("illnessDuration", publicHealthCaseDT.getEffectiveDurationAmt());
                request.setAttribute("illnessDurationUnitCd", publicHealthCaseDT.getEffectiveDurationUnitCd());
                request.setAttribute("didThePatientDie", publicHealthCaseDT.getOutcomeCd());
                request.setAttribute("investigationStartDate", this.formatDate(publicHealthCaseDT.getActivityFromTime()));
                request.setAttribute("partOfOutbreak", publicHealthCaseDT.getOutbreakInd());//PHC134
                request.setAttribute("outbreakName", publicHealthCaseDT.getOutbreakName());//PHC132
                request.setAttribute("diseaseImported", publicHealthCaseDT.getDiseaseImportedCd());//PHC118/CORE200
                request.setAttribute("transmissionMode", publicHealthCaseDT.getTransmissionModeCd());//PHC147
                request.setAttribute("otherTransmissionModeTxt", publicHealthCaseDT.getTransmissionModeDescTxt());
                request.setAttribute("detectionMethod", publicHealthCaseDT.getDetectionMethodCd());
                request.setAttribute("otherDetectionMethodDescTxt", publicHealthCaseDT.getDetectionMethodDescTxt());
                request.setAttribute("caseStatus", publicHealthCaseDT.getCaseClassCd());
                request.setAttribute("numberOfCases", publicHealthCaseDT.getGroupCaseCnt());
                request.setAttribute("MMWRWeek", publicHealthCaseDT.getMmwrWeek());
                request.setAttribute("MMWRYear", publicHealthCaseDT.getMmwrYear());
                request.setAttribute("generalComments", publicHealthCaseDT.getTxt());
                request.setAttribute("conditionCd", publicHealthCaseDT.getCd());
                request.setAttribute("conditionCdDescTxt", publicHealthCaseDT.getCdDescTxt());
                request.setAttribute("sharedIndicator", publicHealthCaseDT.getSharedInd());
                conditionCd = publicHealthCaseDT.getCd();
                programAreaCd = publicHealthCaseDT.getProgAreaCd();

                if(investigationProxyVO.getAssociatedNotificationsInd())
                  request.setAttribute("NotificationExists","true");
                else
                  request.setAttribute("NotificationExists","false");

                logger.debug("AssociatedNotificationsInd is " + investigationProxyVO.getAssociatedNotificationsInd());

            }

            Collection<Object>  actIdDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection();
            if(actIdDTCollection  != null)
            {
               Iterator<Object>  anIterator = null;
                for(anIterator = actIdDTCollection.iterator(); anIterator.hasNext();)
                {
                    ActIdDT actIdDT = (ActIdDT)anIterator.next();
                    request.setAttribute("stateCaseID", actIdDT.getRootExtensionTxt());
                    break;

                }
            }

            Collection<Object>  participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
            if( participationDTCollection  != null)
            {
               Iterator<Object>  anIterator = null;
                for(anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
                {
                    ParticipationDT participationDT = (ParticipationDT)anIterator.next();
                    if(participationDT.getTypeCd() != null && participationDT.getTypeCd().equals(NEDSSConstants.PHC_INVESTIGATOR))
                    {
                        request.setAttribute("dateAssignedToInvestigation", this.formatDate(participationDT.getFromTime()));
                        break;
                    }
                }
            }


            ConfirmationMethodDT confirmationMethodDT = null;
            Collection<Object>  confirmationMethodDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
            request.setAttribute("confirmationMethodList", this.getConfirmationMethodList(investigationProxyVO));
            if(confirmationMethodDTCollection  != null)
            {
               Iterator<Object>  anIterator = null;
                for(anIterator = confirmationMethodDTCollection.iterator(); anIterator.hasNext();)
                {
                    confirmationMethodDT = (ConfirmationMethodDT)anIterator.next();
                    if(confirmationMethodDT != null && confirmationMethodDT.getConfirmationMethodTime() != null)
                    {
                        request.setAttribute("confirmationDate", this.formatDate(confirmationMethodDT.getConfirmationMethodTime()));
                        break;
                    }
                }
            }
        }

   }

        /**
      * Access to get Counties By State code from database
      * @param String the stateCd
      * @return String the value of the state code
      */
    private String getCountiesByState(String stateCd) {
      StringBuffer parsedCodes = new StringBuffer("");
      if (stateCd!=null){
	  //SRTValues srtValues = new SRTValues();
	  CachedDropDownValues srtValues = new CachedDropDownValues();
	  TreeMap<?,?> treemap = null;

	  treemap = srtValues.getCountyCodes(stateCd);

	  if (treemap != null){
	      Set<?> set = treemap.keySet();
	     Iterator<?>  itr = set.iterator();
	      while (itr.hasNext()){
		  String key = (String)itr.next();
		  String value = (String)treemap.get(key);
                  ////##!! System.out.println(" \n key = " + key + "  vakue =" + value);
		      parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim()).append(NEDSSConstants.SRT_LINE);
	       }
	   }
      }
      return parsedCodes.toString();
    }

}