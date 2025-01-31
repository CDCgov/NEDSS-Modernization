/*Program Name : Chalk_Talk_Rpt_CA01																												*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	09-14-2016																												*/
/*																																				*/
/*Program Last Modified Date:11-21-2016																											*/
/*							:Corrected order for case number in report and fixed page break error in the report																													*/
/*							:Format for Gender revised and includes MTF and FTM values based on logic																													*/
/*							:01-19-2017 formats AND Layout updated by Sameer																	*/
/*							:01-23-2017: no page breaks with end of line formatted, deleted end of records statement							*/
/*Program Description:	Creates CA01 Chalk Talk Type report for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%Formats;

%macro CA01_DIAGNOSIS;

%if  %upcase(&skip)=YES %then
      %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as 
select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') where diagnosis_cd is not null and CA_INTERVIEWER_ASSIGN_DT ~=. 
;
quit;

/*Oct-31-2016 :Added  ix_type_cd = 'INITIAL' to the patient querry*/
proc sql;
	create table patient_init as  
		select distinct a.PATIENT_NAME as Case_name label = 'Case_name'
			,a.PATIENT_CURRENT_SEX , e.local_id 
			,a.PATIENT_PREFERRED_GENDER
			,a.PATIENT_CURR_SEX_UNK_RSN
			,a.PATIENT_PREGNANT_IND as Preg  label = 'PREG' format = $preg.
			,a.PATIENT_MARITAL_STATUS as marital label = 'Marital' format = $mar.
			,a.ADI_900_STATUS as NineHund format = $nine.
			,datepart(a.CC_CLOSED_DT)  as closed_date format = mmddyy10. label = 'closed_date' 
			,a.DIAGNOSIS_CD as dx  label = 'DIAG' 
			,a.INV_LOCAL_ID as case_number  label = 'case_number',
			a.INVESTIGATION_KEY  , c.ix_type_cd,
			a.EPI_LINK_ID as lot_num , e.CTT_REFERRAL_BASIS ,trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
		FROM STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
			left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
			left outer join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
			left outer join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
				where e.CTT_REL_WITH_PATIENT not in ('Other infected patient')  and a.CA_INTERVIEWER_ASSIGN_DT ~=.
					order by keyvar, case_number ,Case_name ,e.CTT_REFERRAL_BASIS;
quit;
%chk_mv;
proc sql;
create table patient_dte as  
select distinct 
datepart(c.IX_DATE) as OI_Date format = mmddyy10.  ,
a.EPI_LINK_ID as lot_num , trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
left outer join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
where not missing (a.DIAGNOSIS_CD) 
and c.ix_type_cd = 'INITIAL'
order by keyvar;
quit;

proc sql;
create table cts_count as
select keyvar, count(distinct local_id) as cts_count from patient_init  
where CTT_REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') 
group by keyvar 
order by keyvar;
quit;

proc sql;
create table s_count as
select keyvar, count(distinct local_id) as s_count from patient_init  
where CTT_REFERRAL_BASIS in ('A1 - Associate 1','A2 - Associate 2','A3 - Associate 3',
'S1 - Social Contact 1', 'S2 - Social Contact 2', 'S3 - Social Contact 3', 'C1- Cohort')
group by keyvar 
order by keyvar;
quit;

data patient_init;
set patient_init ;
if not missing (PATIENT_PREFERRED_GENDER) then gender = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then gender = PATIENT_CURRENT_SEX;
if missing (PATIENT_PREFERRED_GENDER)  and missing (PATIENT_CURR_SEX_UNK_RSN) then gender = PATIENT_CURRENT_SEX;
format gender $gender. ; 
drop PATIENT_PREFERRED_GENDER PATIENT_CURR_SEX_UNK_RSN PATIENT_CURRENT_SEX;
run;

proc sort data = patient_init;
by keyvar case_number Case_name;
run;

data t1 ;
merge patient_init patient_dte cts_count s_count;
by keyvar;
drop CTT_REFERRAL_BASIS IX_TYPE_CD LOCAL_ID;
run;


proc sort noduprecs;
by _all_;
run;

proc sort data = t1;
  by keyvar case_name  ;
run;


data t1key;
  set t1;
  by keyvar case_name  ;
 If first.keyvar   then n_Count=0;
 n_Count+1;
 nkey = cats(keyvar,n_count);
 drop n_count;
run;

proc sort data = t1key;
  by nkey ;
run;

proc sql;
create table named as
 select distinct C.INV_LOCAL_ID AS OP_LOCAL_ID,
 b.CTT_PROCESSING_DECISION as proc_dec format = $field. ,
            datepart(b.CTT_FIRST_SEX_EXP_DT) as named_first_expo_dt format = mmddyy10.  
            ,datepart(b.CTT_FIRST_NDLSHARE_EXP_DT) as named_first_exp_dt format = mmddyy10.
