%macro PA01_HIV;



%chk_mv;
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

options missing=0;

proc sql  buffersize=1M;
create table STD_HIV_DATAMART1 as select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') and CA_INTERVIEWER_ASSIGN_DT~=.;
quit;


proc sql;
create table pa1_new as 
select  distinct a.INV_LOCAL_ID, c.IX_TYPE, e.INV_CASE_STATUS, e.record_status_cd, a.CC_CLOSED_DT, 
a.ADI_900_STATUS_CD,   a.HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT, a.ADI_900_STATUS,
 a.HIV_900_TEST_IND, a.source_spread,  a.FL_FUP_INIT_ASSGN_DT,
 e.CURR_PROCESS_STATE , a.CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(c.IX_DATE)-datepart(a.CA_INTERVIEWER_ASSIGN_DT)  as Days, /* Should it be CA_INIT_INTVWR_ASSGN_DT or CA_INTERVIEWER_ASSIGN_DT? */
d.PROVIDER_QUICK_CODE
from STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL' 
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY;
quit;




proc sql;
create table pa1_dte as 
select distinct  a.INV_LOCAL_ID, c.IX_TYPE, e.INV_CASE_STATUS, e.record_status_cd, a.CC_CLOSED_DT,
a.ADI_900_STATUS_CD,  a.HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT,a.ADI_900_STATUS,
 a.HIV_900_TEST_IND, a.source_spread,  a.FL_FUP_INIT_ASSGN_DT,
 e.CURR_PROCESS_STATE , a.CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(c.IX_DATE)-datepart(a.CA_INIT_INTVWR_ASSGN_DT)  as Days,
d.PROVIDER_QUICK_CODE
from STD_HIV_DATAMART1 a inner join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
where datepart(IX_DATE) GE datepart(CA_INIT_INTVWR_ASSGN_DT)
order by INVESTIGATOR_INTERVIEW_KEY;
quit;



/*---------------------total partners initiated----> R*/
proc sql;
create table pp as 
select distinct g.CONTACT_INVESTIGATION_KEY, input(a.STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL,  f.FL_FUP_DISPOSITION, a.INV_LOCAL_ID as STD_INV_LOCAL_ID,
input(a.SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , input(a.SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q, c.IX_TYPE,
D_provider.PROVIDER_QUICK_CODE, d_contact_record.LOCAL_ID as INV_LOCAL_ID , a.CA_PATIENT_INTV_STATUS, a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.F_INTERVIEW_CASE b
  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c
  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
  inner join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE g
on  a.investigation_key =g.SUBJECT_investigation_key
and c.d_interview_key = g.CONTACT_INTERVIEW_KEY and g.CONTACT_INTERVIEW_KEY~=1
inner join nbs_rdb.STD_HIV_DATAMART f on g.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.d_contact_record
on d_contact_record.d_contact_record_key = g.d_contact_record_key and d_contact_record.RECORD_STATUS_CD~='LOG_DEL'
    where d_contact_record.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed');
  quit;

/*      --------------no PARTNERS INITIATED(v)------------*/
 proc sql;
create table part2 as 
select distinct a.inv_local_id, a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,f.CTT_REFERRAL_BASIS,f.CTT_PROCESSING_DECISION,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC,e.CONTACT_INVESTIGATION_KEY
from STD_HIV_DATAMART1 a 
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
 left outer join nbs_rdb.F_CONTACT_RECORD_CASE e on 
  e.SUBJECT_INVESTIGATION_KEY = a.Investigation_key
  left outer join nbs_rdb.d_contact_record f on 
  e.D_contact_record_key = f.d_contact_record_key and f.RECORD_STATUS_CD~='LOG_DEL'
  where f.CTT_REFERRAL_BASIS not in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both')
  and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and a.inv_local_id not in (select distinct STD_INV_LOCAL_ID from pp);
quit;


/*  -----------------------W X CLUSTERS INIT. (OI & RI)::-----------------------*/


proc sql;
create table cluster as 
 select distinct a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,a.inv_local_id as STD_INV_LOCAL_ID,
 f.FL_FUP_DISPOSITION,  e.LOCAL_ID as INV_LOCAL_ID,
a.FL_FUP_DISPO_DT, a.FL_FUP_INIT_ASSGN_DT,
a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
and c.d_interview_key = d.CONTACT_INTERVIEW_KEY and d.CONTACT_INTERVIEW_KEY~=1
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
and  
 e.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3');
quit;


/*******Cases with No clusters - Y To Ask and conform logic  ****/
proc sql;
create table cluster_No as 
select distinct a.inv_local_id, a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,f.CTT_REFERRAL_BASIS,f.CTT_PROCESSING_DECISION,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC,e.CONTACT_INVESTIGATION_KEY
from STD_HIV_DATAMART1 a 
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
 left outer join nbs_rdb.F_CONTACT_RECORD_CASE e on 
  e.SUBJECT_INVESTIGATION_KEY = a.Investigation_key
  left outer join nbs_rdb.d_contact_record f on 
  e.D_contact_record_key = f.d_contact_record_key and f.RECORD_STATUS_CD~='LOG_DEL'
  where f.CTT_REFERRAL_BASIS not in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
	'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') 
  and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and a.inv_local_id not in (select distinct STD_INV_LOCAL_ID from cluster);
quit;


/*To Check Results and Query*/
proc sql;
create table cluster2 as 
 select distinct a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,f.inv_local_id,
 f.FL_FUP_DISPOSITION,  a.FL_FUP_DISPO_DT, a.FL_FUP_INIT_ASSGN_DT, datepart(a.FL_FUP_DISPO_DT)-datepart(a.FL_FUP_INIT_ASSGN_DT) as days,
a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a inner join  nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
 inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.fl_fup_disposition in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected', '1 - Prev. Pos' , '2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test'
) ;quit;




/*  -----------------------pm , pa,--pf PARTNERS AND CLUSTERS:-----------------------*/
proc sql;
create table partner2 as 
 select distinct a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,f.inv_local_id, a.INIT_FUP_INITIAL_FOLL_UP,
 f.FL_FUP_DISPOSITION,  a.FL_FUP_DISPO_DT, a.FL_FUP_INIT_ASSGN_DT, datepart(a.FL_FUP_DISPO_DT)-datepart(a.FL_FUP_INIT_ASSGN_DT) as days,
 a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
 inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY

and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND f.FL_FUP_DISPOSITION in ( '2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test');

quit;

proc sql;
create table partner2_dte as 
 select distinct  a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,f.inv_local_id,
 f.FL_FUP_DISPOSITION,  a.FL_FUP_DISPO_DT, a.FL_FUP_INVESTIGATOR_ASSGN_DT, datepart(f.FL_FUP_DISPO_DT)-datepart(f.FL_FUP_INVESTIGATOR_ASSGN_DT) as days,
 a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY

and e.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND f.FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test') 
and datepart(f.FL_FUP_DISPO_DT) ge datepart(f.FL_FUP_INVESTIGATOR_ASSGN_DT)
order by INVESTIGATOR_INTERVIEW_KEY ;
quit;




proc sql;
create table cluster2_dte as 
select distinct*
from cluster2
where datepart(FL_FUP_DISPO_DT) ge datepart(FL_FUP_INIT_ASSGN_DT)
;quit;





/******PART1 - Individual Worker Report ********/



proc sql;
create table cases_assigned  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_A  
from pa1_new
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit; 
title;


proc sql;
create table cases_closed  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_B 
from pa1_new 
where CC_CLOSED_DT is not null
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;quit;

proc sql;
create table cases_ixd  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_C  
from pa1_new  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit; 
title;


proc freq data = pa1_dte  ;
by INVESTIGATOR_INTERVIEW_KEY;
table Days / nocum missing out = dte_freq_ind_A 
(rename =(count=days_count_A)
drop = percent);
where  IX_TYPE='Initial/Original' and days le 14   ;
run;



proc sql; 
create table days_A_03 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(days_count_A) as Var_D
from dte_freq_ind_A
where Days le 3
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_05 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(days_count_A) as Var_E
from dte_freq_ind_A
where Days le 5
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_07 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(days_count_A) as Var_F
from dte_freq_ind_A
where Days le 7
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;

proc sql; 
create table days_A_14 as 
select INVESTIGATOR_INTERVIEW_KEY , sum(days_count_A) as Var_G
from dte_freq_ind_A
where Days le 14
group by INVESTIGATOR_INTERVIEW_KEY
order by INVESTIGATOR_INTERVIEW_KEY;
quit;



proc sql;/*Oct 6th change made*/
create table cases_reint  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY , count (distinct INV_LOCAL_ID) as Var_H
from pa1_new
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')  and   IX_TYPE = 'Re-Interview'
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit; 
title;



data big;
merge cases_assigned cases_closed cases_ixd
days_A_03 days_A_05 days_A_07 days_A_14 cases_reint;
by INVESTIGATOR_INTERVIEW_KEY;
run;


proc sort data = big;
by INVESTIGATOR_INTERVIEW_KEY;
run;



/****HIV prev positive Incude interview status;*/
proc sql;
create table hiv_prev_pos as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct INV_LOCAL_ID) as Var_I
from pa1_new		/***Change made Oct 6th*/ 
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')  and ADI_900_STATUS_CD in ('03','04','05')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY,PROVIDER_QUICK_CODE;
quit;

