/*Program Name : PA02_HIV.sas																													*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:																															*/
/*																																				*/
/*Program Last Modified Date:																													*/
/*							:01-04-2017: Formatting complete																													*/
/*							:01-09-2017: All upcase worker title in detail report																*/
/*Program Description:	Creates PA02 STD Report: for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%macro PA02_HIV;

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

proc sql buffersize=1M;
create table PA02 as select distinct a.INV_LOCAL_ID, b.REFERRAL_BASIS, a.FL_FUP_INVESTIGATOR_ASSGN_DT, a.FL_FUP_DISPO_DT,
datepart(a.FL_FUP_INVESTIGATOR_ASSGN_DT) as assign_dt format = mmddyy10., a.FL_FUP_DISPOSITION, 
datepart(a.FL_FUP_DISPO_DT) as disp_dt format = mmddyy10.,datepart(a.FL_FUP_DISPO_DT)- datepart(a.FL_FUP_INVESTIGATOR_ASSGN_DT) as days,
a.INVESTIGATOR_FL_FUP_KEY, a.INVESTIGATOR_FL_FUP_QC, c.PROVIDER_QUICK_CODE, c.PROVIDER_FIRST_NAME, INVESTIGATOR_DISP_FL_FUP_KEY
from STD_HIV_DATAMART a 
inner join nbs_rdb.INVESTIGATION b on 
a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_PROVIDER c on
a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
where 
/*b.INV_CASE_STATUS in ('Probable','Confirmed') and --Defect ND-11097*/
a.INVESTIGATOR_FL_FUP_KEY ~=1;
quit; 


proc sql noprint;
select min(FL_FUP_DISPO_DT), max(FL_FUP_DISPO_DT), min(FL_FUP_INVESTIGATOR_ASSGN_DT), max(FL_FUP_INVESTIGATOR_ASSGN_DT)
into :MIN_FL_FUP_DISPO_DT , :MAX_FL_FUP_DISPO_DT,:MIN_FL_FUP_INVESTIGATOR_ASSGN_DT, :MAX_FL_FUP_INVESTIGATOR_ASSGN_DT
from PA02
;

proc sql;
create table PA02_DISPO as select a.INV_LOCAL_ID, b.REFERRAL_BASIS, a.FL_FUP_INVESTIGATOR_ASSGN_DT, a.FL_FUP_DISPO_DT, a.FL_FUP_DISPOSITION,
a.INVESTIGATOR_FL_FUP_KEY, a.INVESTIGATOR_FL_FUP_QC, c.PROVIDER_QUICK_CODE, c.PROVIDER_FIRST_NAME, INVESTIGATOR_DISP_FL_FUP_KEY
from STD_HIV_DATAMART a inner join nbs_rdb.investigation b on
a.investigation_key = b.investigation_key
inner join nbs_rdb.D_PROVIDER c on
a.INVESTIGATOR_DISP_FL_FUP_KEY = c.PROVIDER_KEY
where INVESTIGATOR_DISP_FL_FUP_KEY in (select INVESTIGATOR_DISP_FL_FUP_KEY from PA02)
and INVESTIGATOR_DISP_FL_FUP_KEY ~= 1
and datepart(FL_FUP_DISPO_DT) GE datepart(&MIN_FL_FUP_DISPO_DT)
and datepart(FL_FUP_DISPO_DT) LE datepart(&MAX_FL_FUP_DISPO_DT)
and datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) GE datepart(&MIN_FL_FUP_INVESTIGATOR_ASSGN_DT)
and datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) LE datepart(&MAX_FL_FUP_INVESTIGATOR_ASSGN_DT);
quit; 

proc sql;
create table PA02_dt as select distinct a.INV_LOCAL_ID, b.REFERRAL_BASIS, 
datepart(a.FL_FUP_INVESTIGATOR_ASSGN_DT) as assign_dt format = mmddyy10., a.FL_FUP_DISPOSITION, 
datepart(a.FL_FUP_DISPO_DT) as disp_dt format = mmddyy10.,datepart(a.FL_FUP_DISPO_DT)- datepart(a.FL_FUP_INVESTIGATOR_ASSGN_DT) as days,
a.INVESTIGATOR_FL_FUP_KEY, a.INVESTIGATOR_FL_FUP_QC, c.PROVIDER_QUICK_CODE, c.PROVIDER_FIRST_NAME
from STD_HIV_DATAMART a 
inner join nbs_rdb.INVESTIGATION b on 
a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_PROVIDER c on
a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
where 
/*b.INV_CASE_STATUS in ('Probable','Confirmed') and --Defect ND-11097*/
a.INVESTIGATOR_FL_FUP_KEY ~=1 and  datepart(a.FL_FUP_DISPO_DT) ge datepart(a.FL_FUP_INVESTIGATOR_ASSGN_DT)
order by a.INVESTIGATOR_FL_FUP_KEY;
quit; 



