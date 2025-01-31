%macro NBSSR00023;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;
PROC SORT DATA = CC_CLOSED_DT_DATAMART;
by CC_CLOSED_DT;




data CC_CLOSED_DT_DATAMART;
set CC_CLOSED_DT_DATAMART;
LENGTH OPEN $78;
LENGTH DX $78;
LENGTH Investigator $78;
LENGTH NAME $78;
NAME = PATIENT_NAME;

if(DIAGNOSIS ='') then DX=DIAGNOSIS;
else DX=substr(trim(DIAGNOSIS),1,3); 
if(PATIENT_RACE='') then PATIENT_RACE=' ';
if(PATIENT_SEX='') then PATIENT_SEX='';
   
LENGTH Investigator $78;
len=lengthn(INVESTIGATOR_QUICK_CODE);
if(len>1) then Investigator=trim(INVESTIGATOR_QUICK_CODE)||"-"||trim(INVESTIGATOR_NAME);
else Investigator=INVESTIGATOR_NAME;

OPEN='N';
IF CC_CLOSED_DT =. THEN OPEN='Y';

drop CA_INIT_INTVWR_ASSGN_DT DIAGNOSIS INVESTIGATOR_NAME INVESTIGATOR_QUICK_CODE DIAGNOSIS CC_CLOSED_DT;
run;
 


%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "QA01 Interview Record Listing (Closed Date)" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;

Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 



%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");

PROC REPORT DATA = CC_CLOSED_DT_DATAMART NOWINDOWS; 
column NAME PATIENT_AGE PATIENT_RACE Dx FIELD_RECORD_NUMBER  PATIENT_SEX Investigator OPEN ADD_PROVIDER_QUICK_CODE ;
define NAME/ "Name" display;
define PATIENT_AGE/  "Age" display;
define PATIENT_RACE/ "Race" display;
define Dx/  "Dx" display;
define FIELD_RECORD_NUMBER / "Field Record #" display;
define PATIENT_SEX/  "Sex" display;
define Investigator/ order "Investigator";
define OPEN/ order "Open?";
define ADD_PROVIDER_QUICK_CODE/ "Entry" display;
RUN;
quit;
ods html close;
%end;
%else 
	%export(work,CC_CLOSED_DT_DATAMART,sock,&exporttype);
Title;
%finish:
%mend NBSSR00023;
%NBSSR00023;
