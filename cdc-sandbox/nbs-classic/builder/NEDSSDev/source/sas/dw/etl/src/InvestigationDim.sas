/*****************************************************

	Investigation Dimension 

******************************************************/
options fmtsearch=(nbsfmt);
proc sql;
create table S_Investigation as 
select DISTINCT
	phc.*,
	act.root_extension_txt		as Inv_State_Case_Id,
	act2.root_extension_txt		as city_county_case_nbr,
	act3.root_extension_txt		as legacy_case_id,
	stateCode.state_nm as Import_Frm_State_PHC 'Import_Frm_State_PHC',
	stateCounty.code_desc_txt as Import_Frm_Cnty_PHC 'Import_Frm_Cnty_PHC',
	countryCode.code_short_desc_txt as  Import_Frm_Cntry_PHC 'Import_Frm_Cntry_PHC',
	par2.from_time AS INV_ASSIGNED_DT 'INV_ASSIGNED_DT'
from
	/*nbs_ods.Public_Health_Case as phc,*/
	rdbdata.phcrootobs	as phc
	left join nbs_ods.act_id as act	/* left join to retain summary case*/
on 
	phc.public_health_case_uid = act.act_uid 
	and act.act_id_seq=1 /*state id*/

	left join nbs_ods.act_id as act2
on 
	phc.public_health_case_uid = act2.act_uid 
	and act2.act_id_seq=2 /*city_county_case_nbr*/
	
	left join nbs_ods.act_id as act3
on 
	phc.public_health_case_uid = act3.act_uid 
	and act3.act_id_seq=3 /*legacy_case_id*/
	
	left join nbs_srt.state_code as stateCode
on 
	phc.imported_state_code = stateCode.state_cd
	/*State name translation*/
	left join nbs_srt.state_county_code_value as stateCounty
on 
	phc.imported_county_code = stateCounty.code
	 /*state_county translation*/
	left join nbs_srt.country_code as countryCode
on 
	phc.imported_country_code = countryCode.code
	/*country_code translation*/
	left join nbs_ods.participation	as par2
on phc.public_health_case_uid = par2.act_uid
		and par2.type_cd ='InvestgrOfPHC'
		and par2.act_class_cd = 'CASE'
		and par2.subject_class_cd = 'PSN'

order by public_health_case_uid
;
quit;

data S_Investigation;
	set S_Investigation;
	MMWR_WEEK1=INPUT(MMWR_WEEK,8.);
    MMWR_YEAR1=INPUT(MMWR_YEAR,8.);
	if record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE' ;
	else record_status_cd = 'ACTIVE' ;
run;

data invobscoded invobsdate invobstxt invobsnum;
set rdbdata.phcchildobs;
format col_nm $32.;
	col_nm = child_obs_cd ; 
	
	select (child_obs_cd);
		when (
			'INV153',	/* Import country*/
			'INV154', 	/* state*/	
			'INV156', 	/* county*/
			'INV128',	/* HSPTLIZD_IND*/
			'RUB162',   /* DIE_FRM_THIS_ILLNESS_IND */
			'MEA078',  	/* DIE_FRM_THIS_ILLNESS_IND */
			'PRT103', 	/* DIE_FRM_THIS_ILLNESS_IND */
			'INV145',	/* DIE_FRM_THIS_ILLNESS_IND in Generic */
			'INV149',
			'INV178',
			'INV148'
		) output invobscoded;
		when (
			'INV155'	/* city*/
		) output invobstxt;
		when (
			'INV132',	/* HSPTL_ADMISSION_DT */
			'INV133'	/* HSPTL_DISCHARGE_DT */
		) output invobsdate;
		when (
			'INV134'	/* HSPTL_DURATION_DAYS */
		) output invobsnum;

		otherwise;
	end;


run;


%getobscode(invcodedvalue,invobscoded);
%getobstxt(invtxt,invobstxt);
%getobsdate(invfrom_time,invobsdate,from_time);  
%getobsnum(invNum1, invobsnum,numeric_value_1);

