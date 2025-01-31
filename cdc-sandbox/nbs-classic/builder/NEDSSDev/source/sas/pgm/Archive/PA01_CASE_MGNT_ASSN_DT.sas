
/*NBS-2922 STD REPORT PROJECT*/
/*TYPE :PA1 CASE MANAGEMENT REPORT PROGRAM */
/*last updated 09-13-2016*/

/*1. To Ask How to handle negative date differences, for now just excluided*/


/*dm log 'clear' continue ;*/
/*dm list 'clear' continue ;*/

/*proc datasets kill lib = work nolist;*/
/*run;*/

libname nbs_ods ODBC DSN=nedss2 UID=nbs_ods PASSWORD=ods ACCESS=READONLY;
libname nbs_rdb ODBC DSN=nbs_rdb1 UID=nbs_rdb PASSWORD=rdb ACCESS=READONLY;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;




/*ods listing;*/


/***************DATA PULL FOR Main Table**************/

/*New Query*/

 proc sql;
create table STD_HIV_DATAMART as select a.* from nbs_rdb.STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed');
quit;

proc sql;
create table pa1_new as 
select distinct  a.INV_LOCAL_ID, IX_TYPE_CD, INV_CASE_STATUS, e.record_status_cd, CC_CLOSED_DT, datepart(IX_DATE) as inter_dt format = mmddyy10.,
ADI_900_STATUS_CD,   HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT,ADI_900_STATUS,
 HIV_900_TEST_IND,source_spread,  FL_FUP_INIT_ASSGN_DT,
 CURR_PROCESS_STATE , CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(IX_DATE)-datepart(FL_FUP_INIT_ASSGN_DT)  as Days,
d.provider_last_name
from STD_HIV_DATAMART a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY

order by provider_last_name;
quit;



proc sql;
create table pa1_dte as 
select distinct  a.INV_LOCAL_ID, IX_TYPE_CD, INV_CASE_STATUS, e.record_status_cd, CC_CLOSED_DT, datepart(IX_DATE) as inter_dt format = mmddyy10.,
ADI_900_STATUS_CD,   HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT,ADI_900_STATUS,
 HIV_900_TEST_IND,source_spread,  FL_FUP_INIT_ASSGN_DT,
 CURR_PROCESS_STATE , CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(IX_DATE)-datepart(FL_FUP_INIT_ASSGN_DT)  as Days,
d.provider_last_name
from STD_HIV_DATAMART a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY

where datepart(IX_DATE) GE datepart(FL_FUP_INIT_ASSGN_DT)
order by provider_last_name;
quit;

/*data dte;*/
/*set pa1_dte;*/
/*if days lt 0 then flag = '1';*/
/*else flag = '' ;*/
/*run;*/

/*proc freq data = dte ;*/
/*table inter_dt*FL_FUP_INIT_ASSGN_DT*days/ list missing;*/
/*run;*/


