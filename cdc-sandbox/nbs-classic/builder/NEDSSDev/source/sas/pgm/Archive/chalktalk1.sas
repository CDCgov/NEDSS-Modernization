dm log 'clear' continue ;
dm list 'clear' continue ;

proc datasets kill lib = work ;
quit;


 libname nbs_ods ODBC DSN=nedss2 UID=nbs_ods PASSWORD=ods ACCESS=READONLY;
 libname nbs_rdb ODBC DSN=nbs_rdb1 UID=nbs_rdb PASSWORD=rdb ACCESS=READONLY;

proc format;
value $gender
'Male' = '1'
'' = '2'
other = '3'
;

value $preg
'Yes' = 'Y'
'No' = 'N'
other = 'U'
;

value $mar
'Marr','Sing','wido' ='C'
other  = 'M';

value dte
. = '  /  /    '
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
create table patient as  
select distinct upcase(PATIENT_NAME)as Case_name label = 'Case_name'
,PATIENT_PREFERRED_GENDER as Gender label = 'Gender' format = $gender.
,PATIENT_PREGNANT_IND as Preg  label = 'PREG' format = $preg.
,PATIENT_MARITAL_STATUS as marital label = 'Marital'
,status_900 as NineHund 
,CC_CLOSED_DT  as closed_date  label = 'closed_date'
,DIAGNOSIS as diag
,DIAGNOSIS_CD as dx  label = 'Dx' 
,INV_LOCAL_ID as case_number  label = 'case_number',
INVESTIGATION_KEY ,
EPI_LINK_ID as lot_num
FROM nbs_rdb.STD_HIV_DATAMART
where INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')
order by lot_num , INVESTIGATION_KEY
;
quit;


proc sql;
create table rx as 
select distinct EPI_LINK_ID as lot_num,  
				a.INVESTIGATION_KEY ,  c.treatment_nm,
PROVIDER_FIRST_NAME, PROVIDER_LAST_NAME, PROVIDER_NAME_SUFFIX ,
/*INV_LOCAL_ID as case_number  label = 'case_number',*/
datepart(DATE_MM_DD_YYYY)as rx_date format = mmddyy10.,
propcase(catx(", ",PROVIDER_FIRST_NAME,PROVIDER_LAST_NAME)) as provider

from nbs_rdb.STD_HIV_DATAMART as a left outer join nbs_rdb.TREATMENT_EVENT as b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.TREATMENT as c on c.TREATMENT_KEY= b.TREATMENT_KEY
inner join nbs_rdb.RDB_DATE as d on d.DATE_KEY = b.TREATMENT_DT_KEY
left outer join nbs_rdb.d_provider  as e
on e.Provider_key = b.TREATMENT_PHYSICIAN_KEY
where INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')
order by lot_num , a.INVESTIGATION_KEY
;
quit;



proc sql;
create table sx as 
select distinct EPI_LINK_ID as lot_num, a.INVESTIGATION_KEY ,  SYM_SIGN__SX_DURATION_IN_DAYS,
SYM_SIGN_SX_OBVTN_ONSET_DT,
SYM_SIGN_SX_SOURCE,
SYM_SignsSymptoms
/*INV_LOCAL_ID as case_number label = 'case_number'*/
from  nbs_rdb.STD_HIV_DATAMART  as a left outer join nbs_rdb.F_STD_PAGE_CASE as b  on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INVESTIGATION_REPEAT as c on b.D_INVESTIGATION_REPEAT_KEY = c.D_INVESTIGATION_REPEAT_KEY
where INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')
order by lot_num , a.INVESTIGATION_KEY;
;
quit;

data sx1 (drop = SYM_SIGN_SX_OBVTN_ONSET_DT SYM_SIGN_SX_SOURCE case_number );
set sx;
format sym_sign_dte  mmddyy10.  ;
sym_sign_dte = datepart(SYM_SIGN_SX_OBVTN_ONSET_DT) ;
if  SYM_SIGN_SX_SOURCE = 'Clinician' then clin = 'Y';
else clin = 'N';
if   SYM_SIGN_SX_SOURCE = 'Patient' then Pat = 'Y';
else Pat = 'N';

run;

proc sort;
by lot_num  INVESTIGATION_KEY;
run;


proc sql;
create table named as
 select distinct  CTT_DISPOSITION as named_dispo,c.INVESTIGATION_KEY,
            CTT_FIRST_SEX_EXP_DT as named_first_expo_dt , 
            CTT_FIRST_NDLSHARE_EXP_DT as named_first_ndlshr_dt
,           CTT_SEX_EXP_FREQ as named_freq ,
            CTT_NDLSHARE_EXP_FREQ as named_ndl_shr_freq
,           CTT_LAST_SEX_EXP_DT as named_last_exp_dt , 
            CTT_LAST_NDLSHARE_EXP_DT as named_last_ndlshr_dt
