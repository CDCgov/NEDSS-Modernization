
/**SET UP CONNECTION TO RDB DATABASE**/

libname nbs_rdb1 oracle user=rdb password=rdb path=rdb111 DBLIBINIT=  'drop table lab_organism_drug_fact';
libname nbs_rdb oracle user=rdb password=rdb path=rdb111;
libname nbs_srt oracle user=nbs_ods password=ods path=DEVDB1  schema=nbs_srte ACCESS=READONLY;


/*get srt value for drugs*/
/*CIPROFLOXACIN 18906-8
CLINDAMYCIN 18908-4
ERYTHROMYCIN 18919-1
GENTAMICIN 18928-2
LOMEFLOXACIN 18939-9
OXACILLIN 18961-3
PENICILLIN 18964-7
RIFAMPIN 18974-6
VANCOMYCIN 19000-9*/
DATA drugs (keep= LAB_TEST_DESC_TXT);
SET nbs_srt.lab_test;
WHERE drug_test_ind = 'Y' 
	AND test_type_cd = 'R'
	AND LAB_TEST_CD IN ('18906-8','18908-4', '18919-1', '18928-2', '18939-9', '18961-3', '18964-7', '18974-6', '19000-9' ); 
RUN;



/*get srt value for selected organism name (Enterococcus species (organism), Staphylococcus aureus (organism), Streptococcus pneumoniae (organism)*/
/*DATA organism;
SET nbs_srt.lab_result;
WHERE LAB_RESULT_CD in('L-25116', 'L-1E60A', 'L-24801'); 
RUN;*/

DATA organism;
SET nbs_srt.lab_result;
WHERE ORGANISM_NAME_IND = 'Y';
RUN;

/*DATA coded_result_value;
SET nbs_srt.lab_result;
WHERE organism_name_ind = 'N'AND LAB_RESULT_CD IN ('SENS', 'INTMED', 'RSNT');
RUN;

PROC FORMAT;
VALUE $CODED_RESULT_VAL_FMT 'SENS' = 'Sensitive'
						   'INTMED' = 'Intermediate'
						   'RSNT' = 'Resistant';
RUN;*/
proc sql;
CREATE TABLE CODED_RESULT_VALUE AS
SELECT lab_result_cd AS START, 
	lab_result_desc_txt AS LABEL, 
	'$CODED_FMT' AS FMTNAME 
FROM NBS_SRT.lab_result 
WHERE organism_name_ind = 'N' AND LAB_RESULT_CD ~='HIGH' AND LAB_RESULT_CD ~='LOW'; 
QUIT;

DATA interpretation_flg (keep = CODE CODE_DESC_TXT fmtname);
SET nbs_srt.code_value_general;
fmtname = '$INTERCODE';
WHERE code_set_nm = 'OBS_INTRP';
RUN;	

PROC DATASETS nolist;
	MODIFY interpretation_flg;
	rename code = start CODE_DESC_TXT = label;
RUN;
PROC FORMAT CNTLIN = CODED_RESULT_VALUE FMTLIB; RUN; QUIT;
PROC FORMAT CNTLIN = interpretation_flg FMTLIB; RUN; QUIT;
		
PROC SQL;
CREATE TABLE lab_test_sus AS
	SELECT lt.lab_test_key,
		lt.oid AS PROGRAM_JURISDICTION_OID, 
		lt.JURISDICTION_CD, 
		lt.JURISDICTION_NM, 
		lt.root_ordered_test_pntr, 
		lt.PARENT_TEST_PNTR, 
		lt.LAB_TEST_PNTR,
		lt.specimen_src,
		lt.specimen_site,
		lt.specimen_site_desc, 
		lt.LAB_TEST_TYPE,
		lt.SPECIMEN_COLLECTION_DT,
		LT.LAB_TEST_CD, /*DRUG NAME CODE*/
		lt.LAB_TEST_CD_DESC,   /*DRUG NAME DESC*/
		PUT(lt.interpretation_flg, $INTERCODE.) AS Interpretation_Flg,
		PUT(lrv.TEST_RESULT_VAL_CD, $CODED_FMT.) AS TEST_RESULT_VAL_CD, /*will contain code for Coded Result Value or Organism name*/
		lrv.TEST_RESULT_VAL_CD_DESC /*same as above but description*/
	FROM nbs_rdb.lab_test lt, 
		nbs_rdb.lab_test_result ltr, 
		nbs_rdb.lab_result_val lrv		
	WHERE lt.lab_test_key = ltr.lab_test_key
		AND ltr.test_result_grp_key = lrv.test_result_grp_key
		AND lab_test_type IN ('Order', 'R_Result', 'Result')
		
