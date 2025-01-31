/*Program Name : PA04_Std.sas																													*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	11-21-2016																												*/
/*																																				*/
/*Program Last Modified Date:12-16-2016																											*/
/*							:01-04-2017: Formatting changes.																													*/
/*							:																													*/
/*Program Description:	Creates PA04 Program Indicator STD report for NBS5.1 																	*/
/*																																				*/
/*Comments:	
*/

data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;
 
%macro PA04_STD;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

  
proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') and a.CA_INTERVIEWER_ASSIGN_DT~=. and a.CC_CLOSED_DT~=. and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed');
quit;

options missing =  ' ' ;
proc sql;
	create table PA04 as 
	select  distinct a.INV_LOCAL_ID, c.IX_TYPE, e.INV_CASE_STATUS, e.record_status_cd, a.CC_CLOSED_DT, 
	a.ADI_900_STATUS_CD,   a.HIV_POST_TEST_900_COUNSELING, a.HIV_900_RESULT, a.ADI_900_STATUS,
	a.HIV_900_TEST_IND, a.source_spread,  a.FL_FUP_INIT_ASSGN_DT, d_contact_record.CTT_PROCESSING_DECISION,
	e.CURR_PROCESS_STATE , a.CA_PATIENT_INTV_STATUS, f.contact_investigation_key,
	INVESTIGATOR_INTERVIEW_KEY, INVESTIGATOR_INTERVIEW_QC, a.DIAGNOSIS_CD,
	input(a.STD_PRTNRS_PRD_TRNSGNDR_TTL , best9.) as STD_PRTNRS_TRNSGNDR_TTL, 
	input(a.SOC_PRTNRS_PRD_FML_TTL, best9.) as SOC_PRTNRS_FML_TTL , 
	input(a.SOC_PRTNRS_PRD_MALE_TTL,best9.) as SOC_PRTNRS_MALE_TTL,
	sum(calculated STD_PRTNRS_TRNSGNDR_TTL, calculated SOC_PRTNRS_FML_TTL,calculated SOC_PRTNRS_MALE_TTL) as count_Q,
	d.provider_last_name
	from STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
	left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
	left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
	left outer join nbs_rdb.F_CONTACT_RECORD_CASE f on f.Subject_investigation_key = e.INVESTIGATION_KEY 
	left outer join nbs_rdb.F_INTERVIEW_CASE on
	F_INTERVIEW_CASE.INVESTIGATION_KEY = e.investigation_key
	left outer join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = F_INTERVIEW_CASE.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
	left outer join nbs_rdb.d_contact_record 
on d_contact_record.d_contact_record_key = F_CONTACT_RECORD_CASE.d_contact_record_key
and d_contact_record.RECORD_STATUS_CD~='LOG_DEL' 
;
quit;

options missing = 0 ;

proc sql noprint;
select count (distinct inv_local_id) into :Val_A 
from PA04 
where CC_CLOSED_DT is not null   ;

select count (distinct inv_local_id) into :Val_B   
from PA04  
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
;
 
select  count(distinct inv_local_id) into :Val_C 
from PA04 where CC_CLOSED_DT is not null and
CA_PATIENT_INTV_STATUS in ('I - Interviewed') and (contact_investigation_key=1)
;

select count (distinct inv_local_id) into :Val_D  
from PA04  
where CC_CLOSED_DT is not null and
CA_PATIENT_INTV_STATUS in ('I - Interviewed') and IX_TYPE in ('Re-Interview')
;


select  count(distinct inv_local_id) into :Val_E 
from PA04 where CC_CLOSED_DT is not null and
CA_PATIENT_INTV_STATUS in ('I - Interviewed') and IX_TYPE in ('Re-Interview')
and (contact_investigation_key=1) 
;

 
select count (distinct inv_local_id) into :Val_G 
from PA04  
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed') 
and HIV_900_TEST_IND = 'Yes'
;

select count (distinct inv_local_id) into :Val_H 
from PA04  
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed') 
and HIV_900_RESULT in  ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive');


select count (distinct inv_local_id) into :Val_i
from PA04  
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed') and
HIV_POST_TEST_900_COUNSELING = 'Yes'
;