proc sql;
create table partners as 
select distinct * 
from pa02  
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') ; 
quit;


proc sql;
create table partners_dispo as 
select distinct * 
from PA02_DISPO 
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') ; 
quit;

proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_P 
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') 
and FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test')
and days le 14  ; 
run;

proc sql; 
create table jp as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_j_p
from dte_freq_ind_P
where Days le 3
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table kp as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_k_p
from dte_freq_ind_P
where Days le 5
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
proc sql; 
create table lp as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_l_p
from dte_freq_ind_P
where Days le 7
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table mp as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_m_p
from dte_freq_ind_P
where Days le 14
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
quit;



proc sql;
create table clus as 
select distinct * 
from pa02  
where REFERRAL_BASIS in ('A1 - Associate 1', 'A2 - Associate 2','A3 - Associate 3', 'S2 - Social Contact 2','S1 - Social Contact 1'
,'S3 - Social Contact 3')  
;
quit;


proc sql;
create table clus_dispo as 
select distinct * 
from PA02_DISPO  
where REFERRAL_BASIS in ('A1 - Associate 1', 'A2 - Associate 2','A3 - Associate 3', 'S2 - Social Contact 2','S1 - Social Contact 1'
,'S3 - Social Contact 3')  
;
quit;


proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_c
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('A1 - Associate 1', 'A2 - Associate 2','A3 - Associate 3', 'S2 - Social Contact 2','S1 - Social Contact 1'
,'S3 - Social Contact 3') 
and FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test')
and days le 14  ; 
run;

proc sql; 
create table jc as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_j_c
from dte_freq_ind_c
where Days le 3
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table kc as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_k_c
from dte_freq_ind_c
where Days le 5
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
proc sql; 
create table lc as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_l_c
from dte_freq_ind_c
where Days le 7
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table mc as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_m_c
from dte_freq_ind_c
where Days le 14
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
quit;



proc sql;
create table reac as 
select distinct *
from pa02  
where REFERRAL_BASIS in ('T1 - Positive Test', 'T2 - Morbidity Report')  
;
quit;


proc sql;
create table reac_dispo as 
select distinct *
from PA02_DISPO  
where REFERRAL_BASIS in ('T1 - Positive Test', 'T2 - Morbidity Report')  
;
quit;

proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_r
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('T1 - Positive Test', 'T2 - Morbidity Report') 
and FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test')
and days le 14  ; 
run;

proc sql; 
create table jr as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_j_r
from dte_freq_ind_r
where Days le 3
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table kr as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_k_r
from dte_freq_ind_r
where Days le 5
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
proc sql; 
create table lr as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_l_r
from dte_freq_ind_r
where Days le 7
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table mr as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_m_r
from dte_freq_ind_r
where Days le 14
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
quit;



proc sql;
create table cohort as 
select distinct *
from pa02  
where REFERRAL_BASIS in ('C1- Cohort')  
;
quit;


proc sql;
create table cohort_dispo as 
select distinct *
from PA02_DISPO  
where REFERRAL_BASIS in ('C1- Cohort')  
;
quit;


proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_o
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('C1- Cohort') 
and FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test')
and days le 14  ; 
run;

proc sql; 
create table jo as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_j_o
from dte_freq_ind_o
where Days le 3
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table ko as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_k_o
from dte_freq_ind_o
where Days le 5
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
proc sql; 
create table lo as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_l_o
from dte_freq_ind_o
where Days le 7
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;

proc sql; 
create table mo as 
select INVESTIGATOR_FL_FUP_KEY , sum(days_count_A) as Var_m_o
from dte_freq_ind_o
where Days le 14
group by INVESTIGATOR_FL_FUP_KEY
order by INVESTIGATOR_FL_FUP_KEY;
quit;



%Macro flds(in,in1,out);
proc sql;
create table g_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_g_p  
from &in
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 
title;


proc sql;
create table h_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_h_p  
from &in
where FL_FUP_DISPOSITION is not null
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 
title;


