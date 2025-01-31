package gov.cdc.nedss.proxy.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReport;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.Susceptibility;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.TestResult;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.Numeric;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class LabResultProxyVOParser {
	
	private static MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
	
	public LabReport parseLabReportProxyVO(LabReport labReport, LabResultProxyVO labResultProxyVO) {
		ObservationVO rootObservation = retrieveRootObservation(labReport, labResultProxyVO.getTheObservationVOCollection());		
		HashMap<Object,Object> participantsMap = getParticipantUIDs(rootObservation.getTheParticipationDTCollection());
		
		if (labResultProxyVO.getTheMaterialVOCollection() != null)
			retrieveSpecimen(labReport, labResultProxyVO.getTheMaterialVOCollection());		
		
		retrievePatient(labReport, labResultProxyVO.getThePersonVOCollection(), participantsMap);		
		retrieveReportingFacility(labReport, labResultProxyVO.getTheOrganizationVOCollection(), participantsMap);		
		retrieveOrderingProvider(labReport, labResultProxyVO.getThePersonVOCollection(), participantsMap);		
		retrieveOrderingFacility(labReport, labResultProxyVO.getTheOrganizationVOCollection(), participantsMap);	
		
		retrieveTestResults(labReport, labResultProxyVO, participantsMap);
		
		return labReport;
	}


	private HashMap<Object,Object> getParticipantUIDs(Collection<Object> participationDTs) {
		HashMap<Object,Object> participants = new HashMap<Object,Object>();

		Iterator<Object>  participationIter = participationDTs.iterator();
		while (participationIter.hasNext()) {
			ParticipationDT participationDT = (ParticipationDT)participationIter.next();
			String participantKey = participationDT.getTypeCd() + NEDSSConstants.STR_PARS + participationDT.getSubjectClassCd();
			// If performing lab, append act_uid since this is the only participant type that repeats
			if (participationDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.PAR122_TYP_CD))
				participantKey += NEDSSConstants.STR_PARS + participationDT.getActUid().toString();
			Long participantUID = participationDT.getSubjectEntityUid();
			participants.put(participantKey, participantUID);
		}

		return participants;
	}

	private void retrieveTestResults(LabReport labReport, LabResultProxyVO labResultProxyVO, HashMap<Object,Object> participantsMap) {
		//Find each associated Test Result
		Iterator<ObservationVO>  observationVOIter = labResultProxyVO.getTheObservationVOCollection().iterator();
		while (observationVOIter.hasNext()) {
			ObservationVO observationVO = (ObservationVO)observationVOIter.next();
			if (observationVO.getTheObservationDT().getObsDomainCdSt1() != null && observationVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD)) {
					TestResult testResult = new TestResult();
					labReport.getTestResults().add(testResult);
					parseTestResult(testResult, observationVO);
					retrieveSusceptibilities(testResult, observationVO, labResultProxyVO.getTheObservationVOCollection());
					retrievePerformingFacility(testResult, labResultProxyVO, participantsMap);
				}
		}
	}

	private void parseTestResult(TestResult testResult, ObservationVO testResultObservationVO) {
		ObservationDT testResultObservationDT = testResultObservationVO.getTheObservationDT();

		testResult.setTestResultObservationUID(testResultObservationDT.getObservationUid());
		
		Coded testResultCode = new Coded();
		testResultCode.setCode(testResultObservationDT.getCd());
		testResultCode.setCodeDescription(testResultObservationDT.getCdDescTxt());
		testResultCode.setCodeSystemCd(testResultObservationDT.getCdSystemCd());
		testResult.setTestResultCode(testResultCode);

		// Coded Value
		if (testResultObservationVO.getTheObsValueCodedDTCollection() != null) {
			Iterator<Object>  obsValueCodedItr = testResultObservationVO.getTheObsValueCodedDTCollection().iterator();
			
			if (obsValueCodedItr.hasNext()) {
				ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)obsValueCodedItr.next();

				if (obsValueCodedDT.getCode() != null) {
					Coded codedValue = new Coded();
					codedValue.setCode(obsValueCodedDT.getCode());
					codedValue.setCodeDescription(obsValueCodedDT.getDisplayName());
					codedValue.setCodeSystemCd(obsValueCodedDT.getCodeSystemCd()); 
					testResult.setCodedValue(codedValue);
				}
			}
		}
		
		// Numeric Value and Reference Range
		if (testResultObservationVO.getTheObsValueNumericDTCollection() != null) {
			Iterator<Object>  obsValueNumericItr = testResultObservationVO.getTheObsValueNumericDTCollection().iterator();
			
			if (obsValueNumericItr.hasNext()) {
				ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)obsValueNumericItr.next();

				if (obsValueNumericDT.getLowRange() != null) {
					testResult.setReferenceRangeLow(obsValueNumericDT.getLowRange());
				}

				if (obsValueNumericDT.getHighRange() != null) {
					testResult.setReferenceRangeHigh(obsValueNumericDT.getHighRange());
				}
				
				if (obsValueNumericDT.getNumericValue1() != null) {
					Numeric numericValue = new Numeric();
					numericValue.setValue1(obsValueNumericDT.getNumericValue1());
					
					if (obsValueNumericDT.getNumericValue2() != null) 
						numericValue.setValue2(obsValueNumericDT.getNumericValue2());
						
					if (obsValueNumericDT.getComparatorCd1() != null)
						numericValue.setComparator(obsValueNumericDT.getComparatorCd1());
						
					if (obsValueNumericDT.getSeparatorCd() != null)
						numericValue.setSeparator(obsValueNumericDT.getSeparatorCd());
					
					if (obsValueNumericDT.getNumericUnitCd()!= null) {
						Coded numericUnit = Coded.createCoded(obsValueNumericDT.getNumericUnitCd(), DataTables.CODE_VALUE_GENERAL, "UNIT_ISO");
						numericValue.setUnit(numericUnit);
					}
					testResult.setNumericValue(numericValue);
				}
			}
		}
		
		//Text value and Comments
		if (testResultObservationVO.getTheObsValueTxtDTCollection() != null) {
			Iterator<Object>  obsValueTxtItr = testResultObservationVO.getTheObsValueTxtDTCollection().iterator();
			
			while (obsValueTxtItr.hasNext()) {
				ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT)obsValueTxtItr.next();

				if (obsValueTxtDT.getTxtTypeCd()!= null && obsValueTxtDT.getTxtTypeCd().equalsIgnoreCase("N") && obsValueTxtDT.getValueTxt() != null)
					testResult.getComments().add(obsValueTxtDT.getValueTxt()); //Comments, maybe repeating
				else if (obsValueTxtDT.getValueTxt() != null)
					testResult.setTextValue(obsValueTxtDT.getValueTxt());
			}
		}
		
		if (testResultObservationDT.getActivityToTime() != null) {
			Calendar observationDateTime = messageBuilderHelper.convertNBSDateToCalendar(testResultObservationDT.getActivityToTime().toString());
			testResult.setObservationDateTime(observationDateTime);
		}
	}


	private void retrieveSusceptibilities(TestResult testResult, ObservationVO testResultVO, Collection<ObservationVO>  observationVOs) {
		// Get list of Reflex Order UID's - they have the susceptibilities chained off of them
		Collection<Object>  reflexOrderUIDs = getSourceActUIDsFromActRelationshipByTypeCd(testResultVO.getTheActRelationshipDTCollection(), NEDSSConstants.ACT109_TYP_CD);
		
		// Now get the ObservationVO's for the Reflex Orders
		Collection<Object>  reflexOrderTests = findObservationVOsFromCollectionByUID(observationVOs, reflexOrderUIDs);
		
		// Iterate through Reflex Orders, find their Reflex Results (Susceptibilities) and build susceptibility
		Iterator<Object>  reflexOrderTestsItr = reflexOrderTests.iterator();
		
		while (reflexOrderTestsItr.hasNext()) {
			ObservationVO reflexOrderVO = (ObservationVO)reflexOrderTestsItr.next();

			//Only process R_ORDER's - their may be IsolateTracking ObservationVO's in this collection too
			if (reflexOrderVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.R_ORDER)) {
				// Get UIDs for Reflex Res 
				Collection<Object>  reflexResultTestsUIDs = getSourceActUIDsFromActRelationshipByTypeCd(reflexOrderVO.getTheActRelationshipDTCollection(), NEDSSConstants.ACT110_TYP_CD);
	
				// Now get the ObservationVO's for the Reflex Results
				Collection<Object>  reflexResultTests = findObservationVOsFromCollectionByUID(observationVOs, reflexResultTestsUIDs);
	
				// Now process susceptibilities
				Iterator<Object>  susceptibiltiesItr = reflexResultTests.iterator();
				while (susceptibiltiesItr.hasNext()) {
					ObservationVO susceptibiltyVO = (ObservationVO)susceptibiltiesItr.next();
					
					Susceptibility susceptibility = new Susceptibility();
					testResult.getSusceptibilities().add(susceptibility);
					parseSusceptibility(susceptibility, susceptibiltyVO);
				}
			}
		}
		
		
	}

	private Collection<Object>  getSourceActUIDsFromActRelationshipByTypeCd(Collection<Object> actRelationshipDTs, String typeCd) {
		Collection<Object>  sourceActUIDs = new ArrayList<Object> ();
		Iterator<Object>  actRelationshipItr = actRelationshipDTs.iterator();
		while (actRelationshipItr.hasNext()) {
			ActRelationshipDT actRelationshipDT = (ActRelationshipDT)actRelationshipItr.next();
			
			if (actRelationshipDT.getTypeCd().equalsIgnoreCase(typeCd))
				sourceActUIDs.add(actRelationshipDT.getSourceActUid());
		}

		return sourceActUIDs;
	}

	
	private Collection<Object>  findObservationVOsFromCollectionByUID(Collection<ObservationVO> observationVOs, Collection<Object>  observationUIDs) {
		ArrayList<Object> foundObservationVOs = new ArrayList<Object> ();
		
		Iterator<ObservationVO>  observationVOsItr = observationVOs.iterator();
		while (observationVOsItr.hasNext()) {
			ObservationVO observationVO = (ObservationVO)observationVOsItr.next();
			Long observationUID = observationVO.getTheObservationDT().getObservationUid();
			
			Iterator<Object>  observationUIDsItr = observationUIDs.iterator();
			
			while (observationUIDsItr.hasNext()) {
				if ( observationUID.compareTo((Long)observationUIDsItr.next()) == 0 ) {
					foundObservationVOs.add(observationVO);
				}
			}
		}

		return foundObservationVOs;
	}
	
	private void parseSusceptibility(Susceptibility susceptibility, ObservationVO susceptibilityVO) {
		
		// Drug - Coded 
		if (susceptibilityVO.getTheObservationDT().getCd() != null) {
			Coded drug = new Coded();
			drug.setCode(susceptibilityVO.getTheObservationDT().getCd());
			drug.setCodeDescription(susceptibilityVO.getTheObservationDT().getCdDescTxt());
			drug.setCodeSystemCd(susceptibilityVO.getTheObservationDT().getCdSystemCd());
			susceptibility.setDrug(drug);
		}

		// Coded Result
		if (susceptibilityVO.getTheObsValueCodedDTCollection() != null) {
			Iterator<Object>  obsValueCodedItr = susceptibilityVO.getTheObsValueCodedDTCollection().iterator();
			
			if (obsValueCodedItr.hasNext()) {
				ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)obsValueCodedItr.next();

				if (obsValueCodedDT.getCode() != null && ( !obsValueCodedDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB_TESTCODE_NI) || 
					(obsValueCodedDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB_TESTCODE_NI) && susceptibilityVO.getTheObsValueNumericDTCollection() == null)) ) {
					Coded codedResult = new Coded();
					codedResult.setCode(obsValueCodedDT.getCode());
					codedResult.setCodeDescription(obsValueCodedDT.getDisplayName());
					codedResult.setCodeSystemCd(obsValueCodedDT.getCodeSystemCd()); 
					susceptibility.setCodedResult(codedResult);
				}
			}
		}
		
		// Numeric Result
		if (susceptibilityVO.getTheObsValueNumericDTCollection() != null) {
			Iterator<Object>  obsValueNumericItr = susceptibilityVO.getTheObsValueNumericDTCollection().iterator();
			
			if (obsValueNumericItr.hasNext()) {
				ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)obsValueNumericItr.next();

				if (obsValueNumericDT.getNumericValue1() != null) {
					Numeric numericResult = new Numeric();
					numericResult.setValue1(obsValueNumericDT.getNumericValue1());
					
					if (obsValueNumericDT.getNumericValue2() != null) 
						numericResult.setValue2(obsValueNumericDT.getNumericValue2());
						
					if (obsValueNumericDT.getComparatorCd1() != null)
						numericResult.setComparator(obsValueNumericDT.getComparatorCd1());
						
					if (obsValueNumericDT.getSeparatorCd() != null)
						numericResult.setSeparator(obsValueNumericDT.getSeparatorCd());
					
					if (obsValueNumericDT.getNumericUnitCd()!= null) {
						Coded numericUnit = Coded.createCoded(obsValueNumericDT.getNumericUnitCd(), DataTables.CODE_VALUE_GENERAL, "UNIT_ISO");
						numericResult.setUnit(numericUnit);
					}
					
					susceptibility.setNumericResult(numericResult);	
					
				}
			}
		}
		
		// Interpretive Result - Coded
		if (susceptibilityVO.getTheObservationInterpDTCollection() != null) {
			Iterator<Object>  obsValueInterpItr = susceptibilityVO.getTheObservationInterpDTCollection().iterator();
			
			if (obsValueInterpItr.hasNext()) {
				ObservationInterpDT observationInterpDT = (ObservationInterpDT)obsValueInterpItr.next();

				if (observationInterpDT.getInterpretationCd() != null) {
					Coded interpretiveResult = Coded.createCoded(observationInterpDT.getInterpretationCd(), DataTables.CODE_VALUE_GENERAL, "OBS_INTRP");
					susceptibility.setInterpretation(interpretiveResult);
				}
			}
		}
		
		
	}


	private ObservationVO retrieveRootObservation(LabReport labReport, Collection<ObservationVO>  observationVOs) {
		ObservationVO rootObservationVO = null;
		
		Iterator<ObservationVO>  observationIter = observationVOs.iterator();
		while (observationIter.hasNext()) {
			ObservationVO observationVO = (ObservationVO)observationIter.next();
			if (observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null && observationVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LABRESULT_CODE) &&
				observationVO.getTheObservationDT().getObsDomainCdSt1() != null && observationVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD)) {
				rootObservationVO = observationVO;
				break;
			}
		}
			
		if (rootObservationVO != null) {
			parseRootObservation(rootObservationVO, labReport);
			return rootObservationVO;
		}
		else { 
			String errString = "LabResultProxyVOParser.retrieveRootObservation:  Couldn't find root observation's ObservationDT in collection.";
			throw new NEDSSSystemException(errString);
		}
	}
	
	private void parseRootObservation(ObservationVO rootObservationVO, LabReport labReport) {
		
		labReport.setLabReportLocalId(rootObservationVO.getTheObservationDT().getLocalId());
		
		labReport.setFillerOrderNumber(parseFillerNumber(rootObservationVO));
		
		if (rootObservationVO.getTheObservationDT().getEffectiveFromTime() != null) {
			Calendar observationDateTime = messageBuilderHelper.convertNBSDateToCalendar(rootObservationVO.getTheObservationDT().getEffectiveFromTime().toString());
			labReport.setObservationDateTime(observationDateTime);
		}
		
		if (rootObservationVO.getTheObservationDT().getActivityToTime() != null) {
			Calendar laboratoryReportDate = messageBuilderHelper.convertNBSDateToCalendar(rootObservationVO.getTheObservationDT().getActivityToTime().toString());
			labReport.setLaboratoryReportDate(laboratoryReportDate);
		} 
		
		// Ordered Test / Requested Observation
		Coded requestedObservation = new Coded();
		requestedObservation.setCode(rootObservationVO.getTheObservationDT().getCd());
		
		if (rootObservationVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_TESTCODE_NI))
			requestedObservation.setCodeDescription(NEDSSConstants.NOINFORMATIONGIVEN);
		else
			requestedObservation.setCodeDescription(rootObservationVO.getTheObservationDT().getCdDescTxt());
		
		requestedObservation.setCodeSystemCd(rootObservationVO.getTheObservationDT().getCdSystemCd());
		labReport.setRequestedObservation(requestedObservation);

		labReport.setClinicalInformation(rootObservationVO.getTheObservationDT().getTxt());
		
		Coded resultStatus = Coded.createCoded(rootObservationVO.getTheObservationDT().getStatusCd(), DataTables.CODE_VALUE_GENERAL, "ACT_OBJ_ST");
		labReport.setResultStatus(resultStatus);
	}

	private String parseFillerNumber(ObservationVO rootObservationVO) {
		Collection<Object>  actIds = rootObservationVO.getTheActIdDTCollection();
			
		Iterator<Object>  actIdIter = actIds.iterator();
		while (actIdIter.hasNext()) {
			ActIdDT actId = (ActIdDT)actIdIter.next();
			if ( actId.getTypeCd() != null && actId.getTypeCd().equalsIgnoreCase(NEDSSConstants.FILLER_NUMBER_FOR_ACCESSION_NUMBER) && actId.getRootExtensionTxt() != null) {
				return actId.getRootExtensionTxt().trim();
			}
		}
		
		return ""; //None found, but it isn't required
	}

	
	private void retrieveSpecimen(LabReport labReport, Collection<Object>  materialVOs) {

		MaterialVO specimenVO = null;
		
		Iterator<Object>  materialIter = materialVOs.iterator();
		while (materialIter.hasNext()) {
			MaterialVO materialVO = (MaterialVO)materialIter.next();
			specimenVO = materialVO;
			break;
		}
			
		if (specimenVO != null) {
			parseSpecimen(specimenVO, labReport);
		}
	}

	private void parseSpecimen(MaterialVO specimenVO, LabReport labReport) {
		if ( specimenVO.getTheMaterialDT().getCd() != null && 
			 specimenVO.getTheMaterialDT().getCd().trim().length() > 0 && 
			 !specimenVO.getTheMaterialDT().getCd().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN) ) {
			Coded specimenCode = Coded.createCoded(specimenVO.getTheMaterialDT().getCd(), DataTables.CODE_VALUE_GENERAL, "SPECMN_SRC");
			labReport.setSpecimenCode(specimenCode);
		} else if ( specimenVO.getTheMaterialDT().getDescription() != null && specimenVO.getTheMaterialDT().getDescription().trim().length() > 0 ) {
			labReport.setSpecimenDescription(specimenVO.getTheMaterialDT().getDescription() );
		} 
		//Removed error if no code or description since we create "phantom" specimen material objects
	}

	private void retrievePatient(LabReport labReport, Collection<Object>  personVOs, HashMap<Object,Object> participantsMap) {
		// Patient - participation.type_cd = 'PATSBJ', subject_class_cd = 'PSN'
		Long patientUID = (Long)participantsMap.get(NEDSSConstants.PAR110_TYP_CD +
													NEDSSConstants.STR_PARS + 
													NEDSSConstants.PAR101_SUB_CD);
		
		if (patientUID != null) {
			Iterator<Object>  personIter = personVOs.iterator();
			while (personIter.hasNext()) {
				PersonVO personVO = (PersonVO)personIter.next();
				if (personVO.getThePersonDT() != null && personVO.getThePersonDT().getPersonUid() != null &&
					personVO.getThePersonDT().getPersonUid().compareTo(patientUID) == 0 ) {
					labReport.setPatient(personVO);
					break;
				}
			}
		} else {
			String errString = "LabResultProxyVOParser.retrievePatient:  Couldn't find patient for Lab Report:  " + labReport.getLabReportLocalId();
			throw new NEDSSSystemException(errString);
		}
	}

	private void retrieveReportingFacility(LabReport labReport, Collection<Object>  organizationVOs, HashMap<Object,Object> participantsMap) {
		// Reporting Facility - participation.type_cd = 'AUT', subject_class_cd = 'ORG'
		Long reportingFacilityUID = (Long)participantsMap.get(NEDSSConstants.PAR111_TYP_CD +
															  NEDSSConstants.STR_PARS +
															  NEDSSConstants.PAR122_SUB_CD);

		if (reportingFacilityUID != null) {
			Iterator<Object>  organizaitonIter = organizationVOs.iterator();
			while (organizaitonIter.hasNext()) {
				OrganizationVO organizationVO = (OrganizationVO)organizaitonIter.next();
				if (organizationVO.getTheOrganizationDT() != null && organizationVO.getTheOrganizationDT().getOrganizationUid() != null &&
						organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(reportingFacilityUID) == 0 ) {
					labReport.setReportingFacility(organizationVO);
					break;
				}
			}
		} else {
			String errString = "LabResultProxyVOParser.retrieveReportingFacility:  Couldn't find Reporting Facility for Lab Report:  " + labReport.getLabReportLocalId();
			throw new NEDSSSystemException(errString);
		}
	
	}

	private void retrieveOrderingProvider(LabReport labReport, Collection<Object>  personVOs, HashMap<Object,Object> participantsMap) {
		// Ordering Provider - participation.type_cd = 'ORD', subject_class_cd = 'PSN'
		Long orderingProviderUID = (Long)participantsMap.get(NEDSSConstants.PAR102_TYP_CD + 
															 NEDSSConstants.STR_PARS + 
															 NEDSSConstants.PAR101_SUB_CD);
		
		if (orderingProviderUID != null) {
			Iterator<Object>  personIter = personVOs.iterator();
			while (personIter.hasNext()) {
				PersonVO personVO = (PersonVO)personIter.next();
				if (personVO.getThePersonDT() != null && personVO.getThePersonDT().getPersonUid() != null &&
					personVO.getThePersonDT().getPersonUid().compareTo(orderingProviderUID) == 0 ) {
					labReport.setOrderingProvider(personVO);
					break;
				}
			}
			
		}
	}

	private void retrieveOrderingFacility(LabReport labReport, Collection<Object>  organizationVOs, HashMap<Object,Object> participantsMap) {
		// Ordering Facility - participation.type_cd = 'ORD', subject_class_cd = 'ORG'
		Long orderingFaciltiyUID = (Long)participantsMap.get(NEDSSConstants.PAR102_TYP_CD + 
															 NEDSSConstants.STR_PARS + 
															 NEDSSConstants.PAR122_SUB_CD);

		if (orderingFaciltiyUID != null) {
			Iterator<Object>  organizaitonIter = organizationVOs.iterator();
			while (organizaitonIter.hasNext()) {
				OrganizationVO organizationVO = (OrganizationVO)organizaitonIter.next();
				if (organizationVO.getTheOrganizationDT() != null && organizationVO.getTheOrganizationDT().getOrganizationUid() != null &&
						organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(orderingFaciltiyUID) == 0 ) {
					labReport.setOrderingFacility(organizationVO);
					break;
				}
			}
		}
}

	private void retrievePerformingFacility(TestResult testResult, LabResultProxyVO labResultProxyVO, HashMap<Object,Object> participantsMap) {
		Collection<Object>  organizationVOs = labResultProxyVO.getTheOrganizationVOCollection();

		Long performingFacilityUID = (Long)participantsMap.get(NEDSSConstants.PAR122_TYP_CD  + 
															   NEDSSConstants.STR_PARS + 
															   NEDSSConstants.PAR122_SUB_CD + 
															   NEDSSConstants.STR_PARS + 
															   testResult.getTestResultObservationUID().toString());

		if (performingFacilityUID != null) {
			Iterator<Object>  organizaitonIter = organizationVOs.iterator();
			while (organizaitonIter.hasNext()) {
				OrganizationVO organizationVO = (OrganizationVO)organizaitonIter.next();
				if (organizationVO.getTheOrganizationDT() != null && organizationVO.getTheOrganizationDT().getOrganizationUid() != null &&
						organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(performingFacilityUID) == 0 ) {
					testResult.setPerformingFacility(organizationVO);
					break;
				}
			}
		}

	}
	
}
