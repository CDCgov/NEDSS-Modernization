**************************************************************************************;
*** NBSSR0004: Cases of Disease by Month, by Selected Area With Annual and YTD     ***;
***            Cumulative Totals for the Region, City and State                    ***;
*** CREATED : 01-10-2002														   				  ***;
*** MODIFIED: 03-18-2002	To bring the program on the line of Beta1 Reports		  ***;
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   			  ***;
**************************************************************************************;

%macro NBSSR00004;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

proc format;
value mon
	1	=	'JAN'
	2	=	'FEB'
	3	=	'MAR'
	4	=	'APR'
	5	=	'MAY'
	6	=	'JUN'
	7	=	'JUL'
	8	=	'AUG'
	9	=	'SEP'
	10	=	'OCT'
	11	=	'NOV'
	12	=	'DEC'
	13	=	'YTD'
;
run;

************************************************************************;
** SORTING THE DATA BY STATE COUNTY DISEASE AND YEAR                    ;
************************************************************************;
proc sql;
create table sr4
as select 
state_cd,
State,
County,
phc_code_short_desc,
year(datepart(diagnosis_date)) as year,
month(datepart(diagnosis_date)) as month format mon.,
count(*) as cases
from &DataSourceName
where state ne '' and state ne '' and County ne ' '
		and phc_code_short_desc ne '' and diagnosis_date gt 0
group by state_cd,state,PHC_code_short_desc,County,month,year
order by state_cd,state,PHC_code_short_desc,County,month,year;

create table counties 
as select distinct county,state,state_cd,phc_code_short_desc
from sr4;
quit;

Data _null_;
set sr4(obs=1);
call symput('stname',trim(State));
call symput('disease',trim(phc_code_short_desc));
call symput('rptdate',put(date(),MMDDYY10.));
call symput('update',put(date(),MMDDYY10.));
call symput('inityear',left(put(year(today())-5,4.)));
call symput('secondyear',left(put(year(today())-4,4.)));
call symput('thirdyear',left(put(year(today())-3,4.)));
call symput('fourthyear',left(put(year(today())-2,4.)));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
run;

data full(drop=i j);
 set counties;
 do i=1 to 12;
    month=i;
	 do j=5 to 0 by -1;
	 	year=&year-j;
		cases=0;
		output;
	 end;
 end;
run;

data sr4full;
 merge full sr4;
 by state state_cd phc_code_short_desc county month year;
run;

proc transpose data=sr4full out=sr4tran(drop=_name_) prefix=Y;
by state_cd state PHC_code_short_desc County month;
id year;
run;

proc summary data=sr4tran nway;
class state state_cd month;
var Y&prevyear Y&year;
output out=sr4sum(drop=_type_ _freq_) sum=S&prevyear S&year;
run;

data sr4tran;
merge sr4tran sr4sum;
run;

data sr4ytd;
  set sr4tran;
  retain YTD&inityear- YTD&year sprev spres 0;
  by state_cd state PHC_code_short_desc County month;
	if first.county then do;
		YTD&inityear=0;
		YTD&secondyear=0;
		YTD&thirdyear=0;
		YTD&fourthyear=0;
		YTD&prevyear=0;
		YTD&year=0;
		spres=0;
		sprev=0;
	end;
   else do;
	   YTD&inityear+Y&inityear;
		YTD&secondyear+Y&secondyear;
		YTD&thirdyear+Y&thirdyear;
		YTD&fourthyear+Y&fourthyear;
		YTD&prevyear+Y&prevyear;
		YTD&year+Y&year;
		yprev+Y&prevyear;
		y+Y&year;
		s&prevyear+sprev;
	end;
	sprev+s&prevyear;
	spres+s&year;
run;

