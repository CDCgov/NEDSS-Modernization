OPTIONS SORTPGM=BEST;
options compress=yes;
proc sql;
create table All_Case as 
SELECT INVESTIGATION.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY FROM NBS_RDB.INVESTIGATION LEFT OUTER JOIN NBS_RDB.CASE_COUNT ON
 INVESTIGATION.INVESTIGATION_KEY =CASE_COUNT.INVESTIGATION_KEY WHERE CASE_TYPE='I'; 
quit;

/*=================================================== ALL_CASE Load Complete ==================================*/
proc sql;
create table GEN_PATIENT_ADD as 
	select  GC.*,
			C.CONDITION_CD AS CONDITION_CD 'CONDITION_CD',
			p.PATIENT_local_id AS PATIENT_LOCAL_ID 'PATIENT_LOCAL_ID',
			P.PATIENT_FIRST_NAME AS PATIENT_FIRST_NM 'PATIENT_FIRST_NM',
			P.PATIENT_MIDDLE_NAME AS PATIENT_MIDDLE_NM 'PATIENT_MIDDLE_NM',
			P.PATIENT_LAST_NAME AS PATIENT_LAST_NM 'PATIENT_LAST_NM',         
			P.PATIENT_PHONE_HOME AS PATIENT_HOME_PHONE 'PATIENT_HOME_PHONE',
			P.PATIENT_PHONE_EXT_HOME,
			P.PATIENT_STREET_ADDRESS_1,
			P.PATIENT_STREET_ADDRESS_2,
			P.PATIENT_CITY,
			P.PATIENT_STATE,
			P.PATIENT_ZIP,
			p.PATIENT_RACE_CALCULATED AS RACE 'RACE',
			P.PATIENT_COUNTY,
			P.PATIENT_DOB AS PATIENT_DOB 'PATIENT_DOB',   
			P.PATIENT_AGE_REPORTED AS PATIENT_REPORTED_AGE'PATIENT_REPORTED_AGE', 
			P.PATIENT_AGE_REPORTED_UNIT AS PATIENT_REPORTED_AGE_UNITS 'PATIENT_REPORTED_AGE_UNITS', 
			P.PATIENT_CURRENT_SEX AS PATIENT_CURR_GENDER'PATIENT_CURR_GENDER',      
 			P.PATIENT_ENTRY_METHOD AS PATIENT_ELECTRONIC_IND'PATIENT_ELECTRONIC_IND',
 			P.PATIENT_UID AS PATIENT_UID 'PATIENT_UID'
	from ALL_CASE as GC
	left join NBS_RDB.D_PATIENT as p
	on GC.PATIENT_KEY = p.PATIENT_key
	left join NBS_RDB.CONDITION as C
	on C.CONDITION_KEY = GC.CONDITION_KEY
	AND P.PATIENT_KEY ~= 1;
QUIT;
DATA GEN_PATIENT_ADD;
SET GEN_PATIENT_ADD;
if(PATIENT_HOME_PHONE ~='' && PATIENT_PHONE_EXT_HOME ~='') then PATIENT_HOME_PHONE=trim(PATIENT_HOME_PHONE)||' ext. '||trim(PATIENT_PHONE_EXT_HOME);
	if(PATIENT_HOME_PHONE ~='' && PATIENT_PHONE_EXT_HOME ='') then PATIENT_HOME_PHONE=trim(PATIENT_HOME_PHONE);
	if(PATIENT_HOME_PHONE ='' && PATIENT_PHONE_EXT_HOME ~='') then PATIENT_HOME_PHONE='ext. '||trim(PATIENT_PHONE_EXT_HOME);
