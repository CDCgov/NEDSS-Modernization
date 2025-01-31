%macro NBSSR00035;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
create table PA1 as select
INTERVIEWER_QUICK_CODE,
INTERVIEWER_NAME,
INVESTIGATOR_NAME,
HIV_900_TEST_IND,
INV_CASE_STATUS,
HIV_900_RESULT,
ADM_ReferralBasisOOJ,
INIT_FUP_INITIAL_FOLL_UP_CD,
HIV_POST_TEST_900_COUNSELLING,
CA_INTERVIEWER_ASSIGN_DT,
CA_PATIENT_INTV_STATUS,
CC_CLOSED_DT,
SOURCE_SPREAD,
FL_FUP_DISPOSITION_DESC,
HIV_900_TEST_IND,
REFERRAL_BASIS,
confirmation_DATE,
investigation_key,INTERVIEWER_KEY,
STD_PRTNRS_PRD_FML_TTL,
STD_PRTNRS_PRD_MALE_TTL,
STD_PRTNRS_PRD_TRNSGNDR_TTL ,ADI_900_STATUS_CD,
	1 AS count
from STD_HIV_DATAMART;


create table test as select 
IX_LOCATION,IX_TYPE
from nbs_rdb.d_interview;

create table interviewed_case_INIT as 
select  distinct d_interview.d_interview_key,
IX_LOCATION,IX_TYPE, interview_date, pa1.*, f_interview_case.provider_key AS  INT_INTERVIEWER_KEY 
from nbs_rdb.d_interview
inner join 
nbs_rdb.f_interview_case 
on 
f_interview_case.d_interview_key=d_interview.d_interview_key 

right outer join 
PA1 on 
f_interview_case.investigation_key = pa1.investigation_key;




create table interviewed_cases as 
select interviewed_case_INIT.*, D_CONTACT_RECORD.PROCESSING_DECSN_DESCRIPTION ,  contact_referral_basis  from interviewed_case_INIT left outer join
nbs_rdb.F_CONTACT_RECORD_CASE on F_CONTACT_RECORD_CASE.SUBJECT_INVESTIGATION_KEY =interviewed_case_INIT.investigation_key
left outer join nbs_rdb.d_contact_record 
ON  
d_contact_record.d_contact_record_key=F_CONTACT_RECORD_CASE.d_contact_record_key;
quit;


DATA interviewed_cases;
*DATA interviewed_cases;
SET interviewed_cases;
LENGTH BASE_CASE_STATUS $200;
LENGTH Interview_days $200;
LENGTH Exam_days $200;
LENGTH CLUSTER_DAYS $200;