,           b.CTT_SEX_EXP_FREQ as named_freq 
,           b.CTT_NDLSHARE_EXP_FREQ as named_ndl_shr_freq
,           datepart(b.CTT_LAST_SEX_EXP_DT) as  named_last_exp_dt format = mmddyy10.  
,           substr(b.CTT_REFERRAL_BASIS,1,2) as named_ref
,           propcase(catx(", ",G.PATIENT_LAST_NAME, G.PATIENT_FIRST_NAME,  G.PATIENT_MIDDLE_NAME))  as Named_pt_name label = 'Named_pt_name'
,			G.PATIENT_KEY
,			A.SUBJECT_KEY
,			g.PATIENT_CURRENT_SEX  
,			g.PATIENT_CURR_SEX_UNK_RSN
,			G.PATIENT_LOCAL_ID
,           G.PATIENT_PREFERRED_GENDER 
,           F.DIAGNOSIS_CD as dx_named label = 'dx_named'
,           c.EPI_LINK_ID as lot_num
,           d.INV_LOCAL_ID as named_case_number 'named_case_number'

,datepart(b.CTT_DISPO_DT) as  ct_named_dispo_dt format = mmddyy10., trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar
,f.FL_FUP_DISPOSITION as  named_dispo format= $dispo., datepart(f.FL_FUP_DISPO_DT) as named_dispo_dt format = mmddyy10.
,b.CTT_DISPOSITION as ct_named_dispo format= $dispo. 
from nbs_rdb.F_CONTACT_RECORD_CASE a 
inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY and b.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.D_PATIENT G ON G.PATIENT_KEY = A.CONTACT_KEY
INNER JOIN NBS_RDB.D_PATIENT K ON K.PATIENT_KEY = A.Subject_KEY
inner join STD_HIV_DATAMART1 as c on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART F ON F.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
left outer join nbs_rdb.INVESTIGATION as d on d.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
where  a.CONTACT_INTERVIEW_KEY~=1 and b.CTT_REL_WITH_PATIENT not in ('Other infected patient')
order by keyvar
;
quit;

data named ;
set named ;
if named_ref = 'P2' then  named_first_expo_dt = named_first_exp_dt ;
if missing (named_freq) and not missing (named_ndl_shr_freq) then named_freq = named_ndl_shr_freq;
if not missing (PATIENT_PREFERRED_GENDER) then gender_named = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then gender_named = PATIENT_CURRENT_SEX;
if missing (PATIENT_PREFERRED_GENDER)  and missing (PATIENT_CURR_SEX_UNK_RSN) then gender_named = PATIENT_CURRENT_SEX;
if missing (named_dispo) then named_dispo = ct_named_dispo;
if missing (named_dispo_dt) then named_dispo_dt = ct_named_dispo_dt;
format gender_named $gender. ; 
drop named_first_exp_dt named_ndl_shr_freq PATIENT_CURRENT_SEX PATIENT_PREFERRED_GENDER PATIENT_CURR_SEX_UNK_RSN ct_named_dispo ct_named_dispo_dt;
run;

proc sort data = named;
  by keyvar  Named_pt_name patient_key ;
run;

data namekey;
  set named;
  by keyvar  Named_pt_name patient_key ;
 If first.keyvar   then n_Count=0;
 n_Count+1;
 nkey = cats(keyvar,n_count);
 drop n_count;
run;

data p4;
merge t1key (in=a) namekey (in = b);
by keyvar;
run;

proc sql;
create table name_back_by as 
select distinct D.PATIENT_NAME as bb_name label = 'bb_name',
G.PATIENT_KEY,
				b.CTT_FIRST_SEX_EXP_DT as bb_first_dt , 
