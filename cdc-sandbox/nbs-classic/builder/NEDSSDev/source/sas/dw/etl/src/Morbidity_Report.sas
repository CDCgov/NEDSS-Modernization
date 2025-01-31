/*-------------------------------------------------------

	Morb Report Dimension

	Note:
		1) morb_rpt_share_ind and morb_rpt_oid need to 
		be renamed to shared_ind and program_jurisdiction_oid

		2 wrong modeling,  morb 2 trmt is one-many
		3 MORB_RPT_TEST_DT_KEY, no mapping
		4 NURSING_HOME_KEY, no mapping
---------------------------------------------------------*/


proc sql;
create table updt_MORBIDITY_REPORT_list as select morb_rpt_uid, morb_rpt_key from 
nbs_rdb.MORBIDITY_REPORT where morb_rpt_uid in (select observation_uid from nbs_rdb.updated_observation_List)
and morb_rpt_uid is not null;

DROP TABLE NBS_RDB.SAS_updt_MORBIDITY_REPORT_list;
CREATE TABLE NBS_RDB.SAS_updt_MORBIDITY_REPORT_list AS SELECT * FROM updt_MORBIDITY_REPORT_list;

create table updt_MORBIDITY_REPORT_EVENT_list as select morb_rpt_key from 
nbs_rdb.MORBIDITY_REPORT_EVENT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list);

DROP TABLE NBS_RDB.SAS_up_MORBIDITY_RPT_EVNT_lst;
CREATE TABLE NBS_RDB.SAS_up_MORBIDITY_RPT_EVNT_lst AS SELECT * FROM updt_MORBIDITY_REPORT_EVENT_list;

QUIT;

/* Texas - Moved code execution to database 08/20/2020 */
/* delete * from nbs_rdb.MORBIDITY_REPORT_EVENT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_EVENT_list); */
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
delete from MORBIDITY_REPORT_EVENT where morb_rpt_key in (select morb_rpt_key from SAS_up_MORBIDITY_RPT_EVNT_lst);
) by sql;
disconnect from sql; 
QUIT;

PROC SQL;
create table UPDT_MORB_RPT_USER_COMMENT_LIST as select MORB_RPT_UID from 
nbs_rdb.MORB_RPT_USER_COMMENT where MORB_RPT_UID in (select observation_uid from nbs_rdb.updated_observation_List);
QUIT;

/* Texas - Moved code execution to database 08/20/2020 */
/* delete * from nbs_rdb.MORB_RPT_USER_COMMENT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
delete from MORB_RPT_USER_COMMENT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
) by sql;
disconnect from sql; 
QUIT;

/* Texas - Moved code execution to database 08/20/2020 */
/* delete * from nbs_rdb.LAB_TEST_RESULT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
delete from LAB_TEST_RESULT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
) by sql;
disconnect from sql; 
QUIT;

/* Texas - Moved code execution to database 08/20/2020 */
/* delete * from nbs_rdb.MORBIDITY_REPORT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
delete from MORBIDITY_REPORT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
) by sql;
disconnect from sql; 
QUIT;


PROC SQL;
create table Morb_Root as 
select 	obs.local_id				'MORB_RPT_LOCAL_ID' as morb_rpt_local_id,
		obs.shared_ind				'MORB_RPT_SHARE_IND' as morb_rpt_share_ind,
		obs.PROGRAM_JURISDICTION_OID 'MORB_RPT_OID' as morb_rpt_oid,	
		obs.ADD_TIME				'MORB_RPT_CREATED_DT' as morb_RPT_Created_DT,
		obs.ADD_USER_ID  		 	'MORB_RPT_CREATED_BY' as morb_RPT_Create_BY, 
		obs.rpt_to_state_time  		'PH_RECEIVE_DT' as PH_RECEIVE_DT,
		obs.LAST_CHG_TIME 			'MORB_RPT_LAST_UPDATE_DT' as morb_RPT_LAST_UPDATE_DT, 
		obs.LAST_CHG_USER_ID		'MORB_RPT_LAST_UPDATE_BY' as morb_RPT_LAST_UPDATE_BY, /**/
		obs.jurisdiction_cd			'JURISDICTION_CD' as Jurisdiction_cd,		/*mrb137*/
		put(obs.jurisdiction_cd, $JURCODE.)  as Jurisdiction_nm,
		obs.activity_to_time   	 	'MORB_REPORT_DATE' as morb_report_date, 	/*mrb101*/
		obs.cd						'CONDITION_CD' as Condition_cd, 		/*MRB121*/
		obs.observation_uid			'MORB_RPT_UID' as morb_rpt_uid,
		obs.electronic_ind			'ELECTRONIC_IND' as ELECTRONIC_IND, 
		obs.record_status_cd,
		obs.PROCESSING_DECISION_CD 
