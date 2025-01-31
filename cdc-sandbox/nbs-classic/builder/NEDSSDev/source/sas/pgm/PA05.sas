/*Program Name : PA05.sas																														*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	NOV-2016																												*/
/*																																				*/
/*Program Last Modified Date:01-04-2017 :Formatting complete 																					*/
/*							01-13-2017:issues for formating fixed.																													*/
/*																																				*/
/*Program Description:	Creates PA05 									 																		*/
/*																																				*/
/*Comments:	
*/

%macro PA05;

%if  %upcase(&skip)=YES %then
      %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

data _null_;
call symputx ('TDAY' , put(today(),mmddyy10.));

call symputx ('time_text',put(time(),timeampm8.));
call symputx ('DTE1' , cats(put(intnx('month',today(),-1),mmddyy10.)));
call symputx ('DTE2' , cats(put(intnx('month',today(),-1,"E"),mmddyy10.)));
run;

data _null_;
put " Tday = &tday";
put " DTE1 = &DTE1";
put " DTE2 = &DTE2";

put " time_text = &time_text";
run;

ods listing;

proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and  e.INV_CASE_STATUS in ('Probable','Confirmed') and a.CA_INTERVIEWER_ASSIGN_DT~=.
;
quit;



proc sql;
create table PA05 as 
select  distinct a.INV_LOCAL_ID, c.IX_TYPE, c.ix_location, e.INV_CASE_STATUS,A.INVESTIGATION_KEY,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(c.IX_DATE)-datepart(a.CA_INTERVIEWER_ASSIGN_DT)  as Days,
d.PROVIDER_QUICK_CODE, a.CC_CLOSED_DT, a.CA_PATIENT_INTV_STATUS
from STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY;
quit;

proc sql noprint;
select min(CA_INTERVIEWER_ASSIGN_DT), max(CA_INTERVIEWER_ASSIGN_DT), min(CC_CLOSED_DT), max(CC_CLOSED_DT)
into :MIN_CA_INTERVIEWER_ASSIGN_DT , :MAX_CA_INTERVIEWER_ASSIGN_DT,:MIN_CC_CLOSED_DT, :MAX_CC_CLOSED_DT
from STD_HIV_DATAMART1 where INVESTIGATOR_INTERVIEW_KEY ~=1 
; 

proc sql;
create table PA05_IXS_INIT as 
select  distinct a.IX_TYPE, PROVIDER_QUICK_CODE, IX_INTERVIEWER_KEY as INVESTIGATOR_INTERVIEW_KEY, 
a.D_INTERVIEW_KEY, d.D_CONTACT_RECORD_KEY, d.CONTACT_INVESTIGATION_KEY, e.CTT_REFERRAL_BASIS, e.CTT_PROCESSING_DECISION,
f.CA_INTERVIEWER_ASSIGN_DT, f.CC_CLOSED_DT
from nbs_rdb.D_INTERVIEW a 
inner join nbs_rdb.F_INTERVIEW_CASE b on
a.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY 
and a.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.std_hiv_datamart f on
b.INVESTIGATION_KEY = f.INVESTIGATION_KEY
inner join nbs_rdb.D_provider c on
b.IX_INTERVIEWER_KEY = c.PROVIDER_KEY
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d
on  d.CONTACT_INTERVIEW_KEY =a.D_INTERVIEW_KEY
left outer join nbs_rdb.D_CONTACT_RECORD e
on d.d_contact_record_key = e.d_contact_record_key
and e.RECORD_STATUS_CD~='LOG_DEL'
where 
f.INVESTIGATOR_INTERVIEW_KEY in (select INVESTIGATOR_INTERVIEW_KEY from STD_HIV_DATAMART1 where INVESTIGATOR_INTERVIEW_KEY ~=1)
/*these conditions are added to make sure we are getting the records where the selected 
worker was not aassigned but end up performing the interview*/
and datepart(f.CA_INTERVIEWER_ASSIGN_DT) GE datepart(&MIN_CA_INTERVIEWER_ASSIGN_DT)
and datepart(f.CA_INTERVIEWER_ASSIGN_DT) LE datepart(&MAX_CA_INTERVIEWER_ASSIGN_DT)
and (datepart(f.CC_CLOSED_DT) GE datepart(&MIN_CC_CLOSED_DT) or f.CC_CLOSED_DT in(select CC_CLOSED_DT from STD_HIV_DATAMART1 where INVESTIGATOR_INTERVIEW_KEY ~=1))
and (datepart(f.CC_CLOSED_DT) LE datepart(&MAX_CC_CLOSED_DT) or f.CC_CLOSED_DT in(select CC_CLOSED_DT from STD_HIV_DATAMART1 where INVESTIGATOR_INTERVIEW_KEY ~=1))
;
quit;



