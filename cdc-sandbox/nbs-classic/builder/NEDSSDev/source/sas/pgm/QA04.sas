
options nocenter mprint mlogic symbolgen missing=' ';

/******************************************************************************************************************************

Program:  		QA04
Created:  		11/24/16
Created By:		MSG
Description:	QA04 Case Listing Report
Updates:		

******************************************************************************************************************************/	

%macro QA04;

%chk_mv;
%if  %upcase(&skip)=YES %then %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;
proc sql buffersize=1M;
create table QA04_INIT as
	select distinct a.inv_local_id,PATIENT_NAME, PATIENT_LOCAL_ID, a.diagnosis_cd, datepart(a.CONFIRMATION_DT) as CONFIRMATION_DT format = mmddyy10., TREATMENT_KEY, LAB_TEST_KEY
	from std_hiv_datamart a
	inner join nbs_rdb.investigation e on a.investigation_key = e.investigation_key
	left outer join nbs_rdb.LAB_TEST_RESULT b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
	left outer join nbs_rdb.TREATMENT_EVENT c on a.INVESTIGATION_KEY = c.INVESTIGATION_KEY
	where a.inv_local_id is not null and e.INV_CASE_STATUS in ('Probable','Confirmed')
	order by PATIENT_NAME
;
quit;

DATA QA04;
	SET QA04_INIT;
	LENGTH ERROR_TXT $250 PATIENTID $10;
	IF(MISSING(LAB_TEST_KEY) AND MISSING(TREATMENT_KEY)) THEN
		ERROR_TXT = 'No Treatment or Lab' ;
	ELSE IF(MISSING(LAB_TEST_KEY)) THEN
		ERROR_TXT = 'No Lab' ;
	ELSE IF(MISSING(TREATMENT_KEY)) THEN
		ERROR_TXT = 'No Treatment' ;
	ELSE ERROR_TXT = 'N/A';
	PATIENTID = substr(PATIENT_LOCAL_ID, 4, 8) - 10000000;
	DROP LAB_TEST_KEY TREATMENT_KEY;

	IF ERROR_TXT NE 'N/A';
run;


proc sort data=QA04 nodupkey;
by PATIENTID PATIENT_NAME diagnosis_cd inv_local_id ERROR_TXT CONFIRMATION_DT;
run;


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

PROC REPORT  data=QA04 nowindows ls=256 
style(header)={just=center font_weight=bold font_face="Arial" font_size = 8pt};
COLUMNS PATIENTID PATIENT_NAME diagnosis_cd inv_local_id ERROR_TXT CONFIRMATION_DT;
DEFINE PATIENTID / display 'Patient ID' center style(column)=[cellwidth=18mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0]; 
DEFINE PATIENT_NAME / display 'Name' style(column)=[cellwidth=30mm just=left vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0]
       style(header)={just=left};
DEFINE diagnosis_cd / display 'Dx' center style(column)=[cellwidth=8mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE inv_local_id / display 'Case ID' center style(column)=[cellwidth=30mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE ERROR_TXT / display 'Error Explanation' center style(column)=[cellwidth=40mm just=center vjust=center font_face="Calibri" font_size=8pt cellpadding=0.7 cellspacing=0];
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

%export(work,QA04,sock,&exporttype);
Title;

%finish:
%mend QA04;

%QA04;



