/*Program Name : Worker_Activity_PA5																												*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	09-16-2016																												*/
/*																																				*/
/*Program Last Modified Date:10-12-2016																											*/
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
ods listing;


/***************DATA PULL FOR Main Table**************/

/*New Query*/

 proc sql;
create table STD_HIV_DATAMART1 as select a.* from nbs_rdb.STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and  e.INV_CASE_STATUS in ('Probable','Confirmed') and CA_INTERVIEWER_ASSIGN_DT~=.
;
quit;



proc sql;
create table PA05 as 
select  distinct a.INV_LOCAL_ID, IX_TYPE, ix_location, INV_CASE_STATUS, e.record_status_cd, CC_CLOSED_DT, 
ADI_900_STATUS_CD,   HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT,ADI_900_STATUS,A.INVESTIGATION_KEY,
 HIV_900_TEST_IND,source_spread,  FL_FUP_INIT_ASSGN_DT,
 CURR_PROCESS_STATE , CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(IX_DATE)-datepart(CA_INIT_INTVWR_ASSGN_DT)  as Days,
d.provider_last_name
from STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY;
quit;

proc sql;
create table PA05_IXS_INIT as 
select  distinct IX_TYPE, PROVIDER_LAST_NAME, IX_INTERVIEWER_KEY as INVESTIGATOR_INTERVIEW_KEY, a.D_INTERVIEW_KEY, d.D_CONTACT_RECORD_KEY, d.CONTACT_INVESTIGATION_KEY, e.CTT_REFERRAL_BASIS
from nbs_rdb.D_INTERVIEW a 
inner join nbs_rdb.F_INTERVIEW_CASE b on
a.D_INTERVIEW_KEY = b.D_INTERVIEW_KEY 
inner join nbs_rdb.D_provider c on
b.IX_INTERVIEWER_KEY = c.PROVIDER_KEY
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d
on  d.CONTACT_INTERVIEW_KEY =a.D_INTERVIEW_KEY
left outer join nbs_rdb.D_CONTACT_RECORD e
on d.d_contact_record_key = e.d_contact_record_key
where b.IX_INTERVIEWER_KEY in (select INVESTIGATOR_INTERVIEW_KEY from STD_HIV_DATAMART1 where INVESTIGATOR_INTERVIEW_KEY ~=1);
quit;

