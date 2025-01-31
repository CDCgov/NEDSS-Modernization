/*******************Lab_Test_Result**************************************
*																		*
*																		*
*																		*
*																		*			
*************************************************************************/
PROC datasets library = work nolist;
 
delete
	filter_participants
	merge_person
	participants	 
	LabReportMorb
	Morb_OID
	R_Result_to_R_Order
	R_Result_to_R_Order_to_Order
	R_Order_to_Result
	Result_to_Order
	Lab_Rpt_User_Comment
	order_test
	Merge_Order
;
run;
quit;
proc sql;
create table lab_test_resultInit as
select 
	tst.lab_test_key,
	tst.root_ordered_test_pntr,
	tst.lab_test_uid,	
	tst.record_status_cd,
	tst.Root_Ordered_Test_Pntr,
	tst.lab_rpt_created_dt,
	coalesce(morb.morb_rpt_key,1) 'MORB_RPT_KEY' as morb_rpt_key, 
	morb_event.PATIENT_KEY as morb_patient_key,
	morb_event.Condition_Key as morb_Condition_Key,
	morb_event.Investigation_Key as morb_Investigation_Key,
	morb_event.MORB_RPT_SRC_ORG_KEY as MORB_RPT_SRC_ORG_KEY
	from  D_LAB_TEST_N as tst
	/* Morb report */
	left join nbs_ods.act_relationship	as act
		on tst.Lab_test_Uid = act.source_act_uid
		and act.type_cd = 'LabReport'
		and act.target_class_cd = 'OBS'
		and act.source_class_cd = 'OBS'
		and act.record_status_cd = 'ACTIVE'
	left join rdbdata.Morbidity_Report	as morb
		on act.target_act_uid = morb.Morb_rpt_uid
	left join rdbdata.Morbidity_report_event morb_event on
		morb_event.morb_rpt_key= morb.morb_rpt_key;
quit;