proc sql;
create table i_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_i_p  
from &in
where FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos' , '3 - Prev. Neg, Still Neg' , 
'4 - Prev. Neg, No Test' ,'5 - No Prev Test, New Pos' , '6 - No Prev Test, New Neg' , '7 - No Prev Test, No Test')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table n_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_n_p  
from &in
where FL_FUP_DISPOSITION in ('2 - Prev. Neg, New Pos')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table o_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_o_p  
from &in
where FL_FUP_DISPOSITION in ('3 - Prev. Neg, Still Neg')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table p_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_p_p  
from &in
where FL_FUP_DISPOSITION in ('4 - Prev. Neg, No Test')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table q_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_q_p  
from &in
where FL_FUP_DISPOSITION in ('5 - No Prev Test, New Pos')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 


proc sql;
create table r_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_r_p  
from &in
where FL_FUP_DISPOSITION in ('6 - No Prev Test, New Neg')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table s_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_s_p  
from &in
where FL_FUP_DISPOSITION in ('7 - No Prev Test, No Test')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit;


proc sql;
create table t_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_t_p  
from &in
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',

'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table u_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_u_p  
from &in
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table v_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_v_p  
from &in
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table w_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_w_p  
from &in
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table x_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_x_p  
from &in
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 


proc sql;
create table y_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_y_p  
from &in
where FL_FUP_DISPOSITION in ('L - Other')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 


proc sql;
create table z_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_z_p  
from &in
where FL_FUP_DISPOSITION in ('V - Domestic Violence Risk')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table aa_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_aa_p  
from &in
where FL_FUP_DISPOSITION in ('X - Patient Deceased')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table ab_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ab_p  
from &in
where FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 


proc sql;
create table ad_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ad_p  
from &in
where FL_FUP_DISPOSITION is null
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

proc sql;
create table ae_p  as 
select PROVIDER_QUICK_CODE,  INVESTIGATOR_DISP_FL_FUP_KEY as INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ae_p  
from &in1
where FL_FUP_DISPOSITION is not null and INVESTIGATOR_DISP_FL_FUP_KEY ~= INVESTIGATOR_FL_FUP_KEY
group by INVESTIGATOR_DISP_FL_FUP_KEY, PROVIDER_QUICK_CODE
order by INVESTIGATOR_DISP_FL_FUP_KEY, PROVIDER_QUICK_CODE;quit; 

data &out;
merge g_p h_p i_p n_p o_p p_p q_p r_p s_p t_p
		u_p v_p w_p x_p y_p z_p
		aa_p ab_p  ad_p ae_p;
		by INVESTIGATOR_FL_FUP_KEY;
		run;

data &out;
   set &out;
   array change _numeric_;
        do over change;
            if change=. then change=0;
        end;
 run ;

%Mend;
%flds(partners,partners_dispo,partnerpa02);
%flds(clus,clus_dispo,cluspa02);
%flds(reac,reac_dispo,reacpa02);
%flds(cohort,cohort_dispo,cohpa02);

options missing = 0;
data partner_final ;
merge partnerpa02 jp kp lp mp;
by INVESTIGATOR_FL_FUP_KEY;
run;


data clus_final ;
merge cluspa02 jc kc lc mc;
by INVESTIGATOR_FL_FUP_KEY;
run;

data reac_final ;
merge reacpa02 jr kr lr mr;
by INVESTIGATOR_FL_FUP_KEY;
run;

data coh_final ;
merge cohpa02 jo ko lo mo;
by INVESTIGATOR_FL_FUP_KEY;
run;


data ind1_1 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_QUICK_CODE colname colval );
set partner_final;
length colname $35. ; 
format colname $35. ; 
colname = 'Assigned:';
colval = var_g_p;
output;
colname = 'Dispositioned:';
colval = var_h_p;
output;
colname = 'Exam''d:';
colval = var_i_p;
output;
colname = 'Exam''d w/in 3:';
colval = var_j_p;
output;
colname = 'Exam''d w/in 5:';
colval = var_k_p;
output;
colname = 'Exam''d w/in 7:';
colval = var_l_p;
output;
colname = 'Exam''d w/in 14:';
colval = var_m_p;
output;
colname = 'Dispo 2:';
colval = var_n_p;
output;
colname = 'Dispo 3: ';
colval = var_o_p;
output;
colname = 'Dispo 4:';
colval = var_p_p;
output;
colname = 'Dispo 5:';
colval = var_q_p;
output;
colname = 'Dispo 6:';
colval = var_r_p;
output;
colname = 'Dispo 7:';
colval = var_s_p;
output;
colname = 'Not Examined:';
colval = var_t_p;
output;
colname = 'Dispo G:';
colval = var_u_p;
output;
colname = 'Dispo H:';
colval = var_v_p;
output;
colname = 'Dispo J:';
colval = var_w_p;
output;
colname = 'Dispo K:';
colval = var_x_p;
output;
colname = 'Dispo L:';
colval = var_y_p;
output;
colname = 'Dispo V:';
colval = var_z_p;
output;
colname = 'Dispo X:';
colval = var_aa_p;
output;
colname = "Dispo Z:";
colval = var_ab_p;
output;
colname = 'Open:';
colval = var_ad_p;
output;
colname = 'Non-assigned Dispos:';
colval = var_ae_p;
output;
run;