;
QUIT;

/**split up RESULT, R_RESULT, ORDER into separate data sets**/
DATA lab_test_result lab_test_r_result lab_test_order;
SET lab_test_sus;
IF lab_test_type = 'Result' THEN OUTPUT lab_test_result;
ELSE IF lab_test_type = 'Order' THEN OUTPUT lab_test_order;
ELSE OUTPUT lab_test_r_result;
RUN;

/**isolate only the RESULTS that have ORGANISM TESTED NAME value of Streptococcus Pneumonie, Enterococcus, Staphylococcus aureus**/
/*
DATA lab_test_result;
SET lab_test_result;
WHERE TEST_RESULT_VAL_CD in ('L-25116', 'L-1E60A', 'L-24801');
RUN;*/
proc sql;
create table lab_test_result as
select * from lab_test_result where root_ordered_test_pntr in (select root_ordered_test_pntr from lab_test_r_result);
quit;
/*PROC SQL;
CREATE TABLE lab_test_result as 
	SELECT  * 
	FROM lab_test_result 
	WHERE TEST_RESULT_VAL_CD IN (SELECT LAB_RESULT_CD FROM organism); 
QUIT;
*/
/*data lab_test_r_result (drop=LAB_TEST_CD_DESC);
set lab_test_r_result; run;*/

/**retrieve R_RESULT rows that are based on lab_test_result root_order_test_pntr**/
/*
PROC SQL;
CREATE TABLE filtered_r_result AS
	SELECT ltr.*, lt.LAB_TEST_DESC_TXT as LAB_TEST_CD_DESC 
	FROM lab_test_r_result LTR, NBS_SRT.LAB_TEST LT 
	WHERE root_ordered_test_pntr 
	IN (SELECT root_ordered_test_pntr 
		FROM lab_test_result)
	AND 
	LT.LAB_TEST_CD = LTR.LAB_TEST_CD
	AND LT.LAB_TEST_CD IN ('18906-8','18908-4', '18919-1', '18928-2', '18939-9', '18961-3' '18964-7', '18974-6', '19000-9')

;
QUIT;
*/

PROC SQL;
CREATE TABLE filtered_r_result AS
	SELECT ltr.*
	FROM lab_test_r_result LTR
	WHERE root_ordered_test_pntr 
	IN (SELECT root_ordered_test_pntr 
		FROM lab_test_result)
	AND LAB_TEST_CD_DESC IN ('CIPROFLOXACIN',
'CLINDAMYCIN',
'ERYTHROMYCIN', 
'GENTAMICIN',
'LOMEFLOXACIN',
'OXACILLIN',
'PENICILLIN',
'RIFAMPIN',
'VANCOMYCIN')

;
QUIT;


PROC SQL;
CREATE TABLE distinct_drugs as 
SELECT distinct LAB_TEST_CD_DESC  from filtered_r_result; QUIT;

PROC SQL;
CREATE TABLE SUB_SET_DRUGS AS
SELECT put(LAB_TEST_DESC_TXT, $30.) as LAB_TEST_DESC_TXT, put('', $30.) as String  FROM DRUGS WHERE LAB_TEST_DESC_TXT NOT IN (SELECT PUT(LAB_TEST_CD_DESC, $30.) FROM DISTINCT_DRUGS); QUIT;

PROC TRANSPOSE DATA = SUB_SET_DRUGS OUT =TRANS_SUB_SET_DRUGS ;
ID LAB_TEST_DESC_TXT; 
VAR String; QUIT;

DATA TRANS_SUB_SET_DRUGS (DROP = _NAME_);
SET TRANS_SUB_SET_DRUGS; RUN;

/**retrieve ORDER rows that are based on lab_test_result root_order_test_pntr**/
PROC SQL;
CREATE TABLE filtered_order AS
	SELECT * 
	FROM lab_test_order 
	WHERE root_ordered_test_pntr 
		IN (SELECT root_ordered_test_pntr 
			FROM lab_test_result);
