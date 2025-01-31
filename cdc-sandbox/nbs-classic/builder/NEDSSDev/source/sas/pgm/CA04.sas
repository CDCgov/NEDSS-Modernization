/*Program Name : CA04.sas																													    */
/*																																				*/
/*Original Program Created by : SA																												*/
/*																																				*/
/*Program Created Date:	12-12-2016																												*/
/*																																				*/
/*Program Last Modified Date:	12-30-2016: formatting complete																												*/
/*							:   01-02-2017: Fixed error when their is no data for conformation date selected filter.Page gives no error			*/
/*							:																													*/
/*Program Description:	Creates CA04 Report: detail hangout  for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%Formats;

data codes;
  input dx :& $3.;
cards;
200
300
710
720
730
740
745
750
755
790
900
950
run;

%macro CA04;

%if  %upcase(&skip)=YES %then
      %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;



proc format;
value $place
''='# Associated Cases:'
other = '# Associated Cases:'
;
value $combdx
'300','350' = '300'
'200' = '200'
'710' = '710'
'720' = '720'
'730' ='730'
'740'='740'
'745' ='745'
'750'='750'
'755'='755'
'790'='790'
'900'='900'
'950'='950'       
;
value $states
'Georgia' = 'GA'
;
run;



proc sql buffersize=1M;
create table patient_init as  
select distinct
upcase(PATIENT_NAME)as Case_name label = 'Case_name'
,ADI_900_STATUS_CD as NineHund 
,INVESTIGATOR_CURRENT_QC
,datepart(CONFIRMATION_DT)  as confirm_date format = mmddyy10. label = 'confirm_date' 
,DIAGNOSIS_CD as dx  label = 'Dx' format = $combdx.
,INV_LOCAL_ID as case_number  label = 'case_number'
,CONTACT_INV_LOCAL_ID
,LOCAL_ID
 ,catx("",PATIENT_LAST_NAME, ", ",PATIENT_FIRST_NAME, " ", PATIENT_MIDDLE_NAME) as CONTACT_NAME
,CONTACT_INV_CASE_STATUS
,FL_FUP_DISPOSITION as  dispo format= $dispo., datepart(FL_FUP_DISPO_DT) as dispo_dt format = mmddyy10.
,CTT_DISPOSITION as ct_dispo format= $dispo. ,datepart(CTT_DISPO_DT) as  ct_dispo_dt format = mmddyy10.
,CONTACT_CURRENT_SEX format = $gender.
,CTT_REFERRAL_BASIS
, REFERRAL_BASIS 
 ,PLACE_NAME
,PLACE_STREET_ADDRESS_1,
PLACE_STREET_ADDRESS_2 
,PLACE_CITY,PLACE_STATE_DESC as new_state label ='new_state' , PLACE_ZIP
,PLACE_TYPE_DESCRIPTION
FROM v_chalk_talk 
where 
DIAGNOSIS_CD is not null and DIAGNOSIS_CD in ('300','350','200','710', '720','730','740','745','750','755','790','900','950') 
 ;
quit; 


%chk_mv ;

data init_add;
length newadd   $100;
length Type add1 add2 str $50;
set patient_init;
Type  = trim(PLACE_TYPE_DESCRIPTION);
Str = trim(PLACE_NAME);
add1 = catx(" ",PLACE_STREET_ADDRESS_1,PLACE_STREET_ADDRESS_2) ;
add2 = catx(" ",PLACE_CITY,new_state,PLACE_ZIP);
newadd = catx(' ^{newline 1}',str,add1,add2);
label newadd = 'Hang-Out Name' ; 
run;




proc sql;
create table sumary as 
select distinct newadd,Type, dx, count(distinct case_number) as Count
from init_add 
group by newadd,Type, dx 
order by newadd,Type, dx
;quit;

proc sql;
  create table MERGED as
  select unique cartesian.newadd
  				,cartesian.Type
              , cartesian.dx
              , coalesce(sumary.COUNT,0) as COUNT
  from (select unique newadd,Type, codes.dx 
        from sumary, codes) as cartesian 
         left join 
       sumary                as sumary
         on  sumary.dx  =cartesian.dx
         and sumary.newadd=cartesian.newadd
		 and sumary.type=cartesian.type
  order by newadd  ;
quit;

proc transpose data=merged 
               out =WANT(drop=_NAME_);
  id dx;
  by newadd type;
  var COUNT;
run;

proc sql;
create table cohort as 
select distinct newadd,  type, count(distinct LOCAL_ID) as cohort_count
from init_add 
where CTT_REFERRAL_BASIS in ('C1- Cohort')
group by  newadd, type
order by newadd
;quit;

data a ;
merge want cohort;
by newadd type;
run;

proc sql;
create table named as 
select distinct  newadd ,  type, Case_name , dx , dx as named_dx label ='named_dx',case_number, NineHund , REFERRAL_BASIS, INVESTIGATOR_CURRENT_QC
from init_add
order by newadd
;quit;

proc sort data = named;
  by newadd type;
run;

data named;
  set named;
  by newadd type ;
 If first.newadd and first.type  then n_Count=0;
 n_Count+1;
 nbkey = cats(newadd, type, n_count);
 drop n_count;
run;

proc sort data = named;
  by nbkey ;
run;

proc sql;
create table name_cohort as 
select distinct  newadd,  type,
CONTACT_NAME as cname, 
dx as c_dx, 
CONTACT_CURRENT_SEX as gender format = $gender., 
dispo_dt, dispo, ct_dispo_dt, ct_dispo
from init_add
where CTT_REFERRAL_BASIS in ('C1- Cohort') and CONTACT_INV_CASE_STATUS not  IN('Probable', 'Confirmed')
order by newadd
;quit;

data name_cohort ;
set name_cohort ;
if missing (dispo) then dispo = ct_dispo;
if missing (dispo_dt) then dispo_dt = ct_dispo_dt;
drop ct_dispo ct_dispo_dt;
run;

proc sort data = name_cohort;
  by newadd type ;
run;

data name_cohort;
  set name_cohort;
  by newadd type ;
 If first.newadd and first.type  then n_Count=0;
 n_Count+1;
 nbkey = cats(newadd, type, n_count);
 drop n_count;
run;

proc sort data = name_cohort;
  by nbkey ;
run;

data b (drop =dx);
merge named name_cohort;
by nbkey;
run;

proc sort data = b;
by nbkey ;
drop nbkey;
run;

data final;
merge a b;
by newadd type ;
if missing (cohort_count) then cohort_count = 0;
/*else if missing (count) then count = 0;*/
else if NineHund = '00' then NineHund = ' ' ;
blank = "# Associated Cases";
run;