from 	nbs_rdb.s_updated_lab as updated_lab  inner join nbs_ods.observation obs
on updated_lab.observation_uid =obs.observation_uid
where obs.obs_domain_cd_st_1 = 'Order'
	  and obs.CTRL_CD_DISPLAY_FORM  = 'MorbReport'
;
quit;

proc sort data = Morb_Root;
by morb_rpt_uid;

%assign_key(Morb_Root, morb_Rpt_Key);
proc sql;
delete from Morb_Root where  morb_Rpt_Key=1;

ALTER TABLE Morb_Root ADD morb_rpt_KEY_MAX_VAL  NUMERIC;
UPDATE Morb_Root SET morb_rpt_KEY_MAX_VAL=(SELECT MAX(morb_rpt_KEY) FROM NBS_RDB.morbidity_report);
quit;
DATA Morb_Root;
SET Morb_Root;
IF morb_rpt_KEY_MAX_VAL  ~=1 THEN morb_rpt_KEY= morb_rpt_KEY+morb_rpt_KEY_MAX_VAL;
DROP morb_rpt_KEY_MAX_VAL;
PROCESSING_DECISION_DESC=PUT(PROCESSING_DECISION_CD,$APROCDNF.);
RUN;
data Morbidity_Report;
	set Morb_Root;
	if record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE' ;
	if record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE' ;
	if record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE' ;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
run;

/* Morb Report Form Question */
proc sql;
create table MorbFrmQ as 

select 	mr.morb_rpt_uid,
		oq.cd,
		oq.observation_uid
from	morb_root					as mr,
		nbs_ods.act_relationship	as ar,
		nbs_ods.observation			as oq
where 	mr.morb_rpt_uid = ar.target_act_uid
		and ar.type_cd = 'MorbFrmQ'
		and ar.RECORD_STATUS_CD = 'ACTIVE'
		and oq.observation_uid = ar.source_act_uid
;
quit;



/*Morb Report Coded */

proc sql;
create table MorbFrmQCoded as 

select 	oq.*,
		ob.code
from	MorbFrmQ					as oq,
		nbs_ods.obs_value_coded 	as ob
where 	oq.observation_uid = ob.observation_uid

;
quit;

proc sort data = MorbFrmQCoded;
by morb_rpt_uid;


/*Morb Report date  */

proc sql;
create table MorbFrmQDate as 

select 	oq.*,
		ob.from_time
from	MorbFrmQ					as oq,
		nbs_ods.obs_value_date	 	as ob
where 	oq.observation_uid = ob.observation_uid

;
quit;

proc sort data = MorbFrmQDate;
by morb_rpt_uid;


/*Morb Report Txt  */

proc sql;
create table MorbFrmQTxt as 

select 	oq.*,
		TRANSLATE(ob.value_txt,' ' ,'0D0A'x) as VALUE_TXT
from	MorbFrmQ					as oq,
		nbs_ods.obs_value_txt	 	as ob
where 	oq.observation_uid = ob.observation_uid

;
quit;

proc sort data = MorbFrmQTxt;
by morb_rpt_uid;


proc transpose data = MorbFrmQCoded out =MorbFrmQCoded2(drop= _name_ _label_);
	id cd;
	var code;
	by morb_rpt_uid;

run;


proc transpose data = MorbFrmQDate out =MorbFrmQDate2 (drop= _name_ _label_);
	id cd;
	var from_time;
	by morb_rpt_uid;
