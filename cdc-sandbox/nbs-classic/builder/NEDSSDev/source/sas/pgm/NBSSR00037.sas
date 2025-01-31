%macro NBSSR00037;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
	create table qa6_init_diagnosis as select distinct
    SHD.INVESTIGATION_KEY,
	SHD.PATIENT_LOCAL_ID,
	PATIENT_NAME,
	DIAGNOSIS_CD,
	STD_PID_IND,
	up.PROVIDER_QUICK_CODE, 
    up.FIRST_NM, up.LAST_NM,
	INVESTIGATOR_NAME,
	FL_FUP_EXAM_DT,
	ISD.DIAGNOSIS_DATE
	from STD_HIV_DATAMART SHD inner join nbs_rdb.USER_PROFILE up on   
	SHD.add_user_id = up.NEDSS_ENTRY_ID
    left outer join nbs_rdb.INV_SUMM_DATAMART ISD on 
	SHD.INVESTIGATION_KEY = ISD.INVESTIGATION_KEY where SHD.INV_CASE_STATUS in ('Confirmed', 'Probable');
quit;

proc sort data=qa6_init_diagnosis;
   by INVESTIGATION_KEY;
run;

Proc sql;
	create table qa6_init_specimen as select distinct
    SHD.INVESTIGATION_KEY,
	LAB100.SPECIMEN_COLLECTION_DT
	from STD_HIV_DATAMART SHD left outer join nbs_rdb.D_STD_LAB_INV SLI on 
	SHD.INVESTIGATION_KEY = SLI.INVESTIGATION_KEY
	left outer join nbs_rdb.LAB100 LAB100 on SLI.lab_local_id = LAB100.LAB_RPT_LOCAL_ID;
Quit;

proc sort data=qa6_init_specimen;
   by INVESTIGATION_KEY;
run;

data qa6_init;
   merge qa6_init_diagnosis qa6_init_specimen;
   by INVESTIGATION_KEY;
run;

data qa6_init;
SET qa6_init;
IF FL_FUP_EXAM_DT ~= .  THEN FL_FUP_EXAM_DT = FL_FUP_EXAM_DT;
ELSE IF SPECIMEN_COLLECTION_DT ~= . THEN FL_FUP_EXAM_DT = SPECIMEN_COLLECTION_DT;
ELSE IF DIAGNOSIS_DATE ~=. THEN FL_FUP_EXAM_DT =DIAGNOSIS_DATE;
drop SPECIMEN_COLLECTION_DT;
drop DIAGNOSIS_DATE;
PATIENT_LOCAL_ID=SUBSTR(PATIENT_LOCAL_ID,5);
PATIENT_LOCAL_ID=SUBSTR(PATIENT_LOCAL_ID,1,7);
LENGTH Entry $78;
len=lengthn(PROVIDER_QUICK_CODE);
if(len>1) then Entry=trim(PROVIDER_QUICK_CODE)||"-"||trim(FIRST_NM) ||" "||trim(LAST_NM);
else Entry=trim(FIRST_NM) ||" "||trim(LAST_NM);
drop PROVIDER_QUICK_CODE;
drop FIRST_NM;
drop LAST_NM;
RUN;
proc sort data=qa6_init;
   by PATIENT_NAME FL_FUP_EXAM_DT;
run;
%footnote;
title 'QA06 Patients with Multiple Cases'; 
%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);
ods html;
	proc report data=qa6_init nowd; 
	     column PATIENT_LOCAL_ID PATIENT_NAME FL_FUP_EXAM_DT
	            DIAGNOSIS_CD STD_PID_IND Entry; 
		 define PATIENT_LOCAL_ID / group
                   'Patient Id';
		 define PATIENT_NAME / group
                   'Name';
	     define FL_FUP_EXAM_DT / 'Dx Date';
	     define DIAGNOSIS_CD / 'Diagnosis';
	     define STD_PID_IND / 'PID';
	run;
   quit;
 ods html close;
%end;
%else 
      %export(work,qa6_init,sock,&exporttype);
Title;
Footnote;
%finish:
%mend NBSSR00037;
%NBSSR00037;
