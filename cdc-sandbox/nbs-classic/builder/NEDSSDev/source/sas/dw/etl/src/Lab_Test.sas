Data _Null_; 
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '--------The Start LAB--------';
run;

ods escapechar='\';
%MACRO ASSIGN_ADDITIONAL_KEY (DS, KEY);
 DATA &DS;
  IF &KEY=1 THEN OUTPUT;
  SET &DS;  
	&KEY+1;
	OUTPUT;
 RUN;
%MEND ASSIGN_KEY;

PROC SQL;
CREATE TABLE ACTIVITY_LOG_MASTER_LAST 
(ACTIVITY_LOG_MASTER_UID NUM,
START_DATE DATE, 
START_DATE2 DATE,
START_DATEINIT DATE,
COUNT NUM);
INSERT INTO ACTIVITY_LOG_MASTER_LAST( ACTIVITY_LOG_MASTER_UID, START_DATE,START_DATEINIT, START_DATE2) VALUES 
(1 , '01JUN1900'D,'01JUN1900'D, NULL);

UPDATE ACTIVITY_LOG_MASTER_LAST SET START_DATE2= (SELECT START_DATE FROM NBS_RDB.ACTIVITY_LOG_MASTER 
WHERE ACTIVITY_LOG_MASTER_UID= SELECT MAX(ACTIVITY_LOG_MASTER_UID) FROM NBS_RDB.ACTIVITY_LOG_MASTER);

CREATE TABLE ACTIVITY_LOG_MASTER  (ACTIVITY_LOG_MASTER_UID NUM,START_DATE DATE, END_DATE DATE);

