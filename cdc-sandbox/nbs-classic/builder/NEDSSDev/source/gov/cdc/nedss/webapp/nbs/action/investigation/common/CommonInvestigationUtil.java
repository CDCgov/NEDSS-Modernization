package gov.cdc.nedss.webapp.nbs.action.investigation.common;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Computer Science Corporation
 * </p>
 * 
 * @author Shailender Rachamalla
 *  
 */

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ObservationUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.notification.util.NotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ObservationSummaryUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonInvestigationUtil extends CommonAction {

	static final LogUtils logger = new LogUtils(CommonInvestigationUtil.class
			.getName());
	static final QuickEntryEventHelper helper =new QuickEntryEventHelper();

	
	public CommonInvestigationUtil() {
		
	}

	protected void convertNotificationSummaryToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Collection<Object>  notificationSummaryVOCollection  = investigationProxyVO
				.getTheNotificationSummaryVOCollection();
		if (notificationSummaryVOCollection  == null) {
			logger.debug("notificationSummaryVOCollection  arraylist is null");
		} else {
			logger.debug("notificationSummaryVOCollection  size is "
					+ notificationSummaryVOCollection.size());
			
			NotificationUtil util = new NotificationUtil();
			String notifDisplayHTML = util.buildNotificationList(notificationSummaryVOCollection);
			request.setAttribute("notificationSummaryListForView", notifDisplayHTML);			
			session.setAttribute("notificationSummaryList", notificationSummaryVOCollection);
		}
	}

	protected void convertPersonsToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {

		PersonVO patientPersonVO = getPersonVO(NEDSSConstants.PHC_PATIENT,
				investigationProxyVO);
		if (patientPersonVO != null) {

			String pFirstName = "";
			String pLastName = "";

			request.setAttribute("patientPersonUid", patientPersonVO
					.getThePersonDT().getPersonUid());
			PersonNameDT patientPersonNameDT = getPersonNameDT(
					NEDSSConstants.LEGAL_NAME, patientPersonVO);
			request.setAttribute("personUID", String.valueOf(patientPersonVO
					.getThePersonDT().getPersonUid()));
			request.setAttribute("personLocalID", patientPersonVO
					.getThePersonDT().getLocalId());
			if (patientPersonNameDT != null) {
				String strPatientName = (((patientPersonNameDT.getFirstNm() == null) ? ""
						: patientPersonNameDT.getFirstNm())
						+ " " + ((patientPersonNameDT.getLastNm() == null) ? ""
						: patientPersonNameDT.getLastNm()));
				request.setAttribute("patientName", strPatientName);
				request.setAttribute("patientDateOfBirth",
						formatDate(patientPersonVO.getThePersonDT()
								.getBirthTime()));
				request.setAttribute("patientCurrentSex", patientPersonVO
						.getThePersonDT().getCurrSexCd());
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

	public  void convertPublicHealthCaseToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		String conditionCd = null;
		String programAreaCd = "";
		boolean notificationExists = false;
		boolean noReqNotificationCheck = false;
		HttpSession session = request.getSession();
		String sCurrTask = NBSContext.getCurrentTask(session);
		String contextAction = request.getParameter("ContextAction");
	    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile");
		//TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS114", contextAction);
		 //NBSContext.lookInsideTreeMap(tm);
		 String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
	    // String viewFileHref = "/nbs/" + sCurrTask + ".do?ContextAction=" + contextAction;
	    // String viewHref = "/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID");
	     String contactUrl="/nbs/"+sCurrentTask+".do?ContextAction=PatientSearch";
	     //String populateContactRecord = "/nbs/"+sCurrentTask+".do?ContextAction=ContactTracing";
	     String managectAssoUrl="/nbs/"+sCurrentTask+".do?ContextAction=ManageCtAssociation";
	     request.setAttribute("contactUrl", contactUrl);
	     request.setAttribute("managectAssoUrl", managectAssoUrl);
		if (investigationProxyVO != null) {
			try {
				PublicHealthCaseDT publicHealthCaseDT = investigationProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT();
				if (publicHealthCaseDT != null) {

					request.setAttribute("createdDate", formatDate(publicHealthCaseDT.getAddTime()));
					request.setAttribute("createdBy", publicHealthCaseDT.getAddUserName());

					request.setAttribute("updatedDate", formatDate(publicHealthCaseDT.getLastChgTime()));
					request.setAttribute("updatedBy", publicHealthCaseDT.getLastChgUserName());

					request.setAttribute("publicHealthCaseUid", publicHealthCaseDT
							.getPublicHealthCaseUid());
					request.setAttribute("publicHealthCaseLocalUid",
							publicHealthCaseDT.getLocalId());
					request.setAttribute("investigationStatus", publicHealthCaseDT
							.getInvestigationStatusCd());
					request.setAttribute("jurisdiction", publicHealthCaseDT
							.getJurisdictionCd());
					request.setAttribute("programAreaCd", publicHealthCaseDT
							.getProgAreaCd());
					request.setAttribute("dateOfReport", this
							.formatDate(publicHealthCaseDT.getRptFormCmpltTime()));

					//String sCurrentTask = NBSContext.getCurrentTask(request
					//.getSession());
					request.setAttribute("CurrentTask", sCurrentTask);
					request.setAttribute("currentTask", sCurrentTask);  

					request.setAttribute("reportingSourceType", publicHealthCaseDT
							.getRptSourceCd());
					request.setAttribute("rptSourceDescTxt", publicHealthCaseDT
							.getRptSourceCdDescTxt());
					request.setAttribute("reportingCountyDate", this
							.formatDate(publicHealthCaseDT.getRptToCountyTime()));
					request.setAttribute("reportingStateDate", this
							.formatDate(publicHealthCaseDT.getRptToStateTime()));
					request.setAttribute("caseConfidentiality", publicHealthCaseDT
							.getConfidentialityCd());
					request.setAttribute("diagnosisDate", this
							.formatDate(publicHealthCaseDT.getDiagnosisTime()));
					request.setAttribute("illnessOnsetDate", this
							.formatDate(publicHealthCaseDT.getEffectiveFromTime()));
					request.setAttribute("illnessEndDate", this
							.formatDate(publicHealthCaseDT.getEffectiveToTime()));
					request.setAttribute("illnessDuration", publicHealthCaseDT
							.getEffectiveDurationAmt());
					request.setAttribute("illnessDurationUnitCd",
							publicHealthCaseDT.getEffectiveDurationUnitCd());
					request.setAttribute("didThePatientDie", publicHealthCaseDT
							.getOutcomeCd());
					request.setAttribute("investigationStartDate", this
							.formatDate(publicHealthCaseDT.getActivityFromTime()));
					request.setAttribute("partOfOutbreak", publicHealthCaseDT
							.getOutbreakInd());
					//PHC134
					request.setAttribute("outbreakName", publicHealthCaseDT
							.getOutbreakName());
					//PHC132
					request.setAttribute("diseaseImported", publicHealthCaseDT
							.getDiseaseImportedCd());
					//PHC118/CORE200
					request.setAttribute("transmissionMode", publicHealthCaseDT
							.getTransmissionModeCd());
					//PHC147
					request.setAttribute("otherTransmissionModeTxt",
							publicHealthCaseDT.getTransmissionModeDescTxt());
					request.setAttribute("detectionMethod", publicHealthCaseDT
							.getDetectionMethodCd());
					request.setAttribute("otherDetectionMethodDescTxt",
							publicHealthCaseDT.getDetectionMethodDescTxt());
					request.setAttribute("caseStatus", publicHealthCaseDT
							.getCaseClassCd());
					request.setAttribute("numberOfCases", publicHealthCaseDT
							.getGroupCaseCnt());
					request.setAttribute("MMWRWeek", publicHealthCaseDT
							.getMmwrWeek());
					request.setAttribute("MMWRYear", publicHealthCaseDT
							.getMmwrYear());
					String generalComments=publicHealthCaseDT.getTxt();
					if(generalComments!=null)
						generalComments=generalComments.replaceAll("\r\n", "\n");
					request.setAttribute("generalComments",generalComments);
					request.setAttribute("conditionCd", publicHealthCaseDT.getCd());
					request.setAttribute("conditionCdDescTxt", publicHealthCaseDT
							.getCdDescTxt());
					request.setAttribute("sharedIndicator", publicHealthCaseDT
							.getSharedInd());
					request.setAttribute("patAgeAtOnset", publicHealthCaseDT
							.getPatAgeAtOnset());
					request.setAttribute("patAgeAtOnsetUnitCd", publicHealthCaseDT
							.getPatAgeAtOnsetUnitCd());
					//Added for Contact Tracing
					request.setAttribute("contactPriorityCd", publicHealthCaseDT.getPriorityCd());
					request.setAttribute("infectiousFromDate", publicHealthCaseDT
							.getInfectiousFromDate_s());
					request.setAttribute("infectiousToDate", publicHealthCaseDT
							.getInfectiousToDate_s());
					request.setAttribute("contactInvStatus", publicHealthCaseDT
							.getContactInvStatus());
					request.setAttribute("contactInvTxt", publicHealthCaseDT
							.getContactInvTxt());

					Collection<Object>  actRelationshipDTColl = investigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
					if(actRelationshipDTColl != null)
					{	
						Iterator<Object>  iter = actRelationshipDTColl.iterator();
						while(iter.hasNext()) {	
							ActRelationshipDT dt = (ActRelationshipDT) iter.next();

							if(investigationProxyVO.getTheNotificationSummaryVOCollection()!=null && investigationProxyVO.getTheNotificationSummaryVOCollection().size() > 0) {
								Collection<Object>   theNotificationSummaryVOCollection=investigationProxyVO.getTheNotificationSummaryVOCollection();
								Iterator<Object>  iterNot = theNotificationSummaryVOCollection.iterator();
								while(iterNot.hasNext()){
									NotificationSummaryVO notificationSummaryVO = (NotificationSummaryVO)iterNot.next();
									if((notificationSummaryVO.getNotificationUid().compareTo(dt.getSourceActUid()) == 0) && 
											notificationSummaryVO.isHistory!=null && !notificationSummaryVO.isHistory.equals("T") &&
											!(notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE) || notificationSummaryVO.getRecordStatusCd().trim().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED)))
									{
										notificationExists=true;

										if(dt != null && (dt.isShareInd() || dt.isExportInd()) && !dt.isNNDInd()){
											noReqNotificationCheck=true;
											break;                    		
										}
									}
								}

							}
						}
					}

					if (notificationExists) {
						request.setAttribute("NotificationExists", "true");
					} else {
						request.setAttribute("NotificationExists", "false");

					}
					if(noReqNotificationCheck)
						request.setAttribute("NoReqNotificationCheck","true");
					else if(!noReqNotificationCheck)
						request.setAttribute("NoReqNotificationCheck","false");
					//logger.debug("AssociatedNotificationsInd is "
					//	+ investigationProxyVO.getAssociatedNotificationsInd());
					logger.debug("AssociatedNotificationsInd is "+ notificationExists);

					conditionCd = publicHealthCaseDT.getCd();
					programAreaCd = publicHealthCaseDT.getProgAreaCd();
				}
				Collection<Object>  actIdDTCollection  = investigationProxyVO
						.getPublicHealthCaseVO().getTheActIdDTCollection();
				if (actIdDTCollection  != null) {
					Iterator<Object> anIterator = actIdDTCollection.iterator();
					if (anIterator.hasNext()) {
						ActIdDT actIdDT = (ActIdDT) anIterator.next();
						request.setAttribute("stateCaseID", actIdDT
								.getRootExtensionTxt());
					}
					if (anIterator.hasNext()) {
						ActIdDT actIdDT = (ActIdDT) anIterator.next();
						logger.debug(" ABCstateCaseID");
						request.setAttribute("abcsStateCaseID", actIdDT
								.getRootExtensionTxt());
					}

				}

				Collection<Object>  participationDTCollection  = investigationProxyVO
						.getPublicHealthCaseVO().getTheParticipationDTCollection();
				if (participationDTCollection  != null) {
					Iterator<Object> anIterator = null;
					for (anIterator = participationDTCollection.iterator(); anIterator
							.hasNext();) {
						ParticipationDT participationDT = (ParticipationDT) anIterator
								.next();
						if (participationDT.getTypeCd() != null
								&& participationDT.getTypeCd().equals(
										NEDSSConstants.PHC_INVESTIGATOR)) {
							request.setAttribute("dateAssignedToInvestigation",
									this.formatDate(participationDT.getFromTime()));
							break;
						}
					}
				}
				ConfirmationMethodDT confirmationMethodDT = null;
				Collection<Object>  confirmationMethodDTCollection  = investigationProxyVO
						.getPublicHealthCaseVO()
						.getTheConfirmationMethodDTCollection();
				request.setAttribute("confirmationMethodList", this
						.getConfirmationMethodList(investigationProxyVO));
				if (confirmationMethodDTCollection  != null) {
					Iterator<Object> anIterator = null;
					for (anIterator = confirmationMethodDTCollection.iterator(); anIterator
							.hasNext();) {
						confirmationMethodDT = (ConfirmationMethodDT) anIterator
								.next();
						if (confirmationMethodDT != null
								&& confirmationMethodDT.getConfirmationMethodTime() != null) {
							request.setAttribute("confirmationDate", this
									.formatDate(confirmationMethodDT
											.getConfirmationMethodTime()));
							break;
						}
					}
				}

				ArrayList<Object> contactNamedByPatList = new ArrayList<Object>();
				ArrayList<Object> patNamedByContactsList = new ArrayList<Object>();	
				CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
				StringBuffer strContactNamedByPatList = new StringBuffer(" ");
				StringBuffer strPatNamedByContactsList = new StringBuffer(" ");
				Collection<Object>  ContactSummaryDTCollection  = (ArrayList<Object> )investigationProxyVO.getTheCTContactSummaryDTCollection();
				if(sCurrentTask.substring(0,4).equalsIgnoreCase("View")){
					if(ContactSummaryDTCollection != null) {
						Iterator<Object> ite = ContactSummaryDTCollection.iterator();
						while (ite.hasNext()) {
							CTContactSummaryDT ctSumDT = (CTContactSummaryDT) ite.next();
							ctSumDT.setPriority((ctSumDT.getPriorityCd() == null) ?
									"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,ctSumDT.getPriorityCd()));

							ctSumDT.setDisposition((ctSumDT.getDispositionCd()== null) ?  
									"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION,ctSumDT.getDispositionCd()));

							if(ctSumDT.isContactNamedByPatient()){						
								contactNamedByPatList.add(ctSumDT);
							}
							if(ctSumDT.isPatientNamedByContact()){

								patNamedByContactsList.add(ctSumDT);
							}
						}
					}
					request.setAttribute("contactNamedByPatList",contactNamedByPatList);
					request.setAttribute("patNamedByContactsList",patNamedByContactsList);	
				}
				if(!sCurrentTask.substring(0,4).equalsIgnoreCase("View")){
					if(ContactSummaryDTCollection != null) {
						Iterator<Object> ite = ContactSummaryDTCollection.iterator();
						while (ite.hasNext()) {
							CTContactSummaryDT contactsumDT = (CTContactSummaryDT) ite.next();
							if(contactsumDT != null && contactsumDT.isContactNamedByPatient()){
								strContactNamedByPatList.append(contactsumDT.getNamedOnDate() == null ? "No Date" :
									StringUtils.formatDate(contactsumDT.getNamedOnDate()))
									.append("$");
								strContactNamedByPatList.append(contactsumDT.getLocalId()== null ?
										"" :  contactsumDT.getLocalId()).                     
										append("$");
								strContactNamedByPatList.append( (contactsumDT.getName() == null) ?
										"" :contactsumDT.getName()).
										append("$");
								strContactNamedByPatList.append((contactsumDT.getPriorityCd() == null) ?
										"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,contactsumDT.getPriorityCd())).
										append("$");
								strContactNamedByPatList.append( (contactsumDT.getDispositionCd()
										== null) ?  "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION,contactsumDT.getDispositionCd())).append("$");
								strContactNamedByPatList.append(contactsumDT.getContactPhcLocalId() == null ?
										"" :  contactsumDT.getContactPhcLocalId()).append("|");


							}
							if(contactsumDT != null && contactsumDT.isPatientNamedByContact()){
								strPatNamedByContactsList.append(contactsumDT.getNamedOnDate() == null ? "No Date" :
									StringUtils.formatDate(contactsumDT.getNamedOnDate()))
									.append("$");
								strPatNamedByContactsList.append(contactsumDT.getLocalId()== null ?
										"" :  contactsumDT.getLocalId()).                     
										append("$");
								strPatNamedByContactsList.append( (contactsumDT.getNamedBy() == null) ?
										"" :contactsumDT.getNamedBy()).
										append("$");
								strPatNamedByContactsList.append((contactsumDT.getPriorityCd() == null) ?
										"" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_PRIORITY,contactsumDT.getPriorityCd())).
										append("$");
								strPatNamedByContactsList.append( (contactsumDT.getDispositionCd()
										== null) ?  "" : cachedDropDownValues.getDescForCode(NEDSSConstants.CONTACT_DISPOSITION,contactsumDT.getDispositionCd())).append("$");
								strPatNamedByContactsList.append(contactsumDT.getContactPhcLocalId() == null ?
										"" :  contactsumDT.getContactPhcLocalId()).append("|");	        		 

							}
						}
					}
					request.setAttribute("contactNamedByPatList",strContactNamedByPatList);
					request.setAttribute("patNamedByContactsList",strPatNamedByContactsList);	
				}	

				if(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid()!=null)
					InvestigationUtil.storeContactContextInformation(request.getSession(),investigationProxyVO.getPublicHealthCaseVO(),investigationProxyVO.getThePersonVOCollection());
			} catch (Exception ex) {
				logger.error("Error in convertPublicHealthCaseToRequest: "+ex.getMessage());
				ex.printStackTrace();
			}			
		}
		
	}
		

	protected void convertVaccinationSummaryToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {

		String currentTask = NBSContext.getCurrentTask(request.getSession());

		Collection<Object>  vaccinationSummaryVOCollection  = investigationProxyVO
				.getTheVaccinationSummaryVOCollection();
		StringBuffer strVaccinationSumList = new StringBuffer("");

		if (vaccinationSummaryVOCollection  == null) {
			logger.debug("vaccination summary collection arraylist is null");
		} else {

			Iterator<Object> itr = vaccinationSummaryVOCollection.iterator();

			while (itr.hasNext()) {

				VaccinationSummaryVO vaccination = (VaccinationSummaryVO) itr
						.next();
				if (vaccination != null
						&& vaccination.getInterventionUid() != null) {
					
					String actionLink = vaccination.getActivityFromTime() == null ? "No Date"
							: StringUtils.formatDate(vaccination.getActivityFromTime());
					
					vaccination.setActionLink(actionLink);
				}
				/*if (vaccination != null
						&& vaccination.getInterventionUid() != null) {

						strVaccinationSumList.append("--");

					strVaccinationSumList.append(vaccination
							.getActivityFromTime() == null ? "No Date"
							: StringUtils.formatDate(vaccination
									.getActivityFromTime()));
						strVaccinationSumList.append("--");
					   strVaccinationSumList.append("$");

					strVaccinationSumList.append(
							(vaccination.getVaccineAdministered() == null) ? ""
									: vaccination.getVaccineAdministered())
							.append("$");
					strVaccinationSumList.append(
							(vaccination.getLocalId() == null) ? ""
									: vaccination.getLocalId()).append("$");

					strVaccinationSumList
								.append("**")
								.append(
										(vaccination.getInterventionUid() == null) ? new Long(
												0)
												: vaccination
														.getInterventionUid())
								.append("**");

					strVaccinationSumList.append("|");

					logger.debug("\n\n\nMaterial Name: "
							+ vaccination.getVaccineAdministered()
							+ "\n\n Time: "
							+ StringUtils.formatDate(vaccination
									.getActivityFromTime()) + "\n\n\nID: "
							+ vaccination.getLocalId());
					logger.debug("\n\n\nInterventionUID : "
							+ vaccination.getInterventionUid());
				}
			*/}
		}

		request.setAttribute("vaccinationSummaryList", vaccinationSummaryVOCollection);
	}

	protected EntityLocatorParticipationDT getPersonLocatorDT(
			String locatorType, PersonVO personVO) {
		Collection<Object>  entityLocatorParticipationDTCollection  = personVO
				.getTheEntityLocatorParticipationDTCollection();
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object> anIterator = null;
			for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator
					.hasNext();) {
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
						.next();
				if (entityLocatorParticipationDT.getClassCd() != null
						&& entityLocatorParticipationDT.getClassCd().equals(
								NEDSSConstants.TELE)
						&& entityLocatorParticipationDT.getTheTeleLocatorDT()
								.getPhoneNbrTxt() != null
						&& entityLocatorParticipationDT.getUseCd() != null
						&& entityLocatorParticipationDT.getUseCd().equals(
								NEDSSConstants.WORK_PHONE)
						&& entityLocatorParticipationDT.getCd() != null
						&& !entityLocatorParticipationDT.getCd().trim().equals(
								"")
						&& entityLocatorParticipationDT.getCd().equals(
								NEDSSConstants.PHONE)) {
					logger
							.debug("InvestigationMeaslesPreAction.getPersonLocatorDT: locatorType: "
									+ entityLocatorParticipationDT.getClassCd());
					return entityLocatorParticipationDT;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	protected PersonNameDT getPersonNameDT(String nameUserCd, PersonVO personVO) {
		PersonNameDT personNameDT = null;
		//logger.debug("getPersonNameDT().getThePersonNameDTCollection: " +
		// personVO.getThePersonNameDTCollection());
		Collection<Object>  personNameDTCollection  = personVO
				.getThePersonNameDTCollection();
		if (personNameDTCollection  != null) {
			//logger.info("getPersonNameDT().getThePersonNameDTCollection: " +
			// personVO.getThePersonNameDTCollection());
			Iterator<Object> anIterator = null;
			for (anIterator = personNameDTCollection.iterator(); anIterator
					.hasNext();) {
				PersonNameDT temp = (PersonNameDT) anIterator.next();
				logger.debug("getPersonNameDT.getThePersonNameDT: "
						+ temp.getNmUseCd());
				if (temp.getNmUseCd() != null
						&& temp.getNmUseCd().trim()
								.equalsIgnoreCase(nameUserCd)) {
					logger.info("getPersonNameDT.getPersonNameDT: "
							+ temp.getFirstNm() + " nameType: "
							+ temp.getNmUseCd());
					personNameDT = temp;
				} else {
					continue;
				}
			}
		}
		return personNameDT;
	}

	protected PersonVO getPersonVO(String type_cd,
			InvestigationProxyVO investigationProxyVO) {
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  personVOCollection  = null;
		ParticipationDT participationDT = null;
		PersonVO personVO = null;
		if (investigationProxyVO != null
				&& investigationProxyVO.getPublicHealthCaseVO() != null) {
			participationDTCollection  = investigationProxyVO
					.getPublicHealthCaseVO().getTheParticipationDTCollection();
			personVOCollection  = investigationProxyVO
					.getThePersonVOCollection();
			//logger.debug("convertProxyToRequestObj() after personVOCollection
			// size: " + personVOCollection.size());
			if (participationDTCollection  != null && personVOCollection  != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null
							&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
						//logger.debug("convertProxyToRequestObj() got
						// participationDT for type_cd: " +
						// participationDT.getTypeCd());
						for (anIterator2 = personVOCollection.iterator(); anIterator2
								.hasNext();) {
							personVO = (PersonVO) anIterator2.next();
							if (personVO.getThePersonDT().getPersonUid()
									.longValue() == participationDT
									.getSubjectEntityUid().longValue()) {
								//logger.debug("convertProxyToRequestObj() got
								// personVO for person_uid: " +
								// personVO.getThePersonDT().getPersonUid().longValue()
								// + " and type_cd " +
								// participationDT.getTypeCd());
								return personVO;
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}
			}
		}
		return null;
	}

	protected PersonVO getPersonVO(String type_cd, VaccinationProxyVO vVO) {
		logger.debug("Got into the persoVO finder");
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  personVOCollection  = null;
		ParticipationDT participationDT = null;
		PersonVO personVO = null;
		InterventionVO iVO = vVO.getTheInterventionVO();
		participationDTCollection  = iVO.getTheParticipationDTCollection();
		personVOCollection  = vVO.getThePersonVOCollection();
		if (participationDTCollection  != null) {
			Iterator<Object> anIterator1 = null;
			Iterator<Object> anIterator2 = null;
			for (anIterator1 = participationDTCollection.iterator(); anIterator1
					.hasNext();) {
				participationDT = (ParticipationDT) anIterator1.next();
				if (participationDT.getTypeCd() != null
						&& (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
					for (anIterator2 = personVOCollection.iterator(); anIterator2
							.hasNext();) {
						personVO = (PersonVO) anIterator2.next();
						if (personVO.getThePersonDT().getPersonUid()
								.longValue() == participationDT
								.getSubjectEntityUid().longValue()) {
							logger
									.debug("convertProxyToRequestObj() got personVO for  person_uid: "
											+ personVO.getThePersonDT()
													.getPersonUid().longValue()
											+ " and type_cd "
											+ participationDT.getTypeCd());
							return personVO;
						} else {
							continue;
						}
					}
				} else {
					continue;
				}
			}
		}
		return null;
	}

	protected String getSelectedTest(String code) {
		CachedDropDownValues cdv = new CachedDropDownValues();
		logger.debug("code ie. ordered test is: " + code);
		String obsCode = cdv.getDescForCode("LAB_TEST", code);
		logger.debug("Value for code passed is: " + obsCode);
		return obsCode;
	} //getSelectedTest

	protected EntityLocatorParticipationDT getWorkTeleLocatorDT(PersonVO perVO) {

		Collection<Object>  entityLocatorParticipationDTCollection  = perVO
				.getTheEntityLocatorParticipationDTCollection();

		if (entityLocatorParticipationDTCollection  != null) {

			Iterator<Object> anIterator = null;

			for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator
					.hasNext();) {

				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
						.next();

				if (entityLocatorParticipationDT.getClassCd() != null
						&& entityLocatorParticipationDT.getClassCd().equals(
								NEDSSConstants.TELE)
						&& entityLocatorParticipationDT.getUseCd() != null
						&& entityLocatorParticipationDT.getUseCd().equals(
								NEDSSConstants.WORK_PHONE)
						&& entityLocatorParticipationDT.getTheTeleLocatorDT()
								.getPhoneNbrTxt() != null
						&& entityLocatorParticipationDT.getCd() != null
						&& !entityLocatorParticipationDT.getCd().trim().equals(
								"")
						&& entityLocatorParticipationDT.getCd().equals(
								NEDSSConstants.PHONE)) {
					logger
							.debug("InvestigationMeaslesPreAction.getPersonLocatorDT: locatorType: "
									+ entityLocatorParticipationDT.getClassCd());

					return entityLocatorParticipationDT;
				} else {

					continue;
				}
			}
		}

		return null;

	}

	protected void setNIForObsValueCoded(Collection<Object>  observations) {

		Iterator<Object> collItor = observations.iterator();

		if (collItor == null) {

			return;
		}

		String _blank = "";

		while (collItor.hasNext()) {

			ObservationVO obsVO = (ObservationVO) collItor.next();
			Collection<Object>  obsValDTs = obsVO.getTheObsValueCodedDTCollection();

			if (obsValDTs == null) {

				continue;
			}

			Iterator<Object> dtItor = obsValDTs.iterator();

			while (dtItor.hasNext()) {

				ObsValueCodedDT dt = (ObsValueCodedDT) dtItor.next();

				if ((dt.getCode() == null)
						|| (dt.getCode().trim().length() == 0)) {
					dt.setCode("NI");
				}
			}
		}
	}

	protected ObservationVO getObservationVO(
			InvestigationProxyVO investigationProxyVO,
			String investigationFormCd) {

		String observationCode = "";
		ObservationVO observationVO = null;
		Iterator<ObservationVO> anIterator = null;
		Collection<ObservationVO>  obsCollection  = investigationProxyVO
				.getTheObservationVOCollection();

		if (obsCollection  != null) {
			anIterator = obsCollection.iterator();

			while (anIterator.hasNext()) {
				observationVO = (ObservationVO) anIterator.next();

				if (observationVO.getTheObservationDT().getCd() != null
						&& observationVO.getTheObservationDT().getCd()
								.startsWith(investigationFormCd)) {
					logger.debug("getObservation: "
							+ observationVO.getTheObservationDT().getCd());

					return observationVO;
				}
			}
		}

		return null;
	}

	protected String getConfirmationMethodList(
			InvestigationProxyVO investigationProxyVO) {
		String confirmationMethodList = "";
		ConfirmationMethodDT confirmationMethodDT = null;
		Collection<Object>  coll = investigationProxyVO.getPublicHealthCaseVO()
				.getTheConfirmationMethodDTCollection();
		if (coll != null) {
			Iterator<Object> anIterator = null;
			for (anIterator = coll.iterator(); anIterator.hasNext();) {
				confirmationMethodDT = (ConfirmationMethodDT) anIterator.next();
				if (confirmationMethodDT != null) {
					if (confirmationMethodDT.getConfirmationMethodCd() != null
							&& !confirmationMethodDT.getConfirmationMethodCd()
									.equals("NA")) {
						confirmationMethodList += confirmationMethodDT
								.getConfirmationMethodCd();
					}
				}
				confirmationMethodList += "|";
			}
			//getConfirmationMethodCd
		}
		return confirmationMethodList;
	}

	protected String formatDate(java.sql.Timestamp timestamp) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (timestamp != null) {
			date = new Date(timestamp.getTime());
		}
		if (date == null) {
			return "";
		} else {
			return formatter.format(date);
		}
	}

	protected String dateLink(java.sql.Timestamp timestamp) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (timestamp != null) {
			date = new Date(timestamp.getTime());
		}
		if (date == null) {
			return "No Date";
		} else {
			return formatter.format(date);
		}
	}

	/**
	 * Get ObservationSummary values from request object and put it to
	 * appropriate object
	 * 
	 * @param InvestigationProxyVO
	 *            the investigationProxyVO
	 * @param HttpServletRequest
	 *            the request
	 */
	protected void convertObservationSummaryToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		ObservationSummaryUtil summaryUtil = new ObservationSummaryUtil();

		String currentTask = NBSContext.getCurrentTask(request.getSession());

		Collection<Object>  labObsSummaryColl = investigationProxyVO
				.getTheLabReportSummaryVOCollection();
		if (labObsSummaryColl == null)
			labObsSummaryColl = new ArrayList<Object> ();
		Collection<Object>  morbObsSummaryColl = investigationProxyVO
				.getTheMorbReportSummaryVOCollection();
		this.checkAndAddLabSummaryCollection(morbObsSummaryColl,
				labObsSummaryColl);
		Collection<Object>  treatmentCollection  = investigationProxyVO
				.getTheTreatmentSummaryVOCollection();
		if (treatmentCollection  == null)
			treatmentCollection  = new ArrayList<Object> ();

		if (labObsSummaryColl != null && labObsSummaryColl.size() > 0) {
			StringBuffer sbObsLabList = new StringBuffer("");
			for (Iterator<Object> anIterator = labObsSummaryColl.iterator(); anIterator
					.hasNext();) {
				ReportSummaryInterface sumVO = (ReportSummaryInterface) anIterator
						.next();
				if (sumVO instanceof LabReportSummaryVO) {
					LabReportSummaryVO labSummaryVO = (LabReportSummaryVO) sumVO;
					sbObsLabList.append("1--");
					sbObsLabList.append(this.dateLink(labSummaryVO
							.getDateReceived()));
						sbObsLabList.append("--");
					sbObsLabList.append("$");

					sbObsLabList.append(
							this.formatDate(labSummaryVO.getDateCollected()))
							.append("$");
					sbObsLabList.append(
							this.replaceNull(labSummaryVO.getOrderedTest()))
							.append("$");
					sbObsLabList.append(
							this.replaceNull(labSummaryVO.getLocalId()))
							.append("$");
						sbObsLabList.append("(1)**").append(
								labSummaryVO.getObservationUid()).append("**");
					sbObsLabList.append(
							summaryUtil.convertResultedTest(labSummaryVO
									.getTheResultedTestSummaryVOCollection(),
									false)).append("|");

				} else if (sumVO instanceof MorbReportSummaryVO) {
					MorbReportSummaryVO morbSummaryVO = (MorbReportSummaryVO) sumVO;
					Collection<Object>  morbLabColl = morbSummaryVO
							.getTheLabReportSummaryVOColl();
					if (morbLabColl != null && morbLabColl.size() > 0) {
						Iterator< Object> morbLabItor = morbLabColl.iterator();
						if (morbLabItor.hasNext()) {
							LabReportSummaryVO labSummaryVO = (LabReportSummaryVO) morbLabItor
									.next();
							sbObsLabList.append(
									this.dateLink(morbSummaryVO
											.getDateReceived())).append("$");
							sbObsLabList.append(
									this.formatDate(labSummaryVO
											.getDateCollected())).append("$");
							sbObsLabList.append(
									this.replaceNull(labSummaryVO
											.getOrderedTest())).append("$");
							sbObsLabList.append(
									this
											.replaceNull(morbSummaryVO
													.getLocalId())).append("$");
							sbObsLabList
									.append(
											summaryUtil
													.convertMorbInLab(currentTask,
															morbLabColl,
															morbSummaryVO
																	.getConditionDescTxt(),
															morbSummaryVO
																	.getObservationUid(),
															false)).append("|");
						} //if
					} //if
				}
			}
			request.setAttribute("observationSummaryLabList", sbObsLabList
					.toString());
		}

		if (morbObsSummaryColl != null && morbObsSummaryColl.size() > 0) {
			StringBuffer sb = new StringBuffer("");
			for (Iterator<Object> anIterator = morbObsSummaryColl.iterator(); anIterator
					.hasNext();) {
				MorbReportSummaryVO morbSummaryVO = (MorbReportSummaryVO) anIterator
						.next();

				sb.append("--");
				sb.append(this.dateLink(morbSummaryVO.getDateReceived()));

				sb.append("--");
				sb.append("$");
				sb
						.append(
								this.replaceNull(morbSummaryVO
										.getConditionDescTxt())).append("$");
				sb.append(this.formatDate(morbSummaryVO.getReportDate()))
						.append("$");
				sb.append(
						this.replaceNull(morbSummaryVO.getReportTypeDescTxt()))
						.append("$");
				sb.append(this.replaceNull(morbSummaryVO.getLocalId())).append(
						"$");
					sb.append("**").append(morbSummaryVO.getObservationUid())
							.append("**");
				sb.append(
						summaryUtil
								.convertMorbResultedTest(morbSummaryVO, true))
						.append("|");
				if (morbSummaryVO.getTheTreatmentSummaryVOColl() != null) {
					treatmentCollection.addAll(morbSummaryVO
							.getTheTreatmentSummaryVOColl());
				}
			}
			request.setAttribute("observationSummaryMorbList", sb.toString());
		}
	}

	private void checkAndAddLabSummaryCollection(
			Collection<Object>  morbSummaryCollection, Collection<Object>  labSummaryCollection) {
		if (morbSummaryCollection  != null && morbSummaryCollection.size() > 0) {
			Collection<Object>  labColl = null;
			MorbReportSummaryVO morbSumVO = null;
			Iterator<Object> itor = morbSummaryCollection.iterator();
			while (itor.hasNext()) {
				morbSumVO = (MorbReportSummaryVO) itor.next();
				labColl = morbSumVO.getTheLabReportSummaryVOColl();
				if (labColl != null && labColl.size() > 0) {
					//if there is morb collection and no lab collection create
					// new Arraylist
					if (labSummaryCollection  == null)
						labSummaryCollection  = new ArrayList<Object> ();
					labSummaryCollection.add(morbSumVO);
				}
			}
		}
	}

	/**
	 * Get values from InvestigationForm object and put it to request object
	 * 
	 * @param InvestigationProxyVO
	 *            the investigationProxyVO
	 * @param HttpServletRequest
	 *            the request
	 */
	protected void convertOrganizationToRequest(
			InvestigationProxyVO investigationProxyVO,
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

	
	/**
	 * Access to get EntityLocatorParticipationDT from personVO object
	 * 
	 * @param String
	 *            the locatorType
	 * @param PersonVO
	 *            the personVO
	 * @return EntityLocatorParticipationDT
	 */
	protected EntityLocatorParticipationDT getInvestigatorTeleLocatorDT(
			PersonVO personVO) {
		Collection<Object>  entityLocatorParticipationDTCollection  = personVO
				.getTheEntityLocatorParticipationDTCollection();
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object> anIterator = null;
			for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator
					.hasNext();) {
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
						.next();
				if (entityLocatorParticipationDT.getClassCd() != null
						&& entityLocatorParticipationDT.getClassCd().equals(
								NEDSSConstants.TELE)
						&& entityLocatorParticipationDT.getUseCd().equals(
								NEDSSConstants.WORK_PHONE)
						&& entityLocatorParticipationDT.getCd() != null
						&& !entityLocatorParticipationDT.getCd().trim().equals(
								"")
						&& entityLocatorParticipationDT.getCd().equals(
								NEDSSConstants.PHONE)
						&& entityLocatorParticipationDT.getTheTeleLocatorDT()
								.getPhoneNbrTxt() != null) {
					logger
							.debug("InvestigationMeaslesPreAction.getPersonLocatorDT: locatorType: "
									+ entityLocatorParticipationDT.getClassCd());
					return entityLocatorParticipationDT;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	/**
	 * Access to get observation code from observationvo object
	 * 
	 * @param ObservationVO
	 *            the observationVO
	 * @return String
	 */
	protected String getObservationCode(ObservationVO observationVO) {
		String observationCode = "";
		ObsValueCodedDT obsValueCodedDT = null;
		Iterator<Object> anIterator = null;
		Collection<Object>  obsValueCodedDTCollection  = observationVO
				.getTheObsValueCodedDTCollection();
		if (obsValueCodedDTCollection  != null) {
			anIterator = obsValueCodedDTCollection.iterator();
			if (anIterator.hasNext()) {
				obsValueCodedDT = (ObsValueCodedDT) anIterator.next();
				if (obsValueCodedDT != null
						&& obsValueCodedDT.getCode() != null
						&& !obsValueCodedDT.getCode().equals("NI")) {
					logger.debug("getObservationCode: "
							+ obsValueCodedDT.getCode()
							+ " for observationCd: "
							+ observationVO.getTheObservationDT().getCd());
					observationCode = obsValueCodedDT.getCode();
				}
			}
		}
		return observationCode;
	}

	/**
	 * Access to get EntityLocatorParticipationDT from OrganizationVO object
	 * 
	 * @param String
	 *            the nameUserCd
	 * @param OrganizationVO
	 *            the organizationVO
	 * @return EntityLocatorParticipationDT
	 */
	protected EntityLocatorParticipationDT getOrganizationLocatorDT(
			String locatorType, OrganizationVO organizationVO) {
		Collection<Object>  entityLocatorParticipationDTCollection  = organizationVO
				.getTheEntityLocatorParticipationDTCollection();
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object> anIterator = null;
			for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator
					.hasNext();) {
				EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
						.next();
				if (entityLocatorParticipationDT.getClassCd() != null
						&& entityLocatorParticipationDT.getClassCd().trim()
								.equalsIgnoreCase(locatorType)) {
					logger
							.info("InvestigationMeaslesPreAction.getOrganizationLocatorDT: locatorType: "
									+ entityLocatorParticipationDT.getClassCd());
					return entityLocatorParticipationDT;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	/**
	 * Access to get OrganizationNameDT from OrganizationVO object
	 * 
	 * @param String
	 *            the nameUserCd
	 * @param OrganizationVO
	 *            the organizationVO
	 * @return OrganizationNameDT
	 */
	protected OrganizationNameDT getOrganizationNameDT(String nameUserCd,
			OrganizationVO organizationVO) {

		OrganizationNameDT organizationNameDT = null;
		Collection<Object>  organizationNameDTCollection  = organizationVO
				.getTheOrganizationNameDTCollection();

		if (organizationNameDTCollection  != null) {

			Iterator<Object> anIterator = null;

			for (anIterator = organizationNameDTCollection.iterator(); anIterator
					.hasNext();) {
				organizationNameDT = (OrganizationNameDT) anIterator.next();

				if (organizationNameDT.getNmUseCd() != null
						&& organizationNameDT.getNmUseCd().trim()
								.equalsIgnoreCase(nameUserCd)) {
					logger
							.debug("getOrganizationNameDT.organizationNameDT: nameUseCd: "
									+ organizationNameDT.getNmUseCd());

					return organizationNameDT;
				} else {

					continue;
				}
			}
		}

		return null;
	}

	/**
	 * Access to get OrganizationVO from InvestigationProxyVO object
	 * 
	 * @param PersonVO
	 *            the personVO
	 * @param InvestigationProxyVO
	 *            the investigationProxyVO
	 * @return OrganizationVO
	 */
	protected OrganizationVO getOrganizationVO(String type_cd,
			InvestigationProxyVO investigationProxyVO) {
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  organizationVOCollection  = null;
		ParticipationDT participationDT = null;
		OrganizationVO organizationVO = null;
		//Long phcUID =
		// investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		if (investigationProxyVO != null
				&& investigationProxyVO.getPublicHealthCaseVO() != null) {
			participationDTCollection  = investigationProxyVO
					.getPublicHealthCaseVO().getTheParticipationDTCollection();
			organizationVOCollection  = investigationProxyVO
					.getTheOrganizationVOCollection();
			if (participationDTCollection  != null
					&& organizationVOCollection  != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null
							&& (participationDT.getTypeCd().trim())
									.equals(type_cd)) {
						for (anIterator2 = organizationVOCollection.iterator(); anIterator2
								.hasNext();) {
							organizationVO = (OrganizationVO) anIterator2
									.next();
							if (organizationVO.getTheOrganizationDT()
									.getOrganizationUid().longValue() == participationDT
									.getSubjectEntityUid().longValue()) {
								logger
										.debug("InvestigationMeaslesPreAction.getOrganizationVO: got OrganizationVO for  Organization_uid: "
												+ organizationVO
														.getTheOrganizationDT()
														.getOrganizationUid()
												+ " and type_cd "
												+ participationDT.getTypeCd());
								return organizationVO;
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}
			}
		}
		return null;
	}

	/*
	 * Takes an array of Strings, obs code, and the observation collection and
	 * builds the ObsValueCoded Collection<Object>  in the Observation. @param array:
	 * array of Strings that represent the multiple values to be stored @param
	 * code: observation code ie. HEP100, HEP130 @param obsColl: Observation
	 * collection to be modified
	 */
	public void setMultipleValueToObservation(String[] array, String code,
			Collection<ObservationVO>  obsColl) {
		logger.debug("enter ObservationUtils.setMultipleToObservation");
		//        logger.debug("setting multiple observations");
		ObservationVO obsVO = null;
		ObsValueCodedDT obsValueCodedDT = null;
		if (array != null) {
			obsVO = ObservationUtils.findObservationByCode(obsColl, code);
			if (obsVO != null) {
				List<Object> obsValueCoded = new ArrayList<Object> ();
				for (int i = 0; i < array.length; i++) {
					//don't store empty strings in parameter string array
					if (!(array[i].trim()).equals("")) {
						//Build the ObsValueCodedDT object for insert
						obsValueCodedDT = new ObsValueCodedDT();
						obsValueCodedDT.setObservationUid(obsVO
								.getTheObservationDT().getUid());
						obsValueCodedDT.setCode(array[i]);
						obsValueCodedDT.setItNew(true);
						obsValueCodedDT.setItDirty(false);
						obsValueCodedDT.setItDelete(false);
						obsValueCoded.add(obsValueCodedDT);
					}

				}
				//If the observation is not new, add the delete
				if (!obsVO.getTheObservationDT().isItNew()) {
					ObsValueCodedDT obsValueCodedDTdel = new ObsValueCodedDT();
					obsValueCodedDTdel.setObservationUid(obsVO
							.getTheObservationDT().getUid());
					obsValueCodedDTdel.setItNew(false);
					obsValueCodedDTdel.setItDirty(false);
					obsValueCodedDTdel.setItDelete(true);
					/*
					 * Add it to the first position in the collection, so
					 * existing records are deleted before new ones are
					 * inserted.
					 */
					obsValueCoded.add(0, obsValueCodedDTdel);
				}
				obsVO.setTheObsValueCodedDTCollection(obsValueCoded);
				ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
			}
		}
		logger.debug("end  ObservationUtils.setMultipleToObservation");
	}

	/**
	 * Access to get OrganizationVO from Vaccination ProxyVO object
	 * 
	 * @param String
	 *            the type_cd
	 * @param VaccinationProxyVO
	 *            the vVO
	 * @return OrganizationVO object
	 */
	protected OrganizationVO getOrganizationVO(String type_cd,
			VaccinationProxyVO vVO) {

		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  organizationVOCollection  = null;
		ParticipationDT participationDT = null;
		OrganizationVO organizationVO = null;
		InterventionVO iVO = vVO.getTheInterventionVO();
		participationDTCollection  = iVO.getTheParticipationDTCollection();
		organizationVOCollection  = vVO.getTheOrganizationVOCollection();

		if (participationDTCollection  != null
				&& organizationVOCollection  != null) {

			Iterator<Object> anIterator1 = null;
			Iterator<Object> anIterator2 = null;

			for (anIterator1 = participationDTCollection.iterator(); anIterator1
					.hasNext();) {
				participationDT = (ParticipationDT) anIterator1.next();

				if (participationDT.getTypeCd() != null
						&& (participationDT.getTypeCd().trim()).equals(type_cd)) {

					for (anIterator2 = organizationVOCollection.iterator(); anIterator2
							.hasNext();) {
						organizationVO = (OrganizationVO) anIterator2.next();

						if (organizationVO.getTheOrganizationDT()
								.getOrganizationUid().longValue() == participationDT
								.getSubjectEntityUid().longValue()) {

							return organizationVO;
						} else {

							continue;
						}
					}
				} else {

					continue;
				}
			}
		}

		return null;
	}

	/**
	 * Access to get Counties By State code from database
	 * 
	 * @param String
	 *            the stateCd
	 * @return String the value of the country code list
	 */
	private String getCountiesByState(String stateCd) {
		StringBuffer parsedCodes = new StringBuffer("");
		if (stateCd != null) {
			//SRTValues srtValues = new SRTValues();
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<?, ?> treemap = null;

			treemap = srtValues.getCountyCodes(stateCd);

			if (treemap != null) {
				Set<?> set = treemap.keySet();
				Iterator<?> itr = set.iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					String value = (String) treemap.get(key);
					parsedCodes.append(key.trim()).append(
							NEDSSConstants.SRT_PART).append(value.trim())
							.append(NEDSSConstants.SRT_LINE);
				}
			}
		}
		return parsedCodes.toString();
	}

	/**
	 * 
	 * @param investigationProxyVO
	 * @param securityObj
	 * @return boolean
	 * @description This method returns false if auto resend is on for an
	 *              associated notification
	 */
	public boolean showCreateNotificationButton(
			InvestigationProxyVO investigationProxyVO,
			NBSSecurityObj securityObj) {

		if (investigationProxyVO.getTheNotificationSummaryVOCollection() != null) {

			for (Iterator<Object> anIterator = investigationProxyVO
					.getTheNotificationSummaryVOCollection().iterator(); anIterator
					.hasNext();) {
				NotificationSummaryVO notSumVO = (NotificationSummaryVO) anIterator
						.next();
				
				if (notSumVO.getAutoResendInd() != null && 
								((notSumVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF) && notSumVO.getAutoResendInd().equalsIgnoreCase("T")))) {
					return false;
				} //if
			} //for
		} //if
		return true;
	} //disableCreateNotificationButton

	
	public boolean showTransferOwnerShipButton(InvestigationProxyVO investigationProxyVO, NBSSecurityObj securityObj) {
		
		 if(investigationProxyVO.isOOSystemPendInd() == true)
			 return false;
		 else
			 return true;
	}

	/**
	 * 
	 * @param investigationProxyVO
	 * @param request
	 * 
	 * @description This method prepares the treatment summary for data table
	 */
	protected void convertTreatmentSummaryToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {

		String currentTask = NBSContext.getCurrentTask(request.getSession());

		Collection<Object>  treatmentSummaryVOCollection  = investigationProxyVO
				.getTheTreatmentSummaryVOCollection();
		StringBuffer strTreatmentSumList = new StringBuffer("");

		if (treatmentSummaryVOCollection  == null) {
			logger.debug("vaccination summary collection arraylist is null");
		} else {
			CachedDropDownValues cddv = new CachedDropDownValues();
			String sTreatments = cddv.getTreatmentDesc();
			Iterator<Object> itr = treatmentSummaryVOCollection.iterator();

			while (itr.hasNext()) {

				TreatmentSummaryVO treatment = (TreatmentSummaryVO) itr.next();

				if (treatment != null && treatment.getTreatmentUid() != null) {
					strTreatmentSumList.append("--");
					strTreatmentSumList
							.append(treatment.getActivityFromTime() == null ? (treatment
									.getActivityToTime() == null ? "No Date"
									: StringUtils.formatDate(treatment
											.getActivityToTime()))
									: StringUtils.formatDate(treatment
											.getActivityFromTime()));
						strTreatmentSumList.append("--");
					strTreatmentSumList.append("$");

					if (treatment.getTreatmentNameCode() != null) {
						strTreatmentSumList
								.append(
										(treatment.getTreatmentNameCode()
												.equals("OTH")) ? treatment
												.getCustomTreatmentNameCode()
												: StringUtils
														.DescriptionForCode(
																sTreatments,
																treatment
																		.getTreatmentNameCode()))
								.append("$");
					} else {
						strTreatmentSumList.append("");
					}

					strTreatmentSumList.append(
							(treatment.getLocalId() == null) ? "" : treatment
									.getLocalId()).append("$");
						strTreatmentSumList
								.append("**")
								.append(
										(treatment.getTreatmentUid() == null) ? new Long(
												0)
												: treatment.getTreatmentUid())
								.append("**");
					strTreatmentSumList.append("|");

				}
			}

		}

		request.setAttribute("openTreatmentList", strTreatmentSumList
				.toString());
	}

	protected void convertTreatmentSummaryAndMorbToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {

		String currentTask = NBSContext.getCurrentTask(request.getSession());

		Collection<Object>  treatmentSummaryVOCollection  = investigationProxyVO
				.getTheTreatmentSummaryVOCollection();
		Collection<Object>  morbSummaryVOColl = investigationProxyVO
				.getTheMorbReportSummaryVOCollection();
		//System.out.println("TESING <<<<<<<<<<<<<<<<< >>>>>>>>>>>>>>>>>>>>>");
		StringBuffer strTreatmentSumList = new StringBuffer("");
		HashMap<Object, Object> treatmentIdCompare = new HashMap<Object,Object>();

		if (morbSummaryVOColl != null) {
			try {
				CachedDropDownValues cddv = new CachedDropDownValues();
				String sTreatments = cddv.getTreatmentDesc();

				Iterator<Object> morbIter = morbSummaryVOColl.iterator();
				while (morbIter.hasNext()) {
					MorbReportSummaryVO morbSummaryVO = (MorbReportSummaryVO) morbIter
							.next();
					if (morbSummaryVO.getTheTreatmentSummaryVOColl() != null) {
						Collection<Object>  treatmentColl = new ArrayList<Object> ();
						treatmentColl = morbSummaryVO
								.getTheTreatmentSummaryVOColl();
						Iterator<Object> treamentIter = treatmentColl.iterator();
						while (treamentIter.hasNext()) {
							TreatmentSummaryVO treatment = (TreatmentSummaryVO) treamentIter
									.next();

							if (treatment != null
									&& treatment.getTreatmentUid() != null) {

								treatmentIdCompare.put(treatment.getTreatmentUid(),
										treatment.getLocalId());
									strTreatmentSumList.append("1--");
								strTreatmentSumList.append(treatment
										.getActivityToTime() == null ? "No Date"
												: StringUtils.formatDate(treatment
														.getActivityToTime()));
									strTreatmentSumList.append("--");
								strTreatmentSumList.append("$");
								if (treatment.getTreatmentNameCode() != null) {
									if(!treatment
											.getTreatmentNameCode().equals("OTH") &&  StringUtils.DescriptionForCode(sTreatments,treatment
													.getTreatmentNameCode())==null)
										strTreatmentSumList.append(treatment
												.getTreatmentNameCode());
									else		
										strTreatmentSumList
										.append(
												(treatment
														.getTreatmentNameCode()
														.equals("OTH")) ? treatment
																.getCustomTreatmentNameCode()
																: StringUtils
																.DescriptionForCode(
																		sTreatments,
																		treatment
																		.getTreatmentNameCode()))
																		.append("$");
								} else {
									strTreatmentSumList.append("");
								}
								strTreatmentSumList.append(
										(treatment.getLocalId() == null) ? ""
												: treatment.getLocalId()).append(
														"$");
									strTreatmentSumList
									.append("(1)**")
									.append(
											(treatment.getTreatmentUid() == null) ? new Long(
													0)
											: treatment
											.getTreatmentUid())
											.append("**");
									if (morbSummaryVO.getConditionDescTxt() != null) {
										strTreatmentSumList.append("[[");
										strTreatmentSumList
										.append("2--")
										.append(
												morbSummaryVO
												.getConditionDescTxt())
												.append("--~");
										strTreatmentSumList
										.append("(2)**")
										.append(
												(morbSummaryVO
														.getObservationUid() == null) ? new Long(
																0)
												: morbSummaryVO
												.getObservationUid())
												.append("**");
										strTreatmentSumList.append("$]]");
								}
								strTreatmentSumList.append("|");
							}
						}
					}
				}
			} catch (Exception ex) {
				logger.error("Error in Morb Summary: "+ex.getMessage());
				ex.printStackTrace();
			}	
		}

		if (treatmentSummaryVOCollection  == null) {
			logger.debug("vaccination summary collection arraylist is null");
		} else {
			try {
				CachedDropDownValues cddv = new CachedDropDownValues();
				String sTreatments = cddv.getTreatmentDesc();
				Iterator<Object> itr = treatmentSummaryVOCollection.iterator();

				while (itr.hasNext()) {

					TreatmentSummaryVO treatment = (TreatmentSummaryVO) itr.next();

					if (treatment != null && treatment.getTreatmentUid() != null) {

						if (treatmentIdCompare.get(treatment.getTreatmentUid()) == null) {

								strTreatmentSumList.append("--");

							strTreatmentSumList.append(treatment
									.getActivityFromTime() == null ? (treatment
											.getActivityToTime() == null ? "No Date"
													: StringUtils.formatDate(treatment
															.getActivityToTime())) : StringUtils
															.formatDate(treatment.getActivityFromTime()));

								strTreatmentSumList.append("--");
							strTreatmentSumList.append("$");

							if (treatment.getTreatmentNameCode() != null) {
								if(!treatment
										.getTreatmentNameCode().equals("OTH") &&  StringUtils.DescriptionForCode(sTreatments,treatment
												.getTreatmentNameCode())==null)
									strTreatmentSumList.append(treatment
											.getTreatmentNameCode()).append("$");
								else		
									strTreatmentSumList
									.append(
											(treatment
													.getTreatmentNameCode()
													.equals("OTH")) ? treatment
															.getCustomTreatmentNameCode()
															: StringUtils
															.DescriptionForCode(
																	sTreatments,
																	treatment
																	.getTreatmentNameCode()))
																	.append("$");
							} else {
								strTreatmentSumList.append("");
							}

							strTreatmentSumList.append(
									(treatment.getLocalId() == null) ? ""
											: treatment.getLocalId()).append("$");

								strTreatmentSumList
								.append("**")
								.append(
										(treatment.getTreatmentUid() == null) ? new Long(
												0)
										: treatment
										.getTreatmentUid())
										.append("**");
							strTreatmentSumList.append("|");

						}
					}
				}
			} catch (Exception ex) {
				logger.error("Error in Treatment Summary: "+ex.getMessage());
				ex.printStackTrace();
			}	
		}

		request.setAttribute("openTreatmentList", strTreatmentSumList
				.toString());
	}

	protected String replaceNull(Object nullObj) {
		String returnStr = "";
		if (nullObj instanceof String) {
			returnStr = (nullObj == null) ? "" : (String) nullObj;
		} else if (nullObj instanceof Long) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = ((Long) nullObj).toString();
		} else if (nullObj instanceof BigDecimal) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = ((BigDecimal) nullObj).toString();
		} else if (nullObj instanceof Double) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = ((Double) nullObj).toString();
		} else if (nullObj instanceof Integer) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = ((Integer) nullObj).toString();
		} else
			returnStr = "";

		return returnStr;

	}

	protected String integerValue(Object nullObj) {
		String returnStr = "";
		if (nullObj instanceof Double) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = String.valueOf(((Double) nullObj).intValue());
		} else if (nullObj instanceof Integer) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = ((Integer) nullObj).toString();
		} else if (nullObj instanceof BigDecimal) {
			if (nullObj == null)
				returnStr = "";
			else
				returnStr = String.valueOf(((BigDecimal) nullObj).intValue());
		} else
			returnStr = "";

		return returnStr;

	}

	public void setNumericUnitCd(ObservationVO obsVO) {
		if (obsVO.getTheObsValueNumericDTCollection() != null) {
			Iterator<Object> it = obsVO.getTheObsValueNumericDTCollection().iterator();
			while (it.hasNext()) {
				ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
				if (obsValDT.getNumericValue1() == null
						&& obsValDT.getNumericValue2() == null)
					obsValDT.setNumericUnitCd(null);
				obsValDT.setSeparatorCd(null);
			}
		}
	}

	protected String formatDateObj(Object timeObj) {
		Date date = null;
		java.sql.Timestamp timestamp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (timeObj != null) {
			timestamp = (java.sql.Timestamp) timeObj;
			date = new Date(timestamp.getTime());
		}
		if (date == null) {
			return "";
		} else {
			return formatter.format(date);
		}
	}

	protected void setMultipleToObservationForEdit(String[] array, String code,
			Collection<ObservationVO>  obsColl, Map<Object,Object> obsMap) {
		logger.debug("setting multiple observations");
		ObservationVO obsVO = null;
		ObsValueCodedDT obsValueCodedDT = null;

		ObservationVO oldObsVO = null;
		oldObsVO = (ObservationVO) obsMap.get(code);
		if ((array != null)
				&& (array.length == 1 && array[0].trim().equals("")))
			array = null;
		//dirty
		if ((array != null) && (oldObsVO != null)) {

			obsVO = ObservationUtils.findObservationByCode(obsColl, code);
			if (obsVO != null) {

				//If the observation is not new, add the delete
				if (oldObsVO.getTheObsValueCodedDTCollection() != null) {
					Iterator<Object> itor = oldObsVO.getTheObsValueCodedDTCollection()
							.iterator();
					while (itor.hasNext()) {
						ObsValueCodedDT obsValueCodedDTdel = (ObsValueCodedDT) itor
								.next();
						obsValueCodedDTdel.setItNew(false);
						obsValueCodedDTdel.setItDirty(false);
						obsValueCodedDTdel.setItDelete(true);
						obsVO.getTheObsValueCodedDTCollection().add(
								obsValueCodedDTdel);
					}

					for (int i = 0; i < array.length; i++) {
						//Build the ObsValueCodedDT object for insert
						if (!array[i].trim().equals("")) {
							obsValueCodedDT = new ObsValueCodedDT();
							obsValueCodedDT.setObservationUid(oldObsVO
									.getTheObservationDT().getUid());
							obsValueCodedDT.setCode(array[i]);
							obsValueCodedDT.setItNew(true);
							obsValueCodedDT.setItDirty(false);
							obsValueCodedDT.setItDelete(false);
							obsVO.getTheObsValueCodedDTCollection().add(
									obsValueCodedDT);
						}
					}

				}

			}
			//obsColl.add(oldObsVO);
		}

		//for new obsevation
		if ((array != null) && (oldObsVO == null)) {

			obsVO = ObservationUtils.findObservationByCode(obsColl, code);
			if (obsVO != null) {
				obsVO.getTheObservationDT().setCdSystemDescTxt(
						NEDSSConstants.NEDSS_BASE_SYSTEM);
				obsVO.getTheObservationDT().setCdSystemCd(NEDSSConstants.NBS);
				obsVO.getTheObservationDT()
						.setCdVersion(NEDSSConstants.VERSION);

				obsVO.getTheObservationDT().setStatusTime(
						new java.sql.Timestamp(new Date().getTime()));
				obsVO.getTheObservationDT().setStatusCd(
						NEDSSConstants.STATUS_ACTIVE);

				obsVO.getTheObservationDT().setItNew(true);
				obsVO.getTheObservationDT().setItDirty(false);

				//obsVO.getTheObservationDT().setObservationUid(new
				// Long(tempID--));
				obsVO.setItNew(true);
				obsVO.setItDirty(false);

				List<Object> obsValueCoded = new ArrayList<Object> ();
				for (int i = 0; i < array.length; i++) {
					//Build the ObsValueCodedDT object for insert
					obsValueCodedDT = new ObsValueCodedDT();
					obsValueCodedDT.setObservationUid(obsVO
							.getTheObservationDT().getObservationUid());
					obsValueCodedDT.setCode((String) array[i]);
					obsValueCodedDT.setItNew(true);
					obsValueCodedDT.setItDirty(false);
					obsValueCodedDT.setItDelete(false);
					obsValueCoded.add(obsValueCodedDT);
				}

				obsVO.setTheObsValueCodedDTCollection(obsValueCoded);
				ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
			}
		}

		//for delete
		if ((array == null) && (oldObsVO != null)) {

			oldObsVO.setItDelete(true);
			oldObsVO.setItDirty(false);
			obsColl.add(oldObsVO);

		}

	}



	/**
	 * to get county description using county code
	 * 
	 * @param sStateCd
	 * @param sCountyCd
	 * @return
	 */
	public String getCountyDescTxt(String sStateCd, String sCountyCd) {

		String countyDesc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")
				&& sCountyCd != null && !sCountyCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<?, ?> treemap = srtValues.getCountyCodes(sStateCd);
			if (treemap != null && treemap.get(sCountyCd) != null) {
				countyDesc = (String) treemap.get(sCountyCd);
			}
		}

		return countyDesc;
	}

	/**
	 * Find the corresponding vaccination age unit from the observation
	 * collection
	 * 
	 * @param observationVOCollection
	 *            Collection
	 * @return vaccinationAgeUnit
	 */
	protected Object findVaccinationAgeUnit(Collection<ObservationVO>  observationVOCollection) {
	
	if(observationVOCollection!=null && observationVOCollection.size()>0){
		ObservationVO ageUnitObsVO = ObservationUtils.findObservationByCode(
				observationVOCollection, NEDSSConstants.VAC106);
		Collection<Object>  obsValueCodedColl = ageUnitObsVO
				.getTheObsValueCodedDTCollection();

		if (obsValueCodedColl == null) {
			logger.debug("No vaccination age unit code available.");
			return null;
		} else {
			Iterator<Object> codedIter = obsValueCodedColl.iterator();
			while (codedIter.hasNext()) {
				ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) codedIter
						.next();
				if (obsValueCodedDT == null)
					continue;

				if (obsValueCodedDT.getCode() != null) {
					logger.debug("Vaccination age unit code is: "
							+ obsValueCodedDT.getCode());
					return obsValueCodedDT.getCode();
				}
			}
		}
		}
		return null;
	}
	/**
	 * @param sStateCd :
	 *            String
	 * @return : String
	 */
	public String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<Object, Object> treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}
	protected void convertDocumentSummaryToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		
		String currentTask = NBSContext.getCurrentTask(request.getSession());
		CachedDropDownValues cdv = new CachedDropDownValues();
		Collection<Object>  documentSummaryVOCollection  = investigationProxyVO.getTheDocumentSummaryVOCollection();
		StringBuffer strDocumentSumList = new StringBuffer("");
		if (documentSummaryVOCollection  == null) {
			logger.debug("document summary collection arraylist is null");
		} 
	else {

		Iterator<Object> itr = documentSummaryVOCollection.iterator();

		while (itr.hasNext()) {

			SummaryDT document =(SummaryDT)itr.next();
			
			if (document != null
					&& document.getNbsDocumentUid() != null) {

					strDocumentSumList.append("--");

				strDocumentSumList.append(document
						.getAddTime() == null ? "No Date"
						: StringUtils.formatDate(document
								.getLastChgTime()));
				strDocumentSumList.append("--");
				strDocumentSumList.append("$");
				if(document.getDocType() != null && document.getDocType().length() > 0)
					document.setDocTypeConditionDescTxt(cdv.getCodeShortDescTxt(document.getDocType(),"PUBLIC_HEALTH_EVENT"));
				   if(document.getDocPurposeCd() != null && document.getDocPurposeCd().length() > 0)
					   document.setDocPurposeCdConditionDescTxt(cdv.getCodeShortDescTxt(document.getDocPurposeCd(),"NBS_DOC_PURPOSE"));

				strDocumentSumList.append(
						(document.getDocType() == null) ? ""
								: document.getDocTypeConditionDescTxt())
						.append("$");
				strDocumentSumList.append(
						(document.getDocPurposeCd() == null) ? ""
								: document.getDocPurposeCdConditionDescTxt())
						.append("$");


				strDocumentSumList.append(
						(document.getCdDescTxt() == null) ? ""
								: document.getCdDescTxt())
						.append("$");
				String documentId = (document.getExternalVersionCtrlNbr() != null && document
						.getExternalVersionCtrlNbr().intValue() > 1) ? document
						.getLocalId() + " (Update)"
						: document.getLocalId();
				strDocumentSumList.append(
						(document.getLocalId() == null) ? ""
								: documentId)
						.append("$");

					strDocumentSumList
							.append("**")
							.append(
									(document.getNbsDocumentUid() == null) ? new Long(
											0)
											: document
													.getNbsDocumentUid())
							.append("**");
				strDocumentSumList.append("|");

				
			}
		}
	}
	request.setAttribute("documentSummaryList",strDocumentSumList.toString());
			
			
	}
	
	
	
	/**
	 * This method is used for display vaccination records
	 * @param personUid
	 * @param request
	 */
	public void getVaccinationSummaryRecords(Long personUid, HttpServletRequest request)
    {

		logger.debug("personUid: "+personUid);
        HttpSession session = request.getSession(false);
        MainSessionCommand msCommand = null;
        ArrayList<?> arrVacs = null;
        try{
		        if (personUid != null)
		        {
		
		            try
		            {
		
		                String sBeanJndiName = JNDINames.INTERVENTION_PROXY_EJB;
		                String sMethod = "getVaccinationTableCollectionForPatient";
		                Object[] oParams = new Object[] { personUid };
		                MainSessionHolder holder = new MainSessionHolder();
		                msCommand = holder.getMainSessionCommand(session);
		
		                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		                arrVacs = (ArrayList<?> )arr.get(0);
		            }catch (Exception ex){
		                if (session == null){
		                    logger.error("Error: no session, please login");
		                }
		                logger.fatal("getOldProxyObject: ", ex);
		            }
		
		            if (arrVacs != null && arrVacs.size() > 0)
		            {
		
		                Iterator<?>  iter = arrVacs.iterator();
		                StringBuffer sbVS = new StringBuffer("");
		
		                while (iter.hasNext())
		                {
		                    
		                    VaccinationProxyVO proxy = (VaccinationProxyVO)iter.next();
		                    if (proxy != null)
		                    {
		
		                            Timestamp dateValueRange  = null;
		                            String dateValue="";
		                            
		                            String materialName = "";
		                            
		                            String MfgName = "";
		                            
		                            String orgInterLocalID = "";
		                            String maunLotNumber = "";
		                            
		                            String expDate = "";
		                            Timestamp expDateValue = null;
		                            
		                            String sAgeAtVaccination = "";
		                            String sAgeAtVaccinationUnits = "";
		                            
		                            String targetSite = "";
		                            
		                            String orgName = "";
		                            String orgLocalID = "";
		                            
		                            if(proxy.getTheInterventionVO() != null && proxy.getTheInterventionVO().getTheInterventionDT() != null){
		                            	  if(proxy.getTheInterventionVO()!= null && proxy.getTheInterventionVO().getTheInterventionDT() != null)
		                            		  dateValueRange = proxy.getTheInterventionVO().getTheInterventionDT().getActivityFromTime();
		                                  
		                                  if(dateValueRange != null)
		                                	  dateValue = formatDate(dateValueRange);
		                                  
			                               orgInterLocalID=proxy.getTheInterventionVO().getTheInterventionDT().getLocalId();
			                                
			                               materialName = CachedDropDowns.getCodeDescTxtForCd(proxy.getTheInterventionVO().getTheInterventionDT().getMaterialCd(), "VAC_NM");
			                               
			                               if(materialName == null)
			                                   materialName="";
			                               
			                               MfgName = CachedDropDowns.getCodeDescTxtForCd(proxy.getTheInterventionVO().getTheInterventionDT().getVaccMfgrCd(), "VAC_MFGR");
			                               if(MfgName == null)
			                                   MfgName = "";
			                               
			                               maunLotNumber = proxy.getTheInterventionVO().getTheInterventionDT().getMaterialLotNm();
			                               
			                               if(maunLotNumber == null)
			                            	   maunLotNumber = "";
			                                
			                               expDateValue = proxy.getTheInterventionVO().getTheInterventionDT().getMaterialExpirationTime();
			                               if(expDateValue != null)
			                                   expDate = formatDate(expDateValue);
			                               
			                                Integer ageAtVacc = proxy.getTheInterventionVO().getTheInterventionDT().getAgeAtVacc();
			                                if(ageAtVacc != null)
			                                sAgeAtVaccination = Integer.toString(ageAtVacc);
			                                
			                                sAgeAtVaccinationUnits = CachedDropDowns.getCodeDescTxtForCd(proxy.getTheInterventionVO().getTheInterventionDT().getAgeAtVaccUnitCd(), "AGE_UNIT");
			                                if(sAgeAtVaccinationUnits == null)
			                                	sAgeAtVaccinationUnits = "";
			                                
			                                targetSite = proxy.getTheInterventionVO().getTheInterventionDT().getTargetSiteCd();
			                                if(targetSite == null)
			                                	targetSite= "";
			                               
		                             }
		                            
		                            sbVS.append("dateAdministered").append(NEDSSConstants.BATCH_PART).append(dateValue);
		                            
		                            sbVS.append(NEDSSConstants.BATCH_SECT).append("vaccinationAnatomicalSite").append(NEDSSConstants.BATCH_PART).append(targetSite);
		                            
		                            PersonVO vaccineGiverPersonVO = getPersonVO(NEDSSConstants.PERFORMER_OF_VACCINE, proxy);
		                            String personLastName = "";
		                            String personFirstName = "";
		                            String personLocalID = "";
		                            if(vaccineGiverPersonVO != null){
		                               if(vaccineGiverPersonVO.getPersonNameDT_s(0)!= null)
		                              {
		                               personLastName = vaccineGiverPersonVO.getPersonNameDT_s(0).getLastNm();
		                                if(personLastName == null)
		                                 personLastName="";
		                               personFirstName = vaccineGiverPersonVO.getPersonNameDT_s(0).getFirstNm();
		                                if(personFirstName == null)
		                                 personFirstName ="";
		                               personLocalID = vaccineGiverPersonVO.getThePersonDT().getLocalId();
		                                if(personLocalID == null)
		                                 personLocalID = "";
		                              }
		                           }
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("lastName").append(NEDSSConstants.BATCH_PART).append(personLastName);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("firstName").append(NEDSSConstants.BATCH_PART).append(personFirstName);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("personID").append(NEDSSConstants.BATCH_PART).append(personLocalID);
		                           
		                           OrganizationVO vaccineGiverOrgVO =
		                                   getOrganizationVO(NEDSSConstants.PERFORMER_OF_VACCINE, proxy);
		
		                           
		                           if(vaccineGiverOrgVO != null){
		                             if(vaccineGiverOrgVO.getOrganizationNameDT_s(0) != null)
		                               orgName= vaccineGiverOrgVO.getOrganizationNameDT_s(0).getNmTxt();
		                               if(orgName == null)
		                                 orgName = "";
		                             if(vaccineGiverOrgVO.getTheOrganizationDT() != null)
		                               orgLocalID = vaccineGiverOrgVO.getTheOrganizationDT().getLocalId();
		                               if(orgLocalID == null)
		                                orgLocalID = "";
		                           }
		                           
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("organizationName").append(NEDSSConstants.BATCH_PART).append(orgName);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("organizationID1").append(NEDSSConstants.BATCH_PART).append(orgLocalID);
		                           
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("vaccineAdministered").append(NEDSSConstants.BATCH_PART).append(materialName);
		                           
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("vaccinationID").append(NEDSSConstants.BATCH_PART).append(orgInterLocalID);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("ageAtVaccination").append(NEDSSConstants.BATCH_PART).append(sAgeAtVaccination);
		                            
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("ageAtVaccinationUnits").append(NEDSSConstants.BATCH_PART).append(sAgeAtVaccinationUnits);
		                            
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("manufacturer").append(NEDSSConstants.BATCH_PART).append(MfgName);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("lotNumber").append(NEDSSConstants.BATCH_PART).append(maunLotNumber);
		                            
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append("expirationDate").append(NEDSSConstants.BATCH_PART).append(expDate);
		
		                           sbVS.append(NEDSSConstants.BATCH_SECT).append(NEDSSConstants.BATCH_LINE);
		
		                    }
		                }
		
		                request.setAttribute("vaccinationRecordList", sbVS.toString());
		            }
		        }
		    }catch(Exception ex){
		    	logger.error("Error in getVaccinationSummaryRecords personUid: "+personUid+", Exception: " +ex.getMessage(), ex);
		    }
        }
}