
proc sql;
DROP table NBS_RDB.ACTIVITY_LOG_MASTER_LAST_SAS;
quit;
proc sql;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
SELECT * 
INTO ACTIVITY_LOG_MASTER_LAST_SAS
FROM RDB.DBO.ACTIVITY_LOG_MASTER 
WHERE 
ACTIVITY_LOG_MASTER_UID= (SELECT MAX(ACTIVITY_LOG_MASTER_UID) FROM RDB..ACTIVITY_LOG_MASTER );
) by sql;
disconnect from sql; 
Quit;


PROC SQL;
Create table RDBDATA.ACTIVITY_LOG_MASTER_LAST as select * from NBS_RDB.ACTIVITY_LOG_MASTER_LAST_SAS;
quit;
PROC SQL;
UPDATE  RDBDATA.ACTIVITY_LOG_MASTER_LAST SET 
ACTIVITY_LOG_MASTER_UID = (SELECT MAX(ACTIVITY_LOG_MASTER_UID) FROM NBS_RDB.ACTIVITY_LOG_MASTER where (refresh_ind ='T' OR  refresh_ind is null))
WHERE ACTIVITY_LOG_MASTER_UID>1;
UPDATE  RDBDATA.ACTIVITY_LOG_MASTER_LAST SET 
START_DATE= (SELECT MAX(START_DATE) FROM NBS_RDB.ACTIVITY_LOG_MASTER where (refresh_ind ='T' OR  refresh_ind is null));
QUIT;
DATA RDBDATA.ACTIVITY_LOG_MASTER_LAST;
SET RDBDATA.ACTIVITY_LOG_MASTER_LAST;
IF START_DATE=. then START_DATE='01JUN1900'D;
RUN;

PROC SQL;
CREATE TABLE ACTIVITY_LOG_DETAIL  (ACTIVITY_LOG_DETAIL_UID NUMERIC,	PROCESS_UID NUMERIC ,	
	SOURCE_ROW_COUNT NUMERIC , ROW_COUNT_INSERT NUMERIC, ROW_COUNT_UPDATE NUMERIC,
	SOURCE_ROW_COUNT_EXISTING NUMERIC ,	SOURCE_ROW_COUNT_NEW NUMERIC ,
	DESTINATION_ROW_COUNT NUMERIC ,
	START_DATE DATE, END_DATE DATE,
	START_DATE2 DATE,
	ADMIN_COMMENT VARCHAR(200), ACTIVITY_LOG_MASTER_UID NUMERIC);