proc sql;
create table Lab_Test_Result1 as select
	tst.lab_test_key,
	tst.root_ordered_test_pntr,
	tst.lab_test_uid,	
	tst.record_status_cd,
	tst.Root_Ordered_Test_Pntr,
	tst.lab_rpt_created_dt,
	morb_rpt_key, 
	tst.morb_patient_key,
	tst.morb_Condition_Key,
	tst.morb_Investigation_Key,
	tst.MORB_RPT_SRC_ORG_KEY,
	/*per1.person_key as Transcriptionist_Key,*/
	/*per2.person_key as Assistant_Interpreter_Key,*/
	/*per3.person_key as Result_Interpreter_Key,*/
	per4.provider_key as Specimen_Collector_Key,
	per5.provider_key as Copy_To_Provider_Key,
	per6.provider_key as Lab_Test_Technician_key,
	coalesce(org.Organization_key,1)		'REPORTING_LAB_KEY'		as Reporting_Lab_Key,
	coalesce(prv.provider_key,1) 'ORDERING_PROVIDER_KEY' as Ordering_provider_key,	
	coalesce(org2.Organization_key,1)	'ORDERING_ORG_KEY'		as Ordering_org_key,
	coalesce(con.condition_key,1) 'CONDITION_KEY' as condition_key,
	dat.Date_key 						as LAB_RPT_DT_KEY,
	
	coalesce(inv.Investigation_key,1) 	'INVESTIGATION_KEY' as Investigation_key,
	coalesce(ldf_g.ldf_group_key,1)			as LDF_GROUP_KEY,
	tst.record_status_cd
	from lab_test_resultInit as tst
	/*get transcriptionist
	left join nbs_ods.participation as par1
	
	on tst.Root_Ordered_Test_Pntr = par1.act_uid
	and par1.type_cd = 'ENT'
	and par1.act_class_cd = 'OBS'
	and par1.record_status_cd ='ACTIVE'
	and par1.subject_class_cd = 'PSN'
	left join rdbdata.person as per1
	on par1.subject_entity_uid = per1.person_uid*/

	/*get assistant_interpreter
	left join nbs_ods.participation as par2
	on tst.Root_Ordered_Test_Pntr = par2.act_uid
	and par2.type_cd = 'ASS'
	and par2.act_class_cd = 'OBS'
	and par2.record_status_cd ='ACTIVE'
	and par2.subject_class_cd = 'PSN'
	left join rdbdata.person as per2
	on par2.subject_entity_uid = per2.person_uid*/

	/*get result_interpreter
	left join nbs_ods.participation as par3
	on tst.Root_Ordered_Test_Pntr = par3.act_uid
	and par3.type_cd = 'VER'
	and par3.act_class_cd = 'OBS'
	and par3.record_status_cd ='ACTIVE'
	and par3.subject_class_cd = 'PSN'
	left join rdbdata.person as per3
	on par3.subject_entity_uid = per3.person_uid*/

	/*get specimen collector*/
	left join updated_participant as par4
	on tst.Root_Ordered_Test_Pntr = par4.act_uid
	and par4.type_cd = 'PATSBJ'	
	left join nbs_ods.role as r1	
	on par4.subject_entity_uid = r1.subject_entity_uid
	and r1.cd = 'SPP'
	and r1.subject_class_cd = 'PROV'
	and r1.scoping_class_cd = 'PSN'
	left join nbs_rdb.d_provider as per4
	on r1.scoping_entity_uid = per4.provider_uid 

	/*get copy_to_provider key*/
	left join updated_participant as par5
	on tst.Root_Ordered_Test_Pntr = par5.act_uid
	and par5.type_cd = 'PATSBJ'	
	left join nbs_ods.role as r2 
	on par5.subject_entity_uid = r2.subject_entity_uid
	and r2.cd ='CT'
	AND r2.subject_class_cd = 'PROV'
	left join nbs_rdb.d_provider as per5
	on r2.scoping_entity_uid = per5.provider_uid

	/*get lab_test_technician*/

	left join updated_participant as par6
	on tst.Root_Ordered_Test_Pntr = par6.act_uid
	and par6.act_class_cd = 'OBS'
	and par6.subject_class_cd = 'PSN'
	and par6.type_cd = 'PRF'
	left join nbs_rdb.d_provider as per6
	on par6.subject_entity_uid = per6.provider_uid 

	/* Ordering Provider */
	left join updated_participant as par7
		on tst.Lab_test_Uid = par7.act_uid
		and par7.type_cd ='ORD'
		and par7.act_class_cd ='OBS'
		and par7.subject_class_cd = 'PSN'
		and par7.record_status_cd ='ACTIVE'
	left join	nbs_rdb.d_provider 	as prv
		on	par7.subject_entity_uid = prv.provider_uid

	/* Reporting_Lab*/
	left join updated_participant as par
		on tst.Lab_test_uid = par.act_uid
		and par.type_cd = 'AUT'
		and par.record_status_cd = 'ACTIVE'
		and par.act_class_cd = 'OBS'
		and par.subject_class_cd = 'ORG'
	left join nbs_rdb.d_Organization	as org
		on par.subject_entity_uid = org.Organization_uid

	/* Ordering Facility */
	left join updated_participant as par8
		on tst.Lab_Test_uid = par8.act_uid
		/*and par2.type_cd = 'ORG'*/
		and par8.type_cd = 'ORD'
		and par8.record_status_cd = 'ACTIVE'
		and par8.act_class_cd = 'OBS'
		and par8.subject_class_cd = 'ORG'
	left join nbs_rdb.d_Organization	as org2
		on par8.subject_entity_uid = org2.Organization_uid

	/* Conditon, it's just program area */

	/*if we add a program area to the Lab_Report Dimension we probably don't 
	even need a condition dimension.  Even though it's OK with the Dimension Modeling
	principle for adding a prog_area_cd row to the condition, it sure will cause 
	some confusion among users.  There's no "disease" on the input. 
	*/
	left join nbs_ods.observation 	as obs
		on tst.Lab_test_Uid = obs.observation_uid
	left join	rdbdata.Condition	as con
		on	obs.prog_area_cd  = con.program_area_cd
		and con.condition_cd is null


	/*LDF_GRP_KEY*/
	left join ldf_group as ldf_g
		on tst.Lab_test_UID = ldf_g.business_object_uid

	

	/* Lab_Rpt_Dt */
	left join rdbdata.datetable 		as dat
		on datepart(tst.lab_rpt_created_dt)*24*60*60 = dat.DATE_MM_DD_YYYY


	/* PHC */
	left join nbs_ods.act_relationship	as act2
		on tst.Lab_Test_Uid = act2.source_act_uid
		and act2.type_cd = 'LabReport'
		and act2.target_class_cd = 'CASE'
		and act2.source_class_cd = 'OBS'
		and act2.record_status_cd = 'ACTIVE'
	left join NBS_RDB.investigation		as inv
		on act2.target_act_uid = inv.case_uid