run;

proc transpose data = MorbFrmQTxt out =MorbFrmQTxt2 (drop= _name_ _label_);
	id cd;
	var value_txt;
	by morb_rpt_uid;
run;

data Morbidity_Report;
	merge Morb_Root MorbFrmQCoded2 MorbFrmQDate2 MorbFrmQTxt2;
	by morb_rpt_uid;
run;

data Morbidity_Report;
format MRB122 MRB165 MRB166 MRB167 DATETIME20. ;
format INV128 INV145 INV148 INV149 INV178 MRB130 MRB168 $50.;
format MRB100 MRB161 $20. MRB102 MRB169 $2000.;

	INV128 = '';		
	INV145 = '';
	INV148 = '';
	INV149 = '';
	INV178 = '';
	MRB100 = '';
	MRB102 = '';
	MRB122 = .;
	MRB129 = '';
	MRB130 = '';
	MRB161 = '';
	MRB165 = .;
	MRB166 = .;
	MRB167 = .;
	MRB168 = '';
	MRB169 = '';

	set Morbidity_Report;
	if record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE' ;
	if record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE' ;
	if record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE' ;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
run;

/*Reason for not using lookup to find rdb column names
	1. Some columns in root obs. These columns must be hard coded, not suitable for lookup
	2. Same as above for Key columns, must be hard coded
	3. Unique id to Column name lookup table Not Reliable
*/
proc datasets lib=work nolist;
	modify Morbidity_Report;
	rename 
		/*These were no longer in the logical model*/
		INV128 = HOSPITALIZED_IND		
		INV145 = DIE_FROM_ILLNESS_IND
		INV148 = DAYCARE_IND
		INV149 = FOOD_HANDLER_IND
		INV178 = PREGNANT_IND
		MRB100 = MORB_RPT_TYPE
		MRB102 = MORB_RPT_COMMENTS
		MRB122 = TEMP_ILLNESS_ONSET_DT_KEY
		MRB129 = NURSING_HOME_ASSOCIATE_IND
		MRB130 = HEALTHCARE_ORG_ASSOCIATE_IND
		MRB161 = MORB_RPT_DELIVERY_METHOD
		MRB165 = TEMP_DIAGNOSIS_DT_KEY
		MRB166 = HSPTL_ADMISSION_DT
		MRB167 = TEMP_HSPTL_DISCHARGE_DT_KEY
		MRB168 = SUSPECT_FOOD_WTRBORNE_ILLNESS
		MRB169 = MORB_RPT_OTHER_SPECIFY
;
run;

/*-------------------------------------------------------

	morb_Report_User_Comment Dimension

	Note: Comments under the Order Test object (LAB214)
---------------------------------------------------------*/
proc sql;
create index morb_rpt_uid on Morbidity_Report(morb_rpt_uid);
quit;

/* Texas - Moved code execution to database 08/20/2020 */
PROC SQL;
DROP TABLE NBS_RDB.SAS_morb_Rpt_User_Comment;
DROP TABLE NBS_RDB.SAS_Morbidity_Report;
QUIT;

PROC SQL;
CREATE TABLE NBS_RDB.SAS_Morbidity_Report AS SELECT * FROM Morbidity_Report;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
execute (CREATE INDEX morb_rpt_uid ON SAS_Morbidity_Report(morb_rpt_uid)) by sql;
disconnect from sql;
QUIT;