/*Incude interview status*/
proc sql;
create table hiv_tested as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_k 
from pa1_new 
where   CA_PATIENT_INTV_STATUS in ('I - Interviewed')  and HIV_900_TEST_IND in ('Yes') 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

/*Incude interview status*/
proc sql;
create table hiv_new as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_l 
from pa1_new  
where  CA_PATIENT_INTV_STATUS in ('I - Interviewed') and  HIV_900_result in ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

/*Incude interview status*/
proc sql;
create table hiv_post_test as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_m 
from pa1_new 
where CA_PATIENT_INTV_STATUS in ('I - Interviewed') and HIV_POST_TEST_900_COUNSELING in ('Yes')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


/*********** Partner Notification Index*****/
proc sql;
create table pix as 
select  distinct 
d.PROVIDER_QUICK_CODE,  A.INVESTIGATOR_INTERVIEW_KEY, count(distinct a.inv_local_id) as var_n 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
left join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY = a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL' 
left outer join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where  g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') and 
f.FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg') 
and a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
group by A.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by A.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;   /***********Testing Index for HIV*****/
create table testindex as 
select  distinct 
d.PROVIDER_QUICK_CODE,  A.INVESTIGATOR_INTERVIEW_KEY, count(distinct g.local_id) as var_o 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
left join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY = a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
left outer join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where  g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') and 
f.FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg') 
and a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
group by A.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by A.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


data hiv;
merge hiv_prev_pos hiv_tested hiv_new hiv_post_test pix
testindex ;
by INVESTIGATOR_INTERVIEW_KEY;
run;

proc sort data = HIV;
by INVESTIGATOR_INTERVIEW_KEY;
run;




DATA monster ;
merge big hiv;
by INVESTIGATOR_INTERVIEW_KEY;
run;


data xxxx;
set monster;
per_b = Var_B/Var_A ; 
per_c = Var_C/Var_A ; 
per_d = var_d/Var_C ; 
per_e = var_e/Var_C ; 
per_f= var_f/Var_C ;
per_g = var_g/Var_C ; 
per_h = var_h/Var_C ;
per_i = var_i/Var_A ; 
per_k= var_k/Var_A ; 
per_l = var_l/var_k ;
per_m = var_m/var_k ;
per_n = var_n/var_C ;
per_o = var_o/var_C ;
run;

proc sort ;
by INVESTIGATOR_INTERVIEW_KEY;
run;



proc sql;
create table q as   
select distinct PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, sum(count_q) as var_q
from pp 
where CA_PATIENT_INTV_STATUS in ('I - Interviewed') 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE  
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE  ;
quit;

proc sql;
create table R as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_R
from PP 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table S as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_S 
from PP  
WHERE IX_TYPE IN ('Initial/Original')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table t as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_t 
from PP  
WHERE IX_TYPE IN ('Re-Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table v as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_v 
from PART2  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table w as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_w
from cluster  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table y as   
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id)  as var_y 
from cluster_no  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

data partner;
merge q r s t v w y cases_ixd;
by INVESTIGATOR_INTERVIEW_KEY;
run;

data partner;
set partner ;
per_q = var_q/Var_C;
var_u= var_r/Var_C;
per_v = var_v/Var_C;
VAR_X = VAR_W/Var_C;
per_y= var_y/Var_C;
run;

proc sort ;
by INVESTIGATOR_INTERVIEW_KEY;
run;




/******Dispositions*************/

proc sql;
create table pm as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY,  count(distinct inv_local_id) as var_pm  
from partner2
where 
fl_fup_disposition in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test'
)  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


/*, '1 - Prev. Pos' , '2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , */
/*'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test'*/
proc sql;
create table p2 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p2 
from partner2  
where fl_fup_disposition in ('2 - Prev. Neg, New Pos')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table p3 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p3 
from partner2  
where fl_fup_disposition in ('3 - Prev. Neg, Still Neg')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table p4 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p4 
from partner2  
where fl_fup_disposition in ('4 - Prev. Neg, No Test')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table p5 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p5
from partner2  
where fl_fup_disposition in ('5 - No Prev Test, New Pos')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table p6 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p6
from partner2  
where fl_fup_disposition in ('6 - No Prev Test, New Neg')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table p7 as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_p7
from partner2  
where fl_fup_disposition in ('7 - No Prev Test, No Test')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


/*  -----------------------pn-PZ  NEW PARTNERS NO EXAM:-----------------------*/
proc sql;
create table pn as 
 select distinct  c.IX_TYPE, a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,
 h.FL_FUP_DISPOSITION , g.CTT_REFERRAL_BASIS, h.inv_local_id, a.INVESTIGATOR_INTERVIEW_KEY
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.F_INTERVIEW_CASE b
  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c
  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
  inner join nbs_rdb.D_provider d
  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.investigation e
  on e.investigation_key =a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE f
on  a.investigation_key =f.SUBJECT_investigation_key
inner join nbs_rdb.STD_HIV_DATAMART h on f.CONTACT_INVESTIGATION_KEY = h.Investigation_key
inner join nbs_rdb.d_contact_record g
on f.d_contact_record_key = g.d_contact_record_key and g.RECORD_STATUS_CD~='LOG_DEL'

where g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex','P2 - Partner, Needle-Sharing',
'P3 - Partner, Both') AND a.CA_PATIENT_INTV_STATUS in ('I - Interviewed') 
and  h.FL_FUP_DISPOSITION in (
'G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',

'V - Domestic Violence Risk',
'X - Patient Deceased')
;quit;


