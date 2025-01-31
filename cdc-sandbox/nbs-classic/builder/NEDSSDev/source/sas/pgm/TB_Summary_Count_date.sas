%macro TB_Summay_Count_date;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
data initData;
Time1=&BEGIN_RANGE;
format Time1 datetime.;
run;
data finalData;
TimeFinal2=&END_RANGE;
Time2=TimeFinal2-1;
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
WHERE QUESTION_IDENTIFIER IN ('INV1109','NBS900' ) AND CONDITION_CD IN ('102201');
QUIT;

DATA TB_CASE_VER_REPORT_METATDATA2;
SET TB_CASE_VER_REPORT_METATDATA;
 if QUESTION_IDENTIFIER='INV1109' then call symputx('ADM_PREV_COUNT_CASE', COMPRESS(USER_DEFINED_COLUMN_NM)); 
 if QUESTION_IDENTIFIER='NBS900' then call symputx('PREV_COUNTED_DATE', COMPRESS(USER_DEFINED_COLUMN_NM)); 
RUN;

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




proc sql;
create table TBSUMMARY_COUNT_RPT as
select &PREV_COUNTED_DATE as COUNT_DATE 'COUNT_DATE',  &ADM_PREV_COUNT_CASE as ADM_PREV_COUNT_CASE 'ADM_PREV_COUNT_CASE', TBSUMMARY_COUNT_RPT_CODED.CASE_SUMMARY_CODE from work.&DataSourceName DSN
LEFT JOIN TBSUMMARY_COUNT_RPT_CODED ON TBSUMMARY_COUNT_RPT_CODED.CASE_SUMMARY_CODE_DESC=DSN. &ADM_PREV_COUNT_CASE
where  &PREV_COUNTED_DATE is not null and TBSUMMARY_COUNT_RPT_CODED.CASE_SUMMARY_CODE='N' 
AND disease_cd='102201'  and  (&PREV_COUNTED_DATE) >=datepart(&BEGIN_RANGE) and  (&PREV_COUNTED_DATE) <=datepart(&END_RANGE);
quit;

proc sql;
create table countSummary20 as
select COUNT_DATE, CASE_SUMMARY_CODE as CASE_SUMMARY_CODE 'CASE_SUMMARY_CODE' from TBSUMMARY_COUNT_RPT;
quit;
data countSummary1;
set countSummary20;
dateNew= COUNT_DATE;
run;
data countSummary2;
set countSummary1;
length MonthYear $50;
	date= day(dateNew);
   Year = year(dateNew);
   month= month(dateNew);
if month = 1 then MonthYear='January';
if month = 2 then MonthYear='February';
if month = 3 then MonthYear='March';
if month = 4 then MonthYear='April';
if month = 5 then MonthYear='May';
if month = 6 then MonthYear='June';
if month = 7 then MonthYear='July';
if month = 8 then MonthYear='August';
if month = 9 then MonthYear='September';
if month = 10 then MonthYear='October';
if month = 11 then MonthYear='November';
if month = 12 then MonthYear='December';
MonthYear = trim(MonthYear)||trim(year);
newDate= mdy(month, date, year);
if CASE_SUMMARY_CODE ='N' then count_status='Count as a TB Case';
if count_status ='Count as a TB Case' then count_status_num= 1;

run;     
 
proc sql;
create table countSummary3 as
select MonthYear,month, year, sum(count_status_num) as counted_cases  
from countSummary2
GROUP BY MonthYear , month, year
ORDER BY MonthYear;
quit;


proc sort data=countSummary3;  by MonthYear; run;

proc sql;
create table countSummary5 as 
select * from newData3 left outer join countSummary3 on newData3.monthYearTxt=countSummary3.MonthYear;
quit;

data countSummary6(keep=monthYearTxt counted_cases non_counted_cases total_cases sasdate);
set countSummary5;
length counted_cases noncounted_cases 8 MonthYearText $50; 
if counted_cases =. then counted_cases=0;  
run;

PROC SORT DATA = countSummary6;
BY sasdate;

%footnote;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");
proc report data=countSummary6  nowd;

column monthYearTxt  counted_cases ;
  define monthYearTxt /  order=internal   ''   style(column)={just=left  cellwidth=1.8 in};
  define counted_cases /display  'Counted Cases' analysis sum center style=[cellwidth=1.8 in just=c];


compute  monthYearTxt;
	if monthYearTxt =  ' ' then
	CALL DEFINE('monthYearTxt','style','style=[pretext="<b>Total</b>"]');	
endcomp;



rbreak after / summarize dol;
	      
    title BOLD HEIGHT=5 FONT=Times <p align="center">'TB Record Count - Summary Report by Count Date - 2020 RVCT'</p>;
run;
quit;
ods html close;
%end;
%else 
      %export(work,countSummary6,sock,&exporttype);
Title;

%finish:

%mend TB_Summay_Count_date;
%TB_Summay_Count_date;