PROC SQL;
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (

		select 	root.morb_Rpt_Key,
			root.morb_rpt_uid,
			obs.activity_to_time	 as user_comments_dt,
			mrb180.add_user_id		 as user_comments_by,
			REPLACE(ovt.value_txt,'0D0A',' ') as external_morb_rpt_comments,  /* TRANSLATE(ovt.value_txt,' ' ,'0D0A'x) 'EXTERNAL_MORB_RPT_COMMENTS' as external_morb_rpt_comments, */
			root.record_status_cd 
	into    SAS_morb_Rpt_User_Comment		
	from 	SAS_Morbidity_Report			as root,
			nbs_odse.dbo.act_relationship 	as ar1,
			nbs_odse.dbo.observation			as obs,
			nbs_odse.dbo.act_relationship 	as ar2,
			nbs_odse.dbo.observation			as mrb180,
			nbs_odse.dbo.obs_value_txt 				as ovt
	where   ovt.value_txt is not null
			and root.morb_rpt_uid = ar1.target_act_uid
			and ar1.type_cd = 'APND'
			and ar1.source_act_uid = obs.observation_uid
			and obs.OBS_DOMAIN_CD_ST_1 ='C_Order'
			and obs.CTRL_CD_DISPLAY_FORM ='MorbComment'
			and obs.observation_uid = ar2.target_act_uid
			and ar2.source_act_uid = mrb180.observation_uid
			and ar2.type_cd = 'COMP'
			and mrb180.OBS_DOMAIN_CD_ST_1 ='C_Result'
			and mrb180.observation_uid = ovt.observation_uid
) by sql;
disconnect from sql; 
QUIT;

proc sql;
create table morb_Rpt_User_Comment as select * from NBS_RDB.SAS_morb_Rpt_User_Comment;
quit; 


%assign_key(morb_Rpt_User_Comment, User_Comment_key);


DATA morb_rpt_user_comment;
set morb_rpt_user_comment;
if morb_rpt_key = . then morb_rpt_key = 1;
run;

proc sql;
ALTER TABLE morb_rpt_user_comment ADD User_Comment_key_MAX_VAL NUMERIC;
UPDATE  morb_rpt_user_comment SET User_Comment_key_MAX_VAL=(SELECT MAX(User_Comment_key) FROM NBS_RDB.morb_rpt_user_comment);
quit;
DATA  morb_rpt_user_comment;
SET  morb_rpt_user_comment;
IF User_Comment_key_MAX_VAL  ~=. THEN User_Comment_key= User_Comment_key+User_Comment_key_MAX_VAL;
RUN;
/*-------------------------------------------------------

	Morbidity_Report_Event( Keys table )

---------------------------------------------------------*/
proc sql;
create table Morbidity_Report_Event as 

select 	pat.PATIENT_Key				'PATIENT_KEY' as PATIENT_KEY,
		con.CONDITION_KEY,
		con.condition_cd,
			
		org1.Organization_key				as HEALTH_CARE_KEY,
		coalesce(dt3.Date_key,1)	as HSPTL_DISCHARGE_DT_KEY,
		org2.Organization_key				as HSPTL_KEY,
		coalesce(dt4.Date_key,1)	as ILLNESS_ONSET_DT_KEY,
		inv.INVESTIGATION_KEY,
		rpt.morb_Rpt_Key,

		coalesce(dt5.Date_key,1)	as MORB_RPT_CREATE_DT_KEY,
		coalesce(dt6.Date_key,1)	as MORB_RPT_DT_KEY,
		
		org3.Organization_Key				as MORB_RPT_SRC_ORG_KEY,
		coalesce(phy.provider_key,1)		as PHYSICIAN_KEY,
		per1.provider_key				as REPORTER_KEY,
		coalesce(ldf_g.ldf_group_key,1) as LDF_GROUP_KEY,
		1							as Morb_Rpt_Count,
		1							as Nursing_Home_Key, /*cannot find mapping*/
		rpt.record_status_cd