RUN;
proc sql;
create table GEN_PAT_ADD_INV as
	select GPA.*,
			i.INV_LOCAL_ID 'INV_LOCAL_ID',	
			i.INVESTIGATION_STATUS 'INVESTIGATION_STATUS',	
			i.INV_CASE_STATUS 'INV_CASE_STATUS',	
			i.JURISDICTION_NM AS INV_JURISDICTION_NM 'INV_JURISDICTION_NM',	
			i.ILLNESS_ONSET_DT 'ILLNESS_ONSET_DT',	
			i.INV_START_DT 'INV_START_DT',
			i.INV_RPT_DT 'INV_RPT_DT',	
			i.RPT_SRC_CD_DESC 'RPT_SRC_CD_DESC',	
			i.EARLIEST_RPT_TO_CNTY_DT 'EARLIEST_RPT_TO_CNTY_DT',	
			i.EARLIEST_RPT_TO_STATE_DT 'EARLIEST_RPT_TO_STATE_DT',	
			i.DIE_FRM_THIS_ILLNESS_IND 'DIE_FRM_THIS_ILLNESS_IND',	
			I.outbreak_ind 'OUTBREAK_IND',
    		I.DISEASE_IMPORTED_IND,
			I.Import_Frm_Cntry AS IMPORT_FROM_COUNTRY 'IMPORT_FROM_COUNTRY',
			I.Import_Frm_State AS IMPORT_FROM_STATE 'IMPORT_FROM_STATE',
			I.Import_Frm_Cnty AS IMPORT_FROM_COUNTY 'IMPORT_FROM_COUNTY',
			I.Import_Frm_City AS IMPORT_FROM_CITY 'IMPORT_FROM_CITY',
			i.CASE_RPT_MMWR_WK 'CASE_RPT_MMWR_WK',	
			i.CASE_RPT_MMWR_YR 'CASE_RPT_MMWR_YR',	
			i.DIAGNOSIS_DT 'DIAGNOSIS_DT',	
			i.HSPTLIZD_IND 'HSPTLIZD_IND',
			i.HSPTL_ADMISSION_DT 'HSPTL_ADMISSION_DT',
			I.HSPTL_DISCHARGE_DT,
			I.HSPTL_DURATION_DAYS,
			I.Transmission_mode,
			I.CASE_OID,
			i.INV_COMMENTS 'INV_COMMENTS',
			em.ADD_TIME AS INV_ADD_TIME 'INV_ADD_TIME',
			em.LAST_CHG_TIME AS PHC_LAST_CHG_TIME 'PHC_LAST_CHG_TIME',
			em.PROG_AREA_DESC_TXT AS PROGRAM_AREA_DESCRIPTION 'PROGRAM_AREA_DESCRIPTION',
			i.record_status_cd
			from work.GEN_PATIENT_ADD as GPA
	left join NBS_RDB.investigation as i
	on GPA.investigation_key=i.investigation_key
	left join NBS_RDB.EVENT_METRIC as em
	on em.event_uid = i.case_uid
	and i.investigation_key ~= 1
	WHERE     (I.RECORD_STATUS_CD <> 'INACTIVE') AND (I.CASE_TYPE <> 'S');
QUIT; 
proc datasets memtype=DATA;
   delete GEN_PATIENT_ADD;
run;
PROC SQL;
CREATE TABLE GEN_PATCOMPL_INV_PROVIDER AS
	SELECT GPI. *,
		   PP.PROVIDER_FIRST_NAME,
		   PP.PROVIDER_LAST_NAME,
		   PP.PROVIDER_MIDDLE_NAME,
		   PP.PROVIDER_PHONE_WORK,
		   PP.PROVIDER_PHONE_EXT_WORK
	FROM WORK.GEN_PAT_ADD_INV AS GPI
	LEFT JOIN NBS_RDB.D_PROVIDER AS PP
	ON GPI.PHYSICIAN_KEY=PP.PROVIDER_KEY
	ORDER BY PATIENT_KEY;
QUIT;