proc sql;
create table PA05_WRKR as 
select  distinct IX_TYPE, PROVIDER_LAST_NAME, INVESTIGATOR_INTERVIEW_KEY, count(INVESTIGATOR_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table PA05_NCI as 
select  distinct IX_TYPE, PROVIDER_LAST_NAME, INVESTIGATOR_INTERVIEW_KEY, count(INVESTIGATOR_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT where D_CONTACT_RECORD_KEY is null or D_CONTACT_RECORD_KEY=1
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table PA05_PI as 
select  distinct IX_TYPE, PROVIDER_LAST_NAME, INVESTIGATOR_INTERVIEW_KEY, count(INVESTIGATOR_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and 
(D_CONTACT_RECORD_KEY is not null or D_CONTACT_RECORD_KEY ~=1)
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table PA05_CI as 
select  distinct IX_TYPE, PROVIDER_LAST_NAME, INVESTIGATOR_INTERVIEW_KEY, count(INVESTIGATOR_INTERVIEW_KEY) as count_per_wrkr 
from PA05_IXS_INIT where CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and 
(D_CONTACT_RECORD_KEY is not null or D_CONTACT_RECORD_KEY ~=1)
group by IX_TYPE, INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table PA05_DTE as 
select distinct  a.INV_LOCAL_ID, IX_TYPE, INV_CASE_STATUS, e.record_status_cd, CC_CLOSED_DT,
ADI_900_STATUS_CD,   HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT,ADI_900_STATUS,
 HIV_900_TEST_IND,source_spread,  FL_FUP_INIT_ASSGN_DT,
 CURR_PROCESS_STATE , CA_PATIENT_INTV_STATUS,
INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, datepart(IX_DATE)-datepart(CA_INIT_INTVWR_ASSGN_DT)  as Days,
d.provider_last_name
from STD_HIV_DATAMART1 a inner join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
inner join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
where datepart(IX_DATE) GE datepart(CA_INIT_INTVWR_ASSGN_DT)
order by INVESTIGATOR_INTERVIEW_KEY;
quit;



/*---------------------total partners initiated----> R*/
proc sql;
create table pp as 
select distinct  input(a.STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL,  f.FL_FUP_DISPOSITION,
input(a.SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , input(a.SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q,IX_TYPE,
D_provider.provider_last_name, f.inv_local_id , a.CA_PATIENT_INTV_STATUS, a.INVESTIGATOR_INTERVIEW_KEY, a.INVESTIGATOR_INTERVIEW_QC
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.F_INTERVIEW_CASE b
  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c
  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  inner join nbs_rdb.D_provider 
  on D_provider.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  inner join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE g
on  a.investigation_key =g.SUBJECT_investigation_key
inner join nbs_rdb.STD_HIV_DATAMART f on g.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.d_contact_record
on d_contact_record.d_contact_record_key = F_CONTACT_RECORD_CASE.d_contact_record_key
    where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed');
  quit;
  
options missing=0;


 proc freq data = PA05_DTE ;
by INVESTIGATOR_INTERVIEW_KEY PROVIDER_LAST_NAME;
table Days / nocum missing out = dte_freq_ind ;
where Days le 14 and IX_TYPE='Initial/Original';
run;

proc sql;
create table cases_assigned  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_A  
from PA05
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;quit; 
title;

proc sql;
create table cases_open  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_B 
from PA05 
where CC_CLOSED_DT is null
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;quit;

proc sql;
create table cases_closed  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_C 
from PA05 
where CC_CLOSED_DT is not null
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;quit;


proc sql;
create table cases_pending  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_D 
from PA05 
where CA_PATIENT_INTV_STATUS in ('A - Awaiting')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;quit;


proc sql;
create table cases_ixd  as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_E  
from PA05  
where CA_PATIENT_INTV_STATUS in ('I - Interviewed')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit; 


proc sql;
create table f  as 
select DISTINCT  A.PROVIDER_LAST_NAME,  A.INVESTIGATOR_INTERVIEW_KEY,  count (distinct inv_local_id) as Var_F 
from PA05  A
WHERE
IX_TYPE= 'Initial/Original'
AND ix_location='Clinic'
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;



proc sql;
create table G  as 
select DISTINCT  A.PROVIDER_LAST_NAME,  A.INVESTIGATOR_INTERVIEW_KEY,  count (distinct inv_local_id) as Var_G
from PA05  A
WHERE
IX_TYPE= 'Initial/Original'
AND ix_location='Field'
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
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
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_L
from PA05 
where CA_PATIENT_INTV_STATUS  in('O - Other' , 'R - Refused Interview', 'U - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;


proc sql; 
create table M as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_M
from PA05 
where CA_PATIENT_INTV_STATUS  in ('R - Refused Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;


proc sql; 
create table N as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_N
from PA05 
where CA_PATIENT_INTV_STATUS  in ('U - Unable to Locate')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;


proc sql; 
create table O as 
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY , count (distinct inv_local_id) as Var_O
from PA05 
where CA_PATIENT_INTV_STATUS  in ('O - Other')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table p as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_P 
from PA05_WRKR  
WHERE IX_TYPE IN ('Initial/Original')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table q as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_q 
from PA05_NCI
WHERE IX_TYPE IN ('Initial/Original') 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table r as   
select distinct PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, sum(count_q) as var_r
from pp 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME  ;
quit;

proc sql;
create table s as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_s
from PA05_PI 
WHERE IX_TYPE IN ('Initial/Original')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;


proc sql;
create table t as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_t 
from PA05_WRKR  
WHERE IX_TYPE IN ('Re-Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table u as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_u 
from PA05_NCI  
WHERE IX_TYPE IN ('Re-Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table v as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, count_per_wrkr  as var_v
from PA05_PI 
WHERE IX_TYPE IN ('Re-Interview')
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

proc sql;
create table w as   
select PROVIDER_LAST_NAME,  INVESTIGATOR_INTERVIEW_KEY, sum(count_per_wrkr)  as var_w
from PA05_CI 
WHERE IX_TYPE IN ('Initial/Original', 'Re-Interview') 
group by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME
order by INVESTIGATOR_INTERVIEW_KEY, PROVIDER_LAST_NAME;
quit;

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
put "PER_s =  &PER_s";
put "PER_u =  &PER_u";
put "PER_v =  &PER_v";
run;




/*Create template data for overall summary report*/
options linesize=max;
data templatePA05 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NUM. CASES ASSIGNED:		! !0	!		!	!		NUM. OF OI'S:				!	!	!		
	NUM. CASES OPEN:		! !0	!0.0%	!	!			OI'S THAT WERE NCI:		!	!	!		
	NUM. CASES CLOSED:		! !0	!0.0%	!	!			PERIOD PARTNERS:		!	!	!		
	NUM. CASES PENDING:		! !0	!0.0%	!	!			PARTNERS INITIATED:		!	!	!		
							! !	 	!		!	!									!	!	!		
	NUM. CASES IX'D:		! !0	!		!	!		NUM. OF RI'S:				!	!	!		
		CLINIC IX'S:		! !0	!0.0%	!	!			RI'S THAT WERE NCI:		!	!	!		
		FIELD IX'S:			! !0	!0.0%	!	!			PARTNERS INITIATED:		!	!	!		
							! !		!		!	!									!	!	!		
		IX'D W/IN 3 DAYS:	! !0	!0.0%	!	!		CLUSTERS INIT (OI & RI):	!	!	!		
		IX'D W/IN 5 DAYS:	! !0	!0.0%	!	!
		IX'D W/IN 7 DAYS:	! !0	!0.0%	!	!
		IX'D W/IN 14 DAYS:	! !0	!0.0%	!	!
							! !		!		!	!
	NUM. CASES NOT IX'D:	! !0	!		!	!
		REFUSED:			! !0	!0.0%	!	!
		NO LOCATE:			! !0	!0.0%	!	!
		OTHER:				! !0	!0.0%	!	!
run;

options missing = ' '  ;


data ind1_1 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_LAST_NAME colname colval colpct );
set COMBINED;
length colname $35. ; 
format colname $35. ; 
colname = 'NUM. CASES ASSIGNED:';
colval = Var_A;
colpct = .;
output;
colname = '   NUM. CASES OPEN:';
colval = Var_B;
colpct = per_B;
output;
colname = "   NUM. CASES CLOSED:";
colval = Var_C;
colpct = per_c;
output;
colname = "	  NUM. CASES PENDING:";
colval = Var_D;
colpct = per_D;
output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "	  NUM. CASES IX'D:";
colval = Var_E;
colpct = per_E;
output;
colname = "      CLINIC IX'S:";
colval = Var_F;
colpct = per_F;
output;
colname = "      FIELD IX'S:";
colval = Var_G;
colpct = per_G;
output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "      IX’D W/IN 3 DAYS:";
colval = var_H;
colpct = per_H;
output;
colname = "      IX’D W/IN 5 DAYS:";
colval = var_I;
colpct = per_I;
output;
colname = "      IX’D W/IN 7 DAYS:";
colval = var_J;
colpct = per_J;
output;
colname = "      IX’D W/IN 14 DAYS:";
colval = var_K;
colpct = per_K;
output;
colname = ' ';
colval = .;
colpct = .;
output;
colname = "   NUM. CASES NOT IX'D:";
colval = Var_L;
colpct = per_L;
output;
colname = "      REFUSED:";
colval = Var_M;
colpct = per_M;
output;
colname = "      NO LOCATE:";
colval = Var_N;
colpct = per_N;
output;
colname = "      OTHER";
colval = Var_O;
colpct = per_O;
output;
RUN;


data ind1_2 (keep =INVESTIGATOR_INTERVIEW_KEY PROVIDER_LAST_NAME colname2 colval2 colpct2 );
set COMBINED;
length colname2 $35. ; 
format colname2 $35. ; 
colname2 ="NUM. OF OI'S:";
colval2 = Var_P;
colpct2 = .;
output;
colname2 = "   OI'S THAT WERE NCI:";
colval2 = Var_Q;
colpct2 = per_Q;
output;
colname2 = "PERIOD PARTNERS:";
colval2 = Var_R;
colpct2 = per_R;
output;
colname2 = "   PARTNERS INITIATED:";
colval2 = Var_S;
colpct2 = per_S;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 ="NUM. OF RI'S:";
colval2 = Var_T;
colpct2 = .;
output;
colname2 = "   RI'S THAT WERE NCI:";
colval2 = Var_U;
colpct2 = per_U;
output;
colname2 = "PARTNERS. INITIATED: ";
colval2 = Var_V;
colpct2 = per_V;
output;
colname2 = ' ';
colval2 = .;
colpct2 = .;
output;
colname2 = "CLUSTERS INIT (OI & RI):";
colval2 = var_W;
colpct2 = per_W;
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

proc sort data = indiePA5;
by PROVIDER_LAST_NAME;
run;

	

options missing = 0 ; 
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
if find(descrip2,'PARTNERS INITIATED:','i') ge 1 then value2 = "&Val_V" ;
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
if find(descrip2,'PARTNERS INITIATED:','i') ge 1 then percent2 = round("&per_S",0.1) ;

if find(descrip2,'RI''S THAT WERE NCI:','i') ge 1 then percent2 = put(round("&per_U",0.001),percent8.1) ;
if find(descrip2,'PARTNERS INITIATED:','i') ge 1 then percent2 = round("&per_V",0.1) ;
if find(descrip2,'CLUSTERS INIT (OI & RI):','i') ge 1 then percent2 = round("&per_W",0.1) ;

run;





ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   MISSING = ' '  
topmargin=0.50 in bottommargin=0.50in leftmargin=0.50in rightmargin=0.50in  nobyline  ;
ods noproctitle;
ods escapechar = "^";
title ;
footnote ;
%let outpath = C:\NBS\report_output;  
ODS pdf FILE =   "&outpath.\ PA05_Worker_Activity_Rpt_Summary.pdf" style= styles.listing  ;
ods layout start width=8.49in height=10.99in;
ods region x = 1.00 in y = 0.25 in ;
ods text = "^{style[font_face='calibri' fontsize=12pt fontweight=bold ] 
				PA05 WORKER INTERVIEW ACTIVITY-[Assigned Date]}";

ods region x = 1.25 in y = 0.45 in;
ods text = "^{style[font_face='calibri' fontsize=12pt fontweight=bold] 
				REPORT DATE: [&TDAY &time_text]}";


ods region x = 1.50 in y = 0.65 in;
ods text = "^{style[font_face='calibri' fontsize=12pt fontweight=bold ] 
				WORKER: Summary of ALL}";

ods region x = 0.25 in y = 0.85 in width = 3.00 in;
proc report data = templatepa05 nowd spanrows ls =200  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt asis=on};

columns  descrip    value1 percent1 ;
define descrip /display order=internal ' ' left style = [cellwidth=40mm ];
define   value1    / display order=internal ''    style = [cellwidth=15mm ];
define   percent1    / display order=internal ''    style = [cellwidth=15mm ];
 run;

ods region x = 2.50 in y = 0.85 in width = 4.50 in;
proc report data = templatepa05 nowd spanrows ls =200  split='~' headline missing style(header)={just=left font_weight=bold font_face="calibri"
font_size = 8pt }
style(report)={font_size=8pt  rules=none frame=void}
style(column)={font_size=8pt asis=on};
columns  descrip2    value2  percent2;
define descrip2 /display order=internal ' ' left style = [cellwidth=40mm ];
define   value2    / display order=internal ''    style = [cellwidth=15mm ];
define   percent2   / display order=internal ''    style = [cellwidth=20mm ];
 run;
ods layout end;
ods proclabel = "Report 1" ;
title1  font="calibri"
font = 8  "PA05 WORKER INTERVIEW ACTIVITY-[Assigned Date] " ;
TITLE2  font="calibri"
font = 8   j= center "REPORT DATE: [&TDAY &time_text] " ;
TITLE3  font="calibri"
font = 8   j= center " 		WORKER: [Summary of #byval(PROVIDER_LAST_NAME)]	";

proc report data = indiePA5 nowd spanrows ls =200  split='~' headline missing style(header)={just=center font_weight=bold font_face="calibri"
font_size = 10pt }
style(report)={font_size=8pt cellpadding=2pt
cellspacing=1 rules=none
frame=void }
style(column)={font_size=8pt asis=on};
by PROVIDER_LAST_NAME ;
columns  PROVIDER_LAST_NAME colname colval colpct colname2 colval2 colpct2;

define PROVIDER_LAST_NAME / group page noprint ;
define colname /DISPLAY "  " style = [cellwidth=40mm ];
define  colval    / DISPLAY " " format = best9. style = [cellwidth=15mm ];
define colpct /DISPLAY " "  format = percent8.1 style = [cellwidth=15mm ];
define colname2 /DISPLAY "  " style = [cellwidth=40mm ];
define  colval2    / DISPLAY " " format = best9. style = [cellwidth=15mm ];
define colpct2 /DISPLAY " "  format = percent8.1  style = [cellwidth=15mm ];
break after PROVIDER_LAST_NAME / page  ; 
compute before _page_ / style = {just = c};
endcomp;
run;

ods pdf close;

 /*delete work data */
/*proc datasets kill lib = work nolist memtype=data;*/
/*quit;*/


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





