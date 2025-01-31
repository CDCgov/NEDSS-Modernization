package gov.cdc.nedss.webapp.nbs.action.observation.common;

import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;

import java.util.*;

public class LabReportProxyVOExtractor {

	static final LogUtils logger = new LogUtils(LabReportProxyVOExtractor.class
			.getName());

	private LabResultProxyVO labResultProxy;

	private Long labUid;

	QuickEntryEventHelper helper;

	/**
	 * The main() fir testing
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
	}

	/**
	 * Creates a new MorbidityProxyVOExtractor object.
	 * 
	 * @param aMorbProxyVO
	 */
	public LabReportProxyVOExtractor(LabResultProxyVO aLabResultProxy,
			Long aLabUid) {
		this.labResultProxy = aLabResultProxy;
		this.labUid = aLabUid;
	}

	/**
	 * Extracts report date
	 * 
	 * @return String
	 */
	public ObservationVO extractReportDate() { 
		ObservationVO obsVO = null;
		String reportDate = null;

		if (labResultProxy.getTheObservationVOCollection() != null) {
			 obsVO = ObservationUtils.findObservationByUid(
					labResultProxy.getTheObservationVOCollection(), labUid);
 
			

		}

		return obsVO;
	}

	/**
	 * Extracts reporting Source properties
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public Object extractRptOrgAttr(String name) {

		Object returnAttr = null;

		if (name != null && name.equalsIgnoreCase("ORD106UID")) {
			Long orgUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, NEDSSConstants.AUT,
					NEDSSConstants.CLASS_CD_ORG);
			returnAttr = orgUID;
		}
		if (name != null && name.equalsIgnoreCase("ORD107UID")) {
			Long orgUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, NEDSSConstants.ORD,
					NEDSSConstants.CLASS_CD_ORG);
			returnAttr = orgUID;
		}
		return returnAttr;
	}

	/**
	 * @description Extracts Reporting Source Details
	 * @return
	 */
	public Object extractReportingSourceDetails() {

		Object returnAttr = null;

		//Get the reporting source org uid
		Long orgUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				NEDSSConstants.AUT, NEDSSConstants.CLASS_CD_ORG);

		if (labResultProxy.getTheOrganizationVOCollection() != null
				&& labResultProxy.getTheObservationVOCollection().size() != 0) {

			for (Iterator<Object> it = labResultProxy.getTheOrganizationVOCollection()
					.iterator(); it.hasNext();) {

				OrganizationVO orgVO = (OrganizationVO) it.next();
				Long uid = orgVO.getTheOrganizationDT().getOrganizationUid();

				if (uid != null && orgUID != null && uid.compareTo(orgUID) == 0) {
					helper = new QuickEntryEventHelper();
					returnAttr = helper.makeORGDisplayString(orgVO);
				}
			}
		}

		return returnAttr;
	}

	/**
	 * @description Extracts Ordering Source Details
	 * @return
	 */
	public Object extractOrderingFacilityDetails() {

		Object returnAttr = null;

		//Get the reporting source org uid
		Long orgUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				NEDSSConstants.ORD, NEDSSConstants.CLASS_CD_ORG);

		if (labResultProxy.getTheOrganizationVOCollection() != null
				&& labResultProxy.getTheObservationVOCollection().size() != 0) {

			for (Iterator<Object> it = labResultProxy.getTheOrganizationVOCollection()
					.iterator(); it.hasNext();) {

				OrganizationVO orgVO = (OrganizationVO) it.next();
				Long uid = orgVO.getTheOrganizationDT().getOrganizationUid();

				if (uid != null && orgUID != null && uid.compareTo(orgUID) == 0) {
					helper = new QuickEntryEventHelper();
					returnAttr = helper.makeORGDisplayString(orgVO);
				}
			}
		}

		return returnAttr;
	}
	
	
	
	/**
	 * Extracts physician property
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public Object extractPhysicianAttr(String name) {

		Object returnAttr = null;

		if (name != null && name.equalsIgnoreCase("NPP001UID")) {

			Long psnUID = this.getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, NEDSSConstants.ORD,
					NEDSSConstants.CLASS_CD_PSN);
			returnAttr = psnUID;
		}

		return returnAttr;
	}

	/**
	 * @description Extracts Physician Details
	 * @return
	 */
	public Object extractPhysicianDetails() {

		Object returnAttr = null;
		Long psnUID = this.getUIDFromParticipation(
				NEDSSConstants.CLASSTYPE_ENTITY, NEDSSConstants.ORD,
				NEDSSConstants.CLASS_CD_PSN);

		if (labResultProxy.getThePersonVOCollection() != null
				&& labResultProxy.getThePersonVOCollection().size() != 0) {

			for (Iterator<Object> it = labResultProxy.getThePersonVOCollection()
					.iterator(); it.hasNext();) {

				PersonVO pVO = (PersonVO) it.next();
				Long thisPsnUID = pVO.getThePersonDT().getPersonUid();

				if (thisPsnUID != null && psnUID != null
						&& thisPsnUID.compareTo(psnUID) == 0) {

					helper = new QuickEntryEventHelper();
					returnAttr = helper.makePRVDisplayString(pVO);
				}
			}
		}

		return returnAttr;
	}

	private Long getUIDFromParticipation(String type, String typeCd,
			String classCd) {

		Long returnUID = null;

		ObservationVO obsVO = ObservationUtils.findObservationByUid(
				labResultProxy.getTheObservationVOCollection(), labUid);

		if (obsVO.getTheParticipationDTCollection() != null) {

			for (Iterator<Object> it = obsVO.getTheParticipationDTCollection().iterator(); it.hasNext();) {

				ParticipationDT partDT = (ParticipationDT) it.next();
				String thisTypeCd = partDT.getTypeCd();
				String thisClassCd = partDT.getSubjectClassCd();

				if ((thisTypeCd != null && thisTypeCd.equalsIgnoreCase(typeCd))
						&& (thisClassCd != null && thisClassCd
								.equalsIgnoreCase(classCd))) {

					if (type.equalsIgnoreCase(NEDSSConstants.CLASSTYPE_ENTITY)) {
						returnUID = partDT.getSubjectEntityUid();

						break;
					} else {
						returnUID = partDT.getActUid();

						break;
					}
				}
			}
		}

		return returnUID;
	}
	
	/**
	 * Extracts Processing Decision
	 * 
	 * @return String
	 */
	public String extractProcesssingDecision() {

		String processingDecision = null;

		if (labResultProxy.getTheObservationVOCollection() != null
				&& labResultProxy.getTheObservationVOCollection().size() != 0) {

			ObservationVO obsVO = ObservationUtils.findObservationByUid(
					labResultProxy.getTheObservationVOCollection(), labUid);

			processingDecision = obsVO.getTheObservationDT()
					.getProcessingDecisionCd();
		}

		return processingDecision;
	}

} //end of class
