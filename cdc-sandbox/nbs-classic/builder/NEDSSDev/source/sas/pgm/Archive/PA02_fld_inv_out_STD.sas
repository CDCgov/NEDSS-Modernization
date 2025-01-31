/*Program Name : PA02_fld_inv_out_std																												*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	10-26-2016																												*/
/*																																				*/
/*Program Last Modified Date:																											*/
/*																																				*/
/*																																				*/
/*Program Description:																															*/
/*																																				*/
/*Comments:																																		*/

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;



libname nbs_rdb ODBC DSN=nbs_rdb UID=nbs_rdb PASSWORD=rdb ACCESS=READONLY;


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




proc sql;
create table PA02 as select distinct a.INV_LOCAL_ID, REFERRAL_BASIS, 
datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) as assign_dt format = mmddyy10., FL_FUP_DISPOSITION, 
datepart(FL_FUP_DISPO_DT) as disp_dt format = mmddyy10.,datepart(FL_FUP_DISPO_DT)- datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) as days,
a.INVESTIGATOR_FL_FUP_KEY, a.INVESTIGATOR_FL_FUP_QC, c.PROVIDER_LAST_NAME, c.PROVIDER_FIRST_NAME
from nbs_rdb.STD_HIV_DATAMART a 
inner join nbs_rdb.INVESTIGATION b on 
a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_PROVIDER c on
a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
where b.INV_CASE_STATUS in ('Probable','Confirmed') 
and a.INVESTIGATOR_FL_FUP_KEY ~=1 ;
quit; 

proc sql;
create table PA02_dt as select distinct a.INV_LOCAL_ID, REFERRAL_BASIS, 
datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) as assign_dt format = mmddyy10., FL_FUP_DISPOSITION, 
datepart(FL_FUP_DISPO_DT) as disp_dt format = mmddyy10.,datepart(FL_FUP_DISPO_DT)- datepart(FL_FUP_INVESTIGATOR_ASSGN_DT) as days,
a.INVESTIGATOR_FL_FUP_KEY, a.INVESTIGATOR_FL_FUP_QC, c.PROVIDER_LAST_NAME, c.PROVIDER_FIRST_NAME
from nbs_rdb.STD_HIV_DATAMART a 
inner join nbs_rdb.INVESTIGATION b on 
a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_PROVIDER c on
a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
where b.INV_CASE_STATUS in ('Probable','Confirmed') 
and a.INVESTIGATOR_FL_FUP_KEY ~=1 and  datepart(FL_FUP_DISPO_DT) ge datepart(FL_FUP_INVESTIGATOR_ASSGN_DT)
order by a.INVESTIGATOR_FL_FUP_KEY;
quit; 

/*proc freq data = PA02_dt  ;*/
/*by INVESTIGATOR_FL_FUP_KEY;*/
/*table Days / nocum missing out = dte_freq_ind_P */
/*(rename =(count=days_count_A)*/
/*drop = percent);*/
/*where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and days le 14  ; */
/*run;*/

proc sql;
create table partners as 
select distinct * 
from pa02  
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') ; 
/*group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME*/
/*order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;*/
quit;

proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_P 
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') 
and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
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


proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_c
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('A1 - Associate 1', 'A2 - Associate 2','A3 - Associate 3', 'S2 - Social Contact 2','S1 - Social Contact 1'
,'S3 - Social Contact 3') 
and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
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
from dte_freq_ind_P
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


proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_r
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('T1 - Positive Test', 'T2 - Morbidity Report') 
and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
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


proc freq data = PA02_dt  ;
by INVESTIGATOR_FL_FUP_KEY;
table Days / nocum missing out = dte_freq_ind_o
(rename =(count=days_count_A)
drop = percent);
where REFERRAL_BASIS in ('C1- Cohort') 
and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
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



%Macro flds(in,out);
proc sql;
create table g_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_g_p  
from &in
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 
title;


proc sql;
create table h_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_h_p  
from &in
where FL_FUP_DISPOSITION is not null
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 
title;