select sum(count_q) into :Val_J 
from
(select distinct inv_local_id, count_q
from PA04  
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed') and count_q is not null) J_INIT
;
quit; 


%let PER_C = %sysevalf(%eval(&Val_C) / %eval(&Val_B)) ;
%let PER_D = %sysevalf(%eval(&Val_D) / %eval(&Val_B)) ;
%let PER_E = %sysevalf(%eval(&Val_E) / %eval(&Val_D)) ;

%let PER_G = %sysevalf(%eval(&Val_G) / %eval(&Val_A)) ;
%let PER_H = %sysevalf(%eval(&Val_H) / %eval(&Val_A)) ;
%let PER_I = %sysevalf(%eval(&Val_I) / %eval(&Val_G)) ;

%let Val_K = %sysevalf(%eval(&Val_J) / %eval(&Val_B)) ;	


data _null_;
put "Val_A =  &Val_A";
put "Val_B =  &Val_B";
put "Val_C =  &Val_C";
put "Val_D =  &Val_D";
put "Val_E =  &Val_E";
put "Val_G =  &Val_G";
put "Val_H =  &Val_H";
put "Val_I =  &Val_I";

put "Val_J =  &Val_J";
put "Val_K =  &Val_K";
put "PER_C =  &PER_C";
put "PER_D =  &PER_D";
put "PER_E =  &PER_E";

put "PER_G =  &PER_H";
put "PER_H =  &PER_H";
put "PER_I =  &PER_I";
run;

filename NBSPGMp1  "&SAS_REPORT_HOME/template";
%include NBSPGMp1 (Template10.sas);



data templatePA04_STDD;
set templatePA04_STDD;
if find(descrip,'Cases closed:','i') ge 1 then value1 = "&Val_A" ;
if find(descrip,'Cases interviewed:','i') ge 1 then value1 = "&Val_B" ;
if find(descrip,'Cases NCI:','i') ge 1 then value1 = "&Val_C" ;
if find(descrip,'Cases re-interviewed:','i') ge 1 then value1 = "&Val_D" ;
if find(descrip,'Cases NCI with re-interview:','i') ge 1 then value1 = "&Val_E" ;
if find(descrip,'Cases HIV Tested:','i') ge 1 then value1 = "&Val_G" ;
if find(descrip,'Cases HIV SeroPositive:','i') ge 1 then value1 = "&Val_H" ;
if find(descrip,'Cases PostTest Counseled:','i') ge 1 then value1 = "&Val_I" ;
if find(descrip,'Total period partners:','i') ge 1 then value1 = "&Val_J" ;
if find(descrip,'Period partner index:','i') ge 1 then value1 = ROUND("&Val_K",0.01) ;

if find(descrip,'Cases NCI:','i') ge 1 then percent1 = put(round("&PER_C",0.001),percent8.1)  ;
if find(descrip,'Cases re-interviewed:','i') ge 1 then percent1 = put(round("&PER_D",0.001),percent8.1)  ;
if find(descrip,'Cases NCI with re-interview:','i') ge 1 then percent1 = put(round("&PER_E",0.001),percent8.1)  ;
if find(descrip,'Cases HIV Tested:','i') ge 1 then percent1 = put(round("&PER_G",0.001),percent8.1)  ;
if find(descrip,'Cases HIV SeroPositive:','i') ge 1 then percent1 = put(round("&PER_h",0.001),percent8.1)  ;
if find(descrip,'Cases PostTest Counseled:','i') ge 1 then percent1 = put(round("&PER_i",0.001),percent8.1)  ;
run;


options missing =  ' ' ;

proc sql;
create table PP04_OI as 
select distinct f.FL_FUP_DISPOSITION, d_contact_record.CTT_PROCESSING_DECISION, c.IX_TYPE, a.CC_CLOSED_DT, f.INV_LOCAL_ID , a.CA_PATIENT_INTV_STATUS

from STD_HIV_DATAMART1 a inner join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_provider d on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE g on  a.investigation_key =g.SUBJECT_investigation_key
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = g.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.STD_HIV_DATAMART f on f.Investigation_key = g.CONTACT_INVESTIGATION_KEY 
left outer join nbs_rdb.investigation e on e.investigation_key =a.investigation_KEY
left outer join nbs_rdb.d_contact_record on d_contact_record.d_contact_record_key = g.d_contact_record_key and d_contact_record.RECORD_STATUS_CD~='LOG_DEL'
where d_contact_record.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') 
and a.CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and d_contact_record.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
and g.CONTACT_INTERVIEW_KEY ~=1 
;
quit;




 proc sql;
create table cluster as 
select distinct f.FL_FUP_DISPOSITION, d_contact_record.CTT_PROCESSING_DECISION, c.IX_TYPE, a.CC_CLOSED_DT, f.INV_LOCAL_ID , a.CA_PATIENT_INTV_STATUS
from STD_HIV_DATAMART1 a inner join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
inner join nbs_rdb.D_provider d on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE g on  a.investigation_key =g.SUBJECT_investigation_key
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = g.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.STD_HIV_DATAMART f on f.Investigation_key = g.CONTACT_INVESTIGATION_KEY 
inner join nbs_rdb.investigation e on e.investigation_key =a.investigation_KEY
inner join nbs_rdb.d_contact_record on d_contact_record.d_contact_record_key = g.d_contact_record_key and d_contact_record.RECORD_STATUS_CD~='LOG_DEL'
where
 d_contact_record.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3','C1- Cohort')
and  a.CA_PATIENT_INTV_STATUS in ('I - Interviewed') and 
d_contact_record.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
and g.CONTACT_INTERVIEW_KEY ~=1;
quit;

/*********************************************************************************************************************************/


options missing =  0 ;
proc sql noprint;
select count(distinct inv_local_id) into :Val_PM 
from PP04_OI
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and IX_TYPE IN ('Initial/Original');

select count(distinct inv_local_id) into :Val_Pn 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION 
in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Pa 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pc 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Pb 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pd
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Pe
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Pf
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Pq
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pg
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ph
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Pj
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Pk
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Pl
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Pv
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Px
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Pz
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');


select  count(distinct a.inv_local_id) into :Val_TWO 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and 
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Initial/Original')
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;



select  count(distinct a.inv_local_id) into :Val_three 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where   g.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3','C1- Cohort') and  
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Initial/Original')
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :Val_CM 
from cluster
where CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original');

select count(distinct inv_local_id) into :Val_Cn 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Ca 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cc 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and  IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Cb 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cd
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Ce
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Cf
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Cq
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cg
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ch
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Cj
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Ck
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Cl
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Cv
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Cx
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Cz
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');
quit;

%let PER_PM = %sysevalf(%eval(&Val_PM) / %eval(&Val_B)) ;
%let PER_PN = %sysevalf(%eval(&Val_PN) / %eval(&Val_PM)) ;
%let PER_PA = %sysevalf(%eval(&Val_PA) / %eval(&VAL_PN)) ;
%let PER_PB = %sysevalf(%eval(&Val_PB) / %eval(&VAL_PN)) ;
%let PER_PC = %sysevalf(%eval(&Val_PC) / %eval(&VAL_PN)) ;
%let PER_PD = %sysevalf(%eval(&Val_PD) / %eval(&VAL_PN)) ;
%let PER_PE = %sysevalf(%eval(&Val_PE) / %eval(&VAL_PN)) ;
%let PER_PF = %sysevalf(%eval(&Val_PF) / %eval(&VAL_PN)) ;
%let PER_PQ = %sysevalf(%eval(&Val_PQ) / %eval(&Val_PM)) ;

%let PER_PG = %sysevalf(%eval(&Val_PG) / %eval(&Val_PQ)) ;
%let PER_PH = %sysevalf(%eval(&Val_PH) / %eval(&Val_PQ)) ;
%let PER_PJ = %sysevalf(%eval(&Val_PJ) / %eval(&Val_PQ)) ;
%let PER_PK = %sysevalf(%eval(&Val_PK) / %eval(&Val_PQ)) ;
%let PER_PL = %sysevalf(%eval(&Val_PL) / %eval(&Val_PQ)) ;
%let PER_PV = %sysevalf(%eval(&Val_PV) / %eval(&Val_PQ)) ;
%let PER_PX = %sysevalf(%eval(&Val_PX) / %eval(&Val_PQ)) ;
%let PER_PZ = %sysevalf(%eval(&Val_PZ) / %eval(&Val_PQ)) ;


%let PER_CM = %sysevalf(%eval(&Val_CM) / %eval(&Val_B)) ;
%let PER_CN = %sysevalf(%eval(&Val_CN) / %eval(&Val_CM)) ;
%let PER_CA = %sysevalf(%eval(&Val_CA) / %eval(&VAL_CN)) ;
%let PER_CB = %sysevalf(%eval(&Val_CB) / %eval(&VAL_CN)) ;
%let PER_CC = %sysevalf(%eval(&Val_CC) / %eval(&VAL_CN)) ;
%let PER_CD = %sysevalf(%eval(&Val_CD) / %eval(&VAL_CN)) ;
%let PER_CE = %sysevalf(%eval(&Val_CE) / %eval(&VAL_CN)) ;
%let PER_CF = %sysevalf(%eval(&Val_CF) / %eval(&VAL_CN)) ;
%let PER_CQ = %sysevalf(%eval(&Val_CQ) / %eval(&Val_CM)) ;

%let PER_CG = %sysevalf(%eval(&Val_CG) / %eval(&Val_CQ)) ;
%let PER_CH = %sysevalf(%eval(&Val_CH) / %eval(&Val_CQ)) ;
%let PER_CJ = %sysevalf(%eval(&Val_CJ) / %eval(&Val_CQ)) ;
%let PER_CK = %sysevalf(%eval(&Val_CK) / %eval(&Val_CQ)) ;
%let PER_CL = %sysevalf(%eval(&Val_CL) / %eval(&Val_CQ)) ;
%let PER_CV = %sysevalf(%eval(&Val_CV) / %eval(&Val_CQ)) ;
%let PER_CX = %sysevalf(%eval(&Val_CX) / %eval(&Val_CQ)) ;
%let PER_CZ = %sysevalf(%eval(&Val_CZ) / %eval(&Val_CQ)) ;

/*treatment index*/
%let VAL_one = %sysevalf(%eval(&Val_PA) + %eval(&Val_PC)+ %eval(&Val_PE)) ;
%let VAL_PO = %sysevalf(%eval(&VAL_one) / %eval(&Val_B)) ;

/*DI index*/

%let VAL_PP = %sysevalf(%eval(&VAL_TWO) / %eval(&Val_B)) ;

/*treatment index*/
%let VAL_tw = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC) + %eval(&Val_CE)) ;
%let VAL_CO = %sysevalf(%eval(&VAL_tw) / %eval(&Val_B)) ;


