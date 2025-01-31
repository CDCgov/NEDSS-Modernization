
options nocenter mprint mlogic symbolgen missing=' ';

/******************************************************************************************************************************

Program:  		QA01
Created:  		10/31/16
Created By:		NVD
Description:	NEDSS STD Program Activity Report - QA01 Interview Record Listing Report
Updates:		

******************************************************************************************************************************/	

%macro QA01;


%if  %upcase(&skip)=YES %then %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

%formats;
proc sql buffersize=1M;
create table qa as
select distinct STD_HIV_DATAMART.INVESTIGATION_KEY,
       ADD_USER_ID,
	   PROVIDER_QUICK_CODE,
       PATIENT_NAME as Name, 
       PATIENT_AGE_REPORTED, 
	   PATIENT_SEX, 
	   PATIENT_RACE as Race1,
	   STD_HIV_DATAMART.DIAGNOSIS_CD as Dx, 
	   STD_HIV_DATAMART.FIELD_RECORD_NUMBER as Field_Record_Num, 
	   INVESTIGATOR_INTERVIEW_QC as Investigator,
	   case when CC_CLOSED_DT is null then 'Y' else 'N' end as Open_Status, 
	   datepart(CA_INTERVIEWER_ASSIGN_DT) as ASSIGNED_DT format = mmddyy10.,
	   datepart(STD_HIV_DATAMART.CC_CLOSED_DT) as CLOSED_DT format = mmddyy10.
	   /*CONVERT(DATE, CA_INTERVIEWER_ASSIGN_DT) as ASSIGNED_DT,
	   CONVERT(DATE, CC_CLOSED_DT) as CLOSED_DT*/
from STD_HIV_DATAMART LEFT OUTER JOIN (SELECT INVESTIGATION.INVESTIGATION_KEY, EVENT_METRIC.ADD_USER_ID, USER_PROFILE.PROVIDER_QUICK_CODE
                                       from nbs_rdb.INVESTIGATION join nbs_rdb.EVENT_METRIC on INVESTIGATION.CASE_UID = EVENT_METRIC.EVENT_UID
									                      join nbs_rdb.USER_PROFILE on EVENT_METRIC.ADD_USER_ID=USER_PROFILE.NEDSS_ENTRY_ID) INV
					  ON STD_HIV_DATAMART.INVESTIGATION_KEY = INV.INVESTIGATION_KEY
where CA_PATIENT_INTV_STATUS='I - Interviewed'
and DIAGNOSIS_CD is not null
and IX_DATE_OI is not null
order by PATIENT_NAME
;
quit;

*and IX_DATE_OI is not null;



data qa;
set qa;
length Field_Record_Num $20. name_l $150. race1 race $50. PROVIDER_QUICK_CODE $10. sex $8.;

array nvar(*) _character_;
	do i= 1 to dim(nvar);  
    if nvar(i)='NULL' then nvar(i)=' ';
end;

name_l=lowcase(name);

race=race1;


if compress(trim(PATIENT_AGE_REPORTED))='.' then PATIENT_AGE_REPORTED=' ';

if PATIENT_AGE_REPORTED=' ' then age=.;
else age=scan(PATIENT_AGE_REPORTED,1,' ')*1;
format PATIENT_SEX $gender. race $race.;
run;


/*proc freq data=qa;
tables race*race1 patient_sex*sex/list missing;
run;*/

proc sort data=qa;
by name_l dx;
run;

%chk_mv;

ods listing close;
options orientation=portrait CENTER NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.02in rightmargin=.02in  nobyline  papersize=a4;


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
title j=center f=Calibri bold h=12pt "&reportTitle";

PROC REPORT  data=qa nowindows ls=256 
style(header)={just=center font_weight=bold font_face="Calibri" font_size = 8pt};
COLUMNS name PATIENT_AGE_REPORTED patient_sex race dx field_record_num investigator open_status provider_quick_code; 
DEFINE name / display 'Name' style(column)=[cellwidth=56mm just=left vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0]
       style(header)={just=left};
DEFINE PATIENT_AGE_REPORTED / display 'Age' center style(column)=[cellwidth=15mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE patient_sex / display 'Sex' center style(column)=[cellwidth=10mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE race / display 'Race' center style(column)=[cellwidth=12mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE dx / display 'Dx' center style(column)=[cellwidth=8mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE field_record_num / display 'Field Record #' center style(column)=[cellwidth=22mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE investigator / display 'Investigator' center style(column)=[cellwidth=30mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];
DEFINE open_status / display 'Open' center style(column)=[cellwidth=10mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];  
DEFINE provider_quick_code / display 'Entry' center style(column)=[cellwidth=12mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=0.7 cellspacing=0];  
RUN; 
ods pdf close;
ods listing;

data _null_;

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

%export(work,qa,sock,&exporttype);
Title;

%finish:
%mend QA01;

%QA01;