b.CTT_FIRST_NDLSHARE_EXP_DT as bb_first_dt2 
,b.CTT_SEX_EXP_FREQ as bb_freq ,
b.CTT_NDLSHARE_EXP_FREQ as bb_ndl_freq
,b.CTT_LAST_SEX_EXP_DT as bb_last_exp_dt ,
b.CTT_LAST_NDLSHARE_EXP_DT as bb_ndl_sh_dt,
f.IX_DATE  as bb_inter_dt,
substr(b.CTT_REFERRAL_BASIS,1,2) as named_ref
,a.CONTACT_INVESTIGATION_KEY as INVESTIGATION_KEY ,
trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar,
trim(cats(c.INV_LOCAL_ID,d.EPI_LINK_ID , d.INV_LOCAL_ID)) as conkeyvar
from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
and b.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.STD_HIV_DATAMART d ON A.SUBJECT_INVESTIGATION_KEY = D.INVESTIGATION_KEY
INNER JOIN NBS_RDB.F_INTERVIEW_CASE E ON  E.D_INTERVIEW_KEY=A.CONTACT_INTERVIEW_KEY
INNER JOIN NBS_RDB.d_INTERVIEW F ON E.D_INTERVIEW_KEY = F.D_INTERVIEW_KEY and f.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.D_PATIENT G ON A.SUBJECT_KEY = G.PATIENT_KEY
INNER JOIN NBS_RDB.D_PATIENT H ON A.CONTACT_KEY = H.PATIENT_KEY
INNER JOIN  STD_HIV_DATAMART1 C ON C.INVESTIGATION_KEY = A.CONTACT_INVESTIGATION_KEY
INNER JOIN NAMED ON G.PATIENT_LOCAL_ID = NAMED.PATIENT_LOCAL_ID
AND NAMED.OP_LOCAL_ID=C.INV_LOCAL_ID
where a.CONTACT_INTERVIEW_KEY~=1  and b.CTT_REL_WITH_PATIENT not in ('Other infected patient')
order by keyvar;
quit;

data name_back_by ;
set name_back_by ;
if named_ref = 'P2' then  bb_first_dt = bb_first_dt2 ;
if missing (bb_freq) and not missing (bb_ndl_freq) then bb_freq = bb_ndl_freq;
drop bb_first_dt2 bb_ndl_freq named_ref bb_ndl_sh_dt;
run;

proc sort data = name_back_by;
  by  keyvar bb_name patient_key  ;
run;

data bbkey (drop = bb_Count);
  set name_back_by;
  by keyvar bb_name patient_key  ;
 If first.keyvar   then bb_Count=0;
 bb_Count+1;
 bbkey = cats(keyvar,bb_count);
run;

data sa (drop = bb_count);
  set p4;
  by keyvar case_number Case_name ;
 If first.keyvar   then bb_Count=0;
 bb_Count+1;
 bbkey = cats(keyvar,bb_count);
run;

proc sort data = sa;
  by bbkey ;
run;

proc sort data = bbkey;
  by bbkey ;
run;

data p5;
merge sa bbkey ; 
by bbkey;
run;

proc sql;
	create table name_by_init as 
		select distinct G.PATIENT_KEY,
			datepart(b.CTT_FIRST_SEX_EXP_DT) as op_first_expo_dt format = mmddyy10., 
			datepart(b.CTT_FIRST_NDLSHARE_EXP_DT) as OP_first_exp_dt format = mmddyy10.
			,           b.CTT_SEX_EXP_FREQ as freq_OP 
			,          b.CTT_NDLSHARE_EXP_FREQ as named_ndl_shr_freq
			,           datepart(b.CTT_LAST_SEX_EXP_DT) as op_last_exp_dt format = mmddyy10. 
			,           substr(b.CTT_REFERRAL_BASIS,1,2) as OP_ref
			,           D.PATIENT_NAME  as OP_case_name label = 'OP_case_name'
			,           d.PATIENT_PREFERRED_GENDER 
			,			d.PATIENT_CURRENT_SEX
			,			d.PATIENT_CURR_SEX_UNK_RSN
			,           d.DIAGNOSIS_CD as OP_dx label = 'OP_dx'
			,			C.INV_LOCAL_ID as case_number  label = 'case_number'
			,			C.EPI_LINK_ID as lot_num
			,			C.diagnosis_cd as dx
			,			substr(b.CTT_REFERRAL_BASIS,1,2) as named_ref
			,           d.INV_LOCAL_ID as OP_case_number 'OP_case_number'
			,			datepart(b.CTT_DISPO_DT) as  CT_OP_dispo_dt format = mmddyy10., trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar
			 ,			trim(cats(c.INV_LOCAL_ID,d.EPI_LINK_ID , d.INV_LOCAL_ID)) as conkeyvar
			,			d.FL_FUP_DISPOSITION as  OP_dispo format= $dispo., datepart(d.FL_FUP_DISPO_DT) as OP_dispo_dt format = mmddyy10.
			,			b.CTT_DISPOSITION as ct_OP_dispo format= $dispo. 
			,   		datepart(b.CTT_DISPO_DT)  as CT_OP_dispo_dt format = mmddyy10. ,trim(cats(C.EPI_LINK_ID , C.INV_LOCAL_ID)) as keyvar
			,   		datepart(d.FL_FUP_DISPO_DT)  as OP_dispo_dt format = mmddyy10.,H.PATIENT_LOCAL_ID,
			b.CTT_PROCESSING_DECISION as OP_dispo format = $field. 
		from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY and b.RECORD_STATUS_CD~='LOG_DEL'
			INNER JOIN NBS_RDB.STD_HIV_DATAMART d ON A.SUBJECT_INVESTIGATION_KEY = D.INVESTIGATION_KEY
			INNER JOIN NBS_RDB.F_INTERVIEW_CASE E ON  E.D_INTERVIEW_KEY=A.CONTACT_INTERVIEW_KEY
			INNER JOIN NBS_RDB.d_INTERVIEW F ON E.D_INTERVIEW_KEY = F.D_INTERVIEW_KEY and f.RECORD_STATUS_CD~='LOG_DEL'
			INNER JOIN NBS_RDB.D_PATIENT G ON A.SUBJECT_KEY = G.PATIENT_KEY
			INNER JOIN NBS_RDB.D_PATIENT H ON A.CONTACT_KEY = H.PATIENT_KEY
			INNER JOIN  STD_HIV_DATAMART1 C ON C.INVESTIGATION_KEY = A.CONTACT_INVESTIGATION_KEY 
				where b.CTT_REL_WITH_PATIENT not in ('Other infected patient') and a.CONTACT_INTERVIEW_KEY~=1 
					order by keyvar;