LENGTH INTERVIEWER_NAME $78;
	len=lengthn(INTERVIEWER_QUICK_CODE);
	if(len>1) then INTERVIEWER_NAME=trim(INTERVIEWER_QUICK_CODE)||"-"||trim(INTERVIEWER_NAME);
	else INTERVIEWER_NAME=INTERVIEWER_NAME;

   /*RightNow = date();*/
	intstartDate=datepart(CA_INTERVIEWER_ASSIGN_DT);
   	intfinalDate=datepart(interview_date);
	if((intstartDate-intfinalDate)<4) and  IX_TYPE='Initial/Original' and CA_PATIENT_INTV_STATUS='I - Interviewed' then Interview_days ='A. IX''d w/in 3 DAYS';
	else if((intstartDate-intfinalDate)<6) and  IX_TYPE='Initial/Original' and CA_PATIENT_INTV_STATUS='I - Interviewed' then Interview_days ='B, IX''d w/in 5 DAYS';
	else if((intstartDate-intfinalDate)<8) and  IX_TYPE='Initial/Original' and CA_PATIENT_INTV_STATUS='I - Interviewed' then intInterview_days ='C. IX''d w/in 7 DAYS';
	else if((intstartDate-intfinalDate)<15)and  IX_TYPE='Initial/Original' and CA_PATIENT_INTV_STATUS='I - Interviewed' then Interview_days ='D. IX''d w/in 14 DAYS';
	else if IX_TYPE='Re-Interview' then Interview_days ='Cases Re-interviewed:';
	else Interview_days='Not Interviewed';
	period_partners=0;

	if(STD_PRTNRS_PRD_FML_TTL) then period_partners=STD_PRTNRS_PRD_FML_TTL*1; else period_partners=0;
	if(STD_PRTNRS_PRD_MALE_TTL) then period_partners=STD_PRTNRS_PRD_MALE_TTL*1 + period_partners;
	if(STD_PRTNRS_PRD_TRNSGNDR_TTL) then period_partners=STD_PRTNRS_PRD_TRNSGNDR_TTL*1 + period_partners; ;
	IF(third_party_investigation_key=1) THEN third_party_investigation_key=.;

	if(CC_CLOSED_DT) then  BASE_CASE_STATUS='CASES CLOSED' ;
	else if (CA_INTERVIEWER_ASSIGN_DT) then BASE_CASE_STATUS='CASES IX''D';
	if(BASE_CASE_STATUS='CASES CLOSED' ) then  close_count =1;
	else if(BASE_CASE_STATUS='CASES CLOSED')  then interviewed_count=1;	
	if(CC_CLOSED_DT) then  Interview_days=0 ;


	examStartDate=datepart(FL_FUP_INIT_ASSGN_DT);
   	examFinalDate=datepart(FL_FUP_DISPO_DT);
	if((examStartDate-examFinalDate)<4) and  contact_referral_basis in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both') then EXAM_DAYS ='Within 3 Days:';
	else if((examStartDate-examFinalDate)<6) and  contact_referral_basis in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both') then EXAM_DAYS ='Within 5 Days:';
	else if((examStartDate-examFinalDate)<8) and  contact_referral_basis in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both') then EXAM_DAYS ='Within 7 Days:';
	else if((examStartDate-examFinalDate)<15) and  contact_referral_basis in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both') then EXAM_DAYS ='Within 14 Days:';


	clusterStartDate=datepart(FL_FUP_INIT_ASSGN_DT);
   	clusterFinalDate=datepart(FL_FUP_DISPO_DT);
	if((clusterStartDate-clusterFinalDate)<4) and  contact_referral_basis in  ('A%') OR contact_referral_basis in   ('S%') OR contact_referral_basis in   ('C%')  then CLUSTER_DAYS ='Within 3 Days:';
	else if((clusterStartDate-clusterFinalDate)<6) and  contact_referral_basis in ('A%') OR contact_referral_basis in   ('S%') OR contact_referral_basis in   ('C%')then CLUSTER_DAYS ='Within 5 Days:';
	else if((clusterStartDate-clusterFinalDate)<8) and  contact_referral_basis in ('A%') OR contact_referral_basis in   ('S%') OR contact_referral_basis in   ('C%') then CLUSTER_DAYS ='Within 7 Days:';
	else if((clusterStartDate-clusterFinalDate)<15) and  contact_referral_basis in ('A%') OR contact_referral_basis in   ('S%') OR contact_referral_basis in   ('C%') then CLUSTER_DAYS ='Within 14 Days:';
	
RUN;


DATA interviewed_cases;
SET interviewed_cases;
drop startDate finalDate;
counter=1;
run;

proc sql;
create table interviewed_cases_out as select * from interviewed_cases where BASE_CASE_STATUS is not null;
quit;


proc sort data=interviewed_cases_out;
by INTERVIEWER_NAME; run;