%rows_to_columns(invcodedvalue,invcodedvalue2);
%rows_to_columns(invtxt,invtxt2);
%rows_to_columns(invfrom_time,invfrom_time2);
%rows_to_columns(invNum1,invNum1_2);
	
/* DIE_FRM_THIS_ILLNESS_IND needs to be handled differently since it's mapped 
to different ODS table with differnt CDs.  In some disease it's mapped to obs
and in some to PHC*/
data invcodedvalue2;
format INV145 PRT103 MEA078 RUB162 $50.;
INV145 = '';
PRT103 = '';
MEA078 = '';
RUB162 = '';
set invcodedvalue2;
	/*Only one of them should have value: INV145, PRT103,MEA078*/
	if INV145 = '' then INV145=left(trim(PRT103)||trim(MEA078)||trim(RUB162));
run;

/*Imported Country, State, County CDs*/
data importCDs;
set invcodedvalue;
	select (child_obs_cd);
		when('INV153')	/* Import country*/
			do; col_nm = 'Import_Frm_Cntry_cd'; response = code; output; end;
		when ('INV154')	/* state*/	
			do; col_nm = 'Import_Frm_State_cd'; response = code; output; end;
		when ('INV156')	/* county */
			do; col_nm = 'Import_Frm_Cnty_cd'; response = code; output; end;
		otherwise delete;
	end;
run;
%rows_to_columns(importCDs,importCDs2);


data S_Investigation;
merge S_Investigation invcodedvalue2 importCDs2 invtxt2 invfrom_time2 invNum1_2 ;
	by public_health_case_uid;
run;
data S_Investigation;
set S_Investigation;

/*format IMPORT_FRM_CNTRY_CD IMPORT_FRM_STATE_CD IMPORT_FRM_CNTY_CD Import_Frm_Cntry
 Import_Frm_State  Import_Frm_Cnty  PATIENT_AGE_AT_ONSET_UNIT $20.;*/

LENGTH HSPTLIZD_IND_PHC $40;
LENGTH FOOD_HANDLR_IND_PHC $40; 
LENGTH DAYCARE_ASSOCIATION_IND $40;
LENGTH PATIENT_PREGNANT_IND_PHC $40;
LENGTH PATIENT_PREGNANT_IND $40;
LENGTH Import_Frm_City $40;
LENGTH Import_Frm_City_PHC $40;
length Import_Frm_Cntry $200;
length Import_Frm_Cnty $200;
length Import_Frm_state $200;
length HSPTLIZD_IND $40;
length FOOD_HANDLR_IND $40;

