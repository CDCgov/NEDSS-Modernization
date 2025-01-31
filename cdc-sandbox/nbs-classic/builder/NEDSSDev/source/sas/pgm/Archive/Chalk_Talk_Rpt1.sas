
/*Program Name : Chalk_Talk_Rpt_CA01																											*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	09-14-2016																												*/
/*																																				*/
/*Program Last Modified Date:09-25-2016																											*/
/*																																				*/
/*																																				*/
/*Program Description:	Creates CA01 Chalk Talk Type report for NBS5.1 																														*/
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


proc format;
value $gender		/*Ask abt mapping...Patient_sex and iuse the code in mart...male to female will be mtf and use always Patient_Sex with code..defect in datamart*/
'Male' = 'M'		/*just display as it is for now till defect taken care of */
'Female' = 'F'
'Unknown' = 'U'
;

value $preg
'Yes' = 'Y'
'No' = 'N'
'Unknown' = 'U'
;

value $mar	/*Ask abt mapping..how to map to 1 letter... */
'Annulled' ='A'
'Divorced' = 'D'
'Married' = 'M'
'Single, Never married' = 'C'
'Widowed' = 'W'
;

value dte
. = '  /  /    '
;
value $field
'Field Follow-up' = 'FF'
'Insufficient Info'= 'II'
'Not Program Priority' = 'NPP'
'Physician Closure'= 'PC'
'Record Search Closure' = 'RSC'
'Other','Send OOJ' , 'Administrative Closure' , 'BFP - No Follow-up' = Missing 
;

value $nine
' ' = '00'
;

value $null
'NULL' = ' ';

value $diag
' ' , 'NULL' = ' ' 
;

value miss
. , other = ' ' 

;

value $ref
'P1 - Partner, Sex' = 'P1'
'P2 - Partner, Needle-Sharing'  = 'P2'

;

value $disp
'E - Previously Treated for This Infection' = 'E'
;
run;


 proc sql;
create table STD_HIV_DATAMART as 
select a.* from nbs_rdb.STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed');
quit;


 proc sql;