,           CTT_REFERRAL_BASIS as named_ref
,           PATIENT_NAME  as Named_pt_name label = 'Named_pt_name'
,           PATIENT_PREFERRED_GENDER as gender_named
,           DIAGNOSIS_CD as dx_named
,           EPI_LINK_ID as lot_num
,           d.INV_LOCAL_ID as named_case_number 'named_case_number'
/*,           c.INV_LOCAL_ID as ccase_number 'ccase_number'*/
,   datepart(CTT_DISPO_DT)  as named_dispo_dt format = mmddyy10. 
from nbs_rdb.F_CONTACT_RECORD_CASE a 
inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join nbs_rdb.STD_HIV_DATAMART as c on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.INVESTIGATION as d on d.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
/*where d.INV_LOCAL_ID in ('CAS10065010GA01','CAS10065012GA01')*/
where c.INVESTIGATION_KEY in (575,577)
;
quit;


proc sort;
by lot_num  INVESTIGATION_KEY;
run;

 

proc sql;
create table name_back_by as 
select distinct PATIENT_NAME as bb_name label = 'bb_name',
CTT_FIRST_SEX_EXP_DT as bb_frst_dt , CTT_FIRST_NDLSHARE_EXP_DT as bb_first_dt2
,CTT_SEX_EXP_FREQ as bb_freq ,CTT_NDLSHARE_EXP_FREQ as bb_ndl_freq
,CTT_LAST_SEX_EXP_DT as bb_last_exp_dt, CTT_LAST_NDLSHARE_EXP_DT as bb_ndl_sh_dt,
EPI_LINK_ID as lot_num,
/*INV_LOCAL_ID as case_number label = 'case_number',*/
CONTACT_INVESTIGATION_KEY as INVESTIGATION_KEY ,SUBJECT_INVESTIGATION_KEY

from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b
on
a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join nbs_rdb.STD_HIV_DATAMART c  on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
where /*( a.SUBJECT_INVESTIGATION_KEY in (575,577)			
and CONTACT_INVESTIGATION_KEY >1)
OR*/
( CONTACT_INVESTIGATION_KEY in (575,577)
and SUBJECT_INVESTIGATION_KEY >1)
order by lot_num, INVESTIGATION_KEY;
quit;


proc sql;
create table name_by as 
select   distinct
CTT_DISPOSITION as op_dispo label = 'op_dispo' ,
datepart(CTT_DISPO_DT) as op_dispo_dt format = mmddyy10.,
datepart(CTT_FIRST_SEX_EXP_DT) as op_first_expo_dt format = mmddyy10., 
datepart(CTT_FIRST_NDLSHARE_EXP_DT) as op_first_ndlshr_dt format = mmddyy10.
,CTT_SEX_EXP_FREQ as freq_op ,
CTT_NDLSHARE_EXP_FREQ as freq_op_ndl
,datepart(CTT_LAST_SEX_EXP_DT) as last_exp_op_dt format = mmddyy10., 
datepart(CTT_LAST_NDLSHARE_EXP_DT) as last_exp_op_ndl_dt  format = mmddyy10., 
CTT_REFERRAL_BASIS as op_ref label = 'op_ref'
,patient_name as OP_case_name label = 'OP_case_name'
,DIAGNOSIS_CD as op_dx label = 'op_dx'
/*, INV_LOCAL_ID as op_named_by_case*/
, PATIENT_PREFERRED_GENDER as op_gender

, EPI_LINK_ID as lot_num
,INV_LOCAL_ID as op_case_number label = 'op_case_number'
, INVESTIGATION_KEY
from nbs_rdb.F_CONTACT_RECORD_CASE as a inner join nbs_rdb.D_CONTACT_RECORD as b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join nbs_rdb.STD_HIV_DATAMART c on c.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
where INVESTIGATION_KEY in (select INVESTIGATION_KEY from patient)
order by lot_num , INVESTIGATION_KEY;
;
quit;

data temp ;
merge patient  rx sx1 named  name_back_by name_by; 
by lot_num INVESTIGATION_KEY   ;
*if a ; 
run;

 





%macro case1 (var ,varname);

proc sort data = temp nodup;
by &var case_number;
run;

ODS _all_ CLOSE;
ods results;

OPTIONS orientation=landscape   NONUMBER NODATE LS=175 PS =100 COMPRESS=NO MISSING=''
topmargin=.15in
bottommargin=.15in
leftmargin=.15in
rightmargin=.15in  nobyline  ;

 LIBNAME LIB "C:\Users\SameerA\dataset";
 %let outpath = C:\Users\SameerA\Output ;  
 ODS pdf FILE =   "&outpath.\ Chalk_Talk_Reports_Type1.pdf" style= styles.listing  ;
 title;

ods proclabel = "Report 1" ;
title1 "                                        CHALK TALK REPORT:  CASE                                                 &varname: #byval(&var)";
TITLE2 "	";
title3 " ";
proc report data = temp nowd spanrows ls =200  split='~' headline missing style(header)={just=center font_weight=bold font_face="Arial"
font_size = 10pt }
style(report)={font_size=9pt cellpadding=2pt
cellspacing=1 rules=none
frame=void}
style(column)={font_size=8pt};
by &var ;
columns  Case_name dx case_number  Gender preg NineHund marital closed_date
	     TREATMENT_NM  Rx_date provider 
		 SYM_SIGNSSYMPTOMS sym_sign_dte SYM_SIGN__SX_DURATION_IN_DAYS clin  pat 