CREATE TABLE ACTIVITY_LOG_DETAIL  (ACTIVITY_LOG_DETAIL_UID NUMERIC,	PROCESS_UID NUMERIC ,	
SOURCE_ROW_COUNT NUMERIC , ROW_COUNT_INSERT NUMERIC, ROW_COUNT_UPDATE NUMERIC,
SOURCE_ROW_COUNT_EXISTING NUMERIC ,	SOURCE_ROW_COUNT_NEW NUMERIC ,
DESTINATION_ROW_COUNT NUMERIC ,
START_DATE DATE, END_DATE DATE,
START_DATE2 DATE,
ADMIN_COMMENT VARCHAR(200), ACTIVITY_LOG_MASTER_UID NUMERIC);
INSERT INTO ACTIVITY_LOG_DETAIL( ACTIVITY_LOG_DETAIL_UID, PROCESS_UID,SOURCE_ROW_COUNT, DESTINATION_ROW_COUNT,
START_DATE,END_DATE, ACTIVITY_LOG_MASTER_UID) VALUES (1 , 1, NULL, NULL, NULL, NULL, NULL);
UPDATE ACTIVITY_LOG_DETAIL SET PROCESS_UID= (select process_uid from nbs_rdb.etl_process where process_name='LAB_TEST');
UPDATE ACTIVITY_LOG_DETAIL SET ACTIVITY_LOG_MASTER_UID=  (SELECT MAX(ACTIVITY_LOG_MASTER_UID) FROM NBS_RDB.ACTIVITY_LOG_MASTER);
UPDATE ACTIVITY_LOG_DETAIL SET ACTIVITY_LOG_DETAIL_UID= (SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1 ;
UPDATE ACTIVITY_LOG_DETAIL SET START_DATE2= (SELECT START_DATE  FROM NBS_RDB.ACTIVITY_LOG_DETAIL WHERE 
PROCESS_UID= (select process_uid from nbs_rdb.etl_process where process_name='LAB_TEST')
having ACTIVITY_LOG_DETAIL_UID=MAX(ACTIVITY_LOG_DETAIL_UID) 
);
QUIT;
DATA ACTIVITY_LOG_DETAIL; 
SET ACTIVITY_LOG_DETAIL;
START_DATE=datetime() ;
RUN;
/*
proc sql;
delete * from nbs_rdb.s_updated_lab;
create table s_updated_lab as select CTRL_CD_DISPLAY_FORM,
observation_uid,last_chg_time from nbs_ods.observation where 
CTRL_CD_DISPLAY_FORM in ('LabReport','MorbReport') and obs_domain_cd_st_1='Order'
and last_chg_time >(SELECT MAX(ACTIVITY_LOG_DETAIL.START_DATE2) FROM  ACTIVITY_LOG_DETAIL)
and  last_chg_time <(SELECT MAX(ACTIVITY_LOG_DETAIL.START_DATE) FROM  ACTIVITY_LOG_DETAIL)
UNION 
select CTRL_CD_DISPLAY_FORM,
EVENT_UID as observation_uid 'observation_uid',event_last_chg_time AS  last_chg_time 'last_chg_time' from NBS_RDB.ETL_MISSING_RECORD where PROCESSED_INDICATOR =0;
quit;
proc sql;
create table s_edx_document as select EDX_Document_uid, act_uid, add_time from nbs_ods.EDX_Document, s_updated_lab where  EDX_Document.act_uid=s_updated_lab.observation_Uid order by add_time desc;

create table l_observation_map as select observation_uid, act1.type_cd,act2.type_cd,act3.type_cd,
act1.source_act_uid as source_act_uid1 'source_act_uid1' ,act2.source_act_uid as source_act_uid2 'source_act_uid2',act3.source_act_uid as source_act_uid3 'source_act_uid3',
act4.source_act_uid as source_act_uid4 'source_act_uid4',
act1.target_act_uid as target_act_uid1 'target_act_uid1', act2.target_act_uid as target_act_uid2 'target_act_uid2', act3.target_act_uid as target_act_uid3 'target_act_uid3', 
act4.target_act_uid as target_act_uid4 'target_act_uid4'
from s_updated_lab 
left outer join nbs_ods.act_relationship act1 ON  s_updated_lab.observation_uid= act1.target_act_uid 
left outer join nbs_ods.act_relationship act2 ON act1.source_act_uid=act2.target_act_uid  
left outer join nbs_ods.act_relationship act3 ON act2.source_act_uid=act3.target_act_uid
left outer join nbs_ods.act_relationship act4 ON act3.source_act_uid=act4.target_act_uid
order by observation_uid;


create table updated_observation_map as select * from nbs_rdb.l_observation_map where 
observation_uid in (select observation_uid from l_observation_map);
create index observation_uid on updated_observation_map(observation_uid);
quit;

proc sql noprint; create view updated_observation_map_v as select distinct observation_uid from updated_observation_map; 
quit;

%macro ScrubTable(TableName);

data _NULL_;
set updated_observation_map_v;
length sql $4096;
sql = "proc sql; DELETE FROM &TAbleName WHERE observation_uid = " || compress(put(observation_uid, 15.)) ||";quit;";
call execute(sql);
run;

%mend ScrubTable;

%ScrubTable(nbs_rdb.l_observation_map);


%DBLOAD (s_updated_lab, s_updated_lab);
%DBLOAD (l_observation_map, l_observation_map);
proc sql;
create table updated_observation1 as select distinct 
observation_uid from l_observation_map;

create table updated_observation2 as select distinct 
source_act_uid1 as observation_uid 'observation_uid' from l_observation_map;

create table updated_observation3 as select distinct 
source_act_uid2 as observation_uid 'observation_uid' from l_observation_map;

create table updated_observation4 as select distinct 
source_act_uid3 as observation_uid 'observation_uid' from l_observation_map;

create table updated_observation5 as select distinct 
source_act_uid4 as observation_uid 'observation_uid' from l_observation_map;

create table updated_observation as 
select observation_uid from updated_observation1
union 
select observation_uid from updated_observation2
union 
select observation_uid from updated_observation3
union 
select observation_uid from updated_observation4
union 
select observation_uid from updated_observation5;
quit; 
proc sql;
create table updated_observation_1 as select distinct 
observation_uid from updated_observation_map;

create table updated_observation_2 as select distinct 
source_act_uid1 as observation_uid 'observation_uid' from updated_observation_map;

create table updated_observation_3 as select distinct 
source_act_uid2 as observation_uid 'observation_uid' from updated_observation_map;

create table updated_observation_4 as select distinct 
source_act_uid3 as observation_uid 'observation_uid' from updated_observation_map;

create table updated_observation_5 as select distinct 
source_act_uid4 as observation_uid 'observation_uid' from updated_observation_map;

create table updated_observation_List as 
select observation_uid from updated_observation_1
union 
select observation_uid from updated_observation_2
union 
select observation_uid from updated_observation_3
union 
select observation_uid from updated_observation_4
union 
select observation_uid from updated_observation_5;
create index observation_uid on updated_observation_List(observation_uid);
*/

PROC  SQL;
create table updated_observation_List as select * from nbs_rdb.updated_observation_List;
create table updated_lab_test_list as select lab_test_uid, lab_test_key from nbs_rdb.lab_test where lab_test_uid in (select observation_uid from nbs_rdb.updated_observation_List);
quit;
PROC  SQL;
create table updated_LAB_RPT_USER_COMMENT as select user_comment_key from nbs_rdb.LAB_RPT_USER_COMMENT where lab_test_key in (select lab_test_key from updated_observation_List);
create table updt_Test_Result_Grouping_LIST as select lab_test_uid from nbs_rdb.Test_Result_Grouping where lab_test_uid in (select observation_uid from updated_observation_List);
create table updt_Lab_Result_Val_list as select lab_test_uid from nbs_rdb.Lab_Result_Val where lab_test_uid in (select observation_uid from updated_observation_List);
create table updated_lab_Test_result_list as select lab_test_key from nbs_rdb.lab_test_RESULT where lab_test_key in (select lab_test_key from updated_observation_List);
create table updT_Result_Comment_Grp_LIST as select lab_test_uid from nbs_rdb.RESULT_COMMENT_GROUP where lab_test_uid in (select observation_uid from updated_observation_List);
create table updt_Lab_Result_Comment_list as select lab_test_uid from nbs_rdb.Lab_Result_Comment where lab_test_uid in (select observation_uid from updated_observation_List);
create table rdbdata.updated_lab_test_list as select * from updated_lab_test_list;
QUIT;
/*
ScrubTable
%macro ScrubTable(TableName);

data _NULL_;
set updated_observation_List;
length sql $4096;
sql = "proc sql; DELETE FROM &TAbleName WHERE Lab_Test_UID = " || put(observation_uid, 15.) || " and lab_test_uid is not null;quit;";
call execute(sql);
run;

%mend ;

%ScrubTable(nbs_rdb.Lab_Result_Val);
%ScrubTable(nbs_rdb.lab_test_RESULT);
%ScrubTable(nbs_rdb.Test_Result_Grouping);
%ScrubTable(nbs_rdb.Lab_Result_Comment);
%ScrubTable(nbs_rdb.RESULT_COMMENT_GROUP);
%ScrubTable(nbs_rdb.LAB_RPT_USER_COMMENT);
%ScrubTable(nbs_rdb.lab_test);
%ScrubTable(nbs_rdb.l_lab_test);
*/

 

proc sql;
create table s_edx_document as select EDX_Document_uid, act_uid, add_time from nbs_ods.EDX_Document, nbs_rdb.s_updated_lab where  EDX_Document.act_uid=s_updated_lab.observation_Uid order by add_time desc;
quit;

proc sql;
create table updated_participant as select act_uid,
subject_entity_uid, type_cd,act_class_cd,record_status_cd,subject_class_cd,
observation_uid
from nbs_ods.participation, UPDATED_OBSERVATION_LIST obs 
where 
participation.act_uid=obs.observation_uid;
quit;
Proc SQL;
 Create Table merged_provider as
  Select distinct a.provider_first_name, a.provider_last_name, a.provider_uid, a.provider_key,
         b.root_extension_txt as person_id_val,
         b.type_cd as patient_id_type,
		 put(b.type_cd,$EI_TYPE.) as person_id_type_desc,   /* code_set_nm = EI_TYPE */
         b.assigning_authority_cd as person_id_assign_auth_cd,
         /*b.assigning_authority_desc_txt as person_id_assign_auth_desc,
         b.valid_to_time as person_id_expire_dt,
         b.as_of_date as person_id_as_of_dt,*/
		 b.record_status_cd
   From updated_participant PART LEFT JOIN
	nbs_rdb.d_provider a 
	ON	PART.SUBJECT_ENTITY_UID=a.provider_uid
	LEFT JOIN nbs_ods.entity_id b
    On a.provider_uid = b.entity_uid;
Quit;
data filter_participants (rename = (subject_entity_uid = provider_uid));
set updated_participant;
where type_cd in ('ENT', 'ASS', 'VRF')
	and act_class_cd = 'OBS'
	and record_status_cd ='ACTIVE'
	and subject_class_cd = 'PSN'; 
run; 
proc sql;
create table participants as select * from 
filter_participants, merged_provider
where filter_participants.provider_uid= merged_provider.provider_uid;
quit;


/*  */
/* proc sql; */
/* create table Lab_Testinit_a as  */
/* select	 */
/* 	obs.observation_uid			'LAB_TEST_UID' as lab_test_uid, */
/* 	1							'PARENT_TEST_PNTR' as parent_test_pntr,	 */
/* 	obs.observation_uid			'LAB_TEST_PNTR' as lab_test_pntr, */
/* 	obs.activity_to_time		'LAB_TEST_DT' as lab_test_dt, */
/* 	obs.method_cd				'TEST_METHOD_CD' as test_method_cd, */
/* 	1							'ROOT_ORDERED_TEST_PNTR' as root_ordered_test_pntr, */
/* 	obs.method_desc_txt			'TEST_METHOD_CD_DESC' as test_method_cd_desc, */
/* 	obs.priority_cd				'PRIORITY_CD' as priority_cd, */
/* 	obs.target_site_cd			'SPECIMEN_SITE' as specimen_site, */
/* 	obs.target_site_desc_txt  	'SPECIMEN_SITE_DESC' as SPECIMEN_SITE_desc,  */
/* 	obs.txt						'CLINICAL_INFORMATION'	as Clinical_information, */
/* 	obs.obs_domain_cd_st_1 		'LAB_TEST_TYPE' as Lab_Test_Type, */
/* 	obs.cd					 	'LAB_TEST_CD' as Lab_test_cd, 	 */
/* 	obs.Cd_desc_txt				'LAB_TEST_CD_DESC' as Lab_test_cd_desc, */
/* 	obs.Cd_system_cd			'LAB_TEST_CD_SYS_CD' as Lab_test_cd_sys_cd, */
/* 	obs.Cd_system_desc_txt		'LAB_TEST_CD_SYS_NM' as Lab_test_cd_sys_nm, */
/* 	obs.Alt_cd					'ALT_LAB_TEST_CD' as Alt_lab_test_cd, */
/* 	obs.Alt_cd_desc_txt			'ALT_LAB_TEST_CD_DESC' as Alt_lab_test_cd_desc, */
/* 	obs.Alt_cd_system_cd		'ALT_LAB_TEST_CD_SYS_CD' as Alt_lab_test_cd_sys_cd, */
/* 	obs.Alt_cd_system_desc_txt 	'ALT_LAB_TEST_CD_SYS_NM' as Alt_lab_test_cd_sys_nm, */
/* 	obs.effective_from_time	 	'SPECIMEN_COLLECTION_DT' as specimen_collection_dt, */
/* 	obs.local_id				'LAB_RPT_LOCAL_ID' as lab_rpt_local_id, */
/* 	obs.shared_ind				'LAB_RPT_SHARE_IND' as lab_rpt_share_ind, */
/* 	obs.PROGRAM_JURISDICTION_OID 'OID' as oid,	 */
/*     obs.record_status_cd         'record_status_cd' as record_status_cd,	 */
/* 	obs.record_status_cd         'record_status_cd' as record_status_cd_for_result, */
/*  */
/* 	obs.STATUS_CD	   		 	'LAB_RPT_STATUS' as lab_rpt_status, */
/* 	obs.ADD_TIME				'LAB_RPT_CREATED_DT' as LAB_RPT_CREATED_DT, */
/* 	obs.ADD_USER_ID  		 	'LAB_RPT_CREATED_BY' as LAB_RPT_CREATED_BY,  */
/* 	obs.rpt_to_state_time  		'LAB_RPT_RECEIVED_BY_PH_DT' as LAB_RPT_RECEIVED_BY_PH_DT,  */
/* 	obs.LAST_CHG_TIME 			'LAB_RPT_LAST_UPDATE_DT' as LAB_RPT_LAST_UPDATE_DT,  */
/* 	obs.LAST_CHG_USER_ID		'LAB_RPT_LAST_UPDATE_BY' as LAB_RPT_LAST_UPDATE_BY,  */
/* 	obs.electronic_ind			'ELR_IND' as ELR_IND,  */
/* 	obs.jurisdiction_cd			'JURISDICTION_CD' as Jurisdiction_cd, */
/* 	put(obs.jurisdiction_cd, $JURCODE.)  as JURISDICTION_NM,	  */
/* 	obs.observation_uid			'LAB_RPT_UID' as Lab_Rpt_Uid, */
/* 	obs.PROG_AREA_CD, */
/* 	obs.activity_to_time   	 	as resulted_lab_report_date,			 */
/* 	obs.activity_to_time   	 	as sus_lab_report_date, */
/* 	loinc_con.condition_cd as condition_cd, */
/* 	cvg.code_short_desc_txt		'LAB_TEST_STATUS' as lab_test_status, */
/* 	obs.PROCESSING_DECISION_CD  */
/* from updated_observation_List left outer join nbs_ods.observation as obs */
/* 	on updated_observation_List.OBSERVATION_UID=obs.OBSERVATION_UID */
/* 	left join nbs_srt.loinc_condition as loinc_con */
/* 		on obs.cd = loinc_con.loinc_cd */
/* 		left join 	nbs_srt.code_value_general  as cvg */
/* 		on obs.status_cd = cvg.code */
/* 	  	and cvg.code_set_nm = 'ACT_OBJ_ST' */
/* 	where obs.obs_domain_cd_st_1 in ('Order','Result','R_Order','R_Result', 'I_Order', 'I_Result', 'Order_rslt')  */
/* 		and (obs.CTRL_CD_DISPLAY_FORM = 'LabReport' or obs.CTRL_CD_DISPLAY_FORM = 'LabReportMorb' or obs.CTRL_CD_DISPLAY_FORM is null) */
/*  */
/* order by obs.OBSERVATION_UID; */
/* quit;  */
/*  */


/* Texas custom Code execution moved to SQL Server for performance tuning 08/20/2020 */
PROC SQL;
DROP TABLE NBS_RDB.SAS_Lab_Testinit_a;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
select	
	obs.observation_uid		as lab_test_uid,
	1						as parent_test_pntr,	
	obs.observation_uid		as lab_test_pntr,
	obs.activity_to_time	as lab_test_dt,
	obs.method_cd			as test_method_cd,
	1						as root_ordered_test_pntr,
	obs.method_desc_txt		as test_method_cd_desc,
	obs.priority_cd			as priority_cd,
	obs.target_site_cd		as specimen_site,
	obs.target_site_desc_txt 	as SPECIMEN_SITE_desc, 
	obs.txt					as Clinical_information,
	obs.obs_domain_cd_st_1 	as Lab_Test_Type,
	obs.cd					as Lab_test_cd, 	
	obs.Cd_desc_txt			as Lab_test_cd_desc,
	obs.Cd_system_cd		as Lab_test_cd_sys_cd,
	obs.Cd_system_desc_txt	as Lab_test_cd_sys_nm,
	obs.Alt_cd				as Alt_lab_test_cd,
	obs.Alt_cd_desc_txt		as Alt_lab_test_cd_desc,
	obs.Alt_cd_system_cd	as Alt_lab_test_cd_sys_cd,
	obs.Alt_cd_system_desc_txt 	as Alt_lab_test_cd_sys_nm,
	obs.effective_from_time		as specimen_collection_dt,
	obs.local_id			as lab_rpt_local_id,
	obs.shared_ind			as lab_rpt_share_ind,
	obs.PROGRAM_JURISDICTION_OID as oid,	
   	obs.record_status_cd    as record_status_cd,	
	obs.record_status_cd    as record_status_cd_for_result,
	obs.STATUS_CD	   		as lab_rpt_status,
	obs.ADD_TIME			as LAB_RPT_CREATED_DT,
	obs.ADD_USER_ID  		as LAB_RPT_CREATED_BY, 
	obs.rpt_to_state_time  	as LAB_RPT_RECEIVED_BY_PH_DT, 
	obs.LAST_CHG_TIME 		as LAB_RPT_LAST_UPDATE_DT, 
	obs.LAST_CHG_USER_ID	as LAB_RPT_LAST_UPDATE_BY, 
	obs.electronic_ind		as ELR_IND, 
	obs.jurisdiction_cd		as Jurisdiction_cd,
	jurs_code.code_short_desc_txt AS JURISDICTION_NM,	/*TO_CHAR(obs.jurisdiction_cd) AS JURISDICTION_NM,*/ /*put(obs.jurisdiction_cd, $JURCODE.) as JURISDICTION_NM,*/
	obs.observation_uid		as Lab_Rpt_Uid,
	/*obs.PROG_AREA_CD,*/
	obs.activity_to_time   	 as resulted_lab_report_date,			
	obs.activity_to_time   	 as sus_lab_report_date,
	loinc_con.condition_cd as condition_cd,
	cvg.code_short_desc_txt	as lab_test_status,
	obs.PROCESSING_DECISION_CD 
INTO SAS_Lab_Testinit_a	
from updated_observation_List 
left outer join nbs_odse.dbo.observation obs ON updated_observation_List.OBSERVATION_UID=obs.OBSERVATION_UID
left join nbs_srte.dbo.loinc_condition loinc_con ON obs.cd = loinc_con.loinc_cd
left join nbs_srte.dbo.code_value_general cvg ON  obs.status_cd = cvg.code and cvg.code_set_nm = 'ACT_OBJ_ST'
left join nbs_srte..Jurisdiction_code jurs_code ON jurs_code.code= obs.jurisdiction_cd
	where obs.obs_domain_cd_st_1 in ('Order','Result','R_Order','R_Result', 'I_Order', 'I_Result', 'Order_rslt') 
	and (obs.CTRL_CD_DISPLAY_FORM = 'LabReport' or obs.CTRL_CD_DISPLAY_FORM = 'LabReportMorb' or obs.CTRL_CD_DISPLAY_FORM is null)
order by obs.OBSERVATION_UID) by sql;
disconnect from sql; 
QUIT;