quit;

proc sql;
create table keyvar_con as select conkeyvar,bb_name  from name_back_by order by conkeyvar;
quit;

proc sort data = name_by_init;
  by conkeyvar ;
run;

data name_by;
merge keyvar_con name_by_init ; 
by conkeyvar;
run;

data name_by ;
set name_by ;
where missing(bb_name);
if named_ref = 'P2' then  op_first_expo_dt = OP_first_exp_dt ;
if missing (freq_OP) and not missing (named_ndl_shr_freq) then freq_OP = named_ndl_shr_freq;
if missing (OP_dispo_dt) then OP_dispo_dt = CT_OP_dispo_dt;
if not missing (PATIENT_PREFERRED_GENDER) then OP_GENDER = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then OP_GENDER = PATIENT_CURRENT_SEX;
if missing (PATIENT_PREFERRED_GENDER)  and missing (PATIENT_CURR_SEX_UNK_RSN) then OP_GENDER = PATIENT_CURRENT_SEX;
format OP_GENDER $gender. ; 
if missing (OP_dispo) then OP_dispo = ct_OP_dispo;
if missing (OP_dispo_dt) then OP_dispo_dt = CT_OP_dispo_dt;
drop OP_first_exp_dt CT_OP_dispo_dt named_ndl_shr_freq named_ref PATIENT_PREFERRED_GENDER  PATIENT_CURR_SEX_UNK_RSN PATIENT_CURRENT_SEX ct_OP_dispo CT_OP_dispo_dt bb_name conkeyvar;
run;

proc sort data = name_by;
  by keyvar  OP_case_name patient_key ;
run;

data some;
  set name_by;
  by keyvar  OP_case_name patient_key ;
 If first.keyvar   then n_Count=0;
 n_Count+1;
 nbkey = cats(keyvar,n_count);
 drop n_count;
run;

proc sort data = some;
by nbkey ;
run;

proc sort data = p5;
by keyvar case_number Case_name ;
run;

data p5key (drop = sb_count);
  set p5;
  by keyvar case_number Case_name ;
 If first.keyvar   then sb_Count=0;
 sb_Count+1;
 nbkey = cats(keyvar,sb_count);
run;

proc sort data = p5key;
by nbkey ;
run;

data p6;
merge p5key some;
by nbkey;
run;

proc sort data = p6;
by keyvar case_number Case_name ;
run;

proc sql;
create table not_name_by_op as 
select distinct G.PATIENT_KEY
,           substr(b.CTT_REFERRAL_BASIS,1,2) as not_name_by_OP_ref
,           f.PATIENT_NAME  as not_name_by_op_case_name label = 'not_name_by_op_case_name'
,           f.PATIENT_PREFERRED_GENDER 
,   f.PATIENT_CURRENT_SEX
,  f.PATIENT_CURR_SEX_UNK_RSN 
,           f.DIAGNOSIS_CD as not_name_by_OP_dx label = 'not_name_by_OP_dx'
,           f.INV_LOCAL_ID as not_name_by_op_case_number 'not_name_by_op_case_number'
,datepart(b.CTT_DISPO_DT) as  ct_not_name_by_OP_dispo_dt format = mmddyy10., trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar
,f.FL_FUP_DISPOSITION as  not_name_by_OP_dispo format= $dispo., datepart(f.FL_FUP_DISPO_DT) as not_name_by_OP_dispo_dt format = mmddyy10.
,b.CTT_DISPOSITION as ct_not_name_by_OP_dispo format= $dispo. 
from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY and b.RECORD_STATUS_CD~='LOG_DEL'
inner join STD_HIV_DATAMART1 c  on c.INVESTIGATION_KEY = a.Subject_INVESTIGATION_KEY
INNER JOIN NBS_RDB.D_PATIENT G ON A.CONTACT_KEY = G.PATIENT_KEY
inner join nbs_rdb.STD_HIV_DATAMART f on f.Investigation_key = a.contact_investigation_key
where (b.CTT_REL_WITH_PATIENT='Other infected patient' or a.CONTACT_INTERVIEW_KEY=1 or a.CONTACT_INTERVIEW_KEY=.)  
order by keyvar, not_name_by_op_case_name, patient_key;
quit;

