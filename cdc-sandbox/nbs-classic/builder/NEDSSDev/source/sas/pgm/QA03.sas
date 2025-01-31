
options nocenter mprint mlogic symbolgen missing=' ';

/******************************************************************************************************************************

Program:  		QA03
Created:  		11/24/16
Created By:		MSG
Description:	NEDSS STD Program Activity Report - QA03 Case Listing Report
Updates:		1/12/17 (NVD) - Removed . from PATIENT_AGE_REPORTED

******************************************************************************************************************************/	

%macro QA03;

%chk_mv;
%if  %upcase(&skip)=YES %then %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;
proc sql buffersize=1M;
create table QA03_INIT as
	select distinct a.inv_local_id,
	PATIENT_NAME,PATIENT_LOCAL_ID,
	a.diagnosis_cd, PATIENT_AGE_REPORTED,
	datepart(a.CONFIRMATION_DT) as CONFIRMATION_DT format = mmddyy10.,
	a.jurisdiction_nm, 
	c.organization_name, 
	b.PROVIDER_FIRST_NAME, b.PROVIDER_LAST_NAME, b.PROVIDER_NAME_SUFFIX
	from std_hiv_datamart a
	inner join nbs_rdb.investigation e on a.investigation_key = e.investigation_key
	left outer join nbs_rdb.d_provider b on a.physician_key = b.provider_key
	left outer join nbs_rdb.d_organization c on a.ordering_facility_key = c.organization_key
	where a.inv_local_id is not null and e.INV_CASE_STATUS in ('Probable','Confirmed')
	order by PATIENT_NAME
;
quit;

DATA QA03;
	SET QA03_INIT;
	LENGTH PROVIDER $250 PATIENTID $10;
	IF(TRIM(PROVIDER_NAME_SUFFIX)='') THEN
		PROVIDER = TRIM(PROVIDER_FIRST_NAME) ||' '|| TRIM(PROVIDER_LAST_NAME);
	ELSE
		PROVIDER = TRIM(PROVIDER_FIRST_NAME) ||' '|| TRIM(PROVIDER_LAST_NAME)||', '|| TRIM(PROVIDER_NAME_SUFFIX);
	DROP PROVIDER_FIRST_NAME PROVIDER_LAST_NAME PROVIDER_NAME_SUFFIX;
PATIENTID = substr(PATIENT_LOCAL_ID, 4, 8) - 10000000;;
if compress(trim(PATIENT_AGE_REPORTED))='.' then PATIENT_AGE_REPORTED=' ';


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

PROC REPORT  data=QA03 nowindows ls=256 
style(header)={just=center font_weight=bold font_face="calibri" font_size = 9pt};
COLUMNS PATIENTID PATIENT_NAME PATIENT_AGE_REPORTED diagnosis_cd  CONFIRMATION_DT organization_name PROVIDER jurisdiction_nm; 
DEFINE PATIENTID / display 'Patient ID' center style(column)=[cellwidth=18mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE PATIENT_NAME / display 'Name' style(column)=[cellwidth=30mm just=left vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0]
       style(header)={just=left};
DEFINE PATIENT_AGE_REPORTED / display 'Age' center style(column)=[cellwidth=15mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE diagnosis_cd / display 'Dx' center style(column)=[cellwidth=10mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE CONFIRMATION_DT / display 'Confirmation Date' center style(column)=[cellwidth=30mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE organization_name / display 'Facility' center style(column)=[cellwidth=30mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE PROVIDER / display 'Physician' center style(column)=[cellwidth=30mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
DEFINE jurisdiction_nm / display 'Jurisdiction' center style(column)=[cellwidth=25mm just=center vjust=center font_face='calibri' font_size=9pt cellpadding=0.7 cellspacing=0];
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

%export(work,QA03,sock,&exporttype);
Title;

%finish:
%mend QA03;

%QA03;



