/**
 * Title: UidClassCodes class
 * Description: This class holds the class code names for generating local and nbs
 *              unique identifier.  The codes must match the column, class_name_cd, in
 *              the Table local_uid_generator.
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author Brent Chen
 * @version 1.0
 */

package gov.cdc.nedss.systemservice.uidgenerator;

import gov.cdc.nedss.util.PropertyUtil;

public class UidClassCodes
{
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	/**
	 * Uses to create entity, activity, and locator uid
	 */
	public static String NBS_CLASS_CODE = propertyUtil.getNBS_CLASS_CODE();

	/**
	 * Uses to create local activity-specific uid
	 */
	public static final String REFERRAL_CLASS_CODE = "REFERRAL";
	public static final String WORKUP_CLASS_CODE = "WORKUP";
	public static final String PUBLIC_HEALTH_CASE_CLASS_CODE = "PUBLIC_HEALTH_CASE";
	public static final String CLINICAL_DOCUMENT_CLASS_CODE = "CLINICAL_DOCUMENT";
	public static final String PATIENT_ENCOUNTER_CLASS_CODE = "PATIENT_ENCOUNTER";
	public static final String INTTERVENTION_CLASS_CODE = "INTERVENTION";
	public static final String NOTITICATION_CLASS_CODE = "NOTIFICATION";
	public static final String OBSERVATION_CLASS_CODE = "OBSERVATION";
	public static final String TREATMENT_CLASS_CODE = "TREATMENT";
	public static final String DOCUMENT_CLASS_CODE = "NBS_DOCUMENT";
	public static final String CONTACT_CLASS_CODE = "CT_CONTACT";
	public static final String COINFECTION_GROUP_CLASS_CODE = "COINFECTION_GROUP";
	public static final String EPI_LINK_ID_CLASS_CODE = "EPILINK";
	
	/**
	 * Uses to create local entity-specific uid
	 */
	public static final String PERSON_CLASS_CODE = "PERSON";
	public static final String GROUP_CLASS_CODE = "GROUP";
	public static final String MATERIAL_CLASS_CODE = "MATERIAL";
	public static final String PLACE_CLASS_CODE = "PLACE";
	public static final String NON_LIVING_SUBJECT_CLASS_CODE = "NON_LIVING_SUBJECT";
	public static final String ORGANIZATION_CLASS_CODE = "ORGANIZATION";
	public static final String DEDUPLICATION_CLASS_CODE = "DEDUPLICATION_LOG";
	public static final String GEOCODING_CLASS_CODE = "GEOCODING";
	public static final String GEOCODING_LOG_CLASS_CODE = "GEOCODING_LOG";
	
	//LDF NBSQuestion MetaData
	public static final String NBS_QUESTION_LDF_CLASS_CODE = "NBS_QUESTION_LDF";
	public static final String NBS_UIMETEDATA_LDF_CLASS_CODE = "NBS_UIMETEDATA_LDF";
	public static final String NBS_QUESTION_ID_LDF_CLASS_CODE = "NBS_QUESTION_ID_LDF";
	public static final String NBS_RDB_METADATA_CLASS_CODE = "NBS_RDB_METADATA";
	public static final String NBS_PAGE_CLASS_CODE = "PAGE";
	public static final String NND_METADATA_CLASS_CODE = "NND_METADATA";
	public static final String CASE_REPORT = "CS_REPORT";
	
	
}