format   PATIENT_AGE_AT_ONSET COMMA5.;
format HSPTL_ADMISSION_DT HSPTL_DISCHARGE_DT INV_ASSIGNED_DT yymmdd6.;
	Jurisdiction_nm		= put(Jurisdiction_cd, $JURCODE.);
	Investigation_status= put(INVESTIGATION_STATUS_CD,$INV109f.);
	RPT_SRC_CD_DESC 	= put(RPT_SOURCE_CD, $INV112f.);
	Transmission_mode	= put(TRANSMISSION_MODE_CD, $INV157f.);
	Inv_Case_status 	= put(case_class_cd, $INV163f.); 

	DISEASE_IMPORTED_IND= put(disease_Imported_cd, $INV152f.); 
	Outbreak_ind		= put(Outbreak_ind, $INV150f.);
	OUTBREAK_NAME_DESC	= put(outbreak_name, $INV151f.);
	txt			= TRANSLATE(txt,' ' ,'0D0A'x);
	PATIENT_AGE_AT_ONSET=pat_age_at_onset;
	PATIENT_AGE_AT_ONSET_UNIT=put(pat_age_at_onset_unit_cd, $INV144Nf.); ;
	ILLNESS_DURATION_UNIT=put(effective_duration_unit_cd, $INV144f.); ;
	ILLNESS_DURATION=INPUT(effective_duration_amt,  COMMA20.);

	/*some disease have DIE_FRM_THIS_ILLNESS_IND mapped to outcome_cd*/
	if outcome_cd ~= '' then DIE_FRM_THIS_ILLNESS_IND = put(outcome_cd, $INV145f.);
	else DIE_FRM_THIS_ILLNESS_IND = INV145;

	HSPTLIZD_IND=INV128;
	HSPTLIZD_IND_PHC= put(hospitalized_ind_cd,$INV128Nf.);

	FOOD_HANDLR_IND=INV149;
	FOOD_HANDLR_IND_PHC= put(food_handler_ind_code,$INV149Nf.);

	DAYCARE_ASSOCIATION_IND= INV148;
	DAYCARE_ASSOCIATION_IND_PHC= put(day_care_ind_cd,$INV148Nf.);

	REFERRAL_BASIS = put(REFERRAL_BASIS_CD,$NBS110NF.);
	CURR_PROCESS_STATE=put(CURR_PROCESS_STATE_CD,$NBS115NF.);

	PATIENT_PREGNANT_IND= INV178; 
	PATIENT_PREGNANT_IND_PHC= put(pregnant_ind_cd,$INV178Nf.);

	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  HSPTLIZD_IND=HSPTLIZD_IND_PHC;
		
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  FOOD_HANDLR_IND=FOOD_HANDLR_IND_PHC;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  DAYCARE_ASSOCIATION_IND=DAYCARE_ASSOCIATION_IND_PHC;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  PATIENT_PREGNANT_IND=PATIENT_PREGNANT_IND_PHC;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  HSPTL_DURATION_DAYS=hospitalized_duration_amt;
		else HSPTL_DURATION_DAYS=INV134;

	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_Cntry_cd=imported_country_code;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_State_cd=imported_state_code;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_Cnty_cd=imported_county_code;

	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_Cntry=Import_Frm_Cntry_PHC;
		else Import_Frm_Cntry=INV153;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
	 	then  Import_Frm_State=Import_Frm_State_PHC;
		else Import_Frm_State=INV154;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_Cnty=Import_Frm_Cnty_PHC;
		else Import_Frm_Cnty=INV156;

	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  HSPTL_ADMISSION_DT=hospitalized_admin_time;
		else HSPTL_ADMISSION_DT=INV132;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  HSPTL_DISCHARGE_DT=hospitalized_discharge_time;
		else HSPTL_DISCHARGE_DT=INV133;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  INV_ASSIGNED_DT=investigator_assigned_time;
	IF INVESTIGATION_FORM_CD NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN','INV_FORM_BMDNM','INV_FORM_BMDHI',
									'INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
									'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB')
		then  Import_Frm_City=imported_city_desc_txt;
		else Import_Frm_City=INV155;
 
	rename 

		local_id =Inv_Local_Id
		activity_from_time	= INV_START_DT
		rpt_form_cmplt_time = Inv_Rpt_Dt
		rpt_to_county_time	= Earliest_Rpt_To_Cnty_Dt
		rpt_to_state_time	= Earliest_Rpt_To_State_Dt
		effective_from_time	= Illness_Onset_Dt
		effective_to_time	= Illness_End_Dt
		case_class_cd		= Inv_Case_status_cd
		CASE_TYPE_CD		= Case_Type
		MMWR_WEEK1			= Case_Rpt_MMWR_Wk
		MMWR_YEAR1			= Case_Rpt_MMWR_Yr
		RPT_CNTY_CD			= Case_Rpt_Cnty_cd
		shared_ind			= Inv_Share_Ind
		diagnosis_time		= DIAGNOSIS_DT
		txt					= Inv_Comments
		RPT_SOURCE_CD		= rpt_src_cd

		program_jurisdiction_oid = case_oid
		public_health_case_uid = case_uid;
		

		
drop 
INV145 PRT103 MEA078 RUB162 MMWR_WEEK MMWR_YEAR HSPTLIZD_IND_PHC 
FOOD_HANDLR_IND_PHC DAYCARE_ASSOCIATION_IND_PHC PATIENT_PREGNANT_IND_PHC 
Import_Frm_City_PHC   Import_Frm_State_PHC Import_Frm_Cnty_PHC Import_Frm_Cntry_PHC;
run;

/*---------------------------------------

	Confirmation_method
	Confirmation_method_Group
	
-----------------------------------------*/
/*Lookup for Confirmation_Method_desc 'PHC_CONF_M' */