;
quit;
proc sort data = Lab_Test_Result1;
by lab_test_key;
/*-------------------------------------------------------

	Lab_Result_Comment Dimension

	Note: User Comments for Result Test Object (Lab104)

---------------------------------------------------------*/
data Result_And_R_Result;
set rdbdata.Lab_Test;
	if (Lab_Test_Type = 'Result' or Lab_Test_Type IN ('R_Result', 'I_Result',  'Order_rslt'));
run; 

proc sql;

create table Lab_Result_Comment as 
select 
		lab104.lab_test_uid,
		TRANSLATE(ovt.value_txt,' ' ,'0D0A'x)		'LAB_RESULT_COMMENTS'	as Lab_Result_Comments,
		ovt.obs_value_txt_seq	'LAB_RESULT_TXT_SEQ' as Lab_Result_Txt_Seq,
		lab104.record_status_cd
from 	
		Result_And_R_Result		as lab104,
		nbs_ods.obs_value_txt	as ovt
where 	ovt.value_txt is not null 
		and ovt.txt_type_cd = 'N'
		and ovt.OBS_VALUE_TXT_SEQ ~= 0
		and ovt.observation_uid =  lab104.lab_test_uid

;
quit;


/*************************************************************
Added by Amit below to support wrapping of comments when comments are 
stored in multiple obs_value_txt rows in ODS
*/

proc sort data = Lab_Result_Comment;
by lab_test_uid DESCENDING lab_result_txt_seq; 
data New_Lab_Result_Comment (drop = lab_result_txt_seq);
  set Lab_Result_Comment;
	by lab_test_uid;

	Length v_lab_result_val_comments $10000; 
	Retain v_lab_result_val_comments;
	
	if first.lab_test_uid then
		v_lab_result_val_comments = trim(lab_result_comments); 
	else
		v_lab_result_val_comments = (trim(lab_result_comments) || ' ' || v_lab_result_val_comments);  

	if last.lab_test_uid then
		output;
run;
data Lab_Result_Comment (drop = Lab_Result_Comments);
 set New_Lab_Result_Comment;
 rename v_lab_result_val_comments = lab_result_comments;
run;
data rdbdata.Lab_Result_Comment;
set Lab_Result_Comment; run;

/*************************************************************/

proc sort data = Lab_Result_Comment nodupkey; by Lab_test_uid; run;
%assign_key(Lab_Result_Comment, Lab_Result_Comment_Key);

proc sql;
ALTER TABLE Lab_Result_Comment ADD Lab_Result_Comment_Key_MAX_VAL  NUMERIC;
UPDATE  Lab_Result_Comment SET Lab_Result_Comment_Key_MAX_VAL=(SELECT MAX(Lab_Result_Comment_Key) FROM NBS_RDB.Lab_Result_Comment);
quit;
DATA Lab_Result_Comment;
SET Lab_Result_Comment;
IF Lab_Result_Comment_Key_MAX_VAL  ~=. AND Lab_Result_Comment_Key~=1 THEN Lab_Result_Comment_Key= Lab_Result_Comment_Key+Lab_Result_Comment_Key_MAX_VAL;
RUN;

data Lab_Result_Comment;
set Lab_Result_Comment;
Result_Comment_Grp_Key = Lab_Result_Comment_Key;
run;
data Result_Comment_Group (Keep = Result_Comment_Grp_Key lab_test_uid);
	Set Lab_Result_Comment; 
run;

proc sort data=result_comment_group;
	by Result_Comment_Grp_Key;
proc sql;
delete from Result_Comment_Group where result_comment_grp_key=1;
delete from Result_Comment_Group where result_comment_grp_key=.;
quit;
DATA lab_test_result1;
	MERGE Result_Comment_Group lab_test_result1; 
	by lab_test_uid; 
run;

data lab_test_result1;
set lab_test_result1;
if Result_Comment_Grp_Key =.  then Result_Comment_Grp_Key = 1;
/*Creating Result_Comment_Group **/
data Result_Comment_Group;
	set Result_Comment_Group; 
run;

data lab_result_comment;
set lab_result_comment;
where Lab_Result_Comment_Key ~=1; run; 
proc sort data = Lab_Result_Comment nodupkey; by Lab_test_uid; run;

/*-------------------------------------------------------

	Lab_Result_Val Dimension
	Test_Result_Grouping Dimension

---------------------------------------------------------*/
proc sql;

