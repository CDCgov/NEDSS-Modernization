/**********/
/* DBLOAD */
/**********/


%checkerr;

%checkerr;
/* load dimension tables first, then fact tables to preserve referential integrity */
/* dimension tables */
/* load outrigger tables first, then bridge tables to preserve referential integrity */
/* outrigger tables off of dimension tables */
%dbload (GEOCODING_LOCATION, rdbdata.geocoding_location);
%checkerr;

/*------------------------------------
	Investigation
--------------------------------------*/
/*
%dbload (Confirmation_method, rdbdata.Confirmation_method);   
%dbload (Confirmation_method_Group, rdbdata.Confirmation_method_Group);   
%checkerr;
*/
/*------------------------------------
	USER_PROFILE
--------------------------------------*/


%checkerr;
%dbload (GENERIC_CASE, rdbdata.GENERIC_CASE);        
%checkerr;
%dbload (CRS_CASE, rdbdata.CRS_CASE);  
%checkerr;
%dbload (Measles_CASE, rdbdata.Measles_CASE);  
%checkerr;
%dbload (RUBELLA_CASE, rdbdata.RUBELLA_CASE);
%checkerr;
/*-----------------------------------------
	HEPATITIS_CASE
-------------------------------------------*/
%dbload (HEP_MULTI_VALUE_FIELD_GROUP,rdbdata.HEP_MULTI_VALUE_FIELD_GROUP);
%dbload (HEP_MULTI_VALUE_FIELD,rdbdata.HEP_MULTI_VALUE_FIELD);
%dbload (HEPATITIS_CASE,rdbdata.HEPATITIS_CASE);
%checkerr;

/*-----------------------------------------
	Bmird_CASE
-------------------------------------------*/
%dbload (BMIRD_MULTI_VALUE_FIELD_GROUP,rdbdata.BMIRD_MULTI_VALUE_FIELD_GROUP);
%dbload (BMIRD_MULTI_VALUE_FIELD,rdbdata.BMIRD_MULTI_VALUE_FIELD);
%dbload (ANTIMICROBIAL_GROUP,rdbdata.ANTIMICROBIAL_GROUP);
%dbload (ANTIMICROBIAL,rdbdata.ANTIMICROBIAL);
%dbload (Bmird_CASE,rdbdata.Bmird_CASE);
%checkerr;

/*-----------------------------------------
	PERTUSSIS_CASE
-------------------------------------------*/
%dbload (PERTUSSIS_SUSPECTED_SOURCE_GRP,rdbdata.PERTUSSIS_SUSPECTED_SOURCE_GRP);
%dbload (PERTUSSIS_SUSPECTED_SOURCE_FLD,rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD);
%dbload (PERTUSSIS_TREATMENT_GROUP,rdbdata.PERTUSSIS_TREATMENT_GROUP);
%dbload (PERTUSSIS_TREATMENT_FIELD,rdbdata.PERTUSSIS_TREATMENT_FIELD);
%dbload (PERTUSSIS_CASE,rdbdata.Pertussis_Case);
%checkerr;

/*-----------------------------------------
	NOTIFICATION_EVENT
-------------------------------------------*/
%dbload (NOTIFICATION,rdbdata.NOTIFICATION);
%dbload (NOTIFICATION_EVENT,rdbdata.NOTIFICATION_EVENT);
%checkerr;

/*-----------------------------------------
	Lab_REPORT and MORB_REPORT
	Lab_Test
-------------------------------------------*/
%checkerr;
%checkerr;
%checkerr;
%checkerr;

/*-----------------------------------------
	TREATMENT
-------------------------------------------*/
%dbload (TREATMENT,rdbdata.TREATMENT);
%dbload (TREATMENT_EVENT,rdbdata.TREATMENT_EVENT);
%checkerr;

/*-----------------------------------------
	VACCINATION
%dbload (VACCINATION,rdbdata.VACCINATION);
%checkerr;
*/

