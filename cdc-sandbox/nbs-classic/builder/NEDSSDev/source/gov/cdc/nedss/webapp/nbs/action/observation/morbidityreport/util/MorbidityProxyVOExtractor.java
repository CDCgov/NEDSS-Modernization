package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util;

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

public class MorbidityProxyVOExtractor {

	static final LogUtils logger = new LogUtils(MorbidityProxyVOExtractor.class
			.getName());

	private MorbidityProxyVO morbProxyVO;

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
	public MorbidityProxyVOExtractor(MorbidityProxyVO aMorbProxyVO) {
		this.morbProxyVO = aMorbProxyVO;
	}

	/**
	 * Extracts report date
	 * 
	 * @return String
	 */
	public ObservationVO extractReportVO() {

		ObservationVO obsVO = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				obsVO = (ObservationVO) it.next();
				String ccdf = obsVO.getTheObservationDT()
						.getCtrlCdDisplayForm();

				if (ccdf != null && ccdf.equalsIgnoreCase(NEDSSConstants.MOB_CTRLCD_DISPLAY)) {
					return obsVO;
					
				}
			}
		}

		return obsVO;
	}
	
	public Integer extractPregnantWeeks(String cd) {
		Integer pregnantWeeks = null;
		if (morbProxyVO.getTheObservationVOCollection() != null && morbProxyVO.getTheObservationVOCollection().size() != 0) {
			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection().iterator(); it.hasNext();) {
				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb126 = obsVO.getTheObservationDT().getCd();
				if (mrb126 != null && mrb126.equalsIgnoreCase(cd)) {
					pregnantWeeks = obsVO.getTheObservationDT().getPregnantWeek();					
					break;
				}
			}
		}
		return pregnantWeeks;
	}
	/**
	 * Extracts reporting organization properties
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public Object extractRptOrgAttr(String name) {

		Object returnAttr = null;

		if (name != null && name.equalsIgnoreCase("INV183UID")) {

			//Get the reporting org uid
			Long orgUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, "ReporterOfMorbReport",
					"ORG");
			returnAttr = orgUID;
		}
		return returnAttr;
	}

	/**
	 * @description Extracts Reporting Source Details
	 * @param orgUID
	 * @return
	 */
	public Object extractReportingSourceDetails() {

		Long orgUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				"ReporterOfMorbReport", "ORG");

		Object returnAttr = null;
		if (morbProxyVO.getTheOrganizationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<Object> it = morbProxyVO.getTheOrganizationVOCollection()
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

		if (name != null && name.equalsIgnoreCase("INV182UID")) {
			Long psnUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, "PhysicianOfMorb", "PSN");
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

		Long psnUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				"PhysicianOfMorb", "PSN");

		if (morbProxyVO.getThePersonVOCollection() != null
				&& morbProxyVO.getThePersonVOCollection().size() != 0) {

			for (Iterator<Object> it = morbProxyVO.getThePersonVOCollection()
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

	/**
	 * Extract reporter properties
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public Object extractReporterAttr(String name) {

		Object returnAttr = null;

		if (name != null && name.equalsIgnoreCase("INV181UID")) {
			Long psnUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, "ReporterOfMorbReport",
					"PSN");
			returnAttr = psnUID;
		}
		return returnAttr;
	}

	/**
	 * @description Reporter Details
	 * @return
	 */
	public Object extractReporterDetails() {

		Object returnAttr = null;
		Long psnUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				"ReporterOfMorbReport", "PSN");

		if (morbProxyVO.getThePersonVOCollection() != null
				&& morbProxyVO.getThePersonVOCollection().size() != 0) {

			for (Iterator<Object> it = morbProxyVO.getThePersonVOCollection()
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

	/**
	 * Extracts hospital property
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public Object extractHospitalAttr(String name) {

		Object returnAttr = null;

		if (name != null && name.equalsIgnoreCase("INV184UID")) {
			Long orgUID = getUIDFromParticipation(
					NEDSSConstants.CLASSTYPE_ENTITY, "HospOfMorbObs", "ORG");
			returnAttr = orgUID;
		}

		return returnAttr;
	}

	/**
	 * @description Hospital Details
	 * @return
	 */
	public Object extractHospitalDetails() {

		Object returnObj = null;
		Long orgUID = getUIDFromParticipation(NEDSSConstants.CLASSTYPE_ENTITY,
				"HospOfMorbObs", "ORG");

		if (morbProxyVO.getTheOrganizationVOCollection() != null
				&& morbProxyVO.getTheOrganizationVOCollection().size() != 0) {

			for (Iterator<Object> it = morbProxyVO.getTheOrganizationVOCollection()
					.iterator(); it.hasNext();) {

				OrganizationVO orgVO = (OrganizationVO) it.next();
				Long thisOrgUID = orgVO.getTheOrganizationDT()
						.getOrganizationUid();

				if (thisOrgUID != null && orgUID != null
						&& thisOrgUID.compareTo(orgUID) == 0) {

					helper = new QuickEntryEventHelper();
					returnObj = helper.makeORGDisplayString(orgVO);
				}
			}
		}

		return returnObj;
	}

	/**
	 * Extracts hospitalized property
	 * 
	 * @param name
	 *            the name of the property
	 * @return String
	 */
	public String extractHospitalizedAttr(String name) {

		String returnAttr = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String obsCd = obsVO.getTheObservationDT().getCd();

				if (obsCd != null && obsCd.equalsIgnoreCase(name)) {

					if (obsVO.getTheObsValueCodedDTCollection() != null
							&& obsVO.getTheObsValueCodedDTCollection().size() != 0) {

						for (Iterator<Object> it2 = obsVO
								.getTheObsValueCodedDTCollection().iterator(); it2
								.hasNext();) {

							ObsValueCodedDT dt = (ObsValueCodedDT) it2.next();
							returnAttr = dt.getCode(); //Assume only one
													   // ObsValueCodedDT for
													   // this observation
						}
					}
				}
			}
		}

		return returnAttr;
	}

	/**
	 * Extract condition property attributes
	 * 
	 * @param name
	 *            the name of the condition property
	 * @return String
	 */
	public String extractConditionAttr(String name) {

		String returnAttr = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String ccdf = obsVO.getTheObservationDT()
						.getCtrlCdDisplayForm();

				if (ccdf != null
						&& ccdf
								.equalsIgnoreCase(NEDSSConstants.MOB_CTRLCD_DISPLAY)) {

					if (name != null && name.equalsIgnoreCase("MRB121")) {
						returnAttr = obsVO.getTheObservationDT().getCd();

						break;
					} else if (name != null
							&& name
									.equalsIgnoreCase("MRB121Condition_Desc_txt")) {

						if (obsVO.getTheObsValueCodedDTCollection() != null
								&& obsVO.getTheObsValueCodedDTCollection()
										.size() != 0) {

							for (Iterator<Object> iter = obsVO
									.getTheObsValueCodedDTCollection()
									.iterator(); iter.hasNext();) {
								returnAttr = ((ObsValueCodedDT) iter.next())
										.getCodeSystemDescTxt();

								break;
							}
						}
					}
				}
			}
		}

		return returnAttr;
	}

	/**
	 * Extract onset date
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractOnsetDate(String cd) {

		String onsetDate = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb122 = obsVO.getTheObservationDT().getCd();

				if (mrb122 != null && mrb122.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueDateDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueDateDT dateDT = (ObsValueDateDT) it2.next();
						onsetDate = StringUtils
								.formatDate(dateDT.getFromTime());
					}

					break;
				}
			}
		}

		return onsetDate;
	}

	/**
	 * Extracts case status
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractCaseStatus(String cd) {

		String status = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb123 = obsVO.getTheObservationDT().getCd();

				if (mrb123 != null && mrb123.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueCodedDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueCodedDT codedDT = (ObsValueCodedDT) it2.next();
						status = codedDT.getCode();
					}

					break;
				}
			}
		}

		return status;
	}

	/**
	 * Extract patient die property
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractPatientDie(String cd) {

		String die = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb124 = obsVO.getTheObservationDT().getCd();

				if (mrb124 != null && mrb124.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueCodedDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueCodedDT codedDT = (ObsValueCodedDT) it2.next();
						die = codedDT.getCode();
					}

					break;
				}
			}
		}

		return die;
	}

	/**
	 * Extracts death date
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractDeadDate(String cd) {

		String deadDate = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb124 = obsVO.getTheObservationDT().getCd();

				if (mrb124 != null && mrb124.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueDateDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueDateDT date = (ObsValueDateDT) it2.next();
						deadDate = StringUtils.formatDate(date.getFromTime());
					}

					break;
				}
			}
		}

		return deadDate;
	}

	/**
	 * Extracts pregnant property
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractPregnant(String cd) {

		String pregnant = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb126 = obsVO.getTheObservationDT().getCd();

				if (mrb126 != null && mrb126.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueCodedDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueCodedDT coded = (ObsValueCodedDT) it2.next();
						pregnant = coded.getCode();
					}

					break;
				}
			}
		}

		return pregnant;
	}

	/**
	 * Extracts food handler property
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractFoodHandler(String cd) {

		String handler = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb127 = obsVO.getTheObservationDT().getCd();

				if (mrb127 != null && mrb127.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueCodedDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueCodedDT coded = (ObsValueCodedDT) it2.next();
						handler = coded.getCode();
					}

					break;
				}
			}
		}

		return handler;
	}

	/**
	 * Extracts day care property
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractDayCare(String cd) {

		String dayCare = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String mrb128 = obsVO.getTheObservationDT().getCd();

				if (mrb128 != null && mrb128.equalsIgnoreCase(cd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueCodedDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueCodedDT coded = (ObsValueCodedDT) it2.next();
						dayCare = coded.getCode();
					}

					break;
				}
			}
		}

		return dayCare;
	}

	private Long getUIDFromParticipation(String type, String typeCd,
			String classCd) {

		Long returnUID = null;

		if (morbProxyVO.getTheParticipationDTCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<Object> it = morbProxyVO.getTheParticipationDTCollection()
					.iterator(); it.hasNext();) {

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
	 * Extract Morb Dates
	 * 
	 * @param cd
	 *            the observation code
	 * @return String
	 */
	public String extractDate(String observationCd) {

		String returnDate = null;

		if (morbProxyVO.getTheObservationVOCollection() != null
				&& morbProxyVO.getTheObservationVOCollection().size() != 0) {

			for (Iterator<ObservationVO> it = morbProxyVO.getTheObservationVOCollection()
					.iterator(); it.hasNext();) {

				ObservationVO obsVO = (ObservationVO) it.next();
				String paramDate = obsVO.getTheObservationDT().getCd();

				if (paramDate != null
						&& paramDate.equalsIgnoreCase(observationCd)) {

					for (Iterator<Object> it2 = obsVO.getTheObsValueDateDTCollection()
							.iterator(); it2.hasNext();) {

						ObsValueDateDT dateDT = (ObsValueDateDT) it2.next();
						returnDate = StringUtils.formatDate(dateDT
								.getFromTime());
					}

					break;
				}
			}
		}

		return returnDate;
	}

} //end of class
