%macro TB_Summary_count;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
data initData;
Time1=&BEGIN_RANGE;
format Time1 datetime.;
run;
data finalData;
Time2=&END_RANGE;
format Time2 datetime.;
run;
proc sql;
create table newData as
select * from initData full join finalData 
on Time1=Time2;
quit;

data newData2;
length monthYearTxt $50;
set newData;
dateNew1= DATEPART(Time1);
dateNew2= DATEPART(Time2);
if dateNew1 ~=. then monthYear= dateNew1;
if dateNew2 ~=. then monthYear=dateNew2;
run;
proc expand data=newData2 out=newData3 to=month method=none;
      id monthYear;
run;
data newData3;
length monthYearTxt $50;
length monthText $20;
set newData3;
Year = year(monthYear);
month= month(monthYear);
sasdate=mdy(month,1, year);
if month = 1 then monthText='January';
if month = 2 then monthText='February';
if month = 3 then monthText='March';
if month = 4 then monthText='April';
if month = 5 then monthText='May';
if month = 6 then monthText='June';
if month = 7 then monthText='July';
if month = 8 then monthText='August';
if month = 9 then monthText='September';
if month = 10 then monthText='October';
if month = 11 then monthText='November';
if month = 12 then monthText='December';
monthYearTxt= trim(monthText)||trim(year);
run;

PROC SQL;
CREATE TABLE TB_CASE_VER_REPORT_METATDATA AS 
SELECT QUESTION_IDENTIFIER, RDB_COLUMN_NM, RDB_TABLE_NM, USER_DEFINED_COLUMN_NM,DATAMART_NM 
FROM NBS_ODS.NBS_UI_METADATA INNER JOIN NBS_ODS.NBS_RDB_METADATA
ON NBS_UI_METADATA.NBS_UI_METADATA_UID=NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN NBS_SRT.CONDITION_CODE ON CONDITION_CODE.INVESTIGATION_FORM_CD=NBS_UI_METADATA.INVESTIGATION_FORM_CD
INNER JOIN NBS_ODS.NBS_PAGE ON NBS_PAGE.FORM_CD=CONDITION_CODE.INVESTIGATION_FORM_CD
WHERE QUESTION_IDENTIFIER IN ('INV1109','INV111' ) AND CONDITION_CD IN ('102201');
QUIT;



DATA TB_CASE_VER_REPORT_METATDATA2;
SET TB_CASE_VER_REPORT_METATDATA;
 if QUESTION_IDENTIFIER='INV1109' then call symputx('ADM_PREV_COUNT_CASE', COMPRESS(USER_DEFINED_COLUMN_NM)); 
 if QUESTION_IDENTIFIER='INV111' then call symputx('INV_RPT_DT', COMPRESS(USER_DEFINED_COLUMN_NM)); 

RUN;


PROC SQL;
CREATE TABLE TBSUMMARY_COUNT_RPT AS SELECT &INV_RPT_DT as DATE_REPORTED 'DATE_REPORTED', &ADM_PREV_COUNT_CASE as ADM_PREV_COUNT_CASE 'ADM_PREV_COUNT_CASE' 
 FROM work.&DataSourceName where disease_cd='102201' and &INV_RPT_DT >=&BEGIN_RANGE and &INV_RPT_DT <=&END_RANGE;
QUIT;

PROC SQL;
CREATE TABLE TBSUMMARY_COUNT_RPT_CODED
AS SELECT CODE_VALUE_GENERAL.CODE AS CASE_SUMMARY_CODE, CODE_VALUE_GENERAL.CODE_SHORT_DESC_TXT AS CASE_SUMMARY_CODE_DESC
FROM NBS_ODS.NBS_UI_METADATA INNER JOIN NBS_ODS.NBS_RDB_METADATA  
ON NBS_UI_METADATA.NBS_UI_METADATA_UID=NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN NBS_SRT.CONDITION_CODE ON CONDITION_CODE.INVESTIGATION_FORM_CD=NBS_UI_METADATA.INVESTIGATION_FORM_CD
INNER JOIN NBS_SRT.CODESET ON  CODESET.CODE_SET_GROUP_ID=NBS_UI_METADATA.CODE_SET_GROUP_ID
INNER JOIN NBS_SRT.CODE_VALUE_GENERAL ON
CODESET.CODE_SET_NM=CODE_VALUE_GENERAL.CODE_SET_NM
WHERE QUESTION_IDENTIFIER IN ('INV1109') AND CONDITION_CD IN ('102201');
QUIT;