QUIT;

PROC TRANSPOSE DATA = filtered_r_result OUT=transposed_r_result;
BY LAB_TEST_KEY ROOT_ORDERED_TEST_PNTR PARENT_TEST_PNTR LAB_TEST_PNTR LAB_TEST_TYPE LAB_TEST_CD TEST_RESULT_VAL_CD /*INTERPRETATION_FLG*/ TEST_RESULT_VAL_CD_DESC; 
ID LAB_TEST_CD_DESC ;
VAR INTERPRETATION_FLG;
/*VAR  TEST_RESULT_VAL_CD;*/
QUIT;

DATA transposed_r_result;
MERGE transposed_r_result TRANS_SUB_SET_DRUGS;
RUN; 


DATA transposed_r_result (drop = LAB_TEST_TYPE LAB_TEST_CD  TEST_RESULT_VAL_CD TEST_RESULT_VAL_CD_DESC  _NAME_ _LABEL_ PARENT_TEST_PNTR LAB_TEST_PNTR);
SET transposed_r_result;
RUN;

PROC SORT DATA = lab_test_result;
BY root_ordered_test_pntr;
QUIT;

PROC SORT DATA = transposed_r_result;
BY root_ordered_test_pntr; 
QUIT;

DATA combined;
MERGE lab_test_result (RENAME = (TEST_RESULT_VAL_CD=SNOMED LAB_TEST_CD_DESC =RESULTED_TEST TEST_RESULT_VAL_CD_DESC = ORGANISM_NAME)) transposed_r_result ;
BY ROOT_ORDERED_TEST_PNTR; 
RUN;

DATA lab_test_combined (drop = Interpretation_Flg PARENT_TEST_PNTR LAB_TEST_PNTR LAB_TEST_TYPE LAB_TEST_CD);
SET combined;
RUN;

/**GET PATIENT DATA**/
PROC SQL;
CREATE TABLE order_patient AS
	SELECT 	
		p.person_key  'PATIENT_KEY' AS PATIENT_KEY, 
		p.person_first_nm  'PATIENT_FIRST_NM' as PATIENT_FIRST_NM, 
		p.person_last_nm  'PATIENT_LAST_NM' AS PATIENT_LAST_NM, 		
		l.ZIP_CD_5 'PATIENT_ZIP' AS PATIENT_ZIP,
		l.STATE_FIPS  AS STATE_CD, 
		l.STATE_SHORT_DESC AS STATE_DESC,
		l.CNTY_FIPS AS cnty_cd,
		f.lab_test_key,
		ci.phone_nbr 'PATIENT_PHONE_NUMBER' AS PATIENT_PHONE_NUMBER,	
		f.ROOT_ORDERED_TEST_PNTR
	FROM filtered_order f,
		nbs_rdb.lab_test_result ltr,
		nbs_rdb.person p,
		nbs_rdb.person_location pl,
		nbs_rdb.location l,
		nbs_rdb.person_contact pc,
		nbs_rdb.contact_info ci		
	WHERE f.lab_test_key = ltr.lab_test_key
		AND  ltr.patient_key = p.person_key
		AND p.person_key = pl.patient_key
		AND pl.location_key = l.location_key	
		AND p.person_key = pc.person_key
		AND pc.contact_key = ci.contact_key		
	;

QUIT;

/*Eliminate Duplicate rows*/
PROC SORT data = order_patient nodupkey;
by patient_key lab_test_key; 
QUIT;

/**GET PHYSICIAN DATA**/
PROC SQL;
CREATE TABLE order_physician AS
	SELECT p.person_key  'PHYSICIAN_KEY' AS PHYSICIAN_KEY ,
		p.person_first_nm  'PHYSICIAN_FIRST_NM' as PHYSICIAN_FIRST_NM, 
		p.person_last_nm  'PHYSICIAN_LAST_NM' AS PHYSICIAN_LAST_NM, 		
		l.ZIP_CD_5 'PHYSICIAN_ZIP' AS PHYSICIAN_ZIP,	
		ci.phone_nbr 'PHYSICIAN_PHONE_NUMBER' AS PHYSICIAN_PHONE_NUMBER,	
		f.lab_test_key,
		f.ROOT_ORDERED_TEST_PNTR  
	FROM filtered_order f,
		nbs_rdb.lab_test_result ltr,
		nbs_rdb.person p,
		nbs_rdb.person_location pl,
		nbs_rdb.location l,
		nbs_rdb.person_contact pc,
		nbs_rdb.contact_info ci
	WHERE f.lab_test_key = ltr.lab_test_key
		AND  ltr.ORDERING_PROVIDER_KEY = p.person_key
		AND p.person_key = pl.patient_key
		AND pl.location_key = l.location_key
		AND p.person_key = pc.person_key
		AND pc.contact_key = ci.contact_key
		AND p.person_key ~=1	
	;