proc sql;
create table Lab_Testinit_a as select * from NBS_RDB.SAS_Lab_Testinit_a order by lab_test_uid;
quit;

/* --------------------------- 08/20/2020 ------------------------------------ */


PROC SORT DATA=s_edx_document NODUPKEY OUT=s_edx_document; BY act_uid; RUN;
proc sql;
create table s_edx_document as select EDX_Document_uid, act_uid, add_time , put(datepart(add_time),mmddyy10.) as add_timeSt from s_edx_document;
quit;
options fmtsearch=(nbsfmt);
DATA s_edx_document;
set s_edx_document;
LENGTH document_link $500;
document_link =compbl('<a href="#" '|| compress('onClick="window.open(''/nbs/viewELRDocument.do?method=viewELRDocument&documentUid=' 
|| EDX_Document_uid || ' &dateReceivedHidden=' || add_timeSt ||''' ,''DocumentViewer'',''width=900,height=800,left=0,top=0,
menubar=no,titlebar=no,toolbar=no,scrollbars=yes,location=no'');">View Lab Document</a>'));
run;
proc sql;
create table Lab_Testinit as select a.*, b.document_link 
from Lab_Testinit_a a left outer join s_edx_document b
on a.lab_test_uid=b.act_uid;
quit;

proc datasets memtype=DATA;
   delete s_edx_document Lab_Testinit_a;
quit;
proc sql;
create table lab_test_mat_init as select 
	obs.observation_uid			'LAB_TEST_UID' as lab_test_uid,
	mat.cd						'SPECIMEN_SRC' as specimen_src,
	mat.nm						'SPECIMEN_NM' as specimen_nm,
	mat.description				'SPECIMEN_DETAILS' as Specimen_details,
	mat.qty						'SPECIMEN_COLLECTION_VOL' as Specimen_collection_vol,
	mat.qty_unit_cd				'SPECIMEN_COLLECTION_VOL_UNIT' as Specimen_collection_vol_unit,
	mat.Cd_desc_txt				'SPECIMEN_DESC' as Specimen_desc,
	mat.Risk_cd					'DANGER_CD' as Danger_cd,
	mat.Risk_desc_txt			'DANGER_CD_DESC' as Danger_cd_desc
	from updated_observation_List obs inner join updated_participant	as par
		on obs.observation_uid = par.act_uid
		and par.type_cd ='SPC'
		and par.subject_class_cd = 'MAT'
		and par.act_class_cd = 'OBS'
	inner join nbs_ods.material	as mat
		on par.subject_entity_uid = mat.material_uid order by obs.OBSERVATION_UID;
quit;
PROC SQL;
CREATE TABLE OBS_REASON AS SELECT 
obs.observation_uid,
rsn.reason_desc_txt,
rsn.reason_cd 
FROM updated_observation_List obs LEFT JOIN  nbs_ods.observation_reason	as rsn
		on obs.observation_uid= rsn.observation_uid
order by obs.observation_uid;
QUIT;
DATA OBS_REASON;
SET OBS_REASON;
LENGTH REASON_FOR_TEST_DESC $4000;
LENGTH REASON_FOR_TEST_CD $2000;
 
DO UNTIL(LAST.OBSERVATION_UID);
	SET OBS_REASON;
	BY OBSERVATION_UID NOTSORTED;
if(LENGTHN(COMPRESS(reason_desc_txt))> 0) and (LENGTHN(COMPRESS(reason_cd))> 0)
and (LENGTHN(COMPRESS(REASON_FOR_TEST_DESC))= 0) 
	then REASON_FOR_TEST_DESC= COMPRESS( reason_cd|| '(' || reason_desc_txt|| ')' || REASON_FOR_TEST_DESC) ;
else if(LENGTHN(COMPRESS(reason_desc_txt))> 0) and (LENGTHN(COMPRESS(reason_cd))> 0)
and (LENGTHN(COMPRESS(REASON_FOR_TEST_DESC))> 0) 
	then REASON_FOR_TEST_DESC= COMPRESS(reason_cd|| '(' || reason_desc_txt|| ')|'|| REASON_FOR_TEST_DESC );

if(LENGTHN(COMPRESS(reason_cd))> 0) and (LENGTHN(COMPRESS(REASON_FOR_TEST_CD))= 0) 
	then REASON_FOR_TEST_CD= COMPRESS(reason_cd);
else if(LENGTHN(COMPRESS(reason_cd))> 0) and (LENGTHN(COMPRESS(REASON_FOR_TEST_CD))> 0) 
	then REASON_FOR_TEST_CD= COMPRESS(reason_cd|| '|' || REASON_FOR_TEST_CD );
END;
RUN;
proc sql;
create table lab_test_oth as select 
	obs.observation_uid			'LAB_TEST_UID' as lab_test_uid,
	oin.interpretation_cd		'INTERPRETATION_FLAG' as interpretation_flg, 
	ai.root_extension_txt 		'ACCESSION_NBR' as ACCESSION_NBR, 
	obs.REASON_FOR_TEST_DESC, 
	obs.REASON_FOR_TEST_CD,
	/*rsn.reason_desc_txt			'REASON_FOR_TEST_DESC' as reason_for_test_desc,
	rsn.reason_cd				'REASON_FOR_TEST_CD' as reason_for_test_cd,
	
	SPECIMEN_ADD_TIME,
	SPECIMEN_LAST_CHANGE_TIME,
	*/
	
	trim(par1.provider_first_name)||' '||trim(par1.provider_last_name) as transcriptionist_name,
	par1.person_id_assign_auth_cd 'transcriptionist_ass_auth_cd' as transcriptionist_ass_auth_cd,
	par1.person_id_type_desc 'Transcriptionist_Ass_Auth_Type' as Transcriptionist_Ass_Auth_Type,
	par1.person_id_val 'transcriptionist_id' as transcriptionist_id,
	trim(par2.provider_first_name)||' '||trim(par2.provider_last_name) as Assistant_Interpreter_Name,
	par2.person_id_assign_auth_cd 'Assistant_inter_ass_auth_cd' as Assistant_inter_ass_auth_cd,
	par2.person_id_type_desc 'Assistant_inter_ass_auth_type' as Assistant_inter_ass_auth_type,
	par2.person_id_val 'Assistant_interpreter_id' as Assistant_interpreter_id,
	trim(par3.provider_first_name)||' '||trim(par3.provider_last_name) as result_interpreter_name

	/*obs.status_cd				,*/	 
	/*oin.interpretation_desc_txt,*/	
	/*put(mat.cd specmn_src $lab165f.)		as s,*/	
	/*obs.cd_desc_txt as ordered_test_name,*/
	from OBS_REASON obs left join 	nbs_ods.act_id  as ai
		on obs.observation_uid = ai.ACT_UID
	  	and ai.type_cd='FN'
		/*and ai.act_id_seq=2*/
	left join nbs_ods.observation_interp	as oin
		on obs.observation_uid = oin.observation_uid
		and oin.INTERPRETATION_CD ~=' '
	/*left join nbs_ods.observation_reason	as rsn
		on obs.observation_uid= rsn.observation_uid*/
	/*get transcriptionist*/
	left join participants as par1
	on obs.observation_uid = par1.act_uid
	and par1.type_cd = 'ENT'	
	/*get assistant_interpreter*/
	left join participants as par2
	on obs.observation_uid= par2.act_uid
	and par2.type_cd = 'ASS'
	/*get result_interpreter*/
	left join participants as par3
	on obs.observation_uid= par3.act_uid
	and par3.type_cd = 'VRF'
order by obs.OBSERVATION_UID;
quit;
data lab_test1 output;
merge lab_test_oth lab_test_mat_init Lab_Testinit;
by lab_test_uid;
run;
proc datasets memtype=DATA;
   delete lab_test_mat_init Lab_Testinit OBS_REASON lab_test_oth;
quit;

data LabReportMorb (keep=lab_rpt_uid);
set Lab_Test1;
where  lab_test_type in ('Order', 'Result', 'Order_rslt') and  oid =4;
run;

proc sql;
create table Morb_OID as 
select l.*, o.PROGRAM_JURISDICTION_OID 'Morb_oid' as Morb_oid
from LabReportMorb l, nbs_ods.act_relationship ar, nbs_ods.observation o 
where ar.source_act_uid = l.lab_rpt_uid 
	and ar.target_act_uid = o.observation_uid 
	and o.CTRL_CD_DISPLAY_FORM = 'MorbReport';

proc sort data = Morb_oid;by lab_rpt_uid;run;
proc sort data =  Lab_Test1;by lab_rpt_uid;run;

data lab_test1;
	merge Morb_OID Lab_Test1;
	by lab_rpt_uid; 
run;
data lab_test1;
	set lab_test1;
	if morb_oid~=. then oid = morb_oid; 
run;
data lab_test1 (drop=morb_oid);
set lab_test1;
PROCESSING_DECISION_DESC=PUT(PROCESSING_DECISION_CD,$APROCDNF.);run;
/**********************************/
/* update parent of R_Result */
proc sql;
create table R_Result_to_R_Order as
select 	act.source_act_uid			as lab_test_uid label='R_Result_uid',
		act.target_act_uid			as parent_test_pntr label='R_Order_uid'

from 	Lab_Test1 as tst,
		/*R_Result_to_R_Order*/
		nbs_ods.act_relationship	as act
where	 tst.lab_test_uid = act.source_act_uid
		/*and act.type_cd = 'COMP'*/
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
 		and tst.lab_test_type IN ('R_Result', 'I_Result')
		

;
quit;
proc sort data = R_Result_to_R_Order;
by lab_test_uid;
run;
/* update root of R_Result */
proc sql;
create table R_Result_to_R_Order_to_Order as
select 	tst.*,
		coalesce(tst2.record_status_cd, tst3.record_status_cd, tst4.record_status_cd )as record_status_cd_for_result_drug label='record_status_cd_for_result_drug',
		act2.target_act_uid				as root_thru_srpt, 	
		act3.target_act_uid				as root_thru_refr,
		coalesce(act2.target_act_uid, 	act4.target_act_uid)
									as root_ordered_test_pntr label='Order uid'

from 	R_Result_to_R_Order	as tst
	/*R_Order to Order */
	left join	nbs_ods.act_relationship as act2
		on tst.parent_test_pntr = act2.source_act_uid
		and act2.type_cd = 'SPRT'
		and act2.target_class_cd = 'OBS'
		and act2.source_class_cd ='OBS'
		left join 	Lab_Test1 as tst2
	    on   tst2.lab_test_uid = act2.target_act_uid

	/*R_Order to Result to Order */
	left join	nbs_ods.act_relationship as act3
		on tst.parent_test_pntr  = act3.source_act_uid
		and act3.type_cd = 'REFR'
		and act3.target_class_cd = 'OBS'
		and act3.source_class_cd ='OBS'
		left join 	Lab_Test1 as tst3
	    on   tst3.lab_test_uid = act3.target_act_uid
	left join	nbs_ods.act_relationship as act4
		on act3.target_act_uid = act4.source_act_uid
		and act4.type_cd = 'COMP'
		and act4.target_class_cd = 'OBS'
		and act4.source_class_cd ='OBS'
	left join 	Lab_Test1 as tst4
	    on   tst4.lab_test_uid = act4.target_act_uid
;
quit;


proc sort data = R_Result_to_R_Order_to_Order;
by lab_test_uid;run;
proc sort data = Lab_Test1;
by lab_test_uid;
run;
data Lab_Test1;
merge Lab_Test1 R_Result_to_R_Order_to_Order
	(keep=lab_test_uid parent_test_pntr root_ordered_test_pntr
     record_status_cd_for_result_drug)
	;
by lab_test_uid;
run;

/* update root order test and parent of R_Order */

proc sql;
create table R_Order_to_Result as
select 	act.source_act_uid			as lab_test_uid label='R_Order_uid',
		act.target_act_uid			as parent_test_pntr label='Result_uid',
		act2.target_act_uid			as root_ordered_test_pntr label='Order uid',
		tst2.record_status_cd as record_status_cd label='record_status_cd_for_result'
from 	Lab_Test1 as tst,
		Lab_Test1 as tst2,
		/*R_Order_to_Result */
		nbs_ods.act_relationship	as act,
		/*Result to Order */
		nbs_ods.act_relationship	as act2
where tst.lab_test_type IN( 'R_Order','I_Order')
		and tst.lab_test_uid = act.source_act_uid
		and act.type_cd = 'REFR'
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
		and act.target_act_uid = act2.source_act_uid
		and act2.type_cd = 'COMP'
		and act2.target_class_cd ='OBS'
		and act2.source_class_cd ='OBS'
		and tst2.lab_test_uid = act2.target_act_uid
;
quit;
proc sort data = R_Order_to_Result;
by lab_test_uid;
data Lab_Test1;
merge Lab_Test1 R_Order_to_Result;
by lab_test_uid;
run;

/* update root and parent of Result */

proc sql;
create table Result_to_Order as
select 	act.source_act_uid			as lab_test_uid label='Result_uid',
		act.target_act_uid			as parent_test_pntr label='Order_uid',
		act.target_act_uid			as root_ordered_test_pntr label='Order uid',
		tst2.record_status_cd as record_status_cd label='record_status_cd_for_result'
from 	Lab_Test1 as tst,
		Lab_Test1 as tst2,
		nbs_ods.act_relationship	as act
where	tst.lab_test_uid = act.source_act_uid
		and tst.lab_test_type in ('Result', 'Order_rslt')
		and act.type_cd = 'COMP'
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
		and tst2.lab_test_uid = act.target_act_uid
;
quit;

proc sort data = Result_to_Order;
by lab_test_uid;


data Lab_Test1;
merge Lab_Test1 Result_to_Order;
by lab_test_uid;
run;
proc datasets memtype=DATA;
   delete Result_to_Order R_Order_to_Result R_Result_to_R_Order_to_Order;
quit;


/*	update root and parent of Order, which is itself*/
data Lab_Test1;
set Lab_Test1;
	if lab_test_type = 'Order' then do;
		parent_test_pntr = lab_test_pntr;
		root_ordered_test_pntr = lab_test_pntr;
	end;
run;
/****creating Root_Ordered_Test_Nm column in Lab_Test***/
proc sql;
create index root_ordered_test_pntr on lab_test1(root_ordered_test_pntr);
create table Lab_Test2 as 
select tst.*, obs.Cd_desc_txt 'Root_Ordered_Test_Nm' as Root_ordered_test_nm

from nbs_ods.observation as obs, lab_test1 as tst
where tst.root_ordered_test_pntr = obs.observation_uid;
Quit; 
PROC SQL;
DROP TABLE NBS_RDB.SAS_Lab_Test2;
DROP TABLE NBS_RDB.SAS_Lab_Test3;
DROP TABLE NBS_RDB.SAS_lab_test4;
QUIT;
PROC SQL;
CREATE TABLE NBS_RDB.SAS_Lab_Test2 AS SELECT * FROM lab_test2;
QUIT;
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
/******creating Lab_Test column in Lab_Test***/
select tst.*, obs.Cd_desc_txt  as Parent_test_nm
into SAS_Lab_Test3
from nbs_odse.dbo.observation as obs, rdb.dbo.SAS_Lab_Test2 as tst
where tst.parent_test_pntr = obs.observation_uid;
) by sql;
disconnect from sql; 
Quit;
/*Setting SPECIMEN_ADD_TIME &  SPECIMEN_LAST_CHANGE_TIME*/
/*PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
execute (CREATE INDEX lab_test_uid ON SAS_Lab_Test3(lab_test_uid)) by sql;
disconnect from sql;
QUIT;*/
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
 select tst.*,
	obs.add_time as SPECIMEN_ADD_TIME,
	obs1.last_chg_time as SPECIMEN_LAST_CHANGE_TIME
	into SAS_lab_test4
 from rdb.dbo.SAS_Lab_Test3 as tst
 	left join nbs_odse.dbo.observation as obs
	on tst.lab_test_uid = obs.observation_uid
	and obs.obs_domain_cd_st_1 = 'Order'  
	
	left join nbs_odse.dbo.observation as obs1
	on tst.lab_test_uid = obs1.observation_uid
	and obs1.obs_domain_cd_st_1 = 'Order';
) by sql;
disconnect from sql; 
quit;
proc sql;
create table lab_test4 as select * from NBS_RDB.SAS_lab_test4;
quit; 

/*Issue arose when the OID value was set to 4 for Result, R_Order & R_Result which resulted in 
Resulted test values not populating the line list lab report. This work around will get the value
of the Order Test OID and set the values of its children (Result, R_Result) to the same value. 
This fix will bring up the resulted values in the line list.
*/

proc datasets memtype=DATA;
   delete lab_test2 Lab_Test3;
quit;
proc sql ;
create table order_test as 
select 
oid, 
root_ordered_test_pntr 
from lab_test4 
where Lab_Test_Type = 'Order' and oid ~=4;
quit;
proc sql;
create index root_ordered_test_pntr on lab_test4(root_ordered_test_pntr);
create index root_ordered_test_pntr on order_test(root_ordered_test_pntr);
quit;
data lab_test4 (drop = oid);
set lab_test4; run; 
PROC SQL;
DROP TABLE NBS_RDB.lab_test4_SAS;
DROP TABLE NBS_RDB.order_test_SAS;
DROP TABLE NBS_RDB.lab_test_SAS;
QUIT;
PROC SQL;
CREATE TABLE NBS_RDB.lab_test4_SAS AS SELECT * FROM lab_test4;
QUIT;

PROC SQL;
CREATE TABLE NBS_RDB.order_test_SAS AS SELECT * FROM order_test;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
select distinct
		lab.*,
		ord.oid
		into lab_test_SAS
from 
	rdb.dbo.lab_test4_SAS lab left join
	rdb.dbo.order_test_SAS ord
	on lab.root_ordered_test_pntr=ord.root_ordered_test_pntr;
) by sql;
disconnect from sql; 
Quit;
PROC SQL;
CREATE TABLE lab_test AS SELECT * FROM NBS_rdb.lab_test_SAS;
QUIT;
/*Issue arrose where only the Order Test and not its related Result, R_Result were not showing. This
will resolve this isse by merging order test specific attribute values into the Result & R_Result records*/
data Merge_Order 
(keep = root_ordered_test_pntr 
ACCESSION_NBR
LAB_RPT_CREATED_BY
LAB_RPT_CREATED_DT
JURISDICTION_CD
JURISDICTION_NM
lab_test_dt
specimen_collection_dt
LAB_RPT_RECEIVED_BY_PH_DT
LAB_RPT_LAST_UPDATE_DT
LAB_RPT_LAST_UPDATE_BY
ELR_IND
specimen_src
specimen_site
Specimen_desc
SPECIMEN_SITE_desc
LAB_RPT_LOCAL_ID
record_status_cd
);
set Lab_Test;
Where Lab_Test_Type = 'Order';

run;

proc sort data = Merge_Order;
by root_ordered_test_pntr;
data Merge_Order;
set Merge_Order;
If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED_PREV_D' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
	
run;
data Lab_Test; 
set Lab_Test;
if record_status_cd ='' then 
		record_status_cd =record_status_cd_for_result_drug;
if LAB_TEST_TYPE ='Result' then 
		LAB_TEST_DT = resulted_lab_report_date;
if LAB_TEST_TYPE ='Order_rslt' then 
		LAB_TEST_DT = sus_lab_report_date;
		
run;
data Lab_Test (Drop = 
ACCESSION_NBR
LAB_RPT_CREATED_BY
LAB_RPT_CREATED_DT
JURISDICTION_CD
JURISDICTION_NM
lab_test_dt
specimen_collection_dt
LAB_RPT_RECEIVED_BY_PH_DT
LAB_RPT_LAST_UPDATE_DT
LAB_RPT_LAST_UPDATE_BY
ELR_IND
resulted_lab_report_date
sus_lab_report_date
specimen_src
specimen_site
Specimen_desc
SPECIMEN_SITE_desc
LAB_RPT_LOCAL_ID
record_status_cd_for_result
record_status_cd_for_result_drug
);
set Lab_Test; run;
data Lab_Test;
set Lab_Test;
If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED_PREV_D' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
	
run;
proc sort data = Lab_Test;
by root_ordered_test_pntr;
data Lab_Test;
	MERGE Merge_Order Lab_Test;
	BY root_ordered_test_pntr; run; 

data rdbdata.Lab_Test;
set Lab_Test;
run;
PROC datasets library = work nolist;
delete	Lab_Test;run;
quit;

proc sql;
create index complex_index on rdbdata.lab_test(root_ordered_Test_pntr, lab_test_pntr);
quit;
PROC SQL;
CREATE TABLE L_LAB_TEST_N  AS 
	SELECT DISTINCT lab_test.lab_test_UID FROM rdbdata.Lab_Test
	EXCEPT SELECT lab_test.lab_test_UID FROM NBS_RDB.lab_test;

ALTER TABLE L_lab_test_N ADD lab_test_KEY_MAX_VAL  NUMERIC;
UPDATE L_lab_test_N SET lab_test_KEY_MAX_VAL=(SELECT MAX(lab_test_KEY) FROM NBS_RDB.L_lab_test);

QUIT;
%ASSIGN_KEY (L_LAB_TEST_N, Lab_Test_Key);
proc sql;
DELETE FROM L_lab_test_N WHERE LAB_TEST_UID IS NULL;
QUIT;
DATA L_LAB_TEST_N;
SET L_LAB_TEST_N;
IF LAB_TEST_KEY_MAX_VAL  ~=. THEN LAB_TEST_KEY= LAB_TEST_KEY+LAB_TEST_KEY_MAX_VAL;
/*IF LAB_TEST_KEY_MAX_VAL  =. THEN LAB_TEST_KEY= LAB_TEST_KEY+1;*/
DROP LAB_TEST_KEY_MAX_VAL;
RUN;
%DBLOAD (L_LAB_TEST, L_LAB_TEST_N);
/*proc sort data = rdbdata.Lab_Test tagsort;
By root_ordered_Test_pntr lab_test_pntr;
*/
PROC SQL;
CREATE TABLE D_LAB_TEST_N AS 
	SELECT * FROM rdbdata.LAB_TEST , L_LAB_TEST_N
WHERE LAB_TEST.LAB_TEST_UID=L_LAB_TEST_N.LAB_TEST_UID;
QUIT;
PROC SORT DATA=D_LAB_TEST_N NODUPKEY OUT=D_LAB_TEST_N; BY lab_test_key; RUN;
DATA D_LAB_TEST_N;
SET D_LAB_TEST_N;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%checkerr;
%DBLOAD (LAB_TEST, D_LAB_TEST_N);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM RDBDATA.LAB_TEST),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.LAB_TEST),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM L_LAB_TEST_N),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updated_lab_test_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='LAB_TEST');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
IF ACTIVITY_LOG_DETAIL_UID=. THEN ACTIVITY_LOG_DETAIL_UID=1;
ELSE ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN LAB_TEST TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE LAB_TEST TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
/*-------------------------------------------------------

	Lab_Report_User_Comment Dimension

	Note: Comments under the Order Test object (LAB214)
---------------------------------------------------------*/ 

/* proc sql; */	
/* create index lab_test_uid on D_LAB_TEST_N(lab_test_uid); */	
/* quit; */	
/*  */	
/* proc sql; */	
/* UPDATE ACTIVITY_LOG_DETAIL SET START_DATE=DATETIME(); */	
/* create table Lab_Rpt_User_Comment as */	
/* select 	root.Lab_Test_Key, */	
/* 		root.lab_rpt_uid as lab_test_uid, */	
/* 		lab214.activity_to_time	'COMMENTS_FOR_ELR_DT' as comments_for_elr_dt, */	
/* 		lab214.add_user_id		'USER_COMMENT_CREATED_BY' as user_comment_created_by, */	
/* 		TRANSLATE(ovt.value_txt,' ' ,'0D0A'x)	'USER_RPT_COMMENTS' as user_rpt_comments, */	
/* 		root.record_status_cd        'RECORD_STATUS_CD' as record_status_cd, */	
/* 		lab214.observation_uid */	
/*  */	
/* from 	D_LAB_TEST_N					as root, */	
/* 		nbs_ods.act_relationship 	as ar1, */	
/* 		nbs_ods.observation			as obs, */	
/* 		nbs_ods.act_relationship 	as ar2, */	
/* 		nbs_ods.observation			as lab214, */	
/* 		nbs_ods.obs_value_txt 				as ovt */	
/* where   ovt.value_txt is not null */	
/* 		and root.lab_test_uid = ar1.target_act_uid */	
/* 		and ar1.type_cd = 'APND' */	
/* 		and ar1.source_act_uid = obs.observation_uid */	
/* 		and obs.OBS_DOMAIN_CD_ST_1 ='C_Order' */	
/* 		and obs.observation_uid = ar2.target_act_uid */	
/* 		and ar2.source_act_uid = lab214.observation_uid */	
/* 		and ar2.type_cd = 'COMP' */	
/* 		and lab214.OBS_DOMAIN_CD_ST_1 ='C_Result' */	
/* 		and lab214.observation_uid = ovt.observation_uid */	
/* 		 */	
/* ; */	
/* quit; */
	
/* Texas custom- Code execution moved to SQL Server for performance tuning 08/20/2020 */
proc sql;
UPDATE ACTIVITY_LOG_DETAIL SET START_DATE=DATETIME();
QUIT;

PROC SQL;
DROP TABLE NBS_RDB.SAS_Lab_Rpt_User_Comment;
DROP TABLE NBS_RDB.D_LAB_TEST_N;
QUIT;

PROC SQL;
CREATE TABLE NBS_RDB.D_LAB_TEST_N AS SELECT * FROM D_LAB_TEST_N;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
execute (CREATE INDEX lab_test_uid ON D_LAB_TEST_N(lab_test_uid)) by sql;
disconnect from sql;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
select 	root.Lab_Test_Key,
		root.lab_rpt_uid as lab_test_uid,
		lab214.activity_to_time as comments_for_elr_dt,
		lab214.add_user_id as user_comment_created_by,
		REPLACE(ovt.value_txt, '0D0A', ' ') as user_rpt_comments,		/* TRANSLATE(ovt.value_txt,' ' ,'0D0A'x) as user_rpt_comments, */
		root.record_status_cd  as record_status_cd,
		lab214.observation_uid
into  SAS_Lab_Rpt_User_Comment
from 	        D_LAB_TEST_N					as root,
		nbs_odse.dbo.act_relationship 	as ar1,
		nbs_odse.dbo.observation			as obs,
		nbs_odse.dbo.act_relationship 	as ar2,
		nbs_odse.dbo.observation			as lab214,
		nbs_odse.dbo.obs_value_txt 				as ovt
where   ovt.value_txt is not null
		and root.lab_test_uid = ar1.target_act_uid
		and ar1.type_cd = 'APND'
		and ar1.source_act_uid = obs.observation_uid
		and obs.OBS_DOMAIN_CD_ST_1 ='C_Order'
		and obs.observation_uid = ar2.target_act_uid
		and ar2.source_act_uid = lab214.observation_uid
		and ar2.type_cd = 'COMP'
		and lab214.OBS_DOMAIN_CD_ST_1 ='C_Result'
		and lab214.observation_uid = ovt.observation_uid
) by sql;
disconnect from sql; 
QUIT;

proc sql;
create table Lab_Rpt_User_Comment as select * from NBS_RDB.SAS_Lab_Rpt_User_Comment;
quit; 
/* --------------------------- 08/20/2020 ------------------------------------ */ 

	
data Lab_Rpt_User_Comment;
	set Lab_Rpt_User_Comment;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
run;

data  Lab_Rpt_User_Comment;
set Lab_Rpt_User_Comment;
if lab_test_key =. then lab_test_key =1;
run;
%assign_key(LAB_RPT_USER_COMMENT, USER_COMMENT_KEY);
proc sql;
ALTER TABLE LAB_RPT_USER_COMMENT ADD User_Comment_key_MAX_VAL  NUMERIC;
UPDATE  LAB_RPT_USER_COMMENT SET User_Comment_key_MAX_VAL=(SELECT MAX(User_Comment_key) FROM NBS_RDB.LAB_RPT_USER_COMMENT);
quit;
DATA rdbdata.Lab_Rpt_User_Comment;
SET Lab_Rpt_User_Comment;
IF USER_COMMENT_KEY_MAX_VAL  ~=. AND USER_COMMENT_KEY~=1 THEN USER_COMMENT_KEY= USER_COMMENT_KEY+USER_COMMENT_KEY_MAX_VAL;
RUN;
proc sql;
delete from rdbdata.LAB_RPT_USER_COMMENT where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL >0;
delete from rdbdata.LAB_RPT_USER_COMMENT where LAB_TEST_KEY=.;
quit;
DATA rdbdata.LAB_RPT_USER_COMMENT;
SET rdbdata.LAB_RPT_USER_COMMENT;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%DBLOAD (LAB_RPT_USER_COMMENT, rdbdata.Lab_Rpt_User_Comment);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM nbs_rdb.LAB_RPT_USER_COMMENT),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.LAB_RPT_USER_COMMENT),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.Lab_Rpt_User_Comment),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updated_LAB_RPT_USER_COMMENT),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='LAB_RPT_USER_COMMENT');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN LAB_RPT_USER_COMMENT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE LAB_RPT_USER_COMMENT TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	End Time:   ' Time   Datetime20.;
	put '--------The END LAB--------';
run;