create table Lab_Result_Val as
select 
		rslt.lab_test_uid, 
        TRANSLATE(otxt.value_txt,' ' ,'0D0A'x)		'LAB_RESULT_TXT_VAL' as Lab_Result_Txt_Val,
		otxt.obs_value_txt_seq			'LAB_RESULT_TXT_SEQ' as Lab_Result_Txt_Seq,
		onum.COMPARATOR_CD_1,
		onum.NUMERIC_VALUE_1,
		onum.separator_cd,
		onum.NUMERIC_VALUE_2,									
		onum.numeric_unit_cd    	'Result_Units'  as Result_Units,
		onum.LOW_RANGE					'REF_RANGE_FRM' as Ref_Range_Frm,
		onum.HIGH_RANGE				'REF_RANGE_TO' as Ref_Range_To,
		code.code						'TEST_RESULT_VAL_CD' as Test_result_val_cd,
		code.display_name				'TEST_RESULT_VAL_CD_DESC' as Test_result_val_cd_desc,
		code.CODE_SYSTEM_CD			'TEST_RESULT_VAL_CD_SYS_CD' as Test_result_val_cd_sys_cd,
		code.CODE_SYSTEM_DESC_TXT	'TEST_RESULT_VAL_CD_SYS_NM' as Test_result_val_cd_sys_nm,
		code.ALT_CD						'ALT_RESULT_VAL_CD' as Alt_result_val_cd,
		code.ALT_CD_DESC_TXT			'ALT_RESULT_VAL_CD_DESC' as Alt_result_val_cd_desc,
		code.ALT_CD_SYSTEM_CD		'ALT_RESULT_VAL_CD_SYS_CD' as Alt_result_val_cd_sys_cd,
		code.ALT_CD_SYSTEM_DESC_TXT	'ALT_RESULT_VAL_CD_SYSTEM_NM' as Alt_result_val_cd_sys_nm,
		date.from_time 'FROM_TIME' as from_time,
		date.to_time 'TO_TIME' as to_time,
		rslt.record_status_cd
FROM	Result_And_R_Result			as rslt
		LEFT JOIN nbs_ods.obs_value_txt	as otxt
		ON rslt.lab_test_uid = otxt.observation_uid
		and ((otxt.TXT_TYPE_CD is null) OR (rslt.ELR_IND = 'Y' AND otxt.TXT_TYPE_CD ~= 'N')) 
		/*
		Commented out because an ELR Test Result can have zero to many text result values 
		AND otxt.OBS_VALUE_TXT_SEQ =1 
		*/
		
		LEFT JOIN  nbs_ods.obs_value_numeric	as onum
		ON rslt.lab_test_uid = onum.observation_uid

		LEFT JOIN nbs_ods.obs_value_coded		as code
		ON rslt.lab_test_uid = code.observation_uid

		LEFT JOIN nbs_ods.obs_value_date		as date
		ON rslt.lab_test_uid = date.observation_uid
;
quit;
%assign_key(Lab_Result_Val,Test_Result_Grp_Key);

proc sql;
ALTER TABLE Lab_Result_Val ADD test_result_grp_key_MAX_VAL  NUMERIC;
UPDATE  Lab_Result_Val SET test_result_grp_key_MAX_VAL=(SELECT MAX(test_result_grp_key) FROM NBS_RDB.Test_Result_Grouping);
quit;
DATA Lab_Result_Val;
SET Lab_Result_Val;
if test_result_grp_key_MAX_VAL = 1 then test_result_grp_key_MAX_VAL=.;
IF test_result_grp_key_MAX_VAL  ~=. AND test_result_grp_key~=1 THEN test_result_grp_key= test_result_grp_key+test_result_grp_key_MAX_VAL;
RUN;

proc sort tagsort data = Lab_Result_Val;
	by lab_test_uid;

data Lab_Result_Val;
	set Lab_Result_Val;
	format Numeric_Result	$50.;

	if NUMERIC_VALUE_1 ~=. then 
		Numeric_Result = trim(COMPARATOR_CD_1)||trim(left(put(NUMERIC_VALUE_1, 11.5)));
	if NUMERIC_VALUE_2 ~=. then
		Numeric_Result = trim(Numeric_Result) ||trim(left(separator_cd)) || trim(left(put(NUMERIC_VALUE_2, 11.5)));
	drop COMPARATOR_CD_1 NUMERIC_VALUE_1 separator_cd NUMERIC_VALUE_2;
