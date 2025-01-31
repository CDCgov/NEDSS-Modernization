***********************************************************************************;
*** Macro: NBSSR00005.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00005;
/*
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
*/
data sr00005;
	set &DataSourceName;
	if state_cd='' then state_cd='N/A';
	if state='' then state='N/A';
run;

proc sql noprint;

Create table sr5 
as select 
state_cd,
State,
/*County,*/
PHC_code_short_desc							,
month(datepart(event_date)) 	as Month	label='Month',
year(datepart(event_date)) 		as year		label='Year',
sum(group_case_cnt)	 			as Cases	label='Cases'

from sr00005
where state ne '' and event_date gt 0 
		and phc_code_short_desc ne ''
		and datepart(event_date) LE date()
group by state_cd,state,/*county,*/PHC_code_short_desc,year,month
order by PHC_code_short_desc,year,month;
quit;
/*
DataSet Sr5 may be empty even when &DataSourceName has records.
This happens if &DataSourceName contans only summary data that  
all have future event_date. 

Until the report date for summary data is Changed to the beginning of the week,
i.e. Sunday, instead of end of week or Saturday, we need to check if SR5 is empty
before continuing with the rest of the program.
*/
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

proc sql noprint;
create table disease 
as select distinct /*county,*/state,state_cd,phc_code_short_desc
from sr5
order by PHC_code_short_desc;

create table state
as select distinct state from sr5 where state <> 'N/A';
quit;

Data _null_;
set sr5(obs=1);
*call symput('stname',trim(State));
call symput('inityear',left(put(year(today())-5,4.)));
call symput('midyear',left(put(year(today())-3,4.)));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
call symput('thismth',left(Put(today(),monyy7.)));
call symput('currdate', put(date(), mmddyy10.));
run;

Data _null_;
set state;
call symput('stname',trim(State));
run;

data sr5;
  set sr5;
  where month le &month and year ge &inityear;
run;

data full(drop=i j);
 set disease;
 do j=5 to 0 by -1;
 	year=&year-j;
	 do i=1 to &month;
   	 month=i;
		cases=0;
		output;
	 end;
 end;
run;

data sr5full;
 merge full sr5;
 by phc_code_short_desc;
run;

proc sort data=sr5full;
	by phc_code_short_desc year month;
run;

data thismon;
set sr5full;
if month=&month and year=&year;
run;

data thismonth;
if 0 then set thismon nobs=numobs;
if numobs=0 then do;
	set sr5full;
	if _n_=1 then do;
		month=&month; year=&year; cases=0;
		output;
	end;
	stop;
end;
else do;
 set thismon;
 output;
end;
run;


Proc means data=thismonth;
by PHC_code_short_desc;
var cases;
output out=thismonth(drop=_type_ _freq_) sum=mthcases;
run;

Proc means data=sr5full;
by PHC_code_short_desc year;
var cases;
output out=rptsr5(drop=_type_ _freq_) sum=;
run;

proc univariate data=rptsr5(where=(year ne &year)) noprint;
by PHC_code_short_desc;
var cases;
output out=med median=med_year;
run;

Data rptdata;
format pctchg percent9.;
retain cases&prevyear cases&year;
merge rptsr5(in=in1) med(in=in2) thismonth(in=in3);
by PHC_code_short_desc;
if first.PHC_code_short_desc then do;
 cases&prevyear=0; cases&year=0;
end;
if mthcases=. then mthcases=0;
if year=&year then do;
  cases&year=cases;
  if med_year then
  pctchg=(cases&year-med_year)/med_year;
  else pctchg=0;
end;
if year=&prevyear then cases&prevyear=cases;
if last.PHC_code_short_desc and (year =&year or year=&year-1);
label
 PHC_code_short_desc='Disease'
 mthcases="&thismth"
 cases&year="Cumulative for &year to Date"
 cases&prevyear="Cumulative for &prevyear to Date"
 med_year='5 Year Median Year to Date'
 pctchg="Percent Change &year vs 5 Year Median";
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "SR5: Cases of Reportable Diseases by State"</p> ;
Title2 HEIGHT=4 FONT=Times <p align="center"> "&stname "</p>;
Title3 HEIGHT=4 FONT=Times <p align="center"> "&currdate"</p>;
Title4 <div style='background-color:#E6E6E6;'><table><tr><td style='font-size:11pt;font-family:Times New Roman;'><b><U>Report content</U></b></td>
	</tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Output:</b> Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by County irrespective of Case Status.   Output:
	<LI>Does not include Investigation(s) that have been logically deleted</LI>
	<LI>Is filtered based on the state, disease(s) and advanced criteria selected by user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the State selected by the user</LI>
	<LI>Is based on month and year of the calculated Event Date</LI>
	</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Calculations:</b></td></tr><tr><td style='font-size:11pt;font-family:Times New Roman;'>
	<LI><b>Current Month Totals by disease:</b>  Total Investigation(s) [both Individual and Summary] where the Year and Month of the Event Date equal the current Year and Month</LI>
	<LI><b>Current Year Totals by disease:</b>  Total Investigation(s) [both Individual and Summary] where the Year of the Event Date equal the current Year</LI>
	<LI><b>Previous Year  Totals by disease:</b>  Total Investigation(s) [both Individual and Summary] where the Year of the Event Date equal last Year</LI>
	<LI><b>5-Year median:</b>  Median number of Investigation(s) [both Individual and Summary] for the past five years</LI>
	<LI><b>Percentage change (current year vs. 5 year median):</b>   Percentage change between the Current Year Totals by disease and the 5-Year median</LI>
	<LI><b>Event Date:</b>  Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.</LI>
	</td></tr></table>
	<div>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_top_matter no_bottom_matter);

Proc print data=rptdata noobs Label;
var PHC_code_short_desc mthcases cases&year cases&prevyear med_year pctchg;
run;
quit;
ods html close;
%end;
%else 
      %export(work,rptdata,sock,&exporttype);

Title;
Footnote;
%finish:
%mend nbssr00005;
%nbssr00005;
