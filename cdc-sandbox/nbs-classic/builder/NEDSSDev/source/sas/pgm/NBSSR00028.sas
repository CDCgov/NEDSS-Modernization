%macro NBSSR00028;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
create table pa5_init as select
INTERVIEWER_QUICK_CODE,
INTERVIEWER_NAME,
INVESTIGATOR_QUICK_CODE,
INVESTIGATOR_LAST_NAME,
CA_INTERVIEWER_ASSIGN_DT,
CC_CLOSED_DT,
CA_PATIENT_INTV_STATUS,
investigation_key,
INTERVIEWER_KEY,
STD_PRTNRS_PRD_FML_TTL,
STD_PRTNRS_PRD_MALE_TTL,
STD_PRTNRS_PRD_TRNSGNDR_TTL 
from STD_HIV_DATAMART;
quit;

data pa5_init;
set pa5_init;
counter=1;
	LENGTH WORKER $78;
	len=lengthn(INTERVIEWER_QUICK_CODE);
	if(len>1) then WORKER=trim(INTERVIEWER_QUICK_CODE)||"-"||trim(INTERVIEWER_NAME);
	else WORKER=INTERVIEWER_NAME;
;  
run;
proc sql;
create table Pa5 
as select 
	subject_investigation_key,third_party_investigation_key,D_CONTACT_RECORD.CONTACT_REFERRAL_BASIS,
	pa5_init.*
from 
	pa5_init
left outer join 
	nbs_rdb.F_CONTACT_RECORD_CASE 
on pa5_init.investigation_key =F_CONTACT_RECORD_CASE.subject_investigation_key 
left outer join 
	nbs_rdb.D_CONTACT_RECORD 
on D_CONTACT_RECORD.D_CONTACT_RECORD_KEY=F_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY;

create table interviewed_cases as 
select  distinct d_interview.d_interview_key,
IX_LOCATION,IX_TYPE, interview_date, pa5.*, f_interview_case.provider_key AS INT_INTERVIEWER_KEY 'INT_INTERVIEWER_KEY'
from nbs_rdb.d_interview
inner join 
nbs_rdb.f_interview_case 
on 
f_interview_case.d_interview_key=d_interview.d_interview_key 

right outer join 
PA5 on 
f_interview_case.investigation_key = pa5.investigation_key;
quit;
DATA interviewed_cases(drop=STD_PRTNRS_PRD_FML_TTL STD_PRTNRS_PRD_MALE_TTL STD_PRTNRS_PRD_TRNSGNDR_TTL);
*DATA interviewed_cases;
SET interviewed_cases;
   /*RightNow = date();*/
	startDate=datepart(CA_INTERVIEWER_ASSIGN_DT);
   	finalDate=datepart(interview_date);
    Interview_days=(startDate-finalDate); 
	period_partners=0;
	if(STD_PRTNRS_PRD_FML_TTL) then period_partners=STD_PRTNRS_PRD_FML_TTL*1; else period_partners=0;
	if(STD_PRTNRS_PRD_MALE_TTL) then period_partners=STD_PRTNRS_PRD_MALE_TTL*1 + period_partners;
	if(STD_PRTNRS_PRD_TRNSGNDR_TTL) then period_partners=STD_PRTNRS_PRD_TRNSGNDR_TTL*1 + period_partners; ;
	IF(third_party_investigation_key=1) THEN third_party_investigation_key=.;

RUN;
PROC SORT DATA=interviewed_cases; BY WORKER CA_INTERVIEWER_ASSIGN_DT; RUN;
proc sql;
CREATE TABLE UNIQUE_CASES AS
 	SELECT distinct COUNT(distinct INVESTIGATION_KEY) as assigned_cases_count,worker FROM interviewed_cases GROUP BY worker; 