QUIT;
/*Eliminate Duplicate rows*/
PROC SORT DATA = order_physician nodupkey;
BY lab_test_key PHYSICIAN_KEY;
QUIT;

/**GET REPORTING FACILITY DATA**/

PROC SQL;
CREATE TABLE order_reporting_fac AS
	SELECT 
		o.org_key as REPORTING_FACILITY_KEY,
		o.org_nm 'REPORTING_FACILITY' AS REPORTING_FACILITY,
		f.ROOT_ORDERED_TEST_PNTR,
		f.lab_test_key 
	FROM filtered_order f,
		nbs_rdb.lab_test_result ltr,
		nbs_rdb.organization o	
	WHERE f.lab_test_key = ltr.lab_test_key
		AND  ltr.REPORTING_LAB_KEY = o.org_key
		AND o.org_key ~=1
	;

QUIT;

/*Eliminate Duplicate rows*/
PROC SORT DATA = order_reporting_fac nodupkey;
BY REPORTING_FACILITY_KEY lab_test_key ; quit;

/**GET ORDERING FACILITY DATA**/

PROC SQL;
CREATE TABLE order_ordering_fac AS
	SELECT 
		o.org_key as ORDERING_FACILITY_KEY,
		o.org_nm 'ORDERING_FACILITY' AS ORDERING_FACILITY,
		f.lab_test_key,
		f.ROOT_ORDERED_TEST_PNTR  
	FROM filtered_order f,
		nbs_rdb.lab_test_result ltr,
		nbs_rdb.organization o	
	WHERE f.lab_test_key = ltr.lab_test_key
		AND  ltr.ORDERING_ORG_KEY = o.org_key
		AND o.org_key ~=1	
	;

QUIT;
/**Eliminate Duplicate rows**/
PROC SORT DATA = order_ordering_fac nodupkey;
BY ORDERING_FACILITY_KEY lab_test_key;
QUIT;

PROC SORT DATA = order_ordering_fac;
BY ROOT_ORDERED_TEST_PNTR;
QUIT;

PROC SORT DATA = order_reporting_fac;
BY ROOT_ORDERED_TEST_PNTR;
quit;

PROC SORT DATA = order_physician; 
BY ROOT_ORDERED_TEST_PNTR;
QUIT;

PROC SORT data = order_patient; 
by ROOT_ORDERED_TEST_PNTR;
QUIT;


/**START: Chaining together the observation variables**/
DATA COMBINED_LAB;
MERGE order_patient order_physician;
BY ROOT_ORDERED_TEST_PNTR; 
RUN;

DATA COMBINED_LAB;
MERGE COMBINED_LAB order_reporting_fac;
BY ROOT_ORDERED_TEST_PNTR; 
RUN;

DATA COMBINED_LAB;
MERGE COMBINED_LAB order_ordering_fac ;
BY ROOT_ORDERED_TEST_PNTR; 
RUN;

DATA COMBINED_LAB;
MERGE COMBINED_LAB lab_test_combined ;
BY ROOT_ORDERED_TEST_PNTR; 
RUN;
/**END: Chaining together the observation variables**/

DATA LAB_NE (DROP = ORDERING_FACILITY_KEY REPORTING_FACILITY_KEY PHYSICIAN_KEY);
SET COMBINED_LAB; RUN;


ods listing close;
ods html file="C:\Lab_Test.html";
proc PRINT DATA =LAB_NE ;
TITLE 'Lab Line List for Step pneumoniae, Enterococcus species, Staphylococcus Aureus';
quit;
ods html close;
ods listing;

DATA nbs_rdb.lab_organism_drug_fact;
SET lab_ne; RUN;

















