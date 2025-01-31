PROC SQL;
DROP TABLE NBS_RDB.AGGREGATE_REPORT_DATAMART; 
CREATE TABLE 
	SUMM_PHC_UID_INIT AS 
select 
	public_health_case_UID LENGTH =8 AS AGG_UID 'AGG_UID', 
	count_interval_cd, rpt_cnty_cd,
	add_time,
    add_user_id,
    last_chg_time,
    last_chg_user_id,
    record_status_cd,
    record_status_time
from 
	nbs_ods.public_health_case 
where 
	case_type_cd='A'  
and 
	record_status_cd='ACTIVE';

 CREATE TABLE SUMM_PHC_BASE1 AS 
	SELECT 	SUMM_PHC_UID_INIT.*, 
			CODE_SHORT_DESC_TXT AS REPORTING_COUNTY 'REPORTING_COUNTY'
	FROM	SUMM_PHC_UID_INIT 
			LEFT JOIN NBS_SRT.State_county_code_value CVG
			on CVG.CODE=SUMM_PHC_UID_INIT.rpt_cnty_cd;
QUIT;
PROC SQL;
CREATE TABLE 
	METADATA_TABLE AS
select 
	nbs_table_metadata_uid, 
	nbs_table_metadata.datamart_column_nm, 
	nbs_question.nbs_question_uid  
from  
	nbs_ods.nbs_table_metadata,  
	nbs_ods.nbs_question
where  
	nbs_question.nbs_question_uid =nbs_table_metadata.nbs_question_uid ;

CREATE TABLE 
	numerical_dataset AS
SELECT 
	nbs_case_answer_uid LENGTH =8,
	act_uid LENGTH =8 AS AGG_UID 'AGG_UID',
    answer_txt,
    nbs_case_answer.nbs_question_uid,
    nbs_question_version_ctrl_nbr,
    nbs_case_answer.nbs_table_metadata_uid,
	METADATA_TABLE.datamart_column_nm
from  
	METADATA_TABLE
	left outer join nbs_ods.nbs_case_answer 
on
	nbs_case_answer.nbs_table_metadata_uid=METADATA_TABLE.nbs_table_metadata_uid
order by act_uid;

CREATE table basic_agg_data
as
select 
	SUMM_PHC_BASE1.AGG_UID,
	SUMM_PHC_BASE1.REPORTING_COUNTY,
	INVESTIGATION.INVESTIGATION_key,
	INVESTIGATION.INV_COMMENTS	AS COMMENTS 'COMMENTS',
	Investigation.INV_LOCAL_ID AS REPORT_LOCAL_ID  'REPORT_LOCAL_ID',
	CONDITION.CONDITION_DESC AS CONDITION_DESCRIPTION  'CONDITION_DESCRIPTION',
	INVESTIGATION.CASE_RPT_MMWR_YR AS MMWR_YEAR  'MMWR_YEAR',
	INVESTIGATION.CASE_RPT_MMWR_WK AS MMWR_WEEK  'MMWR_WEEK',
	INVESTIGATION.INV_COMMENTS AS COMMENTS  'COMMENTS',
	EVENT_METRIC.ADD_TIME AS REPORT_CREATE_DATE  'REPORT_CREATE_DATE',
	EVENT_METRIC.ADD_USER_ID ,
	EVENT_METRIC.LAST_CHG_TIME AS REPORT_LAST_UPDATE_DATE  'REPORT_LAST_UPDATE_DATE',
	EVENT_METRIC.LAST_CHG_USER_ID
from 
	SUMM_PHC_BASE1, nbs_rdb.Investigation,nbs_rdb.EVENT_METRIC,nbs_rdb.CONDITION
where 
	SUMM_PHC_BASE1.AGG_UID=INVESTIGATION.CASE_UID
and  
	EVENT_METRIC.EVENT_UID=SUMM_PHC_BASE1.AGG_UID
and	 
	EVENT_METRIC.RECORD_STATUS_CD <> 'LOG_DEL'
and 
	CONDITION.CONDITION_cd=EVENT_METRIC.CONDITION_cd;


create table other_agg_answer_coded
as
select SUMM_PHC_BASE1.AGG_UID, 
	nbs_case_answer.answer_txt,
	nbs_question_uid,nbs_case_answer.act_uid, nbs_case_answer.record_status_cd
	from SUMM_PHC_BASE1 left outer join nbs_ods.nbs_case_answer
	on nbs_case_answer.act_uid=SUMM_PHC_BASE1.AGG_UID
where nbs_table_metadata_uid is null;

create table SUMM_PHC_UIDS_BASE as 
select DATAMART_COLUMN_NM, 
	other_agg_answer_coded.ANSWER_TXT, 
	other_agg_answer_coded.ACT_UID  LENGTH =8 AS AGG_UID 'AGG_UID' , 
	NBS_QUESTION.CODE_SET_GROUP_ID 
FROM
	NBS_ODS.NBS_QUESTION   inner JOIN  other_agg_answer_coded 
ON
	nbs_question.nbs_question_uid=other_agg_answer_coded.nbs_question_uid
inner join 
	nbs_ods.nbs_UI_Metadata 
on 
	nbs_question.nbs_question_uid=nbs_UI_Metadata.nbs_question_uid
where other_agg_answer_coded.record_status_cd ='ACTIVE';
CREATE TABLE MAPPED_CODEDSET AS 
select distinct a.CODE_SET_GROUP_ID, a.code_set_nm, class_cd 
from nbs_srt.codeset_group_metadata  a left outer join nbs_srt.codeset b on
a.code_set_nm= b.code_set_nm 
where b.ldf_picklist_ind_cd='Y'
order by class_cd;