create table counter5_PART1 As
select distinct worker,
    sum( case when CA_INTERVIEWER_ASSIGN_DT is not null then 1 else 0 end) As number_cases_count  'NUM OF. CASES OPEN',
    sum( case when CA_INTERVIEWER_ASSIGN_DT is not null AND CC_CLOSED_DT is NULL then 1 else 0 end) As assigned_Count 'NUM OF. CASES OPEN',
    sum( case when CC_CLOSED_DT is not null then 1 else 0 end) AS close_Count 'NUM OF. CASES CLOSED',
    sum( case when CA_PATIENT_INTV_STATUS='A - Awaiting' then 1 else 0 end) AS pending_Count 'NUM OF. CASES PENDING',

	sum( case when CA_PATIENT_INTV_STATUS='I - Interviewed' then 1 else 0 end) AS interviewed_Count 'NUM OF. CASES IX''D',
	sum( case when CA_PATIENT_INTV_STATUS in ('O - Other', 'R - Refused Interview', 'U - Unable to Locate') then 1 else 0 end) AS NOT_INTERVIEWED 'NUM OF. CASES NOT IX''D',
	sum( case when CA_PATIENT_INTV_STATUS ='O - Other' then 1 else 0 end) AS OTHER_INTERVIEWED 'OTHERS',
	sum( case when CA_PATIENT_INTV_STATUS ='R - Refused Interview' then 1 else 0 end) AS REFUSED_INTERVIEWED 'REUSED',
	sum( case when CA_PATIENT_INTV_STATUS ='U - Unable to Locate' then 1 else 0 end) AS NO_LOCATE_INTERVIEWED 'NO LOCATE'
from pa5_init
group by worker;

create table counter5_PART2 As
select distinct worker,
	period_partners,
	sum( case when third_party_investigation_key=. then 1 else 0 end) AS NUMBER_OF_CASES_NCI 'OI’S THAT WERE NCI:',
    sum( case when IX_LOCATION='Clinic' and IX_TYPE='Initial/Original'  then 1 else 0 end) AS clinic_interviewed_Count 'CLINIC IX''D',
	sum( case when IX_LOCATION='Field' and IX_TYPE='Initial/Original'  then 1 else 0 end) AS field_interviewed_Count 'FIELD IX''D',

	sum( case when Interview_days<3  and IX_TYPE='Initial/Original'  then 1 else 0 end) AS INTERVIEW_LESS_THAN_3_DAYS  'IX''D W/IN 3 DAYS',
	sum( case when Interview_days<5  and IX_TYPE='Initial/Original'  then 1 else 0 end) AS INTERVIEW_LESS_THAN_5_DAYS  'IX''D W/IN 5 DAYS',
	sum( case when Interview_days<7  and IX_TYPE='Initial/Original'  then 1 else 0 end) AS INTERVIEW_LESS_THAN_7_DAYS  'IX''D W/IN 7 DAYS',
	sum( case when Interview_days<14 and IX_TYPE='Initial/Original'  then 1 else 0 end) AS INTERVIEW_LESS_THAN_14_DAYS 'IX''D W/IN 14 DAYS',

	sum( case when IX_TYPE='Initial/Original'   then 1 else 0 end) AS NUMBER_OF_OIS 'NUM. OF OI''S:',

	sum( case when INT_INTERVIEWER_KEY=. then 1 else 0 end) AS NUMBER_OF_OIS_NCI 'OI’S THAT WERE NCI:',
	sum( case when (third_party_investigation_key) then 1 else 0 end) AS PERIOD_PARTNERS 'PERIOD_PARTNERS:',
	sum( case when INT_INTERVIEWER_KEY and (third_party_investigation_key) then 1 else 0 end) AS NUMBER_OF_OIS_PI 'PARTNERS INITIATED:',

	sum( case when IX_TYPE='Re-Interview'   then 1 else 0 end) AS NUMBER_OF_RIS 'NUM. OF RI''S:',
	sum( case when IX_TYPE='Re-Interview' AND INT_INTERVIEWER_KEY=. then 1 else 0 end) AS NUMBER_OF_RIS_NCI 'RI''S THAT WERE NCI:',
	sum( case when IX_TYPE='Re-Interview' AND (subject_investigation_key) then 1 else 0 end) AS NUMBER_OF_RIS_PI 'PARTNERS INITIATED:',

	sum( case when IX_TYPE in ('Initial/Original','Re-Interview') AND CONTACT_REFERRAL_BASIS in ('S1 - Social Contact 1','S2 - Social Contact 2','S2 - Social Contact 3') then 1 else 0 end) AS NUM_CLUSTERED_INIT 'CLUSTERS INIT. (OI & RI):'