/*DI index*/
/*%let VAL_twoo = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC));*/
%let VAL_CP = %sysevalf(%eval(&VAL_three) / %eval(&Val_B)) ;

/*********************************************************************************************************************************/


data _null_;
put "Val_PM =  &Val_PM";
put "Val_PN =  &Val_PN";
put "Val_PA =  &Val_PA";
put "Val_PB =  &Val_PB";
put "Val_PC =  &Val_PC";
put "Val_PQ =  &Val_PQ";
put "Val_PO =  &Val_PO";
put "Val_PP =  &Val_PP";

put "Val_CM =  &Val_CM";
put "Val_CN =  &Val_CN";
put "Val_CA =  &Val_CA";
put "Val_CB =  &Val_CB";
put "Val_CC =  &Val_CC";
put "Val_CQ =  &Val_CQ";
put "Val_CO =  &Val_CO";
put "Val_Cp =  &Val_CP";

put "PER_PM =  &PER_PM";
put "PER_PN =  &PER_PN";
put "PER_PA =  &PER_PA";
put "PER_PB =  &PER_PB";
put "PER_PC =  &PER_PC";
put "PER_PQ =  &PER_PQ";

put "PER_CM =  &PER_CM";
put "PER_CN =  &PER_CN";
put "PER_CA =  &PER_CA";
put "PER_CB =  &PER_CB";
put "PER_CC =  &PER_CC";
put "PER_CQ =  &PER_CQ";

