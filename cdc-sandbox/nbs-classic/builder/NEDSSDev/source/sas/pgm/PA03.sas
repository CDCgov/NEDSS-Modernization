/*Program Name : Pa03.sas																													*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	11-14-2016																												*/
/*																																				*/
/*Program Last Modified Date:12-27-2016 :added startpage option to ods 																													*/
/*							:01-06-2017:Formatting changes for report 																													*/
/*							:01-13-2017: Numbers are in center for last section.																													*/
/*Program Description:	Creates PA03 Report:  for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%macro PA03;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;																															

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

data _null_;
call symputx ('TDAY' , put(today(),mmddyy10.));

call symputx ('time_text',put(time(),timeampm8.));
run;

data _null_;
put " Tday = &tday";

put " time_text = &time_text";
run;

proc format;
value miss
. = 0;
run;

proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') ;
quit;


proc sql;
create table pa3_new as 
select  distinct a.INV_LOCAL_ID, a.CA_PATIENT_INTV_STATUS, a.INIT_FUP_INTERNET_FOLL_UP_CD, a.FL_FUP_INTERNET_OUTCOME_CD,
INVESTIGATOR_INTERVIEW_KEY, d.provider_last_name
from STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on b.INVESTIGATION_KEY =a.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY 
  left outer join nbs_rdb.D_provider d  on d.provider_key =a.INVESTIGATOR_INTERVIEW_KEY
  left outer join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY;
quit;

/*---------------------total partners initiated----> B*/
proc sql;
create table pp03 as 
select distinct cat(f.inv_local_id,a.inv_local_id) as inv_local_id , a.CA_PATIENT_INTV_STATUS,
a.INVESTIGATOR_INTERVIEW_KEY, a.INIT_FUP_INTERNET_FOLL_UP_CD, d_contact_record.ctt_processing_decision as proc_dec,
f.INIT_FUP_INTERNET_FOLL_UP_CD as CON_INIT_FUP_INTERNET_FOLL_UP_CD, d_contact_record.CTT_REFERRAL_BASIS,
f.FL_FUP_INTERNET_OUTCOME_CD
from STD_HIV_DATAMART1 a 
  inner join nbs_rdb.investigation 
  on investigation.investigation_key =a.investigation_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE g
on  a.investigation_key =g.SUBJECT_investigation_key
inner join nbs_rdb.STD_HIV_DATAMART f on g.CONTACT_INVESTIGATION_KEY = f.Investigation_key
inner join nbs_rdb.d_contact_record
on d_contact_record.d_contact_record_key = F_CONTACT_RECORD_CASE.d_contact_record_key and d_contact_record.RECORD_STATUS_CD~='LOG_DEL'
    where d_contact_record.CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both'
,'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3'
,'A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') ;
  quit;

options missing = 0 ; 
  proc sql noprint;
 select count(distinct inv_local_id) into :val_a
 from pa3_new ;

select count(distinct inv_local_id) into :val_b
from pp03 
where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :val_c
from pp03 
where CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :val_d
from pp03 
where CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :val_G
from pa3_new 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')
;


select count(distinct inv_local_id) into :val_H
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and 
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;


select count(distinct inv_local_id) into :val_I
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and 
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;


select count(distinct inv_local_id) into :val_J
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and 
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;


select count(distinct inv_local_id) into :val_M
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and 
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :val_N
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;


select count(distinct inv_local_id) into :val_O
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure')
;

select count(distinct inv_local_id) into :val_q1
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I1'
;

select count(distinct inv_local_id) into :val_q2
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I2'
;

select count(distinct inv_local_id) into :val_q3
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I3'
;

select count(distinct inv_local_id) into :val_q4
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I4'
;

select count(distinct inv_local_id) into :val_q5
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I5'
;

select count(distinct inv_local_id) into :val_q6
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I6'
;

select count(distinct inv_local_id) into :val_q7
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I7'
;


select count(distinct inv_local_id) into :val_r1
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I1'
;

select count(distinct inv_local_id) into :val_r2
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I2'
;

select count(distinct inv_local_id) into :val_r3
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I3'
;

select count(distinct inv_local_id) into :val_r4
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I4'
;

select count(distinct inv_local_id) into :val_r5
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I5'
;