/*---------------------total partners initiated----> R*/
proc sql;
create table pp as 
select distinct  input(STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL, 
input(SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , input(SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q,
a.INV_LOCAL_ID, IX_TYPE_CD, INV_CASE_STATUS, fl_fup_disposition,
investigation.record_status_cd, CC_CLOSED_DT, datepart(IX_DATE) as inter_dt format = mmddyy10.,
ADI_900_STATUS_CD,   IX_DATE,
 a.HIV_900_RESULT, /*HIV_POST_TEST_900_COUNSELLING_CD,  HIV_900_TEST_IND_CD,*/  
 CURR_PROCESS_STATE , CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC,
D_provider.provider_last_name
from STD_HIV_DATAMART a 
  left outer join nbs_rdb.F_INTERVIEW_CASE b
  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c
  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  left outer join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
left outer join nbs_rdb.F_CONTACT_RECORD_CASE
on  STD_HIV_DATAMART.investigation_key =F_CONTACT_RECORD_CASE.SUBJECT_investigation_key
left outer join nbs_rdb.d_contact_record
on d_contact_record.d_contact_record_key = F_CONTACT_RECORD_CASE.d_contact_record_key
    where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both')
  order by provider_last_name;
  quit;

  

/*      --------------PARTNERS INITIATED(S)------------*/
  proc sql;
create table part1 as 
select distinct IX_TYPE, a.INVESTIGATION_KEY , b.D_INTERVIEW_KEY ,provider_last_name
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on d.CONTACT_INTERVIEW_KEY = b.D_INTERVIEW_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
where IX_TYPE in ( 'Initial/Original' , 'Re-Interview');
QUIT;


/*     --------------PARTNERS INITIATED(T)------------*/
/*select distinct b.D_INTERVIEW_KEY,IX_TYPE, a.INVESTIGATION_KEY */
/*from STD_HIV_DATAMART a inner join */
/* nbs_rdb.F_INTERVIEW_CASE  b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY*/
/*inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY*/
/*inner join nbs_rdb.F_CONTACT_RECORD_CASE d on d.CONTACT_INTERVIEW_KEY = b.D_INTERVIEW_KEY*/
/*where IX_TYPE= 'Re-Interview'*/

/*      --------------no PARTNERS INITIATED(v)------------*/
proc sql;
create table part2 as 
select distinct b.D_INTERVIEW_KEY,IX_TYPE, a.INVESTIGATION_KEY, provider_last_name
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
where IX_TYPE= 'Re-Interview' and b.D_INTERVIEW_KEY NOT in (select CONTACT_INTERVIEW_KEY from nbs_rdb.F_CONTACT_RECORD_CASE );
quit;



/*  -----------------------W X CLUSTERS INIT. (OI & RI)::-----------------------*/
proc sql;
create table cluster as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name, a.FL_FUP_DISPOSITION
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3')
;quit;


/*******Cases with No clusters - Y To Ask and conform logic  ****/
proc sql;
create table cluster_No as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') 
and b.D_INTERVIEW_KEY NOT in (select CONTACT_INTERVIEW_KEY from nbs_rdb.F_CONTACT_RECORD_CASE );
quit;



/*  -----------------------pm , pa,--pf PARTNERS AND CLUSTERS:-----------------------*/
proc sql;
create table cluster2 as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition,  FL_FUP_DISPO_DT, FL_FUP_INIT_ASSGN_DT, datepart(FL_FUP_DISPO_DT)-datepart(FL_FUP_INIT_ASSGN_DT) as days
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.fl_fup_disposition in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected') 
;quit;

proc sql;
create table cluster2_dte as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition,  FL_FUP_DISPO_DT, FL_FUP_INIT_ASSGN_DT, datepart(FL_FUP_DISPO_DT)-datepart(FL_FUP_INIT_ASSGN_DT) as days
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.fl_fup_disposition in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected') and datepart(FL_FUP_DISPO_DT) ge datepart(FL_FUP_INIT_ASSGN_DT)
;quit;


/*data cluster2;*/
/*set cluster2;*/
/*if days lt 0 then days = .;*/
/*run;*/

/**/
/*proc  freq data = STD_HIV_DATAMART;*/
/*table fl_fup_disposition / list missing;*/
/*run;*/


/******PART2 - Individual Worker Report (In Progress)******************************/


proc sql;
create table cases_assigned  as 
select PROVIDER_LAST_NAME , count (*) as Var_A  
from pa1_new where INV_CASE_STATUS in ('Confirmed','Probable')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;quit; 
title;



proc sql;
create table cases_closed  as 
select PROVIDER_LAST_NAME , count (*) as Var_B 
from pa1_new 

/*where not  missing(CC_CLOSED_DT); */
where CC_CLOSED_DT is not null
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;quit;




proc sql;
create table cases_ixd  as 
select PROVIDER_LAST_NAME , count (*) as Var_C  
from pa1_new  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit; 
title;



proc freq data = pa1_dte  ;
by PROVIDER_LAST_NAME;
table Days / nocum missing out = dte_freq_ind_A 
(rename =(count=days_count_A)
drop = percent);
where  days le 14   ;
run;


proc sql; 
create table days_A_03 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_D
from dte_freq_ind_A
where Days le 3
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table days_A_05 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_E
from dte_freq_ind_A
where Days le 5
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table days_A_07 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_F
from dte_freq_ind_A
where Days le 7
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table days_A_14 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_G
from dte_freq_ind_A
where Days le 14
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cases_reint  as 
select PROVIDER_LAST_NAME , count (*) as Var_H  
from pa1_new  
/*where interview_type_cd in ('REINTVW')*/
where IX_TYPE_CD = 'REINTVW'
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;quit; 
title;


data big;
merge cases_assigned cases_closed cases_ixd
days_A_03 days_A_05 days_A_07 days_A_14 cases_reint;
by PROVIDER_LAST_NAME;
run;


proc sort data = big;
by PROVIDER_LAST_NAME;
run;



/****HIV prev positive;*/
proc sql;
create table hiv_prev_pos as 
select PROVIDER_LAST_NAME, count(*) as var_i 
from pa1_new 
where  INV_CASE_STATUS in ('Confirmed','Probable')
and ADI_900_STATUS in ('03','04','05')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


/*Notes : Do not include in report per requirement doc implementation notes */
/*proc sql;*/
/*create table hiv_pre_cou as */
/*select PROVIDER_LAST_NAME, count(*) as var_j */
/*from pa1_new */
/*where ADI_900_STATUS_CD in ('3 - Prior Positive - Not Previously Known')*/
/*group by PROVIDER_LAST_NAME*/
/*order by PROVIDER_LAST_NAME;*/
/*quit;*/

proc sql;
create table hiv_tested as 
select PROVIDER_LAST_NAME, count(*) as var_k 
from pa1_new 
where HIV_900_TEST_IND in ('Yes')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table hiv_new as 
select PROVIDER_LAST_NAME, count(*) as var_l 
from pa1_new  
where HIV_900_result in ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table hiv_post_test as 
select PROVIDER_LAST_NAME, count(*) as var_m 
from pa1_new 
where HIV_POST_TEST_900_COUNSELING in ('Yes')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;



proc sql;
create table dix as /***********To Ask Disease Intervention Index*****/
select PROVIDER_LAST_NAME, count(*) as var_n 
from pa1_new 
where ADI_900_STATUS_CD in ('1 - Negative')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;   /***********To Ask Rx Intervention Index*****/
create table rxindex as 
select PROVIDER_LAST_NAME, count(*) as var_o 
from pa1_new  
where ADI_900_STATUS_CD in ('6 - Other')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table src as 
select PROVIDER_LAST_NAME, count(*) as var_p 
from pa1_new  
where source_spread in ('1 - Not Related')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


data hiv;
merge hiv_prev_pos hiv_tested hiv_new hiv_post_test dix
rxindex src;
by PROVIDER_LAST_NAME;
run;

proc sort data = HIV;
by PROVIDER_LAST_NAME;
run;



DATA monster ;
merge big hiv;
by PROVIDER_LAST_NAME;
run;

data xxxx;
set monster;
per_b = var_b/var_a ; 
per_c = var_c/var_a ; 
per_d = var_d/var_c ; 
per_e = var_e/var_c ; 
per_f= var_f/var_c ;
per_g = var_g/var_c ; 
per_h = var_h/var_c ;
per_i = var_i/var_a ; 
/*per_j = var_j/var_a ; */
/*per_k = var_k/var_a ; */
per_l = var_l/var_k ;
per_m = var_m/var_k ;
per_p = var_p/var_c ;
run;

proc sort ;
by provider_last_name;
run;


/*data  indiepop;*/
/*merge ind1_1 ind1_2;*/
/*by provider_last_name;*/
/*run;*/


proc sql;
create table q as   
select PROVIDER_LAST_NAME, sum(count_Q) as var_q 
from pp  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table R as   
select PROVIDER_LAST_NAME, count(*)  as var_R 
from pp  
WHERE IX_TYPE_CD IN ('INITIAL','PRESMPTV','REINTVW')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table S as   
select PROVIDER_LAST_NAME, count(*)  as var_S 
from PART1  
WHERE IX_TYPE IN ('Initial/Original')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table t as   
select PROVIDER_LAST_NAME, count(*)  as var_t 
from PART1  
WHERE IX_TYPE IN ('Re-Interview')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table v as   
select PROVIDER_LAST_NAME, count(*)  as var_v 
from PART2  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table w as   
select PROVIDER_LAST_NAME, count(*)  as var_w
from cluster  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table y as   
select PROVIDER_LAST_NAME, count(*)  as var_y 
from cluster_no  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

data partner;
merge q r s t v w y cases_ixd;
by PROVIDER_LAST_NAME;
run;

data partner;
set partner ;
per_q = var_q/var_c;
var_u= var_r/var_c;
per_v = var_v/var_c;
VAR_X = VAR_W/VAR_C;
per_y= var_y/var_c;
run;

proc sort ;
by provider_last_name;
run;



/******Dispositions*************/

proc sql;
create table pm as 
select PROVIDER_LAST_NAME, count(*) as var_pm  
from cluster2  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pa as 
select PROVIDER_LAST_NAME, count(*) as var_pa 
from cluster2  
where fl_fup_disposition in ('F - Not Infected' ,'A - Preventative Treatment')  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pb as 
select PROVIDER_LAST_NAME, count(*) as var_pb 
from cluster2  
where fl_fup_disposition in ('F - Not Infected' ,'B - Refused Preventative Treatment')  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pc as 
select PROVIDER_LAST_NAME, count(*) as var_pc 
from cluster2  
where fl_fup_disposition in ('C - Infected, Brought to Treatment')  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pd as 
select PROVIDER_LAST_NAME, count(*) as var_pd 
from cluster2  
where fl_fup_disposition in ('D - Infected, Not Treated')  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table pf as 
select PROVIDER_LAST_NAME, count(*) as var_pf 
from cluster2  
where fl_fup_disposition in ('F - Not Infected')  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

/*  -----------------------pn-PZ  NEW PARTNERS NO EXAM:-----------------------*/
proc sql;
create table pn as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition , e.CTT_REFERRAL_BASIS
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.fl_fup_disposition in (
'G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',

'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment')
;quit;


proc sql;
create table pn_count as 
select PROVIDER_LAST_NAME, count(*) as var_pn 
from pn  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pg as 
select PROVIDER_LAST_NAME, count(*) as var_pg 
from pn  
where FL_FUP_DISPOSITION in (
'G - Insufficient Info to Begin Investigation')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table ph as 
select PROVIDER_LAST_NAME, count(*) as var_ph 
from pn  
where FL_FUP_DISPOSITION in (
'H - Unable to Locate')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table pj as 
select PROVIDER_LAST_NAME, count(*) as var_pj 
from pn  
where FL_FUP_DISPOSITION in (
'J - Located, Not Examined and/or Interviewed')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table pk as 
select PROVIDER_LAST_NAME, count(*) as var_pk 
from pn  
where FL_FUP_DISPOSITION in (
'K - Sent Out Of Jurisdiction')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table pl as 
select PROVIDER_LAST_NAME, count(*) as var_pl
from pn  
where FL_FUP_DISPOSITION in (
'L - Other')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pv as 
select PROVIDER_LAST_NAME, count(*) as var_pv
from pn  
where FL_FUP_DISPOSITION in (
'V - Domestic Violence Risk')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table px as 
select PROVIDER_LAST_NAME, count(*) as var_px
from pn  
where FL_FUP_DISPOSITION in (
'X - Patient Deceased')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table pz as 
select PROVIDER_LAST_NAME, count(*) as var_pz
from pn  
where FL_FUP_DISPOSITION in (
'Z - Previous Preventative Treatment')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table PE as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.fl_fup_disposition in ('E - Previously Treated for This Infection')
;quit;



proc sql;
create table pee as 
select PROVIDER_LAST_NAME, count(*) as var_pe
from pe  
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

/*proc sql;*/
/*create table PO as */
/* select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,*/
/* fl_fup_disposition*/
/*from STD_HIV_DATAMART a inner join */
/* nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY*/
/*inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY*/
/*inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY*/
/*inner join */
/* nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY*/
/*inner  join nbs_rdb.D_provider */
/*  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY*/
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
/*and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',*/
/*'P3 - Partner, Both') AND a.fl_fup_disposition IS NULL;*/
/*QUIT;*/


proc sql;
create table peo as 
select PROVIDER_LAST_NAME, count(*) as var_po
from pp
where fl_fup_disposition IS NULL
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table CM as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition,  FL_FUP_DISPO_DT, FL_FUP_INIT_ASSGN_DT, datepart(FL_FUP_DISPO_DT)-datepart(FL_FUP_INIT_ASSGN_DT) as days
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('A1 - Associate 1',
'A2 - Associate 2',
'A3 - Associate 3',
'C1- Cohort',
'S1 - Social Contact 1',
'S2 - Social Contact 2',
'S3 - Social Contact 3') AND a.fl_fup_disposition in (
'A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected',
'G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other'
);
quit;


proc sql;
create table CM_dte as 
 select distinct b.D_INTERVIEW_KEY, IX_TYPE, a.INVESTIGATION_KEY, provider_last_name,
 fl_fup_disposition,  FL_FUP_DISPO_DT, FL_FUP_INIT_ASSGN_DT, datepart(FL_FUP_DISPO_DT)-datepart(FL_FUP_INIT_ASSGN_DT) as days
from STD_HIV_DATAMART a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
/*where IX_TYPE in ( 'Re-Interview', 'Initial/Original')  */
and e.CTT_REFERRAL_BASIS in ('A1 - Associate 1',
'A2 - Associate 2',
'A3 - Associate 3',
'C1- Cohort',
'S1 - Social Contact 1',
'S2 - Social Contact 2',
'S3 - Social Contact 3') AND a.fl_fup_disposition in (
'A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected') and datepart(FL_FUP_DISPO_DT) ge datepart(FL_FUP_INIT_ASSGN_DT);
quit;


proc sql;
create table Co as 
select PROVIDER_LAST_NAME, count(*) as var_co
from cluster 
where fl_fup_disposition IS NULL
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cmm as 
select PROVIDER_LAST_NAME, count(*) as var_cm
from cm 
where fl_fup_disposition in (
'A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table ca as 
select PROVIDER_LAST_NAME, count(*) as var_ca
from cm 
where FL_FUP_DISPOSITION in (
'F - Not Infected','A - Preventative Treatment')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cb as 
select PROVIDER_LAST_NAME, count(*) as var_cb
from cm 
where FL_FUP_DISPOSITION in (
'F - Not Infected','B - Refused Preventative Treatment')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cc as 
select PROVIDER_LAST_NAME, count(*) as var_cc
from cm 
where FL_FUP_DISPOSITION in (
'C - Infected, Brought to Treatment')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table cd as 
select PROVIDER_LAST_NAME, count(*) as var_cd
from cm 
where FL_FUP_DISPOSITION in ('D - Infected, Not Treated')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table cf as 
select PROVIDER_LAST_NAME, count(*) as var_cf
from cm 
where FL_FUP_DISPOSITION in ('F - Not Infected')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table cn as 
select PROVIDER_LAST_NAME, count(*) as var_cn
from cm 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cg as 
select PROVIDER_LAST_NAME, count(*) as var_cg
from cm 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table ch as 
select PROVIDER_LAST_NAME, count(*) as var_ch
from cm 
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sql;
create table cj as 
select PROVIDER_LAST_NAME, count(*) as var_cj
from cm 
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table ck as 
select PROVIDER_LAST_NAME, count(*) as var_ck
from cm 
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table cl as 
select PROVIDER_LAST_NAME, count(*) as var_cl
from cm 
where FL_FUP_DISPOSITION in ('L - Other')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;

proc sql;
create table ce as 
select PROVIDER_LAST_NAME, count(*) as var_ce
from cm 
where FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection')
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;



proc freq data = cluster2_dte  ;
by PROVIDER_LAST_NAME;
table Days / nocum missing out = cluster2dte 
(rename =(count=days_count_A)
drop = percent);
where Days le 14;
run;

proc sql; 
create table clust_days_A_03 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_pr
from cluster2dte
where Days le 3
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table clust_days_A_05 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_ps
from cluster2dte
where Days le 5
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table clust_days_A_07 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_pt
from cluster2dte
where Days le 7
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table clust_days_A_14 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_pu
from cluster2dte
where Days le 14
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;


proc sort data = CM_dte;
by PROVIDER_LAST_NAME;
run;

proc freq data = CM_dte  ;
by PROVIDER_LAST_NAME;
table Days / nocum missing out = cmdte 
(rename =(count=days_count_A)
drop = percent);
where  Days le 14;
run;

proc sql; 
create table cm_days_A_03 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_cr
from cmdte
where Days le 3
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table cm_days_A_05 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_cs
from cmdte
where Days le 5
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table cm_days_A_07 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_ct
from cmdte
where Days le 7
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;

proc sql; 
create table cm_days_A_14 as 
select PROVIDER_LAST_NAME , sum(days_count_A) as Var_cu
from cmdte
where Days le 14
group by PROVIDER_LAST_NAME
order by PROVIDER_LAST_NAME;
quit;





data dispo_monster;
merge pm  r pa pb pc pd pf pn_count pg ph pj pk pl pee peo
cmm ca cb cc cd cf cn cg ch cj ck cl ce w cmm pv px pz co
 clust_days_A_03 clust_days_A_05 clust_days_A_07 clust_days_A_14 
cm_days_A_03 cm_days_A_05 cm_days_A_07 cm_days_A_14;
 by PROVIDER_LAST_NAME;
run;



data dispo_monster;
set dispo_monster ;
per_pm= var_pm/var_r;
per_pa = var_pa/var_pm;
per_pb= var_pb/var_pm;
PER_pc = VAR_pc/VAR_pm;
per_pd = var_pd/var_pm;
per_pf= var_pf/var_pm;
PER_pn = VAR_pn/VAR_r;
per_pg = var_pg/var_pn;
per_ph= var_ph/var_pn;
per_pj= var_pj/var_pn;
per_pk= var_pk/var_pn;
per_pl= var_pl/var_pn;
per_pv= var_pv/var_pn;
per_px= var_px/var_pn;
per_pz= var_pz/var_pn;
per_pe= var_pe/var_r;
per_po= var_po/var_r;
per_cm= var_cm/var_w;
per_ca= var_ca/var_cm;
per_cb= var_cb/var_cm;
per_cc= var_cc/var_cm;
per_cd= var_cd/var_cm;
per_cf= var_cf/var_cm;
per_cn= var_cn/var_w;
per_cg= var_cg/var_cn;
per_ch= var_ch/var_cn;
per_cj= var_cj/var_cn;
per_ck= var_ck/var_cn;
per_cl= var_cl/var_cn;
per_ce= var_ce/var_w;
per_co= var_co/var_w;
per_pr= var_pr/var_pm;
per_ps= var_ps/var_pm;
per_pt= var_pt/var_pm;
per_pu= var_pu/var_pm;
per_cr= var_cr/var_cm;
per_cs= var_cs/var_cm;
per_ct= var_ct/var_cm;
per_cu= var_cu/var_cm;
run;


proc sort ;
by provider_last_name;
run;


data hyy;
merge xxxx partner DISPO_MONSTER;
by provider_last_name ;
run;


data ind1_1 (keep = PROVIDER_LAST_NAME colname colval colpct );
set hyy;
length colname $35. ; 
format colname $35. ; 
colname = '-----CASE ASSIGNMENTS &';
colval = .;
colpct = .;
output;
colname = 'CASES ASSIGNED:';
colval = var_a;
output;
colname = 'CASES CLOSED:';
colval = var_b;
colpct = per_b;
output;
colname = "CASES IX'D:";
colval = var_c;
colpct = per_c;
output;
colname = 'WITHIN 3 DAYS:';
colval = var_d;
colpct = per_d;
output;
colname = 'WITHIN 5 DAYS:';
colval = var_e;
colpct = per_e;
output;
colname = 'WITHIN 7 DAYS:';
colval = var_f;
colpct = per_f;
output;
colname = 'WITHIN 14 DAYS:';
colval = var_g;
colpct = per_g;
output;
colname = 'CASES REINTERVIEWED:';
colval = var_h;
colpct = per_h;
output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = '----PARTNERS &';
colval = .;
colpct = .;
output;
colname = 'TOTAL PERIOD PARTNERS:';
colval = var_q;
colpct = per_q;
output;
colname = 'TOTAL PARTNERS INITIATED:';
colval = var_r;
colpct = .;
output;
colname = "FROM OI:";
colval = var_s;
colpct = .;
output;
colname = 'FROM RI:';
colval = var_t;
colpct = .;
output;
colname = 'CONTACT INDEX:';
colval = round(var_u,0.1);
colpct = .;
output;
colname = 'CASES /W NO PARTNERS:';
colval = var_v;
colpct = per_v;
output;
colname = '-----DISPOSITIONS ';
colval = .;
colpct = .;
output;
colname = 'NEW PARTNERS EXAMINED:';
colval = var_PM;
colpct = PER_PM;
output;
colname = 'PREVENTATIVE RX:';
colval = var_PA;
colpct = per_PA;
output;
colname = "REFUSED PREV. RX:";
colval = var_PB;
colpct = per_PB;
output;
colname = "INFECTED, RX'D:";
colval = var_PC;
colpct = per_PC;
output;
colname = 'INFECTED, NO RX:';
colval = var_PD;
colpct = per_PD;
output;
colname = 'NOT INFECTED:';
colval = var_PF;
colpct = per_PF;
output;
colname = 'NEW PARTNERS NO EXAM:';
colval = var_PN;
colpct = per_PN;
output;
colname = 'INSUFFICIENT INFO:';
colval = var_PG;
colpct = per_PG;
output;
colname = 'UNABLE TO LOCATE:';
colval = var_PH;
colpct = per_PH;
output;
colname = 'REFUSED EXAM:';
colval = VAR_PJ;
colpct = PER_PJ;
output;
colname = 'OOJ:';
colval = VAR_PK;
colpct = PER_PK;
output;
colname = 'OTHER:';
colval = VAR_PL;
colpct = PER_PL;
output;
colname = 'PREVIOUS RX:';
colval = VAR_PE;
colpct = PER_PE;
output;
colname = 'OPEN:';
colval = VAR_PO;
colpct = PER_PO;
output;
colname = '-----SPEED OF EXAM- ';
colval = .;
colpct = .;
output;
colname = 'NEW PARTNERS EXAMINED:';
colval = var_PM;
colpct = .;
output;
colname = 'WITHIN 3 DAYS:';
colval = var_PR;
colpct = per_PR;
output;
colname = "WITHIN 5 DAYS:";
colval = var_PS;
colpct = per_PS;
output;
colname = "WITHIN 7 DAYS:";
colval = var_PT;
colpct = per_PT;
output;
colname = 'WITHIN 14 DAYS:';
colval = var_PU;
colpct = per_PU;
output;
run;



proc sort ;
by PROVIDER_LAST_NAME;
run;




data ind1_2 (keep = PROVIDER_LAST_NAME colname2 colval2 colpct2 );
set hyy;
length colname2 $35. ; 
format colname2 $35. ; 
colname2 = 'OUTCOMES-------';
colval = .;
colpct = .;
output;
colname2 = 'HIV PREVIOUS POSITIVE:';
colval2 = var_i;
colpct2 = per_i;
output;
/*colname2 = 'HIV PRETEST COUNSEL:';*/
/*colval2 = .;*/
/*colpct2 = .;*/
/*output;*/
colname2 = 'HIV TESTED:';
colval2 = var_k;
colpct2 = .;
output;
colname2 = 'HIV NEW POSITIVE:';
colval2 = var_l;
colpct2 = per_l;
output;
colname2 = 'HIV POSTTEST COUNSEL:';
colval2 = var_m;
colpct2 = per_m;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = 'DISEASE INTERVENTION INDEX:';
colval2 = round(var_n,0.1);
/*colpct = per_m;*/
output;
colname2 = 'TREATMENT INDEX:';
colval2 = round(var_O,0.1);
/*colpct = per_m;*/
output;
colname2 = 'CASES W/SOURCE IDENTIFIED:';
colval = var_p;
colpct = per_p;
output;
colname2 = ' ';
colval2 = .;
colpct2= .;
output;
colname2 = 'CLUSTERS INITIATED-------';
colval = .;
colpct = .;
output;
colname2 = 'TOTAL CLUSTERS INITIATED:';
colval2 = var_W;
colpct2 = .;
output;
colname2 = 'CLUSTER INDEX:';
colval2 = round(var_X,0.1);
colpct2 = .;
output;
colname2 = "CASES /W NO CLUSTERS:";
colval2 = var_Y;
colpct2 = per_Y;
output;
colname2 = " ";
colval2 = .;
colpct2 = .;
output;
colname2 = " ";
colval2 = .;
colpct2 = .;
output;
colname2 = " ";
colval2 = .;
colpct2 = .;
output;
colname2 = '-NEW PARTNERS & CLUSTERS---- ';
colval2 = .;
colpct2 = .;
output;
colname2 = 'NEW CLUSTERS EXAMINED:';
colval2 = var_CM;
colpct2 = PER_CM;
output;
colname2 = 'PREVENTATIVE RX:';
colval2 = var_CA;
colpct2 = per_CA;
output;
colname2 = "REFUSED PREV. RX:";
colval2 = var_CB;
colpct2 = per_CB;
output;
colname2 = "INFECTED, RX'D:";
colval2 = var_CC;
colpct2 = per_CC;
output;
colname2 = 'INFECTED, NO RX:';
colval2 = var_CD;
colpct2 = per_CD;
output;
colname2 = 'NOT INFECTED:';
colval2 = var_CF;
colpct2 = per_CF;
output;
colname2 = 'NEW CLUSTERS NO EXAM:';
colval2 = var_CN;
colpct2 = per_CN;
output;
colname2 = 'INSUFFICIENT INFO:';
colval2 = var_CG;
colpct2 = per_CG;
output;
colname2 = 'UNABLE TO LOCATE:';
colval2 = var_CH;
colpct2 = per_CH;
output;
colname2 = 'REFUSED EXAM:';
colval2 = VAR_CJ;
colpct2 = PER_CJ;
output;
colname2 = 'OOJ:';
colval2= VAR_CK;
colpct2 = PER_CK;
output;
colname2 = 'OTHER:';
colval2 = VAR_CL;
colpct2 = PER_CL;
output;
colname2 = 'PREVIOUS RX:';
colval = VAR_CE;
colpct2 = PER_CE;
output;
colname2 = 'OPEN:';
colval2 = VAR_CO;
colpct2 = PER_CO;
output;
colname2 = '--NEW PARTNERS & CLUSTERS.---';
colval2 = .;
colpct2 = .;
output;
colname2 = 'NEW CLUSTERS EXAMINED:';
colval2 = var_CM;
colpct2 = .;
output;
colname2 = 'WITHIN 3 DAYS:';
colval2 = var_CR;
colpct2 = per_CR;
output;
colname2 = "WITHIN 5 DAYS:";
colval2 = var_cs;
colpct2 = per_CS;
output;
colname2 = "WITHIN 7 DAYS:";
colval2 = var_CT;
colpct2 = per_CT;
output;
colname2 = 'WITHIN 14 DAYS:';
colval2 = var_CU;
colpct2 = per_CU;
output;
run;


proc sort ;
by PROVIDER_LAST_NAME;
run;

data indie;
merge ind1_1 ind1_2;
by PROVIDER_LAST_NAME;
run;





/*ODS LISTING;*/



/*proc datasets library=work ;*/
/*   copy in=work out=lib;*/
/*      *select bodyfat / memtype=data;*/
/*run;*/

/******PART1 - Overall Worker Report ******************************/



 proc freq data = pa1_dte ;
table Days / nocum missing out = dte_freq_all ;
where  Days le 14;
run;

/**/
/* proc freq data = pa1_new ;*/
/*by PROVIDER_LAST_NAME;*/
/*table Days / nocum missing out = dte_freq_ind ;*/
/*where   Days le 14;*/
/*run;*/


Proc sql noprint;
select count(*) into :Val_A 
from pa1_new where INV_CASE_STATUS in ('Confirmed','Probable');

select count(*) into :Val_B
from pa1_new
where CC_CLOSED_DT is not null;

select count(*) into :Val_C 
from pa1_new  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed');

select sum(count)  into :Val_D
from dte_freq_all
where Days le 3;

select sum(count)  into :Val_E
from dte_freq_all
where Days le 5;

select sum(count) into :Val_F
from dte_freq_all
where Days le 7;

select sum(count) into :Val_G
from dte_freq_all
where Days le 14;

select count(*) into :Val_H 
from pa1_new  
/*where interview_type_cd in ('REINTVW')*/
where IX_TYPE_CD = 'REINTVW';

/****HIV prev positive;*/
select count(*) into :Val_I
from pa1_new 
where  ADI_900_STATUS in ('03','04','05');

/****HIV prev TEST COUNSEL;*/
/*select count(*) into :Val_J*/
/*from pa1_new */
/*where ADI_900_STATUS_CD in ('3 - Prior Positive - Not Previously Known');*/

/***hiv_tested;*/
select count(*) into :Val_K
from pa1_new 
where HIV_900_TEST_IND in ('Yes');


/****HIV New Positive;*/
select count(*) into :Val_L 
from pa1_new  
where HIV_900_result in ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive');

/****HIV Post test counsel;*/
select count(*) into :Val_M 
from pa1_new 
where HIV_POST_TEST_900_COUNSELING in ('Yes');


select count(*) into :Val_N 			/******correct The Disease intervention index*/ 
from pa1_new 
where ADI_900_STATUS_CD in ('1 - Negative');

select count(*) into :Val_O 			/******correct The treat  index*/ 
from pa1_new  
where ADI_900_STATUS_CD in ('6 - Other');


/*CASE OR SOURCE INDEX*/
select count(*) into :Val_P 
from pa1_new 
where ADI_900_STATUS_CD in ('1 - Negative');

select sum(count_Q) INTO :val_q 
from pp  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed');

select count(*) into :Val_R 
from pp  
WHERE IX_TYPE_CD IN ('INITIAL','PRESMPTV','REINTVW');

select count(*) into :Val_S 
from PART1  
WHERE IX_TYPE IN ('Initial/Original');

select count(*) into :Val_T 
from PART1  
WHERE IX_TYPE IN ('Re-Interview');

/***contact index Ask ****/ 
select count(*) into :Val_U			
from PART1  
WHERE IX_TYPE IN ('Re-Interview');

select count(*) into :Val_v 
from PART2  ;

select count(*) into :Val_w 
from cluster; 

/**cluster index..Ask*/
select count(*) into :Val_x 
from cluster; 

select count(*) into :Val_Y
from PART2; 

select  count(*) into :val_pm  
from cluster2  ;

select count(*) into :val_pa 
from cluster2  
where fl_fup_disposition in ('F - Not Infected' ,'A - Preventative Treatment')  ;

select count(*) into : val_pb 
from cluster2  
where fl_fup_disposition in ('F - Not Infected' ,'B - Refused Preventative Treatment');

select count(*) into : val_pc 
from cluster2  
where fl_fup_disposition in ('C - Infected, Brought to Treatment') ;

select  count(*) into : val_pd 
from cluster2  
where fl_fup_disposition in ('D - Infected, Not Treated') ;

 select  count(*) into : val_pf 
from cluster2  
where fl_fup_disposition in ('F - Not Infected');

select  count(*) into : val_pn 
from pn   ;

select count(*) into : val_pg 
from pn  
where FL_FUP_DISPOSITION in (
'G - Insufficient Info to Begin Investigation');

select count(*) into : val_ph 
from pn  
where FL_FUP_DISPOSITION in (
'H - Unable to Locate');

select  count(*) into : val_pj 
from pn  
where FL_FUP_DISPOSITION in (
'J - Located, Not Examined and/or Interviewed');

select count(*) into : val_pk 
from pn  
where FL_FUP_DISPOSITION in (
'K - Sent Out Of Jurisdiction')
;


select count(*) into : val_pl
from pn  
where FL_FUP_DISPOSITION in (
'L - Other')
;


select count(*) into : val_pv
from pn  
where FL_FUP_DISPOSITION in (
'V - Domestic Violence Risk')
;

select  count(*) into : val_px
from pn  
where FL_FUP_DISPOSITION in (
'X - Patient Deceinto :ed')
;

select count(*) into : val_pz
from pn  
where FL_FUP_DISPOSITION in (
'Z - Previous Preventative Treatment')
;
select count(*) into : val_pe
from pe ;

select count(*) into : val_po
from pp
where fl_fup_disposition IS NULL ;

select count(*) into : val_cm
from cm 
where fl_fup_disposition in (
'A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
;


select  count(*) into : val_ca
from cm 
where FL_FUP_DISPOSITION in (
'F - Not Infected','A - Preventative Treatment')
;

select count(*) into : val_cb
from cm 
where FL_FUP_DISPOSITION in (
'F - Not Infected','B - Refused Preventative Treatment')
;


select  count(*) into : val_cc
from cm 
where FL_FUP_DISPOSITION in (
'C - Infected, Brought to Treatment')
;


select count(*) into : val_cd
from cm 
where FL_FUP_DISPOSITION in ('D - Infected, Not Treated')
;

select count(*) into : val_cf
from cm 
where FL_FUP_DISPOSITION in ('F - Not Infected')
;


select count(*) into : val_cn
from cm 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other')
;

select count(*) into : val_cg
from cm 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
;

select count(*) into : val_ch
from cm 
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
;

select count(*) into : val_cj
from cm 
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
;

select count(*) into : val_ck
from cm 
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
;

select count(*) into : val_cl
from cm 
where FL_FUP_DISPOSITION in ('L - Other')
;

select count(*) into : val_ce
from cm 
where FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection')
;

select count(*) into : val_co
from cluster 
where fl_fup_disposition IS NULL
;


select  sum(days_count_A) into : Val_pr
from cluster2dte
where Days le 3
;

select  sum(days_count_A) into : Val_ps
from cluster2dte
where Days le 5
;

select  sum(days_count_A) into : Val_pt
from cluster2dte
where Days le 7
;
 
select  sum(days_count_A) into : Val_pu
from cluster2dte
where Days le 14
;

select sum(days_count_A) into : Val_cr
from cmdte
where Days le 3
;

select  sum(days_count_A) into : Val_cs
from cmdte
where Days le 5
;


select  sum(days_count_A) into : Val_ct
from cmdte
where Days le 7
;


select sum(days_count_A) into : Val_cu
from cmdte
where Days le 14
;
quit;



%let PER_B = %sysevalf(%eval(&Val_B) / %eval(&Val_A)) ;
%let PER_C = %sysevalf(%eval(&Val_C) / %eval(&Val_A)) ;
%let PER_D = %sysevalf(%eval(&Val_D) / %eval(&Val_C)) ;
%let PER_E = %sysevalf(%eval(&Val_E) / %eval(&Val_C)) ;
%let PER_F = %sysevalf(%eval(&Val_F) / %eval(&Val_C)) ;
%let PER_G = %sysevalf(%eval(&Val_G) / %eval(&Val_C)) ;
%let PER_H = %sysevalf(%eval(&Val_H) / %eval(&Val_C)) ;
%let PER_I = %sysevalf(%eval(&Val_I) / %eval(&Val_A)) ;
/*%let PER_J = %sysevalf(%eval(&Val_J) / %eval(&Val_A)) ;	*/
%let PER_K = %sysevalf(%eval(&Val_K) / %eval(&Val_A)) ;	/***Confirm*/
%let PER_L = %sysevalf(%eval(&Val_L) / %eval(&Val_K)) ;
%let PER_M = %sysevalf(%eval(&Val_M) / %eval(&Val_K)) ;
%let PER_P = %sysevalf(%eval(&Val_P) / %eval(&Val_C)) ;
%let PER_Q = %sysevalf(%eval(&Val_Q) / %eval(&Val_C)) ;
%let val_U = %sysevalf(%eval(&Val_R) / %eval(&Val_C)) ;
%let PER_v = %sysevalf(%eval(&Val_v) / %eval(&Val_C)) ;
%let Val_x = %sysevalf(%eval(&Val_w) / %eval(&Val_C)) ;
%let PER_Y = %sysevalf(%eval(&Val_Y) / %eval(&Val_C)) ;
%let PER_PM = %sysevalf(%eval(&Val_PM) / %eval(&Val_R)) ;
%let PER_PA = %sysevalf(%eval(&Val_PA) / %eval(&Val_PM)) ;
%let PER_PB = %sysevalf(%eval(&Val_PB) / %eval(&Val_PM)) ;
%let PER_PC = %sysevalf(%eval(&Val_PC) / %eval(&Val_PM)) ;
%let PER_PD = %sysevalf(%eval(&Val_PD) / %eval(&Val_PM)) ;
%let PER_PF = %sysevalf(%eval(&Val_PF) / %eval(&Val_PM)) ;
%let PER_PN = %sysevalf(%eval(&Val_PN) / %eval(&Val_R)) ;
%let PER_PG = %sysevalf(%eval(&Val_PG) / %eval(&Val_PN)) ;
%let PER_PH = %sysevalf(%eval(&Val_PH) / %eval(&Val_PN)) ;
%let PER_PJ = %sysevalf(%eval(&Val_PJ) / %eval(&Val_PN)) ;
%let PER_PK = %sysevalf(%eval(&Val_PK) / %eval(&Val_PN)) ;
%let PER_PL = %sysevalf(%eval(&Val_PL) / %eval(&Val_PN)) ;
%let PER_PV = %sysevalf(%eval(&Val_PV) / %eval(&Val_PN)) ;
%let PER_PX = %sysevalf(%eval(&Val_PX) / %eval(&Val_PN)) ;
%let PER_PZ = %sysevalf(%eval(&Val_PZ) / %eval(&Val_PN)) ;
%let PER_PE = %sysevalf(%eval(&Val_PE) / %eval(&Val_R)) ;
%let PER_PO = %sysevalf(%eval(&Val_PO) / %eval(&Val_R)) ;

%let PER_CM = %sysevalf(%eval(&Val_CM) / %eval(&Val_W)) ;
%let PER_CA = %sysevalf(%eval(&Val_CA) / %eval(&Val_CM)) ;
%let PER_CB = %sysevalf(%eval(&Val_CB) / %eval(&Val_CM)) ;
%let PER_CC = %sysevalf(%eval(&Val_CC) / %eval(&Val_CM)) ;
%let PER_CD = %sysevalf(%eval(&Val_CD) / %eval(&Val_CM)) ;
%let PER_CF = %sysevalf(%eval(&Val_CF) / %eval(&Val_CM)) ;
%let PER_CN = %sysevalf(%eval(&Val_CN) / %eval(&Val_W)) ;
%let PER_CG = %sysevalf(%eval(&Val_CG) / %eval(&Val_CN)) ;
%let PER_CH = %sysevalf(%eval(&Val_CH) / %eval(&Val_CN)) ;
%let PER_CJ = %sysevalf(%eval(&Val_CJ) / %eval(&Val_CN)) ;
%let PER_CK = %sysevalf(%eval(&Val_CK) / %eval(&Val_CN)) ;
%let PER_CL = %sysevalf(%eval(&Val_CL) / %eval(&Val_CN)) ;

%let PER_CE = %sysevalf(%eval(&Val_CE) / %eval(&Val_W)) ;
%let PER_CO = %sysevalf(%eval(&Val_CO) / %eval(&Val_W)) ;

%let PER_PR = %sysevalf(%eval(&Val_PR) / %eval(&Val_PM)) ;
%let PER_PS = %sysevalf(%eval(&Val_PS) / %eval(&Val_PM)) ;
%let PER_PT = %sysevalf(%eval(&Val_PT) / %eval(&Val_PM)) ;
%let PER_PU = %sysevalf(%eval(&Val_PU) / %eval(&Val_PM)) ;


%let PER_CR = %sysevalf(%eval(&Val_CR) / %eval(&Val_CM)) ;
%let PER_CS = %sysevalf(%eval(&Val_CS) / %eval(&Val_CM)) ;
%let PER_CT = %sysevalf(%eval(&Val_CT) / %eval(&Val_CM)) ;
%let PER_CU = %sysevalf(%eval(&Val_CU) / %eval(&Val_CM)) ;


data _null_;
put "Val_A =  &Val_A";
put "Val_B =  &Val_B";
put "Val_C =  &Val_C";
put "Val_D =  &Val_D";
put "Val_E =  &Val_E";
put "Val_F =  &Val_F";
put "Val_G =  &Val_G";
put "Val_H =  &Val_H";
put "Val_I =  &Val_I";
/*put "Val_J =  &Val_J";*/
put "Val_K =  &Val_K";
put "Val_L =  &Val_L";
put "Val_M =  &Val_M";
put "Val_p =  &Val_p";
put "Val_q =  &Val_q";
put "Val_r =  &Val_r";
put "Val_s =  &Val_s";
put "Val_t =  &Val_t";
put "Val_u =  &Val_u";
put "Val_v =  &Val_v";
put "Val_w =  &Val_w";
put "Val_x =  &Val_x";
put "Val_y =  &Val_y";
put "PER_B =  &PER_B";
put "PER_C =  &PER_C";
put "PER_D =  &PER_D";
put "PER_E =  &PER_E";
put "PER_F =  &PER_F";
put "PER_G =  &PER_H";
put "PER_H =  &PER_H";
put "PER_I =  &PER_I";
put "PER_J =  &PER_J";
put "PER_K =  &PER_K";
put "PER_L=   &PER_L";
put "PER_M =  &PER_M";
put "PER_P =  &PER_P";
put "PER_q =  &PER_q";
put "PER_v =  &PER_v";
put "PER_Y =  &PER_Y";
run;
 
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


/*Create template data for overall summary report*/
options linesize=max;
data template1 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
	CASES ASSIGNED:		! 	!	!		!	!	HIV PREVIOUS POSITIVE:		!	!	!	
	CASES CLOSED:		!   !	!		!	!								!	!	!	
	CASES IX'D:			!   !0	!0.0%	!	!	HIV TESTED:					!	!	!	
	WITHIN 3 DAYS:		!   !0	!0.0%	!	!	HIV NEW POSITIVE:			!	!	!	
	WITHIN 5 DAYS:		!   !0	!0.0%	!	!	HIV POSTTEST COUNSEL:		!	!	!	
	WITHIN 7 DAYS:		!   !0	!0.0%	!	!								!	!	!	
	WITHIN 14 DAYS:		!   !0	!0.0%	!	!	DISEASE INTERVENTION INDEX:	!	!	!	
						!   !	!	!	!		TREATMENT INDEX:			!	!	!	
CASES REINTERVIEWED:	!   !	!	!	!	CASES W/SOURCE IDENTIFIED:		!	!	!	
run;




data template2 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
TOTAL PERIOD PARTNERS:	!   !	!	!	!	TOTAL CLUSTERS INITIATED:	!	!	!	
TOTAL PARTNERS INITIATED:	!   !	!	!	!	CLUSTER INDEX:				!	!	!	
	FROM OI:			!   !	!	!	!	CASES /W NO CLUSTERS:		!	!	!	
	FROM RI:			!   !	!	!	!								!	!	!	
	CONTACT INDEX:		!   !	!	!	!								!	!	!	
	CASES W/NO PARTNERS:!   !	!	!	!								!	!	!	
;
RUN;

data template3 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS EXAMINED:	!   !	!	!	!	NEW CLUSTERS EXAMINED:		!	!	!	
PREVENTATIVE RX:		!   !	!	!	!	PREVENTATIVE RX:			!	!	!	
REFUSED PREV. RX:		!   !	!	!	!	REFUSED PREV. RX:			!	!	!	
INFECTED, RX'D:			!   !	!	!	!	INFECTED, RX'D:				!	!	!	
INFECTED, NO RX:		!   !	!	!	!	INFECTED, NO RX:			!	!	!	
NOT INFECTED:			!   !	!	!	!	NOT INFECTED:				!	!	!	
NEW PARTNERS NO EXAM:	!   !	!	!	!	NEW CLUSTERS NO EXAM:		!	!	!	
INSUFFICIENT INFO:		!   !	!	!	!	INSUFFICIENT INFO:			!	!	!	
UNABLE TO LOCATE:		!   !	!	!	!	UNABLE TO LOCATE:			!	!	!	
REFUSED EXAM:			!   !	!	!	!	REFUSED EXAM:				!	!	!	
OOJ:					!   !	!	!	!	OOJ:						!	!	!	
OTHER:					!   !	!	!	!	OTHER:						!	!	!	
PREVIOUS RX:			!   !	!	!	!	PREVIOUS RX:				!	!	!	
OPEN:					!   !	!	!	!	OPEN:						!	!	!	
;
RUN;

data template4 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS EXAMINED:	!   !	!	!	!	NEW CLUSTERS EXAMINED:			!	!	!	
	WITHIN 3 DAYS:		!   !0	!0.0%	!	!	WITHIN 3 DAYS:				!	!	!	
	WITHIN 5 DAYS:		!   !0	!0.0%	!	!	WITHIN 5 DAYS:				!	!	!	
	WITHIN 7 DAYS:		!   !0	!0.0%	!	!	WITHIN 7 DAYS:				!	!	!	
	WITHIN 14 DAYS:		!   !0	!0.0%	!	!	WITHIN 14 DAYS:				!	!	!	
;
RUN;

/***Insert values into template1 from data for the First */
data template1;
set template1;
if descrip = "CASES ASSIGNED:" then value1 = "&Val_A" ;
if descrip = "CASES CLOSED:" then value1 = "&Val_B" ;
if descrip = "CASES IX'D:" then value1 = "&Val_C" ;
if descrip = "WITHIN 3 DAYS:" then value1 = "&Val_D" ;
if descrip = "WITHIN 5 DAYS:" then value1 = "&Val_E" ;
if descrip = "WITHIN 7 DAYS:" then value1 = "&Val_F" ;
if descrip = "WITHIN 14 DAYS:" then value1 = "&Val_G" ;
if descrip = "CASES REINTERVIEWED:" then value1 = "&Val_H" ;

if descrip2 = "HIV PREVIOUS POSITIVE:" then value2 = "&Val_I" ;
/*if descrip2 = "HIV PRETEST COUNSEL:" then value2 = "&Val_J" ;*/
if descrip2 = "HIV TESTED:" then value2 = "&Val_K" ;
if descrip2 = "HIV NEW POSITIVE:" then value2 = "&Val_L" ;
if descrip2 = "HIV POSTTEST COUNSEL:" then value2 = "&Val_M" ;
if descrip2 = "CASES W/SOURCE IDENTIFIED:" then value2 = "&Val_P" ;

if descrip = "CASES CLOSED:" then percent1 = put(round("&PER_B",0.001),percent8.1)  ;
if descrip = "CASES IX'D:" then percent1 = put(round("&PER_C",0.001),percent8.1)  ;
if descrip = "WITHIN 3 DAYS:" then percent1 = put(round("&PER_D",0.001),percent8.1)  ;
if descrip = "WITHIN 5 DAYS:" then percent1 = put(round("&PER_E",0.001),percent8.1)  ;
if descrip = "WITHIN 7 DAYS:" then percent1 = put(round("&PER_F",0.001),percent8.1)  ;
if descrip = "WITHIN 14 DAYS:" then percent1 = put(round("&PER_G",0.001),percent8.1)  ;
if descrip = "CASES REINTERVIEWED:" then percent1 = put(round("&PER_H",0.001),percent8.1)  ;

if descrip2 = "HIV PREVIOUS POSITIVE:" then percent2 = put(round("&PER_I",0.001),percent8.1)  ;
/*if descrip2 = "HIV PRETEST COUNSEL:" then percent2 = put(round("&PER_J",0.001),percent8.1)  ;*/
if descrip2 = "HIV TESTED:" then percent2 = put(round("&PER_K",0.001),percent8.1)  ;
if descrip2 = "HIV NEW POSITIVE:" then percent2 = put(round("&PER_L",0.001),percent8.1)  ;
if descrip2 = "HIV POSTTEST COUNSEL:" then percent2 = put(round("&PER_M",0.001),percent8.1)  ;
if descrip2 = "CASES W/SOURCE IDENTIFIED:" then percent2 = put(round("&PER_P",0.001),percent8.1)  ;
run;

data template2;
set template2;
if descrip = "TOTAL PERIOD PARTNERS:" then value1 = "&Val_q" ;
if descrip = "TOTAL PARTNERS INITIATED:" then value1 = "&Val_r" ;
if descrip = "FROM OI:" then value1 = "&Val_S" ;
if descrip = "FROM RI:" then value1 = "&Val_T" ;
if descrip = "CONTACT INDEX:" then value1 = ROUND("&Val_U",0.1);
if descrip = "CASES W/NO PARTNERS:" then value1 = "&Val_V" ;

if descrip2 = "TOTAL CLUSTERS INITIATED:" then value2 = "&Val_W" ;
if descrip2 = "CLUSTER INDEX:" then value2 = ROUND("&Val_X",0.1) ;
if descrip2 = "CASES /W NO CLUSTERS:" then value2 = "&Val_Y" ;

if descrip = "TOTAL PERIOD PARTNERS:" then percent1 = put(round("&PER_Q",0.001),percent8.1)  ;
if descrip = "CASES W/NO PARTNERS:" then percent1 = put(round("&PER_V",0.001),percent8.1)  ;

if descrip2 = "CASES /W NO CLUSTERS:" then percent2 = put(round("&PER_Y",0.001),percent8.1)  ;
run;


data template3;
set template3;
if descrip = "NEW PARTNERS EXAMINED:" then value1 = "&Val_pm" ;
if descrip = "PREVENTATIVE RX:" then value1 = "&Val_pa" ;
if descrip = "REFUSED PREV. RX:" then value1 = "&Val_pb" ;
if descrip = "INFECTED, RX'D:" then value1 = "&Val_pc" ;
if descrip = "INFECTED, NO RX:" then value1 = "&Val_pd" ;
if descrip = "NOT INFECTED:" then value1 = "&Val_pf" ;
if descrip = "NEW PARTNERS NO EXAM:" then value1 = "&Val_pn" ;
if descrip = "INSUFFICIENT INFO:" then value1 = "&Val_pg" ;
if descrip = "UNABLE TO LOCATE:" then value1 = "&Val_ph" ;
if descrip = "REFUSED EXAM:" then value1 = "&Val_pj" ;
if descrip = "OOJ:" then value1 = "&Val_pk" ;
if descrip = "OTHER:" then value1 = "&Val_pl" ;
if descrip = "PREVIOUS RX:" then value1 = "&Val_pe" ;
if descrip = "OPEN:" then value1 = "&Val_po" ;



if descrip2 = "NEW CLUSTERS EXAMINED:" then value2 = "&Val_cm" ;
if descrip2 = "PREVENTATIVE RX:" then value2 = "&Val_ca" ;
if descrip2 = "REFUSED PREV. RX:" then value2 = "&Val_cb" ;
if descrip2 = "INFECTED, RX'D:" then value2 = "&Val_cc" ;
if descrip2 = "INFECTED, NO RX:" then value2 = "&Val_cd" ;
if descrip2 = "NOT INFECTED:" then value2 = "&Val_cf" ;
if descrip2 = "NEW CLUSTERS NO EXAM:" then value2 = "&Val_cn" ;
if descrip2 = "INSUFFICIENT INFO:" then value2 = "&Val_cg" ;
if descrip2 = "UNABLE TO LOCATE:" then value2 = "&Val_ch" ;
if descrip2 = "REFUSED EXAM:" then value2 = "&Val_cj" ;
if descrip2 = "OOJ:" then value2 = "&Val_ck" ;
if descrip2 = "OTHER:" then value2 = "&Val_cl" ;
if descrip2 = "PREVIOUS RX:" then value2 = "&Val_ce" ;
if descrip2 = "OPEN:" then value2 = "&Val_co" ;

if descrip = "NEW PARTNERS EXAMINED:" then percent1 = put(round("&PER_pm",0.001),percent8.1)  ;
if descrip = "PREVENTATIVE RX:" then percent1 = put(round("&PER_pa",0.001),percent8.1)  ;
if descrip = "REFUSED PREV. RX:" then percent1 = put(round("&PER_pb",0.001),percent8.1)  ;
if descrip = "INFECTED, RX'D:" then percent1 = put(round("&PER_pc",0.001),percent8.1)  ;
if descrip = "INFECTED, NO RX:" then percent1 = put(round("&PER_pd",0.001),percent8.1)  ;
if descrip = "NOT INFECTED:" then percent1 = put(round("&PER_pf",0.001),percent8.1)  ;
if descrip = "NEW PARTNERS NO EXAM:" then percent1 = put(round("&PER_pn",0.001),percent8.1)  ;
if descrip = "INSUFFICIENT INFO:" then percent1 =put(round("&PER_pg",0.001),percent8.1)  ;
if descrip = "UNABLE TO LOCATE:" then percent1 = put(round("&PER_ph",0.001),percent8.1)  ;
if descrip = "REFUSED EXAM:" then percent1 = put(round("&PER_pj",0.001),percent8.1)  ;
if descrip = "OOJ:" then percent1 = put(round("&PER_pk",0.001),percent8.1)  ;
if descrip = "OTHER:" then percent1 = put(round("&PER_pl",0.001),percent8.1)  ;
if descrip = "PREVIOUS RX:" then percent1 =  put(round("&PER_pe",0.001),percent8.1)  ;
if descrip = "OPEN:" then percent1 = put(round("&PER_po",0.001),percent8.1)  ;



if descrip2 = "NEW CLUSTERS EXAMINED:" then percent2 = put(round("&PER_Cm",0.001),percent8.1)  ;
if descrip2 = "PREVENTATIVE RX:" then percent2 = put(round("&PER_Ca",0.001),percent8.1)  ;
if descrip2 = "REFUSED PREV. RX:" then percent2 = put(round("&PER_Cb",0.001),percent8.1)  ;
if descrip2 = "INFECTED, RX'D:" then percent2 = put(round("&PER_cc",0.001),percent8.1)  ;
if descrip2 = "INFECTED, NO RX:" then percent2 = put(round("&PER_cd",0.001),percent8.1)  ;
if descrip2 = "NOT INFECTED:" then percent2 = put(round("&PER_cf",0.001),percent8.1)  ;
if descrip2 = "NEW CLUSTERS NO EXAM:" then percent2 = put(round("&PER_cn",0.001),percent8.1)  ;
if descrip2 = "INSUFFICIENT INFO:" then percent2 =put(round("&PER_cg",0.001),percent8.1)  ;
if descrip2 = "UNABLE TO LOCATE:" then percent2 = put(round("&PER_ch",0.001),percent8.1)  ;
if descrip2 = "REFUSED EXAM:" then percent2 = put(round("&PER_cj",0.001),percent8.1)  ;
if descrip2 = "OOJ:" then percent2 = put(round("&PER_ck",0.001),percent8.1)  ;
if descrip2 = "OTHER:" then percent2 = put(round("&PER_cl",0.001),percent8.1)  ;
if descrip2 = "PREVIOUS RX:" then percent2 =  put(round("&PER_ce",0.001),percent8.1)  ;
if descrip2 = "OPEN:" then percent2 = put(round("&PER_co",0.001),percent8.1)  ;
run;

data template4;
set template4;
if descrip = "NEW PARTNERS EXAMINED:" then value1 = "&Val_PM" ;
if descrip = "WITHIN 3 DAYS:" then value1 = "&Val_PR" ;
if descrip = "WITHIN 5 DAYS:" then value1 = "&Val_PS" ;
if descrip = "WITHIN 7 DAYS:" then value1 = "&Val_PT" ;
if descrip = "WITHIN 14 DAYS:" then value1 = "&Val_PU" ;


if descrip2 = "NEW CLUSTERS EXAMINED:" then value2 = "&Val_CM" ;
if descrip2 = "WITHIN 3 DAYS:" then value2 = "&Val_CR" ;
if descrip2 = "WITHIN 5 DAYS:" then value2 = "&Val_CS" ;
if descrip2 = "WITHIN 7 DAYS:" then value2 = "&Val_CT" ;
if descrip2 = "WITHIN 14 DAYS:" then value2 = "&Val_CU" ;


if descrip = "WITHIN 3 DAYS:" then percent1 = put(round("&PER_PR",0.001),percent8.1)  ;
if descrip = "WITHIN 5 DAYS:" then percent1 = put(round("&PER_PS",0.001),percent8.1)  ;
if descrip = "WITHIN 7 DAYS:" then percent1 = put(round("&PER_PT",0.001),percent8.1)  ;
if descrip = "WITHIN 14 DAYS:" then percent1 = put(round("&PER_PU",0.001),percent8.1)  ;


if descrip2 = "WITHIN 3 DAYS:" then percent2 = put(round("&PER_CR",0.001),percent8.1)  ;
if descrip2 = "WITHIN 5 DAYS:" then percent2 = put(round("&PER_CS",0.001),percent8.1)  ;
if descrip2 = "WITHIN 7 DAYS:" then percent2 = put(round("&PER_CT",0.001),percent8.1)  ;
if descrip2 = "WITHIN 14 DAYS:" then percent2 = put(round("&PER_CU",0.001),percent8.1)  ;
run;



ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   MISSING = ' ' 
topmargin=0.50 in bottommargin=0.50in leftmargin=0.50in rightmargin=0.50in  nobyline  ;
ods noproctitle;
ods escapechar = "^";
title ;
footnote ;
%let outpath = C:\Users\SameerA\Output;  
ODS pdf FILE =   "&outpath.\ PA01_Case_Management_Rpt_Summary.pdf" style= styles.listing  ;
ods layout start width=8.49in height=10.99in;
ods region x = 0.50 in y = 0.25 in ;
ods text = "^{style[font_face='calibri' fontsize=10pt just=left fontweight=bold] 
				[&TDAY &time_text] CASE MANAGEMENT REPORT-[Disease Category] CDCWKST2}";

ods region x = 0.50 in y = 0.40 in;
ods text = "^{style[font_face='calibri' fontsize=10pt just=center fontweight=bold] 
				WORKER: [Summary of ALL Worker]}";

ods region x = 0.50 in y = 0.60 in ;
ods text = "^{style[font_face='calibri' fontsize=9pt just=Left] 
				---------------------------------CASE ASSIGNMENTS & OUTCOMES-------------------------------------------- }";
ods region x = 0.25 in y = 0.70 in width = 3.00 in;
proc report data = template1 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};

columns  descrip    value1 percent1;
define descrip /display order=internal ' ' left style = [cellwidth=40mm ];
define   value1    / display order=internal ''    style = [cellwidth=10mm ];
define   percent1    / display order=internal ''    style = [cellwidth=15mm ];
 run;

ods region x = 2.50 in y = 0.70 in width = 4.50 in;
proc report data = template1 nowd spanrows ls =200  split='~' headline missing style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};
columns  descrip2    value2  percent2;
define descrip2 /display order=internal ' ' left style = [cellwidth=50mm ];
define   value2    / display order=internal ''    style = [cellwidth=10mm ];
define   percent2   / display order=internal ''    style = [cellwidth=15mm ];
 run;