proc sort noduprecs ;
by _all_;
run;

proc sort data = final ;
by   newadd Type DESCENDING cname;
run;

proc sql noprint;
select count(*) into :record_count 
from patient_init;
quit;

/*Note: this chk_mv seems to fail as sqlobs resolves to val of 1 even when no data is created*/
%chk_mv ;

goptions device=actximg;
%let reportTitle = CHALK TALK REPORT: HANGOUT DETAIL ;
ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = '' 
topmargin=.25in bottommargin=.25in leftmargin=.50in rightmargin=.50in  nobyline  papersize=a4;
ods escapechar='^'; 
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "_____________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";

%if %upcase(&exporttype)=REPORT %then %do;

%if &record_count eq 0 %then %do;
data _null_;
		file sock;
		put 'There is no data for the criteria you selected. Please check your selection and try again. ';
	run;
%end;



%else %do;
options printerpath=pdf;
ods PDF body = sock  style=styles.listing notoc uniform startpage=yes;
TITLE1 bold f= Calibri h=14pt j=c "&reportTitle";
TITLE2      " ";
TITLE3 	  f=Calibri j=c h=12pt '^{style[fontweight=bold] #byvar(newadd):} ^{style[fontweight=medium]#byval(newadd)}';
/*TITLE3    f= Calibri j=c h=12pt    "#byval(newadd)";*/
TITLE4 	  f=Calibri j=c h=12pt '^{style[fontweight=bold] #byvar(Type):} ^{style[fontweight=medium]#byval(Type)}';
/*TITLE4    f= Calibri j=c h=12pt    "#byval(Type)";*/

proc report nofs  data = FINAL nowd spanrows   split='~' headline missing 
style(header)={just=left CELLHEIGHT=12pt  font_weight=bold font_face="Calibri"  font_size = 11pt BORDERSPACING=8pt   cellspacing=1pt cellpadding=1pt
 borderbottomcolor=black  cellwidth=50%  borderbottomwidth=2 BORDERWIDTH=5pt rules=cols bordertopcolor=black
BORDERBOTTOMWIDTH=0.25pt BORDERTOPWIDTH=0.20pt  BORDERCOLORLIGHT= BLACK FRAMEBORDER=ON   frame=above WHITESPACE=pre_line }

style(report)={font_size=10pt cellpadding=0.0 NOBREAKSPACE=on 
 cellspacing=0 rules=none font_face="Calibri" frame = void vjust = middle  };


by newadd Type  ;
column newadd blank _200 _300 _710 _720 _730 _740 _745 _750 _755 _790 _900 _950   cohort_count 
Case_name named_dx case_number NineHund REFERRAL_BASIS INVESTIGATOR_CURRENT_QC
cname gender dispo dispo_dt  c_dx    
;

define  newadd /group page noprint ' '  left style = {cellwidth=25mm  rules=none };
define blank /group 'Diagnosis Code' left  style = {cellwidth=25mm  rules=none }; 
define  _200    /group '200' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _300    /group '300' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _710    /group '710' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _720    /group '720' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _730    /group '730' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _740    /group '740' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _745   /group '745' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _750   /group '750' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _755   /group '755' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _790    /group '790' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _900    /group '900' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
define  _950    /group '950' center   style(column)={just=center cellwidth=11mm rules=none }
style(header)={just=center cellwidth=11mm rules=none};
   define  cohort_count    / group '# Cohorts' center style(column)={just=center cellwidth=20mm rules=none }
style(header)={just=center cellwidth=20mm rules=none};

define Case_name /display 'Name'  left style = {cellwidth=40mm  rules=none };
define named_dx/display 'Diagnosis'  center style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define case_number/display 'Case Number' center style(column)={just=center cellwidth=35mm rules=none }
style(header)={just=center cellwidth=35mm rules=none};
define NineHund /display '900 Status' center style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define REFERRAL_BASIS  /display 'Referral Basis' format=$ref. center style(column)={just=center cellwidth=25mm rules=none }
style(header)={just=center cellwidth=25mm rules=none};
define INVESTIGATOR_CURRENT_QC/display 'Current Inv' center style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};

define cname /display 'Cohort Name'  left style = {cellwidth=60mm  rules=none };
define gender  /display 'Gender' center style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};
define dispo/display 'Dispo' center style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};
define dispo_dt /display 'Dispo Date' center style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};
define c_dx/display 'Dx'  center style(column)={just=center cellwidth=30mm rules=none }
style(header)={just=center cellwidth=30mm rules=none};

break after newadd / page  ; 
compute before _page_ / style = {just = c NOBREAKSPACE=on asis=on};
endcomp;
break after  newadd / skip dul;
run;

	ods pdf close;
%end;


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
	      %export(work,FINAL,sock,&exporttype);
	Title;
	%finish:
	%mend CA04;
%CA04;