data not_name_by_op;
set not_name_by_op;
if not missing (PATIENT_PREFERRED_GENDER) then not_name_by_OP_GENDER = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then not_name_by_OP_GENDER = PATIENT_CURRENT_SEX;
if missing (PATIENT_PREFERRED_GENDER)  and missing (PATIENT_CURR_SEX_UNK_RSN) then not_name_by_OP_GENDER = PATIENT_CURRENT_SEX;
format not_name_by_OP_GENDER $gender. ; 
if missing (not_name_by_OP_dispo) then not_name_by_OP_dispo = ct_not_name_by_OP_dispo;
if missing (not_name_by_OP_dispo_dt) then not_name_by_OP_dispo_dt = ct_not_name_by_OP_dispo_dt;
drop PATIENT_PREFERRED_GENDER PATIENT_CURR_SEX_UNK_RSN  PATIENT_CURRENT_SEX ct_not_name_by_OP_dispo ct_not_name_by_OP_dispo_dt; 
run;

data some2;
  set not_name_by_op;
  by keyvar  not_name_by_op_case_name patient_key ;
 If first.keyvar   then n_Count=0;
 n_Count+1;
 nckey = cats(keyvar,n_count);
 drop n_count;
run;

proc sort data = some2;
by nckey ;
run;

data p6key (drop = bb_count);
  set p6;
  by keyvar case_number Case_name ;
 If first.keyvar   then bb_Count=0;
 bb_Count+1;
 nckey = cats(keyvar,bb_count);
run;

proc sort data = p6key;
by nckey ;
run;

data p7;
merge p6key some2;
by nckey;
run;

proc sql;
create table rx as 
select distinct c.treatment_nm,
datepart(DATE_MM_DD_YYYY)as rx_date format = mmddyy10.,
propcase(catx(", ",PROVIDER_FIRST_NAME,PROVIDER_LAST_NAME,PROVIDER_NAME_SUFFIX)) as provider,
trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
from STD_HIV_DATAMART1 a inner join nbs_rdb.TREATMENT_EVENT  b on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY 
inner join nbs_rdb.TREATMENT c on c.TREATMENT_KEY= b.TREATMENT_KEY
inner join nbs_rdb.RDB_DATE d on d.DATE_KEY = b.TREATMENT_DT_KEY
left outer join nbs_rdb.d_provider e
on e.Provider_key = b.TREATMENT_PHYSICIAN_KEY
where not missing (diagnosis_cd) 
order by keyvar ,rx_date
;
quit;

data rx_grp ;
  set rx;
  by keyvar ;
 If first.keyvar   then rx_Count=0;
 rx_Count+1;
 rxkey = cats(keyvar,rx_Count);
run;


proc sql;
create table sx as 
select distinct c.SYM_SIGN_SX_DURATION_IN_DAYS,
c.SYM_SIGN_SX_OBVTN_ONSET_DT  as Sym_On_dte,
c.SYM_SIGN_SX_SOURCE as Obs_source label = 'Obs_source' ,
c.SYM_SIGNSSYMPTOMS ,trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar
from  STD_HIV_DATAMART1 a inner join nbs_rdb.F_STD_PAGE_CASE b  on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
inner join nbs_rdb.D_INVESTIGATION_REPEAT c on b.D_INVESTIGATION_REPEAT_KEY = c.D_INVESTIGATION_REPEAT_KEY
where not missing (diagnosis_cd) and b.D_INVESTIGATION_REPEAT_KEY~=1
order by keyvar, Sym_On_dte;
quit;

data sx_grp ;
  set sx;
  by keyvar ;
 If first.keyvar   then sx_Count=0;
 sx_Count+1;
 rxkey = cats(keyvar,sx_Count);
run;

data p8;
merge rx_grp sx_grp;
by rxkey;
run;