ods region x = 0.50 in y = 2.90 in ;
ods text = "^{style[font_face='calibri' fontsize=9pt just=Left] 
				---------------------------------PARTNERS & CLUSTERS INITIATED------------------------------------------ }";

ods region x = 0.25 in y = 3.00 in width = 3.00 in;
proc report data = template2 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};

columns  descrip    value1 percent1;
define descrip /display order=internal ' ' left style = [cellwidth=40mm ];
define   value1    / display order=internal ''    style = [cellwidth=10mm ];
define   percent1    / display order=internal ''    style = [cellwidth=15mm ];
 run;

ods region x = 2.55 in y = 3.00 in width = 4.50 in;
proc report data = template2 nowd spanrows ls =200  split='~' headline missing style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};
columns  descrip2    value2  percent2;
define descrip2 /display order=internal ' ' left style = [cellwidth=50mm ];
define   value2    / display order=internal ''    style = [cellwidth=10mm ];
define   percent2   / display order=internal ''    style = [cellwidth=15mm ];
 run;

 
ods region x = 0.50 in y = 4.60in ;
ods text = "^{style[font_face='calibri' fontsize=9pt just=Left] 
				---------------------------------DISPOSITIONS- NEW PARTNERS & CLUSTERS------------------------------}";

ods region x = 0.25 in y = 4.70 in width = 3.00 in;
proc report data = template3 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};