/*add confirmation_method_key*/
PROC SORT DATA=S_INVESTIGATION nodupkey; BY CASE_UID; RUN;


%DBLOAD (S_INVESTIGATION, S_INVESTIGATION);
PROC SQL;
CREATE TABLE L_INVESTIGATION_N  AS 
	SELECT DISTINCT S_INVESTIGATION.CASE_UID FROM NBS_RDB.S_INVESTIGATION
	EXCEPT SELECT L_INVESTIGATION.CASE_UID FROM NBS_RDB.L_INVESTIGATION;
CREATE TABLE L_INVESTIGATION_E AS
	SELECT S_INVESTIGATION.CASE_UID,L_INVESTIGATION.INVESTIGATION_KEY
		FROM NBS_RDB.S_INVESTIGATION,NBS_RDB.L_INVESTIGATION
WHERE S_INVESTIGATION.CASE_UID= L_INVESTIGATION.CASE_UID;
ALTER TABLE L_INVESTIGATION_N ADD INVESTIGATION_KEY_MAX_VAL  NUMERIC;
UPDATE L_INVESTIGATION_N SET INVESTIGATION_KEY_MAX_VAL=(SELECT MAX(INVESTIGATION_KEY) FROM NBS_RDB.L_INVESTIGATION);
QUIT;
%ASSIGN_ADDITIONAL_KEY (L_INVESTIGATION_N, INVESTIGATION_KEY);
PROC SORT DATA=L_INVESTIGATION_N NODUPKEY; BY INVESTIGATION_KEY; RUN;
DATA L_INVESTIGATION_N;
SET L_INVESTIGATION_N;
IF INVESTIGATION_KEY_MAX_VAL  ~=. THEN INVESTIGATION_KEY= INVESTIGATION_KEY+INVESTIGATION_KEY_MAX_VAL;
IF INVESTIGATION_KEY_MAX_VAL  =. THEN INVESTIGATION_KEY= INVESTIGATION_KEY+1;
DROP INVESTIGATION_KEY_MAX_VAL;
RUN;
%DBLOAD (L_INVESTIGATION, L_INVESTIGATION_N);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM S_INVESTIGATION),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.S_INVESTIGATION),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM L_INVESTIGATION_N),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM L_INVESTIGATION_E),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='S_INVESTIGATION');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
IF ACTIVITY_LOG_DETAIL_UID=. THEN ACTIVITY_LOG_DETAIL_UID=1;
IF ROW_COUNT_UPDATE<0 THEN ROW_COUNT_UPDATE=0;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN S_INVESTIGATION TABLE.'||
' THERE IS(ARE) NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE S_INVESTIGATION TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
proc sql;
   create index CASE_UID on S_INVESTIGATION(CASE_UID);
   create index INVESTIGATION_KEY on L_INVESTIGATION_N(INVESTIGATION_KEY);
   create index INVESTIGATION_KEY on L_INVESTIGATION_E(INVESTIGATION_KEY);
quit;
PROC SQL;
CREATE TABLE D_INVESTIGATION_N AS 
	SELECT * FROM NBS_RDB.S_INVESTIGATION , L_INVESTIGATION_N
WHERE S_INVESTIGATION.CASE_UID=L_INVESTIGATION_N.CASE_UID;
CREATE TABLE D_INVESTIGATION_E AS 
	SELECT * FROM NBS_RDB.S_INVESTIGATION , L_INVESTIGATION_E
WHERE S_INVESTIGATION.CASE_UID=L_INVESTIGATION_E.CASE_UID;
QUIT;
PROC SQL;
	CREATE TABLE INVESTIGATION_E_KEYHOLDER AS SELECT CASE_UID, INVESTIGATION_KEY from D_INVESTIGATION_E;
	CREATE TABLE INVESTIGATION_N_KEYHOLDER AS SELECT CASE_UID, INVESTIGATION_KEY from D_INVESTIGATION_N;
	CREATE TABLE INVESTIGATION_N_ALL_KEYHOLDER AS SELECT CASE_UID, INVESTIGATION_KEY from D_INVESTIGATION_N 
											UNION SELECT CASE_UID, INVESTIGATION_KEY from D_INVESTIGATION_E;

