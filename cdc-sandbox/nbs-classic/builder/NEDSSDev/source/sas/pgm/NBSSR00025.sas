%macro NBSSR00025;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

DATA STD_CASE_LISTING_DATAMART;
set  STD_CASE_LISTING_DATAMART;
by INVESTIGATION_KEY;


DATA D_STD_LAB_INVESTIGATION;
set  nbs_rdb.D_STD_LAB_INVESTIGATION;



DATA STD_CASE_LISTING_DATAMART;
set  nbs_rdb.STD_CASE_LISTING_DATAMART;
by INVESTIGATION_KEY;


DATA D_STD_LAB_INVESTIGATION;
set  nbs_rdb.D_STD_LAB_INVESTIGATION;


proc sql;
create table QA3_init
as
	select STD_CASE_LISTING_DATAMART.*,
	trim(d_provider.provider_first_Name) || ' '||  trim(d_provider.provider_last_Name) || ' '||  trim(d_provider.provider_middle_Name) || ' '||  trim(d_provider.provider_Name_PREFIX) as LAB_Physician 'LAB_Physician',
	trim(d_organization.organization_Name) as LAB_Facilty 'LAB_Facilty'

from 
	STD_CASE_LISTING_DATAMART left outer join 
	nbs_rdb.D_STD_LAB_INVESTIGATION
on
	STD_CASE_LISTING_DATAMART.INVESTIGATION_KEY = D_STD_LAB_INVESTIGATION.INVESTIGATION_KEY
and 
	STD_CASE_LISTING_DATAMART.REFERRAL_BASIS = 'T1 - Positive Test'
left outer join 
	nbs_rdb.d_provider 
on 
	D_STD_LAB_INVESTIGATION.ORDERING_PROVIDER_KEY= d_provider.provider_key
left outer join 
	nbs_rdb.d_organization 
on 
	D_STD_LAB_INVESTIGATION.ORDERING_ORG_KEY= d_organization.organization_key;
quit;

proc sql;
create table QA3_final
as
	select QA3_init.*,
	trim(d_provider.provider_first_Name) || ' '||  trim(d_provider.provider_last_Name) || ' '||  trim(d_provider.provider_middle_Name) || ' '||  trim(d_provider.provider_Name_PREFIX) as MORB_Physician 'MORB_Physician',
	trim(d_organization.organization_Name) as MORB_Facilty 'MORB_Facilty'

from 
	QA3_init 
left outer join 
	nbs_rdb.D_STD_LAB_INVESTIGATION
on
	QA3_init.INVESTIGATION_KEY = D_STD_LAB_INVESTIGATION.INVESTIGATION_KEY
and 
	QA3_init.REFERRAL_BASIS = 'T2 - Positive Test'
left outer join 
	nbs_rdb.d_provider 
on 
	D_STD_LAB_INVESTIGATION.ORDERING_PROVIDER_KEY= d_provider.provider_key
left outer join 
	nbs_rdb.d_organization 
on 
	D_STD_LAB_INVESTIGATION.ORDERING_ORG_KEY= d_organization.organization_key;
quit;
data QA3_final;
set QA3_final;
/*
Name, PATIENT_LOCAL_ID, DIAGNOSIS, CONFIRMATION_DATE,Physician, facility, jURISDICTION_NM
*/
LENGTH Physician $78;
LENGTH facility $78;
Physician=LAB_Physician;
facility=LAB_Facilty;
if missing(Physician) then Physician=MORB_Physician;
if missing(facility) then Physician=MORB_Facilty;
LENGTH DX $78;
LENGTH OPEN $78;
LENGTH Jurisdiction $78;
Jurisdiction =Jurisdiction_NM;
LENGTH NAME $78;
NAME = PATIENT_NAME;
if(DIAGNOSIS ='') then DX=DIAGNOSIS;
else DX=substr(trim(DIAGNOSIS),1,3);   
DROP LAB_Physician LAB_Facilty MORB_Physician MORB_Facilty DIAGNOSIS CA_INIT_INTVWR_ASSGN_DT INVESTIGATION_KEY REFERRAL_BASIS Investigator;
RUN;

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



PROC REPORT DATA = QA3_final NOWINDOWS; 
column NAME PATIENT_LOCAL_ID Dx Jurisdiction  CONFIRMATION_DATE Physician facility; 
define NAME/  "Name" display;
define PATIENT_LOCAL_ID/   "Patient ID" display;
define Dx/   "Dx" display ;
define Jurisdiction/  "Jurisdiction" display;
define CONFIRMATION_DATE/ "Confirmation Date" display;
define Physician/ "Physician" display;
define facility/ "Facility" display;
RUN;
quit;

ods html close;
%end;
%else 
	%export(work,QA3_final,sock,&exporttype);
Title;
%finish:
%mend NBSSR00025;
%NBSSR00025;