create table patient as  
select distinct upcase(PATIENT_NAME)as Case_name label = 'Case_name'
,PATIENT_CURRENT_SEX as Gender label = 'Gender' format = $gender.
,PATIENT_PREGNANT_IND as Preg  label = 'PREG' format = $preg.
,PATIENT_MARITAL_STATUS as marital label = 'Marital' format = $mar.
,ADI_900_STATUS as NineHund format = $nine.
,datepart(CC_CLOSED_DT)  as closed_date format = mmddyy10. label = 'closed_date' 
,DIAGNOSIS_CD as dx  label = 'Dx' 
,INV_LOCAL_ID as case_number  label = 'case_number',
a.INVESTIGATION_KEY ,datepart(IX_DATE) as OI_Date format = mmddyy10.  ,
EPI_LINK_ID as lot_num , CTT_REFERRAL_BASIS ,trim(cats(EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART a   inner join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY
/*where lot_num in  ('1310200016') */
where not missing (lot_num)
and IX_TYPE='Initial/Original'
order by case_number ,Case_name ,CTT_REFERRAL_BASIS
;
quit;


data sa;
  set patient;
  by case_number Case_name CTT_REFERRAL_BASIS;
 If first.case_number   then bb_Count=0;
 bb_Count+1;
 bbkey = cats(keyvar,bb_count);
run;



proc sql;
create table cts_count as 
select distinct  keyvar,count(*) as cts_count
from patient
where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') 
/*and  lot_num in  ('1310200016') */
/*and lot_num in  ('1310400016' ,'1310400216','1310400416') */
group by Case_name ,Gender,Preg,marital,NineHund,closed_date, dx,case_number ,OI_Date, keyvar 
order by keyvar;
quit;




proc sql;
create table s_count as 
select distinct count(*) as s_count , keyvar
from patient
where CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3', 'C1- Cohort')
/*and  lot_num in  ('1310400016' ,'1310400216','1310400416') */
/*lot_num in  ('1310200016') */
group by keyvar
order by  keyvar;
quit;



data t1 (drop = bb_count);
merge sa cts_count s_count;
by keyvar;
drop CTT_REFERRAL_BASIS;
run;

/*proc freq data = patient ;*/
/*table _all_ / list missing;*/
/*run;*/

proc sort noduprecs;
by _all_;
run;

proc sort ;
by keyvar;
run;

proc sql;
create table rx as 
select distinct   
				 c.treatment_nm,
/*INV_LOCAL_ID as case_number  label = 'case_number',*/
datepart(DATE_MM_DD_YYYY)as rx_date format = mmddyy10.,
propcase(catx(", ",PROVIDER_FIRST_NAME,PROVIDER_LAST_NAME,PROVIDER_NAME_SUFFIX)) as provider,
trim(cats(EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 

from STD_HIV_DATAMART as a inner join nbs_rdb.TREATMENT_EVENT as b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.TREATMENT as c on c.TREATMENT_KEY= b.TREATMENT_KEY
inner join nbs_rdb.RDB_DATE as d on d.DATE_KEY = b.TREATMENT_DT_KEY
left outer join nbs_rdb.d_provider  as e
on e.Provider_key = b.TREATMENT_PHYSICIAN_KEY
where not missing (EPI_LINK_ID) /*where INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')*/
/*where lot_num in  ('1310200016')  */
/*where EPI_LINK_ID in  ('1310400016' ,'1310400216','1310400416') */
order by keyvar
;
quit;



proc sql;
create table sx as 
select distinct SYM_SIGN_SX_DURATION_IN_DAYS,
datepart(SYM_SIGN_SX_OBVTN_ONSET_DT) as Sym_On_dte format = mmddyy10.,
SYM_SIGN_SX_SOURCE as Obs_source label = 'Obs_source' ,
SYM_SignsSymptoms ,trim(cats(EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar
/*INV_LOCAL_ID as case_number label = 'case_number'*/
from  STD_HIV_DATAMART  as a left outer join nbs_rdb.F_STD_PAGE_CASE as b  on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INVESTIGATION_REPEAT as c on b.D_INVESTIGATION_REPEAT_KEY = c.D_INVESTIGATION_REPEAT_KEY
/*where lot_num in  ('1310200016') */
/*where EPI_LINK_ID in  ('1310400016' ,'1310400216','1310400416') */
where not missing (EPI_LINK_ID)
order by keyvar;
quit;

data t2;
merge rx sx;
by keyvar;
run;

proc sort;
by keyvar;
run;


data t3 ;
merge t1 t2;
by keyvar ;
run;

proc sort;
by keyvar; run;



proc sql;
create table named as
 select distinct 
 c.SURV_PATIENT_FOLL_UP as proc_dec format = $field. ,
            datepart(CTT_FIRST_SEX_EXP_DT) as named_first_expo_dt format = mmddyy10.  
            ,datepart(CTT_FIRST_NDLSHARE_EXP_DT) as named_first_exp_dt format = mmddyy10.
,           substr(CTT_SEX_EXP_FREQ,1,1) as named_freq 
 ,           substr(CTT_NDLSHARE_EXP_FREQ,1,1) as named_ndl_shr_freq
,           datepart(CTT_LAST_SEX_EXP_DT) as  named_last_exp_dt format = mmddyy10.  
/*           , datepart(CTT_LAST_NDLSHARE_EXP_DT) as named_last_exp_dt format = mmddyy10. */
,           substr(CTT_REFERRAL_BASIS,1,2) as named_ref
,           propcase(catx(", ",G.PATIENT_LAST_NAME, G.PATIENT_FIRST_NAME,  G.PATIENT_MIDDLE_NAME))  as Named_pt_name label = 'Named_pt_name'
,			G.PATIENT_KEY
,           G.PATIENT_PREFERRED_GENDER as gender_named
,           F.DIAGNOSIS_CD as dx_named label = 'dx_named'
,           c.EPI_LINK_ID as lot_num
,           d.INV_LOCAL_ID as named_case_number 'named_case_number'
/*,           c.INV_LOCAL_ID as ccase_number 'ccase_number'*/
,   datepart(CTT_DISPO_DT)  as named_dispo_dt format = mmddyy10. ,trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar,
substr(ctt_disposition,1,30) as named_dispo
from nbs_rdb.F_CONTACT_RECORD_CASE a 
inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
INNER JOIN NBS_RDB.D_PATIENT G ON G.PATIENT_KEY = A.CONTACT_KEY
inner join STD_HIV_DATAMART as c on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART F ON F.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
inner join nbs_rdb.INVESTIGATION as d on d.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY

/*where d.INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')*/
/*where C.EPI_LINK_ID in  ('1310200016') */
/*where EPI_LINK_ID in  ('1310400016' ,'1310400216','1310400416') */
order by keyvar
;
quit;

data named ;
set named ;
if named_ref = 'P2' then  named_first_expo_dt = named_first_exp_dt ;
if missing (named_freq) and not missing (named_ndl_shr_freq) then named_freq = named_ndl_shr_freq;
drop named_first_exp_dt named_ndl_shr_freq;
run;


data named_nonmiss;
set named ;
if  missing (named_case_number) then delete;
run;


proc sort data = named_nonmiss;
  by keyvar  Named_pt_name patient_key ;
run;



data namekey;
  set named_nonmiss;
  by keyvar  Named_pt_name patient_key ;
 If first.keyvar   then n_Count=0;
 n_Count+1;
 nkey = cats(keyvar,n_count);
 drop n_count;
/* If Last.case_number then Output;*/
run;



proc sql;
create table name_back_by as 
select distinct D.PATIENT_NAME as bb_name label = 'bb_name',
G.PATIENT_KEY,
				datepart(CTT_FIRST_SEX_EXP_DT) as bb_first_dt format = mmddyy10. , 
datepart(CTT_FIRST_NDLSHARE_EXP_DT) as bb_first_dt2 format = mmddyy10.
,CTT_SEX_EXP_FREQ as bb_freq ,
CTT_NDLSHARE_EXP_FREQ as bb_ndl_freq
,datepart(CTT_LAST_SEX_EXP_DT) as bb_last_exp_dt format = mmddyy10.,
datepart(CTT_LAST_NDLSHARE_EXP_DT) as bb_ndl_sh_dt format = mmddyy10.,
datepart(IX_DATE) as bb_inter_dt format = mmddyy10.,
substr(CTT_REFERRAL_BASIS,1,2) as named_ref
/*INV_LOCAL_ID as case_number label = 'case_number',SUBJECT_INVESTIGATION_KEY EPI_LINK_ID as lot_num,*/
,CONTACT_INVESTIGATION_KEY as INVESTIGATION_KEY ,
trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar
from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join STD_HIV_DATAMART c  on c.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
INNER JOIN NBS_RDB.STD_HIV_DATAMART d ON A.SUBJECT_INVESTIGATION_KEY = D.INVESTIGATION_KEY
INNER JOIN NBS_RDB.F_INTERVIEW_CASE E ON  E.D_INTERVIEW_KEY=A.CONTACT_INTERVIEW_KEY
INNER JOIN NBS_RDB.d_INTERVIEW F ON E.D_INTERVIEW_KEY = F.D_INTERVIEW_KEY
INNER JOIN NBS_RDB.D_PATIENT G ON A.SUBJECT_KEY = G.PATIENT_KEY AND A.CONTACT_KEY IN (SELECT PATIENT_KEY FROM named)

/*WHERE C.EPI_LINK_ID in  ('1310200016')*/
/* EPI_LINK_ID in  ('1310400016' ,'1310400216','1310400416') */
order by keyvar;
quit;


data name_back_by ;
set name_back_by ;
if named_ref = 'P2' then  bb_first_dt = bb_first_dt2 ;
if missing (bb_freq) and not missing (bb_ndl_freq) then bb_freq = bb_ndl_freq;
drop bb_first_dt2 bb_ndl_freq named_ref bb_ndl_sh_dt;
run;


proc sort data = name_back_by;
  by bb_name patient_key ;
run;



data bbkey;
  set name_back_by;
  by bb_name patient_key;
 If first.bb_name   then bb_Count=0;
 bb_Count+1;
 bbkey = cats(keyvar,bb_count);
/* If Last.case_number then Output;*/
run;


proc sql;
create table name_by as 
select distinct 
            datepart(CTT_FIRST_SEX_EXP_DT) as op_first_expo_dt format = mmddyy10., 
            datepart(CTT_FIRST_NDLSHARE_EXP_DT) as OP_first_exp_dt format = mmddyy10.
,           substr(CTT_SEX_EXP_FREQ,1,1) as freq_OP 
  ,          substr(CTT_NDLSHARE_EXP_FREQ,1,1) as named_ndl_shr_freq
,           datepart(CTT_LAST_SEX_EXP_DT) as op_last_exp_dt format = mmddyy10. 
/*           , datepart(CTT_LAST_NDLSHARE_EXP_DT) as last_exp_dt format = mmddyy10. */
,           substr(CTT_REFERRAL_BASIS,1,2) as OP_ref
,           D.PATIENT_NAME  as OP_case_name label = 'OP_case_name'
,           D.PATIENT_PREFERRED_GENDER as OP_GENDER
,           d.DIAGNOSIS_CD as OP_dx label = 'OP_dx'
/*,           EPI_LINK_ID as lot_num*/
,substr(CTT_REFERRAL_BASIS,1,2) as named_ref
,           d.INV_LOCAL_ID as OP_case_number 'OP_case_number'
/*,           c.INV_LOCAL_ID as ccase_number 'ccase_number'*/
,   datepart(CTT_DISPO_DT)  as OP_dispo_dt format = mmddyy10. ,trim(cats(C.EPI_LINK_ID , C.INV_LOCAL_ID)) as keyvar,
c.SURV_PATIENT_FOLL_UP as OP_dispo format = $field. 

from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join STD_HIV_DATAMART c  on c.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
INNER JOIN NBS_RDB.STD_HIV_DATAMART d ON A.SUBJECT_INVESTIGATION_KEY = D.INVESTIGATION_KEY
INNER JOIN NBS_RDB.F_INTERVIEW_CASE E ON  E.D_INTERVIEW_KEY=A.CONTACT_INTERVIEW_KEY
INNER JOIN NBS_RDB.d_INTERVIEW F ON E.D_INTERVIEW_KEY = F.D_INTERVIEW_KEY
INNER JOIN NBS_RDB.D_PATIENT G ON A.SUBJECT_KEY = G.PATIENT_KEY AND A.CONTACT_KEY NOT IN (SELECT PATIENT_KEY FROM named)

/*WHERE C.EPI_LINK_ID in  ('1310200016')*/
/* EPI_LINK_ID in  ('1310400016' ,'1310400216','1310400416') */
order by keyvar;
quit;


data name_by ;
set name_by ;
if named_ref = 'P2' then  op_first_expo_dt = OP_first_exp_dt ;
if missing (freq_OP) and not missing (named_ndl_shr_freq) then freq_OP = named_ndl_shr_freq;
drop OP_first_exp_dt named_ndl_shr_freq named_ref;

run;



data p;
merge t3  name_by;
by keyvar;
run;
proc sort;
by bbkey;
run;

data p2 (drop = bbkey investigation_key bb_count);
merge p bbkey;
by bbkey;
run;

proc sort data = p2;by keyvar; run;



proc sort data = p2;
  by keyvar case_name Sym_On_dte ;
run;



data p2key;
  set p2;
  by keyvar case_name Sym_On_dte ;
 If first.case_name   then n_Count=0;
 n_Count+1;
 nkey = cats(keyvar,n_count);
 drop n_count;
run;

data pat;
merge p2key namekey;
by nkey;
run;



proc sort noduprecs;
by _all_; 
run;

goptions device=actximg;



%macro case1 (var ,varname);

proc sort data = pat ;
by &var case_number ;
run;


ODS _all_ CLOSE;
ods results;
ods html close;
OPTIONS orientation=landscape   nonumber NODATE LS=248 PS =256 COMPRESS=NO MISSING=''
topmargin=.25in
bottommargin=.25in
leftmargin=.25in rightmargin=.25in  nobyline papersize=a4 
;

 %let outpath = C:\Users\Aroras\Documents\Report ;  
 ODS pdf FILE =   "&outpath\Chalk_Talk_Reports_CA01_&varname..pdf"   style=styles.listing notoc  uniform;
 ods escapechar= '!';
 title;

ods proclabel = "Report 1" ;
title1 "  &TDAY &time_text                       CA01  CHALK TALK REPORT:  CASE                                                 &varname: #byval(&var)";
TITLE2 "	";
title3 " ";
proc report nofs  data = pat nowd spanrows ls =256  split='~' headline missing style(header)={just=center font_weight=bold font_face="Arial"
font_size = 8pt }
style(report)={font_size=7pt cellpadding=1.0
cellspacing=0 rules=none
frame = void  }
style(column)={font_size=7pt vjust = middle} 
  ;
by &var ;
where not missing (&var);
columns  Case_name dx case_number cts_count s_count OI_Date  Gender preg NineHund marital closed_date
	     TREATMENT_NM  Rx_date provider   SYM_SIGNSSYMPTOMS Sym_On_dte SYM_SIGN_SX_DURATION_IN_DAYS  Obs_source
		 Named_pt_name named_ref gender_named  named_first_expo_dt   named_freq named_last_exp_dt proc_dec named_dispo named_dispo_dt dx_named  named_case_number 
		bb_name bb_inter_dt bb_first_dt   bb_freq   bb_last_exp_dt
		OP_case_name   op_ref op_gender   op_first_expo_dt   freq_op op_last_exp_dt op_dispo op_dispo_dt op_dx  op_case_number
;
define Case_name /group order page center   order=internal 'Case Name'   style = [cellwidth=60mm bordertopcolor=black borderbottomcolor=black rules=none];
define  dx    /group 'Dx' width=3   center   style = [cellwidth=15mm bordertopcolor=black borderbottomcolor=black rules=none];
define   case_number    / group 'Case Number'    style = [cellwidth=35mm bordertopcolor=black borderbottomcolor=black rules=none];
define   cts_count    / group '# cts'    style = [cellwidth=10mm bordertopcolor=black borderbottomcolor=black rules=none];
define   s_count    / group '# S/As'    style = [cellwidth=15mm bordertopcolor=black borderbottomcolor=black rules=none];
define   OI_Date    / group 'OI Date'    style = [cellwidth=20mm  bordertopcolor=black borderbottomcolor=black rules=none];
define   Gender     / group  'Gender' format = $gender. width=6 center  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black rules=none];
define   preg / group 'Preg' format = $preg.  center  width=4  style = [ rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define  NineHund     / group '900' width=3  center  format = $nine.  style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define  marital     / group 'Marital' width=3  center  width = 10  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black];
define  closed_date     / group 'Date Closed'   center  width=13 style =  [rules=none cellwidth=35mm bordertopcolor=black borderbottomcolor=black] ;

define   TREATMENT_NM    / group  'Treatment'  center style = [rules=none cellwidth=60mm bordertopcolor=black borderbottomcolor=black] ;
define rx_date    / group 'Rx Date'  center  width=10 style = [rules=none cellwidth=20mm bordertopcolor=black borderbottomcolor=black] ;
define   provider  / group 'Provider'  center  width=16  style = [rules=none cellwidth=45mm bordertopcolor=black borderbottomcolor=black];
define   SYM_SIGNSSYMPTOMS/display 
  'Sign/Symptom' width=20   
center  style = [rules=none cellheight=.2in cellwidth=40mm bordertopcolor=black borderbottomcolor=black ];
 
define    Sym_On_dte    / display 'Symp. Onset Dt.'  center width=12  style = [rules=none cellwidth=20mm bordertopcolor=black borderbottomcolor=black];
define   SYM_SIGN_SX_DURATION_IN_DAYS/ display 'Duration' width=8 center  style = [rules=none cellwidth=20mm bordertopcolor=black borderbottomcolor=black];
define   Obs_source       / display 'Obs. Source'   width=10  center  style = [rules=none cellwidth=35mm bordertopcolor=black borderbottomcolor=black];


define Named_pt_name    / display 'Named'  center style = [cellheight=.2in  rules=none cellwidth=60mm bordertopcolor=black borderbottomcolor=black] ;
define  named_ref    / display 'Ref. Basis'  center  width=10 format=$ref. style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black ] ;
define   gender_named     / display  'Gender' format = $gender. width=6 center  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black]; 
define   named_first_expo_dt/ display '1st Expose' width=13 format =mmddyy10.   center  style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black];
define    named_freq    / display  'Freq'  center width=12  style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define   named_last_exp_dt/ display 'Last Expose' width=8 center format =mmddyy10.  style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black];
define   proc_dec/ display 'Proc. Decision' width=8 center  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black];
define   named_dispo/ display 'Dispo' width=8 center format =$ref.  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black];
define   named_dispo_dt/ display 'Dispo Dt'  width=8 center format=mmddyy10.  style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black ];
define  dx_named    / display 'Dx' width=3   center   style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define named_case_number / display 'Case No.' center style = [rules=none cellwidth=30mm bordertopcolor=black borderbottomcolor=black];