run;


/*-------------------------------------------------------

	
	Result_Comment_Group

---------------------------------------------------------*/

data Lab_Result_val Test_Result_Grouping (keep=TEST_RESULT_Grp_Key lab_test_uid);
set  Lab_Result_val;
 	TEST_RESULT_Grp_Key = TEST_RESULT_Grp_Key;
	output Lab_Result_val Test_Result_Grouping;
run;
/*Setting value for Test_Result_Val_Key column*/
data Lab_Result_Val;
	set Lab_Result_Val;
	if Test_Result_Grp_Key ~=. then Test_Result_Val_Key = Test_Result_Grp_Key;
run;

proc sort tagsort data = Lab_Result_Val;
	by lab_test_uid DESCENDING lab_result_txt_seq; 
data New_Lab_Result_Val (drop = lab_result_txt_seq);
  set Lab_Result_Val;
	by lab_test_uid;

	Length v_lab_result_val_txt $10000; 
	Retain v_lab_result_val_txt;
	
	if first.lab_test_uid then
		v_lab_result_val_txt = trim(lab_result_txt_val); 
	else
		/* v_lab_result_val_txt = (trim(lab_result_txt_val) || v_lab_result_val_txt);  */
		v_lab_result_val_txt = (trim(lab_result_txt_val) || ' ' || v_lab_result_val_txt);  


	if last.lab_test_uid then
		output;
run;

data Lab_Result_Val (drop = Lab_Result_Txt_Val);
 set New_Lab_Result_Val;
 rename v_lab_result_val_txt = lab_result_txt_val;
run;
/*proc sql;
delete from Lab_Result_Val where TEST_RESULT_Grp_Key=1 and TEST_RESULT_Grp_Key_MAX_VAL >0;
delete from Lab_Result_Val where TEST_RESULT_Grp_Key=.;
quit;
*/
data rdbdata.Lab_Result_Val;
	set Lab_Result_Val; 
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
run;
DATA rdbdata.Lab_Result_Val;
SET rdbdata.Lab_Result_Val;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
proc sql;
delete from rdbdata.Lab_Result_Val where Test_Result_Val_Key =1;
quit;



/* Update Lab_Test Keys */

/* Test_Result_Grp_Key */
proc sql;
create table Lab_Test_Result2 as 
select 	tst.*,
		coalesce(lrv.Test_Result_Grp_Key,1) as Test_Result_Grp_Key
from 	
		Lab_Test_Result1		as tst  
	left join	Lab_Result_Val as lrv
		on tst.Lab_test_uid = lrv.Lab_test_uid
		and lrv.Test_Result_Grp_Key ~=1

;
quit;
proc sort tagsort data = lab_test_result2;
	by Lab_test_uid;

/* Patient Key */
/* bad data seen for a order test without patient, will reseach later*/
proc sql;
create table Lab_Test_Result3 as 
select 	tst.*,
		coalesce(psn.patient_key,1) as patient_key
from 	Lab_Test_Result2 as tst
	left join updated_participant as par
		on tst.Root_Ordered_Test_Pntr = par.act_uid
		and par.type_cd ='PATSBJ'
		and par.act_class_cd ='OBS'
		and par.subject_class_cd = 'PSN'
		and par.record_status_cd ='ACTIVE'
	left join nbs_rdb.d_patient as psn
		on par.subject_entity_uid = psn.patient_uid
		and psn.patient_key ~=1

;
quit;
data Lab_Test_Result3;
set Lab_Test_Result3;
if morb_rpt_key>1 then PATIENT_KEY=morb_patient_key;
if morb_rpt_key>1 then Condition_Key=morb_Condition_Key;
if morb_rpt_key>1 then Investigation_Key = morb_Investigation_Key;
if morb_rpt_key>1 then REPORTING_LAB_KEY= MORB_RPT_SRC_ORG_KEY;
run;
proc sort tagsort data = lab_test_result3;
	by lab_test_uid;

/* Performing Lab */
proc sql;
create table Lab_Test_Result as 
select 	tst.*,
		coalesce(org.Organization_key,1) as Performing_lab_key

from 	Lab_Test_Result3 as tst
	left join updated_participant as par
		on tst.lab_test_uid = par.act_uid
		and par.type_cd ='PRF'
		and par.act_class_cd ='OBS'
		and par.subject_class_cd = 'ORG'
		and par.record_status_cd ='ACTIVE'
	left join nbs_rdb.d_Organization  as org
		on par.subject_entity_uid = org.Organization_uid
		and org.Organization_key ~=1
	;