RUN;

%macro fills(name,per) ; 
data templatePA04;
set templatePA04;
if find(descrip,'PARTNERS INITIATED:','i') ge 1 then &name = "&val_PM" ;
if find(descrip,'PARTNERS EXAMINED:','i') ge 1 then &name = "&val_PN" ;
if find(descrip,'DISPO. A - PREVENTIVE TX:','i') ge 1 then &name = "&val_PA" ;
if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1 then &name = "&val_PC" ;
if find(descrip,'TREAT. INDEX.:','i') ge 1 then &name = ROUND("&val_PO",0.01);
if find(descrip,'DI. INDEX.:','i') ge 1 then &name = ROUND("&val_PP",0.01);
if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1 then &name = "&val_PB";
if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1 then &name = "&val_PD";

if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1 then &name = "&val_PE" ;
if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1 then &name = "&val_PF" ;
if find(descrip,'PARTNERS NOT EXAMINED:','i') ge 1 then &name = "&val_PQ" ;
if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1 then &name = "&val_PG" ;
if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1 then &name =  "&val_PH" ;
if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1 then &name =  "&val_PJ" ;
if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1 then &name = "&val_PK" ;
if find(descrip,'DISPO. L - OTHER:','i') ge 1 then &name = "&val_PL" ;
if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1 then &name =  "&val_PV" ;
if find(descrip,'DISPO. X - DECEASED:','i') ge 1 then &name = "&val_PX" ;
if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1 then &name = "&val_PZ" ;

if find(descrip,'CLUSTERS INITIATED:','i') ge 1 then &name = "&val_CM" ;
if find(descrip,'CLUSTERS EXAMINED:','i') ge 1 then &name = "&val_CN" ;
if find(descrip,'DISPO. A - PREVENTIVE TX:','i') ge 1 then &name = "&val_CA" ;
if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1 then &name = "&val_CC" ;
if find(descrip,'TREAT. INDEX:','i') ge 1 then &name = ROUND("&val_CO",0.01);
if find(descrip,'DI. INDEX:','i') ge 1 then &name = ROUND("&val_CP",0.01);
if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1 then &name = "&val_CB";
if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1 then &name = "&val_CD";

if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1 then &name = "&val_CE" ;
if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1 then &name = "&val_CF" ;
if find(descrip,'CLUSTERS NOT EXAMINED:','i') ge 1 then &name = "&val_CQ" ;
if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1 then &name = "&val_CG" ;
if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1 then &name =  "&val_CH" ;
if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1 then &name =  "&val_CJ" ;
if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1 then &name = "&val_CK" ;
if find(descrip,'DISPO. L - OTHER:','i') ge 1 then &name = "&val_CL" ;
if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1 then &name =  "&val_CV" ;
if find(descrip,'DISPO. X - DECEASED:','i') ge 1 then &name = "&val_CX" ;
if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1 then &name = "&val_CZ" ;

/*if find(descrip,'PARTNERS INITIATED:','i') ge 1 then &per = put(round("&per_PM",0.001),percent8.1) ;*/
if find(descrip,'PARTNERS EXAMINED:','i') ge 1 then &per = put(round("&per_PN",0.001),percent8.1) ;
if find(descrip,'DISPO. A - PREVENTIVE TX:','i') ge 1 then &per = put(round("&per_PA",0.001),percent8.1) ;
if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1 then &per = put(round("&per_PC",0.001),percent8.1) ;

if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1 then &per = put(round("&per_PB",0.001),percent8.1) ;
if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1 then &per = put(round("&per_PD",0.001),percent8.1) ;
if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1 then &per = put(round("&per_PE",0.001),percent8.1) ;
if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1 then &per = put(round("&per_PF",0.001),percent8.1) ;
if find(descrip,'PARTNERS NOT EXAMINED:','i') ge 1 then &per = put(round("&per_PQ",0.001),percent8.1) ;
if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1 then &per = put(round("&per_PG",0.001),percent8.1) ;
if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1 then &per = put(round("&per_PH",0.001),percent8.1) ;
if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1 then &per = put(round("&per_PJ",0.001),percent8.1) ;
if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1 then &per = put(round("&per_PK",0.001),percent8.1) ;
if find(descrip,'DISPO. L - OTHER:','i') ge 1 then &per = put(round("&per_PL",0.001),percent8.1) ;
if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1 then &per = put(round("&per_PV",0.001),percent8.1) ;
if find(descrip,'DISPO. X - DECEASED:','i') ge 1 then &per = put(round("&per_PX",0.001),percent8.1) ;
if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1 then &per = put(round("&per_PZ",0.001),percent8.1) ;

/*if find(descrip,'CLUSTERS INITIATED:','i') ge 1 then &per = put(round("&per_CM",0.001),percent8.1) ;*/
if find(descrip,'CLUSTERS EXAMINED:','i') ge 1 then &per = put(round("&per_CN",0.001),percent8.1);
if find(descrip,'DISPO. A - PREVENTIVE TX:','i') ge 1 then &per = put(round("&per_CA",0.001),percent8.1) ;
if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1 then &per = put(round("&per_CC",0.001),percent8.1) ;