proc sort ;
by INVESTIGATOR_FL_FUP_KEY;
run;


data ind1_2 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_QUICK_CODE colname2 colval2 );
set clus_final;
length colname2 $35. ; 
format colname2 $35. ; 
colname2 = 'Assigned:';
colval2 = var_g_p;
output;
colname2 = 'Dispositioned:';
colval2 = var_h_p;
output;
colname2 = 'Exam''d:';
colval2 = var_i_p;
output;
colname2 = 'Exam''d w/in 3:';
colval2 = var_j_c;
output;
colname2 = 'Exam''d w/in 5:';
colval2 = var_k_c;
output;
colname2 = 'Exam''d w/in 7:';
colval2 = var_l_c;
output;
colname2 = 'Exam''d w/in 14:';
colval2 = var_m_c;
output;
colname2 = 'Dispo 2:';
colval2 = var_n_p;
output;
colname2 = 'Dispo 3: ';
colval2 = var_o_p;
output;
colname2 = 'Dispo 4:';
colval2 = var_p_p;
output;
colname2 = 'Dispo 5:';
colval2 = var_q_p;
output;
colname2 = 'Dispo 6:';
colval2 = var_r_p;
output;
colname2 = 'Dispo 7:';
colval2 = var_s_p;
output;
colname2 = 'Not Examined:';
colval2 = var_t_p;
output;
colname2 = 'Dispo G:';
colval2 = var_u_p;
output;
colname2 = 'Dispo H:';
colval2 = var_v_p;
output;
colname2 = 'Dispo J:';
colval2 = var_w_p;
output;
colname2 = 'Dispo K:';
colval2 = var_x_p;
output;
colname2 = 'Dispo L:';
colval2 = var_y_p;
output;
colname2 = 'Dispo V:';
colval2 = var_z_p;
output;
colname2 = 'Dispo X:';
colval2 = var_aa_p;
output;
colname2 = "Dispo Z:";
colval2 = var_ab_p;
output;
colname2 = 'Open:';
colval2 = var_ad_p;
output;
colname2 = 'Non-assigned Dispos:';
colval2 = var_ae_p;
output;
run;

proc sort ;
by INVESTIGATOR_FL_FUP_KEY;
run;



data ind1_3 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_QUICK_CODE colname3 colval3 );
set reac_final;
length colname3 $35. ; 
format colname3 $35. ; 
colname3 = 'Assigned:';
colval3 = var_g_p;
output;
colname3 = 'Dispositioned:';
colval3 = var_h_p;
output;
colname3 = 'Exam''d:';
colval3 = var_i_p;
output;
colname3 = 'Exam''d w/in 3:';
colval3 = var_j_r;
output;
colname3 = 'Exam''d w/in 5:';
colval3 = var_k_r;
output;
colname3 = 'Exam''d w/in 7:';
colval3 = var_l_r;
output;
colname3 = 'Exam''d w/in 14:';
colval3 = var_m_r;
output;
colname3 = 'Dispo 2:';
colval3 = var_n_p;
output;
colname3 = 'Dispo 3: ';
colval3 = var_o_p;
output;
colname3 = 'Dispo 4:';
colval3 = var_p_p;
output;
colname3 = 'Dispo 5:';
colval3 = var_q_p;
output;
colname3 = 'Dispo 6:';
colval3 = var_r_p;
output;
colname3 = 'Dispo 7:';
colval3 = var_s_p;
output;
colname3 = 'Not Examined:';
colval3 = var_t_p;
output;
colname3 = 'Dispo G:';
colval3 = var_u_p;
output;
colname3 = 'Dispo H:';
colval3 = var_v_p;
output;
colname3 = 'Dispo J:';
colval3 = var_w_p;
output;
colname3 = 'Dispo K:';
colval3 = var_x_p;
output;
colname3 = 'Dispo L:';
colval3 = var_y_p;
output;
colname3 = 'Dispo V:';
colval3 = var_z_p;
output;
colname3 = 'Dispo X:';
colval3 = var_aa_p;
output;
colname3 = "Dispo Z:";
colval3 = var_ab_p;
output;
colname3 = 'Open:';
colval3 = var_ad_p;
output;
colname3 = 'Non-assigned Dispos:';
colval3 = var_ae_p;
output;
run;