from	Morbidity_Report	rpt
	/*PATIENT_KEY*/
	left join nbs_ods.participation 	par
		on rpt.morb_rpt_uid = par.act_uid
		and par.type_cd = 'SubjOfMorbReport'
		and par.subject_class_cd = 'PSN'
		and par.act_class_cd ='OBS'
		and par.record_status_cd = 'ACTIVE'
	left join nbs_rdb.d_patient pat
		on par.subject_entity_uid = pat.patient_uid

	left join rdbdata.condition	con
		on  rpt.condition_cd =con.condition_cd
		AND con.condition_cd ~=''


	/*HEALTH_CARE_KEY   */
	left join nbs_ods.participation 	par1
		on rpt.morb_rpt_uid = par1.act_uid
		and par1.type_cd = 'HCFAC'
	left join nbs_rdb.d_Organization	org1
		on org1.Organization_uid = par1.subject_entity_uid

	/*HSPTL_DISCHARGE_DT_KE*/
	left join rdbdata.datetable		dt3
		on rpt.TEMP_HSPTL_DISCHARGE_DT_KEY = dt3.DATE_MM_DD_YYYY

	/*	HSPTL_KEY*/
	left join nbs_ods.participation 	par2
		on rpt.morb_rpt_uid = par2.act_uid
		/*and par2.type_cd = 'HospOfADT'*/
		and par2.type_cd = 'HospOfMorbObs'
		AND par2.subject_class_cd = 'ORG'
	left join nbs_rdb.d_Organization		org2
		on par2.subject_entity_uid = org2.Organization_uid	
	
	/*ILLNESS_ONSET_DT_KEY*/
	left join rdbdata.datetable		dt4
		on rpt.TEMP_ILLNESS_ONSET_DT_KEY = dt4.DATE_MM_DD_YYYY
	
	/* INVESTIGATION_KEY  */
	left join nbs_ods.act_relationship	ar1
		on rpt.morb_rpt_uid = ar1.source_act_uid
		and ar1.type_cd = 'MorbReport'
		and ar1.source_class_cd ='OBS'
		and ar1.target_class_cd ='CASE'
		and ar1.record_status_cd = 'ACTIVE'
	left join NBS_RDB.Investigation		inv
		on ar1.target_act_uid = inv.case_uid


	/*MORB_RPT_CREATE_DT_KEY*/
	left join rdbdata.datetable		dt5
		on datepart(rpt.morb_RPT_Created_DT)*24*60*60 = dt5.DATE_MM_DD_YYYY

	/*MORB_RPT_DT_KEY*/
	left join rdbdata.datetable		dt6
		on rpt.morb_report_date = dt6.DATE_MM_DD_YYYY


	/*MORB_RPT_SRC_ORG_KEY */
	left join nbs_ods.participation 	par3
		on rpt.morb_rpt_uid = par3.act_uid
		and par3.type_cd = 'ReporterOfMorbReport'
		and par3.subject_class_cd ='ORG'
	left join nbs_rdb.d_Organization		org3
		on par3.subject_entity_uid = org3.Organization_uid

	/*PHYSICIAN_KEY*/
	left join nbs_ods.participation 	par4
		on rpt.morb_rpt_uid = par4.act_uid
		and par4.type_cd = 'PhysicianOfMorb'
		and par4.subject_class_cd = 'PSN'
		and par4.act_class_cd ='OBS'
		and par4.record_status_cd = 'ACTIVE'
	left join nbs_rdb.d_provider	phy
		on par4.subject_entity_uid = phy.provider_uid

	/*	REPORTER_KEY           */
	left join nbs_ods.participation 	par6
		on rpt.morb_rpt_uid = par6.act_uid
		and par6.type_cd = 'ReporterOfMorbReport'
		and par6.subject_class_cd = 'PSN'
		and par6.act_class_cd ='OBS'
		and par6.record_status_cd = 'ACTIVE'
	left join nbs_rdb.d_provider		per1
		on par6.subject_entity_uid = per1.provider_uid

	/*Ldf group key*/		
	left join ldf_group as ldf_g
		on rpt.morb_rpt_uid = ldf_g.business_object_uid

;
quit;
/*Need this because there is bad data existing in ODS...once the bad data 
is removed this code will not execute*/
/*data Morbidity_Report_Event;
set Morbidity_Report_Event;
if lab_test_key =. then lab_test_key =1;
run;*/

data morbidity_report 
	(drop = /*TEMP_PH_RECEIVE_DT_KEY*/
			TEMP_ILLNESS_ONSET_DT_KEY
			/*TEMP_DIAGNOSIS_DT_KEY*/
			/*TEMP_HSPTL_ADMISSION_DT_KEY*/
			TEMP_HSPTL_DISCHARGE_DT_KEY
			/*DIE_FROM_ILLNESS_IND
			DAYCARE_IND
			FOOD_HANDLER_IND
			PREGNANT_IND*/
			morb_RPT_Created_DT
			morb_report_date
			Condition_cd
			/*HOSPITALIZED_IND*/
			/*ELECTRONIC_IND*/

	);

	set morbidity_report;
