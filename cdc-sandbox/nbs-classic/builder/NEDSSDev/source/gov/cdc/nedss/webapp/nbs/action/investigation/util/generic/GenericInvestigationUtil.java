package gov.cdc.nedss.webapp.nbs.action.investigation.util.generic;

/**
 * Title: CoreDemographic Description: this class retrieves data from EJB and
 * puts them into request object for use in the xml file Copyright: Copyright
 * (c) 2001 Company: CSC
 * 
 * @author Jay Kim
 * @version 1.0
 */

import java.util.*;

import javax.servlet.http.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;

public class GenericInvestigationUtil extends CommonInvestigationUtil {

	static final LogUtils logger = new LogUtils(GenericInvestigationUtil.class
			.getName());
	public static CachedDropDownValues cdv = new CachedDropDownValues();

	public GenericInvestigationUtil() {
	}

	public void setGenericRequestForView(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) throws NEDSSAppException{
		this.displayRevisionPatient(investigationProxyVO, request);
		this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
		this.convertPersonsToRequest(investigationProxyVO, request);
		this.convertOrganizationToRequest(investigationProxyVO, request);
		this.convertObservationsToRequest(investigationProxyVO, request);
		this.convertNotificationSummaryToRequest(investigationProxyVO, request);
		this.convertObservationSummaryToRequest(investigationProxyVO, request);
		this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
		//this.convertTreatmentSummaryToRequest(investigationProxyVO, request);
		this.convertTreatmentSummaryAndMorbToRequest(investigationProxyVO,
				request);
		this.convertDocumentSummaryToRequest(investigationProxyVO, request);

	}