columns  descrip    value1 percent1;
define descrip /display order=internal ' ' left style = [cellwidth=40mm ];
define   value1    / display order=internal ''    style = [cellwidth=10mm ];
define   percent1    / display order=internal ''    style = [cellwidth=15mm ];
 run;

ods region x = 2.55 in y = 4.70 in width = 4.50 in;
proc report data = template3 nowd spanrows ls =200  split='~' headline missing style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};
columns  descrip2    value2  percent2;
define descrip2 /display order=internal ' ' left style = [cellwidth=50mm ];
define   value2    / display order=internal ''    style = [cellwidth=10mm ];
define   percent2   / display order=internal ''    style = [cellwidth=15mm ];
 run;

 

 ods region x = 0.50 in y = 8.10 in ;
ods text = "^{style[font_face='calibri' fontsize=9pt just=Left] 
				---------------------------------SPEED OF EXAM - NEW PARTNERS & CLUSTERS----------------------------}";

ods region x = 0.25 in y = 8.20 in width = 3.00 in;
proc report data = template4 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};

columns  descrip    value1 percent1;
define descrip /display order=internal ' ' left style = [cellwidth=40mm ];
define   value1    / display order=internal ''    style = [cellwidth=10mm ];
define   percent1    / display order=internal ''    style = [cellwidth=15mm ];
 run;