data p9 (drop= sa_Count);
  set p8;
  by keyvar ;
 If first.keyvar   then sa_Count=0;
 sa_Count+1;
 rxkey = cats(keyvar,sa_Count);
run;

proc sort data = p9;
by rxkey;
run;

data p7key (drop= sa_Count);
  set p7;
  by keyvar ;
 If first.keyvar   then sa_Count=0;
 sa_Count+1;
 rxkey = cats(keyvar,sa_Count);
run;

proc sort ;
by rxkey;
run;

data p10 ;
merge p7key p9;
by keyvar ;
run;

DATA PAT (drop = INVESTIGATION_KEY keyvar nkey PATIENT_KEY nbkey nckey rxkey rx_Count sx_Count);
SET p10;
VAR = SUBSTR(case_number,4,8);
   new = input(VAR,8.);
   DROP VAR ; 
   run;

proc sql;
create table firts as 
select distinct Case_name ,dx ,new ,case_number ,lot_num,cts_count,s_count ,OI_Date , gender ,preg ,NineHund ,marital ,closed_date
from pat
order by dx , new;
quit;



proc sql;
create table sec as 
select distinct case_number ,lot_num, dx, new,
TREATMENT_NM , Rx_date, provider,   SYM_SIGNSSYMPTOMS, Sym_On_dte ,SYM_SIGN_SX_DURATION_IN_DAYS,  Obs_source	 
from pat
order by dx, new;
quit;

proc sql;
create table third as 
select distinct case_number ,lot_num, dx, new,
Named_pt_name ,named_ref ,gender_named , named_first_expo_dt  , named_freq , named_last_exp_dt , proc_dec ,named_dispo ,
named_dispo_dt, dx_named , named_case_number 		 
from pat
order by dx, new;
quit;


proc sql;
create table four as 
select distinct case_number ,lot_num, dx, new,
bb_name ,bb_inter_dt, bb_first_dt ,  bb_freq ,  bb_last_exp_dt 	 
from pat where not missing(bb_name)
order by dx, new;
quit;



proc sql;
create table five as 
select distinct case_number ,lot_num, dx, new,
OP_case_name  , op_ref, op_gender  , op_first_expo_dt  , freq_op ,
op_last_exp_dt, op_dispo, op_dispo_dt ,op_dx  ,op_case_number
 from pat
order by dx, new;
quit;
 
proc sql;
create table six as 
select distinct case_number ,lot_num, dx, new,
not_name_by_op_case_name ,not_name_by_op_ref ,not_name_by_op_gender,
not_name_by_op_dispo, not_name_by_op_dispo_dt, not_name_by_op_dx,  not_name_by_op_case_number

 from pat
order by dx, new;
quit;

/*proc sort data = pat ;*/
/*by  dx new lot_num   rx_date    ;*/
/*run;*/

goptions device=actximg;

ODS _ALL_ CLOSE;
ODS LISTING CLOSE;
ODS RESULTS;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.25in rightmargin=.25in  nobyline  papersize=a4;
ods escapechar='^';
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "_________________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";


OPTIONS NOQUOTELENMAX;
%if %upcase(&exporttype)=REPORT %then %do;
options printerpath=pdf;
ods PDF body = sock  style=styles.listing notoc uniform  startpage=no;
TITLE1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)"		 	;
TITLE2 " ";
%MACRO hippo;
%LOCAL nValues i case_Number  lot_num;
 
PROC SQL NOPRINT;
   SELECT DISTINCT
     (catx("!", case_Number, lot_num)) as CH_Number_CH_Name_SHA
   INTO :CH_Number_CH_Name_SHA1 - :CH_Number_CH_Name_SHA800
     FROM FIRTS;
 
%LET nValues = &sqlobs;
QUIT;
OPTIONS NOBYLINE;
 
%DO i = 1 %TO &nValues;
%LET case_Number = %SCAN(&&CH_Number_CH_Name_SHA&i, 1, !);
%LET lot_num = %SCAN(&&CH_Number_CH_Name_SHA&i, 2, !);
%PUT NOTE: &sysmacroname Processing &i of &nValues &=case_Number  &=lot_num;
 
ods PDF body = sock  style=styles.listing notoc uniform  startpage=no;
OPTIONS NOBYLINE;
OPTIONS NOQUOTELENMAX;
 TITLE1 bold f= Calibri h=14pt  j=c "%upcase(&reportTitle)"		 	;
TITLE2 	  f=Calibri j=c h=12pt '^{style[fontweight=bold] #byvar(dx):} ^{style[fontweight=medium]#byval(dx)}';
TITLE3 " ";
proc report nofs  data = FIRTS nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
 WHERE not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num" and not missing(Case_name);
BY dx new;

