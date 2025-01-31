package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FieldMappingBuilder {

	static final LogUtils logger = new LogUtils(FieldMappingBuilder.class
			.getName());

	public static final String MORB_MAP = System.getProperty("nbs.dir")
			+ System.getProperty("file.separator") + "Properties"
			+ System.getProperty("file.separator") + "NBSMorbMap.xml";

	public static TreeMap<Object,Object> morbMap; //a tree map to be used for field mapping
								   // for morbility observation

	/**
	 * The main() for testing
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.setLogLevel(6);

		FieldMappingBuilder builder = new FieldMappingBuilder();
		logger.debug("MorbMap contains: " + morbMap.size());

		Object key = morbMap.firstKey();
		logger.debug("1st Key: " + key + " and its value: " + morbMap.get(key));
	}

	/**
	 * Creates a new FieldMappingBuilder object.
	 */
	public FieldMappingBuilder() {

		if (morbMap == null) {

			try {
				initMorbMap();
			} catch (Exception ex) {
				logger.fatal(
						"Error: unable to init field mapping map from file.",
						ex);
			}
		}
	}



	/**
	 * Initialize a morbility map from xml file
	 * 
	 * @throws Exception
	 */
	public static void initMorbMap() throws Exception {

		//Initialize the morb map
		Document doc;
		morbMap = new TreeMap<Object,Object>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(MORB_MAP);
		logger.debug("Done parsing!");

		//Get the root
		Element root = doc.getDocumentElement();

		//Get "NBSMorbMap" nodes
		NodeList morbMapNode = root.getChildNodes();

		//Get "InvestigationFormCodes" nodes
		for (int i = 0; i < morbMapNode.getLength(); i++) {

			Node formCodes = morbMapNode.item(i);

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
				if (formCode.getNodeType() == formCode.ELEMENT_NODE) {

					Element formCodeElement = (Element) formCode;

					if (formCodeElement.getAttribute("name") != null
							&& formCodeElement.getAttribute("name").trim()
									.equals("INV_FRM_GEN")) {

						NodeList fieldMappingList = formCode.getChildNodes();

						//Get "InvestigationFieldMapping" nodes
						for (int k = 0; k < fieldMappingList.getLength(); k++) {

							Node invFieldMapping = fieldMappingList.item(k);

							if (invFieldMapping.getNodeType() == invFieldMapping.ELEMENT_NODE) {

								Element invFieldMappingElement = (Element) invFieldMapping;
								morbMap
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
	}

	/**
	 * Create a map from observation for use in investigation later
	 * 
	 * @param morbProxyVO
	 *            the original Java proxy object
	 * @return TreeMap
	 */
	public TreeMap<Object,Object> createMorbidityLoadTreeMap(MorbidityProxyVO morbProxyVO, String processingDecision) {

		TreeMap<Object,Object> loadMap = new TreeMap<Object,Object>();
		MorbidityProxyVOExtractor extractor = new MorbidityProxyVOExtractor(
				morbProxyVO);
		ObservationVO obsVO= null;
		try {

			Set<Object> keys = morbMap.keySet();

			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {

				String key = (String) it.next();

				//Report Date
				if (key != null && key.trim().equalsIgnoreCase("MRB162")) {

					if(obsVO==null)
						obsVO= extractor.extractReportVO();
					if(obsVO.getTheObservationDT().getRptToStateTime()!=null){
						String reportDate = StringUtils.formatDate(obsVO.getTheObservationDT().getRptToStateTime());
						loadMap.put(morbMap.get(key), reportDate);
					}
					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("pregnantWeek")) {
					if(obsVO==null)
						obsVO= extractor.extractReportVO();
					if(obsVO.getTheObservationDT().getPregnantWeek()!=null){
						Integer pregnantWeeks = obsVO.getTheObservationDT().getPregnantWeek();
						loadMap.put(morbMap.get(key), pregnantWeeks);
					}

					continue;
				}

				//Reporting Source UID

				if (key != null && key.trim().equalsIgnoreCase("INV183UID")) {

					Object orgUID = extractor.extractRptOrgAttr("INV183UID");
					loadMap.put(morbMap.get(key), ((orgUID == null) ? null
							: orgUID.toString().trim()));

					continue;
				}
				//Reporting Source Details
				if (key != null && key.trim().equalsIgnoreCase("INV183ORG")) {

					Object reportingSrcDetails = extractor
							.extractReportingSourceDetails();
					loadMap.put(morbMap.get(key),
							((reportingSrcDetails == null) ? null
									: reportingSrcDetails.toString().trim()));

					continue;
				}

				//Physician
				if (key != null && key.trim().equalsIgnoreCase("INV182UID")) {

					Object personUID = extractor
							.extractPhysicianAttr("INV182UID");
					loadMap.put(morbMap.get(key), (personUID == null) ? null
							: personUID.toString().trim());

					continue;
				}

				//Physician Details
				if (key != null && key.trim().equalsIgnoreCase("INV182PRV")) {

					Object physicianDetails = extractor
							.extractPhysicianDetails();
					loadMap.put(morbMap.get(key),
							((physicianDetails == null) ? null
									: physicianDetails.toString().trim()));

					continue;
				}

				//Reporter
				if (key != null && key.trim().equalsIgnoreCase("INV181UID")) {

					Object reporterUID = extractor
							.extractReporterAttr("INV181UID");
					loadMap.put(morbMap.get(key), ((reporterUID == null) ? null
							: reporterUID.toString().trim()));

					continue;
				}

				//Reporter Details
				if (key != null && key.trim().equalsIgnoreCase("INV181PRV")) {

					Object reporterDetails = extractor.extractReporterDetails();
					loadMap.put(morbMap.get(key),
							((reporterDetails == null) ? null : reporterDetails
									.toString().trim()));

					continue;
				}

				//Hospital
				if (key != null && key.trim().equalsIgnoreCase("INV184UID")) {

					Object hospitalUID = extractor
							.extractHospitalAttr("INV184UID");
					loadMap.put(morbMap.get(key), (hospitalUID == null) ? null
							: hospitalUID.toString().trim());

					continue;
				}

				//Hospital Details
				if (key != null && key.trim().equalsIgnoreCase("INV184ORG")) {

					Object hospitalDetails = extractor.extractHospitalDetails();
					loadMap.put(morbMap.get(key),
							((hospitalDetails == null) ? null : hospitalDetails
									.toString().trim()));

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("INV128")) {

					String code = extractor.extractHospitalizedAttr("INV128");
					loadMap.put(morbMap.get(key), code);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("MRB121")) {

					String condition = extractor.extractConditionAttr("MRB121");
					loadMap.put(morbMap.get(key), condition);

					continue;
				}

				if (key != null
						&& key.trim().equalsIgnoreCase(
								"MRB121Condition_Desc_txt")) {

					String conditionTxt = extractor
							.extractConditionAttr("MRB121Condition_Desc_txt");
					loadMap.put(morbMap.get(key), conditionTxt);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("MRB122")) {

					String onsetDate = extractor.extractOnsetDate("MRB122");
					loadMap.put(morbMap.get(key), onsetDate);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("MRB123")) {

					String caseStatus = extractor.extractCaseStatus("MRB123");
					loadMap.put(morbMap.get(key), caseStatus);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("INV145")) {

					String patientDie = extractor.extractPatientDie("INV145");
					loadMap.put(morbMap.get(key), patientDie);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("MRB125")) {

					String deadDate = extractor.extractDeadDate("MRB124");
					loadMap.put(morbMap.get(key), deadDate);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("INV178")) {

					String pregnant = extractor.extractPregnant("INV178");
					loadMap.put(morbMap.get(key), pregnant);

					continue;
				}

				
				if (key != null && key.trim().equalsIgnoreCase("INV149")) {

					String foodHandler = extractor.extractFoodHandler("INV149");
					loadMap.put(morbMap.get(key), foodHandler);

					continue;
				}

				if (key != null && key.trim().equalsIgnoreCase("INV148")) {

					String dayCare = extractor.extractDayCare("INV148");
					loadMap.put(morbMap.get(key), dayCare);

					continue;
				}

				//New entries for 1.1 Rel

				//Hospital Admission Date
				if (key != null && key.trim().equalsIgnoreCase("MRB166")) {

					String admissionDate = extractor.extractDate("MRB166");
					loadMap.put(morbMap.get(key), admissionDate);

					continue;
				}

				//Hospital Discharge Date
				if (key != null && key.trim().equalsIgnoreCase("MRB167")) {

					String dischargeDate = extractor.extractDate("MRB167");
					loadMap.put(morbMap.get(key), dischargeDate);

					continue;
				}

				//Date of Diagnosis
				if (key != null && key.trim().equalsIgnoreCase("MRB165")) {

					String diagnosisDate = extractor.extractDate("MRB165");
					loadMap.put(morbMap.get(key), diagnosisDate);

					continue;
				}
				// Processing Decision
				if (key != null && key.trim().equalsIgnoreCase(NEDSSConstants.PROCESSING_DECISION)) {

					loadMap.put(morbMap.get(key), processingDecision);

					continue;
				}
			}
		} catch (Exception ex) {
			logger.error("Error in creating morbidity load tree map", ex);
		}

		return loadMap;
	}
} //end of class
