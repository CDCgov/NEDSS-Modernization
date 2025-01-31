
options nocenter mprint mlogic  symbolgen missing=' ';

/******************************************************************************************************************************

Program:  		QA07
Created:  		12/07/16
Created By:		MSG
Description:	QA07 Duplicate Cases
Updates:		

******************************************************************************************************************************/	

%macro QA07;

%chk_mv;
%if  %upcase(&skip)=YES %then %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

proc sql buffersize=1M;
create table QA07_INIT as
	select distinct PATIENT_NAME, PATIENT_LOCAL_ID, a.INV_LOCAL_ID, INV.REFERRAL_BASIS,
	PROVIDER_QUICK_CODE, DIAGNOSIS,
	datepart(a.CONFIRMATION_DT) as CONFIRMATION_DT format = mmddyy10., 
	datepart(a.FL_FUP_EXAM_DT) as FL_FUP_EXAM_DT format = mmddyy10.,
	lab.SPECIMEN_COLLECTION_DT as SPECIMEN_COLLECTION_DT,
	e.DIAGNOSIS_DT as DIAGNOSIS_DT
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
	order by PATIENT_NAME, a.DIAGNOSIS
;
quit;

DATA QA07_DT_CALC;
	SET QA07_INIT;
	LENGTH PATIENTID $10 ;
	PATIENTID = substr(PATIENT_LOCAL_ID, 4, 8) - 10000000;
	IF (FL_FUP_EXAM_DT =. AND REFERRAL_BASIS = 'T1 - Positive Test') THEN 
		FL_FUP_EXAM_DT = SPECIMEN_COLLECTION_DT;
	ELSE IF (FL_FUP_EXAM_DT =. AND REFERRAL_BASIS = 'T2 - Morbidity Report') THEN  
		FL_FUP_EXAM_DT = DIAGNOSIS_DT;
	DROP SPECIMEN_COLLECTION_DT DIAGNOSIS_DT  REFERRAL_BASIS PROVIDER_QUICK_CODE;
run;

PROC SORT DATA=QA07_DT_CALC;
  BY PATIENT_NAME DIAGNOSIS DESCENDING FL_FUP_EXAM_DT;
RUN ;

DATA QA07_DUP;
	SET QA07_DT_CALC;
	WHERE FL_FUP_EXAM_DT ~=.;
	RETAIN OLD_PATIENTID OLD_FL_FUP_EXAM_DT OLD_DIAGNOSIS;
	IF OLD_PATIENTID = PATIENTID  AND OLD_DIAGNOSIS = DIAGNOSIS THEN DO;
		DAYS=FL_FUP_EXAM_DT-OLD_FL_FUP_EXAM_DT;
	END;
	OLD_FL_FUP_EXAM_DT=FL_FUP_EXAM_DT;
	OLD_PATIENTID=PATIENTID;
	OLD_DIAGNOSIS=DIAGNOSIS;
	IF DAYS=. THEN DO;
		DAYS=0;
	END;
	DROP OLD_FL_FUP_EXAM_DT OLD_PATIENTID OLD_DIAGNOSIS;
RUN;

PROC SORT DATA=QA07_DT_CALC;
  BY PATIENT_NAME DIAGNOSIS  FL_FUP_EXAM_DT ;
RUN ;

DATA QA07_DUP1;
	SET QA07_DT_CALC;
	WHERE FL_FUP_EXAM_DT ~=.;
	RETAIN OLD_PATIENTID OLD_FL_FUP_EXAM_DT OLD_DIAGNOSIS;
	IF OLD_PATIENTID = PATIENTID  AND OLD_DIAGNOSIS = DIAGNOSIS THEN DO;
		DAYS1=FL_FUP_EXAM_DT-OLD_FL_FUP_EXAM_DT;
	END;
	OLD_FL_FUP_EXAM_DT=FL_FUP_EXAM_DT;
	OLD_PATIENTID=PATIENTID;
	OLD_DIAGNOSIS=DIAGNOSIS;
	IF DAYS1=. THEN DO;
		DAYS1=0;
	END;
	DROP OLD_FL_FUP_EXAM_DT OLD_PATIENTID OLD_DIAGNOSIS;
RUN;

PROC SORT DATA=QA07_DUP;
  BY INV_LOCAL_ID;
RUN ;

PROC SORT DATA=QA07_DUP1;
  BY INV_LOCAL_ID;
RUN ;

DATA QA07_MERGE;
	MERGE QA07_DUP QA07_DUP1;
	by INV_LOCAL_ID;
run;

PROC SORT DATA=QA07_MERGE;
  BY PATIENT_NAME DIAGNOSIS DESCENDING FL_FUP_EXAM_DT;
RUN ;

proc sql;
create table QA07_COUNT as
	select PATIENT_NAME, count(PATIENTID) as COUNT, DIAGNOSIS 
	from QA07_MERGE
	WHERE DAYS >=-30 and DAYS1<=30
	group by PATIENT_NAME,DIAGNOSIS  
	order by PATIENT_NAME,DIAGNOSIS, FL_FUP_EXAM_DT DESCENDING;
quit;

DATA QA07_MERGE1;
	MERGE QA07_MERGE QA07_COUNT;
	by PATIENT_NAME DIAGNOSIS;
run;

proc sql;
create table QA07 as
	select * from QA07_MERGE1 where count >1 AND (DAYS >=-30 and DAYS1 <=30)
	order by PATIENT_NAME, DIAGNOSIS, FL_FUP_EXAM_DT;
quit;

%chk_mv;

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

PROC REPORT  data=QA07 nowindows ls=256 
style(header)={just=center font_weight=bold font_face="Arial" font_size = 8pt};
COLUMNS PATIENT_NAME PATIENTID DIAGNOSIS CONFIRMATION_DT FL_FUP_EXAM_DT;
DEFINE PATIENTID / display 'Patient ID' center style(column)=[cellwidth=20mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE PATIENT_NAME / group 'Name' style(column)=[cellwidth=30mm just=left vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0]
       style(header)={just=left};
DEFINE DIAGNOSIS / display 'Dx' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE FL_FUP_EXAM_DT / display 'Dx Date' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE CONFIRMATION_DT / display 'Confirmation Date' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
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

%export(work,QA07,sock,&exporttype);
Title;

%finish:
%mend QA07;

%QA07;



