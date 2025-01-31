/*Program Name : CA03.sas																														*/
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	11-15-2016																												*/
/*																																				*/
/*Program Last Modified Date:	12-30-2016: formatting updated.																												*/
/*							:	01-04-2017: title header to all CAPS and selective bold styling for by var.										*/
/*							:	01-26-2017: Fixed page breaks and end of record marker															*/
/*Program Description:	Creates CA03 Chalk Talk Report: Marginals  for NBS5.1 																	*/
/*																																				*/
/*Comments:																																		*/


%macro CA03;

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

%Formats;

proc sql buffersize=1M;
create table STD_HIV_DATAMART1 as 
select a.* from STD_HIV_DATAMART a 
inner join nbs_rdb.investigation e  on e.investigation_key =a.investigation_KEY
and e.INV_CASE_STATUS in ('Probable','Confirmed') where diagnosis_cd is not null  and diagnosis_cd~='' 
;
quit;

proc sql;
create table patient_init as  
select distinct upcase(a.PATIENT_NAME)as Case_name label = 'Case_name'
,a.DIAGNOSIS_CD as dx  label = 'Dx' 
,a.INV_LOCAL_ID as case_number  label = 'case_number',
a.INVESTIGATION_KEY  ,ix_type_cd, e.CTT_PROCESSING_DECISION ,
a.EPI_LINK_ID as lot_num label = 'LOT NUMBER' , e.CTT_REFERRAL_BASIS ,trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART1 a   left outer join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
left outer join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= b.D_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
left outer join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
left outer join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
where not missing (dx)
order by keyvar, case_number ,Case_name ;
quit;

%chk_mv;

proc sql;
create table patient_dte as  
select distinct 
datepart(c.IX_DATE) as OI_Date format = mmddyy10.  ,
a.EPI_LINK_ID as lot_num label = 'LOT NUMBER' , trim(cats(a.EPI_LINK_ID , a.INV_LOCAL_ID)) as keyvar 
FROM STD_HIV_DATAMART1 a   inner join nbs_rdb.F_INTERVIEW_CASE b  on a.INVESTIGATION_KEY =b.INVESTIGATION_KEY
inner join nbs_rdb.F_CONTACT_RECORD_CASE d on a.INVESTIGATION_KEY = d.SUBJECT_INVESTIGATION_KEY
inner join nbs_rdb.D_INTERVIEW c  on c.D_INTERVIEW_KEY= d.CONTACT_INTERVIEW_KEY and c.RECORD_STATUS_CD~='LOG_DEL'
inner join  nbs_rdb.D_CONTACT_RECORD e on e.D_CONTACT_RECORD_KEY = d.D_CONTACT_RECORD_KEY and e.RECORD_STATUS_CD~='LOG_DEL'
where not missing (a.DIAGNOSIS_CD) and not missing (c.IX_DATE) and e.CTT_PROCESSING_DECISION in ('Insufficient Info') and d.CONTACT_INTERVIEW_KEY ~=1
order by keyvar;
quit;

proc sql;
create table named as
 select distinct 
            datepart(b.CTT_FIRST_SEX_EXP_DT) as named_first_expo_dt format = mmddyy10.  
            ,datepart(b.CTT_FIRST_NDLSHARE_EXP_DT) as named_first_exp_dt format = mmddyy10.
,           b.CTT_SEX_EXP_FREQ as named_freq 
 ,          b.CTT_NDLSHARE_EXP_FREQ as named_ndl_shr_freq
,           datepart(b.CTT_LAST_SEX_EXP_DT) as  named_last_exp_dt format = mmddyy10.  

,           substr(b.CTT_REFERRAL_BASIS,1,2) as named_ref
,           propcase(catx(", ",G.PATIENT_LAST_NAME, G.PATIENT_FIRST_NAME,  G.PATIENT_MIDDLE_NAME))  as Named_pt_name label = 'Named_pt_name'
,			G.PATIENT_KEY
,g.PATIENT_CURRENT_SEX  
,g.PATIENT_CURR_SEX_UNK_RSN
,           G.PATIENT_PREFERRED_GENDER 

,           c.EPI_LINK_ID as lot_num label = 'LOT NUMBER',
trim(cats(C.EPI_LINK_ID , c.INV_LOCAL_ID)) as keyvar, LOCAL_ID

from nbs_rdb.F_CONTACT_RECORD_CASE a 
inner join nbs_rdb.D_CONTACT_RECORD b on a.D_CONTACT_RECORD_KEY =b.D_CONTACT_RECORD_KEY and b.RECORD_STATUS_CD~='LOG_DEL'
INNER JOIN NBS_RDB.D_PATIENT G ON G.PATIENT_KEY = A.CONTACT_KEY
inner join STD_HIV_DATAMART1 as c on c.INVESTIGATION_KEY = a.SUBJECT_INVESTIGATION_KEY
LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART F ON F.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
inner join nbs_rdb.INVESTIGATION as d on d.INVESTIGATION_KEY = a.CONTACT_INVESTIGATION_KEY
where  b.CTT_PROCESSING_DECISION in ('Insufficient Info') 
and not missing (c.diagnosis_cd)
order by keyvar
;
quit;

proc sql;
create table marg_count as
select keyvar, count(distinct LOCAL_ID) as marg_count 
from named  
group by keyvar 
order by keyvar;
quit;

