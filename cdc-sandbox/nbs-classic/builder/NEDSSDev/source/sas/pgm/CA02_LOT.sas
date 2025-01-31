/*Program Name : CA02_LOT.sas																													*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	11-14-2016																												*/
/*																																				*/
/*Program Last Modified Date:12-27-2016 :added startpage option to ods 																													*/
/*							:01-06-2017:Formatting changes for report 																													*/
/*							:01-12-2017: footer fixed.																													*/
/*							:01-23-2017: no page breaks with end of line formatted, deleted end of records statement							*/
/*Program Description:	Creates CA02 Chalk Talk Report: Lot  for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%Formats;

%macro CA02_LOT;

%if  %upcase(&skip)=YES %then
      %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;

proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as 
select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') where a.diagnosis_cd is not null and a.CA_INTERVIEWER_ASSIGN_DT~=.;
quit;


proc sql;
create table patient_init as  
select distinct upcase(a.PATIENT_NAME)as Case_name label = 'Case_name'
,a.PATIENT_CURRENT_SEX , e.local_id 
,a.PATIENT_PREFERRED_GENDER
,a.PATIENT_CURR_SEX_UNK_RSN
,a.PATIENT_PREGNANT_IND as Preg  label = 'PREG' format = $preg.
,a.PATIENT_MARITAL_STATUS as marital label = 'Marital' format = $mar.
,a.ADI_900_STATUS as NineHund format = $nine.
,datepart(a.CC_CLOSED_DT)  as closed_date format = mmddyy10. label = 'closed_date' 
,a.DIAGNOSIS_CD as dx  label = 'Dx' 
,a.INV_LOCAL_ID as case_number  label = 'case_number',
a.INVESTIGATION_KEY  , c.ix_type_cd, d.contact_interview_key,
a.EPI_LINK_ID as lot_num label = 'LOT NUMBER' , e.CTT_REFERRAL_BASIS ,trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
left outer join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
where not missing (dx) and e.CTT_REL_WITH_PATIENT not in ('Other infected patient')
order by keyvar, case_number ,Case_name ,e.CTT_REFERRAL_BASIS;
quit;
%chk_mv;
proc sql;
create table patient_dte as  
select distinct 
datepart(c.IX_DATE) as OI_Date format = mmddyy10.  ,
a.EPI_LINK_ID as lot_num label = 'LOT NUMBER' , trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
left outer join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
where not missing (a.DIAGNOSIS_CD) and e.CTT_REL_WITH_PATIENT not in ('Other infected patient')
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
if not missing (PATIENT_PREFERRED_GENDER) then Gender = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then Gender = PATIENT_CURRENT_SEX;
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
 select distinct 
 b.CTT_PROCESSING_DECISION as proc_dec format = $field. ,
            b.CTT_FIRST_SEX_EXP_DT as named_first_expo_dt   
            ,b.CTT_FIRST_NDLSHARE_EXP_DT as named_first_exp_dt 
,           b.CTT_SEX_EXP_FREQ as named_freq 
,           b.CTT_NDLSHARE_EXP_FREQ as named_ndl_shr_freq
,           b.CTT_LAST_SEX_EXP_DT as  named_last_exp_dt 
,           substr(b.CTT_REFERRAL_BASIS,1,2) as named_ref
,           propcase(catx(", ",G.PATIENT_LAST_NAME, G.PATIENT_FIRST_NAME,  G.PATIENT_MIDDLE_NAME))  as Named_pt_name label = 'Named_pt_name'
,			G.PATIENT_KEY
,			g.PATIENT_CURRENT_SEX  
,			g.PATIENT_CURR_SEX_UNK_RSN
,           G.PATIENT_PREFERRED_GENDER 
,           F.DIAGNOSIS_CD as dx_named label = 'dx_named'
,           c.EPI_LINK_ID as lot_num label = 'LOT NUMBER'
,           d.INV_LOCAL_ID as named_case_number 'named_case_number'

,datepart(b.CTT_DISPO_DT) as  ct_named_dispo_dt format = mmddyy10., trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar
,f.FL_FUP_DISPOSITION as  named_dispo format= $dispo., datepart(f.FL_FUP_DISPO_DT) as named_dispo_dt format = mmddyy10.
,b.CTT_DISPOSITION as ct_named_dispo format= $dispo. 
from nbs_rdb.F_CONTACT_RECORD_CASE a 
inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY and b.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.D_PATIENT G ON G.PATIENT_KEY = A.CONTACT_KEY
inner join STD_HIV_DATAMART1 as c on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART F ON F.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
inner join nbs_rdb.INVESTIGATION as d on d.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
where b.CTT_REL_WITH_PATIENT not in ('Other infected patient') and not missing (c.diagnosis_cd) and a.contact_interview_key~=1
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

proc sort ;
by named_case_number;
run;


proc sql;
create table name_back_by as 
select distinct D.PATIENT_NAME as bb_name label = 'bb_name' , d.INV_LOCAL_ID as named_case_number 'named_case_number',
G.PATIENT_KEY
from nbs_rdb.F_CONTACT_RECORD_CASE a inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY
inner join STD_HIV_DATAMART1 c  on c.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
INNER JOIN NBS_RDB.STD_HIV_DATAMART d ON A.SUBJECT_INVESTIGATION_KEY = D.INVESTIGATION_KEY
INNER JOIN NBS_RDB.F_INTERVIEW_CASE E ON  E.D_INTERVIEW_KEY=A.CONTACT_INTERVIEW_KEY
INNER JOIN NBS_RDB.d_INTERVIEW F ON E.D_INTERVIEW_KEY = F.D_INTERVIEW_KEY and f.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.D_PATIENT G ON A.SUBJECT_KEY = G.PATIENT_KEY AND A.CONTACT_KEY IN (SELECT PATIENT_KEY FROM named)
where not missing (c.diagnosis_cd) and  substr(b.CTT_REFERRAL_BASIS,1,2) in ('P1','P2','P3')
order by named_case_number;
quit;

data common;
merge p4(in=a) name_back_by (in=b);
by named_case_number;
if a ;
if not missing(bb_name) and not missing(Named_pt_name) then var_w = 'Y';
else var_w = 'N';
run;

proc sort;
by keyvar;
run;


data sa (drop = bb_count keyvar);
  set common;
  by keyvar case_number Case_name ;
 If first.keyvar   then bb_Count=0;
 bb_Count+1;
 rxkey = cats(keyvar,bb_count);
run;

proc sort data = sa;
by rxkey;
run;

proc sql;
create table sx as 
select distinct c.SYM_SIGN_SX_DURATION_IN_DAYS,
c.SYM_SIGN_SX_OBVTN_ONSET_DT as Sym_On_dte,
c.SYM_SIGN_SX_SOURCE as Obs_source label = 'Obs_source' , c.STD_SIGN_SX_ANATOMIC_SITE as Anat_site label = 'Anat_site' ,
c.SYM_SIGNSSYMPTOMS ,trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar
from  STD_HIV_DATAMART1 a left outer join nbs_rdb.F_STD_PAGE_CASE b  on a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INVESTIGATION_REPEAT c on b.D_INVESTIGATION_REPEAT_KEY = c.D_INVESTIGATION_REPEAT_KEY
where not missing (a.diagnosis_cd)
order by keyvar, Sym_On_dte;
quit;

data sx_grp ;
  set sx;
  by keyvar ;
 If first.keyvar   then sx_Count=0;
 sx_Count+1;
 rxkey = cats(keyvar,sx_Count);
run;

proc sort data = sx_grp;
by rxkey;
run;


data p8;
merge sa sx_grp;
by rxkey;
run;


DATA PAT (drop = INVESTIGATION_KEY keyvar nkey PATIENT_KEY nbkey nckey rxkey rx_Count sx_Count);
SET p8;
VAR = SUBSTR(case_number,4,8);
   new = input(VAR,8.);
   DROP VAR ; 
   run;

proc sql;
create table firts as 
select distinct Case_name ,dx ,new ,case_number ,lot_num,cts_count,s_count ,OI_Date , Gender ,preg ,NineHund ,marital ,closed_date
from pat
where not missing(lot_num)
order by lot_num , new;
quit;
%chk_mv;

proc sql;
create table third as 
select distinct case_number ,lot_num, new,
Named_pt_name ,named_ref ,gender_named , named_first_expo_dt  , named_freq , named_last_exp_dt , proc_dec ,named_dispo ,
named_dispo_dt, dx_named , named_case_number , var_w		 
from pat
where not missing(lot_num)
order by lot_num, new;
quit;

proc sql;
create table sec as 
select distinct case_number ,lot_num, new,
SYM_SIGNSSYMPTOMS , Anat_Site , Sym_On_dte , SYM_SIGN_SX_DURATION_IN_DAYS	 
from pat
where not missing(lot_num)
order by lot_num, new;
quit;

	 

goptions device=actximg;

ODS _ALL_ CLOSE;
ODS LISTING CLOSE;
ODS RESULTS;
OPTIONS orientation=landscape   NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.25in rightmargin=.25in  byline  papersize=a4;
ods escapechar='^';
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "___________________________________________________________________________________________________________________________________________________________________________________________________";
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
%MACRO hippi;
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
TITLE2 	  f=Calibri j=c h=12pt '^{style[fontweight=bold] #byvar(lot_num):} ^{style[fontweight=medium]#byval(lot_num)}';
TITLE3 " ";
proc report nofs  data = FIRTS nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=2pt cellpadding=9pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=1.0 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=10pt vjust = middle WHITESPACE=pre_line } ;
by lot_num new;
     WHERE case_Number ="&case_Number"
     AND lot_num ="&lot_num";
COLUMN (Case_name dx new case_number cts_count s_count OI_Date  Gender preg NineHund marital closed_date);
define  Case_name /group   'CASE NAME' left style = [cellwidth=40mm   rules=none NOBREAKSPACE];
define  dx    / group 'DX' left   style = [cellwidth=10mm   rules=none];
define  new    / noprint;
define  case_number    / group'CASE NUMBER'  left style = [cellwidth=35mm   rules=none];
define  cts_count    / group '# CTS'  center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  s_count    / group  '# S/AS'  center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  OI_Date    / group 'OI DATE' left style = [cellwidth=25mm    rules=none];
define  Gender     / group  'GENDER' format = $gender. center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  preg / group 'PREG' format = $preg.  center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  NineHund     / group '900' center  format = $nine.  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  marital     / group 'MARITAL' center  format = $mar. style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  closed_date     / group 'DATE CLOSED' center format =mmddyy10. style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none} ;
run;

/*****************************************************************************************/
proc report nofs  data = third nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=2pt cellpadding=9pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=1.0 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=10pt vjust = middle WHITESPACE=pre_line } ;
   WHERE case_Number ="&case_Number"
     AND lot_num ="&lot_num";