proc sql;
create table i_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_i_p  
from &in
where FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'F - Not Infected')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table n_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_n_p  
from &in
where FL_FUP_DISPOSITION in ('A - Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table o_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_o_p  
from &in
where FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table p_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_p_p  
from &in
where FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table q_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_q_p  
from &in
where FL_FUP_DISPOSITION in ('D - Infected, Not Treated')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 


proc sql;
create table r_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_r_p  
from &in
where FL_FUP_DISPOSITION in ('F - Not Infected')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 


proc sql;
create table t_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_t_p  
from &in
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',

'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table u_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_u_p  
from &in
where FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table v_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_v_p  
from &in
where FL_FUP_DISPOSITION in ('H - Unable to Locate')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table w_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_w_p  
from &in
where FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table x_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_x_p  
from &in
where FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 


proc sql;
create table y_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_y_p  
from &in
where FL_FUP_DISPOSITION in ('L - Other')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 


proc sql;
create table z_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_z_p  
from &in
where FL_FUP_DISPOSITION in ('V - Domestic Violence Risk')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table aa_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_aa_p  
from &in
where FL_FUP_DISPOSITION in ('X - Patient Deceased')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table ab_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ab_p  
from &in
where FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table ac_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ac_p  
from &in
where FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection')
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table ad_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ad_p  
from &in
where FL_FUP_DISPOSITION is null
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

proc sql;
create table ae_p  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_FL_FUP_KEY , count (distinct inv_local_id) as var_ae_p  
from &in
where FL_FUP_DISPOSITION is not null
group by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_FL_FUP_KEY, PROVIDER_LAST_NAME;quit; 

data &out;
merge g_p h_p i_p n_p o_p p_p q_p r_p t_p
		u_p v_p w_p x_p y_p z_p
		aa_p ab_p ac_p ad_p ae_p;
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
%flds(partners,partnerpa02);
%flds(clus,cluspa02);
%flds(reac,reacpa02);
%flds(cohort,cohpa02);

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


data ind1_1 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_LAST_NAME colname colval );
set partner_final;
length colname $35. ; 
format colname $35. ; 
colname = 'Assigned:';
colval = var_g_p;
output;
colname = 'Dispositioned:';
colval = var_h_p;
output;
colname = "Exam’d:";
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
colname = 'Dispo A:';
colval = var_n_p;

output;
colname = 'Dispo B: ';
colval = var_o_p;

output;
colname = 'Dispo C:';
colval = var_p_p;

output;
colname = 'Dispo D:';
colval = var_q_p;

output;
colname = 'Dispo F:';
colval = var_r_p;

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
colname = "Dispo E:";
colval = var_ac_p;

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


data ind1_2 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_LAST_NAME colname2 colval2 );
set clus_final;
length colname2 $35. ; 
format colname2 $35. ; 
colname2 = 'Assigned:';
colval2 = var_g_p;
output;
colname2 = 'Dispositioned:';
colval2 = var_h_p;
output;
colname2 = "Exam’d:";
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
colname2 = 'Dispo A:';
colval2 = var_n_p;

output;
colname2 = 'Dispo B: ';
colval2 = var_o_p;

output;
colname2 = 'Dispo C:';
colval2 = var_p_p;

output;
colname2 = 'Dispo D:';
colval2 = var_q_p;

output;
colname2 = 'Dispo F:';
colval2 = var_r_p;

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
colname2 = "Dispo E:";
colval2 = var_ac_p;

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



data ind1_3 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_LAST_NAME colname3 colval3 );
set reac_final;
length colname3 $35. ; 
format colname3 $35. ; 
colname3 = 'Assigned:';
colval3 = var_g_p;
output;
colname3 = 'Dispositioned:';
colval3 = var_h_p;
output;
colname3 = "Exam’d:";
colval3 = var_i_p;
output;
colname3 = 'Exam''d w/in 3:';
colval3 = var_j_c;
output;
colname3 = 'Exam''d w/in 5:';
colval3 = var_k_c;
output;
colname3 = 'Exam''d w/in 7:';
colval3 = var_l_c;

output;
colname3 = 'Exam''d w/in 14:';
colval3 = var_m_c;

output;
colname3 = 'Dispo A:';
colval3 = var_n_p;