QUIT;
PROC SORT DATA=D_INVESTIGATION_N NODUPKEY; BY INVESTIGATION_KEY;RUN;
%DBLOAD (INVESTIGATION, D_INVESTIGATION_N);
DATA NBS_RDB.INVESTIGATION;
 MODIFY NBS_RDB.INVESTIGATION D_INVESTIGATION_E UPDATEMODE=NOMISSINGCHECK;
 BY INVESTIGATION_KEY;
RUN;
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM S_INVESTIGATION),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.INVESTIGATION),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM D_INVESTIGATION_N),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM D_INVESTIGATION_E),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='INVESTIGATION');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
IF ACTIVITY_LOG_DETAIL_UID=. THEN ACTIVITY_LOG_DETAIL_UID=1;
IF ROW_COUNT_UPDATE<0 THEN ROW_COUNT_UPDATE=0;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN INVESTIGATION TABLE.'||
' THERE IS(ARE) NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE INVESTIGATION TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
PROC SQL;

CREATE TABLE CONFIRMATION_METHOD_E AS SELECT CONFIRMATION_METHOD_KEY FROM NBS_RDB.CONFIRMATION_METHOD_GROUP WHERE INVESTIGATION_KEY IN (
		SELECT INVESTIGATION_KEY FROM INVESTIGATION_E_KEYHOLDER);
 
/*
DELETE FROM NBS_RDB.CONFIRMATION_METHOD 
	WHERE CONFIRMATION_METHOD_KEY IN 
	(SELECT CONFIRMATION_METHOD_KEY FROM NBS_RDB.CONFIRMATION_METHOD_GROUP WHERE INVESTIGATION_KEY IN (
		SELECT INVESTIGATION_KEY FROM INVESTIGATION_E_KEYHOLDER));
*/
DELETE FROM NBS_RDB.CONFIRMATION_METHOD_GROUP WHERE INVESTIGATION_KEY IN (
		SELECT INVESTIGATION_KEY FROM INVESTIGATION_E_KEYHOLDER);
DELETE FROM NBS_RDB.L_CONFIRMATION_METHOD WHERE INVESTIGATION_KEY IN (
		SELECT INVESTIGATION_KEY FROM INVESTIGATION_E_KEYHOLDER);


QUIT;

/*proc sql;
create table S_CONFIRMATION_METHOD_GROUP as 
select distinct Confirmation_method.public_health_case_uid, Confirmation_Method_cd,CONFIRMATION_METHOD_TIME
from nbs_ods.Confirmation_Method 
inner join nbs_ods.public_health_case PHC 
on Confirmation_method.public_health_case_uid=public_health_case.public_health_case_uid
	 where phc.LAST_CHG_TIME> (SELECT MAX(ACTIVITY_LOG_MASTER_LAST.START_DATE) FROM  ACTIVITY_LOG_MASTER_LAST)
order by public_health_case_uid;
quit;
*/

/*Updated to prevent duplicate case data due to mismatching confirmation_method_time*/
proc sql;
create table S_CONFIRMATION_METHOD_GROUP as
SELECT Confirmation_method.public_health_case_uid, 
       confirmation_method_cd, 
       a.confirmation_method_time
FROM
(
    SELECT Confirmation_method.public_health_case_uid, 
           MAX(confirmation_method_time) AS confirmation_method_time
    FROM nbs_ods.Confirmation_Method
         INNER JOIN nbs_ods.public_health_case PHC ON Confirmation_method.public_health_case_uid = PHC.public_health_case_uid
       and phc.LAST_CHG_TIME> (SELECT MAX(ACTIVITY_LOG_MASTER_LAST.START_DATE) FROM  ACTIVITY_LOG_MASTER_LAST)
    GROUP BY Confirmation_method.public_health_case_uid
) a
INNER JOIN nbs_ods.Confirmation_method ON a.public_health_case_uid = Confirmation_method.public_health_case_uid
GROUP BY Confirmation_method.public_health_case_uid, 
         confirmation_method_cd, 
         a.confirmation_method_time;