INSERT INTO ACTIVITY_LOG_DETAIL( ACTIVITY_LOG_DETAIL_UID, PROCESS_UID,SOURCE_ROW_COUNT, DESTINATION_ROW_COUNT,
START_DATE,END_DATE, ACTIVITY_LOG_MASTER_UID) VALUES (1 , 1, NULL, NULL, NULL, NULL, NULL);
UPDATE ACTIVITY_LOG_DETAIL SET ACTIVITY_LOG_DETAIL_UID= (SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1 ;
UPDATE ACTIVITY_LOG_DETAIL SET START_DATE2= (
SELECT MAX(START_DATE)  FROM NBS_RDB.ACTIVITY_LOG_DETAIL);
QUIT;
data ACTIVITY_LOG_DETAIL;
set ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID =MAX(ACTIVITY_LOG_DETAIL_UID,1);
run;

PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
END_DATE =DATETIME(),
START_DATE =DATETIME(),
ACTIVITY_LOG_MASTER_UID= (SELECT ACTIVITY_LOG_MASTER_UID FROM RDBDATA.ACTIVITY_LOG_MASTER_LAST);
QUIT;

DATA ACTIVITY_LOG_MASTER_LAST;
SET RDBDATA.ACTIVITY_LOG_MASTER_LAST; 
ODSE_COUNT=0;
RDB_COUNT=0;
RUN;
%MACRO ASSIGN_ADDITIONAL_KEY (DS, KEY);
 DATA &DS;
  IF &KEY=1 THEN OUTPUT;
  SET &DS;
	&KEY+1;
	OUTPUT;
 RUN;
%MEND;
DATA RDBDATA.ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
RUN;

/****************************************/
/*** PHC Observations ***/
/****************************************/

/* join ODS.Public_Health_Case and ODS.Act_Relationship to get root case details */
/* also select case-specific fields to merge with case facts later */
Proc SQL;
 Create table PHCroot_INIT as 
  Select phc.public_health_case_uid                    label='public_health_case_uid',
		 act.source_act_uid as root_obs_uid            label='root_obs_uid',
		 phc.cd as condition_cd                        label='condition_cd',
		 /* columns needed for specific fact tables */
		 phc.jurisdiction_cd,
		 phc.prog_area_cd,
		 phc.investigation_status_cd,
		 phc.rpt_form_cmplt_time,
		 phc.rpt_source_cd,
		/*phc.rpt_source_desc_txt, */
		 phc.rpt_to_county_time,
		 phc.rpt_to_state_time,
		 phc.diagnosis_time,
		 phc.effective_from_time,
		 phc.effective_to_time,
		 phc.effective_duration_amt,
		 phc.effective_duration_unit_cd,
		 phc.pat_age_at_onset,
		 phc.pat_age_at_onset_unit_cd,
		 phc.activity_from_time,
		 phc.outbreak_ind,
		 phc.outbreak_name,
		 phc.disease_imported_cd,
		 phc.transmission_mode_cd,
		 phc.transmission_mode_desc_txt,
		 phc.detection_method_cd,
		 cvg.code_desc_txt as detection_method_desc_txt,
		 phc.group_case_cnt,
		 phc.mmwr_week,
		 phc.mmwr_year,
		 phc.txt,
		 phc.local_id,
		 phc.cd_desc_txt,
		 phc.outcome_cd, 
		 phc.shared_ind,
		 phc.rpt_cnty_cd,
		 phc.case_type_cd,
		 phc.case_class_cd,
		 phc.cd_system_cd,
		 phc.cd_system_desc_txt,
		 phc.confidentiality_cd,
		 phc.confidentiality_desc_txt,
		 phc.program_jurisdiction_oid,
		 con.investigation_form_cd,
		 phc.record_status_cd,
		 phc.hospitalized_ind_cd,
 		 phc.pregnant_ind_cd,
		 phc.day_care_ind_cd,
		 phc.food_handler_ind_cd as food_handler_ind_code 'food_handler_ind_code',
		 phc.imported_country_cd as imported_country_code 'imported_country_code',
		 phc.imported_state_cd as imported_state_code 'imported_state_code',
		 phc.imported_county_cd as imported_county_code 'imported_county_code',
		 phc.investigator_assigned_time,
		 phc.hospitalized_admin_time,
		 phc.hospitalized_discharge_time,
		 phc.hospitalized_duration_amt, 
		 phc.imported_city_desc_txt,
		 phc.deceased_time as INVESTIGATION_DEATH_DATE 'INVESTIGATION_DEATH_DATE',
		 PHC.REFERRAL_BASIS_CD,
		 PHC.CURR_PROCESS_STATE_CD,
	     PHC.INV_PRIORITY_CD,
         PHC.COINFECTION_ID,
	 	 PHC.contact_inv_txt as CONTACT_INV_COMMENTS label='CONTACT_INV_COMMENTS',
		 PHC.infectious_from_date as CONTACT_INFECTIOUS_FROM_DATE label='CONTACT_INFECTIOUS_FROM_DATE',
		 PHC.infectious_to_date	as CONTACT_INFECTIOUS_TO_DATE label='CONTACT_INFECTIOUS_TO_DATE',
		 CVG1.code_short_desc_txt as  CONTACT_INV_PRIORITY label='CONTACT_INV_PRIORITY',
		 CVG2.code_short_desc_txt as CONTACT_INV_STATUS label='CONTACT_INV_STATUS',
		 PHC.ACTIVITY_TO_TIME AS INV_CLOSE_DT 'INV_CLOSE_DT',
		 P.PROG_AREA_DESC_TXT as PROGRAM_AREA_DESCRIPTION 'PROGRAM_AREA_DESCRIPTION',
		 PHC.ADD_TIME,
		 PHC.ADD_USER_ID,
		 PHC.LAST_CHG_TIME,
		 PHC.LAST_CHG_USER_ID
   From nbs_ods.public_health_case phc 
	LEFT OUTER JOIN nbs_srt.code_value_general AS cvg
	ON phc.detection_method_cd = cvg.code
	and cvg.code_set_nm in ('PHC_DET_MT', 'PHVS_DETECTIONMETHOD_STD')
	inner join nbs_srt.condition_code con
	on phc.cd = con.condition_cd
	left join nbs_ods.act_relationship act 
    on phc.public_health_case_uid = act.target_act_uid
	  and (act.type_cd = 'PHCInvForm' or act.type_cd ='SummaryForm')
	  and act.source_class_cd = 'OBS' 
	  and act.target_class_cd = 'CASE'
	LEFT OUTER JOIN nbs_srt.Code_Value_General CVG1 
		on phc.priority_CD = CVG1.code 
		and CVG1.code_set_nm='NBS_PRIORITY'
	LEFT OUTER JOIN nbs_srt.Code_Value_General CVG2 
		on phc.contact_inv_status_cd = CVG2.code 
		and CVG2.code_set_nm='PHC_IN_STS'
	LEFT OUTER JOIN nbs_srt.Program_area_code P 
		on phc.prog_area_cd = p.prog_area_cd 
   where
phc.LAST_CHG_TIME> (SELECT MAX(ACTIVITY_LOG_MASTER_LAST.START_DATE) FROM  ACTIVITY_LOG_MASTER_LAST)
	order by public_health_case_uid
	;
Quit;
PROC SQL;

CREATE TABLE  PHCroot AS SELECT A.*, 
B.FIRST_NM AS ADD_USER_FIRST_NAME 'ADD_USER_FIRST_NAME', B.LAST_NM AS ADD_USER_LAST_NAME 'ADD_USER_LAST_NAME', 
C.FIRST_NM AS CHG_USER_FIRST_NAME 'CHG_USER_FIRST_NAME', C.LAST_NM AS CHG_USER_LAST_NAME 'CHG_USER_LAST_NAME' 
FROM
PHCroot_INIT A LEFT OUTER JOIN NBS_RDB.USER_PROFILE B
ON A.ADD_USER_ID=B.NEDSS_ENTRY_ID
LEFT OUTER JOIN NBS_RDB.USER_PROFILE C
ON A.LAST_CHG_USER_ID=C.NEDSS_ENTRY_ID;
QUIT;
DATA PHCroot;
SET PHCroot;
LENGTH INVESTIGATION_ADDED_BY $50;
LENGTH INVESTIGATION_LAST_UPDATED_BY $50;
INVESTIGATION_ADDED_BY= TRIM(ADD_USER_LAST_NAME)|| ', ' ||TRIM(ADD_USER_FIRST_NAME);
INVESTIGATION_LAST_UPDATED_BY= TRIM(CHG_USER_LAST_NAME)|| ', ' ||TRIM(CHG_USER_FIRST_NAME);
IF LENGTH(COMPRESS(ADD_USER_FIRST_NAME))> 0 AND LENGTHN(COMPRESS(ADD_USER_LAST_NAME))>0 
	THEN INVESTIGATION_ADDED_BY= TRIM(ADD_USER_LAST_NAME)|| ', ' ||TRIM(ADD_USER_FIRST_NAME);
ELSE IF LENGTHN(COMPRESS(ADD_USER_FIRST_NAME))> 0 THEN INVESTIGATION_ADDED_BY= TRIM(ADD_USER_FIRST_NAME);
ELSE IF LENGTHN(COMPRESS(ADD_USER_LAST_NAME))> 0 THEN INVESTIGATION_ADDED_BY= TRIM(ADD_USER_LAST_NAME);
IF LENGTH(COMPRESS(CHG_USER_FIRST_NAME))> 0 AND LENGTHN(COMPRESS(CHG_USER_LAST_NAME))>0 THEN INVESTIGATION_LAST_UPDATED_BY= TRIM(CHG_USER_LAST_NAME)|| ', ' ||TRIM(CHG_USER_FIRST_NAME);
ELSE IF LENGTHN(COMPRESS(CHG_USER_FIRST_NAME))> 0 THEN INVESTIGATION_LAST_UPDATED_BY= TRIM(CHG_USER_FIRST_NAME);
ELSE IF LENGTHN(COMPRESS(CHG_USER_LAST_NAME))> 0 THEN INVESTIGATION_LAST_UPDATED_BY= TRIM(CHG_USER_LAST_NAME);
RUN;
proc sql;
Create table PHCroot_NONINC as 
  Select phc.public_health_case_uid ,
phc.cd,
phc.diagnosis_time,
 phc.transmission_mode_cd,
 phc.transmission_mode_desc_txt,
phc.effective_duration_amt,
phc.effective_duration_unit_cd,
act.source_act_uid as root_obs_uid  label='root_obs_uid',
phc.pat_age_at_onset,
phc.pat_age_at_onset_unit_cd,
phc.detection_method_cd,
cvg.code_desc_txt as detection_method_desc_txt,
con.investigation_form_cd
from nbs_ods.public_health_case phc
LEFT OUTER JOIN nbs_srt.code_value_general AS cvg
	ON phc.detection_method_cd = cvg.code
	and cvg.code_set_nm='PHC_DET_MT'
/*left join nbs_ods.act_relationship act 
    on phc.public_health_case_uid = act.target_act_uid
*/
left join nbs_ods.act_relationship act 
    on phc.public_health_case_uid = act.target_act_uid
	  and (act.type_cd = 'PHCInvForm' or act.type_cd ='SummaryForm')
	  and act.source_class_cd = 'OBS' 
	  and act.target_class_cd = 'CASE'

inner join nbs_srt.condition_code con
	on phc.cd = con.condition_cd
where INVESTIGATION_FORM_CD IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',,'INV_FORM_HEPB',
									'INV_FORM_HEPC','INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB', 'INV_FORM_CRS')
	;