title1 'Case Management Report';
/*proc report data=interviewed_cases_out nowd split ='*' missing out =test;
column BASE_CASE_STATUS Interview_days   counter counter= counter1   percent1 percent2 ADI_900_STATUS_CD;
by INTERVIEWER_NAME;
define BASE_CASE_STATUS /group;
define Interview_days/group;
define percent1/COMPUTED '%age of total' format=percent10.2 ;
define percent2/COMPUTED '%age of total Interviewed' format=percent10.2 ;
define counter1 / noprint analysis sum   format=5.;
define counter /  analysis    format=5.;

compute before;
 totage = counter1; 
 if ADI_900_STATUS_CD in ('3 - Prior Positive - Not Previously Known','4 - Prior Positive - New STD or Pregnacy','5 - Prior Positive - Contact to STD/HIV Case') 
	then totADL=counter1;
else totADL=0;	
endcomp;
compute before BASE_CASE_STATUS;
if(Interview_days) in ('A. IX''d w/in 3 DAYS', 'B, IX''d w/in 5 DAYS', 'D. IX''d w/in 14 DAYS') then total=counter1;
endcomp;

compute percent1;
if _break_='_RBREAK_' then percent1=.; 
else  percent1 =(counter1)/totage;
endcomp;
compute percent2;
if(Interview_days) in ('A. IX''d w/in 3 DAYS', 'B, IX''d w/in 5 DAYS', 'D. IX''d w/in 14 DAYS') then percent2 =(counter1)/totage;
else  percent2=.; 

if ADI_900_STATUS_CD in ('3 - Prior Positive - Not Previously Known','4 - Prior Positive - New STD or Pregnacy','5 - Prior Positive - Contact to STD/HIV Case') 
	then percent2=counter1/totADL;
	

endcomp;

compute BASE_CASE_STATUS/char length=8;
if _break_='_RBREAK_' then BASE_CASE_STATUS = 'NUM. CASES ASSIGNED'; 
endcomp;

*break after BASE_CASE_STATUS /skip summarize UL dul;
rbreak after/summarize dol skip;
run;
*/

proc freq data=interviewed_cases_out ;
   tables interview_days / out=interview_days outexpect sparse;
   weight count;
by INTERVIEWER_NAME;
   title 'interview_days';
run;

proc freq data=interviewed_cases_out ;
   tables ADI_900_STATUS_CD/ out=ADL;
   where ADI_900_STATUS_CD is not null;
   weight count;
by INTERVIEWER_NAME;
   title 'Eye and Hair Color of European Children';
run;
proc freq data=interviewed_cases_out ;
   tables HIV_900_TEST_IND/ out=HIV_900_TEST_IND outexpect sparse;
   WHERE HIV_900_TEST_IND ='YES';
   weight count;
by INTERVIEWER_NAME;
   title 'HIV_900_TEST_IND';
run;
proc freq data=interviewed_cases_out ;
   tables HIV_900_RESULT/ out=HIV_900_RESULT outexpect sparse;
   where HIV_900_RESULT='13-Positive/Reactive';
	weight count;
by INTERVIEWER_NAME;
   title 'HIV_900_RESULT';
run;
proc freq data=interviewed_cases_out(where =(HIV_POST_TEST_900_COUNSELLING='YES')) ;
   tables HIV_POST_TEST_900_COUNSELLING/ out=HIV_POST_TEST_900_COUNSELLING ;
weight count;
by INTERVIEWER_NAME;
   title 'HIV_POST_TEST_900_COUNSELLING';
run;
proc freq data=interviewed_cases_out ;
   tables FL_FUP_DISPOSITION_DESC / out=DISEASE_INT_INDEX;
   where CA_PATIENT_INTV_STATUS='I - Interviewed' AND FL_FUP_DISPOSITION_DESC in ('A - Preventative Treatment', 'C - Infected, Brought to Treatment');
   weight count;
by INTERVIEWER_NAME;
   title 'HIV_POST_TEST_900_COUNSELLING';
run;

proc freq data=interviewed_cases_out ;
   tables SOURCE_SPREAD / out=SOURCE_SPREAD;
   where SOURCE_SPREAD='2 - Source';
   weight count;
by INTERVIEWER_NAME;
   title 'HIV_POST_TEST_900_COUNSELLING';
run;

proc freq data=interviewed_cases_out ;
   tables STD_PRTNRS_PRD_FML_TTL STD_PRTNRS_PRD_MALE_TTL STD_PRTNRS_PRD_TRNSGNDR_TTL/ out=partners;
   weight count;
by INTERVIEWER_NAME;
   title 'PARTNERS';
run;

proc freq data=interviewed_cases_out ;
   tables FL_FUP_DISPOSITION_DESC/ out=PA;
    *where contact_referral_basis in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both') and FL_FUP_DISPOSITION_DESC in ('A - Preventative Treatment');
   weight count;
by INTERVIEWER_NAME;
   title 'Preventative Treatment:';
