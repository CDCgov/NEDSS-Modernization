**************************************************************************************;
*** NBSSR0003: Counts of Cases of Selected Diseases by City, Region and State by   ***;
***            Month and Quarter												   				  ***;
*** CREATED : 01-10-2002														   				  ***;
*** MODIFIED: 03-13-2002	To bring the program on the line of Beta1 Reports		  ***;	
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   			  ***;
**************************************************************************************;
%macro NBSSR00003;
*options macrogen symbolgen;

************************************************************************;
** SORTING THE DATA BY STATE REGION DISEASE AND YEAR                    ;
************************************************************************;
proc sql;
create table sr3
as select 
state_cd,
State,
region_district_cd,
phc_code_short_desc,
year(datepart(diagnosis_date)) as year,
month(datepart(diagnosis_date)) as month,
qtr(datepart(diagnosis_date)) as quarter,
count(*) as cases
from &DataSourceName
by group state_cd,state,region_district_cd,PHC_code_short_desc,year,month
order by state_cd,state,region_district_cd,PHC_code_short_desc,year,month;
quit;

data sr3;
   set sr3;
	if state ne '' and state ne '' and region_district_cd ne ' '
		and phc_code_short_desc ne '' and year;
run;

Data _null_;
set sr3(obs=1);
call symput('stname',trim(State));
call symput('rptdate',put(today(),MMDDYY10.));
call symput('update',put(today(),MMDDYY10.));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
call symput('quarter',left(put(month(today()),2.)));
call symput('thismth',left(put(today(),Monname9.)));
call symput('thisqtr',left(put(today(),qtr.)));
run;

proc summary data=sr3(where=(month le &month and year eq &year));
class state region_district_cd PHC_code_short_desc year quarter month;
var cases;
output out=sr3_sum(drop= _freq_ where=(_type_ in (63 47))) sum=;
run;

************************************************************************;
** COUNTING YTD CASES PER REGION PER DISEASE                            ;
************************************************************************;
proc sort data=sr3_sum(drop=_type_);
	by state region_district_cd phc_code_short_desc year quarter month;
run;

proc sql;
	create table state
	as select distinct state
	from sr3_sum;

	create table regions
	as select distinct region_district_cd
	from sr3_sum;

	create table disease
	as select distinct phc_code_short_desc
	from sr3_sum;

	create table years
	as select distinct year
	from sr3_sum;

	create table qtrs
	as select distinct quarter
	from sr3_sum;

	create table months
	as select distinct month
	from sr3_sum;

	create table comp_data 
	as select *, 0 as cases
	from state, regions, disease, years, qtrs, months
	order by state,region_district_cd,phc_code_short_desc,year,quarter,month ;
quit;

data sr3_sumf;
  merge comp_data(in=b) sr3_sum(in=a) ;
  by state region_district_cd phc_code_short_desc year quarter month;
  if a and b then output;
  if b and not a then do;
     cases=0; 
	  output;
	end;
run;	

data sr3_ytd(drop=ytd);
	set sr3_sumf;
	retain ytd 0;
 	by state region_district_cd phc_code_short_desc year;
	if first.year then do;
		ytd_cases=cases;
	end;
	if not first.year then do;
		ytd_cases=cases+ytd;
	end;
	ytd=ytd_cases;
run;

%if %upcase(&exporttype)=REPORT %then %do;

data sr3_ytd_qtr;
  set sr3_ytd;
  by region_district_cd phc_code_short_desc year quarter month;
  if last.quarter;
run;

data sr3_ytd;
  set sr3_ytd;
    if region_district_cd=' ' then region_district_cd="[ &stname ]";
run;

data sr3_ytd_qtr;
  set sr3_ytd_qtr;
    if region_district_cd=' ' then region_district_cd="[ &stname ]";
run;


ods html body=sock (no_top_matter no_bottom_matter);

Title;
footnote;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> " Counts of Cases of Selected Diseases by Region and State by Month and Quarter" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "For &stname and &timeperiod"</p>;


proc format; 
value mon
1='January'
2='February'
3='March'
4='April'
5='May'
6='June'
7='July'
8='August'
9='September'
10='October'
11='November'
12='December'
;
run;

%do num_of_month=1 %to 12;
	%let mon_name=%sysfunc(putn(&num_of_month,mon.));
	Proc tabulate data=sr3_ytd(where=(month=&num_of_month)) format=8.0;
	class region_district_cd phc_code_short_desc year quarter month;
	var cases ytd_cases ;
	table Quarter*month,phc_code_short_desc='', 
      region_district_cd=''*cases='' region_district_cd=''*ytd_cases='YTD'
		/ box="&mon_name";
	keylabel sum=' ';
	run;

	%if %eval(%sysfunc(mod(&num_of_month,3))) eq 0 %then %do;
		%let num_of_qtr=%eval(%sysfunc(int(&num_of_month/3)));
		Proc tabulate data=sr3_ytd_qtr(where=(quarter=&num_of_qtr)) format=8.0;
		class region_district_cd phc_code_short_desc year quarter month;
		var cases ytd_cases ;
		table Quarter='Total for Quarter',phc_code_short_desc='', 
      	region_district_cd=''*ytd_cases=''
		/ box=" ";
		keylabel sum=' ';
		run;
	%end;
Title;
%end;
data _null_;
file print;
put a;
%parse_wcls;
run;

ods html close;
%end;

%if %upcase(&exporttype)=EXPORT_ASCII_CSV %then %do;
      %ascii(work,sr3,sock,label,comma);
%end;

%if %upcase(&exporttype)=EXPORT_ASCII_TAB %then %do;
      %ascii(work,sr3,sock,label,tab);
%end;

Title;
Footnote;
options nomacrogen nosymbolgen;
%mend NBSSR00003;

%NBSSR00003;