quit; 
/* join ODS.Observation and PHCRoot to get root observation details for non incremental code*/
Proc SQL;
 Create table rdbdata.PHCrootobs_NONINC as
  Select phc.*,
  		 obs.cd as root_obs_cd                        label='root_obs_cd',

		 phc.cd as condition_cd                        label='condition_cd'
   From  PHCroot_NONINC phc left outer join nbs_ods.observation obs
    on obs.observation_uid = phc.root_obs_uid
	
    Order By public_health_case_uid, root_obs_uid;        /* sort required for merge with fact tables */
Quit;

/* join PHCrootobs to ODS.Act_Relationship to get child observation details */
Proc SQL;
 Create table PHCchild_NONINC as
  Select phc.public_health_case_uid,
		 phc.root_obs_uid,
		 phc.condition_cd,
		 phc.root_obs_cd,
		 phc.investigation_form_cd,
         act.source_act_uid as child_obs_uid           label='child_obs_uid'
   From nbs_ods.act_relationship act, rdbdata.PHCrootobs_NONINC phc
    Where act.target_act_uid = phc.root_obs_uid
	  and act.type_cd = 'InvFrmQ'
	  and act.source_class_cd = 'OBS' 
	  and act.target_class_cd = 'OBS'
	  /*and act.record_status_cd = 'ACTIVE'*/  
	  /*some hep cases form questions did not have record_status_cd set to ACTIVE */