BY lot_num new;
COLUMN Named_pt_name named_ref gender_named  named_first_expo_dt   named_freq named_last_exp_dt 
 		proc_dec named_dispo named_dispo_dt dx_named  named_case_number var_w ;
define 	Named_pt_name    / display 'NAMED'  left style = [cellwidth=38mm   rules=none] ;
define  named_ref    / display 'REF. BASIS'  center format=$ref. style = [cellwidth=20mm   rules=none ] ;
define  gender_named     / display  'GENDER' format = $gender. center  style = [cellwidth=15mm   rules=none]; 
define  named_first_expo_dt/ display '1ST EXPOSE' format =mmddyy10. left  style = [cellwidth=25mm   rules=none];
define  named_freq    / display 'FREQ.'  left style = [cellwidth=15mm   rules=none];
define  named_last_exp_dt/ display 'LAST EXPOSE' left format =mmddyy10.  style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define  proc_dec/ display 'PROC. DECISION' center  style = [cellwidth=30mm   rules=none];
define  named_dispo/ display 'DISPO' left   style = [cellwidth=13mm   rules=none];
define  named_dispo_dt/ display 'DISPO DATE' left format=mmddyy10.  style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define  dx_named    / display 'DX' left   style = [cellwidth=10mm   rules=none];
define  named_case_number / display 'CASE NUMBER' left style = [cellwidth=34mm   rules=none];
define  var_w / display 'NB' left style = [cellwidth=5mm   rules=none];
RUN;
/*****************************************************************************************/
proc report nofs  data = SEC nowd spanrows  split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=2pt cellpadding=9pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=1.0 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=10pt vjust = middle WHITESPACE=pre_line } ;
   WHERE case_Number ="&case_Number"
     AND lot_num ="&lot_num";
BY lot_num new;
COLUMN SYM_SIGNSSYMPTOMS Anat_Site Sym_On_dte SYM_SIGN_SX_DURATION_IN_DAYS  ;
define   SYM_SIGNSSYMPTOMS/ order  = data 'SIGN/SYMPTOM' left  style = [cellwidth=55mm   rules=none ];
define   Anat_Site    / display 'ANATOMIC SITE' left style = [cellwidth=70mm   rules=none];
define   Sym_On_dte    / display 'OBS./ONSET DATE' center style(column)={just=center cellwidth=70mm rules=none }
style(header)={just=center cellwidth=70mm rules=none};
define   SYM_SIGN_SX_DURATION_IN_DAYS/ display 'DURATION' center  style(column)={just=center cellwidth=60mm rules=none }
style(header)={just=center cellwidth=60mm rules=none};
 compute before; 
    len=186; 
    len2=len; 
    text=repeat("********************************************************************************************************************************",196); 
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
%hippi
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
	%mend CA02_LOT;
%CA02_LOT;
