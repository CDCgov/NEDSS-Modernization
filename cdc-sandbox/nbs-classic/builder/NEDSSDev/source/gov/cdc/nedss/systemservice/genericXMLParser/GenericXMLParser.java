package gov.cdc.nedss.systemservice.genericXMLParser;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class GenericXMLParser extends DAOBase {
	final String TAG_SEPARATOR = "/";
	final String TABLE_SEPARATOR = ".";
	final String ATTRIBUTE_SEPARATOR = "@";
	final String TOP1="#1";
	final String MULTI="#MULTI";
	final String DEFAULT_VALUE_SEPERATOR="$";
	final String OVERRIDE_FALSE="[override=false]";
	final String DEPENDENCY = "|dependency=";
	final String EVENT_ID_CONDITION = "MSG_CASE_INVESTIGATION.INV_CONDITION_CD^1";	
	static final LogUtils logger = new LogUtils(ReadXMLFile.class.getName());

	/**
	 * Inserts on tables
	 */
	final String MSG_CONTAINER_QUERY = "INSERT INTO MSG_CONTAINER(MSG_CONTAINER_UID,NBS_INTERFACE_UID,ADD_REASON_CD,AUTHOR_ID,CD, CONFIDENTIALITY_CD, CUSTODIAN_ID,DOCUMENT_ID,DOC_TYPE_CD,EFFECTIVE_TIME,MSG_LOCAL_ID,RECORD_STATUS_CD"
			+ ",RECORD_STATUS_TIME,RECEIVING_SYSTEM,SENDING_SYSTEM_CD,VERSION_CTRL_NBR,DATA_MIGRATION_STATUS,ONGOING_CASE)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_PATIENT_QUERY = "INSERT INTO MSG_PATIENT (MSG_CONTAINER_UID, PAT_LOCAL_ID, PAT_AUTHOR_ID, PAT_ADDR_AS_OF_DT, PAT_ADDR_CITY_TXT, PAT_ADDR_COMMENT_TXT, PAT_ADDR_COUNTY_CD, PAT_ADDR_COUNTRY_CD, PAT_ADDITIONAL_GENDER_TXT"
			+ ",PAT_ADDR_CENSUS_TRACT_TXT, PAT_ADDR_STATE_CD, PAT_ADDR_STREET_ADDR1_TXT, PAT_ADDR_STREET_ADDR2_TXT, PAT_ADDR_ZIP_CODE_TXT, PAT_BIRTH_COUNTRY_CD, PAT_BIRTH_DT, PAT_BIRTH_SEX_CD, PAT_CELL_PHONE_NBR_TXT, PAT_COMMENT_TXT, PAT_CURRENT_SEX_CD"
			+ ",PAT_DECEASED_IND_CD, PAT_DECEASED_DT, PAT_EFFECTIVE_TIME, PAT_ID_MEDICAL_RECORD_NBR_TXT, PAT_ID_STATE_HIV_CASE_NBR_TXT, PAT_INFO_AS_OF_DT, PAT_ID_SSN_TXT, PAT_EMAIL_ADDRESS_TXT, PAT_ETHNIC_GROUP_IND_CD, PAT_ETHNICITY_UNK_REASON_CD"
			+ ",PAT_HOME_PHONE_NBR_TXT, PAT_MARITAL_STATUS_CD, PAT_NAME_ALIAS_TXT, PAT_NAME_AS_OF_DT, PAT_NAME_DEGREE_CD, PAT_NAME_FIRST_TXT, PAT_NAME_LAST_TXT, PAT_NAME_MIDDLE_TXT, PAT_NAME_PREFIX_CD, PAT_NAME_SUFFIX_CD, PAT_OCCUPATION_CD, PAT_PHONE_COMMENT_TXT"
			+ ",PAT_PHONE_COUNTRY_CODE_TXT, PAT_PRIMARY_LANGUAGE_CD, PAT_PREFERRED_GENDER_CD, PAT_RACE_CATEGORY_CD, PAT_RACE_DESC_TXT, PAT_REPORTED_AGE, PAT_REPORTED_AGE_UNIT_CD, PAT_SEX_UNK_REASON_CD, PAT_SPEAKS_ENGLISH_IND_CD, PAT_PHONE_AS_OF_DT"
			+ ",PAT_URL_ADDRESS_TXT, PAT_WORK_PHONE_NBR_TXT, PAT_WORK_PHONE_EXTENSION_TXT) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_CASE_INVESTIGATION_QUERY = "INSERT INTO MSG_CASE_INVESTIGATION(MSG_CONTAINER_UID, INV_LOCAL_ID, PAT_LOCAL_ID,INV_AUTHOR_ID, INV_CASE_STATUS_CD, INV_CLOSE_DT, INV_COINFECTION_ID, INV_COMMENT_TXT, INV_CONDITION_CD, INV_CONTACT_INV_COMMENT_TXT"
			+ ", INV_CONTACT_INV_PRIORITY_CD, INV_CONTACT_INV_STATUS_CD, INV_CURR_PROCESS_STATE_CD, INV_DAYCARE_IND_CD, INV_DETECTION_METHOD_CD, INV_DIAGNOSIS_DT, INV_DISEASE_ACQUIRED_LOC_CD, INV_EFFECTIVE_TIME, INV_FOODHANDLER_IND_CD"
			+ ", INV_HOSPITALIZED_ADMIT_DT, INV_HOSPITALIZED_DISCHARGE_DT, INV_HOSPITALIZED_IND_CD, INV_HOSP_STAY_DURATION, INV_ILLNESS_START_DT, INV_ILLNESS_END_DT, INV_ILLNESS_DURATION, INV_ILLNESS_DURATION_UNIT_CD, INV_ILLNESS_ONSET_AGE"
			+ ", INV_ILLNESS_ONSET_AGE_UNIT_CD, INV_INVESTIGATOR_ASSIGNED_DT, INV_IMPORT_CITY_TXT, INV_IMPORT_COUNTY_CD, INV_IMPORT_COUNTRY_CD, INV_IMPORT_STATE_CD, INV_INFECTIOUS_FROM_DT, INV_INFECTIOUS_TO_DT, INV_LEGACY_CASE_ID"
			+ ", INV_MMWR_WEEK_TXT, INV_MMWR_YEAR_TXT, INV_OUTBREAK_IND_CD, INV_OUTBREAK_NAME_CD, INV_PATIENT_DEATH_DT, INV_PATIENT_DEATH_IND_CD, INV_PREGNANCY_IND_CD, INV_REFERRAL_BASIS_CD, INV_REPORT_DT, INV_REPORT_TO_COUNTY_DT"
			+ ", INV_REPORT_TO_STATE_DT, INV_REPORTING_COUNTY_CD, INV_SHARED_IND_CD, INV_SOURCE_TYPE_CD, INV_START_DT, INV_STATE_ID, INV_STATUS_CD, INV_TRANSMISSION_MODE_CD) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_PROVIDER_QUERY = "INSERT INTO MSG_PROVIDER (MSG_CONTAINER_UID,PRV_LOCAL_ID, PRV_AUTHOR_ID, PRV_ADDR_CITY_TXT, PRV_ADDR_COMMENT_TXT, PRV_ADDR_COUNTY_CD, PRV_ADDR_COUNTRY_CD, PRV_ADDR_STREET_ADDR1_TXT, PRV_ADDR_STREET_ADDR2_TXT, PRV_ADDR_STATE_CD"
			+ ", PRV_ADDR_ZIP_CODE_TXT, PRV_COMMENT_TXT, PRV_ID_ALT_ID_NBR_TXT, PRV_ID_QUICK_CODE_TXT, PRV_ID_NBR_TXT, PRV_ID_NPI_TXT, PRV_EFFECTIVE_TIME, PRV_EMAIL_ADDRESS_TXT, PRV_NAME_DEGREE_CD, PRV_NAME_FIRST_TXT"
			+ ", PRV_NAME_LAST_TXT, PRV_NAME_MIDDLE_TXT, PRV_NAME_PREFIX_CD, PRV_NAME_SUFFIX_CD, PRV_PHONE_COMMENT_TXT, PRV_PHONE_COUNTRY_CODE_TXT, PRV_PHONE_EXTENSION_TXT, PRV_PHONE_NBR_TXT, PRV_ROLE_CD, PRV_URL_ADDRESS_TXT)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_ORGANIZATION_QUERY = "INSERT INTO MSG_ORGANIZATION(MSG_CONTAINER_UID, ORG_LOCAL_ID, ORG_AUTHOR_ID, ORG_EFFECTIVE_TIME, ORG_NAME_TXT, ORG_ADDR_CITY_TXT, ORG_ADDR_COMMENT_TXT, ORG_ADDR_COUNTY_CD, ORG_ADDR_COUNTRY_CD"
			+ ", ORG_ADDR_STATE_CD, ORG_ADDR_STREET_ADDR1_TXT, ORG_ADDR_STREET_ADDR2_TXT, ORG_ADDR_ZIP_CODE_TXT, ORG_CLASS_CD, ORG_COMMENT_TXT, ORG_EMAIL_ADDRESS_TXT, ORG_ID_CLIA_NBR_TXT, ORG_ID_FACILITY_IDENTIFIER_TXT"
			+ ", ORG_ID_QUICK_CODE_TXT, ORG_PHONE_COMMENT_TXT, ORG_PHONE_COUNTRY_CODE_TXT, ORG_PHONE_EXTENSION_TXT, ORG_PHONE_NBR_TXT, ORG_ROLE_CD, ORG_URL_ADDRESS_TXT)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_TREATMENT_QUERY = "INSERT INTO MSG_TREATMENT(MSG_CONTAINER_UID, TRT_LOCAL_ID, TRT_AUTHOR_ID, TRT_COMPOSITE_CD, TRT_COMMENT_TXT, TRT_CUSTOM_TREATMENT_TXT, TRT_DOSAGE_AMT, TRT_DOSAGE_UNIT_CD, TRT_DRUG_CD, TRT_DURATION_AMT"
			+ ", TRT_DURATION_UNIT_CD, TRT_EFFECTIVE_TIME, TRT_FREQUENCY_AMT_CD, TRT_ROUTE_CD, TRT_TREATMENT_DT)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final String MSG_ANSWER_QUERY = "INSERT INTO MSG_ANSWER(MSG_CONTAINER_UID,MSG_EVENT_ID,MSG_EVENT_TYPE,ANS_CODE_SYSTEM_CD,ANS_CODE_SYSTEM_DESC_TXT,ANS_DISPLAY_TXT,ANSWER_TXT,ANSWER_LARGE_TXT,ANSWER_GROUP_SEQ_NBR, ANSWER_XML_TXT, PART_TYPE_CD"
			+ ",QUESTION_IDENTIFIER,QUES_CODE_SYSTEM_CD,QUES_CODE_SYSTEM_DESC_TXT,QUES_DISPLAY_TXT,QUESTION_GROUP_SEQ_NBR,SECTION_NM, SEQ_NBR)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final String MSG_CONTAINER = "MSG_CONTAINER";
	final String MSG_PATIENT = "MSG_PATIENT";
	final String MSG_CASE_SIGNS = "MSG_CASE_SIGNS";
	final String MSG_CASE_INVESTIGATION = "MSG_CASE_INVESTIGATION";
	final String MSG_PROVIDER = "MSG_PROVIDER";
	final String MSG_ORGANIZATION = "MSG_ORGANIZATION";
	final String MSG_TREATMENT = "MSG_TREATMENT";
	final String MSG_ANSWER = "MSG_ANSWER";
	final String EVENT_ID_KEY = "MSG_CASE_INVESTIGATION.INV_LOCAL_ID";

	final String[] LOCAL_ID_ARRAY_ENTITY = new String[] { "PRV_LOCAL_ID",
			"ORG_LOCAL_ID", "PAT_LOCAL_ID" };
	final String REPLACEWITHTIMESTAMP = "REPLACEWITHTIMESTAMP";
	StringBuffer stringBuffer = null;

	/**
	 * parseXML: this method parse the xml in xmlLocation. The xml is of type
	 * type, and it stores the information in the corresponding source table.
	 * 
	 * @param documentLocation
	 *            : the location of the xml document.
	 * @param type
	 *            : like EICR_FORM
	 */
	public void parseXML(ArrayList<MsgXMLMappingDT> eICRMappingTable,
			NbsInterfaceDT nbsInterfaceDT) throws Exception {

		Map<String, String> toSideToValueMap = new HashMap<String, String>();
		ArrayList<MsgAnswerDT> toSideToVMSgAnswereList = new ArrayList<MsgAnswerDT>();
		Map<String, String> sourceQuestionTypeMap = new HashMap<String, String>();
		Map<String, String> participantMap = new HashMap<String, String>();
		MsgXMLMappingDT mappingDT = new MsgXMLMappingDT();
		Document doc = null;
		NbsInterfaceDAOImpl nbsInterfaceDAO=null;
		String maxContainerUid = generateUid();

		// String content = "";// delete
		// final String FILENAME = "C:\\eICR\\eICR.txt";// delete
		//
		// BufferedWriter bw = null;// delete
		// FileWriter fw = null;// delete
		try {

			// fw = new FileWriter(FILENAME);// delete
			// bw = new BufferedWriter(fw);// delete

			// Create and parse the document in the documentLocation
			// location
			// File file = new File(documentLocation);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// doc = dBuilder.parse(file);

			// doc=dBuilder.parse(stringBuffer.toString());
			InputSource is = new InputSource(new StringReader(
					nbsInterfaceDT.getOriginalPayload()));
			is.setEncoding("UTF-8");
			doc = dBuilder.parse(is);
			

			ReadXMLFile readXmlFile = new ReadXMLFile();

			for (int i = 0; eICRMappingTable != null
					&& i < eICRMappingTable.size(); i++) {
				
				
				MsgXMLMappingDT row = (MsgXMLMappingDT) eICRMappingTable.get(i);
				try{
				String convertedValue = "";
				String tag = null;
				if (!row.getXmlTag().contains(DEPENDENCY)) {
					tag = row.getXmlTag().replace(TAG_SEPARATOR,
							ReadXMLFile.TAG_SEPARATOR_AUX);
				} else
					tag = row.getXmlTag();
				boolean singleValueInd = false;
				boolean multiValueInd = false;
				boolean isStoreNullasNAInd = false;
				boolean overrideInd = true;
				String dependencyTag = null;
				String dependencyValue = null;
				if (tag.contains(DEPENDENCY)) {
					dependencyTag = tag.substring(tag.indexOf(DEPENDENCY) + 12);
					tag = tag.substring(0, tag.indexOf(DEPENDENCY));
				}
				if (tag.endsWith(TOP1)) {
					tag = tag.substring(0, tag.length() - 2);
					singleValueInd = true;
				}
				if (tag.endsWith(MULTI)) {
					tag = tag.substring(0, tag.length() - 6);
					multiValueInd = true;
				}
				if (tag.contains(DEFAULT_VALUE_SEPERATOR)) {
					convertedValue = tag.substring(tag
							.indexOf(DEFAULT_VALUE_SEPERATOR) + 1);
					tag = tag
							.substring(0, tag.indexOf(DEFAULT_VALUE_SEPERATOR));
				}
				if (tag.endsWith(OVERRIDE_FALSE)) {
					overrideInd = false;
					tag = tag.substring(0, tag.indexOf(OVERRIDE_FALSE));
				}
				String from = row.getXmlPath() + TAG_SEPARATOR + tag;

				String sourceTable = "";

				if (row.getTranslationTableNm() != null)
					sourceTable = row.getTranslationTableNm().trim();

				String sourceColumn = row.getColumnNm();

				String typeSourceQuestion = "";

				if (row.getQuestionDataType() != null)
					typeSourceQuestion = row.getQuestionDataType();

				String sourceTableSourceQuestion = "";

				if (!sourceTable.isEmpty())
					sourceTableSourceQuestion = sourceTable
							+ "."
							+ sourceColumn
							+ "^"
							+ String.valueOf(row.getRepeatGroupSeqNbr()
									.intValue());
				if(sourceColumn.equals("INV_CONDITION_CD")) {
					isStoreNullasNAInd= true;
				}
				String value = readXmlFile.findValue(doc, from, isStoreNullasNAInd, multiValueInd);
				// check for dependency, go to next element if dependency not
				// met.
			
				if (dependencyTag != null && !dependencyTag.isEmpty()) {
					dependencyValue = readXmlFile.findValue(doc,
							row.getXmlPath() + TAG_SEPARATOR + dependencyTag,
							isStoreNullasNAInd, multiValueInd);
					if (dependencyValue == null || dependencyValue.isEmpty())
						continue;
				}
				if (toSideToValueMap.get(sourceTableSourceQuestion) != null
						&& !toSideToValueMap.get(sourceTableSourceQuestion)
								.isEmpty() && !overrideInd)
					value = toSideToValueMap.get(sourceTableSourceQuestion);

				if (value != null && !value.isEmpty() && convertedValue != null
						&& !convertedValue.isEmpty()) {
					value = convertedValue;
				}
				if (Arrays.asList(LOCAL_ID_ARRAY_ENTITY).contains(sourceColumn)) {
					if (participantMap.get(row.getXmlPath() + TAG_SEPARATOR
							+ tag) != null
							&& !participantMap.get(
									row.getXmlPath() + TAG_SEPARATOR + tag)
									.isEmpty())
						value = participantMap.get(row.getXmlPath()
								+ TAG_SEPARATOR + tag);
					else
						value = generateLocalId();
					participantMap.put(row.getXmlPath() + TAG_SEPARATOR + tag,
							value);
				}
				// In case there's more than one value and needed single value
				if (singleValueInd && value != null)
					value = getOnlyOneValue(value);
				if (multiValueInd && value != null) {
					setMultipleValues(toSideToValueMap, value,
							sourceTableSourceQuestion.substring(0,
									sourceTableSourceQuestion.length() - 2));
				}
				// In case there's more than one value for the source question
				value = appendPreviousValuesInSourceQuestion(
						toSideToValueMap,
						sourceColumn
								+ "^"
								+ String.valueOf(row.getRepeatGroupSeqNbr()
										.intValue()), value);

				if (!sourceTableSourceQuestion.isEmpty() && value != null
						&& !value.isEmpty()
						&& !sourceTable.contains(MSG_ANSWER)) {
					if (!multiValueInd)
						toSideToValueMap.put(sourceTableSourceQuestion, value);
					sourceQuestionTypeMap.put(sourceTable + "." + sourceColumn,
							typeSourceQuestion);
				} else if (!sourceTableSourceQuestion.isEmpty()
						&& value != null && !value.isEmpty()
						&& sourceTable.contains(MSG_ANSWER)) {
					MsgAnswerDT msgAnswerDT = new MsgAnswerDT();
					if (row.getPartTypeCd() != null)
						value = participantMap.get(row.getXmlPath()
								+ TAG_SEPARATOR + tag);
					if (value != null) {
						prepareMsgAnswerDT(msgAnswerDT, value, row,
								maxContainerUid);
						toSideToVMSgAnswereList.add(msgAnswerDT);
					}

				}
				} catch (Exception ex) {
					ex.printStackTrace();
					String error = "Exception while processing element "
							+ row.getQuestionIdentifier() + " for Mapping: "
							+ row.getXmlPath() + TAG_SEPARATOR
							+ row.getXmlPath() + " for table.column "
							+ row.getTranslationTableNm() + "."
							+ row.getColumnNm();
					throw new Exception(ex.getMessage() + " : " + error);
				}
				// content = from + ": " + sourceTableSourceQuestion + ": "
				// + value + "\n";// delete
				//
				// bw.write(content);// delete
				// bw.newLine();// delete
			}

			// if (bw != null)// delete
			// bw.close();// delete
			// if (fw != null)// delete
			// fw.close();// delete

			// } catch (IOException ex) {
			//
			// ex.printStackTrace();
			//
			// }
			nbsInterfaceDAO = new NbsInterfaceDAOImpl();
			
			
			// Insert the values in the corresponding source table
			insertInSourceTable(toSideToValueMap, sourceQuestionTypeMap,
					mappingDT, toSideToVMSgAnswereList, maxContainerUid,
					nbsInterfaceDT.getNbsInterfaceUid());

			nbsInterfaceDT
					.setRecordStatusCd(NEDSSConstants.MSG_RHAP_PROCESSING);
			nbsInterfaceDT.setRecordStatusTime(new Timestamp(new Date()
					.getTime()));
			nbsInterfaceDAO.updateNbsInterfaceDT(nbsInterfaceDT);
		} catch (Exception e) {
			nbsInterfaceDT.setRecordStatusCd(NEDSSConstants.MSG_FAIL);
			nbsInterfaceDT.setRecordStatusTime(new Timestamp(new Date()
					.getTime()));
			nbsInterfaceDAO.updateNbsInterfaceDT(nbsInterfaceDT);
			logger.error("Error in file: GenericXMLParser.java method: parseXML: "
					+ e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * appendPreviousValuesInSourceQuestion(): in case there's more than 1 value
	 * to be saved in the same MSG_question_nnd, they are concatenated by |.
	 * If they value is null or empty we don't concatenate it.
	 * 
	 * @param toSideToValueMap
	 * @param sourceColumn
	 * @param value
	 * @return
	 */

	private String appendPreviousValuesInSourceQuestion(
			Map<String, String> toSideToValueMap, String sourceColumn,
			String value) {
		if (value != null && !value.isEmpty()) {

			String existingValue = toSideToValueMap.get(sourceColumn);

			if (existingValue != null && !existingValue.isEmpty())
				value = existingValue + "|" + value;
		}

		return value;

	}

	/**
	 * insertInSourceTable(): this method insert the values in the source table.
	 * Temporal solution: delete the record if it's already in the table. It
	 * should be an update with an unique id for the eICR.
	 * 
	 * @param toSideToValueMap
	 * @param mappingDT
	 */

	private void insertInSourceTable(Map<String, String> toSideToValueMap,
			Map<String, String> sourceQuestionTypeMap,
			MsgXMLMappingDT mappingDT, ArrayList<MsgAnswerDT> msgAnswerDTList,
			String maxContainerUid, Long nbsInterfaceUid) {

		Map<String, String> ConstantMap = new HashMap<String, String>();

		// Container Fields
		ConstantMap.put(MSG_CONTAINER + ".MSG_CONTAINER_UID", maxContainerUid);
		ConstantMap.put(MSG_CONTAINER + ".NBS_INTERFACE_UID",
				nbsInterfaceUid.toString());
		ConstantMap.put(MSG_CONTAINER + ".RECORD_STATUS_CD",
				NEDSSConstants.APPROVED);
		ConstantMap.put(MSG_CONTAINER + ".RECORD_STATUS_TIME",REPLACEWITHTIMESTAMP);
		ConstantMap.put(MSG_CONTAINER + ".ADD_REASON_CD", "PHC1463");
		// ConstantMap.put(MSG_CONTAINER+".DOC_TYPE_CD","PHDC");
		ConstantMap.put(MSG_CONTAINER + ".DATA_MIGRATION_STATUS", "-1");
		ConstantMap.put(MSG_CONTAINER + ".ONGOING_CASE", "YES");

		// Carry over the container Uid
		ConstantMap.put(MSG_PATIENT + ".MSG_CONTAINER_UID", maxContainerUid);
		ConstantMap.put(MSG_CASE_INVESTIGATION + ".MSG_CONTAINER_UID",
				maxContainerUid);
		ConstantMap.put(MSG_PROVIDER + ".MSG_CONTAINER_UID", maxContainerUid);
		ConstantMap.put(MSG_ORGANIZATION + ".MSG_CONTAINER_UID",
				maxContainerUid);
		ConstantMap.put(MSG_TREATMENT + ".MSG_CONTAINER_UID", maxContainerUid);

		Map<String, Map<String, String>> repeatingMap = new HashMap<String, Map<String, String>>();
		Map<String, String> singleEntryMap = new HashMap<String, String>();
		
		ArrayList<String> removeList = new ArrayList<String>();
		Set<String> keys = toSideToValueMap.keySet();
		Iterator<String> ite = keys.iterator();
		while (ite.hasNext()) {
			String key = (String) ite.next();
			String[] toSide = (key).split("\\^");

			String tableName = toSide[0].split("\\.")[0] + toSide[1];
			singleEntryMap.put(toSide[0], toSideToValueMap.get(key));
			if (repeatingMap.containsKey(tableName)) {
				repeatingMap.get(tableName).put(toSide[0],
						toSideToValueMap.get(key));
			} else {
				repeatingMap.put(tableName, new HashMap<String, String>());
				repeatingMap.get(tableName).put(toSide[0],
						toSideToValueMap.get(key));
			}

		}

		singleEntryMap.putAll(ConstantMap);
		insertValuesInSourceTable(singleEntryMap, sourceQuestionTypeMap,
				MSG_CONTAINER_QUERY, mappingDT, MSG_CONTAINER);
		insertValuesInSourceTable(singleEntryMap, sourceQuestionTypeMap,
				MSG_PATIENT_QUERY, mappingDT, MSG_PATIENT);
		insertValuesInSourceTable(singleEntryMap, sourceQuestionTypeMap,
				MSG_CASE_INVESTIGATION_QUERY, mappingDT, MSG_CASE_INVESTIGATION);
		Iterator<String> ite1 = repeatingMap.keySet().iterator();
		while (ite1.hasNext()) {
			String key = ite1.next();
			if (key.contains(MSG_PROVIDER)) {
				repeatingMap.get(key).putAll(ConstantMap);
				if (((Map<String, String>) repeatingMap.get(key))
						.get(MSG_PROVIDER + ".PRV_NAME_FIRST_TXT") != null
						|| ((Map<String, String>) repeatingMap.get(key))
								.get(MSG_PROVIDER + ".PRV_NAME_LAST_TXT") != null)
					insertValuesInSourceTable(repeatingMap.get(key),
							sourceQuestionTypeMap, MSG_PROVIDER_QUERY,
							mappingDT, MSG_PROVIDER);
				else
					removeList
							.add(((Map<String, String>) repeatingMap.get(key))
									.get(MSG_PROVIDER + ".PRV_LOCAL_ID"));

			} else if (key.contains(MSG_ORGANIZATION)) {
				repeatingMap.get(key).putAll(ConstantMap);
				if (((Map<String, String>) repeatingMap.get(key))
						.get(MSG_ORGANIZATION + ".ORG_NAME_TXT") != null)
					insertValuesInSourceTable(repeatingMap.get(key),
							sourceQuestionTypeMap, MSG_ORGANIZATION_QUERY,
							mappingDT, MSG_ORGANIZATION);
				else
					removeList
							.add(((Map<String, String>) repeatingMap.get(key))
									.get(MSG_ORGANIZATION + ".ORG_LOCAL_ID"));

			} else if (key.contains(MSG_TREATMENT)) {
				repeatingMap.get(key).putAll(ConstantMap);
				if (((Map<String, String>) repeatingMap.get(key))
						.get(MSG_TREATMENT + ".TRT_COMPOSITE_CD") != null
						&& ((Map<String, String>) repeatingMap.get(key))
								.get(MSG_TREATMENT + ".TRT_COMPOSITE_CD") != "") {
					// ((Map<String,
					// String>)repeatingMap.get(key)).put(MSG_TREATMENT+".TRT_LOCAL_ID",
					// generateLocalId());
					insertValuesInSourceTable(repeatingMap.get(key),
							sourceQuestionTypeMap, MSG_TREATMENT_QUERY,
							mappingDT, MSG_TREATMENT);
				}

			}
		}
		String eventID = singleEntryMap.get(EVENT_ID_KEY);
		for (MsgAnswerDT msgAnswerDT : msgAnswerDTList) {
			msgAnswerDT.setMsgEventId(eventID);
			if (removeList.contains(msgAnswerDT.getAnswerTxt()))
				continue;
			else
				insertMsgAnswerDT(msgAnswerDT);
		}
	}
	

	/**
	 * generateUid(): this method generates aleatory Uid. Used for SGN100 for example.
	 * 
	 * @return
	 */

	public static String generateUid() {

		Long uid = null;
		
		try{
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
	        uid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
		}catch(Exception e){
			e.printStackTrace();
			
		}
        return uid+"";
	}
	
	public static String generateLocalId() {

		String localId = null;
		
		try{
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			localId = uidGen.getLocalID(UidClassCodes.CASE_REPORT);
		}catch(Exception e){
			e.printStackTrace();
			
		}
        return localId+"";
	}

	/**
	 * getOnlyOneValue(): in case we have more than one telephone, we only store
	 * the first one
	 * 
	 * @param value
	 * @return
	 */

	public String getOnlyOneValue(String value) {
		String[] values = value.split("\\|");
		return values[0];
	}
	
	public void setMultipleValues(Map<String, String> toSideToValueMap,
			String value, String toSide) {
		String[] values = value.split("\\|");
		int i = 1;
		for (String splitValue : values) {
			String toSideFinal = toSide + "^" + i;
			String toSideNextFinal = toSide + "^" + (i+1);
			if(toSideToValueMap.get(toSideFinal)==null || toSideToValueMap.get(toSideFinal).isEmpty()){
				toSideToValueMap.put(toSide + "^" + i, splitValue);
			}
			else if(toSideToValueMap.get(toSideNextFinal)==null || toSideToValueMap.get(toSideNextFinal).isEmpty()){
				toSideToValueMap.put(toSide + "^" + (i+1), splitValue);
			}
			i++;
		}
	}


	/**
	 * insertValuesInSourceTable: insert the values in the corresponding source
	 * table
	 * 
	 * @param toSideToValueMapSourceTable
	 * @param sourceTableName
	 * @param mappingDT
	 */
	private void insertValuesInSourceTable(
			Map<String, String> toSideToValueMapSourceTable,
			Map<String, String> sourceQuestionTypeMap, String insertQuery,
			MsgXMLMappingDT mappingDT, String sourceTable) {

		// TODO: change to ArrayList<ArrayList<String>> in case there's repeated
		// question for different data?
		ArrayList<Object> MSG_table_values = new ArrayList<Object>();
		boolean existingValues = fillSourceTable(toSideToValueMapSourceTable,
				sourceQuestionTypeMap, MSG_table_values, insertQuery,
				sourceTable);
		if (existingValues) {// In case there's no values to insert
			int resultCount = (Integer) preparedStmtMethod(mappingDT,
					MSG_table_values, insertQuery, "INSERT", NEDSSConstants.MSGOUT);
		}
	}

	/**
	 * fillSourceTable(): fills the ArrayList values with the values to insert
	 * in the corresponding source table for each of the columns in the query
	 * insertStatement
	 * 
	 * @param toSideToValueMapSourcePatientTable
	 * @param values
	 */
	private boolean fillSourceTable(
			Map<String, String> toSideToValueMapSourceTable,
			Map<String, String> sourceQuestionTypeMap,
			ArrayList<Object> values, String insertStatement, String sourceTable) {
		String columns = insertStatement.substring(
				insertStatement.indexOf("("), insertStatement.indexOf(")"));
		columns = columns.replace(" ", "").replace("(", "").replace(")", "");
		String[] ids = columns.trim().split(",");
		boolean existingValues = false;

		for (int i = 0; i < ids.length; i++) {
			String sourceTableSourceQuestion = sourceTable + "." + ids[i];
			String value = toSideToValueMapSourceTable
					.get(sourceTableSourceQuestion);
			String type = sourceQuestionTypeMap.get(sourceTableSourceQuestion);
			if (type != null
					&& (type.equalsIgnoreCase("TS") || type
							.equalsIgnoreCase("DT"))) {
				Date date = convertToDate(value);
				values.add((Date) date);
			} else if(value!=null && value.equals(REPLACEWITHTIMESTAMP)){
				values.add(new Timestamp(new Date().getTime()));
            }
			else
				values.add(value);
		}

		if (values != null && values.size() > 0)
			existingValues = true;

		return existingValues;

	}
	
//	public static void main (String arga[]){
//		GenericXMLParser dateParser = new GenericXMLParser();
//		dateParser.convertToDate("20180425021047.1338");
//	}

	/**
	 * convertToDate: this method converts the string into a format date
	 * @param dateString
	 * @return
	 */
	
	
	private Date convertToDate(String dateString) {
		Timestamp date = null;
		if (dateString != null && !dateString.isEmpty()) {
			SimpleDateFormat formatter = null;

			if (dateString.length() == 8)
				formatter = new SimpleDateFormat("yyyyMMdd");
			else if (dateString.length() == 10)
				formatter = new SimpleDateFormat("yyyyMMddHH");
			else if (dateString.length() == 12)
				formatter = new SimpleDateFormat("yyyyMMddHHmm");
			else if (dateString.length() >= 14 ){
				dateString = dateString.substring(0, 14);
				formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			}
			
			try {
				date = new java.sql.Timestamp(formatter.parse(dateString)
						.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	private void prepareMsgAnswerDT(MsgAnswerDT msgAnswerDT, String value,
			MsgXMLMappingDT xmlMappingDT, String containerUId) {
		String[] values = value.split("\\^");
		if (xmlMappingDT.getColumnNm().equalsIgnoreCase("ANSWER_XML_TXT"))
			msgAnswerDT.setAnswerXmlTxt(value);
		else {
			msgAnswerDT.setAnswerTxt(values[0]);
			if (values.length > 1)
				msgAnswerDT.setAnsDisplayTxt(values[1]);
			if (values.length > 2)
				msgAnswerDT.setAnsCodeSystemDescTxt(values[2]);
			if (values.length > 3)
				msgAnswerDT.setAnsCodeSystemCode(values[3]);
			if (values.length == 2) {
				msgAnswerDT.setAnswerTxt(value);

				msgAnswerDT.setAnsDisplayTxt(null);
				msgAnswerDT.setAnsCodeSystemCode(values[1]);
			}
		}
		msgAnswerDT.setQuestionIdenitifer(xmlMappingDT
				.getQuestionIdentifier());
		msgAnswerDT.setMsgEventType("CASE");
		msgAnswerDT.setMsgContainerUid(new Long(containerUId));
		msgAnswerDT.setPartTypeCd(xmlMappingDT.getPartTypeCd());
		msgAnswerDT.setQuesCodeSystemCd(xmlMappingDT.getQuesCodeSystemCd());
		msgAnswerDT.setQuesCodeSystemDescTxt(xmlMappingDT.getQuesCodeSystemDescTxt());
		msgAnswerDT.setQuesDisplayTxt(xmlMappingDT.getQuesDisplayTxt());
	}
	
	public void insertMsgAnswerDT(MsgAnswerDT msgAnswerDT)
			throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_MSG_ANSWER=" + MSG_ANSWER_QUERY);
		dbConnection = getConnection(NEDSSConstants.MSGOUT);

		try {
			preparedStmt = dbConnection.prepareStatement(MSG_ANSWER_QUERY);
			int i = 1;
			preparedStmt.setLong(i++, msgAnswerDT.getMsgContainerUid()
					.longValue()); // 1
			preparedStmt.setString(i++, msgAnswerDT.getMsgEventId()); // 2
			preparedStmt.setString(i++, msgAnswerDT.getMsgEventType()); // 3
			preparedStmt.setString(i++, msgAnswerDT.getAnsCodeSystemCode()); // 4
			preparedStmt.setString(i++, msgAnswerDT.getAnsCodeSystemDescTxt()); // 5
			preparedStmt.setString(i++, msgAnswerDT.getAnsDisplayTxt()); // 6
			preparedStmt.setString(i++, msgAnswerDT.getAnswerTxt()); // 7
			preparedStmt.setBlob(i++, msgAnswerDT.getAnswerLargeTxt()); // 8
			if (msgAnswerDT.getAnswerGroupSeqNbr() != null)
				preparedStmt.setInt(i++, msgAnswerDT.getAnswerGroupSeqNbr()
						.intValue()); // 9
			else
				preparedStmt.setNull(i++, Types.INTEGER);
			preparedStmt.setString(i++, msgAnswerDT.getAnswerXmlTxt()); // 10
			preparedStmt.setString(i++, msgAnswerDT.getPartTypeCd()); // 11
			preparedStmt.setString(i++, msgAnswerDT.getQuestionIdenitifer()); // 12
			preparedStmt.setString(i++, msgAnswerDT.getQuesCodeSystemCd()); // 13
			preparedStmt.setString(i++, msgAnswerDT.getQuesCodeSystemDescTxt()); // 14
			preparedStmt.setString(i++, msgAnswerDT.getQuesDisplayTxt()); // 15
			if (msgAnswerDT.getQuestionGroupSeqNbr() != null)
				preparedStmt.setInt(i++, msgAnswerDT.getQuestionGroupSeqNbr()
						.intValue()); // 16
			else
				preparedStmt.setNull(i++, Types.INTEGER);
			preparedStmt.setString(i++, msgAnswerDT.getSectionNm()); // 17
			if (msgAnswerDT.getSeqNbr() != null)
				preparedStmt.setInt(i++, msgAnswerDT.getSeqNbr().intValue()); // 18
			else
				preparedStmt.setNull(i++, Types.INTEGER);
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertMsgAnswerDT is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "msgAnswerDT into MSG_ANSWER: ", sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into MSG_ANSWER, nsgAnswerDT= ", ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}

}