DATA GEN_PATCOMPL_INV_PROVIDER;
SET GEN_PATCOMPL_INV_PROVIDER; 
PHYSICIAN_NAME=trim(PROVIDER_LAST_NAME)||', '||trim(PROVIDER_FIRST_NAME);
if(PROVIDER_PHONE_WORK ~='' && PROVIDER_PHONE_EXT_WORK ~='') then PHYSICIAN_PHONE=trim(PROVIDER_PHONE_WORK)||' ext. '||trim(PROVIDER_PHONE_EXT_WORK);
if(PROVIDER_PHONE_WORK ~='' && PROVIDER_PHONE_EXT_WORK ='') then PHYSICIAN_PHONE=trim(PROVIDER_PHONE_WORK);
if(PROVIDER_PHONE_WORK ='' && PROVIDER_PHONE_EXT_WORK ~='') then PHYSICIAN_PHONE='ext. '||trim(PROVIDER_PHONE_EXT_WORK);
RUN;
PROC SQL;
CREATE TABLE GEN_PATCOMPL_INV_INVESTIGATOR AS
	SELECT GPI. *,
		   O.ORGANIZATION_NAME AS REPORTING_SOURCE 'REPORTING_SOURCE'
	FROM GEN_PATCOMPL_INV_PROVIDER AS GPI
	LEFT JOIN NBS_RDB.D_ORGANIZATION AS O
	ON GPI.RPT_SRC_ORG_KEY=O.ORGANIZATION_KEY
	ORDER BY PATIENT_KEY;
QUIT;

/* GET THE CONDITION - JOIN WITH THE CONDITION TABLE */

PROC SQL;
CREATE TABLE GEN_PATINFO_INV_PHY_RPTSRC_COND AS
	SELECT GPIPR.*,
		   C.CONDITION_SHORT_NM,
		   C.PROGRAM_AREA_DESC
	FROM WORK.GEN_PATCOMPL_INV_INVESTIGATOR AS GPIPR
	LEFT JOIN NBS_RDB.CONDITION AS C
	ON GPIPR.CONDITION_KEY=C.CONDITION_KEY;
QUIT;
proc datasets memtype=DATA;
   delete GEN_PATINFO_INV_PHY_RPTSRC;
run;
/*
Derive the event date using following algorithm
1. Illness_onset_dt
2. Diagnosis_Dt
3. The earliest of the following dates:
	Earliest_rpt_to_cnty_dt,
	Earliest_rpt_to_state_dt,
	Inv_rpt_dt. Inv_rpt_dt,
	Inv_start_dt,
	ALT_Result_dt,
	AST_result_dt,
	HSPTL_Admission_dt,
	Hsptl_discharge_dt
*/
data GENERIC_DBASE_WITH_EVENT_DATE;
set GEN_PATINFO_INV_PHY_RPTSRC_COND;
if ILLNESS_ONSET_DT ~= . then 
		EVENT_DATE = ILLNESS_ONSET_DT;
else if DIAGNOSIS_DT ~= . then 
		EVENT_DATE = DIAGNOSIS_DT;
if EVENT_DATE = . then
	do; 
		EVENT_DATE = EARLIEST_RPT_TO_CNTY_DT;
		if EVENT_DATE ~= . then
		    do; 
				if EARLIEST_RPT_TO_STATE_DT ~= . AND EARLIEST_RPT_TO_STATE_DT < EVENT_DATE then
				EVENT_DATE=EARLIEST_RPT_TO_STATE_DT;
			end;
		else EVENT_DATE = EARLIEST_RPT_TO_STATE_DT;

		if EVENT_DATE ~= . then
		    do; 
				if INV_RPT_DT ~= . AND INV_RPT_DT < EVENT_DATE then
				EVENT_DATE=INV_RPT_DT;
			end;
		else EVENT_DATE = INV_RPT_DT;

		if EVENT_DATE ~= . then
		    do; 
				if INV_START_DT ~= . AND INV_START_DT < EVENT_DATE then
				EVENT_DATE=INV_START_DT;
			end;
		else EVENT_DATE = INV_START_DT;

		if EVENT_DATE ~= . then
		    do; 
				if ALT_RESULT_DT ~= . AND ALT_RESULT_DT < EVENT_DATE then
				EVENT_DATE=ALT_RESULT_DT;
			end;
		else EVENT_DATE = ALT_RESULT_DT;

		if EVENT_DATE ~= . then
		    do; 
				if AST_RESULT_DT ~= . AND AST_RESULT_DT < EVENT_DATE then
				EVENT_DATE=AST_RESULT_DT;
			end;
		else EVENT_DATE = AST_RESULT_DT;

		if EVENT_DATE ~= . then
		    do; 
				if HSPTL_ADMISSION_DT ~= . AND HSPTL_ADMISSION_DT < EVENT_DATE then
				EVENT_DATE=HSPTL_ADMISSION_DT;
			end;
		else EVENT_DATE = HSPTL_ADMISSION_DT;

		if EVENT_DATE ~= . then
		    do; 
				if HSPTL_DISCHARGE_DT ~= . AND HSPTL_DISCHARGE_DT < EVENT_DATE then
				EVENT_DATE=HSPTL_DISCHARGE_DT;
			end;
		else EVENT_DATE = HSPTL_DISCHARGE_DT;

		if EVENT_DATE ~= . then
		    do; 
				if INV_ADD_TIME ~= . AND INV_ADD_TIME < EVENT_DATE then
				EVENT_DATE=INV_ADD_TIME;
			end;
		else EVENT_DATE = INV_ADD_TIME;

