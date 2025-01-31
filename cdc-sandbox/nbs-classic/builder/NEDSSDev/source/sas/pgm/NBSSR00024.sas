%macro NBSSR00024;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;
PROC SORT DATA = INTRV_PREG_REC_BIRTH_DATAMART;
by CA_INIT_INTVWR_ASSGN_DT;


DATA INTRV_PREG_REC_BIRTH_DATAMART;
set  INTRV_PREG_REC_BIRTH_DATAMART;
by CA_INIT_INTVWR_ASSGN_DT;

data INTRV_PREG_REC_BIRTH_DATAMART;
set INTRV_PREG_REC_BIRTH_DATAMART;
LENGTH DX $78;
LENGTH Investigator $78;
LENGTH Jurisdiction $78;
LENGTH PBI_PREG_IN_LAST_12MO  $78;
LENGTH OPEN $78;
LENGTH NAME $78;
NAME = PATIENT_NAME;

PBI_PREG_IN_LAST_12MO =PBI_PREG_IN_LAST_12MO_IND ;
OPEN ='N';

Jurisdiction = Jurisdiction_nm;
if(CC_CLOSED_DT=.) then OPEN = 'Y';

if(DIAGNOSIS ='') then DX=DIAGNOSIS;
else DX=substr(trim(DIAGNOSIS),1,3);
   
LENGTH Investigator $78;
len=lengthn(INVESTIGATOR_QUICK_CODE);
if(len>1) then Investigator=trim(INVESTIGATOR_QUICK_CODE)||"-"||trim(INVESTIGATOR_NAME);
else Investigator=INVESTIGATOR_NAME;
		
drop  CA_INIT_INTVWR_ASSGN_DT DIAGNOSIS INVESTIGATOR_NAME INVESTIGATOR_QUICK_CODE DIAGNOSIS Jurisdiction_nm;
run;

%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "INTERVIEW RECORD LISTING - PREGNANT/RECENT BIRTH" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 



%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");


PROC REPORT DATA = INTRV_PREG_REC_BIRTH_DATAMART NOWINDOWS; 
/* col NAME AGE RACE, Dx, FL_FUP_FIELD_RECORD_NUM,SEX;   */
column NAME PATIENT_LOCAL_ID PATIENT_AGE Dx Jurisdiction ADD_PROVIDER_QUICK_CODE OPEN PATIENT_PREGNANT_IND PBI_PREG_IN_LAST_12MO; 
define NAME/  "Name" display;
define PATIENT_LOCAL_ID/   "Patient ID" display;
define PATIENT_AGE/   "Age" display;
define Dx/   "Dx" display ;
define Jurisdiction/  "Jurisdiction" display;
define ADD_PROVIDER_QUICK_CODE/ "Investigator" display;
define OPEN / "OPEN/Closed Case" display;
define PATIENT_PREGNANT_IND/  "Currently Pregnant" display;
define PBI_PREG_IN_LAST_12MO/ "Recent Birth" display;
RUN;
quit;
ods html close;
%end;
%else 
	%export(work,INTRV_PREG_REC_BIRTH_DATAMART,sock,&exporttype);
Title;
%finish:
%mend NBSSR00024;
%NBSSR00024;
