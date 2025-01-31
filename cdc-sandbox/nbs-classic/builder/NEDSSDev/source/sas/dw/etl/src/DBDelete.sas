/********************/
/***    DBDELETE    */
/********************/

/* delete bridge tables first, then outrigger tables to preserve referential integrity */

/* bridge tables off of dimension tables */
%dbdelete (PERSON_RACE);
%dbdelete (PERSON_ETHNICITY);
%dbdelete (PERSON_NAME);
%dbdelete (PERSON_CONTACT);
%dbdelete (PERSON_LOCATION);
%dbdelete (ORG_ROLE);
%dbdelete (ORG_CONTACT);
%dbdelete (ORG_LOCATION);


/* outrigger tables off of dimension tables */
%dbdelete (RACE);
%dbdelete (DETAIL_ETHNICITY);
%dbdelete (NAME);
%dbdelete (CONTACT_INFO);
%dbdelete (ORG_ROLE_CODE);
%dbdelete (PERSON_RELATIONSHIP);
%dbdelete (PERSON_ID);
%dbdelete (ORG_ID);

/* delete fact tables first, then dimension tables to preserve referential integrity */

/* fact tables */
%dbdelete (Case_Count);
%dbdelete (GENERIC_CASE);
%dbdelete (CRS_CASE);
%dbdelete (Measles_CASE);
%dbdelete (RUBELLA_CASE);
/*-----------------------------------------
	NOTIFICATION_EVENT
-------------------------------------------*/
%dbdelete (NOTIFICATION_EVENT);
%dbdelete (NOTIFICATION);

/*-----------------------------------------
	TREATMENT
-------------------------------------------*/
%dbdelete (TREATMENT_EVENT);
%dbdelete (TREATMENT);

/*-----------------------------------------
	VACCINATION
-------------------------------------------*/
%dbdelete (VACCINATION);
/*-----------------------------------------
	HEPATITIS_CASE
-------------------------------------------*/
%dbdelete (HEP_MULTI_VALUE_FIELD);
%dbdelete (HEPATITIS_CASE);
%dbdelete (HEP_MULTI_VALUE_FIELD_GROUP);
/*-----------------------------------------
	Bmird_CASE
-------------------------------------------*/
%dbdelete (Bmird_CASE);
%dbdelete (ANTIBIOTICS);
%dbdelete (ANTIBIOTICS_GROUP);
%dbdelete (ANTIMICROBIAL);
%dbdelete (ANTIMICROBIAL_GROUP);
%dbdelete (BMIRD_MULTI_VALUE_FIELD);
%dbdelete (BMIRD_MULTI_VALUE_FIELD_GROUP);

/*-----------------------------------------
	PERTUSSIS_CASE
-------------------------------------------*/
%dbdelete (Pertussis_CASE);
%dbdelete (PERTUSSIS_SUSPECTED_SOURCE_FLD);
%dbdelete (PERTUSSIS_SUSPECTED_SOURCE_GRP);
%dbdelete (PERTUSSIS_TREATMENT_FIELD);
%dbdelete (PERTUSSIS_TREATMENT_GROUP);


/*-----------------------------------------
	Lab_REPORT tables
	MORBIDITY_REPORT tables
-------------------------------------------*/
%dbdelete (LAB_RESULT_VAL);
%dbdelete (LAB_RESULT_COMMENT);
%dbdelete (LAB_TEST_RESULT);
%dbdelete (TEST_RESULT_GROUPING);
%dbdelete (RESULT_COMMENT_GROUP);
%dbdelete (LAB_RPT_USER_COMMENT);
%dbdelete (LAB_TEST);
%dbdelete (MORB_RPT_USER_COMMENT);
%dbdelete (MORBIDITY_REPORT_EVENT);
%dbdelete (MORBIDITY_REPORT);

/*------------------------------------
	SUMMARY_REPORT_CASE 
--------------------------------------*/
%dbdelete (SUMMARY_REPORT_CASE);
%dbdelete (SUMMARY_CASE_GROUP);

/*------------------------------------
	LDF
--------------------------------------*/
%dbdelete (LDF_Data);  
%dbdelete (Person_LDF_Group);   
%dbdelete (Organization_LDF_Group);   
%dbdelete (LDF_Group); 

/*------------------------------------
	USER_PROFILE
--------------------------------------*/
%dbdelete (USER_PROFILE);  

/*------------------------------------
	Investigation
--------------------------------------*/
%dbdelete (Confirmation_method_Group);
%dbdelete (Confirmation_method);
%dbdelete (INVESTIGATION);



/*-----------------------------------------
	dimension tables
	last to delete
-------------------------------------------*/
%dbdelete (LOCATION);
%dbdelete (PERSON);
%dbdelete (ORGANIZATION);


/* static tables (one time load) */
%dbdelete (Condition);
%dbdelete (RDB_DATE);
