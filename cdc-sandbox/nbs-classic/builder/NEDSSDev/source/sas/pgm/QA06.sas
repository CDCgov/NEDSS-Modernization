
options nocenter mprint mlogic symbolgen missing=' ';

/******************************************************************************************************************************

Program:  		QA06
Created:  		11/24/16
Created By:		MSG
Description:	QA06 Case Listing Report
Updates:		

******************************************************************************************************************************/	

%macro QA06;


 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

proc sql buffersize=1M;
create table QA06_INIT as
	select distinct PATIENT_NAME, PATIENT_LOCAL_ID, a.INV_LOCAL_ID, INV.REFERRAL_BASIS,
	PROVIDER_QUICK_CODE, a.DIAGNOSIS, a.CMP_PID_IND,
	datepart(a.CONFIRMATION_DT) as CONFIRMATION_DT format = mmddyy10., 
	datepart(a.FL_FUP_EXAM_DT) as FL_FUP_EXAM_DT format = mmddyy10.,
	datepart(lab.SPECIMEN_COLLECTION_DT) as SPECIMEN_COLLECTION_DT format = mmddyy10.,
	datepart(e.DIAGNOSIS_DT) as DIAGNOSIS_DT format = mmddyy10.
	from std_hiv_datamart a
	INNER JOIN (SELECT INVESTIGATION.INVESTIGATION_KEY, INVESTIGATION.INV_CASE_STATUS, REFERRAL_BASIS, EVENT_METRIC.ADD_USER_ID, USER_PROFILE.PROVIDER_QUICK_CODE
                                       from nbs_rdb.INVESTIGATION join nbs_rdb.EVENT_METRIC on INVESTIGATION.CASE_UID = EVENT_METRIC.EVENT_UID
									                      join nbs_rdb.USER_PROFILE on EVENT_METRIC.ADD_USER_ID=USER_PROFILE.NEDSS_ENTRY_ID) INV
					  ON a.INVESTIGATION_KEY = INV.INVESTIGATION_KEY
	left outer join ( select b.INVESTIGATION_KEY, max(d.SPECIMEN_COLLECTION_DT) as SPECIMEN_COLLECTION_DT from  nbs_rdb.LAB_TEST_RESULT b
						inner join nbs_rdb.lab_test d on b.lab_test_key = d.lab_test_key group by b.INVESTIGATION_KEY) lab on
	a.INVESTIGATION_KEY = lab.INVESTIGATION_KEY 
	left outer join nbs_rdb.MORBIDITY_REPORT_EVENT c on a.INVESTIGATION_KEY = c.INVESTIGATION_KEY
	left outer join nbs_rdb.MORBIDITY_REPORT e on c.MORB_RPT_KEY = e.MORB_RPT_KEY
	where a.inv_local_id is not null and a.DIAGNOSIS is not null and INV.INV_CASE_STATUS in ('Probable','Confirmed')
	order by PATIENT_NAME
;
quit;

proc sql;
create table QA06_COUNT as
	select PATIENT_NAME, count(PATIENT_LOCAL_ID) as COUNT
	from QA06_INIT a
	group by PATIENT_NAME 
	order by PATIENT_NAME
;
quit;


DATA QA06_MERGE;
	MERGE QA06_INIT QA06_COUNT;
	by PATIENT_NAME ;
run;

proc sql;
create table QA06 as
	select * from QA06_MERGE where count >1
	order by PATIENT_NAME
;
quit;

DATA QA06;
	SET QA06;
	LENGTH PATIENTID $10 ;
	PATIENTID = substr(PATIENT_LOCAL_ID, 4, 8) - 10000000;
	IF (FL_FUP_EXAM_DT =. AND REFERRAL_BASIS = 'T1 - Positive Test') THEN 
		FL_FUP_EXAM_DT = SPECIMEN_COLLECTION_DT;
	ELSE IF (FL_FUP_EXAM_DT =. AND REFERRAL_BASIS = 'T2 - Morbidity Report') THEN  
		FL_FUP_EXAM_DT = DIAGNOSIS_DT;
run;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
ods listing close;
options orientation=portrait CENTER NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.10in rightmargin=.10in  nobyline  papersize=a4;


%if %upcase(&exporttype)=REPORT %then %do;


ods escapechar='^';
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "___________________________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on:&rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";
options printerpath=pdf;
ods PDF body = sock style=styles.listing notoc uniform;
title j=center f=Arial bold h=12pt "&reportTitle";

PROC REPORT  data=QA06 nowindows ls=256 
style(header)={just=center font_weight=bold font_face="Arial" font_size = 8pt};
COLUMNS PATIENT_NAME PATIENTID DIAGNOSIS CONFIRMATION_DT FL_FUP_EXAM_DT /*CMP_PID_IND*/ PROVIDER_QUICK_CODE;
DEFINE PATIENTID / display 'Patient ID' center style(column)=[cellwidth=20mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE PATIENT_NAME / group 'Name' style(column)=[cellwidth=30mm just=left vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0]
       style(header)={just=left};
DEFINE DIAGNOSIS / display 'Dx' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE CONFIRMATION_DT / display 'Confirmation Date' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE FL_FUP_EXAM_DT / display 'Dx Date' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
/*DEFINE CMP_PID_IND / display 'PID' center style(column)=[cellwidth=10mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];*/
DEFINE PROVIDER_QUICK_CODE / display 'Entry' center style(column)=[cellwidth=12mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
RUN; 
ods pdf close;
ods listing;

data _null_;
proc datasets kill lib = work nolist memtype=data;
quit;
call symputx ('START_TIME',PUT(DATETIME(),15.));
START_TIME = &START_TIME;
END_TIME = DATETIME();
ELAPSED = END_TIME - START_TIME;

PUT ' NOTE: Start Time (HH:MM) = ' START_TIME TIMEAMPM8.;
PUT ' NOTE: End Time (HH:MM) = ' END_TIME TIMEAMPM8.;
PUT ' NOTE: Elapsed Time (HH:MM:SS) = ' ELAPSED TIME.; PUT ' ';
run;

%end;

%else 

%export(work,QA06,sock,&exporttype);
Title;

%finish:
%mend QA06;

%QA06;