run;
data morbidity_report 
	(rename = (TEMP_DIAGNOSIS_DT_KEY = DIAGNOSIS_DT))
	;
	set morbidity_report;
data rdbdata.Morbidity_Report;
	set Morbidity_Report;
run;
proc sql;
delete from rdbdata.MORBIDITY_REPORT where morb_rpt_uid is null;
quit;
DATA rdbdata.MORBIDITY_REPORT;
SET rdbdata.MORBIDITY_REPORT;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%dbload (MORBIDITY_REPORT, rdbdata.MORBIDITY_REPORT);
proc sql;
delete from morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL >0;
delete from morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL =.;
delete from morb_Rpt_User_Comment where morb_rpt_KEY=.;
quit;
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM RDBDATA.MORBIDITY_REPORT),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.MORBIDITY_REPORT),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM RDBDATA.MORBIDITY_REPORT),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_MORBIDITY_REPORT_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='MORBIDITY_REPORT');
QUIT;

DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN MORBIDITY_REPORT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE MORBIDITY_REPORT TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
data rdbdata.morb_Rpt_User_Comment;
	set morb_Rpt_User_Comment;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
run;
DATA rdbdata.MORB_RPT_USER_COMMENT;
SET rdbdata.MORB_RPT_USER_COMMENT;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%dbload (MORB_RPT_USER_COMMENT, rdbdata.MORB_RPT_USER_COMMENT);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM RDBDATA.MORB_RPT_USER_COMMENT),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.MORB_RPT_USER_COMMENT),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM RDBDATA.MORB_RPT_USER_COMMENT),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_MORB_RPT_USER_COMMENT_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='MORB_RPT_USER_COMMENT');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN MORB_RPT_USER_COMMENT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE MORBIDITY_REPORT TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);



data Morbidity_Report_Event (drop= condition_cd);
	set Morbidity_Report_Event;
	if patient_key =. then patient_key =1;
	if condition_key =. then condition_key=1;
	if investigation_key =. then investigation_key=1;
	if MORB_RPT_SRC_ORG_KEY=. then MORB_RPT_SRC_ORG_KEY=1;
	if HSPTL_KEY=. then HSPTL_KEY=1;
	if HEALTH_CARE_KEY=. then HEALTH_CARE_KEY=1;
	if PHYSICIAN_KEY=. then PHYSICIAN_KEY=1;
	if REPORTER_KEY=. then REPORTER_KEY=1;
	if Nursing_Home_Key=. then Nursing_Home_Key=1;
run;

/*if treatment_key = . then treatment_key =1;*/
data rdbdata.Morbidity_Report_Event;
	set Morbidity_Report_Event;
run;
proc sql;
delete from rdbdata.MORBIDITY_REPORT_Event where morb_rpt_key is null;
quit;
proc sort data = rdbdata.Morbidity_Report_Event;
	by morb_rpt_key;
run;
DATA rdbdata.MORBIDITY_REPORT_Event;
SET rdbdata.MORBIDITY_REPORT_Event;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%dbload (MORBIDITY_REPORT_Event, rdbdata.MORBIDITY_REPORT_Event);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM RDBDATA.MORBIDITY_REPORT_Event),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.MORBIDITY_REPORT_EVENT),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM RDBDATA.MORBIDITY_REPORT_Event),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_MORBIDITY_REPORT_Event_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='MORBIDITY_REPORT_EVENT');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN MORBIDITY_REPORT_EVENT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE MORBIDITY_REPORT TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);


/**Delete temporary data sets**/
PROC datasets library = work nolist;
delete
Morb_Root
MorbFrmQ
MorbFrmQCoded
MorbFrmQDate
MorbFrmQTxt
MorbFrmQCoded2
MorbFrmQDate2
MorbFrmQTxt2
Morbidity_Report
morb_Rpt_User_Comment
Morbidity_Report_Event;
run;
quit;