from interviewed_cases
group by worker;
quit;
data counter5;
merge counter5_PART1 counter5_PART2;
by worker;
run;

PROC TRANSPOSE DATA=counter5 OUT=counter5_out;
	BY worker number_cases_count assigned_Count close_Count pending_Count interviewed_Count clinic_interviewed_Count field_interviewed_Count 
		INTERVIEW_LESS_THAN_3_DAYS INTERVIEW_LESS_THAN_5_DAYS INTERVIEW_LESS_THAN_7_DAYS INTERVIEW_LESS_THAN_14_DAYS
		NOT_INTERVIEWED OTHER_INTERVIEWED REFUSED_INTERVIEWED NO_LOCATE_INTERVIEWED 
		NUMBER_OF_OIS NUMBER_OF_OIS_NCI NUMBER_OF_OIS_PI PERIOD_PARTNERS NUMBER_OF_RIS NUMBER_OF_RIS_NCI NUMBER_OF_RIS_PI NUM_CLUSTERED_INIT;
	VAR worker number_cases_count assigned_Count close_Count pending_Count interviewed_Count clinic_interviewed_Count field_interviewed_Count
		INTERVIEW_LESS_THAN_3_DAYS INTERVIEW_LESS_THAN_5_DAYS INTERVIEW_LESS_THAN_7_DAYS INTERVIEW_LESS_THAN_14_DAYS
		NOT_INTERVIEWED OTHER_INTERVIEWED REFUSED_INTERVIEWED NO_LOCATE_INTERVIEWED 
		NUMBER_OF_OIS NUMBER_OF_OIS_NCI NUMBER_OF_OIS_PI PERIOD_PARTNERS NUMBER_OF_RIS NUMBER_OF_RIS_NCI NUMBER_OF_RIS_PI NUM_CLUSTERED_INIT;
RUN;
proc sql;
delete from counter5_out where _NAME_ = 'WORKER';
quit;
data counter5_out;
SET counter5_out;
if(_NAME_='pending_Count') then DO; count = pending_Count; IF(number_cases_count>0) THEN PERCENT=pending_Count/number_cases_count;END;
else if(_NAME_='close_Count') then DO; count = close_Count;IF(number_cases_count>0) THEN PERCENT=close_Count/number_cases_count;END;
else if(_NAME_='assigned_Count') then DO; count = assigned_Count;IF(number_cases_count>0) THEN PERCENT=assigned_Count/number_cases_count;END;
else if(_NAME_='interviewed_Count') then DO;count = interviewed_Count;IF(number_cases_count>0) THEN PERCENT=interviewed_Count/number_cases_count ;END;
else if(_NAME_='clinic_interviewed_Count') then DO;count = clinic_interviewed_Count;IF(interviewed_Count>0) THEN PERCENT=clinic_interviewed_Count/interviewed_Count ;END;
else if(_NAME_='field_interviewed_Count') then DO;count = clinic_interviewed_Count;IF(interviewed_Count>0) THEN PERCENT=field_interviewed_Count/interviewed_Count ;END;
else if(_NAME_='number_cases_count') then count = number_cases_count;
else if(_NAME_='INTERVIEW_LESS_THAN_3_DAYS') then DO;count = INTERVIEW_LESS_THAN_3_DAYS;IF(interviewed_Count>0) THEN PERCENT=INTERVIEW_LESS_THAN_3_DAYS/interviewed_Count ;END;
else if(_NAME_='INTERVIEW_LESS_THAN_5_DAYS') then DO;count = INTERVIEW_LESS_THAN_5_DAYS;IF(interviewed_Count>0) THEN PERCENT=INTERVIEW_LESS_THAN_5_DAYS/interviewed_Count ;END;
else if(_NAME_='INTERVIEW_LESS_THAN_7_DAYS') then DO;count = INTERVIEW_LESS_THAN_7_DAYS;IF(interviewed_Count>0) THEN PERCENT=INTERVIEW_LESS_THAN_7_DAYS/interviewed_Count ;END;
else if(_NAME_='INTERVIEW_LESS_THAN_14_DAYS') then DO;count = INTERVIEW_LESS_THAN_14_DAYS;IF(interviewed_Count>0) THEN PERCENT=INTERVIEW_LESS_THAN_14_DAYS/interviewed_Count ;END;