output;
colname3 = 'Dispo B: ';
colval3 = var_o_p;

output;
colname3 = 'Dispo C:';
colval3 = var_p_p;

output;
colname3 = 'Dispo D:';
colval3 = var_q_p;

output;
colname3 = 'Dispo F:';
colval3 = var_r_p;

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
colname3 = "Dispo E:";
colval3 = var_ac_p;

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


data ind1_4 (keep = INVESTIGATOR_FL_FUP_KEY PROVIDER_LAST_NAME colname4 colval4 );
set coh_final;
length colname4 $35. ; 
format colname4 $35. ; 
colname4 = 'Assigned:';
colval4 = var_g_p;
output;
colname4 = 'Dispositioned:';
colval4 = var_h_p;
output;
colname4 = "Exam’d:";
colval4 = var_i_p;
output;
colname4 = 'Exam''d w/in 3:';
colval4 = var_j_c;
output;
colname4 = 'Exam''d w/in 5:';
colval4 = var_k_c;
output;
colname4 = 'Exam''d w/in 7:';
colval4 = var_l_c;

output;
colname4 = 'Exam''d w/in 14:';
colval4 = var_m_c;

output;
colname4 = 'Dispo A:';
colval4 = var_n_p;

output;
colname4 = 'Dispo B: ';
colval4 = var_o_p;

output;
colname4 = 'Dispo C:';
colval4 = var_p_p;

output;
colname4 = 'Dispo D:';
colval4 = var_q_p;

output;
colname4 = 'Dispo F:';
colval4 = var_r_p;

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
colname4 = "Dispo E:";
colval4 = var_ac_p;

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
proc sort DATA = ind_5 ;
by PROVIDER_LAST_NAME;
run;





options linesize=max;
data templatePA02;
length descrip $35 col1 8 col2 8 col3 8 col4 8 col5 8;
infile datalines truncover dsd dlm='!' ;
input descrip $ col1  col2  col3 col4  col5 ;
datalines ;
Assigned:			! 0	 !0	!0	!0	!0	!	
Dispositioned:		! 0	 !0	!0	!0	!0	!	
Exam’d:				! 0	 !0	!0	!0	!0	!	
Exam'd w/in 3:		! 0	 !0	!0	!0	!0	!	
Exam'd w/in 5:      ! 0	 !0	!0	!0	!0	!	
Exam'd w/in 7:		! 0	 !0	!0	!0	!0	!	
Exam'd w/in 14:		! 0	 !0	!0	!0	!0	!	
Dispo A:			! 0	 !0	!0	!0	!0	!	
Dispo B:			! 0	 !0	!0	!0	!0	!	
Dispo C:			! 0	 !0	!0	!0	!0	!	
Dispo D:			! 0	 !0	!0	!0	!0	!	
Dispo F:			! 0	 !0	!0	!0	!0	!	
Not Examined:		! 0	 !0	!0	!0	!0	!	
Dispo G:			! 0	 !0	!0	!0	!0	!	
Dispo H:			! 0	 !0	!0	!0	!0	!	
Dispo J:			! 0	 !0	!0	!0	!0	!	
Dispo K:			! 0	 !0	!0	!0	!0	!	
Dispo L:			! 0	 !0	!0	!0	!0	!	
Dispo V:			! 0	 !0	!0	!0	!0	!	
Dispo X:			! 0	 !0	!0	!0	!0	!	
Dispo Z:			! 0	 !0	!0	!0	!0	!	
Dispo E:			! 0	 !0	!0	!0	!0	!	
Open:				! 0	 !0	!0	!0	!0	!	
Non-assigned Dispos:! 0	 !0	!0	!0	!0	!	
run;


proc sql noprint;
 select sum(colval) into :val_gp
 from ind_5 where colname = 'Assigned:' 
;

select sum(colval) into :val_hp
 from ind_5 where colname = 'Dispositioned:' 
;

select sum(colval) into :val_ip
 from ind_5 where colname = "Exam’d:" 
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
 from ind_5 where colname = "Dispo A:" 
;
select sum(colval) into :val_op
 from ind_5 where colname = "Dispo B:" 