CREATE TABLE BASE_CODED  AS 
	SELECT * FROM SUMM_PHC_UIDS_BASE 
		LEFT JOIN MAPPED_CODEDSET METADATA
		ON METADATA.CODE_SET_GROUP_ID=SUMM_PHC_UIDS_BASE.CODE_SET_GROUP_ID;

CREATE TABLE BASE_TRANSLATED AS 
	SELECT 	DATAMART_COLUMN_NM, ANSWER_TXT,  AGG_UID, 
			CODE,CODE_SHORT_DESC_TXT AS CODE_SHORT_DESC_TXT, BASE_CODED.CODE_SET_NM, BASE_CODED.CLASS_CD
	FROM	BASE_CODED 
			LEFT JOIN NBS_SRT.CODE_VALUE_GENERAL CVG
			ON CVG.CODE_SET_NM=BASE_CODED.CODE_SET_NM
			AND CVG.CODE=BASE_CODED.ANSWER_TXT
			AND BASE_CODED.CLASS_CD='code_value_general'
	ORDER BY AGG_UID, answer_txt;
QUIT;
data numerical_dataset;
set numerical_dataset ;
answer_txt1=INPUT( answer_txt,  COMMA20.);
run;
proc transpose 
	data=numerical_dataset 
	out=numerical_dataset_output;
BY 
	AGG_uid;
id 
	datamart_column_nm;
VAR 
	ANSWER_TXT1;
run;
data BASE_TRANSLATED;
set BASE_TRANSLATED;
if lengthn(CODE_SHORT_DESC_TXT)>0 then answer_txt= CODE_SHORT_DESC_TXT;
else answer_txt= answer_txt;
run;
proc sort data= BASE_TRANSLATED; by AGG_uid datamart_column_nm; 
run;
PROC TRANSPOSE DATA=  BASE_TRANSLATED OUT=  Base_AGG;
    BY AGG_UID;
	ID DATAMART_COLUMN_NM;
	VAR ANSWER_TXT;
RUN;

proc sql;
create table agg_init as select
* from  basic_agg_data left outer join 
 Base_AGG on Base_AGG.AGG_UID= basic_agg_data.AGG_UID
 left outer join numerical_dataset_output on numerical_dataset_output.AGG_UID=basic_agg_data.AGG_UID;

create table agg_event as 
select *,NOTIFICATION.NOTIFICATION_LOCAL_ID,
NOTIFICATION.NOTIFICATION_STATUS,
createUser.first_nm as createUser_first_nm, createUser.last_nm as createUser_last_nm,
editUser.first_nm as editUser_first_nm, editUser.last_nm as editUser_last_nm
from agg_init  left outer join nbs_rdb.notification_event
on agg_init.investigation_key= notification_event.investigation_key
left outer join nbs_rdb.notification
on notification_event.notification_key= notification.notification_key
left outer join nbs_rdb.user_profile createUser
on agg_init.ADD_USER_ID=createUser.NEDSS_ENTRY_ID
left outer join nbs_rdb.user_profile editUser
on agg_init.LAST_CHG_USER_ID=editUser.NEDSS_ENTRY_ID;
quit;
data agg_event;
set agg_event;
if lengthn(trim(createUser_first_nm))> 0 and lengthn(trim(createUser_last_nm))>0  then REPORT_CREATED_BY_USER = trim(createUser_last_nm)|| ',' ||trim(createUser_first_nm);
else if lengthn(trim(createUser_last_nm))>0 then REPORT_CREATED_BY_USER = trim(createUser_last_nm);
else if lengthn(trim(createUser_first_nm))>0 then REPORT_CREATED_BY_USER= trim(createUser_first_nm);
if lengthn(trim(editUser_first_nm))> 0 and lengthn(trim(editUser_last_nm))>0  then REPORT_LAST_UPDATED_BY_USER= trim(editUser_last_nm)|| ',' ||trim(editUser_first_nm);
else if lengthn(trim(editUser_last_nm))>0 then REPORT_LAST_UPDATED_BY_USER = trim(editUser_last_nm);
else if lengthn(trim(editUser_first_nm))>0 then REPORT_LAST_UPDATED_BY_USER = trim(editUser_first_nm);
drop createUser_first_nm createUser_last_nm editUser_first_nm editUser_last_nm 
FIRST_NM LAST_NM PATIENT_KEY CONDITION_KEY COUNT NEDSS_ENTRY_ID AGG_UIDINVESTIGATION_KEY AGG_UID INVESTIGATION_KEY 
NOTIFICATION_SENT_DT_KEY NOTIFICATION_SUBMIT_DT_KEY  NOTIFICATION_KEY NOTIFICATION_COMMENTS NOTIFICATION_SUBMITTED_BY LAST_UPD_TIME
LAST_CHG_USER_ID ADD_USER_ID _LABEL_ _NAME_;
run;

proc sql;
create table nbs_rdb.AGGREGATE_REPORT_DATAMART  AS SELECT * FROM  agg_event;
quit;
PROC DATASETS LIBRARY = WORK NOLIST;
DELETE 
SUMM_PHC_UID_INIT
SUMM_PHC_BASE1
METADATA_TABLE
NUMERICAL_DATASET
BASIC_AGG_DATA
OTHER_AGG_ANSWER_CODED
SUMM_PHC_UIDS_BASE
MAPPED_CODEDSET
BASE_CODED
BASE_TRANSLATED
AGG_INIT
RUN;
QUIT;