proc sql;
create table pn_count as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_pn 
from pn
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table pg as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_pg 
from pn  
where FL_FUP_DISPOSITION in (
'G - Insufficient Info to Begin Investigation')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table ph as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_ph 
from pn  
where FL_FUP_DISPOSITION in (
'H - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table pj as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_pj 
from pn  
where FL_FUP_DISPOSITION in (
'J - Located, Not Examined and/or Interviewed')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table pk as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_pk 
from pn  
where FL_FUP_DISPOSITION in (
'K - Sent Out Of Jurisdiction')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table pl as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_pl
from pn  
where FL_FUP_DISPOSITION in (
'L - Other')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table pv as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_pv
from pn  
where FL_FUP_DISPOSITION in (
'V - Domestic Violence Risk')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table px as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_px
from pn  
where FL_FUP_DISPOSITION in (
'X - Patient Deceased')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

/*Oct-31-2016 : deleted the PZ and CZ section for the report per requirement doc*/


proc sql;
create table pp1 as 
select   
input(a.STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL, a.fl_fup_disposition,
input(a.SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , input(a.SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q,
D_provider.PROVIDER_QUICK_CODE, 
a.inv_local_id ,
a.CA_PATIENT_INTV_STATUS, a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
  and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed');
  quit;

proc sql;
create table pee as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_p1
from pp1
 where fl_fup_disposition  = 'E - Previously Treated for This Infection'  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;



proc sql;
create table peo as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_po
from pp1
where fl_fup_disposition IS NULL  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table cm as 
 select distinct a.INVESTIGATION_KEY, PROVIDER_QUICK_CODE,
 a.FL_FUP_DISPOSITION,  f.inv_local_id,
f.FL_FUP_DISPO_DT, f.FL_FUP_INVESTIGATOR_ASSGN_DT,
a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC,
datepart(f.FL_FUP_DISPO_DT)-datepart(f.FL_FUP_INVESTIGATOR_ASSGN_DT) as days

from STD_HIV_DATAMART1 a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
and d.contact_interview_key = c.D_INTERVIEW_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
where 
 e.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') 
AND f.fl_fup_disposition in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test');
quit;



proc sql;
create table CM_dte as 
 select distinct *
from cm 
 where datepart(FL_FUP_DISPO_DT) ge datepart(FL_FUP_INVESTIGATOR_ASSGN_DT);
quit;



proc sql;
create table cmm as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_cm
from cm
group by INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table c2 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c2
from cm
where fl_fup_disposition in ('2 - Prev. Neg, New Pos')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table c3 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c3 
from cm  
where fl_fup_disposition in ('3 - Prev. Neg, Still Neg')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table c4 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c4 
from cm  
where fl_fup_disposition in ('4 - Prev. Neg, No Test')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table c5 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c5
from cm  
where fl_fup_disposition in ('5 - No Prev Test, New Pos')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table c6 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c6
from cm  
where fl_fup_disposition in ('6 - No Prev Test, New Neg')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table c7 as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_c7
from cm  
where fl_fup_disposition in ('7 - No Prev Test, No Test')  
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table cn as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_cn
from cluster 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table cg as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_cg
from cluster 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table ch as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_ch
from cluster 
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sql;
create table cj as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_cj
from cluster 
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table ck as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_ck
from cluster 
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table cl as 
select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_cl
from cluster 
where FL_FUP_DISPOSITION in ('L - Other')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table cv as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_cv
from cluster  
where FL_FUP_DISPOSITION in ('V - Domestic Violence Risk')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;

proc sql;
create table cx as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_INTERVIEW_KEY, count(distinct inv_local_id) as var_cx
from cluster  
where FL_FUP_DISPOSITION in ('X - Patient Deceased')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;



proc sql;
create table c1 as 
 select a.INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct f.inv_local_id) as var_c1

from STD_HIV_DATAMART1 a inner join 
 nbs_rdb.F_INTERVIEW_CASE b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c on c.D_INTERVIEW_KEY =b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join 
 nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
inner  join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
where  a.FL_FUP_DISPOSITION in ('1 - Prev. Pos')  and 
 e.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') 
group by a.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by a.INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;





proc sql;
create table Co as 
 select INVESTIGATOR_INTERVIEW_KEY,  PROVIDER_QUICK_CODE, count(distinct inv_local_id) as var_co
from cluster 
where fl_fup_disposition IS NULL 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc freq data = partner2_dte  ;
by INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE ;
table Days / nocum missing out = partner2dte 
(rename =(count=days_count_A)
drop = percent);
where Days le 14;
run;

proc sql; 
create table clust_days_A_03 as 
select INVESTIGATOR_INTERVIEW_KEY , PROVIDER_QUICK_CODE, sum(days_count_A) as Var_pr
from partner2dte
where Days le 3
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table clust_days_A_05 as 
select INVESTIGATOR_INTERVIEW_KEY , PROVIDER_QUICK_CODE,  sum(days_count_A) as Var_ps
from partner2dte
where Days le 5
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table clust_days_A_07 as 
select INVESTIGATOR_INTERVIEW_KEY , PROVIDER_QUICK_CODE,  sum(days_count_A) as Var_pt
from partner2dte
where Days le 7
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table clust_days_A_14 as 
select INVESTIGATOR_INTERVIEW_KEY , PROVIDER_QUICK_CODE,  sum(days_count_A) as Var_pu
from partner2dte
where Days le 14
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


proc sort data = CM_dte;
by INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE;
run;

proc freq data = CM_dte  ;
by INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE;
table Days / nocum missing out = cmdte 
(rename =(count=days_count_A)
drop = percent);
where  Days le 14;
run;

proc sql; 
create table cm_days_A_03 as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, sum(days_count_A) as Var_cr
from cmdte
where Days le 3
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table cm_days_A_05 as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, sum(days_count_A) as Var_cs
from cmdte
where Days le 5
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table cm_days_A_07 as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, sum(days_count_A) as Var_ct
from cmdte
where Days le 7
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;

proc sql; 
create table cm_days_A_14 as 
select INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE, sum(days_count_A) as Var_cu
from cmdte
where Days le 14
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_QUICK_CODE;
quit;


/******PART1 - Overall Worker Report ******************************/

proc freq data = pa1_dte ;
table Days / nocum missing out = dte_freq_all ;
where  Days le 14 and IX_TYPE='Initial/Original';
run;


Proc sql noprint;
select count(distinct inv_local_id) into :Val_A 
from pa1_new;

select count(distinct inv_local_id) into :Val_B
from pa1_new
where CC_CLOSED_DT is not null;

select count(distinct inv_local_id) into :Val_C 
from pa1_new  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed');

select sum(days_count_A)  into :Val_D
from dte_freq_ind_a
where Days le 3;

select sum(days_count_A)  into :Val_E
from dte_freq_ind_a
where Days le 5;

select sum(days_count_A) into :Val_F
from dte_freq_ind_a
where Days le 7;

select sum(days_count_A) into :Val_G
from dte_freq_ind_a
where Days le 14;

select count(distinct INV_LOCAL_ID) into :Val_H 
from pa1_new  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')  and IX_TYPE = 'Re-Interview';

/****HIV prev positive;*/

select count(distinct INV_LOCAL_ID) into :Val_I
from pa1_new 
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')  and ADI_900_STATUS_CD in ('03','04','05');



/***hiv_tested;*/
select sum(var_k) into :Val_K
from hiv_tested 
;


/****HIV New Positive;*/
select count(distinct inv_local_id) into :Val_L 
from pa1_new  
where HIV_900_result in ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive');

/****HIV Post test counsel;*/
select sum(var_m) into :Val_M 
from hiv_post_test 
;

select sum(var_n) into :Val_N
from pix ;


select sum(var_o)  into :Val_O 			 
from testindex  ;



select sum(var_q) INTO :val_q 
from q
; 


select count(distinct inv_local_id) into :Val_R 
from PP;


select count(distinct inv_local_id) into :Val_S 
from PP  
WHERE IX_TYPE IN ('Initial/Original');

select count(distinct inv_local_id) into :Val_T 
from PP  
WHERE IX_TYPE IN ('Re-Interview');



select count(distinct inv_local_id) into :Val_v 
from PART2  ;

select count(distinct inv_local_id) into :Val_w 
from cluster; 



select count(distinct inv_local_id) into :Val_Y
from cluster_no; 


select  count(distinct inv_local_id) into : val_pn 
from pn
;

select count(distinct inv_local_id) into : val_pg 
from pn  
where FL_FUP_DISPOSITION in (
'G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into : val_ph 
from pn  
where FL_FUP_DISPOSITION in (
'H - Unable to Locate');

select  count(distinct inv_local_id) into : val_pj 
from pn  
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into : val_pk 
from pn  
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');


select count(distinct inv_local_id) into : val_pl
from pn  
where FL_FUP_DISPOSITION in ('L - Other')
;

select count(distinct inv_local_id) into : val_pv
from pn  
where FL_FUP_DISPOSITION in ('V - Domestic Violence Risk')
;

select  count(distinct inv_local_id) into : val_px
from pn  
where FL_FUP_DISPOSITION in ('X - Patient Deceased')
;

select  count(distinct inv_local_id) into :val_pm  
from partner2 
where 
fl_fup_disposition in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , 
'6 - No Prev Test, New Neg', '7 - No Prev Test, No Test') ;


select count(distinct inv_local_id) into :val_p2 
from partner2  
where fl_fup_disposition in ('2 - Prev. Neg, New Pos') ;

select count(distinct inv_local_id) into : val_p3 
from partner2  
where fl_fup_disposition in ('3 - Prev. Neg, Still Neg');

select count(distinct inv_local_id) into : val_p4 
from partner2  
where fl_fup_disposition in ('4 - Prev. Neg, No Test')  ;

select  count(distinct inv_local_id) into : val_p5 
from partner2  
where fl_fup_disposition in ('5 - No Prev Test, New Pos')  ;

 select  count(distinct inv_local_id) into : val_p6
from partner2  
where fl_fup_disposition in ('6 - No Prev Test, New Neg')  ;

select  count(distinct inv_local_id) into : val_p7
from partner2  
where fl_fup_disposition in ('7 - No Prev Test, No Test') ;



select count(distinct inv_local_id) into : val_p1
from pp1
where fl_fup_disposition  = 'E - Previously Treated for This Infection'  
;

select count(distinct inv_local_id) into : val_po
from pp1
where fl_fup_disposition IS NULL  
 ;

select count(*) into : val_cm
from cm 
;


select  count(distinct inv_local_id) into : val_c2
from cm 
where fl_fup_disposition in ('2 - Prev. Neg, New Pos')
;

select count(distinct inv_local_id) into : val_c3
from cm  
where fl_fup_disposition in ('3 - Prev. Neg, Still Neg')
;


select  count(distinct inv_local_id) into : val_c4
from cm  
where fl_fup_disposition in ('4 - Prev. Neg, No Test')  
;


select count(distinct inv_local_id) into : val_c5
from cm  
where fl_fup_disposition in ('5 - No Prev Test, New Pos')  
;

select count(distinct inv_local_id) into : val_c6
from cm  
where fl_fup_disposition in ('6 - No Prev Test, New Neg')  
;

select count(distinct inv_local_id) into : val_c7
from cm  
where fl_fup_disposition in ('7 - No Prev Test, No Test') ; 


select count(distinct inv_local_id) into : val_cn
from cluster 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other' ,
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment')
;

select count(distinct inv_local_id) into : val_cg
from cluster 
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
;

select count(distinct inv_local_id) into : val_ch
from cluster 
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
;

select count(distinct inv_local_id) into : val_cj
from cluster 
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
;

select count(distinct inv_local_id) into : val_ck
from cluster 
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
;

select count(distinct inv_local_id) into :val_cl
from cluster 
where FL_FUP_DISPOSITION in ('L - Other')
;

select count(distinct inv_local_id) into : val_cv
from cluster 
where FL_FUP_DISPOSITION in ('V - Domestic Violence Risk')
;

select  count(distinct inv_local_id) into : val_cx
from cluster  
where FL_FUP_DISPOSITION in ('X - Patient Deceased')
;

select sum(var_c1) into :val_c1
from c1  
;

select sum(var_co) into :val_co
from Co 
;


select  sum(days_count_A) into :Val_pr
from partner2dte
where Days le 3
;

select  sum(days_count_A) into :Val_ps
from partner2dte
where Days le 5
;

select  sum(days_count_A) into :Val_pt
from partner2dte
where Days le 7
;
 
select  sum(days_count_A) into :Val_pu
from partner2dte
where Days le 14
;

select sum(days_count_A) into :Val_cr
from cmdte
where Days le 3
;

select  sum(days_count_A) into :Val_cs
from cmdte
where Days le 5
;


select  sum(days_count_A) into :Val_ct
from cmdte
where Days le 7
;


select sum(days_count_A) into :Val_cu
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

%let PER_K = %sysevalf(%eval(&Val_K) / %eval(&Val_A)) ;
%let PER_L = %sysevalf(%eval(&Val_L) / %eval(&Val_K)) ;
%let PER_M = %sysevalf(%eval(&Val_M) / %eval(&Val_K)) ;

%let PER_N = %sysevalf(%eval(&Val_N) / %eval(&Val_C)) ;
%let PER_O = %sysevalf(%eval(&Val_O) / %eval(&Val_C)) ;

%let PER_Q = %sysevalf(%eval(&Val_Q) / %eval(&Val_C)) ;

%let val_U = %sysevalf(%eval(&Val_R) / %eval(&Val_C)) ;

%let PER_v = %sysevalf(%eval(&Val_v) / %eval(&Val_C)) ;

%let Val_x = %sysevalf(%eval(&Val_w) / %eval(&Val_C)) ;

%let PER_Y = %sysevalf(%eval(&Val_Y) / %eval(&Val_C)) ;

%let PER_PM = %sysevalf(%eval(&Val_PM) / %eval(&Val_R)) ;
%let PER_P2 = %sysevalf(%eval(&Val_P2) / %eval(&Val_PM)) ;
%let PER_P3 = %sysevalf(%eval(&Val_P3) / %eval(&Val_PM)) ;
%let PER_P4= %sysevalf(%eval(&Val_P4) / %eval(&Val_PM)) ;
%let PER_P5 = %sysevalf(%eval(&Val_P5) / %eval(&Val_PM)) ;
%let PER_P6 = %sysevalf(%eval(&Val_P6) / %eval(&Val_PM)) ;
%let PER_P7 = %sysevalf(%eval(&Val_P7) / %eval(&Val_PM)) ;
%let PER_PN = %sysevalf(%eval(&Val_PN) / %eval(&Val_R)) ;

%let PER_PG = %sysevalf(%eval(&Val_PG) / %eval(&Val_PN)) ;
%let PER_PH = %sysevalf(%eval(&Val_PH) / %eval(&Val_PN)) ;
%let PER_PJ = %sysevalf(%eval(&Val_PJ) / %eval(&Val_PN)) ;
%let PER_PK = %sysevalf(%eval(&Val_PK) / %eval(&Val_PN)) ;
%let PER_PL = %sysevalf(%eval(&Val_PL) / %eval(&Val_PN)) ;
%let PER_PV = %sysevalf(%eval(&Val_PV) / %eval(&Val_PN)) ;
%let PER_PX = %sysevalf(%eval(&Val_PX) / %eval(&Val_PN)) ;

%let PER_P1 = %sysevalf(%eval(&Val_P1) / %eval(&Val_R)) ;
%let PER_PO = %sysevalf(%eval(&Val_PO) / %eval(&Val_R)) ;

%let PER_CM = %sysevalf(%eval(&Val_CM) / %eval(&Val_W)) ;
%let PER_C2 = %sysevalf(%eval(&Val_C2) / %eval(&Val_CM)) ;
%let PER_C3 = %sysevalf(%eval(&Val_C3) / %eval(&Val_CM)) ;
%let PER_C4= %sysevalf(%eval(&Val_C4) / %eval(&Val_CM)) ;
%let PER_C5 = %sysevalf(%eval(&Val_C5) / %eval(&Val_CM)) ;
%let PER_C6 = %sysevalf(%eval(&Val_C6) / %eval(&Val_CM)) ;
%let PER_C7 = %sysevalf(%eval(&Val_C7) / %eval(&Val_CM)) ;
%let PER_CN = %sysevalf(%eval(&Val_CN) / %eval(&Val_W)) ;

%let PER_CG = %sysevalf(%eval(&Val_CG) / %eval(&Val_CN)) ;
%let PER_CH = %sysevalf(%eval(&Val_CH) / %eval(&Val_CN)) ;
%let PER_CJ = %sysevalf(%eval(&Val_CJ) / %eval(&Val_CN)) ;
%let PER_CK = %sysevalf(%eval(&Val_CK) / %eval(&Val_CN)) ;
%let PER_CL = %sysevalf(%eval(&Val_CL) / %eval(&Val_CN)) ;
%let PER_CV = %sysevalf(%eval(&Val_CV) / %eval(&Val_CN)) ;
%let PER_CX = %sysevalf(%eval(&Val_CX) / %eval(&Val_CN)) ;

%let PER_C1 = %sysevalf(%eval(&Val_C1) / %eval(&Val_W)) ;
%let PER_CO = %sysevalf(%eval(&Val_CO) / %eval(&Val_W)) ;

%let PER_PR = %sysevalf(%eval(&Val_PR) / %eval(&Val_PM)) ;
%let PER_PS = %sysevalf(%eval(&Val_PS) / %eval(&Val_PM)) ;
%let PER_PT = %sysevalf(%eval(&Val_PT) / %eval(&Val_PM)) ;
%let PER_PU = %sysevalf(%eval(&Val_PU) / %eval(&Val_PM)) ;


%let PER_CR = %sysevalf(%eval(&Val_CR) / %eval(&Val_CM)) ;
%let PER_CS = %sysevalf(%eval(&Val_CS) / %eval(&Val_CM)) ;
%let PER_CT = %sysevalf(%eval(&Val_CT) / %eval(&Val_CM)) ;
%let PER_CU = %sysevalf(%eval(&Val_CU) / %eval(&Val_CM)) ;


data dispo_monster;
merge pm  r p2 p3 p4 p5 p6 p7 pn_count pg ph pj pk pl pee peo
pv px  cv cx 
cmm c2 c3 c4 c5 c6 c7 cg ch cj ck cl cn c1   co w
 clust_days_A_03 clust_days_A_05 clust_days_A_07 clust_days_A_14 
cm_days_A_03 cm_days_A_05 cm_days_A_07 cm_days_A_14;
 by INVESTIGATOR_INTERVIEW_KEY;
run;



data dispo_monster;
set dispo_monster ;
per_pm= var_pm/var_r;

per_p2 = var_p2/var_pm;
per_p3= var_p3/var_pm;
PER_p4 = VAR_p4/VAR_pm;
per_p5 = var_p5/var_pm;
per_p6= var_p6/var_pm;
per_p7= var_p7/var_pm;
PER_pn = VAR_pn/VAR_r;

per_pg = var_pg/var_pn;
per_ph= var_ph/var_pn;
per_pj= var_pj/var_pn;
per_pk= var_pk/var_pn;
per_pl= var_pl/var_pn;
per_pv= var_pv/var_pn;
per_px= var_px/var_pn;

per_p1= var_p1/var_r;
per_po= var_po/var_r;

per_cm= var_cm/var_w;

per_c2= var_c2/var_cm;
per_c3= var_c3/var_cm;
per_c4= var_c4/var_cm;
per_c5= var_c5/var_cm;
per_c6= var_c6/var_cm;
per_c7= var_c7/var_cm;
per_cn= var_cn/var_w;

per_cg= var_cg/var_cn;
per_ch= var_ch/var_cn;
per_cj= var_cj/var_cn;
per_ck= var_ck/var_cn;
per_cl= var_cl/var_cn;
per_Cv= var_Cv/var_Cn;
per_Cx= var_Cx/var_Cn;

per_c1= var_c1/var_w;
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
by INVESTIGATOR_INTERVIEW_KEY;
run;


data hyy;
merge xxxx partner DISPO_MONSTER;
by INVESTIGATOR_INTERVIEW_KEY ;
run;

data hyy;
   set hyy;
   array change _numeric_;
        do over change;
            if change=. then change=0;
        end;
 run ;


data ind1_1 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE colgroup colname colval colpct );
set hyy;
length colname $35. colgroup $45.; 
format colname $35. colgroup $45.; 
*colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
*colname = '-----CASE ASSIGNMENTS &';
*colval = .;
*colpct = .;
*output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'CASES ASSIGNED:';
colval = var_a;
colpct = .;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'CASES CLOSED:';
colval = var_b;
colpct = per_b;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = "CASES IX'D:";
colval = var_c;
colpct = per_c;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'WITHIN 3 DAYS:';
colval = var_d;
colpct = per_d;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'WITHIN 5 DAYS:';
colval = var_e;
colpct = per_e;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'WITHIN 7 DAYS:';
colval = var_f;
colpct = per_f;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'WITHIN 14 DAYS:';
colval = var_g;
colpct = per_g;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = ' ';
colval = .;
colpct = .;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = 'CASES REINTERVIEWED:';
colval = var_h;
colpct = per_h;
output;
colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
colname = ' ';
colval = .;
colpct = .;
output;
*colgroup = 'PARTNERS & CLUSTERS INITIATED';
*colname = '----PARTNERS &';
*colval = .;
*colpct = .;
*output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'TOTAL PERIOD PARTNERS:';
colval = var_q;
colpct = per_q;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'TOTAL PARTNERS INITIATED:';
colval = var_r;
colpct = .;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'FROM OI:';
colval = var_s;
colpct = .;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'FROM RI:';
colval = var_t;
colpct = .;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'CONTACT INDEX:';
colval = round(var_u,0.1);
colpct = .;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = 'CASES /W NO PARTNERS:';
colval = var_v;
colpct = per_v;
output;
colgroup = 'PARTNERS & CLUSTERS INITIATED';
colname = ' ';
colval = .;
colpct = .;
output;
*colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
*colname = '-----DISPOSITIONS ';
*colval = .;
*colpct = .;
*output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'NEW PARTNERS NOTIFIED:';
colval = var_PM;
colpct = PER_PM;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'PREV. NEG, NEW POS:';
colval = var_P2;
colpct = per_P2;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'PREV NEG, STILL NEG:';
colval = var_P3;
colpct = per_P3;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'PREV NEG, NO TEST:';
colval = var_P4;
colpct = per_P4;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'NO PREV. TEST, NEW POS:';
colval = var_P5;
colpct = per_P5;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'NO PREV. TEST, NEW NEG:';
colval = var_P6;
colpct = per_P6;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'NO PREV. TEST, NO TEST:';
colval = var_P7;
colpct = per_P7;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'NEW PARTNERS NOT NOTIFIED:';
colval = var_PN;
colpct = per_PN;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'INSUFFICIENT INFO:';
colval = var_PG;
colpct = per_PG;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'UNABLE TO LOCATE:';
colval = var_PH;
colpct = per_PH;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'REFUSED EXAM:';
colval = VAR_PJ;
colpct = PER_PJ;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'OOJ:';
colval = VAR_PK;
colpct = PER_PK;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'OTHER:';
colval = VAR_PL;
colpct = PER_PL;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'DOMESTIC VIOLENCE RISK:';
colval = VAR_PV;
colpct = PER_PV;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'PATIENT DECEASED:';
colval = VAR_PX;
colpct = PER_PX;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'PREVIOUS POS:';
colval = VAR_P1;
colpct = PER_P1;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = 'OPEN:';
colval = VAR_PO;
colpct = PER_PO;
output;
colgroup = 'DISPOSITIONS - PARTNERS & CLUSTERS';
colname = ' ';
colval = .;
colpct = .;
output;
*colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
*colname = '-----SPEED OF NOTIFICATION-- ';
*colval = .;
*colpct = .;
*output;
colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
colname = 'NEW PARTNERS NOTIFIED:';
colval = var_PM;
colpct = .;
output;
colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
colname = 'WITHIN 3 DAYS:';
colval = var_PR;
colpct = per_PR;
output;
colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
colname = 'WITHIN 5 DAYS:';
colval = var_PS;
colpct = per_PS;
output;
colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
colname = 'WITHIN 7 DAYS:';
colval = var_PT;
colpct = per_PT;
output;
colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
colname = 'WITHIN 14 DAYS:';
colval = var_PU;
colpct = per_PU;
output;

run;



proc sort ;
by INVESTIGATOR_INTERVIEW_KEY;
run;




data ind1_2 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_QUICK_CODE colname2 colval2 colpct2 );
set hyy;
length colname2 $35. ; 
format colname2 $35. ; 
*colname2 = 'OUTCOMES-------';
*colval = .;
*colpct = .;
*output;
colname2 = 'HIV PREVIOUS POSITIVE:';
colval2 = var_i;
colpct2 = per_i;
output;

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
colname2 = 'PARTNER NOTIFICATION INDEX:';
colval2 = round(PER_N,0.01);
colpct2 = .;
output;
colname2 = 'TESTING INDEX:';
colval2 = round(per_O,0.01);
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
*colname2 = 'CLUSTERS INITIATED-------';
*colval = .;
*colpct = .;
*output;
colname2 = 'TOTAL CLUSTERS INITIATED:';
colval2 = var_W;
colpct2 = .;
output;
colname2 = 'CLUSTER INDEX:';
colval2 = round(var_X,0.01);
colpct2 = .;
output;
colname2 = 'CASES /W NO CLUSTERS:';
colval2 = var_Y;
colpct2 = per_Y;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
*colname2 = '-PARTNERS & CLUSTERS---- ';
*colval2 = .;
*colpct2 = .;
*output;
colname2 = 'NEW CLUSTERS NOTIFIED:';
colval2 = var_CM;
colpct2 = PER_CM;
output;
colname2 = 'PREV NEG, NEW POS:';
colval2 = var_C2;
colpct2 = per_C2;
output;
colname2 = 'PREV NEG, STILL NEG:';
colval2 = var_C3;
colpct2 = per_C3;
output;
colname2 = 'PREV NEG, NO TEST:';
colval2 = var_C4;
colpct2 = per_C4;
output;
colname2 = 'NO PREV. TEST, NEW POS:';
colval2 = var_C5;
colpct2 = per_C5;
output;
colname2 = 'NO PREV. TEST, NEW NEG:';
colval2 = var_C6;
colpct2 = per_C6;
output;
colname2 = 'NO PREV. TEST, NO TEST:';
colval2 = var_C7;
colpct2 = per_C7;
output;
colname2 = 'NEW CLUSTERS NOT NOTIFIED:';
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
colval2 = VAR_CK;
colpct2 = PER_CK;
output;
colname2 = 'OTHER:';
colval2 = VAR_CL;
colpct2 = PER_CL;
output;
colname2 = 'DOMESTIC VIOLENCE RISK:';
colval2 = VAR_CV;
colpct2 = PER_CV;
output;
colname2 = 'PATIENT DECEASED:';
colval2 = VAR_CX;
colpct2 = PER_CX;
output;
colname2 = 'PREVIOUS POS:';
colval2 = VAR_C1;
colpct2 = PER_C1;
output;
colname2 = 'OPEN:';
colval2 = VAR_CO;
colpct2 = PER_CO;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
*colname2 = '--PARTNERS & CLUSTERS.---';
*colval2 = .;
*colpct2 = .;
*output;
colname2 = 'NEW CLUSTERS NOTIFIED:';
colval2 = var_CM;
colpct2 = .;
output;
colname2 = 'WITHIN 3 DAYS:';
colval2 = var_CR;
colpct2 = per_CR;
output;
colname2 = 'WITHIN 5 DAYS:';
colval2 = var_cs;
colpct2 = per_CS;
output;
colname2 = 'WITHIN 7 DAYS:';
colval2 = var_CT;
colpct2 = per_CT;
output;
colname2 = 'WITHIN 14 DAYS:';
colval2 = var_CU;
colpct2 = per_CU;
output;
run;


proc sort ;
by INVESTIGATOR_INTERVIEW_KEY;
run;

data indie;
merge ind1_1 ind1_2;
by INVESTIGATOR_INTERVIEW_KEY;
run;


proc sort ;
by PROVIDER_QUICK_CODE;
run;


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
put "Val_K =  &Val_K";
put "Val_L =  &Val_L";
put "Val_M =  &Val_M";
put "PER_n =  &PER_n";
put "PER_o =  &PER_o";
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
put "PER_K =  &PER_K";
put "PER_L=   &PER_L";
put "PER_M =  &PER_M";
put "PER_P =  &PER_P";
put "PER_q =  &PER_q";
put "PER_v =  &PER_v";
put "PER_Y =  &PER_Y";
put "PER_PG =  &PER_PG";
put "PER_PH =  &PER_PH";
put "PER_CG =  &PER_CG";
put "PER_CH =  &PER_CH";
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

filename NBSPGMp1  "&SAS_REPORT_HOME/template";


%include NBSPGMp1 (Template5.sas);

/*Create template data for overall summary report*/
/***Insert values into template1 from data for the First */
data template1;
set template1;
if find(descrip,'CASES ASSIGNED:','i') ge 1 then value1 = "&Val_A" ;
if find(descrip,'CASES CLOSED:','i') ge 1 then value1 = "&Val_B" ;
if find(descrip,'CASES IX''D:','i') ge 1 then value1 = "&Val_C" ;
if find(descrip,'WITHIN 3 DAYS:','i') ge 1 then value1 = "&Val_D" ;
if find(descrip,'WITHIN 5 DAYS:','i') ge 1 then value1 = "&Val_E" ;
if find(descrip,'WITHIN 7 DAYS:','i') ge 1 then value1 = "&Val_F" ;
if find(descrip,'WITHIN 14 DAYS:','i') ge 1 then value1 = "&Val_G" ;
if find(descrip,'CASES REINTERVIEWED:','i') ge 1 then value1 = "&Val_H" ;

if find(descrip2,'HIV PREVIOUS POSITIVE:','i') ge 1 then value2 = "&Val_I" ;
if find(descrip2,'HIV TESTED:','i') ge 1 then value2 = "&Val_K" ;
if find(descrip2,'HIV NEW POSITIVE:','i') ge 1 then value2 = "&Val_L" ;
if find(descrip2,'HIV POSTTEST COUNSEL:','i') ge 1 then value2 = "&Val_M" ;
if find(descrip2,'PARTNER NOTIFICATION INDEX','i') ge 1 then value2 = round("&PER_N",0.01) ;
if find(descrip2,'TESTING INDEX:','i') ge 1 then value2 = round("&PER_O",0.01) ;

if find(descrip,'CASES CLOSED:','i') ge 1 then percent1 = put(round("&PER_B",0.001),percent8.1)  ;
if find(descrip,'CASES IX''D:','i') ge 1 then percent1 = put(round("&PER_C",0.001),percent8.1)  ;
if find(descrip,'WITHIN 3 DAYS:','i') ge 1 then percent1 = put(round("&PER_D",0.001),percent8.1)  ;
if find(descrip,'WITHIN 5 DAYS:','i') ge 1 then percent1 = put(round("&PER_E",0.001),percent8.1)  ;
if find(descrip,'WITHIN 7 DAYS:','i') ge 1 then percent1 = put(round("&PER_F",0.001),percent8.1)  ;
if find(descrip,'WITHIN 14 DAYS:','i') ge 1 then percent1 = put(round("&PER_G",0.001),percent8.1)  ;
if find(descrip,'CASES REINTERVIEWED:','i') ge 1 then percent1 = put(round("&PER_H",0.001),percent8.1)  ;

if find(descrip2,'HIV PREVIOUS POSITIVE:','i') ge 1 then percent2 = put(round("&PER_I",0.001),percent8.1)  ;
if find(descrip2,'HIV TESTED:','i') ge 1 then percent2 = put(round("&PER_K",0.001),percent8.1)  ;
if find(descrip2,'HIV NEW POSITIVE:','i') ge 1 then percent2 = put(round("&PER_L",0.001),percent8.1)  ;
if find(descrip2,'HIV POSTTEST COUNSEL:','i') ge 1 then percent2 = put(round("&PER_M",0.001),percent8.1)  ;
run;

data template2;
set template2;
if find(descrip,'TOTAL PERIOD PARTNERS:','i') ge 1 	then value1 = "&Val_q" ;
if find(descrip,'TOTAL PARTNERS INITIATED:','i') ge 1 	then value1 = "&Val_r" ;
if find(descrip,'FROM OI:','i') ge 1 		then value1 = "&Val_S" ;
if find(descrip,'FROM RI:','i') ge 1 		then value1 = "&Val_T" ;
if find(descrip,'CONTACT INDEX:','i') ge 1 		then value1 = round("&Val_U",0.01);
if find(descrip,'CASES W/NO PARTNERS:','i') ge 1 		then value1 = "&Val_V" ;

if find(descrip2,'TOTAL CLUSTERS INITIATED:','i') ge 1 then value2 = "&Val_W" ;
if find(descrip2,'CLUSTER INDEX:','i') ge 1 then value2 = ROUND("&Val_X",0.01) ;
if find(descrip2,'CASES W/NO CLUSTERS:','i') ge 1 then value2 = "&Val_Y" ;

if find(descrip,'TOTAL PERIOD PARTNERS:','i') ge 1 then percent1 = round("&PER_Q",0.1)  ;
if find(descrip,'CASES W/NO PARTNERS:','i') ge 1 then percent1 = put(round("&PER_V",0.001),percent8.1)  ;
if find(descrip2,'CASES W/NO CLUSTERS:','i') ge 1 then percent2 = put(round("&PER_Y",0.001),percent8.1)  ;
run;


data template3;
set template3;
if find(descrip,'NEW PARTNERS NOTIFIED:','i') ge 1 		then value1 = "&Val_pm" ;
if find(descrip,'PREV. NEG, NEW POS:','i') ge 1 		then value1 = "&Val_p2" ;
if find(descrip,'PREV. NEG, STILL NEG:','i') ge 1 		then value1 = "&Val_p3" ;
if find(descrip,'PREV. NEG, NO TEST:','i') ge 1 		then value1 = "&Val_p4" ;
if find(descrip,'NO PREV. TEST, NEW POS:','i') ge 1 		then value1 = "&Val_p5" ;
if find(descrip,'NO PREV. TEST, NEW NEG:','i') ge 1 		then value1 = "&Val_p6" ;
if find(descrip,'NO PREV TEST, NO TEST:','i') ge 1 		then value1 = "&Val_p7" ;
if find(descrip,'NEW PARTNERS NOT NOTIFIED:','i') ge 1 		then value1 = "&Val_pn" ;
if find(descrip,'INSUFFICIENT INFO:','i') ge 1 		then value1 = "&Val_pg" ;
if find(descrip,'UNABLE TO LOCATE:','i') ge 1 		then value1 = "&Val_ph" ;
if find(descrip,'REFUSED EXAM:','i') ge 1 		then value1 = "&Val_pj" ;
if find(descrip,'OOJ:','i') ge 1 		then value1 = "&Val_pk" ;
if find(descrip,'OTHER:','i') ge 1 		then value1 = "&Val_pl" ;
if find(descrip,'DOMESTIC VIOLENCE RISK:','i') ge 1 	then value1 = "&Val_pv" ;
if find(descrip,'PATIENT DECEASED:','i') ge 1 	then value1 = "&Val_px" ;
if find(descrip,'PREVIOUS POS:','i') ge 1 		then value1 = "&Val_p1" ;
if find(descrip,'OPEN:','i') ge 1 		then value1 = "&Val_po" ;



if find(descrip2,'NEW CLUSTERS NOTIFIED:','i') ge 1 		then value2 = "&Val_cm" ;
if find(descrip2,'PREV. NEG, NEW POS:','i') ge 1 		then value2 = "&Val_c2" ;
if find(descrip2,'PREV. NEG, STILL NEG:','i') ge 1 		then value2 = "&Val_c3" ;
if find(descrip2,'PREV. NEG, NO TEST:','i') ge 1 		then value2 = "&Val_c4" ;
if find(descrip2,'NO PREV. TEST, NEW POS:','i') ge 1 		then value2 = "&Val_c5" ;
if find(descrip2,'NO PREV. TEST, NEW NEG:','i') ge 1 		then value2 = "&Val_c6" ;
if find(descrip2,'NO PREV TEST, NO TEST:','i') ge 1 		then value2 = "&Val_c7" ;
if find(descrip2,'NEW CLUSTERS NOT NOTIFIED:','i') ge 1 		then value2 = "&Val_cn" ;
if find(descrip2,'INSUFFICIENT INFO:','i') ge 1 		then value2 = "&Val_cg" ;
if find(descrip2,'UNABLE TO LOCATE:','i') ge 1 		then value2 = "&Val_ch" ;
if find(descrip2,'REFUSED EXAM:','i') ge 1 		then value2 = "&Val_cj" ;
if find(descrip2,'OOJ:','i') ge 1 		then value2 = "&Val_ck" ;
if find(descrip2,'OTHER:','i') ge 1 		then value2 = "&Val_cl" ;
if find(descrip2,'DOMESTIC VIOLENCE RISK:','i') ge 1 	then value2 = "&Val_cv" ;
if find(descrip2,'PATIENT DECEASED:','i') ge 1 	then value2 = "&Val_cx" ;
if find(descrip2,'PREVIOUS POS:','i') ge 1 		then value2 = "&Val_c1" ;
if find(descrip2,'OPEN:','i') ge 1 		then value2 = "&Val_co" ;

if find(descrip,'NEW PARTNERS NOTIFIED:','i') ge 1 then percent1 = put(round("&PER_pm",0.001),percent8.1)  ;
if find(descrip,'PREV. NEG, NEW POS:','i') ge 1 		then percent1 = put(round("&PER_p2",0.001),percent8.1)  ;
if find(descrip,'PREV. NEG, STILL NEG:','i') ge 1 		then percent1 = put(round("&PER_p3",0.001),percent8.1)  ;
if find(descrip,'PREV. NEG, NO TEST:','i') ge 1 		then percent1 = put(round("&PER_p4",0.001),percent8.1)  ;
if find(descrip,'NO PREV. TEST, NEW POS:','i') ge 1 		then percent1 = put(round("&PER_p5",0.001),percent8.1)  ;
if find(descrip,'NO PREV. TEST, NEW NEG:','i') ge 1 		then percent1 = put(round("&PER_p6",0.001),percent8.1)  ;
if find(descrip,'NO PREV TEST, NO TEST:','i') ge 1 		then percent1 = put(round("&PER_p7",0.001),percent8.1)  ;
if find(descrip,'NEW PARTNERS NOT NOTIFIED:','i') ge 1 		then percent1 = put(round("&PER_pn",0.001),percent8.1)  ;
if find(descrip,'INSUFFICIENT INFO:','i') ge 1 		then percent1 = put(round("&PER_pg",0.001),percent8.1)  ;
if find(descrip,'UNABLE TO LOCATE:','i') ge 1 		then percent1 = put(round("&PER_ph",0.001),percent8.1)  ;
if find(descrip,'REFUSED EXAM:','i') ge 1 		then percent1 = put(round("&PER_pj",0.001),percent8.1)  ;
if find(descrip,'OOJ:','i') ge 1 		then percent1 = put(round("&PER_pk",0.001),percent8.1)  ;
if find(descrip,'OTHER:','i') ge 1 		then percent1 = put(round("&PER_pl",0.001),percent8.1)  ;
if find(descrip,'DOMESTIC VIOLENCE RISK:','i') ge 1 then percent1 = put(round("&PER_pv",0.001),percent8.1)  ;
if find(descrip,'PATIENT DECEASED:','i') ge 1 then percent1 = put(round("&PER_px",0.001),percent8.1)  ;
if find(descrip,'PREVIOUS POS:','i') ge 1 		then percent1 = put(round("&PER_p1",0.001),percent8.1)  ;
if find(descrip,'OPEN:','i') ge 1 		then percent1 = put(round("&PER_po",0.001),percent8.1)  ;


if find(descrip2,'NEW CLUSTERS NOTIFIED:','i') ge 1  then percent2 = put(round("&PER_cm",0.001),percent8.1)  ;
if find(descrip2,'PREV. NEG, NEW POS:','i') ge 1 		then percent2 = put(round("&PER_c2",0.001),percent8.1)  ;
if find(descrip2,'PREV. NEG, STILL NEG:','i') ge 1 		then percent2 = put(round("&PER_c3",0.001),percent8.1)  ;
if find(descrip2,'PREV. NEG, NO TEST:','i') ge 1 		then percent2 = put(round("&PER_c4",0.001),percent8.1)  ;
if find(descrip2,'NO PREV. TEST, NEW POS:','i') ge 1 		then percent2 = put(round("&PER_c5",0.001),percent8.1)  ;
if find(descrip2,'NO PREV. TEST, NEW NEG:','i') ge 1 		then percent2 = put(round("&PER_c6",0.001),percent8.1)  ;
if find(descrip2,'NO PREV TEST, NO TEST:','i') ge 1 		then percent2 = put(round("&PER_c7",0.001),percent8.1)  ;
if find(descrip2,'NEW CLUSTERS NOT NOTIFIED:','i') ge 1 		then percent2 = put(round("&PER_cn",0.001),percent8.1)  ;
if find(descrip2,'INSUFFICIENT INFO:','i') ge 1 		then percent2 = put(round("&PER_cg",0.001),percent8.1)  ;
if find(descrip2,'UNABLE TO LOCATE:','i') ge 1 		then percent2 = put(round("&PER_ch",0.001),percent8.1)  ;
if find(descrip2,'REFUSED EXAM:','i') ge 1 		then percent2 = put(round("&PER_cj",0.001),percent8.1)  ;
if find(descrip2,'OOJ:','i') ge 1 		then percent2 = put(round("&PER_ck",0.001),percent8.1)  ;
if find(descrip2,'OTHER:','i') ge 1 		then percent2 = put(round("&PER_cl",0.001),percent8.1)  ;
if find(descrip2,'DOMESTIC VIOLENCE RISK:','i') ge 1 then percent1 = put(round("&PER_cv",0.001),percent8.1)  ;
if find(descrip2,'PATIENT DECEASED:','i') ge 1 then percent1 = put(round("&PER_cx",0.001),percent8.1)  ;
if find(descrip2,'PREVIOUS POS:','i') ge 1 		then percent2 = put(round("&PER_c1",0.001),percent8.1)  ;
if find(descrip2,'OPEN:','i') ge 1 		then percent2 = put(round("&PER_CO",0.001),percent8.1)  ;
run;

data template4;
set template4;
if find(descrip,'NEW PARTNERS NOTIFIED:','i') ge 1 		then value1 = "&Val_PM" ;
if find(descrip,'WITHIN 3 DAYS:','i') ge 1 		then value1 = "&Val_PR" ;
if find(descrip,'WITHIN 5 DAYS:','i') ge 1 		then value1 = "&Val_PS" ;
if find(descrip,'WITHIN 7 DAYS:','i') ge 1 		then value1 = "&Val_PT" ;
if find(descrip,'WITHIN 14 DAYS:','i') ge 1 		then value1 = "&Val_PU" ;


if find(descrip2,'NEW CLUSTERS NOTIFIED:','i') ge 1 		then value2 = "&Val_CM" ;
if find(descrip2,'WITHIN 3 DAYS:','i') ge 1 		then value2 = "&Val_CR" ;
if find(descrip2,'WITHIN 5 DAYS:','i') ge 1 		then value2 = "&Val_CS" ;
if find(descrip2,'WITHIN 7 DAYS:','i') ge 1 		then value2 = "&Val_CT" ;
if find(descrip2,'WITHIN 14 DAYS:','i') ge 1 		then value2 = "&Val_CU" ;


if find(descrip,'WITHIN 3 DAYS:','i') ge 1 		then percent1 = put(round("&PER_PR",0.001),percent8.1)  ;
if find(descrip,'WITHIN 5 DAYS:','i') ge 1 		then percent1 = put(round("&PER_PS",0.001),percent8.1)  ;
if find(descrip,'WITHIN 7 DAYS:','i') ge 1 		then percent1 = put(round("&PER_PT",0.001),percent8.1)  ;
if find(descrip,'WITHIN 14 DAYS:','i') ge 1 		then percent1 = put(round("&PER_PU",0.001),percent8.1)  ;


if find(descrip2 = 'WITHIN 3 DAYS:','i') ge 1 		then percent2 = put(round("&PER_CR",0.001),percent8.1)  ;
if find(descrip2 = 'WITHIN 5 DAYS:','i') ge 1  		then percent2 = put(round("&PER_CS",0.001),percent8.1)  ;
if find(descrip2 = 'WITHIN 7 DAYS:','i') ge 1  		then percent2 = put(round("&PER_CT",0.001),percent8.1)  ;
if find(descrip2 = 'WITHIN 14 DAYS:','i') ge 1 		then percent2 = put(round("&PER_CU",0.001),percent8.1)  ;
run;

data template_all;
length colgroup $45.;
set template1 (in=a) template2 (in=b) template3 (in=c) template4 (in=d);
if a then colgroup = 'CASE ASSIGNMENTS & OUTCOMES';
else if b then colgroup = 'PARTNERS & CLUSTERS INITIATED';
else if c then colgroup = 'DISPOSITIONS - NEW PARTNERS & CLUSTERS';
else if d then colgroup = 'SPEED OF NOTIFICATION - PARTNERS & CLUSTERS';
run;


title;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   MISSING = ' ' 
topmargin=0.25 in bottommargin=0.50in leftmargin=0.25in rightmargin=0.25in  nobyline  ;
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
ods PDF body = sock uniform;

title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";
TITLE2 bold f= Calibri h=12pt  j=c "WORKER: SUMMARY (ALL WORKERS)";


proc report data = template_all nowd spanrows split='~' headline missing 
style(header)={just=center font_weight=bold font_face="calibri" font_size = 9pt }
style(report)={font_size=9pt font_face="calibri" cellpadding=2pt cellspacing=1 rules=none frame=void }
style(column)={font_size=9pt font_face="calibri"};
columns colgroup descrip value1 percent1 blank descrip2 value2 percent2;

define colgroup / order order=data noprint;
define descrip /DISPLAY "  " style = [font_size=9pt font_face="calibri" cellwidth=45mm];
define  value1    / DISPLAY " " style = [font_size=9pt font_face="calibri" just=center cellwidth=10mm ];
define percent1 /DISPLAY " "  style = [font_size=9pt font_face="calibri" just=right cellwidth=15mm ];
define blank / ' ' style={cellwidth=10mm};
define descrip2 /DISPLAY "  " style = [font_size=9pt font_face="calibri" cellwidth=50mm];
define  value2    / DISPLAY " " style = [font_size=9pt font_face="calibri" just=center cellwidth=10mm ];
define percent2 /DISPLAY " "  style = [font_size=9pt font_face="calibri" just=right cellwidth=15mm ];

compute before colgroup/style = [font_face="calibri" just=center cellwidth=160mm fontsize=10pt fontweight=bold 
                                    background=light gray 
                                    bordertopcolor=black bordertopwidth=1
                                    borderbottomcolor=black borderbottomwidth=2]; 
length colgroup $45.;
LINE colgroup $45. ;
endcomp; 

compute after colgroup;
line ' ';
endcomp;

compute descrip ;
if find(descrip,'CASES ASSIGNED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'CASES CLOSED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,"CASES IX'D:",'i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'CASES REINTERVIEWED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'TOTAL PERIOD PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'TOTAL PARTNERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'CONTACT INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'CASES W/NO PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'NEW PARTNERS NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'NEW PARTNERS NOT NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'PREVIOUS POS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt }');
  end;
if find(descrip,'WITHIN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'WITHIN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'WITHIN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'WITHIN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'FROM OI:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'FROM RI:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
  
  if find(descrip,'PREV. NEG, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'PREV. NEG, STILL NEG:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'PREV. NEG, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'NO PREV. TEST, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip,'NO PREV. TEST, NEW NEG:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'NO PREV TEST, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'UNABLE TO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'REFUSED EXAM:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    if find(descrip,'OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'INSUFFICIENT INFO:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    if find(descrip,'DOMESTIC VIOLENCE RISK:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip,'PATIENT DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    endcomp;
compute descrip2 ;
if find(descrip2,'HIV PREVIOUS POSITIVE:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'HIV TESTED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'HIV NEW POSITIVE:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'HIV POSTTEST COUNSEL:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'PARTNER NOTIFICATION INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'TESTING INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
if find(descrip2,'TOTAL CLUSTERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip2,'NEW CLUSTERS NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'NEW CLUSTERS NOT NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'PREVIOUS POS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;

if find(descrip2,'WITHIN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'WITHIN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'WITHIN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'WITHIN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'CLUSTER INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'CASES /W NO CLUSTERS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
  
  if find(descrip2,'PREV. NEG, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'PREV. NEG, STILL NEG:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'PREV. NEG, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'NO PREV. TEST, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
  if find(descrip2,'NO PREV. TEST, NEW NEG:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'NO PREV TEST, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'UNABLE TO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'REFUSED EXAM:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
    if find(descrip2,'OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'INSUFFICIENT INFO:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
    if find(descrip2,'DOMESTIC VIOLENCE RISK:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(descrip2,'PATIENT DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
 endcomp;

run;


/***********2nd part*******************************/


ods proclabel = "Report 1" ;
title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";
TITLE2 bold f= Calibri h=12pt  j=c " 		WORKER: SUMMARY OF #byval(PROVIDER_QUICK_CODE)	";

proc report data = indie nowd spanrows ls =200  split='~' headline missing 
style(header)={just=center font_weight=bold font_face="calibri" font_size = 9pt }
style(report)={font_size=9pt font_face="calibri" cellpadding=2pt cellspacing=1 rules=none frame=void }
style(column)={font_size=9pt font_face="calibri"};
by PROVIDER_QUICK_CODE;
columns  PROVIDER_QUICK_CODE colgroup colname colval colpct blank colname2 colval2 colpct2;

define PROVIDER_QUICK_CODE / order order=data page noprint ;
define colgroup / order order=data noprint;
define colname /DISPLAY "  " style = [font_size=9pt font_face="calibri" cellwidth=45mm];
define  colval    / DISPLAY " " format = best9. style = [font_size=9pt font_face="calibri" just=center cellwidth=10mm ];
define colpct /DISPLAY " "  format = percent8.1 style = [font_size=9pt font_face="calibri" just=right cellwidth=15mm ];
define blank / ' ' style={cellwidth=10mm};
define colname2 /DISPLAY "  " style = [font_size=9pt font_face="calibri" cellwidth=50mm];
define  colval2    / DISPLAY " " format = best9. style = [font_size=9pt font_face="calibri" just=center cellwidth=10mm ];
define colpct2 /DISPLAY " "  format = percent8.1  style = [font_size=9pt font_face="calibri" just=right cellwidth=15mm ];

   compute before colgroup/style = [font_face="calibri" just=center cellwidth=160mm fontsize=10pt fontweight=bold 
                                    background=light gray 
                                    bordertopcolor=black bordertopwidth=1
                                    borderbottomcolor=black borderbottomwidth=2]; 
      length colgroup $45.;
	  LINE colgroup $45. ;
   endcomp; 


break after PROVIDER_QUICK_CODE / page  ; 

compute colpct;
  if colname='TOTAL PERIOD PARTNERS:' then do;
    call define(_col_,'style','style={font_face="calibri" fontsize=9pt}');
    call define(_col_,'format','3.1');
  end;
  else do;
    call define(_col_,'style','style={font_face="calibri" fontsize=9pt}');
    call define(_col_,'format','percent8.1');
  end;
endcomp;

compute colname ;
if find(colname,'CASES ASSIGNED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'CASES CLOSED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,"CASES IX'D:",'i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'CASES REINTERVIEWED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'TOTAL PERIOD PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'TOTAL PARTNERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'CONTACT INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'CASES /W NO PARTNERS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'NEW PARTNERS NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'NEW PARTNERS NOT NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'PREVIOUS POS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt }');
  end;
if find(colname,'WITHIN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'WITHIN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'WITHIN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'WITHIN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'FROM OI:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'FROM RI:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
  
  if find(colname,'PREV. NEG, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'PREV NEG, STILL NEG:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'PREV NEG, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'NO PREV. TEST, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
  if find(colname,'NO PREV. TEST, NEW NEG:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'NO PREV. TEST, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'UNABLE TO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'REFUSED EXAM:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    if find(colname,'OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'INSUFFICIENT INFO:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    if find(colname,'DOMESTIC VIOLENCE RISK:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname,'PATIENT DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
    endcomp;
compute colname2 ;
if find(colname2,'HIV PREVIOUS POSITIVE:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'HIV TESTED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'HIV NEW POSITIVE:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'HIV POSTTEST COUNSEL:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'PARTNER NOTIFICATION INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'TESTING INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
if find(colname2,'TOTAL CLUSTERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
  if find(colname2,'NEW CLUSTERS NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'NEW CLUSTERS NOT NOTIFIED:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'PREVIOUS POS:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'OPEN:','i') ge 1  then do;
    call define(_col_,'style','style={font_weight=bold font_face="calibri" fontsize=9pt}');
  end;

if find(colname2,'WITHIN 3 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'WITHIN 5 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'WITHIN 7 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'WITHIN 14 DAYS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'CLUSTER INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'CASES /W NO CLUSTERS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
  
  if find(colname2,'PREV NEG, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'PREV NEG, STILL NEG:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'PREV NEG, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'NO PREV. TEST, NEW POS:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
  if find(colname2,'NO PREV. TEST, NEW NEG:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'NO PREV. TEST, NO TEST:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'UNABLE TO LOCATE:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'REFUSED EXAM:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
    if find(colname2,'OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'INSUFFICIENT INFO:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
    if find(colname2,'DOMESTIC VIOLENCE RISK:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
   if find(colname2,'PATIENT DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={indent=40 font_face="calibri" fontsize=9pt}');
  end;
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
      %export(work,pa1_new,sock,&exporttype);
Title;
%finish:
%mend PA01_HIV;
%PA01_HIV;