;
Quit;


/* join PHCchild to ODS.Observation to get child observation details */
Proc SQL;
 Create table rdbdata.PHCchildobs_NONINC as
  Select phc.*,
         obs.cd as child_obs_cd                        label='child_obs_cd'
   From nbs_ods.observation obs, PHCchild_NONINC phc 
    Where obs.observation_uid = phc.child_obs_uid;
Quit;
/* join ODS.Observation and PHCRoot to get root observation details */
Proc SQL;
 Create table rdbdata.PHCrootobs as
  Select phc.*,
  		 obs.cd as root_obs_cd                        label='root_obs_cd'
   From  PHCRoot phc left outer join nbs_ods.observation obs
    on obs.observation_uid = phc.root_obs_uid
    Order By public_health_case_uid, root_obs_uid;        /* sort required for merge with fact tables */
Quit;


/* join PHCrootobs to ODS.Act_Relationship to get child observation details */
Proc SQL;
 Create table PHCchild as
  Select phc.public_health_case_uid,
		 phc.root_obs_uid,
		 phc.condition_cd,
		 phc.root_obs_cd,
		 phc.investigation_form_cd,
         act.source_act_uid as child_obs_uid           label='child_obs_uid'
   From nbs_ods.act_relationship act, rdbdata.PHCrootobs phc
    Where act.target_act_uid = phc.root_obs_uid
	  and act.type_cd = 'InvFrmQ'
	  and act.source_class_cd = 'OBS' 
	  and act.target_class_cd = 'OBS'
	  /*and act.record_status_cd = 'ACTIVE'*/  
	  /*some hep cases form questions did not have record_status_cd set to ACTIVE */