data sr4year;
   set sr4ytd;
	by state_cd state PHC_code_short_desc County month;
	if month=12 then do;
		Ychange=S&year-S&prevyear;
		YTDchange=YTD&year-YTD&prevyear;
		output;
		Y&inityear=YTD&inityear;
		Y&secondyear=YTD&secondyear;
		Y&thirdyear=YTD&thirdyear;
		Y&fourthyear=YTD&fourthyear;
		Y&prevyear=YTD&prevyear;
		Y&year=YTD&year;
		s&prevyear=sprev;
		s&year=spres;
		month=13;
	end;
	Ychange=S&year-S&prevyear;
	YTDchange=YTD&year-YTD&prevyear;
output;
run;

proc summary data=sr4year nway;
class month;
var YTD&prevyear YTD&year;
output out=sr4ytdyear(drop=_type_ _freq_) sum=Sytd&prevyear Sytd&year;
run;

data sr4yeartest;
merge sr4year sr4ytdyear;
run;

data sr4yeartest;
   set sr4yeartest;
	if sytd&year then
	sytd_perchange=(sytd&year-sytd&prevyear)/sytd&year;
	else sytd_perchange=0;
run;

data sr4per;
   set sr4yeartest;
	if yprev then
		ytdper=(y-yprev)/y;
	else ytdper=0;
	if Y&year then do;
	   perchange=Ychange/(Y&year);
	end;
	else perchange=0;
	if ytd&year then
		ytd_perchange=ytdchange/ytd&year;
	else
		ytd_perchange=0;

	if S&year then
		s_perchange=(s&year-s&prevyear)/s&year;
	else
	   s_perchange=0;
	format perchange ytd_perchange  ytdper s_perchange percent9.;
	label  perchange='% Change' ytd_perchange='% Change' ytdper='% Change'
		s_perchange='% Change' sytd_perchange='% Change';

	YTD_mean=mean(of ytd&inityear - ytd&prevyear);
	YTD_median=ordinal(3, of ytd&inityear - ytd&prevyear);
run;

Title;
Footnote;

%if %upcase(&exporttype)=REPORT %then %do;

ods html body=sock (no_top_matter no_bottom_matter);
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Disease by month and YTD cumulative Totals"</p> ;
Title2 HEIGHT=4 FONT=Times <p align="center"> "For &stname., &timeperiod"</p>;

proc tabulate data=sr4per format=4.;
class state county month;
var Y&inityear - Y&year YTD&inityear - YTD&year perchange ytd_perchange ychange s_perchange ytd_mean ytd_median
	  ;

table (Y&inityear Y&secondyear Y&thirdyear Y&fourthyear Y&prevyear Y&year)*(county='' all="&stname"*[style=[background=white]]) 
	   ychange='Change' state=' '*s_perchange*f=percent9. ,month='';
keylabel sum=' ';
run;
Title;

proc tabulate data=sr4per format=4.;
class state county month;
var Y&inityear - Y&year YTD&inityear - YTD&year perchange ytdchange ytd_perchange ychange s_perchange ytd_mean ytd_median
    sytd_perchange;

table
		(state=''*county='' )*(YTD&inityear YTD&secondyear YTD&thirdyear YTD&fourthyear 
		YTD&prevyear YTD&year ytd_mean*f=8.2 ytd_median ytdchange ytd_perchange*f=percent9.)
		,month=' ';

keylabel sum=' ';
run;

%parse_wcls;
proc tabulate data=sr4per format=4.;
class state month;
var Y&inityear - Y&year YTD&inityear - YTD&year perchange ytdchange ytd_perchange ychange s_perchange ytd_mean ytd_median
    sytd_perchange;

table
		state=''*(YTD&inityear YTD&secondyear YTD&thirdyear YTD&fourthyear 
		YTD&prevyear YTD&year ytd_mean*f=8.2 ytd_median ytdchange sytd_perchange*f=percent9.)
		,month=' ';

keylabel sum=' ';
*keyword=sum;
run;
Title;
Footnote;
ods html close;
%end;

%else 
      %export(work,sr4,sock,&exporttype);

%finish:
%mend NBSSR00004;
%NBSSR00004;