select count(distinct inv_local_id) into :val_r6
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I6'
;

select count(distinct inv_local_id) into :val_r7
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I7'
;

select count(distinct inv_local_id) into :val_s1
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I1'
;

select count(distinct inv_local_id) into :val_s2
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I2'
;

select count(distinct inv_local_id) into :val_s3
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I3'
;

select count(distinct inv_local_id) into :val_s4
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I4'
;

select count(distinct inv_local_id) into :val_s5
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I5'
;

select count(distinct inv_local_id) into :val_s6
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I6'
;

select count(distinct inv_local_id) into :val_s7
from pp03 
where INIT_FUP_INTERNET_FOLL_UP_CD in ('Y') and CON_INIT_FUP_INTERNET_FOLL_UP_CD in ('Y')and
CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3') and
proc_dec in ('Field Follow-up','Secondary Referral','Record Search Closure') and 
FL_FUP_INTERNET_OUTCOME_CD = 'I7'
;

QUIT;


%let VAL_E = %sysevalf(%eval(&Val_B) / %eval(&Val_A)) ;

%let VAL_K = %sysevalf(%eval(&Val_H) / %eval(&Val_G)) ;


%let VAL_one = %sysevalf(%eval(&Val_c) + %eval(&Val_d)) ;
%let VAL_F = %sysevalf(%eval(&VAL_one) / %eval(&Val_A)) ;

%let VAL_two = %sysevalf(%eval(&Val_i) + %eval(&Val_j)) ;
%let VAL_l = %sysevalf(%eval(&VAL_two) / %eval(&Val_G)) ;


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
put "Val_J =  &Val_J";
put "Val_K =  &Val_K";
put "Val_L =  &Val_L";
put "Val_M =  &Val_M";
put "Val_n =  &Val_n";
put "Val_o =  &Val_o";

put "Val_Q1 =  &Val_Q1";
put "Val_Q2 =  &Val_Q2";
put "Val_Q3 =  &Val_Q3";
put "Val_Q4 =  &Val_Q4";
put "Val_Q5 =  &Val_Q5";
put "Val_Q6 =  &Val_Q6";
put "Val_Q7 =  &Val_Q7";

put "Val_R1 =  &Val_R1";
put "Val_R2 =  &Val_R2";
put "Val_R3 =  &Val_R3";
put "Val_R4 =  &Val_R4";
put "Val_R5 =  &Val_R5";
put "Val_R6 =  &Val_R6";
put "Val_R7 =  &Val_R7";

put "Val_S1 =  &Val_S1";
put "Val_S2 =  &Val_S2";
put "Val_S3 =  &Val_S3";
put "Val_S4 =  &Val_S4";
put "Val_S5 =  &Val_S5";
put "Val_S6 =  &Val_S6";
put "Val_S7 =  &Val_S7";
run;

/*Create template data for overall report*/
filename NBSPGMp1  "&SAS_REPORT_HOME/template";
%include NBSPGMp1 (Template7.sas);

data templatePA03_1;
set templatePA03_1;
if find(descrip,'Total Number of Cases:','i') ge 1 then value1 = "&Val_A" ;
if find(descrip,"Total No. Partners Init'd:",'i') ge 1 then value1 = "&Val_B" ;
if find(descrip,"Total No. Social Contacts Init'd:",'i') ge 1 then value1 = "&Val_C" ;
if find(descrip,"Total No. Associates Init'd:",'i') ge 1 then value1 = "&Val_D" ;
if find(descrip,'Contact Index:','i') ge 1 then value1 = round("&Val_E",0.01) ;
if find(descrip,'Cluster Index:','i') ge 1 then value1 = round("&Val_F" ,0.01);

if find(descrip2,'No. Cases w/Internet Follow-up:','i') ge 1 then value2 = "&Val_G" ;
if find(descrip2,'Total No. Partners','i') ge 1 then value2 = "&Val_H" ;
if find(descrip2,'Total No. Social Contacts:','i') ge 1 then value2 = "&Val_I" ;
if find(descrip2,'Total No. Associates:','i') ge 1 then value2 = "&Val_J" ;
if find(descrip2,'IPS Contact Index:','i') ge 1 then value2 = round("&Val_K",0.01) ;
if find(descrip2,'IPS Cluster Index:','i') ge 1 then value2 = round("&Val_L" ,0.01);
run;