;
select sum(colval) into :val_Pp
 from ind_5 where colname = "Dispo C:" 
;
select sum(colval) into :val_Qp
 from ind_5 where colname = "Dispo D:" 
;
select sum(colval) into :val_Rp
 from ind_5 where colname = "Dispo F:" 
;
select sum(colval) into :val_Sp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval) into :val_tp
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval) into :val_up
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval) into :val_vp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval) into :val_wp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval) into :val_xp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval) into :val_yp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval) into :val_zp
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval) into :val_aap
 from ind_5 where colname = "Dispo Z:" 
;
select sum(colval) into :val_abp
 from ind_5 where colname = "Dispo E:" 
;

select sum(colval) into :val_acp
 from ind_5 where colname = "Open:" 
;

select sum(colval) into :val_adp
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
data templatePA02;
set templatePA02;
if find(descrip,'Assigned:','i') ge 1 then &name = "&val_gp" ;
if find(descrip,'Dispositioned:','i') ge 1 then &name = "&val_Hp" ;
if find(descrip,'Exam’d:','i') ge 1 then &name = "&val_Ip" ;
if find(descrip,'Exam''d w/in 3:','i') ge 1 then &name = "&val_Jp" ;
if find(descrip,'Exam''d w/in 5:','i') ge 1 then &name = "&val_kp";
if find(descrip,'Exam''d w/in 7:','i') ge 1 then &name = "&val_lp";
if find(descrip,'Exam''d w/in 14:','i') ge 1 then &name = "&val_mp";

if find(descrip,'Dispo A:','i') ge 1 then &name = "&val_np" ;
if find(descrip,'Dispo B:','i') ge 1 then &name = "&val_op" ;
if find(descrip,'Dispo C:','i') ge 1 then &name = "&val_pp" ;
if find(descrip,'Dispo D:','i') ge 1 then &name = "&val_qp" ;
if find(descrip,'Dispo F:','i') ge 1 then &name =  "&val_rp" ;
if find(descrip,'Not Examined:','i') ge 1 then &name =  "&val_sp" ;
if find(descrip,'Dispo G:','i') ge 1 then &name = "&val_tp" ;
if find(descrip,'Dispo H:','i') ge 1 then &name = "&val_up" ;
if find(descrip,'Dispo J:','i') ge 1 then &name = "&val_vp" ;
if find(descrip,'Dispo K:','i') ge 1 then &name = "&val_wp" ;
if find(descrip,'Dispo L:','i') ge 1 then &name = "&val_xp" ;
if find(descrip,'Dispo V:','i') ge 1 then &name =  "&val_yp" ;
if find(descrip,'Dispo X:','i') ge 1 then &name =  "&val_zp" ;
if find(descrip,'Dispo Z:','i') ge 1 then &name =  "&val_aap" ;
if find(descrip,'Dispo E:','i') ge 1 then &name =  "&val_abp" ;
if find(descrip,'Open:','i') ge 1 then &name =  "&val_acp" ;
if find(descrip,'Non-assigned Dispos:','i') ge 1 then &name =  "&Val_adp" ;
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
 from ind_5 where colname = "Exam’d:" 
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
 from ind_5 where colname = "Dispo A:" 
;
select sum(colval2) into :val_op
 from ind_5 where colname = "Dispo B:" 
;
select sum(colval2) into :val_Pp
 from ind_5 where colname = "Dispo C:" 
;
select sum(colval2) into :val_Qp
 from ind_5 where colname = "Dispo D:" 
;
select sum(colval2) into :val_Rp
 from ind_5 where colname = "Dispo F:" 
;
select sum(colval2) into :val_Sp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval2) into :val_tp
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval2) into :val_up
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval2) into :val_vp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval2) into :val_wp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval2) into :val_xp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval2) into :val_yp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval2) into :val_zp
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval2) into :val_aap
 from ind_5 where colname = "Dispo Z:" 
;
select sum(colval2) into :val_abp
 from ind_5 where colname = "Dispo E:" 
;

select sum(colval2) into :val_acp
 from ind_5 where colname = "Open:" 
;

select sum(colval2) into :val_adp
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
 from ind_5 where colname = "Exam’d:" 
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
 from ind_5 where colname = "Dispo A:" 