COLUMN (Case_name dx new case_number cts_count s_count OI_Date  gender preg NineHund marital closed_date);
define  Case_name /group    'CASE NAME' left style = [cellwidth=35mm   rules=none NOBREAKSPACE=on
cellspacing=0 cellpadding=0  ];
define  dx    / group 'DX' left   style = [cellwidth=10mm   rules=none NOBREAKSPACE=on cellspacing=0 cellpadding=0 ];
define  new    /  noprint;
define  case_number    / group'Case Number'  left style = [cellwidth=25mm   rules=none cellspacing=0 cellpadding=0 ];
define  cts_count    / group '# CTS'  center  style(column)={just=center cellwidth=10mm rules=none cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=10mm rules=none};
define  s_count    / group  '# S/AS'  center  style(column)={just=center cellwidth=15mm rules=none cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=15mm rules=none};
define  OI_Date    / group 'OI DATE' left style = [cellwidth=15mm    rules=none];
define  gender     / group  'GENDER' format = $gender. center  style(column)={just=center cellwidth=15mm rules=none
cellspacing=0 cellpadding=0 }
style(header)={just=center cellwidth=15mm rules=none};
define  preg / group 'PREG' format = $preg.  left  style = [cellwidth=10mm   rules=none];
define  NineHund     / group '900' center  format = $nine.   style(column)={just=center cellwidth=15mm rules=none cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=15mm rules=none};
define  marital     / group 'MARITAL' center  format = $mar.  style(column)={just=center cellwidth=20mm rules=none cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=20mm rules=none};
define  closed_date     / group 'DATE CLOSED' left format =mmddyy10.  style(column)={just=center cellwidth=25mm rules=none cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=25mm rules=none} ;
run;
/*****************************************************************************************/
proc report nofs  data = SEC nowd spanrows  split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
WHERE not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num";
BY dx new;
COLUMN TREATMENT_NM  Rx_date provider   SYM_SIGNSSYMPTOMS Sym_On_dte SYM_SIGN_SX_DURATION_IN_DAYS  Obs_source;
define   TREATMENT_NM    / group  'TREATMENT'  left style = [cellwidth=45mm cellspacing=0 cellpadding=0  rules=none NOBREAKSPACE=on vjust = middle] ;
define 	 rx_date    / group 'RX DATE'  left  style = [cellwidth=20mm   rules=none vjust = middle cellspacing=0 cellpadding=0 ] ;
define   provider  / group 'PROVIDER'  left  style = [cellwidth=20mm   rules=none vjust = middle cellspacing=0 cellpadding=0 ];
define   SYM_SIGNSSYMPTOMS/ display 'SIGN/SYMPTOM'  left  style = [cellwidth=30mm   rules=none vjust = middle cellspacing=0 cellpadding=0  ];
define   Sym_On_dte    / display 'OBS./ONSET DATE'  center style(column)={just=center cellwidth=35mm rules=none vjust = middle cellspacing=0 cellpadding=0  }
style(header)={just=center cellwidth=35mm rules=none};
define   SYM_SIGN_SX_DURATION_IN_DAYS/ group 'DURATION'  center  style(column)={just=center cellspacing=0 cellpadding=0  cellwidth=20mm rules=none vjust = middle }
style(header)={just=center cellwidth=20mm rules=none};
define   Obs_source       / group 'OBS. SOURCE'  left  style(column)={just=left cellwidth=25mm cellspacing=0 cellpadding=0  rules=none vjust = middle }
style(header)={just=left cellwidth=25mm rules=none};
RUN;

/*****************************************************************************************/
proc report nofs  data = third nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
WHERE  not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num";
BY dx new;
COLUMN Named_pt_name named_ref gender_named  named_first_expo_dt   named_freq named_last_exp_dt  proc_dec named_dispo named_dispo_dt dx_named  named_case_number; 
	
define 	Named_pt_name    / display 'NAMED'  left style = [cellwidth=30mm   rules=none] ;
define  named_ref    / display 'REF'  left format=$ref. style = [cellwidth=7mm   rules=none ] ;
define  gender_named     / display  'GENDER' format = $gender. center  style(column)={just=center cellwidth=15mm rules=none }
style(header)={just=center cellwidth=15mm rules=none}; 
define  named_first_expo_dt/ group '1ST EXP.' format =mmddyy10. center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  named_freq    / group 'FREQ'  center style(column)={just=center cellwidth=10mm rules=none }
style(header)={just=center cellwidth=10mm rules=none};
define  named_last_exp_dt/ group 'LAST EXP.' center format =mmddyy10.  style = [cellwidth=22mm   rules=none];
define  proc_dec/ group 'PROC DEC' left  style = [cellwidth=25mm   rules=none];
define  named_dispo/ group 'DISPO' left   style = [cellwidth=15mm   rules=none];
define  named_dispo_dt/ group 'DISPO DT' left format=mmddyy10.  style = [cellwidth=20mm   rules=none ];
define  dx_named    / group 'DX' left   style = [cellwidth=7mm   rules=none];
define  named_case_number / group 'CASE NO.' left style = [cellwidth=23mm   rules=none];
RUN;