if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1 then &per = put(round("&per_CB",0.001),percent8.1) ;
if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1 then &per = put(round("&per_CD",0.001),percent8.1) ;
if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1 then &per = put(round("&per_CE",0.001),percent8.1) ;
if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1 then &per = put(round("&per_CF",0.001),percent8.1) ;
if find(descrip,'CLUSTERS NOT EXAMINED:','i') ge 1 then &per = put(round("&per_CQ",0.001),percent8.1) ;
if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1 then &per = put(round("&per_CG",0.001),percent8.1) ;
if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1 then &per = put(round("&per_CH",0.001),percent8.1) ;
if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1 then &per = put(round("&per_CJ",0.001),percent8.1) ;
if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1 then &per = put(round("&per_CK",0.001),percent8.1) ;
if find(descrip,'DISPO. L - OTHER:','i') ge 1 then &per = put(round("&per_CL",0.001),percent8.1) ;
if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1 then &per = put(round("&per_CV",0.001),percent8.1) ;
if find(descrip,'DISPO. X - DECEASED:','i') ge 1 then &per = put(round("&per_CX",0.001),percent8.1) ;
if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1 then &per = put(round("&per_CZ",0.001),percent8.1) ;
run;
%Mend ;
%fills(col1,col2);


/*********************************************************************************************************************************/


proc sql noprint;
select count(distinct inv_local_id) into :Val_PM 
from PP04_OI
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and IX_TYPE IN ('Re-Interview');

select count(distinct inv_local_id) into :Val_Pn 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION 
in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Pa 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pc 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Pb 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pd
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Pe
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Pf
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Pq
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pg
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ph
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Pj
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Pk
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Pl
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Pv
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Px
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Pz
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');

select  count(distinct a.inv_local_id) into :Val_TWO 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join  nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL' 
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and 
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Re-Interview')
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;



select  count(distinct a.inv_local_id) into :Val_three 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where   g.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3','C1- Cohort') and  
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Re-Interview')
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :Val_CM 
from cluster
where CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview');

select count(distinct inv_local_id) into :Val_Cn 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Ca 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cc 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and  IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Cb 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cd
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Ce
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Cf
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Cq
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cg
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ch
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Cj
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Ck
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Cl
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Cv
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Cx
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Cz
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Re-Interview') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');
quit;

%let PER_PM = %sysevalf(%eval(&Val_PM) / %eval(&Val_B)) ;
%let PER_PN = %sysevalf(%eval(&Val_PN) / %eval(&Val_PM)) ;
%let PER_PA = %sysevalf(%eval(&Val_PA) / %eval(&VAL_PN)) ;
%let PER_PB = %sysevalf(%eval(&Val_PB) / %eval(&VAL_PN)) ;
%let PER_PC = %sysevalf(%eval(&Val_PC) / %eval(&VAL_PN)) ;
%let PER_PD = %sysevalf(%eval(&Val_PD) / %eval(&VAL_PN)) ;
%let PER_PE = %sysevalf(%eval(&Val_PE) / %eval(&VAL_PN)) ;
%let PER_PF = %sysevalf(%eval(&Val_PF) / %eval(&VAL_PN)) ;
%let PER_PQ = %sysevalf(%eval(&Val_PQ) / %eval(&Val_PM)) ;

%let PER_PG = %sysevalf(%eval(&Val_PG) / %eval(&Val_PQ)) ;
%let PER_PH = %sysevalf(%eval(&Val_PH) / %eval(&Val_PQ)) ;
%let PER_PJ = %sysevalf(%eval(&Val_PJ) / %eval(&Val_PQ)) ;
%let PER_PK = %sysevalf(%eval(&Val_PK) / %eval(&Val_PQ)) ;
%let PER_PL = %sysevalf(%eval(&Val_PL) / %eval(&Val_PQ)) ;
%let PER_PV = %sysevalf(%eval(&Val_PV) / %eval(&Val_PQ)) ;
%let PER_PX = %sysevalf(%eval(&Val_PX) / %eval(&Val_PQ)) ;
%let PER_PZ = %sysevalf(%eval(&Val_PZ) / %eval(&Val_PQ)) ;


%let PER_CM = %sysevalf(%eval(&Val_CM) / %eval(&Val_B)) ;
%let PER_CN = %sysevalf(%eval(&Val_CN) / %eval(&Val_CM)) ;
%let PER_CA = %sysevalf(%eval(&Val_CA) / %eval(&VAL_CN)) ;
%let PER_CB = %sysevalf(%eval(&Val_CB) / %eval(&VAL_CN)) ;
%let PER_CC = %sysevalf(%eval(&Val_CC) / %eval(&VAL_CN)) ;
%let PER_CD = %sysevalf(%eval(&Val_CD) / %eval(&VAL_CN)) ;
%let PER_CE = %sysevalf(%eval(&Val_CE) / %eval(&VAL_CN)) ;
%let PER_CF = %sysevalf(%eval(&Val_CF) / %eval(&VAL_CN)) ;
%let PER_CQ = %sysevalf(%eval(&Val_CQ) / %eval(&Val_CM)) ;

%let PER_CG = %sysevalf(%eval(&Val_CG) / %eval(&Val_CQ)) ;
%let PER_CH = %sysevalf(%eval(&Val_CH) / %eval(&Val_CQ)) ;
%let PER_CJ = %sysevalf(%eval(&Val_CJ) / %eval(&Val_CQ)) ;
%let PER_CK = %sysevalf(%eval(&Val_CK) / %eval(&Val_CQ)) ;
%let PER_CL = %sysevalf(%eval(&Val_CL) / %eval(&Val_CQ)) ;
%let PER_CV = %sysevalf(%eval(&Val_CV) / %eval(&Val_CQ)) ;
%let PER_CX = %sysevalf(%eval(&Val_CX) / %eval(&Val_CQ)) ;
%let PER_CZ = %sysevalf(%eval(&Val_CZ) / %eval(&Val_CQ)) ;

