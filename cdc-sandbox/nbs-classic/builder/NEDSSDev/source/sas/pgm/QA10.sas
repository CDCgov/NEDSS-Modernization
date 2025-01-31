
options nocenter mprint mlogic symbolgen missing=' ';


%macro QA10;


%if  %upcase(&skip)=YES %then %goto finish;
 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

%formats;
proc sql buffersize=1M;
create table work as
select a.INVESTIGATION_KEY,
       a.PATIENT_NAME as Name, 
	   a.PATIENT_LOCAL_ID,
       a.PATIENT_AGE_REPORTED, 
	   a.PATIENT_SEX, 
	   a.DIAGNOSIS_CD as Dx, 
	   a.JURISDICTION_NM as Jurisdiction,
	   INVESTIGATOR_INTERVIEW_QC as Investigator,
	   datepart(a.CC_CLOSED_DT) as CLOSED_DT format = mmddyy10.,
	   case when a.CC_CLOSED_DT is null then 'Y' else 'N' end as Open_Status, 
	   a.PATIENT_PREGNANT_IND as curr_pregnant,
	   a.PBI_PREG_IN_LAST_12MO_IND as recent_birth,
	   a.PBI_PREG_AT_EXAM_IND as pregnant_at_exam,
	   a.PBI_PREG_AT_IX_IND as pregnant_at_interview,
	   a.PBI_PREG_IN_LAST_12MO_IND as pregnant_last12mo,
	   datepart(a.CA_INTERVIEWER_ASSIGN_DT) as ASSIGNED_DT format = mmddyy10.
from STD_HIV_DATAMART a inner join nbs_rdb.investigation e on a.investigation_key = e.investigation_key
where a.DIAGNOSIS_CD is not null
and INVESTIGATOR_INTERVIEW_KEY is not NULL
and (a.PATIENT_PREGNANT_IND='Yes' or a.PBI_PREG_AT_EXAM_IND='Yes' or a.PBI_PREG_AT_IX_IND='Yes' or a.PBI_PREG_IN_LAST_12MO_IND='Yes')
and a.inv_local_id is not null and e.INV_CASE_STATUS in ('Probable','Confirmed')
and a.PATIENT_SEX='Female'
order by INVESTIGATION_KEY
;
quit;

***NEED TO ADD where INV163 = confirmed or probable and PATIENT_SEX='Female';

data work;
set work;
array nvar(*) _character_;
	do i= 1 to dim(nvar);  
    if nvar(i)='NULL' then nvar(i)=' ';
end;

name_l=lowcase(name);

patient_id=SUBSTR(PATIENT_LOCAL_ID, 4, 8)-10000000;

if compress(trim(PATIENT_AGE_REPORTED))='.' then PATIENT_AGE_REPORTED=' ';

if PATIENT_AGE_REPORTED=' ' then age=.;
else age=scan(PATIENT_AGE_REPORTED,1,' ')*1;
format assigned_dt closed_dt mmddyy10. patient_sex $gender. 
       curr_pregnant recent_birth pregnant_at_exam pregnant_at_interview pregnant_last12mo $preg.;

run;

proc sort data=work;
by name_l dx;
run;

%chk_mv;

ods listing close;
options orientation=portrait CENTER NONUMBER NODATE LS=248 PS =256 COMPRESS=NO  MISSING = ' ' 
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

PROC REPORT  data=work nowindows ls=256 split='|'
style(header)={just=center font_weight=bold font_face="Calibri" font_size = 10pt};
COLUMNS name patient_id PATIENT_AGE_REPORTED dx jurisdiction investigator open_status curr_pregnant recent_birth; 
DEFINE name / display 'Name' style(column)=[cellwidth=35mm just=left vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0]
       style(header)={just=left};
DEFINE patient_id / display "Patient|ID" center style(column)=[cellwidth=17mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE PATIENT_AGE_REPORTED / display 'Age' center style(column)=[cellwidth=15mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE dx / display 'Dx' center style(column)=[cellwidth=13mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE jurisdiction / display 'Jurisdiction' center style(column)=[cellwidth=20mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE investigator / display 'Investigator' center style(column)=[cellwidth=20mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE open_status / display 'Open' center style(column)=[cellwidth=15mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE curr_pregnant / display 'Currently Pregnant' center style(column)=[cellwidth=20mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];  
DEFINE recent_birth / display 'Recent Birth' center style(column)=[cellwidth=20mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];  
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

%export(work,work,sock,&exporttype);
Title;

%finish:
%mend QA10;

%QA10;



