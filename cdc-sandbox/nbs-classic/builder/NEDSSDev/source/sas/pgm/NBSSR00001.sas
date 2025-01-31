**************************************************************************************;
*** NBSSR0001: 2 year Disease Count (Year to Date) With Percentage Change,    by   ***;
***            Geographic Area  												   				  ***;
*** CREATED : 01-10-2002														   				  ***;
*** MODIFIED: 03-13-2002	To bring the program on the line of Beta1 Reports		  ***;	
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   			  ***;
**************************************************************************************;
%macro NBSSR00001;
************************************************************************;
** SORTING THE DATA BY STATE REGION DISEASE AND YEAR                    ;
************************************************************************;

proc sql;
create table sr1 
as select 
State_cd,
State,
region_district_cd,
phc_code_short_desc,
year(datepart(diagnosis_date)) as year,
month(datepart(diagnosis_date)) as month,
count(*) as cases
from &DataSourceName
by group state_cd,state,region_district_cd,PHC_code_short_desc,year,month
order by state_cd,state,region_district_cd,PHC_code_short_desc,year,month;

quit;

Data _null_;
set sr1(obs=1);
call symput('stname',trim(State));
call symput('rptdate',put(today(),MMDDYY10.));
call symput('update',put(today(),MMDDYY10.));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
call symput('thismth',left(put(today(),Monname9.)));
run;

proc summary data=sr1(where=(month le &month and year ge &year-1));
class state_cd region_district_cd PHC_code_short_desc year;
var cases;
output out=sr1_sum(drop= _freq_  where=(_type_ in (15 3))) sum=;
run;

************************************************************************;
** COUNTING YTD CASES PER REGION PER DISEASE                            ;
************************************************************************;
proc sort data=sr1_sum(drop=_type_);
	by state_cd region_district_cd phc_code_short_desc descending year ;
run;

data sr1_ytd(drop=hold_cases);
	set sr1_sum;
 	by state_cd region_district_cd phc_code_short_desc descending year;
 	if first.phc_code_short_desc=last.phc_code_short_desc then do;
		hold_cases=cases;
		if year=&year then do;
			output;
			cases=0; year=year-1; output;
		end;
		else if year=&prevyear then do;
			cases=0; year+1; output;
			cases=hold_cases; year=year-1; output;
		end;
	end;
	else output;
run;

proc sql;
	create table state
	as select distinct state_cd
	from sr1_ytd;

	create table regions
	as select distinct region_district_cd
	from sr1_ytd;

	create table disease
	as select distinct phc_code_short_desc
	from sr1_ytd;

	create table years
	as select distinct year
	from sr1_ytd;

	create table comp_data 
	as select *, 0 as cases
	from state, regions, disease, years
	order by state_cd,region_district_cd,phc_code_short_desc,year descending;
quit;

data sr1_ytdf;
  merge comp_data(in=b) sr1_ytd(in=a) ;
  by state_cd region_district_cd phc_code_short_desc descending year;
  if a and b then output;
  if b and not a then do;
     cases=0;
	  output;
	end;
run;

proc transpose data=sr1_ytdf out=sr1_tran(drop=_name_) prefix=YTD_;
	by state_cd region_district_cd phc_code_short_desc;
	id year;
run;
data sr1_pct_chng;
format pct_chng percent9.2;
  set sr1_tran;
  label pct_chng='Percent Change'
		ytd_&prevyear="Year to Date &prevyear"
		ytd_&year="Year to Date &year";;
  if ytd_&prevyear then
  	pct_chng=(ytd_&year-ytd_&prevyear)/(ytd_&prevyear);
  else pct_chng=0;
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> " &stname 'Reported Diseases'"</p> ;
Title2 HEIGHT=4 FONT=Times <p align="center">"2 Year Disease Count(year to date) with Percentage Change, by Geographic Area" </p>;
Title3 HEIGHT=4 FONT=Times <p align="center"> "Current Month of Previous Year to Current Month of Current Year"</p>;

%parse_wcls;

%if %upcase(&exporttype)=REPORT %then %do;

data sr1_pct_chng;
  set sr1_pct_chng;
    if region_district_cd=' ' then region_district_cd="[ &stname ]";
run;

ods html body=sock (no_top_matter no_bottom_matter);
Proc tabulate data=sr1_pct_chng ;
class region_district_cd phc_code_short_desc;
var ytd_&prevyear ytd_&year pct_chng ;
table phc_code_short_desc='', 
      region_district_cd=''*
      ((ytd_&year ytd_&prevyear)*f=8. pct_chng*f=percent9.2)
/ box="&thismth" ;
keylabel sum=' ';
run;
ods html close;
%end;

%if %upcase(&exporttype)=EXPORT_ASCII_CSV %then %do;
      %ascii(work,sr5,sock,label,comma);
%end;

%if %upcase(&exporttype)=EXPORT_ASCII_TAB %then %do;
      %ascii(work,sr5,sock,label,tab);
%end;

Title;
Footnote;
%finish:

%mend NBSSR00001;
%NBSSR00001;