quit;
proc sort tagsort data = Test_Result_Grouping nodupkey;
	by test_result_grp_key;

proc sql;
quit;
data rdbdata.Test_Result_Grouping;
	set Test_Result_Grouping; run;
DATA rdbdata.Test_Result_Grouping;
SET rdbdata.Test_Result_Grouping;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
proc sql;
delete from rdbdata.TEST_RESULT_GROUPING where test_result_grp_key=1;
delete from rdbdata.TEST_RESULT_GROUPING where test_result_grp_key=.;
delete from rdbdata.TEST_RESULT_GROUPING where TEST_RESULT_GRP_KEY not in (select TEST_RESULT_GRP_KEY from rdbdata.LAB_RESULT_VAL);
quit;

%dbload(TEST_RESULT_GROUPING, rdbdata.TEST_RESULT_GROUPING);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM rdbdata.TEST_RESULT_GROUPING),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.TEST_RESULT_GROUPING),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.TEST_RESULT_GROUPING),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_Test_Result_Grouping_LIST),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='TEST_RESULT_GROUPING');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN TEST_RESULT_GROUPING TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE TEST_RESULT_GROUPING TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
%dbload(Lab_Result_Val, rdbdata.Lab_Result_Val);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM rdbdata.Lab_Result_Val),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.Lab_Result_Val),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.Lab_Result_Val),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_Lab_Result_Val_LIST),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='LAB_RESULT_VAL');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN LAB_RESULT_VAL TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE LAB_RESULT_VAL TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
/*
data rdbdata.Lab_Result_Val (drop = lab_test_uid);
set Lab_Result_Val; run;
*/
data rdbdata.Result_Comment_Group;
	set Result_Comment_Group; 
	RDB_LAST_REFRESH_TIME=DATETIME();
run;
%dbload(Result_Comment_Group, rdbdata.Result_Comment_Group);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM rdbdata.Result_Comment_Group),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.Result_Comment_Group),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.Result_Comment_Group),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM UPDT_Result_Comment_Grp_LIST),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='RESULT_COMMENT_GROUP');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN RESULT_COMMENT_GROUP TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE RESULT_COMMENT_GROUP TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);

data rdbdata.Lab_Result_Comment;
	set Lab_Result_Comment; 
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
run;
DATA rdbdata.Lab_Result_Comment;
SET rdbdata.Lab_Result_Comment;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
%dbload(LAB_RESULT_COMMENT, rdbdata.Lab_Result_Comment);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM rdbdata.Lab_Result_Comment),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.Lab_Result_Comment),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.Lab_Result_Comment),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updt_Lab_Result_Comment_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='LAB_RESULT_COMMENT');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN LAB_RESULT_COMMENT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE LAB_RESULT_COMMENT TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
proc sql;
delete * from Lab_Test_Result where lab_test_key is null;
run;
proc sort data = Lab_Test_Result;
	by root_ordered_test_pntr lab_test_uid;
data rdbdata.Lab_Test_Result (drop = root_ordered_test_pntr);
	set Lab_Test_Result; 
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
run;
DATA rdbdata.Lab_Test_Result;
SET rdbdata.Lab_Test_Result;
RDB_LAST_REFRESH_TIME=DATETIME();
RUN;
proc sort tagsort data = Lab_Test_Result nodupkey;
	by test_result_grp_key;
	
%dbload(Lab_Test_Result, rdbdata.Lab_Test_Result);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM rdbdata.Lab_Test_Result),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.LAB_TEST_RESULT),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM rdbdata.Lab_Test_Result),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM updated_lab_Test_result_list),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='LAB_TEST_RESULT');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
ACTIVITY_LOG_DETAIL_UID= ACTIVITY_LOG_DETAIL_UID +1;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN LAB_TEST_RESULT TABLE.'||
' THERE ARE NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE LAB_TEST_RESULT TABLE.';
RUN;
%dbload(ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);

quit;
/**Delete Temporary Data sets**/
/**Delete temporary data set**/
PROC datasets library = work nolist;

delete
	 Lab_Result_Val
	New_Lab_Result_Val
	Result_And_R_Result
	Lab_Result_Comment
	Result_Comment_Group
	lab_test_result
	lab_test_result1
	lab_test_result2
	lab_test_result3
	Test_Result_Grouping
	Lab_Test
;
run;
quit;