data templatePA03_2;
set templatePA03_2;
if find(descrip,'Total No. IPS Partners:','i') ge 1 then value1 = "&Val_M" ;
if find(descrip,'Total No. IPS Social Contacts:','i') ge 1 then value1 = "&Val_N" ;
if find(descrip,'Total No. IPS Associates::','i') ge 1 then value1 = "&Val_O" ;
run;

data templatePA03_3;
set templatePA03_3;
if find(descrip,'Sexual Contact:','i') ge 1 then I1 = "&Val_Q1" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I2 = "&Val_Q2" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I3 = "&Val_Q3" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I4 = "&Val_Q4" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I5 = "&Val_Q5" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I6 = "&Val_Q6" ;
if find(descrip,'Sexual Contact:','i') ge 1 then I7 = "&Val_Q7" ;
run;

data templatePA03_4;
set templatePA03_4;
if find(descrip,'Social Contact:','i') ge 1 then I1 = "&Val_R1" ;
if find(descrip,'Social Contact:','i') ge 1 then I2 = "&Val_R2" ;
if find(descrip,'Social Contact:','i') ge 1 then I3 = "&Val_R3" ;
if find(descrip,'Social Contact:','i') ge 1 then I4 = "&Val_R4" ;
if find(descrip,'Social Contact:','i') ge 1 then I5 = "&Val_R5" ;
if find(descrip,'Social Contact:','i') ge 1 then I6 = "&Val_R6" ;
if find(descrip,'Social Contact:','i') ge 1 then I7 = "&Val_R7" ;
run;

data templatePA03_5;
set templatePA03_5;
if find(descrip,'Associate:','i') ge 1 then I1 = "&Val_S1" ;
if find(descrip,'Associate:','i') ge 1 then I2 = "&Val_S2" ;
if find(descrip,'Associate:','i') ge 1 then I3 = "&Val_S3" ;
if find(descrip,'Associate:','i') ge 1 then I4 = "&Val_S4" ;
if find(descrip,'Associate:','i') ge 1 then I5 = "&Val_S5" ;
if find(descrip,'Associate:','i') ge 1 then I6 = "&Val_S6" ;
if find(descrip,'Associate:','i') ge 1 then I7 = "&Val_S7" ;
run;

data rptpa03;
set templatePA03_3 templatePA03_4 templatePA03_5;
run;

data rptpa03 ;
set rptpa03;
j1 = put(i1,10.);
j2 = put(i2,10.);
j3 = put(i3,10.);
j4 = put(i4,10.);
j5 = put(i5,10.);
j6 = put(i6,10.);
j7 = put(i7,10.);
drop  i1 i2 i3 i4 i5 i6 i7;
rename j1 = i1
j2 = i2
j3 = i3
j4=i4 j5 = i5 j6=i6 j7=i7;
run;
title;

ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=256 PS =248 COMPRESS=NO   MISSING = 0 
topmargin=0.50 in bottommargin=0.50in leftmargin=0.25in rightmargin=0.25in  nobyline  ;
ods noproctitle;
ods escapechar='^'; 
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "__________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer ";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";

%if %upcase(&exporttype)=REPORT %then %do;
options printerpath=pdf;
ods PDF body = sock notoc uniform ;

ods layout start width=8.49in height=10.99in;
ods region x = 0.40 in y = 0.25 in ;


ods text =  "^{style[font_face='calibri' fontsize=14pt just=CENTER fontweight=bold ] 
%upcase(&reportTitle)}";

ods region x = 0.15 in y = 1.00 in width = 8.1 in;


proc report data = templatepa03_1 nowd spanrows  split='~' headline missing
style(header)={just=left font_weight=bold font_face="calibri" font_size = 10pt }
style(report)={font_size=10pt  rules=none frame=void font_face="calibri"}
style(column)={font_size=10pt font_face="calibri"};