;
select sum(colval3) into :val_op
 from ind_5 where colname = "Dispo B:" 
;
select sum(colval3) into :val_Pp
 from ind_5 where colname = "Dispo C:" 
;
select sum(colval3) into :val_Qp
 from ind_5 where colname = "Dispo D:" 
;
select sum(colval3) into :val_Rp
 from ind_5 where colname = "Dispo F:" 
;
select sum(colval3) into :val_Sp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval3) into :val_tp
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval3) into :val_up
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval3) into :val_vp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval3) into :val_wp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval3) into :val_xp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval3) into :val_yp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval3) into :val_zp
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval3) into :val_aap
 from ind_5 where colname = "Dispo Z:" 
;
select sum(colval3) into :val_abp
 from ind_5 where colname = "Dispo E:" 
;

select sum(colval3) into :val_acp
 from ind_5 where colname = "Open:" 
;

select sum(colval3) into :val_adp
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
 from ind_5 where colname = "Exam’d:" 
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
 from ind_5 where colname = "Dispo A:" 
;
select sum(colval4) into :val_op
 from ind_5 where colname = "Dispo B:" 
;
select sum(colval4) into :val_Pp
 from ind_5 where colname = "Dispo C:" 
;
select sum(colval4) into :val_Qp
 from ind_5 where colname = "Dispo D:" 
;
select sum(colval4) into :val_Rp
 from ind_5 where colname = "Dispo F:" 
;
select sum(colval4) into :val_Sp
 from ind_5 where colname = "Not Examined:" 
;
select sum(colval4) into :val_tp
 from ind_5 where colname = "Dispo G:" 
;

select sum(colval4) into :val_up
 from ind_5 where colname = "Dispo H:" 
;

select sum(colval4) into :val_vp
 from ind_5 where colname = "Dispo J:" 
;

select sum(colval4) into :val_wp
 from ind_5 where colname = "Dispo K:" 
;

select sum(colval4) into :val_xp
 from ind_5 where colname = "Dispo L:" 
;

select sum(colval4) into :val_yp
 from ind_5 where colname = "Dispo V:" 
;

select sum(colval4) into :val_zp
 from ind_5 where colname = "Dispo X:" 
;

select sum(colval4) into :val_aap
 from ind_5 where colname = "Dispo Z:" 
;
select sum(colval4) into :val_abp
 from ind_5 where colname = "Dispo E:" 
;

select sum(colval4) into :val_acp
 from ind_5 where colname = "Open:" 
;

select sum(colval4) into :val_adp
 from ind_5 where colname = "Non-assigned Dispos:" 
;
quit;

%fill(col4);


proc sql noprint;
 select sum(col1,col2,col3,col4) into :val_gp
 from templatepa02 where descrip = 'Assigned:' 
;

select sum(col1,col2,col3,col4) into :val_hp
 from templatepa02 where descrip = 'Dispositioned:' 
;

select sum(col1,col2,col3,col4) into :val_ip
 from templatepa02 where descrip = "Exam’d:" 
;

select sum(col1,col2,col3,col4) into :val_jp
 from templatepa02 where descrip = "Exam'd w/in 3:" 
;
select sum(col1,col2,col3,col4) into :val_kp
 from templatepa02 where descrip = "Exam'd w/in 5:" 
;
select sum(col1,col2,col3,col4) into :val_lp
 from templatepa02 where descrip = "Exam'd w/in 7:" 
;
select sum(col1,col2,col3,col4) into :val_mp
 from templatepa02 where descrip = "Exam'd w/in 14:" 
;
select sum(col1,col2,col3,col4) into :val_np
 from templatepa02 where descrip = "Dispo A:" 
;
select sum(col1,col2,col3,col4) into :val_op
 from templatepa02 where descrip = "Dispo B:" 
;
select sum(col1,col2,col3,col4) into :val_Pp
 from templatepa02 where descrip = "Dispo C:" 
;
select sum(col1,col2,col3,col4) into :val_Qp
 from templatepa02 where descrip = "Dispo D:" 
;
select sum(col1,col2,col3,col4) into :val_Rp
 from templatepa02 where descrip = "Dispo F:" 
