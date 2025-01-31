package gov.cdc.nedss.webapp.nbs.action.observation.common;

import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LabReportFieldMappingBuilder {

	static final LogUtils logger = new LogUtils(
			LabReportFieldMappingBuilder.class.getName());

	public static final String LAB_MAP = System.getProperty("nbs.dir")
			+ System.getProperty("file.separator") + "Properties"
			+ System.getProperty("file.separator") + "NBSLabMap.xml";

	public static TreeMap<Object,Object> labMap; //a tree map to be used for field mapping
								   // for Lab observation

	/**
	 * The main() for testing
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		LogUtils.setLogLevel(6);

		LabReportFieldMappingBuilder builder = new LabReportFieldMappingBuilder();
		logger.debug("LabMap contains: " + labMap.size());

		Object key = labMap.firstKey();
		logger.debug("1st Key: " + key + " and its value: " + labMap.get(key));
	}

	/**
	 * Creates a new FieldMappingBuilder object.
	 */
	public LabReportFieldMappingBuilder() {

		if (labMap == null) {

			try {
				initLabMap();
			} catch (Exception ex) {
				logger.fatal(
						"Error: unable to init field mapping map from file.",
						ex);
			}
		}
	}

	//Get the staging Lab map

	/*
	 * public static TreeMap<Object,Object> getLabMap() { return LabMap; }
	 */

	/**
	 * Initialize a Lab map from xml file
	 * 
	 * @throws Exception
	 */
	public static void initLabMap() throws Exception {

		//Initialize the Lab map
		Document doc;
		labMap = new TreeMap<Object,Object>();
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(LAB_MAP);
		logger.debug("Done parsing!");

		//Get the root
		Element root = doc.getDocumentElement();

		//Get "NBSLabMap" nodes
		NodeList LabMapNode = root.getChildNodes();

		//Get "InvestigationFormCodes" nodes
		for (int i = 0; i < LabMapNode.getLength(); i++) {

			Node formCodes = LabMapNode.item(i);

			//Check if this node has any child; go to the next node if no child
			if (formCodes.getFirstChild() == null) {

				continue;
			}

			NodeList formCodeList = formCodes.getChildNodes();

			//Get "InvestigationFormCode" nodes
			for (int j = 0; j < formCodeList.getLength(); j++) {

				Node formCode = formCodeList.item(j);

				//Check if this node has any child; go to the next node if no
				// child
				if (formCode.getFirstChild() == null) {

					continue;
				}

				//Check if the node is of element type; then cast the node to
				// element type
				if (formCode.getNodeType() == Node.ELEMENT_NODE) {

					Element formCodeElement = (Element) formCode;

					if (formCodeElement.getAttribute("name") != null
							&& formCodeElement.getAttribute("name").trim()
									.equals("INV_FRM_GEN")) {

						NodeList fieldMappingList = formCode.getChildNodes();

						//Get "InvestigationFieldMapping" nodes
						for (int k = 0; k < fieldMappingList.getLength(); k++) {

							Node invFieldMapping = fieldMappingList.item(k);

							if (invFieldMapping.getNodeType() == Node.ELEMENT_NODE) {

								Element invFieldMappingElement = (Element) invFieldMapping;
								labMap
										.put(
												invFieldMappingElement
														.getAttribute("mappingFromField"),
												invFieldMappingElement
														.getAttribute("mappingToField"));
							}
						}
					}
				}
			}
		}
		} catch (Exception ex) {
			logger.error("Error in LabReportFieldMappingBuilder.initLabMap: "+ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Create a map from observation for use in investigation later
	 * 
	 * @param LabProxyVO
	 *            the original Java proxy object
	 * @return TreeMap
	 */
	@SuppressWarnings("unchecked")
	public TreeMap<Object, Object> createLabReportLoadTreeMap(LabResultProxyVO labResultProxy, Long labUid,
			String processingDecision) {

		TreeMap<Object, Object> loadMap = new TreeMap<Object, Object>();
		QuestionsCache.fillPrePopMap();
		TreeMap<Object, Object> fromPrePopMap = (TreeMap<Object, Object>) QuestionsCache.fromPrePopFormMapping
				.get(NEDSSConstants.LAB_FORM_CD);
		LabReportProxyVOExtractor extractor = new LabReportProxyVOExtractor(labResultProxy, labUid);
		Collection<ObservationVO> obsCollection = labResultProxy.getTheObservationVOCollection();
		TreeMap<Object, Object> prePopMap = new TreeMap<Object, Object>();

		ObservationVO obsVO = null;
		try {

			// Begin Dynamic Pre-pop mapping

			Iterator<ObservationVO> ite = obsCollection.iterator();
			while (ite.hasNext()) {
				ObservationVO obs = ite.next();
				if (obs.getTheObsValueNumericDTCollection() != null
						&& obs.getTheObsValueNumericDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					String value = obs.getObsValueNumericDT_s(0).getNumericUnitCd() == null
							? obs.getObsValueNumericDT_s(0).getNumericValue1() + ""
							: obs.getObsValueNumericDT_s(0).getNumericValue1() + "^"
									+ obs.getObsValueNumericDT_s(0).getNumericUnitCd();
					prePopMap.put(obs.getTheObservationDT().getCd(), value);
				} else if (obs.getTheObsValueDateDTCollection() != null
						&& obs.getTheObsValueDateDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					String value = StringUtils.formatDate(obs.getObsValueDateDT_s(0).getFromTime());
					prePopMap.put(obs.getTheObservationDT().getCd(), value);
				}  else if (obs.getTheObsValueCodedDTCollection() != null
						&& obs.getTheObsValueCodedDTCollection().size() > 0) {
					String key = obs.getTheObservationDT().getCd() + "$" + obs.getObsValueCodedDT_s(0).getCode();
					if (fromPrePopMap.containsKey(key))
						prePopMap.put(key, obs.getObsValueCodedDT_s(0).getCode());
					else if(fromPrePopMap.containsKey(obs.getTheObservationDT().getCd()))
						prePopMap.put(obs.getTheObservationDT().getCd(), obs.getObsValueCodedDT_s(0).getCode());
				}
				else if (obs.getTheObsValueTxtDTCollection() != null && obs.getTheObsValueTxtDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					Iterator<Object> txtIte = obs.getTheObsValueTxtDTCollection().iterator();
					while (txtIte.hasNext()) {
						ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) txtIte.next();
						if (obsValueTxtDT.getTxtTypeCd() == null || obsValueTxtDT.getTxtTypeCd().trim().equals("")
								|| obsValueTxtDT.getTxtTypeCd().equalsIgnoreCase("O")) {
							prePopMap.put(obs.getTheObservationDT().getCd(), obsValueTxtDT.getValueTxt());
							break;
						}
					}
				}
			}
			loadMap.put(NEDSSConstants.LAB_FORM_CD, prePopMap);

			// End Dynamic Pre-pop mapping

			Set<Object> keys = labMap.keySet();

			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {

				String key = (String) it.next();

				if (key != null && key.trim().equalsIgnoreCase("LAB201")) {
					String reportDate = null;
					obsVO = extractor.extractReportDate();

					if (obsVO != null) {
						reportDate = StringUtils.formatDate(obsVO.getTheObservationDT().getRptToStateTime());
						loadMap.put(labMap.get(key), reportDate);
					}

					continue;
				}

				// Reporting Source UID
				else if (key != null && key.trim().equalsIgnoreCase("ORD106UID")) {

					Object orgUID = extractor.extractRptOrgAttr("ORD106UID");
					loadMap.put(labMap.get(key), ((orgUID == null) ? null : orgUID.toString().trim()));

					continue;
				}
				// Ordering Facility UID
				else if (key != null && key.trim().equalsIgnoreCase("ORD107UID")) {

					Object orgUID = extractor.extractRptOrgAttr("ORD107UID");
					loadMap.put(labMap.get(key), ((orgUID == null) ? null : orgUID.toString().trim()));

					continue;
				}
				// Reporting Source Details
				else if (key != null && key.trim().equalsIgnoreCase("ORD106ORG")) {

					Object reportingSrcDetails = extractor.extractReportingSourceDetails();
					loadMap.put(labMap.get(key),
							((reportingSrcDetails == null) ? null : reportingSrcDetails.toString().trim()));

					continue;
				} else if (key != null && key.trim().equalsIgnoreCase("ORD107ORG")) {

					Object orderingFacilityDetails = extractor.extractOrderingFacilityDetails();
					loadMap.put(labMap.get(key),
							((orderingFacilityDetails == null) ? null : orderingFacilityDetails.toString().trim()));

					continue;
				}
				// Physician UID
				else if (key != null && key.trim().equalsIgnoreCase("NPP001UID")) {

					Object personUID = extractor.extractPhysicianAttr("NPP001UID");
					loadMap.put(labMap.get(key), (personUID == null) ? null : personUID.toString().trim());

					continue;
				}
				// Physician Details
				else if (key != null && key.trim().equalsIgnoreCase("NPP001PRV")) {

					Object physicianDetails = extractor.extractPhysicianDetails();
					loadMap.put(labMap.get(key),
							((physicianDetails == null) ? null : physicianDetails.toString().trim()));

					continue;
				}
				// Processing Decision
				else if (key != null && key.trim().equalsIgnoreCase(NEDSSConstants.PROCESSING_DECISION)) {

					loadMap.put(labMap.get(key), ((processingDecision == null) ? null : processingDecision));

					continue;
				}
				// Assesses whether or not the patient is pregnant.
				else if (key != null && key.trim().equalsIgnoreCase(NEDSSConstants.PREGNANT_IND_CD)) {
					if (obsVO != null) {
						Object pregnantIndCd = obsVO.getTheObservationDT().getPregnantIndCd();
						loadMap.put(labMap.get(key), ((pregnantIndCd == null) ? null : pregnantIndCd));
					}
				}
				// Processing Decision
				else if (key != null && key.trim().equalsIgnoreCase(NEDSSConstants.PREGNANT_WEEKS)) {
					if (obsVO != null) {
						Object pregnantWeeks = obsVO.getTheObservationDT().getPregnantWeek();
						loadMap.put(labMap.get(key), ((pregnantWeeks == null) ? null : pregnantWeeks));
					}
				}

			}
		} catch (Exception ex) {
			logger.error("Error in creating Lab load tree map", ex);
			ex.printStackTrace();
		}

		return loadMap;
	}
} //end of class