else if(_NAME_='NOT_INTERVIEWED') then DO;count = NOT_INTERVIEWED;IF(assigned_Count>0) THEN PERCENT=NOT_INTERVIEWED/assigned_Count ;END;
else if(_NAME_='OTHER_INTERVIEWED') then DO;count = OTHER_INTERVIEWED;IF(NOT_INTERVIEWED>0) THEN PERCENT=OTHER_INTERVIEWED/NOT_INTERVIEWED ;END;
else if(_NAME_='REFUSED_INTERVIEWED') then DO;count = REFUSED_INTERVIEWED;IF(NOT_INTERVIEWED>0) THEN PERCENT=REFUSED_INTERVIEWED/NOT_INTERVIEWED ;END;
else if(_NAME_='NO_LOCATE_INTERVIEWED') then DO;count = NO_LOCATE_INTERVIEWED;IF(NOT_INTERVIEWED>0) THEN PERCENT=NO_LOCATE_INTERVIEWED/NOT_INTERVIEWED ;END;
else if(_NAME_='NUMBER_OF_OI') then DO;count = NUMBER_OF_OI; PERCENT=-1; END; /*IF(Count>0) THEN PERCENT=NUMBER_OF_OI/interviewed_Count ;END;*/
else if(_NAME_='NUMBER_OF_OIS_NCI') then DO;count = NUMBER_OF_OIS_NCI;IF(NUMBER_OF_OI>0) THEN INDEX=NUMBER_OF_OIS_NCI/NUMBER_OF_OI ;END;
else if(_NAME_='PERIOD_PARTNERS') then DO;count = PERIOD_PARTNERS;IF(NUMBER_OF_OI>0) THEN INDEX=PERIOD_PARTNERS/NUMBER_OF_OI ;END;
else if(_NAME_='NUMBER_OF_OIS_PI') then DO;count = NUMBER_OF_OIS_PI;IF(NUMBER_OF_OI>0) THEN INDEX=NUMBER_OF_OIS_PI/NUMBER_OF_OI ;END;

else if(_NAME_='NUMBER_OF_RIS') then DO;count = NUMBER_OF_RIS; INDEX=-1;END;
/*else if(_NAME_='NUMBER_OF_RIS_NCI') then DO;count = NUMBER_OF_RIS_NCI;IF(NUMBER_OF_OI>0) THEN PERCENT=-1;END;*/
else if(_NAME_='NUMBER_OF_RIS_NCI') then DO;count = NUMBER_OF_RIS_NCI;IF(NUMBER_OF_RIS>0) THEN INDEX=NUMBER_OF_RIS_NCI/NUMBER_OF_RIS;END;
else if(_NAME_='NUMBER_OF_RIS_PI') then DO;count = NUMBER_OF_RIS_PI;IF(NUMBER_OF_RIS>0) THEN INDEX=NUMBER_OF_RIS_PI/NUMBER_OF_RIS ;END;
else if(_NAME_='NUM_CLUSTERED_INIT') then DO;count = NUM_CLUSTERED_INIT;IF(NUMBER_OF_RIS>0) THEN INDEX=NUM_CLUSTERED_INIT/NUMBER_OF_OI;END;
else count = 0;
IF(PERCENT=.) THEN PERCENT=0;
run;



%footnote;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Worker Interview Activity Report" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "REPORT DATE: &rptdate"</p>;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");
proc report data=counter5_out nowd split='*';
	column WORKER _LABEL_ count percent index;
   	define WORKER   / group;
   	define count/ 'Count';
   	define percent/ DISPLAY 'Percent' format=percent10.2;
	define index/ DISPLAY 'Index' format=8.1;
	define _LABEL_ /display 'Type of Count';
	compute BEFORE WORKER;
	endcomp;
  	compute after WORKER;
      line ' ';
   	endcomp;
	compute percent;
      IF(WORKER ne '') THEN  percent='';
	  IF(PERCENT<0) THEN PERCENT='';
   	endcomp;


   run;
 ods html close;
 %end;
%else 
	%export(work,QA7,sock,&exporttype);
Title;
%finish:
%mend NBSSR00028;
%NBSSR00028;
