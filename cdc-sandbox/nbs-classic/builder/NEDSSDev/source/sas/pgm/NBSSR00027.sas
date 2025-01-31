%macro NBSSR00027;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

proc sql;
create table QA7 as
select PATIENT_LOCAL_ID, PATIENT_NAME, DIAGNOSIS_CD, 
	DIAGNOSIS_DATE,  confirmation_DATE
from 
	nbs_rdb.STD_HIV_DATAMART a
join 
	(  
	select 
		PATIENT_MPR_UID
    from 
		nbs_rdb.STD_HIV_DATAMART 
	WHERE 
		DIAGNOSIS_DATE IS NOT NULL
		or confirmation_DATE IS NOT NULL
	group by 
		PATIENT_MPR_UID 
         having count(*) > 1 ) b
    on 
		a.PATIENT_MPR_UID = b.PATIENT_MPR_UID
where INV_CASE_STATUS in ('Confirmed','Probable');
quit;
%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Patients with Duplicate Cases" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");

PROC REPORT DATA = QA7 NOWINDOWS ls=150; 
column PATIENT_LOCAL_ID PATIENT_NAME DIAGNOSIS_CD DIAGNOSIS_DATE confirmation_DATE; 

define PATIENT_LOCAL_ID/  "Patient ID" display;
define DIAGNOSIS_CD/   "Diagnosis" display;
define PATIENT_NAME/ "Name" display;
define DIAGNOSIS_DATE/  "Diagnosis Date" display;
define confirmation_DATE/   "Confirmation Date" display ;
RUN;
ods html close;
%end;
%else 
	%export(work,QA7,sock,&exporttype);
Title;
%finish:
%mend NBSSR00027;
%NBSSR00027;