proc sort ;
by INVESTIGATOR_FL_FUP_KEY;
run;


data ind1_4 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_QUICK_CODE colname4 colval4 );
set coh_final;
length colname4 $35. ; 
format colname4 $35. ; 
colname4 = 'Assigned:';
colval4 = var_g_p;
output;
colname4 = 'Dispositioned:';
colval4 = var_h_p;
output;
colname4 = 'Exam''d:';
colval4 = var_i_p;
output;
colname4 = 'Exam''d w/in 3:';
colval4 = var_j_o;
output;
colname4 = 'Exam''d w/in 5:';
colval4 = var_k_o;
output;
colname4 = 'Exam''d w/in 7:';
colval4 = var_l_o;
output;
colname4 = 'Exam''d w/in 14:';
colval4 = var_m_o;
output;
colname4 = 'Dispo 2:';
colval4 = var_n_p;
output;
colname4 = 'Dispo 3: ';
colval4 = var_o_p;
output;
colname4 = 'Dispo 4:';
colval4 = var_p_p;
output;
colname4 = 'Dispo 5:';
colval4 = var_q_p;
output;
colname4 = 'Dispo 6:';
colval4 = var_r_p;
output;
colname3 = 'Dispo 7:';
colval3 = var_s_p;
output;
colname4 = 'Not Examined:';
colval4 = var_t_p;
output;
colname4 = 'Dispo G:';
colval4 = var_u_p;
output;
colname4 = 'Dispo H:';
colval4 = var_v_p;
output;
colname4 = 'Dispo J:';
colval4 = var_w_p;
output;
colname4 = 'Dispo K:';
colval4 = var_x_p;
output;
colname4 = 'Dispo L:';
colval4 = var_y_p;
output;
colname4 = 'Dispo V:';
colval4 = var_z_p;
output;
colname4 = 'Dispo X:';
colval4 = var_aa_p;
output;
colname4 = "Dispo Z:";
colval4 = var_ab_p;
output;
colname4 = 'Open:';
colval4 = var_ad_p;
output;
colname4 = 'Non-assigned Dispos:';
colval4 = var_ae_p;
output;
run;

proc sort ;
by INVESTIGATOR_FL_FUP_KEY;
run;

data ind_5;
merge ind1_1 ind1_2 ind1_3 ind1_4;
by INVESTIGATOR_FL_FUP_KEY;
run;

data ind_5;
set ind_5;
IF colname = '' THEN colname = colname1;
IF colname = '' THEN colname = colname2;
IF colname = '' THEN colname = colname3;
IF colname = '' THEN colname = colname4;
run;

data final (keep = PROVIDER_QUICK_CODE_new colname colval colval2 colval3 colval4 pname_l);
length PROVIDER_QUICK_CODE_new $50.;
set ind_5;
PROVIDER_QUICK_CODE_new=left(trim(PROVIDER_QUICK_CODE));
pname_l=lowcase(PROVIDER_QUICK_CODE_new);
run;

proc sort DATA = final ;
by pname_l PROVIDER_QUICK_CODE_new;
run;


/*Create template data for overall summary report*/
filename NBSPGMp1  "&SAS_REPORT_HOME/template";

%include NBSPGMp1 (template9.sas);


proc sql noprint;
 select sum(colval) into :val_gp
 from ind_5 where colname = 'Assigned:' 
;

select sum(colval) into :val_hp
 from ind_5 where colname = 'Dispositioned:' 
;

select sum(colval) into :val_ip
 from ind_5 where colname = 'Exam''d:' 
;

select sum(colval) into :val_jp
 from ind_5 where colname = "Exam'd w/in 3:" 
;
select sum(colval) into :val_kp
 from ind_5 where colname = "Exam'd w/in 5:" 
;
select sum(colval) into :val_lp
 from ind_5 where colname = "Exam'd w/in 7:" 
;
select sum(colval) into :val_mp
 from ind_5 where colname = "Exam'd w/in 14:" 
;
select sum(colval) into :val_np
 from ind_5 where colname = "Dispo 2:" 
;
select sum(colval) into :val_op
 from ind_5 where colname = "Dispo 3:" 
;
select sum(colval) into :val_Pp
 from ind_5 where colname = "Dispo 4:" 
;
select sum(colval) into :val_Qp
 from ind_5 where colname = "Dispo 5:" 
;
select sum(colval) into :val_Rp
 from ind_5 where colname = "Dispo 6:" 
;
select sum(colval) into :val_Sp
 from ind_5 where colname = "Dispo 7:" 