;
select sum(col1,col2,col3,col4) into :val_Sp
 from templatepa02 where descrip = "Not Examined:" 
;
select sum(col1,col2,col3,col4) into :val_tp
 from templatepa02 where descrip = "Dispo G:" 
;

select sum(col1,col2,col3,col4) into :val_up
 from templatepa02 where descrip = "Dispo H:" 
;

select sum(col1,col2,col3,col4) into :val_vp
 from templatepa02 where descrip = "Dispo J:" 
;

select sum(col1,col2,col3,col4) into :val_wp
 from templatepa02 where descrip = "Dispo K:" 
;

select sum(col1,col2,col3,col4) into :val_xp
 from templatepa02 where descrip = "Dispo L:" 
;

select sum(col1,col2,col3,col4) into :val_yp
 from templatepa02 where descrip = "Dispo V:" 
;

select sum(col1,col2,col3,col4) into :val_zp
 from templatepa02 where descrip = "Dispo X:" 
;

select sum(col1,col2,col3,col4) into :val_aap
 from templatepa02 where descrip = "Dispo Z:" 
;
select sum(col1,col2,col3,col4) into :val_abp
 from templatepa02 where descrip = "Dispo E:" 
;

select sum(col1,col2,col3,col4) into :val_acp
 from templatepa02 where descrip = "Open:" 
;

select sum(col1,col2,col3,col4) into :val_adp
 from templatepa02 where descrip = "Non-assigned Dispos:" 
;
quit;


data templatePA02;
set templatePA02;
if find(descrip,'Assigned:','i') ge 1 then col5 = "&val_gp" ;
if find(descrip,'Dispositioned:','i') ge 1 then col5 = "&val_Hp" ;
if find(descrip,'Exam’d:','i') ge 1 then col5 = "&val_Ip" ;
if find(descrip,'Exam''d w/in 3:','i') ge 1 then col5 = "&val_Jp" ;
if find(descrip,'Exam''d w/in 5:','i') ge 1 then col5 = "&val_kp";
if find(descrip,'Exam''d w/in 7:','i') ge 1 then col5 = "&val_lp";
if find(descrip,'Exam''d w/in 14:','i') ge 1 then col5 = "&val_mp";

if find(descrip,'Dispo A:','i') ge 1 then col5 = "&val_np" ;
if find(descrip,'Dispo B:','i') ge 1 then col5 = "&val_op" ;
if find(descrip,'Dispo C:','i') ge 1 then col5 = "&val_pp" ;
if find(descrip,'Dispo D:','i') ge 1 then col5 = "&val_qp" ;
if find(descrip,'Dispo F:','i') ge 1 then col5 =  "&val_rp" ;
if find(descrip,'Not Examined:','i') ge 1 then col5 =  "&val_sp" ;
if find(descrip,'Dispo G:','i') ge 1 then col5 = "&val_tp" ;
if find(descrip,'Dispo H:','i') ge 1 then col5 = "&val_up" ;
if find(descrip,'Dispo J:','i') ge 1 then col5 = "&val_vp" ;
if find(descrip,'Dispo K:','i') ge 1 then col5 = "&val_wp" ;
if find(descrip,'Dispo L:','i') ge 1 then col5 = "&val_xp" ;
if find(descrip,'Dispo V:','i') ge 1 then col5 =  "&val_yp" ;
if find(descrip,'Dispo X:','i') ge 1 then col5 =  "&val_zp" ;
if find(descrip,'Dispo Z:','i') ge 1 then col5 =  "&val_aap" ;
if find(descrip,'Dispo E:','i') ge 1 then col5 =  "&val_abp" ;
if find(descrip,'Open:','i') ge 1 then col5 =  "&val_acp" ;
if find(descrip,'Non-assigned Dispos:','i') ge 1 then col5 =  "&Val_adp" ;
run;



