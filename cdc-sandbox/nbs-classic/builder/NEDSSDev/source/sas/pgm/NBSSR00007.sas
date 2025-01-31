**********************************************************************************;
*** Macro: NBSSR00007.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00007;
/*
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
*/
Proc SQL noprint;
Create table sr7 
as select 
state_cd,
State,
PHC_code_short_desc	length=32,
year(datepart(event_date)) 			as year		label='Year',
month(datepart(event_date)) 		as month	label='Month',
sum(group_case_cnt)	 				as Cases	label='Cases'
from &DataSourceName
where datepart(event_date) LE date() 
group by state_cd,state,PHC_code_short_desc,year,month

order by PHC_code_short_desc,year,month;
quit;

/*
DataSet Sr7 may be empty even when &DataSourceName has records.
This happens if &DataSourceName contans only summary data that  
all have future event_date. 

Until the report date for summary data is Changed to the beginning  of the week,
i.e. Sunday, instead of end of week or Saturday, we need to check if SR7 is empty
before continuing with the rest of the program.
*/
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;



Data _null_;
set sr7(obs=1);
call symput('stname',trim(State));
call symput('inityear',left(put(year(today())-5,4.)));
call symput('prevyear',left(put(year(today())-1,4.)));
call symput('year',left(put(year(today()),4.)));
call symput('month',left(put(month(today()),2.)));
call symput('currdate', put(date(), mmddyy10.));
run;

data sr7;
  set sr7;
  where month le &month and year ge &inityear;
run;

proc sql;
Create table Disease as
select distinct PHC_code_short_desc from sr7
order by PHC_code_short_desc;
quit;

Data _null_;
retain diseases;
length diseases $30000;
set disease nobs=numobs;
if _N_=1 then diseases=trim(PHC_code_short_desc);
else diseases=trim(diseases)||'^'||trim(PHC_code_short_desc);
if _N_=numobs then call symput('diseaseSr7',trim(diseases));
run;
%put disease=&diseaseSr7;
data full(drop=i j k);
	length phc_code_short_desc $32;
  	k=1;
  	do while (scan("&diseaseSr7",k, '^' ) ne ' ');
  		phc_code_short_desc=scan("&diseaseSr7",k,'^');
		put phc_code_short_desc=;
  		k+1;
		do i=5 to 0 by -1;
  			do j=1 to &month;
  				year=&year-i;
  				month=j;
				cases=0;
				state="&stname";
  				output;
		  	end;
  		end;
	end;
	call symput('VDimension', put((4+k/2), 4.));
run;

proc sort data=full; by phc_code_short_desc year month; run;

data sr7full;
merge full sr7;
by phc_code_short_desc;
run;

proc sort data=sr7full;
	by phc_code_short_desc year month;
run;

Proc means data=sr7full noprint;
by PHC_code_short_desc year;
var cases;
output out=rptsr7(drop=_type_ _freq_) sum=;
run;

proc univariate data=rptsr7(where=(year ne &year)) noprint;
by PHC_code_short_desc;
var cases;
output out=med median=med_year;
run;

Data rptdata(keep=PHC_code_short_desc type Cases_ytd);
format pctchg percent9.2 ;
retain cases&prevyear cases&year;
merge rptsr7(in=in1) med(in=in2);
by PHC_code_short_desc;
if first.PHC_code_short_desc then do;
cases&year=0;
end;
if year=&year then do;
  cases&year=cases;
end;
if last.PHC_code_short_desc and (year =&year) then do;
  type="Five Year Median YTD";
  Cases_ytd=med_year;
  output;
  type="Current YTD";
  Cases_ytd=cases&year;
  output;
end;
label
 PHC_code_short_desc='Disease'
 cases&year="Cumulative for &year to Date"
 Cases_ytd='Number of Cases';
run;

Title;
Title1 F=SWISS HEIGHT=1 "SR7: Bar Graph of Cases of Selected Diseases vs. 5-Year Median for Selected Time Period ";
Title2 F=SWISS HEIGHT=.7 "For &stname ";
Title3 F=SWISS HEIGHT=.7 "&currdate";
Title4 J=L UNDERLIN=1 F=SWISS HEIGHT=.7 'Report content'
       J=L 'Data Source: nbs_ods.PHCDemographic (publichealthcasefact)'
       J=L ' '
       J=L 'Output: Report demonstrates, using a horizontal bar graph, Investigation(s) [both Individual and Summary]' 
       J=L 'by year-to-date, and 5-year median irrespective of Case Status.  Output:'
       J=L '1) Does not include Investigation(s) that have been logically deleted'
       J=L '2) Is filtered based on the state, disease(s) and advanced criteria selected by user'
       J=L '3) Will not include Investigation(s) that do not have a value for the State selected by the user'
       J=L '4) Is based on month and year of the calculated Event Date'
       J=L ' '
       J=L 'Calculations:'
       J=L '1) Current Year Totals by disease:  Total Investigation(s) [both Individual and Summary] where the' 
       J=L '   Year of the Event Date equal the current Year'
       J=L '2) 5-Year median: Median number of Investigation(s) [both Individual and Summary] for the past five years'
       J=L '3) Event Date: Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to' 
       J=L '   State and Date the Investigation was created in the NBS.';

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
  goptions reset=goptions;
  goptions transparency gsfname=sock device=gif noborder
  	xmax=8in ymax=&VDimension in; 
  pattern1 color=blue;
  pattern2 color=green;
  axis1 label=none
        major=none
        minor=none
        value=none;
  axis2 label=(HEIGHT=1 Font="SWISS" 'Number of Cases');
  axis3 label=(HEIGHT=1 JUSTIFY=LEFT Font="SWISS" 'Diseases')
        major=none
        minor=none;
  legend1 label= none
          cborder=black
          position=(top Center outside)
          mode=protect
          ;
GOPTIONS NOCHARACTERS;
proc gchart data=rptdata;
	/*32 is max length for label */
   format PHC_code_short_desc $32.;
   hbar3d Type/sumvar=Cases_ytd group=PHC_code_short_desc 
             maxis=axis1 raxis=axis2 gaxis=axis3 subgroup=Type
                   legend=legend1 name="_5yrmed";
              run;quit;
%end;
%else 
      %export(work,rptdata,sock,&exporttype);

Title;
Footnote;
%finish:
%mend nbssr00007;
%nbssr00007;

