%macro NBSSR00021;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;
PROC SORT DATA = Interview_assign_dt_datamart;
by CA_INIT_INTVWR_ASSGN_DT;


data Interview_assign_dt_datamart;
set Interview_assign_dt_datamart;
LENGTH DX $78;
LENGTH Investigator $78;
LENGTH OPEN $78;
LENGTH GENDER $78;
LENGTH NAME $78;
GENDER =  PATIENT_SEX;
NAME = PATIENT_NAME;
OPEN ='N';
if(CC_CLOSED_DT=.) then OPEN = 'Y';

if(DIAGNOSIS ='') then DX=DIAGNOSIS;
else DX=substr(trim(DIAGNOSIS),1,3);
if(PATIENT_RACE='') then PATIENT_RACE=' ';
if(GENDER='') then GENDER=' ';

LENGTH Investigator $78;
len=lengthn(INVESTIGATOR_QUICK_CODE);
if(len>1) then Investigator=trim(INVESTIGATOR_QUICK_CODE)||"-"||trim(INVESTIGATOR_NAME);
else Investigator=INVESTIGATOR_NAME;

drop PATIENT_SEX CA_INIT_INTVWR_ASSGN_DT DIAGNOSIS INVESTIGATOR_NAME INVESTIGATOR_QUICK_CODE DIAGNOSIS;
run;



%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "QA01 Interview Record Listing (Assigned Date)" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;

Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 



%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");

/*PROC REPORT DATA = Interview_assign_dt_datamart NOWINDOWS; 
define NAME/ "Name";
define AGE/  "Age";
define RACE/ "Race";
define Dx/  "Dx";
define FIELD_RECORD_NUMBER / "Field Record #";
define SEX/  "Sex";
define ADD_PROVIDER_QUICK_CODE/ "Entry";
RUN;
*/

PROC REPORT DATA = Interview_assign_dt_datamart NOWINDOWS; 
column NAME PATIENT_AGE  GENDER PATIENT_RACE  Dx FIELD_RECORD_NUMBER Investigator OPEN ADD_PROVIDER_QUICK_CODE; 
define NAME/  "Name" display;
define PATIENT_AGE/   "Age" display;
define GENDER/   "Sex" display;
define PATIENT_RACE/  "Race" display;
define Dx/   "Dx" display ;
define FIELD_RECORD_NUMBER /  "Field Record #" display;
define ADD_PROVIDER_QUICK_CODE/ "Entry" display;
define OPEN / "OPEN?" display;
 
RUN;
quit;
ods html close;
%end;
%else 
	%export(work,Interview_assign_dt_datamart,sock,&exporttype);
Title;
%finish:
%mend NBSSR00021;
%NBSSR00021;