;
select sum(colval) into :val_tp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval) into :val_up
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval) into :val_vp
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval) into :val_wp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval) into :val_xp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval) into :val_yp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval) into :val_zp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval) into :val_aap
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval) into :val_abp
 from ind_5 where colname = "Dispo Z:" 
;


select sum(colval) into :val_adp
 from ind_5 where colname = "Open:" 
;

select sum(colval) into :val_aep
 from ind_5 where colname = "Non-assigned Dispos:" 
;
quit;

data _null_;
put "val_gp =  &val_gp";
put "val_hp =  &val_hp";
put "val_ip =  &val_ip";
put "val_jp =  &val_jp";
put "val_kp =  &val_kp";
run;


%macro fill(name) ; 
data templatePA02_HIV;
set templatePA02_HIV;
if find(descrip,'Assigned:','i') ge 1 then &name = "&val_gp" ;
if find(descrip,'Dispositioned:','i') ge 1 then &name = "&val_Hp" ;
if find(descrip,'Exam''d:','i') ge 1 then &name = "&val_Ip" ;
if find(descrip,'Exam''d w/in 3:','i') ge 1 then &name = "&val_Jp" ;
if find(descrip,'Exam''d w/in 5:','i') ge 1 then &name = "&val_kp";
if find(descrip,'Exam''d w/in 7:','i') ge 1 then &name = "&val_lp";
if find(descrip,'Exam''d w/in 14:','i') ge 1 then &name = "&val_mp";

if find(descrip,'Dispo 2:','i') ge 1 then &name = "&val_np" ;
if find(descrip,'Dispo 3:','i') ge 1 then &name = "&val_op" ;
if find(descrip,'Dispo 4:','i') ge 1 then &name = "&val_pp" ;
if find(descrip,'Dispo 5:','i') ge 1 then &name = "&val_qp" ;
if find(descrip,'Dispo 6:','i') ge 1 then &name =  "&val_rp" ;
if find(descrip,'Dispo 7:','i') ge 1 then &name =  "&val_sp" ;
if find(descrip,'Not Examined:','i') ge 1 then &name =  "&val_tp" ;
if find(descrip,'Dispo G:','i') ge 1 then &name = "&val_up" ;
if find(descrip,'Dispo H:','i') ge 1 then &name = "&val_vp" ;
if find(descrip,'Dispo J:','i') ge 1 then &name = "&val_wp" ;
if find(descrip,'Dispo K:','i') ge 1 then &name = "&val_xp" ;
if find(descrip,'Dispo L:','i') ge 1 then &name = "&val_yp" ;
if find(descrip,'Dispo V:','i') ge 1 then &name =  "&val_zp" ;
if find(descrip,'Dispo X:','i') ge 1 then &name =  "&val_aap" ;
if find(descrip,'Dispo Z:','i') ge 1 then &name =  "&val_abp" ;

if find(descrip,'Open:','i') ge 1 then &name =  "&Val_adp" ;
if find(descrip,'Non-assigned Dispos:','i') ge 1 then &name =  "&Val_aep" ;
run;
%Mend ;
%fill(col1);


proc sql noprint;
 select sum(colval2) into :val_gp
 from ind_5 where colname = 'Assigned:' 
;

select sum(colval2) into :val_hp
 from ind_5 where colname = 'Dispositioned:' 
;

select sum(colval2) into :val_ip
 from ind_5 where colname = 'Exam''d:'
;

select sum(colval2) into :val_jp
 from ind_5 where colname = "Exam'd w/in 3:" 
;
select sum(colval2) into :val_kp
 from ind_5 where colname = "Exam'd w/in 5:" 
;
select sum(colval2) into :val_lp
 from ind_5 where colname = "Exam'd w/in 7:" 
;
select sum(colval2) into :val_mp
 from ind_5 where colname = "Exam'd w/in 14:" 
;
select sum(colval2) into :val_np
 from ind_5 where colname = "Dispo 2:" 
;
select sum(colval2) into :val_op
 from ind_5 where colname = "Dispo 3:" 
;
select sum(colval2) into :val_Pp
 from ind_5 where colname = "Dispo 4:" 
;
select sum(colval2) into :val_Qp
 from ind_5 where colname = "Dispo 5:" 
;
select sum(colval2) into :val_Rp
 from ind_5 where colname = "Dispo 6:" 
;
select sum(colval2) into :val_Sp
 from ind_5 where colname = "Dispo 7:" 
;
select sum(colval2) into :val_tp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval2) into :val_up
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval2) into :val_vp
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval2) into :val_wp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval2) into :val_xp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval2) into :val_yp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval2) into :val_zp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval2) into :val_aap
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval2) into :val_abp
 from ind_5 where colname = "Dispo Z:" 