define bb_name    / display 'OP Named Back By:'  center style = [rules=none cellwidth=60mm bordertopcolor=black borderbottomcolor=black] ;
define  bb_inter_dt    / display 'Interview Date'  center  width=10 style = [rules=none cellwidth=45mm bordertopcolor=black borderbottomcolor=black ] ;
define  bb_first_dt    / display '1st Expose'  center  width=10 style = [rules=none cellwidth=45mm bordertopcolor=black borderbottomcolor=black] ;
define    bb_freq    / display 'Freq'  center width=12  style = [rules=none cellwidth=45mm bordertopcolor=black borderbottomcolor=black];
define   bb_last_exp_dt/ display 'Last Expose'  width=8 center   style = [rules=none cellwidth=45mm bordertopcolor=black borderbottomcolor=black];

define OP_case_name    / display 'OP Named by but did not name'  center style = [rules=none cellwidth=60mm bordertopcolor=black borderbottomcolor=black ] ;
define  op_ref    / display 'Ref. Basis'  center  width=10 format=$ref. style = [rules=none cellwidth=20mm bordertopcolor=black borderbottomcolor=black] ;
define   op_gender     / display  'Gender' format = $gender. width=6 center  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black]; 
define   op_first_expo_dt/ display '1st Expose'  width=13    center  style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black];
define    freq_op    / display 'Freq'  center width=12  style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define   op_last_exp_dt/ display 'Last Expose'  width=8 center   style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black];
define   op_dispo/ display 'Dispo' width=8 center format = $disp.  style = [rules=none cellwidth=15mm bordertopcolor=black borderbottomcolor=black];
define  op_dispo_dt / display 'Dispo Dt'  width=8 center    style = [rules=none cellwidth=25mm bordertopcolor=black borderbottomcolor=black];
define  op_dx    / display 'Dx' width=3   center   style = [rules=none cellwidth=10mm bordertopcolor=black borderbottomcolor=black];
define op_case_number / display 'Case No.' center style = [rules=none cellwidth=35mm bordertopcolor=black borderbottomcolor=black];

break after Case_name / page   ; 
compute before _page_ / style = {just = c};
/*line @1 Case_name $40.;*/
endcomp;

footnote j = r 'Page !{thispage} of !{lastpage}';
 run;

 ods pdf close;

ods html;
%mend;

%case1(lot_num , Lot); 


%case1(dx , Diagnosis); 



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
