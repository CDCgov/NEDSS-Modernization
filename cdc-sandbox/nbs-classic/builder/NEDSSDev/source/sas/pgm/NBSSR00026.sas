%macro NBSSR00026;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;


DATA STD_CASE_LISTING_DATAMART;
set  STD_CASE_LISTING_DATAMART;
by investigation_key;
run;

proc sql;
create table 
	MISSING_TREATMENT 
as select 
	* 
from 
	STD_CASE_LISTING_DATAMART
where investigation_key not in ( select investigation_key from nbs_rdb.D_STD_TREATMENT_INVESTIGATION);
quit;
data MISSING_TREATMENT;
set MISSING_TREATMENT;
LENGTH treatment_data_error $2000;
treatment_data_error='No Treatment Record';
run;
proc sql;
create table 
	MISSING_LAB 
as select 
	* 
from 
	STD_CASE_LISTING_DATAMART
where investigation_key not in ( select investigation_key from nbs_rdb.D_STD_LAB_INVESTIGATION);
quit;
data MISSING_LAB;
set MISSING_LAB;
LENGTH lab_data_error $2000;
lab_data_error='No Test Record';
run;

data MISSING_LAB_treatment;
merge MISSING_TREATMENT MISSING_LAB;
by investigation_key;
run;

DATA MISSING_LAB_treatment; 
LENGTH data_error $78;
LENGTH DX $78;
if(DIAGNOSIS ='') then DX=DIAGNOSIS;
else DX=substr(trim(DIAGNOSIS),1,3);  
LENGTH NAME $78;
NAME = PATIENT_NAME;

SET MISSING_LAB_treatment; BY investigation_key; 
IF lab_data_error NE ' ' THEN data_error=lab_data_error;
IF treatment_data_error NE ' ' THEN data_error=treatment_data_error;
IF (treatment_data_error NE '' and lab_data_error NE '') then data_error='No Treatment & Test Record';
drop treatment_data_error lab_data_error REFERRAL_BASIS PATIENT_LOCAL_ID jURISDICTION_NM INVESTIGATION_KEY DIAGNOSIS;
RUN; 





%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Cases Missing Lab and/or Treatment" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");

PROC REPORT DATA = MISSING_LAB_treatment NOWINDOWS; 
/* col NAME AGE RACE, Dx, FL_FUP_FIELD_RECORD_NUM,SEX;   */
column NAME dx INV_LOCAL_ID  data_error CONFIRMATION_DATE; 
define NAME/  "Name" display;
define DX/   "Dx" display ;
define INV_LOCAL_ID/   "Case ID" display;
define data_error/ "Error Explained" display;
define CONFIRMATION_DATE/ "Confirmation Date" display;
RUN;

ods html close;
%end;
%else 
	%export(work,MISSING_LAB_treatment,sock,&exporttype);
Title;
%finish:
%mend NBSSR00026;
%NBSSR00026;