;
select sum(colval2) into :val_adp
 from ind_5 where colname = "Open:" 
;

select sum(colval2) into :val_aep
 from ind_5 where colname = "Non-assigned Dispos:" 
;
quit;


%fill(col2);


proc sql noprint;
 select sum(colval3) into :val_gp
 from ind_5 where colname = 'Assigned:' 
;

select sum(colval3) into :val_hp
 from ind_5 where colname = 'Dispositioned:' 
;

select sum(colval3) into :val_ip
 from ind_5 where colname = "Exam'd:" 
;

select sum(colval3) into :val_jp
 from ind_5 where colname = "Exam'd w/in 3:" 
;
select sum(colval3) into :val_kp
 from ind_5 where colname = "Exam'd w/in 5:" 
;
select sum(colval3) into :val_lp
 from ind_5 where colname = "Exam'd w/in 7:" 
;
select sum(colval3) into :val_mp
 from ind_5 where colname = "Exam'd w/in 14:" 
;
select sum(colval3) into :val_np
 from ind_5 where colname = "Dispo 2:" 
;
select sum(colval3) into :val_op
 from ind_5 where colname = "Dispo 3:" 
;
select sum(colval3) into :val_Pp
 from ind_5 where colname = "Dispo 4:" 
;
select sum(colval3) into :val_Qp
 from ind_5 where colname = "Dispo 5:" 
;
select sum(colval3) into :val_Rp
 from ind_5 where colname = "Dispo 6:" 
;
select sum(colval3) into :val_sp
 from ind_5 where colname = "Dispo 7:" 
;
select sum(colval3) into :val_tp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval3) into :val_up
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval3) into :val_vp
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval3) into :val_wp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval3) into :val_xp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval3) into :val_yp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval3) into :val_zp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval3) into :val_aap
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval3) into :val_abp
 from ind_5 where colname = "Dispo Z:" 
;

select sum(colval3) into :val_adp
 from ind_5 where colname = "Open:" 
;

select sum(colval3) into :val_aep
 from ind_5 where colname = "Non-assigned Dispos:" 
;
quit;

%fill(col3);


proc sql noprint;
 select sum(colval4) into :val_gp
 from ind_5 where colname = 'Assigned:' 
;

select sum(colval4) into :val_hp
 from ind_5 where colname = 'Dispositioned:' 
;

select sum(colval4) into :val_ip
 from ind_5 where colname = "Exam'd:" 
;

select sum(colval4) into :val_jp
 from ind_5 where colname = "Exam'd w/in 3:" 
;
select sum(colval4) into :val_kp
 from ind_5 where colname = "Exam'd w/in 5:" 
;
select sum(colval4) into :val_lp
 from ind_5 where colname = "Exam'd w/in 7:" 
;
select sum(colval4) into :val_mp
 from ind_5 where colname = "Exam'd w/in 14:" 
;
select sum(colval4) into :val_np
 from ind_5 where colname = "Dispo 2:" 
;
select sum(colval4) into :val_op
 from ind_5 where colname = "Dispo 3:" 
;
select sum(colval4) into :val_Pp
 from ind_5 where colname = "Dispo 4:" 
;
select sum(colval4) into :val_Qp
 from ind_5 where colname = "Dispo 5:" 
;
select sum(colval4) into :val_Rp
 from ind_5 where colname = "Dispo 6:" 
;
select sum(colval4) into :val_sp
 from ind_5 where colname = "Dispo 7:" 
;
select sum(colval4) into :val_tp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval4) into :val_up
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval4) into :val_vp
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval4) into :val_wp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval4) into :val_xp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval4) into :val_yp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval4) into :val_zp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval4) into :val_aap
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval4) into :val_abp
 from ind_5 where colname = "Dispo Z:" 
;

select sum(colval4) into :val_adp
 from ind_5 where colname = "Open:" 
;

select sum(colval4) into :val_aep
 from ind_5 where colname = "Non-assigned Dispos:" 
;
quit;

%fill(col4);

proc sql;
create table thepla02 as 
select descrip , col1, col2, col3 ,col4, sum(col1,col2,col3,col4) as col5
from templatePA02_HIV;
quit;

data final;
set final;
array nvar(*) _numeric_;
	do i= 1 to dim(nvar);  
    if nvar(i)=. then nvar(i)=0;
end;
run;



title;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   missing = 0  
topmargin=0.50 in bottommargin=0.50in leftmargin=0.50in rightmargin=0.50in  nobyline  ;
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


title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";
TITLE2 bold f= Calibri h=12pt  j=c "WORKER: SUMMARY (ALL WORKERS)";