options missing = 0 ; 
data t1 ;
merge patient_init  marg_count patient_dte;
by keyvar;
drop CTT_REFERRAL_BASIS IX_TYPE_CD INVESTIGATION_KEY CTT_PROCESSING_DECISION ;
run;

proc sort noduprecs;
by _all_;
run;

proc sort data = t1;
  by keyvar case_name  ;
run;

data named ;
set named ;
if named_ref = 'P2' then  named_first_expo_dt = named_first_exp_dt ;
if missing (named_freq) and not missing (named_ndl_shr_freq) then named_freq = named_ndl_shr_freq;
if not missing (PATIENT_PREFERRED_GENDER) then gender_named = PATIENT_PREFERRED_GENDER;
if missing (PATIENT_PREFERRED_GENDER)  and PATIENT_CURR_SEX_UNK_RSN = 'Refused' then gender_named = PATIENT_CURRENT_SEX;
if missing (PATIENT_PREFERRED_GENDER)  and missing (PATIENT_CURR_SEX_UNK_RSN) then gender_named = PATIENT_CURRENT_SEX;
format gender_named $gender. ; 
drop named_first_exp_dt named_ndl_shr_freq PATIENT_CURRENT_SEX PATIENT_PREFERRED_GENDER PATIENT_CURR_SEX_UNK_RSN;
run;

proc sort data = named;
  by keyvar  Named_pt_name patient_key ;
run;

data p4 (drop = proc_dec PATIENT_KEY keyvar);
merge t1(in=a) named (in = b);
by keyvar;
run;

DATA PAT ;
SET p4;
VAR = SUBSTR(case_number,4,8);
   new = input(VAR,8.);
   DROP VAR ; 
   run;

proc sql;
create table firts as 
select distinct Case_name ,case_number ,dx,lot_num,new  ,marg_count
from pat where not missing (lot_num)
order by lot_num , new;
quit;

%chk_mv;

proc sql;
create table sec as 
select distinct case_number ,lot_num,new  ,OI_Date ,Named_pt_name ,
named_ref ,gender_named , named_first_expo_dt ,  named_freq, named_last_exp_dt
from pat where not missing (lot_num)
order by lot_num , new;
quit;



goptions device=actximg;

ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.50in rightmargin=.50in  nobyline  papersize=a4;
ods escapechar='^'; 
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "__________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: <<&footer >>";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";
OPTIONS NOQUOTELENMAX;

%if %upcase(&exporttype)=REPORT %then %do;
options printerpath=pdf;
ods PDF body = sock  style=styles.listing notoc uniform startpage=no;
TITLE1 bold f= Calibri h=14pt wrap j=c "%upcase(&reportTitle)"		 	;
TITLE2 " ";
%MACRO hippp;
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

/*ods proclabel = "Report 1" ;*/
TITLE1 bold f= Calibri h=14pt wrap j=c "%upcase(&reportTitle)"		 	;
/*TITLE2  f= Calibri j=C h=12pt  "Lot: " f= Calibri h=12pt   "#byval(lot_num)"		;*/
TITLE2 	  f=Calibri j=c h=12pt '^{style[fontweight=bold] #byvar(lot_num):} ^{style[fontweight=medium]#byval(lot_num)}';
TITLE3 " ";
proc report nofs  data = FIRTS nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
by lot_num new;
where not missing (lot_num) and case_Number ="&case_Number"
     AND lot_num ="&lot_num";
columns  (Case_name case_number dx new  marg_count)
 				;
define  Case_name /group order page  order=internal 'CASE NAME' left style = [cellwidth=60mm   rules=none];
define  case_number    / group'CASE NUMBER'  left style = [cellwidth=40mm   rules=none];
define  dx    / group 'DX' left   style = [cellwidth=40mm   rules=none];
define  new    / noprint;
define  marg_count    / group '# MARGINALS'  center  style(column)={just=center cellwidth=40mm rules=none }
style(header)={just=center cellwidth=40mm rules=none};
run;
/*		 OI_Date Named_pt_name named_ref gender_named  named_first_expo_dt   named_freq named_last_exp_dt */

proc report nofs  data = sec nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }
style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 just=left cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  }
style(column)={font_size=7pt vjust = middle WHITESPACE=pre_line NOBREAKSPACE=on } ;
by lot_num new;
where not missing (lot_num) and case_Number ="&case_Number"
     AND lot_num ="&lot_num";
 columns (OI_Date Named_pt_name named_ref gender_named  named_first_expo_dt   named_freq named_last_exp_dt);
 define  OI_Date    / display 'IX  DATE' left style = [cellwidth=25mm    rules=none];
define 	Named_pt_name    / display 'NAMED'  left style = [cellwidth=30mm   rules=none] ;
define  named_ref    / display 'REF. BASIS'  center format=$ref. style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none} ;
define  gender_named     / display  'GENDER' format = $gender. center  style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none}; 
define  named_first_expo_dt/ display '1ST EXPOSE' format =mmddyy10. center  style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define  named_freq    / display 'FREQ.'  center style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};
define  named_last_exp_dt/ display 'LAST EXPOSE' left format =mmddyy10.  style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};
compute before; 
    len=130; 
    len2=len; 
    text=repeat("********************************************************************************************************************",136); 
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
%hippp
QUIT;
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
	      %export(work,pat,sock,&exporttype);
	Title;
	%finish:
	%mend CA03;
%CA03;