	private String getCountiesByState(String stateCd) {
		StringBuffer parsedCodes = new StringBuffer("");
		if (stateCd != null) {
			//SRTValues srtValues = new SRTValues();
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap treemap = null;

			treemap = srtValues.getCountyCodes(stateCd);

			if (treemap != null) {
				Set set = treemap.keySet();
				Iterator itr = set.iterator();
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

	public void setObservationConstantsForCreate(
			InvestigationProxyVO investigationProxyVO) {
		//set some values to observations
		Collection<ObservationVO>  observationVOCollection  = investigationProxyVO
				.getTheObservationVOCollection();
		logger
				.debug("convertProxyToRequestObj() before observationVOCollection  ");
		if (observationVOCollection  != null) {

			Iterator anIterator1 = null;
			Iterator anIterator2 = null;
			Iterator anIterator3 = null;
			logger
					.debug("convertProxyToRequestObj() observationVOCollection  size: "
							+ observationVOCollection.size());
			for (anIterator1 = observationVOCollection.iterator(); anIterator1
					.hasNext();) {
				ObservationVO observationVO = (ObservationVO) anIterator1
						.next();
				String obsCode = observationVO.getTheObservationDT().getCd();
				if (obsCode != null && obsCode.equals("MEA004")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("CLN");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA021")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("CLN");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}

				if (obsCode != null && obsCode.equals("MEA027")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("LAB");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA033")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("LAB");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA038")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("LAB");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA045")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("VAC");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA057")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("EPI");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA058")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("EPI");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA060")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("EPI");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT
									.setCodeSystemDescTxt(NEDSSConstants.YNU);
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA067")) {
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT
									.setCodeSystemDescTxt(NEDSSConstants.YNU);
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
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
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT
									.setCodeSystemDescTxt(NEDSSConstants.YNU);
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("INV128")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("CLN");
					Collection<Object>  obsValueDateDTCollection  = observationVO
							.getTheObsValueDateDTCollection();
					if (obsValueDateDTCollection  != null) {
						anIterator3 = obsValueDateDTCollection.iterator();
						if (anIterator3.hasNext()) {
							ObsValueDateDT obsValueDateDT = ((ObsValueDateDT) anIterator3
									.next());
							obsValueDateDT.setDurationUnitCd("D");
						}
					}
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT
									.setCodeSystemDescTxt(NEDSSConstants.YNU);
						}
					}
					continue;
				}
				if (obsCode != null && obsCode.equals("MEA001")) {

					observationVO.getTheObservationDT().setCdSystemCd("NBS");
					observationVO.getTheObservationDT().setObsDomainCd("CLN");
					Collection<Object>  obsValueCodedDTCollection  = observationVO
							.getTheObsValueCodedDTCollection();
					if (obsValueCodedDTCollection  != null) {
						anIterator2 = obsValueCodedDTCollection.iterator();
						if (anIterator2.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) anIterator2
									.next());
							obsValueCodedDT
									.setCodeSystemDescTxt(NEDSSConstants.YNU);
							obsValueCodedDT.setCodeSystemCd("NBS");
							obsValueCodedDT.setCodeVersion("1");
						}
					}
				}
			}
		}
	} //setObsevationCodesForCreate

	public void convertObservationsToRequest(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		Collection<ObservationVO>  obsColl = investigationProxyVO
				.getTheObservationVOCollection();
		if (obsColl != null) {
			Iterator<ObservationVO> iter = obsColl.iterator();
			while (iter.hasNext()) {
				ObservationVO obsVO = (ObservationVO) iter.next();

				String obsCode = obsVO.getTheObservationDT().getCd();
				if (obsCode != null) {
					//get the coded values
					Collection<Object>  codedColl = obsVO
							.getTheObsValueCodedDTCollection();
					if (codedColl != null) {
						Iterator<Object> codedIter = codedColl.iterator();
						if (codedIter.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT) codedIter
									.next());
							request.setAttribute(obsCode, obsValueCodedDT
									.getCode());
							request.setAttribute(obsCode + "-originalTxt",
									obsValueCodedDT.getOriginalTxt());

							if (obsCode.equals("INV154")) {
								String code = obsValueCodedDT.getCode();
								request.setAttribute("county", this
										.getCountiesByState(code));
							}
						}
					}
					//get date
					Collection<Object>  dateColl = obsVO
							.getTheObsValueDateDTCollection();
					if (dateColl != null) {
						Iterator<Object> dateIter = dateColl.iterator();
						if (dateIter.hasNext()) {
							ObsValueDateDT obsDateCodedDT = ((ObsValueDateDT) dateIter
									.next());
							request.setAttribute(obsCode + "-fromTime",
									formatDate(obsDateCodedDT.getFromTime()));
							request.setAttribute(obsCode + "-toTime",
									formatDate(obsDateCodedDT.getToTime()));
							request
									.setAttribute(
											obsCode + "-durationAmt",
											(obsDateCodedDT.getDurationAmt() == null ? ""
													: obsDateCodedDT
															.getDurationAmt()));
							request
									.setAttribute(
											obsCode + "-durationUnit",
											(obsDateCodedDT.getDurationUnitCd() == null ? ""
													: obsDateCodedDT
															.getDurationUnitCd()));
						}
					}
					//get text
					Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
					if (textColl != null) {
						Iterator<Object> textIter = textColl.iterator();
						if (textIter.hasNext()) {
							ObsValueTxtDT obsTextCodedDT = ((ObsValueTxtDT) textIter
									.next());
							request.setAttribute(obsCode + "-valueTxt",
									(obsTextCodedDT.getValueTxt() == null ? ""
											: obsTextCodedDT.getValueTxt()));
						}
					}
					// get numeric
					Collection<Object>  numColl = obsVO
							.getTheObsValueNumericDTCollection();
					if (numColl != null) {
						Iterator<Object> numIter = numColl.iterator();
						if (numIter.hasNext()) {
							ObsValueNumericDT obsNumCodedDT = ((ObsValueNumericDT) numIter
									.next());
							request
									.setAttribute(
											obsCode + "-numericValue1",
											(obsNumCodedDT.getNumericValue1() == null ? ""
													: String
															.valueOf(obsNumCodedDT
																	.getNumericValue1()
																	.intValue())));
							request
									.setAttribute(
											obsCode + "-numericUnitCd",
											(obsNumCodedDT.getNumericUnitCd() == null ? ""
													: String
															.valueOf(obsNumCodedDT
																	.getNumericUnitCd())));
						}
					}

				}
			}
		}

	}

	public void setDefaultValuesToRequestForCreate(TreeMap<Object, Object> DSMorbMap,
			HttpServletRequest request) {
		String INV111 = (String) DSMorbMap.get("INV111");

		//Reporting Source
		String INV183UID = (String) DSMorbMap.get("INV183UID");
		String INV183ORG = (String) DSMorbMap.get("INV183ORG");

		//Physician
		String INV182UID = (String) DSMorbMap.get("INV182UID");
		String INV182PRV = (String) DSMorbMap.get("INV182PRV");

		//Reporter
		String INV181UID = (String) DSMorbMap.get("INV181UID");
		String INV181PRV = (String) DSMorbMap.get("INV181PRV");

		//Hospital
		String INV184UID = (String) DSMorbMap.get("INV184UID");
		String INV184ORG = (String) DSMorbMap.get("INV184ORG");

		String INV128 = (String) DSMorbMap.get("INV128");

		String PHC108 = (String) DSMorbMap.get("PHC108");
		String INV137 = (String) DSMorbMap.get("INV137");
		String INV163 = (String) DSMorbMap.get("INV163");
		String INV145 = (String) DSMorbMap.get("INV145");
		String INV146 = (String) DSMorbMap.get("INV146");
		String INV178 = (String) DSMorbMap.get("INV178");
		String INV149 = (String) DSMorbMap.get("INV149");
		String INV148 = (String) DSMorbMap.get("INV148");
		String INV114 = (String) DSMorbMap.get("INV114");

		//new for Rel 1.1
		String INV132 = (String) DSMorbMap.get("INV132");
		String INV133 = (String) DSMorbMap.get("INV133");
		String INV136 = (String) DSMorbMap.get("INV136");

		request.setAttribute("dateOfReport", INV111);

		//Reporting Source Information
		request.setAttribute("reportingOrgUID", INV183UID);
		request.setAttribute("reportingOrgDemographics", INV183ORG);

		//Physician Information
		request.setAttribute("physicianPersonUid", INV182UID);
		request.setAttribute("physicianDemographics", INV182PRV);

		//Reporter Information
		request.setAttribute("reporterPersonUid", INV181UID);
		request.setAttribute("reporterDemographics", INV181PRV);

		request.setAttribute("INV128", INV128);

		//Hospital Information
		request.setAttribute("hospitalOrgUID", INV184UID);
		request.setAttribute("hospitalDemographics", INV184ORG);

		request.setAttribute("illnessOnsetDate", INV137);
		request.setAttribute("caseStatus", INV163);
		request.setAttribute("didThePatientDie", INV145);
		request.setAttribute("dateOfDeath", INV146);
		request.setAttribute("INV178", INV178);
		request.setAttribute("INV148", INV148);
		request.setAttribute("INV149", INV149);
		request.setAttribute("reportingSourceName", INV114);

		//new for Rel 1.1
		request.setAttribute("INV132-fromTime", INV132);
		request.setAttribute("INV133-fromTime", INV133);
		request.setAttribute("diagnosisDate", INV136);

		//   logger.info( "key: " + (String)itor.next() + " value: " +
		// DSMorbMap.get((String)itor.next()));

	}

	protected void convertVaccinationSummaryToRequestGeneric(
			InvestigationProxyVO investigationProxyVO,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Collection<Object>  vaccinationSummaryVOCollection  = investigationProxyVO
				.getTheVaccinationSummaryVOCollection();
		if (vaccinationSummaryVOCollection  == null) {
			logger.debug("vaccinationSummaryVOCollection  arraylist is null");
		} else {
			logger.debug("vaccinationSummaryVOCollection  size is "
					+ vaccinationSummaryVOCollection.size());
			session.setAttribute("vaccinationSummaryList",
					vaccinationSummaryVOCollection);
		}

	}

	/**
	 *  
	 */
	public PersonVO getMPRevision(String type_cd, InvestigationProxyVO proxy) {
		logger.debug("Got into the persoVO finder");
		Collection<Object>  participationDTCollection  = null;
		Collection<Object>  personVOCollection  = null;
		ParticipationDT participationDT = null;
		PersonVO personVO = null;
		PublicHealthCaseVO phcVO = proxy.getPublicHealthCaseVO();
		participationDTCollection  = phcVO.getTheParticipationDTCollection();
		personVOCollection  = proxy.getThePersonVOCollection();
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
						//System.out.println("Out side if loop
						// :"+personVO.getThePersonDT().getPersonUid() +"parent
						// Uid is
						// :"+personVO.getThePersonDT().getPersonParentUid());
						if (personVO.getThePersonDT().getPersonUid()
								.longValue() == participationDT
								.getSubjectEntityUid().longValue()) {

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
	} //getMPRevision

	/**
	 *  
	 */
	public void displayRevisionPatient(InvestigationProxyVO proxy,
			HttpServletRequest request) throws NEDSSAppException{

		PersonVO revisionVO = this.getMPRevision(NEDSSConstants.PHC_PATIENT,
				proxy);

		ArrayList<Object> stateList = new ArrayList<Object> ();
		PersonUtil.convertPersonToRequestObj(revisionVO, request,
				"AddPatientFromEvent", stateList);

	} //displayRevisionPatient
	
	 public static  String getConditionTracingEnableInd(String conditonCd) {

			String enableContactTab ="";	

			try {				
				TreeMap<Object,Object>  conditionList = cdv.getConditionTracingEnableInd();
				if (conditionList != null){
					  if(conditonCd!=null && conditionList.get(conditonCd)!=null)
						  enableContactTab = (String)conditionList.get(conditonCd);					    
					}

				} catch (Exception ex) {
				logger.fatal("getProxy: ", ex);
			}

			return enableContactTab;
		}

}