PROC SQL NOPRINT;
	CREATE TABLE TBSUMMARY_COUNT_RPT_BASE AS SELECT COMPRESS(CASE_SUMMARY_CODE) AS CASE_SUMMARY_CODE 'CASE_SUMMARY_CODE', DATE_REPORTED
FROM TBSUMMARY_COUNT_RPT LEFT JOIN TBSUMMARY_COUNT_RPT_CODED ON TBSUMMARY_COUNT_RPT_CODED.CASE_SUMMARY_CODE_DESC=TBSUMMARY_COUNT_RPT.ADM_PREV_COUNT_CASE;
QUIT;


data TBSUMMARY_COUNT_RPT1;
set TBSUMMARY_COUNT_RPT_BASE;
dateNew= DATEPART(DATE_REPORTED);
run;
data TBSUMMARY_COUNT_RPT2;
set TBSUMMARY_COUNT_RPT1;
length monthText $50;
	date= day(dateNew);
   Year = year(dateNew);
   month= month(dateNew);
if month = 1 then monthText='January';
if month = 2 then monthText='February';
if month = 3 then monthText='March';
if month = 4 then monthText='April';
if month = 5 then monthText='May';
if month = 6 then monthText='June';
if month = 7 then monthText='July';
if month = 8 then monthText='August';
if month = 9 then monthText='September';
if month = 10 then monthText='October';
if month = 11 then monthText='November';
if month = 12 then monthText='December';
MonthYear = trim(monthText)||trim(year);
newDate= mdy(month, date, year);
if CASE_SUMMARY_CODE ='N' then count_status_num =1;
if CASE_SUMMARY_CODE ='PHC659' then  non_count_status_num=1;
if CASE_SUMMARY_CODE ='PHC660' then  non_count_status_num=1;
if missing(CASE_SUMMARY_CODE) then  non_count_status_num=1;
run;

proc sql;
create table TBSUMMARY_COUNT_RPT3 as
select MonthYear,month, year, sum(count_status_num) as counted_cases, sum(non_count_status_num) as non_counted_cases 
from TBSUMMARY_COUNT_RPT2
GROUP BY MonthYear , month, year
ORDER BY MonthYear;
quit;

data TBSUMMARY_COUNT_RPT4;
set TBSUMMARY_COUNT_RPT3;
sasdate=mdy(month,1, year);
total_cases =counted_cases + non_counted_cases;
/*if lengthn(counted_cases) gt 0 then counted_cases=counted_cases; else if counted_cases= 0;
if lengthn(non_count_status_num) then non_count_status_num=0;*/
run;
proc sort data=TBSUMMARY_COUNT_RPT4;  by MonthYear; run;

proc sql;
create table TBSUMMARY_COUNT_RPT5 as 
select * from newData3 left outer join TBSUMMARY_COUNT_RPT4 on newData3.monthYearTxt=TBSUMMARY_COUNT_RPT4.MonthYear;
quit;

PROC SORT DATA = TBSUMMARY_COUNT_RPT5;
BY sasdate;

data TBSUMMARY_COUNT_RPT6(keep=MonthYear monthYearTxt counted_cases non_counted_cases total_cases sasdate);
set TBSUMMARY_COUNT_RPT5;
length counted_cases noncounted_cases 8 MonthYearText $50; 
if counted_cases =. then counted_cases=0;  
if non_counted_cases =. then non_counted_cases=0;  
run;


%footnote;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");
proc report data=TBSUMMARY_COUNT_RPT6  nowd  MISSING;

column  monthYearTxt counted_cases non_counted_cases total_cases1;
  define monthYearTxt / order display '' left;
  define counted_cases /display  'Counted Cases' analysis sum center;
  define  non_counted_cases /display 'Non-Counted Cases' center analysis sum;
  define total_cases1 /computed 'Total Cases' center style(column)=[font_weight=bold];
  compute total_cases1;
  total_cases1 = non_counted_cases.sum + counted_cases.sum;
  if _break_ = ' ' then 
	_sum_total_cases1 + total_cases1;
	 if compress(monthYearTxt) ='' then
	 	CALL DEFINE('monthYearTxt','style','style=[pretext="<b>Total</b>"]');	

   endcomp;
   compute after;
      total_cases1 = _sum_total_cases1;
   endcomp;
  rbreak after / summarize dol;
	      
   title BOLD HEIGHT=5 FONT=Times <p align="center">'TB Record Count - Summary Report by Report Date - 2020 RVCT'</p>;
run;
quit;
ods html close;
%end;
%else 
      %export(work,TBSUMMARY_COUNT_RPT6,sock,&exporttype);
Title;

%finish:

%mend TB_Summary_count;
%TB_Summary_count;