end;
run; 

DATA GENERIC_DBASE (DROP= PATIENT_REPORTED_AGE Case_Rpt_MMWR_Wk CASE_RPT_MMWR_YR);
SET GENERIC_DBASE_WITH_EVENT_DATE;
rename
Case_Rpt_MMWR_Wk = Case_Rpt_MMWR_WEEK
CASE_RPT_MMWR_YR = CASE_RPT_MMWR_YEAR
PATIENT_REPORTED_AGE=PATIENT_REPORTEDAGE
;
PROC SORT DATA=GENERIC_DBASE OUT=GENERIC_DBASE NODUPKEY;
BY INVESTIGATION_KEY;
RUN;

PROC SQL;
CREATE TABLE RDBDATA.CASE_LAB_DATAMART AS
		SELECT 	
				INVESTIGATION_KEY,
				PATIENT_LOCAL_ID,
				INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,
				PATIENT_FIRST_NM,
				PATIENT_MIDDLE_NM ,
				PATIENT_LAST_NM,    
				PATIENT_STREET_ADDRESS_1,
				PATIENT_STREET_ADDRESS_2,
				/*PATIENT_ADDRESS,  */
				PATIENT_CITY,
				PATIENT_STATE,
				PATIENT_ZIP, 
				PATIENT_COUNTY,
				PATIENT_HOME_PHONE,
				PATIENT_DOB,
				PATIENT_REPORTEDAGE AS AGE_REPORTED,  
				PATIENT_REPORTED_AGE_UNITS AS AGE_REPORTED_UNIT,
				PATIENT_CURR_GENDER AS PATIENT_CURRENT_SEX,      
				RACE,
				INV_JURISDICTION_NM AS JURISDICTION_NAME,
	   			PROGRAM_AREA_DESCRIPTION,
				INV_START_DT AS INVESTIGATION_START_DATE,
				INV_CASE_STATUS AS CASE_STATUS,
				condition_short_nm AS DISEASE,
				CONDITION_CD AS DISEASE_CD,
				REPORTING_SOURCE,
				INV_COMMENTS AS GENERAL_COMMENTS,
				PHYSICIAN_NAME,
	   			PHYSICIAN_PHONE,
	   			/*LABORATORY_INFORMATION,*/
	   			CASE_OID AS PROGRAM_JURISDICTION_OID,
	   			INV_ADD_TIME AS PHC_ADD_TIME,
	   			PHC_LAST_CHG_TIME,
				EVENT_DATE
	FROM GENERIC_DBASE
	ORDER BY INVESTIGATION_KEY;
QUIT;
/************ LAB INFORMATION STUFF STARTS FROM HERE *******************************************/
proc sql noprint;
create table invlab as
	SELECT l.INVESTIGATION_KEY, l.LAB_TEST_KEY
	FROM nbs_rdb.LAB_TEST_RESULT l  INNER JOIN nbs_rdb.INVESTIGATION I ON l.INVESTIGATION_KEY = I.INVESTIGATION_KEY
	WHERE (l.LAB_TEST_KEY IN(SELECT  LAB_TEST_KEY FROM nbs_rdb.LAB_TEST)) 
	AND (l.INVESTIGATION_KEY <> 1) 	AND (I.RECORD_STATUS_CD = 'ACTIVE')
	ORDER BY LAB_TEST_KEY;