/*treatment index*/
%let VAL_one = %sysevalf(%eval(&Val_PA) + %eval(&Val_PC)+ %eval(&Val_PE)) ;
%let VAL_PO = %sysevalf(%eval(&VAL_one) / %eval(&Val_B)) ;

/*DI index*/

%let VAL_PP = %sysevalf(%eval(&VAL_TWO) / %eval(&Val_B)) ;

/*treatment index*/
%let VAL_tw = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC) + %eval(&Val_CE)) ;
%let VAL_CO = %sysevalf(%eval(&VAL_tw) / %eval(&Val_B)) ;


/*DI index*/
/*%let VAL_twoo = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC));*/
%let VAL_CP = %sysevalf(%eval(&VAL_three) / %eval(&Val_B)) ;

/*********************************************************************************************************************************/


data _null_;
put "Val_PM =  &Val_PM";
put "Val_PN =  &Val_PN";
put "Val_PA =  &Val_PA";
put "Val_PB =  &Val_PB";
put "Val_PC =  &Val_PC";
put "Val_PQ =  &Val_PQ";
put "Val_PO =  &Val_PO";
put "Val_PP =  &Val_PP";

put "Val_CM =  &Val_CM";
put "Val_CN =  &Val_CN";
put "Val_CA =  &Val_CA";
put "Val_CB =  &Val_CB";
put "Val_CC =  &Val_CC";
put "Val_CQ =  &Val_CQ";
put "Val_CO =  &Val_CO";
put "Val_Cp =  &Val_CP";

put "PER_PM =  &PER_PM";
put "PER_PN =  &PER_PN";
put "PER_PA =  &PER_PA";
put "PER_PB =  &PER_PB";
put "PER_PC =  &PER_PC";
put "PER_PQ =  &PER_PQ";

put "PER_CM =  &PER_CM";
put "PER_CN =  &PER_CN";
put "PER_CA =  &PER_CA";
put "PER_CB =  &PER_CB";
put "PER_CC =  &PER_CC";
put "PER_CQ =  &PER_CQ";

RUN;

%fills(col3,col4);

/*********************************************************************************************************************************/


proc sql noprint;
select count(distinct inv_local_id) into :Val_PM 
from PP04_OI
where CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and IX_TYPE IN ('Initial/Original','Re-Interview');

select count(distinct inv_local_id) into :Val_Pn 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION 
in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Pa 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pc 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Pb 
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pd
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Pe
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Pf
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Pq
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Pg
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ph
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Pj
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Pk
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Pl
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Pv
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Px
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Pz
from PP04_OI
WHERE CC_CLOSED_DT is not null and CA_PATIENT_INTV_STATUS in ('I - Interviewed')
and
IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');

select  count(distinct a.inv_local_id||c.IX_TYPE ) into :Val_four 
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where g.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and 
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Initial/Original','Re-Interview' )
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;




select  count(distinct a.inv_local_id||c.IX_TYPE ) into :Val_five
from STD_HIV_DATAMART1 a
inner join nbs_rdb.investigation e  on e.investigation_key = a.investigation_KEY 
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on d.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.D_CONTACT_RECORD g on g.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and g.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_INTERVIEW c on  c.D_INTERVIEW_KEY = d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join nbs_rdb.D_provider d  on d.provider_key = a.INVESTIGATOR_INTERVIEW_KEY
where   g.CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3','C1- Cohort') and  
f.FL_FUP_DISPOSITION in ('A - Preventative Treatment','C - Infected, Brought to Treatment'
,'E - Previously Treated for This Infection') and
a.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL AND c.IX_TYPE IN ('Re-Interview','Initial/Original')
and g.CTT_PROCESSING_DECISION in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :Val_CM 
from cluster
where CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview');

select count(distinct inv_local_id) into :Val_Cn 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment',
'B - Refused Preventative Treatment',
'C - Infected, Brought to Treatment',
'D - Infected, Not Treated',
'E - Previously Treated for This Infection',
'F - Not Infected');

