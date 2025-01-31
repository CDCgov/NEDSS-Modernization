**********************************************************************************;
*** Macro: NBSSR00010.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00010;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

data sr00010;
	set work.PHCDemographic;
	if state_cd='' then state_cd='N/A';
	if state='' then state='N/A';
	if county='' then county='N/A';
	if cnty_cd='' then cnty_cd='N/A';
run;

Proc SQL;
Create table sr10 
as select 
state_cd,
State,
County,
PHC_code_short_desc,
year(datepart(event_date)) 		as year,
sum(group_case_cnt)	 as Cases	label='Cases'
from sr00010
by group state_cd,state,county,PHC_code_short_desc,year;

quit;

Data _null_;
set sr10(obs=1);
call symput('stname',trim(fipnamel(input(state_cd,2.))));
call symput('disease',trim(PHC_code_short_desc));
run;

Proc SQL noprint;
select min(year)
into: first_year
from sr10;

select max(year)
into: last_year
from sr10;
quit;


Proc Means data=sr10(keep=state PHC_code_short_desc year cases) noprint;
class year;
id state PHC_code_short_desc;
var cases;
output out=rptdata sum=;
run;

Data rptdata;
set rptdata;
if year ne .;
run;

Data _null_;
retain max_cases;
set rptdata nobs=numobs;
if _N_=1 then max_cases=cases*1.25;
else if cases > max_cases then max_cases=cases*1.25;
if _N_=numobs then do;
call symput('max_cases',put(max_cases,8.));
if max_cases ge 4 then
	call symput('maxdiv',put(floor(max_cases/4),8.));
else call symput('maxdiv',1);
end;
run;

Title;
Title1 "SR10: Multi-Year Line Graph of Disease Cases ";
Title2 "For &stname &timeperiod";
Title3 J=L UNDERLIN=1 F=SWISS HEIGHT=.8 'Report content'
       J=L 'Data Source: nbs_ods.PHCDemographic (publichealthcasefact)'
       J=L ' '
       J=L 'Output: Report demonstrates, using a multi-year line graph, the total number of yearly Investigation(s)' 
       J=L '[both Individual and Summary] for a given disease, by State, irrespective of Case Status. Output:'
       J=L '1) Does not include Investigation(s) that have been logically deleted'
       J=L '2) Is filtered based on the state, disease, time frame and advanced filter criteria selected by the user'
       J=L '3) Will not include Investigation(s) that do not have a value for the State selected by the user'
       J=L '4) Is based on the calculated Event Date'
       J=L ' '
       J=L 'Calculations:'
       J=L '1) Event Date: Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to' 
       J=L '   State and Date the Investigation was created in the NBS.';




%footnote; 

%if %upcase(&exporttype)=REPORT %then %do;
goptions reset=goptions;
goptions gsfname=sock device=gif nocharacters;

axis1 order=(&first_year to &last_year by 1)
      label=(HEIGHT=1 Font="SWISS" 'Year')
      COLOR=Blue;

axis2 order=(0 to &max_cases by &maxdiv)
      label=(HEIGHT=1 Font="SWISS" Angle=90 'Number of Cases')
      COLOR=Blue;

symbol1 color=violet
        interpol=join
        value=dot
        height=1;

  proc gplot data=rptdata;
   plot cases*year  /haxis=axis1 
                     vaxis=axis2;
                      
run;
quit;
goptions reset=goptions;
%end;
%else 
      %export(work,sr10,sock,&exporttype);
Title;
Footnote;
%finish:
%mend nbssr00010;
%nbssr00010;