create table lab as
	select lab_test_key,lab_rpt_local_id from nbs_rdb.lab_test order by lab_test_key;
quit;

data both;
   merge invlab lab;
   by lab_test_key;
run;

data both;
	set both;
	if investigation_key ~= .;
run;

proc sort data=both out=both;
	by investigation_key;
run;
proc sql noprint;
	create table inv2labs as
		SELECT distinct b.investigation_key, b.lab_test_key,
		l.lab_rpt_LOCAL_ID, l.LAB_RPT_RECEIVED_BY_PH_DT, l.SPECIMEN_COLLECTION_DT,
		l.RESULTED_LAB_TEST_CD_DESC, l.RESULTEDTEST_VAL_CD_DESC, l.NUMERIC_RESULT_WITHUNITS, l.LAB_RESULT_TXT_VAL, l.LAB_RESULT_COMMENTS,
		l.ELR_IND
	  	FROM both b inner join nbs_rdb.lab100 l  on
	  	l.LAB_RPT_LOCAL_ID = b.LAB_RPT_LOCAL_ID
	  	order by b.investigation_key;
quit;
/* Retrieving LabResults for the Morbs (associated to INVs) starts here */

proc sql noprint;
create table invmorb as
	SELECT ME.MORB_RPT_KEY, I.INVESTIGATION_KEY, I.INV_LOCAL_ID, MR.MORB_RPT_LOCAL_ID
	FROM nbs_rdb.MORBIDITY_REPORT_EVENT ME INNER JOIN
	nbs_rdb.INVESTIGATION I ON ME.INVESTIGATION_KEY = I.INVESTIGATION_KEY INNER JOIN
	nbs_rdb.MORBIDITY_REPORT MR ON ME.MORB_RPT_KEY = MR.MORB_RPT_KEY
	WHERE     (I.RECORD_STATUS_CD = 'ACTIVE')
	ORDER BY ME.MORB_RPT_KEY;

create table morbResults as
	select * from nbs_rdb.lab100  where morb_rpt_key in(
	SELECT ME.MORB_RPT_KEY
	FROM nbs_rdb.MORBIDITY_REPORT_EVENT ME INNER JOIN
	nbs_rdb.INVESTIGATION I ON ME.INVESTIGATION_KEY = I.INVESTIGATION_KEY
	WHERE (I.RECORD_STATUS_CD = 'ACTIVE')
	) ORDER BY morb_rpt_key;

quit;

proc sql noprint;
	create table morbLabResults as
		SELECT distinct a.investigation_key, b.resulted_lab_test_key,
		a.MORB_RPT_LOCAL_ID as lab_rpt_LOCAL_ID 'lab_rpt_LOCAL_ID', 
		b.LAB_RPT_RECEIVED_BY_PH_DT, b.SPECIMEN_COLLECTION_DT,
		b.RESULTED_LAB_TEST_CD_DESC, b.RESULTEDTEST_VAL_CD_DESC, b.NUMERIC_RESULT_WITHUNITS, b.LAB_RESULT_TXT_VAL, b.LAB_RESULT_COMMENTS
	  	FROM invmorb a inner join morbResults b  on
	  	a.MORB_RPT_KEY = b.MORB_RPT_KEY
	  	order by a.investigation_key;
quit;
/* Retrieving LabResults for the Morbs (associated to INVs) ends here */

/* APPEND both Labs(associated to INVs) and Morbs(with L/R Info associated to INVs now)*/

DATA Inv2labs;
	SET Inv2labs Morblabresults; 
RUN;

/* Displaying Lab Test-Result Information starts below */
proc sql noprint;
	create table sample1
	as select
	investigation_key as key 'KEY',	"" as Bigchunk from rdbdata.CASE_LAB_DATAMART;
quit;