proc report data = thepla02 nowd spanrows split='~' headline missing 
style(header)={cellwidth=20mm font_face="calibri" just=center fontsize=11pt fontweight=bold 
               background=light gray bordertopcolor=black bordertopwidth=1 borderbottomcolor=black borderbottomwidth=2
               rules=group frame=void cellspacing=0}
style(report)={font_size=10pt font_face="Calibri"  rules=none frame=void cellspacing=0}
style(column)={font_size=10pt font_face="Calibri" cellspacing=0};


columns  descrip  col1 col2 col3 col4 col5 ;
define descrip / ORDER=internal "  " style(column)={cellwidth=40mm CELLHEIGHT = 4mm font_size=10pt}
style(header)={background=white bordertopcolor=white borderbottomcolor=white rules=none frame=void};
define  col1    / DISPLAY left "PART." format = best9.  style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  col2    / DISPLAY left "CLUS." format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  col3    / DISPLAY left "REAC." format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  col4    / DISPLAY left "OTHER" format =  best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  col5    / DISPLAY left "TOTAL" format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
compute descrip ;
  if find(descrip,'Exam''d w/in 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in font_size=10pt font_face="calibri"}');
  end;
  if find(descrip,'Assigned:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri" CELLHEIGHT = 8mm vjust=b}');
  end;
  if find(descrip,'Dispositioned:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
 if find(descrip,'Exam''d:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Not Examined:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
    if find(descrip,'Open:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Non-assigned Dispos:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;

   if find(descrip,'Exam''d w/in 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Exam''d w/in 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Exam''d w/in 14:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in font_size=10pt font_face="calibri"}');
  end;
  if find(descrip,'Dispo 2:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  if find(descrip,'Dispo 4:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo 6:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo G:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  if find(descrip,'Dispo H:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo J:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo K:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo L:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
    if find(descrip,'Dispo V:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo X:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo Z:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Dispo Q:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  descrip=upcase(descrip);
   endcomp;

 run;


 /***********2nd part*******************************/

title1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)";

TITLE2 bold f= Calibri h=12pt  j=c "WORKER: SUMMARY OF #byval(PROVIDER_QUICK_CODE_new)	";



proc report data = final nowd spanrows split='~' headline missing 
style(header)={cellwidth=20mm font_face="calibri" just=center fontsize=11pt fontweight=bold 
               background=light gray bordertopcolor=black bordertopwidth=1 borderbottomcolor=black borderbottomwidth=2
               rules=group frame=void cellspacing=0}
style(report)={font_size=10pt font_face="Calibri"  rules=none frame=void cellspacing=0}
style(column)={font_size=10pt font_face="Calibri" cellspacing=0};
by pname_l PROVIDER_QUICK_CODE_new;
columns  PROVIDER_QUICK_CODE_new colname colval colval2 colval3 colvaL4 colvaL5 ;

define PROVIDER_QUICK_CODE_new / ORDER=data page noprint ;
define colname / ORDER=data "  " style(column)={cellwidth=40mm CELLHEIGHT = 4mm font_size=10pt}
style(header)={background=white bordertopcolor=white borderbottomcolor=white rules=none frame=void};
define  colval    / DISPLAY left "PART." format = best9.  style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  colval2   / DISPLAY left "CLUS." format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  colval3    / DISPLAY left "REAC." format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  colval4    / DISPLAY left "OTHER" format =  best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
define  colval5    / COMPUTED left "TOTAL" format = best9. style(column)={just=center cellwidth=20mm CELLHEIGHT = 6mm font_size=10pt}
;
compute colval5;
colval5 = SUM(colval,colval2,colval3,colval4);
endcomp;

compute colname ;
if find(colname,'Assigned:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri" CELLHEIGHT = 8mm vjust=b}');
  end;
  if find(colname,'Dispositioned:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
 if find(colname,"Exam'd:",'i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Not Examined:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
    if find(colname,'Open:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Non-assigned Dispos:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
  if find(colname,'Exam''d w/in 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Exam''d w/in 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Exam''d w/in 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Exam''d w/in 14:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in font_size=10pt font_face="calibri"}');
  end;
  if find(colname,'Dispo 2:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  if find(colname,'Dispo 4:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo 6:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo G:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  if find(colname,'Dispo H:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo J:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo K:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo L:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
    if find(colname,'Dispo V:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo X:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo Z:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
   if find(colname,'Dispo Q:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in font_size=10pt font_face="calibri"}');
  end;
  colname=upcase(colname);
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
	      %export(work,final,sock,&exporttype);
	Title;
	%finish:
	%mend PA02_HIV;
%PA02_HIV;