/*****************************************************************************************/
proc report nofs  data = four nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
WHERE not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num";
BY dx new;
COLUMN bb_name bb_inter_dt bb_first_dt   bb_freq   bb_last_exp_dt;

define  bb_name    / group 'OP NAMED BACK BY:'  left style = [cellwidth=65mm   rules=none] ;
define  bb_inter_dt    / group 'INTERVIEW DATE'  center   style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none} ;
define  bb_first_dt    / group '1ST EXP.'  center  width=10 style(column)={just=center cellwidth=35mm rules=none }
style(header)={just=center cellwidth=35mm rules=none};
define  bb_freq    / group 'FREQ'  center width=12  style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};
define  bb_last_exp_dt/ group 'LAST EXP.' width=8 center   style(column)={just=center cellwidth=35mm rules=none }
style(header)={just=center cellwidth=35mm rules=none};
RUN;

/*****************************************************************************************/
proc report nofs  data = five nowd spanrows  split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
WHERE not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num";
BY dx new;
COLUMN OP_case_name   op_ref op_gender   op_first_expo_dt   freq_op op_last_exp_dt op_dispo op_dispo_dt op_dx  op_case_number;

define  OP_case_name    / group 'OP NAMED BY BUT DID NOT NAME'  left style = [cellwidth=58mm   rules=none ] ;
define  op_ref    / group 'REF.'  left  format=$ref. style = [cellwidth=10mm   rules=none] ;
define  op_gender     / group  'GENDER' format = $gender. center  style(column)={just=center cellwidth=15mm rules=none }
style(header)={just=center cellwidth=15mm rules=none}; 
define  op_first_expo_dt/ group '1ST EXP.'    left  style = [cellwidth=20mm   rules=none];
define  freq_op    / group 'FREQ'  center style(column)={just=center cellwidth=10mm rules=none }
style(header)={just=center cellwidth=10mm rules=none};
define  op_last_exp_dt/ group 'LAST EXP.' left   style = [cellwidth=18mm   rules=none];
define  op_dispo/ group 'DISPO' left   style = [cellwidth=12mm   rules=none];
define  op_dispo_dt / group 'DISPO DT' left    style = [cellwidth=22mm   rules=none];
define  op_dx    / group 'DX' left   style = [cellwidth=7mm   rules=none];
define  op_case_number / group 'CASE NO.' left style = [cellwidth=23mm   rules=none];
RUN;

/*****************************************************************************************/
proc report nofs  data = six nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
 WHERE not missing(dx) and case_Number ="&case_Number"
 AND lot_num ="&lot_num";
BY dx new;
COLUMN not_name_by_op_case_name not_name_by_op_ref not_name_by_op_gender not_name_by_op_dispo not_name_by_op_dispo_dt not_name_by_op_dx  not_name_by_op_case_number
;

define  not_name_by_op_case_name    / group 'NAMED (NOT BY OP)'  left style = [cellwidth=50mm   rules=none ] ;
define  not_name_by_op_ref    / group 'REF.'  left  format=$ref. style = [cellwidth=20mm   rules=none] ;
define  not_name_by_op_gender     / group  'GENDER' format = $gender. left  style = [cellwidth=20mm   rules=none]; 
define  not_name_by_op_dispo/ group 'DISPO' left   style = [cellwidth=25mm   rules=none];
define  not_name_by_op_dispo_dt / group 'DISPO DT' left    style = [cellwidth=30mm   rules=none];
define  not_name_by_op_dx    / group 'DX' left   style = [cellwidth=20mm   rules=none];
define  not_name_by_op_case_number / group 'CASE NO.' left style = [cellwidth=30mm   rules=none];
 compute before; 
    len=140; 
    len2=len; 
    text=repeat("********************************************************************************************************************",146); 
  endcomp; 
  compute after _page_; 
    line @15 text $varying. len; 
  endcomp; 
  compute after; 
    len=0; 
    line @15 text $varying. len2; 
  endcomp; 
run;

%END;
%MEND;
%hippo
QUIT;
 
ODS PDF CLOSE;
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
	      %export(work,pat,sock,&exporttype);
	Title;
	%finish:
	%mend CA01_DIAGNOSIS;
%CA01_DIAGNOSIS;