select count(distinct inv_local_id) into :Val_Ca 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('A - Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cc 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and  IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('C - Infected, Brought to Treatment');

select count(distinct inv_local_id) into :Val_Cb 
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('B - Refused Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cd
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('D - Infected, Not Treated');

select count(distinct inv_local_id) into :Val_Ce
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('E - Previously Treated for This Infection');

select count(distinct inv_local_id) into :Val_Cf
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('F - Not Infected');

select count(distinct inv_local_id) into :Val_Cq
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation',
'H - Unable to Locate',
'J - Located, Not Examined and/or Interviewed',
'K - Sent Out Of Jurisdiction',
'L - Other',
'V - Domestic Violence Risk',
'X - Patient Deceased',
'Z - Previous Preventative Treatment');

select count(distinct inv_local_id) into :Val_Cg
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('G - Insufficient Info to Begin Investigation');

select count(distinct inv_local_id) into :Val_Ch
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('H - Unable to Locate');

select count(distinct inv_local_id) into :Val_Cj
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('J - Located, Not Examined and/or Interviewed');

select count(distinct inv_local_id) into :Val_Ck
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('K - Sent Out Of Jurisdiction');

select count(distinct inv_local_id) into :Val_Cl
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('L - Other');

select count(distinct inv_local_id) into :Val_Cv
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('V - Domestic Violence Risk');

select count(distinct inv_local_id) into :Val_Cx
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('X - Patient Deceased');

select count(distinct inv_local_id) into :Val_Cz
from CLUSTER
WHERE CC_CLOSED_DT is not null 
and IX_TYPE IN ('Initial/Original','Re-Interview') and FL_FUP_DISPOSITION in ('Z - Previous Preventative Treatment');
quit;

%let PER_PM = %sysevalf(%eval(&Val_PM) / %eval(&Val_B)) ;
%let PER_PN = %sysevalf(%eval(&Val_PN) / %eval(&Val_PM)) ;
%let PER_PA = %sysevalf(%eval(&Val_PA) / %eval(&VAL_PN)) ;
%let PER_PB = %sysevalf(%eval(&Val_PB) / %eval(&VAL_PN)) ;
%let PER_PC = %sysevalf(%eval(&Val_PC) / %eval(&VAL_PN)) ;
%let PER_PD = %sysevalf(%eval(&Val_PD) / %eval(&VAL_PN)) ;
%let PER_PE = %sysevalf(%eval(&Val_PE) / %eval(&VAL_PN)) ;
%let PER_PF = %sysevalf(%eval(&Val_PF) / %eval(&VAL_PN)) ;
%let PER_PQ = %sysevalf(%eval(&Val_PQ) / %eval(&Val_PM)) ;

%let PER_PG = %sysevalf(%eval(&Val_PG) / %eval(&Val_PQ)) ;
%let PER_PH = %sysevalf(%eval(&Val_PH) / %eval(&Val_PQ)) ;
%let PER_PJ = %sysevalf(%eval(&Val_PJ) / %eval(&Val_PQ)) ;
%let PER_PK = %sysevalf(%eval(&Val_PK) / %eval(&Val_PQ)) ;
%let PER_PL = %sysevalf(%eval(&Val_PL) / %eval(&Val_PQ)) ;
%let PER_PV = %sysevalf(%eval(&Val_PV) / %eval(&Val_PQ)) ;
%let PER_PX = %sysevalf(%eval(&Val_PX) / %eval(&Val_PQ)) ;
%let PER_PZ = %sysevalf(%eval(&Val_PZ) / %eval(&Val_PQ)) ;


%let PER_CM = %sysevalf(%eval(&Val_CM) / %eval(&Val_B)) ;
%let PER_CN = %sysevalf(%eval(&Val_CN) / %eval(&Val_CM)) ;
%let PER_CA = %sysevalf(%eval(&Val_CA) / %eval(&VAL_CN)) ;
%let PER_CB = %sysevalf(%eval(&Val_CB) / %eval(&VAL_CN)) ;
%let PER_CC = %sysevalf(%eval(&Val_CC) / %eval(&VAL_CN)) ;
%let PER_CD = %sysevalf(%eval(&Val_CD) / %eval(&VAL_CN)) ;
%let PER_CE = %sysevalf(%eval(&Val_CE) / %eval(&VAL_CN)) ;
%let PER_CF = %sysevalf(%eval(&Val_CF) / %eval(&VAL_CN)) ;
%let PER_CQ = %sysevalf(%eval(&Val_CQ) / %eval(&Val_CM)) ;

%let PER_CG = %sysevalf(%eval(&Val_CG) / %eval(&Val_CQ)) ;
%let PER_CH = %sysevalf(%eval(&Val_CH) / %eval(&Val_CQ)) ;
%let PER_CJ = %sysevalf(%eval(&Val_CJ) / %eval(&Val_CQ)) ;
%let PER_CK = %sysevalf(%eval(&Val_CK) / %eval(&Val_CQ)) ;
%let PER_CL = %sysevalf(%eval(&Val_CL) / %eval(&Val_CQ)) ;
%let PER_CV = %sysevalf(%eval(&Val_CV) / %eval(&Val_CQ)) ;
%let PER_CX = %sysevalf(%eval(&Val_CX) / %eval(&Val_CQ)) ;
%let PER_CZ = %sysevalf(%eval(&Val_CZ) / %eval(&Val_CQ)) ;

/*treatment index*/
%let VAL_one = %sysevalf(%eval(&Val_PA) + %eval(&Val_PC)+ %eval(&Val_PE)) ;
%let VAL_PO = %sysevalf(%eval(&VAL_one) / %eval(&Val_B)) ;

/*DI index*/

%let VAL_PP = %sysevalf(%eval(&VAL_four) / %eval(&Val_B)) ;

/*treatment index*/
%let VAL_tw = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC) + %eval(&Val_CE)) ;
%let VAL_CO = %sysevalf(%eval(&VAL_tw) / %eval(&Val_B)) ;


/*DI index*/
/*%let VAL_twoo = %sysevalf(%eval(&Val_CA) + %eval(&Val_CC));*/
%let VAL_CP = %sysevalf(%eval(&VAL_five) / %eval(&Val_B)) ;

/*********************************************************************************************************************************/


data _null_;
put "Val_PM =  &Val_PM";
put "Val_PN =  &Val_PN";
put "Val_PA =  &Val_PA";
put "Val_PB =  &Val_PB";
put "Val_PC =  &Val_PC";
put "Val_PQ =  &Val_PQ";
put "Val_PO =  &Val_PO";
put "Val_PP =  &Val_PP";

put "Val_CM =  &Val_CM";
put "Val_CN =  &Val_CN";
put "Val_CA =  &Val_CA";
put "Val_CB =  &Val_CB";
put "Val_CC =  &Val_CC";
put "Val_CQ =  &Val_CQ";
put "Val_CO =  &Val_CO";
put "Val_Cp =  &Val_CP";

