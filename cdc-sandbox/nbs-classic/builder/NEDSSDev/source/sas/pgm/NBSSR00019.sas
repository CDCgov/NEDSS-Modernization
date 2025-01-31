%macro NBSSR00019;
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

data sr00019;
	set work.TB_DATAMART;
run;

proc sql;
create table countSummary19 as
select DATE_REPORTED, count_status from sr00019;
quit;
data countSummary1;
set countSummary19;
dateNew= DATEPART(DATE_REPORTED);
run;
data countSummary2;
set countSummary1;
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
if count_status ~='Count as a TB Case' then count_status='Do not count as a TB Case';
if count_status ='Count as a TB Case' then count_status_num= 1;
else if count_status='Do not count as a TB Case' then non_count_status_num=1;
run; 
proc sql;
create table countSummary3 as
select MonthYear,month, year, sum(count_status_num) as counted_cases, sum(non_count_status_num) as non_counted_cases 
from countSummary2
GROUP BY MonthYear , month, year
ORDER BY MonthYear;
quit;
data countSummary4;
set countSummary3;
sasdate=mdy(month,1, year);
total_cases =counted_cases + non_counted_cases;
/*if lengthn(counted_cases) gt 0 then counted_cases=counted_cases; else if counted_cases= 0;
if lengthn(non_count_status_num) then non_count_status_num=0;*/
run;
proc sort data=countSummary4;  by MonthYear; run;
proc sql;
create table countSummary5 as 
select * from newData3 left outer join countSummary4 on newData3.monthYearTxt=countSummary4.MonthYear;
quit;
data countSummary6(keep=MonthYear counted_cases non_counted_cases total_cases sasdate);
set countSummary5;
length counted_cases noncounted_cases 8 MonthYearText $50; 
if counted_cases =. then counted_cases=0;  
if non_counted_cases =. then non_counted_cases=0;  
run;
PROC SORT DATA = countSummary6;
BY sasdate;

%footnote;
Footnote;
Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_bottom_matter) stylesheet=(URL="nbsreport.css");
proc report data=countSummary6  nowd  MISSING;

column MonthYear  counted_cases non_counted_cases total_cases1;
  define MonthYear / order display '' left;
  define counted_cases /display  'Counted Cases' analysis sum center;
  define  non_counted_cases /display 'Non-Counted Cases' center analysis sum;
  define total_cases1 /computed 'Total Cases' center style(column)=[font_weight=bold];
  compute total_cases1;
  total_cases1 = non_counted_cases.sum + counted_cases.sum;
  if _break_ = ' ' then 
	_sum_total_cases1 + total_cases1;
	 if MonthYear =. then
	 	CALL DEFINE('MonthYear','style','style=[pretext="<b>Total</b>"]');	

   endcomp;
   compute after;
      total_cases1 = _sum_total_cases1;
   endcomp;
  rbreak after / summarize dol;
	      
   title BOLD HEIGHT=5 FONT=Times <p align="center">'TB Record Count - Summary Report by Report Date'</p>;
run;
quit;
ods html close;
%end;
%else 
      %export(work,countSummary6,sock,&exporttype);
Title;

%finish:

%mend NBSSR00019;
%NBSSR00019;