proc sql;
create table PA05_WRKR as 
select  distinct IX_TYPE, PROVIDER_QUICK_CODE, INVESTIGATOR_INTERVIEW_KEY, count(distinct D_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT
where IX_TYPE in ('Initial/Original', 'Re-Interview')
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table PA05_NCI as 
select  distinct IX_TYPE, PROVIDER_QUICK_CODE, INVESTIGATOR_INTERVIEW_KEY, count(distinct D_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT where IX_TYPE in ('Initial/Original', 'Re-Interview') and D_CONTACT_RECORD_KEY is null or D_CONTACT_RECORD_KEY=1
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table PA05_PI as 
select  distinct IX_TYPE, PROVIDER_QUICK_CODE, INVESTIGATOR_INTERVIEW_KEY, count(distinct CATS(CONTACT_INVESTIGATION_KEY,CTT_REFERRAL_BASIS)) as count_per_wrkr 
from PA05_IXS_INIT  where IX_TYPE in ('Initial/Original', 'Re-Interview' ) and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and 
CTT_PROCESSING_DECISION in ('Field Follow-up','Record Search Closure','Secondary Referral')
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table PA05_CI as 
select  distinct IX_TYPE, PROVIDER_QUICK_CODE, INVESTIGATOR_INTERVIEW_KEY, count(distinct CATS(CONTACT_INVESTIGATION_KEY,CTT_REFERRAL_BASIS)) as count_per_wrkr 
from PA05_IXS_INIT  where IX_TYPE in ('Initial/Original', 'Re-Interview' ) 
and CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and 
CTT_PROCESSING_DECISION in ('Field Follow-up','Record Search Closure','Secondary Referral')
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table PA05_DTE as 
select distinct  c.LOCAL_ID,a.INV_LOCAL_ID, c.IX_TYPE, e.INV_CASE_STATUS, e.record_status_cd, a.CC_CLOSED_DT,c.IX_DATE,a.CA_INTERVIEWER_ASSIGN_DT,
 a.FL_FUP_INIT_ASSGN_DT, e.CURR_PROCESS_STATE , a.CA_PATIENT_INTV_STATUS,c.D_INTERVIEW_KEY,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(c.IX_DATE)-datepart(a.CA_INTERVIEWER_ASSIGN_DT)  as Days,
d.PROVIDER_QUICK_CODE
from STD_HIV_DATAMART1 a inner join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.record_status_cd~='LOG_DEL' 
inner join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
where c.IX_TYPE in ('Initial/Original', 'Re-Interview' ) 
and datepart(c.IX_DATE) GE datepart(a.CA_INTERVIEWER_ASSIGN_DT)
order by INVESTIGATOR_INTERVIEW_KEY;
quit;



/*---------------------total partners initiated----> R*/
proc sql;
create table pp as 
select distinct c.LOCAL_ID,  input(a.STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL, 
input(a.SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , input(a.SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q, c.IX_TYPE,
D_provider.PROVIDER_QUICK_CODE, a.inv_local_id , a.CA_PATIENT_INTV_STATUS, a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.F_INTERVIEW_CASE b
  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c
  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  and c.RECORD_STATUS_CD~='LOG_DEL'
  inner join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
  where c.IX_TYPE in ('Initial/Original')and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed');
  quit;

%chk_mv;
  
options missing=0 ;


 proc freq data = PA05_DTE ;
by INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE;
table Days / nocum missing out = dte_freq_ind ;
where Days le 14 and IX_TYPE='Initial/Original';
run;

proc sql;
create table cases_assigned  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_A  
from PA05
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit; 
title;

proc sql;
create table cases_open  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_B 
from PA05 
where CC_CLOSED_DT is null
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit;

proc sql;
create table cases_closed  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_C 
from PA05 
where CC_CLOSED_DT is not null
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit;


proc sql;
create table cases_pending  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_D 
from PA05 
where CA_PATIENT_INTV_STATUS in ('A - Awaiting')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit;


proc sql;
create table cases_ixd  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_E  
from PA05  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit; 


proc sql;
create table f  as 
select DISTINCT  A.PROVIDER_QUICK_CODE,  A.INVESTIGATOR_INTERVIEW_KEY,  count (distinct inv_local_id) as Var_F 
from PA05  A
WHERE
IX_TYPE= 'Initial/Original'
AND ix_location='Clinic'
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;



proc sql;
create table G  as 
select DISTINCT  A.PROVIDER_QUICK_CODE,  A.INVESTIGATOR_INTERVIEW_KEY,  count (distinct inv_local_id) as Var_G
from PA05  A
WHERE
IX_TYPE= 'Initial/Original'
AND ix_location='Field'
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;




proc sql; 
create table days_A_03 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(COUNT) as Var_H
from dte_freq_ind
where Days le 3
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_05 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(COUNT) as Var_I
from dte_freq_ind
where Days le 5
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_07 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(COUNT) as Var_J
from dte_freq_ind
where Days le 7
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_14 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(COUNT) as Var_K
from dte_freq_ind
where Days le 14
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;
quit;

proc sql; 
create table L as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_L
from PA05 
where CA_PATIENT_INTV_STATUS  in('O - Other' , 'R - Refused Interview', 'U - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql; 
create table M as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_M
from PA05 
where CA_PATIENT_INTV_STATUS  in ('R - Refused Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql; 
create table N as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_N
from PA05 
where CA_PATIENT_INTV_STATUS  in ('U - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql; 
create table O as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_O
from PA05 
where CA_PATIENT_INTV_STATUS  in ('O - Other')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table p as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_P 
from PA05_WRKR  
WHERE IX_TYPE IN ('Initial/Original')
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table q as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_q 
from PA05_NCI
WHERE IX_TYPE IN ('Initial/Original') 
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sort data=pp nodupkey out=ppnodup ;
by local_id;
run;

proc sql;
create table r as   
select distinct PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, sum(count_q) as var_r
from ppnodup 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE  ;
quit;

proc sql;
create table s as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_s
from PA05_PI 
WHERE IX_TYPE IN ('Initial/Original')
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table t as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_t 
from PA05_WRKR  
WHERE IX_TYPE IN ('Re-Interview')
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table u as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_u 
from PA05_NCI  
WHERE IX_TYPE IN ('Re-Interview')
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table v as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_v
from PA05_PI 
WHERE IX_TYPE IN ('Re-Interview')
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table w as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, sum(count_per_wrkr)  as var_w
from PA05_CI 
WHERE IX_TYPE IN ('Initial/Original', 'Re-Interview') 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

/*options missing=0 ;*/
data combined;
merge
cases_assigned
cases_open
cases_closed
cases_pending
cases_ixd
f
G
days_A_03
days_A_05
days_A_07
days_A_14 l m n o p
q r s t u v w ;
by INVESTIGATOR_INTERVIEW_KEY;
run;

/*data combined;*/
/*   set combined;*/
/*   array change _numeric_;*/
/*        do over change;*/
/*            if change=. then change=0;*/
/*        end;*/
/* run ;*/

data combined;
set combined ;
per_b = Var_B/Var_A ; 
per_c = Var_C/Var_A ; 
per_d = var_d/Var_A ; 
per_e = var_e/Var_A ; 

per_f= var_f/Var_e ;
per_g = var_g/Var_e ; 
per_h = var_h/Var_e ;
per_i = var_i/Var_e ; 
per_j = var_j/Var_e ; 
per_k= var_k/Var_e ; 

per_l = var_l/var_a;
per_m = var_m/var_l ;
per_n = var_n/var_l ;
per_o = var_o/var_l ;


per_q = var_q/Var_p;
per_r= var_r/Var_p;
per_s = var_s/Var_p;

per_u= var_u/Var_t;
per_v = var_v/Var_t;

per_w= var_w/Var_p;
format per_b per_c per_d per_e 
per_f per_g per_h per_i per_j per_k per_l per_m per_n per_o per_q per_u percent8.1; 
/*if missing (per_m) then per_m = 0.0%;*/

run;


proc sql noprint;
select sum(Var_A) into :Val_A 
from cases_assigned 
;

select sum(Var_b) into :Val_B 
from cases_OPEN
;

select sum(Var_C) into :Val_C
from cases_CLOSED 
;

select sum(Var_D) into :Val_D 
from cases_PENDING 
;

select sum(Var_E) into :Val_E 
from cases_ixd 
;

select sum(Var_F) into :Val_F 
from F 
;

select sum(Var_g) into :Val_g 
from g 
;
select sum(Var_h) into :Val_h 
from days_A_03 
;

select sum(Var_i) into :Val_i 
from days_A_05 
;

select sum(Var_j) into :Val_j 
from days_A_07 
;

select sum(Var_k) into :Val_k 
from days_A_14
;
select sum(Var_l) into :Val_l 
from l 
;

select sum(Var_m) into :Val_m 
from m 
;

select sum(Var_n) into :Val_n 
from n 
;

select sum(Var_o) into :Val_o 
from o 
;

select sum(var_p) into :Val_p 
from p 
;

select sum(Var_q) into :Val_q 
from q
;

select sum(Var_r) into :Val_r 
from r 
;

select sum(Var_s) into :Val_s 
from s 
;

select sum(Var_t) into :Val_t 
from t 
;

select sum(Var_u) into :Val_u 
from u 
;

select sum(Var_v) into :Val_v 
from v 
;

select sum(Var_w) into :Val_w 
from w 
;
quit;





%let PER_B = %sysevalf(%eval(&Val_B) / %eval(&Val_A)) ;
%let PER_C = %sysevalf(%eval(&Val_C) / %eval(&Val_A)) ;
%let PER_D = %sysevalf(%eval(&Val_D) / %eval(&Val_A)) ;
%let PER_E = %sysevalf(%eval(&Val_E) / %eval(&Val_A)) ;

%let PER_F = %sysevalf(%eval(&Val_F) / %eval(&Val_E)) ;
%let PER_G = %sysevalf(%eval(&Val_G) / %eval(&Val_E)) ;
%let PER_H = %sysevalf(%eval(&Val_H) / %eval(&Val_E)) ;
%let PER_I = %sysevalf(%eval(&Val_I) / %eval(&Val_E)) ;
%let PER_J = %sysevalf(%eval(&Val_J) / %eval(&Val_E)) ;	
%let PER_K = %sysevalf(%eval(&Val_K) / %eval(&Val_E)) ;	

%let PER_L = %sysevalf(%eval(&Val_L) / %eval(&Val_A)) ;
%let PER_M = %sysevalf(%eval(&Val_M) / %eval(&Val_L)) ;
%let PER_N = %sysevalf(%eval(&Val_N) / %eval(&Val_L)) ;
%let PER_O= %sysevalf(%eval(&Val_O) / %eval(&Val_L)) ;


%let PER_Q = %sysevalf(%eval(&Val_Q) / %eval(&Val_P)) ;
%let PER_R = %sysevalf(%eval(&Val_R) / %eval(&Val_P)) ;
%let PER_S = %sysevalf(%eval(&Val_S) / %eval(&Val_P)) ;

%let PER_U = %sysevalf(%eval(&Val_U) / %eval(&Val_T)) ;
%let PER_V = %sysevalf(%eval(&Val_V) / %eval(&Val_T)) ;
%let per_W = %sysevalf(%eval(&Val_W) / %eval(&Val_P)) ;




data _null_;
put "Val_A =  &Val_A";
put "Val_B =  &Val_B";
put "Val_C =  &Val_C";

put "Val_D =  &Val_D";

put "Val_E =  &Val_E";
put "Val_F =  &Val_F";
put "Val_G =  &Val_G";
put "Val_w =  &Val_w";
put "per_b =  &per_b";
put "per_c =  &per_c";
put "per_d =  &per_d";
put "PER_E =  &PER_E";
put "PER_F =  &PER_F";
put "PER_l =  &PER_l";
put "PER_m =  &PER_m";
put "PER_n =  &PER_n";
put "PER_o =  &PER_o";
put "PER_q =  &PER_q";
put "PER_r =  &PER_r";
put "PER_S =  &PER_S";
put "PER_u =  &PER_u";
put "PER_v =  &PER_v";
run;

/*Create template data for overall summary report*/
filename NBSPGMp1  "&SAS_REPORT_HOME/template";


%include NBSPGMp1 (Template6.sas);

options missing =0   ;

data ind1_1 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE colname colval colpct );
set COMBINED;
length colname $35. ; 
format colname $35. ;
length colval 8 ;
format colval best12. ;
length colpct 8 ; 
format colpct PERCENT8.1 ;
colname = 'NUM. CASES ASSIGNED:';
colval = Var_A;
colpct = .;

output;
colname = 'NUM. CASES OPEN:';
colval = Var_B;
colpct =  per_b;

output;
colname = "NUM. CASES CLOSED:";
colval = Var_C;
colpct = per_c;

output;
colname = "NUM. CASES PENDING:";
colval = Var_D;
colpct = per_D;

output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "NUM. CASES IX'D:";
colval = Var_E;
colpct = per_E;

output;
colname = "CLINIC IX'S:";
colval = Var_F;
colpct = per_F;

output;
colname = "FIELD IX'S:";
colval = Var_G;
colpct = per_G;

output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "IX’D W/IN 3 DAYS:";
colval = var_H;
colpct = per_H;

output;
colname = "IX’D W/IN 5 DAYS:";
colval = var_I;
colpct = per_I;

output;
colname = "IX’D W/IN 7 DAYS:";
colval = var_J;
colpct = per_J;

output;
colname = "IX’D W/IN 14 DAYS:";
colval = var_K;
colpct = per_K;
output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "NUM. CASES NOT IX'D:";
colval = Var_L;
colpct = per_L;

output;
colname = "REFUSED:";
colval = Var_M;
colpct = per_M;

output;
colname = "NO LOCATE:";
colval = Var_N;
colpct = per_N;

output;
colname = "OTHER:";
colval = Var_O;
colpct = per_O;
output;
format colpct PERCENT8.1 ;
RUN;


data ind1_2 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE colname2 colval2 colpct2 );
set COMBINED;
length colname2 $35. ; 
format colname2 $35. ; 
length colval2 8 ;
format colval2 best12. ;
length colpct2 8 ; 
/*format colpct2 PERCENT8.1 ;*/
colname2 ="NUM. OF OI'S:";
colval2 = Var_P;
colpct2 = .;
output;
colname2 = "OI'S THAT WERE NCI:";
colval2 = Var_Q;
colpct2 = per_q;

output;
/*format per_q percent8.1;*/
colname2 = "PERIOD PARTNERS:";
colval2 = Var_R;
colpct2 = round(per_R,0.1);
output;
colname2 = "PARTNERS INITIATED:";
colval2 = Var_S;
colpct2 = round(per_S,0.1);
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 ="NUM. OF RI'S:";
colval2 = Var_T;
colpct2 = .;
output;
colname2 = "RI'S THAT WERE NCI:";
colval2 = Var_U;
colpct2 = per_u;
output;
colname2 = "PARTNERS. INITIATED:";
colval2 = Var_V;
colpct2 = round(per_V,0.1);
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = "CLUSTERS INIT (OI & RI):";
colval2 = var_W;
colpct2 = round(per_W,0.1);
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
colname2 = "";
colval2 = .;
colpct2 = .;
output;
RUN;


data indiePA5;
merge ind1_1 ind1_2;
by INVESTIGATOR_INTERVIEW_KEY;
run;

 data indiePA5;
   set indiePA5;
   array change _numeric_;
        do over change;
            if change=. then change=0;
        end;
 run ;

proc sort data = indiePA5;
by PROVIDER_QUICK_CODE;
run;

	

options missing = '' ; 
data templatePA05;
set templatePA05;
if find(descrip,'NUM. CASES ASSIGNED:','i') ge 1 then value1 = "&Val_A";
if find(descrip,'NUM. CASES OPEN:','i') ge 1 then value1 = "&Val_B" ;
if find(descrip,'NUM. CASES CLOSED:','i') ge 1 then value1 = "&Val_C" ;
if find(descrip,'NUM. CASES PENDING:','i') ge 1 then value1 = "&Val_D" ;
if find(descrip,'NUM. CASES IX''D:','i') ge 1 then value1 = "&Val_E" ;
if find(descrip,'CLINIC IX''S:','i') ge 1 then value1 = "&Val_F" ;
if find(descrip,'FIELD IX''S:','i') ge 1 then value1 = "&Val_G" ;
if find(descrip,'IX''D W/IN 3 DAYS:','i') ge 1 then value1 = "&Val_H" ;
if find(descrip,'IX''D W/IN 5 DAYS:','i') ge 1 then value1 = "&Val_I" ;
if find(descrip,'IX''D W/IN 7 DAYS:','i') ge 1 then value1 = "&Val_J" ;
if find(descrip,'IX''D W/IN 14 DAYS:','i') ge 1 then value1 = "&Val_K" ;
if find(descrip,'NUM. CASES NOT IX''D:','i') ge 1 then value1 = "&Val_L" ;
if find(descrip,'REFUSED:','i') ge 1 then value1 = "&Val_M" ;
if find(descrip,'NO LOCATE:','i') ge 1 then value1 = "&Val_N" ;
if find(descrip,'OTHER:','i') ge 1 then value1 = "&Val_O" ;

if find(descrip2,'NUM. OF OI''S:','i') ge 1 then value2 = "&Val_P" ;
if find(descrip2,'OI''S THAT WERE NCI:','i') ge 1 then value2 = "&Val_Q" ;
if find(descrip2,'PERIOD PARTNERS:','i') ge 1 then value2 = "&Val_R" ;
if find(descrip2,'PARTNERS INITIATED:','i') ge 1 then value2 = "&Val_S" ;
if find(descrip2,'NUM. OF RI''S:','i') ge 1 then value2 = "&Val_T" ;
if find(descrip2,'RI''S THAT WERE NCI:','i') ge 1 then value2 = "&Val_U" ;
if find(descrip2,'PARTNERS. INITIATED:','i') ge 1 then value2 = "&Val_V" ;
if find(descrip2,'CLUSTERS INIT (OI & RI):','i') ge 1 then value2 = "&Val_W" ;

if find(descrip,'NUM. CASES OPEN:','i') ge 1 then percent1 = put(round("&per_B",0.001),percent8.1) ;
if find(descrip,'NUM. CASES CLOSED:','i') ge 1 then percent1 = put(round("&per_C",0.001),percent8.1) ;
if find(descrip,'NUM. CASES PENDING:','i') ge 1 then percent1 = put(round("&per_D",0.001),percent8.1) ;
if find(descrip,'NUM. CASES IX''D:','i') ge 1 then percent1 = put(round("&per_E",0.001),percent8.1) ;
if find(descrip,'CLINIC IX''S:','i') ge 1 then percent1 = put(round("&per_F",0.001),percent8.1) ;
if find(descrip,'FIELD IX''S:','i') ge 1 then percent1 = put(round("&per_G",0.001),percent8.1) ;
if find(descrip,'IX''D W/IN 3 DAYS:','i') ge 1 then percent1 = put(round("&per_H",0.001),percent8.1) ;
if find(descrip,'IX''D W/IN 5 DAYS:','i') ge 1 then percent1 = put(round("&per_I",0.001),percent8.1) ;
if find(descrip,'IX''D W/IN 7 DAYS:','i') ge 1 then percent1 = put(round("&per_J",0.001),percent8.1) ;
if find(descrip,'IX''D W/IN 14 DAYS:','i') ge 1 then percent1 = put(round("&per_K",0.001),percent8.1) ;
if find(descrip,'NUM. CASES NOT IX''D:','i') ge 1 then percent1 = put(round("&per_L",0.001),percent8.1) ;
if find(descrip,'REFUSED:','i') ge 1 then percent1 = put(round("&per_M",0.001),percent8.1) ;
if find(descrip,'NO LOCATE:','i') ge 1 then percent1 = put(round("&per_N",0.001),percent8.1) ;
if find(descrip,'OTHER:','i') ge 1 then percent1 = put(round("&per_O",0.001),percent8.1) ;

if find(descrip2,'OI''S THAT WERE NCI:','i') ge 1 then percent2 = put(round("&per_Q",0.001),percent8.1) ;
if find(descrip2,'PERIOD PARTNERS:','i') ge 1 then percent2 = round("&per_R",0.1) ;
if find(descrip2,'PARTNERS INITIATED:','i') ge 1 then percent2 = round("&PER_S",0.1) ;

if find(descrip2,'RI''S THAT WERE NCI:','i') ge 1 then percent2 = put(round("&per_U",0.001),percent8.1) ;
if find(descrip2,'PARTNERS. INITIATED:','i') ge 1 then percent2 = round("&per_V",0.1) ;
if find(descrip2,'CLUSTERS INIT (OI & RI):','i') ge 1 then percent2 = round("&per_W",0.1) ;

run;
title;
ods results;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   MISSING = ' ' 
topmargin=0.75 in bottommargin=0.50in leftmargin=0.50in rightmargin=0.50in  nobyline  ;
ods noproctitle;
ods escapechar='^'; 
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "_________________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";



%if %upcase(&exporttype)=REPORT %then %do;
options printerpath=pdf;

ods PDF body = sock NOTOC UNIFORM;
/*ods pdf file = "C:\Users\Aroras\Documents\Report\rpt.pdf"  NOTOC UNIFORM;*/


title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";
TITLE2 bold f= Calibri h=12pt  j=c "WORKER: SUMMARY (ALL WORKERS)";


proc report data = templatepa05 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="calibri" font_size = 10pt }
style(report)={font_size=10pt  rules=none frame=void  font_face="calibri"}
style(column)={font_size=10pt font_face="calibri"};


columns  descrip    value1 percent1 gap descrip2 value2 percent2;
define descrip /display order=internal ' ' left style = [cellwidth=50mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define   value1    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define   percent1    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define gap / '    ' format=$4. style={cellwidth=20mm CELLHEIGHT = 6mm};
define descrip2 /display order=internal ' ' left style = [cellwidth=50mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define   value2    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define   percent2    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];


compute descrip;
 if find(descrip,'NUM. CASES ASSIGNED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
  if find(descrip,'NUM. CASES IX''D:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
 if find(descrip,'NUM. CASES NOT IX''D:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;

  if find(descrip,'NUM. CASES OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'NUM. CASES CLOSED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'NUM. CASES PENDING:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'CLINIC IX''S:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'FIELD IX''S:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'IX''D W/IN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'IX''D W/IN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'IX''D W/IN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'IX''D W/IN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
    if find(descrip,'REFUSED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'NO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
  descrip=upcase(descrip);
 endcomp;

compute descrip2;
if find(descrip2,'NUM. OF OI''S:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
  if find(descrip2,'NUM. OF RI''S:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
  if find(descrip2,'OI''S THAT WERE NCI:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip2,'PERIOD PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip2,'PARTNERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip2,'RI''S THAT WERE NCI:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip2,'PARTNERS. INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(descrip2,'CLUSTERS INIT (OI & RI)','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
  descrip2=upcase(descrip2);
  endcomp;

 run;



ods proclabel = "Report 1" ;
title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";
TITLE2 bold f= Calibri h=12pt  j=c " 		WORKER: SUMMARY OF #byval(PROVIDER_QUICK_CODE)	";


proc report data = indiePA5 nowd spanrows   split='~' headline missing 
style(header)={just=left font_weight=bold font_face="calibri" font_size = 10pt }
style(report)={font_size=10pt rules=none frame=void  font_face="calibri"}
style(column)={font_size=10pt font_face="calibri"};
by PROVIDER_QUICK_CODE ;
columns  PROVIDER_QUICK_CODE colname colval colpct blank colname2 colval2 colpct2;

define PROVIDER_QUICK_CODE / group page noprint ;
define colname /DISPLAY "  " left style = [cellwidth=50mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define  colval    / DISPLAY " " format = best9. style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define colpct /DISPLAY " "  format = percent8.1 style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define blank / ' ' format=$4. style={cellwidth=20mm CELLHEIGHT = 6mm};
define colname2 /DISPLAY "  " style = left style = [cellwidth=50mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define  colval2    / DISPLAY " " format = best9. style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
define colpct2 /DISPLAY " "    style = [just=center cellwidth=15mm CELLHEIGHT = 6mm font_size=10pt font_face="calibri"];
compute colname;
  if find(colname,'NUM. CASES OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
  if find(colname,'NUM. CASES ASSIGNED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'NUM. CASES NOT IX''D:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
    if find(colname,'NUM. CASES CLOSED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'NUM. CASES PENDING:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
    if find(colname,'NUM. CASES IX''D:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'CLINIC IX''S:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'FIELD IX''S:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'IX’D W/IN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'IX’D W/IN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'IX’D W/IN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'IX’D W/IN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
    if find(colname,'REFUSED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'NO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.42in font_face="calibri" font_size = 10pt}');
  end;
  colname=upcase(colname);
 endcomp;

 compute colval;
   if missing(colname)  then do; colval = ' ' ;
  end;
  endcomp;
 compute colval2;
   if missing(colname2)  then do; colval2 = ' ' ;
  end;
  endcomp;
 compute colpct;
   if colname ='NUM. CASES ASSIGNED:'  then do;
colpct = ' ' ;
 end;
  if missing(colname)  then do; 
colpct = ' ' ;
end ;
    endcomp;
compute colpct2;
   if colname2 ='NUM. CASES ASSIGNED:'  then do;
colpct = ' ' ;
 end;
  if missing(colname2)  then do; 
colpct2 = ' ' ;
end ;
 if colname2 ='NUM. OF OI''S:'  then do;
colpct2 = ' ' ;
 end;
 if colname2 ='NUM. OF RI''S:'  then do;
colpct2 = ' ' ;
 end;

 if find(colname2,'OI''S THAT WERE NCI:','i') ge 1  then do;
 call define(_col_,'format','Percent8.1');
 call define(_col_,'style','style={tagattr="###%" font_face="calibri" font_size = 10pt}');
  end;
 if find(colname2,'RI''S THAT WERE NCI:','i') ge 1  then do;
 call define(_col_,'format','Percent8.1');
 call define(_col_,'style','style={tagattr="###%" font_face="calibri" font_size = 10pt}');
  end;
    endcomp;

  compute colname2;
  if find(colname2,'OI''S THAT WERE NCI:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname2,'PERIOD PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname2,'PARTNERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname2,'RI''S THAT WERE NCI:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname2,'PARTNERS. INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=.27in font_face="calibri" font_size = 10pt}');
  end;
   if find(colname2,'NUM. OF OI''S:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
  if find(colname2,'NUM. OF RI''S:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;
     if find(colname2,'CLUSTERS INIT (OI & RI)','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_face="calibri" font_size = 10pt}');
  end;

  colname2=upcase(colname2);
  endcomp;

break after PROVIDER_QUICK_CODE / page  ; 
compute before _page_ / style = {just = c};
endcomp;
run;

ods pdf close;




	/*delete work data */
	proc datasets kill lib = work nolist memtype=data;
	quit;
	 data _null_;
	 call symputx ('START_TIME',PUT(DATETIME(),15.));
	 START_TIME = &START_TIME;
	 END_TIME = DATETIME();
	 ELAPSED = END_TIME - START_TIME;
	 PUT ' NOTE: Start Time (HH:MM) = ' START_TIME TIMEAMPM8.;
	 PUT ' NOTE: End Time (HH:MM) = ' END_TIME TIMEAMPM8.;
	 PUT ' NOTE: Elapsed Time (HH:MM:SS) = ' ELAPSED TIME.; PUT ' ';
	RUN;
	%end;
	%else 
	      %export(work,PA05,sock,&exporttype);
	Title;
	%finish:
	%mend PA05;
%PA05;
/******************************************************END OF PROGRAM**********************************************************************************************************/