Named_pt_name
named_ref
gender_named
  named_first_expo_dt
   named_freq
named_last_exp_dt named_dispo
named_dispo_dt
dx_named  ccase_number
bb_name
bb_frst_dt
bb_first_dt2
  bb_freq
  bb_last_exp_dt
  OP_case_name
op_ref
op_gender
  op_first_expo_dt
   freq_op
last_exp_op_dt op_dispo
op_dispo_dt
op_dx  op_case_number

;

/*define break /group noprint;*/
define Case_name /group order=internal 'Case Name' width=24 left style = [cellwidth=75mm ];
define  dx    / group 'Dx' width=3   center   style = [cellwidth=20mm ];
define   case_number    / group order=internal 'Case Number' width=24   style = [cellwidth=45mm ];
define   Gender     / group  'Gender' format = $gender. width=6 center  style = [cellwidth=20mm ];
define   preg / group 'Preg' format = $preg.  center  width=4  style = [cellwidth=20mm ];
define  NineHund     / group '900' width=3  center  format = $nine.  style = [cellwidth=20mm ];
define  marital     / group 'Marital' width=3  center  format = $mar. width = 7  style = [cellwidth=20mm ];
define  closed_date     / group 'Date Closed' format = dte.   center  width=13 style =  [cellwidth=35mm ] ;

define   TREATMENT_NM    / group 'Treatment' width=24 left style = [cellwidth=60mm ] ;
define rx_date    / group 'Rx Date'  center  width=10 style = [cellwidth=90mm ] ;
define   provider  / group 'Provider'  center  width=16  style = [cellwidth=90mm ];

define   SYM_SIGNSSYMPTOMS/ group 'Sign/Symptoms' width=13   center  style = [cellwidth=60mm ];
define    sym_sign_dte    / group 'Onset Dt.'  center width=12  style = [cellwidth=45mm ];
define   SYM_SIGN__SX_DURATION_IN_DAYS/ group 'Duration' width=8 center  style = [cellwidth=45mm ];
define   clin       / group 'Clin. Obs.'   width=10  center  style = [cellwidth=45mm ];
define   pat      / group 'Pat. Desc'   width=9  center style = [cellwidth=45mm ];

define Named_pt_name    / group 'Named' width=24 left style = [cellwidth=60mm ] ;
define  named_ref    / group 'Ref. Basis'  center  width=10 format=$ref. style = [cellwidth=20mm ] ;
define   gender_named     / group  'Gender' format = $gender. width=6 center  style = [cellwidth=15mm ]; 
define   named_first_expo_dt/ group '1st Expose' width=13 format =dte.   center  style = [cellwidth=25mm ];
define    named_freq    / group 'Freq'  center width=12  style = [cellwidth=10mm ];
define   named_last_exp_dt/ group 'Last Expose' width=8 center format =dte.  style = [cellwidth=25mm ];
define   named_dispo/ group 'Dispo' width=8 center format =$ref.  style = [cellwidth=15mm ];
define   named_dispo_dt/ group 'Dispo Dt' width=8 center format=mmddyy10.  style = [cellwidth=30mm ];
define  dx_named    / group 'Dx' width=3   center   style = [cellwidth=10mm ];
define case_number / group 'Case No.' center style = [cellwidth=30mm ];

define bb_name    / group 'OP Named Back By:' width=24 left style = [cellwidth=60mm ] ;
define  bb_frst_dt    / group 'Interview Date'  center  width=10 style = [cellwidth=45mm ] ;
define   bb_first_dt2     / group  '1st Expose' width=6 center  style = [cellwidth=45mm ]; 
define    bb_freq    / group 'Freq'  center width=12  style = [cellwidth=45mm ];
define   bb_last_exp_dt/ group 'Last Expose' width=8 center format =miss.  style = [cellwidth=45mm ];

define OP_case_name    / group 'OP Named by but did not name' width=24 left style = [cellwidth=60mm ] ;
define  op_ref    / group 'Ref. Basis'  center  width=10 format=$ref. style = [cellwidth=20mm ] ;
define   op_gender     / group  'Gender' format = $gender. width=6 center  style = [cellwidth=15mm ]; 
define   op_first_expo_dt/ group '1st Expose' width=13 format =miss.   center  style = [cellwidth=25mm ];
define    freq_op    / group 'Freq'  center width=12  style = [cellwidth=10mm ];
define   last_exp_op_dt/ group 'Last Expose' width=8 center format =miss.  style = [cellwidth=15mm ];
define   op_dispo/ group 'Dispo' width=8 center format = $disp.  style = [cellwidth=15mm ];
define  op_dispo_dt / group 'Dispo Dt' width=8 center  format =miss.  style = [cellwidth=25mm ];
define  op_dx    / group 'Dx' width=3   center   style = [cellwidth=20mm ];
define op_case_number / group 'Case No.' center style = [cellwidth=35mm ];
break after Case_name/skip; 
 run;

 ods pdf close;

%mend;

%case1(lot_num , Lot); 
*%case1(dx , Diagnosis); 