columns  descrip    value1 gap descrip2 value2;
define descrip /display order=internal ' ' left style = [cellwidth=65mm CELLHEIGHT = 4mm font_size=10pt font_face="calibri"];
define   value1    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 4mm font_size=10pt font_face="calibri"];
define gap / '    ' format=$4. style={cellwidth=20mm CELLHEIGHT = 4mm};
define descrip2 /display order=internal ' ' left style = [cellwidth=65mm CELLHEIGHT = 4mm font_size=10pt font_face="calibri"];
define   value2    / display order=internal ''    style = [just=center cellwidth=15mm CELLHEIGHT = 4mm font_size=10pt font_face="calibri"];

compute descrip;
  if find(descrip,'Total Number of Cases:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,"Total No. Partners Init'd:",'i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri" CELLHEIGHT = 6mm vjust=b}');
  end;
   if find(descrip,"Total No. Social Contacts Init'd:",'i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Total No. Associates Init''d:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Contact Index:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
if find(descrip,'Cluster Index:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in  font_size=10pt font_face="calibri"}');
  end;
  descrip=upcase(descrip);
 endcomp;

compute descrip2;
  if find(descrip2,'Total No. Partners:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri" CELLHEIGHT = 6mm vjust=b}');
  end;
  if find(descrip2,'No. Cases w/Internet Follow-up:','i') ge 1  then do;
    call define(_col_,'style','style={fontweight=bold  font_size=10pt font_face="calibri"}');
  end;
   if find(descrip2,'Total No. Social Contacts:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip2,'Total No. Associates:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip2,'IPS Contact Index:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
if find(descrip2,'IPS Cluster Index:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_size=10pt font_face="calibri"}');
  end;
   descrip2=upcase(descrip2);
endcomp;
run;



ods region x = 0.50 in y = 2.45 in ;


ods region x = 0.25 in y = 2.60 in ;
proc report data = templatepa03_2 nowd spanrows  split='~' headline missing 
style(header)={just=left font_weight=bold font_face="calibri" font_size = 10pt }
style(report)={font_face="calibri" font_size=10pt  rules=none frame=void}
style(column)={font_face="calibri" font_size=10pt};

columns  descrip    value1 ;
define descrip /display order=internal ' ' left style = [cellwidth=60mm CELLHEIGHT = 4mm];
define   value1    / display order=internal ''    style = [cellwidth=15mm CELLHEIGHT = 4mm];
compute descrip;
  if find(descrip,'Total No. IPS Partners:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.30in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Total No. IPS Social Contacts:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.30in font_size=10pt font_face="calibri"}');
  end;
   if find(descrip,'Total No. IPS Associates:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.30in font_size=10pt font_face="calibri"}');
  end;
  descrip=upcase(descrip);
   endcomp;
run;
ods region x = 0.50 in y = 3.35 in ;

 
ods region x = 0.25 in y = 3.50 in  width = 8.10 in;

proc report data = rptpa03 nowd  split='~' headskip headline missing 
style(header)={font_face="calibri" just=center fontsize=11pt fontweight=bold 
               background=light gray bordertopcolor=black borderbottomcolor=black 
               }
style(report)={font_face="calibri" just=center font_size=10pt CELLHEIGHT = 4mm};

columns  descrip    I1 I2 I3 I4 I5 I6 I7;
define descrip /order order=data  'OUTCOMES' left style = [leftmargin=.22in just=left cellwidth=40mm CELLHEIGHT = 4mm  font_face="calibri" font_size=10pt ];
define   I1   / order  'I1'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I2   / order  'I2'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I3   / order  'I3'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I4   / order  'I4'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I5   / order  'I5'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I6   / order  'I6'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};
define   I7   / order  'I7'  center  style(column)={just=center cellwidth=17mm rules=none CELLHEIGHT = 4mm font_face="calibri" font_size=10pt};

compute descrip;
  if find(descrip,'Sexual Contact:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_face="calibri" font_size=10pt vjust=b}');
  end;
   if find(descrip,'Social Contact:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_face="calibri" font_size=10pt vjust=b}');
  end;
   if find(descrip,'Associate:','i') ge 1  then do;
    call define(_col_,'style','style={leftmargin=.22in font_face="calibri" font_size=10pt vjust=b}');
  end;
  descrip=upcase(descrip);
   endcomp;
    
 run;

ods layout end;
ods pdf close;
ods listing;
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
	      %export(work,pa3_new,sock,&exporttype);
	Title;
	%finish:
	%mend PA03;
%PA03;