;
Quit;

/* join PHCchild to ODS.Observation to get child observation details */
Proc SQL;
 Create table rdbdata.PHCchildobs as
  Select phc.*,
         obs.cd as child_obs_cd                        label='child_obs_cd'
   From nbs_ods.observation obs, PHCchild phc 
    Where obs.observation_uid = phc.child_obs_uid;
Quit;


/*************************/

/* join to Codeset */
/* may result in multiple records per observation if used in multiple fact tables */
/*
Proc SQL;
 Create table rdbdata.PHCchildobs as
  Select phc.*,
         c.tbl_nm, c.col_nm, c.multi_select_ind, c.etl_ctrl, c.data_type
   From rdbdata.PHCchildobs phc LEFT JOIN rdbdata.Codeset c 
     On c.cd = phc.child_obs_cd;
Quit;
*/

/***************************/
/* add code for Batch observations */
Data rdbdata.BatchRow_NONINC;
 set rdbdata.PHCchildobs_NONINC;
	where child_obs_cd = 'ItemToRow';
run;

proc sql;
create table rdbdata.BatchItem_NONINC as 
select
	bat.PUBLIC_HEALTH_CASE_UID,
	bat.ROOT_OBS_UID,
	bat.root_obs_cd,
	bat.child_obs_uid,
	bat.child_obs_cd,
	bat.INVESTIGATION_FORM_CD,
	item.cd					as item_obs_cd,
	item.observation_uid	as item_obs_uid
from 
	rdbdata.BatchRow_NONINC as bat,
	nbs_ods.act_relationship as act,
	nbs_ods.observation	as item
where
	bat.child_obs_uid = act.target_act_uid
	and item.observation_uid = act.source_act_uid
	and act.type_cd = 'ItemToRow'
	and bat.child_obs_cd = 'ItemToRow'
;
quit;

PROC datasets library = work nolist;
delete PHCroot;
DELETE PHCroot_INIT;
delete PHCchild;
run;
quit;

