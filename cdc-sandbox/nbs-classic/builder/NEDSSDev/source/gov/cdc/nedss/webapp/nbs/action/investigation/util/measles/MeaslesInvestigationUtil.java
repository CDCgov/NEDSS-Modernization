package gov.cdc.nedss.webapp.nbs.action.investigation.util.measles;

/**
 * Title:        CoreDemographic
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */

import java.util.*;

import javax.servlet.http.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.
    CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;

public class MeaslesInvestigationUtil
    extends CommonInvestigationUtil {

   static final LogUtils logger = new LogUtils(MeaslesInvestigationUtil.class.
                                               getName());
   static final  QuickEntryEventHelper helper = new QuickEntryEventHelper();
   public MeaslesInvestigationUtil() {
	   
   }

   public void setMeaslesRequestForView(InvestigationProxyVO
                                        investigationProxyVO,
                                        HttpServletRequest request) {

      super.convertPublicHealthCaseToRequest(investigationProxyVO, request);
      this.convestPersonsToRequest(investigationProxyVO, request);
      this.convestOrganizationToRequest(investigationProxyVO, request);
      this.convertObservationsToRequest(investigationProxyVO, request);
      this.convertNotificationSummaryToRequest(investigationProxyVO, request);
      this.convertObservationSummaryToRequest(investigationProxyVO, request);
      this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
      this.convertTreatmentSummaryToRequest(investigationProxyVO, request);

      getVaccinationSummaryRecords(getPersonVO(NEDSSConstants.PHC_PATIENT,
          investigationProxyVO).getThePersonDT().getPersonUid(), request);
      this.convertDocumentSummaryToRequest(investigationProxyVO, request);

   }

   private void convestPersonsToRequest(InvestigationProxyVO
                                        investigationProxyVO,
                                        HttpServletRequest request) {
   	
   	
      PersonVO patientPersonVO = this.getPersonVO(NEDSSConstants.PHC_PATIENT,
                                                  investigationProxyVO);
      if (patientPersonVO != null) {

         String pFirstName = "";
         String pLastName = "";

         request.setAttribute("patientPersonUid",
                              patientPersonVO.getThePersonDT().getPersonUid());
         PersonNameDT patientPersonNameDT = this.getPersonNameDT(NEDSSConstants.
             LEGAL_NAME, patientPersonVO);
         request.setAttribute("personUID",
                              String.valueOf(patientPersonVO.getThePersonDT().
                                             getPersonUid()));
         request.setAttribute("personLocalID",
                              patientPersonVO.getThePersonDT().getLocalId());
         if (patientPersonNameDT != null) {
            String strPatientName = ( ( (patientPersonNameDT.getFirstNm() == null) ?
                                       "" : patientPersonNameDT.getFirstNm()) +
                                     " " +
                                     ( (patientPersonNameDT.getLastNm() == null) ? "" :
                                      patientPersonNameDT.getLastNm()));
            request.setAttribute("patientName", strPatientName);
            request.setAttribute("patientDateOfBirth",
                                 this.formatDate(patientPersonVO.getThePersonDT().
                                                 getBirthTime()) + " ");
            request.setAttribute("patientCurrentSex",
                                 patientPersonVO.getThePersonDT().getCurrSexCd() +
                                 "  ");
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

   private void convestOrganizationToRequest(InvestigationProxyVO
                                             investigationProxyVO,
                                             HttpServletRequest request) {
	
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

   private void convestPublicHealthCaseToRequest(InvestigationProxyVO
                                                 investigationProxyVO,
                                                 HttpServletRequest request) {
      String conditionCd = null;
      String programAreaCd = "";
      if (investigationProxyVO != null) {
         PublicHealthCaseDT publicHealthCaseDT = investigationProxyVO.
                                                 getPublicHealthCaseVO().
                                                 getThePublicHealthCaseDT();
         if (publicHealthCaseDT != null) {
            request.setAttribute("publicHealthCaseUid",
                                 publicHealthCaseDT.getPublicHealthCaseUid());
            request.setAttribute("publicHealthCaseLocalUid",
                                 publicHealthCaseDT.getLocalId());
            request.setAttribute("investigationStatus",
                                 publicHealthCaseDT.getInvestigationStatusCd());
            request.setAttribute("jurisdiction",
                                 publicHealthCaseDT.getJurisdictionCd());
            request.setAttribute("programAreaCd",
                                 publicHealthCaseDT.getProgAreaCd());
            request.setAttribute("dateOfReport",
                                 this.formatDate(publicHealthCaseDT.
                                                 getRptFormCmpltTime()));
            request.setAttribute("reportingSourceType",
                                 publicHealthCaseDT.getRptSourceCd());
            request.setAttribute("rptSourceDescTxt",
                                 publicHealthCaseDT.getRptSourceCdDescTxt());
            request.setAttribute("reportingCountyDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getRptToCountyTime()));
            request.setAttribute("reportingStateDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getRptToStateTime()));
            request.setAttribute("caseConfidentiality",
                                 publicHealthCaseDT.getConfidentialityCd());
            request.setAttribute("diagnosisDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getDiagnosisTime()));
            request.setAttribute("illnessOnsetDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getEffectiveFromTime()));
            request.setAttribute("illnessEndDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getEffectiveToTime()));
            request.setAttribute("illnessDuration",
                                 publicHealthCaseDT.getEffectiveDurationAmt());
            request.setAttribute("illnessDurationUnitCd",
                                 publicHealthCaseDT.getEffectiveDurationUnitCd());
            // request.setAttribute("didThePatientDie", publicHealthCaseDT.getOutcomeCd());
            request.setAttribute("investigationStartDate",
                                 this.formatDate(publicHealthCaseDT.
                                                 getActivityFromTime()));
            request.setAttribute("partOfOutbreak",
                                 publicHealthCaseDT.getOutbreakInd()); //PHC134
            request.setAttribute("outbreakName",
                                 publicHealthCaseDT.getOutbreakName()); //PHC132
            request.setAttribute("diseaseImported",
                                 publicHealthCaseDT.getDiseaseImportedCd()); //PHC118/CORE200
            request.setAttribute("transmissionMode",
                                 publicHealthCaseDT.getTransmissionModeCd()); //PHC147
            request.setAttribute("otherTransmissionModeTxt",
                                 publicHealthCaseDT.getTransmissionModeDescTxt());
            request.setAttribute("detectionMethod",
                                 publicHealthCaseDT.getDetectionMethodCd());
            request.setAttribute("otherDetectionMethodDescTxt",
                                 publicHealthCaseDT.getDetectionMethodDescTxt());
            request.setAttribute("caseStatus",
                                 publicHealthCaseDT.getCaseClassCd());
            request.setAttribute("numberOfCases",
                                 publicHealthCaseDT.getGroupCaseCnt());
            request.setAttribute("MMWRWeek", publicHealthCaseDT.getMmwrWeek());
            request.setAttribute("MMWRYear", publicHealthCaseDT.getMmwrYear());
            request.setAttribute("generalComments", publicHealthCaseDT.getTxt());
            request.setAttribute("conditionCd", publicHealthCaseDT.getCd());
            request.setAttribute("conditionCdDescTxt",
                                 publicHealthCaseDT.getCdDescTxt());
            request.setAttribute("sharedIndicator",
                                 publicHealthCaseDT.getSharedInd());
            conditionCd = publicHealthCaseDT.getCd();
            programAreaCd = publicHealthCaseDT.getProgAreaCd();

            if(investigationProxyVO.getAssociatedNotificationsInd())
              request.setAttribute("NotificationExists","true");
            else
              request.setAttribute("NotificationExists","false");

            logger.debug("AssociatedNotificationsInd is " + investigationProxyVO.getAssociatedNotificationsInd());

         }

         Collection<Object>  participationDTCollection  = investigationProxyVO.
                                                getPublicHealthCaseVO().
                                                getTheParticipationDTCollection();
         if (participationDTCollection  != null) {
           Iterator<Object>  anIterator = null;
            for (anIterator = participationDTCollection.iterator();
                              anIterator.hasNext(); ) {
               ParticipationDT participationDT = (ParticipationDT) anIterator.
                                                 next();
               if (participationDT.getTypeCd() != null &&
                   participationDT.
                   getTypeCd().equals(NEDSSConstants.PHC_INVESTIGATOR)) {
                  request.setAttribute("dateAssignedToInvestigation",
                                       this.
                                       formatDate(participationDT.getFromTime()));
                  break;
               }
            }
         }

         Collection<Object>  actIdDTCollection  = investigationProxyVO.
                                        getPublicHealthCaseVO().
                                        getTheActIdDTCollection();
         if (actIdDTCollection  != null) {
           Iterator<Object>  anIterator = null;
            for (anIterator = actIdDTCollection.iterator(); anIterator.hasNext(); ) {
               ActIdDT actIdDT = (ActIdDT) anIterator.next();
               request.setAttribute("stateCaseID", actIdDT.getRootExtensionTxt());
               break;

            }
         }
         ConfirmationMethodDT confirmationMethodDT = null;
         Collection<Object>  confirmationMethodDTCollection  = investigationProxyVO.
             getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
         request.setAttribute("confirmationMethodList",
                              this.
                              getConfirmationMethodList(investigationProxyVO));
         if (confirmationMethodDTCollection  != null) {
           Iterator<Object>  anIterator = null;
            for (anIterator = confirmationMethodDTCollection.iterator();
                              anIterator.hasNext(); ) {
               confirmationMethodDT = (ConfirmationMethodDT) anIterator.next();
               if (confirmationMethodDT != null &&
                   confirmationMethodDT.getConfirmationMethodTime() != null) {
                  request.setAttribute("confirmationDate",
                                       this.formatDate(confirmationMethodDT.
                      getConfirmationMethodTime()));
                  break;
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
                  request.setAttribute(obsCode, (obsValueCodedDT.getCode()==null? "":obsValueCodedDT.getCode()));
                  request.setAttribute(obsCode+"-desc", (obsValueCodedDT.getCodeSystemDescTxt()==null? "":obsValueCodedDT.getCodeSystemDescTxt()));
                  if ( obsCode.equalsIgnoreCase("INV154")) {
                  String code = obsValueCodedDT.getCode();
                  request.setAttribute("importedState", code);
                  request.setAttribute("importedCountyInState", getCountiesByState(code));
                  continue;
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
                  logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getFromTime());
                  logger.debug(obsCode+"ToTime:"+obsDateCodedDT.getToTime());
                  logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getDurationAmt());
                  logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getDurationUnitCd());

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
                  logger.debug("Value text for " +obsCode+ " is :" +obsTextCodedDT.getValueTxt());
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
                  request.setAttribute(obsCode+"-numericValue1", (obsNumCodedDT.getNumericValue1()==null? "":String.valueOf(obsNumCodedDT.getNumericValue1().toString())));
                  logger.debug("Numeric text for " +obsCode+ " is :" +obsNumCodedDT.getNumericValue1());
                  request.setAttribute(obsCode+"-numericUnitCd", (obsNumCodedDT.getNumericUnitCd()==null?"":String.valueOf(obsNumCodedDT.getNumericUnitCd())));
                }
              }


            }
          }
        }

   }


   private String getCountiesByState(String stateCd) {
      StringBuffer parsedCodes = new StringBuffer("");
      if (stateCd != null) {
         //SRTValues srtValues = new SRTValues();
         CachedDropDownValues srtValues = new CachedDropDownValues();
         TreeMap<?,?> treemap = null;

         treemap = srtValues.getCountyCodes(stateCd);

         if (treemap != null) {
            Set<?> set = treemap.keySet();
           Iterator<?>  itr = set.iterator();
            while (itr.hasNext()) {
               String key = (String) itr.next();
               String value = (String) treemap.get(key);
               parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).
                   append(value.trim()).append(NEDSSConstants.SRT_LINE);
            }
         }
      }
      return parsedCodes.toString();
   }

   public void setObservationConstantsForCreate(InvestigationProxyVO
                                                investigationProxyVO) {
      //set some values to observations
      Collection<ObservationVO>  observationVOCollection  = investigationProxyVO.
                                           getTheObservationVOCollection();
      logger.debug("convertProxyToRequestObj() before observationVOCollection  ");
      if (observationVOCollection  != null) {

        Iterator<ObservationVO>  anIterator1 = null;
        Iterator<Object>  anIterator2 = null;
        Iterator<Object>  anIterator3 = null;
         logger.debug(
             "convertProxyToRequestObj() observationVOCollection  size: " +
             observationVOCollection.size());
         for (anIterator1 = observationVOCollection.iterator();
                            anIterator1.hasNext(); ) {
            ObservationVO observationVO = (ObservationVO) anIterator1.next();
            String obsCode = observationVO.getTheObservationDT().getCd();
            if (obsCode != null && obsCode.equals("MEA004")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA005")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA008")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA010")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA012")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA009")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA013")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA014")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA015")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA016")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA017")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA018")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA011")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA021")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }

            if (obsCode != null && obsCode.equals("MEA027")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA028")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");

               continue;
            }
            if (obsCode != null && obsCode.equals("MEA030")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");

               continue;
            }
            if (obsCode != null && obsCode.equals("MEA031")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");

               continue;
            }
            if (obsCode != null && obsCode.equals("MEA032")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA033")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA036")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA038")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("LAB");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA072")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");

               continue;
            }
            if (obsCode != null && obsCode.equals("MEA039")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("VAC");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA042")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("VAC");
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA043")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("VAC");
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA044")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("VAC");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA045")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("VAC");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA057")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA058")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA060")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA067")) {
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("INV153")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");

               continue;
            }
            if (obsCode != null && obsCode.equals("INV154")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemCd("FIPS");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("INV155")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               continue;
            }
            if (obsCode != null && obsCode.equals("INV155")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               continue;
            }
            if (obsCode != null && obsCode.equals("INV156")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA059")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("EPI");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("INV128")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueDateDTCollection  = observationVO.
                   getTheObsValueDateDTCollection();
               if (obsValueDateDTCollection  != null) {
                  anIterator3 = obsValueDateDTCollection.iterator();
                  if (anIterator3.hasNext()) {
                     ObsValueDateDT obsValueDateDT = ( (ObsValueDateDT)
                         anIterator3.next());
                     obsValueDateDT.setDurationUnitCd("D");
                  }
               }
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                  }
               }
               continue;
            }
            if (obsCode != null && obsCode.equals("MEA001")) {

               observationVO.getTheObservationDT().setCdSystemCd("NBS");
               observationVO.getTheObservationDT().setObsDomainCd("CLN");
               Collection<Object>  obsValueCodedDTCollection  = observationVO.
                   getTheObsValueCodedDTCollection();
               if (obsValueCodedDTCollection  != null) {
                  anIterator2 = obsValueCodedDTCollection.iterator();
                  if (anIterator2.hasNext()) {
                     ObsValueCodedDT obsValueCodedDT = ( (ObsValueCodedDT)
                         anIterator2.next());
                     obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                     obsValueCodedDT.setCodeSystemCd("NBS");
                     obsValueCodedDT.setCodeVersion("1");
                  }
               }
            }
         }
      }
   } //setObsevationCodesForCreate

}