quit;

proc sort data = S_CONFIRMATION_METHOD_GROUP
			out= S_CONFIRMATION_METHOD_GROUP; by Confirmation_Method_cd; run;

proc sql;
CREATE TABLE CONFIRMATION_METHOD_N AS 
SELECT DISTINCT Confirmation_Method_cd, cvg.CODE_SHORT_DESC_TXT as Confirmation_Method_Desc 'Confirmation_Method_Desc' 
FROM S_CONFIRMATION_METHOD_GROUP 
left outer join nbs_srt.code_value_general cvg 
ON S_CONFIRMATION_METHOD_GROUP.confirmation_method_cd= cvg.code 
and cvg.code_set_nm='PHC_CONF_M' WHERE Confirmation_Method_cd
NOT IN (SELECT Confirmation_Method_cd FROM NBS_RDB.Confirmation_Method);
QUIT;
data CONFIRMATION_METHOD_N;
	retain Confirmation_Method_key 1;
	set CONFIRMATION_METHOD_N;
	by CONFIRMATION_METHOD_CD;
	if first.Confirmation_Method_cd then Confirmation_Method_key+1;
run;
proc sql;

ALTER TABLE CONFIRMATION_METHOD_N ADD CONFIRMATION_METHOD_KEY_MAX_VAL  NUMERIC;
UPDATE CONFIRMATION_METHOD_N SET CONFIRMATION_METHOD_KEY_MAX_VAL=(SELECT MAX(CONFIRMATION_METHOD_KEY) FROM NBS_RDB.Confirmation_Method);
QUIT;
DATA CONFIRMATION_METHOD_N;
SET CONFIRMATION_METHOD_N;
IF CONFIRMATION_METHOD_KEY_MAX_VAL  ~=. THEN CONFIRMATION_METHOD_KEY= CONFIRMATION_METHOD_KEY+CONFIRMATION_METHOD_KEY_MAX_VAL;
IF CONFIRMATION_METHOD_KEY_MAX_VAL  =. THEN CONFIRMATION_METHOD_KEY= CONFIRMATION_METHOD_KEY+1;
DROP CONFIRMATION_METHOD_KEY_MAX_VAL;
RUN;
%DBLOAD (Confirmation_Method, confirmation_method_n);

PROC SQL;
CREATE TABLE L_CONFIRMATION_METHOD AS 
	SELECT distinct 
		coalesce(cm1.Confirmation_method_key,1)	as Confirmation_method_key,
		inv.Investigation_Key, 
		inv.case_uid
	from	
		INVESTIGATION_N_ALL_KEYHOLDER		as inv
	LEFT JOIN
		S_CONFIRMATION_METHOD_GROUP	
	ON 	inv.case_uid = S_CONFIRMATION_METHOD_GROUP.public_health_case_uid
	LEFT JOIN NBS_RDB.Confirmation_Method as cm1
	ON Confirmation_Method.Confirmation_method_CD =S_CONFIRMATION_METHOD_GROUP.Confirmation_method_CD;

QUIT;
PROC SORT DATA=L_CONFIRMATION_METHOD NODUPKEY; BY CONFIRMATION_METHOD_KEY Investigation_Key; RUN;

%DBLOAD (L_CONFIRMATION_METHOD, L_CONFIRMATION_METHOD);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM S_CONFIRMATION_METHOD_GROUP),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.Confirmation_Method),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM L_Confirmation_Method),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM CONFIRMATION_METHOD_E),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='S_CONFIRMATION_METHOD_GROUP');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
IF ACTIVITY_LOG_DETAIL_UID=. THEN ACTIVITY_LOG_DETAIL_UID=1;
IF ROW_COUNT_UPDATE<0 THEN ROW_COUNT_UPDATE=0;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN S_CONFIRMATION_METHOD_GROUP TABLE.'||
' THERE IS(ARE) NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE S_CONFIRMATION_METHOD_GROUP TABLE.';
RUN;
%DBLOAD (S_CONFIRMATION_METHOD_GROUP, S_CONFIRMATION_METHOD_GROUP);
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);
proc sql;
create table Confirmation_method_group as  
select 	DISTINCT INV.Investigation_Key,
		INV.Confirmation_method_key,	
		cm2.Confirmation_Method_cd,
		cm1.CONFIRMATION_METHOD_TIME	as CONFIRMATION_DT/*,  
		cm1.Confirmation_Method_Desc as Confirmation_Method_Desc length=50,
		inv.case_uid,cm1.public_health_case_uid*/
		