proc sql noprint;
	create table sample2
	as select 
	investigation_key as key 'KEY',
	lab_test_key as subkey 'SUBKEY',
	put(datepart((LAB_RPT_RECEIVED_BY_PH_DT)),mmddyy10.) as c1 'C1',
	put(datepart((SPECIMEN_COLLECTION_DT)),mmddyy10.) as c2 'C2',
	trim(RESULTED_LAB_TEST_CD_DESC) as c3 'C3',
	trim(RESULTEDTEST_VAL_CD_DESC) as c4 'C4',
	trim(NUMERIC_RESULT_WITHUNITS) as c5 'C5',
	trim(LAB_RESULT_TXT_VAL) as c6 'C6',
	trim(LAB_RESULT_COMMENTS) as c7 'C7',
	trim(LAB_RPT_LOCAL_ID) as c8 'C8',
  	trim(ELR_IND) as c9 'c9'
	from inv2labs;
quit;

proc sql noprint;
	create table sample3 as select distinct key, subkey,
		(
		'<b>Local ID:</b> ' || trim(C8) || '<br>' ||
		'<b>Date Received by PH:</b> ' || trim(C1) || '<br>' ||
		'<b>Specimen Collection Date:</b> ' || trim(C2) || '<br>' ||
		'<b>ELR Indicator:<b>'|| trim (c9)|| '<br>' ||
		'<b>Resulted Test:</b> ' || ifc(trim(C3)="","",trim(C3)) || '<br>' ||
		'<b>Coded Result:</b> ' || ifc(trim(C4)="","",trim(C4)) || '<br>' ||
		'<b>Numeric Result:</b> ' || ifc(trim(C5)="","",trim(C5)) || '<br>' ||
		'<b>Text Result:</b> ' || ifc(trim(C6)="","",trim(C6)) || '<br>' ||
		'<b>Comments:</b> ' || ifc(trim(C7)="","",trim(C7)) )	as bigChunk
		from sample2;
quit;

data sample4(rename=(
			lab9=lab_concatenated_desc_txt key=investigation_key)
			);
	set sample3;
	by	key;
	format lab1-lab8 $2000. lab9 $4000.;
	array lab(8) lab1-lab8;
	retain lab1-lab9 ' ' i 0;

	if first.key then do;
		do j=1 to 8; lab(j) = ' ';	end;
		i = 0; lab9 = ''; 
		end;
	i+1;
	if i <= 8 then do;
		lab(i) = bigChunk;
		lab9 =left(trim(bigChunk))||'<br><br>'|| left(trim(lab9)) ;
	end;
	if last.key then output;
run;

proc sql noprint;
	create table sample5 as select investigation_key, lab_concatenated_desc_txt as LABORATORY_INFORMATION from sample4;
run;

data RDBDATA.CASE_LAB_DATAMART;
   merge rdbdata.CASE_LAB_DATAMART sample5;
   by investigation_key;
run;

/************ LAB INFORMATION STUFF ENDS HERE **************************************************/

%dbload (CASE_LAB_DATAMART, RDBDATA.CASE_LAB_DATAMART);
QUIT;

proc sql;
create table SPECIMEN_COLLECTION_TABLE AS 
select distinct investigation_key as key 'KEY', SPECIMEN_COLLECTION_DT  from 	inv2labs order by SPECIMEN_COLLECTION_DT asc;
/*create table SPECIMEN_COLLECTION_NON_SORTED AS 
select investigation_key as key 'KEY', SPECIMEN_COLLECTION_DT  from 	inv2labs order by SPECIMEN_COLLECTION_DT asc;*/
quit;
PROC SQL;
CREATE TABLE RDBDATA.CASE_LAB_DATAMART_MODIFIED AS SELECT  *,SPECIMEN.SPECIMEN_COLLECTION_DT 
FROM
RDBDATA.CASE_LAB_DATAMART  case
LEFT OUTER JOIN 
SPECIMEN_COLLECTION_TABLE SPECIMEN
ON
  CASE.INVESTIGATION_KEY = SPECIMEN.KEY
ORDER BY
   INVESTIGATION_KEY;
RUN;
/* DELETES ALL FILES FROM THE WORK FOLDER */
PROC DATASETS LIB=WORK MEMTYPE=DATA
		KILL;
RUN;
QUIT;