title;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NUMBER NODATE LS=248 PS =200 COMPRESS=NO   missing = 0  
topmargin=0.50 in bottommargin=0.50in leftmargin=0.50in rightmargin=0.50in  nobyline  ;
ods noproctitle;
ods escapechar='^';
ods PDF FILE = "C:\Users\Aroras\Documents\Report\PA02.PDF";
ods layout start width=8.49in height=10.99in;
ods region x = 0.50 in y = 0.25 in ;
ods text =  "^{style[font_face='calibri' fontsize=10pt just=CENTER fontweight=bold ] 
				[&TDAY &time_text] FIELD INVESTIGATION OUTCOMES - STD CDCWKST5}";

ods region x = 0.50 in y = 0.40 in;
ods text = "^{style[font_face='calibri' fontsize=10pt just=center fontweight=bold] 
				WORKER: [Summary of ALL Worker]}";
ods region x = 0.25 in y = 1.00 in width = 7.99 in;
proc report data = templatepa02 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt asis=on};

columns  descrip  col1 col2 col3 col4 col5 ;
define descrip / ORDER=internal "  " style(column)=[asis=on];
define  col1    / DISPLAY left "Part." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  col2    / DISPLAY left "Clus." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  col3    / DISPLAY left "Reac." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  col4    / DISPLAY left "Other" format =  best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  col5    / DISPLAY left "Total" format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];

compute descrip ;
  if find(descrip,'Exam''d w/in 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(descrip,'Exam''d w/in 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(descrip,'Exam''d w/in 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(descrip,'Exam''d w/in 14:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
  if find(descrip,'Dispo A:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo B:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
  if find(descrip,'Dispo C:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo D:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo F:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo G:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
  if find(descrip,'Dispo H:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo J:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo K:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo L:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
    if find(descrip,'Dispo V:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo X:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo Z:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(descrip,'Dispo Q:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   endcomp;

 run;
ods layout end;


/***********2nd part*******************************/

ods proclabel = "Report 1" ;
title1 "[&TDAY &time_text] FIELD INVESTIGATION OUTCOMES - STD CDCWKST5";

TITLE2    j= center " 		WORKER: [Summary of #byval(PROVIDER_LAST_NAME)]	";

proc report data = ind_5 nowd spanrows ls =200  split='~' headline missing 
style(header)={ just=left font_weight=bold font_face="Arial" vjust=m just = c
foreground=black bordercolor=black background=white BORDERTOPCOLOR=white BORDERBOTTOMCOLOR=white
BORDERLEFTCOLOR=white BORDERRIGHTCOLOR=white borderstyle= DOUBLE borderbottomwidth=1
borderbottomstyle = double textdecoration = underline borderbottomstyle = dotted
cellspacing=5 cellpadding = 1 font_size = 9pt rules=none frame=void }
style(report)={font_size=8pt   vjust = middle rules=none frame=void}
style(column)={font_size=8pt  vjust = middle rules=none frame=void asis=on};
by PROVIDER_LAST_NAME ;
columns  PROVIDER_LAST_NAME colname colval colval2 colval3 colvaL4 colvaL5 ;

define PROVIDER_LAST_NAME / group page noprint ;
define colname / ORDER=internal "  " style(column)=[asis=on];
define  colval    / DISPLAY left "Part." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  colval2    / DISPLAY left "Clus." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
/*define blank / ' ' style={cellwidth=10mm};*/
define  colval3    / DISPLAY left "Reac." format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  colval4    / DISPLAY left "Other" format =  best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
define  colval5    / COMPUTED left "Total" format = best9. style = [cellwidth=15mm CELLHEIGHT = 4mm ];
break after PROVIDER_LAST_NAME / page  ; 
compute before _page_ / style = {just = c};
endcomp;
compute colval5;
colval5 = SUM(colval,colval2,colval3,colval4);
endcomp;
compute colname ;
  if find(colname,'Exam''d w/in 3:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(colname,'Exam''d w/in 5:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(colname,'Exam''d w/in 7:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
   if find(colname,'Exam''d w/in 14:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.27in}');
  end;
  if find(colname,'Dispo A:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo B:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
  if find(colname,'Dispo C:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo D:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo F:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo G:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
  if find(colname,'Dispo H:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo J:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo K:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo L:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
    if find(colname,'Dispo V:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo X:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo Z:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
  end;
   if find(colname,'Dispo Q:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.29in}');
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
