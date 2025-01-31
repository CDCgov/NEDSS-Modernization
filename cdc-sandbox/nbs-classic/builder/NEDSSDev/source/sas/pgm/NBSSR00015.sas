**************************************************************************************;
*** NBSSR0015: Bar chart of 5-Year Case Count, By Month, for Selected Diseases     ***;
***                                                                                ***;
*** CREATED : 01-11-2002														   ***;
*** MODIFIED: 03-28-2002    Modified 0n the lines of Beta1 Reports					  ***;
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   ***;
**************************************************************************************;

%macro NBSSR00015;

%chk_mv;
%if %upcase(&mac_var_missing)=YES or %upcase(&mac_val_missing)=YES 
	or %upcase(&skip)=YES %then
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
create table sr15
as select 
state_cd,
State,
phc_code_short_desc			label='Disease',
year(datepart(deceased_time)) 						as year			label='Year',
month(datepart(deceased_time)) 						as month			label='Month',
count(*) as cases
from &DataSourceName
where state ne '' and state ne ''
		and phc_code_short_desc ne ''
group by state_cd,state,PHC_code_short_desc,year,month
order by state_cd,state,PHC_code_short_desc,year,month;

create table disease
as select distinct state,state_cd,phc_code_short_desc
from sr15;
quit;

Data _null_;
set sr15(obs=1);
call symput('stname',trim(State));
call symput('disease',trim(phc_code_short_desc));
call symput('rptdate',put(date(),MMDDYY10.));
call symput('update',put(date(),MMDDYY10.));
call symput('inityear',left(put(year(today())-4,4.)));
call symput('midyear',left(put(year(today())-2,4.)));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
run;

data full(drop=i j);
 set disease;
	 do i=1 to 12;
    	month=i;
		do j=4 to 0 by -1;
	 		year=&year-j;
			cases=0;
			output;
	 end;
 end;
run;

data sr15full;
 merge full sr15;
 by state state_cd phc_code_short_desc month year;
run;

proc sort data=sr15full; by month year state;
format month mon.;
run;

proc sql;
create table sr15avg 
as select
year,
month,
avg(cases) as cases
from sr15full
group by month;
quit;

data sr15avg_;
   set sr15avg;
	where year=&midyear;
run;

data anno;

   set sr15avg;
	by month year;
   length function color style $8;
   retain xsys ysys '2' hsys '3' color 'black' line 1 when 'a';

   midpoint=year; 
	y=cases; 

	group=month; subgroup=year;
	format group mon.;

   /* place plot symbol */
   function = 'label';
   position = '+';         /* center the text */
	if first.month and first.year or
		last.month and first.year		then
   	size     = 2;           /* size of the character is 5% */
	else size=0.25;

   style    = 'swissb';
   text     = 'O';
   output;

   /* locate beginning of plot line */
   if _n_=1 then do;
           function='move';
            output;
            end;
if first.month and first.year and _n_ > 1 then do;
            function='draw';
            size=.25;        /* width of line is .25% */
            output;
            end;

            /* draw plot line */
            *else do;
if last.month and last.year then do;
            function='draw';
            size=.25;        /* width of line is .25% */
            output;
            end;
       run;


goptions reset=all;
Title;
Footnote;

goptions transparency gsfname=sock device=gif noborder nocharacters;
options ls=132 spool;

%local endyear;
%let endyear=%eval(&year-5);

Title1 BOLD HEIGHT=5 FONT=Times <p align="center">"Number of Deaths by &disease during &inityear - &year"</p>;

axis1 value=none label=none;
proc gchart data=sr15full;
   vbar year/sumvar=Cases group=month subgroup=year maxis=axis1 annotate=anno;
   run;
quit;

/*proc gplot data=anno;
   plot avg*month;
   run;
quit;

proc greplay nofs 
igout=work.gseg tc=sashelp.templt template=whole;
treplay 1:gchart8 1:gplot1;
run;
quit;
*/
goptions reset=all;
Title;
Footnote;

%finish:

%mend NBSSR00015;
%NBSSR00015;