put "PER_PM =  &PER_PM";
put "PER_PN =  &PER_PN";
put "PER_PA =  &PER_PA";
put "PER_PB =  &PER_PB";
put "PER_PC =  &PER_PC";
put "PER_PQ =  &PER_PQ";

put "PER_CM =  &PER_CM";
put "PER_CN =  &PER_CN";
put "PER_CA =  &PER_CA";
put "PER_CB =  &PER_CB";
put "PER_CC =  &PER_CC";
put "PER_CQ =  &PER_CQ";

RUN;

%fills(col5,col6);

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


title;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =200 COMPRESS=NO   missing = ' '  
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
ods PDF body = sock style=styles.listing NOTOC uniform;
ods layout start width=8.49in height=10.99in;
ods region x = 0.75 in y = 0.25 in ;
ods text = "^{style[font_face='calibri' fontsize=14pt just=center   fontweight=bold] 
						 %upcase(&reportTitle)}";

ods region x = 0.75 in y = 0.60 in width = 5.0in ;
proc report data = templatePA04_STDD nowd spanrows ls =256  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="calibri"
font_size = 10pt }
style(report)={font_face="calibri" font_size=10pt  rules=none frame=void vjust = middle }
style(column)={font_face="calibri" font_size=10pt vjust = middle };

columns  descrip    value1 percent1;
define descrip /ORDER=internal ' '  left WIDTH=30 style(column)={asis=on cellwidth=50mm rules=none CELLHEIGHT = 4mm  fontweight=bold  }
style(header)={just=center cellwidth=50mm rules=none  fontweight=bold};
define   value1    / display CENTER  ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE  }
style(header)={just=center cellwidth=15mm rules=none CELLHEIGHT = 4mm};
define   percent1    / display CENTER ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE }
style(header)={just=center cellwidth=15mm rules=none CELLHEIGHT = 4mm};
run;

ods region x = 0.00 in y = 2.45 in ;
ods text = "^{style[font_face='calibri' fontsize=11pt just=CENTER fontweight=bold] 
						FROM OI 							}";
ods region x = 2.00 in y = 2.45 in ;
ods text = "^{style[font_face='calibri' fontsize=11pt just=CENTER fontweight=bold] 
						FROM RI 		}";
ods region x = 4.25 in y = 2.45 in ;
ods text = "^{style[font_face='calibri' fontsize=11pt just=CENTER fontweight=bold] 
						TOTAL}";
ods region x = 0.25 in y = 2.55 in width = 10.75in ;
proc report data = templatePA04 nowd spanrows   split='~' headline missing 
style(header)={just=left font_weight=bold font_face="calibri"
font_size = 10pt }
style(report)={font_face="calibri" font_size=10pt  rules=none frame=void vjust = middle }
style(column)={font_face="calibri" font_size=10pt vjust = middle };

columns  descrip    col1 col2 col3 col4 col5 col6;
define descrip /ORDER=internal ' '  left WIDTH=20 style(column)={asis=on cellwidth=55mm rules=none CELLHEIGHT = 4mm  }
style(header)={just=center cellwidth=55mm rules=none};
define   col1    / display CENTER  ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE  }
style(header)={just=center cellwidth=15mm rules=none};
define   col2    / display CENTER ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE }
style(header)={just=center cellwidth=15mm rules=none};
define   col3    / display CENTER  ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE  }
style(header)={just=center cellwidth=15mm rules=none};
define   col4    / display CENTER ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE }
style(header)={just=center cellwidth=15mm rules=none};
define   col5    / display CENTER  ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE  }
style(header)={just=center cellwidth=15mm rules=none};
define   col6    / display CENTER ''    style(column)={ cellwidth=15mm rules=none CELLHEIGHT = 4mm VJUST=MIDDLE }
style(header)={just=center cellwidth=15mm rules=none};
compute descrip;
if find(descrip,'PARTNERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
   if find(descrip,'PARTNERS EXAMINED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
   if find(descrip,'PARTNERS NOT EXAMINED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
  if find(descrip,'CLUSTERS INITIATED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
   if find(descrip,'CLUSTERS EXAMINED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
   if find(descrip,'CLUSTERS NOT EXAMINED:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold}');
  end;
  if find(descrip,'DISPO. A - PREVENTIVE TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. L - OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
 if find(descrip,'DISPO. X - DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;

   if find(descrip,'DISPO. A','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. B - REFUSE PREV TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. C - INFECTED, TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. D - INFECTED, NO TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. E - PREVIOUS TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. F - NOT INFECTED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. G - INSUFF. INFO:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. H - UNABLE TO LOC:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. J - LOC, NO EXAM/IX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. K - SENT OOJ:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. L - OTHER:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. V - DOM VIOL:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
 if find(descrip,'DISPO. X - DECEASED:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;
   if find(descrip,'DISPO. Z - PREVIOUS PREV TX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.25in}');
  end;

   if find(descrip,'TREAT. INDEX.:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.40in}');
  end;
   if find(descrip,'TREAT. INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.40in}');
  end;

   if find(descrip,'DI. INDEX.:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.40in}');
  end;
   if find(descrip,'DI. INDEX:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.40in}');
  end;
 endcomp;

 run;

ods layout end;
ods pdf close;

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
	      %export(work,templatePA04_STDD,sock,&exporttype);
	Title;
	%finish:
	%mend PA04_STD;
%PA04_STD;