run;

proc freq data=interviewed_cases_out ;
   tables FL_FUP_DISPOSITION_DESC/ out=PE;
    where INIT_FUP_INITIAL_FOLL_UP_CD='Field Follow-up' 
		AND FL_FUP_DISPOSITION_DESC in (
		'E - Previously Treated for This Infection') 
		AND Referral_basis not in ('P1 - Partner, Sex',
		'P2 - Partner, Needle-Sharing',
		'P3 - Partner, Both');
   weight count;
by INTERVIEWER_NAME;
   title 'Preventative Treatment:';
run;
proc freq data=interviewed_cases_out ;
   tables FL_FUP_DISPOSITION_DESC/ out=PO;
    where FL_FUP_DISPOSITION_DESC is null;
   weight count;
by INTERVIEWER_NAME;
   title 'OPEN';
run;

proc freq data=interviewed_cases_out ;
   tables FL_FUP_DISPOSITION_DESC/ out=CM;
    where REFERRAL_BASIS like ('A%') OR REFERRAL_BASIS like  ('S%') OR REFERRAL_BASIS like ('C%') 
		and  ADM_ReferralBasisOOJ is null;
   weight count;
by INTERVIEWER_NAME;
   title 'New Clusters Examined:';
run;
proc freq data=interviewed_cases_out ;
   tables Exam_days / out=pm outexpect sparse;
   where Exam_days is not null;
   weight count;
by INTERVIEWER_NAME;
   title 'Exam Days';
run;
proc freq data=interviewed_cases_out ;
   tables CLUSTER_DAYS / out=cluster outexpect sparse;
   where CLUSTER_DAYS is not null;
   weight count;
by INTERVIEWER_NAME;
   title 'Case Management Report';
run;
data interview_days;
set interview_days;
rank=1;
run;
data ADL;
 set ADL;
rank=2;
run;

data HIV_900_TEST_IND ;
set HIV_900_TEST_IND ;
IF(HIV_900_TEST_IND ='YES' then HIV_900_TEST_IND  ='HIV Tested:';
rank=3;
run;


data HIV_900_RESULT;
set HIV_900_RESULT;
rank=4;
run;

data HIV_POST_TEST_900_COUNSELLING;
set HIV_POST_TEST_900_COUNSELLING;
rank=5;
run;

data DISEASE_INT_INDEX;
set DISEASE_INT_INDEX;
rank=6;
run;

data SOURCE_SPREAD;
set SOURCE_SPREAD;
rank=7;
run;

data partners;
set partners;
rank=8;
run;

data PA;
set PA;
rank=9;
run;

data PE;
set PE;
rank=10;
run;

data PO;
set PO;
rank=11;
run;

data CM;
set CM;
rank=12;
run;
data PM;
set PM;
rank=13;
run;
data cluster;
set cluster;
rank=14;
run;


Proc SQL;

Create table output as select * from interview_days 
UNION select * from ADL 
UNION select * from HIV_900_TEST_IND 
UNION select * from HIV_900_TEST_IND
UNION select * from HIV_900_RESULT
UNION select * from HIV_POST_TEST_900_COUNSELLING
UNION select * from DISEASE_INT_INDEX
UNION select * from SOURCE_SPREAD
UNION select * from partners
UNION select * from PA
UNION select * from PE
UNION select * from PO
UNION select * from CM
UNION select * from pm
UNION select * from cluster;
quit;


%footnote;


%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);


proc report data=output nowd split ='*' missing;
column RANK  Interview_days   count percent ;
by INTERVIEWER_NAME;
define RANK /group noprint;
define Interview_days/group '';
define percent2/DISPLAY 'Percentage' format=percent10. ;
define count /  DISPLAY 'number' format=5.;
break after RANK/summrize dul;
endcomp;

compute BEFORE RANK;
Interview_days='CASE ASSIGNMENTS & OUTCOMES';
ENDCOMP;
compute after RANK;
Line @5 '';
endcomp;

run;
quit;

ods html close;
%end;
%else 
      %export(work,output,sock,&exporttype);

Title;
Footnote;
%finish:
%mend NBSSR00035;
%NBSSR00035;