ods region x = 2.55 in y = 8.20 in width = 4.50 in;
proc report data = template4 nowd spanrows ls =200  split='~' headline missing style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt};
columns  descrip2    value2  percent2;
define descrip2 /display order=internal ' ' left style = [cellwidth=50mm ];
define   value2    / display order=internal ''    style = [cellwidth=10mm ];
define   percent2   / display order=internal ''    style = [cellwidth=15mm ];
 run;
ods layout end;
/***********2nd part*******************************/

ods proclabel = "Report 1" ;
title1 "[&TDAY &time_text] CASE MANAGEMENT REPORT-[Disease Category] CDCWKST2}";

TITLE2    j= center " 		WORKER: [Summary of #byval(PROVIDER_LAST_NAME)]	";

proc report data = indie nowd spanrows ls =200  split='~' headline missing style(header)={just=center font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt cellpadding=2pt
cellspacing=1 rules=none
frame=void }
style(column)={font_size=8pt};
by PROVIDER_LAST_NAME ;
columns  PROVIDER_LAST_NAME colname colval colpct colname2 colval2 colpct2;

define PROVIDER_LAST_NAME / group page noprint ;
define colname /DISPLAY "  " ;
define  colval    / DISPLAY " " format = best9. style = [cellwidth=10mm ];
define colpct /DISPLAY " "  format = percent8.1 style = [cellwidth=15mm ];
define colname2 /DISPLAY "  " ;
define  colval2    / DISPLAY " " format = best9. style = [cellwidth=10mm ];
define colpct2 /DISPLAY " "  format = percent8.1  style = [cellwidth=15mm ];
break after PROVIDER_LAST_NAME / page  ; 
compute before _page_ / style = {just = c};
endcomp;
compute colname ;
if colname in ('----PARTNERS &','-----DISPOSITIONS','-----SPEED OF EXAM-') then call define (_row_, 'style',
'style=[  fontsize=8pt just=center fontweight=bold]');
endcomp;
compute colname2 ;
if colname2 in ('OUTCOMES-------','-NEW PARTNERS & CLUSTERS----','--NEW PARTNERS & CLUSTERS.---') then call define (_row_, 'style','style=[  fontsize=8pt just=center fontweight=bold]');
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
/******************************************************END OF PROGRAM**********************************************************************************************************/