from	L_CONFIRMATION_METHOD		as INV
	left  join S_CONFIRMATION_METHOD_GROUP	as CM1
		on 	CM1.public_health_case_uid = inv.case_uid

left  join NBS_RDB.Confirmation_Method	as CM2
		on 	INV.Confirmation_method_key = CM2.Confirmation_method_key
order by 1,2
;
quit;

proc sort  data = Confirmation_method_group;
by Confirmation_method_key; 
run;
/*
data Confirmation_Method (keep= Confirmation_method_key Confirmation_Method_cd Confirmation_Method_Desc) ;
set S_Confirmation_method_group;
	by Confirmation_method_key; 
	if first.Confirmation_method_key then output Confirmation_Method;
run;

PROC SQL;
create table CONFIRMATION_METHOD_UPDATE as select * from 
CONFIRMATION_METHOD where CONFIRMATION_METHOD_CD not in (select distinct CONFIRMATION_METHOD_CD from nbs_rdb.CONFIRMATION_METHOD);
quit;
*/
%DBLOAD (Confirmation_Method_group, Confirmation_Method_group);
PROC SQL;
UPDATE ACTIVITY_LOG_DETAIL SET SOURCE_ROW_COUNT=(SELECT COUNT(*) FROM CONFIRMATION_METHOD_GROUP),
END_DATE=DATETIME(),
DESTINATION_ROW_COUNT=(SELECT COUNT(*) FROM NBS_RDB.CONFIRMATION_METHOD_GROUP),
ACTIVITY_LOG_DETAIL_UID= ((SELECT MAX(ACTIVITY_LOG_DETAIL_UID) FROM NBS_RDB.ACTIVITY_LOG_DETAIL)+1),
ROW_COUNT_INSERT=(SELECT COUNT(*) FROM L_Confirmation_Method),
ROW_COUNT_UPDATE=(SELECT COUNT(*) FROM CONFIRMATION_METHOD_E),
PROCESS_UID= (SELECT PROCESS_UID FROM NBS_RDB.ETL_PROCESS WHERE PROCESS_NAME='CONFIRMATION_METHOD_GROUP');
QUIT;
DATA ACTIVITY_LOG_DETAIL;
SET ACTIVITY_LOG_DETAIL;
IF ACTIVITY_LOG_DETAIL_UID=. THEN ACTIVITY_LOG_DETAIL_UID=1;
IF ROW_COUNT_UPDATE<0 THEN ROW_COUNT_UPDATE=0;
ADMIN_COMMENT=COMPRESS(ROW_COUNT_INSERT) || ' RECORD(S) INSERTED AND ' ||COMPRESS(ROW_COUNT_UPDATE) || ' RECORD(S) UPDATED IN CONFIRMATION_METHOD_GROUP TABLE.'||
' THERE IS(ARE) NOW '|| COMPRESS(DESTINATION_ROW_COUNT) || ' TOTAL NUMBER OF RECORD(S) IN THE CONFIRMATION_METHOD_GROUP TABLE.';
RUN;
%DBLOAD (ACTIVITY_LOG_DETAIL, ACTIVITY_LOG_DETAIL);






/*Delete Temporary Data Sets*/
PROC datasets library = work nolist;
delete 
	Investigation
	invobscoded
	invobsdate
	invobstxt
	invobsnum
	invcodedvalue2
	importCDs
	CONFIRMATION_METHOD_E
	Confirmation_Method
	Confirmation_method_group
	PHC_Keys
	Invtxt2
	Invtxt
	Invnum1_2
	Invnum1
	Invfrom_time2
	Invfrom_time
	Invcodedvalue
	Importcds2
;
run;
quit;


